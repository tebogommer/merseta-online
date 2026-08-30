package haj.com.service.lookup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.primefaces.model.SortOrder;

import haj.com.bean.LegacyDataReportBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyBursaryDAO;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.JasperService;
import haj.com.utils.GenericUtility;

public class LegacyBursaryService extends AbstractService {
	/** The dao. */
	private LegacyBursaryDAO dao = new LegacyBursaryDAO();
	private HomeAffairsService homeAffairsService = new HomeAffairsService();
	private QualificationService qualificationService = new QualificationService();
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();

	private static LegacyBursaryService legacyBursaryService = null;
	public static synchronized LegacyBursaryService instance() {
		if (legacyBursaryService == null) {
			legacyBursaryService = new LegacyBursaryService();
		}
		return legacyBursaryService;
	}
	/**
	 * Get all LegacyBursary
	 * 
	 * @author TechFinium
	 * @see LegacyBursary
	 * @return a list of {@link haj.com.entity.LegacyBursary}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyBursary> allLegacyBursary() throws Exception {
		return dao.allLegacyBursary();
	}

	/**
	 * Create or update LegacyBursary.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyBursary
	 */
	public void create(LegacyBursary entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update LegacyBursary.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyBursary
	 */
	public void update(LegacyBursary entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyBursary.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyBursary
	 */
	public void delete(LegacyBursary entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyBursary}
	 * @throws Exception
	 *             the exception
	 * @see LegacyBursary
	 */
	public LegacyBursary findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyBursary by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyBursary}
	 * @throws Exception
	 *             the exception
	 * @see LegacyBursary
	 */
	public List<LegacyBursary> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<LegacyBursary> findBySdlNumber(String levyNumber) throws Exception {
		return dao.findBySdlNumber(levyNumber);
	}

	/**
	 * Lazy load LegacyBursary
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyBursary}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyBursary> allLegacyBursary(int first, int pageSize) throws Exception {
		return dao.allLegacyBursary(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyBursary for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyBursary
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyBursary.class);
	}

	/**
	 * Lazy load LegacyBursary with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyBursary class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.LegacyBursary} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyBursary> allLegacyBursary(Class<LegacyBursary> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyBursary>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyBursary for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyBursary class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyBursary entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyBursary> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyBursary> allEntries = allLegacyBursary();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyBursary> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacyBursaryNotProcessed() throws Exception {
		return dao.countAllLegacyBursaryNotProcessed();
	}

	public List<LegacyBursary> allLegacyBursaryNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyBursaryNotProcessed(numberOfEntries);
	}

	public void validateRsaIdNumbers() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				validiationRun();
			}
		}).start();
	}

	public void validiationRun() {
		try {
			logger.info("validiationRun() Started");
			Integer count = countAllLegacyBursaryNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyBursary> li = allLegacyBursaryNotProcessed(1000);
				for (LegacyBursary legacyBursary : li) {
					if (legacyBursary.getSaqaId() != null && !legacyBursary.getSaqaId().isEmpty()) {
						try {
							Qualification qua = qualificationService.findByCodeString(legacyBursary.getSaqaId().trim());
							if (qua != null && qua.getId() != null) {
								legacyBursary.setQualification(qua);
							}
						} catch (Exception e) {
						}
					}
					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService.findByDhaIdNumber(legacyBursary.getIdNo().trim());
						if (entriesFound > 0) {
							legacyBursary.setAppearsOnHomeAffairsData(true);
						} else {
							legacyBursary.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						legacyBursary.setAppearsOnHomeAffairsData(false);
					}
					// On Sites Table
					if (legacyBursary.getEmployerSdl() != null && !legacyBursary.getEmployerSdl().isEmpty()) {
						try {
							int countSite = legacyOrganisationSitesService.countBySdlNumber(legacyBursary.getEmployerSdl().trim());
							if (countSite == 1) {
								LegacyOrganisationSites los = legacyOrganisationSitesService.findBySdlNumber(legacyBursary.getEmployerSdl().trim());
								if (los != null && los.getId() != null) {
									// legacyBursary.setLegacyOrganisationSites(los);
								}
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}

					// legacyBursary.setProcessed(true);

					updateList.add(legacyBursary);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyBursaryNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Bursary Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Bursary Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	/*
	 * REPORTING START
	 */

	/*
	 * Count Reporting: 1 - processed by boolean: value 2 - Valid RSA ID Numbers by
	 * boolean: value 3 - On Home Affairs Files by boolean: value 4 - Linked To SAQA
	 * Qualification 5 - Not Linked To SAQA Qualification 6 - Linked To Site 7 - Not
	 * Linked To Site
	 */
	public Integer countReporting(int reportNumber, boolean value) throws Exception {
		return dao.countReporting(reportNumber, value);
	}

	public void downloadReport() throws Exception{
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		Integer counter = 1;
		// headers
		data.put(counter.toString(), new Object[] { "Employer Entity ID", "Employer Name", "Employer Trading Name", "Provider Entity ID", "Provider Name",  "Provider Trading Name", "Accreditation Number", "First name", "Middle name", "Surname", "RSA ID Number", "Passport Number", "Effective Date", "Start Date", "End Date", "Status", "code" , "Title"});
		counter++;
		// data population
		populateDataForReport(data, counter);
		counter = null;
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sh.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);;
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			byte[] bytes = bos.toByteArray();
			String fileName = "Legacy Skills Programme Report.xlsx";
			JasperService.instance().downloadFileExcel(bytes, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void downloadReport(String sdlNo) throws Exception{
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		Integer counter = 1;
		// headers
		data.put(counter.toString(), new Object[] { "Employer Entity ID", "Employer Name", "Employer Trading Name", "Provider Entity ID", "Provider Name",  "Provider Trading Name", "Accreditation Number", "First name", "Middle name", "Surname", "RSA ID Number", "Passport Number", "Effective Date", "Start Date", "End Date", "Status", "code" , "Title"});
		counter++;
		// data population
		populateDataForReport(data, counter, sdlNo);
		counter = null;
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sh.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);;
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			byte[] bytes = bos.toByteArray();
			String fileName = "Legacy Skills Programme Report.xlsx";
			JasperService.instance().downloadFileExcel(bytes, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void populateDataForReport(Map<String, Object[]> data, Integer counter) throws Exception{
		int counterForPopulation = counter;
		List<LegacyDataReportBean> resultsList = populateReport();
		// populate data found into report
		for (LegacyDataReportBean entry : resultsList) {
			populateDataReport(data, entry, counterForPopulation);
			counterForPopulation++;
		}
		resultsList.clear();
		counter = counterForPopulation;
	}
	
	private void populateDataForReport(Map<String, Object[]> data, Integer counter, String sdlNo) throws Exception{
		int counterForPopulation = counter;
		List<LegacyDataReportBean> resultsList = populateReport(sdlNo);
		// populate data found into report
		for (LegacyDataReportBean entry : resultsList) {
			populateDataReport(data, entry, counterForPopulation);
			counterForPopulation++;
		}
		resultsList.clear();
		counter = counterForPopulation;
	}
	
	private void populateDataReport(Map<String, Object[]> data, LegacyDataReportBean entry, Integer counter) throws Exception{
		data.put(counter.toString(), new Object[] { entry.getEntityId(), entry.getCompanyName(), entry.getTradingName(), entry.getProviderEntityId(), entry.getProviderName(), entry.getProviderTradingName(), entry.getAccreditationNumber(), entry.getFirstName(), entry.getMiddleNames(), entry.getLastName(), entry.getRsaIdNumber(), entry.getPassportNumber(), entry.getEffectiveDate(), entry.getStartDate() , entry.getEndDate(), entry.getStatus(), entry.getCode(), entry.getTitle()});
	}

	
	public List<LegacyDataReportBean> populateReport() throws Exception {
		return dao.populateReport();
	}
	
	public List<LegacyDataReportBean> populateReport(String sdlNo) throws Exception {
		return dao.populateReport(sdlNo);
	}
}
