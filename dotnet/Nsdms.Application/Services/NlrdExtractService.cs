using System.IO.Compression;
using System.Security.Cryptography;
using System.Text;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Logging;
using Nsdms.Application.Common;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Production batch engine generating statutory SAQA National Learners' Records Database (NLRD / Edu.Dex)
/// flat-file extracts (Files 21 through 30) strictly compliant with SAQA Edu.Dex Release 2 specifications.
/// </summary>
public class NlrdExtractService : INlrdExtractService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly IAuditService _auditService;
    private readonly ILogger<NlrdExtractService> _logger;

    public NlrdExtractService(
        INsdmsDbContextFactory contextFactory,
        IAuditService auditService,
        ILogger<NlrdExtractService> logger)
    {
        _contextFactory = contextFactory;
        _auditService = auditService;
        _logger = logger;
    }

    public async Task<List<StatutoryBatchFileSummary>> GetNlrdFileDescriptorsAsync(CancellationToken cancellationToken = default)
    {
        using var context = await _contextFactory.CreateDbContextAsync();

        var providerCount = await context.TrainingProviders.CountAsync(cancellationToken);
        var providerAccredCount = await context.TrainingProviderQualifications.CountAsync(cancellationToken);
        var personCount = await context.People.CountAsync(cancellationToken);
        var assessorCount = await context.EtqaAssessors.CountAsync(cancellationToken);
        var scopeCount = await context.AssessorModeratorScopes.CountAsync(cancellationToken);
        var learnershipCount = await context.CompanyLearners.CountAsync(l => l.LearnershipId != null, cancellationToken);
        var qualCount = await context.CompanyLearners.CountAsync(l => l.SaqaQualificationId != null, cancellationToken);
        var assessmentCount = await context.LearnerAssessments.CountAsync(cancellationToken);

        return new List<StatutoryBatchFileSummary>
        {
            new() { FileCode = "21", FileTitle = "Provider", Standard = "NLRD", RecordLength = 847, EstimatedRecordCount = providerCount, ExpectedFileNamePattern = "MERS21{yyMMdd}.dat", Description = "SAQA accredited provider identification, addresses, and status." },
            new() { FileCode = "24", FileTitle = "Provider Accreditation", Standard = "NLRD", RecordLength = 135, EstimatedRecordCount = providerAccredCount, ExpectedFileNamePattern = "MERS24{yyMMdd}.dat", Description = "Accredited provider qualification and unit standard offerings." },
            new() { FileCode = "25", FileTitle = "Person Information", Standard = "NLRD", RecordLength = 791, EstimatedRecordCount = personCount, ExpectedFileNamePattern = "MERS25{yyMMdd}.dat", Description = "Person demographic profiles and Washington 6 difficulty ratings." },
            new() { FileCode = "26", FileTitle = "Person Designation", Standard = "NLRD", RecordLength = 157, EstimatedRecordCount = assessorCount, ExpectedFileNamePattern = "MERS26{yyMMdd}.dat", Description = "Assessor and moderator statutory registrations." },
            new() { FileCode = "27", FileTitle = "NQF Designation Registration", Standard = "NLRD", RecordLength = 119, EstimatedRecordCount = scopeCount, ExpectedFileNamePattern = "MERS27{yyMMdd}.dat", Description = "Assessor scope links to registered qualifications and unit standards." },
            new() { FileCode = "28", FileTitle = "Learnership Enrolment/Achievement", Standard = "NLRD", RecordLength = 143, EstimatedRecordCount = learnershipCount, ExpectedFileNamePattern = "MERS28{yyMMdd}.dat", Description = "Learnership agreement commencements, milestones, and awards." },
            new() { FileCode = "29", FileTitle = "Qualification Enrolment/Achievement", Standard = "NLRD", RecordLength = 161, EstimatedRecordCount = qualCount, ExpectedFileNamePattern = "MERS29{yyMMdd}.dat", Description = "Full qualification enrolments, credits, and graduation statuses." },
            new() { FileCode = "30", FileTitle = "Unit Standard Enrolment/Achievement", Standard = "NLRD", RecordLength = 171, EstimatedRecordCount = assessmentCount, ExpectedFileNamePattern = "MERS30{yyMMdd}.dat", Description = "Unit standard assessments and credit achievements." }
        };
    }

    public async Task<StatutoryFileExtractResult> ExtractNlrdFileAsync(string fileCode, DateTime? extractionDate = null, CancellationToken cancellationToken = default)
    {
        var extractDate = extractionDate ?? DateTime.UtcNow;
        var dateStr = extractDate.ToString("yyMMdd");
        var fileName = $"MERS{fileCode}{dateStr}.dat";

        using var context = await _contextFactory.CreateDbContextAsync();
        var sb = new StringBuilder();
        var recordCount = 0;
        var recordLength = 0;
        var title = string.Empty;

        var recordsSb = new StringBuilder();

        switch (fileCode)
        {
            case "21":
                title = "Provider";
                recordLength = 847;
                var providers = await context.TrainingProviders
                    .Include(p => p.Organisation)
                    .Include(p => p.PrimaryContactPerson)
                    .AsNoTracking()
                    .ToListAsync(cancellationToken);

                recordCount = providers.Count;
                foreach (var p in providers)
                {
                    recordsSb.Append(FormatFile21Line(p, extractDate));
                    recordsSb.Append("\r\n");
                }
                break;

            case "24":
                title = "Provider Accreditation";
                recordLength = 135;
                var accreds = await context.TrainingProviderQualifications
                    .Include(q => q.TrainingProvider)
                    .AsNoTracking()
                    .ToListAsync(cancellationToken);

                recordCount = accreds.Count;
                foreach (var a in accreds)
                {
                    recordsSb.Append(FormatFile24Line(a, extractDate));
                    recordsSb.Append("\r\n");
                }
                break;

            case "25":
                title = "Person Information";
                recordLength = 791;
                var persons = await context.People
                    .AsNoTracking()
                    .ToListAsync(cancellationToken);

                recordCount = persons.Count;
                foreach (var p in persons)
                {
                    recordsSb.Append(FormatFile25Line(p, extractDate));
                    recordsSb.Append("\r\n");
                }
                break;

            case "26":
                title = "Person Designation";
                recordLength = 157;
                var assessors = await context.EtqaAssessors
                    .Include(a => a.Person)
                    .AsNoTracking()
                    .ToListAsync(cancellationToken);

                recordCount = assessors.Count;
                foreach (var a in assessors)
                {
                    recordsSb.Append(FormatFile26Line(a, extractDate));
                    recordsSb.Append("\r\n");
                }
                break;

            case "27":
                title = "NQF Designation Registration";
                recordLength = 119;
                var scopes = await context.AssessorModeratorScopes
                    .Include(s => s.EtqaAssessor)
                        .ThenInclude(a => a!.Person)
                    .AsNoTracking()
                    .ToListAsync(cancellationToken);

                recordCount = scopes.Count;
                foreach (var s in scopes)
                {
                    recordsSb.Append(FormatFile27Line(s, extractDate));
                    recordsSb.Append("\r\n");
                }
                break;

            case "28":
                title = "Learnership Enrolment/Achievement";
                recordLength = 143;
                var learnerships = await context.CompanyLearners
                    .Include(l => l.Person)
                    .Include(l => l.TrainingProvider)
                    .Where(l => l.LearnershipId != null)
                    .AsNoTracking()
                    .ToListAsync(cancellationToken);

                recordCount = learnerships.Count;
                foreach (var l in learnerships)
                {
                    recordsSb.Append(FormatFile28Line(l, extractDate));
                    recordsSb.Append("\r\n");
                }
                break;

            case "29":
                title = "Qualification Enrolment/Achievement";
                recordLength = 161;
                var quals = await context.CompanyLearners
                    .Include(l => l.Person)
                    .Include(l => l.TrainingProvider)
                    .Where(l => l.SaqaQualificationId != null)
                    .AsNoTracking()
                    .ToListAsync(cancellationToken);

                recordCount = quals.Count;
                foreach (var q in quals)
                {
                    recordsSb.Append(FormatFile29Line(q, extractDate));
                    recordsSb.Append("\r\n");
                }
                break;

            case "30":
                title = "Unit Standard Enrolment/Achievement";
                recordLength = 171;
                var assessments = await context.LearnerAssessments
                    .Include(a => a.CompanyLearner)
                        .ThenInclude(l => l!.Person)
                    .Include(a => a.CompanyLearner)
                        .ThenInclude(l => l!.TrainingProvider)
                    .AsNoTracking()
                    .ToListAsync(cancellationToken);

                recordCount = assessments.Count;
                foreach (var a in assessments)
                {
                    recordsSb.Append(FormatFile30Line(a, extractDate));
                    recordsSb.Append("\r\n");
                }
                break;

            default:
                throw new ArgumentException($"Unsupported NLRD file code: {fileCode}");
        }

        // SAQA Standard HEADER record (line 1 of every NLRD file)
        var header = FixedFormatWriter.FormatNlrdHeader($"{title} ({fileCode})", recordCount, recordLength, "599");
        sb.Append(header);
        sb.Append("\r\n");
        sb.Append(recordsSb);

        var content = sb.ToString();
        var contentBytes = Encoding.ASCII.GetBytes(content);
        var checksum = ComputeSha256(contentBytes);

        return new StatutoryFileExtractResult
        {
            FileCode = fileCode,
            FileTitle = title,
            FileName = fileName,
            RecordCount = recordCount,
            RecordLength = recordLength,
            FileSizeBytes = contentBytes.LongLength,
            ChecksumSha256 = checksum,
            Content = content,
            ContentBytes = contentBytes
        };
    }

    public async Task<StatutorySubmissionBatch> GenerateFullNlrdBatchAsync(
        int financialYear,
        string? comments = null,
        string? userId = null,
        CancellationToken cancellationToken = default)
    {
        var extractDate = DateTime.UtcNow;
        var dateStr = extractDate.ToString("yyMMdd");
        var batchNumber = $"NLRD-{financialYear}-{extractDate:yyyyMMddHHmmss}";

        var batch = new StatutorySubmissionBatch
        {
            BatchType = "NLRD",
            BatchNumber = batchNumber,
            SubmissionYear = financialYear,
            ExtractionDate = extractDate,
            StatusCode = "Extracted",
            Comments = comments ?? $"Full statutory SAQA NLRD Edu.Dex batch extracted for scheme year {financialYear}."
        };

        var fileCodes = new[] { "21", "24", "25", "26", "27", "28", "29", "30" };
        var memoryStream = new MemoryStream();

        using (var archive = new ZipArchive(memoryStream, ZipArchiveMode.Create, leaveOpen: true))
        {
            var totalRecords = 0;

            foreach (var code in fileCodes)
            {
                var extract = await ExtractNlrdFileAsync(code, extractDate, cancellationToken);
                totalRecords += extract.RecordCount;

                var batchFile = new StatutoryBatchFile
                {
                    FileCode = extract.FileCode,
                    FileTitle = extract.FileTitle,
                    FileName = extract.FileName,
                    RecordCount = extract.RecordCount,
                    RecordLength = extract.RecordLength,
                    FileSizeBytes = extract.FileSizeBytes,
                    ChecksumSha256 = extract.ChecksumSha256,
                    StatusCode = extract.RecordCount > 0 ? "Extracted" : "Empty",
                    FileContent = extract.Content
                };

                batch.Files.Add(batchFile);

                // Add entry to Zip archive
                var zipEntry = archive.CreateEntry(extract.FileName, CompressionLevel.Optimal);
                using var entryStream = zipEntry.Open();
                await entryStream.WriteAsync(extract.ContentBytes, cancellationToken);
            }

            batch.TotalRecords = totalRecords;
        }

        var zipBytes = memoryStream.ToArray();
        var zipFileName = $"NLRD_MERS_{dateStr}.zip";
        batch.ArchiveFileName = zipFileName;
        batch.DigitalSecuritySeal = ComputeSha256(zipBytes);

        // Save batch to database
        using var context = await _contextFactory.CreateDbContextAsync();
        context.StatutorySubmissionBatches.Add(batch);
        await context.SaveChangesAsync(cancellationToken);

        // Double-Write Audit Trail
        await _auditService.LogActionAsync(
            "StatutorySubmissionBatch",
            batch.Id,
            "GENERATE_NLRD_BATCH",
            userId ?? "System",
            null,
            new { batch.BatchNumber, batch.TotalRecords, batch.DigitalSecuritySeal, batch.ArchiveFileName });

        _logger.LogInformation("Generated NLRD batch {BatchNumber} with {TotalRecords} records. Seal: {Seal}",
            batch.BatchNumber, batch.TotalRecords, batch.DigitalSecuritySeal);

        return batch;
    }

    public async Task<StatutoryZipArchiveResult> DownloadNlrdBatchArchiveAsync(int batchId, CancellationToken cancellationToken = default)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        var batch = await context.StatutorySubmissionBatches
            .Include(b => b.Files)
            .FirstOrDefaultAsync(b => b.Id == batchId, cancellationToken);

        if (batch == null)
        {
            throw new KeyNotFoundException($"Statutory submission batch #{batchId} was not found.");
        }

        using var memoryStream = new MemoryStream();
        using (var archive = new ZipArchive(memoryStream, ZipArchiveMode.Create, leaveOpen: true))
        {
            foreach (var file in batch.Files)
            {
                var zipEntry = archive.CreateEntry(file.FileName, CompressionLevel.Optimal);
                using var entryStream = zipEntry.Open();
                var bytes = Encoding.ASCII.GetBytes(file.FileContent ?? string.Empty);
                await entryStream.WriteAsync(bytes, cancellationToken);
            }
        }

        var zipBytes = memoryStream.ToArray();
        return new StatutoryZipArchiveResult
        {
            ArchiveFileName = batch.ArchiveFileName ?? $"NLRD_MERS_{batch.ExtractionDate:yyMMdd}.zip",
            ZipBytes = zipBytes,
            TotalFilesCount = batch.Files.Count,
            TotalRecordsCount = batch.TotalRecords,
            DigitalSecuritySeal = batch.DigitalSecuritySeal ?? ComputeSha256(zipBytes)
        };
    }

    public async Task<List<StatutorySubmissionBatch>> GetNlrdBatchesAsync(CancellationToken cancellationToken = default)
    {
        using var context = await _contextFactory.CreateDbContextAsync();
        return await context.StatutorySubmissionBatches
            .Include(b => b.Files)
            .Where(b => b.BatchType == "NLRD")
            .OrderByDescending(b => b.ExtractionDate)
            .Take(50)
            .AsNoTracking()
            .ToListAsync(cancellationToken);
    }

    // =========================================================================
    // Line Formatters strictly mapping to 8 SAQA NLRD File Specifications
    // =========================================================================

    private static string FormatFile21Line(TrainingProvider p, DateTime date)
    {
        var sb = new StringBuilder(847);
        var org = p.Organisation;
        var contact = p.PrimaryContactPerson;

        sb.Append(FixedFormatWriter.FormatString(p.ProviderCode, 20));                                    // 1. Provider_Code (20)
        sb.Append(FixedFormatWriter.FormatString(p.EtqaId ?? "599", 10));                                // 2. ETQE_Id (10)
        sb.Append(FixedFormatWriter.FormatString(org?.SicCode, 10));                                      // 3. SIC_Code (10)
        sb.Append(FixedFormatWriter.FormatString(p.ProviderName, 128));                                   // 4. Provider_Name (128)
        sb.Append(FixedFormatWriter.FormatString(p.ProviderTypeId ?? "02", 10));                         // 5. Provider_Type_Id (10)
        sb.Append(FixedFormatWriter.FormatString(org?.PostalAddress, 50));                                // 6. Postal_Address_1 (50)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 50));                                      // 7. Postal_Address_2 (50)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 50));                                      // 8. Postal_Address_3 (50)
        sb.Append(FixedFormatWriter.FormatString(org?.PostalAddressPostalCode, 4));                       // 9. Postal_Address_Code (4)
        sb.Append(FixedFormatWriter.FormatString(org?.PhoneNumber, 20));                                  // 10. Phone_Number (20)
        sb.Append(FixedFormatWriter.FormatString(p.FaxNumber ?? org?.FaxNumber, 20));                    // 11. Fax_Number (20)
        sb.Append(FixedFormatWriter.FormatString(p.SarsNumber ?? org?.TaxNumber, 20));                   // 12. Sars_Number (20)
        sb.Append(FixedFormatWriter.FormatString(contact != null ? $"{contact.FirstName} {contact.LastName}" : org?.CompanyName, 50)); // 13. Contact_Name (50)
        sb.Append(FixedFormatWriter.FormatString(contact?.Email ?? org?.PrimaryContactPerson?.Email, 50)); // 14. Contact_Email (50)
        sb.Append(FixedFormatWriter.FormatString(contact?.PhoneNumber ?? org?.PhoneNumber, 20));         // 15. Contact_Phone (20)
        sb.Append(FixedFormatWriter.FormatString(contact?.CellNumber, 20));                              // 16. Contact_Cell (20)
        sb.Append(FixedFormatWriter.FormatString(p.AccreditationNumber, 20));                             // 17. Accreditation_Num (20)
        sb.Append(FixedFormatWriter.FormatDate(p.AccreditationStartDate, "yyyyMMdd", 8));                 // 18. Start_Date (8)
        sb.Append(FixedFormatWriter.FormatDate(p.AccreditationEndDate, "yyyyMMdd", 8));                   // 19. End_Date (8)
        sb.Append(FixedFormatWriter.FormatString(p.EtqaDecisionNumber, 20));                              // 20. Decision_Number (20)
        sb.Append(FixedFormatWriter.FormatString(p.ProviderClassId ?? "02", 10));                        // 21. Provider_Class_Id (10)
        sb.Append(FixedFormatWriter.FormatString(p.ProviderStatusId ?? "01", 10));                       // 22. Provider_Status_Id (10)
        sb.Append(FixedFormatWriter.FormatString(org?.ProvinceCode ?? "GP", 2));                          // 23. Province_Code (2)
        sb.Append(FixedFormatWriter.FormatString(org?.CountryCode ?? "ZA", 4));                           // 24. Country_Code (4)
        sb.Append(FixedFormatWriter.FormatString("-26", 3));                                              // 25. Lat_Deg (3)
        sb.Append(FixedFormatWriter.FormatString("12", 2));                                               // 26. Lat_Min (2)
        sb.Append(FixedFormatWriter.FormatString("00.000", 6));                                           // 27. Lat_Sec (6)
        sb.Append(FixedFormatWriter.FormatString("28", 2));                                               // 28. Long_Deg (2)
        sb.Append(FixedFormatWriter.FormatString("02", 2));                                               // 29. Long_Min (2)
        sb.Append(FixedFormatWriter.FormatString("00.000", 6));                                           // 30. Long_Sec (6)
        sb.Append(FixedFormatWriter.FormatString(org?.PhysicalAddress, 50));                              // 31. Physical_Address_1 (50)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 50));                                      // 32. Physical_Address_2 (50)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 50));                                      // 33. Physical_Address_3 (50)
        sb.Append(FixedFormatWriter.FormatString(org?.PhysicalAddressPostalCode, 4));                     // 34. Physical_Address_Code (4)
        sb.Append(FixedFormatWriter.FormatString(p.WebsiteUrl, 50));                                      // 35. Web_Address (50)
        sb.Append(FixedFormatWriter.FormatDate(date, "yyyyMMdd", 8));                                     // 36. Date_Stamp (8)

        return sb.ToString();
    }

    private static string FormatFile24Line(TrainingProviderQualification a, DateTime date)
    {
        var sb = new StringBuilder(135);
        var p = a.TrainingProvider;

        sb.Append(FixedFormatWriter.FormatString(string.Empty, 10));                                      // 1. Learnership_Id (10)
        sb.Append(FixedFormatWriter.FormatString(a.SaqaQualificationId.ToString(), 10));                 // 2. Qualification_Id (10)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 10));                                      // 3. Unit_Standard_Id (10)
        sb.Append(FixedFormatWriter.FormatString(p?.ProviderCode, 20));                                   // 4. Provider_Code (20)
        sb.Append(FixedFormatWriter.FormatString(p?.EtqaId ?? "599", 10));                                // 5. Provider_Etqa_Id (10)
        sb.Append(FixedFormatWriter.FormatString(p?.AccreditationNumber, 20));                             // 6. Provider_Accreditation_Num (20)
        sb.Append(FixedFormatWriter.FormatString("Y", 1));                                                // 7. Provider_Accredit_Assessor_Ind (1)
        sb.Append(FixedFormatWriter.FormatDate(a.CreatedAt, "yyyyMMdd", 8));                              // 8. Provider_Accred_Start_Date (8)
        sb.Append(FixedFormatWriter.FormatDate(a.ExpiryDate, "yyyyMMdd", 8));                             // 9. Provider_Accred_End_Date (8)
        sb.Append(FixedFormatWriter.FormatString(p?.EtqaDecisionNumber ?? "ETQA/DEC/2026", 20));          // 10. Etqa_Decision_Number (20)
        sb.Append(FixedFormatWriter.FormatString(a.AccreditationStatusCode ?? "01", 10));                // 11. Provider_Accred_Status_Code (10)
        sb.Append(FixedFormatWriter.FormatDate(date, "yyyyMMdd", 8));                                     // 12. Date_Stamp (8)

        return sb.ToString();
    }

    private static string FormatFile25Line(Person p, DateTime date)
    {
        var sb = new StringBuilder(791);

        sb.Append(FixedFormatWriter.FormatString(p.RsaIdNumber, 15));                                     // 1. National_Id (15)
        sb.Append(FixedFormatWriter.FormatString(p.PassportNumber, 20));                                  // 2. Person_Alternate_Id (20)
        sb.Append(FixedFormatWriter.FormatString(p.AlternateIdTypeId ?? "521", 3));                       // 3. Alternative_Id_Type (3)
        sb.Append(FixedFormatWriter.FormatString(p.EquityCode ?? "BA", 10));                              // 4. Equity_Code (10)
        sb.Append(FixedFormatWriter.FormatString(p.NationalityCode ?? "SA", 3));                          // 5. Nationality_Code (3)
        sb.Append(FixedFormatWriter.FormatString(p.HomeLanguageCode ?? "01", 10));                        // 6. Home_Language_Code (10)
        sb.Append(FixedFormatWriter.FormatString(p.GenderCode ?? "M", 1));                                // 7. Gender_Code (1)
        sb.Append(FixedFormatWriter.FormatString(p.CitizenStatusCode ?? "SA", 10));                       // 8. Citizen_Resident_Status_Code (10)
        sb.Append(FixedFormatWriter.FormatString("01", 2));                                               // 9. Socioeconomic_Status_Code (2)
        sb.Append(FixedFormatWriter.FormatString(p.DisabilityCode ?? "N", 10));                           // 10. Disability_Status_Code (10)
        sb.Append(FixedFormatWriter.FormatString(p.LastName, 45));                                        // 11. Person_Last_Name (45)
        sb.Append(FixedFormatWriter.FormatString(p.FirstName, 26));                                       // 12. Person_First_Name (26)
        sb.Append(FixedFormatWriter.FormatString(p.MiddleName, 50));                                      // 13. Person_Middle_Name (50)
        sb.Append(FixedFormatWriter.FormatString(p.Title ?? "MR", 10));                                   // 14. Person_Title (10)
        sb.Append(FixedFormatWriter.FormatDate(p.DateOfBirth, "yyyyMMdd", 8));                            // 15. Person_Birth_Date (8)
        sb.Append(FixedFormatWriter.FormatString(p.PhysicalAddress, 50));                                 // 16. Person_Home_Address_1 (50)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 50));                                      // 17. Person_Home_Address_2 (50)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 50));                                      // 18. Person_Home_Address_3 (50)
        sb.Append(FixedFormatWriter.FormatString(p.PostalAddress, 50));                                   // 19. Person_Postal_Address_1 (50)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 50));                                      // 20. Person_Postal_Address_2 (50)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 50));                                      // 21. Person_Postal_Address_3 (50)
        sb.Append(FixedFormatWriter.FormatString(p.PhysicalAddressPostalCode, 4));                        // 22. Person_Home_Addr_Postal_Code (4)
        sb.Append(FixedFormatWriter.FormatString(p.PostalAddressPostalCode, 4));                          // 23. Person_Postal_Addr_Post_Code (4)
        sb.Append(FixedFormatWriter.FormatString(p.PhoneNumber, 20));                                     // 24. Person_Phone_Number (20)
        sb.Append(FixedFormatWriter.FormatString(p.CellNumber, 20));                                      // 25. Person_Cell_Phone_Number (20)
        sb.Append(FixedFormatWriter.FormatString(p.FaxNumber, 20));                                       // 26. Person_Fax_Number (20)
        sb.Append(FixedFormatWriter.FormatString(p.Email, 50));                                           // 27. Person_Email_Address (50)
        sb.Append(FixedFormatWriter.FormatString(p.ProvinceCode ?? "GP", 2));                             // 28. Province_Code (2)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 20));                                      // 29. Provider_Code (20)
        sb.Append(FixedFormatWriter.FormatString("599", 10));                                             // 30. Provider_Etqa_Id (10)
        sb.Append(FixedFormatWriter.FormatString(p.PreviousLastName, 45));                                // 31. Person_Previous_Lastname (45)
        sb.Append(FixedFormatWriter.FormatString(p.PreviousAlternateId, 20));                             // 32. Person_Previous_Alternate_Id (20)
        sb.Append(FixedFormatWriter.FormatString(p.PreviousAlternateIdTypeId, 3));                        // 33. Person_Previous_Alternative_Id_Type (3)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 20));                                      // 34. Person_Previous_Provider_Code (20)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 10));                                      // 35. Person_Previous_Provider_Etqa_Id (10)
        sb.Append(FixedFormatWriter.FormatString(p.SeeingRatingId ?? "01", 2));                           // 36. Seeing_Rating_Id (2)
        sb.Append(FixedFormatWriter.FormatString(p.HearingRatingId ?? "01", 2));                          // 37. Hearing_Rating_Id (2)
        sb.Append(FixedFormatWriter.FormatString(p.CommunicatingRatingId ?? "01", 2));                   // 38. Communicating_Rating_Id (2)
        sb.Append(FixedFormatWriter.FormatString(p.WalkingRatingId ?? "01", 2));                          // 39. Walking_Rating_Id (2)
        sb.Append(FixedFormatWriter.FormatString(p.RememberingRatingId ?? "01", 2));                      // 40. Remembering_Rating_Id (2)
        sb.Append(FixedFormatWriter.FormatString(p.SelfCareRatingId ?? "01", 2));                         // 41. Selfcare_Rating_Id (2)
        sb.Append(FixedFormatWriter.FormatDate(date, "yyyyMMdd", 8));                                     // 42. Date_Stamp (8)

        return sb.ToString();
    }

    private static string FormatFile26Line(EtqaAssessor a, DateTime date)
    {
        var sb = new StringBuilder(157);
        var person = a.Person;

        sb.Append(FixedFormatWriter.FormatString(person?.RsaIdNumber, 15));                                // 1. National_Id (15)
        sb.Append(FixedFormatWriter.FormatString(person?.PassportNumber, 20));                             // 2. Person_Alternate_Id (20)
        sb.Append(FixedFormatWriter.FormatString("521", 3));                                              // 3. Alternative_Id_Type (3)
        sb.Append(FixedFormatWriter.FormatString(a.DesignationTypeId ?? "01", 5));                        // 4. Designation_Id (5)
        sb.Append(FixedFormatWriter.FormatString(a.RegistrationNumber, 20));                              // 5. Designation_Registration_Number (20)
        sb.Append(FixedFormatWriter.FormatString(a.EtqaId ?? "599", 10));                                 // 6. Designation_ETQA_Id (10)
        sb.Append(FixedFormatWriter.FormatDate(a.StartDate, "yyyyMMdd", 8));                              // 7. Designation_Start_Date (8)
        sb.Append(FixedFormatWriter.FormatDate(a.EndDate, "yyyyMMdd", 8));                                // 8. Designation_End_Date (8)
        sb.Append(FixedFormatWriter.FormatString(a.DesignationStructureStatusId ?? "01", 10));             // 9. Structure_Status_Id (10)
        sb.Append(FixedFormatWriter.FormatString(a.EtqeDecisionNumber ?? "ETQA/2026/01", 20));            // 10. Etqa_Decision_Number (20)
        sb.Append(FixedFormatWriter.FormatString(a.TrainingProvider?.ProviderCode, 20));                  // 11. Provider_Code (20)
        sb.Append(FixedFormatWriter.FormatString(a.TrainingProvider?.EtqaId ?? "599", 10));               // 12. Provider_ETQA_ID (10)
        sb.Append(FixedFormatWriter.FormatDate(date, "yyyyMMdd", 8));                                     // 13. Date_Stamp (8)

        return sb.ToString();
    }

    private static string FormatFile27Line(AssessorModeratorScope s, DateTime date)
    {
        var sb = new StringBuilder(119);
        var a = s.EtqaAssessor;

        sb.Append(FixedFormatWriter.FormatString(string.Empty, 10));                                      // 1. Learnership_Id (10)
        sb.Append(FixedFormatWriter.FormatString(s.SaqaQualificationId.ToString(), 10));                  // 2. Qualification_Id (10)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 10));                                      // 3. Unit_Standard_Id (10)
        sb.Append(FixedFormatWriter.FormatString(a?.DesignationTypeId ?? "01", 5));                        // 4. Designation_Id (5)
        sb.Append(FixedFormatWriter.FormatString(a?.RegistrationNumber, 20));                             // 5. Designation_Registration_Number (20)
        sb.Append(FixedFormatWriter.FormatString(a?.EtqaId ?? "599", 10));                                // 6. Designation_ETQA_Id (10)
        sb.Append(FixedFormatWriter.FormatDate(s.CreatedAt, "yyyyMMdd", 8));                              // 7. NQF_Designation_Start_Date (8)
        sb.Append(FixedFormatWriter.FormatDate(s.ExpiryDate, "yyyyMMdd", 8));                             // 8. NQF_Designation_End_Date (8)
        sb.Append(FixedFormatWriter.FormatString(a?.EtqeDecisionNumber ?? "ETQA/2026/01", 20));            // 9. Etqa_Decision_Number (20)
        sb.Append(FixedFormatWriter.FormatString(s.RegistrationStatusCode ?? "01", 10));                  // 10. NQF_Desig_Status_Code (10)
        sb.Append(FixedFormatWriter.FormatDate(date, "yyyyMMdd", 8));                                     // 11. Date_Stamp (8)

        return sb.ToString();
    }

    private static string FormatFile28Line(CompanyLearner l, DateTime date)
    {
        var sb = new StringBuilder(143);
        var p = l.Person;

        sb.Append(FixedFormatWriter.FormatString(p?.RsaIdNumber, 15));                                    // 1. National_Id (15)
        sb.Append(FixedFormatWriter.FormatString(p?.PassportNumber, 20));                                 // 2. Person_Alternate_Id (20)
        sb.Append(FixedFormatWriter.FormatString("521", 3));                                              // 3. Alternative_Id_Type (3)
        sb.Append(FixedFormatWriter.FormatString(l.LearnershipId ?? "18Q1800262", 10));                   // 4. Learnership_Id (10)
        sb.Append(FixedFormatWriter.FormatString(l.EnrolmentStatusId ?? "01", 3));                        // 5. Learner_Achievement_Status_Id (3)
        sb.Append(FixedFormatWriter.FormatString(l.AssessorRegistrationNumber, 20));                      // 6. Assessor_Registration_Number (20)
        sb.Append(FixedFormatWriter.FormatDate(l.CompletionDate, "yyyyMMdd", 8));                         // 7. Learner_Achievement_Date (8)
        sb.Append(FixedFormatWriter.FormatDate(l.CommencementDate, "yyyyMMdd", 8));                       // 8. Learner_Enrolled_Date (8)
        sb.Append(FixedFormatWriter.FormatString(l.PracticalProviderCode ?? l.TrainingProvider?.ProviderCode, 20)); // 9. Provider_Code (20)
        sb.Append(FixedFormatWriter.FormatString(l.PracticalProviderEtqaId ?? "599", 10));                // 10. Provider_Etqa_Id (10)
        sb.Append(FixedFormatWriter.FormatString(l.AssessorEtqaId ?? "599", 10));                         // 11. Assessor_Etqa_Id (10)
        sb.Append(FixedFormatWriter.FormatDate(l.CompletionDate, "yyyyMMdd", 8));                         // 12. Certification_Date (8)
        sb.Append(FixedFormatWriter.FormatDate(date, "yyyyMMdd", 8));                                     // 13. Date_Stamp (8)

        return sb.ToString();
    }

    private static string FormatFile29Line(CompanyLearner l, DateTime date)
    {
        var sb = new StringBuilder(161);
        var p = l.Person;

        sb.Append(FixedFormatWriter.FormatString(p?.RsaIdNumber, 15));                                    // 1. National_Id (15)
        sb.Append(FixedFormatWriter.FormatString(p?.PassportNumber, 20));                                 // 2. Person_Alternate_Id (20)
        sb.Append(FixedFormatWriter.FormatString("521", 3));                                              // 3. Alternative_Id_Type (3)
        sb.Append(FixedFormatWriter.FormatString(l.SaqaQualificationId?.ToString() ?? "24418", 10));      // 4. Qualification_Id (10)
        sb.Append(FixedFormatWriter.FormatString(l.EnrolmentStatusId ?? "01", 3));                        // 5. Learner_Achievement_Status_Id (3)
        sb.Append(FixedFormatWriter.FormatString(l.AssessorRegistrationNumber, 20));                      // 6. Assessor_Registration_Number (20)
        sb.Append(FixedFormatWriter.FormatString(l.EnrolmentTypeId ?? "01", 3));                          // 7. Learner_Achievement_Type_Id (3)
        sb.Append(FixedFormatWriter.FormatDate(l.EnrolmentStatusDate ?? l.CompletionDate, "yyyyMMdd", 8)); // 8. Learner_Achievement_Date (8)
        sb.Append(FixedFormatWriter.FormatDate(l.CommencementDate, "yyyyMMdd", 8));                       // 9. Learner_Enrolled_Date (8)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 3));                                       // 10. Honours_Classification (3)
        sb.Append(FixedFormatWriter.FormatString(l.PartOfId ?? "01", 2));                                 // 11. Part_of (2)
        sb.Append(FixedFormatWriter.FormatString(l.LearnershipId, 10));                                   // 12. Learnership_Id (10)
        sb.Append(FixedFormatWriter.FormatString(l.PracticalProviderCode ?? l.TrainingProvider?.ProviderCode, 20)); // 13. Provider_Code (20)
        sb.Append(FixedFormatWriter.FormatString(l.PracticalProviderEtqaId ?? "599", 10));                // 14. Provider_Etqa_Id (10)
        sb.Append(FixedFormatWriter.FormatString(l.AssessorEtqaId ?? "599", 10));                         // 15. Assessor_Etqa_Id (10)
        sb.Append(FixedFormatWriter.FormatDate(l.CompletionDate, "yyyyMMdd", 8));                         // 16. Certification_Date (8)
        sb.Append(FixedFormatWriter.FormatDate(date, "yyyyMMdd", 8));                                     // 17. Date_Stamp (8)

        return sb.ToString();
    }

    private static string FormatFile30Line(LearnerAssessment a, DateTime date)
    {
        var sb = new StringBuilder(171);
        var l = a.CompanyLearner;
        var p = l?.Person;

        sb.Append(FixedFormatWriter.FormatString(p?.RsaIdNumber, 15));                                    // 1. National_Id (15)
        sb.Append(FixedFormatWriter.FormatString(p?.PassportNumber, 20));                                 // 2. Person_Alternate_Id (20)
        sb.Append(FixedFormatWriter.FormatString("521", 3));                                              // 3. Alternative_Id_Type (3)
        sb.Append(FixedFormatWriter.FormatInteger(a.UnitStandardId ?? 119472, 10, zeroPad: false));       // 4. Unit_Standard_Id (10)
        sb.Append(FixedFormatWriter.FormatString(a.EnrolmentStatusId ?? "02", 3));                        // 5. Learner_Achievement_Status_Id (3)
        sb.Append(FixedFormatWriter.FormatString(a.AssessorRegistrationNumber, 20));                      // 6. Assessor_Registration_Number (20)
        sb.Append(FixedFormatWriter.FormatString(a.EnrolmentTypeId ?? "01", 3));                          // 7. Learner_Achievement_Type_Id (3)
        sb.Append(FixedFormatWriter.FormatDate(a.AssessmentDate, "yyyyMMdd", 8));                         // 8. Learner_Achievement_Date (8)
        sb.Append(FixedFormatWriter.FormatDate(l?.CommencementDate, "yyyyMMdd", 8));                      // 9. Learner_Enrolled_Date (8)
        sb.Append(FixedFormatWriter.FormatString(string.Empty, 3));                                       // 10. Honours_Classification (3)
        sb.Append(FixedFormatWriter.FormatString(a.PartOfId ?? "01", 2));                                 // 11. Part_of (2)
        sb.Append(FixedFormatWriter.FormatString(l?.SaqaQualificationId?.ToString(), 10));                // 12. Qualification_Id (10)
        sb.Append(FixedFormatWriter.FormatString(l?.LearnershipId, 10));                                  // 13. Learnership_Id (10)
        sb.Append(FixedFormatWriter.FormatString(l?.PracticalProviderCode ?? l?.TrainingProvider?.ProviderCode, 20)); // 14. Provider_Code (20)
        sb.Append(FixedFormatWriter.FormatString(l?.PracticalProviderEtqaId ?? "599", 10));               // 15. Provider_Etqa_Id (10)
        sb.Append(FixedFormatWriter.FormatString(a.AssessorEtqaId ?? "599", 10));                         // 16. Assessor_Etqa_Id (10)
        sb.Append(FixedFormatWriter.FormatDate(a.AssessmentDate, "yyyyMMdd", 8));                         // 17. Certification_Date (8)
        sb.Append(FixedFormatWriter.FormatDate(date, "yyyyMMdd", 8));                                     // 18. Date_Stamp (8)

        return sb.ToString();
    }

    private static string ComputeSha256(byte[] data)
    {
        using var sha256 = SHA256.Create();
        var hash = sha256.ComputeHash(data);
        return Convert.ToHexString(hash).ToLowerInvariant();
    }
}
