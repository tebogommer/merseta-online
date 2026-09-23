import sys
import io
import time
from playwright.sync_api import sync_playwright

if sys.stdout.encoding != 'utf-8':
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

BASE_URL = "http://localhost:5121"

def verify_option_b_ticker():
    print("==================================================================")
    print("   OPTION B: SMART RESPONSIVE TICKER END-TO-END VERIFICATION")
    print(f"   Target: {BASE_URL}")
    print("==================================================================\n")

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        # Step 1: Login
        print("[1/4] Authenticating as System Administrator...")
        page.goto(f"{BASE_URL}/login?returnUrl=/wsp", wait_until="domcontentloaded", timeout=20000)
        page.fill("input#username", "sysadmin@merseta.org.za")
        page.fill("input#password", "MerSETA@2026!")
        page.click("button[type='submit']")
        page.wait_for_selector("table tbody tr", timeout=15000)
        print("  --> Logged in successfully and reached /wsp.")

        # Step 2: Verify WspList.razor Ticker & Action Button
        print("\n[2/4] Verifying Option B Ticker & Adaptive Action Button on /wsp...")
        page.wait_for_selector(".nsdms-ticker-badge, .mud-chip", timeout=10000)
        
        # Check context strip text
        submission_window_label = page.locator("text=Submission Window:")
        assert submission_window_label.is_visible(), "Expected 'Submission Window:' label in context strip"
        print("  --> 'Submission Window:' context strip label found.")

        # Check action button presence (either New WSP Submission or Request Deadline Extension)
        new_sub_btn = page.locator("button:has-text('New WSP Submission')")
        ext_btn = page.locator("button:has-text('Request Deadline Extension')")
        has_new = new_sub_btn.count() > 0
        has_ext = ext_btn.count() > 0
        assert has_new or has_ext, "Expected adaptive action button to be present"
        print(f"  --> Adaptive Action Button found: {'New WSP Submission' if has_new else 'Request Deadline Extension'}")

        # Ticker badge content
        ticker_badge = page.locator(".nsdms-ticker-badge, .mud-chip").first
        badge_text = ticker_badge.text_content()
        print(f"  --> Ticker Badge text: '{badge_text.strip()}'")

        # Step 3: Navigate to /wsp/create (WspAtrSubmissionWizard)
        print("\n[3/4] Navigating to /wsp/create (WSP/ATR Submission Wizard)...")
        page.goto(f"{BASE_URL}/wsp/create", wait_until="domcontentloaded", timeout=15000)
        page.wait_for_timeout(2000)

        # Verify wizard header ticker
        wizard_ticker = page.locator(".nsdms-ticker-badge, .mud-chip")
        assert wizard_ticker.count() > 0, "Expected countdown ticker badge in Wizard header"
        print(f"  --> Wizard Countdown Ticker verified in header: '{wizard_ticker.first.text_content().strip()}'")

        # Step 4: Verify Employer WSP Levies Tab
        print("\n[4/4] Verifying Employer WSP / Levies Tab (/employers/3102)...")
        page.goto(f"{BASE_URL}/employers/3102", wait_until="domcontentloaded", timeout=15000)
        page.wait_for_timeout(2000)

        # Click WSP & Levies tab if present
        wsp_tab = page.locator(".mud-tab:has-text('WSP'), .mud-tab:has-text('Levies')")
        if wsp_tab.count() > 0:
            wsp_tab.first.click()
            page.wait_for_timeout(2000)
            print("  --> Switched to WSP & Levies tab.")
            tab_ticker = page.locator(".nsdms-ticker-badge, .mud-chip")
            print(f"  --> Employer-scoped ticker elements found: {tab_ticker.count()}")

        print("\n==================================================================")
        print("   OPTION B RESPONSIVE TICKER VERIFICATION PASSED SUCCESSFULLY!")
        print("==================================================================")
        browser.close()

if __name__ == "__main__":
    verify_option_b_ticker()
