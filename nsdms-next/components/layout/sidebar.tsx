"use client";

import Link from "next/link";
import { usePathname } from "next/navigation";
import { cn } from "@/lib/utils";
import { 
  LayoutDashboard, 
  Building2, 
  GraduationCap, 
  Users, 
  FolderTree, 
  Settings,
  Library,
  Briefcase,
  Shield,
  ClipboardList,
  Award
} from "lucide-react";

const navigation = [
  { name: "Dashboard", href: "/", icon: LayoutDashboard },

  { name: "Workplaces", href: "/workplaces", icon: Briefcase },
  { name: "Organisations", href: "/organisations", icon: Building2 },
  { name: "Learner Records", href: "/learners", icon: GraduationCap },
  { name: "WSP Submissions", href: "/workplace-skills-plans", icon: ClipboardList },
  { name: "ETQA Approvals", href: "/etqa/approvals", icon: Award },
  { name: "Users", href: "/users", icon: Users },
  { name: "System Settings", href: "/settings", icon: Settings },
  { name: "System Audit Matrix", href: "/admin/audit-logs", icon: Shield },
];

const lookups = [
  { name: "Categories", href: "/admin/lookups/category-types", icon: FolderTree },
  { name: "Interventions", href: "/admin/lookups/intervention-types", icon: Library },
  { name: "Qualifications", href: "/admin/lookups/qualification-types", icon: FolderTree },
];

export function Sidebar() {
  const pathname = usePathname();

  return (
    <div className="flex bg-slate-950 text-slate-300 w-64 flex-col h-full border-r border-slate-900 shadow-xl overflow-y-auto">
      {/* Branding Header */}
      <div className="flex h-16 shrink-0 items-center px-6 bg-slate-900 border-b border-slate-800">
        <Link href="/" className="flex items-center gap-2">
          <div className="bg-primary/20 p-1.5 rounded border border-primary/50">
            <Building2 className="h-5 w-5 text-primary" />
          </div>
          <span className="font-bold text-lg text-white tracking-tight">mer<span className="text-primary">SETA</span></span>
        </Link>
      </div>

      {/* Navigation */}
      <nav className="flex flex-1 flex-col p-4 space-y-1">
        <div className="text-xs font-semibold uppercase tracking-wider text-slate-500 mb-2 px-2">Menu</div>
        {navigation.map((item) => {
          const isActive = pathname.startsWith(item.href) && (item.href !== "/" || pathname === "/");
          return (
            <Link
              key={item.name}
              href={item.href}
              className={cn(
                "group flex items-center gap-x-3 rounded-md px-3 py-2 text-sm font-medium transition-colors",
                isActive 
                  ? "bg-slate-900 text-primary" 
                  : "hover:bg-slate-900 hover:text-white"
              )}
            >
              <item.icon
                className={cn(
                  "h-5 w-5 shrink-0",
                  isActive ? "text-primary" : "text-slate-500 group-hover:text-slate-300"
                )}
                aria-hidden="true"
              />
              {item.name}
            </Link>
          );
        })}

        <div className="text-xs font-semibold uppercase tracking-wider text-slate-500 mb-2 mt-6 px-2">Data Lookups</div>
        {lookups.map((item) => {
          const isActive = pathname.startsWith(item.href);
          return (
            <Link
              key={item.name}
              href={item.href}
              className={cn(
                "group flex items-center gap-x-3 rounded-md px-3 py-2 text-sm font-medium transition-colors",
                isActive 
                  ? "bg-slate-900 text-primary" 
                  : "hover:bg-slate-900 hover:text-white"
              )}
            >
              <item.icon
                className={cn(
                  "h-5 w-5 shrink-0",
                  isActive ? "text-primary" : "text-slate-500 group-hover:text-slate-300"
                )}
                aria-hidden="true"
              />
              {item.name}
            </Link>
          );
        })}
      </nav>
      
      {/* Footer / Info Segment */}
      <div className="mt-auto p-4 border-t border-slate-900 bg-slate-950/50">
         <div className="text-xs text-slate-500 px-2 py-1 flex items-center justify-between">
            <span>NSDMS v2</span>
            <span className="bg-primary/10 text-primary px-1.5 py-0.5 rounded text-[10px] font-mono border border-primary/20">MVP</span>
         </div>
      </div>
    </div>
  );
}
