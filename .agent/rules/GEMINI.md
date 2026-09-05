---
trigger: always_on
---

# GEMINI.md - Antigravity Kit

> This file defines how the AI behaves in this workspace.

---

## CRITICAL: AGENT & SKILL PROTOCOL (START HERE)

> **MANDATORY:** You MUST read the appropriate agent file and its skills BEFORE performing any implementation. This is the highest priority rule.

### 1. Modular Skill Loading Protocol

Agent activated → Check frontmatter "skills:" → Read SKILL.md (INDEX) → Read specific sections.

- **Selective Reading:** DO NOT read ALL files in a skill folder. Read `SKILL.md` first, then only read sections matching the user's request.
- **Rule Priority:** P0 (GEMINI.md) > P1 (Agent .md) > P2 (SKILL.md). All rules are binding.

### 2. Enforcement Protocol

1. **When agent is activated:**
    - ✅ Activate: Read Rules → Check Frontmatter → Load SKILL.md → Apply All.
2. **Forbidden:** Never skip reading agent rules or skill instructions. "Read → Understand → Apply" is mandatory.

---

## 📥 REQUEST CLASSIFIER (STEP 1)

**Before ANY action, classify the request:**

| Request Type     | Trigger Keywords                           | Active Tiers                   | Result                      |
| ---------------- | ------------------------------------------ | ------------------------------ | --------------------------- |
| **QUESTION**     | "what is", "how does", "explain"           | TIER 0 only                    | Text Response               |
| **SURVEY/INTEL** | "analyze", "list files", "overview"        | TIER 0 + Explorer              | Session Intel (No File)     |
| **SIMPLE CODE**  | "fix", "add", "change" (single file)       | TIER 0 + TIER 1 (lite)         | Inline Edit                 |
| **COMPLEX CODE** | "build", "create", "implement", "refactor" | TIER 0 + TIER 1 (full) + Agent | **{task-slug}.md Required** |
| **DESIGN/UI**    | "design", "UI", "page", "dashboard"        | TIER 0 + TIER 1 + Agent        | **{task-slug}.md Required** |
| **SLASH CMD**    | /create, /orchestrate, /debug              | Command-specific flow          | Variable                    |

---

## 🤖 INTELLIGENT AGENT ROUTING (STEP 2 - AUTO)

**ALWAYS ACTIVE: Before responding to ANY request, automatically analyze and select the best agent(s).**

> 🔴 **MANDATORY:** You MUST follow the protocol defined in `@[skills/intelligent-routing]`.

### Auto-Selection Protocol

1. **Analyze (Silent)**: Detect domains (Frontend, Backend, Security, etc.) from user request.
2. **Select Agent(s)**: Choose the most appropriate specialist(s).
3. **Inform User**: Concisely state which expertise is being applied.
4. **Apply**: Generate response using the selected agent's persona and rules.

### Response Format (MANDATORY)

When auto-applying an agent, inform the user:

```markdown
🤖 **Applying knowledge of `@[agent-name]`...**

[Continue with specialized response]
```

**Rules:**

1. **Silent Analysis**: No verbose meta-commentary ("I am analyzing...").
2. **Respect Overrides**: If user mentions `@agent`, use it.
3. **Complex Tasks**: For multi-domain requests, use `orchestrator` and ask Socratic questions first.

### ⚠️ AGENT ROUTING CHECKLIST (MANDATORY BEFORE EVERY CODE/DESIGN RESPONSE)

**Before ANY code or design work, you MUST complete this mental checklist:**

| Step | Check | If Unchecked |
|------|-------|--------------|
| 1 | Did I identify the correct agent for this domain? | → STOP. Analyze request domain first. |
| 2 | Did I READ the agent's `.md` file (or recall its rules)? | → STOP. Open `.agent/agents/{agent}.md` |
| 3 | Did I announce `🤖 Applying knowledge of @[agent]...`? | → STOP. Add announcement before response. |
| 4 | Did I load required skills from agent's frontmatter? | → STOP. Check `skills:` field and read them. |

**Failure Conditions:**

- ❌ Writing code without identifying an agent = **PROTOCOL VIOLATION**
- ❌ Skipping the announcement = **USER CANNOT VERIFY AGENT WAS USED**
- ❌ Ignoring agent-specific rules (e.g., Purple Ban) = **QUALITY FAILURE**

> 🔴 **Self-Check Trigger:** Every time you are about to write code or create UI, ask yourself:
> "Have I completed the Agent Routing Checklist?" If NO → Complete it first.

---

## TIER 0: UNIVERSAL RULES (Always Active)

- **Identity & Access Guardrails**: Always maintain the separation between `Person` (Demographics) and `User` (Authentication). A `Person` may exist without a `User` (and vice-versa).
- **RSA ID Logic**: Always use RSA ID numbers to auto-calculate Date of Birth in the UI.
- **Audit Requirement**: Link/Unlink actions must be captured in `audit_logs`.
- **Prisma Windows Blocking**: Remind the user to restart `npm run dev` if `prisma generate` fails due to file locks.

### 🌐 Language Handling

When user's prompt is NOT in English:

1. **Internally translate** for better comprehension
2. **Respond in user's language** - match their communication
3. **Code comments/variables** remain in English

### 🧹 Clean Code (Global Mandatory)

**ALL code MUST follow `@[skills/clean-code]` rules. No exceptions.**

- **Code**: Concise, direct, no over-engineering. Self-documenting.
- **Testing**: Mandatory. Pyramid (Unit > Int > E2E) + AAA Pattern.
- **Performance**: Measure first. Adhere to 2025 standards (Core Web Vitals).
- **Infra/Safety**: 5-Phase Deployment. Verify secrets security.

### 📁 File Dependency Awareness

**Before modifying ANY file:**

1. Check `CODEBASE.md` → File Dependencies
2. Identify dependent files
3. Update ALL affected files together

### 🗺️ System Map Read

> 🔴 **MANDATORY:** Read `ARCHITECTURE.md` at session start to understand Agents, Skills, and Scripts.

**Path Awareness:**

- Agents: `.agent/` (Project)
- Skills: `.agent/skills/` (Project)
- Runtime Scripts: `.agent/skills/<skill>/scripts/`

### 🧠 Read → Understand → Apply

```
❌ WRONG: Read agent file → Start coding
✅ CORRECT: Read → Understand WHY → Apply PRINCIPLES → Code
```

**Before coding, answer:**

1. What is the GOAL of this agent/skill?
2. What PRINCIPLES must I apply?
3. How does this DIFFER from generic output?

---

## TIER 1: CODE RULES (When Writing Code)

### 📱 Project Type Routing

| Project Type                           | Primary Agent         | Skills                        |
| -------------------------------------- | --------------------- | ----------------------------- |
| **MOBILE** (iOS, Android, RN, Flutter) | `mobile-developer`    | mobile-design                 |
| **WEB** (Next.js, React web)           | `frontend-specialist` | frontend-design               |
| **BACKEND** (API, server, DB)          | `backend-specialist`  | api-patterns, database-design |

> 🔴 **Mobile + frontend-specialist = WRONG.** Mobile = mobile-developer ONLY.

### 🛑 Socratic Gate

**For complex requests, STOP and ASK first:**

### 🛑 GLOBAL SOCRATIC GATE (TIER 0)

**MANDATORY: Every user request must pass through the Socratic Gate before ANY tool use or implementation.**

| Request Type            | Strategy       | Required Action                                                   |
| ----------------------- | -------------- | ----------------------------------------------------------------- |
| **New Feature / Build** | Deep Discovery | ASK minimum 3 strategic questions                                 |
| **Code Edit / Bug Fix** | Context Check  | Confirm understanding + ask impact questions                      |
| **Vague / Simple**      | Clarification  | Ask Purpose, Users, and Scope                                     |
| **Full Orchestration**  | Gatekeeper     | **STOP** subagents until user confirms plan details               |
| **Direct "Proceed"**    | Validation     | **STOP** → Even if answers are given, ask 2 "Edge Case" questions |

**Protocol:**

1. **Never Assume:** If even 1% is unclear, ASK.
2. **Handle Spec-heavy Requests:** When user gives a list (Answers 1, 2, 3...), do NOT skip the gate. Instead, ask about **Trade-offs** or **Edge Cases** (e.g., "LocalStorage confirmed, but should we handle data clearing or versioning?") before starting.
3. **Wait:** Do NOT invoke subagents or write code until the user clears the Gate.
4. **Reference:** Full protocol in `@[skills/brainstorming]`.

### 🏁 Final Checklist Protocol

**Trigger:** When the user says "son kontrolleri yap", "final checks", "çalıştır tüm testleri", or similar phrases.

| Task Stage       | Command                                            | Purpose                        |
| ---------------- | -------------------------------------------------- | ------------------------------ |
| **Manual Audit** | `python .agent/scripts/checklist.py .`             | Priority-based project audit   |
| **Pre-Deploy**   | `python .agent/scripts/checklist.py . --url <URL>` | Full Suite + Performance + E2E |

**Priority Execution Order:**

1. **Security** → 2. **Lint** → 3. **Schema** → 4. **Tests** → 5. **UX** → 6. **Lighthouse/E2E**

**Rules:**

- **Completion:** A task is NOT finished until `checklist.py` returns success.
- **Reporting:** If it fails, fix the **Critical** blockers first (Security/Lint).

**Available Scripts (12 total):**

| Script                     | Skill                 | When to Use         |
| -------------------------- | --------------------- | ------------------- |
| `security_scan.py`         | vulnerability-scanner | Always on deploy    |
| `dependency_analyzer.py`   | vulnerability-scanner | Weekly / Deploy     |
| `lint_runner.py`           | lint-and-validate     | Every code change   |
| `test_runner.py`           | testing-patterns      | After logic change  |
| `schema_validator.py`      | database-design       | After DB change     |
| `ux_audit.py`              | frontend-design       | After UI change     |
| `accessibility_checker.py` | frontend-design       | After UI change     |
| `bundle_analyzer.py`       | performance-profiling | Before deploy       |
| `mobile_audit.py`          | mobile-design         | After mobile change |
| `lighthouse_audit.py`      | performance-profiling | Before deploy       |
| `playwright_runner.py`     | webapp-testing        | Before deploy       |

> 🔴 **Agents & Skills can invoke ANY script** via `python .agent/skills/<skill>/scripts/<script>.py`

### 🎭 Gemini Mode Mapping

| Mode     | Agent             | Behavior                                     |
| -------- | ----------------- | -------------------------------------------- |
| **plan** | `project-planner` | 4-phase methodology. NO CODE before Phase 4. |
| **ask**  | -                 | Focus on understanding. Ask questions.       |
| **edit** | `orchestrator`    | Execute. Check `{task-slug}.md` first.       |

**Plan Mode (4-Phase):**

1. ANALYSIS → Research, questions
2. PLANNING → `{task-slug}.md`, task breakdown
3. SOLUTIONING → Architecture, design (NO CODE!)
4. IMPLEMENTATION → Code + tests

> 🔴 **Edit mode:** If multi-file or structural change → Offer to create `{task-slug}.md`. For single-file fixes → Proceed directly.

---

## TIER 2: DESIGN RULES (Reference)

> **Design rules are in the specialist agents, NOT here.**

| Task         | Read                            |
| ------------ | ------------------------------- |
| Web UI/UX    | `.agent/frontend-specialist.md` |
| Mobile UI/UX | `.agent/mobile-developer.md`    |

**These agents contain:**

- Purple Ban (no violet/purple colors)
- Template Ban (no standard layouts)
- Anti-cliché rules
- Deep Design Thinking protocol

> 🔴 **For design work:** Open and READ the agent file. Rules are there.

---

## 📁 QUICK REFERENCE

### Agents & Skills

- **Masters**: `orchestrator`, `project-planner`, `security-auditor` (Cyber/Audit), `backend-specialist` (API/DB), `frontend-specialist` (UI/UX), `mobile-developer`, `debugger`, `game-developer`
- **Key Skills**: `clean-code`, `brainstorming`, `app-builder`, `frontend-design`, `mobile-design`, `plan-writing`, `behavioral-modes`

### Key Scripts

- **Verify**: `.agent/scripts/verify_all.py`, `.agent/scripts/checklist.py`
- **Scanners**: `security_scan.py`, `dependency_analyzer.py`
- **Audits**: `ux_audit.py`, `mobile_audit.py`, `lighthouse_audit.py`
- **Test**: `playwright_runner.py`, `test_runner.py`

---

## 🐞 Controlled Fix-As-You-Go Bug Policy

> This block HARD-BINDS how the agent handles defects discovered during testing.
> 
> The agent MUST follow this policy. No exceptions. No questions.

---

### 1. Execution Philosophy

The agent SHALL operate under a **Controlled Fix-As-You-Go** model:

- Do NOT wait until all tests complete before fixing defects.
- Do NOT blindly fix everything immediately.
- Classify every failure before acting.
- Prioritise system integrity, governance rules, and security invariants.

All failures MUST be classified before any change is made.

---

### 2. Failure Classification Model (MANDATORY)

Every failing test MUST be categorised into one of the following types:

#### A) HARNESS / ENVIRONMENT DEFECT
Examples:
- Test DB not resetting
- Seed data inconsistent
- Migration drift
- Misconfigured environment variables
- Auth test mode not working

**Action:**
- FIX IMMEDIATELY.
- Re-run entire suite after fix.
- Document in `docs/testing/changes-for-testability.md`.

#### B) PRODUCT DEFECT
A defect in business logic, workflow enforcement, CASL permissions, audit integrity, or data consistency. Must be further classified as P0 / P1 / P2.

#### C) TEST DEFECT
Examples:
- Incorrect expectation
- Brittle selector
- Invalid assumption
- Race condition in E2E

**Action:**
- Fix test ONLY if business rule is confirmed correct.
- Document rationale in commit message and execution report.

---

### 3. Severity Model (Hard-Bound)

#### P0 — CRITICAL (FIX IMMEDIATELY)
The agent MUST fix immediately if the defect affects:

**Security / CASL**
- Role escalation
- Unauthorized read/write/update/delete
- Bypassing ability checks
- Condition-based rule failure (e.g., ownership constraints)

**Workflow Governance**
- Invalid state transition allowed
- Valid transition blocked incorrectly
- Transition not atomic (entity updated but transition log missing)
- Transition log written but entity not updated
- Status inconsistent with workflow state

**Data Integrity**
- Partial writes
- Missing required relational integrity
- Transaction not wrapped
- Duplicate primary business identifiers

**Audit Integrity**
- createdAt/updatedAt missing
- createdBy/updatedBy missing or incorrect
- No trace of workflow transition
- Audit middleware bypassed

**Error Taxonomy**
- Wrong error code
- Missing error code
- Generic 500 where domain error expected

**Action for P0:**
1. STOP adding new tests.
2. Fix root cause immediately.
3. Re-run full suite.
4. Update traceability + execution report.

#### P1 — HIGH (FIX AFTER STABILISATION PASS)
- Incorrect UI messaging
- Non-blocking validation error
- Minor reporting miscalculation
- Performance inefficiency not affecting correctness

**Action:**
- Log in `docs/testing/defects.md`
- Fix after suite reaches stable baseline.

#### P2 — LOW (DEFER)
- Cosmetic UI issues
- Minor formatting
- Non-critical UX friction

**Action:**
- Log only.
- Do NOT interrupt test stabilisation.

---

### 4. Fix-As-You-Go Algorithm (MANDATORY FLOW)

For EACH failing test:
1. Classify (Harness / Product / Test)
2. If Harness → Fix immediately.
3. If Product:
    - Determine severity (P0/P1/P2)
    - If P0 → Fix immediately.
    - If P1/P2 → Log and continue.
4. If Test defect → Correct test and document.
5. Re-run affected tests.
6. If root cause fixed → run FULL suite.

The agent MUST always confirm that multiple failures do not share a single root cause before fixing individually.

---

### 5. Root Cause Enforcement Rule
Before fixing more than one failure, the agent MUST:
- Check if failures share module, error code, migration, or permission rule.
- Fix root cause first.
- Avoid patch-style fixes on multiple surfaces.

---

### 6. Transaction & Atomicity Safeguard
If a defect touches:
- Workflow transitions
- Financial calculations
- State changes
- Permission checks + writes

The agent MUST:
- Ensure operation is wrapped in a transaction.
- Validate atomic write + log.
- Add regression test.

---

### 7. CASL Enforcement Safeguard
If defect involves authorization:
- Ability definitions MUST NOT be weakened to make tests pass.
- Server-side enforcement MUST exist even if UI hides controls.
- E2E tests MUST confirm denial behaviour.

---

### 8. Schema Drift Safeguard
If defect requires schema change:
- Update Prisma schema (or ORM schema)
- Run migration
- Update seeds
- Update integration tests
- Document in `docs/testing/schema-impact.md`

No direct DB hot-fixes.

---

### 9. Documentation & Reporting Requirements
After any fix:
- Update `docs/testing/test-execution-report.md`
- Add entry in `docs/testing/defects.md`
- Update traceability matrix if rule changed

Execution report MUST include:
- Failure ID
- Classification
- Severity
- Root cause summary
- Fix summary
- Verification evidence

---

### 10. Prohibited Behaviours
The agent MUST NOT:
- Silence failing tests
- Remove assertions to force pass
- Add arbitrary waits in E2E without root cause
- Disable permission checks to satisfy test
- Skip failed tests without classification

---

### 11. Completion Gate
The agent may only declare testing phase complete when:
- All P0 defects resolved
- No Harness defects remain
- Full suite passes
- Execution report generated
- Traceability updated

---

## 🚀 .NET 10 Blazor & SQL Server Express Protocol
1. **Target Solution**: `.NET 10` solution file `Nsdms.slnx` located in project root.
2. **Project Directory**: All C# projects live under `dotnet/` (`Nsdms.Domain`, `Nsdms.Application`, `Nsdms.Infrastructure`, `Nsdms.Web`, `Nsdms.Tests`).
3. **Database Credentials**: SQL Server Express on `localhost`, Database: `NSDMS-NET`, User: `NSDMS-NET`, Password: `NSDMS-NET`.
4. **Database Naming Conventions**:
   - Table names: Singular PascalCase (`GrantMoa`, `SetmisSubmissionBatch`, `MandatoryGrantDisbursement`, `Organisation`, `WspSubmission`).
   - Primary key: Auto-generated integer `id`.
   - Audit columns: `CreatedAt`, `CreatedBy`, `ModifiedAt`, `ModifiedBy`.
   - Lookups: `*Type` suffix with unique `Code`, indexed `Name`, `Description`, `Active`.
5. **Employer Visit Requirement**: When building features that schedule or execute ANY type of "Visit" activity against an Employer, ALWAYS enforce selection of a specific Contact Person (`contact_person_id` / `ContactPersonId`).
6. **UI Pattern**: MudBlazor Stacked Master-Detail layout (`/employers` table -> `/employers/{id}` full-page view with sticky top bar, breadcrumb back button, and confirmation toasts).
7. **Organisation Nomenclature**: We work with organisations across all legal forms (NPOs, NGOs, public entities, private corporations, levy payers, non-levy payers). The database, domain models, services, and UI components must consistently use `Organisation` (not `Company`), unless specifically referring to a registered corporate entity subtype.
8. **MudBlazor Layout & App Bar Spacing Standard**:
   - Never place utility padding classes (`Class="pa-*"`, `Class="pt-*"`, `Class="py-*"`) directly on `<MudMainContent>`. MudBlazor utility classes apply `!important` which overrides the framework's calculated `padding-top: var(--mud-appbar-height)` (64px) and causes the header to overlap page content by 48px.
   - Always nest inner padding inside `<MudMainContent>`: `<MudMainContent><div class="pa-4"><main id="main-content">@Body</main></div></MudMainContent>`.
   - All sticky action bars, detail top bars, and table toolbars must use `top: var(--mud-appbar-height, 64px) !important;` (or the `.sticky-top` / `.sticky-top-header` CSS classes) so they dock flush underneath the `MudAppBar` during scroll.


---

### 📘 Phase Documentation & Database Governance Standard
1. **Database Schema Standard**:
   - Table names must always use singular PascalCase (`GrantMoa`, `SetmisSubmissionBatch`, `MandatoryGrantDisbursement`).
   - Every table must have an auto-generated integer primary key `id` and audit columns (`CreatedAt`, `CreatedBy`, `ModifiedAt`, `ModifiedBy`).
   - All foreign keys, status fields, and search terms must have explicit indexes in Fluent API.
   - Idempotent T-SQL DDL migrators must accompany every new module under `dotnet/Nsdms.Infrastructure/Data/`.
2. **Clean Architecture Service Contracts**:
   - Every service mutation must perform a double-write into `audit_logs` with a structured `MetadataJson` snapshot.
   - Services must use `INsdmsDbContextFactory` to ensure thread-safety in Blazor Server interactive circuits.
3. **Executable Living Documentation**:
   - Add unit tests in `Nsdms.Tests` for every new business rule and calculation.
   - Maintain and update `test_all_pages_playwright.py` with every new route created to guarantee 100% test coverage.

---

### ⚙️ Dynamic Configuration & Feature Flags Governance
1. **Zero Hardcoding Invariant**: No business parameter, threshold, storage path, or external integration endpoint may be hardcoded. Always use `ISystemConfigurationService` with cascading database overrides.
2. **Integrations Off-By-Default**: All external integrations (Dynamics GP, Sage, Live DHET SFTP, Live SARS FTP, SMS OTP, Azure Blob) MUST default to `IsEnabled = false`. Workflows must cleanly execute in mock simulation mode when disabled.
---

### 🛡️ Razor Component & Master-Detail Invariants
1. **Nomenclature Invariant**: Never use `Company` in labels, placeholders, or headers unless referring to a registered corporate entity subtype. Always use `Organisation`.
2. **Master-Detail Action Toolbar**:
   - Every `*Detail.razor` page must include an action bar with a Back button (`Icons.Material.Filled.ArrowBack`), Cancel button (`Icons.Material.Filled.Cancel`), and Save button (`Icons.Material.Filled.Save`).
   - Mutations must provide `ISnackbar` toast feedback and perform atomic double-writes into `audit_logs`.
   - Top bars on detail views must include `.sticky-top-header` docking below the 64px `MudAppBar` using `position: sticky; top: var(--mud-appbar-height, 64px) !important; z-index: 10;`.
3. **Employer Visit Contact Person Requirement**: Any page scheduling, recording, or executing a visit/monitoring against an Employer MUST enforce selection of a `ContactPersonId`.
4. **Demographics RSA ID Helper**: Any component capturing an RSA ID number must bind an `OnBlur` / `TextChanged` handler to automatically extract and populate Date of Birth, Gender, and Citizenship via `RsaIdValidator.Parse`.

---

### 🛡️ EF Core Nullability & SETMIS Schema Resilience Standard
1. **Optional Relational Codes**: In EF Core entities representing legacy or SETMIS records (`LearnerTradeTest`, `CompanyLearner`, `Person`, `TrainingProvider`), declare all optional foreign key string properties as nullable (`string?`) to prevent `SqlNullValueException` when existing database rows contain NULLs.
2. **Explicit Singular Table Names**: When defining new `DbSet<T>` properties in `INsdmsDbContext` and `NsdmsDbContext`, always configure `modelBuilder.Entity<T>().ToTable("SingularName")` in Fluent API to ensure EF Core does not default to plural table names.

---

### 🎨 UI/UX 5-Pillar Standards & CI Quality Gate Standard
1. **Five-Pillar Invariant**: All UI interfaces must strictly comply with:
   - **W3C WCAG 2.2 AA**: All interactive buttons/icons must provide machine-readable `aria-label` or `Title`; explicit `:focus-visible` outlines; skip-to-content bypass.
   - **NN/g 10 Usability Heuristics**: State visibility via chips/toasts, user freedom with Back buttons, error prevention with confirmation dialogs.
   - **ISO 9241-110 Dialogue Principles**: Direct self-descriptive action verbs (*Save Person*, *Approve Grant*), task suitability, controllability.
   - **IxDF Interaction Design Laws**: Universal $\ge 36\text{px}$ touch targets (Fitts's Law), tabbed schema chunking (Hick's & Miller's Laws).
   - **Google Lighthouse Core Web Vitals**: Sub-2.5s LCP render latency, 0.00 CLS, and accessible contrast.
2. **Illustrated Empty States Invariant**: Never use raw, unstyled text for empty data tables. Always use `<EmptyStateCard>` with a contextual icon, title, description, and action button.
3. **Power-User Keybindings**: Form detail views must support <kbd>Ctrl+S</kbd> to quick-save, <kbd>/</kbd> to focus table search, and <kbd>Esc</kbd> to cancel.
4. **CI/CD Quality Gate**: All code changes must pass `python scripts/ci_ux_quality_gate.py` with 0 blocking errors and $\ge 90\%$ clean pass rate before merging.

---

### 🛡️ SQL Server Temporal Tables & History Schema Governance
1. **Dedicated History Schema**: All system-versioned temporal history tables must reside in the `history` schema (`history.<Entity>History`).
2. **EF Core Convention**: All persistent domain entities in `NsdmsDbContext` are mapped as temporal tables via `modelBuilder.ApplyTemporalTables()`.
3. **Exclusions**: Append-only tables (`AuditLog`), workflow transient leases (`WorkflowTaskLease`), and static lookup tables (`lookup.*`) are non-temporal.
4. **Point-in-Time Queries**: Use EF Core's native temporal query extensions (`.TemporalAsOf(dateTime)`, `.TemporalBetween(start, end)`, `.TemporalAll()`) when retrieving historical snapshots.

---

### 🛡️ Schema-Domain Synchronization Rule
- Whenever new properties are added to an Entity class in `Nsdms.Domain/Entities/`, immediately:
  1. Add corresponding `ALTER TABLE ... ADD [ColumnName] ...` clauses to the active Schema Migrator in `Nsdms.Infrastructure/Data/`.
  2. Update the master DDL script (`V2026_08_Complete_Nsdms_Enterprise_DDL.sql`).
  3. Verify column presence against `INFORMATION_SCHEMA.COLUMNS` before testing UI routes.

---

### 🛡️ Artisan Mentor-to-Apprentice Ratio Governance Standard
1. **Cascading Evaluation Precedence**:
   When validating learner enrollments, workplace capacity, or artisan quotas, ALWAYS resolve ratios via `IMentorRatioPolicyEngine.EvaluateWorkplaceApprovalCapacityAsync(id)`. Never hardcode a 1:4 ratio. The engine evaluates policies in the following strict order:
   - **Tier 1 (Mentor Override)**: `WorkplaceApprovalMentor.MaxLearnerCapacity` or `IsRatioExempt = true`.
   - **Tier 2 (Workplace Approval)**: `WorkplaceApproval.CustomTradeRatio` or `IsRatioEnforced = false`.
   - **Tier 3 (Organisation Exemption)**: `Organisation.IsMentorRatioEnforced = false` or `Organisation.CustomMentorRatioCap`.
   - **Tier 4 (Trade Policy)**: `TradeMentorRatioPolicy.StandardRatio` matching the trade/qualification.
   - **Tier 5 (Global Toggle)**: System configuration `WorkplaceApproval.EnforceMentorRatios`.
2. **Audit Double-Write**: All policy overrides and trade policy mutations must record snapshots in `audit_logs`.

---

### 🛡️ Statutory Navigation Pillar Architecture & Persona Governance Standard
1. **7 Statutory Domain Pillars**: All navigation menu items MUST reside strictly within the 7 official NSDMS domain pillars:
   - `Overview & tasks`
   - `Registries & stakeholders`
   - `Grants, levies & finance`
   - `Learner & artisan development`
   - `Quality assurance & ETQA`
   - `Legal, compliance & BI`
   - `System administration`
2. **Zero IT Asset / Hardware Module Leakage**: Never place IT asset tracking, SIM cards, or telecom hardware modules into core MerSETA skills navigation menus.
3. **Collapsible Accordion & Sentence Case Invariant**: All pillar headers and menu items must use standard sentence case and implement `MudNavGroup` accordion containers with persisted collapse states.
4. **Persona Normalization**: All 10 role personas (`SDF`, `SDP`, `Assessor`, `Finance`, `CLO`, `Legal`, `Compliance`, `Executive`, `Admin`, `All`) must be mapped dynamically in `NavigationMenuService` and `PersonaSwitcher`.
5. **Decoupled Pin Targets**: Quick-access pin toggles must be standalone icon buttons isolated from nav link anchors to prevent overlapping focus/touch hitboxes (WCAG 2.2 AA SC 2.5.8).

---

### 🛡️ UI Plain-Language & Anti-Jargon Governance Invariant
1. **Zero Database/Architecture Jargon**: Replace "Double-Write / MetadataJson" with **"Audited Change Log"**; replace "Temporal Tables" with **"Historical Version Timeline"**.
2. **Zero Cryptography Jargon**: Replace "SHA-256 Hash / Fingerprint" with **"Digital Security Seal"** or **"Verification Reference"**. Keep 64-character hashes hidden under an expandable "Technical Verification Data" drawer.
3. **Disambiguate "Claims"**: Never use "Claims" for authorization permissions on the UI (use **"Permissions / Authorised Functions"**). Reserve "Claims" exclusively for **Discretionary Grant Tranche Invoices**.
4. **Natural Workflow State Language**: Replace "Terminal State" with **"Completed / Finalised"**; replace "Workflow Blueprint" with **"Approval Process Lifecycle"**.
5. **Task Management Clarity**: Replace "Task Lease" with **"Reserved / In Review by [Officer]"**.
6. **Mask All Database Integer Keys**: Dropdowns, headers, badges, and table cells must only display statutory business references (e.g. `DG-2026-TOYOTA-01`, `WSP-2026-0042`, `SDL: L123456789`).

---

### 🛡️ MerSETA Statutory Nomenclature & Terminology Governance
1. **Discretionary Grants (DG) vs Mandatory Grants (MG)**: Never use the word "Grant" in isolation. Always qualify as "Discretionary Grant (DG)" (PIVOTAL strategic allocations / MoAs) or "Mandatory Grant (MG)" (20% WSP/ATR levy rebates).
2. **Contracting via MoA**: The legal contracting instrument for Discretionary Grants is the **Memorandum of Agreement (MoA)**, never generic "Contracts".
3. **Skills Development Providers (SDP)**: Refer to accredited training institutions as **Skills Development Providers (SDPs)** per QCTO statutory guidelines.
4. **Artisan Mentorship Ratios**: Enforce NAMB / QCTO artisan mentor-to-apprentice ratios via `IMentorRatioPolicyEngine`, respecting trade-specific caps.
5. **Governance & PFMA Controls**: Adhere to Delegation of Financial Authority (DOFA), Segregation of Duties (Maker-Checker), and non-repudiation audit logging for all approval gates.

---

## UI rules

`UI-STANDARD.md` in this repository governs every page, dialog, and shared UI component. Read it before creating or modifying any UI, and treat it as normative — not as advice.

**Before generating a page:**

1. State which archetype it implements (A1–A5, or T3 reference data). If it fits none, stop and ask — do not invent a sixth.
2. Check the shared component set. Use those components; do not hand-write badges, headers, button groups, grids, steppers, or stat cards.
3. Check the technology mapping note for this application's entity vocabulary, and use those nouns in routes, labels, and messages.

**Rules that are breached most often — check these explicitly:**

- Records open in **View** mode. Edit is a separate route reached by an explicit action.
- **State reports, actions perform.** A badge is never clickable; a button never displays a value.
- Workflow **state**, **status**, and **flags** are three distinct things. One state badge per page.
- Transition actions come from the shared transition service, never from conditions written into a page. Never render an action that will fail.
- Every list meets the full data table baseline, via the shared grid component.
- The unique key column is a real hyperlink to the record's View route.
- Reference data is maintained in the reference-data area, not as entity CRUD. Values are deactivated, never deleted once referenced.

**Before declaring any page done:**

Run the compliance checklist in the standard. A page that cannot tick every box is not finished.

**When declining or deviating:**

Cite the clause identifier. If the standard does not cover what is needed, stop and ask rather than improvising. Report clauses you believe are wrong — do not edit `UI-STANDARD.md`.

### 🛡️ Multi-Tenancy Scoped Factory & Entity Query Filter Invariant
1. **Primary Entity Query Filters**: Every entity scoped to an organisation (`Organisation`, `OrganisationSite`, `OrganisationContact`, `WspSubmission`, `GrantApplication`, `Visit`) MUST declare `entity.HasQueryFilter(e => _tenantProvider.IsAdmin || _tenantProvider.CurrentOrganisationId == null || e.OrganisationId == _tenantProvider.CurrentOrganisationId);` in `NsdmsDbContext`. For `Organisation`, the filter evaluates `o.Id == _tenantProvider.CurrentOrganisationId`.
2. **Scoped Factory Registration**: `INsdmsDbContextFactory` MUST be registered as `Scoped` in `Program.cs` and inject scoped `ITenantProvider`. `NsdmsDbContext` must have `[ActivatorUtilitiesConstructor]` on its constructor accepting `ITenantProvider? tenantProvider`.
3. **Application Service Defense**: Application service listing methods (`GetAllAsync`, `GetPagedAsync`, `GetByIdAsync`) must inject `ITenantProvider` and enforce `CurrentOrganisationId` constraints when `!_tenantProvider.IsAdmin`.
4. **UI Tenancy Subscription**: Any Blazor page rendering tenant-scoped records must inject `ITenantProvider`, implement `IDisposable`, subscribe to `TenantProvider.OnTenantChanged`, and re-query data on tenant context mutations.
5. **Cross-Tenant Aggregation**: Services dedicated to discovering a user's multi-company affiliations (`OrganisationContextService`) or background bulk reconciliation engines must explicitly append `.IgnoreQueryFilters()`.

---

### 🛡️ Statutory Learner Registration & Minor Protection Invariant
1. **Minor Co-Signatory Requirement**:
   - For all learner registration workflows (Agreements, ARPL, Trade Tests), any applicant under 18 years of age at the time of agreement execution MUST capture a linked `PersonGuardian` record. Bypassing guardian details for minors violates the Skills Development Act.
2. **30-Working-Day Submission Deadline**:
   - Applications must be submitted within 30 working days of `LearnerSignatureDate`. Working days must exclude Saturdays, Sundays, and gazetted South African public holidays via `CompanyLearnerDomainValidator.CalculateWorkingDays`. Submissions exceeding 30 working days require formal condonation.
3. **Engineering Candidacy Exception**:
   - Engineering Candidacy programmes do not require an active SAQA Qualification ID, but MANDATE a verified Professional Council Registration Number (e.g. ECSA Candidate Engineer Reference).
4. **SETMIS Anti-Placeholder Enforcement**:
   - Never accept placeholder strings (`%UNKNOWN%`, `AS ABOVE`, `N/A`, `SOOS BO`, `TEST`) in person names, street addresses, or postal codes. Mobile numbers must strictly conform to 10 digits starting with `0` (`^0\d{9}$`).

---

### 🛡️ ARPL & Artisan Trade Test Statutory Governance Standard (Signed 2023 Specification)
1. **17 Designated Toolkit Trades Whitelist**:
   - Only 17 statutory designated trades (Diesel Mechanic, Motor Mechanic, Boilermaker, Welder, Fitter, Fitter & Turner, Electrician, Heavy Equipment Mechanic, Instrument Mechanic, Lift Mechanic, Shipbuilder, Panel Beater, Vehicle Painter, Bricklayer, Plumber, Carpenter, Sheet fed-Lithograph) require toolkits.
   - Category 7 applications (`Category7_Min3Years_ToolkitAssessment`) are strictly restricted to these 17 trades. All other trades require qualification prerequisites under Categories 1–6.
2. **50% Task Credit Retention & Non-Destructive History**:
   - Candidates passing $\ge 50\%$ of evaluated practical tasks retain credit for passed modules for a maximum of 3 attempts or 18 months.
   - Old tasks must NEVER be deleted from `TradeTestTask` upon re-testing; tasks are partitioned by `AttemptNumber` to maintain a non-repudiable audit trail.
3. **2-Tier Regional Approval & Serial Number Milestone**:
   - Applications must pass 2 sequential regional gates: CLA recommendation (`RecommendedApplication`) followed by Regional QA approval (`Registered`).
   - The official Trade Test Serial Number (`TT-SER-{yyyy}-{id:D5}`) is generated strictly upon QA approval and must be stamped on the re-uploaded application document.
   - QA rejection requires setting `IsFinalRejection`: `false` routes for candidate resubmission (`RejectedForResubmission`); `true` marks terminal rejection (`RejectedApplication`).
4. **Dynamic Document Upload Gate**:
   - Toolkit trades require 7 mandatory verified documents; non-toolkit trades require 6 verified documents per Section 4.2.7.

---

### 🛡️ Workplace Approval & Site Inspection Statutory Governance Standard (Signed 2022 Specification)
1. **Role-Neutral Maker-Checker Workflow Architecture**:
   - Job titles such as "CLO" or "QA" are transitory organizational assignments, not permanent workflow roles.
   - All workflow states, transitions, entity columns, and UI actions MUST use role-neutral terminology:
     - **Verification / Inspection**: `Verification Officer` (verification audit, on-site or desktop audit, `VerifiedDate`, `VerifiedByPersonId`, `VerificationRecommendationReason`, `VerificationRejectionReason`).
     - **Evaluation / Adjudication**: `Approval Authority` (maker-checker decision gatekeeper, `DecisionDate`, `DecisionByPersonId`, `ApprovalReason`, `RejectionReason`).
     - **Applicant / Submitter**: `Primary SDF / Applicant` (submission, condonation, withdrawal).
2. **20-Working-Day Statutory Inspection SLA**:
   - Applications submitted to status `APPLICATION` MUST compute a 20-working-day SLA inspection due date (`InspectionDueDate`), excluding weekends and gazetted public holidays via `WorkplaceApprovalService.AddBusinessDays`.
   - UI master detail views must render real-time countdown chips with alert state progression (Compliant, Warning $\le 5$ days, Overdue).
3. **Desktop vs Physical On-Site Inspection Dual-Mode**:
   - Verification may occur via physical on-site audit (`IsSiteVisitRequired = true`) or desktop evaluation (`IsSiteVisitRequired = false`).
   - If a physical site visit is waived in favour of desktop audit, `SiteVisitJustification` is mandatory and must be audited.
4. **Official QuestPDF Statutory Controlled Documents**:
   - **Annexure 10.1 (`ETQ-TP-003`)**: Workplace Approval Outcome Letter complete with official merSETA branding, QR code verification reference (`/verify/workplace-approval/{id}`), 3-year accreditation validity cycle, and controlled document metadata box.
   - **Annexure 10.2 (`ETQ-TP-054`)**: Workplace Approval Report complete with site information, trade curriculum scope, tool/equipment compliance audit table, and certified artisan mentor quota breakdown.
5. **Preservation of Advanced Invariants & Relational Collections**:
   - Live 5-tier cascading NAMB mentor-to-apprentice ratio policy engine (`IMentorRatioPolicyEngine`).
   - Mandatory Employer Contact Person linkage for all site visits and inspections.
   - 360-degree relational tabs (Placed Apprentices, Partnering SDPs, Site Audits, Tool Inventory, Mentors, Evidence Vault, Workflow Timeline).

---

# END OF POLICY — NON-NEGOTIABLE


