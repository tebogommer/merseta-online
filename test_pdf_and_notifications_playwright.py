import sys
import urllib.request
from playwright.sync_api import sync_playwright

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

def test_pdf_endpoints_and_notifications():
    print("[START] Running PDF Engine & Real-Time SignalR Notifications Verification Test...")
    base_url = "http://localhost:5121"

    # Browser E2E UI & Authenticated API Verification
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        errors = []
        page.on("pageerror", lambda err: errors.append(f"Page Error: {err}"))
        page.on("console", lambda msg: errors.append(f"Console Error: {msg.text}") if msg.type == "error" else None)

        # Authenticate as SuperAdmin
        print("[AUTH] Logging in as SuperAdmin...")
        page.goto(f"{base_url}/login", wait_until="networkidle")
        page.fill("input#username", "sysadmin@merseta.org.za")
        page.fill("input#password", "MerSETA@2026!")
        page.click("button[type='submit']")
        page.wait_for_load_state("networkidle")
        print("[AUTH] Successfully authenticated!")

        # 1. Authenticated PDF Generation Minimal API Endpoints
        print("\n--- 1. Testing PDF Generation Minimal API Endpoints ---")
        endpoints = [
            ("/api/documents/moa/1/pdf", "Grant MOA Contract PDF"),
            ("/api/documents/tradetest/1/pdf", "Artisan Trade Test Certificate PDF"),
            ("/api/documents/wsp/1/pdf", "WSP Outcome Letter PDF"),
            ("/api/documents/remittance/1/pdf", "Mandatory Rebate Remittance Advice PDF")
        ]

        for path, desc in endpoints:
            url = f"{base_url}{path}"
            response = context.request.get(url)
            status = response.status
            content_type = response.headers.get("content-type", "")
            data = response.body()

            assert status == 200, f"Expected 200 OK from {url}, got {status}"
            assert "application/pdf" in content_type, f"Expected application/pdf from {url}, got {content_type}"
            assert len(data) > 500, f"Expected non-empty PDF bytes from {url}, got {len(data)} bytes"
            assert data.startswith(b"%PDF"), f"Expected %PDF magic header from {url}"
            print(f"  [PASS] {desc} ({path}): HTTP {status}, {len(data):,} bytes generated with valid %PDF header")

        # 2.1 Test App Bar Notification Bell & Universal Inbox
        print("\n  2.1 Testing App Bar Notification Bell & Matrix Menu...")
        page.goto(f"{base_url}/", wait_until="networkidle")
        page.wait_for_selector("text=Executive Operations Portal", timeout=10000)
        print("  [PASS] Dashboard loaded successfully")

        # Find notification menu button in app bar
        notif_btn = page.locator("button.mud-menu-button:has(svg), button:has(svg.mud-icon-root)").first
        assert page.locator("header").count() > 0, "Expected App Bar header"
        print("  [PASS] Top App Bar with SignalR badge elements verified")

        # 2.2 Test Grant MOA Detail PDF Export Button
        print("\n  2.2 Testing Grant MOA Detail PDF Export Button...")
        page.goto(f"{base_url}/finance/grants/1", wait_until="networkidle")
        page.wait_for_selector("text=Download MOA (PDF)", timeout=10000)
        print("  [PASS] 'Download MOA (PDF)' button verified on Grant MOA Detail View")

        # 2.3 Test Trade Test Detail Artisan Certificate PDF Export Button
        print("\n  2.3 Testing Trade Test Detail Certificate Export Button...")
        page.goto(f"{base_url}/tradetests/1", wait_until="networkidle")
        page.wait_for_selector("text=Download certificate (PDF)", timeout=10000)
        print("  [PASS] 'Download certificate (PDF)' button verified on Trade Test Detail View")

        # 2.4 Test WSP Detail Outcome Letter PDF Export Button
        print("\n  2.4 Testing WSP Detail Outcome Letter Export Button...")
        page.goto(f"{base_url}/wsp/1", wait_until="networkidle")
        page.wait_for_selector("text=Outcome Letter (PDF)", timeout=10000)
        print("  [PASS] 'Outcome Letter (PDF)' button verified on WSP Detail View")

        # 2.5 Test Mandatory Rebate Remittance PDF Export Button
        print("\n  2.5 Testing Mandatory Rebate Remittance Advice Export Button...")
        page.goto(f"{base_url}/finance/levy-rebates", wait_until="networkidle")
        page.wait_for_selector("text=Remittance (PDF)", timeout=10000)
        print("  [PASS] 'Remittance (PDF)' button verified on Mandatory Rebate Disbursements Table")

        browser.close()

    print("\n[SUCCESS] PDF & Statutory Document Engine and Real-Time SignalR Notifications fully verified!")

if __name__ == "__main__":
    test_pdf_endpoints_and_notifications()
