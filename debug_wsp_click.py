import sys
from playwright.sync_api import sync_playwright

with sync_playwright() as p:
    browser = p.chromium.launch(headless=True)
    page = browser.new_page()
    page.goto("http://localhost:5121/wsp", wait_until="networkidle")
    print("On WSP List. URL:", page.url)
    
    btn = page.locator("button[aria-label='Edit Record']").first
    print("Found edit button:", btn.count())
    btn.click()
    page.wait_for_timeout(3000)
    print("Current URL after click:", page.url)
    print("HTML Content excerpt:", page.content()[:500])
    browser.close()
