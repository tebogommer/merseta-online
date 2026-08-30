---
name: Institutional Clarity Dark
colors:
  surface: '#18120c'
  surface-dim: '#18120c'
  surface-bright: '#403830'
  surface-container-lowest: '#130d07'
  surface-container-low: '#211a13'
  surface-container: '#251e17'
  surface-container-high: '#302921'
  surface-container-highest: '#3b332b'
  on-surface: '#eee0d4'
  on-surface-variant: '#d7c3b0'
  inverse-surface: '#eee0d4'
  inverse-on-surface: '#372f27'
  outline: '#9f8e7c'
  outline-variant: '#524436'
  surface-tint: '#ffb962'
  primary: '#ffb962'
  on-primary: '#472a00'
  primary-container: '#cc851f'
  on-primary-container: '#432700'
  inverse-primary: '#865300'
  secondary: '#bcc7de'
  on-secondary: '#263143'
  secondary-container: '#3e495d'
  on-secondary-container: '#aeb9d0'
  tertiary: '#7bd0ff'
  on-tertiary: '#003549'
  tertiary-container: '#279fd2'
  on-tertiary-container: '#003145'
  error: '#ffb4ab'
  on-error: '#690005'
  error-container: '#93000a'
  on-error-container: '#ffdad6'
  primary-fixed: '#ffddb9'
  primary-fixed-dim: '#ffb962'
  on-primary-fixed: '#2b1700'
  on-primary-fixed-variant: '#663e00'
  secondary-fixed: '#d8e3fb'
  secondary-fixed-dim: '#bcc7de'
  on-secondary-fixed: '#111c2d'
  on-secondary-fixed-variant: '#3c475a'
  tertiary-fixed: '#c4e7ff'
  tertiary-fixed-dim: '#7bd0ff'
  on-tertiary-fixed: '#001e2c'
  on-tertiary-fixed-variant: '#004c69'
  background: '#18120c'
  on-background: '#eee0d4'
  surface-variant: '#3b332b'
typography:
  headline-xl:
    fontFamily: Public Sans
    fontSize: 48px
    fontWeight: '700'
    lineHeight: 56px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Public Sans
    fontSize: 32px
    fontWeight: '600'
    lineHeight: 40px
  headline-lg-mobile:
    fontFamily: Public Sans
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
  body-md:
    fontFamily: Public Sans
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-sm:
    fontFamily: Public Sans
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  label-caps:
    fontFamily: Public Sans
    fontSize: 12px
    fontWeight: '700'
    lineHeight: 16px
    letterSpacing: 0.05em
rounded:
  sm: 0.125rem
  DEFAULT: 0.25rem
  md: 0.375rem
  lg: 0.5rem
  xl: 0.75rem
  full: 9999px
spacing:
  base: 4px
  xs: 4px
  sm: 8px
  md: 16px
  lg: 24px
  xl: 40px
  gutter: 24px
  margin-mobile: 16px
  margin-desktop: 64px
---

## Brand & Style
The design system evolves into a high-authority dark mode, moving from its "official" light-mode roots into a "Command Center" aesthetic. The brand personality is institutional, serious, and highly organized, catering to users who manage complex data or govern sophisticated systems in low-light environments.

The style is **Corporate Modern** with a focus on **Tonal Layering**. It utilizes deep charcoal and navy foundations to create a sense of infinite depth and stability. The emotional response is one of calm control, precision, and trust. White space is treated as "dark space," ensuring that information density remains high without feeling cluttered.

## Colors
The palette shifts to a deep, monochromatic base to reduce eye strain while highlighting the heritage Gold/Orange primary accent.

- **Primary (#CC851F):** Used exclusively for high-priority actions, active states, and critical brand moments.
- **Surface Foundations:** The background is a true Neutral Dark (#121212), while primary containers use Deep Charcoal (#1E1E1E) to establish elevation.
- **Secondary Surfaces:** Navy-tinted greys (#1E293B) are used for sidebars and navigation elements to provide subtle structural contrast.
- **Semantic Logic:** Success, Error, and Warning colors are shifted to more vibrant, desaturated versions of their original hues to ensure they meet AA contrast standards against dark backgrounds.

## Typography
The system uses **Public Sans** across all levels for its institutional clarity and exceptional legibility in digital interfaces. 

In this dark theme, text hierarchy is strictly enforced through color rather than size alone. Pure White (#FFFFFF) is reserved for headlines and primary buttons. High-level body text uses a soft off-white (#E2E8F0), while secondary metadata and captions use a muted light-grey (#94A3B8). This prevents "text vibration" and ensures the interface remains easy to scan for long periods.

## Layout & Spacing
The design system employs a **Fluid Grid** model based on a 4px baseline. 

- **Desktop:** A 12-column grid with 24px gutters. Content is primarily contained within a max-width of 1440px.
- **Tablet:** 8-column grid with 16px gutters and margins.
- **Mobile:** 4-column grid with 16px margins. 

Spacing is utilized to group related institutional data. Larger gaps (xl) are used between distinct content sections, while tight spacing (sm/md) is used for form groups and data table cells to maintain high information density.

## Elevation & Depth
In the dark theme, depth is communicated through **Tonal Layers** rather than heavy shadows. 

1. **Level 0 (Base):** #121212 (Background).
2. **Level 1 (Cards/Sections):** #1E1E1E (Charcoal).
3. **Level 2 (Modals/Popovers):** #2D2D2D.

For floating elements like dropdowns or tooltips, a very subtle, high-diffusion shadow is used (`0px 8px 24px rgba(0, 0, 0, 0.5)`), paired with a 1px low-contrast border (#334155) to define the object's edge against the dark background.

## Shapes
The shape language is **Soft (Level 1)**. This approach maintains the institutional, professional feel of the design system without appearing overly clinical or sharp. 

- Standard components (Inputs, Buttons) use a 4px (0.25rem) radius.
- Larger containers and cards use an 8px (0.5rem) radius.
- Use sharp edges only for dividers or table borders to maintain structural integrity.

## Components
- **Buttons:** Primary buttons use the Gold accent (#CC851F) with black text for maximum contrast. Secondary buttons use a ghost style with a light-grey border (#475569) and white text.
- **Input Fields:** Backgrounds are slightly darker than the card surface (#161616). Borders are #334155, turning Primary Gold on focus. Label text must be #E2E8F0.
- **Chips/Tags:** Used for categorization. These use a subtle Navy fill (#1E293B) with light-grey text to avoid competing with primary actions.
- **Cards:** No stroke by default; use the Level 1 surface color (#1E1E1E). On hover, a subtle 1px border of #CC851F can be applied for interactive states.
- **Lists & Tables:** Row separators use a very low-opacity white (rgba(255, 255, 255, 0.05)). Alternating row stripes are not recommended; use hover highlights instead.
- **Checkboxes/Radios:** Square-ish (4px radius) for checkboxes. Use Primary Gold for the "Checked" state.