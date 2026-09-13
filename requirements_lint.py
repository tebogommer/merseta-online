#!/usr/bin/env python3
"""
requirements_lint.py v1.1 — validator for the Requirements Management System

Turns the aspirational rules in AGENTS.md ("the agent REFUSES to mark done
without an AC") into mechanically enforced ones.

Layers:
    loader  -> normalised records -> checks -> findings -> reporter

Checks never see markdown. Swap the loader for a YAML one later and every
check keeps working.

Usage:
    python requirements_lint.py                 # lint ./REQUIREMENTS.md
    python requirements_lint.py --strict        # warnings also fail
    python requirements_lint.py --format json   # machine-readable
    python requirements_lint.py --explain AC001 # what does this rule mean
"""

from __future__ import annotations

import argparse
import json
import re
import subprocess
import sys
from dataclasses import dataclass, field
from pathlib import Path
from typing import Callable, Iterable

# --------------------------------------------------------------------------
# Findings
# --------------------------------------------------------------------------

SEVERITY_RANK = {"error": 0, "warning": 1, "info": 2}


@dataclass
class Finding:
    severity: str          # error | warning | info
    code: str              # stable rule id, e.g. "AC001"
    message: str
    line: int | None = None
    req_id: str | None = None
    file: str = "REQUIREMENTS.md"

    def as_dict(self) -> dict:
        return {
            "severity": self.severity,
            "code": self.code,
            "message": self.message,
            "file": self.file,
            "line": self.line,
            "req_id": self.req_id,
        }


RULE_DOCS: dict[str, str] = {
    "TBL001": "A table row has a different number of cells than its header.",
    "ID001": "Requirement ID does not match the configured pattern (FR-001).",
    "ID002": "Duplicate requirement ID.",
    "ST001": "Status symbol is not one of the values in the Status Legend.",
    "AC001": "FR is marked [x] Done but has no acceptance criteria (KI-007).",
    "AC002": "FR is marked [x] Done but not all of its ACs are verified.",
    "AC003": "Acceptance criterion references a requirement ID that does not exist.",
    "AC004": "Acceptance criterion is not in GIVEN / WHEN / THEN form.",
    "AC005": "FR has no acceptance criteria yet (fine while pending, not at done).",
    "DEP001": "Depends On references a requirement ID that does not exist.",
    "DEP002": "Dependency cycle detected.",
    "DEP003": "FR is [x] Done but a requirement it depends on is not done (KI-013).",
    "RSK001": "Unresolved [?!] high-risk inferred item present; these block builds.",
    "TRC001": "Traceability row references a requirement ID that does not exist.",
    "TRC002": "Traceability references a file path that does not exist on disk.",
    "TRC003": "Traceability references a commit SHA not found in this repository.",
    "TRC004": "FR is [x] Done with no traceability entry (orphan).",
    "CMP001": "Compliance tag is not defined in the Compliance Matrix.",
    "CMP002": "Compliance-tagged FR has no corresponding row in COMPLIANCE.md.",
    "NFR001": "NFR has no measurable threshold; it can never be verified.",
    "NFR002": "NFR has a threshold but no stated measurement method.",
    "OWN001": "Requirement has no named owner.",
    "VER001": "Schema Version header missing or unparseable.",
}


# --------------------------------------------------------------------------
# Markdown loader
# --------------------------------------------------------------------------


def norm(text: str) -> str:
    """Normalise a column header for tolerant matching."""
    return re.sub(r"[^a-z0-9]", "", text.lower())


@dataclass
class Row:
    cells: dict[str, str]
    line: int

    def get(self, *names: str, default: str = "") -> str:
        for n in names:
            if n in self.cells:
                return self.cells[n]
        return default


@dataclass
class Table:
    kind: str
    headers: list[str]
    rows: list[Row]
    heading: str
    line: int


PLACEHOLDER = re.compile(r"^\{.*\}$")


def split_row(raw: str) -> list[str]:
    """Split a markdown table row, honouring escaped pipes."""
    body = raw.strip()
    body = body[1:] if body.startswith("|") else body
    body = body[:-1] if body.endswith("|") else body
    parts, buf, escaped = [], "", False
    for ch in body:
        if escaped:
            buf += ch
            escaped = False
        elif ch == "\\":
            escaped = True
        elif ch == "|":
            parts.append(buf.strip())
            buf = ""
        else:
            buf += ch
    parts.append(buf.strip())
    return parts


SEPARATOR = re.compile(r"^\|?[\s:\-|]+\|[\s:\-|]*$")


def classify(headers: list[str], first_id: str) -> str:
    h = {norm(x) for x in headers}
    if first_id.startswith("FR-") and "reqid" not in h:
        return "fr"
    if first_id.startswith("NFR-"):
        return "nfr"
    if first_id.startswith("BR-"):
        return "br"
    if first_id.startswith("DEC-"):
        return "dec"
    if "reqid" in h and "verified" in h and ("criterion" in h or "criteriongivenwhenthen" in h):
        return "ac"
    if "reqid" in h and ("codefiles" in h or "commits" in h):
        return "trace"
    if "tag" in h and "meaning" in h:
        return "compliance_matrix"
    if "risk" in h and "resolution" in h:
        return "openq"
    if "version" in h and "trigger" in h:
        return "changelog"
    if "permission" in h or "tier1" in h:
        return "rolematrix"
    if "id" in h and "category" in h:
        return "nfr"
    if "id" in h and ("dependson" in h or "priority" in h):
        return "fr"
    return "unknown"


def parse_tables(text: str, findings: list[Finding], filename: str) -> list[Table]:
    lines = text.splitlines()
    tables: list[Table] = []
    heading = ""
    i = 0
    while i < len(lines):
        line = lines[i]
        if line.startswith("#"):
            heading = line.lstrip("#").strip()
        if line.lstrip().startswith("|") and i + 1 < len(lines) and SEPARATOR.match(lines[i + 1]):
            headers = split_row(line)
            start_line = i + 1
            i += 2
            rows: list[Row] = []
            while i < len(lines) and lines[i].lstrip().startswith("|"):
                cells = split_row(lines[i])
                if len(cells) != len(headers):
                    findings.append(
                        Finding(
                            "error",
                            "TBL001",
                            f"row has {len(cells)} cells, header has {len(headers)}",
                            line=i + 1,
                            file=filename,
                        )
                    )
                mapping = {norm(h): (cells[j] if j < len(cells) else "") for j, h in enumerate(headers)}
                if any(v for v in mapping.values()):
                    rows.append(Row(mapping, i + 1))
                i += 1
            first_id = next((r.get("id", "reqid") for r in rows if r.get("id", "reqid")), "")
            tables.append(Table(classify(headers, first_id), headers, rows, heading, start_line))
            continue
        i += 1
    return tables


# --------------------------------------------------------------------------
# Normalised records
# --------------------------------------------------------------------------

STATUS_RE = re.compile(r"\[[^\]]*\]")
TAG_RE = re.compile(r"\b[A-Z]{4,}\b")
ID_LIST_RE = re.compile(r"\b(?:FR|NFR|BR|DEC)-\d+\b")
SHA_RE = re.compile(r"\b[0-9a-f]{7,40}\b")


@dataclass
class Requirement:
    id: str
    description: str
    status: str
    line: int
    kind: str = "FR"
    priority: str = ""
    source: str = ""
    confidence: str = ""
    depends_on: list[str] = field(default_factory=list)
    compliance: list[str] = field(default_factory=list)
    owner: str = ""
    category: str = ""
    threshold: str = ""
    measurement: str = ""


@dataclass
class Criterion:
    req_id: str
    text: str
    verified: bool
    line: int


@dataclass
class TraceEntry:
    req_id: str
    code_files: list[str]
    test_files: list[str]
    commits: list[str]
    line: int


@dataclass
class Register:
    requirements: dict[str, Requirement] = field(default_factory=dict)
    criteria: list[Criterion] = field(default_factory=list)
    traces: dict[str, TraceEntry] = field(default_factory=dict)
    compliance_tags: set[str] = field(default_factory=set)
    open_questions: list[Row] = field(default_factory=list)
    schema_version: str = ""
    compliance_file_ids: set[str] = field(default_factory=set)


def split_list(cell: str) -> list[str]:
    if not cell or PLACEHOLDER.match(cell):
        return []
    return [p.strip().strip("`") for p in re.split(r"[,\n]| and ", cell) if p.strip()]


def status_of(cell: str) -> str:
    m = STATUS_RE.search(cell or "")
    return m.group(0) if m else ""


def load_markdown(root: Path, findings: list[Finding]) -> Register:
    reg = Register()
    req_path = root / "REQUIREMENTS.md"
    if not req_path.exists():
        findings.append(Finding("error", "VER001", "REQUIREMENTS.md not found", file=str(req_path)))
        return reg

    text = req_path.read_text(encoding="utf-8")

    m = re.search(r"Schema Version[:\s*]*([0-9]+\.[0-9]+)", text) or re.search(
        r"Register Version[:\s*]*([0-9]+\.[0-9]+)", text
    )
    if m:
        reg.schema_version = m.group(1)
    else:
        findings.append(Finding("warning", "VER001", "no Schema Version header found", line=1))

    for table in parse_tables(text, findings, "REQUIREMENTS.md"):
        if table.kind in ("fr", "nfr", "br", "dec"):
            for row in table.rows:
                rid = row.get("id").strip("` ")
                if not rid or PLACEHOLDER.match(rid):
                    continue
                if rid in reg.requirements:
                    findings.append(
                        Finding("error", "ID002", f"duplicate requirement ID {rid}", row.line, rid)
                    )
                    continue
                reg.requirements[rid] = Requirement(
                    id=rid,
                    description=row.get("description", "rule", "decision"),
                    status=status_of(row.get("status", default="[ ]")),
                    line=row.line,
                    kind=table.kind.upper(),
                    priority=row.get("priority"),
                    source=row.get("source"),
                    confidence=row.get("confidence"),
                    depends_on=ID_LIST_RE.findall(row.get("dependson")),
                    compliance=TAG_RE.findall(row.get("compliance")),
                    owner=row.get("owner"),
                    category=row.get("category"),
                    threshold=row.get("threshold"),
                    measurement=row.get("measurementmethod", "measurement", "method"),
                )
        elif table.kind == "ac":
            for row in table.rows:
                rid = row.get("reqid").strip("` ")
                if not rid or PLACEHOLDER.match(rid):
                    continue
                crit = row.get("criterion", "criteriongivenwhenthen")
                if not crit:
                    crit = next((v for k, v in row.cells.items() if k not in ("reqid", "source", "verified")), "")
                reg.criteria.append(
                    Criterion(rid, crit, "x" in status_of(row.get("verified")).lower(), row.line)
                )
        elif table.kind == "trace":
            for row in table.rows:
                rid = row.get("reqid").strip("` ")
                if not rid or PLACEHOLDER.match(rid):
                    continue
                reg.traces[rid] = TraceEntry(
                    rid,
                    split_list(row.get("codefiles")),
                    split_list(row.get("testfiles")),
                    [s for c in split_list(row.get("commits")) for s in SHA_RE.findall(c)],
                    row.line,
                )
        elif table.kind == "compliance_matrix":
            for row in table.rows:
                reg.compliance_tags.update(TAG_RE.findall(row.get("tag")))
        elif table.kind == "openq":
            reg.open_questions.extend(table.rows)

    comp_path = root / "COMPLIANCE.md"
    if comp_path.exists():
        reg.compliance_file_ids.update(
            ID_LIST_RE.findall(comp_path.read_text(encoding="utf-8"))
        )
    return reg


# --------------------------------------------------------------------------
# Git helpers
# --------------------------------------------------------------------------


class Git:
    def __init__(self, root: Path):
        self.root = root
        self.available = (root / ".git").exists()
        self._cache: dict[str, bool] = {}

    def commit_exists(self, sha: str) -> bool:
        if not self.available:
            return True  # cannot verify; do not cry wolf
        if sha not in self._cache:
            r = subprocess.run(
                ["git", "cat-file", "-e", f"{sha}^{{commit}}"],
                cwd=self.root,
                capture_output=True,
            )
            self._cache[sha] = r.returncode == 0
        return self._cache[sha]


# --------------------------------------------------------------------------
# Checks
# --------------------------------------------------------------------------

CheckFn = Callable[[Register, Path, Git, dict], Iterable[Finding]]
CHECKS: list[CheckFn] = []


def check(fn: CheckFn) -> CheckFn:
    CHECKS.append(fn)
    return fn


VALID_STATUSES = {"[ ]", "[~]", "[x]", "[!]", "[?!]", "[?]", "[?~]", "[d]"}


@check
def check_ids(reg, root, git, cfg):
    pattern = re.compile(cfg["id_pattern"])
    for r in reg.requirements.values():
        if not pattern.match(r.id):
            yield Finding("error", "ID001", f"malformed requirement ID {r.id!r}", r.line, r.id)


@check
def check_statuses(reg, root, git, cfg):
    for r in reg.requirements.values():
        if r.kind in ("BR", "DEC"):
            continue
        if r.status and r.status not in VALID_STATUSES:
            yield Finding("error", "ST001", f"unknown status {r.status!r}", r.line, r.id)


@check
def check_acceptance_criteria(reg, root, git, cfg):
    by_req: dict[str, list[Criterion]] = {}
    for c in reg.criteria:
        by_req.setdefault(c.req_id, []).append(c)
        if c.req_id not in reg.requirements:
            yield Finding("error", "AC003", f"AC references unknown requirement {c.req_id}", c.line, c.req_id)
        elif not re.search(r"given\b.*\bwhen\b.*\bthen\b", c.text, re.I | re.S):
            yield Finding("warning", "AC004", f"AC for {c.req_id} is not GIVEN/WHEN/THEN", c.line, c.req_id)

    for r in reg.requirements.values():
        if r.kind != "FR":
            continue
        acs = by_req.get(r.id, [])
        if r.status == "[x]":
            if not acs:
                yield Finding("error", "AC001", f"{r.id} is Done with no acceptance criteria", r.line, r.id)
            elif not all(a.verified for a in acs):
                unver = sum(1 for a in acs if not a.verified)
                yield Finding("error", "AC002", f"{r.id} is Done with {unver} unverified AC(s)", r.line, r.id)
        elif not acs and r.status not in ("[d]",):
            yield Finding("warning", "AC005", f"{r.id} has no acceptance criteria yet", r.line, r.id)


@check
def check_dependencies(reg, root, git, cfg):
    for r in reg.requirements.values():
        for dep in r.depends_on:
            if dep not in reg.requirements:
                yield Finding("error", "DEP001", f"{r.id} depends on unknown {dep}", r.line, r.id)
            elif r.status == "[x]" and reg.requirements[dep].status != "[x]":
                yield Finding(
                    "error", "DEP003",
                    f"{r.id} is Done but dependency {dep} is {reg.requirements[dep].status}",
                    r.line, r.id,
                )

    WHITE, GREY, BLACK = 0, 1, 2
    colour = {rid: WHITE for rid in reg.requirements}

    def walk(rid: str, path: list[str]):
        colour[rid] = GREY
        for dep in reg.requirements[rid].depends_on:
            if dep not in colour:
                continue
            if colour[dep] == GREY:
                cycle = " -> ".join(path[path.index(dep):] + [dep]) if dep in path else f"{rid} -> {dep}"
                yield Finding("error", "DEP002", f"dependency cycle: {cycle}", reg.requirements[rid].line, rid)
            elif colour[dep] == WHITE:
                yield from walk(dep, path + [dep])
        colour[rid] = BLACK

    seen_cycles = set()
    for rid in list(colour):
        if colour[rid] == WHITE:
            for f in walk(rid, [rid]):
                if f.message not in seen_cycles:
                    seen_cycles.add(f.message)
                    yield f


@check
def check_high_risk(reg, root, git, cfg):
    for r in reg.requirements.values():
        if r.status == "[?!]":
            yield Finding("error", "RSK001", f"{r.id} is an unresolved HIGH RISK inference", r.line, r.id)
    for row in reg.open_questions:
        if "[?!]" in row.get("risk") and "pending" in row.get("resolution").lower():
            yield Finding("error", "RSK001", "unresolved HIGH RISK open question", row.line)


@check
def check_traceability(reg, root, git, cfg):
    for t in reg.traces.values():
        if t.req_id not in reg.requirements:
            yield Finding("error", "TRC001", f"traceability references unknown {t.req_id}", t.line, t.req_id)
        for p in t.code_files + t.test_files:
            if PLACEHOLDER.match(p):
                continue
            if not (root / p).exists():
                yield Finding("error", "TRC002", f"{t.req_id} references missing file {p!r}", t.line, t.req_id)
        for sha in t.commits:
            if not git.commit_exists(sha):
                yield Finding("error", "TRC003", f"{t.req_id} references unknown commit {sha}", t.line, t.req_id)

    for r in reg.requirements.values():
        if r.kind == "FR" and r.status == "[x]":
            t = reg.traces.get(r.id)
            if not t or not (t.code_files or t.commits):
                yield Finding("warning", "TRC004", f"{r.id} is Done with no traceability", r.line, r.id)


@check
def check_compliance(reg, root, git, cfg):
    for r in reg.requirements.values():
        for tag in r.compliance:
            if reg.compliance_tags and tag not in reg.compliance_tags:
                yield Finding("error", "CMP001", f"{r.id} uses undefined compliance tag {tag}", r.line, r.id)
            elif r.id not in reg.compliance_file_ids:
                yield Finding(
                    "error", "CMP002",
                    f"{r.id} is tagged {tag} but absent from COMPLIANCE.md", r.line, r.id,
                )


@check
def check_nfr_measurable(reg, root, git, cfg):
    """An NFR needs a number and a way to measure it, or it can only be asserted."""
    for r in reg.requirements.values():
        if r.kind != "NFR":
            continue
        # v3.0 puts the threshold in its own column; v2.x buried it in the description.
        has_number = bool(re.search(r"\d", r.threshold or r.description))
        if not has_number:
            sev = "error" if r.status == "[x]" else "warning"
            yield Finding(sev, "NFR001", f"{r.id} has no measurable threshold", r.line, r.id)
        elif r.threshold and not r.measurement:
            sev = "error" if r.status == "[x]" else "warning"
            yield Finding(sev, "NFR002", f"{r.id} has a threshold but no measurement method", r.line, r.id)


@check
def check_owner(reg, root, git, cfg):
    if not cfg.get("require_owner"):
        return
    for r in reg.requirements.values():
        if r.kind == "FR" and not r.owner:
            yield Finding("warning", "OWN001", f"{r.id} has no named owner", r.line, r.id)


# --------------------------------------------------------------------------
# Config, reporting, entry point
# --------------------------------------------------------------------------

DEFAULT_CONFIG = {
    "id_pattern": r"^(FR|NFR|BR|DEC)-\d{3,}$",
    "require_owner": False,
    "severity_overrides": {},   # {"AC005": "error"} or {"NFR001": "off"}
    "ignore": [],               # [{"code": "TRC002", "req_id": "FR-014", "reason": "..."}]
}


def load_config(root: Path) -> dict:
    cfg = dict(DEFAULT_CONFIG)
    for name in (".requirements-lint.json", ".requirements-lint.yaml"):
        p = root / name
        if not p.exists():
            continue
        raw = p.read_text(encoding="utf-8")
        if name.endswith(".json"):
            cfg.update(json.loads(raw))
        else:
            try:
                import yaml  # type: ignore
                cfg.update(yaml.safe_load(raw) or {})
            except ImportError:
                print("warning: PyYAML not installed; ignoring YAML config", file=sys.stderr)
        break
    return cfg


def apply_policy(findings: list[Finding], cfg: dict) -> list[Finding]:
    out = []
    for f in findings:
        sev = cfg["severity_overrides"].get(f.code, f.severity)
        if sev == "off":
            continue
        if any(
            ig.get("code") == f.code and (ig.get("req_id") in (None, f.req_id))
            for ig in cfg["ignore"]
        ):
            continue
        f.severity = sev
        out.append(f)
    return out


def report_human(findings: list[Finding], use_colour: bool) -> None:
    colours = {"error": "\033[31m", "warning": "\033[33m", "info": "\033[36m"}
    reset = "\033[0m"
    for f in sorted(findings, key=lambda x: (SEVERITY_RANK[x.severity], x.line or 0)):
        c, r = (colours[f.severity], reset) if use_colour else ("", "")
        loc = f"{f.file}:{f.line}" if f.line else f.file
        print(f"{loc}: {c}{f.severity}{r} [{f.code}] {f.message}")
    errors = sum(1 for f in findings if f.severity == "error")
    warnings = sum(1 for f in findings if f.severity == "warning")
    print(f"\n{errors} error(s), {warnings} warning(s)")


def main(argv: list[str] | None = None) -> int:
    ap = argparse.ArgumentParser(description="Validate the requirements register.")
    ap.add_argument("--root", default=".", type=Path)
    ap.add_argument("--strict", action="store_true", help="treat warnings as failures")
    ap.add_argument("--format", choices=["human", "json"], default="human")
    ap.add_argument("--no-colour", action="store_true")
    ap.add_argument("--explain", metavar="CODE", help="describe a rule and exit")
    args = ap.parse_args(argv)

    if args.explain:
        print(RULE_DOCS.get(args.explain.upper(), "unknown rule code"))
        return 0

    root = args.root.resolve()
    cfg = load_config(root)
    findings: list[Finding] = []
    reg = load_markdown(root, findings)
    git = Git(root)

    for fn in CHECKS:
        findings.extend(fn(reg, root, git, cfg))

    findings = apply_policy(findings, cfg)

    if args.format == "json":
        print(json.dumps([f.as_dict() for f in findings], indent=2))
    else:
        report_human(findings, use_colour=not args.no_colour and sys.stdout.isatty())

    if any(f.severity == "error" for f in findings):
        return 1
    if args.strict and any(f.severity == "warning" for f in findings):
        return 1
    return 0


if __name__ == "__main__":
    try:
        sys.exit(main())
    except BrokenPipeError:
        sys.stderr.close()
        sys.exit(0)
