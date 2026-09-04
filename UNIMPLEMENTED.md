# Unimplemented Register — Option & Lookup Controls

| ID | Module / Component | Requirement | Standard Rule | Status | Resolution Detail |
|---|---|---|---|---|---|
| UNIMPL-001 | `lookup.SicCodeType` | Composite index on `(Code, Name)` | OPT-003, OPT-004 | **RESOLVED** | Created composite non-clustered covering index `IX_SicCodeType_Code_Name` on `(Code, Name)` INCLUDE `(Active, Description, ChamberCode, SetaCode)` via `V2026_17_Lookup_Search_Composite_Indexes.sql` and `Phase23LookupIndexesMigrator`. |
| UNIMPL-002 | `lookup.OfoCodeType` | Composite index on `(Code, Name)` | OPT-003, OPT-004 | **RESOLVED** | Created composite non-clustered covering index `IX_OfoCodeType_Code_Name` on `(Code, Name)` INCLUDE `(Active, Description)` via `V2026_17_Lookup_Search_Composite_Indexes.sql` and `Phase23LookupIndexesMigrator`. |
| UNIMPL-003 | `lookup.StatssaAreaCodeType` | Search index on `(Code, Name)` | OPT-003, OPT-004 | **RESOLVED** | Created composite non-clustered covering index `IX_StatssaAreaCodeType_Code_Name` on `(Code, Name)` INCLUDE `(Active, Description)` for 22,108 spatial codes via `V2026_17_Lookup_Search_Composite_Indexes.sql` and `Phase23LookupIndexesMigrator`. |
| UNIMPL-004 | Secondary List Filters | Migration of secondary filter dropdowns to `MudToggleGroup` | E.1 | **RESOLVED** | Migrated to `MudToggleGroup` single-selection segmented controls per E.1 across `EmployerList`, `WspList`, and `GrantList`. |

