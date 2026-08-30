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
import haj.com.dao.lookup.LegacyInternshipDAO;
import haj.com.entity.lookup.LegacyInternship;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.JasperService;
import haj.com.utils.GenericUtility;

public class LegacyInternshipService extends AbstractService {
	/** The dao. */
	private LegacyInternshipDAO dao = new LegacyInternshipDAO();
	private HomeAffairsService homeAffairsService = new HomeAffairsService();
	private QualificationService qualificationService = new QualificationService();
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();

	private static LegacyInternshipService legacyInternshipService = null;
	public static synchronized LegacyInternshipService instance() {
		if (legacyInternshipService == null) {
			legacyInternshipService = new LegacyInternshipService();
		}
		return legacyInternshipService;
	}
	/**
	 * Get all LegacyInternship
	 * 
	 * @author TechFinium
	 * @see LegacyInternship
	 * @return a list of {@link haj.com.entity.LegacyInternship}
	 * @throws Exception the exception
	 */
	public List<LegacyInternship> allLegacyInternship() throws Exception {
		return dao.allLegacyInternship();
	}

	/**
	 * Create or update LegacyInternship.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyInternship
	 */
	public void create(LegacyInternship entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyInternship.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyInternship
	 */
	public void update(LegacyInternship entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyInternship.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyInternship
	 */
	public void delete(LegacyInternship entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyInternship}
	 * @throws Exception the exception
	 * @see LegacyInternship
	 */
	public LegacyInternship findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyInternship by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyInternship}
	 * @throws Exception the exception
	 * @see LegacyInternship
	 */
	public List<LegacyInternship> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyInternship
	 * 
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyInternship}
	 * @throws Exception the exception
	 */
	public List<LegacyInternship> allLegacyInternship(int first, int pageSize) throws Exception {
		return dao.allLegacyInternship(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyInternship for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyInternship
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyInternship.class);
	}

	/**
	 * Lazy load LegacyInternship with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1    LegacyInternship class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.LegacyInternship} containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyInternship> allLegacyInternship(Class<LegacyInternship> class1, int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyInternship>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyInternship for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity  LegacyInternship class
	 * @param filters the filters
	 * @return Number of rows in the LegacyInternship entity
	 * @throws Exception the exception
	 */
	public int count(Class<LegacyInternship> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyInternship> allEntries = allLegacyInternship();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyInternship> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacyInternshipNotProcessed() throws Exception {
		return dao.countAllLegacyInternshipNotProcessed();
	}

	public List<LegacyInternship> allLegacyInternshipNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyInternshipNotProcessed(numberOfEntries);
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
			logger.info("validateRsaIdNumbers() Started");
			Integer count = countAllLegacyInternshipNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyInternship> li = allLegacyInternshipNotProcessed(1000);
				for (LegacyInternship legacyInternship : li) {

					if (legacyInternship.getSaqaId() != null && !legacyInternship.getSaqaId().isEmpty()) {
						try {
							Qualification qua = qualificationService
									.findByCodeString(legacyInternship.getSaqaId().trim());
							if (qua != null && qua.getId() != null) {
								legacyInternship.setQualification(qua);
							}
						} catch (Exception e) {
						}
					}

					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService.findByDhaIdNumber(legacyInternship.getIdNo().trim());
						if (entriesFound > 0) {
							legacyInternship.setAppearsOnHomeAffairsData(true);
						} else {
							legacyInternship.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						legacyInternship.setAppearsOnHomeAffairsData(false);
					}
					// On Sites Table
					if (legacyInternship.getEmployerSdl() != null && !legacyInternship.getEmployerSdl().isEmpty()) {
						try {
							int countSite = legacyOrganisationSitesService
									.countBySdlNumber(legacyInternship.getEmployerSdl().trim());
							if (countSite == 1) {
								LegacyOrganisationSites los = legacyOrganisationSitesService
										.findBySdlNumber(legacyInternship.getEmployerSdl().trim());
								if (los != null && los.getId() != null) {
//									legacyInternship.setLegacyOrganisationSites(los);
								}
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}
					legacyInternship.setProcessed(true);

					updateList.add(legacyInternship);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyInternshipNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Internship Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Internship Process","Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
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
			String fileName = "Legacy Internship Report.xlsx";
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
			String fileName = "Legacy Internship Report.xlsx";
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
	
	private void populateDataForReport(Map<String, Object[]> data, Integer counter,String sdlNo) throws Exception{
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
