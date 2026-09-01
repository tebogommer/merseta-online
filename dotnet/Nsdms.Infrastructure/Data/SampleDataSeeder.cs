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
        // 4b. SEED TRAINING COMMITTEES & WSP DISPUTES
        // ==========================================
        if (!await db.TrainingCommittees.AnyAsync())
        {
            var persons = await db.People.Take(4).ToListAsync();

            if (orgToyotaDb != null && persons.Count >= 2)
            {
                var committeeToyota = new TrainingCommittee
                {
                    OrganisationId = orgToyotaDb.Id,
                    FinancialYear = 2026,
                    CommitteeStatusCode = "Active",
                    ConstitutionalQuorumMet = true,
                    LastMeetingDate = DateTime.UtcNow.AddDays(-14)
                };

                committeeToyota.Members.Add(new TrainingCommitteeMember
                {
                    PersonId = persons[0].Id,
                    MemberRoleCode = "UnionRepresentative",
                    Constituency = "NUMSA",
                    IsActive = true
                });

                committeeToyota.Members.Add(new TrainingCommitteeMember
                {
                    PersonId = persons[1].Id,
                    MemberRoleCode = "EmployerRepresentative",
                    Constituency = "Management",
                    IsActive = true
                });

                db.TrainingCommittees.Add(committeeToyota);
            }

            if (orgSasolDb != null && persons.Count >= 4)
            {
                var committeeSasol = new TrainingCommittee
                {
                    OrganisationId = orgSasolDb.Id,
                    FinancialYear = 2026,
                    CommitteeStatusCode = "Active",
                    ConstitutionalQuorumMet = true,
                    LastMeetingDate = DateTime.UtcNow.AddDays(-28)
                };

                committeeSasol.Members.Add(new TrainingCommitteeMember
                {
                    PersonId = persons[2].Id,
                    MemberRoleCode = "UnionRepresentative",
                    Constituency = "CEPPWAWU",
                    IsActive = true
                });

                committeeSasol.Members.Add(new TrainingCommitteeMember
                {
                    PersonId = persons[3].Id,
                    MemberRoleCode = "EmployerRepresentative",
                    Constituency = "Management",
                    IsActive = true
                });

                db.TrainingCommittees.Add(committeeSasol);
            }

            await db.SaveChangesAsync();
        }

        if (!await db.WspDisputes.AnyAsync())
        {
            var wspToyotaDb = await db.WspSubmissions.FirstOrDefaultAsync(w => w.OrganisationId == (orgToyotaDb != null ? orgToyotaDb.Id : 0));

            if (orgToyotaDb != null)
            {
                db.WspDisputes.Add(new WspDispute
                {
                    OrganisationId = orgToyotaDb.Id,
                    WspSubmissionId = wspToyotaDb?.Id,
                    DisputeReferenceNumber = "DSP-2026-0042",
                    DisputeReasonCode = "UnionRefusalToSign",
                    Description = "NUMSA shop steward dispute regarding consultation over EV battery assembly training plan targets.",
                    DisputeStatusCode = "InvestigationInProgress",
                    CreatedAt = DateTime.UtcNow.AddDays(-10)
                });
            }

            if (orgSasolDb != null)
            {
                db.WspDisputes.Add(new WspDispute
                {
                    OrganisationId = orgSasolDb.Id,
                    DisputeReferenceNumber = "DSP-2026-0089",
                    DisputeReasonCode = "ConsultationFailure",
                    Description = "Failure to convene required consultative committee meeting prior to final WSP submission deadline.",
                    DisputeStatusCode = "Resolved",
                    ResolutionDate = DateTime.UtcNow.AddDays(-3),
                    ResolutionNotes = "Mediation concluded with consensus skills plan signatures and updated training schedule.",
                    CreatedAt = DateTime.UtcNow.AddDays(-20)
                });
            }

            await db.SaveChangesAsync();
        }

        // ==========================================
        // 5. SEED STRATEGIC PRIORITIES & FUNDING WINDOWS
        // ==========================================
        if (!await db.StrategicPriorities.AnyAsync())
        {
            var spGreen = new StrategicPriority
            {
                Code = "SP-GREEN-01",
                Name = "Green Economy, EV & Battery Technologies",
                Description = "Developing specialized skills in electric vehicle powertrains, lithium-ion battery manufacturing, solar-PV microgrid integration, and industrial circular economy.",
                NsdpOutcomeCode = "NSDP-OUTCOME-1",
                NsdpOutcomeDescription = "Outcome 1: Identify and increase production of occupations in high demand",
                SipCategory = "SIP 8: Green Energy Transmission",
                TargetSector = "Automotive & Clean Energy",
                IsActive = true
            };

            var sp4IR = new StrategicPriority
            {
                Code = "SP-4IR-02",
                Name = "Digital Transformation, Robotics & 4IR Automation",
                Description = "Upskilling the engineering workforce in robotic welding, PLC automation, digital twin simulations, CNC precision machining, and IoT industrial telemetry.",
                NsdpOutcomeCode = "NSDP-OUTCOME-2",
                NsdpOutcomeDescription = "Outcome 2: Linking education and the workplace with modern technical infrastructure",
                SipCategory = "SIP 15: Communication & Digital Infrastructure",
                TargetSector = "Metal & Engineering",
                IsActive = true
            };

            var spArtisan = new StrategicPriority
            {
                Code = "SP-ARTISAN-03",
                Name = "National Artisan Development & Apprenticeship Acceleration",
                Description = "Rapid development of Red Seal certified artisans across foundational trades (Millwright, Electrician, Fitter and Turner, Boilermaker, Automotive Motor Mechanic).",
                NsdpOutcomeCode = "NSDP-OUTCOME-1",
                NsdpOutcomeDescription = "Outcome 1: Increase production of certified artisans to reach national targets",
                SipCategory = "SIP 2: Durban-Free State-Gauteng Logistics Corridor",
                TargetSector = "All Sectors",
                IsActive = true
            };

            var spSmme = new StrategicPriority
            {
                Code = "SP-SMME-04",
                Name = "SMME Incubation & Rural Engineering Development",
                Description = "Empowering small, medium, micro-enterprises and community cooperatives through subsidized artisan apprenticeships, toolkits, and incubation vouchers.",
                NsdpOutcomeCode = "NSDP-OUTCOME-5",
                NsdpOutcomeDescription = "Outcome 5: Support entrepreneurship and cooperative development across rural corridors",
                SipCategory = "SIP 11: Agri-Logistics and Rural Infrastructure",
                TargetSector = "Plastics & Metal Fabrication",
                IsActive = true
            };

            var spBursary = new StrategicPriority
            {
                Code = "SP-BURSARY-05",
                Name = "High-End Engineering Bursaries & Applied R&D",
                Description = "Full bursary funding for BEng/BSc Mechatronics, Metallurgy, Materials Science, and Aeronautical Engineering students at public universities.",
                NsdpOutcomeCode = "NSDP-OUTCOME-4",
                NsdpOutcomeDescription = "Outcome 4: Increase access for high-level occupations in high demand",
                SipCategory = "SIP 14: Higher Education Infrastructure",
                TargetSector = "All Sectors",
                IsActive = true
            };

            db.StrategicPriorities.AddRange(spGreen, sp4IR, spArtisan, spSmme, spBursary);
            await db.SaveChangesAsync();
        }

        if (!await db.GrantFundingWindows.AnyAsync())
        {
            var spList = await db.StrategicPriorities.ToListAsync();
            var spGreenDb = spList.First(s => s.Code == "SP-GREEN-01");
            var sp4IRDb = spList.First(s => s.Code == "SP-4IR-02");
            var spArtisanDb = spList.First(s => s.Code == "SP-ARTISAN-03");
            var spSmmeDb = spList.First(s => s.Code == "SP-SMME-04");

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

            var fwp1 = new FundingWindowPriority
            {
                FundingWindowId = windowDG.Id,
                StrategicPriorityId = spGreenDb.Id,
                AllocatedBudget = 15000000m,
                TargetBeneficiaries = 200,
                MinScoreThreshold = 70.00m,
                IsRingFenced = true,
                IsActive = true
            };

            var fwp2 = new FundingWindowPriority
            {
                FundingWindowId = windowDG.Id,
                StrategicPriorityId = sp4IRDb.Id,
                AllocatedBudget = 15000000m,
                TargetBeneficiaries = 180,
                MinScoreThreshold = 65.00m,
                IsRingFenced = false,
                IsActive = true
            };

            var fwp3 = new FundingWindowPriority
            {
                FundingWindowId = windowDG.Id,
                StrategicPriorityId = spArtisanDb.Id,
                AllocatedBudget = 10000000m,
                TargetBeneficiaries = 150,
                MinScoreThreshold = 65.00m,
                IsRingFenced = true,
                IsActive = true
            };

            var fwp4 = new FundingWindowPriority
            {
                FundingWindowId = windowDG.Id,
                StrategicPriorityId = spSmmeDb.Id,
                AllocatedBudget = 5000000m,
                TargetBeneficiaries = 100,
                MinScoreThreshold = 60.00m,
                IsRingFenced = false,
                IsActive = true
            };

            db.FundingWindowPriorities.AddRange(fwp1, fwp2, fwp3, fwp4);
            await db.SaveChangesAsync();

            var dgToyota = new GrantApplication
            {
                OrganisationId = orgToyotaDb.Id,
                FundingWindowId = windowDG.Id,
                StrategicPriorityId = spGreenDb.Id,
                FundingWindowPriorityId = fwp1.Id,
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
                StrategicPriorityId = spArtisanDb.Id,
                FundingWindowPriorityId = fwp3.Id,
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
                StrategicPriorityId = spGreenDb.Id,
                ExpenseCategory = "Learner Stipends",
                Description = "Monthly stipends for 50 apprentices for 12 months",
                UnitCost = 7500m,
                Quantity = 600,
                TotalCost = 4500000m
            };

            var budgetToyota2 = new GrantProjectBudget
            {
                GrantApplicationId = dgToyota.Id,
                StrategicPriorityId = spGreenDb.Id,
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
        // 5b. SEED DG PROJECT IMPLEMENTATION PLANS (PIP)
        // ==========================================
        if (!await db.ProjectImplementationPlans.AnyAsync())
        {
            var dgToyotaDb = await db.GrantApplications.FirstOrDefaultAsync(g => g.OrganisationId == orgToyotaDb.Id);
            var dgSasolDb = await db.GrantApplications.FirstOrDefaultAsync(g => g.OrganisationId == orgSasolDb.Id);
            var windowDb = await db.GrantFundingWindows.FirstOrDefaultAsync();

            var pipToyota = new ProjectImplementationPlan
            {
                OrganisationId = orgToyotaDb.Id,
                FundingWindowId = windowDb?.Id,
                GrantApplicationId = dgToyotaDb?.Id,
                PlanReferenceNumber = "PIP-2026-TOYOTA-01",
                InterventionTypeCode = "Apprenticeship",
                TotalAwardedAmount = 7500000.00m,
                RecoverableAmount = 0.00m,
                TotalLearnersAwarded = 50,
                LearnersWithDisabilityCount = 5,
                StatusCode = "ActiveContractsSigned",
                ContractSignOffDate = DateTime.UtcNow.AddDays(-30)
            };

            pipToyota.Allocations.Add(new PipLearnerAllocation
            {
                QualificationTitle = "Automotive Motor Mechanic (NQF 4)",
                LearnerCount = 30,
                UnitCost = 150000.00m,
                TotalAllowanceBudget = 2700000.00m,
                TotalTuitionBudget = 1800000.00m
            });

            pipToyota.Allocations.Add(new PipLearnerAllocation
            {
                QualificationTitle = "Electric Vehicle Technician (NQF 5)",
                LearnerCount = 20,
                UnitCost = 150000.00m,
                TotalAllowanceBudget = 1800000.00m,
                TotalTuitionBudget = 1200000.00m
            });

            pipToyota.Claims.Add(new GrantPaymentClaim
            {
                ClaimNumber = "CLM-2026-001",
                TrancheNumber = 1,
                ClaimAmount = 2250000.00m,
                DeliverableDescription = "Tranche 1: Learner registration, induction and workplace placement sign-offs.",
                StatusCode = "ApprovedForPayment",
                ApprovalDate = DateTime.UtcNow.AddDays(-15),
                ApprovedByUserId = "FinanceManager",
                ErpBatchNumber = "GP-BATCH-2026-0612"
            });

            var pipSasol = new ProjectImplementationPlan
            {
                OrganisationId = orgSasolDb.Id,
                FundingWindowId = windowDb?.Id,
                GrantApplicationId = dgSasolDb?.Id,
                PlanReferenceNumber = "PIP-2026-SASOL-02",
                InterventionTypeCode = "Learnership",
                TotalAwardedAmount = 5400000.00m,
                RecoverableAmount = 0.00m,
                TotalLearnersAwarded = 40,
                LearnersWithDisabilityCount = 3,
                StatusCode = "Draft",
                ContractSignOffDate = null
            };

            pipSasol.Allocations.Add(new PipLearnerAllocation
            {
                QualificationTitle = "Chemical Plant Operations (NQF 4)",
                LearnerCount = 40,
                UnitCost = 135000.00m,
                TotalAllowanceBudget = 3240000.00m,
                TotalTuitionBudget = 2160000.00m
            });

            db.ProjectImplementationPlans.AddRange(pipToyota, pipSasol);
            await db.SaveChangesAsync();
        }

        // ==========================================
        // 5c. SEED CONTRACT ADDENDAS & VARIATIONS
        // ==========================================
        if (!await db.ContractAddendas.AnyAsync())
        {
            var moaToyota = await db.GrantMoas.FirstOrDefaultAsync(m => m.GrantApplication != null && m.GrantApplication.OrganisationId == (orgToyotaDb != null ? orgToyotaDb.Id : 0));
            if (moaToyota != null)
            {
                var addenda1 = new ContractAddenda
                {
                    GrantMoaId = moaToyota.Id,
                    AddendaNumber = "ADD-2026-TOYOTA-01",
                    VariationTypeCode = "TimelineExtension",
                    OriginalContractValue = moaToyota.TotalContractValue,
                    RevisedContractValue = moaToyota.TotalContractValue,
                    OriginalEndDate = DateTime.UtcNow.AddMonths(6),
                    RevisedEndDate = DateTime.UtcNow.AddMonths(12),
                    MotivationReason = "Extension requested to accommodate expanded EV battery testing modules without budget increment.",
                    StatusCode = "SubmittedForReview",
                    CreatedAt = DateTime.UtcNow.AddDays(-7),
                    CreatedBy = "SDFManager"
                };

                var addenda2 = new ContractAddenda
                {
                    GrantMoaId = moaToyota.Id,
                    AddendaNumber = "ADD-2026-TOYOTA-02",
                    VariationTypeCode = "BudgetReallocation",
                    OriginalContractValue = moaToyota.TotalContractValue,
                    RevisedContractValue = moaToyota.TotalContractValue + 500000.00m,
                    OriginalEndDate = DateTime.UtcNow.AddMonths(6),
                    RevisedEndDate = DateTime.UtcNow.AddMonths(6),
                    MotivationReason = "Additional funding approved for high-voltage PPE and specialized tooling.",
                    StatusCode = "LegalApproved",
                    LegalReviewerUserId = "LegalOfficer",
                    LegalReviewDate = DateTime.UtcNow.AddDays(-2),
                    LegalReviewComments = "Statutory compliance verified against PFMA guidelines.",
                    CreatedAt = DateTime.UtcNow.AddDays(-14),
                    CreatedBy = "SDFManager"
                };

                db.ContractAddendas.AddRange(addenda1, addenda2);
                await db.SaveChangesAsync();
            }
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
                ImportStatusCode = "Reconciled"
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

        // ==========================================
        // 10. SEED AUXILIARY & GOVERNANCE WORKBENCHES
        // ==========================================
        if (!await db.BankingDetails.AnyAsync())
        {
            var bankToyota = new BankingDetails
            {
                OrganisationId = orgToyotaDb.Id,
                BankName = "Standard Bank South Africa",
                BranchCode = "051001",
                BranchName = "Johannesburg Corporate Branch",
                AccountNumber = "00284719284",
                AccountHolderName = "Toyota SA Motors (Pty) Ltd",
                AccountTypeCode = "Current",
                ApprovalStatusCode = "FullyApproved",
                FirstSignoffUserId = "finance.officer@merseta.org.za",
                FirstSignoffDate = DateTime.UtcNow.AddDays(-10),
                FirstSignoffNotes = "Bank confirmation letter and stamped statement verified.",
                SecondSignoffUserId = "finance.manager@merseta.org.za",
                SecondSignoffDate = DateTime.UtcNow.AddDays(-8),
                SecondSignoffNotes = "Dual signoff complete. ERP vendor synchronized.",
                IsErpActive = true,
                ErpVendorId = "VEND-TOYOTA-001",
                ErpSyncDate = DateTime.UtcNow.AddDays(-8),
                CreatedAt = DateTime.UtcNow.AddDays(-12),
                CreatedBy = "SYSTEM"
            };

            db.BankingDetails.Add(bankToyota);
            await db.SaveChangesAsync();
        }

        if (!await db.LearnerTradeTestApplications.AnyAsync())
        {
            var learnerDb = await db.CompanyLearners.FirstOrDefaultAsync() ?? new CompanyLearner { Id = 1, PersonId = pFatima.Id, OrganisationId = orgToyotaDb.Id };
            var ttApp = new LearnerTradeTestApplication
            {
                CompanyLearnerId = learnerDb.Id,
                PersonId = learnerDb.PersonId,
                OrganisationId = learnerDb.OrganisationId,
                TrainingProviderId = providerFestoDb.Id,
                ApplicationNumber = "TT-2026-TOYOTA-001",
                TradeTitle = "Automotive Motor Mechanic",
                TradeOfoCode = "653101",
                ApplicationTypeCode = "ARPL",
                AttemptNumber = 1,
                LearnerReadinessDate = DateTime.UtcNow.AddDays(-30),
                StatusCode = "TradeCenterAllocated",
                CompetencyStatusCode = "Competent",
                NambSerialNumber = "NAMB-2026-8842",
                SerialCertificateNumber = "CERT-2026-TOYOTA-88412",
                CertificateIssueDate = DateTime.UtcNow.AddDays(-5),
                CreatedAt = DateTime.UtcNow.AddDays(-35),
                CreatedBy = "SYSTEM"
            };

            db.LearnerTradeTestApplications.Add(ttApp);
            await db.SaveChangesAsync();
        }

        if (!await db.ReviewCommitteeMeetings.AnyAsync())
        {
            var meeting = new ReviewCommitteeMeeting
            {
                MeetingNumber = "RCM-2026-Q1-001",
                Title = "ETQA & Governance Review Committee Q1 Ordinary Session",
                MeetingTypeCode = "EtqaReviewCommittee",
                FromDateTime = DateTime.UtcNow.AddDays(7),
                ToDateTime = DateTime.UtcNow.AddDays(7).AddHours(4),
                Venue = "merSETA Head Office - Boardroom 3 / Microsoft Teams Hybrid",
                StatusCode = "Scheduled",
                CreatedAt = DateTime.UtcNow.AddDays(-5),
                CreatedBy = "SYSTEM"
            };

            meeting.Agendas.Add(new ReviewCommitteeMeetingAgenda
            {
                ItemNumber = 1,
                Title = "Workplace Approval & Site Accreditation Ratifications",
                Description = "Review and ratify 14 employer site inspection approvals recommended by Regional Field Officers.",
                TargetEntityName = "WorkplaceApproval",
                DecisionCode = "Pending",
                CreatedAt = DateTime.UtcNow.AddDays(-5),
                CreatedBy = "SYSTEM"
            });

            db.ReviewCommitteeMeetings.Add(meeting);
            await db.SaveChangesAsync();
        }

        if (!await db.QualificationsCurriculumDevelopments.AnyAsync())
        {
            var qcd1 = new QualificationsCurriculumDevelopment
            {
                ApplicationNumber = "QCD-2026-AUT-001",
                QualificationTitle = "Occupational Certificate: Electric Vehicle Mechatronics Technician",
                SaqaQualificationId = "SAQA-119842",
                OfoCode = "671201",
                NqfLevel = 5,
                TotalCreditsRequired = 240,
                DevelopmentTypeCode = "NewDevelopment",
                NationalDevelopmentPlanChecked = true,
                NationalDevelopmentPlanEvidence = "Aligned with NDP 2030 Chapter 9: Improving education, training and innovation for green transport economy.",
                NewGrowthPlanChecked = true,
                NewGrowthPlanEvidence = "Supports Green Economy Accord and automotive industry manufacturing transformation.",
                IndustrialPolicyActionPlanChecked = true,
                IndustrialPolicyActionPlanEvidence = "Matches SAAM 2035 EV localization and high-tech manufacturing priority interventions.",
                StrategicInfrastructureChecked = true,
                PurposeOfQualification = "To produce skilled mechatronics technicians capable of diagnosing, servicing and assembling electric and hybrid powertrains.",
                TargetLearnerAudience = "Apprentices, qualified automotive motor mechanics seeking EV upskilling, and TVET engineering diploma graduates.",
                IndustryDemandJustification = "Critical shortage of high-voltage battery diagnostic specialists across SA automotive OEM manufacturing plants.",
                DevelopmentQualityPartner = "merSETA DQP",
                AssessmentQualityPartner = "merSETA AQP",
                OrganisationId = orgToyotaDb.Id,
                WorkingGroupConvenedDate = DateTime.UtcNow.AddDays(-60),
                PublicCommentClosingDate = DateTime.UtcNow.AddDays(30),
                StatusCode = "PublicCommentOpen",
                CreatedAt = DateTime.UtcNow.AddDays(-75),
                CreatedBy = "SYSTEM"
            };

            qcd1.WorkingGroupMembers.Add(new CurriculumWorkingGroupMember
            {
                MemberName = "Dr. Johan van der Merwe",
                StakeholderRoleTitle = "LeadQDF",
                OrganisationRepresented = "merSETA Curriculum Development Unit",
                EmailAddress = "johan.vdm@merietasamples.co.za",
                PhoneNumber = "0112345678",
                IsConfirmedAttendee = true,
                CreatedAt = DateTime.UtcNow.AddDays(-60),
                CreatedBy = "SYSTEM"
            });

            qcd1.WorkingGroupMembers.Add(new CurriculumWorkingGroupMember
            {
                MemberName = "Sipho Khumalo",
                StakeholderRoleTitle = "IndustryExpert",
                OrganisationRepresented = "Toyota SA Motors - Technical Training Centre",
                EmailAddress = "sipho.khumalo@merietasamples.co.za",
                PhoneNumber = "0319102000",
                IsConfirmedAttendee = true,
                CreatedAt = DateTime.UtcNow.AddDays(-60),
                CreatedBy = "SYSTEM"
            });

            qcd1.SkillsRegistrations.Add(new SkillsRegistration
            {
                NonNqfIntervCode = "SP-EV-BATT-01",
                NonNqfIntervName = "High-Voltage EV Battery Pack Diagnostics and Safety Isolation",
                SubfieldId = "06",
                EtqaId = "17",
                NonNqfIntervStatusId = "01",
                LearningProgrammeTypeId = "03",
                RegistrationStartDate = DateTime.UtcNow.AddDays(-30),
                Credits = 45,
                NqfLevel = 5,
                UnitStandardsIncludedJson = "[\"US-EV-001\",\"US-EV-002\",\"US-EV-003\"]",
                CreatedAt = DateTime.UtcNow.AddDays(-30),
                CreatedBy = "SYSTEM"
            });

            var qcd2 = new QualificationsCurriculumDevelopment
            {
                ApplicationNumber = "QCD-2026-MET-002",
                QualificationTitle = "Occupational Certificate: Robotic Welding & Automation Specialist",
                SaqaQualificationId = "SAQA-118751",
                OfoCode = "651202",
                NqfLevel = 4,
                TotalCreditsRequired = 180,
                DevelopmentTypeCode = "ReAlignment",
                NationalDevelopmentPlanChecked = true,
                NationalDevelopmentPlanEvidence = "NDP Priority Skills alignment for heavy engineering manufacturing.",
                NewGrowthPlanChecked = true,
                NewGrowthPlanEvidence = "Supports advanced metals and plastics manufacturing productivity.",
                IndustrialPolicyActionPlanChecked = true,
                IndustrialPolicyActionPlanEvidence = "IPAP Metal Fabrication, Capital and Rail Transport Equipment sector plan.",
                StrategicInfrastructureChecked = false,
                PurposeOfQualification = "To train automated welding system programmers and operators for robotic welding cells in industrial manufacturing.",
                TargetLearnerAudience = "Qualified boilermakers, welders, and precision mechanical engineering artisans.",
                IndustryDemandJustification = "High demand from automotive body shops and structural steel fabricators transitioning to Industry 4.0 automation.",
                DevelopmentQualityPartner = "merSETA DQP",
                AssessmentQualityPartner = "merSETA AQP",
                OrganisationId = orgToyotaDb.Id,
                WorkingGroupConvenedDate = DateTime.UtcNow.AddDays(-120),
                SaqaSubmissionDate = DateTime.UtcNow.AddDays(-15),
                StatusCode = "SubmittedToQcto",
                CreatedAt = DateTime.UtcNow.AddDays(-140),
                CreatedBy = "SYSTEM"
            };

            db.QualificationsCurriculumDevelopments.AddRange(qcd1, qcd2);
            await db.SaveChangesAsync();
        }

        // ==========================================
        // 17. SEED SDF APPOINTMENTS & HISTORY
        // ==========================================
        if (!await db.SdfCompanies.AnyAsync())
        {
            var personSdf1 = await db.People.FirstOrDefaultAsync(p => p.RsaIdNumber == "8506155021088") ?? await db.People.FirstOrDefaultAsync();
            var personSdf2 = await db.People.FirstOrDefaultAsync(p => p.RsaIdNumber == "9201015012089") ?? await db.People.Skip(1).FirstOrDefaultAsync();

            if (personSdf1 != null && orgToyotaDb != null)
            {
                var sdf1 = new SdfCompany
                {
                    OrganisationId = orgToyotaDb.Id,
                    PersonId = personSdf1.Id,
                    SdfTypeCode = "Primary",
                    SdfStatusCode = "Approved",
                    AppointmentStartDate = DateTime.UtcNow.AddMonths(-6),
                    AllowWspSubmission = true,
                    AllowDgApplication = true,
                    AllowTrancheClaims = true,
                    SignedAppointmentLetterReceived = true,
                    SignedAcceptanceDeclarationReceived = true,
                    ApprovedByUserId = "SUPERADMIN",
                    ApprovalDate = DateTime.UtcNow.AddMonths(-6).AddDays(2),
                    ApprovalComments = "Verified board resolution and valid accreditation credentials.",
                    IsActive = true,
                    CreatedAt = DateTime.UtcNow.AddMonths(-6),
                    CreatedBy = "SUPERADMIN"
                };

                db.SdfCompanies.Add(sdf1);
                await db.SaveChangesAsync();

                db.SdfAppointmentHistories.Add(new SdfAppointmentHistory
                {
                    SdfCompanyId = sdf1.Id,
                    PreviousStatusCode = "PendingApproval",
                    NewStatusCode = "Approved",
                    ChangeReason = "Completed appointment verification and compliance sign-off.",
                    ChangedByUserId = "SUPERADMIN",
                    ChangedAt = DateTime.UtcNow.AddMonths(-6).AddDays(2)
                });
                await db.SaveChangesAsync();
            }

            if (personSdf2 != null && orgSasolDb != null)
            {
                var sdf2 = new SdfCompany
                {
                    OrganisationId = orgSasolDb.Id,
                    PersonId = personSdf2.Id,
                    SdfTypeCode = "Secondary",
                    SdfStatusCode = "PendingApproval",
                    AppointmentStartDate = DateTime.UtcNow.AddDays(-10),
                    AllowWspSubmission = true,
                    AllowDgApplication = false,
                    AllowTrancheClaims = false,
                    SignedAppointmentLetterReceived = true,
                    SignedAcceptanceDeclarationReceived = false,
                    IsActive = true,
                    CreatedAt = DateTime.UtcNow.AddDays(-10),
                    CreatedBy = "SDF_USER"
                };

                db.SdfCompanies.Add(sdf2);
                await db.SaveChangesAsync();

                db.SdfAppointmentHistories.Add(new SdfAppointmentHistory
                {
                    SdfCompanyId = sdf2.Id,
                    PreviousStatusCode = "None",
                    NewStatusCode = "PendingApproval",
                    ChangeReason = "Submitted online SDF registration portal application.",
                    ChangedByUserId = "SDF_USER",
                    ChangedAt = DateTime.UtcNow.AddDays(-10)
                });
                await db.SaveChangesAsync();
            }
        }

        // ==========================================
        // 18. SEED TRADE MENTOR RATIO POLICIES & GLOBAL CONFIG
        // ==========================================
        if (!await db.TradeMentorRatioPolicies.AnyAsync())
        {
            var policies = new List<TradeMentorRatioPolicy>
            {
                new()
                {
                    TradeCode = "ELEC",
                    TradeTitle = "Electrician",
                    TradeOfoCode = "671101",
                    SaqaQualificationId = 91761,
                    StandardRatio = 2,
                    MaxAllowedRatio = 3,
                    MinExperienceYearsRequired = 3,
                    EnforceStrictly = true,
                    IsActive = true,
                    Notes = "High-voltage electrical installations - statutory NAMB limit of 1 mentor to 2 apprentices.",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "SYSTEM"
                },
                new()
                {
                    TradeCode = "WELD",
                    TradeTitle = "Welder",
                    TradeOfoCode = "651202",
                    SaqaQualificationId = 96420,
                    StandardRatio = 3,
                    MaxAllowedRatio = 5,
                    MinExperienceYearsRequired = 3,
                    EnforceStrictly = true,
                    IsActive = true,
                    Notes = "Pressure vessel and structural fabrication welding statutory ratio.",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "SYSTEM"
                },
                new()
                {
                    TradeCode = "FITT",
                    TradeTitle = "Fitter and Turner",
                    TradeOfoCode = "653301",
                    SaqaQualificationId = 96421,
                    StandardRatio = 4,
                    MaxAllowedRatio = 6,
                    MinExperienceYearsRequired = 3,
                    EnforceStrictly = true,
                    IsActive = true,
                    Notes = "Precision mechanical machining and fitting workshop ratio standard.",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "SYSTEM"
                },
                new()
                {
                    TradeCode = "BOIL",
                    TradeTitle = "Boilermaker",
                    TradeOfoCode = "651401",
                    SaqaQualificationId = 96422,
                    StandardRatio = 3,
                    MaxAllowedRatio = 5,
                    MinExperienceYearsRequired = 3,
                    EnforceStrictly = true,
                    IsActive = true,
                    Notes = "Heavy structural steel fabrication and boiler vessel development.",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "SYSTEM"
                },
                new()
                {
                    TradeCode = "MILL",
                    TradeTitle = "Millwright",
                    TradeOfoCode = "671201",
                    SaqaQualificationId = 96423,
                    StandardRatio = 3,
                    MaxAllowedRatio = 4,
                    MinExperienceYearsRequired = 4,
                    EnforceStrictly = true,
                    IsActive = true,
                    Notes = "Dual electro-mechanical industrial machinery maintenance trade.",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "SYSTEM"
                },
                new()
                {
                    TradeCode = "DIES",
                    TradeTitle = "Diesel Mechanic",
                    TradeOfoCode = "653101",
                    SaqaQualificationId = 96424,
                    StandardRatio = 4,
                    MaxAllowedRatio = 6,
                    MinExperienceYearsRequired = 3,
                    EnforceStrictly = true,
                    IsActive = true,
                    Notes = "Commercial diesel engines and heavy plant earthmoving machinery.",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "SYSTEM"
                },
                new()
                {
                    TradeCode = "TOOL",
                    TradeTitle = "Toolmaker",
                    TradeOfoCode = "652201",
                    SaqaQualificationId = 96425,
                    StandardRatio = 2,
                    MaxAllowedRatio = 3,
                    MinExperienceYearsRequired = 5,
                    EnforceStrictly = true,
                    IsActive = true,
                    Notes = "Precision jig, fixture, die, and press-tool manufacturing.",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "SYSTEM"
                },
                new()
                {
                    TradeCode = "AUTO_MECH",
                    TradeTitle = "Automotive Motor Mechanic",
                    TradeOfoCode = "653101",
                    SaqaQualificationId = 65409,
                    StandardRatio = 4,
                    MaxAllowedRatio = 6,
                    MinExperienceYearsRequired = 3,
                    EnforceStrictly = true,
                    IsActive = true,
                    Notes = "Light vehicle automotive servicing and engine diagnostic overhaul.",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "SYSTEM"
                },
                new()
                {
                    TradeCode = "AUTO_ELEC",
                    TradeTitle = "Automotive Electrician",
                    TradeOfoCode = "672105",
                    SaqaQualificationId = 96426,
                    StandardRatio = 3,
                    MaxAllowedRatio = 4,
                    MinExperienceYearsRequired = 3,
                    EnforceStrictly = true,
                    IsActive = true,
                    Notes = "Automotive electrical and electronic harness systems.",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "SYSTEM"
                },
                new()
                {
                    TradeCode = "GENERIC",
                    TradeTitle = "Generic Engineering Trade",
                    TradeOfoCode = "000000",
                    SaqaQualificationId = null,
                    StandardRatio = 4,
                    MaxAllowedRatio = 6,
                    MinExperienceYearsRequired = 3,
                    EnforceStrictly = false,
                    IsActive = true,
                    Notes = "Default fallback ratio policy for general artisan skills programmes.",
                    CreatedAt = DateTime.UtcNow,
                    CreatedBy = "SYSTEM"
                }
            };

            db.TradeMentorRatioPolicies.AddRange(policies);
            await db.SaveChangesAsync();

            // Link sample WPA to AUTO_MECH trade
            var existingWpa = await db.WorkplaceApprovals.FirstOrDefaultAsync();
            if (existingWpa != null && string.IsNullOrEmpty(existingWpa.TradeCode))
            {
                existingWpa.TradeCode = "AUTO_MECH";
                await db.SaveChangesAsync();
            }
        }

        // Global System Config for Mentor Ratio Enforcement
        if (!await db.SystemConfigs.AnyAsync(c => c.ConfigKey == "WorkplaceApproval.EnforceMentorRatios"))
        {
            db.SystemConfigs.Add(new SystemConfig
            {
                ConfigKey = "WorkplaceApproval.EnforceMentorRatios",
                ConfigValue = "true",
                Category = "WorkplaceApproval",
                DataType = "Boolean",
                Description = "Master switch enabling statutory artisan mentor-to-learner ratio enforcement globally.",
                IsEncrypted = false,
                CreatedAt = DateTime.UtcNow,
                CreatedBy = "SYSTEM"
            });
            await db.SaveChangesAsync();
        }
    }
}
