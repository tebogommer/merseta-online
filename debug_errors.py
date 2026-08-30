import sys
from playwright.sync_api import sync_playwright

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

with sync_playwright() as p:
    browser = p.chromium.launch(headless=True)
    page = browser.new_page()
    page.goto("http://localhost:5121/", wait_until="networkidle")
    print("=== ERROR ON / ===")
    print(page.inner_text("body")[:1000])

    page.goto("http://localhost:5121/employers/1", wait_until="networkidle")
    print("\n=== ERROR ON /employers/1 ===")
    print(page.inner_text("body")[:1000])
    browser.close()
