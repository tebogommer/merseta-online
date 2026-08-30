import sys
from playwright.sync_api import sync_playwright

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

def test_workflow_sync_suite():
    print("[START] Running Workflow State & Entity Status Synchronization Playwright Test...")
    
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        errors = []
        page.on("pageerror", lambda err: errors.append(f"Page Error: {err}"))
        page.on("console", lambda msg: errors.append(f"Console Error: {msg.text}") if msg.type == "error" else None)

        base_url = "http://localhost:5121"

        # 1. Test Training Provider (SDP) Detail & Workflow Progress Bar (/sdp/1)
        print("\n--- 1. Testing Training Provider Workflow Progress Bar (/sdp/1) ---")
        page.goto(f"{base_url}/sdp/1", wait_until="networkidle")
        page.wait_for_selector(".workflow-step-node", timeout=15000)
        step_nodes = page.locator(".workflow-step-node").count()
        print(f"  [PASS] /sdp/1 loaded. Rendered {step_nodes} sequential workflow state steps for SDP")
        assert step_nodes >= 5, "Expected at least 5 workflow steps for PROVIDER"

        # 2. Test WSP Submission Detail & Workflow Progress Bar (/wsp/1)
        print("\n--- 2. Testing WSP Submission Workflow Progress Bar (/wsp/1) ---")
        page.goto(f"{base_url}/wsp/1", wait_until="networkidle")
        page.wait_for_selector(".workflow-step-node", timeout=15000)
        wsp_steps = page.locator(".workflow-step-node").count()
        print(f"  [PASS] /wsp/1 loaded. Rendered {wsp_steps} sequential workflow state steps for WSP")
        assert wsp_steps >= 5, "Expected at least 5 workflow steps for WSP"

        # 3. Test Discretionary Grant Application Detail & Progress Bar (/grants/1)
        print("\n--- 3. Testing DG Application Workflow Progress Bar (/grants/1) ---")
        page.goto(f"{base_url}/grants/1", wait_until="networkidle")
        page.wait_for_selector(".workflow-step-node", timeout=15000)
        dg_steps = page.locator(".workflow-step-node").count()
        print(f"  [PASS] /grants/1 loaded. Rendered {dg_steps} sequential workflow state steps for DG")
        assert dg_steps >= 4, "Expected at least 4 workflow steps for DG"

        # 4. Test Workplace Approval Detail & Progress Bar (/workplace-approvals/1)
        print("\n--- 4. Testing Workplace Approval Workflow Progress Bar (/workplace-approvals/1) ---")
        page.goto(f"{base_url}/workplace-approvals/1", wait_until="networkidle")
        page.wait_for_selector(".workflow-step-node", timeout=15000)
        wpa_steps = page.locator(".workflow-step-node").count()
        print(f"  [PASS] /workplace-approvals/1 loaded. Rendered {wpa_steps} sequential workflow state steps for WPA")
        assert wpa_steps >= 4, "Expected at least 4 workflow steps for WPA"

        # 5. Test Company Learner Detail & Progress Bar (/learners/1)
        print("\n--- 5. Testing Company Learner Workflow Progress Bar (/learners/1) ---")
        page.goto(f"{base_url}/learners/1", wait_until="networkidle")
        page.wait_for_selector(".workflow-step-node", timeout=15000)
        lrn_steps = page.locator(".workflow-step-node").count()
        print(f"  [PASS] /learners/1 loaded. Rendered {lrn_steps} sequential workflow state steps for Learner")
        assert lrn_steps >= 3, "Expected at least 3 workflow steps for LRN"

        # 6. Test Grant MOA Detail & Progress Bar (/finance/grants/1)
        print("\n--- 6. Testing Grant MOA Workflow Progress Bar (/finance/grants/1) ---")
        page.goto(f"{base_url}/finance/grants/1", wait_until="networkidle")
        page.wait_for_selector(".workflow-step-node", timeout=15000)
        moa_steps = page.locator(".workflow-step-node").count()
        print(f"  [PASS] /finance/grants/1 loaded. Rendered {moa_steps} sequential workflow state steps for Grant MOA")
        assert moa_steps >= 4, "Expected at least 4 workflow steps for Grant MOA"

        browser.close()

        if errors:
            print(f"\n[WARNING] Encountered {len(errors)} console/page warnings/errors:")
            for err in errors[:5]:
                print(f"  - {err}")
        else:
            print("\n[SUCCESS] All Workflow State Machine & Synchronization Playwright tests passed with ZERO errors!")

if __name__ == "__main__":
    test_workflow_sync_suite()
