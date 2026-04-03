import { auth, signIn, signOut } from "@/auth";
import { ThemeToggle } from "@/components/theme-toggle";

export async function Topbar() {
  const session = await auth();

  return (
    <header className="bg-card text-card-foreground dark:bg-slate-950 border-b border-border dark:border-slate-800 h-16 flex items-center justify-between px-6 shadow-sm shrink-0 z-10 relative">
      <div className="flex items-center gap-4">
        {/* Placeholder for Breadcrumbs or Search */}
        <div className="text-sm text-muted-foreground hidden md:block">
           <span className="bg-blue-50 dark:bg-slate-900 text-blue-700 dark:text-blue-400 px-2 py-0.5 rounded text-xs uppercase tracking-wider font-semibold border border-blue-100 dark:border-slate-700">Active Workspace</span>
        </div>
      </div>

      <div className="flex items-center gap-6">
        <ThemeToggle />
        {session?.user ? (
           <div className="flex items-center gap-4">
              <div className="flex flex-col text-right">
                <span className="text-foreground dark:text-gray-100 font-semibold text-sm">{session.user.name || session.user.email}</span>
                <span className="text-xs text-primary font-mono tracking-wider font-bold">{session.user.role}</span>
              </div>
              <form action={async () => {
                "use server";
                await signOut();
              }}>
                <button type="submit" className="bg-slate-100 hover:bg-red-50 text-muted-foreground hover:text-red-700 hover:border-red-200 px-3 py-1.5 rounded transition-all border border-border text-sm font-semibold shadow-sm">
                  Sign out
                </button>
              </form>
           </div>
        ) : (
          <form action={async () => {
            "use server";
            await signIn();
          }}>
             <button type="submit" className="bg-merseta hover:bg-merseta-dark font-semibold px-4 py-1.5 rounded transition-all text-white shadow text-sm">
                Sign In 
             </button>
          </form>
        )}
      </div>
    </header>
  );
}
