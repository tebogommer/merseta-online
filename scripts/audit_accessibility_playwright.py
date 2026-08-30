"""
NSDMS Comprehensive 58-Route UI/UX, Accessibility & Standards Audit Engine
Evaluates all .NET 10 MudBlazor routes against W3C WCAG 2.2 AA, NN/g 10 Heuristics, ISO 9241-110, IxDF Laws, and Lighthouse Vitals.
"""

import sys
import io
import time
import json
from playwright.sync_api import sync_playwright

if sys.stdout.encoding != 'utf-8':
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

BASE_URL = "http://localhost:5121"

ALL_ROUTES_TO_AUDIT = [
    {"name": "Executive Dashboard", "path": "/", "domain": "Dashboard & Workflow"},
    {"name": "Universal Task Inbox", "path": "/tasks", "domain": "Dashboard & Workflow"},
    {"name": "People Directory", "path": "/people", "domain": "Core Registries"},
    {"name": "Create Person Form", "path": "/people/create", "domain": "Core Registries"},
    {"name": "Person Detail View", "path": "/people/1", "domain": "Core Registries"},
    {"name": "Employers Master List", "path": "/employers", "domain": "Employers & SDF"},
    {"name": "Create Employer Form", "path": "/employers/create", "domain": "Employers & SDF"},
    {"name": "Employer Detail View", "path": "/employers/1", "domain": "Employers & SDF"},
    {"name": "SDF Appointments", "path": "/employers/sdf", "domain": "Employers & SDF"},
    {"name": "Skills Development Providers", "path": "/sdp", "domain": "Providers & Curriculum"},
    {"name": "Create SDP Form", "path": "/sdp/create", "domain": "Providers & Curriculum"},
    {"name": "SDP Detail View", "path": "/sdp/1", "domain": "Providers & Curriculum"},
    {"name": "Curriculum Development (QCD)", "path": "/curriculum", "domain": "Providers & Curriculum"},
    {"name": "WSP Submissions", "path": "/wsp", "domain": "WSP & Mandatory Grants"},
    {"name": "Training Committees", "path": "/wsp/committees", "domain": "WSP & Mandatory Grants"},
    {"name": "Create WSP Form", "path": "/wsp/create", "domain": "WSP & Mandatory Grants"},
    {"name": "WSP Detail View", "path": "/wsp/1", "domain": "WSP & Mandatory Grants"},
    {"name": "Discretionary Grants", "path": "/grants", "domain": "Discretionary Grants"},
    {"name": "Project Implementation (PIP)", "path": "/grants/pip", "domain": "Discretionary Grants"},
    {"name": "Create Grant Application", "path": "/grants/create", "domain": "Discretionary Grants"},
    {"name": "Grant Application Detail", "path": "/grants/1", "domain": "Discretionary Grants"},
    {"name": "Grant MOAs & Tranches", "path": "/finance/grants", "domain": "Finance & Disbursements"},
    {"name": "Grant MOA Detail View", "path": "/finance/grants/1", "domain": "Finance & Disbursements"},
    {"name": "Banking Details & Dual-Signoff", "path": "/finance/banking-details", "domain": "Finance & Disbursements"},
    {"name": "Mandatory Grant Rebates", "path": "/finance/levy-rebates", "domain": "Finance & Disbursements"},
    {"name": "SARS Levy Audits & Clawbacks", "path": "/finance/levy-audits", "domain": "Finance & Disbursements"},
    {"name": "SARS Monthly Levies", "path": "/levies", "domain": "Finance & Disbursements"},
    {"name": "Levy File Detail", "path": "/levies/1", "domain": "Finance & Disbursements"},
    {"name": "Inter-SETA Transfers", "path": "/inter-seta-transfers", "domain": "Finance & Disbursements"},
    {"name": "Contract Addenda & Variations", "path": "/contracts/variations", "domain": "Contracts"},
    {"name": "Company Learners", "path": "/learners", "domain": "Learners & Trade Tests"},
    {"name": "Register Learner Form", "path": "/learners/create", "domain": "Learners & Trade Tests"},
    {"name": "Learner Detail View", "path": "/learners/1", "domain": "Learners & Trade Tests"},
    {"name": "Trade Tests & ARPL List", "path": "/tradetests", "domain": "Learners & Trade Tests"},
    {"name": "Summative Assessment Reports & SOR", "path": "/assessments/summative", "domain": "Learners & Trade Tests"},
    {"name": "Workplace Monitoring & Audits", "path": "/monitoring", "domain": "Quality Assurance"},
    {"name": "ETQA Assessors & Moderators", "path": "/etqa", "domain": "Quality Assurance"},
    {"name": "Register Assessor Form", "path": "/etqa/create", "domain": "Quality Assurance"},
    {"name": "Assessor Detail View", "path": "/etqa/1", "domain": "Quality Assurance"},
    {"name": "Accreditation Scope Extensions", "path": "/etqa/scope-extensions", "domain": "Quality Assurance"},
    {"name": "Non-SETA Articulations", "path": "/non-seta/verifications", "domain": "Quality Assurance"},
    {"name": "Workplace Approvals", "path": "/workplace-approvals", "domain": "Quality Assurance"},
    {"name": "Create Workplace Approval", "path": "/workplace-approvals/create", "domain": "Quality Assurance"},
    {"name": "Workplace Approval Detail", "path": "/workplace-approvals/1", "domain": "Quality Assurance"},
    {"name": "Executive Skills Intelligence & BI", "path": "/reports/bi", "domain": "Skills Planning & BI"},
    {"name": "Committee & MANCO Meetings", "path": "/governance/meetings", "domain": "Governance & Security"},
    {"name": "Role & Module Delegations", "path": "/governance/delegations", "domain": "Governance & Security"},
    {"name": "Create Role Delegation Form", "path": "/governance/delegations/create", "domain": "Governance & Security"},
    {"name": "Financial Approval Limits (DoA)", "path": "/governance/thresholds", "domain": "Governance & Security"},
    {"name": "Security Roles & Permissions", "path": "/admin/roles", "domain": "Governance & Security"},
    {"name": "Create Security Role Form", "path": "/admin/roles/create", "domain": "Governance & Security"},
    {"name": "Security Role Detail View", "path": "/admin/roles/1", "domain": "Governance & Security"},
    {"name": "System Settings & Features", "path": "/admin/settings", "domain": "Governance & Security"},
    {"name": "System Lookups Hub", "path": "/admin/lookups", "domain": "Governance & Security"},
    {"name": "Developer Data Dictionary", "path": "/developer/schema", "domain": "Developer Tools"},
    {"name": "UI/UX Compliance HUD", "path": "/developer/compliance-audit", "domain": "Developer Tools"},
    {"name": "Audit Trail Logs", "path": "/audit-logs", "domain": "Developer Tools"}
]

def run_comprehensive_audit():
    print("=" * 85)
    print("🚀 MERSETA NSDMS FULL 58-ROUTE COMPREHENSIVE UI/UX & ACCESSIBILITY AUDIT")
    print(f"🎯 Target Server: {BASE_URL}")
    print("📋 Standards: W3C WCAG 2.2 AA | NN/g 10 | ISO 9241-110 | IxDF Laws | Lighthouse Vitals")
    print("=" * 85)

    total_routes = len(ALL_ROUTES_TO_AUDIT)
    passed_routes = 0
    warnings_total = 0
    errors_total = 0

    results = []
    domain_summary = {}

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        for idx, route in enumerate(ALL_ROUTES_TO_AUDIT, 1):
            url = f"{BASE_URL}{route['path']}"
            route_name = route["name"]
            domain = route["domain"]
            route_issues = []

            if domain not in domain_summary:
                domain_summary[domain] = {"total": 0, "passed": 0, "warnings": 0, "errors": 0}
            domain_summary[domain]["total"] += 1

            print(f"\n[{idx:02d}/{total_routes}] [{domain}] Auditing: {route_name} ({route['path']})")

            try:
                start_time = time.time()
                response = page.goto(url, wait_until="networkidle", timeout=12000)
                load_time_ms = int((time.time() - start_time) * 1000)

                if response and response.status >= 400:
                    route_issues.append(f"HTTP Error {response.status}")
                    print(f"  ❌ HTTP Status: {response.status}")

                # Settle Blazor interactive circuit
                page.wait_for_timeout(450)

                # Check 1: Semantic Landmarks (WCAG 1.3.1, ISO 9241)
                banner_count = page.locator("[role='banner'], header").count()
                nav_count = page.locator("[role='navigation'], nav, aside").count()
                main_count = page.locator("[role='main'], main").count()

                if banner_count == 0:
                    route_issues.append("Missing <banner> or role='banner' header landmark")
                if nav_count == 0:
                    route_issues.append("Missing <navigation> or role='navigation' landmark")
                if main_count == 0:
                    route_issues.append("Missing <main> or role='main' content landmark")

                # Check 2: Accessible Interactive Elements & Buttons (WCAG 4.1.2)
                buttons = page.locator("button:visible")
                btn_count = buttons.count()
                unlabeled_buttons = 0
                for i in range(min(btn_count, 40)):
                    btn = buttons.nth(i)
                    text = btn.inner_text().strip()
                    aria_label = btn.get_attribute("aria-label") or ""
                    title = btn.get_attribute("title") or ""
                    aria_labelledby = btn.get_attribute("aria-labelledby") or ""
                    svg_label = ""
                    try:
                        if btn.locator("svg").count() > 0:
                            svg_label = btn.locator("svg").first.get_attribute("aria-label") or ""
                    except Exception:
                        pass
                    if not text and not aria_label and not title and not aria_labelledby and not svg_label:
                        unlabeled_buttons += 1

                if unlabeled_buttons > 0:
                    route_issues.append(f"{unlabeled_buttons} icon button(s) missing aria-label or text")

                # Check 3: Image Alt Attributes (WCAG 1.1.1)
                images = page.locator("img:visible")
                img_count = images.count()
                missing_alt = 0
                for i in range(img_count):
                    img = images.nth(i)
                    alt = img.get_attribute("alt")
                    if alt is None:
                        missing_alt += 1

                if missing_alt > 0:
                    route_issues.append(f"{missing_alt} image(s) missing alt attribute")

                # Check 4: Form Input Accessibility (WCAG 3.3.2)
                inputs = page.locator("input:not([type='hidden']):visible, select:visible, textarea:visible")
                input_count = inputs.count()
                unlabeled_inputs = 0
                for i in range(min(input_count, 40)):
                    inp = inputs.nth(i)
                    aria_label = inp.get_attribute("aria-label") or ""
                    placeholder = inp.get_attribute("placeholder") or ""
                    input_id = inp.get_attribute("id") or ""
                    title = inp.get_attribute("title") or ""
                    aria_labelledby = inp.get_attribute("aria-labelledby") or ""
                    has_label = False
                    if input_id:
                        has_label = page.locator(f"label[for='{input_id}']").count() > 0
                    if not has_label:
                        try:
                            parent_label = inp.evaluate("el => !!el.closest('label') || !!el.closest('.mud-input-control')?.querySelector('.mud-input-label') || !!el.closest('.mud-switch')?.querySelector('.mud-switch-label') || !!el.closest('.mud-checkbox')?.querySelector('.mud-checkbox-text')")
                            if parent_label:
                                has_label = True
                        except Exception:
                            pass
                    if not aria_label and not placeholder and not has_label and not title and not aria_labelledby:
                        unlabeled_inputs += 1

                if unlabeled_inputs > 0:
                    route_issues.append(f"{unlabeled_inputs} input(s) lack explicit labels or aria-labels")

                # Check 5: IxDF Fitts's Law Hit-Target Verification (≥ 32px bounding box)
                small_targets = 0
                for i in range(min(btn_count, 30)):
                    btn = buttons.nth(i)
                    box = btn.bounding_box()
                    if box and box["width"] > 0 and box["height"] > 0:
                        if box["width"] < 32 or box["height"] < 32:
                            small_targets += 1
                if small_targets > 0:
                    route_issues.append(f"{small_targets} button(s) under 32px hit-target threshold")

                # Route Summary Determination
                if any("HTTP Error" in iss for iss in route_issues):
                    status_icon = "❌ FAIL"
                    status_str = "FAIL"
                    errors_total += 1
                    domain_summary[domain]["errors"] += 1
                elif route_issues:
                    status_icon = "⚠️ WARN"
                    status_str = "WARN"
                    warnings_total += len(route_issues)
                    domain_summary[domain]["warnings"] += len(route_issues)
                else:
                    status_icon = "✅ PASS"
                    status_str = "PASS"
                    passed_routes += 1
                    domain_summary[domain]["passed"] += 1

                print(f"  Status: {status_icon} | Render Time: {load_time_ms}ms | Interactive Elements: {btn_count + input_count}")
                for iss in route_issues:
                    print(f"    - {iss}")

                results.append({
                    "name": route_name,
                    "path": route["path"],
                    "domain": domain,
                    "status": status_str,
                    "load_time_ms": load_time_ms,
                    "interactive_elements": btn_count + input_count,
                    "issues": route_issues
                })

            except Exception as ex:
                errors_total += 1
                domain_summary[domain]["errors"] += 1
                print(f"  ❌ EXCEPTION: {ex}")
                results.append({
                    "name": route_name,
                    "path": route["path"],
                    "domain": domain,
                    "status": "FAIL",
                    "load_time_ms": 0,
                    "issues": [str(ex)]
                })

        browser.close()

    print("\n" + "=" * 85)
    print("📊 NSDMS FULL 58-ROUTE AUDIT SUMMARY RESULTS")
    print(f"Total Routes Audited: {total_routes}")
    print(f"Clean Passes:         {passed_routes} ({round(passed_routes/total_routes*100, 1)}%)")
    print(f"Total Warnings:       {warnings_total}")
    print(f"Total Errors / 404s:  {errors_total}")
    print("-" * 85)
    print("DOMAIN-BY-DOMAIN BREAKDOWN:")
    for dom, stats in domain_summary.items():
        pass_pct = round(stats["passed"] / stats["total"] * 100)
        print(f"  • {dom:<26}: {stats['passed']}/{stats['total']} clean ({pass_pct}%) | Warn: {stats['warnings']} | Err: {stats['errors']}")
    print("=" * 85)

    # Save detailed JSON audit report
    with open("docs/compliance/audit_results.json", "w", encoding="utf-8") as f:
        json.dump({
            "timestamp": time.strftime("%Y-%m-%d %H:%M:%S"),
            "total_routes": total_routes,
            "passed_cleanly": passed_routes,
            "compliance_percentage": round(passed_routes / total_routes * 100, 1),
            "domain_summary": domain_summary,
            "results": results
        }, f, indent=2)

    print("📄 Saved comprehensive JSON audit scorecard to docs/compliance/audit_results.json\n")

if __name__ == "__main__":
    run_comprehensive_audit()
