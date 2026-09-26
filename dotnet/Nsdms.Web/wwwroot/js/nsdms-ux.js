/**
 * merSETA NSDMS Power-User UX & Keyboard Shortcuts Engine
 * Standards: W3C WCAG 2.2 AA (Keyboard Navigation) & IxDF Interaction Design Laws
 */

(function () {
    'use strict';

    window.nsdmsUX = {
        initKeyboardShortcuts: function () {
            document.addEventListener('keydown', function (e) {
                // 1. Ctrl+S or Cmd+S -> Trigger Primary Save Action
                if ((e.ctrlKey || e.metaKey) && e.key.toLowerCase() === 's') {
                    e.preventDefault();
                    
                    // Search for primary Save button in sticky top bar or forms
                    const saveButtons = Array.from(document.querySelectorAll('button:not([disabled])')).filter(b => {
                        const text = (b.innerText || '').toLowerCase();
                        const title = (b.getAttribute('title') || '').toLowerCase();
                        const aria = (b.getAttribute('aria-label') || '').toLowerCase();
                        return text.includes('save') || title.includes('save') || aria.includes('save');
                    });

                    if (saveButtons.length > 0) {
                        // Click the primary save button (preferring filled primary button)
                        const primarySave = saveButtons.find(b => b.classList.contains('mud-button-filled-primary')) || saveButtons[0];
                        primarySave.click();
                        
                        // Visual pulse feedback on button
                        primarySave.style.transform = 'scale(0.96)';
                        setTimeout(() => { primarySave.style.transform = ''; }, 150);
                    }
                    return;
                }

                // 2. Ctrl+K or Cmd+K -> Trigger Command Palette
                if ((e.ctrlKey || e.metaKey) && e.key.toLowerCase() === 'k') {
                    e.preventDefault();
                    const cmdBtn = document.querySelector("button[title*='Command Palette' i], div[title*='Command Palette' i], button[aria-label*='Command Palette' i]");
                    if (cmdBtn) {
                        cmdBtn.click();
                    }
                    return;
                }

                // Check if user is typing in a form input or editable surface
                const activeEl = document.activeElement;
                const activeTag = (activeEl?.tagName || '').toLowerCase();
                const isTyping = activeTag === 'input' || 
                                 activeTag === 'textarea' || 
                                 activeTag === 'select' || 
                                 activeEl?.isContentEditable ||
                                 activeEl?.getAttribute('role') === 'textbox' ||
                                 activeEl?.getAttribute('role') === 'searchbox' ||
                                 activeEl?.getAttribute('role') === 'combobox';

                // Single-key shortcut opt-out mechanism (WCAG 2.1.4 Character Key Shortcuts)
                const isSingleKeyDisabled = window.nsdmsDisableSingleKeyShortcuts === true || 
                                            localStorage.getItem('nsdms_disable_single_key_shortcuts') === 'true';

                // 2. '/' shortcut -> Focus primary search input (prevent focus hijacking & permit opt-out)
                if (e.key === '/' && !isTyping && !isSingleKeyDisabled && !e.ctrlKey && !e.metaKey && !e.altKey) {
                    const searchInput = document.querySelector("input[placeholder*='Search' i], input[placeholder*='search' i], .mud-input-slot[type='text']");
                    if (searchInput && document.activeElement !== searchInput) {
                        e.preventDefault();
                        searchInput.focus();
                        searchInput.select();
                    }
                    return;
                }

                // 3. 'Escape' shortcut handler:
                // Removed automatic button clicking on Escape to prevent destructively discarding user form edits without warning.
                // Modal dismissal is handled natively by MudDialog and HTML dialog elements.
            });
        },

        printPage: function () {
            window.print();
        }
    };

    // Auto-initialize when DOM is ready
    if (document.readyState === 'loading') {
        document.addEventListener('DOMContentLoaded', () => window.nsdmsUX.initKeyboardShortcuts());
    } else {
        window.nsdmsUX.initKeyboardShortcuts();
    }
})();
