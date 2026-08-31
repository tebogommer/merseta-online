using Nsdms.Domain.Common;

namespace Nsdms.Domain.Entities;

/// <summary>
/// Canonical Domain Entity representing a learner agreement / training enrolment contract.
/// Adheres to Clean Architecture naming standards while mapping seamlessly to statutory SETMIS specifications.
/// </summary>
public class LearnerEnrolment : CompanyLearner
{
    // Inherits all statutory SETMIS properties and relational links from CompanyLearner base
}
