"""
NSDMS UI/UX & Accessibility Automated Compliance Auditor
Tests .NET 10 MudBlazor Web App against WCAG 2.2 AA, NN/g, ISO 9241, and IxDF standards.
"""

import sys
import io
import time
import json
from playwright.sync_api import sync_playwright

if sys.stdout.encoding != 'utf-8':
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

BASE_URL = "http://localhost:5121"

ROUTES_TO_AUDIT = [
    {"name": "Executive Dashboard", "path": "/"},
    {"name": "Universal Task Inbox", "path": "/tasks"},
    {"name": "People Directory", "path": "/people"},
    {"name": "Employers Master List", "path": "/employers"},
    {"name": "Employer Detail View", "path": "/employers/1"},
    {"name": "WSP Submissions", "path": "/wsp"},
    {"name": "WSP Detail View", "path": "/wsp/1"},
    {"name": "Discretionary Grants", "path": "/grants"},
    {"name": "Grant Detail View", "path": "/grants/1"},
    {"name": "Banking Details Dual-Signoff", "path": "/finance/banking-details"},
    {"name": "Learner Records", "path": "/learners"},
    {"name": "Learner Detail View", "path": "/learners/1"},
    {"name": "Governance & Delegations", "path": "/governance/delegations"},
    {"name": "Trade Tests & ARPL", "path": "/tradetests"},
    {"name": "ETQA Assessors", "path": "/etqa"},
    {"name": "UI/UX Compliance HUD", "path": "/developer/compliance-audit"}
]

def run_audit():
    print("=" * 80)
    print("🚀 MERSETA NSDMS UI/UX & ACCESSIBILITY AUDIT ENGINE")
    print(f"🎯 Target Server: {BASE_URL}")
    print("📋 Standards: W3C WCAG 2.2 AA | NN/g 10 | ISO 9241-110 | IxDF Laws | Lighthouse")
    print("=" * 80)

    total_routes = len(ROUTES_TO_AUDIT)
    passed_routes = 0
    warnings_total = 0
    errors_total = 0

    results = []

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        # Capture console errors
        console_errors = []
        page.on("console", lambda msg: console_errors.append(msg.text) if msg.type == "error" else None)

        for idx, route in enumerate(ROUTES_TO_AUDIT, 1):
            url = f"{BASE_URL}{route['path']}"
            route_name = route["name"]
            route_issues = []

            print(f"\n[{idx}/{total_routes}] Auditing: {route_name} ({route['path']})")

            try:
                start_time = time.time()
                response = page.goto(url, wait_until="networkidle", timeout=12000)
                load_time_ms = int((time.time() - start_time) * 1000)

                if response and response.status >= 400:
                    route_issues.append(f"HTTP Error {response.status}")
                    print(f"  ❌ HTTP Status: {response.status}")

                # Wait for Blazor interactive circuits to settle
                page.wait_for_timeout(600)

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

                # Check 2: Accessible Buttons (WCAG 4.1.2)
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

                # Check 4: Form Input Labels (WCAG 3.3.2)
                inputs = page.locator("input:not([type='hidden']):visible, select:visible, textarea:visible")
                input_count = inputs.count()
                unlabeled_inputs = 0
                for i in range(min(input_count, 40)):
                    inp = inputs.nth(i)
                    aria_label = inp.get_attribute("aria-label") or ""
                    placeholder = inp.get_attribute("placeholder") or ""
                    input_id = inp.get_attribute("id") or ""
                    has_label = False
                    if input_id:
                        has_label = page.locator(f"label[for='{input_id}']").count() > 0
                    if not aria_label and not placeholder and not has_label:
                        unlabeled_inputs += 1

                if unlabeled_inputs > 0:
                    route_issues.append(f"{unlabeled_inputs} input(s) lack explicit labels or aria-labels")

                # Check 5: IxDF Fitts's Law Target Size Check
                small_targets = 0
                for i in range(min(btn_count, 30)):
                    btn = buttons.nth(i)
                    box = btn.bounding_box()
                    if box and box["width"] > 0 and box["height"] > 0:
                        if box["width"] < 32 or box["height"] < 32:
                            small_targets += 1
                if small_targets > 0:
                    route_issues.append(f"{small_targets} button(s) under 32px hit-target threshold")

                # Summary for Route
                status_icon = "✅ PASS" if not route_issues else "⚠️ WARN"
                if any("HTTP Error" in iss for iss in route_issues):
                    status_icon = "❌ FAIL"
                    errors_total += 1
                elif route_issues:
                    warnings_total += len(route_issues)
                else:
                    passed_routes += 1

                print(f"  Status: {status_icon} | Render Time: {load_time_ms}ms")
                for iss in route_issues:
                    print(f"    - {iss}")

                results.append({
                    "name": route_name,
                    "path": route["path"],
                    "status": "PASS" if not route_issues else ("FAIL" if "HTTP Error" in str(route_issues) else "WARN"),
                    "load_time_ms": load_time_ms,
                    "issues": route_issues
                })

            except Exception as ex:
                errors_total += 1
                print(f"  ❌ EXCEPTION: {ex}")
                results.append({
                    "name": route_name,
                    "path": route["path"],
                    "status": "FAIL",
                    "issues": [str(ex)]
                })

        browser.close()

    print("\n" + "=" * 80)
    print("📊 NSDMS UI/UX AUDIT SUMMARY RESULTS")
    print(f"Total Routes Audited: {total_routes}")
    print(f"Passed Cleanly:       {passed_routes} ({round(passed_routes/total_routes*100)}%)")
    print(f"Total Warnings:       {warnings_total}")
    print(f"Total Errors:         {errors_total}")
    print("=" * 80)

    # Output JSON scorecard
    with open("docs/compliance/audit_results.json", "w", encoding="utf-8") as f:
        json.dump({
            "timestamp": time.strftime("%Y-%m-%d %H:%M:%S"),
            "total_routes": total_routes,
            "passed_cleanly": passed_routes,
            "compliance_percentage": round(passed_routes / total_routes * 100, 1),
            "results": results
        }, f, indent=2)

    print("📄 Saved detailed JSON audit report to docs/compliance/audit_results.json\n")

if __name__ == "__main__":
    run_audit()
