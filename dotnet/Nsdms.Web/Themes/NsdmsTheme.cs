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
                // Brand Identity (Dark Navy Action, merSETA Gold Brand)
                Primary = "#0F172A",
                PrimaryDarken = "#020617",
                PrimaryLighten = "#1E293B",
                PrimaryContrastText = "#FFFFFF",

                // Neutral / Secondary
                Secondary = "#64748B",
                SecondaryContrastText = "#FFFFFF",
                Tertiary = "#C8871E",
                TertiaryContrastText = "#FFFFFF",

                // Semantic Status Palettes
                Info = "#2563EB",
                InfoContrastText = "#FFFFFF",
                Success = "#16A34A",
                SuccessContrastText = "#FFFFFF",
                Warning = "#D97706",
                WarningContrastText = "#FFFFFF",
                Error = "#DC2626",
                ErrorContrastText = "#FFFFFF",

                // Base Surfaces & Backgrounds
                Dark = "#0F172A",
                DarkContrastText = "#FFFFFF",
                Background = "#F8FAFC",
                BackgroundGray = "#F1F5F9",
                Surface = "#FFFFFF",

                // Application Shell: Clean white appbar & sidebar
                AppbarBackground = "#FFFFFF",
                AppbarText = "#0F172A",
                DrawerBackground = "#FFFFFF",
                DrawerText = "#0F172A",
                DrawerIcon = "#64748B",

                // Text Hierarchy
                TextPrimary = "#0F172A",
                TextSecondary = "#64748B",
                TextDisabled = "#94A3B8",

                // Action States
                ActionDefault = "#64748B",
                ActionDisabled = "#CBD5E1",
                ActionDisabledBackground = "#F1F5F9",

                // Subtle Structure Lines & Dividers
                LinesDefault = "#E2E8F0",
                LinesInputs = "#CBD5E1",
                TableLines = "#F1F5F9",
                TableStriped = "#F8FAFC",
                TableHover = "#F8FAFC",
                Divider = "#E2E8F0",
                DividerLight = "#F1F5F9"
            },
            PaletteDark = new PaletteDark
            {
                // Brand Identity in Dark Mode (Luminous Gold Ochre & Soft Navy)
                Primary = "#D49336",
                PrimaryDarken = "#BF822E",
                PrimaryLighten = "#E5A348",
                PrimaryContrastText = "#0B1220",

                // Neutral / Secondary
                Secondary = "#94A3B8",
                SecondaryContrastText = "#0B1220",
                Tertiary = "#60A5FA",
                TertiaryContrastText = "#FFFFFF",

                // Semantic Status Palettes (Luminous for Dark Contrast)
                Info = "#60A5FA",
                InfoContrastText = "#0C4A6E",
                Success = "#4ADE80",
                SuccessContrastText = "#062817",
                Warning = "#FBBF24",
                WarningContrastText = "#2C1A06",
                Error = "#F87171",
                ErrorContrastText = "#2D0F0F",

                // Base Surfaces & Backgrounds
                Dark = "#0B1220",
                DarkContrastText = "#F8FAFC",
                Background = "#0B1220",
                BackgroundGray = "#141D2D",
                Surface = "#111827",

                // Application Shell in Dark Mode
                AppbarBackground = "#111827",
                AppbarText = "#F8FAFC",
                DrawerBackground = "#111827",
                DrawerText = "#F8FAFC",
                DrawerIcon = "#94A3B8",

                // Text Hierarchy
                TextPrimary = "#F8FAFC",
                TextSecondary = "#CBD5E1",
                TextDisabled = "#64748B",

                // Action States
                ActionDefault = "#94A3B8",
                ActionDisabled = "#374151",
                ActionDisabledBackground = "#1F2937",

                // Structure Lines & Dividers
                LinesDefault = "#293548",
                LinesInputs = "#3A475B",
                TableLines = "#293548",
                TableStriped = "#141D2D",
                TableHover = "#1E293B",
                Divider = "#293548",
                DividerLight = "#172033"
            },
            LayoutProperties = new LayoutProperties
            {
                DefaultBorderRadius = "8px",
                AppbarHeight = "60px",
                DrawerWidthLeft = "256px",
                DrawerMiniWidthLeft = "64px"
            },
            Typography = new Typography
            {
                Default = new DefaultTypography
                {
                    FontFamily = new[] { "Inter", "Plus Jakarta Sans", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.875rem",
                    FontWeight = "400",
                    LineHeight = "1.5",
                    LetterSpacing = "normal"
                },
                H1 = new H1Typography
                {
                    FontFamily = new[] { "Inter", "Plus Jakarta Sans", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "1.75rem",
                    FontWeight = "700",
                    LineHeight = "1.25",
                    LetterSpacing = "-0.02em"
                },
                H2 = new H2Typography
                {
                    FontFamily = new[] { "Inter", "Plus Jakarta Sans", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "1.375rem",
                    FontWeight = "600",
                    LineHeight = "1.3",
                    LetterSpacing = "-0.015em"
                },
                H3 = new H3Typography
                {
                    FontFamily = new[] { "Inter", "Plus Jakarta Sans", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "1.125rem",
                    FontWeight = "600",
                    LineHeight = "1.35",
                    LetterSpacing = "-0.01em"
                },
                H4 = new H4Typography
                {
                    FontFamily = new[] { "Inter", "Plus Jakarta Sans", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "1rem",
                    FontWeight = "600",
                    LineHeight = "1.4"
                },
                H5 = new H5Typography
                {
                    FontFamily = new[] { "Inter", "Plus Jakarta Sans", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.9375rem",
                    FontWeight = "600",
                    LineHeight = "1.4"
                },
                H6 = new H6Typography
                {
                    FontFamily = new[] { "Inter", "Plus Jakarta Sans", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.875rem",
                    FontWeight = "600",
                    LineHeight = "1.4"
                },
                Body1 = new Body1Typography
                {
                    FontFamily = new[] { "Inter", "Plus Jakarta Sans", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.875rem",
                    FontWeight = "400",
                    LineHeight = "1.5"
                },
                Body2 = new Body2Typography
                {
                    FontFamily = new[] { "Inter", "Plus Jakarta Sans", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.8125rem",
                    FontWeight = "400",
                    LineHeight = "1.45"
                },
                Button = new ButtonTypography
                {
                    FontFamily = new[] { "Inter", "Plus Jakarta Sans", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.875rem",
                    FontWeight = "600",
                    LineHeight = "1.75",
                    LetterSpacing = "0.01em",
                    TextTransform = "none"
                },
                Caption = new CaptionTypography
                {
                    FontFamily = new[] { "Inter", "Plus Jakarta Sans", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.75rem",
                    FontWeight = "400",
                    LineHeight = "1.35",
                    LetterSpacing = "0.02em"
                }
            }
        };
    }
}
