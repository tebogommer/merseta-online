using System;
using System.Threading;
using Microsoft.AspNetCore.Components;

namespace Nsdms.Web.Components.Shared;

/// <summary>
/// Base Razor component that automatically creates and manages a circuit-aware CancellationTokenSource.
/// Cancels all in-flight async service and EF Core queries when the component is unmounted or user navigates away.
/// </summary>
public abstract class CancellableComponentBase : ComponentBase, IDisposable
{
    private readonly CancellationTokenSource _cts = new();
    private bool _disposed;

    /// <summary>
    /// Gets the CancellationToken bound to this component's active circuit lifecycle.
    /// Passes to async service and database queries to ensure immediate cancellation upon navigation.
    /// </summary>
    protected CancellationToken CancellationToken => _cts.Token;

    public virtual void Dispose()
    {
        if (_disposed) return;
        _disposed = true;

        try
        {
            _cts.Cancel();
            _cts.Dispose();
        }
        catch (ObjectDisposedException)
        {
            // Already disposed
        }

        GC.SuppressFinalize(this);
    }
}
