import { createMachine, assign } from "xstate";
import { CompanyStatus } from "@/types/enums";

/**
 * Organisation Lifecycle Machine
 * Strictly governs the transition of an Organisation through its lifecycle.
 * Logic ported from haj.com.entity.enums.CompanyStatusEnum
 */

export const organisationMachine = createMachine({
  id: "organisation",
  initial: CompanyStatus.PENDING,
  states: {
    [CompanyStatus.PENDING]: {
      on: {
        APPROVE: { target: CompanyStatus.APPROVED },
        REJECT: { target: CompanyStatus.REJECTED },
      },
    },
    [CompanyStatus.APPROVED]: {
      on: {
        ACTIVATE: { target: CompanyStatus.ACTIVE },
        DEREGISTER: { target: CompanyStatus.DE_REGISTERED },
        REJECT: { target: CompanyStatus.REJECTED },
      },
    },
    [CompanyStatus.ACTIVE]: {
      on: {
        DEACTIVATE: { target: CompanyStatus.IN_ACTIVE },
        DEREGISTER: { target: CompanyStatus.DE_REGISTERED },
        PENDING_CHANGE: { target: CompanyStatus.PENDING_CHANGE_APPROVAL },
      },
    },
    [CompanyStatus.IN_ACTIVE]: {
      on: {
        REACTIVATE: { target: CompanyStatus.ACTIVE },
      },
    },
    [CompanyStatus.PENDING_CHANGE_APPROVAL]: {
      on: {
        APPROVE_CHANGE: { target: CompanyStatus.ACTIVE },
        REJECT_CHANGE: { target: CompanyStatus.ACTIVE }, // Returns to active regardless of change decision
      },
    },
    [CompanyStatus.REJECTED]: {
      on: {
        REAPPEAR: { target: CompanyStatus.PENDING }, // Allow reapplying
      },
    },
    [CompanyStatus.DE_REGISTERED]: {
      type: "final", // Terminal state for this instance
    },
  },
});
