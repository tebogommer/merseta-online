import { z } from "zod";

// --- Mathematical RSA ID Validation ---
const isValidRsaId = (id: string): boolean => {
  if (!id || id.length !== 13 || !/^\d{13}$/.test(id)) return false;

  let sum = 0;
  for (let i = 0; i < 12; i++) {
    let num = parseInt(id.charAt(i), 10);
    // Double every second digit
    if (i % 2 !== 0) {
      num *= 2;
      if (num > 9) num -= 9;
    }
    sum += num;
  }
  
  const checksum = (10 - (sum % 10)) % 10;
  return checksum === parseInt(id.charAt(12), 10);
};

export const learnerProfileSchema = z.object({
  rsaIdNumber: z.string().optional(),
  passportNumber: z.string().optional(),
  dateOfBirth: z.string()
    .min(1, "Date of Birth is required")
    .refine((val) => {
      const dob = new Date(val);
      const now = new Date();
      let age = now.getFullYear() - dob.getFullYear();
      const m = now.getMonth() - dob.getMonth();
      if (m < 0 || (m === 0 && now.getDate() < dob.getDate())) {
        age--;
      }
      return age >= 16;
    }, "Learner must be at least 16 years of age"),
  equityStatus: z.string().min(1, "Equity status is required"),
  nationality: z.string().min(1, "Nationality is required")
}).refine(
  (data) => data.rsaIdNumber || data.passportNumber,
  {
    message: "Either an RSA ID Number or a Passport Number must be provided",
    path: ["rsaIdNumber"], // Attach error to the primary field
  }
).refine(
  (data) => {
    if (data.rsaIdNumber) {
        return isValidRsaId(data.rsaIdNumber);
    }
    return true; // if no RSA ID, bypass math check (passport was already verified as present above)
  },
  {
    message: "Invalid RSA Identity Number format or checksum",
    path: ["rsaIdNumber"],
  }
);

export const learnerEnrollmentSchema = z.object({
  learnerId: z.number().int().positive("Valid Learner is required"),
  providerId: z.number().int().positive("Valid Provider/SDP must be selected"),
  employerId: z.number().int().positive("Valid Employer must be selected").optional(),
  interventionTypeId: z.number().int().positive("Intervention Type must be selected"),
  qualificationTypeId: z.number().int().positive("Qualification Type must be selected"),
  status: z.string().default("Application")
});
