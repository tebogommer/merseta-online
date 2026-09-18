import sys
import time
from playwright.sync_api import sync_playwright

BASE_URL = "http://localhost:5121"

def run_test():
    print("==================================================")
    print("   NSDMS 360° COMPLIANCE RADAR & COMMITTEE PARITY TEST")
    print(f"   Target: {BASE_URL}/employers/3102")
    print("==================================================")

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True, args=["--disable-dev-shm-usage", "--no-sandbox"])
        context = browser.new_context(viewport={"width": 1600, "height": 1000})
        page = context.new_page()

        # Auth
        print("[AUTH] Logging in as SuperAdmin...")
        page.goto(f"{BASE_URL}/login", wait_until="networkidle")
        page.fill("input#username", "sysadmin@merseta.org.za")
        page.fill("input#password", "MerSETA@2026!")
        page.click("button[type='submit']", no_wait_after=True)
        page.wait_for_timeout(2000)

        # Navigate to Employer 3102
        print("\n[TEST 1] Loading Employer Detail (/employers/3102)...")
        page.goto(f"{BASE_URL}/employers/3102", wait_until="networkidle")
        page.wait_for_timeout(3000)

        # Check Radar Widget
        print("[TEST 2] Verifying 360° Statutory Compliance Health Radar widget...")
        page.wait_for_selector("text=360° Statutory Compliance Health Radar", timeout=15000)
        print("  --> Radar header found!")

        # Verify Overall Score and Grade
        page.wait_for_selector("text=Overall Health Score", timeout=10000)
        page.wait_for_selector("text=Compliant Pillars", timeout=10000)
        page.wait_for_selector("text=Discretionary Grants", timeout=10000)
        page.wait_for_selector("text=Learner Registrations", timeout=10000)
        print("  --> Executive KPI ribbon verified!")

        # Verify 7 Pillars presence
        pillars = [
            "WSP / ATR Statutory Submission",
            "SARS Levy Contributions",
            "Training Committee 50/50 Parity",
            "Governance & Beneficial Ownership",
            "Workplace Approvals & Mentors",
            "Conflict of Interest Standing",
            "Banking Details Security"
        ]
        for pillar in pillars:
            page.wait_for_selector(f"text={pillar}", timeout=10000)
            print(f"  --> Pillar '{pillar}' verified!")

        # Take screenshot of Radar Widget
        screenshot1 = "employer_compliance_radar_360.png"
        page.screenshot(path=screenshot1, full_page=False)
        print(f"  --> Screenshot saved: {screenshot1}")

        # Test Drill-Down: Click Manage on Training Committee pillar or switch tab
        print("\n[TEST 3] Testing Drill-Down to Contacts & Committee tab...")
        page.click("text=Contacts & Committee")
        page.wait_for_timeout(2000)

        # Verify Training Committee 50/50 Parity Meter
        print("[TEST 4] Verifying Statutory Training Committee 50/50 Parity Meter...")
        page.wait_for_selector("text=Statutory Training Committee 50/50 Parity Meter", timeout=10000)
        page.wait_for_selector("text=Management:", timeout=10000)
        page.wait_for_selector("text=Labour/Union:", timeout=10000)
        page.wait_for_selector("text=Constitutional Quorum", timeout=10000)
        print("  --> Training Committee 50/50 Parity Meter verified!")

        # Take screenshot of Committee Tab with Parity Meter
        screenshot2 = "employer_committee_parity_meter.png"
        page.screenshot(path=screenshot2, full_page=False)
        print(f"  --> Screenshot saved: {screenshot2}")

        print("\n==================================================")
        print("   ALL 360° COMPLIANCE RADAR TESTS PASSED!")
        print("==================================================")
        browser.close()

if __name__ == "__main__":
    run_test()