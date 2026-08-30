# PLAN: Outstanding Work (NSDMS Migration)

## Phase 0: Socratic Gate (Pending User Input)
Before fully finalizing sprint assignments and dependencies, the following strategic questions must be answered:
1. **Reporting & Integration (SARS/NLRD/SETMIS):** Do you want to build the data ingestion/extraction logic using server actions, or should we offload these heavy batch processes to a Next.js API route / background Cron pattern?
2. **Document Management:** Since the legacy system relied heavily on JasperReports, do you want to start building the React-PDF document generators right now, or focus strictly on UI data-capture first?
3. **State Machines (XState):** We are currently using strings (DRAFT, APPROVED) for statuses. Should the very next step be integrating the explicit `xstate` machines for strictly guarded transitions, or defer that until all views are done?
4. **E2E Testing:** Playwright is installed but empty. Should tests be written in parallel with the remaining modules, or handled as a final stabilization sprint?

## Phase 1: Task Breakdown (Draft)

### Module A: Document Management & React-PDF (Frontend / Backend)
- **Goal:** Replace JasperReports capability.
- **Agent:** `frontend-specialist`, `backend-specialist`
- **Tasks:**
  - Scaffold React-PDF rendering utilities.
  - Create Document upload wizard (for WSP Signoffs).
  - Bind documents to the polymorphic `Doc` entity pattern.

### Module B: Reporting & External Integrations (Backend)
- **Goal:** Map the Setmis, Saqa, NLRD flat-file logic and SARS Levy ingestions.
- **Agent:** `backend-specialist`, `database-architect`
- **Tasks:**
  - Create `SarsLevy` and `ReportingExtract` models in Prisma.
  - Scaffold CSV / XML extraction generation logic.
  - Build the Admin Reporting Dashboard.

### Module C: E2E Playwright Implementation (QA)
- **Goal:** Shore up the missing verification layer.
- **Agent:** `test-engineer`
- **Tasks:**
  - Write User Journey: Provider Accreditation Flow.
  - Write User Journey: Learner Enrollment.
  - Write User Journey: WSP Submission.

### Module D: True XState Integration (Controller)
- **Goal:** Move from "DRAFT/APPROVED" string literals to hard XState `.transition()` blocks.
- **Agent:** `backend-specialist`
- **Tasks:**
  - Define `<WSPMachine>` and `<ProviderMachine>`.
  - Wire actions in `_actions/workflow.ts` to `machine.transition`.

---

## Next Steps
- Answer the Socratic Gate questions above.
- Run `/create` to start implementation on the prioritized module.
