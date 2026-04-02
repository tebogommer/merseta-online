import * as React from "react";
import { Badge } from "@/components/ui/badge";
import { ApprovalStatus, CompanyStatus } from "@/types/enums";
import { cn } from "@/lib/utils";

type EnumValue = ApprovalStatus | CompanyStatus | string;

interface StatusBadgeProps {
  value: EnumValue;
  className?: string;
}

/**
 * StatusBadge Component
 * Automatically maps static enum values to themed shadcn/ui Badges.
 * Provides visual parity with legacy MerSETA statuses.
 */
export function StatusBadge({ value, className }: StatusBadgeProps) {
  // Logic for ApprovalStatus
  const getApprovalStyles = (status: string) => {
    switch (status) {
      case ApprovalStatus.APPROVED:
      case ApprovalStatus.COMPLETED:
      case ApprovalStatus.ACCEPTED_MOA:
        return "bg-emerald-500/15 text-emerald-700 dark:text-emerald-400 border-emerald-500/20";
      case ApprovalStatus.REJECTED:
      case ApprovalStatus.WITHDRAWN:
      case ApprovalStatus.EXPIRED:
      case ApprovalStatus.DEACTIVATED:
        return "bg-destructive/15 text-destructive border-destructive/20";
      case ApprovalStatus.PENDING_APPROVAL:
      case ApprovalStatus.WAITING_FOR_MANAGER:
      case ApprovalStatus.PENDING_FINAL_APPROVAL:
        return "bg-amber-500/15 text-amber-700 dark:text-amber-400 border-amber-500/20";
      default:
        return "bg-secondary text-secondary-foreground";
    }
  };

  // Logic for CompanyStatus
  const getCompanyStyles = (status: string) => {
    switch (status) {
      case CompanyStatus.ACTIVE:
      case CompanyStatus.APPROVED:
        return "bg-emerald-500/15 text-emerald-700 dark:text-emerald-400 border-emerald-500/20";
      case CompanyStatus.REJECTED:
      case CompanyStatus.IN_ACTIVE:
      case CompanyStatus.DE_REGISTERED:
        return "bg-destructive/15 text-destructive border-destructive/20";
      case CompanyStatus.PENDING:
      case CompanyStatus.PENDING_CHANGE_APPROVAL:
        return "bg-amber-500/15 text-amber-700 dark:text-amber-400 border-amber-500/20";
      default:
        return "bg-secondary text-secondary-foreground";
    }
  };

  const style = getApprovalStyles(value) || getCompanyStyles(value);

  return (
    <Badge variant="outline" className={cn("font-medium transition-colors", style, className)}>
      {value}
    </Badge>
  );
}
