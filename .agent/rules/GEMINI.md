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

# END OF POLICY — NON-NEGOTIABLE

