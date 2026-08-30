using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

public class NonSetaVerificationService : INonSetaVerificationService
{
    private readonly INsdmsDbContextFactory _contextFactory;
    private readonly AuditService _audit;

    public NonSetaVerificationService(INsdmsDbContextFactory contextFactory, AuditService audit)
    {
        _contextFactory = contextFactory;
        _audit = audit;
    }

    public async Task<NonSetaCompany> RegisterNonSetaCompanyAsync(
        string companyName,
        string primarySetaCode,
        string? sdlNumber,
        string? regNumber,
        string email,
        string phone,
        string? physicalAddress,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var nonSeta = new NonSetaCompany
        {
            CompanyName = companyName,
            PrimarySetaCode = primarySetaCode,
            SdlNumber = sdlNumber,
            CompanyRegistrationNumber = regNumber,
            Email = email,
            PhoneNumber = phone,
            PhysicalAddress = physicalAddress,
            IsActive = true,
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.NonSetaCompanies.Add(nonSeta);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "NonSetaCompany", nonSeta.Id, "RegisterNonSetaCompany", currentUsername, null, nonSeta);
        await db.SaveChangesAsync();

        return nonSeta;
    }

    public async Task<NonSetaQualificationsCompletion> SubmitNonSetaQualificationForVerificationAsync(
        int personId,
        string originatingSetaCode,
        string qualificationTitle,
        string? saqaQualId,
        int nqfLevel,
        int totalCredits,
        string externalCertificateNumber,
        int? nonSetaCompanyId = null,
        int? companyLearnerId = null,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var person = await db.People.FindAsync(personId);
        if (person == null)
        {
            throw new KeyNotFoundException($"Person with ID {personId} not found.");
        }

        var completion = new NonSetaQualificationsCompletion
        {
            PersonId = personId,
            OriginatingSetaCode = originatingSetaCode,
            QualificationTitle = qualificationTitle,
            SaqaQualificationId = saqaQualId,
            NqfLevel = nqfLevel,
            TotalCreditsAchieved = totalCredits,
            ExternalCertificateNumber = externalCertificateNumber,
            NonSetaCompanyId = nonSetaCompanyId,
            CompanyLearnerId = companyLearnerId,
            AchievementDate = DateTime.UtcNow,
            VerificationStatusCode = "PendingVerification",
            CreatedAt = DateTime.UtcNow,
            CreatedBy = currentUsername
        };

        db.NonSetaQualificationsCompletions.Add(completion);
        await db.SaveChangesAsync();

        _audit.LogAction(db, "NonSetaQualificationsCompletion", completion.Id, "SubmitNonSetaVerification", currentUsername, null, completion);
        await db.SaveChangesAsync();

        return completion;
    }

    public async Task<NonSetaQualificationsCompletion> EndorseNonSetaQualificationAsync(
        int verificationId,
        bool isApproved,
        string? endorsementNotes,
        string currentUsername = "SYSTEM")
    {
        using var db = await _contextFactory.CreateDbContextAsync();

        var comp = await db.NonSetaQualificationsCompletions.FirstOrDefaultAsync(n => n.Id == verificationId);
        if (comp == null)
        {
            throw new KeyNotFoundException($"NonSetaQualificationsCompletion with ID {verificationId} not found.");
        }

        var before = new { comp.VerificationStatusCode, comp.EndorsementDate };
        comp.VerificationStatusCode = isApproved ? "EndorsedByMerSeta" : "Rejected";
        comp.EndorsementDate = DateTime.UtcNow;
        comp.EndorsedByUserId = currentUsername;
        comp.EndorsementNotes = endorsementNotes;
        comp.ModifiedAt = DateTime.UtcNow;
        comp.ModifiedBy = currentUsername;

        _audit.LogAction(db, "NonSetaQualificationsCompletion", comp.Id, "EndorseNonSetaQualification", currentUsername, before, comp);
        await db.SaveChangesAsync();

        return comp;
    }

    public async Task<NonSetaQualificationsCompletion?> GetVerificationByIdAsync(int id)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.NonSetaQualificationsCompletions
            .Include(n => n.Person)
            .Include(n => n.NonSetaCompany)
            .Include(n => n.CompanyLearner)
            .FirstOrDefaultAsync(n => n.Id == id);
    }

    public async Task<List<NonSetaQualificationsCompletion>> GetVerificationsAsync(string? statusCode = null, string? originatingSeta = null)
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        var query = db.NonSetaQualificationsCompletions
            .Include(n => n.Person)
            .Include(n => n.NonSetaCompany)
            .AsQueryable();

        if (!string.IsNullOrWhiteSpace(statusCode))
        {
            query = query.Where(n => n.VerificationStatusCode == statusCode);
        }

        if (!string.IsNullOrWhiteSpace(originatingSeta))
        {
            query = query.Where(n => n.OriginatingSetaCode == originatingSeta);
        }

        return await query.OrderByDescending(n => n.CreatedAt).ToListAsync();
    }

    public async Task<List<NonSetaCompany>> GetNonSetaCompaniesAsync()
    {
        using var db = await _contextFactory.CreateDbContextAsync();
        return await db.NonSetaCompanies.OrderBy(c => c.CompanyName).ToListAsync();
    }
}
