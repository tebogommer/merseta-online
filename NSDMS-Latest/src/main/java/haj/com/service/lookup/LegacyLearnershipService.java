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
import haj.com.dao.lookup.LegacyLearnershipDAO;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LegacyLearnership;
import haj.com.entity.lookup.LegacyLearnership;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.LegacyLearnership;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.JasperService;
import haj.com.utils.GenericUtility;

public class LegacyLearnershipService extends AbstractService {
	/** The dao. */
	private LegacyLearnershipDAO dao = new LegacyLearnershipDAO();
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();

	private static LegacyLearnershipService legacyLearnershipService = null;
	public static synchronized LegacyLearnershipService instance() {
		if (legacyLearnershipService == null) {
			legacyLearnershipService = new LegacyLearnershipService();
		}
		return legacyLearnershipService;
	}

	/**
	 * Get all LegacyLearnership
	 * 
	 * @author TechFinium
	 * @see LegacyLearnership
	 * @return a list of {@link haj.com.entity.LegacyLearnership}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyLearnership> allLegacyLearnership() throws Exception {
		return dao.allLegacyLearnership();
	}

	/**
	 * Create or update LegacyLearnership.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnership
	 */
	public void create(LegacyLearnership entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyLearnership.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnership
	 */
	public void update(LegacyLearnership entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyLearnership.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnership
	 */
	public void delete(LegacyLearnership entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyLearnership}
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnership
	 */
	public LegacyLearnership findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyLearnership by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyLearnership}
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnership
	 */
	public List<LegacyLearnership> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<LegacyLearnership> findByEmployerSdl(String employerSdl) throws Exception {
		return dao.findByName(employerSdl);
	}

	/**
	 * Lazy load LegacyLearnership
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyLearnership}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyLearnership> allLegacyLearnership(int first, int pageSize) throws Exception {
		return dao.allLegacyLearnership(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyLearnership for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyLearnership
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyLearnership.class);
	}

	/**
	 * Lazy load LegacyLearnership with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyLearnership class
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
	 * @return a list of {@link haj.com.entity.LegacyLearnership} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyLearnership> allLegacyLearnership(Class<LegacyLearnership> class1, int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyLearnership>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyLearnership for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyLearnership class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyLearnership entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyLearnership> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyLearnership> allEntries = allLegacyLearnership();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyLearnership> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public Integer countAllLegacyLearnershipNotProcessed() throws Exception {
		return dao.countAllLegacyLearnershipNotProcessed();
	}
	
	public Integer countAllLegacyLearnershipNoQualification() throws Exception {
		return dao.countAllLegacyLearnershipNoQualification();
	}

	public List<LegacyLearnership> allLegacyLearnershipNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyLearnershipNotProcessed(numberOfEntries);
	}
	
	public List<LegacyLearnership> allLegacyLearnershipNoQualification(int numberOfEntries) throws Exception {
		return dao.allLegacyLearnershipNoQualification(numberOfEntries);
	}
	
	public List<LegacyLearnership> allLegacyLearnershipNotLearnership() throws Exception {
		return dao.allLegacyLearnershipNotLearnership();
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
			Integer count = countAllLegacyLearnershipNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyLearnership> li = allLegacyLearnershipNotProcessed(1000);
				for (LegacyLearnership entry : li) {


					
					if (entry.getEmployerSdl() != null && !entry.getEmployerSdl().isEmpty()) {
						try {
							int countSite = legacyOrganisationSitesService.countBySdlNumber(entry.getEmployerSdl().trim());
							if (countSite == 1) {
								LegacyOrganisationSites los = legacyOrganisationSitesService.findBySdlNumber(entry.getEmployerSdl().trim());
								if (los != null && los.getId() != null) {
									// entry.setLegacyOrganisationSites(los);
								}
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}

					entry.setProcessed(true);

					updateList.add(entry);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyLearnershipNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Learnership Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Learnership  Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	public void legacyAccreditationUpdate(String levyNumber, String accreditationNumber) throws Exception {
		List<LegacyLearnership> list = dao.findByProviderSDL(levyNumber);
		if(list.size() > 0) {
			List<IDataEntity> updateList = new ArrayList<>();
			for(LegacyLearnership legacySkillsProgramme :list) {
				legacySkillsProgramme.setAccreditationNumber(accreditationNumber);
				updateList.add(legacySkillsProgramme);
			}
			dao.updateBatch(updateList);
		}else {
			throw new Exception("No data found");
		}
	}
	
	public void legacyLearnershipUpdate(String legacyCode, String newCode, String qualificationCode) throws Exception {
		List<LegacyLearnership> list = dao.findByLearnershipCode(legacyCode);
		Learnership learnership = null;
		if(LearnershipService.instance().countLearnershipByCode(newCode) > 1) {
			if(qualificationCode.matches("")) {
				throw new Exception("Please qualification code");
			}
			learnership = LearnershipService.instance().findByLearnershipCode(newCode, qualificationCode);
		}else {
			learnership = LearnershipService.instance().findByLearnershipCode(newCode);
		}
		
		if(learnership == null) {
			throw new Exception("No Learnership found for code" + newCode);
		}
		if(list.size() > 0) {
			List<IDataEntity> updateList = new ArrayList<>();
			for(LegacyLearnership legacyLearnership :list) {
				legacyLearnership.setLearnership(learnership);
				legacyLearnership.setLearnershipCode(newCode);
				updateList.add(legacyLearnership);
			}
			dao.updateBatch(updateList);
		}else {
			throw new Exception("No data found");
		}
	}
	
	public void validateQualification() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				validiationQualificationRun();
			}
		}).start();
	}

	public void validiationQualificationRun() {
		try {
			logger.info("validiationRun() Started");
			
			List<IDataEntity> updateList = new ArrayList<>();
		
			updateList = new ArrayList<>();
			List<LegacyLearnership> li = allLegacyLearnershipNotLearnership();
			for (LegacyLearnership entry : li) {
				if (entry.getLearnershipCode() != null && entry.getlShipCode() != null) {
					try {
						Learnership learnership = LearnershipService.instance().findByLearnershipCode(entry.getLearnershipCode(), entry.getlShipCode());
						if (learnership != null) {
							entry.setLearnership(learnership);
							updateList.add(entry);
						}
					} catch (Exception e) {
						logger.fatal(e);
						e.printStackTrace();
					}
				}
			}
			dao.updateBatch(updateList);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Learnership Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Learnership  Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
	
	public void validiationQualificationRunOld() {
		try {
			logger.info("validiationRun() Started");
			Integer count = countAllLegacyLearnershipNoQualification();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyLearnership> li = allLegacyLearnershipNoQualification(1000);
				for (LegacyLearnership entry : li) {
					if (entry.getLearnershipCode() != null && !entry.getLearnershipCode().isEmpty()) {
						try {
							Learnership learnership = LearnershipService.instance().findByLearnershipCode(entry.getLearnershipCode());
							if (learnership != null) {
								entry.setLearnership(learnership);
								updateList.add(entry);
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyLearnershipNoQualification();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Learnership Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Learnership  Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
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
			String fileName = "Legacy Learnership Report.xlsx";
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
			String fileName = "Legacy Learnership Report.xlsx";
			JasperService.instance().downloadFileExcel(bytes, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void downloadReportAccreditaionNo(String accreditationNumber) throws Exception{
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		Integer counter = 1;
		// headers
		data.put(counter.toString(), new Object[] { "Employer Entity ID", "Employer Name", "Employer Trading Name", "Provider Entity ID", "Provider Name",  "Provider Trading Name", "Accreditation Number", "First name", "Middle name", "Surname", "RSA ID Number", "Passport Number", "Effective Date", "Start Date", "End Date", "Status", "code" , "Title"});
		counter++;
		// data population
		populateDataForReportAccreditationNumber(data, counter, accreditationNumber);
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
			String fileName = "Legacy Learnership Report.xlsx";
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
	
	private void populateDataForReportAccreditationNumber(Map<String, Object[]> data, Integer counter, String accreditationNumber) throws Exception{
		int counterForPopulation = counter;
		List<LegacyDataReportBean> resultsList = populateReportAccreditationNumber(accreditationNumber);
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
	
	public List<LegacyDataReportBean> populateReportAccreditationNumber(String accreditationNumber) throws Exception {
		return dao.populateReportAccreditationNumber(accreditationNumber);
	}
}
