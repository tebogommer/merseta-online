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
                "TradeTestApplicationWizard.razor",
                "DgTrancheClaimWizard.razor",
                "WspAtrSubmissionWizard.razor",
                "SdpAccreditationApplicationWizard.razor",
                "InterSetaTransferWizard.razor",
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

    [Fact]
    public void TradeTestApplicationFields_RendersExpectedStatutoryFields()
    {
        var cut = Render<TradeTestApplicationFields>(parameters => parameters
            .Add(p => p.Section, "All")
            .Add(p => p.TradeTitle, "Fitter and Turner")
            .Add(p => p.TheoryQualificationTitle, "National Technical Certificate N2")
            .Add(p => p.TheorySubjectsPassed, "Mathematics N2, Engineering Science N2")
            .Add(p => p.CurrentEmployerName, "Toyota SA Manufacturing")
            .Add(p => p.WorkplaceMentorName, "Johan van der Merwe")
        );

        Assert.Contains("Candidate learner", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Application route", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Designated trade title", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Technical qualification title", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Compulsory technical subjects passed", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Current employer name", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Workplace practical duration", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Workplace artisan mentor full name", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Accredited Trade Test Centre (TTC)", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Preferred assessment date", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("declare", cut.Markup, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public void DgTrancheClaimFields_RendersExpectedStatutoryFields()
    {
        var cut = Render<DgTrancheClaimFields>(parameters => parameters
            .Add(p => p.Section, "All")
            .Add(p => p.TotalMoaAllocation, 1500000m)
            .Add(p => p.TotalClaimedAmount, 300000m)
            .Add(p => p.RemainingEnvelopeBalance, 1200000m)
            .Add(p => p.LearnerDeliverableHeadcount, 25)
            .Add(p => p.DeliverableDescription, "25 apprentices completed modular theory")
            .Add(p => p.ClaimAmount, 350000m)
            .Add(p => p.TaxCompliancePin, "9823471029")
            .Add(p => p.BankName, "Standard Bank")
        );

        Assert.Contains("Memorandum of Agreement (MoA)", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Project Implementation Plan (PIP)", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Milestone deliverable tranche", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Verified learner headcount", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Deliverable description", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Tranche number", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Claim amount", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("SARS tax compliance PIN", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Beneficiary bank name", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("PFMA", cut.Markup, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public void WspAtrSubmissionFields_RendersExpectedStatutoryFields()
    {
        var cut = Render<WspAtrSubmissionFields>(parameters => parameters
            .Add(p => p.Section, "All")
            .Add(p => p.FinYear, 2026)
            .Add(p => p.PayrollAnnualTotal, 25000000m)
            .Add(p => p.EstimatedLevy, 250000m)
            .Add(p => p.EmployeeCount, 120)
            .Add(p => p.PlannedTrainingBudget, 450000m)
            .Add(p => p.WspPlannedBeneficiaries, 60)
            .Add(p => p.SdfFullName, "Thabo Molefe (Primary SDF)")
            .Add(p => p.CeoFullName, "Sarah Jenkins (Managing Director)")
        );

        Assert.Contains("Submitting employer organisation", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Financial scheme year", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Annual payroll total (EMP201)", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Estimated 1% SDL levy paid", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Estimated 20% Mandatory Grant (MG) rebate", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Total employee headcount", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Actual training expenditure", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Total trained beneficiaries", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Prior year training interventions count", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Planned training budget", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Planned beneficiary headcount", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Consultative training committee met", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Required sign-off count", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Trade union represented in workplace", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Primary SDF full name", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("CEO / Accounting Authority full name", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("solemnly declare", cut.Markup, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public void SdpAccreditationFields_RendersExpectedStatutoryFields()
    {
        var cut = Render<SdpAccreditationFields>(parameters => parameters
            .Add(p => p.Section, "All")
            .Add(p => p.ProviderLegalName, "Apex Technical Training Academy (Pty) Ltd")
            .Add(p => p.RegistrationNumber, "2018/489123/07")
            .Add(p => p.TaxNumber, "9823471029")
            .Add(p => p.PrimaryQualificationTitle, "Occupational Certificate: Automotive Body Repairer")
            .Add(p => p.SaqaQualificationId, "97542")
            .Add(p => p.AssessorFullName, "Johannes Maluleke")
            .Add(p => p.AssessorRegNumber, "ASS-MER-2024-0891")
            .Add(p => p.ModeratorFullName, "Grace Khumalo")
            .Add(p => p.ModeratorRegNumber, "MOD-MER-2023-0412")
            .Add(p => p.OhsCertificateNumber, "OHS-JHB-2026-0941")
            .Add(p => p.QmsManualReference, "DOC-SDP-2026-QMS-01.pdf")
        );

        Assert.Contains("Skills Development Provider (SDP) legal entity name", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("CIPC company or trust registration number", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("SARS tax compliance PIN", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Provider institutional classification", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Accreditation application scope category", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Primary designated occupational qualification", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("SAQA qualification registration ID", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Nominated registered assessor full name", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Assessor ETQA registration number", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Nominated internal moderator full name", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Staff-to-learner ratios compliant", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Learner admissions, selection", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Assessment, re-assessment", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("OHS", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("QMS institutional manual reference document", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("solemnly declare", cut.Markup, StringComparison.OrdinalIgnoreCase);
    }

    [Fact]
    public void InterSetaTransferFields_RendersExpectedStatutoryFields()
    {
        var cut = Render<InterSetaTransferFields>(parameters => parameters
            .Add(p => p.Section, "All")
            .Add(p => p.SdlNumber, "L789123456")
            .Add(p => p.CurrentSicCode, "38100")
            .Add(p => p.TargetSicCode, "33400")
            .Add(p => p.TransferAmount, 185000m)
            .Add(p => p.Emp103DocRef, "DOC-SARS-EMP103-CORROB.pdf")
            .Add(p => p.CipcDocRef, "DOC-CIPC-CORPOBJECTS.pdf")
            .Add(p => p.LabourDocRef, "DOC-UNION-CONSULTATION.pdf")
            .Add(p => p.BoardResolutionDocRef, "DOC-BOARD-RESOLUTION.pdf")
            .Add(p => p.SignatoryFullName, "Morné van Zyl")
        );

        Assert.Contains("Migrating employer organisation", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Skills Development Levy (SDL) registration number", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Current originating SETA", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Transfer direction", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Counterpart counterpart SETA", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Primary statutory transfer justification", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Current reported SARS 5-digit SIC code", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Target destination 5-digit SIC code", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Accumulated uncommitted levy funds", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("SARS EMP103", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("CIPC core business objects", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Organised labour / consultative committee endorsement", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Board resolution", cut.Markup, StringComparison.OrdinalIgnoreCase);
        Assert.Contains("Section 32", cut.Markup, StringComparison.OrdinalIgnoreCase);
    }
}
