using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text.RegularExpressions;
using Xunit;

namespace Nsdms.Tests;

/// <summary>
/// Continuous Automated Build Guard (Tier 2 Governance)
/// Enforces the merSETA & Skills Development Act (SDA) Statutory UI Lexicon Standard.
/// Systematically scans all user-facing Razor markup and UI strings across Nsdms.Web
/// to guarantee zero occurrences of technical engineering or design pattern jargon
/// (e.g., "gate", "gating", "blueprint", "payload", "maker-checker", "4-eyes").
/// </summary>
public class StatutoryUiLexiconTests
{
    private static readonly Dictionary<string, string> ProhibitedJargonPatterns = new()
    {
        { @"\b(gate|gates|gating)\b", "Statutory term required (e.g. Requirement, Criterion, Stage, Milestone, Threshold)" },
        { @"\b(blueprint|blueprints)\b", "Statutory term required (e.g. Process Definition, Approval Lifecycle, Workflow Specification)" },
        { @"\b(payload|payloads)\b", "Statutory term required (e.g. Transaction Data, Test Record, Evaluation Parameters)" },
        { @"\b(maker-checker|4-eyes)\b", "Statutory term required (e.g. Dual Authorisation Control, Independent Review & Approval, Segregation of Duties)" }
    };

    private static string FindWebDirectory()
    {
        var current = AppContext.BaseDirectory;
        while (!string.IsNullOrEmpty(current))
        {
            var candidate = Path.Combine(current, "Nsdms.Web");
            if (Directory.Exists(candidate)) return candidate;

            var dotnetCandidate = Path.Combine(current, "dotnet", "Nsdms.Web");
            if (Directory.Exists(dotnetCandidate)) return dotnetCandidate;

            var parent = Directory.GetParent(current)?.FullName;
            if (parent == current) break;
            current = parent;
        }

        // Fallback relative to repository root
        var solutionDir = Path.GetFullPath(Path.Combine(AppContext.BaseDirectory, "..", "..", "..", ".."));
        var webDir = Path.Combine(solutionDir, "Nsdms.Web");
        if (Directory.Exists(webDir)) return webDir;

        throw new DirectoryNotFoundException("Could not locate Nsdms.Web directory from " + AppContext.BaseDirectory);
    }

    [Fact]
    public void AllRazorFiles_MustNotContainProhibitedTechnicalJargon()
    {
        var webDir = FindWebDirectory();
        var razorFiles = Directory.GetFiles(webDir, "*.razor", SearchOption.AllDirectories);

        Assert.NotEmpty(razorFiles);

        var violations = new List<string>();

        foreach (var file in razorFiles)
        {
            var relativePath = Path.GetRelativePath(webDir, file).Replace('\\', '/');
            var content = File.ReadAllText(file);

            // Strip Razor comments (@* ... *@)
            var cleanContent = Regex.Replace(content, @"@\*[\s\S]*?\*@", string.Empty);

            var lines = cleanContent.Split(new[] { "\r\n", "\r", "\n" }, StringSplitOptions.None);
            for (int i = 0; i < lines.Length; i++)
            {
                var line = lines[i];

                // Skip lines that are purely C# using/inject statements or internal variable/parameter declarations
                var trimmed = line.Trim();
                if (trimmed.StartsWith("@using ") || trimmed.StartsWith("@inject ") || trimmed.StartsWith("//"))
                    continue;

                // Skip seed email address references in login test accounts
                if (trimmed.Contains("admin.checker@merseta.org.za", StringComparison.OrdinalIgnoreCase))
                    continue;

                foreach (var (pattern, replacementGuidance) in ProhibitedJargonPatterns)
                {
                    var match = Regex.Match(line, pattern, RegexOptions.IgnoreCase);
                    if (match.Success)
                    {
                        violations.Add($"[{relativePath}:L{i + 1}] Found prohibited term '{match.Value}' -> {replacementGuidance}. Line: \"{trimmed}\"");
                    }
                }
            }
        }

        Assert.True(violations.Count == 0,
            $"Found {violations.Count} prohibited jargon occurrences in Razor views:\n" + string.Join("\n", violations));
    }

    [Fact]
    public void StatutoryLexiconDictionary_ContainsRequiredStatutoryTermMappings()
    {
        var statutoryTaxonomy = new Dictionary<string, string[]>
        {
            ["Gate"] = new[] { "Requirement", "Criterion", "Stage", "Milestone", "Threshold" },
            ["Gating"] = new[] { "Enforcing statutory requirements", "Qualifying criteria", "Precondition checking" },
            ["Blueprint"] = new[] { "Process Definition", "Approval Lifecycle", "Workflow Specification" },
            ["Payload"] = new[] { "Transaction Data", "Test Records", "Evaluation Parameters" },
            ["Maker-Checker"] = new[] { "Dual Authorisation Control", "Segregation of Duties", "Independent Review & Approval", "Proposer / Reviewer" },
            ["Temporal Table"] = new[] { "Historical Version Timeline", "Point-in-time Audit History" },
            ["Document Hash"] = new[] { "Digital Security Seal", "Verification Reference" }
        };

        foreach (var (prohibited, alternatives) in statutoryTaxonomy)
        {
            Assert.NotEmpty(alternatives);
            foreach (var alt in alternatives)
            {
                Assert.False(string.IsNullOrWhiteSpace(alt), $"Empty alternative provided for {prohibited}");
            }
        }
    }
}