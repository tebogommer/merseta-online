import os, time
from playwright.sync_api import sync_playwright

with sync_playwright() as p:
    b = p.chromium.launch(headless=True)
    page = b.new_page(viewport={'width': 1440, 'height': 900})
    
    # Login
    print("Navigating to login...")
    page.goto('http://localhost:5121/login', wait_until='domcontentloaded', timeout=30000)
    page.fill('input#username', 'sysadmin@merseta.org.za')
    page.fill('input#password', 'MerSETA@2026!')
    page.click('button[type=submit]')
    page.wait_for_selector('text=Operations Portal', timeout=25000)
    print("Logged in successfully.")
    
    # Navigate to /people
    print("Navigating to /people...")
    page.goto('http://localhost:5121/people', wait_until='domcontentloaded', timeout=30000)
    page.wait_for_selector('text=People', timeout=25000)
    time.sleep(2)
    
    out_path = r'D:\nsdms-test\screenshots\test_styled_people.png'
    page.screenshot(path=out_path)
    print('Screenshot saved successfully to:', out_path)
    b.close()
