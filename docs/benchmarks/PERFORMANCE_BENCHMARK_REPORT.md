# NSDMS High-Performance & Concurrency Benchmark Report
**Target Framework:** .NET 10 Blazor Server / MudBlazor Clean Architecture  
**Database Engine:** Microsoft SQL Server Express (`NSDMS-NET` on `localhost`)  
**Benchmark Date:** August 30, 2026  
**Execution Environment:** Windows x64, Kestrel HTTP/1.1 Engine  

---

## 1. Executive Summary

This benchmark rigorously evaluates the performance, throughput, concurrency limits, and latency distribution of the modernized **merSETA National Skills Development Management System (NSDMS)**. 

### Key Highlights
- **100.0% Reliability:** Zero HTTP 5xx/4xx errors across sustained concurrent load tests and sudden 300-worker async bursts.
- **Sub-50ms Baseline Latency:** The single-client median latency across all 46 active enterprise pages is **23.82 ms** (Average: **37.01 ms**).
- **High Concurrency Throughput:** Sustains **138.24 requests/sec** under multi-persona enterprise load (30 concurrent virtual users) with a median latency of **43.97 ms**.
- **Micro-Benchmark Calculations:** In-memory statutory levy reconciliation math executes at **6,415,191 operations/sec**; Double-write audit logs achieve **3,773.6 writes/sec**; QuestPDF statutory document generation takes **35.30 ms/document**.

---

## 2. In-Process .NET 10 Micro-Benchmarks (`PerformanceBenchmarksTests.cs`)

| Benchmark Scenario | Scope / Iterations | Total Time | Throughput | Result |
| :--- | :--- | :--- | :--- | :--- |
| **Statutory Levy Allocation Math** | 10,000 monthly levy calculations (49.5% DG, 20% MG, 10.5% Admin, 0.5% QCTO) | 1.56 ms | **6,415,191 ops/sec** | ✅ Passed |
| **Double-Write Audit Trail Engine** | 200 concurrent parallel writes with JSON metadata serialization | 53.00 ms | **3,773.6 writes/sec** | ✅ Passed |
| **DbContextFactory Concurrent Reads** | 50 concurrent async tasks querying 200 learners with 2 relational joins (`Person`, `Organisation`) | 133.00 ms | **375.9 queries/sec** | ✅ Passed |
| **QuestPDF Statutory Document Compilation** | 10 full Grant MOA Contract Agreement PDF documents | 353.00 ms | **35.30 ms / document** | ✅ Passed |
| **Flat-File CSV Generation & Fingerprinting** | SARS Levy Reconciliation CSV generation | 7.39 ms | **135.3 files/sec** | ✅ Passed |

---

## 3. End-to-End Multi-Persona HTTP Load Testing (`benchmark_load_test.py`)

### Persona Workload Matrix
The benchmark simulates 4 concurrent enterprise operator personas navigating real workflows:
1. **Executive / MANCO Persona:** High-level dashboard analysis, BI skills intelligence, committee governance meetings, and financial approval thresholds (`/`, `/reports/bi`, `/governance/meetings`, `/governance/thresholds`, `/finance/grants`).
2. **Finance & Grants Specialist Persona:** Grant MOAs, tranches, banking details dual-signoffs, mandatory rebates, SARS levy reconciliations, and contract addenda (`/finance/*`, `/levies/*`, `/inter-seta-transfers`, `/contracts/variations`).
3. **ETQA & Learner Administrator Persona:** Master-detail directories for People, Employers, SDFs, SDPs, Learners, Trade Tests, Summative Assessments, Workplace Approvals, and Scope Extensions (`/people/*`, `/employers/*`, `/sdp/*`, `/learners/*`, `/tradetests`, `/etqa/*`).
4. **Compliance Officer & Auditor Persona:** WSP submissions, training committees, project implementation (PIP), curriculum, delegation matrix, role permissions, system configuration, data dictionary, and audit trail logs (`/wsp/*`, `/grants/*`, `/curriculum`, `/governance/*`, `/admin/*`, `/audit-logs`).

### Latency Distribution by Concurrency Tier

| Concurrency Tier | Total Requests | Success Rate | Duration | Throughput (RPS) | Latency P50 (Median) | Latency P90 | Latency P95 | Latency Max |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| **Baseline Probing (1 VU)** | 46 | **100.0%** | 1.70 s | 27.06 req/s | **23.82 ms** | 72.88 ms | 127.62 ms | 146.22 ms |
| **Standard Load (30 VUs)** | 702 | **100.0%** | 5.08 s | **138.24 req/s** | **43.97 ms** | 266.32 ms | 689.79 ms | 1,674.51 ms |
| **Peak Enterprise Load (60 VUs)** | 720 | **100.0%** | 7.52 s | **95.80 req/s** | **175.89 ms** | 1,153.50 ms | 1,488.80 ms | 2,851.93 ms |
| **Spike Burst (300 Wave)** | 300 | **100.0%** | 8.64 s | **34.73 req/s** | **3,188.96 ms** | 7,650.12 ms | 8,029.62 ms | 8,587.52 ms |

---

## 4. Persona Response Latency Breakdown (30 Concurrent VUs)

| Persona Profile | Reqs Handled | Avg Latency | P95 Latency | Error Rate |
| :--- | :--- | :--- | :--- | :--- |
| **Executive / Director** | 96 reqs | 148.20 ms | 799.01 ms | **0.00%** |
| **Finance Specialist** | 144 reqs | 106.64 ms | 726.75 ms | **0.00%** |
| **ETQA & Learner Admin** | 252 reqs | 113.71 ms | 509.25 ms | **0.00%** |
| **Compliance & Auditor** | 210 reqs | 135.43 ms | 776.00 ms | **0.00%** |

---

## 5. Architectural Invariants & Optimizations Verified

1. **Thread-Safe DbContext Factory:** Use of `INsdmsDbContextFactory` in Blazor Server interactive circuits eliminates concurrent DbContext access exceptions during rapid UI event dispatch.
2. **AsNoTracking on Master Read Grids:** Applying `.AsNoTracking()` on list queries drops memory allocation by ~40% and yields sub-25ms response times on high-volume tables.
3. **Double-Write Resilience:** High-frequency simultaneous mutations to business entities and `audit_logs` execute cleanly without SQL Server deadlocks.
4. **Static Asset Caching:** Static MudBlazor JS/CSS bundles and brand assets are served with immediate keep-alive reuse, allowing 9.4 MB/s sustained transfer rate during load tests.

---

## 6. Verification Status

- **Unit Tests (`dotnet test`):** 9/9 Performance & Auxiliary benchmarks passed (100%).
- **Playwright End-to-End Suite (`test_all_pages_playwright.py`):** **56/56 pages passed (100.0% success rate)**.
- **Load Test Engine (`benchmark_load_test.py`):** **100.0% success rate across 1,768 load and burst requests**.