<#
.SYNOPSIS
    Automated k6 Load & Performance Test Runner for merSETA NSDMS.
.DESCRIPTION
    Executes modular k6 performance scenarios with configurable workload profiles,
    custom VUs, duration overrides, and SLA threshold evaluations.
.PARAMETER Scenario
    Scenario to run: 'navigation', 'wsp', 'batch', 'signalr', 'all' (default: 'navigation')
.PARAMETER Profile
    Workload profile: 'smoke', 'average_load', 'stress', 'spike' (default: 'smoke')
.PARAMETER BaseUrl
    Target application URL (default: 'http://localhost:5121')
.PARAMETER VUs
    Optional explicit override for virtual users count.
.PARAMETER Duration
    Optional explicit override for duration (e.g. '30s', '2m').
.EXAMPLE
    .\run-k6.ps1 -Scenario navigation -Profile smoke
    .\run-k6.ps1 -Scenario all -Profile average_load -BaseUrl "http://localhost:5121"
#>

param(
    [ValidateSet('navigation', 'wsp', 'batch', 'signalr', 'all')]
    [string]$Scenario = 'navigation',

    [ValidateSet('smoke', 'average_load', 'stress', 'spike')]
    [string]$Profile = 'smoke',

    [string]$BaseUrl = 'http://localhost:5121',
    [int]$VUs = 0,
    [string]$Duration = ''
)

$ErrorActionPreference = "Stop"
$ScriptDir = Split-Path -Parent $MyInvocation.MyCommand.Path

Write-Host "=========================================================================" -ForegroundColor Cyan
Write-Host " merSETA NSDMS - Grafana k6 Enterprise Load Test Runner" -ForegroundColor Yellow
Write-Host "=========================================================================" -ForegroundColor Cyan
Write-Host " Target URL : $BaseUrl" -ForegroundColor White
Write-Host " Profile    : $Profile" -ForegroundColor White
Write-Host " Scenario   : $Scenario" -ForegroundColor White

# Check k6 binary
$k6Cmd = Get-Command k6 -ErrorAction SilentlyContinue
if (-not $k6Cmd) {
    if (Test-Path "C:\Program Files\k6\k6.exe") {
        $k6Exe = "C:\Program Files\k6\k6.exe"
    } else {
        Write-Error "k6 CLI is not found on PATH or 'C:\Program Files\k6\k6.exe'. Please install k6 or add to PATH."
        exit 1
    }
} else {
    $k6Exe = "k6"
}

# Scenario file mapping
$ScenarioMap = @{
    'navigation' = "$ScriptDir\scenarios\01_auth_and_navigation.js"
    'wsp'        = "$ScriptDir\scenarios\02_wsp_submission_flow.js"
    'batch'      = "$ScriptDir\scenarios\03_batch_ingestion.js"
    'signalr'    = "$ScriptDir\scenarios\04_blazor_signalr.js"
}

$ScenariosToRun = @()
if ($Scenario -eq 'all') {
    $ScenariosToRun = @('navigation', 'wsp', 'batch', 'signalr')
} else {
    $ScenariosToRun = @($Scenario)
}

$env:BASE_URL = $BaseUrl
$env:WS_URL = $BaseUrl.Replace("http://", "ws://").Replace("https://", "wss://")
$env:PROFILE = $Profile

$TotalSuccess = 0
$TotalFailed = 0

foreach ($scName in $ScenariosToRun) {
    $targetFile = $ScenarioMap[$scName]
    if (-not (Test-Path $targetFile)) {
        Write-Warning "Scenario file not found: $targetFile"
        continue
    }

    Write-Host "`n>>> Running Scenario [$scName]: $targetFile" -ForegroundColor Green
    
    $argsList = @("run", $targetFile, "-e", "BASE_URL=$($env:BASE_URL)", "-e", "WS_URL=$($env:WS_URL)", "-e", "PROFILE=$Profile")
    if ($VUs -gt 0) {
        $argsList += @("--vus", $VUs)
    }
    if (-not [string]::IsNullOrWhiteSpace($Duration)) {
        $argsList += @("--duration", $Duration)
    }

    & $k6Exe @argsList

    if ($LASTEXITCODE -eq 0) {
        Write-Host "✅ Scenario [$scName] PASSED (SLA thresholds satisfied)" -ForegroundColor Green
        $TotalSuccess++
    } else {
        Write-Host "❌ Scenario [$scName] FAILED or breached SLA thresholds (Exit code $LASTEXITCODE)" -ForegroundColor Red
        $TotalFailed++
    }
}

Write-Host "`n=========================================================================" -ForegroundColor Cyan
Write-Host " k6 Load Test Summary: $TotalSuccess Passed, $TotalFailed Failed" -ForegroundColor $(if ($TotalFailed -eq 0) { "Green" } else { "Red" })
Write-Host "=========================================================================" -ForegroundColor Cyan

if ($TotalFailed -gt 0) {
    exit 1
}
