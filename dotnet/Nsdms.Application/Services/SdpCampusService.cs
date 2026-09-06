using Nsdms.Application.Common;

namespace Nsdms.Application.Services;

/// <summary>
/// Legacy service implementation deriving from SdpSiteService for backward compatibility.
/// </summary>
public class SdpCampusService : SdpSiteService
{
    public SdpCampusService(INsdmsDbContextFactory contextFactory, IAuditService audit)
        : base(contextFactory, audit)
    {
    }
}
