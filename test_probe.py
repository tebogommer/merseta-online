from playwright.sync_api import sync_playwright

def main():
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        page = browser.new_page()
        page.goto('http://localhost:5121/login')
        page.fill('input#username', 'sysadmin@merseta.org.za')
        page.fill('input#password', 'MerSETA@2026!')
        page.click("button[type='submit']")
        page.wait_for_selector('text=Operations Portal', timeout=30000)
        
        urls = [
            'http://localhost:5121/wsp/extensions/1',
            'http://localhost:5121/dg-funding-windows/1/edit',
            'http://localhost:5121/etqa/aqp/1',
            'http://localhost:5121/admin/document-templates/1'
        ]
        
        for u in urls:
            print(f'=== Testing: {u} ===')
            page.goto(u, wait_until='domcontentloaded', timeout=30000)
            page.wait_for_timeout(2000)
            print('URL:', page.url)
            print('Title:', page.title())
            btns = [b.inner_text().strip() for b in page.locator('button').all() if b.inner_text().strip()]
            print('Buttons:', btns[:10])
            headings = [h.inner_text().strip() for h in page.locator('h1, h2, h3, h4, h5, h6').all() if h.inner_text().strip()]
            print('Headings:', headings[:5])
        
        browser.close()

if __name__ == '__main__':
    main()
