# merSETA NSDMS - Enterprise Load & Performance Testing Suite

This directory contains the production-grade **Option A (Grafana k6 + NBomber)** load and performance test framework for the NSDMS (.NET 10 Blazor + SQL Server) platform.

---

## 🏗️ Architecture: Dual-Tier Testing Strategy

```
                                  NSDMS Load & Performance Framework
                                                   │
                ┌──────────────────────────────────┴──────────────────────────────────┐
                ▼                                                                     ▼
       [Tier 1: Grafana k6]                                                 [Tier 2: NBomber]
    (Protocol & User Simulation)                                       (In-Process & Persistence Stress)
 ├── High-concurrency VUs (HTTP/WS)                                   ├── Direct EF Core / RCSI Concurrency
 ├── Blazor SignalR Circuit Heartbeats                                 ├── ISO 9001 Atomic Double-Write Stress
 ├── Multi-step WSP / DG User Flows                                    ├── Lock escalation & TempDB Latches
 └── P95 / P99 SLA Threshold Enforcement                               └── Microsecond latency profiling
```

---

## 1. Grafana k6 Suite (`tests/load/k6`)

### Prerequisites
- `k6` CLI installed (available at `C:\Program Files\k6\k6.exe` or via `choco install k6`).
- Target web application running (e.g., `http://localhost:5121`).

### Available Scenarios
| Scenario | Script | Target Workload |
| :--- | :--- | :--- |
| `navigation` | `scenarios/01_auth_and_navigation.js` | Simulated user browsing across Dashboard, Employers, WSP, DG, and Learners. |
| `wsp` | `scenarios/02_wsp_submission_flow.js` | End-to-end WSP/ATR submission, skills planning grids, and digital quorum sign-offs. |
| `batch` | `scenarios/03_batch_ingestion.js` | High-volume SETMIS batch upload data spikes and status polling. |
| `signalr` | `scenarios/04_blazor_signalr.js` | Blazor Server SignalR circuit negotiation, WebSocket handshake, and ping/pong survival. |
| `all` | Sequentially executes all scenarios. | Full regression load test pass. |

### Workload Profiles
- `smoke`: 2 Virtual Users for 10 seconds (validation & CI sanity check).
- `average_load`: Ramps up to 50 concurrent users over 2 minutes (typical daytime traffic).
- `stress`: Scaled up to 300 concurrent users to identify breaking points.
- `spike`: Instant surge to 250 concurrent users within 20 seconds (statutory submission deadline simulation).

### Running k6 Tests
Using the automated PowerShell runner:
```powershell
# Run smoke test on navigation
.\tests\load\k6\run-k6.ps1 -Scenario navigation -Profile smoke

# Run WSP submission flow under average load against local dev server
.\tests\load\k6\run-k6.ps1 -Scenario wsp -Profile average_load -BaseUrl "http://localhost:5121"

# Run all scenarios under stress
.\tests\load\k6\run-k6.ps1 -Scenario all -Profile stress -BaseUrl "http://localhost:5121"
```

Direct execution using k6 CLI:
```bash
k6 run tests/load/k6/scenarios/01_auth_and_navigation.js -e PROFILE=average_load -e BASE_URL=http://localhost:5121
```

---

## 2. NBomber In-Process & Database Suite (`dotnet/Nsdms.LoadTester`)

NBomber runs natively in C# directly inside the solution, allowing direct stress-testing of EF Core 10, SQL Server RCSI snapshot isolation, and the ISO 9001 atomic audit logging transaction manager without network overhead.

### Running NBomber Database Stress Tests
```powershell
# Quick smoke test (5 ops/sec, 3 seconds)
dotnet run --project dotnet/Nsdms.LoadTester/Nsdms.LoadTester.csproj -- --nbomber --duration 3 --rate 5

# Full load benchmark (50 ops/sec, 30 seconds)
dotnet run --project dotnet/Nsdms.LoadTester/Nsdms.LoadTester.csproj -- --nbomber --duration 30 --rate 50
```

### Running NBomber HTTP Tests
```powershell
# HTTP benchmark against running application
dotnet run --project dotnet/Nsdms.LoadTester/Nsdms.LoadTester.csproj -- --nbomber-http --url http://localhost:5121 --duration 20 --rate 25
```

### What NBomber Tests:
1. `db_organisation_read_rcsi`: Non-blocking reads under high concurrency utilizing SQL Server Read Committed Snapshot Isolation (`READ_COMMITTED_SNAPSHOT ON`).
2. `db_wsp_submission_doublewrite`: High-throughput inserts of `WspSubmission` + `WspTrainingPlan` + atomic `AuditLog` entry with cryptographic digital security seal.

---

## 3. SLA Compliance Thresholds

Every load test verifies adherence to statutory performance thresholds:
- **P95 Latency:** $< 500\text{ ms}$
- **P99 Latency:** $< 1,000\text{ ms}$
- **Error Rate:** $< 1.0\%$
- **Transaction Atomicity:** Zero partial writes; $100\%$ audit log coverage on state mutations.
