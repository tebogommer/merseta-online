"use server";

import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

export interface LegacyUserPayload {
  email: string;
  role: string;
  firstName: string;
  lastName: string;
  rsaIdNumber?: string;
  passportNumber?: string;
  genderCode?: string;
  equityCode?: string;
  nationalityCode?: string;
}

/**
 * Derives Date of Birth from a standard 13-digit RSA ID Number
 * Expected format YYMMDD...
 */
function extractDOBFromRsaId(idNumber: string): Date | null {
  if (!idNumber || idNumber.length !== 13) return null;
  const yearStr = idNumber.substring(0, 2);
  const monthStr = idNumber.substring(2, 4);
  const dayStr = idNumber.substring(4, 6);

  const yearNum = parseInt(yearStr, 10);
  const monthNum = parseInt(monthStr, 10);
  const dayNum = parseInt(dayStr, 10);

  if (isNaN(yearNum) || isNaN(monthNum) || isNaN(dayNum)) return null;

  // Assuming 2000s if < 30, 1900s if >= 30. (Heuristic for current system constraints)
  const fullYear = yearNum < 30 ? 2000 + yearNum : 1900 + yearNum;
  
  // Creates Date at Midnight UTC
  const dob = new Date(Date.UTC(fullYear, monthNum - 1, dayNum));
  return isNaN(dob.getTime()) ? null : dob;
}

/**
 * Synchronizes a legacy monolithic user record into the split User/Person architecture.
 * Transforms demographic identification parameters into Relational Lookups.
 */
export async function syncLegacyUser(payload: LegacyUserPayload) {
  return await prisma.$transaction(async (tx: any) => {
    
    // 1. Resolve External Relational Lookups
    let genderId = null;
    let equityId = null;
    let nationalityId = null;

    if (payload.genderCode) {
      const g = await tx.genderType.findFirst({ where: { code: payload.genderCode.toUpperCase() } });
      genderId = g?.id || null;
    }

    if (payload.equityCode) {
      const e = await tx.equityType.findFirst({ where: { code: payload.equityCode.toUpperCase() } });
      equityId = e?.id || null;
    }

    if (payload.nationalityCode) {
      const n = await tx.nationalityType.findFirst({ where: { code: payload.nationalityCode.toUpperCase() } });
      nationalityId = n?.id || null;
    }

    // 2. Perform ID Number / Demographics Transformation
    let finalRsaId = undefined;
    let finalPassport = undefined;
    
    if (payload.rsaIdNumber && payload.rsaIdNumber.length === 13) {
      finalRsaId = payload.rsaIdNumber;
    } else if (payload.passportNumber) {
      finalPassport = payload.passportNumber;
    } else if (payload.rsaIdNumber && payload.rsaIdNumber.length !== 13) {
      // Fallback: If legacy threw passport into RSA ID field
      finalPassport = payload.rsaIdNumber;
    }

    const derivedDob = finalRsaId ? extractDOBFromRsaId(finalRsaId) : null;

    // 3. Upsert Identity Record (User)
    const user = await tx.user.upsert({
      where: { email: payload.email },
      update: {
        role: payload.role,
        name: `${payload.firstName} ${payload.lastName}`,
      },
      create: {
        email: payload.email,
        role: payload.role || 'STANDARD',
        name: `${payload.firstName} ${payload.lastName}`,
      }
    });

    // 4. Upsert Demographic Profile (Person)
    const person = await tx.person.upsert({
      where: { userId: user.id },
      update: {
        firstName: payload.firstName,
        lastName: payload.lastName,
        rsaIdNumber: finalRsaId,
        passportNumber: finalPassport,
        dateOfBirth: derivedDob,
        genderId: genderId,
        equityId: equityId,
        nationalityId: nationalityId
      },
      create: {
        userId: user.id,
        firstName: payload.firstName,
        lastName: payload.lastName,
        rsaIdNumber: finalRsaId,
        passportNumber: finalPassport,
        dateOfBirth: derivedDob,
        genderId: genderId,
        equityId: equityId,
        nationalityId: nationalityId
      }
    });

    return { user, person };
  });
}
