import sys
import io
import time
from playwright.sync_api import sync_playwright

if sys.stdout.encoding != 'utf-8':
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

BASE_URL = "http://localhost:5121"

# Comprehensive list of routes across all subsystems
PAGES_TO_TEST = [
    {"name": "Executive Dashboard", "url": "/"},
    {"name": "Universal Task Inbox", "url": "/tasks"},
    {"name": "People Directory", "url": "/people"},
    {"name": "Create Person Form", "url": "/people/create"},
    {"name": "Person Detail View", "url": "/people/1"},
    {"name": "Employers List", "url": "/employers"},
    {"name": "Create Employer Form", "url": "/employers/create"},
    {"name": "Employer Detail View", "url": "/employers/1"},
    {"name": "Employer Edit Mode", "url": "/employers/1/edit"},
    {"name": "Skills Development Providers", "url": "/sdp"},
    {"name": "Create SDP Form", "url": "/sdp/create"},
    {"name": "SDP Detail View", "url": "/sdp/1"},
    {"name": "Curriculum Development (QCD)", "url": "/curriculum"},
    {"name": "Curriculum Detail View", "url": "/curriculum/1"},
    {"name": "Curriculum Edit View", "url": "/curriculum/1/edit"},
    {"name": "WSP Submissions", "url": "/wsp"},
    {"name": "Training Committees", "url": "/wsp/committees"},
    {"name": "Create WSP Form", "url": "/wsp/create"},
    {"name": "WSP Detail View", "url": "/wsp/1"},
    {"name": "Discretionary Grants (DG)", "url": "/dg-grants"},
    {"name": "Discretionary Grants Legacy Route", "url": "/grants"},
    {"name": "DG Funding Windows Hub", "url": "/dg-funding-windows"},
    {"name": "Open DG Funding Window Form", "url": "/dg-funding-windows/create"},
    {"name": "DG Funding Window Detail View", "url": "/dg-funding-windows/1"},
    {"name": "DG Funding Windows Legacy Route", "url": "/grants/windows"},
    {"name": "Open DG Funding Window Legacy Route", "url": "/grants/windows/create"},
    {"name": "DG Funding Window Detail Legacy Route", "url": "/grants/windows/1"},
    {"name": "DG Strategic BI Intelligence Hub", "url": "/reports/dg-strategic"},
    {"name": "DG Strategic Reports Alias", "url": "/dg-strategic-reports"},
    {"name": "Project Implementation (PIP)", "url": "/grants/pip"},
    {"name": "Create DG Application Form", "url": "/dg-grants/create"},
    {"name": "DG Application Detail View", "url": "/dg-grants/1"},
    {"name": "Create Grant Application Legacy Route", "url": "/grants/create"},
    {"name": "Grant Application Detail Legacy Route", "url": "/grants/1"},
    {"name": "Discretionary Grant (DG) MOAs & Tranches", "url": "/finance/dg-moa"},
    {"name": "DG MOA Detail View", "url": "/finance/dg-moa/1"},
    {"name": "Grant MOAs Legacy Route", "url": "/finance/grants"},
    {"name": "Grant MOA Detail Legacy Route", "url": "/finance/grants/1"},
    {"name": "Banking Details & Dual-Signoff", "url": "/finance/banking-details"},
    {"name": "Banking Details Detail View", "url": "/finance/banking-details/1"},
    {"name": "Mandatory Grant (MG) Rebates", "url": "/finance/mg-rebates"},
    {"name": "Mandatory Grant Rebates Legacy Route", "url": "/finance/levy-rebates"},
    {"name": "SARS Levy Audits & Clawbacks", "url": "/finance/levy-audits"},
    {"name": "SARS Monthly Levies", "url": "/levies"},
    {"name": "Levy File Detail", "url": "/levies/1"},
    {"name": "Inter-SETA Transfers", "url": "/inter-seta-transfers"},
    {"name": "SDF Appointments", "url": "/employers/sdf"},
    {"name": "Contract Addenda & Variations", "url": "/contracts/variations"},
    {"name": "Contract Variation Detail", "url": "/contracts/variations/1"},
    {"name": "Company Learners", "url": "/learners"},
    {"name": "Register Learner Form", "url": "/learners/create"},
    {"name": "Learner Detail View", "url": "/learners/1"},
    {"name": "Learner Agreement Registration Wizard", "url": "/learners/agreement-wizard"},
    {"name": "Learner Bulk Register", "url": "/learners/bulk-register"},
    {"name": "Bursaries Hub", "url": "/bursaries"},
    {"name": "Trade Tests & ARPL List", "url": "/tradetests"},
    {"name": "Summative Assessment Reports & SOR", "url": "/assessments/summative"},
    {"name": "Summative Assessment Detail", "url": "/assessments/summative/1"},
    {"name": "Workplace Monitoring & Audits", "url": "/monitoring"},
    {"name": "Workplace Monitoring Detail View", "url": "/monitoring/1"},
    {"name": "ETQA Assessors & Moderators", "url": "/etqa"},
    {"name": "Assessment Quality Partners (AQP)", "url": "/etqa/aqp"},
    {"name": "AQP Partner Detail View", "url": "/etqa/aqp/1"},
    {"name": "Register AQP Partner Form", "url": "/etqa/aqp/create"},
    {"name": "Register Assessor Form", "url": "/etqa/create"},
    {"name": "Assessor Detail View", "url": "/etqa/1"},
    {"name": "Accreditation Scope Extensions", "url": "/etqa/scope-extensions"},
    {"name": "Non-SETA Articulations", "url": "/non-seta/verifications"},
    {"name": "Workplace Approvals", "url": "/workplace-approvals"},
    {"name": "Create Workplace Approval", "url": "/workplace-approvals/create"},
    {"name": "Workplace Approval Detail", "url": "/workplace-approvals/1"},
    {"name": "Executive Skills Intelligence & BI", "url": "/reports/bi"},
    {"name": "Committee & MANCO Meetings", "url": "/governance/meetings"},
    {"name": "Committee Meeting Detail View", "url": "/governance/meetings/1"},
    {"name": "Role & Module Delegations", "url": "/governance/delegations"},
    {"name": "Create Role Delegation Form", "url": "/governance/delegations/create"},
    {"name": "Financial Approval Limits (DoA)", "url": "/governance/thresholds"},
    {"name": "System Administration Hub", "url": "/admin"},
    {"name": "Security Roles & Permissions", "url": "/admin/roles"},
    {"name": "Create Security Role Form", "url": "/admin/roles/create"},
    {"name": "Security Role Detail View", "url": "/admin/roles/1"},
    {"name": "System Settings & Features", "url": "/admin/settings"},
    {"name": "System Lookups Hub", "url": "/admin/lookups"},
    {"name": "Lookup Manager Sample Table", "url": "/admin/lookups/DisabilityType"},
    {"name": "Enterprise Document Templates", "url": "/admin/document-templates"},
    {"name": "Document Template Detail", "url": "/admin/document-templates/1"},
    {"name": "Document Security Snapshots", "url": "/admin/document-snapshots"},
    {"name": "Digital Verification Portal", "url": "/verify"},
    {"name": "Developer Data Dictionary", "url": "/developer/schema"},
    {"name": "Developer Compliance HUD", "url": "/developer/compliance-audit"},
    {"name": "Audit Trail Logs", "url": "/audit-logs"},
    {"name": "User Management", "url": "/admin/users"},
    {"name": "Workflow Definitions", "url": "/admin/workflows"},
    {"name": "Workflow Definition Detail", "url": "/admin/workflows/1"},
    {"name": "Rules Engine Registry", "url": "/system-admin/rules"},
    {"name": "Statutory DHET/SETMIS Hub", "url": "/compliance/statutory"},
    {"name": "NAMB Batch Staging Queue", "url": "/artisans/namb-queue"},
    {"name": "Certificate Printing Hub", "url": "/assessments/printing-hub"},
    {"name": "QA Workbench", "url": "/assessments/qa-workbench"},
]

def run_suite():
    print("==================================================")
    print("   NSDMS AUTHENTICATED DEEP UI TEST SUITE")
    print(f"   Target: {BASE_URL} ({len(PAGES_TO_TEST)} Pages)")
    print("==================================================\n")

    results = []
    failed_count = 0
    passed_count = 0

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        # Step 1: Perform SuperAdmin Authentication
        print("[AUTH] Signing in as SuperAdmin (sysadmin@merseta.org.za)...")
        page.goto(f"{BASE_URL}/login", wait_until="networkidle")
        page.fill("input#username", "sysadmin@merseta.org.za")
        page.fill("input#password", "MerSETA@2026!")
        page.click("button[type='submit']")
        page.wait_for_load_state("networkidle")
        time.sleep(1)

        auth_success = "login" not in page.url.lower()
        if not auth_success:
            print("[FATAL] Authentication failed! Unable to proceed with deep UI tests.")
            browser.close()
            return 1
        print(f"[AUTH] Successfully authenticated! Current URL: {page.url}\n")

        for idx, page_info in enumerate(PAGES_TO_TEST, 1):
            name = page_info["name"]
            rel_url = page_info["url"]
            full_url = f"{BASE_URL}{rel_url}"

            console_errors = []
            page_errors = []
            page.on("console", lambda msg: console_errors.append(msg.text) if msg.type == "error" else None)
            page.on("pageerror", lambda err: page_errors.append(str(err)))

            start_time = time.time()
            try:
                response = page.goto(full_url, wait_until="networkidle", timeout=15000)
                status_code = response.status if response else 0
                elapsed_ms = round((time.time() - start_time) * 1000)

                # Wait slightly for Blazor circuits to stabilize
                time.sleep(0.5)

                current_url = page.url
                is_redirected_to_login = "/login" in current_url.lower() and rel_url != "/login"
                is_access_restricted = "Access Restricted" in page.locator("body").inner_text()

                has_blazor_error = False
                try:
                    has_blazor_error = page.locator(".blazor-error-boundary").is_visible()
                except Exception:
                    pass

                body_text = page.locator("body").inner_text()
                has_unhandled_exception = "An unhandled exception occurred" in body_text

                # Check basic page structure
                has_content = len(body_text.strip()) > 20

                is_pass = (
                    status_code < 400
                    and not is_redirected_to_login
                    and not is_access_restricted
                    and not page_errors
                    and not has_blazor_error
                    and not has_unhandled_exception
                    and has_content
                )

                if is_pass:
                    passed_count += 1
                    print(f"[{idx:02d}/{len(PAGES_TO_TEST)}] [PASS] {status_code} | {elapsed_ms}ms | {name} ({rel_url})")
                else:
                    failed_count += 1
                    print(f"[{idx:02d}/{len(PAGES_TO_TEST)}] [FAIL] {status_code} | {elapsed_ms}ms | {name} ({rel_url})")
                    if is_redirected_to_login:
                        print(f"     Redirected to login: {current_url}")
                    if is_access_restricted:
                        print(f"     Access Restricted banner shown")
                    if page_errors:
                        print(f"     Page Errors: {page_errors}")
                    if console_errors:
                        print(f"     Console Errors: {console_errors[:3]}")
                    if has_blazor_error:
                        print(f"     Blazor Error Boundary triggered")
                    if has_unhandled_exception:
                        print(f"     Unhandled Exception text found in body")

                results.append({
                    "name": name,
                    "url": rel_url,
                    "status_code": status_code,
                    "elapsed_ms": elapsed_ms,
                    "passed": is_pass,
                    "is_redirected_to_login": is_redirected_to_login,
                    "is_access_restricted": is_access_restricted,
                    "has_blazor_error": has_blazor_error,
                    "has_unhandled_exception": has_unhandled_exception,
                    "errors": page_errors + console_errors
                })

            except Exception as ex:
                failed_count += 1
                elapsed_ms = round((time.time() - start_time) * 1000)
                print(f"[{idx:02d}/{len(PAGES_TO_TEST)}] [FAIL] EXCEPTION | {elapsed_ms}ms | {name} ({rel_url}): {str(ex)}")
                results.append({
                    "name": name,
                    "url": rel_url,
                    "status_code": 0,
                    "elapsed_ms": elapsed_ms,
                    "passed": False,
                    "is_redirected_to_login": False,
                    "is_access_restricted": False,
                    "has_blazor_error": False,
                    "has_unhandled_exception": True,
                    "errors": [str(ex)]
                })

        context.close()
        browser.close()

    print("\n==================================================")
    print("   AUTHENTICATED DEEP UI TEST SUITE RESULTS")
    print(f"   Total Pages Tested: {len(PAGES_TO_TEST)}")
    print(f"   Passed: {passed_count}")
    print(f"   Failed: {failed_count}")
    print(f"   Success Rate: {round(passed_count / len(PAGES_TO_TEST) * 100, 1)}%")
    print("==================================================")

    # Print summary of failed pages
    if failed_count > 0:
        print("\n--- FAILED PAGES BREAKDOWN ---")
        for r in results:
            if not r["passed"]:
                print(f"- {r['name']} ({r['url']}):")
                if r.get("is_redirected_to_login"):
                    print("  * Unexpected redirect to /login")
                if r.get("is_access_restricted"):
                    print("  * Access Restricted")
                if r.get("has_blazor_error"):
                    print("  * Blazor error boundary triggered")
                if r.get("has_unhandled_exception"):
                    print("  * Unhandled exception")
                if r.get("errors"):
                    print(f"  * Errors: {r['errors']}")

    return 0 if failed_count == 0 else 1

if __name__ == "__main__":
    sys.exit(run_suite())
