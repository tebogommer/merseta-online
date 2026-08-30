package haj.com.service.lookup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import haj.com.bean.LegacyDataReportBean;
import haj.com.dao.lookup.LegacySectionTwentyEightTradeTestDAO;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacySECTTwentyEight;
import haj.com.entity.lookup.LegacySectionTwentyEightTradeTest;
import haj.com.entity.lookup.LegacyUnitStandardAssessment;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.JasperService;

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

public class LegacySectionTwentyEightTradeTestService extends AbstractService {
	/** The dao. */
	private LegacySectionTwentyEightTradeTestDAO dao = new LegacySectionTwentyEightTradeTestDAO();

	/**
	 * Get all LegacySectionTwentyEightTradeTest
	 * 
	 * @author TechFinium
	 * @see LegacySectionTwentyEightTradeTest
	 * @return a list of {@link haj.com.entity.LegacySectionTwentyEightTradeTest}
	 * @throws Exception the exception
	 */
	public List<LegacySectionTwentyEightTradeTest> allLegacySectionTwentyEightTradeTest() throws Exception {
		return dao.allLegacySectionTwentyEightTradeTest();
	}

	/**
	 * Create or update LegacySectionTwentyEightTradeTest.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacySectionTwentyEightTradeTest
	 */
	public void create(LegacySectionTwentyEightTradeTest entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacySectionTwentyEightTradeTest.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacySectionTwentyEightTradeTest
	 */
	public void update(LegacySectionTwentyEightTradeTest entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacySectionTwentyEightTradeTest.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacySectionTwentyEightTradeTest
	 */
	public void delete(LegacySectionTwentyEightTradeTest entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacySectionTwentyEightTradeTest}
	 * @throws Exception the exception
	 * @see LegacySectionTwentyEightTradeTest
	 */
	public LegacySectionTwentyEightTradeTest findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacySectionTwentyEightTradeTest by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacySectionTwentyEightTradeTest}
	 * @throws Exception the exception
	 * @see LegacySectionTwentyEightTradeTest
	 */
	public List<LegacySectionTwentyEightTradeTest> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacySectionTwentyEightTradeTest
	 * 
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacySectionTwentyEightTradeTest}
	 * @throws Exception the exception
	 */
	public List<LegacySectionTwentyEightTradeTest> allLegacySectionTwentyEightTradeTest(int first, int pageSize)
			throws Exception {
		return dao.allLegacySectionTwentyEightTradeTest(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacySectionTwentyEightTradeTest for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacySectionTwentyEightTradeTest
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacySectionTwentyEightTradeTest.class);
	}

	/**
	 * Lazy load LegacySectionTwentyEightTradeTest with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1    LegacySectionTwentyEightTradeTest class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.LegacySectionTwentyEightTradeTest}
	 *         containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySectionTwentyEightTradeTest> allLegacySectionTwentyEightTradeTest(
			Class<LegacySectionTwentyEightTradeTest> class1, int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacySectionTwentyEightTradeTest>) dao.sortAndFilter(class1, first, pageSize, sortField,
				sortOrder, filters);

	}

	/**
	 * Get count of LegacySectionTwentyEightTradeTest for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity  LegacySectionTwentyEightTradeTest class
	 * @param filters the filters
	 * @return Number of rows in the LegacySectionTwentyEightTradeTest entity
	 * @throws Exception the exception
	 */
	public int count(Class<LegacySectionTwentyEightTradeTest> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacySectionTwentyEightTradeTest> allEntries = allLegacySectionTwentyEightTradeTest();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacySectionTwentyEightTradeTest> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public List<LegacySectionTwentyEightTradeTest> findByLegacySECTTwentyEight(LegacySECTTwentyEight legacySECTTwentyEight) {
		List<LegacySectionTwentyEightTradeTest> list = new  ArrayList<>();
		list = dao.findByIdNo(legacySECTTwentyEight.getIdNo());
		return list;
	}

	public void downloadReport() throws Exception{
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		Integer counter = 1;
		// headers
		data.put(counter.toString(), new Object[] { "Employer Entity ID", "Employer Name", "Employer Trading Name", "First name", "Surname", "RSA ID Number", "Effective Date", "Start Date", "End Date", "Status", "code" , "Title"});
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
			String fileName = "Legacy Section Twenty Eight Trade Test Report.xlsx";
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
	
	private void populateDataReport(Map<String, Object[]> data, LegacyDataReportBean entry, Integer counter) throws Exception{
		data.put(counter.toString(), new Object[] { entry.getEntityId(), entry.getCompanyName(), entry.getTradingName(), entry.getFirstName(), entry.getLastName(), entry.getRsaIdNumber(), entry.getEffectiveDate(), entry.getStartDate() , entry.getEndDate(), entry.getStatus(), entry.getCode(), entry.getTitle()});
	}

	
	public List<LegacyDataReportBean> populateReport() throws Exception {
		return dao.populateReport();
	}
}
