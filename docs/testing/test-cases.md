# NSDMS Test Cases

## P0 Security & AuthZ (CASL)
1. **TC-SEC-001 (Vitest):** Anonymous users cannot read `fetchWSPs`.
2. **TC-SEC-002 (Vitest):** Anonymous users cannot execute `createWSPAction`.
3. **TC-SEC-003 (Vitest):** STANDARD user reading `fetchWSPs` only receives records where `createdBy` matches their user ID.
4. **TC-SEC-004 (Vitest):** ADMIN user reading `fetchWSPs` receives all records globally.
5. **TC-SEC-005 (Vitest):** STANDARD user cannot execute `deleteWSPAction` on a record they do not own.
6. **TC-SEC-006 (Vitest):** STANDARD user cannot update arbitrary fields in global lookup models (e.g. `saveLookup`).
7. **TC-SEC-007 (Playwright):** Authenticated ADMIN can view Dashboard global stats UI.
8. **TC-SEC-008 (Playwright):** Authenticated STANDARD can only view their contextual UI cards on the Dashboard.
9. **TC-SEC-009 (Vitest):** STANDARD user cannot escalate role to ADMIN via any mutation hook.
10. **TC-SEC-010 (Vitest):** Restricted server actions throw "Forbidden" if ability check fails.

## P0 Workflow & Critical Process
11. **TC-WFL-001 (Playwright):** Learner Enrollment e2e flow reaches "Success" on valid Zod form submission.
12. **TC-WFL-002 (Playwright):** WSP creation successfully spawns a UI confirmation toast.
13. **TC-WFL-003 (Playwright):** Provider Accreditation e2e registers an organisation through the registration pipeline.
14. **TC-WFL-004 (Vitest):** XState transition from Draft to Under Review applies context metadata correctly.
15. **TC-WFL-005 (Vitest):** XState blocking transition fails appropriately if required context is missing.
16. **TC-WFL-006 (Playwright):** Employer registration succeeds and redirects to Master Detail view.
17. **TC-WFL-007 (Playwright):** Form validation blocks invalid characters in SDL number (e.g. L-1234567).
18. **TC-WFL-008 (Vitest):** `createWSPAction` enforces `financialYear` mapping context successfully.
19. **TC-WFL-009 (Playwright):** "Visit" scheduling enforces UI selection of a `ContactPerson`.
20. **TC-WFL-010 (Vitest):** `saveLookup` enforces mandatory existence of Code/Description combinations.
21. **TC-WFL-011 (Manual / Playwright):** Workplace Approval & Site Audit (WPAPP) Multi-Persona Lifecycle e2e validation (SDF -> CLO -> Assessor -> QA Approver).

## P0 Data Integrity & Auditing
21. **TC-DAT-001 (Vitest):** Double-Write check: `createWSPAction` successful call creates a row in `AuditLog`.
22. **TC-DAT-002 (Vitest):** Double-Write check: `deleteWSPAction` creates a row in `AuditLog` mirroring the deleted context.
23. **TC-DAT-003 (Vitest):** Required Relationship: Visit mutation throws database or Zod error if `contactPersonId` is effectively null.
24. **TC-DAT-004 (Vitest):** `AuditLog` captures correct stringified JSON snapshot of the mutation.
25. **TC-DAT-005 (Vitest):** Missing or corrupted audit trails (e.g. transaction failure) roll back the entire mutation block cleanly.

## P1 UI CRUD Consistency
26. **TC-UI-001 (Playwright):** Master Data Grid columns hide gracefully on mobile viewports.
27. **TC-UI-002 (Playwright):** Organisation edit modal opens in FULL layout instead of segmented view (Axiom rule).
28. **TC-UI-003 (Playwright):** "Back" Breadcrumb consistently leads from Stacked Detail back to Master List.
29. **TC-UI-004 (Playwright):** "New WSP" button requires visible Lucide-react `LayoutTemplate` icon pattern.
30. **TC-UI-005 (Playwright):** Dashboard loading uses `<Skeleton />` before the data hook resolves.
