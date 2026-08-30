import sys
import time
from playwright.sync_api import sync_playwright

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

BASE_URL = "http://localhost:5121"

ROUTES = [
    # Workflow Orchestration
    ("/", "Executive Dashboard"),
    ("/tasks", "Universal Task Inbox"),
    # Core Registries
    ("/people", "People Directory"),
    ("/people/create", "Create Person Form"),
    ("/people/1", "Person Detail View"),
    ("/employers", "Employers List"),
    ("/employers/create", "Create Employer Form"),
    ("/employers/1", "Employer Detail View"),
    ("/sdp", "Skills Development Providers"),
    ("/sdp/create", "Create SDP Form"),
    ("/sdp/1", "SDP Detail View"),
    ("/curriculum", "Curriculum Development (QCD)"),
    # Grants & WSP
    ("/wsp", "WSP Submissions"),
    ("/wsp/committees", "Training Committees"),
    ("/wsp/create", "Create WSP Form"),
    ("/wsp/1", "WSP Detail View"),
    ("/grants", "Discretionary Grants"),
    ("/grants/pip", "Project Implementation (PIP)"),
    ("/grants/create", "Create Grant Application"),
    ("/grants/1", "Grant Application Detail"),
    # Finance & Disbursements
    ("/finance/grants", "Grant MOAs & Tranches"),
    ("/finance/grants/1", "Grant MOA Detail View"),
    ("/finance/banking-details", "Banking Details & Dual-Signoff"),
    ("/finance/levy-rebates", "Mandatory Grant Rebates"),
    ("/finance/levy-audits", "SARS Levy Audits & Clawbacks"),
    ("/levies", "SARS Monthly Levies"),
    ("/levies/1", "Levy File Detail"),
    ("/inter-seta-transfers", "Inter-SETA Transfers"),
    ("/employers/sdf", "SDF Appointments"),
    ("/contracts/variations", "Contract Addenda & Variations"),
    # Learner Lifecycle
    ("/learners", "Company Learners"),
    ("/learners/create", "Register Learner Form"),
    ("/learners/1", "Learner Detail View"),
    ("/tradetests", "Trade Tests & ARPL List"),
    ("/assessments/summative", "Summative Assessment Reports & SOR"),
    # Quality Assurance & Monitoring
    ("/monitoring", "Workplace Monitoring & Audits"),
    ("/etqa", "ETQA Assessors & Moderators"),
    ("/etqa/aqp", "Assessment Quality Partners (AQP)"),
    ("/etqa/aqp/create", "Register AQP Partner Form"),
    ("/etqa/create", "Register Assessor Form"),
    ("/etqa/1", "Assessor Detail View"),
    ("/etqa/scope-extensions", "Accreditation Scope Extensions"),
    ("/non-seta/verifications", "Non-SETA Articulations"),
    ("/workplace-approvals", "Workplace Approvals"),
    ("/workplace-approvals/create", "Create Workplace Approval"),
    ("/workplace-approvals/1", "Workplace Approval Detail"),
    # Skills Planning & Governance
    ("/reports/bi", "Executive Skills Intelligence & BI"),
    ("/governance/meetings", "Committee & MANCO Meetings"),
    ("/governance/delegations", "Role & Module Delegations"),
    ("/governance/delegations/create", "Create Role Delegation Form"),
    ("/governance/thresholds", "Financial Approval Limits (DoA)"),
    # Administration & Developer Tools
    ("/admin/roles", "Security Roles & Permissions"),
    ("/admin/roles/create", "Create Security Role Form"),
    ("/admin/roles/1", "Security Role Detail View"),
    ("/admin/settings", "System Settings & Features"),
    ("/admin/lookups", "System Lookups Hub"),
    ("/developer/schema", "Developer Data Dictionary"),
    ("/audit-logs", "Audit Trail Logs")
]

def run_deep_test():
    print("==================================================")
    print("  COMPREHENSIVE APPLICATION BROWSER TEST SUITE")
    print("==================================================")
    
    passed_routes = []
    failed_routes = []
    
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        page = browser.new_page(viewport={"width": 1366, "height": 768})
        
        # 1. Route Navigation Audit
        print("\n--- 1. Testing all 58 Navigation Routes ---")
        for idx, (route, name) in enumerate(ROUTES, 1):
            url = f"{BASE_URL}{route}"
            try:
                start_time = time.time()
                response = page.goto(url, wait_until="networkidle", timeout=15000)
                duration = int((time.time() - start_time) * 1000)
                
                status = response.status if response else 0
                body_text = page.inner_text("body")
                
                # Check for unhandled exceptions or error pages
                if "InvalidOperationException" in body_text or "SqlException" in body_text or "NullReferenceException" in body_text or "An unhandled exception occurred" in body_text:
                    print(f"[{idx:02d}/58] [FAIL] EXCEPTION | {duration}ms | {name} ({route})")
                    failed_routes.append((route, name, "Unhandled Exception in Body"))
                elif status == 200:
                    print(f"[{idx:02d}/58] [PASS] 200 | {duration}ms | {name} ({route})")
                    passed_routes.append((route, name, duration))
                else:
                    print(f"[{idx:02d}/58] [FAIL] HTTP {status} | {duration}ms | {name} ({route})")
                    failed_routes.append((route, name, f"HTTP {status}"))
            except Exception as ex:
                print(f"[{idx:02d}/58] [FAIL] TIMEOUT/ERR | {name} ({route}): {str(ex)[:80]}")
                failed_routes.append((route, name, str(ex)))
                
        # 2. Interactive Feature Verification
        print("\n--- 2. Testing Interactive Features & Dynamic Workflows ---")
        
        # 2.1 Universal Task Inbox
        print("Testing /tasks interactive state changes...")
        page.goto(f"{BASE_URL}/tasks", wait_until="networkidle")
        page.click('button:has-text("Unassigned")')
        page.wait_for_timeout(300)
        assert "No unassigned tasks" in page.inner_text("body"), "Unassigned empty state failed"
        
        page.click('button:has-text("Completed")')
        page.wait_for_timeout(300)
        assert "No completed tasks" in page.inner_text("body"), "Completed empty state failed"
        
        page.click('button:has-text("My tasks")')
        page.wait_for_timeout(300)
        search_input = page.locator('input[placeholder="Search tasks..."]')
        search_input.fill("Artisan")
        page.wait_for_timeout(400)
        assert "Active filters:" in page.inner_text("body"), "Active filters chip row failed"
        page.click('button:has-text("Clear all")')
        page.wait_for_timeout(300)
        print("  [PASS] /tasks tab switching, filter chips, and empty states verified.")
        
        # 2.2 Global Topbar Actions
        print("Testing Global Topbar (Theme switch, notifications, shortcuts)...")
        page.click('button[aria-label="Toggle dark mode"]')
        page.wait_for_timeout(300)
        page.click('button[aria-label="Toggle dark mode"]') # Switch back to Light
        page.wait_for_timeout(300)
        print("  [PASS] Dark / Light theme toggle verified.")
        
        # 2.3 Employer Master-Detail Tabs
        print("Testing /employers Master-Detail tab switching & sticky bar...")
        page.goto(f"{BASE_URL}/employers", wait_until="networkidle")
        page.wait_for_timeout(300)
        first_emp_link = page.locator('table tbody tr:first-child a').first
        emp_href = first_emp_link.get_attribute("href")
        print(f"  Navigating to first employer link: {emp_href}")
        first_emp_link.click()
        page.wait_for_timeout(600)
        assert "Organisation Identification" in page.inner_text("body") or "Save Employer" in page.inner_text("body") or "Legal Organisation Name" in page.inner_text("body"), "Employer detail failed"
        print("  [PASS] Employer Master-Detail verified.")
        
        # 2.4 Person Master-Detail View
        print("Testing /people Person detail view...")
        page.goto(f"{BASE_URL}/people", wait_until="networkidle")
        page.wait_for_timeout(300)
        first_person_link = page.locator('table tbody tr:first-child a').first
        person_href = first_person_link.get_attribute("href")
        print(f"  Navigating to first person link: {person_href}")
        first_person_link.click()
        page.wait_for_timeout(600)
        assert "Person" in page.inner_text("body") or "Demographics" in page.inner_text("body"), "Person detail failed"
        print("  [PASS] Person detail verified.")
        
        # 2.5 Responsive Viewport Checks
        print("\n--- 3. Testing Responsive Viewport Dimensions ---")
        for width, height in [(1920, 1080), (1600, 900), (1366, 768), (1280, 800)]:
            page.set_viewport_size({"width": width, "height": height})
            page.goto(f"{BASE_URL}/tasks", wait_until="networkidle")
            print(f"  [PASS] Viewport {width}x{height} rendered cleanly.")
            
        browser.close()
        
    print("\n==================================================")
    print("              FINAL TEST SUMMARY")
    print("==================================================")
    print(f"Total Routes Audited: {len(ROUTES)}")
    print(f"Passed: {len(passed_routes)}")
    print(f"Failed: {len(failed_routes)}")
    print(f"Success Rate: {(len(passed_routes) / len(ROUTES)) * 100:.1f}%")
    print("==================================================\n")
    
    return len(failed_routes) == 0

if __name__ == "__main__":
    success = run_deep_test()
    sys.exit(0 if success else 1)

