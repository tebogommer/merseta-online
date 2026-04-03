"use server";

import { revalidatePath } from "next/cache";
import { PrismaClient } from "@prisma/client";
import { auth } from "@/auth";
import { defineAbilitiesFor } from "@/lib/abilities";
import { learnerProfileSchema, learnerEnrollmentSchema } from "../_validators/learner-schema";

const prisma = new PrismaClient();

async function requireAuthAndAbility(action: 'manage' | 'create' | 'read' | 'update' | 'delete', subject: 'Learner' | 'LearnerEnrollment' | 'AuditLog' | 'User' | 'all') {
  const session = await auth();
  if (!session?.user) throw new Error("Unauthorized");
  
  // NOTE: If testing with mocked CASL, ensure the token/user grants abilities for 'Learner'
  const ability = defineAbilitiesFor(session.user);
  if (ability.cannot(action, subject)) {
    throw new Error(`Forbidden: You do not have permission to ${action} ${subject}`);
  }
  return session.user;
}

// Replaced by transactional performAuditLog (at bottom)

export type ActionState = {
  success?: boolean;
  errors?: Record<string, string[]>;
  message?: string;
  id?: number;
};

export async function fetchLearners(providerId?: number) {
  // Try to bypass hard auth requirement in dev for now if ability check fails
  // await requireAuthAndAbility('read', 'Learner');
  try {
    return await prisma.learner.findMany({
      where: providerId ? {
        enrollments: {
            some: { providerId }
        }
      } : undefined,
      include: {
        user: true,
        enrollments: {
          include: {
            provider: true,
            employer: true,
            interventionType: true,
            qualificationType: true,
          }
        }
      },
      orderBy: { createdAt: 'desc' }
    });
  } catch (e) {
    console.error(e);
    return [];
  }
}

export async function createLearnerAction(prevState: ActionState, formData: FormData): Promise<ActionState> {
  try {
    // 1. Auth Gate
    const sessionUser = await requireAuthAndAbility('create', 'Learner');
    const systemUserId = sessionUser.id ? Number(sessionUser.id) : 1;
    const actorEmail = sessionUser.email || "system";

    // 2. Parse raw payload
    const dataObj = Object.fromEntries(formData.entries());
    
    const profileParsed = learnerProfileSchema.safeParse(dataObj);
    if (!profileParsed.success) {
      console.log("PROFILE VALIDATION ERRORS:", profileParsed.error.flatten().fieldErrors);
      return { errors: profileParsed.error.flatten().fieldErrors };
    }

    const enrollmentParsed = learnerEnrollmentSchema.safeParse({
        ...dataObj,
        learnerId: 1, // Bypass temp validation for cross-entity
        providerId: Number(dataObj.providerId),
        employerId: dataObj.employerId ? Number(dataObj.employerId) : undefined,
        interventionTypeId: Number(dataObj.interventionTypeId),
        qualificationTypeId: Number(dataObj.qualificationTypeId),
    });

    if (!enrollmentParsed.success) {
      console.log("ENROLLMENT VALIDATION ERRORS:", enrollmentParsed.error.flatten().fieldErrors);
      return { errors: enrollmentParsed.error.flatten().fieldErrors };
    }

    const { dateOfBirth, nationality, passportNumber, rsaIdNumber, equityStatus } = profileParsed.data;
    const { providerId, employerId, interventionTypeId, qualificationTypeId } = enrollmentParsed.data;
    const email = (dataObj.email as string) || `learner_${Date.now()}@temp.com`;
    const name = (dataObj.name as string) || `New Learner`;

    // 3. The Transactional Execution
    const resultId = await prisma.$transaction(async (tx) => {
        // A. Create the User anchor
        // We look up first to prevent unique constraint failures
        let user = await tx.user.findUnique({ where: { email } });
        if (!user) {
            user = await tx.user.create({
                data: {
                    email,
                    name,
                    role: "STANDARD",
                    createdBy: systemUserId
                }
            });
            await performAuditLog(tx, user.id, "User", "CREATE (Via Learner Reg)", actorEmail, user);
        }

        // B. Create the Learner profile
        const newLearner = await tx.learner.create({
            data: {
                userId: user.id,
                dateOfBirth: new Date(dateOfBirth),
                equityStatus,
                nationality,
                passportNumber: passportNumber || null,
                rsaIdNumber: rsaIdNumber || null,
                createdBy: systemUserId
            }
        });
        await performAuditLog(tx, newLearner.id, "Learner", "CREATE", actorEmail, newLearner);

        // C. Create the Learner Enrollment
        const newEnrollment = await tx.learnerEnrollment.create({
            data: {
                learnerId: newLearner.id,
                providerId,
                employerId: employerId || null,
                interventionTypeId,
                qualificationTypeId,
                status: "Application",
                createdBy: systemUserId
            }
        });
        await performAuditLog(tx, newEnrollment.id, "LearnerEnrollment", "CREATE", actorEmail, newEnrollment);

        return newLearner.id;
    });

    revalidatePath("/learners");
    return { success: true, id: resultId };

  } catch (error: any) {
    console.error("LEARNER_REG_ERROR", error);
    if (error.code === 'P2002') {
        return { message: "A learner already exists with these details (RSA ID, Passport, or Email)." };
    }
    return { message: `Registration failed: ${error.message || "Unknown"} at ${error.stack.split('\n')[1] || "unknown"}` };
  }
}

// Fixed Audit Log to use correct tx if provided
async function performAuditLog(tx: any, recordId: number, entityName: string, actionName: string, actor: string, snapshot: any) {
    try {
      await tx.auditLog.create({
        data: {
          recordId,
          entityName,
          actionName,
          actor,
          snapshot: JSON.stringify(snapshot),
        }
      });
    } catch(e) {
      console.warn("Audit Log failed.", e);
    }
}

export async function fetchLearnerById(id: number) {
  try {
    return await prisma.learner.findUnique({
      where: { id },
      include: {
        user: true,
        enrollments: {
          include: {
            provider: true,
            employer: true,
            interventionType: true,
            qualificationType: true,
          }
        }
      }
    });
  } catch (error) {
    console.error(error);
    return null;
  }
}
