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
import haj.com.dao.lookup.LegacySECTTwentyEightDAO;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.LegacySECTTwentyEight;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.JasperService;
import haj.com.utils.GenericUtility;

public class LegacySECTTwentyEightService extends AbstractService {
	/** The dao. */
	private LegacySECTTwentyEightDAO dao = new LegacySECTTwentyEightDAO();
	private HomeAffairsService homeAffairsService = new HomeAffairsService();
	private QualificationService qualificationService = new QualificationService();
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();

	private static LegacySECTTwentyEightService legacySECTTwentyEightService = null;
	public static synchronized LegacySECTTwentyEightService instance() {
		if (legacySECTTwentyEightService == null) {
			legacySECTTwentyEightService = new LegacySECTTwentyEightService();
		}
		return legacySECTTwentyEightService;
	}
	/**
	 * Get all LegacySECTTwentyEight
	 * 
	 * @author TechFinium
	 * @see LegacySECTTwentyEight
	 * @return a list of {@link haj.com.entity.LegacySECTTwentyEight}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacySECTTwentyEight> allLegacySECTTwentyEight() throws Exception {
		return dao.allLegacySECTTwentyEight();
	}

	/**
	 * Create or update LegacySECTTwentyEight.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacySECTTwentyEight
	 */
	public void create(LegacySECTTwentyEight entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update LegacySECTTwentyEight.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacySECTTwentyEight
	 */
	public void update(LegacySECTTwentyEight entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacySECTTwentyEight.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacySECTTwentyEight
	 */
	public void delete(LegacySECTTwentyEight entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacySECTTwentyEight}
	 * @throws Exception
	 *             the exception
	 * @see LegacySECTTwentyEight
	 */
	public LegacySECTTwentyEight findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacySECTTwentyEight by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacySECTTwentyEight}
	 * @throws Exception
	 *             the exception
	 * @see LegacySECTTwentyEight
	 */
	public List<LegacySECTTwentyEight> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacySECTTwentyEight
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacySECTTwentyEight}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacySECTTwentyEight> allLegacySECTTwentyEight(int first, int pageSize) throws Exception {
		return dao.allLegacySECTTwentyEight(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacySECTTwentyEight for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacySECTTwentyEight
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacySECTTwentyEight.class);
	}

	/**
	 * Lazy load LegacySECTTwentyEight with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacySECTTwentyEight class
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
	 * @return a list of {@link haj.com.entity.LegacySECTTwentyEight} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySECTTwentyEight> allLegacySECTTwentyEight(Class<LegacySECTTwentyEight> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacySECTTwentyEight>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacySECTTwentyEight for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacySECTTwentyEight class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacySECTTwentyEight entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacySECTTwentyEight> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<LegacySECTTwentyEight> allLegacyLegacySECTTwentyEight(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from LegacySECTTwentyEight o where o.sdlNo is null and o.wasdl is null ";
		return (List<LegacySECTTwentyEight>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllLegacyLegacySECTTwentyEight(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacySECTTwentyEight o where o.sdlNo is null and o.wasdl is null";
		return dao.countWhere(filters, hql);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<LegacySECTTwentyEight> allLegacyLegacySECTTwentyEightNew(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from LegacySECTTwentyEight o ";
		return (List<LegacySECTTwentyEight>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllLegacyLegacySECTTwentyEightNew(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacySECTTwentyEight o ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacySECTTwentyEight> allEntries = allLegacySECTTwentyEight();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacySECTTwentyEight> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public void validateRsaIdNumbers() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				validiationRun();
			}
		}).start();
	}

	public Integer countAllLegacySECTTwentyEightNotProcessed() throws Exception {
		return dao.countAllLegacySECTTwentyEightNotProcessed();
	}

	public List<LegacySECTTwentyEight> allLegacySECTTwentyEightNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacySECTTwentyEightNotProcessed(numberOfEntries);
	}

	public void validiationRun() {
		try {
			logger.info("validiationRun() Started");
			Integer count = countAllLegacySECTTwentyEightNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacySECTTwentyEight> li = allLegacySECTTwentyEightNotProcessed(1000);
				for (LegacySECTTwentyEight legacySECTTwentyEight : li) {

					// ID Validation
					if (legacySECTTwentyEight.getIdNo() != null && !legacySECTTwentyEight.getIdNo().trim().isEmpty()) {
						try {
							legacySECTTwentyEight.setValidRsaIdNumber(GenericUtility.checkRsaId(legacySECTTwentyEight.getIdNo().trim()));
						} catch (Exception e) {
							legacySECTTwentyEight.setValidRsaIdNumber(false);
						}
					}

					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService.findByDhaIdNumber(legacySECTTwentyEight.getIdNo().trim());
						if (entriesFound > 0) {
							legacySECTTwentyEight.setAppearsOnHomeAffairsData(true);
						} else {
							legacySECTTwentyEight.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						legacySECTTwentyEight.setAppearsOnHomeAffairsData(false);
					}

					// On Sites Table
					if (legacySECTTwentyEight.getSdlNo() != null && !legacySECTTwentyEight.getSdlNo().isEmpty()) {
						try {
							int countSite = legacyOrganisationSitesService.countBySdlNumber(legacySECTTwentyEight.getSdlNo().trim());
							if (countSite == 1) {
								LegacyOrganisationSites los = legacyOrganisationSitesService.findBySdlNumber(legacySECTTwentyEight.getSdlNo().trim());
								if (los != null && los.getId() != null) {
//									legacySECTTwentyEight.setLegacyOrganisationSites(los);
								}
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}

					try {
						Qualification qua = qualificationService.findByCodeString(legacySECTTwentyEight.getSaqaCode().trim());
						if (qua != null && qua.getId() != null) {
							legacySECTTwentyEight.setQualification(qua);
						}
					} catch (Exception e) {
					}

					// legacySECTTwentyEight.setProcessed(true);

					updateList.add(legacySECTTwentyEight);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacySECTTwentyEightNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Section 28 Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
			logger.info("validateRsaIdNumbers() Complete");
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Section 28 Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
	
	public void downloadReport() throws Exception{
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		Integer counter = 1;
		// headers
		data.put(counter.toString(), new Object[] { "Employer Entity ID", "Employer Name", "Employer Trading Name", "Provider Entity ID", "Provider Name",  "Provider Trading Name", "First name", "Surname", "RSA ID Number", "Passport Number", "Effective Date", "Start Date", "End Date", "Status", "code" , "Title"});
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
			String fileName = "Legacy Legacy Section 28 Report.xlsx";
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
		data.put(counter.toString(), new Object[] { "Employer Entity ID", "Employer Name", "Employer Trading Name", "Provider Entity ID", "Provider Name",  "Provider Trading Name", "First name", "Surname", "RSA ID Number", "Passport Number", "Effective Date", "Start Date", "End Date", "Status", "code" , "Title"});
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
			String fileName = "Legacy Legacy Section 28 Report.xlsx";
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
		data.put(counter.toString(), new Object[] { entry.getEntityId(), entry.getCompanyName(), entry.getTradingName(), entry.getProviderEntityId(), entry.getProviderName(), entry.getProviderTradingName(), entry.getFirstName(), entry.getLastName(), entry.getRsaIdNumber(), entry.getPassportNumber(), entry.getEffectiveDate(), entry.getStartDate() , entry.getEndDate(), entry.getStatus(), entry.getCode(), entry.getTitle()});
	}

	
	public List<LegacyDataReportBean> populateReport() throws Exception {
		return dao.populateReport();
	}
	
	public List<LegacyDataReportBean> populateReport(String sdlNo) throws Exception {
		return dao.populateReport(sdlNo);
	}
}
