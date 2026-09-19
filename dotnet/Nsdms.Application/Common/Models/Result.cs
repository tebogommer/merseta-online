using System;
using System.Diagnostics.CodeAnalysis;

namespace Nsdms.Application.Common.Models;

/// <summary>
/// Domain error representation encapsulating statutory error code,
/// human-readable diagnostic message, and error taxonomy type.
/// </summary>
public record Error(string Code, string Description)
{
    public static readonly Error None = new(string.Empty, string.Empty);
    public static readonly Error NullValue = new("Error.NullValue", "The specified value is null.");
    public static readonly Error ConditionNotMet = new("Error.ConditionNotMet", "The required business condition was not met.");
    public static readonly Error NotFound = new("Error.NotFound", "The requested entity or resource was not found.");
    public static readonly Error Unauthorized = new("Error.Unauthorized", "The current user is not authorized to perform this operation.");
    public static readonly Error Conflict = new("Error.Conflict", "The operation resulted in a business conflict or invariant violation.");
}

/// <summary>
/// Functional Result pattern representation for non-exception business control flow.
/// </summary>
public class Result
{
    protected Result(bool isSuccess, Error error)
    {
        if (isSuccess && error != Error.None)
        {
            throw new InvalidOperationException("A successful result cannot have an error.");
        }

        if (!isSuccess && error == Error.None)
        {
            throw new InvalidOperationException("A failed result must specify an error.");
        }

        IsSuccess = isSuccess;
        Error = error;
    }

    public bool IsSuccess { get; }
    public bool IsFailure => !IsSuccess;
    public Error Error { get; }

    public static Result Success() => new(true, Error.None);
    public static Result Failure(Error error) => new(false, error);
    public static Result<TValue> Success<TValue>(TValue value) => new(value, true, Error.None);
    public static Result<TValue> Failure<TValue>(Error error) => new(default, false, error);
}

/// <summary>
/// Typed generic functional Result pattern carrying typed value payloads.
/// </summary>
public class Result<TValue> : Result
{
    private readonly TValue? _value;

    protected internal Result(TValue? value, bool isSuccess, Error error)
        : base(isSuccess, error)
    {
        _value = value;
    }

    [NotNull]
    public TValue Value => IsSuccess
        ? _value!
        : throw new InvalidOperationException("The value of a failed result cannot be accessed.");

    public TValue? ValueOrDefault => _value;

    public static implicit operator Result<TValue>(TValue? value) =>
        value is not null ? Success(value) : Failure<TValue>(Error.NullValue);
}
