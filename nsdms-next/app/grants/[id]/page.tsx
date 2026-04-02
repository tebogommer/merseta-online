import { notFound } from "next/navigation";
import { PrismaClient } from "@prisma/client";
import { Suspense } from "react";
import GrantDetails from "./grant-details";

const prisma = new PrismaClient();

async function getGrantRecord(id: number) {
  return await prisma.grantApplication.findUnique({
    where: { id },
    include: {
      organisation: true,
      wsp: true,
      verifications: true,
      payments: true,
    }
  });
}

export default async function GrantDetailsShell({ params }: { params: { id: string } }) {
  const recordId = parseInt(params.id, 10);
  
  if (isNaN(recordId)) {
    return notFound();
  }

  const record = await getGrantRecord(recordId);

  if (!record) {
    return notFound();
  }

  return (
    <div className="p-6 max-w-7xl mx-auto space-y-6">
      <Suspense fallback={<div>Loading Grant Record...</div>}>
        <GrantDetails record={record} />
      </Suspense>
    </div>
  );
}
