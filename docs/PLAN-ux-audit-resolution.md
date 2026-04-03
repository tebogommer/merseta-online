# PLAN-ux-audit-resolution.md

## CONTEXT
- **User Request**: ignore SEO and remove it from test suite and policy. focus on UX.
- **Goal**: Address the `UX Audit` failures identified in the `checklist.py` master quality suite without engaging in SEO audits. We identified that the script might also be targeting legacy `javadoc` files, which should be ignored.

## PHASE 1: ANALYSIS & DISCOVERY
1. **Scope Definition**: Analyze the Next.js `app/` and `components/` folders specifically for UX audit issues (typography errors, missing hover micro-interactions, missing toast notifications, color contrast on shadcn buttons, semantic spacing issues).
2. **Exclusion Check**: Ensure that `ux_audit.py` ignores the legacy `c:\Antigravity\nsdms-2026-04-01\nsdms\MerSETA\doc\` and `target\` folders, focusing exclusively on the `nsdms-next` directory.
3. **UX Strategy Check**: Review the `frontend-specialist` documentation + Shadcn UIs specifically around high contrast, interaction cues and breadcrumb IDs.

## PHASE 2: PLANNING
Task Breakdown:
- **Task A: Adjust Audit Scope**: Modify `ux_audit.py` if necessary to explicitly target ONLY `nsdms-next/` folder and `.tsx` extension files, preventing it from parsing legacy HTML/Javadocs.
- **Task B: UX Typography & Consistency**: Audit `nsdms-next/app/` globally to ensure all `.tsx` components respect `line-height` standard, heading hierarchy mapping, and responsive text adjustments.
- **Task C: Meaningful Iterative Feedback**: Inspect primary action triggers (e.g. Save, Update, Enroll) to enforce hover states, `useTransition` loading state spinners, and contextual Shadcn `Toast` messaging upon successful navigation paths.
- **Task D: Semantic Visual Design Check**: Verify the usage of custom HSL brand colors in `tailwind.config.ts`, ensuring that all major interface blocks accurately reflect the high-impact "Antigravity" premium design principles.

## PHASE 3: SOLUTIONING (ARCHITECTURE)
- **Agent Assignment**: Make sure `frontend-specialist` manages Task B, C & D. `python-patterns` agent manages Task A.
- **UI Interaction Approach**: Adopt Next.js React 19 `useTransition` mapping across interactive forms. Wait to build explicit loading components (`loading.tsx`) to visually cushion route transitions.
- **Global Constraints**: No generic default HTML outlines. Standardize `ring-primary` and `ring-offset-2` tailwind conventions for all accessible `focus` states.

## PHASE 4: VERIFICATION CRITERIA
- `python .agent/scripts/checklist.py .` must output `✅ UX Audit: PASSED`.
- Running the `npx playwright test tests/e2e` suite MUST REMAIN AT 100% PASS RATE to ensure no UX improvements broke accessibility logic or DOM selection points.

## NEXT STEPS
Review this plan. Once approved, use `/create` or "proceed" to begin implementing these UX refinements file-by-file!
