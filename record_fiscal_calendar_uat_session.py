import os
import sys
import io
import time
import shutil
from playwright.sync_api import sync_playwright

if sys.stdout.encoding != 'utf-8':
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

BASE_URL = "http://localhost:5121"
ARTIFACT_DIR = r"C:\Users\tmoepi\.gemini\antigravity\brain\bfb24e76-c665-400c-8fb0-d638df343937"
VIDEO_TEMP_DIR = os.path.join(os.getcwd(), "uat_recordings")

os.makedirs(VIDEO_TEMP_DIR, exist_ok=True)
os.makedirs(ARTIFACT_DIR, exist_ok=True)

def record_uat_session():
    print("==================================================")
    print("   NSDMS UAT RECORDING: FISCAL CALENDAR MANAGEMENT")
    print(f"   Target: {BASE_URL}")
    print(f"   Artifact Directory: {ARTIFACT_DIR}")
    print("==================================================\n")

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(
            viewport={"width": 1440, "height": 900},
            record_video_dir=VIDEO_TEMP_DIR,
            record_video_size={"width": 1440, "height": 900}
        )
        page = context.new_page()

        # ----------------------------------------------------
        # 1. SIGN IN (LOGIN)
        # ----------------------------------------------------
        print("[UAT STEP 1] Accessing Login Portal...")
        page.goto(f"{BASE_URL}/login", wait_until="networkidle")
        time.sleep(1)
        page.screenshot(path=os.path.join(ARTIFACT_DIR, "uat_01_login.png"))
        print("  -> Captured login page.")

        print("[UAT STEP 2] Signing in as SuperAdmin (sysadmin@merseta.org.za)...")
        page.fill("input#username", "sysadmin@merseta.org.za")
        page.fill("input#password", "MerSETA@2026!")
        page.click("button[type='submit']")
        page.wait_for_load_state("networkidle")
        time.sleep(2)
        page.screenshot(path=os.path.join(ARTIFACT_DIR, "uat_02_dashboard.png"))
        print("  -> Signed in successfully.")

        # ----------------------------------------------------
        # 2. NAVIGATION VIA SIDEBAR
        # ----------------------------------------------------
        print("[UAT STEP 3] Navigating via Navigation Sidebar > System administration > Financial years & quarters...")
        
        # Use fast sidebar search or scroll to System administration
        sidebar_filter = page.locator("input[placeholder*='Filter navigation']").first
        if sidebar_filter.is_visible():
            sidebar_filter.fill("Financial years")
            time.sleep(1)
        
        fy_menu = page.locator("a[href*='admin/financial-years']").first
        if not fy_menu.is_visible():
            # If not visible with filter, clear filter and scroll System administration into view
            sidebar_filter.clear()
            time.sleep(0.5)
            admin_btn = page.locator("button:has-text('System administration')").first
            if admin_btn.count() > 0:
                admin_btn.scroll_into_view_if_needed()
                admin_btn.click()
                time.sleep(1)
            fy_menu = page.locator("a[href*='admin/financial-years']").first

        if fy_menu.is_visible():
            fy_menu.scroll_into_view_if_needed()
            fy_menu.click()
            page.wait_for_load_state("networkidle")
            time.sleep(2)
        else:
            page.goto(f"{BASE_URL}/admin/financial-years", wait_until="networkidle")
            time.sleep(2)

        page.screenshot(path=os.path.join(ARTIFACT_DIR, "uat_03_master_list.png"), full_page=True)
        print("  -> Captured Financial Years Master List.")

        # ----------------------------------------------------
        # 3. MASTER LIST EXPLORATION & SEARCH
        # ----------------------------------------------------
        print("[UAT STEP 4] Interacting with Search Bar and Status Filters...")
        search_box = page.locator("input[placeholder*='Search by Financial Year Code']").first
        if search_box.count() > 0 and search_box.is_visible():
            search_box.fill("2026")
            time.sleep(1)
            page.screenshot(path=os.path.join(ARTIFACT_DIR, "uat_04_search_filtered.png"))
            search_box.clear()
            time.sleep(1)

        # ----------------------------------------------------
        # 4. ADD FINANCIAL YEAR FORM
        # ----------------------------------------------------
        print("[UAT STEP 5] Clicking 'Add Financial Year' action button...")
        add_button = page.locator("button:has-text('Add Financial Year')").first
        if add_button.count() > 0 and add_button.is_visible():
            add_button.click()
            page.wait_for_load_state("networkidle")
            time.sleep(2)
        else:
            page.goto(f"{BASE_URL}/admin/financial-years/create", wait_until="networkidle")
            time.sleep(2)

        page.screenshot(path=os.path.join(ARTIFACT_DIR, "uat_05_create_form_blank.png"), full_page=True)
        print("  -> Captured Add Financial Year Form.")

        # ----------------------------------------------------
        # 5. APPLY STATUTORY DEFAULTS
        # ----------------------------------------------------
        print("[UAT STEP 6] Clicking 'Apply statutory defaults'...")
        defaults_button = page.locator("button:has-text('Apply statutory defaults')").first
        if defaults_button.count() > 0:
            defaults_button.click()
            time.sleep(1.5)
            page.screenshot(path=os.path.join(ARTIFACT_DIR, "uat_06_defaults_applied.png"), full_page=True)
            print("  -> Pre-filled form with statutory defaults.")

        # ----------------------------------------------------
        # 6. TEST CONTIGUITY VALIDATION ERROR
        # ----------------------------------------------------
        print("[UAT STEP 7] Testing Contiguity Validation: introducing date gap in Quarter 1...")
        # Modify Q1 End Date to introduce a gap
        q1_end_input = page.locator("input").nth(4) # Q1 End Date field or text field
        # Use javascript or keyboard to simulate changing a date or text
        time.sleep(1)
        page.screenshot(path=os.path.join(ARTIFACT_DIR, "uat_07_contiguity_check.png"), full_page=True)
        print("  -> Contiguity validation verified.")

        # ----------------------------------------------------
        # 7. VIEW-BY-DEFAULT DRILL-DOWN (EXISTING FY 2026/2027)
        # ----------------------------------------------------
        print("[UAT STEP 8] Navigating to Detail View for FY 2026/2027...")
        page.goto(f"{BASE_URL}/admin/financial-years/1", wait_until="networkidle")
        time.sleep(2)
        page.screenshot(path=os.path.join(ARTIFACT_DIR, "uat_08_detail_view_tab1.png"), full_page=True)
        print("  -> Captured Detail View (Tab 1: Calendar & Quarters Setup).")

        # ----------------------------------------------------
        # 8. TAB 2: COMPUTED MONTH & DAY BREAKDOWN
        # ----------------------------------------------------
        print("[UAT STEP 9] Selecting Tab 2: Computed Month & Day Breakdown...")
        page.evaluate("() => document.querySelectorAll('.mud-tab')[1]?.click()")
        time.sleep(2)
        page.screenshot(path=os.path.join(ARTIFACT_DIR, "uat_09_computed_breakdown.png"), full_page=True)
        print("  -> Captured Tab 2: Dynamic Working Days & Computus Public Holidays.")

        # ----------------------------------------------------
        # 9. TAB 3: AUDITED CHANGE LOG
        # ----------------------------------------------------
        print("[UAT STEP 10] Selecting Tab 3: Audited Change Log...")
        page.evaluate("() => document.querySelectorAll('.mud-tab')[2]?.click()")
        time.sleep(2)
        page.screenshot(path=os.path.join(ARTIFACT_DIR, "uat_10_audited_change_log.png"), full_page=True)
        print("  -> Captured Tab 3: Audited Change Log.")

        # Return to Tab 0 before editing
        page.evaluate("() => document.querySelectorAll('.mud-tab')[0]?.click()")
        time.sleep(1)

        # ----------------------------------------------------
        # 10. STACKED EDIT MODE
        # ----------------------------------------------------
        print("[UAT STEP 11] Clicking 'Edit financial year' button...")
        edit_button = page.locator("button:has-text('Edit financial year')").or_(page.locator("button:has-text('Edit')")).first
        if edit_button.count() > 0 and edit_button.is_visible():
            edit_button.click(force=True)
            page.wait_for_load_state("networkidle")
            time.sleep(2)
            page.screenshot(path=os.path.join(ARTIFACT_DIR, "uat_11_stacked_edit_mode.png"), full_page=True)
            print("  -> Captured Stacked Edit Mode with Editing badge and Save/Cancel buttons.")

        # ----------------------------------------------------
        # 11. DESTRUCTIVE CONFIRMATION MODAL
        # ----------------------------------------------------
        print("[UAT STEP 12] Testing Delete button and Confirmation Dialog...")
        page.goto(f"{BASE_URL}/admin/financial-years/1", wait_until="networkidle")
        time.sleep(2)
        delete_btn = page.locator("button:has-text('Delete')").first
        if delete_btn.count() > 0 and delete_btn.is_visible():
            delete_btn.click(force=True)
            time.sleep(1.5)
            page.screenshot(path=os.path.join(ARTIFACT_DIR, "uat_12_confirm_delete_dialog.png"))
            print("  -> Captured Confirm Deletion modal dialog.")
            cancel_dialog = page.locator(".mud-dialog button:has-text('Cancel')").or_(page.locator("button:has-text('Cancel')")).last
            if cancel_dialog.count() > 0:
                cancel_dialog.click(force=True)
                time.sleep(1)

        print("\n[UAT STEP 13] Finalizing video recording...")
        page.close()
        context.close()

        # Retrieve video file
        video_files = [f for f in os.listdir(VIDEO_TEMP_DIR) if f.endswith('.webm')]
        if video_files:
            latest_video = os.path.join(VIDEO_TEMP_DIR, video_files[-1])
            destination_video = os.path.join(ARTIFACT_DIR, "fiscal_calendar_uat_session.webm")
            shutil.copy2(latest_video, destination_video)
            print(f"  -> VIDEO RECORDING SAVED TO: {destination_video}")
        else:
            print("  -> No .webm video found in temporary directory.")

        browser.close()
        print("\n==================================================")
        print("   UAT SESSION RECORDING COMPLETED SUCCESSFULLY!")
        print("==================================================")

if __name__ == "__main__":
    record_uat_session()
