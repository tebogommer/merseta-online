import os
from pathlib import Path
import json

legacy_entity_dir = Path("doc/haj/com/entity")
lookup_dir = legacy_entity_dir / "lookup"

legacy_entities = sorted([f.stem for f in legacy_entity_dir.glob("*.html") if not f.stem.startswith("package-") and not f.stem.startswith("class-use")])
legacy_lookups = sorted([f.stem for f in lookup_dir.glob("*.html") if not f.stem.startswith("package-") and not f.stem.startswith("class-use")]) if lookup_dir.exists() else []

# Ported direct or subsumed entities
# In .NET 10:
# Company -> Organisation
# Users / UsersDisability / etc. -> Person / ApplicationUser
# Address -> Inlined on OrganisationSite / Person / Organisation
# BankingDetails -> Inlined with verification columns on Organisation
# CompanyUsers -> OrganisationContact
# CompanyLearners -> CompanyLearner
# Wsp / WspSignoff / MandatoryGrant -> WspSubmission / WspEmploymentSummary / WspTrainingPlan
# DgAllocation / ActiveContracts -> GrantApplication / GrantFundingWindow / GrantProjectBudget
# SarsLevyDetails / SarsFiles -> LevyFile / LevyFileLine
# AssessorModeratorCompany / AssessorModerators -> EtqaAssessor / AssessorModeratorScope
# WorkplaceApproval / ToolList / Mentors -> WorkplaceApproval / WorkplaceApprovalToolList / WorkplaceApprovalMentor
# TradeTest -> LearnerTradeTest

mapped_or_ported = {
    "Company": "Ported -> Organisation (Singular, clean DDD root)",
    "Users": "Ported -> Person & ApplicationUser (.NET Identity + Demographic separation)",
    "Address": "Subsumed -> Inlined into OrganisationSite and Person for normalized schema",
    "BankingDetails": "Subsumed -> Inlined directly with audit & verification into Organisation",
    "CompanyUsers": "Ported -> OrganisationContact (Relational junction with designation & primary flag)",
    "CompanyLearners": "Ported -> CompanyLearner (Learner lifecycle management)",
    "CompanyQualifications": "Ported -> TrainingProviderQualification & AssessorModeratorScope",
    "CompanyUnitStandard": "Ported -> TrainingProviderUnitStandard",
    "Wsp": "Ported -> WspSubmission",
    "WspSignoff": "Subsumed -> WspSubmission status & workflow history",
    "MandatoryGrant": "Ported -> WspSubmission / LevyFileLine mandatory calculation",
    "DgAllocation": "Ported -> GrantApplication & GrantFundingWindow",
    "ActiveContracts": "Ported -> GrantApplication / CompanyLearner contract tracking",
    "SarsLevyDetails": "Ported -> LevyFileLine",
    "SarsFiles": "Ported -> LevyFile",
    "AssessorModeratorCompany": "Ported -> EtqaAssessor & AssessorModeratorScope",
    "WorkplaceApproval": "Ported -> WorkplaceApproval",
    "WorkplaceApprovalSites": "Ported -> WorkplaceApproval (with OrganisationSiteId link)",
    "WorkplaceApprovalMentors": "Ported -> WorkplaceApprovalMentor",
    "WorkplaceApprovalToolList": "Ported -> WorkplaceApprovalToolList",
    "TradeTest": "Ported -> LearnerTradeTest",
    "Sites": "Ported -> OrganisationSite",
    "SiteVisit": "Ported -> Visit (Enforces ContactPersonId)",
    "AuditLog": "Ported -> AuditLog (Double-write audit interceptor)"
}

unported = {}
for e in legacy_entities:
    if e not in mapped_or_ported:
        unported[e] = "Unported Entity"

print("Total legacy entities:", len(legacy_entities))
print("Mapped/Ported:", len(mapped_or_ported))
print("Unported entities count:", len(unported))
print("\nUnported Entities List:")
for k in sorted(unported.keys()):
    print(f"- {k}")
