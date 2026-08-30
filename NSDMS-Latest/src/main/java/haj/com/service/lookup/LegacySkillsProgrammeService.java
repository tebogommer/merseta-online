package haj.com.service.lookup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import haj.com.bean.LegacyDataReportBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacySkillsProgrammeDAO;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.LegacySkillsProgramme;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.JasperService;
import haj.com.service.SarsEmployerDetailService;
import haj.com.utils.GenericUtility;

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

public class LegacySkillsProgrammeService extends AbstractService {
	/** The dao. */
	private LegacySkillsProgrammeDAO dao = new LegacySkillsProgrammeDAO();

	private HomeAffairsService homeAffairsService = new HomeAffairsService();
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();
	private SarsEmployerDetailService sarsEmployerDetailService = new SarsEmployerDetailService();

	private static LegacySkillsProgrammeService legacySkillsProgrammeService = null;
	public static synchronized LegacySkillsProgrammeService instance() {
		if (legacySkillsProgrammeService == null) {
			legacySkillsProgrammeService = new LegacySkillsProgrammeService();
		}
		return legacySkillsProgrammeService;
	}
	/**
	 * Get all LegacySkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgramme
	 * @return a list of {@link haj.com.entity.LegacySkillsProgramme}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacySkillsProgramme> allLegacySkillsProgramme() throws Exception {
		return dao.allLegacySkillsProgramme();
	}

	/**
	 * Create or update LegacySkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacySkillsProgramme
	 */
	public void create(LegacySkillsProgramme entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update LegacySkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacySkillsProgramme
	 */
	public void update(LegacySkillsProgramme entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacySkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacySkillsProgramme
	 */
	public void delete(LegacySkillsProgramme entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacySkillsProgramme}
	 * @throws Exception
	 *             the exception
	 * @see LegacySkillsProgramme
	 */
	public LegacySkillsProgramme findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacySkillsProgramme by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacySkillsProgramme}
	 * @throws Exception
	 *             the exception
	 * @see LegacySkillsProgramme
	 */
	public List<LegacySkillsProgramme> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacySkillsProgramme
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacySkillsProgramme}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacySkillsProgramme> allLegacySkillsProgramme(int first, int pageSize) throws Exception {
		return dao.allLegacySkillsProgramme(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacySkillsProgramme for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacySkillsProgramme
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacySkillsProgramme.class);
	}

	/**
	 * Lazy load LegacySkillsProgramme with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacySkillsProgramme class
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
	 * @return a list of {@link haj.com.entity.LegacySkillsProgramme} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySkillsProgramme> allLegacySkillsProgramme(Class<LegacySkillsProgramme> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacySkillsProgramme>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacySkillsProgramme for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacySkillsProgramme class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacySkillsProgramme entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacySkillsProgramme> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacySkillsProgramme> allEntries = allLegacySkillsProgramme();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacySkillsProgramme> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacySkillsProgrammeNotProcessed() throws Exception {
		return dao.countAllLegacySkillsProgrammeNotProcessed();
	}

	public List<LegacySkillsProgramme> allLegacySkillsProgrammeNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacySkillsProgrammeNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacySkillsProgrammeNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacySkillsProgramme> li = allLegacySkillsProgrammeNotProcessed(1000);
				for (LegacySkillsProgramme legacySkillsProgramme : li) {
					// Valid ID Number
					try {
						// legacySkillsProgramme.setValidIdNumber(GenericUtility.checkRsaId(legacySkillsProgramme.getIdNo()));
					} catch (Exception e) {
						// legacySkillsProgramme.setValidIdNumber(false);
					}
					// Employer SDL number on SARS
					try {
						if (legacySkillsProgramme.getEmployerSDL() != null && !legacySkillsProgramme.getEmployerSDL().isEmpty()) {
							int counterMainSDL = sarsEmployerDetailService.countByRefNumber(legacySkillsProgramme.getEmployerSDL().trim());
							if (counterMainSDL != 0) {
								// legacySkillsProgramme.setEmployerSDLNumberOnSarsEmployerFile(true);
							} else {
								// legacySkillsProgramme.setEmployerSDLNumberOnSarsEmployerFile(false);
							}
						} else {
							// legacySkillsProgramme.setEmployerSDLNumberOnSarsEmployerFile(false);
						}
					} catch (Exception e) {
						// legacySkillsProgramme.setEmployerSDLNumberOnSarsEmployerFile(false);
						logger.fatal(e);
					}
					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService.findByDhaIdNumber(legacySkillsProgramme.getIdNo().trim());
						if (entriesFound > 0) {
							legacySkillsProgramme.setAppearsOnHomeAffairsData(true);
						} else {
							legacySkillsProgramme.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						legacySkillsProgramme.setAppearsOnHomeAffairsData(false);
					}
					// On Sites Table
					if (legacySkillsProgramme.getProviderSDL() != null && !legacySkillsProgramme.getProviderSDL().isEmpty()) {
						try {
							int countSite = legacyOrganisationSitesService.countBySdlNumber(legacySkillsProgramme.getProviderSDL().trim());
							if (countSite == 1) {
								LegacyOrganisationSites los = legacyOrganisationSitesService.findBySdlNumber(legacySkillsProgramme.getProviderSDL().trim());
								if (los != null && los.getId() != null) {
									// legacySkillsProgramme.setLegacyOrganisationSites(los);
								}
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}

					// legacySkillsProgramme.setProcessed(true);

					updateList.add(legacySkillsProgramme);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacySkillsProgrammeNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Skills Programme Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Skills Programme Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
	
	public void legacyAccreditationUpdate(String levyNumber, String accreditationNumber) throws Exception {
		List<LegacySkillsProgramme> list = dao.findByProviderSDL(levyNumber);
		if(list.size() > 0) {
			List<IDataEntity> updateList = new ArrayList<>();
			for(LegacySkillsProgramme legacySkillsProgramme :list) {
				legacySkillsProgramme.setAccreditationNumber(accreditationNumber);
				updateList.add(legacySkillsProgramme);
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
			Integer count = countAllLegacyLearnershipNoQualification();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacySkillsProgramme> li = allLegacyLearnershipNoQualification(1000);
				for (LegacySkillsProgramme entry : li) {
					if (entry.getsProgrammeCode() != null && !entry.getsProgrammeCode().isEmpty()) {
						try {
							SkillsProgram skillsProgram = SkillsProgramService.instance().findByProgrammeID(entry.getsProgrammeCode());
							if (skillsProgram != null) {
								entry.setSkillsProgram(skillsProgram);
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
	
	private List<LegacySkillsProgramme> allLegacyLearnershipNoQualification(int numberOfEntries) throws Exception {
		return dao.allLegacyLearnershipNoQualification(numberOfEntries);
	}
	
	private Integer countAllLegacyLearnershipNoQualification() throws Exception {
		return dao.countAllLegacyLearnershipNoQualification();
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
	
	private List<LegacySkillsProgramme> allLegacyLearnershipNoQualification() throws Exception {
		return dao.allLegacyLearnershipNoQualification();
	}
	public void runSkillsSetValidiations() throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		updateList = new ArrayList<>();
		List<LegacySkillsProgramme> li = allLegacyLearnershipNoQualification();
		for (LegacySkillsProgramme entry : li) {
			if (entry.getsProgrammeCode() != null && !entry.getsProgrammeCode().isEmpty()) {
				SkillsSet skillsSet = SkillsSetService.instance().findByProgrammeID(entry.getsProgrammeCode());
				if (skillsSet != null) {
					entry.setSkillsSet(skillsSet);
					updateList.add(entry);
				}
			}
		}
		dao.updateBatch(updateList);
	}
}
