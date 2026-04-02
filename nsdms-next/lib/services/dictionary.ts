import {
  ApprovalStatus,
  ApprovalStatusLabels,
  CompanyStatus,
  CompanyStatusLabels,
  AccreditationApplicationType,
  AllocationChangeType,
} from "@/types/enums";
import * as lookupService from "@/lib/services/lookup";

/**
 * System Dictionary Service
 * Combines Static Enums (TypeScript) and Dynamic Lookups (Prisma/DB)
 * into a unified structure for UI consume.
 */

export type DictionaryEntry = {
  id: string | number;
  name: string;
  code?: string;
  description?: string;
};

export type SystemDictionary = {
  // Static Enums
  approvalStatuses: DictionaryEntry[];
  companyStatuses: DictionaryEntry[];
  accreditationApplicationTypes: DictionaryEntry[];
  allocationChangeTypes: DictionaryEntry[];

  // Dynamic Lookups (Commonly used)
  genders: DictionaryEntry[];
  provinces: DictionaryEntry[];
  equities: DictionaryEntry[];
  nationalities: DictionaryEntry[];
  titles: DictionaryEntry[];
  setas: DictionaryEntry[];
};

/**
 * Fetches the complete system dictionary.
 * Uses cached values from the lookup service.
 */
export async function getSystemDictionary(): Promise<SystemDictionary> {
  const common = await lookupService.getCommonLookups();
  const setas = await lookupService.getSetaTypes();

  // Map static enums to common DictionaryEntry format
  const approvalStatuses: DictionaryEntry[] = Object.entries(ApprovalStatus).map(([key, value]) => ({
    id: key,
    name: value,
    code: key,
  }));

  const companyStatuses: DictionaryEntry[] = Object.entries(CompanyStatus).map(([key, value]) => ({
    id: key,
    name: value,
    code: key,
  }));

  const accreditationApplicationTypes: DictionaryEntry[] = Object.entries(AccreditationApplicationType).map(([key, value]) => ({
    id: key,
    name: value,
    code: key,
  }));

  const allocationChangeTypes: DictionaryEntry[] = Object.entries(AllocationChangeType).map(([key, value]) => ({
    id: key,
    name: value,
    code: key,
  }));

  return {
    approvalStatuses,
    companyStatuses,
    accreditationApplicationTypes,
    allocationChangeTypes,
    genders: common.genders.map((g: any) => ({ id: g.id, name: g.name, code: g.code })),
    provinces: common.provinces.map((p: any) => ({ id: p.id, name: p.name, code: p.code })),
    equities: common.equities.map((e: any) => ({ id: e.id, name: e.name, code: e.code })),
    nationalities: common.nationalities.map((n: any) => ({ id: n.id, name: n.name, code: n.code })),
    titles: common.titles.map((t: any) => ({ id: t.id, name: t.name, code: t.code })),
    setas: setas.map((s: any) => ({ id: s.id, name: s.name, code: s.code })),
  };
}

/**
 * Server Action to get dictionary data for Client Components
 */
export async function getDictionaryAction() {
  return await getSystemDictionary();
}
