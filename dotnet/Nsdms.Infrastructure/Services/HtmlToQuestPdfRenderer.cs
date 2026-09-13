using System.Net;
using System.Text.RegularExpressions;
using QuestPDF.Fluent;
using QuestPDF.Helpers;
using QuestPDF.Infrastructure;

namespace Nsdms.Infrastructure.Services;

/// <summary>
/// High-performance HTML and Rich-Text parser that translates document template bodies into QuestPDF layouts.
/// Supports headings (h1-h4), paragraphs, spans (bold, italic, underline, code), lists (ul, ol, li),
/// tables (table, tr, th, td), callout boxes, and horizontal dividers.
/// </summary>
public static class HtmlToQuestPdfRenderer
{
    private static readonly Regex TagRegex = new(@"<(?<closing>/?)(?<tag>[a-zA-Z0-9]+)(?<attrs>[^>]*)>", RegexOptions.Compiled);
    private static readonly Regex StyleColorRegex = new(@"color\s*:\s*(#[0-9a-fA-F]{3,8}|[a-zA-Z]+)", RegexOptions.Compiled);
    private static readonly Regex StyleAlignRegex = new(@"text-align\s*:\s*(left|right|center|justify)", RegexOptions.Compiled);

    public static void RenderHtmlToColumn(ColumnDescriptor column, string htmlContent)
    {
        if (string.IsNullOrWhiteSpace(htmlContent)) return;

        // Normalize line breaks
        string cleanHtml = htmlContent.Trim();

        // Check if content contains HTML tags; if not, render as plain structured text
        if (!cleanHtml.Contains('<') || !cleanHtml.Contains('>'))
        {
            RenderPlainText(column, cleanHtml);
            return;
        }

        // Split into top-level blocks
        var blocks = ExtractBlocks(cleanHtml);
        foreach (var block in blocks)
        {
            RenderBlock(column, block);
        }
    }

    private static void RenderBlock(ColumnDescriptor column, HtmlBlock block)
    {
        switch (block.TagName.ToLowerInvariant())
        {
            case "h1":
                column.Item().PaddingTop(10).PaddingBottom(4).Text(t =>
                {
                    ApplySpan(t, block.InnerText, isBold: true, fontSize: 16, colorHex: block.ColorHex ?? "#0d47a1");
                });
                break;

            case "h2":
                column.Item().PaddingTop(8).PaddingBottom(4).Text(t =>
                {
                    ApplySpan(t, block.InnerText, isBold: true, fontSize: 13, colorHex: block.ColorHex ?? "#0d47a1");
                });
                break;

            case "h3":
                column.Item().PaddingTop(6).PaddingBottom(3).Text(t =>
                {
                    ApplySpan(t, block.InnerText, isBold: true, fontSize: 11, colorHex: block.ColorHex ?? "#1565c0");
                });
                break;

            case "h4":
                column.Item().PaddingTop(4).PaddingBottom(2).Text(t =>
                {
                    ApplySpan(t, block.InnerText, isBold: true, fontSize: 10, colorHex: block.ColorHex ?? "#1b5e20");
                });
                break;

            case "p":
                column.Item().PaddingBottom(4).Text(t =>
                {
                    RenderInlineFormatting(t, block.RawHtml, fontSize: 9.5f);
                });
                break;

            case "ul":
            case "ol":
                RenderList(column, block);
                break;

            case "table":
                RenderTable(column, block.RawHtml);
                break;

            case "hr":
                column.Item().PaddingVertical(6).LineHorizontal(1).LineColor(Colors.Grey.Lighten1);
                break;

            case "div":
            case "blockquote":
                RenderCalloutBox(column, block);
                break;

            default:
                if (!string.IsNullOrWhiteSpace(block.InnerText))
                {
                    column.Item().PaddingBottom(3).Text(t =>
                    {
                        RenderInlineFormatting(t, block.RawHtml, fontSize: 9.5f);
                    });
                }
                break;
        }
    }

    private static void RenderList(ColumnDescriptor column, HtmlBlock listBlock)
    {
        var itemMatches = Regex.Matches(listBlock.RawHtml, @"<li[^>]*>(.*?)</li>", RegexOptions.Singleline | RegexOptions.IgnoreCase);
        bool isOrdered = listBlock.TagName.Equals("ol", StringComparison.OrdinalIgnoreCase);
        int index = 1;

        column.Item().PaddingLeft(12).PaddingVertical(3).Column(listCol =>
        {
            listCol.Spacing(3);
            foreach (Match item in itemMatches)
            {
                string bullet = isOrdered ? $"{index++}." : "•";
                string itemHtml = item.Groups[1].Value.Trim();

                listCol.Item().Row(r =>
                {
                    r.ConstantItem(15).Text(bullet).Bold().FontColor("#0d47a1").FontSize(9.5f);
                    r.RelativeItem().Text(t => RenderInlineFormatting(t, itemHtml, fontSize: 9.5f));
                });
            }
        });
    }

    private static void RenderTable(ColumnDescriptor column, string tableHtml)
    {
        var rowMatches = Regex.Matches(tableHtml, @"<tr[^>]*>(.*?)</tr>", RegexOptions.Singleline | RegexOptions.IgnoreCase);
        if (rowMatches.Count == 0) return;

        var tableData = new List<List<(string Content, bool IsHeader)>>();
        int maxCols = 0;

        foreach (Match row in rowMatches)
        {
            var cellMatches = Regex.Matches(row.Groups[1].Value, @"<(td|th)[^>]*>(.*?)</\1>", RegexOptions.Singleline | RegexOptions.IgnoreCase);
            var rowCells = new List<(string Content, bool IsHeader)>();
            foreach (Match cell in cellMatches)
            {
                bool isHeader = cell.Groups[1].Value.Equals("th", StringComparison.OrdinalIgnoreCase);
                string content = cell.Groups[2].Value.Trim();
                rowCells.Add((content, isHeader));
            }
            if (rowCells.Count > maxCols) maxCols = rowCells.Count;
            if (rowCells.Count > 0) tableData.Add(rowCells);
        }

        if (maxCols == 0) return;

        column.Item().PaddingVertical(6).Table(table =>
        {
            table.ColumnsDefinition(columns =>
            {
                for (int i = 0; i < maxCols; i++)
                {
                    columns.RelativeColumn();
                }
            });

            foreach (var row in tableData)
            {
                for (int c = 0; c < maxCols; c++)
                {
                    var cell = c < row.Count ? row[c] : (Content: string.Empty, IsHeader: false);
                    var cellContainer = table.Cell()
                        .Border(1)
                        .BorderColor(Colors.Grey.Lighten2)
                        .Background(cell.IsHeader ? Colors.Grey.Lighten4 : Colors.White)
                        .Padding(5);

                    cellContainer.Text(t =>
                    {
                        RenderInlineFormatting(t, cell.Content, fontSize: 9.0f, forceBold: cell.IsHeader);
                    });
                }
            }
        });
    }

    private static void RenderCalloutBox(ColumnDescriptor column, HtmlBlock block)
    {
        string borderLeftColor = "#0d47a1";
        string bgColor = "#f5f7fa";

        if (block.RawHtml.Contains("#d32f2f") || block.RawHtml.Contains("red") || block.RawHtml.Contains("fbe9e7"))
        {
            borderLeftColor = "#d32f2f";
            bgColor = "#fbe9e7";
        }
        else if (block.RawHtml.Contains("#2e7d32") || block.RawHtml.Contains("green") || block.RawHtml.Contains("e8f5e9"))
        {
            borderLeftColor = "#2e7d32";
            bgColor = "#e8f5e9";
        }
        else if (block.RawHtml.Contains("#f57c00") || block.RawHtml.Contains("orange") || block.RawHtml.Contains("fff3e0"))
        {
            borderLeftColor = "#f57c00";
            bgColor = "#fff3e0";
        }

        column.Item().PaddingVertical(5).BorderLeft(3.5f).BorderColor(borderLeftColor).Background(bgColor).Padding(8).Column(calloutCol =>
        {
            var innerBlocks = ExtractBlocks(block.RawHtml);
            if (innerBlocks.Count > 0)
            {
                foreach (var ib in innerBlocks)
                {
                    RenderBlock(calloutCol, ib);
                }
            }
            else
            {
                calloutCol.Item().Text(t => RenderInlineFormatting(t, block.RawHtml, fontSize: 9.0f));
            }
        });
    }

    private static void RenderInlineFormatting(TextDescriptor textDescriptor, string inlineHtml, float fontSize = 9.5f, bool forceBold = false)
    {
        if (string.IsNullOrWhiteSpace(inlineHtml)) return;

        // Tokenize inline HTML
        int lastIndex = 0;
        var tagMatches = TagRegex.Matches(inlineHtml);

        bool isBold = forceBold;
        bool isItalic = false;
        bool isCode = false;

        foreach (Match match in tagMatches)
        {
            if (match.Index > lastIndex)
            {
                string text = inlineHtml.Substring(lastIndex, match.Index - lastIndex);
                string decoded = WebUtility.HtmlDecode(text);
                if (!string.IsNullOrEmpty(decoded))
                {
                    var span = textDescriptor.Span(decoded).FontSize(fontSize);
                    if (isBold) span.Bold();
                    if (isItalic) span.Italic();
                    if (isCode) span.FontFamily("Courier New").FontColor("#c2185b").FontSize(fontSize * 0.92f);
                }
            }

            bool isClosing = match.Groups["closing"].Value == "/";
            string tag = match.Groups["tag"].Value.ToLowerInvariant();

            switch (tag)
            {
                case "b":
                case "strong":
                    isBold = !isClosing || forceBold;
                    break;
                case "i":
                case "em":
                    isItalic = !isClosing;
                    break;
                case "code":
                    isCode = !isClosing;
                    break;
                case "br":
                    textDescriptor.Span("\n");
                    break;
            }

            lastIndex = match.Index + match.Length;
        }

        if (lastIndex < inlineHtml.Length)
        {
            string remaining = inlineHtml.Substring(lastIndex);
            string decoded = WebUtility.HtmlDecode(remaining);
            if (!string.IsNullOrEmpty(decoded))
            {
                var span = textDescriptor.Span(decoded).FontSize(fontSize);
                if (isBold) span.Bold();
                if (isItalic) span.Italic();
                if (isCode) span.FontFamily("Courier New").FontColor("#c2185b").FontSize(fontSize * 0.92f);
            }
        }
    }

    private static void ApplySpan(TextDescriptor textDescriptor, string text, bool isBold, float fontSize, string colorHex)
    {
        string decoded = WebUtility.HtmlDecode(text);
        var span = textDescriptor.Span(decoded).FontSize(fontSize).FontColor(colorHex);
        if (isBold) span.Bold();
    }

    private static void RenderPlainText(ColumnDescriptor column, string text)
    {
        var lines = text.Split(new[] { "\r\n", "\r", "\n" }, StringSplitOptions.None);
        foreach (var line in lines)
        {
            if (string.IsNullOrWhiteSpace(line))
            {
                column.Item().Height(4);
                continue;
            }

            if (line.TrimStart().StartsWith("•") || line.TrimStart().StartsWith("-") || line.TrimStart().StartsWith("*"))
            {
                column.Item().PaddingLeft(12).Row(bulletRow =>
                {
                    bulletRow.ConstantItem(12).Text("•").Bold().FontColor("#0d47a1");
                    bulletRow.RelativeItem().Text(line.TrimStart('•', '-', '*', ' ')).FontSize(9.5f);
                });
            }
            else
            {
                column.Item().Text(line).FontSize(9.5f).LineHeight(1.3f);
            }
        }
    }

    private static List<HtmlBlock> ExtractBlocks(string html)
    {
        var list = new List<HtmlBlock>();
        var blockRegex = new Regex(@"<(?<tag>h[1-6]|p|ul|ol|table|div|blockquote|hr)(?<attrs>[^>]*)>(?<content>.*?)</\k<tag>>|<(?<tag>hr)[^>]*>", RegexOptions.Singleline | RegexOptions.IgnoreCase);

        int lastIndex = 0;
        var matches = blockRegex.Matches(html);

        foreach (Match m in matches)
        {
            if (m.Index > lastIndex)
            {
                string interstitial = html.Substring(lastIndex, m.Index - lastIndex).Trim();
                if (!string.IsNullOrWhiteSpace(interstitial) && !interstitial.StartsWith("<") && !interstitial.EndsWith(">"))
                {
                    list.Add(new HtmlBlock("p", interstitial, interstitial, null));
                }
            }

            string tagName = m.Groups["tag"].Value;
            string attrs = m.Groups["attrs"].Value;
            string content = m.Groups["content"].Success ? m.Groups["content"].Value : string.Empty;

            string? color = null;
            var colorMatch = StyleColorRegex.Match(attrs);
            if (colorMatch.Success) color = colorMatch.Groups[1].Value;

            list.Add(new HtmlBlock(tagName, content, StripTags(content), color));
            lastIndex = m.Index + m.Length;
        }

        if (lastIndex < html.Length)
        {
            string remaining = html.Substring(lastIndex).Trim();
            if (!string.IsNullOrWhiteSpace(remaining))
            {
                list.Add(new HtmlBlock("p", remaining, StripTags(remaining), null));
            }
        }

        return list;
    }

    private static string StripTags(string input)
    {
        if (string.IsNullOrEmpty(input)) return string.Empty;
        return WebUtility.HtmlDecode(Regex.Replace(input, "<.*?>", string.Empty).Trim());
    }

    private record HtmlBlock(string TagName, string RawHtml, string InnerText, string? ColorHex);
}
