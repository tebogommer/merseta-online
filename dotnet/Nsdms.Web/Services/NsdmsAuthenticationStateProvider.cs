namespace Nsdms.Web.Services;

using System.Security.Claims;
using Microsoft.AspNetCore.Components.Authorization;

public class NsdmsAuthenticationStateProvider : AuthenticationStateProvider
{
    private ClaimsPrincipal _currentUser;

    public NsdmsAuthenticationStateProvider()
    {
        _currentUser = CreatePrincipal("sysadmin@merseta.org.za", "System Administrator", new[] 
        { 
            "SuperAdmin", 
            "Admin", 
            "FinanceManager", 
            "CLO", 
            "SDF", 
            "SDP", 
            "Assessor", 
            "Executive", 
            "Legal", 
            "Compliance" 
        });
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
