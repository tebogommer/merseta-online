using System;

namespace Nsdms.Infrastructure.Services.DocumentCompilers;

/// <summary>
/// Strategy pattern contract for compiling strongly-typed domain models into official PDF documents.
/// Decomposes monolithic document generation into single-responsibility compilers.
/// </summary>
/// <typeparam name="TModel">Domain entity or view-model representing the document context.</typeparam>
public interface IDocumentCompiler<in TModel>
{
    /// <summary>
    /// Unique identifier code of the statutory document family (e.g. MOA_CONTRACT, TRADE_TEST_CERTIFICATE).
    /// </summary>
    string DocumentType { get; }

    /// <summary>
    /// Compiles the domain model into a rendered PDF byte array with QuestPDF layout and verification seals.
    /// </summary>
    byte[] Compile(TModel model);
}
