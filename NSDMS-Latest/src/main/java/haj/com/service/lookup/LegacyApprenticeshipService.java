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
import haj.com.dao.lookup.LegacyApprenticeshipDAO;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.AddressService;
import haj.com.service.JasperService;
import haj.com.utils.GenericUtility;

public class LegacyApprenticeshipService extends AbstractService {

	/** The dao. */
	private LegacyApprenticeshipDAO dao = new LegacyApprenticeshipDAO();

	private LegacyApprenticeshipTradeTestService legacyApprenticeshipTradeTestService;
	private HomeAffairsService homeAffairsService = new HomeAffairsService();
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();
	
	private static LegacyApprenticeshipService legacyApprenticeshipService = null;
	public static synchronized LegacyApprenticeshipService instance() {
		if (legacyApprenticeshipService == null) {
			legacyApprenticeshipService = new LegacyApprenticeshipService();
		}
		return legacyApprenticeshipService;
	}

	/**
	 * Get all LegacyApprenticeship
	 * 
	 * @author TechFinium
	 * @see LegacyApprenticeship
	 * @return a list of {@link haj.com.entity.LegacyApprenticeship}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyApprenticeship> allLegacyApprenticeship() throws Exception {
		return dao.allLegacyApprenticeship();
	}

	/**
	 * Create or update LegacyApprenticeship.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyApprenticeship
	 */
	public void create(LegacyApprenticeship entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update LegacyApprenticeship.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyApprenticeship
	 */
	public void update(LegacyApprenticeship entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyApprenticeship.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyApprenticeship
	 */
	public void delete(LegacyApprenticeship entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyApprenticeship}
	 * @throws Exception
	 *             the exception
	 * @see LegacyApprenticeship
	 */
	public LegacyApprenticeship findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyApprenticeship by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyApprenticeship}
	 * @throws Exception
	 *             the exception
	 * @see LegacyApprenticeship
	 */
	public List<LegacyApprenticeship> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyApprenticeship
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyApprenticeship}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyApprenticeship> allLegacyApprenticeship(int first, int pageSize) throws Exception {
		return dao.allLegacyApprenticeship(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyApprenticeship for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyApprenticeship
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyApprenticeship.class);
	}

	/**
	 * Lazy load LegacyApprenticeship with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyApprenticeship class
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
	 * @return a list of {@link haj.com.entity.LegacyApprenticeship} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyApprenticeship> allLegacyApprenticeship(Class<LegacyApprenticeship> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyApprenticeship>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyApprenticeship for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyApprenticeship class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyApprenticeship entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyApprenticeship> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyApprenticeship> allEntries = allLegacyApprenticeship();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyApprenticeship> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countByRsaIdNumber(String idNumber) throws Exception {
		return dao.countByRsaIdNumber(idNumber);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacyApprenticeshipNotProcessed() throws Exception {
		return dao.countAllLegacyApprenticeshipNotProcessed();
	}

	public List<LegacyApprenticeship> allLegacyApprenticeshipNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyApprenticeshipNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyApprenticeshipNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyApprenticeship> li = allLegacyApprenticeshipNotProcessed(1000);
				for (LegacyApprenticeship legacyApprenticeship : li) {

					// ID Validation
					if (legacyApprenticeship.getIdNo() != null && !legacyApprenticeship.getIdNo().trim().isEmpty()) {
						try {
							legacyApprenticeship.setValidRsaIdNumber(GenericUtility.checkRsaId(legacyApprenticeship.getIdNo().trim()));
						} catch (Exception e) {
							legacyApprenticeship.setValidRsaIdNumber(false);
						}
					}

					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService.findByDhaIdNumber(legacyApprenticeship.getIdNo().trim());
						if (entriesFound > 0) {
							legacyApprenticeship.setAppearsOnHomeAffairsData(true);
						} else {
							legacyApprenticeship.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						legacyApprenticeship.setAppearsOnHomeAffairsData(false);
					}

					if (legacyApprenticeship.getSdlNo() != null && !legacyApprenticeship.getSdlNo().isEmpty()) {
						try {
							int countSite = legacyOrganisationSitesService.countBySdlNumber(legacyApprenticeship.getSdlNo().trim());
							if (countSite == 1) {
								LegacyOrganisationSites los = legacyOrganisationSitesService.findBySdlNumber(legacyApprenticeship.getSdlNo().trim());
								if (los != null && los.getId() != null) {
									// legacyApprenticeship.setLegacyOrganisationSites(los);
								}
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}

					// legacyApprenticeship.setProcessed(true);

					updateList.add(legacyApprenticeship);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyApprenticeshipNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Apprenticeship Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Apprenticeship Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	public void downloadReport() throws Exception{
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		Integer counter = 1;
		// headers
		data.put(counter.toString(), new Object[] { "Employer Entity ID", "Employer Name", "Employer Trading Name", "Provider Entity ID", "Provider Name",  "Provider Trading Name", "First name", "Middle name", "Surname", "RSA ID Number", "Passport Number", "Effective Date", "Start Date", "End Date", "Status", "code" , "Title"});
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
			String fileName = "Legacy Apprnticeship Report.xlsx";
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
		data.put(counter.toString(), new Object[] { "Employer Entity ID", "Employer Name", "Employer Trading Name", "Provider Entity ID", "Provider Name",  "Provider Trading Name", "First name", "Middle name", "Surname", "RSA ID Number", "Passport Number", "Effective Date", "Start Date", "End Date", "Status", "code" , "Title"});
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
			String fileName = "Legacy Apprnticeship Report.xlsx";
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
		data.put(counter.toString(), new Object[] { entry.getEntityId(), entry.getCompanyName(), entry.getTradingName(), entry.getProviderEntityId(), entry.getProviderName(), entry.getProviderTradingName(), entry.getFirstName(), entry.getMiddleNames(), entry.getLastName(), entry.getRsaIdNumber(), entry.getPassportNumber(), entry.getEffectiveDate(), entry.getStartDate() , entry.getEndDate(), entry.getStatus(), entry.getCode(), entry.getTitle()});
	}

	
	public List<LegacyDataReportBean> populateReport() throws Exception {
		return dao.populateReport();
	}
	
	public List<LegacyDataReportBean> populateReport(String sdlNo) throws Exception {
		return dao.populateReport(sdlNo);
	}
}