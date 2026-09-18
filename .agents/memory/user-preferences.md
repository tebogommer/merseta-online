---
type: user
created: 2026-07-18
updated: 2026-09-13
---

# User Preferences

## Testing & Quality Gate
- **Playwright Visual & Console Quality Gate**: When testing with Playwright, always include visual, console, and interactivity assertions in the test harness (`playwright_assertions.py`) to guarantee that only true, visually styled, fully interactive pages pass the test suite. Never accept tests that merely verify HTTP 200 without DOM layout styling and interactive readiness.

