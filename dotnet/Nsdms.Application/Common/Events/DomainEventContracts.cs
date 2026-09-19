using System;
using System.Threading;
using System.Threading.Tasks;

namespace Nsdms.Application.Common.Events;

/// <summary>
/// Marker interface for in-process transactional domain events.
/// </summary>
public interface IDomainEvent
{
    Guid EventId { get; }
    DateTime OccurredAtUtc { get; }
}

/// <summary>
/// Base class for domain events providing automatic GUID and UTC timestamp generation.
/// </summary>
public abstract record BaseDomainEvent : IDomainEvent
{
    public Guid EventId { get; init; } = Guid.NewGuid();
    public DateTime OccurredAtUtc { get; init; } = DateTime.UtcNow;
}

/// <summary>
/// Handler contract for typed domain event consumers.
/// </summary>
public interface IDomainEventHandler<in TEvent> where TEvent : IDomainEvent
{
    Task HandleAsync(TEvent domainEvent, CancellationToken cancellationToken = default);
}

/// <summary>
/// In-process domain event publisher interface decoupling state transitions from side-effects.
/// </summary>
public interface IDomainEventPublisher
{
    Task PublishAsync<TEvent>(TEvent domainEvent, CancellationToken cancellationToken = default) where TEvent : IDomainEvent;
}
