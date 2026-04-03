import '@testing-library/jest-dom';

// Provide mocks for standard Next.js hooks if required
import { vi } from 'vitest';

vi.mock('next/navigation', () => ({
  useRouter() {
    return {
      push: vi.fn(),
      replace: vi.fn(),
      prefetch: vi.fn(),
      back: vi.fn(),
    };
  },
  usePathname() {
    return '/';
  },
}));
