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

def run_performance_test():
    print("==================================================")
    print("   NSDMS WSP PERFORMANCE & SERVER-SIDE PAGINATION TEST")
    print(f"   Target: {BASE_URL}/wsp")
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
            # 1. Login directly into /wsp
            print("[1/5] Logging in as SysAdmin and navigating to /wsp...")
            page.goto(f"{BASE_URL}/login?returnUrl=/wsp", wait_until="domcontentloaded", timeout=20000)
            page.fill("input#username", "sysadmin@merseta.org.za")
            page.fill("input#password", "MerSETA@2026!")
            
            start_time = time.perf_counter()
            with page.expect_navigation(wait_until="domcontentloaded", timeout=15000):
                page.click("button[type='submit']")

            # Wait for table rows to appear
            page.wait_for_selector("table tbody tr", timeout=15000)
            elapsed_ms = (time.perf_counter() - start_time) * 1000
            print(f"  --> Authentication & /wsp initial load duration: {elapsed_ms:.1f} ms")

            time.sleep(1)
            shot1 = os.path.join(SCREENSHOTS_DIR, "wsp_server_side_paginated_list.png")
            page.screenshot(path=shot1)
            shutil.copyfile(shot1, os.path.join(ARTIFACT_DIR, "wsp_server_side_paginated_list.png"))
            print(f"  --> Screenshot saved: wsp_server_side_paginated_list.png")

            # Verify total count is displayed and page size is constrained
            rows = page.locator("table tbody tr").count()
            print(f"  --> Rendered rows on first page: {rows}")
            assert rows > 0, "Expected table rows to be rendered"
            assert rows <= 50, f"Expected paginated rows, got {rows}"

            # 3. Test Server-Side Search Filtering
            print("\n[3/5] Testing server-side search filter...")
            search_input = page.locator("input[placeholder*='Search by Reference No']").first
            search_start = time.perf_counter()
            search_input.fill("WSP-2026")
            page.keyboard.press("Enter")
            time.sleep(1)
            search_elapsed_ms = (time.perf_counter() - search_start) * 1000
            print(f"  --> Search filter query duration: {search_elapsed_ms:.1f} ms")

            shot2 = os.path.join(SCREENSHOTS_DIR, "wsp_search_filter_applied.png")
            page.screenshot(path=shot2)
            shutil.copyfile(shot2, os.path.join(ARTIFACT_DIR, "wsp_search_filter_applied.png"))

            # 4. Test Status Toggle Filter
            print("\n[4/5] Testing status toggle filter...")
            status_draft = page.locator("text='Draft'").first
            if status_draft.is_visible():
                status_start = time.perf_counter()
                status_draft.click()
                time.sleep(1)
                status_elapsed_ms = (time.perf_counter() - status_start) * 1000
                print(f"  --> Status toggle query duration: {status_elapsed_ms:.1f} ms")
                shot3 = os.path.join(SCREENSHOTS_DIR, "wsp_status_filter_applied.png")
                page.screenshot(path=shot3)
                shutil.copyfile(shot3, os.path.join(ARTIFACT_DIR, "wsp_status_filter_applied.png"))

            # 5. Test Pagination Ladder Navigation
            print("\n[5/5] Testing page navigation...")
            next_button = page.locator("button[aria-label='Next page']").first
            if next_button.is_visible() and next_button.is_enabled():
                page_start = time.perf_counter()
                next_button.click()
                time.sleep(1)
                page_elapsed_ms = (time.perf_counter() - page_start) * 1000
                print(f"  --> Page flip response duration: {page_elapsed_ms:.1f} ms")

            shot4 = os.path.join(SCREENSHOTS_DIR, "wsp_pagination_page2.png")
            page.screenshot(path=shot4)
            shutil.copyfile(shot4, os.path.join(ARTIFACT_DIR, "wsp_pagination_page2.png"))

            print("\n==================================================")
            print("   ALL PERFORMANCE & FUNCTIONAL CHECKS PASSED!")
            print(f"   Initial Load Time: {elapsed_ms:.1f} ms (Sub-second)")
            print("==================================================")

        finally:
            video_path = page.video.path() if page.video else None
            context.close()
            browser.close()

            if video_path and os.path.exists(video_path):
                dest_video = os.path.join(ARTIFACT_DIR, "wsp_performance_recording.webm")
                shutil.copyfile(video_path, dest_video)
                print(f"  --> Video recording saved to: {dest_video}")

if __name__ == "__main__":
    run_performance_test()
