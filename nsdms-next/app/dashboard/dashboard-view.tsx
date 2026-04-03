"use client";

import { Card, CardContent, CardHeader, CardTitle, CardDescription } from "@/components/ui/card";
import { Badge } from "@/components/ui/badge";
import { 
  Building2, 
  Users, 
  GraduationCap, 
  FileCheck, 
  BadgeCheck, 
  Wallet,
  History,
  ArrowUpRight
} from "lucide-react";
import type { DashboardStats } from "./_actions/dashboard";
import { Table, TableBody, TableCell, TableHead, TableHeader, TableRow } from "@/components/ui/table";
import { formatDistanceToNow } from "date-fns";

interface DashboardViewProps {
  stats: DashboardStats;
  auditLogs: any[];
}

export default function DashboardView({ stats, auditLogs }: DashboardViewProps) {
  const statItems = [
    { 
      title: "Organisations", 
      value: stats.totalOrganisations, 
      icon: Building2, 
      description: "Registered Companies",
      color: "text-blue-600 dark:text-blue-400"
    },
    { 
      title: "Providers", 
      value: stats.totalProviders, 
      icon: BadgeCheck, 
      description: "Accredited Training Providers",
      color: "text-amber-600 dark:text-amber-400"
    },
    { 
      title: "Learners", 
      value: stats.totalLearners, 
      icon: GraduationCap, 
      description: "Active Enrollments",
      color: "text-green-600 dark:text-green-400"
    },
    { 
      title: "Pending WSPs", 
      value: stats.pendingWSPs, 
      icon: FileCheck, 
      description: "Awaiting merSETA Approval",
      color: "text-rose-600 dark:text-rose-400"
    }
  ];

  return (
    <div className="space-y-8 animate-in fade-in duration-700">
      {/* Overview Stats Grid */}
      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-4">
        {statItems.map((item, index) => (
          <Card key={index} className="hover:shadow-md transition-all duration-300 border-none bg-secondary/50 dark:bg-secondary/20 backdrop-blur-sm">
            <CardHeader className="flex flex-row items-center justify-between space-y-0 pb-2">
              <CardTitle className="text-sm font-medium">{item.title}</CardTitle>
              <item.icon className={`h-4 w-4 ${item.color}`} />
            </CardHeader>
            <CardContent>
              <div className="text-2xl font-bold tracking-tight">{item.value.toLocaleString()}</div>
              <p className="text-xs text-muted-foreground mt-1">{item.description}</p>
            </CardContent>
          </Card>
        ))}
      </div>

      <div className="grid gap-4 md:grid-cols-2 lg:grid-cols-7">
        {/* Additional Stats / Financials */}
        <Card className="col-span-4 border-none bg-secondary/30">
          <CardHeader>
            <CardTitle>Core System Overview</CardTitle>
            <CardDescription>Consolidated status across all NSDMS modules.</CardDescription>
          </CardHeader>
          <CardContent className="grid gap-6">
            <div className="flex items-center space-x-4 p-4 rounded-lg bg-background/50 border border-border/50">
               <div className="p-3 rounded-full bg-blue-100 dark:bg-blue-900/30 text-blue-600">
                  <Wallet className="h-6 w-6" />
               </div>
               <div className="flex-1 space-y-1">
                 <p className="text-sm font-medium leading-none">Active Grant Applications</p>
                 <p className="text-2xl font-bold text-muted-foreground">{stats.activeGrants}</p>
               </div>
               <Badge className="bg-blue-100 text-blue-700 border-blue-200">Processing</Badge>
            </div>
            
            <div className="flex items-center space-x-4 p-4 rounded-lg bg-background/50 border border-border/50">
               <div className="p-3 rounded-full bg-cyan-100 dark:bg-cyan-900/30 text-cyan-600">
                  <BadgeCheck className="h-6 w-6" />
               </div>
               <div className="flex-1 space-y-1">
                 <p className="text-sm font-medium leading-none">Pending Accreditations</p>
                 <p className="text-2xl font-bold text-muted-foreground">{stats.pendingAccreditations}</p>
               </div>
               <Badge className="bg-cyan-100 text-cyan-700 border-cyan-200">QA Review</Badge>
            </div>
          </CardContent>
        </Card>

        {/* Recent System Activity (Audit Logs) */}
        <Card className="col-span-3 border-none bg-secondary/30">
          <CardHeader className="flex flex-row items-center justify-between">
            <div>
              <CardTitle>Recent Activity</CardTitle>
              <CardDescription>Live audit trail of the latest actions.</CardDescription>
            </div>
            <History className="h-4 w-4 text-muted-foreground" />
          </CardHeader>
          <CardContent>
            <div className="space-y-8">
              {auditLogs.length > 0 ? (
                auditLogs.map((log, index) => (
                  <div key={index} className="flex items-center">
                    <div className="space-y-1">
                      <p className="text-sm font-medium leading-none flex items-center gap-2">
                         {log.actionName} - {log.entityName}
                         <span className="text-xs text-muted-foreground font-normal">#{log.recordId}</span>
                      </p>
                      <p className="text-xs text-muted-foreground">
                        by {log.actor} • {formatDistanceToNow(new Date(log.createdAt))} ago
                      </p>
                    </div>
                    <div className="ml-auto flex items-center">
                       <ArrowUpRight className="h-4 w-4 text-muted-foreground opacity-0 hover:opacity-100 transition-opacity cursor-pointer" />
                    </div>
                  </div>
                ))
              ) : (
                 <p className="text-sm text-muted-foreground text-center py-8">No recent activity found.</p>
              )}
            </div>
          </CardContent>
        </Card>
      </div>
    </div>
  );
}
