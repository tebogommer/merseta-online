using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common.Models;
using Nsdms.Application.Services;
using Nsdms.Application.Validation;
using Nsdms.Domain.Entities;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class LearnerEnrolmentModernisationTests
{
    private static (TestDbContextFactory factory, NsdmsDbContext db, AuditService audit, LearnerService service) CreateHarness()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var db = (NsdmsDbContext)factory.CreateDbContext();
        var audit = new AuditService(factory);
        var service = new LearnerService(factory, audit);

        return (factory, db, audit, service);
    }

    [Fact]
    public void LearnerEnrolment_InheritsCompanyLearner_AndSharesIdAndAliases()
    {
        var enrolment = new LearnerEnrolment
        {
            Id = 42,
            LearnerContractNumber = "LRN-2026-0042",
            PreviousCompanyLearnerId = 10
        };

        // Option A Aliasing Tests
        Assert.Equal(42, enrolment.Id);
        Assert.Equal(42, enrolment.EnrolmentId);
        Assert.Equal(10, enrolment.PreviousEnrolmentId);

        enrolment.EnrolmentId = 99;
        Assert.Equal(99, enrolment.Id);

        enrolment.PreviousEnrolmentId = 55;
        Assert.Equal(55, enrolment.PreviousCompanyLearnerId);
    }

    [Fact]
    public void ChildEntities_HaveEnrolmentIdAliases()
    {
        var transfer = new LearnerEnrolmentTransfer { CompanyLearnerId = 101 };
        Assert.Equal(101, transfer.EnrolmentId);
        transfer.EnrolmentId = 202;
        Assert.Equal(202, transfer.CompanyLearnerId);

        var ext = new LearnerEnrolmentExtension { CompanyLearnerId = 303 };
        Assert.Equal(303, ext.EnrolmentId);

        var lostTime = new LearnerEnrolmentLostTime { CompanyLearnerId = 404 };
        Assert.Equal(404, lostTime.EnrolmentId);

        var term = new LearnerEnrolmentTermination { CompanyLearnerId = 505 };
        Assert.Equal(505, term.EnrolmentId);

        var change = new LearnerEnrolmentChangeRequest { CompanyLearnerId = 606 };
        Assert.Equal(606, change.EnrolmentId);
    }

    [Fact]
    public void LearnerEnrolmentDomainValidator_ProducesIdenticalResults()
    {
        var learner = new LearnerEnrolment
        {
            PartOfId = "02", // Part of Qualification
            SaqaQualificationId = null // Violates rule
        };

        var errorsDirect = CompanyLearnerDomainValidator.Validate(learner);
        var errorsAliased = LearnerEnrolmentDomainValidator.Validate(learner);

        Assert.NotEmpty(errorsDirect);
        Assert.Equal(errorsDirect.Count, errorsAliased.Count);
        Assert.Equal(errorsDirect[0].RuleCode, errorsAliased[0].RuleCode);

        // Document requirements parity
        var docs = LearnerEnrolmentDomainValidator.GetRequiredBursaryDocuments("New", "Employed");
        Assert.NotEmpty(docs);
        Assert.Contains(docs, d => d.DocumentCode == "DOC-BUR-11");
    }

    [Fact]
    public void LearnerEnrolmentDto_ExposesEnrolmentIdProperty()
    {
        var dto = new LearnerEnrolmentDto(
            CompanyLearnerId: 777,
            LearnerContractNumber: "LRN-2026-777",
            LearningProgrammeTypeCode: "01",
            LearningProgrammeTypeName: "Apprenticeship",
            QualificationTitle: "Welder",
            NqfLevel: 4,
            SaqaQualificationId: "12345",
            OFOCode: "651202",
            EnrolmentStatusId: "01",
            EnrolmentStatusName: "Registered",
            RegistrationDate: DateTime.UtcNow,
            CommencementDate: DateTime.UtcNow,
            CompletionDate: null,
            EmployerName: "Toyota SA",
            EmployerId: 10,
            TrainingProviderName: "Technical College",
            TrainingProviderId: 20
        );

        Assert.Equal(777, dto.CompanyLearnerId);
        Assert.Equal(777, dto.EnrolmentId);
    }

    [Fact]
    public async Task LearnerService_ModernEnrolmentMethods_WorkPolymorphically()
    {
        var (factory, db, audit, service) = CreateHarness();

        var person = new Person { FirstName = "Sipho", LastName = "Nkosi", RsaIdNumber = "0105055000088" };
        var org = new Organisation { CompanyName = "Bell Equipment", SdlNumber = "L123456789" };
        db.People.Add(person);
        db.Organisations.Add(org);
        await db.SaveChangesAsync();

        var enrolment = new LearnerEnrolment
        {
            PersonId = person.Id,
            OrganisationId = org.Id,
            QualificationTitle = "National Certificate: Mechanical Fitting",
            LearningProgrammeTypeCode = "01",
            EnrolmentStatusCode = "Registered",
            SubmissionDate = DateTime.UtcNow,
            LearnerSignatureDate = DateTime.UtcNow
        };

        // Test RegisterEnrolmentAsync
        var registered = await service.RegisterEnrolmentAsync(enrolment, "TEST_USER");
        Assert.NotNull(registered);
        Assert.True(registered.Id > 0);
        Assert.Equal(registered.Id, registered.EnrolmentId);

        // Test GetEnrolmentByIdAsync
        var fetched = await service.GetEnrolmentByIdAsync(registered.Id);
        Assert.NotNull(fetched);
        Assert.Equal(registered.Id, fetched.EnrolmentId);
        Assert.Equal("National Certificate: Mechanical Fitting", fetched.QualificationTitle);

        // Test UpdateEnrolmentAsync
        fetched.QualificationTitle = "Updated Fitting Specialization";
        var updated = await service.UpdateEnrolmentAsync(fetched, "TEST_USER");
        Assert.Equal("Updated Fitting Specialization", updated.QualificationTitle);

        // Test DeleteEnrolmentAsync
        var deleted = await service.DeleteEnrolmentAsync(registered.Id, "TEST_USER");
        Assert.True(deleted);

        var notFound = await service.GetEnrolmentByIdAsync(registered.Id);
        Assert.Null(notFound);
    }
}
