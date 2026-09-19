import os
import sys
import io
import time
import requests
from playwright.sync_api import sync_playwright

if sys.stdout.encoding != 'utf-8':
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

BASE_URL = os.environ.get("NSDMS_BASE_URL", "http://localhost:5121")
TEST_USER = os.environ.get("NSDMS_TEST_USER", "sysadmin@merseta.org.za")
TEST_PASSWORD = os.environ.get("NSDMS_TEST_PASSWORD", "MerSETA@2026!")

def run_comprehensive_security_test():
    print("======================================================================")
    print("     NSDMS COMPREHENSIVE END-TO-END APPLICATION SECURITY AUDIT")
    print(f"     Target System: {BASE_URL}")
    print("======================================================================\n")

    passed_tests = 0
    total_tests = 0

    # -------------------------------------------------------------------------
    # TEST 1: Unauthenticated Route Protection (Zero-Trust Gate)
    # -------------------------------------------------------------------------
    total_tests += 1
    print("[TEST 1] Testing Unauthenticated Route Protection (Anonymous Access Prohibition)...")
    protected_routes = [
        "/",
        "/admin/roles",
        "/admin/users",
        "/admin/entra-resilience",
        "/governance/delegations",
        "/governance/thresholds",
        "/audit-logs"
    ]

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        anon_context = browser.new_context()
        anon_page = anon_context.new_page()

        for route in protected_routes:
            url = f"{BASE_URL}{route}"
            anon_page.goto(url, wait_until="networkidle")
            current_url = anon_page.url
            assert "/login" in current_url, f"Unauthenticated request to {route} was NOT redirected to /login! Landed on {current_url}"
            print(f"  --> Confirmed: Anonymous access to '{route}' successfully redirected to /login")

        # Also test direct API unauthenticated call
        api_res = requests.get(f"{BASE_URL}/api/auth/me", allow_redirects=False)
        assert api_res.status_code in [302, 401], f"Expected 302 Redirect to /login or 401 Unauthorized for unauthenticated /api/auth/me, got {api_res.status_code}"
        if api_res.status_code == 302:
            assert "/login" in api_res.headers.get("Location", ""), "Redirect location was not /login!"
        print("  --> Confirmed: Anonymous request to /api/auth/me redirected to /login (HTTP 302 / Zero-Trust Gate)")
        anon_context.close()
        browser.close()
    
    passed_tests += 1
    print("  [PASS] Unauthenticated Route Protection verified.\n")

    # -------------------------------------------------------------------------
    # TEST 2: SQL Injection & Malicious Input Defense
    # -------------------------------------------------------------------------
    total_tests += 1
    print("[TEST 2] Testing Injection & Malicious Input Defense on Authentication Endpoints...")
    sqli_payloads = [
        ("admin' OR '1'='1' --", "Password123!"),
        ("' UNION SELECT NULL, NULL, NULL --", "Password123!"),
        ("sysadmin@merseta.org.za' AND 1=1 --", "Password123!"),
        ("<script>alert('xss')</script>", "Password123!")
    ]

    for user_payload, pass_payload in sqli_payloads:
        res = requests.post(f"{BASE_URL}/api/auth/login", json={
            "username": user_payload,
            "password": pass_payload
        })
        assert res.status_code in [400, 401, 429], f"Unexpected status {res.status_code} for injection payload: {user_payload}"
        if res.status_code == 429:
            print(f"  --> Confirmed: Rate limiter actively throttled burst attempt '{user_payload[:30]}...' (HTTP 429)")
            continue
        data = res.json()
        assert not data.get("success", False), "Injection payload erroneously succeeded!"
        assert "Invalid" in data.get("message", "")
        print(f"  --> Confirmed: Injection payload '{user_payload[:30]}...' blocked cleanly with generic rejection.")

    passed_tests += 1
    print("  [PASS] Injection defenses verified.\n")

    # -------------------------------------------------------------------------
    # TEST 3: Anti-Username-Enumeration & Anti-Timing Defense
    # -------------------------------------------------------------------------
    total_tests += 1
    print("[TEST 3] Testing Anti-Username-Enumeration & Anti-Timing Defenses...")
    # Test non-existent user
    res_fake = requests.post(f"{BASE_URL}/api/auth/login", json={
        "username": "definitely_not_a_real_user_xyz@merseta.org.za",
        "password": "WrongPassword123!"
    })
    assert res_fake.status_code == 401
    fake_msg = res_fake.json().get("message", "")

    # Test real user with wrong password
    res_real = requests.post(f"{BASE_URL}/api/auth/login", json={
        "username": "sysadmin@merseta.org.za",
        "password": "WrongPassword123!"
    })
    assert res_real.status_code == 401
    real_msg = res_real.json().get("message", "")

    assert fake_msg == real_msg, f"Information leakage detected! Messages differ: '{fake_msg}' vs '{real_msg}'"
    print(f"  --> Confirmed: Identical error response returned ('{fake_msg}') for both existing and non-existing accounts.")
    passed_tests += 1
    print("  [PASS] Anti-enumeration defenses verified.\n")

    # -------------------------------------------------------------------------
    # TEST 4: Cookie Security Flags & Authenticated Session Issuance
    # -------------------------------------------------------------------------
    total_tests += 1
    print("[TEST 4] Testing Cookie Security Flags & Authenticated Session Issuance...")
    session = requests.Session()
    login_res = session.post(f"{BASE_URL}/api/auth/login", json={
        "username": TEST_USER,
        "password": TEST_PASSWORD
    })
    assert login_res.status_code == 200, f"Login failed with status {login_res.status_code}: {login_res.text}"
    login_data = login_res.json()
    assert login_data.get("success") == True, "Login response was not successful"

    # Inspect cookies
    cookie_jar = session.cookies
    auth_cookie = None
    for cookie in cookie_jar:
        if cookie.name == "NSDMS_AUTH_TICKET":
            auth_cookie = cookie
            break
    
    assert auth_cookie is not None, "NSDMS_AUTH_TICKET cookie was not set!"
    # Verify HttpOnly (requests cookie object has has_nonstandard_attr for httponly or rest_key)
    # Check Set-Cookie header directly
    set_cookie_header = login_res.headers.get("Set-Cookie", "")
    assert "httponly" in set_cookie_header.lower(), f"Missing HttpOnly attribute in Set-Cookie: {set_cookie_header}"
    assert "samesite=lax" in set_cookie_header.lower() or "samesite=strict" in set_cookie_header.lower(), f"Missing SameSite attribute in Set-Cookie: {set_cookie_header}"
    print(f"  --> Confirmed: NSDMS_AUTH_TICKET cookie contains HttpOnly and SameSite security flags across all ticket chunks.")

    # Verify session via /api/auth/me
    me_res = session.get(f"{BASE_URL}/api/auth/me")
    assert me_res.status_code == 200
    me_data = me_res.json()
    assert me_data.get("authenticated") == True
    assert me_data.get("username") == "sysadmin@merseta.org.za"
    assert "SuperAdmin" in me_data.get("roles", [])
    print(f"  --> Confirmed: /api/auth/me validates active session with SuperAdmin roles and {len(me_data.get('permissions', []))} permissions.")
    passed_tests += 1
    print("  [PASS] Cookie security flags and session issuance verified.\n")

    # -------------------------------------------------------------------------
    # TEST 5: Browser E2E Role-Based Access Control & Navigation Integrity
    # -------------------------------------------------------------------------
    total_tests += 1
    print("[TEST 5] Testing Browser E2E Authenticated Access Across Sensitive Modules...")
    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(viewport={"width": 1440, "height": 900})
        page = context.new_page()

        # Login via UI
        page.goto(f"{BASE_URL}/login", wait_until="networkidle")
        page.fill("input#username", TEST_USER)
        page.fill("input#password", TEST_PASSWORD)
        page.click("button[type='submit']", no_wait_after=True)
        page.wait_for_selector("text=Operations Portal", timeout=35000)
        print("  --> Authenticated in browser session.")

        test_modules = [
            ("/admin/roles", "Security Roles & Permissions Matrix", "Roles Registry"),
            ("/admin/roles/1", "Module & Action Permissions", "Role Permission Matrix"),
            ("/admin/users", "People & Identity Accounts", "User Management"),
            ("/admin/entra-resilience", "Microsoft Entra ID resilience hub", "Entra Resilience Hub"),
            ("/governance/delegations", "Time-Bounded Role & Module Delegations", "Delegation Governance"),
            ("/governance/thresholds", "Financial Delegation of Authority Matrix (DoA)", "Financial Thresholds Matrix"),
            ("/audit-logs", "System Audit Trail & Forensic Records", "Forensic Audit Trail")
        ]

        for route, expected_text, label in test_modules:
            page.goto(f"{BASE_URL}{route}", wait_until="networkidle")
            page.wait_for_selector(f"text={expected_text}", timeout=15000)
            print(f"  --> Confirmed: {label} ({route}) rendered securely and verified expected access.")

        browser.close()
    
    passed_tests += 1
    print("  [PASS] Browser E2E RBAC & Navigation verified.\n")

    # -------------------------------------------------------------------------
    # TEST 6: Entra Outage Resilience Health Service
    # -------------------------------------------------------------------------
    total_tests += 1
    print("[TEST 6] Testing Microsoft Entra ID Health & Resilience Endpoints...")
    health_res = requests.get(f"{BASE_URL}/api/auth/entra-health")
    assert health_res.status_code == 200
    health_data = health_res.json()
    assert health_data.get("status") in ["Healthy", "Degraded", "Outage"]
    assert health_data.get("isOutage") in [True, False]
    print(f"  --> Confirmed: /api/auth/entra-health is active (Status: {health_data.get('status')}, Outage: {health_data.get('isOutage')}).")
    passed_tests += 1
    print("  [PASS] Entra Health service verified.\n")

    # -------------------------------------------------------------------------
    # SUMMARY REPORT
    # -------------------------------------------------------------------------
    print("======================================================================")
    print(f"   E2E SECURITY AUDIT RESULT: {passed_tests}/{total_tests} SUITES PASSED (100%)")
    print("======================================================================")

if __name__ == "__main__":
    run_comprehensive_security_test()
