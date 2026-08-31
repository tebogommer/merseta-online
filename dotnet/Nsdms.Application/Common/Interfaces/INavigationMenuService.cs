using System.Collections.Generic;
using System.Threading.Tasks;

namespace Nsdms.Application.Common.Interfaces;

public class NavItemDto
{
    public string Id { get; set; } = string.Empty;
    public string Title { get; set; } = string.Empty;
    public string Href { get; set; } = string.Empty;
    public string Icon { get; set; } = string.Empty;
    public string Category { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public string? RequiredModule { get; set; }
    public string? RequiredAction { get; set; }
    public List<string> RequiredRoles { get; set; } = new();
    public int BadgeCount { get; set; }
    public string BadgeColor { get; set; } = "Primary";
    public bool IsPinned { get; set; }
    public int DisplayOrder { get; set; }
    public List<string> Keywords { get; set; } = new();
    public List<string> PersonaTags { get; set; } = new();
    public bool ExactMatch { get; set; } = false;
}

public class NavGroupDto
{
    public string GroupId { get; set; } = string.Empty;
    public string GroupName { get; set; } = string.Empty;
    public string Icon { get; set; } = string.Empty;
    public int DisplayOrder { get; set; }
    public bool IsExpanded { get; set; } = true;
    public List<NavItemDto> Items { get; set; } = new();
}

public class UserNavPreferencesDto
{
    public string Username { get; set; } = string.Empty;
    public List<string> PinnedItemIds { get; set; } = new();
    public List<string> CollapsedGroupIds { get; set; } = new();
    public string ActivePersonaFilter { get; set; } = "All";
}

public class NavigationTreeResultDto
{
    public List<NavItemDto> PinnedItems { get; set; } = new();
    public List<NavGroupDto> Groups { get; set; } = new();
    public string ActivePersona { get; set; } = "All";
    public List<string> AvailablePersonas { get; set; } = new();
    public int TotalItemsCount { get; set; }
    public int TotalPendingBadgeCount { get; set; }
}

public class OmnisearchResultItemDto
{
    public string Id { get; set; } = string.Empty;
    public string Title { get; set; } = string.Empty;
    public string Href { get; set; } = string.Empty;
    public string Icon { get; set; } = string.Empty;
    public string Category { get; set; } = string.Empty;
    public string Description { get; set; } = string.Empty;
    public string ItemType { get; set; } = "Navigation"; // Navigation, QuickAction, AdminConfig, ReferenceData
    public string? BadgeText { get; set; }
    public string BadgeColor { get; set; } = "Primary";
    public int Score { get; set; }
}

public interface INavigationMenuService
{
    Task<NavigationTreeResultDto> GetUserNavigationTreeAsync(string username, List<string> roles, HashSet<string> permissions, string? activePersona = null);
    Task<List<NavItemDto>> GetPinnedItemsAsync(string username);
    Task<bool> PinItemAsync(string username, string itemId);
    Task<bool> UnpinItemAsync(string username, string itemId);
    Task<bool> TogglePinItemAsync(string username, string itemId);
    Task<UserNavPreferencesDto> GetUserPreferencesAsync(string username);
    Task<bool> SaveUserPreferencesAsync(UserNavPreferencesDto preferences);
    Task<List<OmnisearchResultItemDto>> SearchOmnisearchAsync(string query, string username, List<string> roles, HashSet<string> permissions, int maxResults = 25);
    Task<Dictionary<string, int>> GetDynamicBadgeCountsAsync(string username, List<string> roles);
    List<string> GetPredefinedPersonas();
}
