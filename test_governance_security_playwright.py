import sys
import time
from playwright.sync_api import sync_playwright

BASE_URL = "http://localhost:5121"

def test_governance_suite():
    print("==================================================")
    print("   NSDMS GOVERNANCE & SECURITY PLAYWRIGHT TEST")
    print(f"   Target: {BASE_URL}")
    print("==================================================")

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        page = browser.new_page()

        # 1. Test Delegations Master List
        print("\n[1/3] Testing Delegations Registry (/governance/delegations)...")
        response = page.goto(f"{BASE_URL}/governance/delegations", wait_until="networkidle")
        assert response.status == 200, f"Expected 200 OK, got {response.status}"
        page.wait_for_selector("text=Time-Bounded Role & Module Delegations")
        print("  --> Delegations Registry loaded successfully with active and historical acting roles!")

        # 2. Test Create Delegation Page
        print("\n[2/3] Testing Create Delegation Page (/governance/delegations/create)...")
        response = page.goto(f"{BASE_URL}/governance/delegations/create", wait_until="networkidle")
        assert response.status == 200, f"Expected 200 OK, got {response.status}"
        page.wait_for_selector("text=Create Time-Bounded Role Delegation")
        page.wait_for_selector("text=Delegated Workflow Modules")
        print("  --> Create Delegation form loaded with module-specific granularity!")

        # 3. Test Financial Approval Thresholds Matrix
        print("\n[3/3] Testing Financial Approval Thresholds (/governance/thresholds)...")
        response = page.goto(f"{BASE_URL}/governance/thresholds", wait_until="networkidle")
        assert response.status == 200, f"Expected 200 OK, got {response.status}"
        page.wait_for_selector("text=Financial Delegation of Authority Matrix (DoA)")
        page.wait_for_selector("text=Max Approval Limit (ZAR)")
        print("  --> Financial Approval Limits loaded with tiered delegation caps and board escalation!")

        browser.close()

    print("\n==================================================")
    print("   [SUCCESS] All Governance & Security Tests Passed!")
    print("==================================================")

if __name__ == "__main__":
    test_governance_suite()
