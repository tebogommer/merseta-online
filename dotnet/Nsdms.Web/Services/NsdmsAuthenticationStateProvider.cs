namespace Nsdms.Web.Services;

using System.Security.Claims;
using Microsoft.AspNetCore.Components.Authorization;
using Microsoft.AspNetCore.Http;

public class NsdmsAuthenticationStateProvider : AuthenticationStateProvider
{
    private ClaimsPrincipal _currentUser;
    private readonly IHttpContextAccessor? _httpContextAccessor;

    public NsdmsAuthenticationStateProvider(IHttpContextAccessor? httpContextAccessor = null)
    {
        _httpContextAccessor = httpContextAccessor;

        var httpUser = _httpContextAccessor?.HttpContext?.User;
        if (httpUser?.Identity?.IsAuthenticated == true)
        {
            _currentUser = httpUser;
        }
        else
        {
            _currentUser = new ClaimsPrincipal(new ClaimsIdentity());
        }
    }

    public override Task<AuthenticationState> GetAuthenticationStateAsync()
    {
        return Task.FromResult(new AuthenticationState(_currentUser));
    }

    public void SetUser(string email, string displayName, IEnumerable<string> roles)
    {
        _currentUser = CreatePrincipal(email, displayName, roles);
        NotifyAuthenticationStateChanged(GetAuthenticationStateAsync());
    }

    public void SetPrincipal(ClaimsPrincipal principal)
    {
        _currentUser = principal;
        NotifyAuthenticationStateChanged(GetAuthenticationStateAsync());
    }

    public void SignOutUser()
    {
        _currentUser = new ClaimsPrincipal(new ClaimsIdentity());
        NotifyAuthenticationStateChanged(GetAuthenticationStateAsync());
    }

    public void ResetToAuthenticatedUser()
    {
        var httpUser = _httpContextAccessor?.HttpContext?.User;
        if (httpUser?.Identity?.IsAuthenticated == true)
        {
            _currentUser = httpUser;
        }
        else
        {
            _currentUser = new ClaimsPrincipal(new ClaimsIdentity());
        }
        NotifyAuthenticationStateChanged(GetAuthenticationStateAsync());
    }

    public static ClaimsPrincipal CreatePrincipal(string email, string displayName, IEnumerable<string> roles)
    {
        var claims = new List<Claim>
        {
            new(ClaimTypes.Name, email),
            new(ClaimTypes.Email, email),
            new(ClaimTypes.GivenName, displayName),
            new(ClaimTypes.NameIdentifier, "1")
        };

        foreach (var role in roles)
        {
            claims.Add(new Claim(ClaimTypes.Role, role));
        }

        var identity = new ClaimsIdentity(claims, "NsdmsAuthType");
        return new ClaimsPrincipal(identity);
    }
}
