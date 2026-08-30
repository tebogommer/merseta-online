using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class EtqaServiceTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, EtqaService service) CreateTestContext()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new EtqaService(factory, audit);
        return (factory, db, audit, service);
    }

    [Fact]
    public async Task RegisterAssessorAsync_GeneratesRegistrationNumberAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Themba", LastName = "Nkosi", RsaIdNumber = "8001015009087" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var assessor = new EtqaAssessor
        {
            PersonId = person.Id,
            EtqaRole = "Assessor"
        };

        // Act
        var registered = await service.RegisterAssessorAsync(assessor, "EtqaAdmin");

        // Assert
        Assert.True(registered.Id > 0);
        Assert.StartsWith("ASM-", registered.RegistrationNumber);
        Assert.Equal("Registered", registered.StatusCode);
        Assert.Equal("EtqaAdmin", registered.CreatedBy);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "EtqaAssessor" && a.RecordId == registered.Id);
        Assert.NotNull(auditLog);
        Assert.Equal("RegisterAssessor", auditLog.ActionName);
    }

    [Fact]
    public async Task RegisterModerator_GeneratesModPrefix()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Nalini", LastName = "Moodley" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var moderator = new EtqaAssessor
        {
            PersonId = person.Id,
            EtqaRole = "Moderator"
        };

        // Act
        var registered = await service.RegisterAssessorAsync(moderator);

        // Assert
        Assert.StartsWith("MOD-", registered.RegistrationNumber);
    }

    [Fact]
    public async Task AddScopeAsync_AddsScopeAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Scope", LastName = "Person" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var assessor = await service.RegisterAssessorAsync(new EtqaAssessor { PersonId = person.Id });

        var scope = new AssessorModeratorScope
        {
            EtqaAssessorId = assessor.Id,
            SaqaQualificationId = 65409,
            QualificationTitle = "National Certificate: Mechanical Engineering",
            RegistrationStatusCode = "Active",
            ExpiryDate = DateTime.UtcNow.AddYears(3)
        };

        // Act
        var added = await service.AddScopeAsync(scope, "ScopeOfficer");

        // Assert
        Assert.True(added.Id > 0);
        Assert.Equal("ScopeOfficer", added.CreatedBy);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "AssessorModeratorScope");
        Assert.NotNull(auditLog);
        Assert.Equal("AddScope", auditLog.ActionName);
    }

    [Fact]
    public async Task RemoveScopeAsync_RemovesScope()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var person = new Person { FirstName = "Scope", LastName = "Remover" };
        db.People.Add(person);
        await db.SaveChangesAsync();

        var assessor = await service.RegisterAssessorAsync(new EtqaAssessor { PersonId = person.Id });
        var scope = await service.AddScopeAsync(new AssessorModeratorScope
        {
            EtqaAssessorId = assessor.Id,
            SaqaQualificationId = 12345,
            QualificationTitle = "Welding Application NQF 3"
        });

        // Act
        var removed = await service.RemoveScopeAsync(scope.Id, "Admin");

        // Assert
        Assert.True(removed);
        var inDb = await db.AssessorModeratorScopes.FindAsync(scope.Id);
        Assert.Null(inDb);
    }

    [Fact]
    public async Task RecordLearnerAssessmentAsync_CreatesAssessmentRecordAndLogsAudit()
    {
        // Arrange
        var (factory, db, audit, service) = CreateTestContext();

        var assessorPerson = new Person { FirstName = "Assessor", LastName = "John" };
        var learnerPerson = new Person { FirstName = "Learner", LastName = "Jane" };
        var org = new Organisation { CompanyName = "Engineering Training Center", SdlNumber = "L123" };
        db.People.AddRange(assessorPerson, learnerPerson);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var assessor = await service.RegisterAssessorAsync(new EtqaAssessor { PersonId = assessorPerson.Id });

        var assessment = new LearnerAssessment
        {
            EtqaAssessorId = assessor.Id,
            PersonId = learnerPerson.Id,
            OrganisationId = org.Id,
            QualificationTitle = "National Certificate: Mechanical Engineering",
            AssessmentDate = DateTime.UtcNow.Date,
            CompetencyStatusCode = "Competent"
        };

        // Act
        var recorded = await service.RecordLearnerAssessmentAsync(assessment, "AssessorJohn");

        // Assert
        Assert.True(recorded.Id > 0);
        Assert.Equal("Competent", recorded.CompetencyStatusCode);

        var learnerAssessments = await service.GetAssessmentsForLearnerAsync(learnerPerson.Id);
        Assert.Single(learnerAssessments);
        Assert.Equal("National Certificate: Mechanical Engineering", learnerAssessments[0].QualificationTitle);

        var auditLog = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "LearnerAssessment");
        Assert.NotNull(auditLog);
    }
}
