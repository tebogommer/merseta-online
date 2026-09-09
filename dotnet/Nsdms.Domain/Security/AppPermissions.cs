namespace Nsdms.Domain.Security;

/// <summary>
/// Canonical Module + Action permission definitions for NSDMS Claims-Based RBAC and CASL Authorization.
/// </summary>
public static class AppPermissions
{
    // Module Names
    public const string ModuleGrants = "Grants";
    public const string ModuleWsp = "Wsp";
    public const string ModuleLearners = "Learners";
    public const string ModuleEtqa = "Etqa";
    public const string ModuleWorkplace = "Workplace";
    public const string ModuleFinance = "Finance";
    public const string ModuleCompliance = "Compliance";
    public const string ModuleOrganisations = "Organisations";
    public const string ModulePeople = "People";
    public const string ModuleSystem = "System";

    // Standard & Domain-Specific Actions
    public const string ActionView = "View";
    public const string ActionCreate = "Create";
    public const string ActionEdit = "Edit";
    public const string ActionDelete = "Delete";
    public const string ActionApprove = "Approve";
    public const string ActionReject = "Reject";
    public const string ActionDisburse = "Disburse";
    public const string ActionSubmit = "Submit";
    public const string ActionManage = "Manage";
    public const string ActionSign = "Sign";
    public const string ActionEndorse = "Endorse";
    public const string ActionVerify = "Verify";
    public const string ActionQuery = "Query";
    public const string ActionRemediate = "Remediate";
    public const string ActionModerate = "Moderate";
    public const string ActionCertificate = "Certificate";
    public const string ActionReview = "Review";
    public const string ActionWithdraw = "Withdraw";
    public const string ActionReconcile = "Reconcile";
    public const string ActionTransfer = "Transfer";
    public const string ActionDelegate = "Delegate";

    // Permission Claims Dictionary (Module -> List of Actions)
    public static readonly Dictionary<string, List<string>> ModuleActions = new()
    {
        { ModuleOrganisations, new() { ActionView, ActionCreate, ActionEdit, ActionDelete, ActionSign, ActionDelegate, ActionManage } },
        { ModulePeople, new() { ActionView, ActionCreate, ActionEdit, ActionDelete, ActionManage } },
        { ModuleWsp, new() { ActionView, ActionCreate, ActionEdit, ActionSubmit, ActionSign, ActionEndorse, ActionQuery, ActionRemediate, ActionApprove, ActionReject, ActionManage } },
        { ModuleGrants, new() { ActionView, ActionCreate, ActionEdit, ActionDelete, ActionSubmit, ActionSign, ActionVerify, ActionQuery, ActionRemediate, ActionApprove, ActionReject, ActionDisburse, ActionManage } },
        { ModuleFinance, new() { ActionView, ActionCreate, ActionEdit, ActionVerify, ActionApprove, ActionDisburse, ActionReconcile, ActionTransfer, ActionManage } },
        { ModuleLearners, new() { ActionView, ActionCreate, ActionEdit, ActionDelete, ActionSubmit, ActionReview, ActionVerify, ActionModerate, ActionCertificate, ActionApprove, ActionReject, ActionWithdraw, ActionManage } },
        { ModuleEtqa, new() { ActionView, ActionCreate, ActionEdit, ActionDelete, ActionQuery, ActionRemediate, ActionVerify, ActionModerate, ActionApprove, ActionReject, ActionManage } },
        { ModuleWorkplace, new() { ActionView, ActionCreate, ActionEdit, ActionDelete, ActionVerify, ActionApprove, ActionReject, ActionManage } },
        { ModuleCompliance, new() { ActionView, ActionCreate, ActionEdit, ActionSubmit, ActionVerify, ActionManage } },
        { ModuleSystem, new() { ActionView, ActionCreate, ActionEdit, ActionDelete, ActionDelegate, ActionManage } }
    };

    public static string Create(string module, string action) => $"{module}:{action}";

    public static List<PermissionDefinition> GetAllPermissions()
    {
        var list = new List<PermissionDefinition>();
        foreach (var (module, actions) in ModuleActions)
        {
            foreach (var action in actions)
            {
                list.Add(new PermissionDefinition
                {
                    Module = module,
                    Action = action,
                    ClaimValue = Create(module, action),
                    DisplayName = $"{action} {module}",
                    Description = $"Allows user to {action.ToLowerInvariant()} records within the {module} subsystem."
                });
            }
        }
        return list;
    }
}

/// <summary>
/// Structured metadata describing an individual granular permission.
/// </summary>
public class PermissionDefinition
{
    public string Module { get; set; } = string.Empty;
    public string Action { get; set; } = string.Empty;
    public string ClaimValue { get; set; } = string.Empty;
    public string DisplayName { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
}
