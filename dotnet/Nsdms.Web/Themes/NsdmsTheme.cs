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
                Primary = "#cc9c47",
                PrimaryContrastText = "#ffffff",
                Secondary = "#5e5e5e",
                SecondaryContrastText = "#ffffff",
                Tertiary = "#875224",
                TertiaryContrastText = "#ffffff",
                Info = "#0369a1",
                InfoContrastText = "#ffffff",
                Success = "#065f46",
                SuccessContrastText = "#ffffff",
                Warning = "#9a3412",
                WarningContrastText = "#ffffff",
                Error = "#ba1a1a",
                ErrorContrastText = "#ffffff",
                Dark = "#1a1c1c",
                DarkContrastText = "#ffffff",
                Background = "#f9f9f9",
                BackgroundGray = "#f3f3f3",
                Surface = "#ffffff",
                AppbarBackground = "#cc9c47",
                AppbarText = "#ffffff",
                DrawerBackground = "#f3f3f3",
                DrawerText = "#1a1c1c",
                DrawerIcon = "#524436",
                TextPrimary = "#1a1c1c",
                TextSecondary = "#524436",
                TextDisabled = "#857464",
                ActionDefault = "#524436",
                ActionDisabled = "#dadada",
                ActionDisabledBackground = "#eeeeee",
                LinesDefault = "#e2e2e2",
                LinesInputs = "#857464",
                TableLines = "#eeeeee",
                TableStriped = "#f9f9f9",
                TableHover = "#f3f3f3",
                Divider = "#e2e2e2",
                DividerLight = "#eeeeee"
            },
            PaletteDark = new PaletteDark
            {
                Primary = "#ffb962",
                PrimaryContrastText = "#472a00",
                Secondary = "#bcc7de",
                SecondaryContrastText = "#263143",
                Tertiary = "#7bd0ff",
                TertiaryContrastText = "#003549",
                Info = "#279fd2",
                InfoContrastText = "#003145",
                Success = "#34d399",
                SuccessContrastText = "#064e3b",
                Warning = "#fbbf24",
                WarningContrastText = "#451a03",
                Error = "#ffb4ab",
                ErrorContrastText = "#690005",
                Dark = "#130d07",
                DarkContrastText = "#eee0d4",
                Background = "#121212",
                BackgroundGray = "#18120c",
                Surface = "#1e1e1e",
                AppbarBackground = "#18120c",
                AppbarText = "#ffb962",
                DrawerBackground = "#18120c",
                DrawerText = "#eee0d4",
                DrawerIcon = "#d7c3b0",
                TextPrimary = "#eee0d4",
                TextSecondary = "#d7c3b0",
                TextDisabled = "#9f8e7c",
                ActionDefault = "#d7c3b0",
                ActionDisabled = "#524436",
                ActionDisabledBackground = "#251e17",
                LinesDefault = "#3b332b",
                LinesInputs = "#9f8e7c",
                TableLines = "#302921",
                TableStriped = "#211a13",
                TableHover = "#251e17",
                Divider = "#3b332b",
                DividerLight = "#302921"
            },
            LayoutProperties = new LayoutProperties
            {
                DefaultBorderRadius = "4px"
            },
            Typography = new Typography
            {
                Default = new DefaultTypography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.9375rem",
                    FontWeight = "400",
                    LineHeight = "1.5",
                    LetterSpacing = "normal"
                },
                H1 = new H1Typography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "2.25rem",
                    FontWeight = "700",
                    LineHeight = "1.25",
                    LetterSpacing = "-0.02em"
                },
                H2 = new H2Typography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "1.75rem",
                    FontWeight = "700",
                    LineHeight = "1.3",
                    LetterSpacing = "-0.02em"
                },
                H3 = new H3Typography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "1.375rem",
                    FontWeight = "600",
                    LineHeight = "1.35",
                    LetterSpacing = "-0.01em"
                },
                H4 = new H4Typography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "1.125rem",
                    FontWeight = "600",
                    LineHeight = "1.4"
                },
                H5 = new H5Typography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "1rem",
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
                    FontSize = "0.9375rem",
                    FontWeight = "400",
                    LineHeight = "1.5"
                },
                Body2 = new Body2Typography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.8125rem",
                    FontWeight = "400",
                    LineHeight = "1.43"
                },
                Button = new ButtonTypography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.875rem",
                    FontWeight = "600",
                    LineHeight = "1.75",
                    LetterSpacing = "0.02em",
                    TextTransform = "none"
                },
                Caption = new CaptionTypography
                {
                    FontFamily = new[] { "Inter", "-apple-system", "BlinkMacSystemFont", "Segoe UI", "Roboto", "Helvetica", "Arial", "sans-serif" },
                    FontSize = "0.75rem",
                    FontWeight = "400",
                    LineHeight = "1.33",
                    LetterSpacing = "0.03em"
                }
            }
        };
    }
}
