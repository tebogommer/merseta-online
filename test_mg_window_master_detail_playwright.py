import sys
import os
import io
import time
import shutil
import urllib.request
from playwright.sync_api import sync_playwright

if sys.stdout.encoding != 'utf-8':
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

BASE_URL = "http://localhost:5121"
ARTIFACT_DIR = r"C:\Users\tmoepi\.gemini\antigravity\brain\9abbf3c8-ba1b-4eae-95a2-da224e71b263"
RECORDING_DIR = os.path.join(ARTIFACT_DIR, "recordings")
SCREENSHOTS_DIR = os.path.join(ARTIFACT_DIR, "screenshots")

os.makedirs(RECORDING_DIR, exist_ok=True)
os.makedirs(SCREENSHOTS_DIR, exist_ok=True)

def wait_for_server(url, timeout=45):
    print(f"Waiting for server at {url} to be ready...")
    start = time.time()
    while time.time() - start < timeout:
        try:
            req = urllib.request.Request(url, headers={'User-Agent': 'Mozilla/5.0'})
            with urllib.request.urlopen(req, timeout=3) as response:
                if response.status in (200, 302, 401):
                    print("  --> Server is ready!")
                    return True
        except Exception:
            time.sleep(1.5)
    print("  --> Timeout waiting for server.")
    return False

def save_screenshots(page, filename):
    shot_path_sub = os.path.join(SCREENSHOTS_DIR, filename)
    shot_path_root = os.path.join(ARTIFACT_DIR, filename)
    page.screenshot(path=shot_path_sub)
    shutil.copyfile(shot_path_sub, shot_path_root)
    print(f"  [SCREENSHOT] Saved: {shot_path_sub} and {shot_path_root}")

def test_mg_window_master_detail():
    print("==================================================================")
    print("   OPTION A: CLEAN SINGLE-SCHEDULE HUB WITH DROPDOWN LOOKUP")
    print("   PLAYWRIGHT VERIFICATION SUITE")
    print(f"   Target: {BASE_URL}")
    print(f"   Artifact Directory: {ARTIFACT_DIR}")
    print("==================================================================\n")

    if not wait_for_server(f"{BASE_URL}/login"):
        print("Server did not become ready in time. Exiting.")
        sys.exit(1)

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(
            viewport={"width": 1440, "height": 900},
            record_video_dir=RECORDING_DIR,
            record_video_size={"width": 1440, "height": 900}
        )
        page = context.new_page()

        try:
            # 1. Login as SysAdmin
            print("[1/5] Authenticating as System Administrator (sysadmin@merseta.org.za)...")
            page.goto(f"{BASE_URL}/login", wait_until="networkidle", timeout=20000)
            page.wait_for_selector("input#username", timeout=15000)
            page.fill("input#username", "sysadmin@merseta.org.za")
            page.fill("input#password", "Password123!")
            page.click("button[type='submit']")
            page.wait_for_timeout(2000)

            # Fallback to dev default if still on login
            if "/login" in page.url:
                print("  --> Retrying with MerSETA@2026!...")
                page.fill("input#username", "sysadmin@merseta.org.za")
                page.fill("input#password", "MerSETA@2026!")
                page.click("button[type='submit']")
                page.wait_for_timeout(2500)

            page.wait_for_load_state("networkidle")
            print(f"  --> Authenticated successfully! Current URL: {page.url}")

            # 2. Navigate to Master Register: /admin/mg-windows
            print("\n[2/5] Navigating to Master Register (/admin/mg-windows)...")
            page.goto(f"{BASE_URL}/admin/mg-windows", wait_until="networkidle", timeout=20000)
            page.wait_for_selector(".mud-table", timeout=15000)
            time.sleep(2)

            body_text = page.inner_text("body")
            assert "Mandatory Grant Submission Windows" in body_text, "Expected page title 'Mandatory Grant Submission Windows'"
            assert "governing ofo framework" in body_text.lower(), "Expected 'Governing OFO Framework' column header"

            # Verify that the "Governing OFO Framework" column displays release badges (e.g. OFO 2021 Release (v21) or OFO 2025 Release (v25))
            ofo_chips = page.locator("td[data-label='Governing OFO Framework'] .mud-chip")
            chip_count = ofo_chips.count()
            print(f"  --> Found {chip_count} Governing OFO Framework badges in Master Register.")
            assert chip_count > 0, "Expected at least one Governing OFO Framework badge in table"
            first_badge_text = ofo_chips.first.inner_text()
            print(f"  --> First Governing OFO Framework badge text: '{first_badge_text}'")
            assert "OFO" in first_badge_text and ("v21" in first_badge_text or "v25" in first_badge_text or "Release" in first_badge_text), \
                f"Badge '{first_badge_text}' should display OFO release version"
            print("  --> Master Register and Governing OFO Framework column verified!")

            # 3. Drill down into Window 1 (/admin/mg-windows/1)
            print("\n[3/5] Drilling down into Window 1 (/admin/mg-windows/1)...")
            manage_btn = page.locator("a:has-text('Manage Window'), a[href*='/admin/mg-windows/1']").first
            if manage_btn.is_visible():
                manage_btn.click()
            else:
                page.goto(f"{BASE_URL}/admin/mg-windows/1", wait_until="networkidle", timeout=20000)

            page.wait_for_load_state("networkidle")
            page.wait_for_selector(".sticky-top-header", timeout=15000)
            time.sleep(2)

            detail_text = page.inner_text("body")
            assert "Mandatory Grant" in detail_text, "Expected Mandatory Grant in detail header"

            # --- GATE 1: Check Tab 1 ("General & Statutory Schedule") ---
            print("\n  [GATE 1] Verifying Tab 1: General & Statutory Schedule...")
            assert "Statutory Submission Dates" in detail_text, "Expected 'Statutory Submission Dates' card"
            assert "Standard Submission Deadline" in detail_text, "Expected 'Standard Submission Deadline' field"
            assert "Regulation 4(2) Extension Cutoff" in detail_text, "Expected 'Regulation 4(2) Extension Cutoff' field"
            assert "Governing OFO Framework Version & Statutory Authority" in detail_text, \
                "Expected 'Governing OFO Framework Version & Statutory Authority' card"
            assert "In terms of DHET Gazette regulations, gazetted OFO taxonomy releases are national statutory reference benchmarks" in detail_text, \
                "Expected statutory guidance alert regarding gazetted OFO taxonomy releases"
            assert "Dual Authorisation Governance Trail" in detail_text, "Expected Dual Authorisation Governance Trail section"
            assert "Proposed By:" in detail_text, "Expected 'Proposed By:' governance metadata"
            assert "Adjudicated By:" in detail_text, "Expected 'Adjudicated By:' governance metadata"
            print("  --> Gate 1 Passed: Schedule dates, OFO statutory guidance, and dual authorisation metadata verified!")

            # Capture Screenshot 1
            save_screenshots(page, "01_mg_window_clean_schedule_tab.png")

            # --- GATE 2: Assert that NO ChildGrid tab or 'Designated Occupations (OFO)' tab exists ---
            print("\n  [GATE 2] Verifying removal of redundant ChildGrid / Designated Occupations tab...")
            tabs = page.locator(".mud-tab")
            tab_count = tabs.count()
            print(f"  --> Total tabs found on page: {tab_count}")
            tab_texts_upper = [tabs.nth(i).inner_text().strip().upper() for i in range(tab_count)]
            print(f"  --> Tab names: {tab_texts_upper}")

            # Verify strictly 2 tabs exist
            assert tab_count == 2, f"Expected exactly 2 tabs (Schedule & Audit), but found {tab_count}: {tab_texts_upper}"
            assert "GENERAL & STATUTORY SCHEDULE" in tab_texts_upper[0], "Tab 1 must be 'General & Statutory Schedule'"
            assert "AUDITED PROPOSAL & REVIEW HISTORY" in tab_texts_upper[1], "Tab 2 must be 'Audited Proposal & Review History'"

            # Explicit negative assertions
            assert not any("DESIGNATED" in t for t in tab_texts_upper), \
                "Violation: 'Designated Statutory Occupations' tab must NOT exist!"
            assert not any("OCCUPATION" in t for t in tab_texts_upper), \
                "Violation: 'Designated Occupations' tab must NOT exist!"
            assert not any("OFO SCOPE" in t for t in tab_texts_upper), \
                "Violation: 'OFO Scope' tab must NOT exist!"
            assert not any("CHILDGRID" in t for t in tab_texts_upper), \
                "Violation: 'ChildGrid' tab must NOT exist!"
            assert not page.locator("button:has-text('Add Designated Occupation')").is_visible(), \
                "Violation: 'Add Designated Occupation' button must NOT exist!"
            print("  --> Gate 2 Passed: Redundant ChildGrid OFO tab is completely removed!")

            # --- GATE 3: Check Tab 2 ("Audited Proposal & Review History") ---
            print("\n  [GATE 3] Switching to Tab 2: Audited Proposal & Review History...")
            audit_tab = page.locator(".mud-tab").filter(has_text="AUDITED PROPOSAL & REVIEW HISTORY").first
            if not audit_tab.is_visible():
                audit_tab = page.locator(".mud-tab:has-text('Audited Proposal & Review History')").first
            audit_tab.click()
            time.sleep(2)
            page.wait_for_selector(".mud-timeline, .mud-alert", timeout=10000)

            audit_page_text = page.inner_text("body")
            assert "Non-Repudiable Governance Audit Trail" in audit_page_text, \
                "Expected 'Non-Repudiable Governance Audit Trail' header in Tab 2"
            print("  --> Gate 3 Passed: Audited timeline and non-repudiable governance trail verified!")

            # Capture Screenshot 2
            save_screenshots(page, "02_mg_window_clean_audit_tab.png")

            # --- GATE 4: Test 'Propose New Grant Window' dialog with OFO dropdown ---
            print("\n[4/5] Testing 'Propose New Grant Window' dialog (<MudSelect> OFO version selector)...")
            # Navigate back to master list
            page.goto(f"{BASE_URL}/admin/mg-windows", wait_until="networkidle", timeout=20000)
            page.wait_for_selector("button:has-text('Propose New Grant Window')", timeout=15000)
            time.sleep(1)

            propose_btn = page.locator("button:has-text('Propose New Grant Window')").first
            propose_btn.click()
            time.sleep(1.5)

            dialog = page.locator(".mud-dialog")
            dialog.wait_for(state="visible", timeout=10000)
            dialog_text = dialog.inner_text()
            assert "Propose Mandatory Grant Submission Window" in dialog_text, "Expected Propose dialog title"
            print("  --> Propose New Grant Window dialog opened successfully!")

            # Verify Governing OFO Taxonomy Version selector is a clean dropdown (<MudSelect>)
            select_input = dialog.locator(".mud-select-input").first
            assert select_input.is_visible(), "Expected .mud-select-input dropdown in dialog"

            # Click select input to expand the dropdown menu
            select_input.click()
            time.sleep(1)

            # Assert dropdown popover is open and displays active gazetted sets
            page.wait_for_selector(".mud-popover.mud-popover-open", timeout=5000)
            popover = page.locator(".mud-popover.mud-popover-open").first
            popover_text = popover.inner_text()
            print(f"  --> OFO Dropdown Options displayed:\n{popover_text}")
            assert "OFO" in popover_text or "occupations" in popover_text or "Release" in popover_text, \
                "Expected active gazetted OFO sets in dropdown popover"
            print("  --> Gate 4 Passed: Dropdown lookup displaying gazetted OFO versions verified!")

            # Capture Screenshot 3
            save_screenshots(page, "03_propose_window_dropdown_dialog.png")

            # Close dialog cleanly
            cancel_btn = dialog.locator("button:has-text('Cancel')").first
            if cancel_btn.is_visible():
                cancel_btn.click()
            else:
                page.keyboard.press("Escape")
            time.sleep(1)

            print("\n==================================================================")
            print("   ALL OPTION A VERIFICATION GATES PASSED (100% SUCCESS)")
            print("==================================================================")

        finally:
            context.close()
            browser.close()

            # Copy recorded session to root artifact directory
            if os.path.exists(RECORDING_DIR):
                videos = [os.path.join(RECORDING_DIR, f) for f in os.listdir(RECORDING_DIR) if f.endswith(".webm")]
                if videos:
                    latest_video = max(videos, key=os.path.getmtime)
                    target_video = os.path.join(ARTIFACT_DIR, "mg_window_streamlined_verification.webm")
                    shutil.copyfile(latest_video, target_video)
                    print(f"\nSession video recording saved to:\n  {target_video}")

if __name__ == "__main__":
    test_mg_window_master_detail()
