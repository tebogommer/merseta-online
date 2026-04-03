"use server";

import { PrismaClient } from "@prisma/client";
import { revalidatePath } from "next/cache";

const prisma = new PrismaClient();

export interface PersonPayload {
  id?: number;
  firstName: string;
  lastName: string;
  rsaIdNumber?: string | null;
  passportNumber?: string | null;
  dateOfBirth?: Date | null;
  genderId?: number | null;
  equityId?: number | null;
  nationalityId?: number | null;
  userId?: number | null;
}

export async function getPersons() {
  return await prisma.person.findMany({
    include: {
      gender: true,
      equity: true,
      nationality: true,
      user: true,
    },
    orderBy: { lastName: 'asc' }
  });
}

export async function getPersonById(id: number) {
  return await prisma.person.findUnique({
    where: { id },
    include: {
      gender: true,
      equity: true,
      nationality: true,
      user: true,
    }
  });
}

export async function savePerson(payload: PersonPayload) {
  const { id, ...data } = payload;
  
  const result = await prisma.$transaction(async (tx) => {
    let person;
    if (id) {
       person = await tx.person.update({
        where: { id },
        data
      });
    } else {
       person = await tx.person.create({
        data: data as any
      });
    }

    // Audit Log
    await tx.auditLog.create({
      data: {
        recordId: person.id,
        entityName: 'Person',
        actionName: id ? 'UPDATE' : 'CREATE',
        actor: 'Admin', // In real app, get from session
        snapshot: JSON.stringify(person)
      }
    });

    return person;
  });

  revalidatePath('/admin/persons');
  revalidatePath(`/admin/persons/${result.id}`);
  return result;
}

export async function deletePerson(id: number) {
  await prisma.$transaction(async (tx) => {
    const person = await tx.person.delete({
      where: { id }
    });

    await tx.auditLog.create({
      data: {
        recordId: id,
        entityName: 'Person',
        actionName: 'DELETE',
        actor: 'Admin',
        snapshot: JSON.stringify(person)
      }
    });
  });

  revalidatePath('/admin/persons');
}

export async function linkPersonToUser(personId: number, userId: number | null) {
  await prisma.person.update({
    where: { id: personId },
    data: { userId }
  });
  revalidatePath(`/admin/persons/${personId}`);
  revalidatePath('/admin/persons');
}

export async function fetchUsersWithoutPersonLink() {
  return await prisma.user.findMany({
    where: {
      person: null
    },
    orderBy: { email: 'asc' }
  });
}
