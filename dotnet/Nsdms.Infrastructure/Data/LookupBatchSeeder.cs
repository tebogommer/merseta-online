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
            new ChamberType { Code = "AUTO", Name = "Automotive Manufacturing Chamber", Description = "Automotive assembly and OEM manufacturing", Active = true },
            new ChamberType { Code = "METAL", Name = "Metal and Engineering Chamber", Description = "Basic metal, fabrication and engineering products", Active = true },
            new ChamberType { Code = "MOTOR", Name = "Motor Retail & Components Chamber", Description = "Retail motor, vehicle maintenance and component manufacturing", Active = true },
            new ChamberType { Code = "NEW_TYRE", Name = "New Tyre Manufacturing Chamber", Description = "Pneumatic tyre manufacturing and retreading", Active = true },
            new ChamberType { Code = "PLASTICS", Name = "Plastics Manufacturing Chamber", Description = "Plastics conversion and synthetic polymer manufacturing", Active = true },
            new ChamberType { Code = "OTHER", Name = "General & Emerging Sectors Chamber", Description = "Electrical, precision engineering, and other manufacturing sub-sectors", Active = true }
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

        // 17. SicCodeType - merSETA Statutory 5-Digit Matrix mapped to Chambers & SETA Scopes
        modelBuilder.Entity<SicCodeType>().HasData(
            // Automotive Manufacturing Chamber (AUTO) - SETA 17
            new SicCodeType { Code = "38400", Name = "Manufacture of Motor Vehicles (OEM)", ChamberCode = "AUTO", SetaCode = "17", Description = "Assembly of passenger cars, commercial vehicles, chassis and buses", Active = true },
            new SicCodeType { Code = "38410", Name = "Manufacture of Bodies, Trailers & Coachwork", ChamberCode = "AUTO", SetaCode = "17", Description = "Motor vehicle bodies, trailers, semi-trailers and specialized caravans", Active = true },
            new SicCodeType { Code = "38420", Name = "Manufacture of Motor Vehicle Parts & Engines", ChamberCode = "AUTO", SetaCode = "17", Description = "Engines, drivetrains, transmissions, and mechanical auto parts", Active = true },
            new SicCodeType { Code = "38430", Name = "Manufacture of Specialized Auto Assemblies", ChamberCode = "AUTO", SetaCode = "17", Description = "Specialized automotive electronic, hydraulic, and structural assemblies", Active = true },

            // Metal and Engineering Chamber (METAL) - SETA 17
            new SicCodeType { Code = "38100", Name = "Basic Iron, Steel & Non-Ferrous Metals", ChamberCode = "METAL", SetaCode = "17", Description = "Primary smelting, refining, rolling mills, and basic metallurgy", Active = true },
            new SicCodeType { Code = "38110", Name = "Metal Casting & Foundries", ChamberCode = "METAL", SetaCode = "17", Description = "Ferrous and non-ferrous foundry operations, casting and forging", Active = true },
            new SicCodeType { Code = "38200", Name = "Fabricated Metal Products & Structural Steel", ChamberCode = "METAL", SetaCode = "17", Description = "Structural steelwork, tanks, pressure vessels, boilers, and architectural metalwork", Active = true },
            new SicCodeType { Code = "38210", Name = "Machining, Tooling, Heat Treatment & Coating", ChamberCode = "METAL", SetaCode = "17", Description = "Precision CNC machining, tool & die making, electroplating, and heat treatment", Active = true },
            new SicCodeType { Code = "38220", Name = "General Purpose Industrial Machinery", ChamberCode = "METAL", SetaCode = "17", Description = "Pumps, compressors, valves, industrial gears, bearings, and power transmission", Active = true },
            new SicCodeType { Code = "38290", Name = "Special Purpose Machinery & Mining Equipment", ChamberCode = "METAL", SetaCode = "17", Description = "Agricultural machinery, earthmoving, mining equipment, lifting and materials handling", Active = true },
            new SicCodeType { Code = "38300", Name = "Heavy Electrical Machinery & Apparatus", ChamberCode = "METAL", SetaCode = "17", Description = "Electric motors, generators, transformers, switchgear, and power distribution apparatus", Active = true },
            new SicCodeType { Code = "38500", Name = "Measuring & Professional Controlling Equipment", ChamberCode = "METAL", SetaCode = "17", Description = "Industrial process control, scientific apparatus, and navigational instrumentation", Active = true },

            // Motor Retail & Components Chamber (MOTOR) - SETA 17
            new SicCodeType { Code = "63100", Name = "Sale of Motor Vehicles & Dealerships", ChamberCode = "MOTOR", SetaCode = "17", Description = "Wholesale and retail sale of new and used motor vehicles", Active = true },
            new SicCodeType { Code = "63200", Name = "Maintenance & Repair of Motor Vehicles", ChamberCode = "MOTOR", SetaCode = "17", Description = "Mechanical workshops, auto-electrical repairs, spray painting, and panel beating", Active = true },
            new SicCodeType { Code = "63300", Name = "Sale of Motor Vehicle Parts & Spares", ChamberCode = "MOTOR", SetaCode = "17", Description = "Wholesale and retail sale of vehicle accessories, spares, and replacement components", Active = true },
            new SicCodeType { Code = "63400", Name = "Sale, Maintenance & Repair of Motorcycles", ChamberCode = "MOTOR", SetaCode = "17", Description = "Motorcycles, scooters, and related components sale and workshop servicing", Active = true },
            new SicCodeType { Code = "63500", Name = "Automotive Service Stations & Fitment Centres", ChamberCode = "MOTOR", SetaCode = "17", Description = "Tyre fitment, exhaust, battery, brake, and automotive retail service centres", Active = true },

            // New Tyre Manufacturing Chamber (NEW_TYRE) - SETA 17
            new SicCodeType { Code = "35510", Name = "Manufacture of Pneumatic & Solid Tyres", ChamberCode = "NEW_TYRE", SetaCode = "17", Description = "Pneumatic tyres, solid rubber tyres, inner tubes, and tyre flaps", Active = true },
            new SicCodeType { Code = "35520", Name = "Retreading & Rebuilding of Rubber Tyres", ChamberCode = "NEW_TYRE", SetaCode = "17", Description = "Commercial vehicle and aircraft tyre retreading, curing, and vulcanisation", Active = true },
            new SicCodeType { Code = "35590", Name = "Technical Rubber & Industrial Seals", ChamberCode = "NEW_TYRE", SetaCode = "17", Description = "Conveyor belting, rubber hoses, gaskets, and industrial rubber products", Active = true },

            // Plastics Manufacturing Chamber (PLASTICS) - SETA 17
            new SicCodeType { Code = "35600", Name = "Manufacture of Plastic Resins & Compounding", ChamberCode = "PLASTICS", SetaCode = "17", Description = "Synthetic polymer compounding, virgin resin processing, and masterbatch", Active = true },
            new SicCodeType { Code = "35610", Name = "Plastic Plates, Sheets, Films & Foils", ChamberCode = "PLASTICS", SetaCode = "17", Description = "Plastic sheet extrusion, industrial film, and polymeric membrane manufacturing", Active = true },
            new SicCodeType { Code = "35620", Name = "Plastic Tubes, Pipes, Hoses & Fittings", ChamberCode = "PLASTICS", SetaCode = "17", Description = "Plumbing, conduit, industrial hoses, and pressure piping extrusion", Active = true },
            new SicCodeType { Code = "35630", Name = "Plastic Packaging Containers & Bottles", ChamberCode = "PLASTICS", SetaCode = "17", Description = "Blow moulding, injection stretch blow moulding, and plastic packaging containers", Active = true },
            new SicCodeType { Code = "35690", Name = "Moulded Plastic & Composite Engineering", ChamberCode = "PLASTICS", SetaCode = "17", Description = "Custom injection moulding, rotational moulding, and composite plastics", Active = true },

            // General & Emerging Sectors Chamber (OTHER) - SETA 17
            new SicCodeType { Code = "39000", Name = "Other Manufacturing & Emerging Technologies", ChamberCode = "OTHER", SetaCode = "17", Description = "Renewable energy equipment, battery energy storage, and innovative fabrication", Active = true },
            new SicCodeType { Code = "39100", Name = "Jewellery & Precious Metal Fabrication", ChamberCode = "OTHER", SetaCode = "17", Description = "Precious metal manufacturing, technical jewellery, and precision lapidary", Active = true },
            new SicCodeType { Code = "39200", Name = "Additive Manufacturing & Laser Prototyping", ChamberCode = "OTHER", SetaCode = "17", Description = "3D printing, advanced additive manufacturing, laser cutting, and precision engineering", Active = true },
            new SicCodeType { Code = "99999", Name = "Unclassified / Pending Verification", ChamberCode = "OTHER", SetaCode = "17", Description = "Provisional unclassified statutory manufacturing category", Active = true },

            // Out-of-Scope Representative SIC Codes (Non-merSETA SETAs for Inter-SETA Boundary Detection)
            new SicCodeType { Code = "50100", Name = "Building Construction & Civil Engineering", ChamberCode = "OTHER", SetaCode = "05", Description = "Construction Education and Training Authority (CETA - SETA 05)", Active = true },
            new SicCodeType { Code = "35100", Name = "Manufacture of Basic Industrial Chemicals", ChamberCode = "OTHER", SetaCode = "03", Description = "Chemical Industries Education & Training Authority (CHIETA - SETA 03)", Active = true },
            new SicCodeType { Code = "61000", Name = "Non-Automotive Wholesale & Retail Trade", ChamberCode = "OTHER", SetaCode = "27", Description = "Wholesale and Retail SETA (W&RSETA - SETA 27)", Active = true },
            new SicCodeType { Code = "81100", Name = "Financial Intermediation & Banking", ChamberCode = "OTHER", SetaCode = "02", Description = "Banking Sector Education and Training Authority (BANKSETA - SETA 02)", Active = true }
        );
    }
}
