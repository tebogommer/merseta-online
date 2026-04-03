import { getPersonById, fetchUsersWithoutPersonLink } from "../_actions/person-controller";
import { PersonForm } from "../_components/person-form";
import { PrismaClient } from "@prisma/client";

const prisma = new PrismaClient();

export default async function PersonDetailPage({
  params
}: {
  params: Promise<{ id: string }>
}) {
  const { id } = await params;
  const isNew = id === "new";

  const person = isNew ? null : await getPersonById(parseInt(id));
  const availableUsers = await fetchUsersWithoutPersonLink();

  // Fetch Lookups for the form
  const genders = await prisma.genderType.findMany({ where: { active: true } });
  const equities = await prisma.equityType.findMany({ where: { active: true } });
  const nationalities = await prisma.nationalityType.findMany({ where: { active: true } });

  const lookups = { genders, equities, nationalities };

  return (
    <main className="p-8">
      <div className="max-w-7xl mx-auto">
        <PersonForm 
          initialData={person as any} 
          lookups={lookups as any} 
          availableUsers={availableUsers as any}
        />
      </div>
    </main>
  );
}
