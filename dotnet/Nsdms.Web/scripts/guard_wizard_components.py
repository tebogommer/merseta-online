#!/usr/bin/env python3
"""
NSDMS0001 Build-Time Guard
Scans .razor files to enforce that MudStepper and MudStep components
are never used outside Components/Shared/Wizard/.
"""

import os
import sys
import re

GUARDED_TAGS = [re.compile(r'<\s*MudStepper\b', re.IGNORECASE), re.compile(r'<\s*MudStep\b', re.IGNORECASE)]
ALLOWED_DIR_FRAGMENT = os.path.normpath("Components/Shared/Wizard").lower()

def check_file(filepath, base_dir, exceptions=None):
    norm_path = os.path.normpath(filepath).lower()
    if ALLOWED_DIR_FRAGMENT in norm_path:
        return []
    
    filename = os.path.basename(filepath)
    if exceptions and filename in exceptions:
        return []

    errors = []
    try:
        with open(filepath, 'r', encoding='utf-8', errors='ignore') as f:
            for line_idx, line in enumerate(f, start=1):
                for tag_re in GUARDED_TAGS:
                    m = tag_re.search(line)
                    if m:
                        col = m.start() + 1
                        errors.append((filepath, line_idx, col, f"Direct usage of {m.group(0).strip('< ')} outside of Components/Shared/Wizard is prohibited. Use WizardShell instead."))
    except Exception as ex:
        print(f"Warning: Could not scan {filepath}: {ex}", file=sys.stderr)
    return errors

def main():
    web_dir = sys.argv[1] if len(sys.argv) > 1 else os.getcwd()
    components_dir = os.path.join(web_dir, "Components")
    if not os.path.exists(components_dir):
        print(f"Error: Components directory not found at {components_dir}", file=sys.stderr)
        sys.exit(1)

    # Exceptions must be documented in DESIGN.md
    exceptions = set()
    design_md_path = os.path.normpath(os.path.join(web_dir, "..", "..", "DESIGN.md"))
    if not os.path.exists(design_md_path):
        design_md_path = os.path.normpath(os.path.join(web_dir, "..", "DESIGN.md"))

    # Check for documented exceptions in DESIGN.md
    if os.path.exists(design_md_path):
        with open(design_md_path, 'r', encoding='utf-8', errors='ignore') as f:
            content = f.read()
            # Match approved exceptions listed under Wizard Exceptions
            m = re.search(r'### Wizard Exceptions\s*\n(.*?)(?=\n###|\n##|\Z)', content, re.DOTALL)
            if m:
                for line in m.group(1).splitlines():
                    line = line.strip()
                    if line.startswith("-") or line.startswith("*"):
                        fn = line.lstrip("-* ").split()[0].strip('`"\'')
                        if fn.endswith(".razor"):
                            exceptions.add(fn)

    all_errors = []
    for root, _, files in os.walk(components_dir):
        for f in files:
            if f.endswith(".razor"):
                fp = os.path.join(root, f)
                all_errors.extend(check_file(fp, web_dir, exceptions))

    if all_errors:
        for err_file, line, col, msg in all_errors:
            print(f"{err_file}({line},{col}): error NSDMS0001: {msg}")
        sys.exit(1)

    sys.exit(0)

if __name__ == '__main__':
    main()
