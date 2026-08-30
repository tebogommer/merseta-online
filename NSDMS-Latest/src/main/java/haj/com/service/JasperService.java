package haj.com.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.faces.context.FacesContext;
import javax.mail.Address;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.ImageIcon;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.axis.utils.ByteArrayOutputStream;
import org.apache.commons.io.FileUtils;
import org.apache.http.entity.mime.MIME;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import org.hibernate.Session;
import org.hibernate.jdbc.Work;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.Doc;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.framework.AbstractService;
import haj.com.provider.MySQLProvider;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.query.JRXPathQueryExecuterFactory;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class JasperService.
 */
public class JasperService extends AbstractService {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The Constant path. */
	private static final String path = HAJConstants.APP_PATH;

	/** The Constant sub_path. */
	private static final String sub_path = "reports/";

	/** The Constant _pdfContent. */
	private static final String _pdfContent = "application/pdf";

	/** The Constant _excelContent. */
	private static final String _excelContent = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
	private static final String _zipContent = "application/zip";

	/** The Constant workPath. */
	// private static final String _wordContent = "application/msword";
	private static final String workPath = "/Users/hendrik/Downloads/";

	private static JasperService jasperService;

	public static synchronized JasperService instance() {
		if (jasperService == null) {
			jasperService = new JasperService();
		}
		return jasperService;
	}

	/**
	 * Adds the merseta  logo.
	 *
	 * @param params
	 *            the params
	 */
	public static void addLogo(Map<String, Object> params) {
		try {
			byte[] buff = FileUtils.readFileToByteArray(new File(path + "/resources/hls/images/logo2.png"));
			params.put("logo", new ImageIcon(buff).getImage());
			byte[] buff2 = FileUtils.readFileToByteArray(new File(path + "/resources/hls/images/mer.jpeg"));
			params.put("backround_image", new ImageIcon(buff2).getImage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * Adds the merseta template params.
	 *
	 * @param params
	 *            the params
	 */
	public static void addLetterTemplateParams(Map<String, Object> params) {
		try {
			byte[] buff = FileUtils.readFileToByteArray(new File(path + "/resources/hls/images/logo2.png"));
			params.put("logo", new ImageIcon(buff).getImage());
			byte[] buff2 = FileUtils.readFileToByteArray(new File(path + "/resources/hls/images/mer.jpeg"));
			params.put("backround_image", new ImageIcon(buff2).getImage());
			
			String headerSubreport = path + "reports/template/PageHeader.jasper";
			String footerSubreport = path + "reports/template/PageFooter.jasper";
			String titleSubreport = path + "reports/template/Title.jasper";
			
			params.put("headerSubreport", headerSubreport);
			params.put("footerSubreport", footerSubreport);
			params.put("titleSubreport", titleSubreport);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Adds the merseta template params.
	 *
	 * @param params
	 *            the params
	 */
	public static void addFormTemplateParams(Map<String, Object> params) {
		try {
			byte[] buff = FileUtils.readFileToByteArray(new File(path + "/resources/hls/images/logo2.png"));
			params.put("logo", new ImageIcon(buff).getImage());
			byte[] buff2 = FileUtils.readFileToByteArray(new File(path + "/resources/hls/images/mer.jpeg"));
			params.put("backround_image", new ImageIcon(buff2).getImage());
			
			String footerSubreport = path + "reports/template/PageFooter.jasper";
			String titleSubreport = path + "reports/template/FormTitle.jasper";
			
			params.put("footerSubreport", footerSubreport);
			params.put("titleSubreport", titleSubreport);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addImage(Map<String, Object> params, String image, String paramName) {
		try {
			byte[] buff = FileUtils.readFileToByteArray(new File(path + "/resources/hls/images/" + image));
			params.put(paramName, new ImageIcon(buff).getImage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void addGrantSubreport(Map<String, Object> params) {
		try {

			String tableOfContent = path + "reports/GRANT_APPLICATION_toc.jasper";
			String coverPage = path + "reports/GRANT_APPLICATION_cover.jasper";
			String grantDetails = path + "reports/GRANT_CONTENT.jasper";

			params.put("table_of_content", tableOfContent);
			params.put("grant_content", grantDetails);
			params.put("cover_page", coverPage);

			byte[] buff = FileUtils.readFileToByteArray(new File(path + "/resources/hls/images/checkbox-marked.png"));
			params.put("checked_image", new ImageIcon(buff).getImage());
			byte[] buff2 = FileUtils.readFileToByteArray(new File(path + "/resources/hls/images/checkbox-outline.png"));
			params.put("unchecked_image", new ImageIcon(buff2).getImage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void addCheckBoxImages(Map<String, Object> params) {
		try {
			byte[] buff = FileUtils.readFileToByteArray(new File(path + "/resources/hls/images/checkbox-marked.png"));
			params.put("checked_image", new ImageIcon(buff).getImage());
			byte[] buff2 = FileUtils.readFileToByteArray(new File(path + "/resources/hls/images/checkbox-outline.png"));
			params.put("unchecked_image", new ImageIcon(buff2).getImage());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds Descretionary_Grant_Annexure_A and Descretionary_Grant_Annexure_C
	 * subreport.
	 *
	 * @param params
	 *            the params
	 */

	public static void addDiscretionaryGrantSubReports(Map<String, Object> params) {
		try {
			int finYear = ((Integer) params.get("wsp_fin_year")).intValue();
			String annexureA = "";
			String annexureC = "";
			if (finYear > 2021){
				annexureA = path + "reports/newmoaver12/Descretionary_Grant_Annexure_A.jasper";
				annexureC = path + "reports/newmoaver12/Descretionary_Grant_Annexure_C.jasper";
			}
			else {
				annexureA = path + "reports/Descretionary_Grant_Annexure_A.jasper";
				annexureC = path + "reports/Descretionary_Grant_Annexure_C.jasper";
			}
			params.put("annexureA", annexureA);
			params.put("annexureC", annexureC);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Adds merSETAReport_SDF_signature subreport.
	 *
	 * @param params
	 *            the params
	 */

	public static void addApplicationSubreport(Map<String, Object> params) {
		try {

			String sdfSubPath = path + "reports/merSETAReport_SDF_signature.jasper";
			String empSubPath = path + "reports/merSETAReport_Employees_Signature.jasper";
			params.put("merseta_sdf_signaturs", sdfSubPath);
			params.put("merseta_employees_signaturs", empSubPath);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/*
	 * 
	 * byte[]buff = FileUtils.getBytes(new File(path +
	 * "/images/momentumlogosmall.png")); parms.put("metraLogo", new
	 * ImageIcon(buff).getImage());
	 */

	/**
	 * Creates the report from XM lto servlet output stream.
	 *
	 * @param report
	 *            the report
	 * @param params
	 *            the params
	 * @param xml
	 *            the xml
	 * @param filename
	 *            the filename
	 * @throws Exception
	 *             the exception
	 */
	public static void createReportFromXMLtoServletOutputStream(String report, Map<String, Object> params, String xml, String filename) throws Exception {
		byte b[] = createReportFromXMLtoByteArray(report, params, xml);
		convertByteArrayToServletOutputStream(b, filename);
	}

	/**
	 * Creates the report from D bto servlet output stream.
	 *
	 * @param report
	 *            the report
	 * @param params
	 *            the params
	 * @param filename
	 *            the filename
	 * @return the doc
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("serial")
	public Doc createReportFromDBtoServletOutputStream(String report, Map<String, Object> params, String filename) throws Exception {
		Doc doc = new Doc();
		Session session = new AbstractDAO() {

			@Override
			public AbstractDataProvider getDataProvider() {
				return new MySQLProvider();
			}
		}.getSession();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {

			params.put("PATH", path);
			params.put("REPORT_LOCALE", new Locale("en", "GB"));
			params.put("net.sf.jasperreports.awt.ignore.missing.font", "true");
			params.put("net.sf.jasperreports.legacy.band.evaluation.enabled", "true");
			params.put("SUBREPORT_DIR", path + sub_path);
			InputStream reportStream = new BufferedInputStream(new FileInputStream(path + sub_path + report));
			session.doWork(new RunReportToPdfStreamHelper(reportStream, out, params));

			byte[] b = out.toByteArray();
			doc.setOriginalFname(filename);
			doc.setExtension("pdf");
			doc.setData(b);

			convertByteArrayToServletOutputStream(b, filename);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
			out.close();
			out.flush();
		}
		return doc;
	}
	
	
	

	/**
	 * Creates the report from D bto doc.
	 *
	 * @param report
	 *            the report
	 * @param params
	 *            the params
	 * @param filename
	 *            the filename
	 * @return the doc
	 * @throws Exception
	 *             the exception
	 */
	public Doc createReportFromDBtoDoc(String report, Map<String, Object> params, String filename) throws Exception {
		Doc doc = new Doc();

		Session session = new AbstractDAO() {

			@Override
			public AbstractDataProvider getDataProvider() {
				return new MySQLProvider();
			}
		}.getSession();

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {

			params.put("PATH", path);
			params.put("REPORT_LOCALE", new Locale("en", "GB"));
			params.put("net.sf.jasperreports.awt.ignore.missing.font", "true");
			params.put("SUBREPORT_DIR", path + sub_path);

			InputStream reportStream = new BufferedInputStream(new FileInputStream(path + sub_path + report));
			session.doWork(new RunReportToPdfStreamHelper(reportStream, out, params));
			byte[] b = out.toByteArray();
			doc.setOriginalFname(filename);
			doc.setExtension("pdf");
			doc.setData(b);

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
			out.close();
			out.flush();
		}
		return doc;
	}

	public void multipleJasperToZip(Map<String, Map<String, Object>> zip, String zipName) {
		Session session = new AbstractDAO() {

			@Override
			public AbstractDataProvider getDataProvider() {
				return new MySQLProvider();
			}
		}.getSession();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (ZipOutputStream zos = new ZipOutputStream(baos)) {

			for (Entry<String, Map<String, Object>> string : zip.entrySet()) {
				String filename = string.getKey().replace(".jasper", ".pdf");
				Map<String, Object> params = string.getValue();
				ZipEntry entry = new ZipEntry("/Cert/" + filename);
				addLogo(params);
				params.put("PATH", path);
				params.put("REPORT_LOCALE", new Locale("en", "GB"));
				params.put("net.sf.jasperreports.awt.ignore.missing.font", "true");
				params.put("SUBREPORT_DIR", path + sub_path);
				InputStream reportStream = new BufferedInputStream(new FileInputStream(path + sub_path + string.getKey()));
				session.doWork(new RunReportToPdfStreamHelper(reportStream, out, params));
				byte[] b = out.toByteArray();
				zos.putNextEntry(entry);
				zos.write(b);
				zos.closeEntry();
			}
			zos.close();
			convertByteArrayToServletOutputStream(baos.toByteArray(), zipName, _zipContent);

		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				baos.close();
				baos.flush();
				out.close();
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Generic method to write a byte array to a Servlet Output Stream.
	 *
	 * @param bytes
	 *            the bytes
	 * @param filename
	 *            the filename
	 * @throws Exception
	 *             the exception
	 */
	private static void convertByteArrayToServletOutputStream(byte[] bytes, String filename) throws Exception {
		FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
		Object resp = context.getExternalContext().getResponse();
		if (resp instanceof HttpServletResponse) {
			HttpServletResponse response = createRespObj((HttpServletResponse) resp, _pdfContent, filename);
			ServletOutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
			context.responseComplete();
		}
	}
	
	public void downloadFile(byte[] bytes, String filename) throws Exception
	{
		convertByteArrayToServletOutputStream(bytes, filename);
	}
	
	public void downloadFileExcel(byte[] bytes, String filename) throws Exception
	{
		convertByteArrayToServletOutputStreamExcel(bytes, filename);
	}

	/**
	 * Convert byte array to servlet output stream excel.
	 *
	 * @param bytes
	 *            the bytes
	 * @param filename
	 *            the filename
	 * @throws Exception
	 *             the exception
	 */
	public static void convertByteArrayToServletOutputStreamExcel(byte[] bytes, String filename) throws Exception {
		FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
		Object resp = context.getExternalContext().getResponse();
		if (resp instanceof HttpServletResponse) {
			HttpServletResponse response = createRespObj((HttpServletResponse) resp, _excelContent, filename);
			ServletOutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
			context.responseComplete();
		}
	}

	public static void convertByteArrayToServletOutputStream(byte[] bytes, String filename, String contentType) throws Exception {
		FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
		Object resp = context.getExternalContext().getResponse();
		if (resp instanceof HttpServletResponse) {
			HttpServletResponse response = createRespObj((HttpServletResponse) resp, contentType, filename);
			ServletOutputStream out = response.getOutputStream();
			out.write(bytes);
			out.flush();
			out.close();
			context.responseComplete();
		}
	}

	/**
	 * Generic method to create a Jasper report to a byteArray.
	 *
	 * @param report
	 *            the report
	 * @param params
	 *            the params
	 * @param xml
	 *            the xml
	 * @return byte[] array containing the report
	 * @throws Exception
	 *             the exception
	 */
	private static byte[] createReportFromXMLtoByteArray(String report, Map<String, Object> params, String xml) throws Exception {
		params.put("SUBREPORT_DIR", path + sub_path);
		params.put("PATH", path);
		params.put("REPORT_LOCALE", new Locale("en", "ZA"));
		params.put("net.sf.jasperreports.awt.ignore.missing.font", "true");
		InputStream reportStream = new BufferedInputStream(new FileInputStream(path + sub_path + report));
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder(); // throws
																// ParserConfigurationException
		StringReader reader = new StringReader(xml);
		InputSource inputData = new InputSource(reader);
		Document inputDocument = builder.parse(inputData);

		params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, inputDocument);
		params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
		params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.##");
		params.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.ENGLISH);
		params.put(JRParameter.REPORT_LOCALE, Locale.ENGLISH);
		JasperRunManager.runReportToPdfStream(reportStream, out, params);
		byte[] rtn = out.toByteArray();
		out.close();
		return rtn;
	}

	/**
	 * Creates the resp obj.
	 *
	 * @param response
	 *            the response
	 * @param contentType
	 *            the content type
	 * @param fileName
	 *            the file name
	 * @return the http servlet response
	 */
	private static HttpServletResponse createRespObj(HttpServletResponse response, String contentType, String fileName) {
		response.setContentType(contentType);
		response.setHeader("Content-disposition", "attachment; filename=" + fileName);
		response.setHeader("Expires", "0");
		response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
		response.setHeader("Pragma", "public");
		return response;
	}

	/**
	 * Creates the report from D bto file.
	 *
	 * @param report
	 *            the report
	 * @param params
	 *            the params
	 * @param filenameOut
	 *            the filename out
	 * @param filename
	 *            the filename
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("serial")
	public void createReportFromDBtoFile(String report, Map<String, Object> params, String filenameOut, String filename) throws Exception {
		try {
			params.put("PATH", path);
			params.put("REPORT_LOCALE", new Locale("en", "ZA"));
			params.put("SUBREPORT_DIR", path + sub_path);
			params.put("net.sf.jasperreports.awt.ignore.missing.font", "true");
			Session session = new AbstractDAO() {

				@Override
				public AbstractDataProvider getDataProvider() {
					return new MySQLProvider();
				}
			}.getSession();
			File f = new File(workPath + filename);
			OutputStream out = new FileOutputStream(f);
			InputStream reportStream = new BufferedInputStream(new FileInputStream(path + sub_path + report));

			session.doWork(new RunReportToPdfStreamHelper(reportStream, out, params));
			out.flush();
			out.close();

			convertByteArrayToServletOutputStream(FileUtils.readFileToByteArray(new File(workPath + filename)), filenameOut);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

	/**
	 * Print report using Java Bean
	 *
	 * @param report
	 *            the report
	 * @param fileName
	 *            the fileName
	 * @param collection
	 *            collection
	 */
	public void printReportUsingBean(String report, String fileName, Collection collection, Map<String, Object> params) throws Exception {

		JasperReport jasperReport = JasperCompileManager.compileReport(path + sub_path + report);
		JRDataSource dataSource = new JRBeanCollectionDataSource(collection);
		JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);
		// PrintingThe Report
		byte[] output = JasperExportManager.exportReportToPdf(jasperPrint);
		convertByteArrayToServletOutputStream(output, fileName);

	}

	/**
	 * Format array.
	 *
	 * @param arr
	 *            the arr
	 * @return the string
	 */
	private String formatArray(Address[] arr) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arr.length; i++) {
			sb.append(arr[i].toString());
			if (i < arr.length - 1) sb.append(", ");
		}
		return sb.toString();
	}

	/**
	 * The Class RunReportToPdfStreamHelper.
	 */
	class RunReportToPdfStreamHelper implements Work {

		/** The input stream. */
		private InputStream inputStream;

		/** The output stream. */
		private OutputStream outputStream;

		/** The parameters. */
		private Map<?, ?> parameters;

		/**
		 * Instantiates a new run report to pdf stream helper.
		 *
		 * @param inputStream
		 *            the input stream
		 * @param outputStream
		 *            the output stream
		 * @param parameters
		 *            the parameters
		 */
		public RunReportToPdfStreamHelper(InputStream inputStream, OutputStream outputStream, Map<?, ?> parameters) {
			this.inputStream = inputStream;
			this.outputStream = outputStream;
			this.parameters = parameters;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.hibernate.jdbc.Work#execute(java.sql.Connection)
		 */
		@Override
		public void execute(Connection conn) throws SQLException {
			try {
				JasperRunManager.runReportToPdfStream(inputStream, outputStream, (Map<String, Object>) parameters, conn);
			} catch (JRException e) {
				throw new SQLException(e);
			}
		}

	}

	/**
	 * Creates the report from XML.
	 *
	 * @param report
	 *            the report
	 * @param params
	 *            the params
	 * @param xml
	 *            the xml
	 * @param draft
	 *            the draft
	 * @param filename
	 *            the filename
	 * @throws Exception
	 *             the exception
	 */
	public void createReportFromXML(String report, Map<String, Object> params, String xml, boolean draft, String filename) throws Exception {
		FacesContext context = javax.faces.context.FacesContext.getCurrentInstance();
		Object resp = context.getExternalContext().getResponse();
		if (resp instanceof HttpServletResponse) {
			HttpServletResponse response = createRespObj((HttpServletResponse) resp, _pdfContent, filename);
			InputStream reportStream = new BufferedInputStream(new FileInputStream(path + sub_path + report));
			ServletOutputStream out = response.getOutputStream();

			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder(); // throws
																	// ParserConfigurationException
			StringReader reader = new StringReader(xml);
			InputSource inputData = new InputSource(reader);
			Document inputDocument = builder.parse(inputData);

			params.put(JRXPathQueryExecuterFactory.PARAMETER_XML_DATA_DOCUMENT, inputDocument);
			params.put(JRXPathQueryExecuterFactory.XML_DATE_PATTERN, "yyyy-MM-dd");
			params.put(JRXPathQueryExecuterFactory.XML_NUMBER_PATTERN, "#,##0.##");
			params.put(JRXPathQueryExecuterFactory.XML_LOCALE, Locale.ENGLISH);
			params.put(JRParameter.REPORT_LOCALE, Locale.ENGLISH);

			JasperRunManager.runReportToPdfStream(reportStream, out, params);
			out.flush();
			out.close();
			context.responseComplete();
			// out.close();
		}

	}

	/**
	 * Export to excel.
	 *
	 * @param report
	 *            the report
	 * @param params
	 *            the params
	 * @param filename
	 *            the filename
	 * @throws Exception
	 *             the exception
	 */
	public void exportToExcel(String report, Map<String, Object> params, String filename) throws Exception {
		Session session = new AbstractDAO() {

			@Override
			public AbstractDataProvider getDataProvider() {
				return new MySQLProvider();
			}
		}.getSession();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try {

			session.doWork(new Work() {
				@Override
				public void execute(Connection connection) throws SQLException {
					try {
						JasperPrint jasperPrint = JasperFillManager.fillReport(path + sub_path + report, params, connection);

						JRXlsxExporter Xlsxexporter = new JRXlsxExporter();
						Xlsxexporter.setExporterInput(new SimpleExporterInput(jasperPrint));
						Xlsxexporter.setExporterOutput(new SimpleOutputStreamExporterOutput(out));
						SimpleXlsxReportConfiguration configuration = new SimpleXlsxReportConfiguration();
						configuration.setDetectCellType(true);
						configuration.setCollapseRowSpan(false);
						Xlsxexporter.exportReport();
						byte[] b = out.toByteArray();
						convertByteArrayToServletOutputStreamExcel(b, filename);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			session.close();
			out.close();
			out.flush();
		}
	}

	/**
	 * Merge list of PDF
	 * 
	 * @param pdfByteList
	 * @param fileName
	 * @throws Exception
	 * @return void
	 */
	public void mergePDF(List<byte[]> pdfByteList, String fileName) throws Exception {

		// Instantiating PDFMergerUtility class
		PDFMergerUtility PDFmerger = new PDFMergerUtility();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PDFmerger.setDestinationStream(bos);
		for (byte[] myB : pdfByteList) {

			PDFmerger.addSource(new ByteArrayInputStream(myB));

		}
		PDFmerger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());

		convertByteArrayToServletOutputStream(bos.toByteArray(), fileName);

	}

	/**
	 * Merge list of PDF
	 * 
	 * @param pdfByteList
	 * @param fileName
	 * @return Doc
	 * @throws Exception
	 */
	public Doc mergePDFs(List<byte[]> pdfByteList, String fileName) throws Exception {
		Doc doc = new Doc();
		// Instantiating PDFMergerUtility class
		PDFMergerUtility PDFmerger = new PDFMergerUtility();
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		PDFmerger.setDestinationStream(bos);
		for (byte[] myB : pdfByteList) {

			PDFmerger.addSource(new ByteArrayInputStream(myB));

		}
		PDFmerger.mergeDocuments(MemoryUsageSetting.setupTempFileOnly());

		doc.setOriginalFname(fileName);
		doc.setExtension("pdf");
		doc.setData(bos.toByteArray());

		return doc;

	}

	/**
	 * Convert Jasper report to byte array
	 * 
	 * @param report
	 *            the report
	 * @param params
	 *            the params
	 * @param filename
	 *            the filename
	 * @return byte[]
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("serial")
	public byte[] convertJasperReportToByte(String report, Map<String, Object> params) throws Exception {
		// Doc doc = new Doc();
		byte[] b = null;
		Session session = new AbstractDAO() {

			@Override
			public AbstractDataProvider getDataProvider() {
				return new MySQLProvider();
			}
		}.getSession();
		ByteArrayOutputStream out = new ByteArrayOutputStream();

		try {
			params.put("PATH", path);
			params.put("REPORT_LOCALE", new Locale("en", "GB"));
			params.put("net.sf.jasperreports.awt.ignore.missing.font", "true");
			params.put("SUBREPORT_DIR", path + sub_path);
			InputStream reportStream = new BufferedInputStream(new FileInputStream(path + sub_path + report));
			session.doWork(new RunReportToPdfStreamHelper(reportStream, out, params));
			b = out.toByteArray();
		} finally {
			session.close();
			out.close();
			out.flush();
		}
		return b;
	}

	/**
	 * Convert a file to bite array
	 *
	 * @param file
	 * @return byte[]
	 * @throws Exception
	 *             the exception
	 */
	public static byte[] readBytesFromFile(File file) throws Exception {
		FileInputStream fileInputStream = null;
		byte[] bytesArray = null;
		bytesArray = new byte[(int) file.length()];

		// read file into bytes[]
		fileInputStream = new FileInputStream(file);
		fileInputStream.read(bytesArray);
		if (fileInputStream != null) {
			fileInputStream.close();
		}
		return bytesArray;
	}

	public void multipleJasperDocumentsToZip(List<AttachmentBean> attachmentBeans, String zipName) {
		Session session = new AbstractDAO() {
			@Override
			public AbstractDataProvider getDataProvider() {
				return new MySQLProvider();
			}
		}.getSession();

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (ZipOutputStream zos = new ZipOutputStream(baos)) {
			for (AttachmentBean attachmentBean : attachmentBeans) {
				ZipEntry entry = new ZipEntry(attachmentBean.getFilename());			
				byte[] b = attachmentBean.getFile();
				zos.putNextEntry(entry);
				zos.write(b);
				zos.closeEntry();
			}
			zos.close();
			convertByteArrayToServletOutputStream(baos.toByteArray(), zipName, _zipContent);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			session.close();
			try {
				baos.close();
				baos.flush();
				out.close();
				out.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
