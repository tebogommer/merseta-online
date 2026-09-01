import os
import time
from playwright.sync_api import sync_playwright

def capture_theme_showcase():
    output_dir = r"C:\Users\tmoepi\.gemini\antigravity\brain\60c43e9a-4bd7-4fbf-8a29-3f1edb69c17c"
    os.makedirs(output_dir, exist_ok=True)

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        views = [
            ("01_executive_operations_dashboard.png", "http://localhost:5121/"),
            ("02_universal_task_inbox.png", "http://localhost:5121/tasks"),
            ("03_organisations_master_grid.png", "http://localhost:5121/employers"),
            ("04_wsp_submissions_grid.png", "http://localhost:5121/wsp"),
            ("05_dg_grants_grid.png", "http://localhost:5121/dg-grants"),
            ("06_learners_directory.png", "http://localhost:5121/learners"),
            ("07_moa_template_detail.png", "http://localhost:5121/legal/moa-templates/1")
        ]

        for filename, url in views:
            print(f"Navigating to {url}...")
            try:
                page.goto(url, wait_until="networkidle", timeout=25000)
                time.sleep(1.5)
                filepath = os.path.join(output_dir, filename)
                page.screenshot(path=filepath, full_page=False)
                print(f"Captured {filename}")
            except Exception as e:
                print(f"Error capturing {url}: {e}")

        browser.close()

if __name__ == "__main__":
    capture_theme_showcase()
