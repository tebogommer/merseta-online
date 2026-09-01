import os
import sys
import time
import shutil
from playwright.sync_api import sync_playwright

BASE_URL = "http://localhost:5121"
ARTIFACT_DIR = r"C:\Users\tmoepi\.gemini\antigravity\brain\f0e9048a-752d-4feb-91ea-8579cb078acc"
VIDEO_DIR = os.path.join(os.getcwd(), "test_recordings")

os.makedirs(VIDEO_DIR, exist_ok=True)
os.makedirs(ARTIFACT_DIR, exist_ok=True)

def run_recorded_test():
    print("==================================================")
    print("  RECORDING UI DEMO: DG FUNDING WINDOWS HUB")
    print(f"  Target: {BASE_URL}")
    print(f"  Recording Directory: {VIDEO_DIR}")
    print("==================================================")

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(
            viewport={"width": 1440, "height": 900},
            record_video_dir=VIDEO_DIR,
            record_video_size={"width": 1440, "height": 900}
        )
        page = context.new_page()

        # Step 1: Navigate to Grants List (/grants)
        print("\n[Step 1] Navigating to Discretionary Grants (/grants)...")
        page.goto(f"{BASE_URL}/grants", wait_until="networkidle", timeout=15000)
        time.sleep(1)
        shot1_path = os.path.join(ARTIFACT_DIR, "step1_grants_list.png")
        page.screenshot(path=shot1_path, full_page=True)
        print(f"  Captured: {shot1_path}")

        # Step 2: Click 'Funding Windows' Action Button
        print("\n[Step 2] Clicking 'Funding Windows' button -> navigating to /grants/windows...")
        page.click("text='Funding Windows'")
        page.wait_for_url("**/grants/windows", timeout=10000)
        time.sleep(1)
        shot2_path = os.path.join(ARTIFACT_DIR, "step2_funding_windows_registry.png")
        page.screenshot(path=shot2_path, full_page=True)
        print(f"  Captured: {shot2_path}")

        # Step 3: Click 'Open New Funding Window' button -> /grants/windows/create
        print("\n[Step 3] Clicking 'Open New Funding Window' button -> navigating to /grants/windows/create...")
        page.click("text='Open New Funding Window'")
        page.wait_for_url("**/grants/windows/create", timeout=10000)
        time.sleep(1)
        shot3_path = os.path.join(ARTIFACT_DIR, "step3_create_window_blank.png")
        page.screenshot(path=shot3_path, full_page=True)
        print(f"  Captured: {shot3_path}")

        # Step 4: Fill Funding Window Form
        print("\n[Step 4] Filling Funding Window form details...")
        # Fill Title
        name_input = page.locator("input[placeholder*='Discretionary Grant Funding Window']").first
        name_input.fill("2026/27 National Green Economy & EV Apprenticeship Funding Window")
        time.sleep(0.5)

        # Fill Budget
        budget_input = page.locator("label:has-text('Total Discretionary Budget')").locator("..").locator("input").first
        budget_input.fill("35000000")
        time.sleep(0.5)

        # Fill Gazette Notes
        desc_input = page.locator("textarea[placeholder*='Government Gazette']").first
        desc_input.fill("Gazette Notice No. 49812 - Priority funding window supporting electric vehicle artisans, solar photovoltaic technicians, and mechatronics trades under NSDP 2030 outcomes.")
        time.sleep(0.5)

        shot4_path = os.path.join(ARTIFACT_DIR, "step4_create_window_filled.png")
        page.screenshot(path=shot4_path, full_page=True)
        print(f"  Captured: {shot4_path}")

        # Step 5: Submit 'Gazette & Open Window'
        print("\n[Step 5] Clicking 'Gazette & Open Window' Save Button...")
        page.click("button:has-text('Gazette & Open Window')")
        time.sleep(2)
        page.wait_for_load_state("networkidle")
        time.sleep(1)

        shot5_path = os.path.join(ARTIFACT_DIR, "step5_window_detail_created.png")
        page.screenshot(path=shot5_path, full_page=True)
        print(f"  Captured: {shot5_path}")

        # Step 6: Explore Tabs on the 360° Window Detail Hub
        print("\n[Step 6] Exploring Tabs on Window Detail Hub...")
        # Tab 2: Applications Lodged
        page.click("text='Applications Lodged'")
        time.sleep(1)
        shot6a_path = os.path.join(ARTIFACT_DIR, "step6a_applications_tab.png")
        page.screenshot(path=shot6a_path, full_page=True)
        print(f"  Captured: {shot6a_path}")

        # Tab 3: Budget Metrics
        page.click("text='Budget Metrics'")
        time.sleep(1)
        shot6b_path = os.path.join(ARTIFACT_DIR, "step6b_budget_metrics_tab.png")
        page.screenshot(path=shot6b_path, full_page=True)
        print(f"  Captured: {shot6b_path}")

        # Tab 4: Gazette Documents
        page.click("text='Gazette Documents'")
        time.sleep(1)
        shot6c_path = os.path.join(ARTIFACT_DIR, "step6c_gazette_documents_tab.png")
        page.screenshot(path=shot6c_path, full_page=True)
        print(f"  Captured: {shot6c_path}")

        # Step 7: Return to /grants/windows to verify active listing
        print("\n[Step 7] Returning to /grants/windows registry...")
        page.click("button:has-text('Back to Windows')")
        page.wait_for_url("**/grants/windows", timeout=10000)
        time.sleep(1)
        shot7_path = os.path.join(ARTIFACT_DIR, "step7_windows_registry_updated.png")
        page.screenshot(path=shot7_path, full_page=True)
        print(f"  Captured: {shot7_path}")

        # Step 8: Visit Grant Application Form to verify Window Association
        print("\n[Step 8] Visiting Discretionary Grant Detail (/grants/1) -> Checking Funding Window link...")
        page.goto(f"{BASE_URL}/grants/1", wait_until="networkidle", timeout=15000)
        time.sleep(1)
        page.click("text='Funding Window'")
        time.sleep(1)
        shot8_path = os.path.join(ARTIFACT_DIR, "step8_grant_application_window_link.png")
        page.screenshot(path=shot8_path, full_page=True)
        print(f"  Captured: {shot8_path}")

        print("\nClosing context and saving video recording...")
        page.close()
        video_path = page.video.path()
        context.close()
        browser.close()

        if video_path and os.path.exists(video_path):
            dest_video = os.path.join(ARTIFACT_DIR, "dg_funding_windows_ui_demo.webm")
            shutil.copy2(video_path, dest_video)
            print(f"\n[SUCCESS] Video recording saved to: {dest_video}")

    print("\n[COMPLETE] UI recording and screenshot sequence generated successfully!")

if __name__ == "__main__":
    run_recorded_test()
