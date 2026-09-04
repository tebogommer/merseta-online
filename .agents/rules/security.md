# Security & POPIA Governance Rule

> Enforces OWASP Top 10, POPIA, and ASP.NET Core Blazor security invariants.

## 1. Authentication & Authorization
- **Router Gateway**: `Routes.razor` must use `<AuthorizeRouteView>`.
- **Component Authorization**: All operational and administrative pages must declare `@attribute [Authorize]` or role-specific attributes (`@attribute [Authorize(Roles = "Admin,Executive")]`).
- **Endpoint Protection**: Every minimal API endpoint returning files, PDFs, or data must explicitly declare `.RequireAuthorization()`.
- **Dual Enforcement**: Enforce CASL ability checks both in UI (`CaslActionButton`) AND server-side in service methods.

## 2. Secret Management & Database Connections
- Never commit plaintext connection strings or credentials to `appsettings.json`.
- Never hardcode connection strings as C# fallbacks in `Program.cs`.
- Always configure SQL Server connections with `Encrypt=True;TrustServerCertificate=False`.

## 3. SQL Injection Prevention
- Never build dynamic SQL statements using string interpolation `$@"..."` inside `ExecuteSqlRaw` or `FromSqlRaw`.
- Always pass parameters as `SqlParameter` or use `ExecuteSqlInterpolatedAsync`.
