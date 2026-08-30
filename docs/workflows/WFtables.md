# Legacy Workflow Database Mapping

To properly hydrate the Next.js XState machines with existing process rules and active execution states from the legacy Java monolith, we must extract data from the following authoritative MySQL/MariaDB database tables. 

This document serves as the data dictionary mapping the legacy dynamic implementation to our modern `Option A` static AST definitions.

## 1. Process Configuration Modules (The Rulebook)

These tables dictate *how* a process moves and *who* is allowed to move it. In the legacy architecture, these rules were generated dynamically per process. **In our Next.js architecture, we are crystallizing this data directly into the `.ts` XState `meta` tags.**

### A. `hosting_company_process` (`HostingCompanyProcess.java`)
This is the root linked-list defining the high-level transitions for a given workflow.
*   **`workflow_process`**: The root identifier mapping to our `ConfigDocProcessEnum` (e.g., `"WSP"`, `"COMPANY_APPROVAL"`). This maps to our Next.js XState `machine.id`.
*   **`next_process_id`**: A self-referencing foreign key creating a chain of states (e.g., `Draft -> Review -> Approve`).
*   **`hosting_company_id`**: Identifies which operational silo this process sequence belongs to.

### B. `process_roles` (`ProcessRoles.java`)
This table links specific user roles and their assigned abilities to a specific step defined in `hosting_company_process`. 
*   **`hosting_company_process_id`**: Links back to the specific workflow node. 
*   **`roles_id`**: Foreign key to the legacy `Roles` lookup table (e.g., `SDF`, `Manager`, `CEO`). Maps to XState `meta.roleAllowed`.
*   **`role_permission`**: Maps to `UserPermissionEnum` (e.g., `Upload`, `View`, `FinalApproval`). Maps to XState `meta.rolePermission`.
*   **`role_order`**: Integer defining sequence. Maps to XState `meta.stepSequence`.
*   **`next_task_role`**: Foreign key identifying which `roles_id` will receive the task once this step is completed.

---

## 2. Active Execution Modules (The Work Queue)

These tables track actual runtime workflows currently in motion. **In our Next.js architecture, this table informs our 'Inbox' or 'Work Queue' dashboard components.**

### C. `tasks` (`Tasks.java`)
This is the central ledger of work. Whenever a generic business entity (like an Application or Document) undergoes a workflow transition, an active task runs here.
*   **`workflow_process`**: Identifies which XState Machine orchestrates this task.
*   **`target_class` & `target_key`**: A **polymorphic relationship** pointing to the exact business entity being processed (e.g., `target_class` = `haj.com.entity.Wsp`, `target_key` = `1456`).
*   **`task_status`**: Enumerable (`TaskStatusEnum`) identifying whether the task is `NotStarted`, `Underway`, `Completed`, or `Closed`.
*   **`hosting_company_process_id`**: The exact structural node the active task is sitting on (identifies current state).
*   **`process_role_id`**: Identifies the specific rule applied to the current assignee.
*   **`action_user_id` & `create_user_id`**: Audit trails pointing back to the `users` table for accountability.
*   **`due_date` & `rag`**: Red/Amber/Green indicators for SLA breach monitoring.

### D. `task_users` (`TaskUsers.java` - Assumed Join Table)
Because `Tasks` represents a piece of work, and multiple individuals within a specific `Role` might be allowed to action it, a one-to-many relationship exists.
*   Maps a specific `task_id` to one or more `user_id` records, manifesting as the user's explicit "Inbox".

---

## 3. Reference Lookup Modules

When extracting testing data to seed into Next.js, ensure you extract cross-references from:
*   **`roles`**: Specifically mapping `roles_id` to actual semantic names like "SDF".
*   **`company`**: Essential contextual entity for multi-tenancy rules.
*   **`users`**: The final actor identities.

## Action Plan for Finalizing Port
1. Execute a `SELECT * FROM process_roles PR JOIN hosting_company_process HCP ON ...` script to export the production role chains into CSV.
2. Cross-reference the resulting CSV arrays into our statically scaffolded `meta` structures inside `lib/machines/domains/*`.
3. Design a Next.js Prisma Model called `WorkItem` (representing the legacy `tasks` table) that can persist XState `context` transitions generically to rebuild the "Inbox/Task" queue dashboard.
