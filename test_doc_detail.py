from playwright.sync_api import sync_playwright

with sync_playwright() as p:
    browser = p.chromium.launch(headless=True)
    page = browser.new_page()
    page.goto('http://localhost:5121/login')
    page.fill('input#username', 'sysadmin@merseta.org.za')
    page.fill('input#password', 'MerSETA@2026!')
    page.click("button[type='submit']")
    page.wait_for_selector('text=Operations Portal', timeout=30000)
    
    page.goto('http://localhost:5121/admin/document-templates/1', wait_until='domcontentloaded', timeout=40000)
    page.wait_for_timeout(3000)
    print("Alerts:", [a.inner_text().strip() for a in page.locator('.mud-alert').all()])
    print("Text snippets:", page.locator('main').inner_text()[:400] if page.locator('main').count() > 0 else "No main")
    browser.close()
