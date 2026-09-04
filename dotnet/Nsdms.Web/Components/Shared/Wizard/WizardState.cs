namespace Nsdms.Web.Components.Shared.Wizard;

/// <summary>
/// Generic state container for multi-step wizards.
/// Preserves model data and step progression across forward and backward navigation.
/// </summary>
/// <typeparam name=TModel>The entity or draft model type managed by the wizard.</typeparam>
public class WizardState<TModel> where TModel : class, new()
{
    public TModel Model { get; set; } = new();

    public int CurrentStepIndex { get; set; } = 0;

    public int MaxVisitedStepIndex { get; set; } = 0;

    public HashSet<int> CompletedSteps { get; } = new();

    public bool IsBusy { get; set; }

    public string? ErrorMessage { get; set; }

    public bool IsDirty { get; set; }

    public bool CanNavigateToStep(int targetIndex)
    {
        return targetIndex <= MaxVisitedStepIndex && targetIndex >= 0;
    }

    public void SetStep(int index)
    {
        if (index < 0) return;
        CurrentStepIndex = index;
        if (index > MaxVisitedStepIndex)
        {
            MaxVisitedStepIndex = index;
        }
    }

    public void MarkStepCompleted(int index)
    {
        CompletedSteps.Add(index);
        if (index >= MaxVisitedStepIndex)
        {
            MaxVisitedStepIndex = index + 1;
        }
    }
}
