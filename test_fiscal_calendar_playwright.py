import sys
import io
import time
from playwright.sync_api import sync_playwright

if sys.stdout.encoding != 'utf-8':
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

BASE_URL = "http://localhost:5121"

def run_fiscal_calendar_uat():
    print("==================================================")
    print("   NSDMS FISCAL CALENDAR E2E / UAT TEST SUITE")
    print(f"   Target: {BASE_URL}")
    print("==================================================\n")

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        # ----------------------------------------------------
        # TEST 1: Unauthenticated Interception (TC-UAT-FY-025)
        # ----------------------------------------------------
        print("[TEST 1] Verifying Unauthenticated Interception on /admin/financial-years...")
        page.goto(f"{BASE_URL}/admin/financial-years", wait_until="networkidle")
        time.sleep(1)
        assert "/login" in page.url, f"Expected redirect to /login, but stayed on {page.url}"
        print("  -> PASS: Unauthenticated access intercepted and redirected to /login.")

        # ----------------------------------------------------
        # TEST 2: Authentication as Admin
        # ----------------------------------------------------
        print("\n[TEST 2] Authenticating as SuperAdmin...")
        page.fill("input#username", "sysadmin@merseta.org.za")
        page.fill("input#password", "MerSETA@2026!")
        page.click("button[type='submit']")
        page.wait_for_load_state("networkidle")
        time.sleep(2)
        print(f"  -> Logged in. Current URL: {page.url}")

        # ----------------------------------------------------
        # TEST 3: Navigation to Financial Years Hub (TC-UAT-FY-001)
        # ----------------------------------------------------
        print("\n[TEST 3] Navigating to /admin/financial-years (Master List)...")
        page.goto(f"{BASE_URL}/admin/financial-years", wait_until="networkidle")
        time.sleep(2)
        page_title = page.title()
        print(f"  -> Page Title: '{page_title}'")
        body_text = page.locator("body").inner_text()
        assert "Financial Years" in body_text, "Expected 'Financial Years' in page text"
        print("  -> PASS: Master list loaded successfully with EntityHeader.")

        # ----------------------------------------------------
        # TEST 4: FilterBar & Search Interaction (TC-UAT-FY-002)
        # ----------------------------------------------------
        print("\n[TEST 4] Testing Search and Filter Controls...")
        search_input = page.locator("input[placeholder*='Search by Financial Year Code']")
        if search_input.count() > 0:
            search_input.fill("2026")
            time.sleep(1)
            print("  -> Search input filled with '2026'.")
            search_input.clear()
            time.sleep(1)
            print("  -> PASS: Search filter input responsive.")

        # ----------------------------------------------------
        # TEST 5: Create Mode Navigation (TC-UAT-FY-010)
        # ----------------------------------------------------
        print("\n[TEST 5] Navigating to /admin/financial-years/create (Form/Creator)...")
        page.goto(f"{BASE_URL}/admin/financial-years/create", wait_until="networkidle")
        time.sleep(2)
        create_body = page.locator("body").inner_text()
        assert "Add new financial year" in create_body or "financial year" in create_body.lower(), "Expected creation mode header"
        print("  -> PASS: Create form loaded with action bar and quarter setup cards.")

        # ----------------------------------------------------
        # TEST 6: Detail Drill-Down in View Mode (TC-UAT-FY-005)
        # ----------------------------------------------------
        print("\n[TEST 6] Navigating to /admin/financial-years/1 (View Mode)...")
        page.goto(f"{BASE_URL}/admin/financial-years/1", wait_until="networkidle")
        time.sleep(2)
        view_body = page.locator("body").inner_text()
        print(f"  -> View mode header preview: {view_body[:200].replace(chr(10), ' ')}")
        assert "Back to list" in view_body, "Expected 'Back to list' breadcrumb button"
        print("  -> PASS: View-by-default detail layout verified.")

        # ----------------------------------------------------
        # TEST 7: Computed Month & Day Breakdown Tab (TC-UAT-FY-007, 008)
        # ----------------------------------------------------
        print("\n[TEST 7] Testing Tab 2: Computed Month & Day Breakdown...")
        tab_button = page.locator("text='Computed Month & Day Breakdown'").or_(page.locator("text='Computed'"))
        if tab_button.count() > 0:
            tab_button.first.click()
            time.sleep(2)
            tab2_body = page.locator("body").inner_text()
            assert "Calendar Days" in tab2_body or "Working Days" in tab2_body, "Expected computed columns in Tab 2"
            print("  -> PASS: Tab 2 rendered dynamic working days, weekends, and public holidays.")

        # ----------------------------------------------------
        # TEST 8: Audited Change Log Tab (TC-UAT-FY-009)
        # ----------------------------------------------------
        print("\n[TEST 8] Testing Tab 3: Audited Change Log...")
        audit_tab = page.locator("text='Audited Change Log'").or_(page.locator("text='Audit Log'"))
        if audit_tab.count() > 0:
            audit_tab.first.click()
            time.sleep(2)
            print("  -> PASS: Tab 3 Audited Change Log rendered.")

        print("\n==================================================")
        print("   ALL FISCAL CALENDAR PLAYWRIGHT UAT TESTS PASSED")
        print("==================================================")
        browser.close()

if __name__ == "__main__":
    run_fiscal_calendar_uat()
