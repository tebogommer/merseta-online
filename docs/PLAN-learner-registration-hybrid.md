# Implementation Plan - Hybrid Evolutionary Learner Registration (Dual-Channel Architecture)

## Context & Architecture Principle: Dual-Channel Architecture
Modernise the MerSETA Learner Registration pipeline by introducing high-speed automated digital alternatives while strictly preserving all existing manual registration processes (LearnerAgreementRegistrationWizard.razor, BursaryRegistrationWizard.razor, and regional officer verification).

The system provides two concurrent operational channels feeding into the identical underlying statutory ledger (CompanyLearner, audit_logs, and SETMIS/NLRD engines):
- Channel 1 (Existing Manual Single Registration Workflow): Multi-step UI wizards, manual file attachments, manual officer maker-checker review, and manual condonation approvals.
- Channel 2 (New Automated Bulk Fast-Track): Interactive bulk spreadsheet ingestion with in-browser pre-flight linting, automated document OCR/verification checks, and rule-based Straight-Through Processing (STP) for 100% compliant, low-risk corporate intakes.

## User Review Required
- Zero Regression Invariant: The existing manual wizards (/learners/register-agreement, /learners/register-bursary) remain 100% functional and untouched in their core routing.
- Straight-Through Processing (STP) Governance: Applications qualifying for STP (levy-paying employers in good standing submitting valid learnerships with 100% verified documents and compliant mentor ratios) are automatically registered (Status: REGISTERED) with an explicit audit stamp: Actor = 'SYSTEM_STP_GATEKEEPER'. Any item with ratio exemptions, minor guardian anomalies, or overdue submission dates routes directly to human officer review.
- Dual-Channel Selection: In LearnerList.razor, the action zone offers both options:
  - Register Learner (Manual Wizard)
  - Bulk Fast-Track Registration (Spreadsheet Ingestion)

## Technical Architecture & Phasing
- Phase 1: Bulk Ingestion & Live Client-Side Pre-Flight Engine (Fast-Track Channel)
- Phase 2: Straight-Through Processing (STP) Risk Engine
- Phase 3: Automated Document OCR & Verification Shield

## Done When
- [ ] Manual registration wizards remain 100% intact and functional.
- [ ] Bulk fast-track spreadsheet ingestion hub operational at /learners/bulk-register.
- [ ] Straight-Through Processing rule engine automatically registers compliant low-risk applications with audited change log.
- [ ] Non-compliant or high-risk exceptions route seamlessly to human officer review queue.
- [ ] 100% unit and integration test suite passes.
