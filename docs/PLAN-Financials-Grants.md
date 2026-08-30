# Orchestration Plan: Financials, Grants & Workflow Engine (Phase 3)

## Objective
The objective of Phase 3 is to document the final and most mathematically complex domains of the MerSETA NSDMS into Domain-Driven specifications ready for the .NET MVC team. These domains handle SARS Levy data ingestion, Mandatory/Discretionary Grant Allocation (Funding), and the core underlying Task Routing Engine.

## Scope of Work (New Specifications)

### 1. `Spec-03-Discretionary-Grants.md` (Funding Allocations & Contracts)
**Context:** Discretionary Grants (DG) dictate how merSETA funds are allocated to employers to execute Project Implementation Plans (PIPs).
**Key Target Files:** 
- `DgAllocationService.java`
- `DgAllocationParent.java`
- `ActiveContractsService.java`

### 2. `Spec-07-Mandatory-Grants.md` (WSP Rebates)
**Context:** The automatic payout mechanism evaluating WSP approvals and SARS submissions to release the 20% Mandatory Grant levy rebate.
**Key Target Files:**
- `MandatoryGrantService.java`

### 3. `Spec-09-SARS-Levy-Integration.md` (Financial Data Ingestion)
**Context:** The highly secured, scheduled batch processing pipeline that ingests SARS flat files to determine an employer's total levy contribution. If an employer has not paid their SARS levies, they are locked out of WSPs and Grants.
**Key Target Files:**
- `SarsLevyDetailsService.java`
- `SarsFilesService.java`
- `PaymentRequestService.java`

### 4. `Spec-10-Workflow-Task-Engine.md` (The "Work Queue")
**Context:** The legacy NSDMS does not use an isolated state-machine per entity. Instead, it relies on a central `Tasks` table to route almost all complex approvals (WSP, Assessors, Providers) to specific Regional/National queues. This routing logic must be reverse-engineered to define the .NET Work-Queue architecture.
**Key Target Files:**
- `TasksService.java`
- `Tasks.java`
- `ReviewCommitteeMeetingService.java`

---

## Agent Task Breakdown

1. **`backend-specialist`**: Analyze the legacy `TasksService.java` exception traces to document exactly how "Region -> CLO -> Review Committee" assignments are generated.
2. **`database-architect`**: Reverse engineer the `DgAllocationParent` and `ActiveContracts` data structures to ensure the .NET target uses correct decimal/currency types (`money` or `decimal(18,2)` vs flat floats), since this heavily impacts auditing.
3. **`project-planner`**: Coordinate the generation of the Markdown specifications, maintaining the consistent 8-point structure (Overview, Schema, MVC/Routing, UI/UX, State Machine, Business Rules, Documents, Required Data Fields).

---

*Phase 1 Plan generation is complete.*
