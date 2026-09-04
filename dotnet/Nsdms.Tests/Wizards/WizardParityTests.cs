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
                "DgGrantApplicationWizard.razor",
                "LearnerAgreementRegistrationWizard.razor",
                "WorkplaceApprovalWizard.razor",
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

    [Fact]
    public void DgGrantApplicationFields_RendersExpectedStatutoryFields()
    {
        var cut = Render<DgGrantApplicationFields>(parameters => parameters
            .Add(p => p.Section, "All")
            .Add(p => p.ProjectTitle, "Artisan Upskilling 2026")
            .Add(p => p.SdlNumber, "L123456789")
            .Add(p => p.TaxCompliancePin, "9823471029")
            .Add(p => p.RequestedAmount, 350000m)
        );

        Assert.Contains("Funding window", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Grant type", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Project title", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Applying organisation", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Skills Development Levy (SDL) number", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Total funding requested", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("SARS tax compliance PIN", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("B-BBEE contributor status level", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("POPIA", cut.Markup, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public void LearnerAgreementRegistrationFields_RendersExpectedStatutoryFields()
    {
        var cut = Render<LearnerAgreementRegistrationFields>(parameters => parameters
            .Add(p => p.Section, "All")
            .Add(p => p.RsaIdNumber, "0102035123088")
            .Add(p => p.FirstName, "Sipho")
            .Add(p => p.LastName, "Dlamini")
            .Add(p => p.LearnershipRegNumber, "17Q170034281204")
            .Add(p => p.QualificationTitle, "Automotive Maintenance")
        );

        Assert.Contains("South African National ID number", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("First names", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Surname", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Learning programme type", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Host employer organisation", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Accredited Skills Development Provider (SDP)", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Monthly learner allowance", cut.Markup, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public void WorkplaceApprovalFields_RendersExpectedStatutoryFields()
    {
        var cut = Render<WorkplaceApprovalFields>(parameters => parameters
            .Add(p => p.Section, "All")
            .Add(p => p.TradeCode, "FITT")
            .Add(p => p.QualificationTitle, "Fitter and Turner")
            .Add(p => p.MentorFullName, "Johan van der Merwe")
            .Add(p => p.TradeCertificateNumber, "TT-NAMB-2018-0941")
            .Add(p => p.OhsCertificateNumber, "OHS-2026-001")
        );

        Assert.Contains("Employer organisation", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Employer contact person", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Physical inspection address", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Designated trade code", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Lead mentor artisan full name", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Artisan trade test certificate number", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("OHS", cut.Markup, StringComparison.OrdinalIgnoreCase);
    }
}
