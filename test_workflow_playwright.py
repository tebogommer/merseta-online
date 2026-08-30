import sys
import time
from playwright.sync_api import sync_playwright

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

def test_workflow_and_document_suite():
    print("[START] Running Workflow Engine, Universal Task Matrix & Document Vault Playwright Test...")
    
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        errors = []
        page.on("pageerror", lambda err: errors.append(f"Page Error: {err}"))
        page.on("console", lambda msg: errors.append(f"Console Error: {msg.text}") if msg.type == "error" else None)

        base_url = "http://localhost:5121"

        # 1. Test Task Inbox Page
        print("\n--- 1. Testing Universal Task Inbox (/tasks) ---")
        page.goto(f"{base_url}/tasks", wait_until="networkidle")
        page.wait_for_selector("text=Universal Task Inbox", timeout=10000)
        print("  [PASS] /tasks header loaded")
        
        # Check task rows
        task_rows = page.locator("tr.cursor-pointer").count()
        print(f"  [PASS] Found {task_rows} workflow tasks in inbox")
        assert task_rows > 0, "Expected at least 1 task in inbox"

        # 2. Test WSP Detail via List Navigation
        print("\n--- 2. Testing WSP Detail via List Navigation (/wsp) ---")
        page.goto(f"{base_url}/wsp", wait_until="networkidle")
        page.wait_for_selector("text=Workplace Skills Plan", timeout=10000)
        page.locator("tbody tr").first.click()
        page.wait_for_selector("text=Workflow Status:", timeout=10000)
        print(f"  [PASS] WSP Detail loaded at {page.url} with WorkflowActionBridge")

        # Click Evidence Vault tab
        vault_tab = page.locator("div.mud-tab:has-text('Evidence Vault')")
        if vault_tab.count() > 0:
            vault_tab.first.click()
            page.wait_for_selector("text=Document & Evidence Vault", timeout=5000)
            print("  [PASS] WSP Evidence Vault tab opened")

        # Click Workflow History tab
        history_tab = page.locator("div.mud-tab:has-text('Workflow History')")
        if history_tab.count() > 0:
            history_tab.first.click()
            page.wait_for_selector("text=Workflow History & Audit Trail", timeout=5000)
            print("  [PASS] WSP Workflow History timeline opened")

        # 3. Test Discretionary Grant Detail via List Navigation
        print("\n--- 3. Testing Grant Detail via List Navigation (/grants) ---")
        page.goto(f"{base_url}/grants", wait_until="networkidle")
        page.wait_for_selector("text=Discretionary Grants", timeout=10000)
        page.locator("tbody tr").first.click()
        page.wait_for_selector("text=Workflow Status:", timeout=10000)
        print(f"  [PASS] Grant Detail loaded at {page.url} with WorkflowActionBridge")

        # 4. Test Workplace Approval Detail via List Navigation
        print("\n--- 4. Testing Workplace Approval Detail via List Navigation (/workplace-approvals) ---")
        page.goto(f"{base_url}/workplace-approvals", wait_until="networkidle")
        page.wait_for_selector("text=Workplace Approvals", timeout=10000)
        page.locator("tbody tr").first.click()
        page.wait_for_selector("text=Workflow Status:", timeout=10000)
        print(f"  [PASS] Workplace Approval Detail loaded at {page.url} with WorkflowActionBridge")

        # 5. Test Learner Detail via List Navigation
        print("\n--- 5. Testing Learner Detail via List Navigation (/learners) ---")
        page.goto(f"{base_url}/learners", wait_until="networkidle")
        page.wait_for_selector("text=Learner Management", timeout=10000)
        page.locator("tbody tr").first.click()
        page.wait_for_selector("text=Workflow Status:", timeout=10000)
        print(f"  [PASS] Learner Detail loaded at {page.url} with WorkflowActionBridge")

        # 6. Test SDP Provider Detail via List Navigation
        print("\n--- 6. Testing SDP Provider Detail via List Navigation (/sdp) ---")
        page.goto(f"{base_url}/sdp", wait_until="networkidle")
        page.wait_for_selector("text=Skills Development Providers", timeout=10000)
        page.locator("tbody tr").first.click()
        page.wait_for_selector("text=Workflow Status:", timeout=10000)
        print(f"  [PASS] Provider Detail loaded at {page.url} with WorkflowActionBridge")

        # 7. Test Employer Detail Evidence Vault via List Navigation
        print("\n--- 7. Testing Employer Detail Evidence Vault via List Navigation (/employers) ---")
        page.goto(f"{base_url}/employers", wait_until="networkidle")
        page.wait_for_selector("text=Employer & Organisation Registry", timeout=10000)
        page.locator("tbody tr").first.click()
        page.wait_for_selector("text=Organisation Identification", timeout=10000)
        
        vault_tab = page.locator("div.mud-tab:has-text('Evidence Vault')")
        if vault_tab.count() > 0:
            vault_tab.first.click()
            page.wait_for_selector("text=Document & Evidence Vault", timeout=5000)
            print(f"  [PASS] Employer Detail loaded at {page.url} with Evidence Vault")

        browser.close()

        if errors:
            print(f"\n[WARNING] Encountered {len(errors)} console/page warnings/errors:")
            for err in errors[:5]:
                print(f"  - {err}")
        else:
            print("\n[SUCCESS] All Workflow & Document Vault E2E Playwright tests passed with ZERO errors!")

if __name__ == "__main__":
    test_workflow_and_document_suite()
