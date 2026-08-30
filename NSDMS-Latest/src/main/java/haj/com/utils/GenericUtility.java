package haj.com.utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.joda.time.DateTime;
import org.joda.time.DateTimeConstants;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.Months;
import org.joda.time.Years;
import org.krysalis.barcode4j.impl.code39.Code39Bean;
import org.krysalis.barcode4j.output.bitmap.BitmapCanvasProvider;
import org.paukov.combinatorics.Factory;
import org.paukov.combinatorics.Generator;
import org.paukov.combinatorics.ICombinatoricsVector;
import org.primefaces.model.UploadedFile;

import haj.com.bean.AttachmentBean;
import haj.com.bean.BulkMailBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.Doc;
import haj.com.entity.Employees;
import haj.com.entity.MailLog;
import haj.com.entity.MandatoryGrantDetail;
import haj.com.entity.TaskUsers;
import haj.com.entity.TrainingComittee;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.RagEnum;
import haj.com.entity.enums.RsaCitizenTypeEnum;
import haj.com.service.MailLogService;
import haj.com.service.SendMail;
import haj.com.service.TasksService;
import haj.com.service.UsersService;
import haj.com.service.lookup.GenderService;
import haj.com.service.lookup.NationalityService;
import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericUtility.
 */
public class GenericUtility implements Serializable {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant logger. */
	protected static final Log logger = LogFactory.getLog(GenericUtility.class);

	/** The Constant sdf. */
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	private static final SimpleDateFormat sdfIdDate = new SimpleDateFormat("yyMMdd");

	/** The Constant sdf2. */
	public static final SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat sdfA = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	/** The Constant sdf3. */
	public static final SimpleDateFormat sdf3 = new SimpleDateFormat("dd MMMM yyyy (HH:mm:ss)");

	/** The Constant sdf5. */
	public static final SimpleDateFormat sdf5 = new SimpleDateFormat("dd MMMM yyyy");

	public static final SimpleDateFormat sdf6 = new SimpleDateFormat("dd-MM-yyyy");
	
	public static final SimpleDateFormat sdf7 = new SimpleDateFormat("dd/MM/yyyy");

	public static final SimpleDateFormat sdfYear = new SimpleDateFormat("yyyy");

	/** The Constant timestamp. */
	private static final SimpleDateFormat timestamp = new SimpleDateFormat("yyyyMMddhhmmssSSSS");

	/** The Constant sdf4. */
	public static final SimpleDateFormat sdf4 = new SimpleDateFormat("HH:mm");

	/** The mailer. */
	private static SendMail mailer = new SendMail();
	public final static SimpleDateFormat sdfa = new SimpleDateFormat("yyyy-MM-dd");
	public final static SimpleDateFormat sdfYYYY = new SimpleDateFormat("yyyy");
	/**
	 * Start of the SARS financial year
	 */
	public final static String SARS_FIN_YEAR_START = "-03-01";
	public final static String APRIL = "-04-01";
	public final static String MARCH = "-03-31";
	private static final String NSDMS_SUPPORT_EMAIL_PROPERTY = "nsdms.support.team.email.list";
	/**
	 * All permutations.
	 *
	 * @param sf
	 *            the sf
	 * @return the list
	 */
	public static List<String> allPermutations(String sf) {
		List<String> fl = new ArrayList<String>();
		List<String> tl = new ArrayList<String>();
		List<String> l = new ArrayList<String>(Arrays.asList(sf.split(" ")));
		for (String ts : l) {
			tl.add(ts.trim());
		}
		ICombinatoricsVector<String> originalVector = Factory.createVector(tl);
		Generator<String> gen = Factory.createPermutationGenerator(originalVector);
		for (ICombinatoricsVector<String> perm : gen) {
			String fs = "";
			for (String s : perm) {
				fs += '%' + s.trim();
			}
			fl.add(fs + '%');
		}

		return fl;
	}

	/**
	 * Creates the all permutations like.
	 *
	 * @param field
	 *            the field
	 * @param sf
	 *            the sf
	 * @return the string
	 */
	public static String createAllPermutationsLike(String field, String sf) {
		String like = "and ( ";
		List<String> l = GenericUtility.allPermutations(sf);
		for (String s : l) {
			like = like + field + " like '" + s + "' or ";
		}
		return like.substring(0, like.length() - 3) + ")";
	}

	/**
	 * Mobile date.
	 *
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String mobileDate(Date date) {
		String d = "";
		try {
			if (date != null) d = sdf3.format(date);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return d;
	}

	/**
	 * Sql reg exp.
	 *
	 * @param value
	 *            the value
	 * @return the string
	 */
	public static String sqlRegExp(String value) {
		if (value == null || value.trim().length() == 0) return "";
		String[] x = value.split(" ");
		String f = "";
		for (String string : x) {
			f = f + string.trim() + "|";
		}
		return "'" + (f.substring(0, f.lastIndexOf('|'))) + "'";
	}

	/**
	 * Removes the list char.
	 *
	 * @param s
	 *            the s
	 * @return the string
	 */
	public static String removeListChar(String s) {
		s = s.replace(']', ' ');
		s = s.replace('[', ' ');
		return s.trim();
	}

	/**
	 * Creates the DM S I N variables.
	 *
	 * @param l
	 *            the l
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String createDMS_IN_Variables(List<String> l) throws Exception {
		String ss[] = GenericUtility.removeListChar(l.toString()).trim().split(",");
		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < ss.length; i++) {
			buf.append('\'');
			buf.append(ss[i].trim());
			buf.append('\'');
			buf.append(',');
		}
		return buf.toString().substring(0, buf.toString().length() - 1);
	}

	/**
	 * Inits the sql in clause.
	 *
	 * @param in
	 *            the in
	 * @return the string
	 */
	public static String initSqlInClause(String in) {
		in = removeListChar(in);
		String[] c = in.split(",");
		String result = "(";
		if (c.length == 1) result = result + "'" + in.trim() + "'";
		else {
			for (String s : c) {
				result = result + "'" + s.trim() + "',";
			}
			result = result.substring(0, result.lastIndexOf(','));
		}

		result += ")";

		return result;
	}

	public static String initNumericSqlInClause(String in) {
		in = removeListChar(in);
		String[] c = in.split(",");
		String result = "(";
		if (c.length == 1) result = result + in.trim();
		else {
			for (String s : c) {
				result = result + s.trim() + ",";
			}
			result = result.substring(0, result.lastIndexOf(','));
		}

		result += ")";

		return result;
	}

	/**
	 * Convert to list.
	 *
	 * @param s
	 *            the s
	 * @return the list
	 */
	public static List<String> convertToList(String s) {
		if (s == null || s.length() == 0) return new ArrayList<String>();
		List<String> l = null;
		try {
			s = removeListChar(s);
			String[] pieces = s.split(",");
			for (int i = 0; i < pieces.length; i++) {
				pieces[i] = pieces[i].trim();
			}
			l = new ArrayList<String>(Arrays.asList(pieces));
		} catch (Exception e) {
			logger.fatal(e);
		}
		return l;
	}

	/**
	 * Convert to map.
	 *
	 * @param s
	 *            the s
	 * @return the map
	 */
	public static Map<String, String> convertToMap(String s) {
		Map<String, String> m = new HashMap<String, String>();
		try {
			s = removeListChar(s);
			String[] pieces = s.split(",");
			for (int i = 0; i < pieces.length; i++) {
				pieces[i] = pieces[i].trim();
				m.put(pieces[i], pieces[i]);
			}

		} catch (Exception e) {
			logger.fatal(e);
		}
		return m;
	}

	/**
	 * Adjust date.
	 *
	 * @param start
	 *            the start
	 * @param days
	 *            the days
	 * @param min
	 *            the min
	 * @return the date
	 */
	public static Date adjustDate(Date start, int days, int min) {
		DateTime ld = new DateTime(start);
		if (days != 0) {
			if (days < 0) ld.minusDays(days);
			else ld.plusDays(days);
		}

		if (min != 0) {
			if (min < 0) ld.minusMinutes(min);
			else ld.plusMinutes(min);
		}
		return ld.toDate();
	}

	/**
	 * Adds the days to date.
	 *
	 * @param start
	 *            the start
	 * @param days
	 *            the days
	 * @return the date
	 */
	public static Date addDaysToDate(Date start, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		cal.add(Calendar.DATE, days);
		return cal.getTime();
	}

	/**
	 * Adds the days to date exclude weekends.
	 *
	 * @param start
	 *            the start
	 * @param days
	 *            the days
	 * @return the date
	 */
	public static Date addDaysToDateExcludeWeekends(Date start, int days) {
		if (days < 1) {
			return start;
		}
		LocalDate date = LocalDate.parse(HAJConstants.sdf.format(start));
		LocalDate result = date;

		int addedDays = 0;
		while (addedDays < days) {
			result = result.plusDays(1);
			if (!(result.getDayOfWeek() == DayOfWeek.SATURDAY.getValue() || result.getDayOfWeek() == DayOfWeek.SUNDAY.getValue())) {
				++addedDays;
			}
		}

		return result.toDate();
	}

	/**
	 * Deduct days from date.
	 *
	 * @param start
	 *            the start
	 * @param days
	 *            the days
	 * @return the date
	 */
	public static Date deductDaysFromDate(Date start, int days) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(start);
		cal.add(Calendar.DATE, -days);
		return cal.getTime();
	}

	/**
	 * Gets the first day of this month.
	 *
	 * @return the first day of this month
	 */
	public static Date getFirstDayOfThisMonth() {
		return (new LocalDate().withDayOfMonth(1)).toDate();
	}

	/**
	 * Gets the lastt day of this month.
	 *
	 * @return the lastt day of this month
	 */
	public static Date getLasttDayOfThisMonth() {
		return (new LocalDate().dayOfMonth().withMaximumValue()).toDate();
	}

	/**
	 * Gets the lastt day of date.
	 *
	 * @param date
	 *            the date
	 * @return the lastt day of date
	 */
	public static Date getLasttDayOfDate(Date date) {
		return (new LocalDate(date).dayOfMonth().withMaximumValue()).toDate();
	}

	public static Date getLasttDayOfYear(Date date) {
		return (new LocalDate(date).dayOfYear().withMaximumValue()).toDate();
	}

	public static Date getFirstDateOfYear(Date date) {
		return (new LocalDate(date).monthOfYear().withMinimumValue().dayOfMonth().withMinimumValue()).toDate();
	}
	
	public static Date getEndOfJan(Date date) {
		return new LocalDateTime(date).withMonthOfYear(1).dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999).toDate();
	}

	public static Date getEndOfApril(Date date) {
		return new LocalDateTime(date).withMonthOfYear(4).dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999).toDate();
	}
	
	public static Date getEndOfMay(Date date) {
		return new LocalDateTime(date).withMonthOfYear(5).dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999).toDate();
	}
	
	public static Date getEndOfJuly(Date date) {
		return new LocalDateTime(date).withMonthOfYear(7).dayOfMonth().withMaximumValue().withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999).toDate();
	}

	/**
	 * Gets the first day of month.
	 *
	 * @param date
	 *            the date
	 * @return the first day of month
	 */
	public static Date getFirstDayOfMonth(Date date) {
		return (new LocalDate(date.getTime()).withDayOfMonth(1)).toDate();
	}

	/**
	 * Gets the start of day.
	 *
	 * @param date
	 *            the date
	 * @return the start of day
	 */
	public static Date getStartOfDay(Date date) {
		return (new LocalDateTime(date.getTime()).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0)).toDate();
	}

	/**
	 * Gets the end of day.
	 *
	 * @param date
	 *            the date
	 * @return the end of day
	 */
	public static Date getEndOfDay(Date date) {
		return (new LocalDateTime(date.getTime()).withHourOfDay(23).withMinuteOfHour(59).withSecondOfMinute(59).withMillisOfSecond(999)).toDate();
	}

	/**
	 * Gets the lastt day of month.
	 *
	 * @param date
	 *            the date
	 * @return the lastt day of month
	 */
	public static Date getLasttDayOfMonth(Date date) {
		return (new LocalDate(date.getTime()).dayOfMonth().withMaximumValue()).toDate();
	}

	/**
	 * Gets the second last day.
	 *
	 * @param date
	 *            the date
	 * @return the second last day
	 */
	public static Date getSecondLastDay(Date date) {
		return deductDaysFromDate(getLasttDayOfMonth(addMonthsToDate(date, 1)), 1);
	}

	/**
	 * Gets the second last day new.
	 *
	 * @param date
	 *            the date
	 * @return the second last day new
	 */
	public static Date getSecondLastDayNew(Date date) {
		return deductDaysFromDate(getLasttDayOfMonth(date), 1);
	}

	/**
	 * Gets the days between dates.
	 *
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the days between dates
	 */
	public static int getDaysBetweenDates(Date startDate, Date endDate) {
		return Days.daysBetween(new LocalDate(startDate.getTime()), new LocalDate(endDate.getTime())).getDays();
	}

	public static int getHoursBetweenDates(Date startDate, Date endDate) {
		return Hours.hoursBetween(new LocalDate(startDate.getTime()), new LocalDate(endDate.getTime())).getHours();
	}

	/**
	 * Gets the months between dates.
	 *
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the months between dates
	 */
	public static int getMonthsBetweenDates(Date startDate, Date endDate) {
		return Months.monthsBetween(new LocalDate(startDate.getTime()), new LocalDate(endDate.getTime())).getMonths();
	}

	/**
	 * Gets the months dates.
	 *
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the months dates
	 */
	public static List<Date> getMonthsDates(Date startDate, Date endDate) {
		int size = getMonthsBetweenDates(getFirstDayOfMonth(startDate), getLasttDayOfMonth(endDate));
		List<Date> dates = new ArrayList<Date>();
		if (size != 0) {
			dates.add(startDate);
		}
		for (int i = 0; i < size; i++) {
			if (i > 0) {
				dates.add(getFirstDayOfMonth(addMonthsToDate(startDate, i)));
			}

		}
		dates.add(endDate);
		return dates;
	}

	/**
	 * Gets the years between dates.
	 *
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the years between dates
	 */
	public static int getYearsBetweenDates(Date startDate, Date endDate) {
		return Years.yearsBetween(new LocalDate(startDate.getTime()), new LocalDate(endDate.getTime())).getYears();
	}

	/**
	 * Gets the days between dates exclude weekends.
	 *
	 * @param start
	 *            the start
	 * @param end
	 *            the end
	 * @return the days between dates exclude weekends
	 */
	public static int getDaysBetweenDatesExcludeWeekends(Date start, Date end) {
		DateTime startDateTime = new DateTime(GenericUtility.getEndOfDay(start));
		DateTime endDateTime = new DateTime(GenericUtility.getEndOfDay(end));

		int dayOfWeek;
		int days = 0;

		while (startDateTime.isBefore(endDateTime)) {
			dayOfWeek = startDateTime.getDayOfWeek();
			if ((dayOfWeek == DateTimeConstants.SUNDAY || dayOfWeek == DateTimeConstants.SATURDAY) == false) {
				days++;
			}
			startDateTime = startDateTime.plusDays(1);
		}
		days++;
		return days;
	}

	/**
	 * Gets the first day of month one year ago.
	 *
	 * @return the first day of month one year ago
	 */
	public static Date getFirstDayOfMonthOneYearAgo() {
		return (new LocalDate().minusYears(1).withDayOfMonth(1)).toDate();
	}

	/**
	 * M trim.
	 *
	 * @param string
	 *            the string
	 * @return the string
	 */
	public static String mTrim(String string) {
		if (string == null) return string;
		else return string.trim();
	}

	/**
	 * Fix report name.
	 *
	 * @param report
	 *            the report
	 * @return the string
	 */
	public static String fixReportName(String report) {
		try {
			return report.substring(0, report.lastIndexOf(".")) + ".pdf";
		} catch (Exception e) {
			logger.fatal(e);
			return "genericReport.pdf";
		}
	}

	/**
	 * Inits the like field.
	 *
	 * @param like
	 *            the like
	 * @return the string
	 */
	public static String initLikeField(String like) {
		if (like == null) return "%";
		else return like.trim() + "%";
	}

	/**
	 * Initreg exp field.
	 *
	 * @param like
	 *            the like
	 * @return the string
	 */
	public static String initregExpField(String like) {
		if (like == null) return "";
		else return like.trim();
	}

	/**
	 * Check rsa id.
	 *
	 * @param idVal
	 *            the id val
	 * @return true, if successful
	 */
	public static boolean checkRsaId(String idVal) {
		if (idVal == null || (idVal != null && idVal.trim().length() == 0)) return true;
		idVal = idVal.trim();
		if (idVal.length() < 13) return false;
		int checkDigit = ((Integer.valueOf("" + (idVal.charAt(idVal.length() - 1)))).intValue());
		String odd = "0";
		String even = "";
		int evenResult = 0;
		int result;
		for (int c = 1; c <= idVal.length(); c++) {
			if (c % 2 == 0) {
				even += idVal.charAt(c - 1);
			} else {
				if (c == idVal.length()) {
					continue;
				} else {
					odd = "" + (Integer.valueOf("" + odd).intValue() + Integer.valueOf("" + (idVal.charAt(c - 1))));
				}
			}
		}
		String evenS = "" + (Integer.valueOf(even) * 2);
		for (int r = 1; r <= evenS.length(); r++) {
			evenResult += Integer.valueOf("" + evenS.charAt(r - 1));
		}
		result = (Integer.valueOf(odd) + Integer.valueOf(evenResult));
		String resultS = "" + result;
		resultS = "" + (10 - (Integer.valueOf("" + (resultS.charAt(resultS.length() - 1)))).intValue());
		if (resultS.length() > 1) {
			resultS = "" + resultS.charAt(resultS.length() - 1);
		}
		if (Integer.valueOf(resultS) != checkDigit) {
			return false;
		} else {
			return true;
		}
	}
	
	/*
	 * Levy Number vlaidiation check
	 * return true if passed
	 * false if failed
	 */
	public static boolean levyEntityNumberValidiation(String levyNumber) {
		// passes if true
		if (levyNumber == null || (levyNumber != null && levyNumber.trim().length() == 0)) return true;
			
		levyNumber = levyNumber.trim();
		
//		Value in field may only contain characters L0123756789 OR N0123456789
		
		
		
//		Value must have a length of exactly 10
//		Value must start with 'L' followed by 9 digits or 'N' followed by 9 digits (indicating a non-levy paying company)
//		LNNN*7*NNNNN 0R LNNN*8*NNNNN
		
		return true;
	}

	/**
	 * Start date of current year.
	 *
	 * @return the date
	 */
	public static Date startDateOfCurrentYear() {
		DateTime dt = new DateTime();
		try {
			return (sdf.parse("" + dt.getYear() + "0101"));

		} catch (ParseException e) {
			logger.fatal(e);
			return null;
		}
	}

	public static Date endDateOfCurrentYear() {
		DateTime dt = new DateTime();
		try {
			return (sdf.parse("" + dt.getYear() + "1231"));

		} catch (ParseException e) {
			logger.fatal(e);
			return null;
		}
	}

	/**
	 * Last date of current year.
	 *
	 * @return the date
	 */
	public static Date lastDateOfCurrentYear() {
		DateTime dt = new DateTime();
		try {
			return (sdf.parse("" + dt.getYear() + "1231"));

		} catch (ParseException e) {
			logger.fatal(e);
			return null;
		}
	}

	/**
	 * Save file.
	 *
	 * @param file
	 *            the file
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String saveFile(UploadedFile file) throws Exception {
		if (file == null) return null;
		String logo = (timestamp.format(new java.util.Date()) + "." + FilenameUtils.getExtension(file.getFileName()));
		FileUtils.writeByteArrayToFile(new File((HAJConstants.DOC_PATH + logo).trim()), IOUtils.toByteArray(file.getInputstream()));
		return logo;
	}

	/**
	 * Read file.
	 *
	 * @param filename
	 *            the filename
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String readFile(String filename) throws Exception {

		FileInputStream fileStream = null;
		BufferedReader reader = null;
		StringBuffer strbuf = null;

		try {

			fileStream = new FileInputStream(filename);
			reader = new BufferedReader(new InputStreamReader(fileStream));
			boolean endFlag = true;
			String line = null;
			strbuf = new StringBuffer(300);

			while (endFlag) {

				line = reader.readLine();

				if (line != null) {
					strbuf.append(line).append("\n");
				}

				if (line == null) {
					endFlag = false;
				}
			}

		} finally {

			if (fileStream != null) {
				fileStream.close();
			}

			if (reader != null) {
				reader.close();
			}
		}
		return strbuf.toString();
	}

	/**
	 * Delete file.
	 *
	 * @param fn
	 *            the fn
	 * @throws IOException
	 *             Signals that an I/O exception has occurred.
	 */
	public static void deleteFile(String fn) throws IOException {
		File f = new File(fn);
		if (f.exists()) f.delete();
	}

	/**
	 * Convert file name.
	 *
	 * @param fullPath
	 *            the full path
	 * @return the string
	 */
	public static String convertFileName(String fullPath) {
		String ret = null;
		int pos = -1;
		if (fullPath.lastIndexOf('/') > -1) pos = fullPath.lastIndexOf('/');
		else if (fullPath.lastIndexOf('\\') > -1) pos = fullPath.lastIndexOf('\\');

		if (pos > -1) ret = fullPath.substring(pos + 1);

		return ret;
	}

	/**
	 * Contains.
	 *
	 * @param s
	 *            the s
	 * @return the string
	 */
	public static String contains(String s) {
		if (s == null) return "%";
		else return "%" + s.trim() + "%";
	}

	/**
	 * Start with.
	 *
	 * @param s
	 *            the s
	 * @return the string
	 */
	public static String startWith(String s) {
		if (s == null) return "%";
		else return s.trim() + "%";
	}

	/**
	 * Construct like.
	 *
	 * @param yourString
	 *            the your string
	 * @return the string
	 */
	public static String constructLike(String yourString) {
		String result = yourString.replaceAll("[\\-\\+\\.\\^:,]", "%");
		result = result.replaceAll(" ", "%");
		String s[] = result.split("%");
		String t = "%";
		for (String string : s) {
			t += string.trim() + "%";
		}
		return t;
	}

	/**
	 * Send mail.
	 *
	 * @param to
	 *            the to
	 * @param subject
	 *            the subject
	 * @param msg
	 *            the msg
	 */
	public static void sendMail(final String to, final String subject, final String msg, final String logo) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean cansend = true;
				try {
					MailLog mailLog = new MailLog(to, subject, msg);
					final UsersService usersService = new UsersService();
					try {
						Users user = usersService.getUserByEmail(to);
						if (user != null) {
							cansend = checkCanSendMail(user, to);
							mailLog.setUser(user);
						}
						MailLogService.create(mailLog);
					} catch (Exception e) {
						logger.fatal(e);
					}
					if (cansend) mailer.sendMailCommons(to, subject, msg, mailLog, logo);
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}

	private static boolean checkCanSendMail(Users u, String email) {
		return !email.equals(u.getFirstName() + "." + u.getLastName() + "@" + u.getId() + ".com") && !email.endsWith("@a.com");
	}

	public static void sendMailAllUsers(final String subject, final String msg, final String logo) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean cansend = true;
				try {
					UsersService us = new UsersService();
					List<Users> allUsers = us.allUsers();
					for (Users user : allUsers) {
						MailLog mailLog = new MailLog(user.getEmail(), subject, msg);
						try {
							if (user != null) {
								cansend = checkCanSendMail(user, user.getEmail());
								mailLog.setUser(user);
							}
							MailLogService.create(mailLog);
						} catch (Exception e) {
							logger.fatal(e);
						}
						if (cansend) mailer.sendMailCommons(user.getEmail(), subject, msg, mailLog, logo);
					}

				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}
	
	public static void sendMailAllPrimarySdfUsers(final String subject, final String msg, final String logo) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean cansend = true;
				try {
					UsersService us = new UsersService();
					List<Users> allUsers = us.allPrimarySdfUsers();
					for (Users user : allUsers) {
						MailLog mailLog = new MailLog(user.getEmail(), subject, msg);
						try {
							if (user != null) {
								cansend = checkCanSendMail(user, user.getEmail());
								mailLog.setUser(user);
							}
							MailLogService.create(mailLog);
						} catch (Exception e) {
							logger.fatal(e);
						}
						if (cansend) mailer.sendMailCommons(user.getEmail(), subject, msg, mailLog, logo);
					}

				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}
	
	public static void sendMailToSelectedUsers(final String subject, final String msg, final String logo, List<BulkMailBean> usersList) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					for (BulkMailBean mail : usersList) {
						MailLog mailLog = new MailLog(mail.getUser().getEmail(), subject, mail.getEmailContents());
						try {
							if (mail.getUser() != null)
								mailLog.setUser(mail.getUser());
							MailLogService.create(mailLog);
						} catch (Exception e) {
							logger.fatal(e);
						}

						mailer.sendMailCommons(mail.getUser().getEmail(), subject, mail.getEmailContents(), mailLog, logo);
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}

	public static void sendMailAllTaskUsers(final String subject, final String msg, final String logo, final ConfigDocProcessEnum workflow) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean cansend = true;
				try {
					List<TaskUsers> tu = TasksService.instance().findOpenTaskUsers(workflow);
					for (TaskUsers user : tu) {
						TasksService.instance().checkRag(user.getTask());
						if (user.getTask().getRag() != null && user.getTask().getRag() == RagEnum.Red) {
							String msg2 = msg + "<br/><p>Task: " + user.getTask().getDescription() + "</p>";
							MailLog mailLog = new MailLog(user.getUser().getEmail(), subject, msg2);
							try {
								if (user != null) {
									cansend = checkCanSendMail(user.getUser(), user.getUser().getEmail());
									mailLog.setUser(user.getUser());
								}
								MailLogService.create(mailLog);
							} catch (Exception e) {
								logger.fatal(e);
							}
							if (cansend) mailer.sendMailCommons(user.getUser().getEmail(), subject, msg2, mailLog, logo);
						}
					}

				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}

	/**
	 * Send mail.
	 *
	 * @param to
	 *            the to
	 * @param subject
	 *            the subject
	 * @param msg
	 *            the msg
	 */
	public static void sendMail(final Users to, final String subject, final String msg, final String logo) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean cansend = true;
				try {
					MailLog mailLog = null;
					try {
						mailLog = new MailLog(to, subject, msg);
						cansend = checkCanSendMail(to, to.getEmail());
						MailLogService.create(mailLog);
					} catch (Exception e) {
						logger.fatal(e);
					}
					if (cansend) mailer.sendMailCommons(to.getEmail(), subject, msg, mailLog, logo);
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}

	/**
	 * Send mail with attachement.
	 *
	 * @param to
	 *            the to
	 * @param subject
	 *            the subject
	 * @param msg
	 *            the msg
	 * @param file
	 *            the file
	 * @param fileName
	 *            the file name
	 * @param extension
	 *            the extension
	 * @param thistle
	 *            the thistle
	 * @param logo
	 *            the logo
	 */
	public static void sendMailWithAttachement(final String to, final String subject, final String msg, final byte[] file, final String fileName, final String extension, final String logo) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean cansend = true;
				try {
					MailLog mailLog = null;
					final UsersService usersService = new UsersService();
					try {
						mailLog = new MailLog(to, subject, msg);
						Users user = usersService.getUserByEmail(to);
						if (user != null) {
							cansend = checkCanSendMail(user, to);
							mailLog.setUser(user);
						}
						MailLogService.create(mailLog);
					} catch (Exception e) {
						logger.fatal(e);
					}
					if (cansend) 
					if (mailLog == null || mailLog.getId() == null) {
						mailer.sendMailCommonsWithAttachement(to, subject, msg, file, fileName, extension, logo, null);
					} else {
						mailer.sendMailCommonsWithAttachement(to, subject, msg, file, fileName, extension, logo, mailLog);
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}

	/**
	 * Send mail with attachement.
	 *
	 * @param to
	 *            the to
	 * @param subject
	 *            the subject
	 * @param msg
	 *            the msg
	 * @param file
	 *            the file
	 * @param fileName
	 *            the file name
	 * @param extension
	 *            the extension
	 * @param thistle
	 *            the thistle
	 * @param logo
	 *            the logo
	 * @param bcc
	 *            the bcc
	 */
	public static void sendMailWithAttachementBcc(final String to, final String subject, final String msg, final byte[] file, final String fileName, final String extension, final String logo, String[] bcc) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean cansend = true;
				try {
					MailLog mailLog = null;
					final UsersService usersService = new UsersService();
					try {
						mailLog = new MailLog(to, subject, msg);
						Users user = usersService.getUserByEmail(to);
						if (user != null) {
							cansend = checkCanSendMail(user, to);
							mailLog.setUser(user);
						}
						MailLogService.create(mailLog);
					} catch (Exception e) {
						logger.fatal(e);
					}
					if (cansend) mailer.sendMailCommonsWithAttachementBcc(to, subject, msg, file, fileName, extension, logo, bcc);
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}

	public static void sendMailWithAttachement(final String to_address, final String subject, final String text, final byte[] attachment, final String attachmentDescription, final String attachmentName, final String mime, MailLog mailLog) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					mailer.mailWithAttachement(to_address, subject, text, attachment, attachmentDescription, attachmentName, mime, mailLog);
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}

	public static void sendMailWithAttachement(String to_address, String subject, String text, List<AttachmentBean> files, String logo) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					mailer.mailWithAttachement(to_address, subject, text, files, logo);
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}
	
	public static void sendMailWithAttachementTempWithLog(String to_address, String subject, String text, List<AttachmentBean> files, String logo) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean cansend = true;
				try {
					MailLog mailLog = null;
					final UsersService usersService = new UsersService();
					try {
						mailLog = new MailLog(to_address, subject, text);
						Users user = usersService.getUserByEmail(to_address);
						if (user != null) {
							cansend = checkCanSendMail(user, to_address);
							mailLog.setUser(user);
						}
						MailLogService.create(mailLog);
					} catch (Exception e) {
						logger.fatal(e);
					}
					if (cansend) {
						mailer.mailWithAttachementTemp(to_address, subject, text, files, logo, mailLog);
					} else {
						if (files.size() != 0 && mailLog != null && mailLog.getId() != null) {
							for (AttachmentBean ab : files) {
								MailLogService.update(mailLog, ab.getFilename(), ab.getExt(), ab.getFile());
							}
						}
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}
	
	public static void sendMailWithAttachementTempWithLog(String to_address, String subject, String text, List<AttachmentBean> files, String logo,String targetClass,Long targetKey) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				boolean cansend = true;
				try {
					MailLog mailLog = null;
					final UsersService usersService = new UsersService();
					try {
						mailLog = new MailLog(to_address, subject, text);
						Users user = usersService.getUserByEmail(to_address);
						if (user != null) {
							cansend = checkCanSendMail(user, to_address);
							mailLog.setUser(user);
						}
						MailLogService.create(mailLog);
					} catch (Exception e) {
						logger.fatal(e);
					}
					if (cansend) {
						mailer.mailWithAttachementTemp(to_address, subject, text, files, logo, mailLog,targetClass,targetKey);
					} else {
						if (files.size() != 0 && mailLog != null && mailLog.getId() != null) {
							for (AttachmentBean ab : files) {
								MailLogService.update(mailLog, ab.getFilename(), ab.getExt(), ab.getFile());
							}
						}
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}

	/**
	 * Convert date.
	 *
	 * @param date
	 *            the date
	 * @return the string
	 */
	public static String convertDate(Date date) {
		if (date == null) return null;
		else return sdf2.format(date);
	}

	/**
	 * Gets the age.
	 *
	 * @param dob
	 *            the dob
	 * @return the age
	 */
	public static int getAge(Date dob) {
		return Years.yearsBetween(new LocalDate(dob.getTime()), new LocalDate()).getYears();
	}

	/**
	 * Gen passord.
	 *
	 * @return the string
	 */
	public static String genPassord() {
		return RandomStringUtils.randomAlphabetic(7);

	}

	/**
	 * Gets the first day of week.
	 *
	 * @param date
	 *            the date
	 * @return the first day of week
	 */
	public static Date getFirstDayOfWeek(Date date) {
		LocalDate now = new LocalDate(date);
		// System.out.println(now.withDayOfWeek(DateTimeConstants.MONDAY));
		// //prints 2011-01-17
		// System.out.println(now.withDayOfWeek(DateTimeConstants.SUNDAY));
		// //prints 2011-01-23
		return now.withDayOfWeek(1).toDate();
	}

	/**
	 * Gets the first day of year.
	 *
	 * @param date
	 *            the date
	 * @return the first day of year
	 */
	public static Date getFirstDayOfYear(Date date) {
		return new DateTime(date).dayOfYear().withMinimumValue().withTimeAtStartOfDay().toDate();
	}

	/**
	 * Gets the last day of year.
	 *
	 * @param date
	 *            the date
	 * @return the last day of year
	 */
	public static Date getLastDayOfYear(Date date) {
		return deductDaysFromDate(new DateTime(getFirstDayOfYear(date)).plusYears(1).toDate(), 1);
	}

	/**
	 * Gets the last day of week.
	 *
	 * @param date
	 *            the date
	 * @return the last day of week
	 */
	public static Date getLastDayOfWeek(Date date) {
		LocalDate now = new LocalDate(date);
		return now.withDayOfWeek(7).toDate();
	}

	/**
	 * Round to precision.
	 *
	 * @param val
	 *            the val
	 * @param precision
	 *            the precision
	 * @return the big decimal
	 */
	public static BigDecimal roundToPrecision(BigDecimal val, int precision) {
		val = val.setScale(precision, RoundingMode.HALF_EVEN);
		return val;
	}

	/**
	 * Round to precision.
	 *
	 * @param value
	 *            the value
	 * @param precision
	 *            the precision
	 * @return the double
	 */
	public static Double roundToPrecision(Double value, int precision) {
		BigDecimal val = BigDecimal.valueOf(value);
		val = val.setScale(precision, RoundingMode.HALF_EVEN);
		return val.doubleValue();
	}

	/**
	 * Adds the miniutes to date.
	 *
	 * @param date
	 *            the date
	 * @param minutes
	 *            the minutes
	 * @return the date
	 */
	public static Date addMiniutesToDate(Date date, int minutes) {
		if (date == null) return null;
		return (new DateTime(date.getTime()).plusMinutes(minutes)).toDate();
	}

	public static Date addSecondsToDate(Date date, int seconds) {
		if (date == null) return null;
		return (new DateTime(date.getTime()).plusSeconds(seconds)).toDate();
	}

	public static Date addMilliSecondsToDate(Date date, int milli) {
		if (date == null) return null;
		return (new DateTime(date.getTime()).plusMillis(milli)).toDate();
	}
	
	public static Integer getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return (cal.get(Calendar.YEAR));
	}

	/**
	 * Adds the hours to date.
	 *
	 * @param date
	 *            the date
	 * @param hours
	 *            the hours
	 * @return the date
	 */
	public static Date addHoursToDate(Date date, int hours) {
		if (date == null) return null;
		return (new DateTime(date.getTime()).plusHours(hours)).toDate();
	}

	/**
	 * Deduct hours from date.
	 *
	 * @param date
	 *            the date
	 * @param hours
	 *            the hours
	 * @return the date
	 */
	public static Date deductHoursFromDate(Date date, int hours) {
		if (date == null) return null;
		return (new DateTime(date.getTime()).minusHours(hours)).toDate();
	}

	/**
	 * Deduct minutes from date.
	 *
	 * @param date
	 *            the date
	 * @param minutes
	 *            the minutes
	 * @return the date
	 */
	public static Date deductMinutesFromDate(Date date, int minutes) {
		if (date == null) return null;
		return (new DateTime(date.getTime()).minusMinutes(minutes)).toDate();
	}

	/**
	 * Adds the weeks to date.
	 *
	 * @param date
	 *            the date
	 * @param weeks
	 *            the weeks
	 * @return the date
	 */
	public static Date addWeeksToDate(Date date, int weeks) {
		if (date == null) return null;
		return (new DateTime(date.getTime()).plusWeeks(weeks)).toDate();
	}

	/**
	 * Adds the months to date.
	 *
	 * @param date
	 *            the date
	 * @param months
	 *            the months
	 * @return the date
	 */
	public static Date addMonthsToDate(Date date, int months) {
		if (date == null) return null;
		return (new DateTime(date.getTime()).plusMonths(months)).toDate();
	}

	/**
	 * Deduct months from date.
	 *
	 * @param date
	 *            the date
	 * @param months
	 *            the months
	 * @return the date
	 */
	public static Date deductMonthsFromDate(Date date, int months) {
		if (date == null) return null;
		return (new DateTime(date.getTime()).minusMonths(months)).toDate();
	}

	/**
	 * Adds the years to date.
	 *
	 * @param date
	 *            the date
	 * @param years
	 *            the years
	 * @return the date
	 */
	public static Date addYearsToDate(Date date, int years) {
		if (date == null) return null;
		return (new DateTime(date.getTime()).plusYears(years)).toDate();
	}

	public static Date deductYearsfromDate(Date date, int years) {
		if (date == null) return null;
		return (new DateTime(date.getTime()).minusYears(years)).toDate();
	}

	/**
	 * Gets the hours between date.
	 *
	 * @param startDate
	 *            the start date
	 * @param endDate
	 *            the end date
	 * @return the hours between date
	 */
	public static Long getHoursBetweenDate(Date startDate, Date endDate) {
		if (startDate == null || endDate == null) return null;
		return (endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60);
	}

	/**
	 * Gets the month for int.
	 *
	 * @param num
	 *            the num
	 * @return the month for int
	 */
	public static String getMonthForInt(int num) {
		String month = "wrong";
		DateFormatSymbols dfs = new DateFormatSymbols();
		String[] months = dfs.getMonths();
		if (num >= 0 && num <= 11) {
			month = months[num];
		}
		return month;
	}

	/**
	 * Removes the last comma.
	 *
	 * @param string
	 *            the string
	 * @return the string
	 */
	public static String removeLastComma(String string) {
		string = string.substring(0, string.lastIndexOf(','));
		return string;
	}

	/**
	 * New line.
	 *
	 * @param string
	 *            the string
	 * @return the string
	 */
	public static String newLine(String string) {
		return string + "\n";
	}

	/**
	 * Finish line.
	 *
	 * @param string
	 *            the string
	 * @return the string
	 */
	public static String finishLine(String string) {
		return newLine(removeLastComma(string));
	}

	/**
	 * Removes the special chars.
	 *
	 * @param string
	 *            the string
	 * @return the string
	 */
	public static String removeSpecialChars(String string) {
		return string.replaceAll(",", " ");
	}

	/**
	 * Adds the 3 days counting from start date.
	 *
	 * @param from
	 *            the from
	 * @return the date
	 */
	public static Date add3DaysCountingFromStartDate(Date from) {
		LocalDate date = LocalDate.parse(HAJConstants.sdf.format(from));
		if (date.getDayOfWeek() < 4) {
			date = date.plusDays(2);
		} else if (date.getDayOfWeek() == 4 || date.getDayOfWeek() == 5 || date.getDayOfWeek() == 6) {
			date = date.plusDays(4);
		} else if (date.getDayOfWeek() == 7) {
			date = date.plusDays(3);
		}
		return date.toDate();
	}

	public static Date add5DaysCountingFromStartDate(Date from) {
		LocalDate date = LocalDate.parse(HAJConstants.sdf.format(from));
		if (date.getDayOfWeek() < 4) {
			date = date.plusDays(4);
		} else if (date.getDayOfWeek() == 4 || date.getDayOfWeek() == 5 || date.getDayOfWeek() == 6) {
			date = date.plusDays(6);
		} else if (date.getDayOfWeek() == 7) {
			date = date.plusDays(5);
		}
		return date.toDate();
	}

	/**
	 * Convert buffered image to byte array.
	 *
	 * @param bImageFromConvert
	 *            the b image from convert
	 * @param ext
	 *            the ext
	 * @return the byte[]
	 * @throws Exception
	 *             the exception
	 */
	public static byte[] convertBufferedImageToByteArray(BufferedImage bImageFromConvert, String ext) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(bImageFromConvert, ext, baos);
		baos.flush();
		byte[] imageInByte = baos.toByteArray();
		baos.close();
		return imageInByte;
	}

	/**
	 * Zip file.
	 *
	 * @param fileToZip
	 *            the file to zip
	 * @throws Exception
	 *             the exception
	 */
	public static void zipFile(File fileToZip) throws Exception {
		FileOutputStream fos = new FileOutputStream(fileToZip.getAbsolutePath() + ".zip");
		ZipOutputStream zipOut = new ZipOutputStream(fos);

		FileInputStream fis = new FileInputStream(fileToZip);
		ZipEntry zipEntry = new ZipEntry(fileToZip.getName());
		zipOut.putNextEntry(zipEntry);
		final byte[] bytes = new byte[1024];
		int length;
		while ((length = fis.read(bytes)) >= 0) {
			zipOut.write(bytes, 0, length);
		}
		zipOut.close();
		fis.close();
		fos.close();

	}

	/**
	 * Camel case word.
	 *
	 * @param word
	 *            the word
	 * @return the string
	 * @throws Exception
	 *             the exception
	 */
	public static String camelCaseWord(String word) throws Exception {
		return word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase();
	}

	public static String buidFileName(Doc doc) {
		String fname = "File";
		// if (doc.getTemplate() != null) {
		// fname = doc.getTemplate().getTitle();
		// }
		if (doc.getOriginalFname() != null) {
			fname = doc.getOriginalFname();
		}

		fname = fname.replaceAll(" ", "");
		fname = fname.trim() + "_V" + doc.getVersionNo() + "." + doc.getExtension().trim();
		return fname;
	}

	public static Double convertStringtoDouble(String amt) {
		Double result = null;
		if (amt != null && amt.trim().length() > 0) {
			amt = amt.replaceAll("[(), ]", "").trim();
			result = Double.valueOf(amt);
		}
		return result;
	}

	public static int yearOfDate(Date date) {
		java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate.getYear();
	}

	public static int monthOfDate(Date date) {
		java.time.LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
		return localDate.getMonthValue();
	}

	public static BufferedImage generateBarcode(String barcode) throws Exception {
		Code39Bean bean = new Code39Bean();
		bean.setHeight(10d);
		bean.doQuietZone(false);
		OutputStream out = new java.io.FileOutputStream(new File(HAJConstants.DOC_PATH + barcode + ".png"));
		BitmapCanvasProvider provider = new BitmapCanvasProvider(out, "image/x-png", 110, BufferedImage.TYPE_BYTE_GRAY, false, 0);
		bean.generateBarcode(provider, barcode);
		provider.finish();
		return provider.getBufferedImage();
	}

	public static boolean checkBeforeApril(Date date) throws Exception {
		DateTime ld = new DateTime(date);
		Date startFinYear = sdfa.parse(ld.getYear() + APRIL);
		return date.before(startFinYear);
	}

	public static Date getApril(Date date) throws Exception {
		DateTime ld = new DateTime(date);
		return sdfa.parse(ld.getYear() + APRIL);
	}

	public static Date getMarch(Date date) throws Exception {
		DateTime ld = new DateTime(date);
		return sdfa.parse((ld.getYear() + MARCH));
	}

	public static List<Integer> resolveFinYears(Date date) throws Exception {
		/*
		 * SARS scheme year 2017 : 2017-03-01 => 2018-02-28 
		 * SARS scheme year 2016 : 2016-03-01 => 2017-02-28 
		 * SARS scheme year 2015 : 2015-03-01 => 2016-02-28
		 * SARS scheme year 2014 : 2014-03-01 => 2015-02-28
		 */
		List<Integer> finYear = new ArrayList<Integer>();
		Date startFinYear = sdfa.parse(sdfYYYY.format(date) + SARS_FIN_YEAR_START);
		if (date.before(startFinYear)) {
			startFinYear = sdfa.parse((Integer.valueOf(sdfYYYY.format(date)) - 1) + SARS_FIN_YEAR_START);
		}
		int months = GenericUtility.getMonthsBetweenDates(startFinYear, date);
		int yy = Integer.valueOf(sdfYYYY.format(startFinYear));
		finYear.add(yy);
		if (months < 11) {
			finYear.add((yy - 1));
		}
		return finYear;
	}

	public static Integer resolveFinYear(Date date) throws Exception {
		Date startFinYear = sdfa.parse(sdfYYYY.format(date) + SARS_FIN_YEAR_START);
		if (date.before(startFinYear)) {
			startFinYear = sdfa.parse((Integer.valueOf(sdfYYYY.format(date)) - 1) + SARS_FIN_YEAR_START);
		}
		int yy = Integer.valueOf(sdfYYYY.format(startFinYear));
		return yy;
	}

	public static String limitLength(String s, int len) {
		if (s == null) return s;
		else if (s.trim().length() < len) return s.trim();
		else return s.substring(0, (len - 1));
	}

	public static void checkPassword(String newPassword, Users u) throws Exception {
		if (newPassword.equals(u.getLastName()) || newPassword.equals(u.getFirstName())) throw new Exception("Password cannot be your Firstname or Surname.");

		if (newPassword.length() < 8 || newPassword.length() < 8) throw new Exception("Password must be 8 characters long.");

		String regex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*?&])[A-Za-z\\d$@$!%*?&]{8,}";
		// Create a Pattern object
		Pattern r = Pattern.compile(regex);
		Matcher m = r.matcher(newPassword);
		if (!m.find()) throw new Exception("Minimum eight characters, at least one uppercase letter, one lowercase letter, one number and one special character");
	}

	public static void calcIDData(Users u) throws Exception {
		/* Add new thing here for ID */
		if (u.getRsaIDNumber() != null && !u.getRsaIDNumber().isEmpty()) {
			GenderService genderService = new GenderService();
			NationalityService nationalityService = new NationalityService();
//			u.setNationality(nationalityService.findByCode("SA"));
			if (Integer.parseInt(u.getRsaIDNumber().substring(10, 11)) == 0) {
				u.setRsaCitizenTypeEnum(RsaCitizenTypeEnum.RsaCitizen);
				u.setNationality(nationalityService.findByCode("SA"));
			} else {
				u.setRsaCitizenTypeEnum(RsaCitizenTypeEnum.PermanentResident);
				u.setNationality(null);
			}
			u.setDateOfBirth(sdfIdDate.parse(u.getRsaIDNumber().substring(0, 6)));
			if (Integer.parseInt(u.getRsaIDNumber().substring(6, 7)) > 4) {
				u.setGender(genderService.findByGenderName("Male"));
			} else {
				u.setGender(genderService.findByGenderName("Female"));
			}
		}
	}
	
	public static void calcIDDataRsaCitizien(Users u) throws Exception {
		NationalityService nationalityService = new NationalityService();
		if (Integer.parseInt(u.getRsaIDNumber().substring(10, 11)) == 0) {
			u.setRsaCitizenTypeEnum(RsaCitizenTypeEnum.RsaCitizen);
			if (u.getNationality() == null) {
				u.setNationality(nationalityService.findByCode("SA"));
			}
		} else {
			u.setRsaCitizenTypeEnum(RsaCitizenTypeEnum.PermanentResident);
//			u.setNationality(null);
		}
	}

	public static void calcIDData(TrainingComittee u) throws Exception {
		if (u.getRsaIDNumber() != null && !u.getRsaIDNumber().isEmpty()) {
			GenderService genderService = new GenderService();
			if (Integer.parseInt(u.getRsaIDNumber().substring(6, 7)) > 4) {
				u.setGender(genderService.findByGenderName("Male"));
			} else {
				u.setGender(genderService.findByGenderName("Female"));
			}
		}
	}

	public static void calcIDData(Employees u) throws Exception {
		if (u.getRsaIDNumber() != null && !u.getRsaIDNumber().isEmpty()) {

			GenderService genderService = new GenderService();
			NationalityService nationalityService = new NationalityService();
			
			if (Integer.parseInt(u.getRsaIDNumber().substring(10, 11)) == 0) {
				u.setNationality(nationalityService.findByCode("SA"));
			} else {
				
			}
			
			u.setNationality(nationalityService.findByCode("SA"));

			u.setDateOfBirth(sdfIdDate.parse(u.getRsaIDNumber().substring(0, 6)));
			if (Integer.parseInt(u.getRsaIDNumber().substring(6, 7)) > 4) {
				u.setGender(genderService.findByGenderName("Male"));
			} else {
				u.setGender(genderService.findByGenderName("Female"));
			}
		}
	}

	public static void calcIDData(MandatoryGrantDetail u) throws Exception {
		if (u.getIdNumber() != null && !u.getIdNumber().isEmpty()) {
			
			GenderService genderService = new GenderService();
			NationalityService nationalityService = new NationalityService();

			u.setDateOfBirth(sdfIdDate.parse(u.getIdNumber().substring(0, 6)));
			if (Integer.parseInt(u.getIdNumber().substring(6, 7)) > 4) {
				u.setGender(genderService.findByGenderName("Male"));
			} else {
				u.setGender(genderService.findByGenderName("Female"));
			}
			
			if (Integer.parseInt(u.getIdNumber().substring(10, 11)) == 0) {
				u.setRsaCitizenTypeEnum(RsaCitizenTypeEnum.RsaCitizen);
				u.setNationality(nationalityService.findByCode("SA"));
			} else {
				u.setRsaCitizenTypeEnum(RsaCitizenTypeEnum.PermanentResident);
				u.setNationality(null);
			}
		}
	}

	public static byte[] compress(byte[] data) throws IOException {
		Deflater deflater = new Deflater();
		deflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		deflater.finish();
		byte[] buffer = new byte[1024];
		while (!deflater.finished()) {
			int count = deflater.deflate(buffer); // returns the generated code... index
			outputStream.write(buffer, 0, count);
		}
		outputStream.close();
		byte[] output = outputStream.toByteArray();
		logger.debug("Original: " + data.length / 1024 + " Kb");
		logger.debug("Compressed: " + output.length / 1024 + " Kb");
		return output;
	}

	public static byte[] decompress(byte[] data) throws IOException, DataFormatException {
		Inflater inflater = new Inflater();
		inflater.setInput(data);
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream(data.length);
		byte[] buffer = new byte[1024];
		while (!inflater.finished()) {
			int count = inflater.inflate(buffer);
			outputStream.write(buffer, 0, count);
		}
		outputStream.close();
		byte[] output = outputStream.toByteArray();
		logger.debug("Original: " + data.length);
		logger.debug("Compressed: " + output.length);
		return output;
	}

	public static List<String> allFilesRecursivelyWithExtension(final String path, final String extension) {
		final List<String> lines = new ArrayList<>();
		try (final Stream<Path> pathStream = Files.walk(Paths.get(path), FileVisitOption.FOLLOW_LINKS)) {
			pathStream.filter((p) -> !p.toFile().isDirectory() && p.toFile().getAbsolutePath().endsWith(extension)).forEach(p -> lines.add(p.toString().replaceAll(path, "")));
		} catch (final IOException e) {
			e.printStackTrace();
		}
		return lines;
	}

	/**
	 * This method takes a list of any type and a delimiter and creates a string
	 * with id's of each entry in the list delimited with the specified character...
	 *
	 * NB - List only constructed if the object in the list has a getId method !
	 *
	 * @param list
	 * @param delimeter
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String convertListIntoSeperatedString(List list, String delimeter) throws Exception {

		String seperatedString = "";

		itemLoop: for (Object entryInList : list) {

			Class entryInListClass = entryInList.getClass();

			Method[] declaredMethods = entryInListClass.getDeclaredMethods();

			for (Method theMethod : declaredMethods) {

				if (theMethod.getName().equals("getId")) {

					seperatedString = seperatedString + delimeter + theMethod.invoke(entryInList, (Object[]) null);
					continue itemLoop;
				}
			}
		}

		if (seperatedString.startsWith(delimeter)) {

			seperatedString = seperatedString.substring(1, seperatedString.length());
		}

		logger.info("Seperated String with delimeter specified as : " + delimeter + " value is now returned as : " + seperatedString);

		return seperatedString;
	}

	/**
	 * This method takes a list of any type and a delimiter and creates a string
	 * with id's of each entry in the list delimited with the specified character...
	 *
	 * @param list
	 * @param delimeter
	 * @param methodNameToInvokeToGetValue
	 * @return String
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static String convertListIntoSeperatedString(List list, String delimeter, String methodNameToInvokeToGetValue) throws Exception {

		String seperatedString = "";

		itemLoop: for (Object entryInList : list) {

			Class entryInListClass = entryInList.getClass();

			Method[] declaredMethods = entryInListClass.getDeclaredMethods();

			for (Method theMethod : declaredMethods) {

				if (theMethod.getName().equals(methodNameToInvokeToGetValue)) {

					seperatedString = seperatedString + delimeter + theMethod.invoke(entryInList, (Object[]) null);
					continue itemLoop;
				}
			}
		}

		if (seperatedString.startsWith(delimeter)) {

			seperatedString = seperatedString.substring(1, seperatedString.length());
		}

		logger.info("Seperated String with delimeter specified as : " + delimeter + " value is now returned as : " + seperatedString);

		return seperatedString;
	}

	public static String padRight(String s, int n) {
		return String.format("%1$-" + n + "s", s);
	}

	public static String padRight(String s, int n, char paddChar) {
		return String.format("%1$-" + n + "s", s).replace(' ', paddChar);
	}
	
	/**
	 * Calculates the standard deviation of list passed
	 * @param listOfEntries
	 * @return the standardDeviation of list passed
	 */
	public static double calculateStandardDeviation(List<Double> listOfEntries) {
		double sd = 0.0;
		double average = 0.0;
		double standardDeviation = 0.0;
		int numberOfEntries = 0;
		if (listOfEntries != null) {
			numberOfEntries = listOfEntries.size();
			for (Double entry : listOfEntries) {
				average += entry;
			}
			average = average / listOfEntries.size();
		} else {
			average = average / 0;
		}
		for (int i = 0; i < numberOfEntries; i++) {
			sd += ((listOfEntries.get(i) - average) * (listOfEntries.get(i) - average)) / (listOfEntries.size() - 1);
		}
		standardDeviation = Math.round(Math.sqrt(sd));
		return standardDeviation;
	}
	
	/**
	 * Calculates the standard deviation percentage of list passed with dividing the standard deviation by the average
	 * @param listOfEntries
	 * @return the standardDeviationPercentage of list passed
	 */
	public static double calculateStandardDeviationPercentage(List<Double> listOfEntries) {
		double sd = 0.0;
		double average = 0.0;
		double standardDeviation = 0.0;
		double standardDeviationPercentage = 0.0;
		int numberOfEntries = 0;
		if (listOfEntries != null) {
			numberOfEntries = listOfEntries.size();
			for (Double entry : listOfEntries) {
				average += entry;
			}
			average = average / listOfEntries.size();
		} else {
			average = average / 0;
		}
		for (int i = 0; i < numberOfEntries; i++) {
			sd += ((listOfEntries.get(i) - average) * (listOfEntries.get(i) - average)) / (listOfEntries.size() - 1);
		}
		standardDeviation = Math.round(Math.sqrt(sd));
		standardDeviationPercentage = standardDeviation / average;
		return standardDeviationPercentage;
	}
	
	/**
	 * Generates random 4 digit number
	 * @return int
	 */
	public static int generateFourNumberPin() {
		Random r = new Random(System.currentTimeMillis());
		return ((1 + r.nextInt(2)) * 1000 + r.nextInt(1000));
	}
	
	public static int returnPlacementInInteger(Integer mainValue, int startingPoint, int endingPoint) {
		String mainValueString = mainValue.toString();
		String mainValuePlacement = mainValueString.substring(startingPoint, endingPoint);
		return (Integer.parseInt(mainValuePlacement));
	}
	
	public static void appendToStringBuilder(StringBuilder stringBuilder, String stringToAppend) {
		if (stringBuilder != null && (stringToAppend != null && !stringToAppend.trim().isEmpty())) {
			stringBuilder.append(stringToAppend);
		}
	}
	
	/**
	 * Temporary measure to mail severe issues to development team
	 * 
	 * @param subject
	 * @param msg
	 */
	public static void mailError(String subject, String msg) {
		if ("D".equals(HAJConstants.DEV_TEST_PROD)) {
			String pmsg = "<h1>Development error</h1><p>";
			pmsg = pmsg + msg + "</p>";
			logger.error("-------------> Possible config error (Development error) " + subject + "\t" + msg);
		} else if ("T".equals(HAJConstants.DEV_TEST_PROD)) {
			String pmsg = "<h1>Test System error</h1><p>";
			pmsg = pmsg + msg + "</p>";
			for (String email : processEmailNotfications()) {
				sendMail(email, subject + " (Test Site error)", pmsg, null);
			}
		} else if ("P".equals(HAJConstants.DEV_TEST_PROD)) {
			String pmsg = "<h1>PRODUCTION SYSTEM ERROR</h1><p>";
			pmsg = pmsg + msg + "</p>";
			for (String email : processEmailNotfications()) {
				sendMail(email, subject + " (Production System)", pmsg, null);
			}
		}
	}
	
	/**
	 * hard coded list of people to notify for support
	 * @return
	 */
	public static List<String> supportEmails(){
		List<String> supportEmails = new ArrayList<>();
		supportEmails.addAll(NSDMSConfiguration.getList(NSDMS_SUPPORT_EMAIL_PROPERTY));
		return supportEmails;
	}
	
	public static List<String> testEmails(){
		List<String> supportEmails = new ArrayList<>();
		supportEmails.addAll(NSDMSConfiguration.getList(NSDMS_SUPPORT_EMAIL_PROPERTY));
		return supportEmails;
	}
	
	public static List<String> processEmailNotfications(){
		List<String> supportEmails = new ArrayList<>();
		supportEmails.addAll(NSDMSConfiguration.getList(NSDMS_SUPPORT_EMAIL_PROPERTY));
		return supportEmails;
	}
	
	/* * creates .zip file with documents and changes name indicating document list size and incremented document index for user readability
	 * @param docs - the documents to zipped
	 * @return and array of type ByteArrayOutputStream
	 * @throws Exception
	 */
	public static byte[] zipBytes(List<Doc> docs) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ZipOutputStream zos = new ZipOutputStream(baos);
		try {
			final int listSize = docs.size();
			for (Doc doc : docs) {
				ZipEntry entry = new ZipEntry((docs.indexOf(doc) + 1)+ " of " + listSize +  " - " + doc.getOriginalFname());
				entry.setSize(doc.getFileContent().length);
				zos.putNextEntry(entry);
				zos.write(doc.getFileContent());
				zos.closeEntry();
			}
		} finally {
			// close the stream when done or exception
			zos.close();
		}
		return baos.toByteArray();
	}

	public static String fileTimestapName(String name, String fileExtension) throws Exception {
		return (name + "-" + timestamp.format(new Date()) + "." + fileExtension).trim();
	}
	
	public static Date convertDateFromString(String dateAsString, SimpleDateFormat sdf) {
		try {
			if (dateAsString == null || dateAsString.trim().isEmpty()) {
				return null;
			} else {
				return sdf.parse(dateAsString);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static double calculatePercentage(double obtainedValue, double totalValue) throws Exception {
		return obtainedValue * 100 / totalValue;
	}
}
