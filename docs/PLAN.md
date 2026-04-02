# Plan: Configure Core UI and State Libraries

## Task Description
The user has requested to check if React Hook Form, TanStack Query, TanStack Table, and shadcn/ui have been configured. These have not been configured yet. We will now orchestrate their installation and configuration.

## Pre-requisites
- Next.js environment is already set up.
- Tailwind CSS is partially set up.

## Phase 1: Installation & Setup
1. **Initialize shadcn/ui:**
   - Run `npx shadcn@latest init -y` inside `nsdms-next` with default options (which sets up Tailwind, Radix primitives, and `components.json`).
   - Note: The user requested shadcn/ui + Radix + Tailwind.

2. **Install React Hook Form:**
   - Run `npm install react-hook-form @hookform/resolvers zod` (zod is already present but resolvers are needed).

3. **Install TanStack Query:**
   - Run `npm install @tanstack/react-query @tanstack/react-query-devtools`.

4. **Install TanStack Table:**
   - Run `npm install @tanstack/react-table`.

## Phase 2: Configuration
1. **Providers Setup:**
   - Create a `providers.tsx` component to wrap TanStack Query (`QueryClientProvider`).
   - Add this provider to the Next.js `layout.tsx`.

2. **shadcn/ui Setup Verification:**
   - Ensure that the generated globals.css and tailwind.config.ts are properly integrated with our existing setups.
   - Install base components like button and input via `npx shadcn@latest add button input form table`.

## Phase 3: Verification
- Verify that the app builds correctly.
- Verify that the dependencies are present in `package.json`.
- Execute standard checks (`lint_runner.py`).

## Open Questions & Dependencies
- Which specific shadcn UI components do we need initially? (Will install defaults: button, input, form, table).
