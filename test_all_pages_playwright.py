import sys
import io
import time
from playwright.sync_api import sync_playwright

if sys.stdout.encoding != 'utf-8':
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

BASE_URL = "http://localhost:5121"

PAGES_TO_TEST = [
    {"name": "Executive Dashboard", "url": "/"},
    {"name": "Universal Task Inbox", "url": "/tasks"},
    {"name": "People Directory", "url": "/people"},
    {"name": "Create Person Form", "url": "/people/create"},
    {"name": "Person Detail View", "url": "/people/1"},
    {"name": "Employers List", "url": "/employers"},
    {"name": "Create Employer Form", "url": "/employers/create"},
    {"name": "Employer Detail View", "url": "/employers/1"},
    {"name": "Skills Development Providers", "url": "/sdp"},
    {"name": "Create SDP Form", "url": "/sdp/create"},
    {"name": "SDP Detail View", "url": "/sdp/1"},
    {"name": "Curriculum Development (QCD)", "url": "/curriculum"},
    {"name": "Curriculum Detail View", "url": "/curriculum/1"},
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
    {"name": "Company Learners", "url": "/learners"},
    {"name": "Register Learner Form", "url": "/learners/create"},
    {"name": "Learner Detail View", "url": "/learners/1"},
    {"name": "Trade Tests & ARPL List", "url": "/tradetests"},
    {"name": "Summative Assessment Reports & SOR", "url": "/assessments/summative"},
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
    {"name": "Enterprise Document Templates", "url": "/admin/document-templates"},
    {"name": "Document Security Snapshots", "url": "/admin/document-snapshots"},
    {"name": "Digital Verification Portal", "url": "/verify"},
    {"name": "Developer Data Dictionary", "url": "/developer/schema"},
    {"name": "Developer Compliance HUD", "url": "/developer/compliance-audit"},
    {"name": "Audit Trail Logs", "url": "/audit-logs"},
]

def run_suite():
    print("==================================================")
    print("   NSDMS COMPREHENSIVE PLAYWRIGHT TEST SUITE")
    print(f"   Target: {BASE_URL} ({len(PAGES_TO_TEST)} Pages)")
    print("==================================================\n")

    import urllib.request
    print("Waiting for server to be responsive at " + BASE_URL + "...")
    server_ready = False
    for attempt in range(25):
        try:
            with urllib.request.urlopen(BASE_URL, timeout=2) as response:
                if response.status in (200, 302):
                    server_ready = True
                    print(f"Server is online and responsive! (Attempt {attempt+1})\n")
                    break
        except Exception:
            time.sleep(1)

    if not server_ready:
        print("Warning: Could not confirm server health via HTTP, proceeding with test suite...\n")

    results = []
    failed_count = 0
    passed_count = 0

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

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
                title = page.title()
                elapsed_ms = round((time.time() - start_time) * 1000)

                # Check for critical errors or blazor crashes
                has_blazor_error = page.locator(".blazor-error-boundary").is_visible()
                body_text = page.locator("body").inner_text()
                has_unhandled_exception = "An unhandled exception occurred" in body_text

                # Check basic page structure
                has_content = len(body_text.strip()) > 20

                is_pass = (
                    status_code < 400
                    and not page_errors
                    and not has_blazor_error
                    and not has_unhandled_exception
                    and has_content
                )

                if is_pass:
                    passed_count += 1
                    status_str = "PASS"
                    print(f"[{idx:02d}/{len(PAGES_TO_TEST)}] [PASS] {status_code} | {elapsed_ms}ms | {name} ({rel_url})")
                else:
                    failed_count += 1
                    status_str = "FAIL"
                    print(f"[{idx:02d}/{len(PAGES_TO_TEST)}] [FAIL] {status_code} | {elapsed_ms}ms | {name} ({rel_url})")
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
                    "errors": [str(ex)]
                })

        context.close()
        browser.close()

    print("\n==================================================")
    print("   PLAYWRIGHT SUITE RESULTS")
    print(f"   Total Pages Tested: {len(PAGES_TO_TEST)}")
    print(f"   Passed: {passed_count}")
    print(f"   Failed: {failed_count}")
    print(f"   Success Rate: {round(passed_count / len(PAGES_TO_TEST) * 100, 1)}%")
    print("==================================================")

    return 0 if failed_count == 0 else 1

if __name__ == "__main__":
    sys.exit(run_suite())
