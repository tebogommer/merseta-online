# Unimplemented Register — Option & Lookup Controls

| ID | Module / Component | Requirement | Standard Rule | Rationale / Block Reason | Resolution Path |
|---|---|---|---|---|---|
| UNIMPL-001 | `lookup.SicCodeType` | Composite index on `(Code, Name)` | OPT-003, OPT-004 | Rule 3.2 prohibits automated DDL index creation without database administrator sign-off. | Scheduled for next database migration cycle (`V2026_09_Lookup_Indexes.sql`). |
| UNIMPL-002 | `lookup.OfoCodeType` | Composite index on `(Code, Name)` | OPT-003, OPT-004 | Rule 3.2 prohibits automated DDL index creation without database administrator sign-off. | Scheduled for next database migration cycle. |
| UNIMPL-003 | `lookup.StatssaAreaType` | Search index on `(AreaCode, SubPlaceName)` | OPT-003, OPT-004 | High-volume table (22,000+ rows) requires DBA review before adding non-clustered index. | Scheduled for spatial lookup optimization pass. |
| UNIMPL-004 | Secondary List Filters | Migration of secondary filter dropdowns to `MudToggleGroup` | E.1 | Segmented controls on 2-5 item status filters across list pages (`EmployerList`, `WspList`, `GrantList`). | **RESOLVED** — Migrated to `MudToggleGroup` single-selection segmented controls per E.1. |
