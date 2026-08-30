import sys
import io
import time
from playwright.sync_api import sync_playwright

if sys.stdout.encoding != 'utf-8':
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

BASE_URL = "http://localhost:5121"

def test_roles_and_permissions():
    print("==================================================")
    print("   NSDMS ROLES & PERMISSIONS PLAYWRIGHT TEST")
    print(f"   Target: {BASE_URL}")
    print("==================================================\n")

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        # 1. Test Master Roles List (/admin/roles)
        print("[1/3] Testing Roles Registry (/admin/roles)...")
        page.goto(f"{BASE_URL}/admin/roles", wait_until="networkidle", timeout=15000)
        page.wait_for_selector("text=Security Roles & Permissions Matrix", timeout=10000)
        body = page.inner_text("body")
        assert "SuperAdmin" in body or "FinanceManager" in body or "Admin" in body, "Expected roles in table"
        print("  --> Roles Registry loaded with seeded system roles!")

        # 2. Test Role Detail View (/admin/roles/1)
        print("\n[2/3] Testing Role Detail & Permission Matrix (/admin/roles/1)...")
        page.goto(f"{BASE_URL}/admin/roles/1", wait_until="networkidle", timeout=15000)
        page.wait_for_selector("text=General Settings", timeout=10000)
        
        # Switch to Module & Action Permissions Tab
        page.click("text=Module & Action Permissions")
        time.sleep(1)
        body = page.inner_text("body")
        assert "Active Claims:" in body, "Expected Active Claims counter"
        assert "Grants Subsystem" in body, "Expected Grants Subsystem card"
        assert "Finance Subsystem" in body, "Expected Finance Subsystem card"
        print("  --> Module & Action Permission matrix rendered with interactive claims!")

        # 3. Test Create Role Page (/admin/roles/create)
        print("\n[3/3] Testing Create Role Page (/admin/roles/create)...")
        page.goto(f"{BASE_URL}/admin/roles/create", wait_until="networkidle", timeout=15000)
        page.wait_for_selector("text=Create New Security Role", timeout=10000)
        print("  --> Create New Security Role form loaded successfully!")

        browser.close()
        print("\n==================================================")
        print("   [SUCCESS] All Roles & Permissions Tests Passed!")
        print("==================================================")

if __name__ == "__main__":
    test_roles_and_permissions()
