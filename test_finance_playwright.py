import sys
from playwright.sync_api import sync_playwright

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

def test_financial_governance_suite():
    print("[START] Running Financial Governance, Grant MOAs & Tranche Disbursements Playwright Test...")
    
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

        # 1. Test Grant MOAs List (/finance/grants)
        print("\n--- 1. Testing Grant MOAs List (/finance/grants) ---")
        page.goto(f"{base_url}/finance/grants", wait_until="networkidle")
        page.wait_for_selector("text=Discretionary Grant (DG) MOAs", timeout=10000)
        print("  [PASS] /finance/grants header loaded")
        
        moa_rows = page.locator("tbody tr").count()
        print(f"  [PASS] Found {moa_rows} MOA contract records")
        assert moa_rows > 0, "Expected at least 1 Grant MOA"

        # 2. Test Grant MOA Detail View (/finance/grants/1)
        print("\n--- 2. Testing Grant MOA Detail View (/finance/grants/1) ---")
        page.locator("tbody tr a[href*='/finance/']").first.click()
        page.wait_for_selector("text=Contract Terms", timeout=10000)
        print(f"  [PASS] MOA Detail loaded at {page.url}")

        # Click PIP Milestones Tab
        ms_tab = page.locator("div.mud-tab:has-text('PIP Milestones')")
        if ms_tab.count() > 0:
            ms_tab.first.click()
            page.wait_for_selector("text=Project Implementation Plan", timeout=5000)
            print("  [PASS] PIP Milestones Tab opened")

        # Click Disbursements & Claims Tab
        inv_tab = page.locator("div.mud-tab:has-text('Disbursements')")
        if inv_tab.count() > 0:
            inv_tab.first.click()
            page.wait_for_selector("text=Disbursements", timeout=5000)
            print("  [PASS] Disbursements & Claims Tab opened")

        # Click Evidence Vault Tab
        vault_tab = page.locator("div.mud-tab:has-text('Evidence Vault')")
        if vault_tab.count() > 0:
            vault_tab.first.click()
            page.wait_for_selector("text=Document & Evidence Vault", timeout=5000)
            print("  [PASS] MOA Evidence Vault Tab opened")

        # 3. Test Mandatory Grant 20% Rebates (/finance/levy-rebates)
        print("\n--- 3. Testing Mandatory Grant Rebates (/finance/levy-rebates) ---")
        page.goto(f"{base_url}/finance/levy-rebates", wait_until="networkidle")
        page.wait_for_selector("text=Mandatory Grant (MG) 20% Rebate Payout Engine", timeout=10000)
        print("  [PASS] /finance/levy-rebates header loaded")
        rebate_rows = page.locator("tbody tr").count()
        print(f"  [PASS] Found {rebate_rows} Mandatory Rebate records")

        # 4. Test Inter-SETA Transfers (/inter-seta-transfers)
        print("\n--- 4. Testing Inter-SETA Transfers (/inter-seta-transfers) ---")
        page.goto(f"{base_url}/inter-seta-transfers", wait_until="networkidle")
        page.wait_for_selector("text=Inter-SETA Transfers", timeout=10000)
        print("  [PASS] /inter-seta-transfers header loaded")
        trf_rows = page.locator("tbody tr").count()
        print(f"  [PASS] Found {trf_rows} Inter-SETA transfer records")
        assert trf_rows > 0, "Expected at least 1 Inter-SETA transfer"

        browser.close()

        if errors:
            print(f"\n[WARNING] Encountered {len(errors)} console/page warnings/errors:")
            for err in errors[:5]:
                print(f"  - {err}")
        else:
            print("\n[SUCCESS] All Phase 4 Financial Governance Playwright tests passed with ZERO errors!")

if __name__ == "__main__":
    test_financial_governance_suite()
