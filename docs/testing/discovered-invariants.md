# Discovered Invariants

## 1. Security & Authorization Constraints (CASL)
- **Invariant:** A user's ability policy dictates what they can view or mutate at the server level.
- **Invariant:** STANDARD users cannot mutate global state. They can only view core entities (Organisations, TrainingProviders) unless specifically allowed via `createdBy` relationships.
- **Invariant:** ADMIN users can `manage all`.

## 2. Activity / Verification Checks
- **Invariant:** Any module scheduling a `Visit` must ensure that the visit corresponds to an existing `contactPersonId`. (Reference: UI constraint).

## 3. Auditing & Traceability
- **Invariant:** All system mutations MUST feature a Double Write: Update the target record, AND create an Audit Log snapshot.
- **Invariant:** The Audit Log must record the `actor`, `actionName`, `recordId`, `entityName` and JSON stringified `snapshot`.

## 4. UI / Visual Parity Constraints
- **Invariant:** The "Smart Shell / Action Bridge" pattern requires the data retrieval (`fetch`) to reside purely in Server files or server actions, preventing the Client component from touching Prisma.
- **Invariant:** Detail forms must reside alongside child grids (Stacked Tabs).

## 5. Testing & Failure Policy
- **Invariant:** The agent operates under the `Controlled Fix-As-You-Go Bug Policy`, meaning test defects discovered during run execution must be triaged immediately, categorised as Harness, Product, or Test errors, and treated sequentially.
