import { defineAbility } from '@casl/ability';
import type { Session } from 'next-auth';

// Define the shape of our RBAC mapping
type Action = 'manage' | 'create' | 'read' | 'update' | 'delete' | 'review_etqa';
type Subject = 'Organisation' | 'AuditLog' | 'User' | 'Learner' | 'LearnerEnrollment' | 'WorkplaceSkillsPlan' | 'TrainingProvider' | 'WorkplaceApproval' | 'GrantApplication' | 'GrantVerification' | 'PaymentRequest' | 'AssessorModeratorApplication' | 'AssessorModExtensionOfScope' | 'Document' | 'SarsLevyDetail' | 'ReportingExtract' | 'all';

// The global policy factory
export const defineAbilitiesFor = (user?: Session['user'] | null) => {
  return defineAbility((can, cannot) => {
    // If absolutely no session, they can do nothing
    if (!user) {
      return; 
    }

    // Role-based coarse access:
    if (user.role === 'ADMIN') {
      can('manage', 'all'); // unrestricted
      return;
    }

    if (user.role === 'STANDARD') {
      // Standard users can view core domain entities, but cannot edit/create/delete
      can('read', 'Organisation');
      cannot('manage', 'Organisation'); 

      can('read', 'Learner');
      cannot('manage', 'Learner');

      can('read', 'LearnerEnrollment');
      cannot('manage', 'LearnerEnrollment');

      can('read', 'WorkplaceSkillsPlan');
      cannot('manage', 'WorkplaceSkillsPlan');

      // Grants
      can('read', 'GrantApplication');
      cannot('manage', 'GrantApplication');
      can('read', 'GrantVerification');
      cannot('manage', 'GrantVerification');
      can('read', 'PaymentRequest');
      cannot('manage', 'PaymentRequest');

      // ETQA
      can('read', 'TrainingProvider');
      cannot('manage', 'TrainingProvider');

      can('read', 'WorkplaceApproval');
      can('create', 'WorkplaceApproval'); // Providers can submit drafts
      cannot('review_etqa', 'WorkplaceApproval'); // Only ADMIN can approve/reject

      can('read', 'AssessorModeratorApplication');
      cannot('manage', 'AssessorModeratorApplication');
      can('read', 'AssessorModExtensionOfScope');
      cannot('manage', 'AssessorModExtensionOfScope');
      
      // Documents
      can('read', 'Document');
      can('create', 'Document'); // Assuming Standard users can upload files

      // Reporting
      cannot('read', 'ReportingExtract');
      cannot('manage', 'ReportingExtract');
      cannot('read', 'SarsLevyDetail');
      cannot('manage', 'SarsLevyDetail');
      
      // More examples later, e.g. can('update', 'Organisation', { ownerId: user.id })
    }
    
  });
};
