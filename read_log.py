with open(r"C:\Users\tmoepi\.gemini\antigravity\brain\dbadc2b6-1522-4b84-87ef-d152e4745cdb\.system_generated\tasks\task-1159.log", "r", encoding="utf-8", errors="ignore") as f:
    lines = f.readlines()
    for l in lines[-60:]:
        print(l, end="")
