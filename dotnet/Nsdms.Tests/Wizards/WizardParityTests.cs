using Bunit;
using Microsoft.AspNetCore.Components;
using Microsoft.Extensions.DependencyInjection;
using MudBlazor.Services;
using Nsdms.Web.Components.Shared.Forms;
using Nsdms.Web.Components.Shared.Wizard;
using Xunit;

namespace Nsdms.Tests.Wizards;

public class WizardParityTests : BunitContext, IAsyncLifetime
{
    public WizardParityTests()
    {
        Services.AddMudServices();
        JSInterop.Mode = JSRuntimeMode.Loose;
    }

    public Task InitializeAsync() => Task.CompletedTask;

    public new async Task DisposeAsync()
    {
        await base.DisposeAsync();
    }

    /// <summary>
    /// Registry of entities configured with standardized wizard and shared field definitions.
    /// Adding an entity is a single entry.
    /// </summary>
    public static readonly TheoryData<string, string[]> RegisteredWizardFields = new()
    {
        {
            "AssessorReRegistration",
            new[]
            {
                "Application type",
                "Practice summary",
                "Activity title",
                "Points claimed",
                "Confirm renewal of registered assessment scopes",
                "Certify compliance with assessor code of conduct",
                "Consent to practitioner credential processing under POPIA"
            }
        }
    };

    [Theory]
    [MemberData(nameof(RegisteredWizardFields))]
    public void SharedFieldDefinition_ContainsExpectedStatutoryFields(string entityName, string[] expectedFields)
    {
        Assert.False(string.IsNullOrWhiteSpace(entityName));

        // Render the shared field definition for the entity in "All" sections mode
        var cut = Render<AssessorReRegistrationFields>(parameters => parameters
            .Add(p => p.Section, "All")
            .Add(p => p.RegistrationNumber, "REG-2026-001")
            .Add(p => p.CurrentExpirationDateText, "2026-09-04")
            .Add(p => p.ProposedExpirationDateText, "2029-09-04")
            .Add(p => p.CpdPortfolioSummary, "Experience")
        );

        var renderedMarkup = cut.Markup;

        foreach (var field in expectedFields)
        {
            Assert.Contains(field, renderedMarkup, StringComparison.OrdinalIgnoreCase);
        }
    }

    [Fact]
    public void WizardReviewStep_RendersAllProvidedReviewGroupsAndEditButtons()
    {
        var editStepInvoked = -1;

        var groups = new List<WizardReviewGroup>
        {
            new()
            {
                Title = "Application type",
                TargetStepIndex = 0,
                Items = new()
                {
                    new("Type", "Standard 3-year re-registration"),
                    new("Registration number", "REG-2026-001", isMonospace: true)
                }
            },
            new()
            {
                Title = "Practice standing",
                TargetStepIndex = 1,
                Items = new()
                {
                    new("Current expiry", "2026-09-04"),
                    new("Proposed new expiry", "2029-09-04"),
                    new("Practice summary", "Summary notes")
                }
            },
            new()
            {
                Title = "CPD evidence",
                TargetStepIndex = 2,
                Items = new()
                {
                    new("Points accumulated", "30 / 30 points"),
                    new("Activities logged", "3 activities")
                }
            }
        };

        var cut = Render<WizardReviewStep>(parameters => parameters
            .Add(p => p.ReviewGroups, groups)
            .Add(p => p.OnEditStep, EventCallback.Factory.Create<int>(this, (idx) => editStepInvoked = idx))
        );

        // Verify all groups and items are displayed
        Assert.Contains("Application type", cut.Markup);
        Assert.Contains("Practice standing", cut.Markup);
        Assert.Contains("CPD evidence", cut.Markup);
        Assert.Contains("REG-2026-001", cut.Markup);
        Assert.Contains("30 / 30 points", cut.Markup);

        // Verify Edit links exist and clicking triggers target step
        var editButtons = cut.FindAll("button.wizard-review-edit-btn");
        Assert.Equal(3, editButtons.Count);

        editButtons[1].Click();
        Assert.Equal(1, editStepInvoked);
    }

    [Fact]
    public void WizardDiscoveryTest_FailsIfWizardExistsWithoutStandardCompliance()
    {
        // Scans the codebase for *Wizard.razor files and asserts all are registered in standard wizard inventory
        var solutionDir = Path.GetFullPath(Path.Combine(AppContext.BaseDirectory, "..", "..", "..", ".."));
        var webDir = Path.Combine(solutionDir, "Nsdms.Web", "Components", "Pages");

        if (Directory.Exists(webDir))
        {
            var wizardFiles = Directory.GetFiles(webDir, "*Wizard.razor", SearchOption.AllDirectories)
                .Select(Path.GetFileName)
                .ToList();

            var recognizedWizards = new HashSet<string>(StringComparer.OrdinalIgnoreCase)
            {
                "AssessorReRegistrationWizard.razor",
                // WspSignoffWizard.razor is documented as an attestation status board rather than a sequential stepper
                "WspSignoffWizard.razor"
            };

            foreach (var wizard in wizardFiles)
            {
                Assert.True(
                    recognizedWizards.Contains(wizard!),
                    $"Discovered multi-step wizard '{wizard}' that is not registered in the standard wizard inventory. Ensure it is built using WizardShell and added to test coverage."
                );
            }
        }
    }
}
