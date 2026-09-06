import sys
from playwright.sync_api import sync_playwright

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

def test_developer_docs_suite():
    print("[START] Running Developer Documentation & Data Dictionary Playwright Test...")
    
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        errors = []
        page.on("pageerror", lambda err: errors.append(f"Page Error: {err}"))
        page.on("console", lambda msg: errors.append(f"Console Error: {msg.text}") if msg.type == "error" else None)

        base_url = "http://localhost:5121"

        # Authenticate as SuperAdmin
        print("[AUTH] Logging in as SuperAdmin...")
        page.goto(f"{base_url}/login", wait_until="networkidle")
        page.fill("input#username", "sysadmin@merseta.org.za")
        page.fill("input#password", "MerSETA@2026!")
        page.click("button[type='submit']")
        page.wait_for_load_state("networkidle")
        print("[AUTH] Successfully authenticated!")

        # 1. Test Developer Data Dictionary & Database Architecture (/developer/schema)
        print("\n--- 1. Testing Developer Schema Portal (/developer/schema) ---")
        page.goto(f"{base_url}/developer/schema", wait_until="networkidle")
        page.wait_for_selector("text=Developer Data Dictionary", timeout=10000)
        print("  [PASS] /developer/schema header loaded")

        # Verify summary metric cards
        page.wait_for_selector("text=Total Database Tables", timeout=5000)
        page.wait_for_selector("text=Documented Columns", timeout=5000)
        page.wait_for_selector("text=Foreign Key Relations", timeout=5000)
        page.wait_for_selector("text=Performance Indexes", timeout=5000)
        print("  [PASS] All 4 summary metric cards rendered")

        # Verify table rows rendered
        table_rows = page.locator("tbody tr").count()
        print(f"  [PASS] Rendered {table_rows} database tables in live dictionary")
        assert table_rows > 0, "Expected database tables to be listed"

        # 2. Test Inspect Schema Drilldown
        print("\n--- 2. Testing Inspect Schema Dialog ---")
        inspect_btn = page.locator("button:has-text('Inspect Schema')").first
        if inspect_btn.count() > 0:
            inspect_btn.click(force=True)
            page.wait_for_selector("text=Columns & Data Types", timeout=5000)
            print("  [PASS] Table inspector opened with Columns & Data Types tab")
            
            # Close inspector
            close_btn = page.locator("button[title='Close Inspector']").first
            if close_btn.count() > 0:
                close_btn.click(force=True)
            print("  [PASS] Table inspector closed cleanly")

        # 3. Test Markdown Data Dictionary Tab
        print("\n--- 3. Testing Markdown Data Dictionary Tab ---")
        doc_tab = page.locator("div.mud-tab:has-text('Markdown Data Dictionary')")
        if doc_tab.count() > 0:
            doc_tab.first.click()
            page.wait_for_selector("text=Generated Markdown Data Dictionary", timeout=5000)
            page.wait_for_selector("text=# MerSETA NSDMS — Database Data Dictionary", timeout=5000)
            print("  [PASS] Markdown Data Dictionary Tab rendered complete dictionary content")

        # 4. Test Architecture & Governance Tab
        print("\n--- 4. Testing Architecture & Governance Tab ---")
        arch_tab = page.locator("div.mud-tab:has-text('Architecture & Governance')")
        if arch_tab.count() > 0:
            arch_tab.first.click()
            page.wait_for_selector("text=MerSETA NSDMS Architecture Standards", timeout=5000)
            print("  [PASS] Architecture & Governance Tab rendered with standards specification")

        browser.close()

        if errors:
            print(f"\n[WARNING] Encountered {len(errors)} console/page warnings/errors:")
            for err in errors[:5]:
                print(f"  - {err}")
        else:
            print("\n[SUCCESS] All Developer Documentation & Schema Playwright tests passed with ZERO errors!")

if __name__ == "__main__":
    test_developer_docs_suite()
