---
name: theming-audit
description: Audits UI components, CSS tokens, and theme configurations for dark/light mode parity and WCAG contrast.
when_to_use: "When checking design tokens, theme toggles, dark mode contrast, or auditing components for hardcoded hex colors."
allowed-tools: Read, Glob, Grep
version: 1.0.0
---

# Theming & Contrast Audit Skill

## Objective
Ensures zero hardcoded hex colors, 100% theme token adoption, and full light/dark mode WCAG 2.2 AA contrast compliance across the Blazor UI.

## Audit Workflow

### 1. Hardcoded Hex Color Scan
Scan all `.razor` files for literal hex color codes:
```regex
#[0-9a-fA-F]{3,8}\b
```
- **Rule**: No hex color may appear directly in `.razor` files (except static SVG brand assets or theme definition files like `NsdmsTheme.cs`).
- **Remediation**: Replace with CSS custom properties (`var(--text-primary)`, `var(--brand-gold)`, `var(--status-success)`).

### 2. Dual-Theme Contrast Verification
- Verify that every background surface token (`--surface`, `--background`, `--surface-subtle`) has an opposing text token (`--text-primary`, `--text-secondary`) with $\ge 4.5:1$ contrast ratio for body text and $\ge 3:1$ for large text/icons in BOTH Light and Dark themes.
- Dark mode must not invert meaning: error remains red/luminous red (`#F87171`), success remains green/luminous green (`#4ADE80`).

### 3. FOUC & JS Theme Initialization
- Ensure `theme-init.js` is loaded synchronously in `App.razor` `<head>` before the DOM renders.
- Prohibit `eval` in JavaScript interop when applying theme classes.

### 4. Semantic Status Mapping
Verify status chips use semantic color tokens:
- **Active / Approved / Paid / Finalised**: Success (Green)
- **Pending / In Review / Submitted / Draft**: Warning / Info (Amber / Blue)
- **Rejected / Terminated / Inactive / Disputed**: Danger (Red)
- **Withdrawn / Archived**: Neutral (Slate / Grey)
