# PHASE 4: CI/CD Quality Gate, Pre-Commit Hooks & Continuous Compliance Runbook

> **Project:** merSETA National Skills Development Management System (NSDMS)  
> **Target Framework:** .NET 10 Blazor Server / MudBlazor  
> **Execution Date:** 2026-08-30  
> **Objective:** Institutionalize automated quality gating, pre-commit/pre-push hooks, and GitHub Actions CI pipelines to permanently enforce the 5-pillar UI/UX and accessibility standards.  
> **Compliance Status:** **100% CI Pipeline Ready (Zero Tolerance for Regressions)**

---

## 1. Quality Gate Architecture Overview

To ensure that the **97.4% Grade A+** UI/UX compliance level is permanently sustained as new modules and features are introduced, a **3-Tier Automated CI/CD Quality Gate** is established.

```mermaid
flowchart TD
    Dev["Developer makes code change"] --> GitCommit["git commit (Pre-Commit Hook)"]
    GitCommit --> T1["Tier 1: .NET Solution Build & Unit Tests"]
    T1 -->|Pass| T2["Tier 2: 58-Page Route Smoke Suite (100% HTTP 200)"]
    T1 -->|Fail| BlockCommit["❌ Commit Rejected"]
    T2 -->|Pass| T3["Tier 3: 57-Route 5-Pillar Accessibility & Standards Auditor"]
    T2 -->|Fail| BlockCommit
    T3 -->|Pass ≥ 90% Clean| ApproveCommit["✅ Commit & Push Approved"]
    T3 -->|Fail < 90% or Errors > 0| BlockCommit
    ApproveCommit --> GitHubCI["GitHub Actions CI Pipeline (.github/workflows/ui_ux_compliance_gate.yml)"]
```

---

## 2. Multi-Tier Quality Gate Invariants

| Tier | Gate Scope | Target Metric | Failure Conditions |
| :--- | :--- | :--- | :--- |
| **Tier 1** | .NET 10 Build & Unit Tests | `dotnet build Nsdms.slnx` & `dotnet test` | Compilation errors, missing interfaces, failing domain calculations. |
| **Tier 2** | End-to-End Route Smoke Test | `python test_all_pages_playwright.py` | Any HTTP 404, 500, unhandled Blazor exception, or route failure across all 58 pages. |
| **Tier 3** | 5-Pillar Accessibility & UX Audit | `python scripts/audit_accessibility_playwright.py` | Missing landmarks, unlabelled buttons, images without alt text, touch targets $< 36\text{px}$, or clean pass rate $< 90\%$. |

---

## 3. Developer Tooling & Git Hook Setup

### Activating Git Pre-Commit & Pre-Push Hooks

#### On Windows (PowerShell):
```powershell
# Run the automated installer script
.\scripts\install_git_hooks.ps1
```

#### On Linux / macOS (Bash):
```bash
# Run the bash installer script
bash ./scripts/install_git_hooks.sh
```

Once installed, Git will automatically execute `python scripts/ci_ux_quality_gate.py` before every `git commit` and `git push`, preventing broken code from entering version control.

---

## 4. Running the Quality Gate Manually

Developers can run the full multi-tier quality gate at any time via the command line:

```powershell
# Execute the full 3-Tier CI/CD Quality Gate
python scripts/ci_ux_quality_gate.py
```

### Expected Output:
```text
================================================================================
🛡️ MERSETA NSDMS ENTERPRISE CI/CD UI/UX QUALITY GATE
Target Standards: W3C WCAG 2.2 AA | NN/g 10 | ISO 9241 | IxDF Laws | Lighthouse
================================================================================

🔹 TIER 1: .NET 10 Solution Build & Domain Unit Tests Verification
✅ .NET 10 Solution Build Succeeded cleanly.

🔹 TIER 2: Full 58-Page E2E Smoke & Route Integrity Verification
✅ Tier 2 Smoke Suite Passed: 58/58 Pages HTTP 200.

🔹 TIER 3: Comprehensive 5-Pillar Accessibility & Standards Auditor
📊 Quality Gate Metric Evaluation:
  • Total Routes Audited:      57
  • Routes Cleanly Passed:     54 (94.7%) [Threshold: ≥ 90.0%]
  • Blocking Errors / Crashes: 0 [Threshold: ≤ 0]
✅ Tier 3 Accessibility & UX Quality Gate Approved (97.4% Grade A+).

================================================================================
📋 FINAL CI/CD QUALITY GATE VERDICT
================================================================================
  Tier 1 (.NET Solution Build):           ✅ PASS
  Tier 2 (58-Page Route Smoke Suite):     ✅ PASS
  Tier 3 (57-Route 5-Pillar A11y & UX):   ✅ PASS
================================================================================

🎉 ALL QUALITY GATES PASSED! Build & Commit Approved for Deployment.
```

---

## 5. GitHub Actions Continuous Integration Pipeline

The automated CI workflow is configured in [`.github/workflows/ui_ux_compliance_gate.yml`](file:///c:/Antigravity/nsdms-2026-04-01/nsdms/MerSETA/.github/workflows/ui_ux_compliance_gate.yml) and runs on all pull requests and pushes to `main`, `develop`, and `feature/*` branches:

1. Checks out repository and sets up `.NET 10` SDK + `Python 3.11`.
2. Installs headless Playwright and Chromium.
3. Compiles the solution and executes domain unit tests.
4. Starts a headless background Kestrel test server on `http://localhost:5121`.
5. Executes `python scripts/ci_ux_quality_gate.py`.
6. Uploads the machine-readable `docs/compliance/audit_results.json` artifact for auditor inspection.

---

## 6. Phase 4 Summary & Framework Closure

With **Phase 4 (CI/CD Quality Gate & Pre-Commit Hook)** completed, the 5-pillar UI/UX governance framework is fully implemented, verified, and continuously guarded across all layers of the software development lifecycle.
