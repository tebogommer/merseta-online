import json
import sys

filename = sys.argv[1] if len(sys.argv) > 1 else "lighthouse-desktop-report.json"

with open(filename, "r", encoding="utf-8") as f:
    data = json.load(f)

categories = data.get("categories", {})
print(f"=== LIGHTHOUSE SCORES ({filename}) ===")
for k, v in categories.items():
    title = v.get("title", k)
    score = round(v.get("score", 0) * 100)
    print(f"  {title}: {score}/100")

audits = data.get("audits", {})
for audit_id, audit in audits.items():
    score = audit.get("score")
    if score is not None and score < 1:
        title = audit.get("title", audit_id)
        display = audit.get("displayValue", "")
        print(f"[{score*100:.0f}/100] {title} ({audit_id})")
        if display:
            print(f"   Value: {display}")
