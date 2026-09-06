import sys
from playwright.sync_api import sync_playwright

BASE_URL = "http://localhost:5121"

FAILING_PAGES = [
    "/people/1",
    "/sdp/create",
    "/wsp/create"
]

def debug_failures():
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context()
        page = context.new_page()

        # Login
        page.goto(f"{BASE_URL}/login")
        page.fill("input#username", "sysadmin@merseta.org.za")
        page.fill("input#password", "MerSETA@2026!")
        page.click("button[type='submit']")
        page.wait_for_load_state("networkidle")

        for url in FAILING_PAGES:
            print(f"\n==================================================")
            print(f"DEBUGGING: {url}")
            print(f"==================================================")
            try:
                res = page.goto(f"{BASE_URL}{url}", wait_until="networkidle", timeout=10000)
                status = res.status if res else "None"
                print(f"Status: {status} | Final URL: {page.url}")

                body_text = page.locator("body").inner_text()
                lines = [line.strip() for line in body_text.split("\n") if line.strip()]
                print("Body preview (first 10 non-empty lines):")
                for line in lines[:10]:
                    print("  >", line)

                for line in lines:
                    if any(k in line.lower() for k in ["exception:", "at nsdms", "invalidoperationexception", "nullreferenceexception", "sqlexception", "keynotfoundexception"]):
                        print("  [STACK/ERROR LINE]:", line)

            except Exception as e:
                print(f"Exception navigating to {url}: {e}")

        browser.close()

if __name__ == "__main__":
    debug_failures()
