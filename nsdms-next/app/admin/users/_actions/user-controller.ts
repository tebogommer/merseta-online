"use server";

import { PrismaClient } from "@prisma/client";
import { revalidatePath } from "next/cache";

const prisma = new PrismaClient();

export interface UserPayload {
  id?: number;
  name?: string | null;
  email?: string | null;
  role: string | null;
  permissions?: string | null;
  active?: boolean;
}

export async function getUsers() {
  return await prisma.user.findMany({
    include: {
      person: true
    },
    orderBy: { email: 'asc' }
  });
}

export async function getUserById(id: number) {
  return await prisma.user.findUnique({
    where: { id },
    include: {
      person: true
    }
  });
}

export async function saveUser(payload: UserPayload) {
  const { id, ...data } = payload;
  
  const result = await prisma.$transaction(async (tx) => {
    let user;
    if (id) {
       user = await tx.user.update({
        where: { id },
        data: data as any
      });
    } else {
       user = await tx.user.create({
        data: data as any
      });
    }

    // Audit Log
    await tx.auditLog.create({
      data: {
        recordId: user.id,
        entityName: 'User',
        actionName: id ? 'UPDATE' : 'CREATE',
        actor: 'Admin',
        snapshot: JSON.stringify(user)
      }
    });

    return user;
  });

  revalidatePath('/admin/users');
  revalidatePath(`/admin/users/${result.id}`);
  return result;
}

export async function toggleUserStatus(id: number, active: boolean) {
  const result = await prisma.user.update({
    where: { id },
    data: { active }
  });
  
  await prisma.auditLog.create({
    data: {
        recordId: id,
        entityName: 'User',
        actionName: 'STATUS_CHANGE',
        actor: 'Admin',
        snapshot: JSON.stringify(result)
    }
  });

  revalidatePath('/admin/users');
  revalidatePath(`/admin/users/${id}`);
}

export async function deleteUser(id: number) {
  await prisma.$transaction(async (tx) => {
    const user = await tx.user.delete({
      where: { id }
    });

    await tx.auditLog.create({
      data: {
        recordId: id,
        entityName: 'User',
        actionName: 'DELETE',
        actor: 'Admin',
        snapshot: JSON.stringify(user)
      }
    });
  });

  revalidatePath('/admin/users');
}

export async function fetchPersonsWithoutUserLink() {
  return await prisma.person.findMany({
    where: {
      userId: null
    },
    orderBy: { lastName: 'asc' }
  });
}

export async function linkUserToPerson(userId: number, personId: number | null) {
   await prisma.person.updateMany({
      where: { userId: userId },
      data: { userId: null }
   });

   if (personId) {
      await prisma.person.update({
         where: { id: personId },
         data: { userId: userId }
      });
   }

   revalidatePath(`/admin/users/${userId}`);
   revalidatePath('/admin/users');
}
