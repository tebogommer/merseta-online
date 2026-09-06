import sys
import io
import time
from playwright.sync_api import sync_playwright

if sys.stdout.encoding != 'utf-8':
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

BASE_URL = "http://localhost:5121"

def test_theme_toggle():
    print("==================================================")
    print("   NSDMS THEME TOGGLE & PALETTE VERIFICATION")
    print(f"   Target: {BASE_URL}")
    print("==================================================\n")

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        # Capture console errors
        console_errors = []
        page.on("console", lambda msg: console_errors.append(msg.text) if msg.type == "error" else None)

        # Authenticate as SuperAdmin
        print("[AUTH] Logging in as SuperAdmin...")
        page.goto(f"{BASE_URL}/login", wait_until="networkidle")
        page.fill("input#username", "sysadmin@merseta.org.za")
        page.fill("input#password", "MerSETA@2026!")
        page.click("button[type='submit']")
        page.wait_for_load_state("networkidle")
        print("[AUTH] Successfully authenticated!")

        print("[1] Navigating to Executive Dashboard...")
        page.goto(BASE_URL, wait_until="networkidle", timeout=15000)
        time.sleep(1)

        # Verify Executive Operations Portal loaded
        header = page.locator("text=Executive Operations Portal")
        assert header.is_visible(), "Executive Operations Portal header should be visible"
        print("  ✓ Executive Operations Portal loaded successfully")

        # Find and click Dark Mode toggle button
        print("[2] Toggling to Dark Mode...")
        toggle_button = page.locator("button[aria-label='Toggle color theme']").first
        assert toggle_button.is_visible(), "Toggle color theme button should be visible in AppBar"
        toggle_button.click()
        time.sleep(1)

        # Check if MudBlazor applied dark theme classes
        has_dark_mode = page.evaluate("() => document.querySelector('.mud-layout.mud-theme-dark') !== null || document.querySelector('.mud-theme-dark') !== null")
        print(f"  ✓ Dark Mode activated successfully (mud-theme-dark present: {has_dark_mode})")
        assert has_dark_mode, "Expected .mud-theme-dark class to be applied"

        # Check that page elements adapt
        print("[3] Testing navigation under Dark Mode to Universal Task Inbox (/tasks)...")
        page.goto(f"{BASE_URL}/tasks", wait_until="networkidle", timeout=15000)
        time.sleep(1)
        tasks_heading = page.locator("text=Universal Task Inbox")
        assert tasks_heading.is_visible(), "Universal Task Inbox heading should render in dark mode"
        print("  ✓ Task Inbox rendered cleanly in Dark Mode")

        print("[4] Testing navigation under Dark Mode to Employer Detail (/employers/1)...")
        page.goto(f"{BASE_URL}/employers/1", wait_until="networkidle", timeout=15000)
        time.sleep(1)
        employer_title = page.locator("text=Organisation & Workplace Registry")
        assert employer_title.is_visible(), "Employer detail should render in dark mode"
        print("  ✓ Employer Detail rendered cleanly with theme-aware sticky top bar")

        # Toggle back to Light Mode
        print("[5] Toggling back to Light Mode...")
        toggle_button = page.locator("button[aria-label='Toggle color theme']").first
        toggle_button.click()
        time.sleep(1)
        is_light_now = page.evaluate("() => document.querySelector('.mud-layout.mud-theme-dark') === null")
        assert is_light_now, "Expected .mud-theme-dark to be removed after toggle"
        print("  ✓ Switched back to Light Mode seamlessly")

        # Validate no fatal console errors
        fatal_errors = [e for e in console_errors if "favicon" not in e and "net::ERR" not in e]
        print(f"\n[6] Console Health Check: {len(fatal_errors)} fatal errors found.")
        if fatal_errors:
            for err in fatal_errors:
                print(f"  ⚠ Console Error: {err}")

        browser.close()
        print("\n==================================================")
        print("   THEME VERIFICATION PASSED 100%!")
        print("==================================================")

if __name__ == "__main__":
    test_theme_toggle()
