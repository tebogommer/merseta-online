"use client";

import { useTransition } from "react";
import { Button } from "@/components/ui/button";
import { Plus, UserMinus } from "lucide-react";

export function LearnerActionsBridge({ learnerId }: { learnerId: number }) {
  const [isPending, startTransition] = useTransition();

  const handleEnrollment = () => {
    startTransition(() => {
      // Logic to start new enrollment or direct to new enrollment wizard with this learner
      // e.g. router.push(`/learners/${learnerId}/enrollments/new`)
      alert("New enrollment workflow not yet implemented.");
    });
  };

  const handleTermination = () => {
    startTransition(() => {
      alert("Global termination not yet implemented.");
    });
  };

  return (
    <div className="flex items-center gap-3">
      <Button 
        variant="outline" 
        onClick={handleTermination}
        disabled={isPending}
        className="flex items-center gap-2 border-red-200 text-red-700 hover:bg-red-50"
      >
        <UserMinus className="w-4 h-4" />
        Terminate Records
      </Button>
      <Button 
        onClick={handleEnrollment}
        disabled={isPending}
        className="flex items-center gap-2"
      >
        <Plus className="w-4 h-4" />
        New Enrollment
      </Button>
    </div>
  );
}
