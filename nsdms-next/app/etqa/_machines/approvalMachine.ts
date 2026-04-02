import { setup } from 'xstate';

// Using XState v5 `setup()` factory
export const approvalMachine = setup({
  types: {
    context: {} as {
      approvalId: number;
      providerId: number;
      reason?: string;
    },
    events: {} as
      | { type: 'SUBMIT' }
      | { type: 'REJECT'; reason: string }
      | { type: 'APPROVE' }
      | { type: 'RETURN_TO_DRAFT' },
  },
}).createMachine({
  id: 'workplaceApproval',
  initial: 'DRAFT',
  context: {
      approvalId: 0,
      providerId: 0,
  },
  states: {
    DRAFT: {
      on: {
        SUBMIT: {
          target: 'UNDER_REVIEW',
        },
      },
    },
    UNDER_REVIEW: {
      on: {
        APPROVE: {
          target: 'APPROVED',
        },
        REJECT: {
          target: 'REJECTED',
        },
        RETURN_TO_DRAFT: {
          target: 'DRAFT',
        },
      },
    },
    APPROVED: {
      type: 'final',
    },
    REJECTED: {
      on: {
        RETURN_TO_DRAFT: {
          target: 'DRAFT',
        },
      },
    },
  },
});
