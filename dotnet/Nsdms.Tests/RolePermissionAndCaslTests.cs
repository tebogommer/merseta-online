using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Nsdms.Application.Common;
using Nsdms.Application.Services;
using Nsdms.Domain.Entities;
using Nsdms.Domain.Security;
using Nsdms.Infrastructure.Data;
using Xunit;

namespace Nsdms.Tests;

public class RolePermissionAndCaslTests
{
    private (RolePermissionService roleService, CaslAbilityService caslService, TestDbContextFactory factory) CreateServices()
    {
        var factory = new TestDbContextFactory(Guid.NewGuid().ToString());
        var audit = new AuditService(factory);
        var roleService = new RolePermissionService(factory, audit);
        var caslService = new CaslAbilityService(factory, roleService);

        return (roleService, caslService, factory);
    }

    [Fact]
    public async Task SeedDefaultRolePermissionsAsync_ShouldSeedStandardRolesAndClaims()
    {
        var (roleService, _, factory) = CreateServices();

        await roleService.SeedDefaultRolePermissionsAsync();

        var roles = await roleService.GetAllRolesAsync();
        Assert.Contains(roles, r => r.Name == "SuperAdmin");
        Assert.Contains(roles, r => r.Name == "Admin");
        Assert.Contains(roles, r => r.Name == "FinanceManager");
        Assert.Contains(roles, r => r.Name == "CLO");
        Assert.Contains(roles, r => r.Name == "SDF");
        Assert.Contains(roles, r => r.Name == "ReviewCommittee");

        var superAdmin = roles.First(r => r.Name == "SuperAdmin");
        Assert.True(superAdmin.PermissionCount > 0);

        var financeRole = await roleService.GetRoleByIdAsync(roles.First(r => r.Name == "FinanceManager").Id);
        Assert.NotNull(financeRole);
        Assert.Contains("Finance:Disburse", financeRole.PermissionClaims);
        Assert.Contains("Finance:Approve", financeRole.PermissionClaims);
    }

    [Fact]
    public async Task CreateRoleAsync_ShouldPersistRoleAndPermissionClaims_WithAudit()
    {
        var (roleService, _, factory) = CreateServices();

        var created = await roleService.CreateRoleAsync(
            "RiskAuditor",
            "Risk and Compliance Auditor",
            new List<string> { "Compliance:View", "Compliance:Manage", "Organisations:View" },
            "ChiefRiskOfficer");

        Assert.NotNull(created);
        Assert.Equal("RiskAuditor", created.Name);

        var detail = await roleService.GetRoleByIdAsync(created.Id);
        Assert.NotNull(detail);
        Assert.Equal(3, detail.PermissionClaims.Count);
        Assert.Contains("Compliance:View", detail.PermissionClaims);
        Assert.Contains("Compliance:Manage", detail.PermissionClaims);
        Assert.Contains("Organisations:View", detail.PermissionClaims);

        using var db = await factory.CreateDbContextAsync();
        var audit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "ApplicationRole" && a.ActionName == "CreateRole");
        Assert.NotNull(audit);
        Assert.Equal("ChiefRiskOfficer", audit.Actor);
    }

    [Fact]
    public async Task UpdateRoleAsync_ShouldSynchronizeClaims_WithAudit()
    {
        var (roleService, _, factory) = CreateServices();

        var created = await roleService.CreateRoleAsync("QualityInspector", "QA Inspector", new List<string> { "Etqa:View" }, "Admin");

        var updated = await roleService.UpdateRoleAsync(
            created.Id,
            "QualityInspector",
            "Updated QA Inspector description",
            true,
            new List<string> { "Etqa:View", "Etqa:Approve", "Workplace:View" },
            "QA_Director");

        Assert.True(updated);

        var detail = await roleService.GetRoleByIdAsync(created.Id);
        Assert.NotNull(detail);
        Assert.Equal(3, detail.PermissionClaims.Count);
        Assert.Contains("Etqa:Approve", detail.PermissionClaims);
        Assert.Contains("Workplace:View", detail.PermissionClaims);

        using var db = await factory.CreateDbContextAsync();
        var audit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "ApplicationRole" && a.ActionName == "UpdateRole");
        Assert.NotNull(audit);
        Assert.Equal("QA_Director", audit.Actor);
    }

    [Fact]
    public async Task GetUserPermissionsAsync_ShouldAggregateAcrossRoles()
    {
        var (roleService, _, factory) = CreateServices();
        await roleService.SeedDefaultRolePermissionsAsync();

        using var db = await factory.CreateDbContextAsync();
        var user = new ApplicationUser
        {
            UserName = "finance_officer@merseta.org.za",
            NormalizedUserName = "FINANCE_OFFICER@MERSETA.ORG.ZA",
            Email = "finance_officer@merseta.org.za",
            NormalizedEmail = "FINANCE_OFFICER@MERSETA.ORG.ZA"
        };
        db.Users.Add(user);
        await db.SaveChangesAsync();

        var allRoles = await roleService.GetAllRolesAsync();
        var financeRole = allRoles.First(r => r.Name == "FinanceManager");

        db.UserRoles.Add(new IdentityUserRole<int> { UserId = user.Id, RoleId = financeRole.Id });
        await db.SaveChangesAsync();

        var userPerms = await roleService.GetUserPermissionsAsync(user.Id);
        Assert.Contains("Finance:Disburse", userPerms);
        Assert.Contains("Finance:Approve", userPerms);

        var hasFinanceDisburse = await roleService.UserHasPermissionAsync(user.Id, "Finance", "Disburse");
        Assert.True(hasFinanceDisburse);

        var hasLearnersDelete = await roleService.UserHasPermissionAsync(user.Id, "Learners", "Delete");
        Assert.False(hasLearnersDelete);
    }

    [Fact]
    public async Task CaslAbilityService_ShouldEnforceViewManageAndOrganisationScoping()
    {
        var (roleService, caslService, factory) = CreateServices();
        await roleService.SeedDefaultRolePermissionsAsync();

        using var db = await factory.CreateDbContextAsync();
        var adminUser = new ApplicationUser
        {
            UserName = "super_admin",
            NormalizedUserName = "SUPER_ADMIN",
            Email = "superadmin@merseta.org.za",
            NormalizedEmail = "SUPERADMIN@MERSETA.ORG.ZA",
            IsActive = true
        };
        var sdfUser = new ApplicationUser
        {
            UserName = "external_sdf",
            NormalizedUserName = "EXTERNAL_SDF",
            Email = "sdf@employer.co.za",
            NormalizedEmail = "SDF@EMPLOYER.CO.ZA",
            DefaultOrganisationId = 42,
            IsActive = true
        };
        db.Users.AddRange(adminUser, sdfUser);
        await db.SaveChangesAsync();

        var allRoles = await roleService.GetAllRolesAsync();
        var superAdminRole = allRoles.First(r => r.Name == "SuperAdmin");
        var sdfRole = allRoles.First(r => r.Name == "SDF");

        db.UserRoles.Add(new IdentityUserRole<int> { UserId = adminUser.Id, RoleId = superAdminRole.Id });
        db.UserRoles.Add(new IdentityUserRole<int> { UserId = sdfUser.Id, RoleId = sdfRole.Id });
        await db.SaveChangesAsync();

        // 1. Admin Context Check
        var adminContext = await caslService.GetUserContextAsync(adminUser.Id);
        Assert.True(adminContext.IsAdmin);
        Assert.True(caslService.Can(adminContext, "Delete", "Organisations", targetOrganisationId: 99));
        Assert.True(caslService.CanViewOrManage(adminContext, "Grants", targetOrganisationId: 99));

        // 2. Scoped SDF Context Check
        var sdfContext = await caslService.GetUserContextAsync(sdfUser.Id);
        Assert.False(sdfContext.IsAdmin);
        Assert.Equal(42, sdfContext.DefaultOrganisationId);

        // Allowed on own organisation
        Assert.True(caslService.Can(sdfContext, "Submit", "Wsp", targetOrganisationId: 42));
        Assert.True(caslService.CanViewOrManage(sdfContext, "Wsp", targetOrganisationId: 42));

        // Denied on different organisation (Tenant Isolation Scoping)
        Assert.False(caslService.Can(sdfContext, "Submit", "Wsp", targetOrganisationId: 99));
        Assert.False(caslService.CanViewOrManage(sdfContext, "Wsp", targetOrganisationId: 99));

        // Denied actions not in role claims
        Assert.False(caslService.Can(sdfContext, "Disburse", "Finance", targetOrganisationId: 42));
    }

    [Fact]
    public async Task AdminUsers_ShouldHaveFullCrudPermissionsAcrossAllModules()
    {
        var (roleService, caslService, factory) = CreateServices();
        var adminContext = await caslService.GetUserContextByUsernameAsync("Admin");

        Assert.True(adminContext.IsAdmin);

        var modules = new[] { "Organisations", "People", "Wsp", "Grants", "Finance", "Learners", "Etqa", "Workplace", "Compliance", "System" };
        var actions = new[] { "View", "Create", "Edit", "Delete", "Manage" };

        foreach (var mod in modules)
        {
            foreach (var act in actions)
            {
                Assert.True(caslService.Can(adminContext, act, mod), $"Admin must have {act} permission on {mod}");
            }
            Assert.True(caslService.CanViewOrManage(adminContext, mod), $"Admin must have ViewOrManage visibility on {mod}");
        }
    }

    [Fact]
    public async Task ManagePermission_ShouldGrantAllSubActionsForNonAdmin()
    {
        var (roleService, caslService, factory) = CreateServices();

        var managerRole = await roleService.CreateRoleAsync(
            "GrantsManager",
            "Full Grants Module Manager",
            new List<string> { "Grants:Manage" },
            "AdminUser");

        using var db = await factory.CreateDbContextAsync();
        var user = new ApplicationUser
        {
            UserName = "grants_lead",
            NormalizedUserName = "GRANTS_LEAD",
            Email = "lead@merseta.org.za",
            NormalizedEmail = "LEAD@MERSETA.ORG.ZA",
            IsActive = true
        };
        db.Users.Add(user);
        await db.SaveChangesAsync();

        db.UserRoles.Add(new IdentityUserRole<int> { UserId = user.Id, RoleId = managerRole.Id });
        await db.SaveChangesAsync();

        var userContext = await caslService.GetUserContextAsync(user.Id);
        Assert.False(userContext.IsAdmin);

        // Grants:Manage grants View, Create, Edit, Delete, Submit, Approve, Reject, Disburse
        Assert.True(caslService.Can(userContext, "View", "Grants"));
        Assert.True(caslService.Can(userContext, "Create", "Grants"));
        Assert.True(caslService.Can(userContext, "Edit", "Grants"));
        Assert.True(caslService.Can(userContext, "Delete", "Grants"));
        Assert.True(caslService.Can(userContext, "Disburse", "Grants"));
        Assert.True(caslService.CanViewOrManage(userContext, "Grants"));

        // But not other modules
        Assert.False(caslService.Can(userContext, "View", "Finance"));
        Assert.False(caslService.Can(userContext, "Delete", "Organisations"));
    }

    [Fact]
    public async Task DashboardDataScoping_NonAdminIsRestrictedToDefaultOrganisation_AdminIsGlobal()
    {
        var (roleService, caslService, factory) = CreateServices();

        using var db = await factory.CreateDbContextAsync();
        var org1 = new Organisation { CompanyName = "Toyota SA", SdlNumber = "L100200300" };
        var org2 = new Organisation { CompanyName = "Sasol Ltd", SdlNumber = "L800200300" };
        db.Organisations.AddRange(org1, org2);
        await db.SaveChangesAsync();

        var adminUser = new ApplicationUser { UserName = "admin_user", NormalizedUserName = "ADMIN_USER", IsActive = true };
        var employerUser = new ApplicationUser { UserName = "toyota_clerk", NormalizedUserName = "TOYOTA_CLERK", DefaultOrganisationId = org1.Id, IsActive = true };
        db.Users.AddRange(adminUser, employerUser);
        await db.SaveChangesAsync();

        var allRoles = await roleService.GetAllRolesAsync();
        var adminRole = allRoles.FirstOrDefault(r => r.Name == "SuperAdmin");
        int adminRoleId = adminRole != null ? adminRole.Id : (await roleService.CreateRoleAsync("SuperAdmin", "Admin", new List<string> { "System:Manage" })).Id;
        db.UserRoles.Add(new IdentityUserRole<int> { UserId = adminUser.Id, RoleId = adminRoleId });
        await db.SaveChangesAsync();

        var adminContext = await caslService.GetUserContextAsync(adminUser.Id);
        var employerContext = await caslService.GetUserContextAsync(employerUser.Id);

        // Simulated dashboard organization visibility query
        var allOrgsQuery = db.Organisations.AsQueryable();

        var adminVisibleOrgs = allOrgsQuery
            .Where(o => caslService.IsOrganisationAccessible(adminContext, o.Id))
            .ToList();

        var employerVisibleOrgs = allOrgsQuery
            .Where(o => caslService.IsOrganisationAccessible(employerContext, o.Id))
            .ToList();

        Assert.Equal(2, adminVisibleOrgs.Count); // Global view
        Assert.Single(employerVisibleOrgs);    // Scoped only to Toyota
        Assert.Equal("Toyota SA", employerVisibleOrgs.First().CompanyName);
    }

    [Fact]
    public async Task AuditService_LinkAndUnlinkActions_ShouldPersistAuditTrail()
    {
        var (_, _, factory) = CreateServices();
        var audit = new AuditService(factory);

        using var db = await factory.CreateDbContextAsync();

        var beforeLink = new { MentorPersonId = (int?)null, WorkplaceApprovalId = 10 };
        var afterLink = new { MentorPersonId = (int?)5, WorkplaceApprovalId = 10 };

        audit.LogAction(db, "WorkplaceApprovalMentor", 10, "LinkMentor", "FieldOfficerThabo", beforeLink, afterLink);
        await db.SaveChangesAsync();

        var beforeUnlink = new { MentorPersonId = (int?)5, WorkplaceApprovalId = 10 };
        var afterUnlink = new { MentorPersonId = (int?)null, WorkplaceApprovalId = 10 };

        audit.LogAction(db, "WorkplaceApprovalMentor", 10, "UnlinkMentor", "FieldOfficerThabo", beforeUnlink, afterUnlink);
        await db.SaveChangesAsync();

        var logs = await db.AuditLogs
            .Where(a => a.EntityName == "WorkplaceApprovalMentor")
            .OrderBy(a => a.CreatedAt)
            .ToListAsync();

        Assert.Equal(2, logs.Count);
        Assert.Equal("LinkMentor", logs[0].ActionName);
        Assert.Equal("UnlinkMentor", logs[1].ActionName);
        Assert.Equal("FieldOfficerThabo", logs[0].Actor);
        Assert.Contains("\"MentorPersonId\":5", logs[0].MetadataJson ?? "");
    }

    [Fact]
    public async Task DeleteRoleAsync_ShouldPreventSuperAdminDeletion_AndLogAuditForCustomRole()
    {
        var (roleService, _, factory) = CreateServices();
        await roleService.SeedDefaultRolePermissionsAsync();

        var superAdmin = (await roleService.GetAllRolesAsync()).First(r => r.Name == "SuperAdmin");

        // Attempting to delete SuperAdmin should throw
        await Assert.ThrowsAsync<InvalidOperationException>(() => roleService.DeleteRoleAsync(superAdmin.Id, "Intruder"));

        // Deleting a custom role should succeed and log audit
        var custom = await roleService.CreateRoleAsync("TemporaryAuditor", "Temp Auditor", new List<string> { "Compliance:View" }, "Admin");
        var deleted = await roleService.DeleteRoleAsync(custom.Id, "AdminUser");
        Assert.True(deleted);

        using var db = await factory.CreateDbContextAsync();
        var audit = await db.AuditLogs.FirstOrDefaultAsync(a => a.EntityName == "ApplicationRole" && a.ActionName == "DeleteRole");
        Assert.NotNull(audit);
        Assert.Equal("AdminUser", audit.Actor);
    }

    [Fact]
    public async Task UserPermissionOverride_GrantAndRevoke_ShouldAffectEffectivePermissionsAndCaslCan()
    {
        var (roleService, caslService, factory) = CreateServices();

        using (var db = await factory.CreateDbContextAsync())
        {
            var cloRole = new ApplicationRole { Id = 101, Name = "CLO", NormalizedName = "CLO", Active = true };
            db.Roles.Add(cloRole);
            db.RoleClaims.Add(new IdentityRoleClaim<int>
            {
                RoleId = 101,
                ClaimType = RolePermissionService.PermissionClaimType,
                ClaimValue = "Workplace:View"
            });
            db.RoleClaims.Add(new IdentityRoleClaim<int>
            {
                RoleId = 101,
                ClaimType = RolePermissionService.PermissionClaimType,
                ClaimValue = "Workplace:Verify"
            });

            var user = new ApplicationUser
            {
                Id = 55,
                UserName = "ThaboCLO",
                NormalizedUserName = "THABOCLO",
                Email = "thabo@merseta.org.za",
                IsActive = true
            };
            db.Users.Add(user);
            db.UserRoles.Add(new IdentityUserRole<int> { UserId = 55, RoleId = 101 });
            await db.SaveChangesAsync();
        }

        // 1. Initial State: CLO has Workplace:View and Workplace:Verify
        var initialPerms = await roleService.GetUserPermissionsAsync(55);
        Assert.Contains("Workplace:View", initialPerms);
        Assert.Contains("Workplace:Verify", initialPerms);
        Assert.DoesNotContain("Grants:Approve", initialPerms);

        var context1 = await caslService.GetUserContextAsync(55);
        Assert.True(caslService.Can(context1, "Verify", "Workplace"));
        Assert.False(caslService.Can(context1, "Approve", "Grants"));

        // 2. Grant User-Specific Override: Give ThaboCLO extra "Grants:Approve"
        var grantResult = await roleService.SetUserPermissionOverrideAsync(55, "Grants:Approve", isGranted: true, "Acting Grant Manager", "AdminUser");
        Assert.True(grantResult);

        var permsAfterGrant = await roleService.GetUserPermissionsAsync(55);
        Assert.Contains("Grants:Approve", permsAfterGrant);

        var context2 = await caslService.GetUserContextAsync(55);
        Assert.True(caslService.Can(context2, "Approve", "Grants"));

        // 3. Revoke User-Specific Override: Revoke inherited "Workplace:Verify" from ThaboCLO
        var revokeResult = await roleService.SetUserPermissionOverrideAsync(55, "Workplace:Verify", isGranted: false, "Under probation review", "AdminUser");
        Assert.True(revokeResult);

        var permsAfterRevoke = await roleService.GetUserPermissionsAsync(55);
        Assert.DoesNotContain("Workplace:Verify", permsAfterRevoke);
        Assert.Contains("Workplace:View", permsAfterRevoke); // Other role perms retained

        var context3 = await caslService.GetUserContextAsync(55);
        Assert.False(caslService.Can(context3, "Verify", "Workplace"));
        Assert.True(caslService.Can(context3, "View", "Workplace"));

        // 4. Remove the revocation override: Returns back to role default
        var removeRevocation = await roleService.RemoveUserPermissionOverrideAsync(55, "Workplace:Verify", "AdminUser");
        Assert.True(removeRevocation);

        var context4 = await caslService.GetUserContextAsync(55);
        Assert.True(caslService.Can(context4, "Verify", "Workplace"));
    }

    [Fact]
    public async Task LearnerAgreement_SdfAndSdpRoles_ShouldHaveSubmitPermissionsAndRoleNeutralNaming()
    {
        var (roleService, caslService, factory) = CreateServices();
        await roleService.SeedDefaultRolePermissionsAsync();

        var roles = await roleService.GetAllRolesAsync();
        
        // 1. SDF must have Learners:Submit
        var sdfRole = await roleService.GetRoleByIdAsync(roles.First(r => r.Name == "SDF").Id);
        Assert.NotNull(sdfRole);
        Assert.Contains("Learners:Submit", sdfRole.PermissionClaims);
        Assert.Contains("Learners:Withdraw", sdfRole.PermissionClaims);

        // 2. TrainingProvider and SDP must have Learners:Submit
        var tpRole = await roleService.GetRoleByIdAsync(roles.First(r => r.Name == "TrainingProvider").Id);
        Assert.NotNull(tpRole);
        Assert.Contains("Learners:Submit", tpRole.PermissionClaims);
        Assert.Contains("Learners:Create", tpRole.PermissionClaims);

        var sdpRole = await roleService.GetRoleByIdAsync(roles.First(r => r.Name == "SDP").Id);
        Assert.NotNull(sdpRole);
        Assert.Contains("Learners:Submit", sdpRole.PermissionClaims);

        // 3. Verification Officer (CLO) must have Learners:Review and Learners:Verify
        var cloRole = await roleService.GetRoleByIdAsync(roles.First(r => r.Name == "CLO").Id);
        Assert.NotNull(cloRole);
        Assert.Contains("Learners:Review", cloRole.PermissionClaims);
        Assert.Contains("Learners:Verify", cloRole.PermissionClaims);

        // 4. Approval Authority (ReviewCommittee) must have Learners:Approve
        var committeeRole = await roleService.GetRoleByIdAsync(roles.First(r => r.Name == "ReviewCommittee").Id);
        Assert.NotNull(committeeRole);
        Assert.Contains("Learners:Approve", committeeRole.PermissionClaims);
    }

    [Fact]
    public async Task LearnerAgreement_RelationshipScoping_ShouldAllowAssociatedSdfOrSdpContact()
    {
        var (roleService, caslService, factory) = CreateServices();
        await roleService.SeedDefaultRolePermissionsAsync();

        using var db = await factory.CreateDbContextAsync();

        // Seed Employer & Training Provider
        var employer = new Organisation { Id = 101, CompanyName = "Bell Equipment", SdlNumber = "L101010101" };
        var sdp = new TrainingProvider { Id = 202, ProviderName = "Ekurhuleni Artisan Academy", AccreditationNumber = "ACC-202" };
        db.Organisations.Add(employer);
        db.TrainingProviders.Add(sdp);

        // Seed People
        var sdfPerson = new Person { Id = 301, FirstName = "Sipho", LastName = "Dlamini", RsaIdNumber = "8001015009087" };
        var sdpPerson = new Person { Id = 302, FirstName = "Nomvula", LastName = "Khumalo", RsaIdNumber = "8502025009088" };
        var unaffiliatedPerson = new Person { Id = 303, FirstName = "John", LastName = "Doe", RsaIdNumber = "9003035009089" };
        db.People.AddRange(sdfPerson, sdpPerson, unaffiliatedPerson);

        // Link SDF Person to Employer
        db.OrganisationContacts.Add(new OrganisationContact
        {
            OrganisationId = employer.Id,
            PersonId = sdfPerson.Id,
            ContactTypeCode = "SDF",
            IsActive = true
        });

        // Link SDP Person to Provider
        db.TrainingProviderContacts.Add(new TrainingProviderContact
        {
            TrainingProviderId = sdp.Id,
            PersonId = sdpPerson.Id,
            Email = "nomvula@ekurhuleni-academy.co.za",
            ContactDesignation = "Primary SDP Contact",
            IsActive = true
        });

        // Users
        var sdfUser = new ApplicationUser 
        { 
            Id = 401, 
            UserName = "sipho_sdf", 
            NormalizedUserName = "SIPHO_SDF",
            Email = "sipho@bell.co.za", 
            NormalizedEmail = "SIPHO@BELL.CO.ZA",
            PersonId = sdfPerson.Id 
        };
        var sdpUser = new ApplicationUser 
        { 
            Id = 402, 
            UserName = "nomvula_sdp", 
            NormalizedUserName = "NOMVULA_SDP",
            Email = "nomvula@ekurhuleni-academy.co.za", 
            NormalizedEmail = "NOMVULA@EKURHULENI-ACADEMY.CO.ZA",
            PersonId = sdpPerson.Id 
        };
        var outsiderUser = new ApplicationUser 
        { 
            Id = 403, 
            UserName = "outsider", 
            NormalizedUserName = "OUTSIDER",
            Email = "outsider@other.co.za", 
            NormalizedEmail = "OUTSIDER@OTHER.CO.ZA",
            PersonId = unaffiliatedPerson.Id 
        };
        db.Users.AddRange(sdfUser, sdpUser, outsiderUser);
        await db.SaveChangesAsync();

        var roles = await roleService.GetAllRolesAsync();
        var sdfRoleId = roles.First(r => r.Name == "SDF").Id;
        var sdpRoleId = roles.First(r => r.Name == "SDP").Id;

        db.UserRoles.Add(new IdentityUserRole<int> { UserId = sdfUser.Id, RoleId = sdfRoleId });
        db.UserRoles.Add(new IdentityUserRole<int> { UserId = sdpUser.Id, RoleId = sdpRoleId });
        db.UserRoles.Add(new IdentityUserRole<int> { UserId = outsiderUser.Id, RoleId = sdfRoleId }); // has SDF claim but not linked to this org
        await db.SaveChangesAsync();

        // Act & Assert 1: SDF can submit for employer 101
        var sdfContext = await caslService.GetUserContextAsync(sdfUser.Id);
        Assert.Contains(101, sdfContext.AssociatedOrganisationIds);
        Assert.True(caslService.CanSubmitLearnerAgreement(sdfContext, targetOrganisationId: 101, targetTrainingProviderId: 202));
        // SDF cannot submit for unlinked employer 999
        Assert.False(caslService.CanSubmitLearnerAgreement(sdfContext, targetOrganisationId: 999, targetTrainingProviderId: null));

        // Act & Assert 2: SDP Contact can submit for training provider 202
        var sdpContext = await caslService.GetUserContextAsync(sdpUser.Id);
        Assert.Contains(202, sdpContext.AssociatedTrainingProviderIds);
        Assert.True(caslService.CanSubmitLearnerAgreement(sdpContext, targetOrganisationId: 101, targetTrainingProviderId: 202));
        // SDP Contact cannot submit for unlinked provider 888
        Assert.False(caslService.CanSubmitLearnerAgreement(sdpContext, targetOrganisationId: null, targetTrainingProviderId: 888));

        // Act & Assert 3: Outsider with SDF role cannot submit for employer 101 or provider 202 because they are not affiliated
        var outsiderContext = await caslService.GetUserContextAsync(outsiderUser.Id);
        Assert.Empty(outsiderContext.AssociatedOrganisationIds);
        Assert.Empty(outsiderContext.AssociatedTrainingProviderIds);
        // Outsider has no affiliation with employer 101 or provider 202
        Assert.False(caslService.CanSubmitLearnerAgreement(outsiderContext, targetOrganisationId: 101, targetTrainingProviderId: 202));
    }

    [Fact]
    public async Task LearnerAgreement_WorkflowDefinition_ShouldUseRoleNeutralFunctionalNomenclature()
    {
        var (_, _, factory) = CreateServices();
        using var context = await factory.CreateDbContextAsync();

        await WorkflowDefinitionSeeder.SeedWorkflowDefinitionsAsync(context);

        var lrn = await context.WorkflowDefinitions
            .Include(w => w.States)
            .Include(w => w.Transitions)
            .FirstOrDefaultAsync(w => w.Code == "LRN");

        Assert.NotNull(lrn);
        Assert.True(lrn.States.Count >= 8);

        // Verify role-neutral functional aliases on all active states
        var draftState = lrn.States.First(s => s.StateCode == "DRAFT");
        Assert.Equal("Proposer / Submitter", draftState.AllowedGroupRole);

        var submittedState = lrn.States.First(s => s.StateCode == "SUBMITTED");
        Assert.Equal("Verification Officer", submittedState.AllowedGroupRole);

        var resubmitState = lrn.States.First(s => s.StateCode == "REJECTED_RESUBMIT");
        Assert.Equal("Proposer / Submitter", resubmitState.AllowedGroupRole);

        var resubmittedState = lrn.States.First(s => s.StateCode == "RESUBMITTED");
        Assert.Equal("Verification Officer", resubmittedState.AllowedGroupRole);

        var recommendedState = lrn.States.First(s => s.StateCode == "RECOMMENDED");
        Assert.Equal("Approval Authority", recommendedState.AllowedGroupRole);

        // Verify that NO state has legacy hardcoded job titles
        foreach (var state in lrn.States)
        {
            if (state.AllowedGroupRole != null)
            {
                Assert.DoesNotContain("Primary SDF", state.AllowedGroupRole);
                Assert.DoesNotContain("CLO", state.AllowedGroupRole);
                Assert.DoesNotContain("Quality Assurance Manager", state.AllowedGroupRole);
            }
        }
    }
}

