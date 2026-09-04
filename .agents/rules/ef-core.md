# EF Core & Data Access Rule

> Enforces query performance, memory limits, and schema synchronization in EF Core 10.

## 1. Query Bounds & Pagination
- **No Unbounded Queries**: Every list query must implement pagination (`.Skip().Take()`) or explicit ceiling limit (`.Take(100)`). Calling `.ToListAsync()` on unpartitioned business tables is strictly prohibited.
- **Server-Side Aggregations**: Always execute counts, sums, and averages on the database engine (`.CountAsync()`, `.SumAsync()`, `.GroupBy()`). Never load full entity collections into memory to perform LINQ operations.

## 2. Read-Only Query Tracking
- Always append `.AsNoTracking()` to queries returning read-only views, search results, or DTO projections.
- Use `.Select(...)` projections to load only the required scalar fields rather than full entity graphs.

## 3. Schema Synchronization & Raw SQL
- All entity property changes in `Nsdms.Domain/Entities/` must have matching idempotent DDL clauses in `Nsdms.Infrastructure/Data/`.
- Dynamic SQL with user input interpolation is forbidden. Parameterize all queries via `SqlParameter` or `ExecuteSqlInterpolatedAsync`.
- T-SQL scripts containing SSMS `GO` separators must be executed via `SqlBatchRunner.ExecuteBatchesAsync`.
