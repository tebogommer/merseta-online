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

/// <summary>
/// Canonical alias for learner contract extension amendments.
/// </summary>
public class LearnerEnrolmentExtension : CompanyLearnerExtension
{
}

/// <summary>
/// Canonical alias for learner contract employer/SDP transfers.
/// </summary>
public class LearnerEnrolmentTransfer : CompanyLearnerTransfer
{
}

/// <summary>
/// Canonical alias for learner training lost time / suspension intervals.
/// </summary>
public class LearnerEnrolmentLostTime : CompanyLearnerLostTime
{
}

/// <summary>
/// Canonical alias for learner contract cancellations / terminations.
/// </summary>
public class LearnerEnrolmentTermination : CompanyLearnerTermination
{
}

/// <summary>
/// Canonical alias for learner demographic / programme change requests.
/// </summary>
public class LearnerEnrolmentChangeRequest : CompanyLearnerChangeRequest
{
}
