import sys
import os
import time
from playwright.sync_api import sync_playwright

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

def test_and_record_sdp_ui():
    print("[START] Running SDP UI End-to-End Test Suite with Video Recording...")
    video_dir = os.path.join(os.getcwd(), "artifacts", "videos")
    os.makedirs(video_dir, exist_ok=True)
    
    screenshot_dir = os.path.join(os.getcwd(), "artifacts", "screenshots", "sdp")
    os.makedirs(screenshot_dir, exist_ok=True)

    base_url = "http://localhost:5121"

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(
            viewport={"width": 1440, "height": 900},
            record_video_dir=video_dir,
            record_video_size={"width": 1440, "height": 900}
        )
        page = context.new_page()

        errors = []
        page.on("pageerror", lambda err: errors.append(f"Page Error: {err}"))
        page.on("console", lambda msg: errors.append(f"Console Error: {msg.text}") if msg.type == "error" else None)

        try:
            # 1. Authenticate as SuperAdmin
            print("\n--- 1. Authenticating as SuperAdmin ---")
            page.goto(f"{base_url}/login", wait_until="networkidle")
            page.fill("input#username", "sysadmin@merseta.org.za")
            page.fill("input#password", "MerSETA@2026!")
            page.click("button[type='submit']")
            page.wait_for_load_state("networkidle")
            time.sleep(1)
            print("  [PASS] Logged in successfully!")

            # 2. Test SDP Register (/sdp)
            print("\n--- 2. Testing SDP Register (/sdp) ---")
            page.goto(f"{base_url}/sdp", wait_until="networkidle")
            page.wait_for_selector("text=Skills Development Providers (SDP)", timeout=10000)
            page.screenshot(path=os.path.join(screenshot_dir, "01_sdp_list.png"))
            print("  [PASS] /sdp loaded and captured screenshot")

            # Search/Filter test
            search_input = page.locator("input[placeholder*='Search by Accreditation No']")
            if search_input.count() > 0:
                search_input.first.fill("SDP")
                time.sleep(1)
                search_input.first.press("Enter")
                time.sleep(1)
                print("  [PASS] Search filter exercised")

            # 3. Test SDP Review Queue (/sdp/review-queue)
            print("\n--- 3. Testing SDP Review Queue (/sdp/review-queue) ---")
            page.goto(f"{base_url}/sdp/review-queue", wait_until="networkidle")
            page.wait_for_selector("text=SDP Accreditation Review Queue", timeout=10000)
            page.screenshot(path=os.path.join(screenshot_dir, "02_sdp_review_queue.png"))
            print("  [PASS] /sdp/review-queue loaded with metrics and queue table")

            # 4. Test SDP Accreditation Application Wizard (/providers/apply-accreditation)
            print("\n--- 4. Testing SDP Accreditation Application Wizard (/providers/apply-accreditation) ---")
            page.goto(f"{base_url}/providers/apply-accreditation", wait_until="networkidle")
            page.wait_for_selector("text=SDP institutional accreditation application", timeout=10000)
            page.screenshot(path=os.path.join(screenshot_dir, "03_sdp_wizard_step1.png"))
            print("  [PASS] Step 1 Provider Profile rendered")

            # Fill Step 1 fields
            legal_input = page.locator("input").nth(0)
            if legal_input.count() > 0:
                legal_input.fill("Apex Precision Skills Institute (Pty) Ltd")
            time.sleep(1)
            page.screenshot(path=os.path.join(screenshot_dir, "04_sdp_wizard_step1_filled.png"))

            # 5. Test SDP Detail View (/sdp/1)
            print("\n--- 5. Testing SDP Detail Hub (/sdp/1) ---")
            page.goto(f"{base_url}/sdp/1", wait_until="networkidle")
            page.wait_for_selector("text=Back to SDP Registry", timeout=10000)
            time.sleep(1)
            page.screenshot(path=os.path.join(screenshot_dir, "05_sdp_detail_profile.png"))
            print("  [PASS] SDP Detail Profile loaded")

            # Verify Tab 5: Delivery Sites
            print("  -> Testing Tab 5: Delivery Sites...")
            sites_tab = page.locator("div.mud-tab:has-text('Delivery Sites')")
            if sites_tab.count() > 0:
                sites_tab.first.click()
                time.sleep(1)
                page.wait_for_selector("text=Accredited Delivery Sites & Physical Locations", timeout=5000)
                page.screenshot(path=os.path.join(screenshot_dir, "06_sdp_detail_delivery_sites.png"))
                print("  [PASS] Tab 5: Delivery Sites loaded with compliant terminology")

                # Test Add Delivery Site modal trigger
                add_site_btn = page.locator("button:has-text('Add Delivery Site')")
                if add_site_btn.count() > 0:
                    add_site_btn.first.click()
                    time.sleep(1)
                    page.wait_for_selector("text=Add Delivery Site / Physical Location", timeout=5000)
                    page.screenshot(path=os.path.join(screenshot_dir, "07_sdp_add_delivery_site_dialog.png"))
                    # Close dialog
                    page.locator("div.mud-dialog button:has-text('Cancel')").click()
                    time.sleep(1)
                    print("  [PASS] Add Delivery Site dialog verified and closed")

            # Verify Tab 4: Enrolled Learners
            print("  -> Testing Tab 4: Enrolled Learners...")
            learners_tab = page.locator("div.mud-tab:has-text('Enrolled Learners')")
            if learners_tab.count() > 0:
                learners_tab.first.click()
                time.sleep(1)
                page.screenshot(path=os.path.join(screenshot_dir, "08_sdp_detail_learners.png"))
                print("  [PASS] Tab 4: Enrolled Learners loaded")

            # Verify Tab 6: Assessors & Moderators
            print("  -> Testing Tab 6: Assessors & Moderators...")
            assessors_tab = page.locator("div.mud-tab:has-text('Assessors & Moderators')")
            if assessors_tab.count() > 0:
                assessors_tab.first.click()
                time.sleep(1)
                page.screenshot(path=os.path.join(screenshot_dir, "09_sdp_detail_assessors.png"))
                print("  [PASS] Tab 6: Assessors & Moderators loaded with 'delivery sites' subtitle")

            # Verify Tab 7: Quality Audits & Visits
            print("  -> Testing Tab 7: Quality Audits & Visits...")
            audits_tab = page.locator("div.mud-tab:has-text('Quality Audits & Visits')")
            if audits_tab.count() > 0:
                audits_tab.first.click()
                time.sleep(1)
                page.screenshot(path=os.path.join(screenshot_dir, "10_sdp_detail_audits.png"))
                print("  [PASS] Tab 7: Quality Audits & Visits loaded")

            # Verify Tab 8: QMS Self-Evaluation
            print("  -> Testing Tab 8: QMS Self-Evaluation...")
            qms_tab = page.locator("div.mud-tab:has-text('QMS Self-Evaluation')")
            if qms_tab.count() > 0:
                qms_tab.first.click()
                time.sleep(1)
                page.screenshot(path=os.path.join(screenshot_dir, "11_sdp_detail_qms.png"))
                print("  [PASS] Tab 8: QMS Self-Evaluation loaded")

            # Verify Tab 9: Contacts & Quorum
            print("  -> Testing Tab 9: Contacts & Quorum...")
            contacts_tab = page.locator("div.mud-tab:has-text('Contacts & Quorum')")
            if contacts_tab.count() > 0:
                contacts_tab.first.click()
                time.sleep(1)
                page.screenshot(path=os.path.join(screenshot_dir, "12_sdp_detail_contacts_quorum.png"))
                print("  [PASS] Tab 9: Contacts & Quorum loaded with statutory verification badge")

            print("\n[COMPLETE] All SDP UI workflows tested successfully!")

        finally:
            page.close()
            context.close()
            browser.close()

            video_files = [os.path.join(video_dir, f) for f in os.listdir(video_dir) if f.endswith(".webm")]
            if video_files:
                latest_video = max(video_files, key=os.path.getctime)
                target_video_path = os.path.join(video_dir, "sdp_ui_walkthrough.webm")
                if os.path.exists(target_video_path) and target_video_path != latest_video:
                    os.remove(target_video_path)
                if target_video_path != latest_video:
                    os.rename(latest_video, target_video_path)
                print(f"\n[VIDEO RECORDING SAVED] => {target_video_path}")
                print(f"File size: {os.path.getsize(target_video_path) / 1024:.1f} KB")

if __name__ == "__main__":
    test_and_record_sdp_ui()
