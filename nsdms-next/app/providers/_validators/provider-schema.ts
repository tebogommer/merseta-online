import * as z from "zod";

export const createProviderSchema = z.object({
  organisationId: z.number().positive("Valid Organisation is required."),
  accreditationNumber: z.string().min(3, "Accreditation number must be at least 3 characters").max(50),
  providerTypeId: z.number().positive("Valid Provider Type is required."),
});
