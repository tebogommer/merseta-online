using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.Json;
using System.Text.RegularExpressions;
using Microsoft.CodeAnalysis;
using Microsoft.CodeAnalysis.CSharp;
using Microsoft.CodeAnalysis.CSharp.Syntax;

namespace RoslynAstAuditor;

public class Program
{
    public static int Main(string[] args)
    {
        Console.WriteLine("================================================================================");
        Console.WriteLine("       NSDMS ENTERPRISE STRUCTURAL DEPENDENCY AUDIT (ROSLYN AST ENGINE)         ");
        Console.WriteLine("================================================================================");
        Console.WriteLine($"Runtime: .NET {Environment.Version} | Process: x64 | Engine: Roslyn CSharp AST");
        Console.WriteLine($"Audit Timestamp: {DateTime.UtcNow:yyyy-MM-dd HH:mm:ss} UTC\n");

        string rootDir = args.Length > 0 ? args[0] : "";
        if (string.IsNullOrWhiteSpace(rootDir) || !Directory.Exists(Path.Combine(rootDir, "Nsdms.Domain")))
        {
            var dir = new DirectoryInfo(AppContext.BaseDirectory);
            while (dir != null && !Directory.Exists(Path.Combine(dir.FullName, "Nsdms.Domain")))
            {
                dir = dir.Parent;
            }
            rootDir = dir?.FullName ?? Path.GetFullPath("dotnet");
        }
        Console.WriteLine($"Target Solution Root: {rootDir}\n");

        var projects = new List<ProjectAuditScope>
        {
            new("Nsdms.Domain", Path.Combine(rootDir, "Nsdms.Domain"), ProjectLayer.Domain),
            new("Nsdms.Application", Path.Combine(rootDir, "Nsdms.Application"), ProjectLayer.Application),
            new("Nsdms.Infrastructure", Path.Combine(rootDir, "Nsdms.Infrastructure"), ProjectLayer.Infrastructure),
            new("Nsdms.Web", Path.Combine(rootDir, "Nsdms.Web"), ProjectLayer.Web),
            new("Nsdms.Tests", Path.Combine(rootDir, "Nsdms.Tests"), ProjectLayer.Tests),
            new("Nsdms.LoadTester", Path.Combine(rootDir, "Nsdms.LoadTester"), ProjectLayer.Tools)
        };

        var auditResults = new SolutionAuditResult();

        // 1. Parse and collect AST data across all projects
        int totalFilesScanned = 0;
        int totalNodesScanned = 0;

        foreach (var proj in projects)
        {
            if (!Directory.Exists(proj.Path))
            {
                Console.WriteLine($"[WARN] Project directory not found: {proj.Path}");
                continue;
            }

            Console.WriteLine($"Analyzing {proj.Name} ({proj.Layer})...");
            var csFiles = Directory.GetFiles(proj.Path, "*.cs", SearchOption.AllDirectories)
                .Where(f => !f.Contains("\\obj\\") && !f.Contains("\\bin\\"))
                .ToList();

            var razorFiles = Directory.GetFiles(proj.Path, "*.razor", SearchOption.AllDirectories)
                .Where(f => !f.Contains("\\obj\\") && !f.Contains("\\bin\\"))
                .ToList();

            proj.TotalCsFiles = csFiles.Count;
            proj.TotalRazorFiles = razorFiles.Count;
            totalFilesScanned += csFiles.Count + razorFiles.Count;

            foreach (var file in csFiles)
            {
                var fileInfo = ParseCsFile(file, proj);
                proj.ParsedFiles.Add(fileInfo);
                totalNodesScanned += fileInfo.SyntaxNodeCount;
            }

            // Razor files AST & directive scanning
            foreach (var file in razorFiles)
            {
                var razorInfo = ParseRazorFile(file, proj);
                proj.ParsedRazorFiles.Add(razorInfo);
            }

            Console.WriteLine($"  -> Scanned {csFiles.Count} C# files, {razorFiles.Count} Razor components.");
        }

        Console.WriteLine($"\nTotal Source Files Parsed: {totalFilesScanned} ({totalNodesScanned:N0} AST syntax nodes analyzed)");

        // 2. Perform Structural Layering Audits
        Console.WriteLine("\n--- EXECUTING STRUCTURAL LAYER & CLEAN ARCHITECTURE RULE CHECKS ---");
        ExecuteArchitectureLayeringChecks(projects, auditResults);

        // 3. UI Zero DbContext Injection Audit (Razor components)
        Console.WriteLine("--- EXECUTING ZERO RAW DBCONTEXT IN UI AUDIT (AGENTS.MD INVARIANT) ---");
        ExecuteRazorDbContextInjectionCheck(projects, auditResults);

        // 4. Calculate Coupling, Instability, Abstractness & Distance from Main Sequence
        Console.WriteLine("--- CALCULATING ROBERT C. MARTIN PACKAGE METRICS (Ca, Ce, I, A, D) ---");
        CalculatePackageMetrics(projects, auditResults);

        // 5. Namespace Circular Dependency Detection (Tarjan / Cycle DFS)
        Console.WriteLine("--- EXECUTING NAMESPACE GRAPH CYCLE & CIRCULAR DEPENDENCY SCAN ---");
        DetectCircularDependencies(projects, auditResults);

        // 6. Print Report Summary
        PrintAuditReport(auditResults);

        // 7. Save JSON Artifact
        string outputPath = Path.Combine(rootDir, "..", "structural-dependency-audit.json");
        try
        {
            string json = JsonSerializer.Serialize(auditResults, new JsonSerializerOptions { WriteIndented = true });
            File.WriteAllText(outputPath, json);
            Console.WriteLine($"\n[INFO] Full audit JSON artifact written to: {Path.GetFullPath(outputPath)}");
        }
        catch (Exception ex)
        {
            Console.WriteLine($"[WARN] Could not write JSON output: {ex.Message}");
        }

        return auditResults.TotalViolations == 0 ? 0 : 1;
    }

    private static ParsedFileInfo ParseCsFile(string filePath, ProjectAuditScope proj)
    {
        string code = File.ReadAllText(filePath);
        var tree = CSharpSyntaxTree.ParseText(code, new CSharpParseOptions(LanguageVersion.CSharp13));
        var root = tree.GetCompilationUnitRoot();

        var info = new ParsedFileInfo
        {
            FilePath = filePath,
            RelativePath = Path.GetRelativePath(proj.Path, filePath),
            SyntaxNodeCount = root.DescendantNodesAndTokens().Count()
        };

        // Extract using directives
        foreach (var u in root.Usings)
        {
            info.Usings.Add(u.Name?.ToString() ?? "");
        }

        // Extract namespace declarations
        var namespaces = root.DescendantNodes().OfType<BaseNamespaceDeclarationSyntax>();
        foreach (var ns in namespaces)
        {
            info.DeclaredNamespaces.Add(ns.Name.ToString());
        }

        // Extract type declarations
        var typeDecls = root.DescendantNodes().OfType<TypeDeclarationSyntax>();
        foreach (var t in typeDecls)
        {
            bool isAbstract = t.Modifiers.Any(m => m.IsKind(SyntaxKind.AbstractKeyword));
            bool isInterface = t is InterfaceDeclarationSyntax;

            var typeInfo = new DeclaredTypeInfo
            {
                Name = t.Identifier.Text,
                Kind = t.Kind().ToString(),
                IsAbstractOrInterface = isAbstract || isInterface
            };

            if (t.BaseList != null)
            {
                foreach (var baseType in t.BaseList.Types)
                {
                    typeInfo.BaseAndInterfaceNames.Add(baseType.Type.ToString());
                }
            }

            info.DeclaredTypes.Add(typeInfo);
        }

        // Extract identifier and qualified names for external type references
        var identifierNames = root.DescendantNodes().OfType<IdentifierNameSyntax>()
            .Select(i => i.Identifier.Text)
            .Where(name => name.Length > 2 && char.IsUpper(name[0]))
            .Distinct();

        foreach (var id in identifierNames)
        {
            info.ReferencedTypeIdentifiers.Add(id);
        }

        // Extract object creation expressions (`new Foo()`)
        var creations = root.DescendantNodes().OfType<ObjectCreationExpressionSyntax>();
        foreach (var c in creations)
        {
            info.InstantiatedTypes.Add(c.Type.ToString());
        }

        return info;
    }

    private static ParsedRazorInfo ParseRazorFile(string filePath, ProjectAuditScope proj)
    {
        string content = File.ReadAllText(filePath);
        var info = new ParsedRazorInfo
        {
            FilePath = filePath,
            RelativePath = Path.GetRelativePath(proj.Path, filePath)
        };

        // Scan @using directives
        var usingMatches = Regex.Matches(content, @"@using\s+([A-Za-z0-9_.]+)");
        foreach (Match m in usingMatches)
        {
            info.Usings.Add(m.Groups[1].Value);
        }

        // Scan @inject directives
        var injectMatches = Regex.Matches(content, @"@inject\s+([A-Za-z0-9_.<>]+)\s+([A-Za-z0-9_]+)");
        foreach (Match m in injectMatches)
        {
            info.Injections.Add(new InjectedService(m.Groups[1].Value, m.Groups[2].Value));
        }

        // Scan for direct DbContext or raw EF calls
        if (Regex.IsMatch(content, @"\b(NsdmsDbContext|DbContext|IDbContextFactory)\b"))
        {
            info.HasDbContextReference = true;
        }

        return info;
    }

    private static void ExecuteArchitectureLayeringChecks(List<ProjectAuditScope> projects, SolutionAuditResult result)
    {
        var domainProj = projects.FirstOrDefault(p => p.Layer == ProjectLayer.Domain);
        var appProj = projects.FirstOrDefault(p => p.Layer == ProjectLayer.Application);
        var infraProj = projects.FirstOrDefault(p => p.Layer == ProjectLayer.Infrastructure);

        // RULE 1: Domain Purity (Zero upward dependencies)
        CheckBannedNamespaces(domainProj,
            ["Nsdms.Application", "Nsdms.Infrastructure", "Nsdms.Web", "Microsoft.EntityFrameworkCore", "Microsoft.AspNetCore.Mvc"],
            "ARCH001", "CRITICAL", "Nsdms.Domain Purity (Zero upward dependencies)",
            "Domain must remain a pure POCO layer.", result);

        // RULE 2: Application Inversion (No Infrastructure/Web coupling)
        CheckBannedNamespaces(appProj,
            ["Nsdms.Infrastructure", "Nsdms.Web"],
            "ARCH002", "CRITICAL", "Nsdms.Application Boundary (No Infrastructure/Web coupling)",
            "Infrastructure details must not leak into Application.", result);

        // RULE 3: Infrastructure Boundary (No Web UI coupling)
        CheckBannedNamespaces(infraProj,
            ["Nsdms.Web"],
            "ARCH003", "HIGH", "Nsdms.Infrastructure Boundary (No Web UI coupling)",
            "Infrastructure layer must not reference presentation layer.", result);
    }

    private static void CheckBannedNamespaces(
        ProjectAuditScope? proj,
        string[] bannedNamespaces,
        string ruleId,
        string severity,
        string checkName,
        string reason,
        SolutionAuditResult result)
    {
        if (proj == null) return;
        Console.Write($"  [{ruleId}] Checking {checkName}... ");
        int violations = 0;

        foreach (var file in proj.ParsedFiles)
        {
            foreach (var u in file.Usings)
            {
                if (bannedNamespaces.Any(banned => u.StartsWith(banned, StringComparison.OrdinalIgnoreCase)))
                {
                    result.Violations.Add(new ArchitectureViolation(
                        ruleId, severity, proj.Name, file.RelativePath,
                        $"{proj.Name} imports forbidden namespace '{u}'. {reason}"
                    ));
                    violations++;
                }
            }
        }

        Console.WriteLine(violations == 0 ? "PASSED [CLEAN]" : $"FAILED ({violations} violations)");
    }

    private static void ExecuteRazorDbContextInjectionCheck(List<ProjectAuditScope> projects, SolutionAuditResult result)
    {
        var webProj = projects.FirstOrDefault(p => p.Layer == ProjectLayer.Web);
        if (webProj == null) return;

        Console.Write("  [RULE 4] Checking Razor components for direct DbContext injection... ");
        int dbContextViolations = 0;

        foreach (var razor in webProj.ParsedRazorFiles)
        {
            foreach (var inj in razor.Injections)
            {
                if (inj.TypeName.Contains("NsdmsDbContext") ||
                    inj.TypeName.Contains("DbContext") ||
                    inj.TypeName.Contains("IDbContextFactory"))
                {
                    result.Violations.Add(new ArchitectureViolation(
                        RuleId: "UI001",
                        Severity: "CRITICAL",
                        Project: webProj.Name,
                        File: razor.RelativePath,
                        Description: $"Razor component directly injects '{inj.TypeName}' as '{inj.PropertyName}'. Violates AGENTS.md invariant: 'Razor components MUST NEVER inject DbContext or IDbContextFactory. All data fetching and mutations must pass through application service interfaces.'"
                    ));
                    dbContextViolations++;
                }
            }
        }

        // Also check C# files in Web project (code-behinds, etc.)
        foreach (var file in webProj.ParsedFiles)
        {
            if (file.RelativePath.Contains("Components") || file.RelativePath.Contains("Pages"))
            {
                foreach (var id in file.ReferencedTypeIdentifiers)
                {
                    if (id == "NsdmsDbContext" || id == "INsdmsDbContextFactory")
                    {
                        // Check if it's an injection in constructor or property
                        result.Violations.Add(new ArchitectureViolation(
                            RuleId: "UI002",
                            Severity: "CRITICAL",
                            Project: webProj.Name,
                            File: file.RelativePath,
                            Description: $"UI component file references '{id}'. Data operations must pass through Application Service contracts."
                        ));
                        dbContextViolations++;
                    }
                }
            }
        }

        if (dbContextViolations == 0)
            Console.WriteLine("PASSED [CLEAN] (Zero DbContext injections in UI)");
        else
            Console.WriteLine($"FAILED ({dbContextViolations} violations detected)");
    }

    private static void CalculatePackageMetrics(List<ProjectAuditScope> projects, SolutionAuditResult result)
    {
        var projData = projects.Select(p => new
        {
            Project = p,
            AllTypes = p.ParsedFiles.SelectMany(f => f.DeclaredTypes).ToList(),
            AllUsings = p.ParsedFiles.SelectMany(f => f.Usings).ToHashSet(StringComparer.OrdinalIgnoreCase)
        }).ToList();

        foreach (var pd in projData)
        {
            int totalTypes = pd.AllTypes.Count;
            int abstractTypes = pd.AllTypes.Count(t => t.IsAbstractOrInterface);
            double abstractness = totalTypes > 0 ? (double)abstractTypes / totalTypes : 0.0;

            int efferentCoupling = pd.AllUsings.Count(u =>
                projData.Any(other => other.Project.Name != pd.Project.Name && u.StartsWith(other.Project.Name, StringComparison.OrdinalIgnoreCase)));

            int afferentCoupling = projData
                .Where(other => other.Project.Name != pd.Project.Name)
                .Count(other => other.AllUsings.Any(u => u.StartsWith(pd.Project.Name, StringComparison.OrdinalIgnoreCase)));

            int totalCoupling = afferentCoupling + efferentCoupling;
            double instability = totalCoupling > 0 ? (double)efferentCoupling / totalCoupling : 0.0;
            double distance = Math.Abs(abstractness + instability - 1.0);

            result.Metrics.Add(new ProjectMetrics(
                pd.Project.Name, totalTypes, abstractTypes,
                Math.Round(abstractness, 3), afferentCoupling, efferentCoupling,
                Math.Round(instability, 3), Math.Round(distance, 3)
            ));

            Console.WriteLine($"  [{pd.Project.Name,-20}] Types: {totalTypes,4} | Ca: {afferentCoupling,2} | Ce: {efferentCoupling,2} | Instability (I): {instability:F2} | Abstractness (A): {abstractness:F2} | Distance (D): {distance:F2}");
        }
    }

    private static void DetectCircularDependencies(List<ProjectAuditScope> projects, SolutionAuditResult result)
    {
        // Build namespace dependency graph
        var graph = new Dictionary<string, HashSet<string>>();
        var namespaceToProject = new Dictionary<string, string>();

        foreach (var proj in projects)
        {
            foreach (var file in proj.ParsedFiles)
            {
                foreach (var declaredNs in file.DeclaredNamespaces)
                {
                    namespaceToProject[declaredNs] = proj.Name;
                    if (!graph.ContainsKey(declaredNs))
                        graph[declaredNs] = new HashSet<string>();

                    foreach (var u in file.Usings)
                    {
                        if (u.StartsWith("Nsdms.") && u != declaredNs)
                        {
                            graph[declaredNs].Add(u);
                        }
                    }
                }
            }
        }

        Console.WriteLine($"  Built namespace graph with {graph.Count} internal namespaces.");

        var visited = new Dictionary<string, int>(); // 0: unvisited, 1: visiting, 2: visited
        var stack = new List<string>();
        var detectedCycles = new HashSet<string>();

        foreach (var node in graph.Keys)
        {
            FindCyclesDfs(node, graph, visited, stack, detectedCycles, namespaceToProject, result);
        }

        if (detectedCycles.Count == 0)
            Console.WriteLine("  PASSED [CLEAN]: Zero circular dependencies found across all namespaces.");
        else
            Console.WriteLine($"  WARNING: Detected {detectedCycles.Count} cyclic dependency chains.");
    }

    private static void FindCyclesDfs(
        string current,
        Dictionary<string, HashSet<string>> graph,
        Dictionary<string, int> visited,
        List<string> stack,
        HashSet<string> detectedCycles,
        Dictionary<string, string> namespaceToProject,
        SolutionAuditResult result)
    {
        if (visited.TryGetValue(current, out int state) && state == 2)
            return;

        visited[current] = 1;
        stack.Add(current);

        if (graph.TryGetValue(current, out var neighbors))
        {
            foreach (var next in neighbors)
            {
                if (!visited.ContainsKey(next))
                    visited[next] = 0;

                if (visited[next] == 1)
                {
                    // Back-edge found -> Cycle!
                    int cycleStart = stack.IndexOf(next);
                    if (cycleStart >= 0)
                    {
                        var cycleNodes = stack.Skip(cycleStart).Concat(new[] { next }).ToList();
                        string canonicalKey = string.Join(" -> ", cycleNodes);
                        if (detectedCycles.Add(canonicalKey))
                        {
                            bool isCrossProject = cycleNodes.Select(n => namespaceToProject.GetValueOrDefault(n, "")).Distinct().Count() > 1;
                            result.Violations.Add(new ArchitectureViolation(
                                RuleId: isCrossProject ? "CYCLE001" : "CYCLE002",
                                Severity: isCrossProject ? "CRITICAL" : "MEDIUM",
                                Project: isCrossProject ? "Cross-Project" : (namespaceToProject.GetValueOrDefault(current, "Intra-Project")),
                                File: current,
                                Description: $"{(isCrossProject ? "Cross-Project" : "Intra-Project")} circular dependency detected: {canonicalKey}"
                            ));
                        }
                    }
                }
                else if (visited[next] == 0)
                {
                    FindCyclesDfs(next, graph, visited, stack, detectedCycles, namespaceToProject, result);
                }
            }
        }

        stack.RemoveAt(stack.Count - 1);
        visited[current] = 2;
    }

    private static void PrintAuditReport(SolutionAuditResult result)
    {
        Console.WriteLine("\n================================================================================");
        Console.WriteLine("                         STRUCTURAL AUDIT SCORECARD                             ");
        Console.WriteLine("================================================================================");
        Console.WriteLine($"Total Architectural Violations: {result.TotalViolations}");
        Console.WriteLine($"  - Critical: {result.CriticalCount}");
        Console.WriteLine($"  - High:     {result.HighCount}");
        Console.WriteLine($"  - Medium:   {result.MediumCount}\n");

        if (result.Violations.Count > 0)
        {
            Console.WriteLine("VIOLATION DETAILS:");
            foreach (var v in result.Violations)
            {
                Console.WriteLine($"  [{v.Severity}] {v.RuleId} ({v.Project}) in {v.File}");
                Console.WriteLine($"     -> {v.Description}\n");
            }
        }
        else
        {
            Console.WriteLine(">>> 100% CLEAN ARCHITECTURE CONFORMANCE CONFIRMED <<<");
            Console.WriteLine("All Domain, Application, Infrastructure, and Presentation boundaries verified.");
        }
        Console.WriteLine("================================================================================\n");
    }
}

// Data models
public enum ProjectLayer { Domain, Application, Infrastructure, Web, Tests, Tools }

public class ProjectAuditScope
{
    public string Name { get; }
    public string Path { get; }
    public ProjectLayer Layer { get; }
    public int TotalCsFiles { get; set; }
    public int TotalRazorFiles { get; set; }
    public List<ParsedFileInfo> ParsedFiles { get; } = new();
    public List<ParsedRazorInfo> ParsedRazorFiles { get; } = new();

    public ProjectAuditScope(string name, string path, ProjectLayer layer)
    {
        Name = name;
        Path = path;
        Layer = layer;
    }
}

public class ParsedFileInfo
{
    public string FilePath { get; set; } = "";
    public string RelativePath { get; set; } = "";
    public int SyntaxNodeCount { get; set; }
    public List<string> Usings { get; } = new();
    public List<string> DeclaredNamespaces { get; } = new();
    public List<DeclaredTypeInfo> DeclaredTypes { get; } = new();
    public HashSet<string> ReferencedTypeIdentifiers { get; } = new();
    public HashSet<string> InstantiatedTypes { get; } = new();
}

public class DeclaredTypeInfo
{
    public string Name { get; set; } = "";
    public string Kind { get; set; } = "";
    public bool IsAbstractOrInterface { get; set; }
    public List<string> BaseAndInterfaceNames { get; } = new();
}

public class ParsedRazorInfo
{
    public string FilePath { get; set; } = "";
    public string RelativePath { get; set; } = "";
    public List<string> Usings { get; } = new();
    public List<InjectedService> Injections { get; } = new();
    public bool HasDbContextReference { get; set; }
}

public record InjectedService(string TypeName, string PropertyName);

public record ArchitectureViolation(
    string RuleId,
    string Severity,
    string Project,
    string File,
    string Description
);

public record ProjectMetrics(
    string ProjectName,
    int TotalClasses,
    int AbstractClassesOrInterfaces,
    double Abstractness,
    int AfferentCoupling,
    int EfferentCoupling,
    double Instability,
    double DistanceFromMainSequence
);

public class SolutionAuditResult
{
    public List<ArchitectureViolation> Violations { get; } = new();
    public List<ProjectMetrics> Metrics { get; } = new();

    public int TotalViolations => Violations.Count;
    public int CriticalCount => Violations.Count(v => v.Severity == "CRITICAL");
    public int HighCount => Violations.Count(v => v.Severity == "HIGH");
    public int MediumCount => Violations.Count(v => v.Severity == "MEDIUM");
}
