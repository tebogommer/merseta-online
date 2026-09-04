# Blazor Components & Architecture Rule

> Governs component design, lifecycle, and layering in Blazor Interactive Server.

## 1. Thin Presentation Layer
- **No Direct DbContext**: Razor components MUST NOT inject `DbContext` or `IDbContextFactory`. All data operations must delegate to scoped application service interfaces.
- **No Business Logic**: Calculations, statutory validation, workflow transitions, and state changes belong in application services, not in `@code` blocks or UI event handlers.

## 2. Shared Component Set Mandatory Adoption
- Detail views must use `<EntityHeader>` for header zones. Hand-rolled `<MudPaper>` top bars are prohibited.
- Edit and create pages must wrap inputs in `<FormShell>` with sticky bottom actions and dirty tracking (`NavigationLock`).
- Tables must use `<DataGridShell>` with 7-tier page sizes.
- Badges must use `<StatusBadge>` with semantic tokens. Never use `<MudChip>` as pseudo-buttons or status indicators.

## 3. Error Handling & Circuit Protection
- Always wrap `@Body` inside `<ErrorBoundary>` in layout components to prevent SignalR circuit crashes.
- Never display raw `ex.Message` directly in user toasts (`Snackbar.Add`). Log structured error details to `ILogger` with correlation IDs and display user-friendly messages.
