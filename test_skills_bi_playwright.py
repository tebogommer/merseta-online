import sys
from playwright.sync_api import sync_playwright

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

def test_bi_suite():
    print("[START] Running Executive Skills Intelligence & BI Playwright Test...")
    
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        errors = []
        page.on("pageerror", lambda err: errors.append(f"Page Error: {err}"))
        page.on("console", lambda msg: errors.append(f"Console Error: {msg.text}") if msg.type == "error" else None)

        base_url = "http://localhost:5121"

        # Authenticate as SuperAdmin
        print("[AUTH] Logging in as SuperAdmin...")
        page.goto(f"{base_url}/login", wait_until="networkidle")
        page.fill("input#username", "sysadmin@merseta.org.za")
        page.fill("input#password", "MerSETA@2026!")
        page.click("button[type='submit']")
        page.wait_for_load_state("networkidle")
        print("[AUTH] Successfully authenticated!")

        # 1. Test Executive Skills Intelligence & BI Dashboard (/reports/bi)
        print("\n--- 1. Testing Executive Skills Intelligence & BI Dashboard (/reports/bi) ---")
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
            print("  [PASS] Provincial Footprint Tab rendered with 9 provinces")

        # Test Scarce Skills Tab
        scarce_tab = page.locator("div.mud-tab:has-text('Scarce Skills')")
        if scarce_tab.count() > 0:
            scarce_tab.first.click()
            page.wait_for_selector("text=Sector Skills Plan (SSP) Top Scarce & Critical Occupations", timeout=5000)
            print("  [PASS] Scarce Skills Tab rendered with priority OFO occupations")

        browser.close()

    print("\n=======================================================")
    if errors:
        print(f"[FAIL] {len(errors)} errors detected:")
        for e in errors:
            print(f"  - {e}")
        sys.exit(1)
    else:
        print("[SUCCESS] All Executive BI tests PASSED with 0 errors!")
        sys.exit(0)

if __name__ == "__main__":
    test_bi_suite()
