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

                // Check if user is typing in a form input
                const activeTag = (document.activeElement?.tagName || '').toLowerCase();
                const isTyping = activeTag === 'input' || activeTag === 'textarea' || activeTag === 'select' || document.activeElement?.isContentEditable;

                // 2. '/' shortcut -> Focus primary search input
                if (e.key === '/' && !isTyping && !e.ctrlKey && !e.metaKey && !e.altKey) {
                    const searchInput = document.querySelector("input[placeholder*='Search' i], input[placeholder*='search' i], .mud-input-slot[type='text']");
                    if (searchInput) {
                        e.preventDefault();
                        searchInput.focus();
                        searchInput.select();
                    }
                    return;
                }

                // 3. 'Escape' shortcut -> Cancel / Back if not in a modal
                if (e.key === 'Escape' && !isTyping) {
                    const openDialog = document.querySelector('.mud-dialog-container');
                    if (!openDialog) {
                        const backOrCancelBtn = Array.from(document.querySelectorAll('button')).find(b => {
                            const text = (b.innerText || '').toLowerCase();
                            return text.includes('back to') || text === 'cancel';
                        });
                        if (backOrCancelBtn) {
                            backOrCancelBtn.click();
                        }
                    }
                }
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
