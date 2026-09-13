using System.Globalization;
using System.Text;
using System.Text.RegularExpressions;
using Markdig;
using Nsdms.Application.Interfaces;
using YamlDotNet.Serialization;
using YamlDotNet.Serialization.NamingConventions;

namespace Nsdms.Application.Services;

/// <summary>
/// Implements the Open Knowledge Format (OKF v0.2) YAML Frontmatter and Markdown parser.
/// Enforces Section 11 Conformance: permissive consumption of unknown keys and types,
/// ISO-8601 UTC timestamp parsing, and footnote citation extraction.
/// </summary>
public class OkfFrontmatterParser : IOkfFrontmatterParser
{
    private static readonly Regex FrontmatterRegex = new(
        @"^---\r?\n(.*?)\r?\n---\r?\n(.*)$", 
        RegexOptions.Singleline | RegexOptions.Compiled);

    private static readonly Regex FootnoteRegex = new(
        @"\[\^([a-zA-Z0-9_\.\-]+)\]", 
        RegexOptions.Compiled);

    private static readonly Regex InternalLinkRegex = new(
        @"\[([^\]]+)\]\((/[^)]+\.md)\)", 
        RegexOptions.Compiled);

    private static readonly Regex ComputationFenceRegex = new(
        @"#\s+Computation\s*\r?\n(?:```(?:sql|tsql)?\r?\n)?(.*?)(?:```|$)", 
        RegexOptions.Singleline | RegexOptions.IgnoreCase | RegexOptions.Compiled);

    private readonly MarkdownPipeline _markdownPipeline;
    private readonly IDeserializer _yamlDeserializer;
    private readonly ISerializer _yamlSerializer;

    public OkfFrontmatterParser()
    {
        _markdownPipeline = new MarkdownPipelineBuilder()
            .UseAdvancedExtensions()
            .Build();

        _yamlDeserializer = new DeserializerBuilder()
            .WithNamingConvention(UnderscoredNamingConvention.Instance)
            .IgnoreUnmatchedProperties()
            .Build();

        _yamlSerializer = new SerializerBuilder()
            .WithNamingConvention(UnderscoredNamingConvention.Instance)
            .ConfigureDefaultValuesHandling(DefaultValuesHandling.OmitNull)
            .Build();
    }

    public ParsedOkfDocument ParseDocument(string rawContent)
    {
        if (string.IsNullOrWhiteSpace(rawContent))
        {
            return new ParsedOkfDocument { ConceptType = "Concept", BodyMarkdown = string.Empty };
        }

        var match = FrontmatterRegex.Match(rawContent);
        if (!match.Success)
        {
            // Plain markdown without frontmatter
            return new ParsedOkfDocument
            {
                ConceptType = "Concept",
                BodyMarkdown = rawContent.Trim()
            };
        }

        var yamlText = match.Groups[1].Value;
        var bodyMarkdown = match.Groups[2].Value.Trim();

        Dictionary<string, object?> rawMap;
        try
        {
            rawMap = _yamlDeserializer.Deserialize<Dictionary<string, object?>>(yamlText) 
                     ?? new Dictionary<string, object?>(StringComparer.OrdinalIgnoreCase);
        }
        catch
        {
            rawMap = new Dictionary<string, object?>(StringComparer.OrdinalIgnoreCase);
        }

        // Required Type
        var conceptType = GetStringValue(rawMap, "type") ?? GetStringValue(rawMap, "concept_type") ?? "Concept";
        var title = GetStringValue(rawMap, "title");
        var description = GetStringValue(rawMap, "description") ?? GetStringValue(rawMap, "summary_description");
        var resource = GetStringValue(rawMap, "resource");
        var status = GetStringValue(rawMap, "status") ?? "stable";

        // Tags
        var tags = new List<string>();
        if (rawMap.TryGetValue("tags", out var tagsObj) && tagsObj is IEnumerable<object> tagsList)
        {
            tags.AddRange(tagsList.Select(t => t?.ToString()?.Trim()).Where(t => !string.IsNullOrEmpty(t))!);
        }

        // Freshness: stale_after
        DateTime? staleAfter = ParseDateTime(GetStringValue(rawMap, "stale_after"));

        // Trust: generated
        string? generatedBy = null;
        DateTime? generatedAt = null;
        if (rawMap.TryGetValue("generated", out var genObj) && genObj is IDictionary<object, object> genMap)
        {
            generatedBy = GetMapValue(genMap, "by");
            generatedAt = ParseDateTime(GetMapValue(genMap, "at"));
        }
        if (string.IsNullOrWhiteSpace(generatedBy))
        {
            generatedBy = GetStringValue(rawMap, "generated_by");
        }

        // Trust: verified (Supports single bare mapping or list of mappings per §5.2)
        var verifications = new List<ParsedOkfVerification>();
        if (rawMap.TryGetValue("verified", out var verObj))
        {
            if (verObj is IList<object> verList)
            {
                foreach (var item in verList)
                {
                    if (item is IDictionary<object, object> vMap)
                    {
                        var by = GetMapValue(vMap, "by") ?? "unknown";
                        var at = ParseDateTime(GetMapValue(vMap, "at")) ?? DateTime.UtcNow;
                        var notes = GetMapValue(vMap, "notes");
                        verifications.Add(new ParsedOkfVerification { By = by, At = at, Notes = notes });
                    }
                }
            }
            else if (verObj is IDictionary<object, object> vMap)
            {
                var by = GetMapValue(vMap, "by") ?? "unknown";
                var at = ParseDateTime(GetMapValue(vMap, "at")) ?? DateTime.UtcNow;
                var notes = GetMapValue(vMap, "notes");
                verifications.Add(new ParsedOkfVerification { By = by, At = at, Notes = notes });
            }
        }

        // Provenance: sources
        var sources = new List<ParsedOkfSource>();
        if (rawMap.TryGetValue("sources", out var srcObj) && srcObj is IList<object> srcList)
        {
            foreach (var item in srcList)
            {
                if (item is IDictionary<object, object> sMap)
                {
                    var res = GetMapValue(sMap, "resource") ?? string.Empty;
                    var id = GetMapValue(sMap, "id");
                    var srcTitle = GetMapValue(sMap, "title");
                    var author = GetMapValue(sMap, "author");
                    long? usageCount = long.TryParse(GetMapValue(sMap, "usage_count"), out var uc) ? uc : null;
                    DateTime? lastMod = ParseDateTime(GetMapValue(sMap, "last_modified"));

                    sources.Add(new ParsedOkfSource
                    {
                        Id = id,
                        Resource = res,
                        Title = srcTitle,
                        Author = author,
                        UsageCount = usageCount,
                        LastModified = lastMod
                    });
                }
            }
        }

        // Attested Computation fields (Section 10)
        ParsedOkfComputation? computation = null;
        if (conceptType.Equals("Attested Computation", StringComparison.OrdinalIgnoreCase) || rawMap.ContainsKey("runtime"))
        {
            var runtime = GetStringValue(rawMap, "runtime") ?? "tsql";
            var compPath = GetStringValue(rawMap, "computation");

            // Extract inline computation from body if not pointing to an external path
            string? inlineComp = null;
            if (string.IsNullOrEmpty(compPath))
            {
                var fenceMatch = ComputationFenceRegex.Match(bodyMarkdown);
                if (fenceMatch.Success)
                {
                    inlineComp = fenceMatch.Groups[1].Value.Trim();
                }
            }

            var executorRes = "references/skills/run-tsql.md";
            var receiptKeys = new List<string> { "execution_id", "executed_sql", "rows_affected", "result_digest" };
            if (rawMap.TryGetValue("executor", out var execObj) && execObj is IDictionary<object, object> execMap)
            {
                executorRes = GetMapValue(execMap, "resource") ?? executorRes;
                if (execMap.TryGetValue("receipt", out var recObj) && recObj is IList<object> recList)
                {
                    receiptKeys = recList.Select(r => r.ToString()!).ToList();
                }
            }

            var attesterRes = "references/attesters/tsql-equality.cs";
            if (rawMap.TryGetValue("attester", out var attObj) && attObj is IDictionary<object, object> attMap)
            {
                attesterRes = GetMapValue(attMap, "resource") ?? attesterRes;
            }

            var parameters = new List<ParsedOkfParameter>();
            if (rawMap.TryGetValue("parameters", out var paramsObj) && paramsObj is IList<object> paramsList)
            {
                foreach (var pItem in paramsList)
                {
                    if (pItem is IDictionary<object, object> pMap)
                    {
                        var pName = GetMapValue(pMap, "name") ?? string.Empty;
                        var pType = GetMapValue(pMap, "type") ?? "nvarchar";
                        var reqStr = GetMapValue(pMap, "required");
                        var isReq = !string.IsNullOrEmpty(reqStr) && bool.TryParse(reqStr, out var r) ? r : true;
                        var defVal = GetMapValue(pMap, "default");

                        if (!string.IsNullOrEmpty(pName))
                        {
                            parameters.Add(new ParsedOkfParameter
                            {
                                Name = pName,
                                Type = pType,
                                Required = isReq,
                                DefaultValue = defVal
                            });
                        }
                    }
                }
            }

            computation = new ParsedOkfComputation
            {
                Runtime = runtime,
                ComputationPath = compPath,
                InlineComputation = inlineComp,
                ExecutorResource = executorRes,
                AttesterResource = attesterRes,
                ReceiptKeys = receiptKeys,
                Parameters = parameters
            };
        }

        // Extract footnote citation IDs from body markdown
        var footnoteIds = new HashSet<string>();
        foreach (Match fnMatch in FootnoteRegex.Matches(bodyMarkdown))
        {
            footnoteIds.Add(fnMatch.Groups[1].Value);
        }

        // Extract internal cross-links (/path/to/concept.md)
        var crossLinks = new HashSet<string>();
        foreach (Match linkMatch in InternalLinkRegex.Matches(bodyMarkdown))
        {
            crossLinks.Add(linkMatch.Groups[2].Value);
        }

        return new ParsedOkfDocument
        {
            ConceptType = conceptType,
            Title = title,
            Description = description,
            Resource = resource,
            Tags = tags,
            Status = status,
            StaleAfter = staleAfter,
            GeneratedBy = generatedBy,
            GeneratedAt = generatedAt,
            Verifications = verifications,
            Sources = sources,
            Computation = computation,
            BodyMarkdown = bodyMarkdown,
            FootnoteReferenceIds = footnoteIds.ToList(),
            InternalCrossLinks = crossLinks.ToList(),
            RawFrontmatter = rawMap
        };
    }

    public string RenderToHtml(string markdownBody)
    {
        if (string.IsNullOrWhiteSpace(markdownBody))
            return string.Empty;

        return Markdown.ToHtml(markdownBody, _markdownPipeline);
    }

    public string SerializeDocument(ParsedOkfDocument doc)
    {
        var frontmatter = new Dictionary<string, object?>
        {
            ["type"] = doc.ConceptType,
            ["title"] = doc.Title,
            ["description"] = doc.Description,
            ["resource"] = doc.Resource,
            ["tags"] = doc.Tags.Count > 0 ? doc.Tags : null,
            ["status"] = doc.Status
        };

        if (doc.StaleAfter.HasValue)
        {
            frontmatter["stale_after"] = doc.StaleAfter.Value.ToString("yyyy-MM-ddTHH:mm:ssZ", CultureInfo.InvariantCulture);
        }

        if (!string.IsNullOrEmpty(doc.GeneratedBy))
        {
            frontmatter["generated"] = new Dictionary<string, string>
            {
                ["by"] = doc.GeneratedBy,
                ["at"] = (doc.GeneratedAt ?? DateTime.UtcNow).ToString("yyyy-MM-ddTHH:mm:ssZ", CultureInfo.InvariantCulture)
            };
        }

        if (doc.Verifications.Count > 0)
        {
            frontmatter["verified"] = doc.Verifications.Select(v => new Dictionary<string, string>
            {
                ["by"] = v.By,
                ["at"] = v.At.ToString("yyyy-MM-ddTHH:mm:ssZ", CultureInfo.InvariantCulture)
            }).ToList();
        }

        if (doc.Sources.Count > 0)
        {
            frontmatter["sources"] = doc.Sources.Select(s =>
            {
                var dict = new Dictionary<string, object?>
                {
                    ["resource"] = s.Resource
                };
                if (!string.IsNullOrEmpty(s.Id)) dict["id"] = s.Id;
                if (!string.IsNullOrEmpty(s.Title)) dict["title"] = s.Title;
                if (!string.IsNullOrEmpty(s.Author)) dict["author"] = s.Author;
                if (s.UsageCount.HasValue) dict["usage_count"] = s.UsageCount.Value;
                if (s.LastModified.HasValue) dict["last_modified"] = s.LastModified.Value.ToString("yyyy-MM-ddTHH:mm:ssZ", CultureInfo.InvariantCulture);
                return dict;
            }).ToList();
        }

        if (doc.Computation != null)
        {
            frontmatter["runtime"] = doc.Computation.Runtime;
            if (!string.IsNullOrEmpty(doc.Computation.ComputationPath))
            {
                frontmatter["computation"] = doc.Computation.ComputationPath;
            }

            if (doc.Computation.Parameters.Count > 0)
            {
                frontmatter["parameters"] = doc.Computation.Parameters.Select(p => new Dictionary<string, object?>
                {
                    ["name"] = p.Name,
                    ["type"] = p.Type,
                    ["required"] = p.Required,
                    ["default"] = p.DefaultValue
                }).ToList();
            }

            frontmatter["executor"] = new Dictionary<string, object?>
            {
                ["resource"] = doc.Computation.ExecutorResource,
                ["receipt"] = doc.Computation.ReceiptKeys
            };

            frontmatter["attester"] = new Dictionary<string, string>
            {
                ["resource"] = doc.Computation.AttesterResource
            };
        }

        var yaml = _yamlSerializer.Serialize(frontmatter);
        var sb = new StringBuilder();
        sb.AppendLine("---");
        sb.Append(yaml);
        sb.AppendLine("---");
        sb.AppendLine();
        sb.Append(doc.BodyMarkdown);

        return sb.ToString();
    }

    private static string? GetStringValue(Dictionary<string, object?> map, string key)
    {
        return map.TryGetValue(key, out var val) && val != null ? val.ToString()?.Trim() : null;
    }

    private static string? GetMapValue(IDictionary<object, object> map, string key)
    {
        foreach (var entry in map)
        {
            if (entry.Key?.ToString()?.Equals(key, StringComparison.OrdinalIgnoreCase) == true)
            {
                return entry.Value?.ToString()?.Trim();
            }
        }
        return null;
    }

    private static DateTime? ParseDateTime(string? raw)
    {
        if (string.IsNullOrWhiteSpace(raw)) return null;

        if (DateTime.TryParse(raw, CultureInfo.InvariantCulture, DateTimeStyles.AdjustToUniversal, out var dt))
        {
            return dt;
        }
        return null;
    }
}
