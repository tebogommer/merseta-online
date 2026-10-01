<#
.SYNOPSIS
    Automated Database and Lookup Scripter for merSETA NSDMS.
.DESCRIPTION
    Scripts the database schema (DDL, tables, constraints, indexes)
    and exports all lookup table data (lookup.*) with INSERT statements.
    Required by repository policy on git commit and git push.
.PARAMETER ServerInstance
    Target SQL Server instance (default: localhost\SQLEXPRESS).
.PARAMETER Database
    Database name (default: NSDMS-NET).
.PARAMETER Username
    SQL authentication username (default: NSDMS-NET).
.PARAMETER Password
    SQL authentication password (default: NSDMS-NET).
.PARAMETER OutputPath
    Output SQL file path (default: dotnet/Nsdms.Infrastructure/Data/SqlScripts/Database_Schema_And_Lookups.sql).
#>
[CmdletBinding()]
param(
    [string]$ServerInstance = "localhost\SQLEXPRESS",
    [string]$Database = "NSDMS-NET",
    [string]$Username = "NSDMS-NET",
    [string]$Password = "NSDMS-NET",
    [string]$OutputPath = "dotnet/Nsdms.Infrastructure/Data/SqlScripts/Database_Schema_And_Lookups.sql"
)

$ErrorActionPreference = "Stop"

Write-Host "========================================================" -ForegroundColor Cyan
Write-Host "merSETA NSDMS - Database and Lookup Scripter" -ForegroundColor Cyan
Write-Host "Server:   $ServerInstance" -ForegroundColor Cyan
Write-Host "Database: $Database" -ForegroundColor Cyan
Write-Host "Output:   $OutputPath" -ForegroundColor Cyan
Write-Host "========================================================" -ForegroundColor Cyan

# Load SMO assemblies
try {
    Add-Type -AssemblyName 'Microsoft.SqlServer.Smo, Version=16.0.0.0, Culture=neutral, PublicKeyToken=89845dcd8080cc91' -ErrorAction Stop
    Add-Type -AssemblyName 'Microsoft.SqlServer.ConnectionInfo, Version=16.0.0.0, Culture=neutral, PublicKeyToken=89845dcd8080cc91' -ErrorAction Stop
} catch {
    Write-Host "Loading SMO via partial name fallback..." -ForegroundColor Yellow
    [System.Reflection.Assembly]::LoadWithPartialName('Microsoft.SqlServer.Smo') | Out-Null
    [System.Reflection.Assembly]::LoadWithPartialName('Microsoft.SqlServer.ConnectionInfo') | Out-Null
}

$serverConn = New-Object Microsoft.SqlServer.Management.Common.ServerConnection($ServerInstance, $Username, $Password)
$server = New-Object Microsoft.SqlServer.Management.Smo.Server($serverConn)

if (-not $server.Databases.Contains($Database)) {
    Write-Error "Database [$Database] was not found on server [$ServerInstance]."
}

$db = $server.Databases[$Database]
Write-Host "Connected to [$Database]. Found $($db.Tables.Count) tables, $($db.Views.Count) views." -ForegroundColor Green

# Ensure target directory exists
$targetFile = [System.IO.Path]::GetFullPath((Join-Path (Get-Location).Path $OutputPath))
$outputDir = [System.IO.Path]::GetDirectoryName($targetFile)
if (-not (Test-Path $outputDir)) {
    New-Item -ItemType Directory -Path $outputDir -Force | Out-Null
}

$sb = New-Object System.Text.StringBuilder
[void]$sb.AppendLine("-- ====================================================================================================")
[void]$sb.AppendLine("-- merSETA NSDMS - Complete Database Schema and Lookup Reference Data Export")
[void]$sb.AppendLine("-- Generated: $([DateTime]::UtcNow.ToString('yyyy-MM-dd HH:mm:ss')) UTC")
[void]$sb.AppendLine("-- Target Engine: Microsoft SQL Server ($ServerInstance / $Database)")
[void]$sb.AppendLine("-- Note: Auto-generated for repository synchronization during git commit / push.")
[void]$sb.AppendLine("-- ====================================================================================================")
[void]$sb.AppendLine("")
[void]$sb.AppendLine("SET NOCOUNT ON;")
[void]$sb.AppendLine("SET XACT_ABORT ON;")
[void]$sb.AppendLine("GO")
[void]$sb.AppendLine("")

# 1. Schemas
[void]$sb.AppendLine("-- ====================================================================================================")
[void]$sb.AppendLine("-- 1. SCHEMAS")
[void]$sb.AppendLine("-- ====================================================================================================")
$systemSchemas = @("dbo", "guest", "INFORMATION_SCHEMA", "sys", "db_owner", "db_accessadmin", "db_securityadmin", "db_ddladmin", "db_backupoperator", "db_datareader", "db_datawriter", "db_denydatareader", "db_denydatawriter")
foreach ($schema in $db.Schemas) {
    if ($systemSchemas -notcontains $schema.Name) {
        [void]$sb.AppendLine("IF NOT EXISTS (SELECT 1 FROM sys.schemas WHERE name = N'$($schema.Name)')")
        [void]$sb.AppendLine("BEGIN")
        [void]$sb.AppendLine("    EXEC('CREATE SCHEMA [$($schema.Name)] AUTHORIZATION [dbo];');")
        [void]$sb.AppendLine("END")
        [void]$sb.AppendLine("GO")
    }
}
[void]$sb.AppendLine("")

# 2. Table Schemas (DDL)
[void]$sb.AppendLine("-- ====================================================================================================")
[void]$sb.AppendLine("-- 2. TABLE DEFINITIONS (DDL)")
[void]$sb.AppendLine("-- ====================================================================================================")

$scripter = New-Object Microsoft.SqlServer.Management.Smo.Scripter($server)
$scripter.Options.DriAll = $true
$scripter.Options.Indexes = $true
$scripter.Options.IncludeHeaders = $false
$scripter.Options.SchemaQualify = $true
$scripter.Options.AnsiPadding = $true
$scripter.Options.NoCollation = $true
$scripter.Options.IncludeIfNotExists = $true

$tableCount = 0
foreach ($table in $db.Tables) {
    if ($table.IsSystemObject) { continue }
    $tableCount++
    try {
        $scripts = $scripter.Script(@($table))
        foreach ($line in $scripts) {
            [void]$sb.AppendLine($line)
            [void]$sb.AppendLine("GO")
        }
    } catch {
        Write-Warning "Could not script table $($table.Schema).$($table.Name): $_"
    }
}
Write-Host "Scripted DDL for $tableCount tables." -ForegroundColor Green

# 3. Lookup Tables Data (lookup.*)
[void]$sb.AppendLine("-- ====================================================================================================")
[void]$sb.AppendLine("-- 3. LOOKUP REFERENCE DATA (lookup.*)")
[void]$sb.AppendLine("-- ====================================================================================================")

$dataScripter = New-Object Microsoft.SqlServer.Management.Smo.Scripter($server)
$dataScripter.Options.ScriptData = $true
$dataScripter.Options.ScriptSchema = $false
$dataScripter.Options.IncludeHeaders = $false
$dataScripter.Options.SchemaQualify = $true

$lookupCount = 0
$lookupRowsCount = 0
foreach ($table in $db.Tables) {
    if ($table.IsSystemObject) { continue }
    if ($table.Schema -eq "lookup" -or $table.Name -like "*Type") {
        $lookupCount++
        $rowCount = $table.RowCount
        $lookupRowsCount += $rowCount

        [void]$sb.AppendLine("-- Reference Data: [$($table.Schema)].[$($table.Name)] ($rowCount rows)")
        try {
            $dataScripts = $dataScripter.EnumScript(@($table))
            if ($dataScripts.Count -gt 0) {
                foreach ($stmt in $dataScripts) {
                    [void]$sb.AppendLine($stmt)
                }
                [void]$sb.AppendLine("GO")
            }
        } catch {
            Write-Warning "Could not script data for $($table.Schema).$($table.Name): $_"
        }
    }
}
Write-Host "Scripted reference data for $lookupCount lookup tables ($lookupRowsCount total rows)." -ForegroundColor Green

# Write to output file
[System.IO.File]::WriteAllText($targetFile, $sb.ToString(), [System.Text.Encoding]::UTF8)

$fileInfo = Get-Item $targetFile
$fileSizeMb = [math]::Round($fileInfo.Length / 1MB, 2)
Write-Host "========================================================" -ForegroundColor Green
Write-Host "Database and Lookups successfully scripted!" -ForegroundColor Green
Write-Host "File: $($fileInfo.FullName) ($fileSizeMb MB)" -ForegroundColor Green
Write-Host "Tables: $tableCount | Lookups with data: $lookupCount" -ForegroundColor Green
Write-Host "========================================================" -ForegroundColor Green
