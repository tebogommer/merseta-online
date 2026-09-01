import os
import time
from playwright.sync_api import sync_playwright

def run():
    artifacts_dir = r"C:\Users\tmoepi\.gemini\antigravity\brain\f0e9048a-752d-4feb-91ea-8579cb078acc"
    video_dir = os.path.join(artifacts_dir, "videos_raw")
    os.makedirs(video_dir, exist_ok=True)

    with sync_playwright() as p:
        browser = p.chromium.launch(
            headless=True,
            args=[
                "--no-sandbox",
                "--disable-gpu",
                "--disable-dev-shm-usage",
                "--window-size=1920,1080"
            ]
        )
        context = browser.new_context(
            viewport={"width": 1920, "height": 1080},
            record_video_dir=video_dir,
            record_video_size={"width": 1920, "height": 1080}
        )
        page = context.new_page()

        print("[Step 1] Navigating to Discretionary Grants (DG) Master Registry (/dg-grants)...")
        page.goto("http://localhost:5121/dg-grants", wait_until="networkidle", timeout=60000)
        time.sleep(2)
        page.screenshot(path=os.path.join(artifacts_dir, "step1_dg_master_registry.png"))

        print("[Step 2] Navigating to DG Funding Windows Registry (/dg-funding-windows)...")
        page.goto("http://localhost:5121/dg-funding-windows", wait_until="networkidle", timeout=60000)
        time.sleep(2)
        page.screenshot(path=os.path.join(artifacts_dir, "step2_dg_funding_windows_hub.png"))

        print("[Step 3] Opening DG Funding Window Detail (/dg-funding-windows/1)...")
        page.goto("http://localhost:5121/dg-funding-windows/1", wait_until="networkidle", timeout=60000)
        time.sleep(2)
        page.screenshot(path=os.path.join(artifacts_dir, "step3_funding_window_detail_config.png"))

        print("[Step 4] Clicking on Tab 2: Strategic Themes & Priorities...")
        tab_themes = page.locator("text='Strategic Themes & Priorities'").first
        if tab_themes.is_visible():
            tab_themes.click()
            time.sleep(2)
        page.screenshot(path=os.path.join(artifacts_dir, "step4_strategic_themes_tab.png"))

        print("[Step 5] Opening DG Grant Application Detail (/dg-grants/1) to verify Strategic Priority masking...")
        page.goto("http://localhost:5121/dg-grants/1", wait_until="networkidle", timeout=60000)
        time.sleep(2)
        page.screenshot(path=os.path.join(artifacts_dir, "step5_dg_application_theme_link.png"))

        print("[Step 6] Navigating to 3-Tier Strategic BI Intelligence Hub (/reports/dg-strategic)...")
        page.goto("http://localhost:5121/reports/dg-strategic", wait_until="networkidle", timeout=60000)
        time.sleep(2)
        page.screenshot(path=os.path.join(artifacts_dir, "step6a_tier1_operational_intelligence.png"))

        print("[Step 7] Viewing Tier 2: Tactical Intelligence (Provincial & SMME Delivery)...")
        tab_tier2 = page.locator("text='Tier 2: Tactical Intelligence'").first
        if tab_tier2.is_visible():
            tab_tier2.click()
            time.sleep(2)
        page.screenshot(path=os.path.join(artifacts_dir, "step6b_tier2_tactical_intelligence.png"))

        print("[Step 8] Viewing Tier 3: Strategic Intelligence (NSDP III Outcomes & Transformation Equity)...")
        tab_tier3 = page.locator("text='Tier 3: Strategic Intelligence'").first
        if tab_tier3.is_visible():
            tab_tier3.click()
            time.sleep(2)
        page.screenshot(path=os.path.join(artifacts_dir, "step6c_tier3_strategic_intelligence.png"))

        # Scroll to inspect transformation equity gauges and NSDP outcomes
        page.mouse.wheel(0, 400)
        time.sleep(2)
        page.screenshot(path=os.path.join(artifacts_dir, "step6d_transformation_equity_gauges.png"))

        print("[Done] Closing context to finalize video recording...")
        context.close()
        browser.close()

        # Rename recorded video
        video_files = [f for f in os.listdir(video_dir) if f.endswith(".webm")]
        if video_files:
            latest_video = os.path.join(video_dir, video_files[-1])
            target_video = os.path.join(artifacts_dir, "dg_strategic_themes_bi_demo.webm")
            if os.path.exists(target_video):
                os.remove(target_video)
            os.rename(latest_video, target_video)
            print(f"[Success] Video saved to: {target_video}")

if __name__ == "__main__":
    run()
