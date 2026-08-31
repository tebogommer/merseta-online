from playwright.sync_api import sync_playwright

with sync_playwright() as p:
    browser = p.chromium.launch(headless=True)
    page = browser.new_page()

    for url in ["http://localhost:5121/people/1", "http://localhost:5121/finance/banking-details/1"]:
        print(f"\n--- Visiting {url} ---")
        page.on("console", lambda m: print(f"Console: {m.type}: {m.text}"))
        page.on("pageerror", lambda e: print(f"PageError: {e}"))
        try:
            resp = page.goto(url, wait_until="networkidle", timeout=10000)
            print(f"Status: {resp.status if resp else 'None'}")
            text = page.inner_text("body")
            print("Body Text (first 500 chars):")
            print(text[:500])
        except Exception as ex:
            print(f"Exception: {ex}")

    browser.close()
