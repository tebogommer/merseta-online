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
    print("================================================================")
    print("   NSDMS CORE REGISTRIES PERFORMANCE & PAGINATION BENCHMARK")
    print(f"   Targets: {BASE_URL}/employers and {BASE_URL}/learners")
    print("================================================================\n")

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(
            viewport={"width": 1440, "height": 900},
            record_video_dir=RECORDING_DIR,
            record_video_size={"width": 1440, "height": 900}
        )
        page = context.new_page()

        try:
            # 1. Login directly into /employers
            print("[1/8] Logging in as SysAdmin and navigating to /employers...")
            page.goto(f"{BASE_URL}/login?returnUrl=/employers", wait_until="domcontentloaded", timeout=20000)
            page.fill("input#username", "sysadmin@merseta.org.za")
            page.fill("input#password", "MerSETA@2026!")
            
            start_time = time.perf_counter()
            with page.expect_navigation(wait_until="domcontentloaded", timeout=15000):
                page.click("button[type='submit']")

            print(f"  --> Logged in, current URL: {page.url}")
            if "/employers" not in page.url:
                page.goto(f"{BASE_URL}/employers", wait_until="domcontentloaded", timeout=15000)

            # Wait for table rows to appear
            page.wait_for_selector("table tbody tr", timeout=15000)
            elapsed_ms = (time.perf_counter() - start_time) * 1000
            print(f"  --> Authentication & /employers initial load duration: {elapsed_ms:.1f} ms")

            time.sleep(1)
            shot1 = os.path.join(SCREENSHOTS_DIR, "employers_server_side_paginated_list.png")
            page.screenshot(path=shot1)
            shutil.copyfile(shot1, os.path.join(ARTIFACT_DIR, "employers_server_side_paginated_list.png"))
            print("  --> Screenshot saved: employers_server_side_paginated_list.png")

            rows = page.locator("table tbody tr").count()
            print(f"  --> Rendered rows on first page: {rows}")
            assert rows > 0, "Expected employer table rows to be rendered"
            assert rows <= 50, f"Expected paginated rows, got {rows}"

            # 2. Test Employer Search Filtering
            print("\n[2/8] Testing /employers server-side search filter...")
            search_input = page.locator("input[placeholder*='Search by SDL Number']").first
            search_start = time.perf_counter()
            search_input.fill("Toyota")
            page.keyboard.press("Enter")
            time.sleep(1)
            search_elapsed_ms = (time.perf_counter() - search_start) * 1000
            print(f"  --> Search filter query duration: {search_elapsed_ms:.1f} ms")

            shot2 = os.path.join(SCREENSHOTS_DIR, "employers_search_filter_applied.png")
            page.screenshot(path=shot2)
            shutil.copyfile(shot2, os.path.join(ARTIFACT_DIR, "employers_search_filter_applied.png"))
            print("  --> Screenshot saved: employers_search_filter_applied.png")

            # 3. Test Employer Status Toggle
            print("\n[3/8] Testing /employers status toggle filter...")
            active_btn = page.locator(".mud-toggle-item:has-text('Active')").first
            if active_btn.is_visible():
                status_start = time.perf_counter()
                active_btn.click()
                time.sleep(1)
                status_elapsed_ms = (time.perf_counter() - status_start) * 1000
                print(f"  --> Status toggle query duration: {status_elapsed_ms:.1f} ms")
                shot3 = os.path.join(SCREENSHOTS_DIR, "employers_status_filter_applied.png")
                page.screenshot(path=shot3)
                shutil.copyfile(shot3, os.path.join(ARTIFACT_DIR, "employers_status_filter_applied.png"))
                print("  --> Screenshot saved: employers_status_filter_applied.png")

            # 4. Test Employer Pagination Next Page
            print("\n[4/8] Testing /employers pagination navigation...")
            next_btn = page.locator("button[aria-label='Next page']").first
            if next_btn.is_visible() and next_btn.is_enabled():
                page_start = time.perf_counter()
                next_btn.click()
                time.sleep(1)
                page_elapsed_ms = (time.perf_counter() - page_start) * 1000
                print(f"  --> Next page query duration: {page_elapsed_ms:.1f} ms")
                shot4 = os.path.join(SCREENSHOTS_DIR, "employers_pagination_page2.png")
                page.screenshot(path=shot4)
                shutil.copyfile(shot4, os.path.join(ARTIFACT_DIR, "employers_pagination_page2.png"))
                print("  --> Screenshot saved: employers_pagination_page2.png")

            # 5. Navigate to /learners
            print("\n[5/8] Navigating to /learners...")
            learner_start = time.perf_counter()
            page.goto(f"{BASE_URL}/learners", wait_until="domcontentloaded", timeout=15000)
            page.wait_for_selector("table tbody tr", timeout=15000)
            learner_load_ms = (time.perf_counter() - learner_start) * 1000
            print(f"  --> /learners initial load duration: {learner_load_ms:.1f} ms")

            time.sleep(1)
            shot5 = os.path.join(SCREENSHOTS_DIR, "learners_server_side_paginated_list.png")
            page.screenshot(path=shot5)
            shutil.copyfile(shot5, os.path.join(ARTIFACT_DIR, "learners_server_side_paginated_list.png"))
            print("  --> Screenshot saved: learners_server_side_paginated_list.png")

            learner_rows = page.locator("table tbody tr").count()
            print(f"  --> Rendered rows on first page: {learner_rows}")
            assert learner_rows > 0, "Expected learner table rows to be rendered"
            assert learner_rows <= 50, f"Expected paginated rows, got {learner_rows}"

            # 6. Test Learner Search Filter
            print("\n[6/8] Testing /learners search filter...")
            learner_search = page.locator("input[placeholder*='Search contract number']").first
            if learner_search.is_visible():
                l_search_start = time.perf_counter()
                learner_search.fill("LRN")
                page.keyboard.press("Enter")
                time.sleep(1)
                l_search_ms = (time.perf_counter() - l_search_start) * 1000
                print(f"  --> Learner search filter query duration: {l_search_ms:.1f} ms")
                shot6 = os.path.join(SCREENSHOTS_DIR, "learners_search_filter_applied.png")
                page.screenshot(path=shot6)
                shutil.copyfile(shot6, os.path.join(ARTIFACT_DIR, "learners_search_filter_applied.png"))
                print("  --> Screenshot saved: learners_search_filter_applied.png")

            # 7. Test Learner Dropdown Filter
            print("\n[7/8] Testing /learners status dropdown filter...")
            status_select = page.locator("text='Status Filter'").first
            if status_select.is_visible():
                status_select.click()
                time.sleep(0.5)
                reg_opt = page.locator(".mud-list-item:has-text('Registered')").first
                if reg_opt.is_visible():
                    l_status_start = time.perf_counter()
                    reg_opt.click()
                    time.sleep(1)
                    l_status_ms = (time.perf_counter() - l_status_start) * 1000
                    print(f"  --> Learner status select query duration: {l_status_ms:.1f} ms")
                    shot7 = os.path.join(SCREENSHOTS_DIR, "learners_status_filter_applied.png")
                    page.screenshot(path=shot7)
                    shutil.copyfile(shot7, os.path.join(ARTIFACT_DIR, "learners_status_filter_applied.png"))
                    print("  --> Screenshot saved: learners_status_filter_applied.png")

            # 8. Test Learner Pagination
            print("\n[8/8] Testing /learners pagination navigation...")
            l_next_btn = page.locator("button[aria-label='Next page']").first
            if l_next_btn.is_visible() and l_next_btn.is_enabled():
                l_page_start = time.perf_counter()
                l_next_btn.click()
                time.sleep(1)
                l_page_ms = (time.perf_counter() - l_page_start) * 1000
                print(f"  --> Learner next page query duration: {l_page_ms:.1f} ms")
                shot8 = os.path.join(SCREENSHOTS_DIR, "learners_pagination_page2.png")
                page.screenshot(path=shot8)
                shutil.copyfile(shot8, os.path.join(ARTIFACT_DIR, "learners_pagination_page2.png"))
                print("  --> Screenshot saved: learners_pagination_page2.png")

            print("\n================================================================")
            print("  ALL BENCHMARKS COMPLETED SUCCESSFULLY!")
            print("================================================================")

        finally:
            page.close()
            context.close()
            browser.close()

            # Find video file and copy
            video_files = [f for f in os.listdir(RECORDING_DIR) if f.endswith(".webm")]
            if video_files:
                latest_video = max([os.path.join(RECORDING_DIR, f) for f in video_files], key=os.path.getmtime)
                dest_video = os.path.join(ARTIFACT_DIR, "core_registries_performance_recording.webm")
                shutil.copyfile(latest_video, dest_video)
                print(f"\n[Artifact Video] Video recording copied to: {dest_video}")

if __name__ == "__main__":
    run_performance_test()
