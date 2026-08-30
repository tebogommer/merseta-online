# NSDMS Modernization: Implementation Roadmap (Option C)

## 📌 Executive Summary
This roadmap dictates the execution sequence for migrating the legacy Java monolithic system into the modern .NET 8 / Code On Time (COT) framework. It relies on the **"Core Shell + Iterative Domains" (Option C)** approach to eliminate "Big Bang" deployment risks. By establishing the Authentication Shell and Global Workflow Engine in Phase 1, developers can confidently generate the complex multipart forms in subsequent phases knowing the underlying entity mappings and workflow actions are actively running in the background.

---

## 🏗️ Phase 1: The Core Application Shell
**Objective:** Establish secure access, layout navigation, and the global Maker-Checker workflow task matrix. At the end of this phase, the application compiles securely, users can log in, interact with their profile, and view their (currently empty) Universal Inbox.

*   **1.1 Access Control (`Spec-01`)**
    *   Map legacy demographic `Users` to `aspnet_Users` for authentication state.
    *   Implement Server-side RSA ID validation via COT Business Rules.
    *   Compile the core Navigational Sidebar.
*   **1.2 The Engine Foundation (`Spec-10`)**
    *   Execute DDL to build `WorkflowDefinition`, `WorkflowState`, `Group`, and `Permission` tables.
    *   Compile the SQL stored procedure `usp_AdvanceWorkflow` with dynamic Option B Entity Status Push logic.
    *   Generate the Universal Task Inbox COT grid bound to the native `HandleOpenTask` C# lookup action.

---

## 📚 Phase 2: Static Baseline Registries
**Objective:** Lock in the complex metadata and static organizational entities. All active domains (like Learners or WSP) require these relational anchors to exist before they can be configured.

*   **2.1 Dynamic Lookups & Complex Registries (`Spec-17`)**
    *   Execute dynamic Lookup Management framework (replacing hardcoded Java modules).
    *   Seed Funding Windows, OfoCodes, OFO Occupations, and SETA chamber mappings.
*   **2.2 Organizational Truth (`Spec-02`)**
    *   Build out `Company` and `SdfCompany` controllers.
    *   Enforce SARS Levy Number constraints and SIC Code mapping.

---

## 🚀 Phase 3: Iterative Domain Injections (The Heavy Lifting)
**Objective:** Now that the Shell and Registries exist, the development team operates in short agile sprints to "drop in" massive business modules one-by-one. Each module instantly connects to Phase 1's Workflow Engine and Phase 2's Companies.

*   **Sprint 3.1 - Sector Skill Demand (`Spec-06` & `Spec-07`)**
    *   Build out Workplace Skills Plan (WSP) complex multipage forms.
    *   Generate Mandatory Grant payment requisition logic.
*   **Sprint 3.2 - ETQA & Accreditation (`Spec-04`, `Spec-05`, `Spec-11`)**
    *   Configure Training Provider Accreditation grids.
    *   Configure Assessor/Moderator Registration controllers.
    *   Build Workplace Approval validation matrices.
*   **Sprint 3.3 - The Learner Lifecycle (`Spec-08`, `Spec-12`)**
    *   Build the Tripartite Registration Wizard (Learner -> Employer -> Provider).
    *   Implement Row-version locking for mentor ratios and funding budget allocation caps.
    *   Build Trade Test routing, artisan tracking grids, and ARPL routing.
*   **Sprint 3.4 - Discretionary Funding (`Spec-03`, `Spec-14`)**
    *   Build Discretionary Grant Project Implementation plans.
    *   Execute the `Dynamics GP ERP Integration` to track commitments vs actual tranche payments.

---

## 🛡️ Phase 4: Compliance & Cutover Preparation
**Objective:** Finalize statutory requirements necessary for the Department of Higher Education and system switchover.

*   **4.1 Document Management Binding (`Spec-15`)**
    *   Map COT virtual upload components to intercept files and ship them directly to Azure Blob/File Storage without bloating the SQL tables.
*   **4.2 High-Volume Data Ingestion (`Spec-16`)**
    *   Build the background tasks (Hangfire) to process massive national batch files (SAQA, NLRD, DHET) without locking the COT UI interface.
*   **4.3 Statutory Reporting (`Spec-13`)**
    *   Generate the heavy SETMIS compliant SQL views necessary for the XML/JSON extract generation, mapping against the newly normalized Singular dataset.

---

## ✅ Deployment Strategy
Because the Authentication Shell and Registries were locked in Phase 1, Phase 3 modules can be released into UAT instantly upon sprint completion without waiting for the rest of the system. The finalized production switchover only requires disabling the Java ingress points and pointing domain traffic over to the .NET 8 COT Shell.
