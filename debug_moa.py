import sys
from playwright.sync_api import sync_playwright

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

with sync_playwright() as p:
    browser = p.chromium.launch(headless=True)
    page = browser.new_page()
    page.goto("http://localhost:5121/finance/grants/1", wait_until="networkidle")
    print("Page title:", page.title())
    text = page.inner_text("body")
    print(text[:2000])
    browser.close()
