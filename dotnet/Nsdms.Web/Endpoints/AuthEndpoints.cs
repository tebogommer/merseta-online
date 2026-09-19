using System.Security.Claims;
using Microsoft.AspNetCore.Authentication;
using Microsoft.AspNetCore.Authentication.Cookies;
using Microsoft.AspNetCore.Builder;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Routing;
using Nsdms.Application.Common.Interfaces;
using Nsdms.Application.Services;

namespace Nsdms.Web.Endpoints;

public static class AuthEndpoints
{
    public static RouteGroupBuilder MapAuthEndpoints(this IEndpointRouteBuilder app)
    {
        var group = app.MapGroup("/api/auth");

        group.MapPost("/login", async (
            HttpContext context,
            IIdentityService identityService,
            IRolePermissionService roleService,
            IAuditService audit) =>
        {
            string username = string.Empty;
            string password = string.Empty;
            string returnUrl = "/";
            bool rememberMe = false;
            bool isJsonRequest = false;

            if (context.Request.HasFormContentType)
            {
                var form = await context.Request.ReadFormAsync();
                username = form["username"].ToString()?.Trim() ?? string.Empty;
                password = form["password"].ToString() ?? string.Empty;
                returnUrl = form["returnUrl"].ToString();
                rememberMe = form["rememberMe"].ToString() == "true" || form["rememberMe"].ToString() == "on";
            }
            else
            {
                isJsonRequest = true;
                try
                {
                    using var reader = new System.IO.StreamReader(context.Request.Body);
                    var jsonBody = await reader.ReadToEndAsync();
                    if (!string.IsNullOrWhiteSpace(jsonBody))
                    {
                        using var doc = System.Text.Json.JsonDocument.Parse(jsonBody);
                        var root = doc.RootElement;
                        if (root.TryGetProperty("username", out var u) || root.TryGetProperty("usernameOrEmail", out u))
                            username = u.GetString()?.Trim() ?? string.Empty;
                        if (root.TryGetProperty("password", out var p))
                            password = p.GetString() ?? string.Empty;
                        if (root.TryGetProperty("returnUrl", out var r))
                            returnUrl = r.GetString() ?? "/";
                        if (root.TryGetProperty("rememberMe", out var rm))
                            rememberMe = rm.ValueKind == System.Text.Json.JsonValueKind.True;
                    }
                }
                catch
                {
                    // Fallback to empty
                }
            }

            if (string.IsNullOrWhiteSpace(returnUrl) || !returnUrl.StartsWith('/') || returnUrl.StartsWith("//") || returnUrl.StartsWith("/\\"))
            {
                returnUrl = "/";
            }

            var authResult = await identityService.ValidateCredentialsExtendedAsync(username, password);
            if (!authResult.Succeeded)
            {
                var errorMsg = authResult.IsLockedOut
                    ? "Account is temporarily locked out due to multiple failed login attempts. Please try again in 15 minutes."
                    : (authResult.IsNotActive
                        ? "Account has been deactivated. Please contact your system administrator."
                        : (authResult.IsEmailUnconfirmed
                            ? "Email address has not been confirmed. Please check your email to activate your account."
                            : (authResult.ErrorMessage ?? "Invalid username or password.")));

                if (isJsonRequest)
                {
                    return Results.Json(new { Success = false, Succeeded = false, Message = errorMsg }, statusCode: StatusCodes.Status401Unauthorized);
                }

                return Results.Redirect($"/login?error={Uri.EscapeDataString(errorMsg)}&returnUrl={Uri.EscapeDataString(returnUrl)}");
            }

            var user = authResult.User!;
            var roles = await identityService.GetUserRolesAsync(user.Id);
            var userPermissions = await roleService.GetUserPermissionsAsync(user.Id);

            var claims = new List<Claim>
            {
                new(ClaimTypes.Name, user.UserName ?? user.Email ?? "User"),
                new(ClaimTypes.Email, user.Email ?? string.Empty),
                new(ClaimTypes.GivenName, user.Person != null ? $"{user.Person.FirstName} {user.Person.LastName}" : (user.UserName ?? "User")),
                new(ClaimTypes.NameIdentifier, user.Id.ToString()),
                new("PersonId", user.PersonId?.ToString() ?? string.Empty),
                new("OrganisationId", user.DefaultOrganisationId?.ToString() ?? string.Empty)
            };

            foreach (var role in roles)
            {
                claims.Add(new Claim(ClaimTypes.Role, role));
            }

            foreach (var perm in userPermissions)
            {
                claims.Add(new Claim("Permission", perm));
            }

            var identity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);
            var principal = new ClaimsPrincipal(identity);

            var authProperties = new AuthenticationProperties
            {
                IsPersistent = rememberMe,
                ExpiresUtc = rememberMe ? DateTimeOffset.UtcNow.AddDays(14) : DateTimeOffset.UtcNow.AddHours(8),
                IssuedUtc = DateTimeOffset.UtcNow
            };

            await context.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, principal, authProperties);

            await audit.LogActionAsync(
                "ApplicationUser",
                user.Id,
                "InteractiveLogin",
                user.UserName ?? user.Email ?? "SYSTEM",
                null,
                new { ClientIp = context.Connection.RemoteIpAddress?.ToString(), UserAgent = context.Request.Headers.UserAgent.ToString() }
            );

            if (isJsonRequest)
            {
                return Results.Ok(new { Success = true, Succeeded = true, ReturnUrl = returnUrl, Username = user.UserName });
            }

            return Results.Redirect(returnUrl);
        }).RequireRateLimiting("auth-limiter");

        // Emergency Staff Sign-in (Disaster Recovery / Entra Outage Fallback)
        group.MapPost("/backup-login", async (
            HttpContext context,
            IEntraResilienceService entraService,
            IIdentityService identityService,
            IRolePermissionService roleService,
            IAuditService audit) =>
        {
            string username = string.Empty;
            string backupPassword = string.Empty;
            string? returnUrl = null;
            bool rememberMe = false;
            bool isJsonRequest = false;

            if (context.Request.HasFormContentType)
            {
                var form = await context.Request.ReadFormAsync();
                username = form["username"].ToString()?.Trim() ?? string.Empty;
                backupPassword = form["backupPassword"].ToString() ?? string.Empty;
                returnUrl = form["returnUrl"].ToString();
                rememberMe = form["rememberMe"].ToString() == "true" || form["rememberMe"].ToString() == "on" || form.ContainsKey("rememberMe");
            }
            else
            {
                isJsonRequest = true;
                try
                {
                    using var reader = new System.IO.StreamReader(context.Request.Body);
                    var jsonBody = await reader.ReadToEndAsync();
                    if (!string.IsNullOrWhiteSpace(jsonBody))
                    {
                        using var doc = System.Text.Json.JsonDocument.Parse(jsonBody);
                        var root = doc.RootElement;
                        if (root.TryGetProperty("username", out var u) || root.TryGetProperty("usernameOrEmail", out u))
                            username = u.GetString()?.Trim() ?? string.Empty;
                        if (root.TryGetProperty("backupPassword", out var p) || root.TryGetProperty("password", out p))
                            backupPassword = p.GetString() ?? string.Empty;
                        if (root.TryGetProperty("returnUrl", out var r))
                            returnUrl = r.GetString();
                        if (root.TryGetProperty("rememberMe", out var rm))
                            rememberMe = rm.ValueKind == System.Text.Json.JsonValueKind.True;
                    }
                }
                catch
                {
                    // Fallback to empty
                }
            }

            if (string.IsNullOrWhiteSpace(returnUrl) || !returnUrl.StartsWith('/') || returnUrl.StartsWith("//") || returnUrl.StartsWith("/\\"))
            {
                returnUrl = "/";
            }

            var clientIp = context.Connection.RemoteIpAddress?.ToString();
            var userAgent = context.Request.Headers.UserAgent.ToString();

            var authResult = await entraService.ValidateBackupCredentialsAsync(username, backupPassword, clientIp, userAgent);
            if (!authResult.Succeeded)
            {
                var errorMsg = authResult.ErrorMessage ?? "Invalid username/email or emergency backup password.";
                if (isJsonRequest)
                {
                    return Results.BadRequest(new { Succeeded = false, ErrorMessage = errorMsg, authResult.IsEntraDisabled, authResult.IsLockedOut, authResult.IsGracePeriodExceeded });
                }
                return Results.Redirect($"/login?error={Uri.EscapeDataString(errorMsg)}&backupMode=true&returnUrl={Uri.EscapeDataString(returnUrl)}");
            }

            var user = authResult.User!;
            var roles = await identityService.GetUserRolesAsync(user.Id);
            var userPermissions = await roleService.GetUserPermissionsAsync(user.Id);

            var claims = new List<Claim>
            {
                new(ClaimTypes.Name, user.UserName ?? user.Email ?? "User"),
                new(ClaimTypes.Email, user.Email ?? string.Empty),
                new(ClaimTypes.GivenName, user.Person != null ? $"{user.Person.FirstName} {user.Person.LastName}" : (user.UserName ?? "User")),
                new(ClaimTypes.NameIdentifier, user.Id.ToString()),
                new("PersonId", user.PersonId?.ToString() ?? string.Empty),
                new("OrganisationId", user.DefaultOrganisationId?.ToString() ?? string.Empty),
                new("AuthMethod", "EmergencyBackupPassword")
            };

            foreach (var role in roles)
            {
                claims.Add(new Claim(ClaimTypes.Role, role));
            }

            foreach (var perm in userPermissions)
            {
                claims.Add(new Claim("Permission", perm));
            }

            var identity = new ClaimsIdentity(claims, CookieAuthenticationDefaults.AuthenticationScheme);
            var principal = new ClaimsPrincipal(identity);

            // Persistent 14-day cookie for resilient session continuity
            var authProperties = new AuthenticationProperties
            {
                IsPersistent = true,
                ExpiresUtc = DateTimeOffset.UtcNow.AddDays(14),
                IssuedUtc = DateTimeOffset.UtcNow
            };

            await context.SignInAsync(CookieAuthenticationDefaults.AuthenticationScheme, principal, authProperties);

            await audit.LogActionAsync(
                "ApplicationUser",
                user.Id,
                "EmergencyInteractiveLogin",
                user.UserName ?? user.Email ?? "SYSTEM",
                null,
                new { AuthMethod = "EmergencyBackupPassword", ClientIp = clientIp, UserAgent = userAgent, ReturnUrl = returnUrl }
            );

            if (isJsonRequest)
            {
                return Results.Ok(new { Succeeded = true, ReturnUrl = returnUrl, Username = user.UserName });
            }

            return Results.Redirect(returnUrl);
        }).RequireRateLimiting("auth-limiter");

        // Live Circuit-Breaker Health Endpoint
        group.MapGet("/entra-health", async (IEntraResilienceService entraService) =>
        {
            var status = await entraService.CheckEntraServiceHealthAsync();
            var isOutage = status == EntraHealthStatus.OutageDetected || status == EntraHealthStatus.ManualOverride;
            return Results.Ok(new
            {
                Status = status.ToString(),
                IsOutage = isOutage,
                Message = isOutage
                    ? "Microsoft Entra ID service degradation detected. merSETA Emergency Access Contingency is active."
                    : "Microsoft Entra ID is responding normally."
            });
        });

        group.MapGet("/logout", async (HttpContext context) =>
        {
            await context.SignOutAsync(CookieAuthenticationDefaults.AuthenticationScheme);
            return Results.Redirect("/login?loggedOut=true");
        });

        group.MapGet("/me", (HttpContext context) =>
        {
            return Results.Ok(new
            {
                Authenticated = context.User.Identity?.IsAuthenticated,
                Username = context.User.Identity?.Name,
                Roles = context.User.Claims.Where(c => c.Type == ClaimTypes.Role).Select(c => c.Value).ToList(),
                Permissions = context.User.Claims.Where(c => c.Type == "Permission").Select(c => c.Value).ToList(),
                AuthenticationType = context.User.Identity?.AuthenticationType,
                Claims = context.User.Claims.Select(c => new { c.Type, c.Value })
            });
        }).RequireAuthorization();

        return group;
    }
}
