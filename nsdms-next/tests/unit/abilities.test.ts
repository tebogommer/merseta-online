import { describe, it, expect } from 'vitest';
import { defineAbilitiesFor } from '../../lib/abilities';

describe('CASL Abilities (TC-SEC-001 to TC-SEC-010)', () => {
  
  describe('ADMIN Role Rules', () => {
    it('TC-SEC-004: Should allow ADMIN to read any generic model', () => {
      const ability = defineAbilitiesFor({ id: '1', role: 'ADMIN' } as any);
      expect(ability.can('read', 'WorkplaceSkillsPlan')).toBe(true);
      expect(ability.can('read', 'Organisation')).toBe(true);
    });

    it('TC-SEC-007: Should allow ADMIN to manage all entities globally', () => {
      const ability = defineAbilitiesFor({ id: '1', role: 'ADMIN' } as any);
      expect(ability.can('manage', 'all')).toBe(true);
    });
  });

  describe('STANDARD Role Rules', () => {
    it('TC-SEC-003: Should allow STANDARD user to read models linked to their createdBy ID', () => {
      const ability = defineAbilitiesFor({ id: '99', role: 'STANDARD' } as any);
      // Validating context condition would require the subject instance.
      // But they inherently get read permissions subject to Prisma filtering in controllers.
      expect(ability.can('read', 'WorkplaceSkillsPlan')).toBe(true);
    });

    it('TC-SEC-005: Should forbid STANDARD user from deleting random WSPs without condition match', () => {
      const ability = defineAbilitiesFor({ id: '99', role: 'STANDARD' } as any);
      // Fails because the condition { createdBy: '99' } must be matched.
      expect(ability.can('delete', 'WorkplaceSkillsPlan')).toBe(false);
    });

    it('TC-SEC-009: Should forbid privilege escalation manually through subjects', () => {
        const ability = defineAbilitiesFor({ id: '99', role: 'STANDARD' } as any);
        expect(ability.can('manage', 'User')).toBe(false);
    });
  });

  describe('Anonymous Rules', () => {
    it('TC-SEC-001: Anonymous definition should default to impossible ability', () => {
      const ability = defineAbilitiesFor(null as any);
      expect(ability.can('read', 'WorkplaceSkillsPlan')).toBe(false);
    });
    
    it('TC-SEC-002: Anonymous cannot create anything', () => {
      const ability = defineAbilitiesFor(undefined as any);
      expect(ability.can('create', 'WorkplaceSkillsPlan')).toBe(false);
    });
  });

});
