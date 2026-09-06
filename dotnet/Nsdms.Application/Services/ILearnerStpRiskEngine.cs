using Nsdms.Domain.Entities;

namespace Nsdms.Application.Services;

/// <summary>
/// Result evaluated by the Straight-Through Processing (STP) Risk Engine.
/// </summary>
public record StpEvaluationResult(
    bool IsStpEligible,
    string DecisionReason,
    List<string> RiskFactors,
    List<string> CompliantFactors
);

/// <summary>
/// Straight-Through Processing (STP) Risk Engine for Learner Registration.
/// Determines whether an incoming registration agreement qualifies for automated approval (The ATM)
/// or mandates human officer verification (The Teller).
/// </summary>
public interface ILearnerStpRiskEngine
{
    /// <summary>
    /// Evaluates a learner registration against statutory PFMA, QCTO, and SDA compliance invariants.
    /// </summary>
    Task<StpEvaluationResult> EvaluateRegistrationRiskAsync(CompanyLearner learner);
}
