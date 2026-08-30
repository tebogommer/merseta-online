package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SetmisFile401ExtractedDAO;
import haj.com.dataextract.bean.SETMISFile401Bean;
import haj.com.entity.SetmisFile401Extracted;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.CSVUtil;

public class SetmisFile401ExtractedService extends AbstractService {
	/** The dao. */
	private SetmisFile401ExtractedDAO dao = new SetmisFile401ExtractedDAO();

	/**
	 * Get all SetmisFile401Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile401Extracted
	 * @return a list of {@link haj.com.entity.SetmisFile401Extracted}
	 * @throws Exception
	 *             the exception
	 */
	public List<SetmisFile401Extracted> allSetmisFile401Extracted() throws Exception {
		return dao.allSetmisFile401Extracted();
	}

	/**
	 * Create or update SetmisFile401Extracted.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile401Extracted
	 */
	public void create(SetmisFile401Extracted entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update SetmisFile401Extracted.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile401Extracted
	 */
	public void update(SetmisFile401Extracted entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SetmisFile401Extracted.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile401Extracted
	 */
	public void delete(SetmisFile401Extracted entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SetmisFile401Extracted}
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile401Extracted
	 */
	public SetmisFile401Extracted findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find SetmisFile401Extracted by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SetmisFile401Extracted}
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile401Extracted
	 */
	public List<SetmisFile401Extracted> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SetmisFile401Extracted
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SetmisFile401Extracted}
	 * @throws Exception
	 *             the exception
	 */
	public List<SetmisFile401Extracted> allSetmisFile401Extracted(int first, int pageSize) throws Exception {
		return dao.allSetmisFile401Extracted(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SetmisFile401Extracted for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the SetmisFile401Extracted
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SetmisFile401Extracted.class);
	}

	/**
	 * Lazy load SetmisFile401Extracted with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            SetmisFile401Extracted class
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
	 * @return a list of {@link haj.com.entity.SetmisFile401Extracted}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SetmisFile401Extracted> allSetmisFile401Extracted(Class<SetmisFile401Extracted> class1, int first,
			int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SetmisFile401Extracted>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of SetmisFile401Extracted for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            SetmisFile401Extracted class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SetmisFile401Extracted entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SetmisFile401Extracted> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	// service level
	private SETMISService setmisService = new SETMISService();

	public void deleteAllEntries() throws Exception {
		List<IDataEntity> deleteList = new ArrayList<>();
		deleteList.addAll(allSetmisFile401Extracted());
		if (deleteList.size() > 0) {
			dao.deleteBatch(deleteList);
		}
	}

	public void populateEntity() throws Exception {
		deleteAllEntries();
		List<SETMISFile401Bean> beanResultList = setmisService.extractSETMISFile401Bean();
		List<IDataEntity> createList = new ArrayList<IDataEntity>();
		if (beanResultList.size() > 0) {
			int lineNumber = 1;
			for (SETMISFile401Bean beanEntry : beanResultList) {
				SetmisFile401Extracted newEntry = new SetmisFile401Extracted();
				org.apache.commons.beanutils.BeanUtils.copyProperties(newEntry, beanEntry);
				populateAdditionalInfoImport(newEntry, beanEntry, lineNumber);
				createList.add(newEntry);
				lineNumber++;
			}
		}
		if (createList.size() > 0) {
			dao.createBatch(createList, 1000);
		}
	}

	private void populateAdditionalInfoImport(SetmisFile401Extracted extractedEntry, SETMISFile401Bean bean, Integer lineNumber) throws Exception {
		// sets lineNumber
		extractedEntry.setLineNumber(lineNumber.toString().trim());

		// sets format
		SimpleDateFormat sdf = new SimpleDateFormat(DataExtractService.QCTO_DATE_FORMAT);
		
//		private Date designationStartDate;
		if (bean.getDesignationStartDate() != null) {
			extractedEntry.setDesignationStartDateString(sdf.format(bean.getDesignationStartDate()));
		}
//		private Date designationEndDate;
		if (bean.getDesignationEndDate() != null) {
			extractedEntry.setDesignationEndDateString(sdf.format(bean.getDesignationEndDate()));
		}
//		private Date dateStamp;
		if (bean.getDateStamp() != null) {
			extractedEntry.setDateStampString(sdf.format(bean.getDateStamp()));
		}
	}

	public void downloadData() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDate = sdf.format(new Date());
		List<SetmisFile401Extracted> allData = allSetmisFile401Extracted();
		String csv = CSVUtil.writeFixedLength(allData);
		String fileName = "MERS_0006_401_v001_" + currentDate + ".dat";
		JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), fileName, "text/plain");
	}

}
