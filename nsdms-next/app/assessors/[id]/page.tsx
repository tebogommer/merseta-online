import { notFound } from "next/navigation";
import { PrismaClient } from "@prisma/client";
import { Suspense } from "react";
import AssessorDetails from "./assessor-details";

const prisma = new PrismaClient();

async function getAssessorRecord(id: number) {
  return await prisma.assessorModeratorApplication.findUnique({
    where: { id },
    include: {
      user: true,
      trainingProvider: {
        include: {
          organisation: true
        }
      },
      extensionsOfScope: true,
    }
  });
}

export default async function AssessorDetailsShell({ params }: { params: { id: string } }) {
  const recordId = parseInt(params.id, 10);
  
  if (isNaN(recordId)) {
    return notFound();
  }

  const record = await getAssessorRecord(recordId);

  if (!record) {
    return notFound();
  }

  return (
    <div className="p-6 max-w-7xl mx-auto space-y-6">
      <Suspense fallback={<div>Loading Application Record...</div>}>
        <AssessorDetails record={record} />
      </Suspense>
    </div>
  );
}
