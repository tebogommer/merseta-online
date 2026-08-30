#!/usr/bin/env bash
# Shell script to configure Git Hooks path for Linux/macOS
echo "🛡️ Configuring merSETA NSDMS Git Hooks Path..."
git config core.hooksPath .githooks
chmod +x .githooks/pre-commit .githooks/pre-push 2>/dev/null || true
echo "✅ Git hooks successfully configured to use '.githooks' directory!"
