from playwright.sync_api import sync_playwright

with sync_playwright() as p:
    browser = p.chromium.launch(headless=True)
    page = browser.new_page()
    page.goto("http://localhost:5121/wsp", wait_until="networkidle")
    
    # Click the first row action or row
    first_view_btn = page.locator("a[href*='/wsp/'], button:has-text('View'), tr.cursor-pointer").first
    print("Found element:", first_view_btn.text_content())
    page.locator("tr.cursor-pointer").first.click()
    page.wait_for_timeout(2000)
    print("Navigated to:", page.url)
    print("Page Title/Header:", page.locator("h1, h2, h6").all_text_contents())
    print("Workflow bridge text:", page.locator(".workflow-action-bridge").all_text_contents())
    browser.close()
