using Microsoft.AspNetCore.Components;

namespace Nsdms.Web.Components.Shared.Wizard;

public class WizardReviewGroup
{
    public string Title { get; set; } = string.Empty;
    public string? Icon { get; set; }
    public int TargetStepIndex { get; set; } = -1;
    public List<WizardReviewItem> Items { get; set; } = new();
    public RenderFragment? CustomContent { get; set; }
}

public class WizardReviewItem
{
    public string Label { get; set; } = string.Empty;
    public string? Value { get; set; }
    public bool IsMonospace { get; set; }

    public WizardReviewItem() { }

    public WizardReviewItem(string label, string? value, bool isMonospace = false)
    {
        Label = label;
        Value = value;
        IsMonospace = isMonospace;
    }
}
