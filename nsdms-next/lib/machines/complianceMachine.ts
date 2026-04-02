import { setup } from 'xstate';

/**
 * Universal Compliance State Machine
 * Centralizes the legal state transitions for all MerSETA Application layers 
 * (Grants, Assessors, Providers, etc.)
 */
export const complianceMachine = setup({
  types: {
    events: {} as
      | { type: 'SUBMIT' }
      | { type: 'COMMENCE_REVIEW' }
      | { type: 'APPROVE' }
      | { type: 'REJECT' }
      | { type: 'APPEAL' }
      | { type: 'REVERT_TO_DRAFT' },
  },
}).createMachine({
  id: 'complianceFlow',
  initial: 'DRAFT',
  states: {
    DRAFT: {
      on: {
        SUBMIT: 'SUBMITTED',
      },
    },
    SUBMITTED: {
      on: {
        COMMENCE_REVIEW: 'IN_REVIEW',
        REVERT_TO_DRAFT: 'DRAFT',
      },
    },
    IN_REVIEW: {
      on: {
        APPROVE: 'APPROVED',
        REJECT: 'REJECTED',
        REVERT_TO_DRAFT: 'DRAFT'
      },
    },
    APPROVED: {
      type: 'final',
    },
    REJECTED: {
      on: {
        APPEAL: 'APPEALED',
      },
    },
    APPEALED: {
      on: {
        COMMENCE_REVIEW: 'IN_REVIEW',
      },
    },
  },
});

/**
 * Utility to deterministically calculate the next theoretical status from a given event.
 * Throws an error strictly if the event is globally illegal from the specific initial state.
 */
export function getNextStatus(currentStatus: string, event: 'SUBMIT' | 'COMMENCE_REVIEW' | 'APPROVE' | 'REJECT' | 'APPEAL' | 'REVERT_TO_DRAFT'): string {
  // Translate current primitive string to the theoretical XState node
  // @ts-expect-error xstate transition TS signature expects actorContext but works dynamically without it internally.
  const nextTarget = complianceMachine.transition(currentStatus, { type: event });

  // In XState v5, transition returns the state object. We check if the state value changed.
  if (nextTarget.value === currentStatus) {
    throw new Error(`ILLEGAL STATE TRANSITION: Cannot apply event '${event}' while strictly in state '${currentStatus}'.`);
  }

  return typeof nextTarget.value === "string" ? nextTarget.value : Object.keys(nextTarget.value)[0];
}
