import sys
from playwright.sync_api import sync_playwright

with sync_playwright() as p:
    browser = p.chromium.launch(headless=True)
    page = browser.new_page()
    page.goto("http://localhost:5121/wsp", wait_until="networkidle")
    print("URL on /wsp:", page.url)
    rows = page.locator("tr").all_text_contents()
    print("WSP Table Rows:")
    for r in rows[:6]:
        print(" ", r.strip())
    browser.close()
