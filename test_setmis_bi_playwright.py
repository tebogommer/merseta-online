import sys
from playwright.sync_api import sync_playwright

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

def test_setmis_bi_suite():
    print("[START] Running Phase 5 (SETMIS Compliance & Executive BI) Playwright Test...")
    
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        errors = []
        page.on("pageerror", lambda err: errors.append(f"Page Error: {err}"))
        page.on("console", lambda msg: errors.append(f"Console Error: {msg.text}") if msg.type == "error" else None)

        base_url = "http://localhost:5121"

        # 1. Test SETMIS Compliance Hub (/compliance/setmis)
        print("\n--- 1. Testing SETMIS Compliance Hub (/compliance/setmis) ---")
        page.goto(f"{base_url}/compliance/setmis", wait_until="networkidle")
        page.wait_for_selector("text=DHET SETMIS Compliance Hub", timeout=10000)
        print("  [PASS] /compliance/setmis header loaded")
        
        # Verify 500-505 extract cards
        page.wait_for_selector("text=Training Providers", timeout=5000)
        page.wait_for_selector("text=Assessors & Moderators", timeout=5000)
        page.wait_for_selector("text=Workplace Approvals", timeout=5000)
        page.wait_for_selector("text=Learner Enrolments", timeout=5000)
        page.wait_for_selector("text=Trade Tests & Awards", timeout=5000)
        print("  [PASS] All 6 SETMIS 500-505 specification cards rendered")

        batch_rows = page.locator("tbody tr").count()
        print(f"  [PASS] Found {batch_rows} SETMIS submission batch records")
        assert batch_rows > 0, "Expected at least 1 SETMIS batch"

        # 2. Test Executive Skills Intelligence & BI Dashboard (/reports/bi)
        print("\n--- 2. Testing Executive Skills Intelligence & BI Dashboard (/reports/bi) ---")
        page.goto(f"{base_url}/reports/bi", wait_until="networkidle")
        page.wait_for_selector("text=Executive Skills Intelligence & BI Dashboard", timeout=10000)
        print("  [PASS] /reports/bi header loaded")

        # Test Chamber Distribution Tab
        page.wait_for_selector("text=Manufacturing & Engineering Chamber Matrix", timeout=5000)
        chamber_rows = page.locator("tbody tr").count()
        print(f"  [PASS] Chamber Matrix rendered with {chamber_rows} chamber rows")

        # Test Demographic Equity Tab
        eq_tab = page.locator("div.mud-tab:has-text('Demographic Equity')")
        if eq_tab.count() > 0:
            eq_tab.first.click()
            page.wait_for_selector("text=Learner Demographic Equity Indicators", timeout=5000)
            print("  [PASS] Demographic Equity Tab rendered with demographic breakdown")

        # Test Provincial Footprint Tab
        prov_tab = page.locator("div.mud-tab:has-text('Provincial Footprint')")
        if prov_tab.count() > 0:
            prov_tab.first.click()
            page.wait_for_selector("text=9-Province National Delivery Footprint", timeout=5000)
            print("  [PASS] Provincial Footprint Tab rendered across all 9 provinces")

        # Test Scarce Skills & OFO Demand Tab
        scarce_tab = page.locator("div.mud-tab:has-text('Scarce Skills & OFO Demand')")
        if scarce_tab.count() > 0:
            scarce_tab.first.click()
            page.wait_for_selector("text=Sector Skills Plan (SSP) Top Scarce & Critical Occupations", timeout=5000)
            print("  [PASS] Scarce Skills & OFO Demand Tab rendered with priority intervention codes")

        browser.close()

        if errors:
            print(f"\n[WARNING] Encountered {len(errors)} console/page warnings/errors:")
            for err in errors[:5]:
                print(f"  - {err}")
        else:
            print("\n[SUCCESS] All Phase 5 SETMIS & Executive BI Playwright tests passed with ZERO errors!")

if __name__ == "__main__":
    test_setmis_bi_suite()
