# Spec 17: Core Business Registries (Complex Lookups)

## 1. Domain Overview
During the initial reverse-engineering of the legacy application, certain elements were incorrectly scoped as standard "Lookups" (simple key-value metadata tables). However, the following tables form the **Core Business Registries** of the NSDMS. They hold critical relational hierarchies, mathematical constraints, and explicit Time-Gates required for the application to function. 

They **must not** be implemented via a generic Lookup Management administration screen. They require dedicated, complex Code On Time (COT) Data Controllers, strict C# Business Rules, and deep Parent-Child hierarchical views.

## 2. The Registries & Structural Constraints

### A) Discretionary Grant (DG) Windows (`Funding_Window`)
Controls the lifecycle of when a company's SDF can submit an application for funding.
*   **Architectural Gap:** This is a Time-Gate entity. It has a `Start_Date`, `End_Date`, and `Budget_Cap`.
*   **Business Rule:** If a learner is registered referencing a specific `ProjectImplementationPlan`, the system must natively validate that the overarching `Funding_Window` is active under that financial year.
*   **COT Implementation Directive:** Needs a dedicated master-detail page where the `Funding_Window` is the Master, and the associated approved `ProjectImplementationPlans` are the Details.

### B) SAQA Qualifications (`Qualification`)
The central registry defining what an ETQA Assessor is allowed to teach, and what a Learner is permitted to study.
*   **Architectural Gap:** Qualifications are heavily relational. They possess:
    *   `NQF_Level_Id` (Dictates funding tiers).
    *   `Total_Credits` (Mathematical minimums).
    *   `Registration_Start_Date` / `Registration_End_Date` (SAQA Expiry).
    *   `Teach_Out_Period` (Grace period for learners already enrolled).
*   **COT Implementation Directive:** The `Qualification` data controller must contain a JavaScript `override_before_insert` to validate that the `Total_Credits` meets the minimum 120 threshold. A C# SQL Business Rule must actively flag an error if an SDF attempts to link a Learner to an EXPIRED SAQA Qualification without adequate Teach-Out validation.

### C) Unit Standards (`Unit_Standard` / `Learner_Learning_Programme_Unit_Standard`)
The modular breakdown of a Qualification. Many Unit Standards link back to one Qualification.
*   **Architectural Gap:** When a Learner applies, they don't just enroll in a Qualification. An Assessor must dictate explicitly which partial Unit Standards they completed to trigger competency uploads.
*   **Business Rule:** The sum of attained Unit Standards must be strictly validated mathematically against the `Total_Credits` defined in the Parent Qualification.
*   **COT Implementation Directive:** Implement a cascading dependency in the Touch UI. When a user selects a `Qualification`, the `Unit_Standard` dropdown must instantly filter to only show legally associated standards.

### D) Interventions (`Intervention_Type` / `Non_NQF_Interv`)
Differentiates between a Bursary, a Learnership, a Skills Programme, or an Apprenticeship.
*   **Architectural Gap:** Interventions dictate routing entirely. A Bursary does not require a Workplace Approval (`Spec-11`), but an Apprenticeship definitively requires a 1:4 Mentor Check (`Spec-11` and `Spec-08`).
*   **Business Rule:** Condition-Based Application Routing.
*   **COT Implementation Directive:** Add a `Requires_Workplace_Approval` scalar boolean field physically onto the `Intervention_Type` table. Then, inside the `LearnerRegistration` COT BusinessRule, inject a dynamic C# validation that throws `Result.ShowMessage` if the Selected Intervention requires approval but the overarching Company possesses no `WorkplaceApproval_Id`.

## 3. SQL Business Rules Layer (Code On Time Pattern)

The following SQL snippet demonstrates the complex validation required on the **Qualification Registry** to prevent associating an Assessor or Learner with an expired SAQA mandate.

```sql
-- Pattern: Code On Time SQL Business Rule (Before Insert/Update)
-- Target Controller: LearnerRegistration (FILE 500)
-- Action: Insert, Update

DECLARE @SaqaEndDate DATETIME;

SELECT @SaqaEndDate = [Registration_End_Date]
FROM [dbo].[Qualification]
WHERE [Qualification_Id] = @Qualification_Id;

IF (@SaqaEndDate < GETDATE())
BEGIN
    SET @BusinessRules_PreventDefault = 1;
    SET @Result_ShowMessage = 'This SAQA Qualification has officially expired. No further Learner Registrations can be accepted.';
END
```

## 4. UI Layout Directive (Touch UI)

Do **NOT** put these registries in a generic flat table.
They must exist under a dedicated standard menu container labeled `Core Architecture Data`:
- `> Education Matrix` (Qualifications -> Linked Unit Standards)
- `> Financial Control` (DG Windows -> Active Tiers -> Budget Ceilings)
- `> Statutory Drivers` (Interventions -> Routing Parameters)


### 6.1 Code On Time (COT) Native Form Validations
To satisfy the rapid Touch UI generation of COT, the following rules must be directly injected into the Data Controllers mapping to Qualification to handle synchronous database constraints and immediate UI validation.

#### A) SQL Business Rule (Server-Side Validation)
This SQL snippet executes within COT's [Controller].xml lifecycle before insertion to enforce business invariants dynamically based on the exact table structure.

`sql
-- Pattern: Code On Time SQL Business Rule (Before Insert/Update)
-- Target Controller: Qualification
-- Action: Insert, Update

-- Example Constraint Enforcement:
IF EXISTS (SELECT 1 FROM [dbo].[Qualification] WHERE [Id] = @Id AND [StatusId] IS NULL)
BEGIN
    SET @BusinessRules_PreventDefault = 1;
    SET @Result_ShowMessage = 'A valid Workflow Status must be assigned before committing this record in Qualification.';
END
`

#### B) JavaScript validation (Client-Side Touch UI)
This JS snippet enforces immediate checks on the UI input before a network dispatch, maintaining UI responsiveness for the Qualification controller.

`javascript
// Pattern: Code On Time JavaScript Business Rule (Before Insert/Update)
// Target Controller: Qualification
function override_before_insert(args) {
    var stat = ('StatusId');
    
    if (stat == null || stat === '') {
        this.preventDefault();
        this.result.showMessage('Workflow Status cannot be left blank during creation.');
        this.result.focus('StatusId');
        return;
    }
}
`
