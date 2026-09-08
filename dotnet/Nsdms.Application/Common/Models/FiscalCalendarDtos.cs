namespace Nsdms.Application.Common.Models;

/// <summary>
/// Breakdown of a single month within a financial quarter or year,
/// detailing exact calendar days, statutory working days, and public holiday observances.
/// </summary>
public class FiscalMonthBreakdownDto
{
    public int MonthNumber { get; set; }
    public string MonthName { get; set; } = string.Empty;
    public int CalendarYear { get; set; }
    public DateTime StartDate { get; set; }
    public DateTime EndDate { get; set; }
    public int CalendarDaysCount { get; set; }
    public int WorkingDaysCount { get; set; }
    public int PublicHolidaysCount { get; set; }
    public List<string> ObservedHolidays { get; set; } = new();
}

/// <summary>
/// Aggregated breakdown of a statutory quarter, containing constituent months.
/// </summary>
public class FiscalQuarterBreakdownDto
{
    public string QuarterCode { get; set; } = string.Empty;
    public int QuarterNumber { get; set; }
    public DateTime StartDate { get; set; }
    public DateTime EndDate { get; set; }
    public int TotalCalendarDays { get; set; }
    public int TotalWorkingDays { get; set; }
    public int TotalPublicHolidays { get; set; }
    public List<FiscalMonthBreakdownDto> Months { get; set; } = new();
}

/// <summary>
/// Comprehensive breakdown of an entire financial year, containing its constituent quarters and months.
/// </summary>
public class FiscalYearBreakdownDto
{
    public string FinYearCode { get; set; } = string.Empty;
    public int StartYear { get; set; }
    public int EndYear { get; set; }
    public DateTime StartDate { get; set; }
    public DateTime EndDate { get; set; }
    public int TotalCalendarDays { get; set; }
    public int TotalWorkingDays { get; set; }
    public int TotalPublicHolidays { get; set; }
    public List<FiscalQuarterBreakdownDto> Quarters { get; set; } = new();
}

/// <summary>
/// Baseline statutory template configuration for South African financial years and quarters.
/// </summary>
public class FiscalTemplateConfigDto
{
    public string DefaultStartMonthDay { get; set; } = "04-01";
    public string DefaultEndMonthDay { get; set; } = "03-31";
    public string Q1StartMonthDay { get; set; } = "04-01";
    public string Q1EndMonthDay { get; set; } = "06-30";
    public string Q2StartMonthDay { get; set; } = "07-01";
    public string Q2EndMonthDay { get; set; } = "09-30";
    public string Q3StartMonthDay { get; set; } = "10-01";
    public string Q3EndMonthDay { get; set; } = "12-31";
    public string Q4StartMonthDay { get; set; } = "01-01";
    public string Q4EndMonthDay { get; set; } = "03-31";
}

/// <summary>
/// Date boundaries and locking state for a single quarter.
/// </summary>
public class QuarterDateRangeDto
{
    public int? Id { get; set; }
    public string QuarterCode { get; set; } = string.Empty;
    public int QuarterNumber { get; set; }
    public DateTime StartDate { get; set; }
    public DateTime EndDate { get; set; }
    public string? Description { get; set; }
    public bool IsLocked { get; set; }
    public bool IsClosed { get; set; }
}

/// <summary>
/// DTO for creating a new FinancialYear with its constituent quarters.
/// </summary>
public class CreateFinancialYearDto
{
    public string FinYearCode { get; set; } = string.Empty;
    public int StartYear { get; set; }
    public int EndYear { get; set; }
    public DateTime StartDate { get; set; }
    public DateTime EndDate { get; set; }
    public string? Description { get; set; }
    public string StatusCode { get; set; } = "Draft";
    public bool IsActive { get; set; } = false;
    public List<QuarterDateRangeDto> Quarters { get; set; } = new();
}

/// <summary>
/// DTO for updating an existing FinancialYear and its quarters.
/// </summary>
public class UpdateFinancialYearDto
{
    public int Id { get; set; }
    public string FinYearCode { get; set; } = string.Empty;
    public int StartYear { get; set; }
    public int EndYear { get; set; }
    public DateTime StartDate { get; set; }
    public DateTime EndDate { get; set; }
    public string? Description { get; set; }
    public string StatusCode { get; set; } = "Draft";
    public bool IsActive { get; set; } = false;
    public bool IsClosed { get; set; } = false;
    public List<QuarterDateRangeDto> Quarters { get; set; } = new();
}

/// <summary>
/// Result of contiguity and date integrity validation on quarters and years.
/// </summary>
public class FiscalValidationResult
{
    public bool IsValid => Errors.Count == 0;
    public List<string> Errors { get; set; } = new();
    public List<string> Warnings { get; set; } = new();
}
