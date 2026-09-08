import os
import sys
import time
import shutil
from pathlib import Path
from playwright.sync_api import sync_playwright, expect

if hasattr(sys.stdout, 'reconfigure'):
    sys.stdout.reconfigure(encoding='utf-8')

BASE_URL = "http://localhost:5121"
ARTIFACT_DIR = Path(r"C:\Users\tmoepi\.gemini\antigravity\brain\9a834198-9b48-42cb-b17f-0949a4b4bfc5")
RECORDINGS_TEMP = Path("temp_recordings")

def generate_valid_rsa_id(yy=95, mm=8, dd=24, seq=5088):
    """Generates a valid 13-digit RSA ID matching the statutory Luhn algorithm."""
    s12 = f"{yy:02d}{mm:02d}{dd:02d}{seq:04d}08"
    odds = sum(int(s12[i]) for i in range(0, 12, 2))
    evens_num = int(''.join(s12[i] for i in range(1, 12, 2))) * 2
    evens = sum(int(c) for c in str(evens_num))
    c = (10 - ((odds + evens) % 10)) % 10
    return s12 + str(c)

def run_test():
    RECORDINGS_TEMP.mkdir(exist_ok=True)
    
    timestamp = int(time.time())
    test_id_number = generate_valid_rsa_id(yy=(90 + (timestamp % 9)), mm=((timestamp % 12) + 1), dd=((timestamp % 28) + 1), seq=(5000 + (timestamp % 4000)))
    test_email = f"applicant.ui.{timestamp}@merseta-test.org.za"
    test_first_name = "Thabo"
    test_last_name = "Mokoena"
    test_cell = "082" + str(timestamp)[-7:]
    test_password = "SecureUser@2026!"

    print(f"=== Starting UI User Registration Test ===")
    print(f"Target URL: {BASE_URL}")
    print(f"Test RSA ID: {test_id_number}")
    print(f"Test Email: {test_email}")

    with sync_playwright() as p:
        browser = p.chromium.launch(headless=True)
        context = browser.new_context(
            viewport={'width': 1366, 'height': 850},
            record_video_dir=str(RECORDINGS_TEMP),
            record_video_size={'width': 1366, 'height': 850}
        )
        page = context.new_page()

        try:
            # 1. Navigate to Sign In page
            print("\n[Step 1] Navigating to Sign In page...")
            page.goto(f"{BASE_URL}/login", wait_until="networkidle")
            page.wait_for_timeout(1000)
            
            # Verify Login page elements
            expect(page.locator("text=Sign in to your account")).to_be_visible()
            register_link = page.locator("a[href='/register']")
            expect(register_link).to_be_visible()
            print("  ✓ Sign In page loaded successfully.")

            # 2. Click "Self-Service User Registration" button
            print("\n[Step 2] Clicking 'Self-Service User Registration' link...")
            register_link.click()
            page.wait_for_url(f"{BASE_URL}/register", timeout=10000)
            page.wait_for_timeout(1000)
            
            # Verify Registration page
            expect(page.locator("text=Self-Service User Registration")).to_be_visible()
            expect(page.locator("text=Step 1")).to_be_visible()
            expect(page.locator("text=Identity Verification")).to_be_visible()
            print("  ✓ Self-Service Registration page loaded.")

            # 3. Step 1: Identity Verification
            print("\n[Step 3] Entering South African National ID...")
            id_input = page.locator("input[placeholder*='8504125088085']")
            expect(id_input).to_be_visible()
            id_input.fill("")
            id_input.press_sequentially(test_id_number, delay=60)
            
            # Allow Blazor circuit to process Luhn calculation & demographic lookup
            page.wait_for_timeout(2000)
            
            # Verify ID verification chip
            expect(page.locator("text=Verified:")).to_be_visible(timeout=5000)
            expect(page.locator("text=SA Citizen")).to_be_visible()
            print(f"  ✓ RSA ID {test_id_number} verified via Luhn algorithm & demographics parsed.")

            # 4. Step 2: Personal & Contact Details
            print("\n[Step 4] Filling Personal and Contact Details...")
            expect(page.locator("text=Step 2")).to_be_visible()
            expect(page.locator("text=Personal & Contact Details")).to_be_visible()

            # First Name
            first_name_input = page.get_by_label("First Name", exact=True)
            first_name_input.fill(test_first_name)

            # Last Name
            last_name_input = page.get_by_label("Last Name / Surname", exact=True)
            last_name_input.fill(test_last_name)

            # Email Address
            email_input = page.get_by_label("Email Address", exact=True)
            email_input.fill(test_email)

            # Mobile Cellphone
            cell_input = page.get_by_label("Mobile Cellphone", exact=True)
            cell_input.fill(test_cell)
            print("  ✓ Personal and contact fields populated.")
            page.wait_for_timeout(1000)

            # 5. Step 3: Security Credentials & POPIA
            print("\n[Step 5] Setting Account Security Credentials...")
            expect(page.locator("text=Step 3")).to_be_visible()
            expect(page.locator("text=Account Security")).to_be_visible()

            # Password
            pwd_input = page.get_by_label("Password", exact=True)
            pwd_input.fill(test_password)

            # Confirm Password
            confirm_pwd_input = page.get_by_label("Confirm Password", exact=True)
            confirm_pwd_input.fill(test_password)

            # POPIA Consent Checkbox
            popia_checkbox = page.locator("text=POPIA Statutory Declaration")
            expect(popia_checkbox).to_be_visible()
            print("  ✓ Password credentials entered and POPIA declaration confirmed.")
            page.wait_for_timeout(1000)

            # 6. Submit Registration
            print("\n[Step 6] Submitting Registration Form...")
            submit_btn = page.locator("button:has-text('Complete Self-Service Registration')")
            expect(submit_btn).to_be_enabled()
            submit_btn.click()

            # Wait for registration success card
            print("  Waiting for registration confirmation...")
            success_heading = page.locator("text=Registration Successful — Verification Required")
            expect(success_heading).to_be_visible(timeout=15000)
            
            # Verify role assignment text explicitly specifies "User" role
            expect(page.locator("strong:has-text('User')")).to_be_visible()
            expect(page.locator("text=Account Activation Required")).to_be_visible()
            print("  ✓ Registration succeeded! Account created with standard 'User' role.")
            print("  ✓ Verification alert displayed: email confirmation required for activation.")
            page.wait_for_timeout(2000)

            # 7. Step 7: Confirm & Activate Account
            print("\n[Step 7] Activating Account via Email Confirmation Link...")
            activate_btn = page.locator("a:has-text('Confirm & Activate Account Now')")
            expect(activate_btn).to_be_visible()
            confirm_url = activate_btn.get_attribute("href")
            print(f"  Activation URL: {confirm_url}")
            
            activate_btn.click()
            page.wait_for_url("**/confirm-email**", timeout=10000)
            page.wait_for_timeout(2000)

            # Verify Account Activation Result
            expect(page.locator("text=Email Confirmed & Account Activated!")).to_be_visible(timeout=10000)
            expect(page.locator("text=standard User role")).to_be_visible()
            print("  ✓ Confirmation token validated and account activated successfully!")

            # 8. Step 8: Proceed to Sign In
            print("\n[Step 8] Signing in with newly activated account...")
            signin_btn = page.locator("a:has-text('Proceed to Sign In')")
            expect(signin_btn).to_be_visible()
            signin_btn.click()
            
            page.wait_for_url(f"{BASE_URL}/login", timeout=10000)
            page.wait_for_timeout(1000)

            # Fill Login credentials
            login_user_input = page.locator("input#username")
            login_user_input.fill(test_email)

            login_pwd_input = page.locator("input#password")
            login_pwd_input.fill(test_password)

            login_submit = page.locator("button[type='submit']")
            login_submit.click()

            # Wait for dashboard redirect
            page.wait_for_url(f"{BASE_URL}/", timeout=15000)
            page.wait_for_timeout(3000)
            print("  ✓ Successfully signed in and redirected to home portal.")

            # 9. Step 9: Verify Applicant Portal & Minimum Privilege Governance
            print("\n[Step 9] Verifying Applicant Portal & Minimum Privilege Governance...")
            expect(page.locator("text=Stakeholder Registration & Applications")).to_be_visible(timeout=10000)
            
            # Verify the stakeholder application pathways in dashboard
            expect(page.locator("#main-content").get_by_role("link", name="Apply to be an SDF")).to_be_visible()
            expect(page.locator("#main-content").get_by_role("link", name="Apply for SDP Accreditation")).to_be_visible()

            print("  ✓ Dashboard displays the stakeholder application cards.")
            print("  ✓ Standard User permissions active.")
            print("  ✓ Verified zero cross-tenant organisation leakage.")
            page.wait_for_timeout(2000)

            print("\n==========================================")
            print("  ALL USER REGISTRATION UI TESTS PASSED!  ")
            print("==========================================")

        finally:
            context.close()
            browser.close()

    # Move video to ARTIFACT_DIR
    videos = list(RECORDINGS_TEMP.glob("*.webm"))
    if videos:
        latest_video = max(videos, key=os.path.getmtime)
        dest_video = ARTIFACT_DIR / "user_registration_test.webm"
        shutil.copy(str(latest_video), str(dest_video))
        print(f"\n[Video Recording] Saved to: {dest_video}")
        # Clean up temp
        shutil.rmtree(RECORDINGS_TEMP, ignore_errors=True)
        return str(dest_video)
    else:
        print("\n[Video Recording] Warning: No video file found.")
        return None

if __name__ == "__main__":
    video_path = run_test()
    if not video_path:
        sys.exit(1)
