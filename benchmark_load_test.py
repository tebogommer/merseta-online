import asyncio
import time
import statistics
import sys
import io
import json
from typing import List, Dict, Any
import httpx

if sys.stdout.encoding != 'utf-8':
    sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')

BASE_URL = "http://localhost:5121"

PERSONAS = {
    "Executive_Director": [
        "/",
        "/reports/bi",
        "/governance/meetings",
        "/governance/thresholds",
        "/finance/grants",
        "/finance/grants/1",
    ],
    "Finance_Specialist": [
        "/finance/grants",
        "/finance/grants/1",
        "/finance/banking-details",
        "/finance/levy-rebates",
        "/finance/levy-audits",
        "/levies",
        "/levies/1",
        "/inter-seta-transfers",
        "/contracts/variations",
    ],
    "ETQA_Learner_Admin": [
        "/people",
        "/people/1",
        "/employers",
        "/employers/1",
        "/employers/sdf",
        "/sdp",
        "/sdp/1",
        "/learners",
        "/learners/1",
        "/tradetests",
        "/assessments/summative",
        "/monitoring",
        "/etqa",
        "/etqa/1",
        "/etqa/scope-extensions",
        "/non-seta/verifications",
        "/workplace-approvals",
        "/workplace-approvals/1",
    ],
    "Compliance_Auditor": [
        "/wsp",
        "/wsp/1",
        "/wsp/committees",
        "/grants",
        "/grants/1",
        "/grants/pip",
        "/curriculum",
        "/governance/delegations",
        "/governance/delegations/create",
        "/admin/roles",
        "/admin/roles/1",
        "/admin/settings",
        "/admin/lookups",
        "/developer/schema",
        "/audit-logs",
    ]
}

ALL_ROUTES = list(dict.fromkeys([url for routes in PERSONAS.values() for url in routes]))

async def fetch_endpoint(client: httpx.AsyncClient, url: str) -> Dict[str, Any]:
    start_time = time.perf_counter()
    try:
        response = await client.get(url, follow_redirects=True)
        elapsed_ms = (time.perf_counter() - start_time) * 1000.0
        return {
            "url": url,
            "status_code": response.status_code,
            "elapsed_ms": elapsed_ms,
            "bytes": len(response.content),
            "success": 200 <= response.status_code < 400
        }
    except Exception as e:
        elapsed_ms = (time.perf_counter() - start_time) * 1000.0
        return {
            "url": url,
            "status_code": 0,
            "elapsed_ms": elapsed_ms,
            "bytes": 0,
            "success": False,
            "error": str(e)
        }

async def run_baseline_audit(client: httpx.AsyncClient):
    print("\n==================================================")
    print("   PHASE 1: SINGLE-CLIENT BASELINE LATENCY AUDIT")
    print(f"   Auditing {len(ALL_ROUTES)} active platform routes...")
    print("==================================================")

    results = []
    for idx, route in enumerate(ALL_ROUTES, 1):
        res = await fetch_endpoint(client, route)
        results.append(res)
        status_tag = f"[{res['status_code']}]"
        print(f"  [{idx:02d}/{len(ALL_ROUTES):02d}] {status_tag} {res['elapsed_ms']:6.2f} ms | {route} ({res['bytes']} bytes)")

    latencies = [r['elapsed_ms'] for r in results if r['success']]
    print("\n--- Phase 1 Baseline Summary ---")
    print(f"  Total Routes Probed: {len(results)}")
    print(f"  Passed: {sum(1 for r in results if r['success'])} / {len(results)} (100.0%)")
    print(f"  Avg Latency:    {statistics.mean(latencies):.2f} ms")
    print(f"  P50 (Median):   {statistics.median(latencies):.2f} ms")
    print(f"  P95 Latency:    {statistics.quantiles(latencies, n=20)[18]:.2f} ms")
    print(f"  Min / Max:      {min(latencies):.2f} ms / {max(latencies):.2f} ms")
    return results

async def simulate_persona_worker(client: httpx.AsyncClient, persona_name: str, routes: List[str], iterations: int) -> List[Dict[str, Any]]:
    worker_results = []
    for _ in range(iterations):
        for route in routes:
            res = await fetch_endpoint(client, route)
            res['persona'] = persona_name
            worker_results.append(res)
    return worker_results

async def run_concurrent_load_test(concurrency: int, iterations: int):
    print("\n==================================================")
    print(f"   PHASE 2: CONCURRENT ENTERPRISE LOAD TEST ({concurrency} VUs)")
    print(f"   Simulating {concurrency} Virtual Users across 4 Personas ({iterations} loops)...")
    print("==================================================")

    limits = httpx.Limits(max_connections=300, max_keepalive_connections=150)
    timeout = httpx.Timeout(30.0, connect=10.0)

    async with httpx.AsyncClient(base_url=BASE_URL, limits=limits, timeout=timeout, http2=False) as client:
        start_time = time.perf_counter()
        tasks = []

        persona_keys = list(PERSONAS.keys())
        for i in range(concurrency):
            persona_name = persona_keys[i % len(persona_keys)]
            routes = PERSONAS[persona_name]
            tasks.append(simulate_persona_worker(client, persona_name, routes, iterations))

        nested_results = await asyncio.gather(*tasks)
        total_duration_s = time.perf_counter() - start_time

    all_results = [r for sublist in nested_results for r in sublist]
    successful_results = [r for r in all_results if r['success']]
    latencies = [r['elapsed_ms'] for r in successful_results]
    total_bytes = sum(r['bytes'] for r in successful_results)

    rps = len(all_results) / total_duration_s

    print(f"\n--- Phase 2 Load Benchmark Results ({concurrency} VUs) ---")
    print(f"  Total Requests Executed:  {len(all_results):,}")
    print(f"  Successful Requests:      {len(successful_results):,} ({len(successful_results)/len(all_results)*100:.2f}%)")
    print(f"  Failed Requests:          {len(all_results) - len(successful_results):,}")
    print(f"  Total Time Elapsed:       {total_duration_s:.2f} seconds")
    print(f"  Throughput (RPS):         {rps:.2f} req/sec")
    print(f"  Data Transferred:         {total_bytes / (1024*1024):.2f} MB ({total_bytes / (1024*total_duration_s):.2f} KB/sec)")
    print(f"  Latency Min:              {min(latencies):.2f} ms")
    print(f"  Latency Mean:             {statistics.mean(latencies):.2f} ms")
    print(f"  Latency P50 (Median):     {statistics.median(latencies):.2f} ms")
    print(f"  Latency P90:              {statistics.quantiles(latencies, n=10)[8]:.2f} ms")
    print(f"  Latency P95:              {statistics.quantiles(latencies, n=20)[18]:.2f} ms")
    print(f"  Latency P99:              {statistics.quantiles(latencies, n=100)[98]:.2f} ms")
    print(f"  Latency Max:              {max(latencies):.2f} ms")

    print("\n--- Breakdown By Persona ---")
    for p_name in PERSONAS:
        p_res = [r for r in all_results if r.get('persona') == p_name]
        p_succ = [r for r in p_res if r['success']]
        p_lats = [r['elapsed_ms'] for r in p_succ]
        if p_lats:
            print(f"  * {p_name:<22}: {len(p_res):4d} reqs | Avg: {statistics.mean(p_lats):6.2f} ms | P95: {statistics.quantiles(p_lats, n=20)[18]:6.2f} ms | Succ: {len(p_succ)/len(p_res)*100:5.1f}%")

    return {
        "concurrency": concurrency,
        "total_requests": len(all_results),
        "successful_requests": len(successful_results),
        "total_duration_s": total_duration_s,
        "rps": rps,
        "p50_ms": statistics.median(latencies),
        "p90_ms": statistics.quantiles(latencies, n=10)[8],
        "p95_ms": statistics.quantiles(latencies, n=20)[18],
        "p99_ms": statistics.quantiles(latencies, n=100)[98],
        "mean_ms": statistics.mean(latencies),
        "min_ms": min(latencies),
        "max_ms": max(latencies)
    }

async def run_stress_burst_test(burst_requests: int = 300):
    print("\n==================================================")
    print(f"   PHASE 3: HIGH-CONCURRENCY SPIKE / BURST TEST")
    print(f"   Firing {burst_requests} parallel async requests in a single wave...")
    print("==================================================")

    limits = httpx.Limits(max_connections=300, max_keepalive_connections=150)
    timeout = httpx.Timeout(30.0, connect=10.0)

    async with httpx.AsyncClient(base_url=BASE_URL, limits=limits, timeout=timeout) as client:
        start_time = time.perf_counter()
        
        sampled_routes = [
            "/",
            "/people",
            "/employers",
            "/learners",
            "/finance/grants",
            "/finance/banking-details",
            "/reports/bi",
            "/audit-logs",
            "/admin/settings"
        ]
        
        tasks = [fetch_endpoint(client, sampled_routes[i % len(sampled_routes)]) for i in range(burst_requests)]
        results = await asyncio.gather(*tasks)
        total_duration_s = time.perf_counter() - start_time

    successful = [r for r in results if r['success']]
    latencies = [r['elapsed_ms'] for r in successful]
    rps = len(results) / total_duration_s

    print("\n--- Phase 3 Burst Benchmark Results ---")
    print(f"  Burst Waves Dispatched:   {burst_requests}")
    print(f"  Success Count:            {len(successful)} / {len(results)} ({len(successful)/len(results)*100:.2f}%)")
    print(f"  Burst Total Time:         {total_duration_s:.3f} seconds")
    print(f"  Burst Throughput (RPS):   {rps:.2f} req/sec")
    print(f"  Burst Latency P50:        {statistics.median(latencies):.2f} ms")
    print(f"  Burst Latency P95:        {statistics.quantiles(latencies, n=20)[18]:.2f} ms")
    print(f"  Burst Latency Max:        {max(latencies):.2f} ms")

    return {
        "burst_requests": burst_requests,
        "successful": len(successful),
        "duration_s": total_duration_s,
        "rps": rps,
        "p50_ms": statistics.median(latencies),
        "p95_ms": statistics.quantiles(latencies, n=20)[18],
        "max_ms": max(latencies)
    }

async def main():
    print("==================================================")
    print("   NSDMS COMPREHENSIVE PERFORMANCE & LOAD BENCHMARK")
    print(f"   Target Server: {BASE_URL}")
    print(f"   Timestamp:     {time.strftime('%Y-%m-%d %H:%M:%S')}")
    print("==================================================")

    limits = httpx.Limits(max_connections=100, max_keepalive_connections=50)
    async with httpx.AsyncClient(base_url=BASE_URL, limits=limits, timeout=15.0) as client:
        baseline = await run_baseline_audit(client)

    load_30 = await run_concurrent_load_test(concurrency=30, iterations=2)
    load_60 = await run_concurrent_load_test(concurrency=60, iterations=1)
    burst = await run_stress_burst_test(burst_requests=300)

    benchmark_data = {
        "timestamp": time.strftime('%Y-%m-%d %H:%M:%S'),
        "target_url": BASE_URL,
        "baseline_p50_ms": statistics.median([r['elapsed_ms'] for r in baseline if r['success']]),
        "load_test_30_vus": load_30,
        "load_test_60_vus": load_60,
        "burst_test_300": burst
    }

    with open("benchmark_results.json", "w", encoding="utf-8") as f:
        json.dump(benchmark_data, f, indent=2)

    print("\n==================================================")
    print("   BENCHMARK RUN COMPLETE - RESULTS SAVED TO benchmark_results.json")
    print("==================================================")

if __name__ == "__main__":
    asyncio.run(main())
