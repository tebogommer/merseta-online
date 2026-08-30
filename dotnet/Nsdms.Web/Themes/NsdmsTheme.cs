namespace Nsdms.Web.Themes;

using MudBlazor;

public static class NsdmsTheme
{
    public static MudTheme CreateTheme()
    {
        return new MudTheme
        {
            PaletteLight = new PaletteLight
            {
                // Brand Identity (merSETA Gold - used as accent, active states, key focus)
                Primary = "#b8860b",
                PrimaryDarken = "#805c06",
                PrimaryLighten = "#d4a72c",
                PrimaryContrastText = "#ffffff",

                // Neutral / Secondary
                Secondary = "#475569",
                SecondaryContrastText = "#ffffff",
                Tertiary = "#64748b",
                TertiaryContrastText = "#ffffff",

                // Semantic Status Palettes
                Info = "#0284c7",
                InfoContrastText = "#ffffff",
                Success = "#059669",
                SuccessContrastText = "#ffffff",
                Warning = "#d97706",
                WarningContrastText = "#ffffff",
                Error = "#dc2626",
                ErrorContrastText = "#ffffff",

                // Base Surfaces & Backgrounds
                Dark = "#0f172a",
                DarkContrastText = "#ffffff",
                Background = "#f8fafc",
                BackgroundGray = "#f1f5f9",
                Surface = "#ffffff",

                // Application Shell: Modern crisp dark topbar and clean white sidebar
                AppbarBackground = "#0f172a",
                AppbarText = "#f8fafc",
                DrawerBackground = "#ffffff",
                DrawerText = "#1e293b",
                DrawerIcon = "#64748b",

                // Text Hierarchy
                TextPrimary = "#0f172a",
                TextSecondary = "#475569",
                TextDisabled = "#94a3b8",

                // Action States
                ActionDefault = "#475569",
                ActionDisabled = "#cbd5e1",
                ActionDisabledBackground = "#f1f5f9",

                // Subtle Structure Lines & Dividers
                LinesDefault = "#e2e8f0",
                LinesInputs = "#94a3b8",
                TableLines = "#f1f5f9",
                TableStriped = "#f8fafc",
                TableHover = "#f1f5f9",
                Divider = "#e2e8f0",
                DividerLight = "#f1f5f9"
            },
            PaletteDark = new PaletteDark
            {
                // Brand Identity (Soft Luminous Gold in Dark Mode)
                Primary = "#e6b054",
                PrimaryDarken = "#cca042",
                PrimaryLighten = "#f7d594",
                PrimaryContrastText = "#0f172a",

                // Neutral / Secondary
                Secondary = "#94a3b8",
                SecondaryContrastText = "#0f172a",
                Tertiary = "#64748b",
                TertiaryContrastText = "#ffffff",

                // Semantic Status Palettes (Luminous for Dark Contrast)
                Info = "#38bdf8",
                InfoContrastText = "#0c4a6e",
                Success = "#10b981",
                SuccessContrastText = "#064e3b",
                Warning = "#f59e0b",
                WarningContrastText = "#451a03",
                Error = "#f87171",
                ErrorContrastText = "#450a0a",

                // Base Surfaces & Backgrounds
                Dark = "#030712",
                DarkContrastText = "#f9fafb",
                Background = "#0b0f19",
                BackgroundGray = "#111827",
                Surface = "#111827",

                // Application Shell in Dark Mode
                AppbarBackground = "#070a12",
                AppbarText = "#f8fafc",
                DrawerBackground = "#0e1422",
                DrawerText = "#f1f5f9",
                DrawerIcon = "#94a3b8",

                // Text Hierarchy
                TextPrimary = "#f8fafc",
                TextSecondary = "#94a3b8",
                TextDisabled = "#64748b",

                // Action States
                ActionDefault = "#94a3b8",
                ActionDisabled = "#374151",
                ActionDisabledBackground = "#1f2937",

                // Structure Lines & Dividers
                LinesDefault = "#1f2937",
                LinesInputs = "#374151",
                TableLines = "#1f2937",
                TableStriped = "#0f1523",
                TableHover = "#1a2234",
                Divider = "#1f2937",
                DividerLight = "#172033"
            },
            LayoutProperties = new LayoutProperties
            {
                DefaultBorderRadius = "6px",
                AppbarHeight = "56px",
                DrawerWidthLeft = "256px",
                DrawerMiniWidthLeft = "64px"
            },
            Typography = new Typography
            {
                Default = new DefaultTypography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.875rem",
                    FontWeight = "400",
                    LineHeight = "1.5",
                    LetterSpacing = "normal"
                },
                H1 = new H1Typography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "1.75rem",
                    FontWeight = "700",
                    LineHeight = "1.25",
                    LetterSpacing = "-0.02em"
                },
                H2 = new H2Typography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "1.375rem",
                    FontWeight = "600",
                    LineHeight = "1.3",
                    LetterSpacing = "-0.015em"
                },
                H3 = new H3Typography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "1.125rem",
                    FontWeight = "600",
                    LineHeight = "1.35",
                    LetterSpacing = "-0.01em"
                },
                H4 = new H4Typography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "1rem",
                    FontWeight = "600",
                    LineHeight = "1.4"
                },
                H5 = new H5Typography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.9375rem",
                    FontWeight = "600",
                    LineHeight = "1.4"
                },
                H6 = new H6Typography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.875rem",
                    FontWeight = "600",
                    LineHeight = "1.4"
                },
                Body1 = new Body1Typography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.875rem",
                    FontWeight = "400",
                    LineHeight = "1.5"
                },
                Body2 = new Body2Typography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.8125rem",
                    FontWeight = "400",
                    LineHeight = "1.45"
                },
                Button = new ButtonTypography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.875rem",
                    FontWeight = "600",
                    LineHeight = "1.75",
                    LetterSpacing = "0.01em",
                    TextTransform = "none"
                },
                Caption = new CaptionTypography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.75rem",
                    FontWeight = "400",
                    LineHeight = "1.35",
                    LetterSpacing = "0.02em"
                }
            }
        };
    }
}
