import { getUserById, fetchPersonsWithoutUserLink } from "../_actions/user-controller";
import { UserForm } from "../_components/user-form";

export default async function UserDetailPage({
  params
}: {
  params: Promise<{ id: string }>
}) {
  const { id } = await params;
  const isNew = id === "new";

  const user = isNew ? null : await getUserById(parseInt(id));
  const availablePersons = await fetchPersonsWithoutUserLink();

  return (
    <main className="p-8">
      <div className="max-w-7xl mx-auto">
        <UserForm 
          initialData={user as any} 
          availablePersons={availablePersons as any}
        />
      </div>
    </main>
  );
}
