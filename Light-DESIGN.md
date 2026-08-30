---
name: Institutional Clarity
colors:
  surface: '#f9f9f9'
  surface-dim: '#dadada'
  surface-bright: '#f9f9f9'
  surface-container-lowest: '#ffffff'
  surface-container-low: '#f3f3f3'
  surface-container: '#eeeeee'
  surface-container-high: '#e8e8e8'
  surface-container-highest: '#e2e2e2'
  on-surface: '#1a1c1c'
  on-surface-variant: '#524436'
  inverse-surface: '#2f3131'
  inverse-on-surface: '#f1f1f1'
  outline: '#857464'
  outline-variant: '#d7c3b0'
  surface-tint: '#cc9c47'
  primary: '#cc9c47'
  on-primary: '#ffffff'
  primary-container: '#cc851f'
  on-primary-container: '#432700'
  inverse-primary: '#ffb962'
  secondary: '#5e5e5e'
  on-secondary: '#ffffff'
  secondary-container: '#e2e2e2'
  on-secondary-container: '#646464'
  tertiary: '#875224'
  on-tertiary: '#ffffff'
  tertiary-container: '#c68654'
  on-tertiary-container: '#492300'
  error: '#ba1a1a'
  on-error: '#ffffff'
  error-container: '#ffdad6'
  on-error-container: '#93000a'
  primary-fixed: '#ffddb9'
  primary-fixed-dim: '#ffb962'
  on-primary-fixed: '#2b1700'
  on-primary-fixed-variant: '#663e00'
  secondary-fixed: '#e2e2e2'
  secondary-fixed-dim: '#c6c6c6'
  on-secondary-fixed: '#1b1b1b'
  on-secondary-fixed-variant: '#474747'
  tertiary-fixed: '#ffdcc4'
  tertiary-fixed-dim: '#feb780'
  on-tertiary-fixed: '#2f1500'
  on-tertiary-fixed-variant: '#6b3b0e'
  background: '#f9f9f9'
  on-background: '#1a1c1c'
  surface-variant: '#e2e2e2'
typography:
  headline-xl:
    fontFamily: Inter
    fontSize: 40px
    fontWeight: '700'
    lineHeight: 48px
    letterSpacing: -0.02em
  headline-lg:
    fontFamily: Inter
    fontSize: 32px
    fontWeight: '700'
    lineHeight: 40px
    letterSpacing: -0.02em
  headline-md:
    fontFamily: Inter
    fontSize: 24px
    fontWeight: '600'
    lineHeight: 32px
    letterSpacing: -0.01em
  headline-sm:
    fontFamily: Inter
    fontSize: 20px
    fontWeight: '600'
    lineHeight: 28px
  body-lg:
    fontFamily: Inter
    fontSize: 18px
    fontWeight: '400'
    lineHeight: 28px
  body-md:
    fontFamily: Inter
    fontSize: 16px
    fontWeight: '400'
    lineHeight: 24px
  body-sm:
    fontFamily: Inter
    fontSize: 14px
    fontWeight: '400'
    lineHeight: 20px
  label-md:
    fontFamily: Inter
    fontSize: 12px
    fontWeight: '600'
    lineHeight: 16px
    letterSpacing: 0.05em
  headline-lg-mobile:
    fontFamily: Inter
    fontSize: 28px
    fontWeight: '700'
    lineHeight: 34px
rounded:
  sm: 0.125rem
  DEFAULT: 0.25rem
  md: 0.375rem
  lg: 0.5rem
  xl: 0.75rem
  full: 9999px
spacing:
  base: 8px
  container-margin-desktop: 48px
  container-margin-mobile: 16px
  gutter: 24px
  stack-sm: 12px
  stack-md: 24px
  stack-lg: 48px
---

## Brand & Style

This design system establishes a **Modern Institutional** aesthetic, specifically tailored for the high-stakes environment of government-sector issue tracking and email management. The visual narrative balances authority with accessibility, moving away from tech-centric SaaS trends toward a more grounded, dependable, and transparent interface.

The personality is defined by:
- **Authority:** High-contrast typography and a restrained use of the Gold/Orange accent communicate stability and official status.
- **Efficiency:** Generous whitespace and a rigorous grid system allow users to process large volumes of information and high-priority requests without cognitive fatigue.
- **Trust:** The combination of pure neutrals and deep semantic colors reinforces a sense of order and reliability.

The design style utilizes a refined **Minimalism** with structural elements borrowed from **Corporate Modernism**. It avoids unnecessary decoration, focusing instead on crisp borders, clear information hierarchy, and a systematic approach to status management.

## Colors

The palette is anchored by the "Gold/Orange" primary accent, used sparingly for critical actions and brand recognition. The foundation is built on high-contrast neutrals to ensure maximum legibility for long-form text and data tables.

- **Primary Canvas:** Pure White (#FFFFFF) is used for the main content areas to provide a clean reading experience.
- **Surface Neutrals:** Light Grey (#F5F5F5) is used for secondary surfaces, sidebars, and grouping containers to create a subtle layered effect.
- **Typography:** Brand Black (#000000) is used for all primary headings and body text to maintain an "official document" feel. Mid Grey (#666666) is reserved for metadata and placeholder text.
- **Semantic Logic:** Statuses use rich, professional tones (Forest Green, Professional Red, Rich Amber) that meet accessibility standards while providing immediate visual cues for issue severity.

## Typography

The design system utilizes **Inter** across all roles to ensure a systematic, utilitarian, and highly legible experience. The typography is tuned for data density and long-form correspondence management.

- **Headlines:** Use Bold (700) and SemiBold (600) weights with tighter letter-spacing for a modern, authoritative look.
- **Body:** Standard body text is optimized for reading at 16px with a generous 1.5x line height to prevent eye strain during prolonged use.
- **Labels:** Small labels and status badges use SemiBold weights and uppercase styling to distinguish them from interactive text and body content.
- **Hierarchy:** Dramatic scale differences between headlines and body text help users navigate complex forms and issue threads quickly.

## Layout & Spacing

The layout follows a **Fixed-Fluid Hybrid** model. The main dashboard content sits within a 12-column grid (max-width 1440px) to ensure comfortable line lengths for reading emails and reports, while sidebar navigations are fixed.

- **Rhythm:** An 8px linear scale governs all padding and margins. 
- **Density:** To maintain an "Institutional" feel, the system favors "Roomy" spacing over "Compact" density. Large margins (48px on desktop) are used to frame content and reduce visual clutter.
- **Responsiveness:** On mobile devices, the grid collapses to 4 columns with 16px side margins. Complex tables reflow into card-based lists to maintain legibility.

## Elevation & Depth

This design system avoids heavy shadows and skeuomorphism. Instead, it uses **Tonal Layers** and **Low-Contrast Outlines** to define hierarchy.

- **Layer 0 (Canvas):** Pure white background for the primary workspace.
- **Layer 1 (Surfaces):** Light Grey (#F5F5F5) for secondary panels, such as navigation sidebars or attachment drawers.
- **Layer 2 (Floating Elements):** Popovers and modals use a subtle, highly diffused ambient shadow (0px 4px 20px rgba(0,0,0,0.08)) and a 1px border (#E0E0E0) to differentiate from the canvas.
- **Depth:** Interaction is communicated through color shifts (Gold/Orange to Deep Yellow) rather than physical lift.

## Shapes

The shape language is **Soft**, using a 0.25rem (4px) base radius. This provides a professional, "sharper" look that aligns with government standards while avoiding the harshness of 0px corners.

- **Standard Elements:** Input fields, buttons, and cards use the base 4px radius.
- **Interactive States:** Focus states are indicated by a 2px offset solid stroke in Gold/Orange.
- **Badges:** Status indicators use a slightly more rounded 8px (rounded-lg) radius to distinguish them from functional UI components.

## Components

- **Buttons:** 
  - *Primary:* Gold/Orange background with white text. No shadow, flat design.
  - *Secondary:* White background with 1px Brand Black border and black text.
- **Status Badges:** Use "Soft Semantic" backgrounds (10% opacity of the semantic color) with "Solid Semantic" text. For example, a "Critical" badge uses a light red fill with Professional Red (#D32F2F) text for maximum clarity without visual noise.
- **Input Fields:** Use 1px Mid Grey borders. On focus, the border transitions to 2px Gold/Orange. Labels are always positioned above the field for accessibility.
- **Data Tables:** High-density rows with 1px bottom borders (#F5F5F5). The header row uses a Light Grey background with uppercase Label-MD typography.
- **Cards:** Used for issue summaries. Cards should have no shadow but use a 1px Light Grey border to define their boundaries on the white canvas.