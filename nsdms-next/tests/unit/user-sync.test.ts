import { syncLegacyUser } from "../../app/admin/users/_actions/user-sync";
import { describe, it, expect, vi } from "vitest";

// Mocking Prisma Client
vi.mock("@prisma/client", () => {
  const mPrismaClient = {
    $transaction: vi.fn(async (callback) => {
      // Create a mock transaction object
      const tx = {
         genderType: { findFirst: vi.fn().mockResolvedValue({ id: 1, code: 'M' }) },
         equityType: { findFirst: vi.fn().mockResolvedValue({ id: 2, code: 'A' }) },
         nationalityType: { findFirst: vi.fn().mockResolvedValue({ id: 3, code: 'SA' }) },
         user: {
           upsert: vi.fn().mockImplementation((args) => ({ id: 100, ...args.create }))
         },
         person: {
           upsert: vi.fn().mockImplementation((args) => ({ id: 200, ...args.create }))
         }
      };
      return await callback(tx);
    })
  };
  return { 
     PrismaClient: class PrismaClient {
         constructor() { return mPrismaClient; }
     }
  };
});

describe('User-Person Legacy Sync Transformations', () => {

  it('correctly maps 13-digit RSA ID and derives Date of Birth', async () => {
    // 920512 = 1992, May, 12
    const payload = {
      email: 'test@merseta.org.za',
      role: 'LEARNER',
      firstName: 'John',
      lastName: 'Doe',
      rsaIdNumber: '9205125000000',
      genderCode: 'm',
      equityCode: 'a',
      nationalityCode: 'sa'
    };

    const result = await syncLegacyUser(payload as any);
    
    // User object assertions
    expect(result.user.email).toBe('test@merseta.org.za');
    
    // Person object assertions
    expect(result.person.rsaIdNumber).toBe('9205125000000');
    expect(result.person.passportNumber).toBeUndefined();
    expect(result.person.genderId).toBe(1);

    // DOB Assertion (May 12, 1992)
    expect(result.person.dateOfBirth).toEqual(new Date(Date.UTC(1992, 4, 12)));
  });

  it('correctly identifies passport string injected into RSA ID field', async () => {
    // Legacy datasets often put passports in RSA ID columns if they didn't have validation.
    // Passport is typically alpha-numeric and shorter than 13.
    const payload = {
      email: 'foreigner@merseta.org.za',
      role: 'PROVIDER',
      firstName: 'Jane',
      lastName: 'Smith',
      rsaIdNumber: 'A12345679' // Only 9 characters
    };

    const result = await syncLegacyUser(payload as any);

    // Expected transformation: Invalid RSA ID falls over to Passport field mapping
    expect(result.person.rsaIdNumber).toBeUndefined();
    expect(result.person.passportNumber).toBe('A12345679');
    expect(result.person.dateOfBirth).toBeNull(); // Cannot extract DOB from passport string
  });
});
