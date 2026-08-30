using Microsoft.EntityFrameworkCore;
using Nsdms.Domain.Lookups;

namespace Nsdms.Infrastructure.Data;

public static class LookupBatchSeeder
{
    public static void SeedAllLookups(ModelBuilder modelBuilder)
    {
        // 1. StatusType
        modelBuilder.Entity<StatusType>().HasData(
            new StatusType { Code = "ACTIVE", Name = "Active", Description = "Active record", Active = true },
            new StatusType { Code = "INACTIVE", Name = "Inactive", Description = "Inactive / Archived", Active = true },
            new StatusType { Code = "PENDING", Name = "Pending Approval", Description = "Submitted awaiting approval", Active = true },
            new StatusType { Code = "APPROVED", Name = "Approved", Description = "Formally approved by committee", Active = true },
            new StatusType { Code = "REJECTED", Name = "Rejected", Description = "Rejected with non-compliance", Active = true },
            new StatusType { Code = "SUSPENDED", Name = "Suspended", Description = "Temporarily suspended", Active = true }
        );

        // 2. GenderType
        modelBuilder.Entity<GenderType>().HasData(
            new GenderType { Code = "M", Name = "Male", Description = "Male", Active = true },
            new GenderType { Code = "F", Name = "Female", Description = "Female", Active = true },
            new GenderType { Code = "O", Name = "Other", Description = "Non-binary / Other", Active = true },
            new GenderType { Code = "U", Name = "Unspecified", Description = "Not specified", Active = true }
        );

        // 3. EquityType (Employment Equity Codes)
        modelBuilder.Entity<EquityType>().HasData(
            new EquityType { Code = "BA", Name = "Black African", Description = "African", Active = true },
            new EquityType { Code = "BC", Name = "Coloured", Description = "Coloured", Active = true },
            new EquityType { Code = "BI", Name = "Indian/Asian", Description = "Indian or Asian", Active = true },
            new EquityType { Code = "WH", Name = "White", Description = "White", Active = true },
            new EquityType { Code = "OTH", Name = "Other", Description = "Other equity status", Active = true }
        );

        // 4. CitizenStatusType
        modelBuilder.Entity<CitizenStatusType>().HasData(
            new CitizenStatusType { Code = "SA_CIT", Name = "South African Citizen", Description = "Holds SA Citizenship", Active = true },
            new CitizenStatusType { Code = "PERM_RES", Name = "Permanent Resident", Description = "Permanent residency permit", Active = true },
            new CitizenStatusType { Code = "TEMP_RES", Name = "Temporary Resident", Description = "Temporary work/study permit", Active = true },
            new CitizenStatusType { Code = "FOREIGN", Name = "Foreign National", Description = "Non-citizen foreign national", Active = true }
        );

        // 5. NationalityType
        modelBuilder.Entity<NationalityType>().HasData(
            new NationalityType { Code = "ZAF", Name = "South Africa", Description = "Republic of South Africa", Active = true },
            new NationalityType { Code = "NAM", Name = "Namibia", Description = "Namibia", Active = true },
            new NationalityType { Code = "BWA", Name = "Botswana", Description = "Botswana", Active = true },
            new NationalityType { Code = "ZWE", Name = "Zimbabwe", Description = "Zimbabwe", Active = true },
            new NationalityType { Code = "MOZ", Name = "Mozambique", Description = "Mozambique", Active = true },
            new NationalityType { Code = "LSO", Name = "Lesotho", Description = "Lesotho", Active = true },
            new NationalityType { Code = "SWZ", Name = "Eswatini", Description = "Eswatini", Active = true },
            new NationalityType { Code = "OTHER", Name = "Other Country", Description = "Other International", Active = true }
        );

        // 6. HomeLanguageType (11 Official Languages + SASL)
        modelBuilder.Entity<HomeLanguageType>().HasData(
            new HomeLanguageType { Code = "ENG", Name = "English", Description = "English", Active = true },
            new HomeLanguageType { Code = "AFR", Name = "Afrikaans", Description = "Afrikaans", Active = true },
            new HomeLanguageType { Code = "ZUL", Name = "isiZulu", Description = "isiZulu", Active = true },
            new HomeLanguageType { Code = "XHO", Name = "isiXhosa", Description = "isiXhosa", Active = true },
            new HomeLanguageType { Code = "SOT", Name = "Sesotho", Description = "Sesotho", Active = true },
            new HomeLanguageType { Code = "TSN", Name = "Setswana", Description = "Setswana", Active = true },
            new HomeLanguageType { Code = "NSO", Name = "Sepedi", Description = "Sepedi", Active = true },
            new HomeLanguageType { Code = "TSO", Name = "Xitsonga", Description = "Xitsonga", Active = true },
            new HomeLanguageType { Code = "SSW", Name = "siSwati", Description = "siSwati", Active = true },
            new HomeLanguageType { Code = "VEN", Name = "Tshivenda", Description = "Tshivenda", Active = true },
            new HomeLanguageType { Code = "NDE", Name = "isiNdebele", Description = "isiNdebele", Active = true },
            new HomeLanguageType { Code = "SASL", Name = "SA Sign Language", Description = "South African Sign Language", Active = true }
        );

        // 7. ProvinceType
        modelBuilder.Entity<ProvinceType>().HasData(
            new ProvinceType { Code = "GP", Name = "Gauteng", Description = "Gauteng Province", Active = true },
            new ProvinceType { Code = "KZN", Name = "KwaZulu-Natal", Description = "KwaZulu-Natal Province", Active = true },
            new ProvinceType { Code = "WC", Name = "Western Cape", Description = "Western Cape Province", Active = true },
            new ProvinceType { Code = "EC", Name = "Eastern Cape", Description = "Eastern Cape Province", Active = true },
            new ProvinceType { Code = "FS", Name = "Free State", Description = "Free State Province", Active = true },
            new ProvinceType { Code = "MP", Name = "Mpumalanga", Description = "Mpumalanga Province", Active = true },
            new ProvinceType { Code = "NW", Name = "North West", Description = "North West Province", Active = true },
            new ProvinceType { Code = "NC", Name = "Northern Cape", Description = "Northern Cape Province", Active = true },
            new ProvinceType { Code = "LP", Name = "Limpopo", Description = "Limpopo Province", Active = true }
        );

        // 8. DisabilityType
        modelBuilder.Entity<DisabilityType>().HasData(
            new DisabilityType { Code = "NONE", Name = "None", Description = "No disability", Active = true },
            new DisabilityType { Code = "SIGHT", Name = "Sight Disability", Description = "Visual impairment / blindness", Active = true },
            new DisabilityType { Code = "HEARING", Name = "Hearing Disability", Description = "Hearing impairment / deafness", Active = true },
            new DisabilityType { Code = "PHYSICAL", Name = "Physical / Mobility", Description = "Physical or mobility disability", Active = true },
            new DisabilityType { Code = "LEARNING", Name = "Learning Disability", Description = "Intellectual / Learning disability", Active = true },
            new DisabilityType { Code = "PSYCH", Name = "Psychiatric", Description = "Mental health / psychiatric", Active = true }
        );

        // 9. CategoryType
        modelBuilder.Entity<CategoryType>().HasData(
            new CategoryType { Code = "EMP", Name = "Employer", Description = "Levy / Non-Levy Paying Employer", Active = true },
            new CategoryType { Code = "SDP", Name = "Skills Development Provider", Description = "Accredited Training Provider", Active = true },
            new CategoryType { Code = "ASS", Name = "Assessment Centre", Description = "Accredited Assessment Centre", Active = true },
            new CategoryType { Code = "TTC", Name = "Trade Test Centre", Description = "Decentralized Trade Test Centre", Active = true }
        );

        // 10. CompanySizeType
        modelBuilder.Entity<CompanySizeType>().HasData(
            new CompanySizeType { Code = "MICRO", Name = "Micro (0 - 9 Employees)", Description = "Fewer than 10 employees", Active = true },
            new CompanySizeType { Code = "SMALL", Name = "Small (10 - 49 Employees)", Description = "10 to 49 employees", Active = true },
            new CompanySizeType { Code = "MEDIUM", Name = "Medium (50 - 149 Employees)", Description = "50 to 149 employees", Active = true },
            new CompanySizeType { Code = "LARGE", Name = "Large (150+ Employees)", Description = "150 or more employees", Active = true }
        );

        // 11. SectorType
        modelBuilder.Entity<SectorType>().HasData(
            new SectorType { Code = "AUTO", Name = "Automotive Manufacturing", Description = "Auto assembly & component manufacturing", Active = true },
            new SectorType { Code = "METAL", Name = "Metal and Engineering", Description = "Basic metal & fabricated metal products", Active = true },
            new SectorType { Code = "MOTOR", Name = "Motor Retail and Service", Description = "Retail automotive repair & service", Active = true },
            new SectorType { Code = "PLAST", Name = "Plastics Industry", Description = "Plastics conversion & polymer manufacturing", Active = true },
            new SectorType { Code = "TYRE", Name = "New Tyre Manufacturing", Description = "Pneumatic tyre manufacturing", Active = true }
        );

        // 12. ChamberType
        modelBuilder.Entity<ChamberType>().HasData(
            new ChamberType { Code = "AUTO_CHAM", Name = "Automotive Chamber", Description = "Automotive Chamber", Active = true },
            new ChamberType { Code = "METAL_CHAM", Name = "Metal Chamber", Description = "Metal Chamber", Active = true },
            new ChamberType { Code = "MOTOR_CHAM", Name = "Motor Chamber", Description = "Motor Chamber", Active = true },
            new ChamberType { Code = "PLAST_CHAM", Name = "Plastics Chamber", Description = "Plastics Chamber", Active = true }
        );

        // 13. LearningProgrammeType
        modelBuilder.Entity<LearningProgrammeType>().HasData(
            new LearningProgrammeType { Code = "LEARNERSHIP", Name = "Learnership", Description = "Structured workplace learning programme", Active = true },
            new LearningProgrammeType { Code = "APPRENTICESHIP", Name = "Apprenticeship", Description = "Trade artisan apprenticeship", Active = true },
            new LearningProgrammeType { Code = "SKILLS_PROG", Name = "Skills Programme", Description = "Unit standard accredited skills programme", Active = true },
            new LearningProgrammeType { Code = "INTERNSHIP", Name = "Internship / WIL", Description = "Work integrated learning for graduates", Active = true },
            new LearningProgrammeType { Code = "BURSARY", Name = "Bursary Programme", Description = "Higher education funded bursary", Active = true }
        );

        // 14. EnrolmentStatusType
        modelBuilder.Entity<EnrolmentStatusType>().HasData(
            new EnrolmentStatusType { Code = "REGISTERED", Name = "Registered", Description = "Formally enrolled and active", Active = true },
            new EnrolmentStatusType { Code = "COMPLETED", Name = "Completed / Certified", Description = "Successfully certified", Active = true },
            new EnrolmentStatusType { Code = "TERMINATED", Name = "Terminated", Description = "Contract prematurely ended", Active = true },
            new EnrolmentStatusType { Code = "TRANSFERRED", Name = "Transferred", Description = "Transferred to another provider/employer", Active = true }
        );

        // 15. GrantTypeType
        modelBuilder.Entity<GrantTypeType>().HasData(
            new GrantTypeType { Code = "DG_PIVOTAL", Name = "Discretionary PIVOTAL Grant", Description = "Professional, Vocational, Technical & Academic learning", Active = true },
            new GrantTypeType { Code = "DG_NON_PIVOTAL", Name = "Discretionary Non-PIVOTAL", Description = "Sectoral research & strategic projects", Active = true },
            new GrantTypeType { Code = "MG_MANDATORY", Name = "Mandatory Grant (WSP/ATR)", Description = "20% employer skills levy rebate", Active = true },
            new GrantTypeType { Code = "SPECIAL_PROJ", Name = "Special Executive Project", Description = "Ministerial / Board special interventions", Active = true }
        );

        // 16. VisitTypeType
        modelBuilder.Entity<VisitTypeType>().HasData(
            new VisitTypeType { Code = "MONITOR", Name = "Monitoring Visit", Description = "Routine learner & workplace monitoring", Active = true },
            new VisitTypeType { Code = "APPROVAL", Name = "Workplace Approval Visit", Description = "Workplace accreditation approval inspection", Active = true },
            new VisitTypeType { Code = "AUDIT", Name = "Quality Assurance Audit", Description = "ETQA comprehensive compliance audit", Active = true },
            new VisitTypeType { Code = "ASSESSMENT", Name = "Trade Assessment Visit", Description = "Practical trade test venue verification", Active = true }
        );
    }
}
