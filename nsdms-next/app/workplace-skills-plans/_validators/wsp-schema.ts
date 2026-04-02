import * as z from "zod";

export const createWspSchema = z.object({
  organisationId: z.number().positive("Valid Organisation is required."),
  finYear: z.number().int().min(2000, "Financial year is too far in the past").max(2100, "Invalid financial year"),
  
  // High-level target metrics
  numberOfEmployees: z.number().int().min(1, "Company must have at least one employee to file a WSP."),
  numberOfBeneficiaries: z.number().int().min(0).default(0),

  // Financial Metrics
  totalPayroll: z.number().min(1, "Total Payroll must be declared and greater than 0"),
  totalTrainingCosts: z.number().min(0, "Training costs cannot be negative"),
  
  // Specifics
  projectDescription: z.string().optional(),
  interventions: z.string().optional()
}).superRefine((val, ctx) => {
  if (val.totalTrainingCosts > val.totalPayroll) {
    ctx.addIssue({
      code: z.ZodIssueCode.custom,
      message: "Total Training Costs cannot exceed the Total Company Payroll.",
      path: ["totalTrainingCosts"]
    });
  }
});
