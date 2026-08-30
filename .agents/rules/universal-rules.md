---
name: universal-rules
version: 1.0.0
priority: P0
trigger: always_on
---

# Universal Rules (TIER 0) - AG Kit

> Always-active rules that apply to every request, regardless of domain.

---

## 🌐 Language Handling

When user's prompt is NOT in English:

1. **Internally translate** for better comprehension
2. **Respond in user's language** - match their communication
3. **Code comments/variables** remain in English

---

## 🧹 Clean Code (Global Mandatory)

**ALL code MUST follow `@[skills/clean-code]` rules. No exceptions.**

- **Code**: Concise, direct, no over-engineering. Self-documenting.
- **Testing**: Mandatory. Pyramid (Unit > Int > E2E) + AAA Pattern.
- **Performance**: Measure first. Adhere to current Core Web Vitals standards.
---

## ⚙️ Dynamic Configuration & Feature Flags Governance
1. **Zero Hardcoding Invariant**: No business parameter, threshold, storage path, or external integration endpoint may be hardcoded. Always use `ISystemConfigurationService` with cascading database overrides.
2. **Integrations Off-By-Default**: All external integrations (Dynamics GP, Sage, Live DHET SFTP, Live SARS FTP, SMS OTP, Azure Blob) MUST default to `IsEnabled = false`. Workflows must cleanly execute in mock simulation mode when disabled.
3. **Double-Write Audit Trail**: All system configuration updates, feature toggles, and document operations must perform atomic double-writes into `audit_logs`.

---

## 🎨 MudBlazor Layout & Sticky Top Bar Invariant
1. **No Utility Classes on `MudMainContent`**: Never add `Class="pa-*"` or `Class="pt-*"` directly to `<MudMainContent>`. MudBlazor utility classes apply `!important`, which cancels the computed `padding-top: var(--mud-appbar-height)` (64px) and causes the header to overlap page content. Always wrap `@Body` inside `<MudMainContent><div class="pa-4">@Body</div></MudMainContent>`.
2. **Sticky Sub-Header Offsets**: Sticky top bars on Master-Detail pages must always dock below the 64px `MudAppBar` using `top: var(--mud-appbar-height, 64px) !important;` (or `.sticky-top-header`).


