"""
playwright_assertions.py
NSDMS Enterprise Playwright Verification Standard Module

Provides comprehensive visual, console, and interactivity assertions to ensure:
1. Zero console errors and zero uncaught JavaScript/page exceptions.
2. True visual styling: stylesheets loaded, MudBlazor CSS variables resolved,
   structural layout containers rendered with positive bounding boxes, non-blank body.
3. Full interactivity: Blazor interactive circuit connected, no error boundary,
   no persistent modal/loading veil blocking interaction, and interactive elements
   (buttons, links, inputs) visible, enabled, and responsive.
"""

from typing import List, Dict, Any, Tuple, Optional


class ConsoleErrorTracker:
    """Tracks console errors and page errors on a Playwright page."""

    def __init__(self, page):
        self.page = page
        self.console_errors: List[str] = []
        self.page_errors: List[str] = []
        self._console_listener = None
        self._pageerror_listener = None
        self._attach()

    def _attach(self):
        def on_console(msg):
            if msg.type == "error":
                # Filter out benign browser noise if needed (e.g. favicon 404 in dev)
                text = msg.text or ""
                if "favicon.ico" in text:
                    return
                self.console_errors.append(text)

        def on_pageerror(err):
            self.page_errors.append(str(err))

        self._console_listener = on_console
        self._pageerror_listener = on_pageerror
        self.page.on("console", self._console_listener)
        self.page.on("pageerror", self._pageerror_listener)

    def clear(self):
        """Clears accumulated errors between page navigations."""
        self.console_errors.clear()
        self.page_errors.clear()

    def detach(self):
        """Removes listeners from the page."""
        try:
            if self._console_listener:
                self.page.remove_listener("console", self._console_listener)
            if self._pageerror_listener:
                self.page.remove_listener("pageerror", self._pageerror_listener)
        except Exception:
            pass

    def get_all_errors(self) -> List[str]:
        return self.page_errors + self.console_errors


def assert_page_visual_interactive_integrity(
    page,
    error_tracker: Optional[ConsoleErrorTracker] = None,
    timeout_ms: int = 5000,
    check_theme: bool = True,
    check_interactivity: bool = True
) -> Tuple[bool, List[str], Dict[str, Any]]:
    """
    Asserts that the current page satisfies:
    1. Console & Exception Integrity: Zero console errors, zero page errors, no Blazor crash.
    2. True Visual Styling: Stylesheets loaded, MudBlazor CSS variables resolved,
       layout container visible with non-zero dimensions.
    3. Full Interactivity: Interactive elements present, visible, enabled, and clickable.

    Returns:
        (is_passed: bool, errors: List[str], details: Dict[str, Any])
    """
    errors: List[str] = []
    details: Dict[str, Any] = {}

    # -------------------------------------------------------------------------
    # 1. Console & Runtime Exception Checks
    # -------------------------------------------------------------------------
    if error_tracker:
        if error_tracker.page_errors:
            errors.append(f"Uncaught JavaScript page errors ({len(error_tracker.page_errors)}): {error_tracker.page_errors[:3]}")
        if error_tracker.console_errors:
            errors.append(f"Browser console errors ({len(error_tracker.console_errors)}): {error_tracker.console_errors[:3]}")

    # Check for Blazor Error Boundary or crash message
    try:
        error_boundary = page.locator(".blazor-error-boundary")
        if error_boundary.count() > 0 and error_boundary.first.is_visible():
            errors.append("Blazor Error Boundary is visible on the page (circuit crash).")
    except Exception as ex:
        errors.append(f"Error checking blazor-error-boundary: {ex}")

    try:
        body_text = page.locator("body").inner_text() or ""
        critical_markers = [
            "An unhandled exception occurred",
            "SqlException",
            "NullReferenceException",
            "InvalidOperationException",
            "Cannot read properties of null",
            "There was an unhandled exception on the current circuit"
        ]
        for marker in critical_markers:
            if marker in body_text:
                errors.append(f"Critical exception text found in body: '{marker}'")
                break
    except Exception as ex:
        errors.append(f"Error reading body text: {ex}")
        body_text = ""

    # -------------------------------------------------------------------------
    # 2. Visual & Styling Checks (True Visually Styled)
    # -------------------------------------------------------------------------
    styling_info = page.evaluate("""() => {
        const styleSheetsCount = document.styleSheets ? document.styleSheets.length : 0;
        let rulesLoaded = false;
        try {
            for (let i = 0; i < document.styleSheets.length; i++) {
                const sheet = document.styleSheets[i];
                if (sheet && sheet.cssRules && sheet.cssRules.length > 0) {
                    rulesLoaded = true;
                    break;
                }
            }
        } catch (e) {
            // Cross-origin stylesheets might throw on accessing cssRules, treat presence as ok
            rulesLoaded = styleSheetsCount > 0;
        }

        const rootStyle = window.getComputedStyle(document.documentElement);
        const bodyStyle = window.getComputedStyle(document.body);

        const primaryColor = rootStyle.getPropertyValue('--mud-palette-primary').trim() ||
                             bodyStyle.getPropertyValue('--mud-palette-primary').trim();
        const bgColor = rootStyle.getPropertyValue('--mud-palette-background').trim() ||
                        bodyStyle.getPropertyValue('--mud-palette-background').trim() ||
                        bodyStyle.backgroundColor;

        const bodyDisplay = bodyStyle.display;
        const bodyVisibility = bodyStyle.visibility;
        const bodyOpacity = parseFloat(bodyStyle.opacity || "1");

        // Layout container check
        const layoutEl = document.querySelector('.mud-layout, main#main-content, .mud-main-content, .mud-container, #main-content');
        let layoutBox = null;
        if (layoutEl) {
            const rect = layoutEl.getBoundingClientRect();
            layoutBox = {
                width: rect.width,
                height: rect.height,
                top: rect.top,
                left: rect.left
            };
        }

        const totalDomElements = document.querySelectorAll('*').length;

        return {
            styleSheetsCount,
            rulesLoaded,
            primaryColor,
            bgColor,
            bodyDisplay,
            bodyVisibility,
            bodyOpacity,
            hasLayout: !!layoutEl,
            layoutBox,
            totalDomElements
        };
    }""")

    details["styling"] = styling_info

    if styling_info.get("styleSheetsCount", 0) == 0:
        errors.append("No CSS stylesheets loaded on the page (raw unstyled document).")

    if not styling_info.get("rulesLoaded", False):
        errors.append("Stylesheets failed to populate CSS rules.")

    if styling_info.get("bodyDisplay") == "none" or styling_info.get("bodyVisibility") == "hidden" or styling_info.get("bodyOpacity", 1) <= 0:
        errors.append("Page <body> is hidden or transparent (display: none or opacity 0).")

    if check_theme:
        # Verify MudBlazor theme CSS custom property is defined
        if not styling_info.get("primaryColor"):
            errors.append("MudBlazor theme token '--mud-palette-primary' is undefined or empty.")

    # Check structural layout container
    if not styling_info.get("hasLayout"):
        errors.append("No structural layout container found (expected .mud-layout, main#main-content, .mud-main-content, or .mud-container).")
    else:
        lbox = styling_info.get("layoutBox")
        if not lbox or lbox.get("width", 0) < 200 or lbox.get("height", 0) < 100:
            errors.append(f"Layout container bounding box is collapsed or zero-dimension: {lbox}")

    if styling_info.get("totalDomElements", 0) < 10:
        errors.append(f"DOM tree is suspiciously empty ({styling_info.get('totalDomElements')} elements).")

    # -------------------------------------------------------------------------
    # 3. Full Interactivity Checks (Fully Interactive)
    # -------------------------------------------------------------------------
    if check_interactivity:
        interactivity_info = page.evaluate("""() => {
            // Check Blazor circuit reconnect modal status
            const reconnectModal = document.querySelector('#components-reconnect-modal');
            let isReconnecting = false;
            let isCircuitFailed = false;
            if (reconnectModal) {
                const style = window.getComputedStyle(reconnectModal);
                const isVisible = style.display !== 'none' && style.visibility !== 'hidden' && style.opacity !== '0';
                const classList = reconnectModal.className || '';
                isReconnecting = isVisible && (classList.includes('components-reconnect-show') || classList.includes('show'));
                isCircuitFailed = isVisible && (classList.includes('components-reconnect-failed') || classList.includes('failed'));
            }

            // Look for interactive elements
            const candidates = Array.from(document.querySelectorAll(
                'button, a[href], input:not([type="hidden"]), select, textarea, [role="button"], [role="tab"], .mud-button-root, .mud-link'
            ));

            let interactiveCount = 0;
            let firstInteractive = null;

            for (const el of candidates) {
                const rect = el.getBoundingClientRect();
                const style = window.getComputedStyle(el);
                const isVisible = style.display !== 'none' &&
                                  style.visibility !== 'hidden' &&
                                  parseFloat(style.opacity || '1') > 0.1 &&
                                  rect.width > 0 && rect.height > 0;
                const isEnabled = !el.disabled && !el.hasAttribute('disabled');
                const pointerEvents = style.pointerEvents;

                if (isVisible && isEnabled && pointerEvents !== 'none') {
                    interactiveCount++;
                    if (!firstInteractive) {
                        firstInteractive = {
                            tag: el.tagName.toLowerCase(),
                            text: (el.innerText || el.value || el.getAttribute('aria-label') || '').trim().substring(0, 30),
                            width: rect.width,
                            height: rect.height
                        };
                    }
                }
            }

            return {
                isReconnecting,
                isCircuitFailed,
                candidateCount: candidates.length,
                interactiveCount,
                firstInteractive
            };
        }""")

        details["interactivity"] = interactivity_info

        if interactivity_info.get("isReconnecting"):
            errors.append("Blazor Server interactive circuit is actively reconnecting (#components-reconnect-modal active).")

        if interactivity_info.get("isCircuitFailed"):
            errors.append("Blazor Server circuit has failed/terminated permanently.")

        if interactivity_info.get("interactiveCount", 0) == 0:
            errors.append("Zero visible, enabled, interactive elements found on the page (not fully interactive).")

    is_passed = len(errors) == 0
    return is_passed, errors, details
