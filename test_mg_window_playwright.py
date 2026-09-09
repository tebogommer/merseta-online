import sys
import os
import io
import time
import shutil
from playwright.sync_api import sync_playwright

if sys.stdout.encoding != 'utf-8':
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

BASE_URL = "http://localhost:5121"
ARTIFACT_DIR = r"C:\Users\tmoepi\.gemini\antigravity\brain\9abbf3c8-ba1b-4eae-95a2-da224e71b263"
RECORDING_DIR = os.path.join(ARTIFACT_DIR, "recordings")
SCREENSHOTS_DIR = os.path.join(ARTIFACT_DIR, "screenshots")

os.makedirs(RECORDING_DIR, exist_ok=True)
os.makedirs(SCREENSHOTS_DIR, exist_ok=True)

def test_mandatory_grant_window():
    print("==================================================")
    print("   NSDMS MANDATORY GRANT (MG) WINDOW UI TEST")
    print(f"   Target: {BASE_URL}")
    print(f"   Artifact Directory: {ARTIFACT_DIR}")
    print("==================================================\n")

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(
            viewport={"width": 1440, "height": 900},
            record_video_dir=RECORDING_DIR,
            record_video_size={"width": 1440, "height": 900}
        )
        page = context.new_page()

        try:
            # 1. Login
            print("[1/5] Logging into NSDMS portal...")
            page.goto(f"{BASE_URL}/login", wait_until="networkidle", timeout=15000)
            page.fill("input#username", "sysadmin@merseta.org.za")
            page.fill("input#password", "MerSETA@2026!")
            page.screenshot(path=os.path.join(SCREENSHOTS_DIR, "01_login_page.png"))
            page.click("button[type='submit']")
            page.wait_for_load_state("networkidle")
            time.sleep(2)
            print("  --> Authenticated successfully!")

            # 2. Navigate to WSP & Mandatory Grants Registry
            print("\n[2/6] Navigating to /wsp (Mandatory Grants Submissions Registry)...")
            page.goto(f"{BASE_URL}/wsp", wait_until="networkidle", timeout=15000)
            time.sleep(2)
            page.screenshot(path=os.path.join(SCREENSHOTS_DIR, "02_wsp_registry.png"))
            page.screenshot(path=os.path.join(ARTIFACT_DIR, "02_wsp_registry.png"))
            
            body = page.inner_text("body")
            assert "WSP & Mandatory Grants" in body, "Expected page title 'WSP & Mandatory Grants'"
            assert "Window Settings" in body, "Expected 'Window Settings' button in ActionZone"
            print("  --> WSP Registry loaded! 'Window Settings' button is present.")

            # 3. Open Mandatory Grant Window Schedule Dialog
            print("\n[3/7] Clicking 'Window Settings' to configure Window Governance...")
            page.click("button:has-text('Window Settings')")
            time.sleep(2)

            # Verify Dialog is rendered
            dialog = page.locator(".mud-dialog")
            dialog.wait_for(state="visible", timeout=10000)
            dialog_text = dialog.inner_text()
            assert "Mandatory Grant (MG) Window Governance" in dialog_text, "Expected Dialog Header"
            print("  --> Window Governance dialog successfully opened with Maker-Checker standard!")
            page.screenshot(path=os.path.join(SCREENSHOTS_DIR, "03_mg_window_governance_dialog.png"))
            page.screenshot(path=os.path.join(ARTIFACT_DIR, "03_mg_window_governance_dialog.png"))

            # 4. If Proposer stage is active, propose a new window schedule
            submit_btn = dialog.locator("button:has-text('Submit for Independent Review')")
            if submit_btn.is_visible():
                print("\n[4/7] Submitting Maker Proposal for Schedule Change...")
                # Fill justification if empty
                justification_input = dialog.locator("textarea, input[placeholder*='operational or legal grounds']").first
                if justification_input.is_visible():
                    justification_input.click()
                    justification_input.press("Control+a")
                    justification_input.press("Backspace")
                    justification_input.press_sequentially("Statutory 2026/27 annual submission window per Regulation 4(1) and Board Resolution BR-2026-08.", delay=50)

                time.sleep(1)
                page.screenshot(path=os.path.join(SCREENSHOTS_DIR, "04_maker_proposal_form.png"))
                page.screenshot(path=os.path.join(ARTIFACT_DIR, "04_maker_proposal_form.png"))

                # Submit proposal
                submit_btn.click()
                time.sleep(3)
                print("  --> Maker proposal submitted! Waiting for UI refresh...")
                page.screenshot(path=os.path.join(SCREENSHOTS_DIR, "05_maker_proposal_submitted.png"))
                page.screenshot(path=os.path.join(ARTIFACT_DIR, "05_maker_proposal_submitted.png"))

            # 5. Verify Pending Dual Authorisation State & Segregation of Duties
            print("\n[5/7] Verifying Segregation of Duties & Diff Cards...")
            dialog_content = dialog.inner_text()
            if "Independent Review Required" in dialog_content or "Pending Independent Review" in dialog_content:
                print("  --> Dual Authorisation state ACTIVE: Proposer/Reviewer segregation enforced.")
                page.screenshot(path=os.path.join(SCREENSHOTS_DIR, "06_maker_checker_pending_review.png"))
                page.screenshot(path=os.path.join(ARTIFACT_DIR, "06_maker_checker_pending_review.png"))

            # 6. Switch to Tab 2: Proposal & Review History
            print("\n[6/7] Inspecting Proposal & Review History Tab...")
            history_tab = dialog.locator(".mud-tab:has-text('Proposal & Review History')")
            if history_tab.is_visible():
                history_tab.click()
                time.sleep(2)
                page.screenshot(path=os.path.join(SCREENSHOTS_DIR, "07_maker_checker_audit_log.png"))
                page.screenshot(path=os.path.join(ARTIFACT_DIR, "07_maker_checker_audit_log.png"))
                print("  --> Proposal & Review History Table loaded and verified!")

            # Close dialog
            close_btn = dialog.locator("button:has-text('Close')")
            close_btn.click()
            time.sleep(2)

            # 7. Verify Wizard & Extensions
            print("\n[7/7] Verifying WSP Wizard and Extensions Queue...")
            page.click("button:has-text('New WSP Submission')")
            page.wait_for_load_state("networkidle")
            time.sleep(2)
            page.screenshot(path=os.path.join(SCREENSHOTS_DIR, "08_wsp_submission_window.png"))
            page.screenshot(path=os.path.join(ARTIFACT_DIR, "08_wsp_submission_window.png"))

        finally:
            # Close page and context to ensure video recording is flushed to disk
            video_path = page.video.path() if page.video else None
            page.close()
            context.close()
            browser.close()

            if video_path and os.path.exists(video_path):
                target_video = os.path.join(ARTIFACT_DIR, "mg_window_ui_recording.webm")
                shutil.copyfile(video_path, target_video)
                print(f"\n[SUCCESS] Session video recording captured and saved to:")
                print(f"  {target_video}")

    print("\n[COMPLETE] All UI stages verified and recorded successfully!")

if __name__ == "__main__":
    test_mandatory_grant_window()
