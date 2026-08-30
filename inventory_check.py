import os
from pathlib import Path

legacy_entity_dir = Path("doc/haj/com/entity")
dotnet_entity_dir = Path("dotnet/Nsdms.Domain/Entities")
dotnet_lookup_dir = Path("dotnet/Nsdms.Domain/Lookups")

# 1. Collect all legacy entities
legacy_entities = []
if legacy_entity_dir.exists():
    for f in legacy_entity_dir.glob("*.html"):
        name = f.stem
        if not name.startswith("package-") and not name.startswith("class-use"):
            legacy_entities.append(name)

legacy_lookups = []
lookup_dir = legacy_entity_dir / "lookup"
if lookup_dir.exists():
    for f in lookup_dir.glob("*.html"):
        name = f.stem
        if not name.startswith("package-") and not name.startswith("class-use"):
            legacy_lookups.append(name)

# 2. Collect all ported .NET entities & lookups
ported_entities = []
if dotnet_entity_dir.exists():
    for f in dotnet_entity_dir.glob("*.cs"):
        with open(f, "r", encoding="utf-8") as file:
            for line in file:
                line = line.strip()
                if line.startswith("public class ") or line.startswith("public record "):
                    parts = line.split()
                    if len(parts) >= 3:
                        class_name = parts[2].split(":")[0].split("<")[0].strip()
                        ported_entities.append(class_name)

ported_lookups = []
if dotnet_lookup_dir.exists():
    for f in dotnet_lookup_dir.glob("*.cs"):
        with open(f, "r", encoding="utf-8") as file:
            for line in file:
                line = line.strip()
                if line.startswith("public class ") or line.startswith("public record "):
                    parts = line.split()
                    if len(parts) >= 3:
                        class_name = parts[2].split(":")[0].split("<")[0].strip()
                        ported_lookups.append(class_name)

print(f"Total Legacy Entities Found: {len(legacy_entities)}")
print(f"Total Legacy Lookups Found: {len(legacy_lookups)}")
print(f"Total Ported .NET Domain Classes: {len(ported_entities)}")
print(f"Total Ported .NET Lookup Classes: {len(ported_lookups)}")

print("\n--- SAMPLE LEGACY ENTITIES ---")
print(sorted(legacy_entities)[:30])

print("\n--- SAMPLE PORTED DOMAIN CLASSES ---")
print(sorted(ported_entities))
