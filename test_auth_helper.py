import sys
from playwright.sync_api import sync_playwright

def test_login():
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context()
        page = context.new_page()

        print("Navigating to /login...")
        page.goto("http://localhost:5121/login")
        page.fill("input#username", "sysadmin@merseta.org.za")
        page.fill("input#password", "MerSETA@2026!")
        page.click("button[type='submit']")
        page.wait_for_load_state("networkidle")
        print(f"Logged in successfully. Current URL: {page.url}")

        print("Navigating to /developer/schema...")
        page.goto("http://localhost:5121/developer/schema", wait_until="networkidle")
        print(f"URL: {page.url}")
        print(f"Title: {page.title()}")
        body_text = page.locator("body").inner_text()
        print(f"Body preview:\n{body_text[:300]}")

        # Check if Developer Data Dictionary is present
        assert "Developer Data Dictionary" in body_text, "Failed to find 'Developer Data Dictionary' on page"
        print("SUCCESS! Authenticated navigation works perfectly!")

        browser.close()

if __name__ == "__main__":
    test_login()
