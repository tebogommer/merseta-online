# PowerShell Script to install and configure merSETA NSDMS Git Hooks
Write-Host "========================================================" -ForegroundColor Cyan
Write-Host "🛡️ Configuring merSETA NSDMS Git Hooks Path..." -ForegroundColor Cyan
Write-Host "========================================================" -ForegroundColor Cyan

try {
    git config core.hooksPath .githooks
    Write-Host "✅ Git hooks successfully configured to use '.githooks' directory!" -ForegroundColor Green
    Write-Host "Pre-commit and Pre-push quality gates are now active." -ForegroundColor Green
}
catch {
    Write-Host "❌ Failed to configure git hooks: $_" -ForegroundColor Red
}
