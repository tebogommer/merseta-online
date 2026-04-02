"use client";

import * as React from "react";
import { SelectNative } from "./ui/select-native";
import { Label } from "./ui/label";

interface Lookup {
  id: number | string;
  name: string;
  code?: string;
}

interface LookupSelectProps extends React.SelectHTMLAttributes<HTMLSelectElement> {
  label?: string;
  options: Lookup[];
  error?: string;
}

export function LookupSelect({ label, options, error, className, ...props }: LookupSelectProps) {
  return (
    <div className="grid w-full items-center gap-1.5">
      {label && <Label htmlFor={props.id || props.name}>{label}</Label>}
      <SelectNative
        {...props}
        className={className}
        options={options.map((l) => ({ value: (l.code || l.id).toString(), label: l.name }))}
      >
        <option value="">Select an option...</option>
      </SelectNative>
      {error && <p className="text-xs text-destructive">{error}</p>}
    </div>
  );
}
