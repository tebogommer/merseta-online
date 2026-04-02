import * as React from "react"
import { cn } from "@/lib/utils"

export interface SelectNativeProps
  extends React.SelectHTMLAttributes<HTMLSelectElement> {
  options?: { value: string | number; label: string }[]
}

const SelectNative = React.forwardRef<HTMLSelectElement, SelectNativeProps>(
  ({ className, options = [], children, ...props }, ref) => {
    return (
      <select
        className={cn(
          "flex h-10 w-full rounded-md border border-input bg-background px-3 py-2 text-sm ring-offset-background placeholder:text-muted-foreground focus-visible:outline-none focus-visible:ring-2 focus-visible:ring-ring focus-visible:ring-offset-2 disabled:cursor-not-allowed disabled:opacity-50 appearance-none bg-no-repeat bg-[right_0.75rem_center] bg-[length:1em_1em]",
          className
        )}
        ref={ref}
        {...props}
      >
        {options.map((opt) => (
          <option key={opt.value} value={opt.value}>
            {opt.label}
          </option>
        ))}
        {children}
      </select>
    )
  }
)
SelectNative.displayName = "SelectNative"

export { SelectNative }
