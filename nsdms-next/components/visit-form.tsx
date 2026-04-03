"use client";

import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import * as z from "zod";
import { Form, FormControl, FormField, FormItem, FormLabel, FormMessage } from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { Button } from "@/components/ui/button";
import { CalendarIcon } from "lucide-react";

export const visitSchema = z.object({
  date: z.string().nonempty("Date is required"),
  purpose: z.string().min(3, "Purpose must be at least 3 characters"),
  contactPersonId: z.coerce.number().min(1, "A Contact Person is required for any Visit"),
});

export type VisitFormValues = z.infer<typeof visitSchema>;

interface VisitFormProps {
  onSubmit: (data: VisitFormValues) => void;
  contactPersons: { id: number; name: string }[];
  isSubmitting?: boolean;
}

export function VisitForm({ onSubmit, contactPersons, isSubmitting = false }: VisitFormProps) {
  const form = useForm<VisitFormValues>({
    resolver: zodResolver(visitSchema),
    defaultValues: {
      date: "",
      purpose: "",
      contactPersonId: 0,
    },
  });

  return (
    <Form {...form}>
      <form onSubmit={form.handleSubmit(onSubmit)} className="space-y-4">
        <FormField
          control={form.control}
          name="date"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Visit Date</FormLabel>
              <FormControl>
                <div className="relative">
                  <Input type="date" {...field} />
                </div>
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <FormField
          control={form.control}
          name="purpose"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Purpose of Visit</FormLabel>
              <FormControl>
                <Input placeholder="e.g., Site Inspection, Audit" {...field} />
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        {/* Global Rule Enforcement: Must Select Contact Person */}
        <FormField
          control={form.control}
          name="contactPersonId"
          render={({ field }) => (
            <FormItem>
              <FormLabel>Contact Person (Required)</FormLabel>
              <FormControl>
                <select
                  {...field}
                  className="flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background disabled:cursor-not-allowed disabled:opacity-50"
                >
                  <option value={0}>-- Select a Contact Person --</option>
                  {contactPersons.map((person) => (
                    <option key={person.id} value={person.id}>
                      {person.name}
                    </option>
                  ))}
                </select>
              </FormControl>
              <FormMessage />
            </FormItem>
          )}
        />

        <div className="flex justify-end gap-2 pt-4">
          <Button type="submit" disabled={isSubmitting}>
            {isSubmitting ? "Scheduling..." : "Schedule Visit"}
          </Button>
        </div>
      </form>
    </Form>
  );
}
