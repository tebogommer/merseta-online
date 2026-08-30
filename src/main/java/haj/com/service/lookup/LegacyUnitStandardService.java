package haj.com.service.lookup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import haj.com.bean.LegacyDataReportBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyUnitStandardDAO;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.JasperService;
import haj.com.service.UnitStandardsService;
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

public class LegacyUnitStandardService extends AbstractService {
	/** The dao. */
	private LegacyUnitStandardDAO dao = new LegacyUnitStandardDAO();

	
	private static LegacyUnitStandardService legacyUnitStandardService = null;
	public static synchronized LegacyUnitStandardService instance() {
		if (legacyUnitStandardService == null) {
			legacyUnitStandardService = new LegacyUnitStandardService();
		}
		return legacyUnitStandardService;
	}
	/**
	 * Get all LegacyUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyUnitStandard
	 * @return a list of {@link haj.com.entity.LegacyUnitStandard}
	 * @throws Exception the exception
	 */
	public List<LegacyUnitStandard> allLegacyUnitStandard() throws Exception {
		return dao.allLegacyUnitStandard();
	}

	/**
	 * Create or update LegacyUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyUnitStandard
	 */
	public void create(LegacyUnitStandard entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyUnitStandard
	 */
	public void update(LegacyUnitStandard entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyUnitStandard
	 */
	public void delete(LegacyUnitStandard entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyUnitStandard}
	 * @throws Exception the exception
	 * @see LegacyUnitStandard
	 */
	public LegacyUnitStandard findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyUnitStandard by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyUnitStandard}
	 * @throws Exception the exception
	 * @see LegacyUnitStandard
	 */
	public List<LegacyUnitStandard> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyUnitStandard
	 * 
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyUnitStandard}
	 * @throws Exception the exception
	 */
	public List<LegacyUnitStandard> allLegacyUnitStandard(int first, int pageSize) throws Exception {
		return dao.allLegacyUnitStandard(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyUnitStandard for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyUnitStandard
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyUnitStandard.class);
	}

	/**
	 * Lazy load LegacyUnitStandard with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1    LegacyUnitStandard class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.LegacyUnitStandard} containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandard> allLegacyUnitStandard(Class<LegacyUnitStandard> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyUnitStandard>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of LegacyUnitStandard for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity  LegacyUnitStandard class
	 * @param filters the filters
	 * @return Number of rows in the LegacyUnitStandard entity
	 * @throws Exception the exception
	 */
	public int count(Class<LegacyUnitStandard> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandard> allLegacyUnitStandardByProviderAccreditationFlatId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long keyValue) throws Exception {
		String hql = "select o from LegacyUnitStandard o where o.legacyProviderAccreditationFlatId = :keyValue";
		filters.put("keyValue", keyValue);
		return (List<LegacyUnitStandard>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllLegacyUnitStandardByProviderAccreditationFlatId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyUnitStandard o where o.legacyProviderAccreditationFlatId = :keyValue";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandard> allLegacyUnitStandardByProviderAccreditationUnitStandardFlatId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long keyValue) throws Exception {
		String hql = "select o from LegacyUnitStandard o where o.legacyProviderAccreditationUnitStandardFlatId = :keyValue";
		filters.put("keyValue", keyValue);
		return (List<LegacyUnitStandard>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllLegacyUnitStandardByProviderAccreditationUnitStandardFlatId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyUnitStandard o where olegacyProviderAccreditationUnitStandardFlatId. = :keyValue";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyUnitStandard> allEntries = allLegacyUnitStandard();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyUnitStandard> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	public List<LegacyUnitStandard> findByIdNumberAndCode(String idNumber, String UnitStandardCode) {
		return dao.findByIdNumberAndCode(idNumber, UnitStandardCode);
	}
	public void legacyAccreditationUpdate(String levyNumber, String accreditationNumber) throws Exception {
		List<LegacyUnitStandard> list = dao.findByProviderSDL(levyNumber);
		if(list.size() > 0) {
			List<IDataEntity> updateList = new ArrayList<>();
			for(LegacyUnitStandard legacySkillsProgramme :list) {
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
				List<LegacyUnitStandard> li = allLegacyLearnershipNoQualification(1000);
				for (LegacyUnitStandard entry : li) {
					if (entry.getUnitStdCode() != null && !entry.getUnitStdCode().isEmpty()) {
						try {
							UnitStandards unitStandard = UnitStandardsService.instance().findByUnitStandartId(Integer.parseInt(entry.getUnitStdCode()));
							if (unitStandard != null) {
								entry.setUnitStandard(unitStandard);
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
	private List<LegacyUnitStandard> allLegacyLearnershipNoQualification(int numberOfEntries) throws Exception {
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
			String fileName = "Legacy Unit Standard Report.xlsx";
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
			String fileName = "Legacy Unit Standard Report.xlsx";
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
		populateDataForReportAccreditationNo(data, counter, accreditationNumber);
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
			String fileName = "Legacy Unit Standard Report.xlsx";
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
	
	private void populateDataForReportAccreditationNo(Map<String, Object[]> data, Integer counter, String accreditationNumber) throws Exception{
		int counterForPopulation = counter;
		List<LegacyDataReportBean> resultsList = populateReportAccreditaionNo(accreditationNumber);
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
	
	public List<LegacyDataReportBean> populateReportAccreditaionNo(String accreditationNumber) throws Exception {
		return dao.populateReportAccreditaionNo(accreditationNumber);
	}
}
