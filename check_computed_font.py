from playwright.sync_api import sync_playwright

with sync_playwright() as p:
    browser = p.chromium.launch()
    page = browser.new_page()
    page.goto("http://localhost:5121/dashboard", wait_until="networkidle")
    
    # Inspect computed font-family across elements
    elements_to_check = [
        "body",
        ".mud-application",
        ".nsdms-brand-title",
        "button",
        ".stat-title",
        ".stat-value",
        ".mud-nav-link"
    ]
    
    for selector in elements_to_check:
        try:
            el = page.query_selector(selector)
            if el:
                font = el.evaluate("el => window.getComputedStyle(el).fontFamily")
                font_weight = el.evaluate("el => window.getComputedStyle(el).fontWeight")
                print(f"[{selector}] -> font-family: '{font}', font-weight: {font_weight}")
            else:
                print(f"[{selector}] -> Element not found")
        except Exception as e:
            print(f"[{selector}] -> Error: {e}")
            
    # Check if Google Fonts stylesheet loaded or failed
    fonts_loaded = page.evaluate("""() => {
        return Array.from(document.fonts).map(f => ({
            family: f.family,
            status: f.status,
            weight: f.weight
        }));
    }""")
    print("\nLoaded Document Fonts:")
    for f in fonts_loaded:
        print(f" - {f['family']} ({f['weight']}): {f['status']}")

    browser.close()
