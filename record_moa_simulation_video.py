import os
import shutil
import time
from playwright.sync_api import sync_playwright

def record_moa_pdf_simulation():
    print("[1/7] Initializing Playwright Video Recorder for MoA & Document PDF Simulation...")
    
    # Paths setup
    project_rec_dir = os.path.abspath("recordings")
    artifact_dir = r"C:\Users\tmoepi\.gemini\antigravity\brain\60c43e9a-4bd7-4fbf-8a29-3f1edb69c17c"
    
    os.makedirs(project_rec_dir, exist_ok=True)
    os.makedirs(artifact_dir, exist_ok=True)
    
    base_url = "http://localhost:5121"
    
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(
            record_video_dir=project_rec_dir,
            record_video_size={"width": 1440, "height": 900},
            viewport={"width": 1440, "height": 900}
        )
        
        page = context.new_page()
        page.set_default_timeout(15000)
        
        # Step 1: MoA Templates Master Registry
        print("[2/7] Navigating to /legal/moa-templates (MoA Template Studio)...")
        page.goto(f"{base_url}/legal/moa-templates", wait_until="networkidle")
        page.wait_for_selector(".mud-table", timeout=15000)
        time.sleep(1.5)
        
        shot1 = os.path.join(artifact_dir, "01_moa_template_registry.png")
        page.screenshot(path=shot1, full_page=False)
        print(f"  Captured: {shot1}")
        
        # Step 2: Open MoA Template Detail (Template 1)
        print("[3/7] Opening MoA Template #1 Detail Hub (/legal/moa-templates/1)...")
        page.goto(f"{base_url}/legal/moa-templates/1", wait_until="networkidle")
        page.wait_for_selector(".mud-tabs", timeout=15000)
        time.sleep(1.5)
        
        shot2 = os.path.join(artifact_dir, "02_moa_template_general.png")
        page.screenshot(path=shot2, full_page=False)
        print(f"  Captured: {shot2}")
        
        # Step 3: Clause Assembly Tab
        print("  Navigating to Clause Structure Tab (Tab 2)...")
        tab2 = page.locator(".mud-tab").filter(has_text="Clause")
        if tab2.count() > 0:
            tab2.first.click(force=True)
            time.sleep(2.0)
            shot3 = os.path.join(artifact_dir, "03_moa_template_clauses.png")
            page.screenshot(path=shot3, full_page=False)
            print(f"  Captured: {shot3}")
            
        # Step 4: Live Simulation & PDF Export Tab
        print("[4/7] Navigating to 'Live Simulation & PDF Export' Workspace (Tab 3)...")
        tab3 = page.locator(".mud-tab").filter(has_text="Simulation")
        tab3.first.click(force=True)
        time.sleep(4.0)
        
        # Wait for the simulation content
        page.wait_for_selector(".mud-grid", timeout=15000)
        time.sleep(1.5)
        
        shot4 = os.path.join(artifact_dir, "04_moa_live_simulation_tab.png")
        page.screenshot(path=shot4, full_page=False)
        print(f"  Captured: {shot4}")
        
        # Step 5: Scenario Profile Switching
        print("  Switching Scenario Profile to 'Public TVET College Partnership'...")
        scenario_select = page.locator(".mud-select").first
        if scenario_select.count() > 0:
            scenario_select.click(force=True)
            time.sleep(0.8)
            tvet_option = page.locator(".mud-list-item:has-text('TVET College'), .mud-list-item").nth(2)
            if tvet_option.count() > 0:
                tvet_option.click(force=True)
                time.sleep(2.5)
                shot5 = os.path.join(artifact_dir, "05_moa_scenario_changed.png")
                page.screenshot(path=shot5, full_page=False)
                print(f"  Captured: {shot5}")

        # Step 6: Custom Token Overrides & Re-rendering
        print("[5/7] Overriding Custom Tokens and Updating Preview...")
        # Find employer name text field
        emp_field = page.locator("input").nth(1)
        if emp_field.count() > 0:
            emp_field.fill("Ekurhuleni East TVET College & merSETA Digital Centre")
            time.sleep(0.5)
            
        # Click Update Preview
        update_btn = page.locator("button:has-text('Update Preview')").first
        if update_btn.count() > 0:
            update_btn.click(force=True)
            time.sleep(3)
            shot6 = os.path.join(artifact_dir, "06_moa_custom_tokens_preview.png")
            page.screenshot(path=shot6, full_page=False)
            print(f"  Captured: {shot6}")
            
        # Toggle Raw Markdown View
        raw_btn = page.locator("button:has-text('Show Raw Markdown')").first
        if raw_btn.count() > 0:
            raw_btn.click(force=True)
            time.sleep(1.5)
            shot7 = os.path.join(artifact_dir, "07_moa_raw_markdown_view.png")
            page.screenshot(path=shot7, full_page=False)
            print(f"  Captured: {shot7}")
            
            # Switch back to PDF
            pdf_btn = page.locator("button:has-text('Show PDF View')").first
            if pdf_btn.count() > 0:
                pdf_btn.click(force=True)
                time.sleep(1.5)
                
        # Test Download Simulated MoA (PDF) button
        print("  Testing Download Simulated MoA (PDF) Action...")
        download_btn = page.locator("button:has-text('Download Simulated MoA')").first
        if download_btn.count() > 0:
            try:
                download_btn.click(force=True)
                time.sleep(1.5)
                shot8 = os.path.join(artifact_dir, "08_moa_pdf_downloaded.png")
                page.screenshot(path=shot8, full_page=False)
                print(f"  [PASS] Clicked Download Simulated MoA button: {shot8}")
            except Exception as ex:
                print(f"  Download note: {ex}")
                
        # Step 7: Executed Grant MoA Contract Detail View
        print("[6/7] Navigating to Executed Grant MoA (/finance/grants/1)...")
        page.goto(f"{base_url}/finance/grants/1", wait_until="networkidle")
        page.wait_for_selector(".sticky-top", timeout=15000)
        time.sleep(1.5)
        
        shot9 = os.path.join(artifact_dir, "09_executed_grant_moa_detail.png")
        page.screenshot(path=shot9, full_page=False)
        print(f"  Captured: {shot9}")
        
        # Step 8: Universal Document Template Simulation
        print("[7/7] Navigating to Universal Document Template #1 (/admin/document-templates/1)...")
        page.goto(f"{base_url}/admin/document-templates/1", wait_until="networkidle")
        page.wait_for_selector(".mud-tabs", timeout=15000)
        time.sleep(1.5)
        
        doc_tab3 = page.locator(".mud-tab").nth(2)
        if doc_tab3.count() > 0:
            doc_tab3.click(force=True)
            time.sleep(3)
            shot10 = os.path.join(artifact_dir, "10_universal_doc_simulation.png")
            page.screenshot(path=shot10, full_page=False)
            print(f"  Captured: {shot10}")
            
        time.sleep(2)
        
        # Save video path
        video_path = page.video.path()
        context.close()
        browser.close()
        
        print("\n[COMPLETE] Video recording finalized.")
        print(f"Original video location: {video_path}")
        
        # Copy to clean destination in artifacts
        dest_video = os.path.join(artifact_dir, "moa_pdf_simulation_recording.webm")
        wwwroot_rec = os.path.join("dotnet", "Nsdms.Web", "wwwroot", "recordings")
        os.makedirs(wwwroot_rec, exist_ok=True)
        wwwroot_video = os.path.join(wwwroot_rec, "moa_pdf_simulation_recording.webm")
        
        if os.path.exists(video_path):
            shutil.copy2(video_path, dest_video)
            shutil.copy2(video_path, wwwroot_video)
            print(f"Artifact video link: {dest_video}")
            print(f"Web server video link: http://localhost:5121/recordings/moa_pdf_simulation_recording.webm")
            
            # Generate player HTML
            html_content = f"""<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>merSETA NSDMS - Live MoA & Document PDF Simulation Recording</title>
    <style>
        body {{ background: #121418; color: #f0f2f5; font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, sans-serif; padding: 24px; margin: 0; }}
        .container {{ max-width: 1200px; margin: 0 auto; }}
        header {{ display: flex; justify-content: space-between; align-items: center; border-bottom: 1px solid #333a46; padding-bottom: 16px; margin-bottom: 24px; flex-wrap: wrap; gap: 12px; }}
        .badge {{ background: #b8860b; color: #000; font-weight: bold; padding: 4px 10px; border-radius: 6px; font-size: 13px; }}
        .player-card {{ background: #1e222a; border-radius: 12px; overflow: hidden; border: 1px solid #333a46; margin-bottom: 24px; box-shadow: 0 10px 30px rgba(0,0,0,0.5); }}
        video {{ width: 100%; max-height: 680px; background: #000; display: block; }}
        .toolbar {{ display: flex; justify-content: space-between; align-items: center; padding: 12px 20px; background: #282d37; border-top: 1px solid #333a46; flex-wrap: wrap; gap: 10px; }}
        .btn {{ background: #374151; color: #fff; border: 1px solid #4b5563; padding: 8px 14px; border-radius: 6px; text-decoration: none; display: inline-flex; align-items: center; gap: 6px; font-weight: 600; cursor: pointer; font-size: 13px; transition: all 0.2s; }}
        .btn:hover, .btn.active {{ background: #b8860b; color: #000; border-color: #b8860b; }}
        .chapters {{ display: grid; grid-template-columns: repeat(auto-fit, minmax(260px, 1fr)); gap: 12px; margin-bottom: 24px; }}
        .chapter-card {{ background: #1e222a; border: 1px solid #333a46; border-radius: 8px; padding: 14px; cursor: pointer; transition: all 0.2s; }}
        .chapter-card:hover {{ border-color: #b8860b; transform: translateY(-2px); }}
        .time {{ color: #b8860b; font-weight: bold; font-size: 13px; margin-bottom: 4px; }}
        .title {{ font-weight: 600; font-size: 14px; color: #fff; }}
        .desc {{ color: #9aa4b2; font-size: 12px; margin-top: 4px; }}
    </style>
</head>
<body>
    <div class="container">
        <header>
            <div>
                <span class="badge">merSETA NSDMS</span>
                <h1 style="margin: 8px 0 4px 0; font-size: 22px;">MoA PDF Simulation & Live Export Video Player</h1>
                <p style="color: #9aa4b2; font-size: 14px;">E2E Playwright Session & QuestPDF Generation Engine</p>
            </div>
            <div style="display: flex; gap: 8px; flex-wrap: wrap;">
                <a href="/legal/moa-templates/1" target="_blank" class="btn">Open MoA Studio</a>
                <a href="moa_pdf_simulation_recording.webm" download class="btn">⬇ Download .webm</a>
                <a href="moa_simulation_demo.gif" download class="btn">⬇ Download .gif</a>
            </div>
        </header>
        <div class="player-card">
            <video id="videoPlayer" controls autoplay muted loop playsinline>
                <source src="moa_pdf_simulation_recording.webm" type="video/webm">
                Your browser does not support the video tag.
            </video>
            <div class="toolbar">
                <div style="display: flex; gap: 8px; align-items: center;">
                    <span style="font-size: 13px; color: #9aa4b2;">Speed:</span>
                    <button class="btn" onclick="setSpeed(0.75, this)">0.75x</button>
                    <button class="btn active" onclick="setSpeed(1.0, this)">1.0x</button>
                    <button class="btn" onclick="setSpeed(1.5, this)">1.5x</button>
                    <button class="btn" onclick="setSpeed(2.0, this)">2.0x</button>
                </div>
                <div style="display: flex; gap: 8px;">
                    <button class="btn" onclick="restartVideo()">⏮ Restart</button>
                    <button class="btn" id="loopBtn" onclick="toggleLoop()">🔁 Loop: ON</button>
                </div>
            </div>
        </div>
        <h3 style="margin-bottom: 12px; font-size: 16px;">Interactive Workflow Chapters (Click to Jump)</h3>
        <div class="chapters">
            <div class="chapter-card" onclick="seekTo(0)">
                <div class="time">⏱ 00:00</div>
                <div class="title">1. MoA Template Registry</div>
                <div class="desc">Master list of all legal templates, approval statuses, and version history.</div>
            </div>
            <div class="chapter-card" onclick="seekTo(4)">
                <div class="time">⏱ 00:04</div>
                <div class="title">2. Policy & Metadata Hub</div>
                <div class="desc">Financial scheme year, grant categories, effective dates, and signoff actors.</div>
            </div>
            <div class="chapter-card" onclick="seekTo(9)">
                <div class="time">⏱ 00:09</div>
                <div class="title">3. Clause Section Structure</div>
                <div class="desc">Ordered legal clauses with drag-and-drop reordering and clause mapping.</div>
            </div>
            <div class="chapter-card" onclick="seekTo(15)">
                <div class="time">⏱ 00:15</div>
                <div class="title">4. Live PDF Simulation Workspace</div>
                <div class="desc">Split studio: Scenario selector & token inspector alongside live QuestPDF preview.</div>
            </div>
            <div class="chapter-card" onclick="seekTo(22)">
                <div class="time">⏱ 00:22</div>
                <div class="title">5. TVET Scenario & Token Overrides</div>
                <div class="desc">Dynamically switching to TVET college profile and updating custom agreement values.</div>
            </div>
            <div class="chapter-card" onclick="seekTo(29)">
                <div class="time">⏱ 00:29</div>
                <div class="title">6. Executed Statutory Contract</div>
                <div class="desc">Workflow timeline, committed budget, and statutory Download MOA (PDF) button.</div>
            </div>
        </div>
        <h3 style="margin: 24px 0 12px 0; font-size: 16px;">Animated Walkthrough</h3>
        <div style="border-radius: 8px; overflow: hidden; border: 1px solid #333a46; margin-bottom: 24px;">
            <img src="moa_simulation_demo.gif" style="width: 100%; display: block;" alt="Animated Walkthrough">
        </div>
    </div>
    <script>
        const v = document.getElementById('videoPlayer');
        function setSpeed(speed, btn) {{
            v.playbackRate = speed;
            document.querySelectorAll('.toolbar button').forEach(b => {{
                if (b.innerText.endsWith('x')) b.classList.remove('active');
            }});
            btn.classList.add('active');
        }}
        function seekTo(seconds) {{
            v.currentTime = seconds;
            v.play();
        }}
        function restartVideo() {{
            v.currentTime = 0;
            v.play();
        }}
        function toggleLoop() {{
            v.loop = !v.loop;
            const btn = document.getElementById('loopBtn');
            btn.innerText = v.loop ? '🔁 Loop: ON' : '🔁 Loop: OFF';
            if (v.loop) btn.classList.add('active'); else btn.classList.remove('active');
        }}
    </script>
</body>
</html>"""
            with open(os.path.join(wwwroot_rec, "index.html"), "w", encoding="utf-8") as f:
                f.write(html_content)
            with open(os.path.join(artifact_dir, "player.html"), "w", encoding="utf-8") as f:
                f.write(html_content)
            print(f"Generated Web Player: http://localhost:5121/recordings/index.html")
            
            return dest_video
            
    return None

if __name__ == "__main__":
    record_moa_pdf_simulation()

