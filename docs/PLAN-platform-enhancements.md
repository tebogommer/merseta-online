# Project Plan: Comprehensive Future Platform Enhancements

**Document**: `docs/PLAN-platform-enhancements.md`  
**Status**: Ready for User Review & Decision  
**Target Solution**: .NET 10 Blazor Web App (`Nsdms.slnx`)  
**Target Database**: SQL Server Express (`NSDMS-NET`)

---

## 🎯 Executive Summary

This plan outlines the end-to-end architecture, technical design, database updates, service contracts, and verification criteria for the three requested future platform enhancements:

1. **Enhancement 1: Enterprise PDF & Excel Batch Report Generation Engine**  
   *QuestPDF and ClosedXML integration for print-ready executive packs, DHET QMR submissions, and SARS financial audit workbooks.*
2. **Enhancement 2: Real-time SignalR Event Matrix & Live Task Sync**  
   *Multi-user real-time workflow transition broadcasting, live inbox task sync, and SLA notification toasts.*
3. **Enhancement 3: High-DPI Organization & SDP Brand Asset Management**  
   *Branded logo/letterhead asset vault integration, dynamic detail view avatars, and certificate header rendering.*

---

## 🧱 Detailed Architectural Breakdown

### Enhancement 1: Enterprise PDF & Excel Batch Report Generation Engine

#### 1.1 Technical Stack & Dependencies
- **QuestPDF (v2024.x)**: Pure C# fluent layout engine for rendering pixel-perfect, responsive PDF documents.
- **ClosedXML (v0.104.x)**: Typed OpenXML spreadsheet builder for multi-tab Excel workbooks.
- **Theme Alignment**: PDF templates will use the exact tokens from `Light-DESIGN.md` (Primary `#865300`, Subtitles `#524436`, Surfaces `#F9F9F9`, and 4px radius).

#### 1.2 Service Architecture (`Nsdms.Application` & `Nsdms.Infrastructure`)
- **Service Contract**: `IReportExportService`
  ```csharp
  public interface IReportExportService
  {
      Task<byte[]> GenerateQmrExecutivePdfAsync(int reportId);
      Task<byte[]> GenerateSetmisBatchValidationPdfAsync(int batchId);
      Task<byte[]> GenerateSarsLevyReconWorkbookAsync(string finYear);
      Task<byte[]> GenerateGrantMoaAgreementPdfAsync(int moaId);
  }
  ```
- **Implementation**: `ReportExportService` in `Nsdms.Infrastructure/Services/`.
- **Client Delivery**: Blazor JS Interop helper `downloadFileFromStream` in `wwwroot/js/file-download.js`.

#### 1.3 UI Touchpoints
- **QMR Report Detail** (`/reports/qmr`): "Export Official DHET PDF" and "Download Excel Sheet".
- **SETMIS Compliance Hub** (`/compliance/setmis`): "Download Batch Validation Certificate".
- **Grant MOA Detail** (`/finance/grants/{id}`): "Download Legal MOA Execution Pack".

---

### Enhancement 2: Real-time SignalR Event Matrix & Live Task Sync

#### 2.1 Technical Stack & Protocols
- **ASP.NET Core SignalR**: WebSocket transport with Long-Polling fallback.
- **Circuit Lifecycle Synchronization**: Integration with Blazor Server circuits without circuit disconnection.

#### 2.2 Hub & Event Architecture
- **Hub Definition**: `NsdmsNotificationHub : Hub` at `/hubs/notifications`.
- **Event Contracts**:
  ```csharp
  public interface INsdmsNotificationClient
  {
      Task ReceiveTaskAssignment(string taskId, string title, string assignedRole);
      Task ReceiveWorkflowTransition(string entityType, int entityId, string fromState, string toState);
      Task ReceiveSlaWarning(string taskTitle, int hoursRemaining);
      Task ReceiveBroadcastAlert(string message, string severity);
  }
  ```
- **Server Action Bridge Hook**:
  - `WorkflowEngineService` injects `IHubContext<NsdmsNotificationHub, INsdmsNotificationClient>`.
  - On `ExecuteTransitionAsync`, the engine dispatches a typed broadcast to targeted user/role groups.

#### 2.3 UI Touchpoints
- **MainLayout AppBar**:
  - Live animated notification bell badge with unread count.
  - Dropdown drawer previewing incoming real-time notifications.
- **Universal Task Inbox** (`/tasks`):
  - Table auto-refreshes seamlessly when a new task is routed to the current user's role.
  - Instant toast confirmation: *"New Task Assigned: Review WSP Submission #WSP-2026-004"*.

---

### Enhancement 3: High-DPI Organization & SDP Brand Asset Management

#### 3.1 Domain Model & Database Governance
- **Entity Modifications**:
  - `Organisation`: Add `LogoDocumentId INT NULL`, `BrandColorHex NVARCHAR(20) NULL`.
  - `TrainingProvider`: Add `LogoDocumentId INT NULL`, `AccreditationBadgeDocumentId INT NULL`.
- **Schema Migration**:
  - Idempotent T-SQL migration in `Phase2SchemaMigrator.cs` adding FK indexes:
    `IX_Organisation_LogoDocumentId` and `IX_TrainingProvider_LogoDocumentId`.

#### 3.2 Service Architecture (`IBrandAssetService`)
- **Service Contract**:
  ```csharp
  public interface IBrandAssetService
  {
      Task<DocumentMetadata> UploadLogoAsync(int entityId, string entityType, Stream fileStream, string fileName, string contentType, string actor);
      Task<string?> GetLogoDataUriAsync(int entityId, string entityType);
      Task<bool> RemoveLogoAsync(int entityId, string entityType, string actor);
  }
  ```
- **Validation Rules**:
  - MIME Whitelist: `image/png`, `image/jpeg`, `image/webp`, `image/svg+xml`.
  - Max file size: 2MB.
  - Anti-tamper file signature verification.

#### 3.3 UI Touchpoints
- **Employer Detail (`/employers/{id}`)** & **SDP Detail (`/sdp/{id}`)**:
  - Top header avatar displays the uploaded high-DPI corporate logo.
  - Quick "Upload/Change Logo" modal in the Action Bridge.
- **Document Vault (`Components/Shared/DocumentVault.razor`)**:
  - Dedicated "Corporate Branding & Logos" category.

---

## 📋 Task Breakdown & Estimated Scope

| Task ID | Phase / Enhancement | Description | Key Files |
|---|---|---|---|
| **TASK-1.1** | Enhancement 1 (PDF/Excel) | Add QuestPDF & ClosedXML packages to `Nsdms.Infrastructure`. | `Nsdms.Infrastructure.csproj` |
| **TASK-1.2** | Enhancement 1 (PDF/Excel) | Implement `ReportExportService` with templates for QMR, SETMIS, and MOA. | `Nsdms.Infrastructure/Services/ReportExportService.cs` |
| **TASK-1.3** | Enhancement 1 (PDF/Excel) | Add JS download helper and wire export buttons on detail/reporting pages. | `wwwroot/js/file-download.js`, `QmrReportHub.razor` |
| **TASK-2.1** | Enhancement 2 (SignalR) | Create `NsdmsNotificationHub` and map route in `Program.cs`. | `Hubs/NsdmsNotificationHub.cs`, `Program.cs` |
| **TASK-2.2** | Enhancement 2 (SignalR) | Hook `WorkflowEngineService` to broadcast notifications on state changes. | `WorkflowEngineService.cs` |
| **TASK-2.3** | Enhancement 2 (SignalR) | Build notification bell component in `MainLayout.razor` & live sync on `/tasks`. | `MainLayout.razor`, `TaskInbox.razor` |
| **TASK-3.1** | Enhancement 3 (Branding) | Add `LogoDocumentId` columns and migrations in Domain & Infrastructure. | `Organisation.cs`, `Phase2SchemaMigrator.cs` |
| **TASK-3.2** | Enhancement 3 (Branding) | Implement `BrandAssetService` with validation and audit logging. | `BrandAssetService.cs` |
| **TASK-3.3** | Enhancement 3 (Branding) | Integrate logo display & upload controls in `EmployerDetail` and `ProviderDetail`. | `EmployerDetail.razor`, `ProviderDetail.razor` |

---

## 🧪 Verification & Acceptance Strategy

1. **Unit & Integration Tests (`Nsdms.Tests`)**:
   - `ReportExportServiceTests`: Validate PDF/Excel byte array generation without corruption.
   - `BrandAssetServiceTests`: Verify image MIME validation, upload limits, and audit double-writing.
   - `SignalRNotificationTests`: Test hub connection, group subscription, and event routing.
2. **Playwright E2E Tests**:
   - Verify file download triggers on QMR and SETMIS pages.
   - Verify logo upload modal and image rendering on Employer Detail view.
   - Verify real-time toast alert popups during workflow state transitions.

---

## 🚦 Decision Gate

This plan is prepared and on standby. No code has been modified.
Upon your decision and approval, we can implement:
- **All 3 Enhancements together**, or
- **One specific enhancement first** (e.g. PDF/Excel reports, or Real-time SignalR).
