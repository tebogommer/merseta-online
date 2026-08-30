using Microsoft.EntityFrameworkCore;
using Nsdms.Domain.Entities;

namespace Nsdms.Infrastructure.Data;

public static class SampleDataSeeder
{
    public static async Task SeedSampleDataAsync(NsdmsDbContext db)
    {
        // ==========================================
        // 1. SEED PEOPLE & DEMOGRAPHICS
        // ==========================================
        if (!await db.People.AnyAsync())
        {
            var p1 = new Person
            {
                Title = "Mr",
                FirstName = "Sipho",
                MiddleName = "Themba",
                LastName = "Khumalo",
                RsaIdNumber = "8001015009087",
                DateOfBirth = new DateTime(1980, 1, 1),
                GenderCode = "M",
                EquityCode = "BA",
                DisabilityCode = "NONE",
                NationalityCode = "ZAF",
                HomeLanguageCode = "ZUL",
                ProvinceCode = "GP",
                Email = "sipho.khumalo@merietasamples.co.za",
                CellNumber = "0821234567",
                PhysicalAddress = "124 Metal Industries Way, Johannesburg",
                PostalAddress = "PO Box 1024, Johannesburg 2000",
                IsActive = true
            };

            var p2 = new Person
            {
                Title = "Ms",
                FirstName = "Nalini",
                MiddleName = "Priya",
                LastName = "Moodley",
                RsaIdNumber = "8505120058089",
                DateOfBirth = new DateTime(1985, 5, 12),
                GenderCode = "F",
                EquityCode = "BI",
                DisabilityCode = "NONE",
                NationalityCode = "ZAF",
                HomeLanguageCode = "ENG",
                ProvinceCode = "KZN",
                Email = "nalini.moodley@merietasamples.co.za",
                CellNumber = "0832345678",
                PhysicalAddress = "45 Prospecton Road, Durban South",
                PostalAddress = "PO Box 542, Durban 4000",
                IsActive = true
            };

            var p3 = new Person
            {
                Title = "Dr",
                FirstName = "Johan",
                LastName = "van der Merwe",
                RsaIdNumber = "7811205012085",
                DateOfBirth = new DateTime(1978, 11, 20),
                GenderCode = "M",
                EquityCode = "WH",
                DisabilityCode = "NONE",
                NationalityCode = "ZAF",
                HomeLanguageCode = "AFR",
                ProvinceCode = "GP",
                Email = "johan.vdm@merietasamples.co.za",
                CellNumber = "0843456789",
                PhysicalAddress = "88 Innovation Boulevard, Centurion",
                PostalAddress = "Private Bag X10, Centurion 0046",
                IsActive = true
            };

            var p4 = new Person
            {
                Title = "Mr",
                FirstName = "Thabo",
                LastName = "Molefe",
                RsaIdNumber = "9807220145082",
                DateOfBirth = new DateTime(1998, 7, 22),
                GenderCode = "M",
                EquityCode = "BA",
                DisabilityCode = "NONE",
                NationalityCode = "ZAF",
                HomeLanguageCode = "SOT",
                ProvinceCode = "MP",
                Email = "thabo.molefe@sasolsample.co.za",
                CellNumber = "0714567890",
                PhysicalAddress = "10 Refinery Road, Secunda",
                PostalAddress = "PO Box 1, Secunda 2302",
                IsActive = true
            };

            var p5 = new Person
            {
                Title = "Ms",
                FirstName = "Fatima",
                LastName = "Adams",
                RsaIdNumber = "0102145896081",
                DateOfBirth = new DateTime(2001, 2, 14),
                GenderCode = "F",
                EquityCode = "BC",
                DisabilityCode = "NONE",
                NationalityCode = "ZAF",
                HomeLanguageCode = "ENG",
                ProvinceCode = "WC",
                Email = "fatima.adams@toyotasample.co.za",
                CellNumber = "0725678901",
                PhysicalAddress = "18 Voortrekker Road, Bellville",
                PostalAddress = "PO Box 230, Bellville 7535",
                IsActive = true
            };

            var p6 = new Person
            {
                Title = "Mr",
                FirstName = "Lunga",
                LastName = "Dlamini",
                RsaIdNumber = "0309190123084",
                DateOfBirth = new DateTime(2003, 9, 19),
                GenderCode = "M",
                EquityCode = "BA",
                DisabilityCode = "NONE",
                NationalityCode = "ZAF",
                HomeLanguageCode = "ZUL",
                ProvinceCode = "GP",
                Email = "lunga.dlamini@learners.org.za",
                CellNumber = "0736789012",
                PhysicalAddress = "55 Seta Avenue, Germiston",
                PostalAddress = "PO Box 99, Germiston 1400",
                IsActive = true
            };

            var p7 = new Person
            {
                Title = "Mr",
                FirstName = "Bongani",
                LastName = "Mabaso",
                RsaIdNumber = "9203155124083",
                DateOfBirth = new DateTime(1992, 3, 15),
                GenderCode = "M",
                EquityCode = "BA",
                DisabilityCode = "NONE",
                NationalityCode = "ZAF",
                HomeLanguageCode = "ZUL",
                ProvinceCode = "KZN",
                Email = "bongani.mabaso@assessors.org.za",
                CellNumber = "0817890123",
                PhysicalAddress = "12 Industrial Road, Pinetown",
                PostalAddress = "PO Box 400, Pinetown 3600",
                IsActive = true
            };

            db.People.AddRange(p1, p2, p3, p4, p5, p6, p7);
            await db.SaveChangesAsync();
        }

        var allPeople = await db.People.ToListAsync();
        var pSipho = allPeople.FirstOrDefault(p => p.RsaIdNumber == "8001015009087") ?? allPeople.First();
        var pNalini = allPeople.FirstOrDefault(p => p.RsaIdNumber == "8505120058089") ?? allPeople.First();
        var pJohan = allPeople.FirstOrDefault(p => p.RsaIdNumber == "7811205012085") ?? allPeople.First();
        var pThabo = allPeople.FirstOrDefault(p => p.RsaIdNumber == "9807220145082") ?? allPeople.First();
        var pFatima = allPeople.FirstOrDefault(p => p.RsaIdNumber == "0102145896081") ?? allPeople.First();
        var pLunga = allPeople.FirstOrDefault(p => p.RsaIdNumber == "0309190123084") ?? allPeople.First();
        var pBongani = allPeople.FirstOrDefault(p => p.RsaIdNumber == "9203155124083") ?? allPeople.First();

        // ==========================================
        // 2. SEED ORGANISATIONS / EMPLOYERS
        // ==========================================
        if (!await db.Organisations.AnyAsync())
        {
            var orgToyota = new Organisation
            {
                CompanyName = "Toyota South Africa Motors (Pty) Ltd",
                TradingName = "Toyota SA",
                SdlNumber = "L700100200",
                RegistrationNumber = "1961/001767/07",
                TaxNumber = "9012345678",
                CategoryCode = "EMP",
                StatusCode = "ACTIVE",
                ProvinceCode = "KZN",
                SectorCode = "AUTO",
                ChamberCode = "CHAMBER_1",
                SicCode = "38100",
                CompanySizeCode = "LARGE",
                OrganisationTypeCode = "CORP",
                BankName = "Standard Bank",
                BankBranchCode = "051001",
                BankAccountNumber = "1098765432",
                BankAccountType = "Current",
                BankingDetailsVerified = true,
                PrimaryContactPersonId = pNalini.Id,
                IsActive = true
            };

            var orgSasol = new Organisation
            {
                CompanyName = "Sasol South Africa Limited",
                TradingName = "Sasol Synfuels",
                SdlNumber = "L800200300",
                RegistrationNumber = "2006/018905/06",
                TaxNumber = "9123456789",
                CategoryCode = "EMP",
                StatusCode = "ACTIVE",
                ProvinceCode = "MP",
                SectorCode = "PLAST",
                ChamberCode = "CHAMBER_2",
                SicCode = "33200",
                CompanySizeCode = "LARGE",
                OrganisationTypeCode = "CORP",
                BankName = "Nedbank",
                BankBranchCode = "198765",
                BankAccountNumber = "2098765432",
                BankAccountType = "Current",
                BankingDetailsVerified = true,
                PrimaryContactPersonId = pThabo.Id,
                IsActive = true
            };

            var orgDenel = new Organisation
            {
                CompanyName = "Denel Dynamics SOC Ltd",
                TradingName = "Denel Aerospace Systems",
                SdlNumber = "L900300400",
                RegistrationNumber = "1992/001337/30",
                TaxNumber = "9234567890",
                CategoryCode = "EMP",
                StatusCode = "ACTIVE",
                ProvinceCode = "GP",
                SectorCode = "METAL",
                ChamberCode = "CHAMBER_3",
                SicCode = "38400",
                CompanySizeCode = "LARGE",
                OrganisationTypeCode = "SOE",
                BankName = "ABSA",
                BankBranchCode = "632005",
                BankAccountNumber = "3098765432",
                BankAccountType = "Current",
                BankingDetailsVerified = true,
                PrimaryContactPersonId = pJohan.Id,
                IsActive = true
            };

            var orgArcelor = new Organisation
            {
                CompanyName = "ArcelorMittal South Africa Limited",
                TradingName = "ArcelorMittal Steel",
                SdlNumber = "L100400500",
                RegistrationNumber = "1989/002164/06",
                TaxNumber = "9345678901",
                CategoryCode = "EMP",
                StatusCode = "ACTIVE",
                ProvinceCode = "GP",
                SectorCode = "METAL",
                ChamberCode = "CHAMBER_3",
                SicCode = "35100",
                CompanySizeCode = "LARGE",
                OrganisationTypeCode = "CORP",
                BankName = "First National Bank",
                BankBranchCode = "250655",
                BankAccountNumber = "4098765432",
                BankAccountType = "Current",
                BankingDetailsVerified = true,
                PrimaryContactPersonId = pSipho.Id,
                IsActive = true
            };

            var orgFesto = new Organisation
            {
                CompanyName = "Festo Didactic South Africa (Pty) Ltd",
                TradingName = "Festo Training Institute",
                SdlNumber = "L200500600",
                RegistrationNumber = "1973/008892/07",
                TaxNumber = "9456789012",
                CategoryCode = "SDP",
                StatusCode = "ACTIVE",
                ProvinceCode = "GP",
                SectorCode = "AUTO",
                ChamberCode = "CHAMBER_4",
                SicCode = "38200",
                CompanySizeCode = "MEDIUM",
                OrganisationTypeCode = "PRIV_PROV",
                BankName = "Standard Bank",
                BankBranchCode = "051001",
                BankAccountNumber = "5098765432",
                BankAccountType = "Current",
                BankingDetailsVerified = true,
                PrimaryContactPersonId = pSipho.Id,
                IsActive = true
            };

            db.Organisations.AddRange(orgToyota, orgSasol, orgDenel, orgArcelor, orgFesto);
            await db.SaveChangesAsync();

            // Add Organisation Contacts & Sites
            var contactToyota = new OrganisationContact
            {
                OrganisationId = orgToyota.Id,
                PersonId = pNalini.Id,
                ContactTypeCode = "Primary_SDF",
                Designation = "Human Resources & Skills Manager",
                IsPrimary = true,
                IsActive = true
            };

            var siteToyota = new OrganisationSite
            {
                OrganisationId = orgToyota.Id,
                SiteName = "Prospecton Assembly Plant",
                SiteCode = "PROS-01",
                PhysicalAddress = "Prospecton Industrial Area, Durban",
                ProvinceCode = "KZN",
                City = "Durban",
                PostalCode = "4110",
                PrimaryContactPersonId = pNalini.Id,
                IsHeadOffice = true,
                IsActive = true
            };

            var contactSasol = new OrganisationContact
            {
                OrganisationId = orgSasol.Id,
                PersonId = pThabo.Id,
                ContactTypeCode = "Primary_SDF",
                Designation = "Technical Training Lead",
                IsPrimary = true,
                IsActive = true
            };

            var siteSasol = new OrganisationSite
            {
                OrganisationId = orgSasol.Id,
                SiteName = "Secunda Operations",
                SiteCode = "SEC-01",
                PhysicalAddress = "Secunda Complex, Mpumalanga",
                ProvinceCode = "MP",
                City = "Secunda",
                PostalCode = "2302",
                PrimaryContactPersonId = pThabo.Id,
                IsHeadOffice = true,
                IsActive = true
            };

            db.OrganisationContacts.AddRange(contactToyota, contactSasol);
            db.OrganisationSites.AddRange(siteToyota, siteSasol);
            await db.SaveChangesAsync();
        }

        var allOrgs = await db.Organisations.ToListAsync();
        var orgToyotaDb = allOrgs.FirstOrDefault(o => o.SdlNumber == "L700100200") ?? allOrgs.First();
        var orgSasolDb = allOrgs.FirstOrDefault(o => o.SdlNumber == "L800200300") ?? allOrgs.First();
        var orgFestoDb = allOrgs.FirstOrDefault(o => o.SdlNumber == "L200500600") ?? allOrgs.First();

        // ==========================================
        // 3. SEED SKILLS DEVELOPMENT PROVIDERS (SDP)
        // ==========================================
        if (!await db.TrainingProviders.AnyAsync())
        {
            var providerFesto = new TrainingProvider
            {
                OrganisationId = orgFestoDb.Id,
                AccreditationNumber = "ACC-2024-003",
                AccreditationStartDate = new DateTime(2024, 1, 1),
                AccreditationEndDate = new DateTime(2029, 12, 31),
                ProviderTypeCode = "Private",
                ProviderStatusCode = "Accredited",
                EtqaDecisionNumber = "ETQA-2024-DEC-882",
                MaxLearnerCapacity = 250,
                PrimaryContactPersonId = pSipho.Id,
                IsActive = true
            };

            db.TrainingProviders.Add(providerFesto);
            await db.SaveChangesAsync();

            var qualFesto1 = new TrainingProviderQualification
            {
                TrainingProviderId = providerFesto.Id,
                SaqaQualificationId = 65409,
                QualificationTitle = "National Certificate: Mechatronics",
                NqfLevel = 5,
                AccreditationStatusCode = "Accredited",
                ExpiryDate = new DateTime(2029, 12, 31)
            };

            var qualFesto2 = new TrainingProviderQualification
            {
                TrainingProviderId = providerFesto.Id,
                SaqaQualificationId = 48878,
                QualificationTitle = "National Certificate: Industrial Automation & Control",
                NqfLevel = 4,
                AccreditationStatusCode = "Accredited",
                ExpiryDate = new DateTime(2029, 12, 31)
            };

            var usFesto1 = new TrainingProviderUnitStandard
            {
                TrainingProviderId = providerFesto.Id,
                UnitStandardId = 119744,
                UnitStandardTitle = "Select, construct and test programmable logic controllers (PLCs)",
                NqfLevel = 4,
                Credits = 12
            };

            db.TrainingProviderQualifications.AddRange(qualFesto1, qualFesto2);
            db.TrainingProviderUnitStandards.Add(usFesto1);
            await db.SaveChangesAsync();
        }

        var providerFestoDb = await db.TrainingProviders.FirstOrDefaultAsync() ?? new TrainingProvider { Id = 1 };

        // ==========================================
        // 4. SEED WSP & MANDATORY GRANTS
        // ==========================================
        if (!await db.WspSubmissions.AnyAsync())
        {
            var wspToyota = new WspSubmission
            {
                OrganisationId = orgToyotaDb.Id,
                FinYear = 2026,
                ReferenceNumber = "WSP-2026-TOYOTA-001",
                StatusCode = "Submitted",
                SubmissionDate = new DateTime(2026, 4, 28),
                PlannedTrainingBudget = 14500000m,
                EmployeeCount = 3850
            };

            var wspSasol = new WspSubmission
            {
                OrganisationId = orgSasolDb.Id,
                FinYear = 2026,
                ReferenceNumber = "WSP-2026-SASOL-001",
                StatusCode = "Approved",
                SubmissionDate = new DateTime(2026, 4, 25),
                PlannedTrainingBudget = 28000000m,
                EmployeeCount = 8200
            };

            db.WspSubmissions.AddRange(wspToyota, wspSasol);
            await db.SaveChangesAsync();

            var empToyota1 = new WspEmploymentSummary
            {
                WspSubmissionId = wspToyota.Id,
                OfoCode = "671201",
                OccupationalCategory = "Craft and Related Trades Workers",
                MaleAfrican = 850,
                FemaleAfrican = 320,
                MaleColoured = 210,
                FemaleColoured = 90,
                MaleIndian = 180,
                FemaleIndian = 75,
                MaleWhite = 95,
                FemaleWhite = 30,
                DisabledCount = 45,
                TotalEmployees = 1850
            };

            var empSasol1 = new WspEmploymentSummary
            {
                WspSubmissionId = wspSasol.Id,
                OfoCode = "671202",
                OccupationalCategory = "Plant and Machine Operators",
                MaleAfrican = 1200,
                FemaleAfrican = 450,
                MaleColoured = 300,
                FemaleColoured = 120,
                MaleIndian = 150,
                FemaleIndian = 60,
                MaleWhite = 220,
                FemaleWhite = 80,
                DisabledCount = 65,
                TotalEmployees = 2580
            };

            var planToyota1 = new WspTrainingPlan
            {
                WspSubmissionId = wspToyota.Id,
                ProgrammeTypeCode = "Apprenticeship",
                NqfLevel = 4,
                BeneficiaryCount = 120,
                EstimatedCost = 4800000m
            };

            var planSasol1 = new WspTrainingPlan
            {
                WspSubmissionId = wspSasol.Id,
                ProgrammeTypeCode = "Learnership",
                NqfLevel = 5,
                BeneficiaryCount = 200,
                EstimatedCost = 7200000m
            };

            db.WspEmploymentSummaries.AddRange(empToyota1, empSasol1);
            db.WspTrainingPlans.AddRange(planToyota1, planSasol1);
            await db.SaveChangesAsync();
        }

        // ==========================================
        // 5. SEED GRANT FUNDING WINDOWS & APPLICATIONS
        // ==========================================
        if (!await db.GrantFundingWindows.AnyAsync())
        {
            var windowDG = new GrantFundingWindow
            {
                FinYear = 2026,
                WindowName = "Discretionary Grant Funding Window 1 - 2026/27",
                GrantTypeCode = "Discretionary",
                OpeningDate = new DateTime(2026, 4, 1),
                ClosingDate = new DateTime(2026, 7, 31),
                TotalAvailableBudget = 45000000m,
                IsActive = true
            };

            db.GrantFundingWindows.Add(windowDG);
            await db.SaveChangesAsync();

            var dgToyota = new GrantApplication
            {
                OrganisationId = orgToyotaDb.Id,
                FundingWindowId = windowDG.Id,
                ApplicationNumber = "DG-2026-TOYOTA-01",
                GrantTypeCode = "Discretionary",
                StatusCode = "Approved",
                RequestedAmount = 7500000m,
                ApprovedAmount = 7500000m,
                ApplicationDate = new DateTime(2026, 5, 10),
                ProjectTitle = "Automotive Artisan Development & Battery Tech Learnership"
            };

            var dgSasol = new GrantApplication
            {
                OrganisationId = orgSasolDb.Id,
                FundingWindowId = windowDG.Id,
                ApplicationNumber = "DG-2026-SASOL-01",
                GrantTypeCode = "Discretionary",
                StatusCode = "UnderReview",
                RequestedAmount = 12000000m,
                ApprovedAmount = null,
                ApplicationDate = new DateTime(2026, 5, 15),
                ProjectTitle = "High-Pressure Welding & Fitting Apprenticeship Programme"
            };

            db.GrantApplications.AddRange(dgToyota, dgSasol);
            await db.SaveChangesAsync();

            var budgetToyota1 = new GrantProjectBudget
            {
                GrantApplicationId = dgToyota.Id,
                ExpenseCategory = "Learner Stipends",
                Description = "Monthly stipends for 50 apprentices for 12 months",
                UnitCost = 7500m,
                Quantity = 600,
                TotalCost = 4500000m
            };

            var budgetToyota2 = new GrantProjectBudget
            {
                GrantApplicationId = dgToyota.Id,
                ExpenseCategory = "Institutional Training",
                Description = "Accredited TVET College tuition and trade test fees",
                UnitCost = 60000m,
                Quantity = 50,
                TotalCost = 3000000m
            };

            db.GrantProjectBudgets.AddRange(budgetToyota1, budgetToyota2);
            await db.SaveChangesAsync();
        }

        // ==========================================
        // 6. SEED SARS LEVY FILES & MONTHLY FINANCE
        // ==========================================
        if (!await db.LevyFiles.AnyAsync())
        {
            var levyFile = new LevyFile
            {
                FileName = "SARS_LEVY_MONTHLY_2026_06.TXT",
                FileRef = "SARS-202606-MERSETA",
                ImportDate = new DateTime(2026, 6, 30),
                TotalRecords = 4,
                TotalAmount = 250000.00m,
                StatusCode = "Reconciled"
            };

            db.LevyFiles.Add(levyFile);
            await db.SaveChangesAsync();

            var line1 = new LevyFileLine
            {
                LevyFileId = levyFile.Id,
                SdlNumber = "L700100200", // Toyota
                SchemeYear = "2026",
                MandatoryLevyAmount = 20000.00m,
                DiscretionaryLevyAmount = 49500.00m,
                AdminLevyAmount = 10500.00m,
                QctoLevyAmount = 500.00m,
                InterestAmount = 0m,
                PenaltyAmount = 0m,
                TotalLevyAmount = 100000.00m,
                IsReconciled = true
            };

            var line2 = new LevyFileLine
            {
                LevyFileId = levyFile.Id,
                SdlNumber = "L800200300", // Sasol
                SchemeYear = "2026",
                MandatoryLevyAmount = 30000.00m,
                DiscretionaryLevyAmount = 74250.00m,
                AdminLevyAmount = 15750.00m,
                QctoLevyAmount = 750.00m,
                InterestAmount = 0m,
                PenaltyAmount = 0m,
                TotalLevyAmount = 150000.00m,
                IsReconciled = true
            };

            db.LevyFileLines.AddRange(line1, line2);
            await db.SaveChangesAsync();
        }

        // ==========================================
        // 7. SEED ETQA ASSESSORS & MODERATORS
        // ==========================================
        if (!await db.EtqaAssessors.AnyAsync())
        {
            var assessorBongani = new EtqaAssessor
            {
                PersonId = pBongani.Id,
                RegistrationNumber = "ASM-2024-KZN-088",
                EtqaRole = "Assessor",
                StatusCode = "Registered",
                StartDate = new DateTime(2024, 1, 1),
                EndDate = new DateTime(2027, 1, 1),
                IsActive = true
            };

            var moderatorJohan = new EtqaAssessor
            {
                PersonId = pJohan.Id,
                RegistrationNumber = "MOD-2023-GP-014",
                EtqaRole = "Moderator",
                StatusCode = "Registered",
                StartDate = new DateTime(2023, 6, 1),
                EndDate = new DateTime(2026, 6, 1),
                IsActive = true
            };

            db.EtqaAssessors.AddRange(assessorBongani, moderatorJohan);
            await db.SaveChangesAsync();

            var scopeBongani = new AssessorModeratorScope
            {
                EtqaAssessorId = assessorBongani.Id,
                SaqaQualificationId = 65409,
                QualificationTitle = "National Certificate: Automotive Motor Mechanic",
                RegistrationStatusCode = "Approved",
                ExpiryDate = new DateTime(2027, 1, 1)
            };

            db.AssessorModeratorScopes.Add(scopeBongani);
            await db.SaveChangesAsync();
        }

        // ==========================================
        // 8. SEED WORKPLACE APPROVALS (WPA) & MENTORS
        // ==========================================
        if (!await db.WorkplaceApprovals.AnyAsync())
        {
            var siteToyotaDb = await db.OrganisationSites.FirstOrDefaultAsync(s => s.OrganisationId == orgToyotaDb.Id);

            var wpaToyota = new WorkplaceApproval
            {
                OrganisationId = orgToyotaDb.Id,
                OrganisationSiteId = siteToyotaDb?.Id,
                SaqaQualificationId = 65409,
                QualificationTitle = "Automotive Motor Mechanic (Trade Test Ready)",
                ApprovalNumber = "WPA-2025-TOYOTA-PROS",
                ApprovalStatusCode = "Approved",
                InspectionDate = new DateTime(2025, 2, 10),
                ApprovalDate = new DateTime(2025, 2, 15),
                ExpiryDate = new DateTime(2028, 2, 15),
                AssessorPersonId = pBongani.Id,
                Recommendations = "Site fully compliant with state of the art tooling and designated master artisans.",
                IsActive = true
            };

            db.WorkplaceApprovals.Add(wpaToyota);
            await db.SaveChangesAsync();

            var mentor1 = new WorkplaceApprovalMentor
            {
                WorkplaceApprovalId = wpaToyota.Id,
                PersonId = pSipho.Id,
                Designation = "Master Automotive Artisan & Workshop Lead",
                ArtisanTradeNumber = "ART-1999-88741",
                YearsExperience = 18,
                IsCertifiedArtisan = true,
                IsActive = true
            };

            var tool1 = new WorkplaceApprovalToolList
            {
                WorkplaceApprovalId = wpaToyota.Id,
                ToolName = "Hydraulic Vehicle Lift (4-Post)",
                Category = "Mechanical",
                RequiredQuantity = 4,
                AvailableQuantity = 6,
                Remarks = "Certified & Inspected 2026"
            };

            var tool2 = new WorkplaceApprovalToolList
            {
                WorkplaceApprovalId = wpaToyota.Id,
                ToolName = "OBD-II Diagnostic Engine Scanners",
                Category = "Electrical",
                RequiredQuantity = 2,
                AvailableQuantity = 4,
                Remarks = "Updated with latest firmware"
            };

            db.WorkplaceApprovalMentors.Add(mentor1);
            db.WorkplaceApprovalToolLists.AddRange(tool1, tool2);
            await db.SaveChangesAsync();
        }

        // ==========================================
        // 9. SEED COMPANY LEARNERS & TRADE TESTING
        // ==========================================
        if (!await db.CompanyLearners.AnyAsync())
        {
            var learnerLunga = new CompanyLearner
            {
                PersonId = pLunga.Id,
                OrganisationId = orgToyotaDb.Id,
                TrainingProviderId = providerFestoDb.Id,
                LearnerContractNumber = "APP-2025-TOYOTA-0042",
                QualificationTitle = "Occupational Certificate: Automotive Motor Mechanic",
                SaqaQualificationId = 65409,
                NqfLevel = 4,
                LearningProgrammeTypeCode = "Apprenticeship",
                FundingTypeCode = "DiscretionaryGrant",
                StatusCode = "InProgress",
                RegistrationDate = new DateTime(2025, 1, 15),
                CommencementDate = new DateTime(2025, 2, 1),
                ExpectedCompletionDate = new DateTime(2027, 1, 31),
                SetaRegion = "KwaZulu-Natal",
                ChamberCode = "CHAMBER_1",
                IsActive = true
            };

            var learnerFatima = new CompanyLearner
            {
                PersonId = pFatima.Id,
                OrganisationId = orgToyotaDb.Id,
                TrainingProviderId = providerFestoDb.Id,
                LearnerContractNumber = "APP-2024-TOYOTA-0018",
                QualificationTitle = "National Certificate: Mechatronics",
                SaqaQualificationId = 65409,
                NqfLevel = 5,
                LearningProgrammeTypeCode = "Learnership",
                FundingTypeCode = "DiscretionaryGrant",
                StatusCode = "Completed",
                RegistrationDate = new DateTime(2024, 1, 10),
                CommencementDate = new DateTime(2024, 2, 1),
                ExpectedCompletionDate = new DateTime(2026, 1, 31),
                CompletionDate = new DateTime(2026, 2, 15),
                SetaRegion = "KwaZulu-Natal",
                ChamberCode = "CHAMBER_1",
                IsActive = true
            };

            db.CompanyLearners.AddRange(learnerLunga, learnerFatima);
            await db.SaveChangesAsync();

            var tradeTestFatima = new LearnerTradeTest
            {
                CompanyLearnerId = learnerFatima.Id,
                TestCenterName = "MerSETA Durban Regional Trade Test Centre",
                TradeTitle = "Automotive Motor Mechanic",
                AttemptNumber = 1,
                TradeTestDate = new DateTime(2026, 2, 10),
                ResultStatusCode = "Competent",
                AssessorPersonId = pBongani.Id,
                ModeratorPersonId = pJohan.Id,
                SerialCertificateNumber = "CERT-2026-TOYOTA-88412",
                CertificateIssueDate = new DateTime(2026, 2, 15),
                Remarks = "Candidate demonstrated exceptional competence in engine timing, electronic diagnostics, and brake systems."
            };

            db.LearnerTradeTests.Add(tradeTestFatima);
            await db.SaveChangesAsync();
        }
    }
}
