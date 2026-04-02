# Plan: Discovering Core Business Flows & Complexity

## 📋 Objective
To list the major business processes and workflows present in the NSDMS codebase (e.g., Workplace Approvals, Learner Management) and grade their architectural complexity.

## 🛰️ Phase 1: Discovery & Complexity Analysis (The "Wait" Phase)
1. **[explorer-agent] (Metrics Gathering)**
    - Leverage the previously captured `list_dir` output of `haj\com\service` and `haj\com\entity`.
    - Identify the top largest services by byte size (e.g., files over 50KB generally indicate massive, complex flows).
2. **[backend-specialist] (Flow Identification)**
    - Group the massive files into logical business flows (e.g., `WspService`, `WspDGService` = Grant Management Flow).
    - Determine complexity based on heuristic metrics (High = >100KB of business logic, Medium = 30-100KB, Low = <30KB).

## 🏗️ Phase 2: Synthesis (The "Write" Phase)
1. **[orchestrator / documentation-writer]**
    - Compile a final markdown artifact (`Core-Flows-Complexity.md`).
    - List the name of the flow, the core classes driving it, and a High/Medium/Low complexity rating with justification.

## 🏁 Final Deliverable
- `Core-Flows-Complexity.md` detailing the primary workflows mapping to the SETA domain.

---

## 🚦 Next Actions
- [ ] Approve this plan.
- [ ] Initiate mapping.
