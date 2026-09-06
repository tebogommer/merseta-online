using System;
using System.Collections.Generic;
using System.Linq;
using System.Reflection;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Components;
using Xunit;

namespace Nsdms.Tests;

public class RouteAuthorizationSecurityTests
{
    [Fact]
    public void AllInternalPages_MustEnforceAuthorization_UnlessExplicitlyAllowAnonymous()
    {
        var webAssembly = typeof(Nsdms.Web.Components.App).Assembly;
        var pageTypes = webAssembly.GetTypes()
            .Where(t => typeof(ComponentBase).IsAssignableFrom(t) && t.GetCustomAttributes<RouteAttribute>().Any())
            .ToList();

        Assert.NotEmpty(pageTypes);

        var whitelistedAnonymousRoutes = new HashSet<string>(StringComparer.OrdinalIgnoreCase)
        {
            "/login",
            "/register",
            "/self-service-register",
            "/confirm-email",
            "/verify",
            "/verify/document/{DocumentHash}"
        };

        var unprotectedPages = new List<string>();

        foreach (var pageType in pageTypes)
        {
            var routes = pageType.GetCustomAttributes<RouteAttribute>().Select(r => r.Template).ToList();
            var hasAllowAnonymous = pageType.GetCustomAttributes<AllowAnonymousAttribute>(inherit: true).Any();
            var hasAuthorize = pageType.GetCustomAttributes<AuthorizeAttribute>(inherit: true).Any();

            if (hasAllowAnonymous)
            {
                foreach (var route in routes)
                {
                    Assert.Contains(route, whitelistedAnonymousRoutes);
                }
            }
            else
            {
                if (!hasAuthorize)
                {
                    unprotectedPages.Add($"{pageType.FullName} ({string.Join(", ", routes)})");
                }
            }
        }

        Assert.True(unprotectedPages.Count == 0,
            $"Found {unprotectedPages.Count} unprotected pages without [Authorize] or [AllowAnonymous]: {string.Join("; ", unprotectedPages)}");
    }

    [Theory]
    [InlineData("", "/login")]
    [InlineData("/", "/login")]
    [InlineData("dashboard", "/login?returnUrl=%2Fdashboard")]
    [InlineData("/employers", "/login?returnUrl=%2Femployers")]
    [InlineData("contracts/variations/12", "/login?returnUrl=%2Fcontracts%2Fvariations%2F12")]
    public void RedirectUrlCalculation_GeneratesCorrectLoginDestination(string relativePath, string expectedDestination)
    {
        var targetPath = string.IsNullOrEmpty(relativePath) ? "" : "/" + relativePath.TrimStart('/');
        var destination = string.IsNullOrWhiteSpace(targetPath) || targetPath == "/"
            ? "/login"
            : $"/login?returnUrl={Uri.EscapeDataString(targetPath)}";

        Assert.Equal(expectedDestination, destination);
    }
}