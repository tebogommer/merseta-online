package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SetmisFile304ExtractedDAO;
import haj.com.dataextract.bean.SETMISFile200Bean;
import haj.com.dataextract.bean.SETMISFile304Bean;
import haj.com.entity.SetmisFile200Extracted;
import haj.com.entity.SetmisFile304Extracted;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.CSVUtil;

public class SetmisFile304ExtractedService extends AbstractService {
	/** The dao. */
	private SetmisFile304ExtractedDAO dao = new SetmisFile304ExtractedDAO();

	/**
	 * Get all SetmisFile304Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile304Extracted
	 * @return a list of {@link haj.com.entity.SetmisFile304Extracted}
	 * @throws Exception
	 *             the exception
	 */
	public List<SetmisFile304Extracted> allSetmisFile304Extracted() throws Exception {
		return dao.allSetmisFile304Extracted();
	}

	/**
	 * Create or update SetmisFile304Extracted.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile304Extracted
	 */
	public void create(SetmisFile304Extracted entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update SetmisFile304Extracted.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile304Extracted
	 */
	public void update(SetmisFile304Extracted entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SetmisFile304Extracted.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile304Extracted
	 */
	public void delete(SetmisFile304Extracted entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SetmisFile304Extracted}
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile304Extracted
	 */
	public SetmisFile304Extracted findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find SetmisFile304Extracted by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SetmisFile304Extracted}
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile304Extracted
	 */
	public List<SetmisFile304Extracted> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SetmisFile304Extracted
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SetmisFile304Extracted}
	 * @throws Exception
	 *             the exception
	 */
	public List<SetmisFile304Extracted> allSetmisFile304Extracted(int first, int pageSize) throws Exception {
		return dao.allSetmisFile304Extracted(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SetmisFile304Extracted for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the SetmisFile304Extracted
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SetmisFile304Extracted.class);
	}

	/**
	 * Lazy load SetmisFile304Extracted with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            SetmisFile304Extracted class
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
	 * @return a list of {@link haj.com.entity.SetmisFile304Extracted}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SetmisFile304Extracted> allSetmisFile304Extracted(Class<SetmisFile304Extracted> class1, int first,
			int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SetmisFile304Extracted>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of SetmisFile304Extracted for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            SetmisFile304Extracted class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SetmisFile304Extracted entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SetmisFile304Extracted> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	// service level
	private SETMISService setmisService = new SETMISService();

	public void deleteAllEntries() throws Exception {
		List<IDataEntity> deleteList = new ArrayList<>();
		deleteList.addAll(allSetmisFile304Extracted());
		if (deleteList.size() > 0) {
			dao.deleteBatch(deleteList);
		}
	}

	public void populateEntity() throws Exception {
		deleteAllEntries();
		List<SETMISFile304Bean> beanResultList = setmisService.extractSETMISFile304Bean();
		List<IDataEntity> createList = new ArrayList<IDataEntity>();
		if (beanResultList.size() > 0) {
			int lineNumber = 1;
			for (SETMISFile304Bean beanEntry : beanResultList) {
				SetmisFile304Extracted newEntry = new SetmisFile304Extracted();
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

	private void populateAdditionalInfoImport(SetmisFile304Extracted extractedEntry, SETMISFile304Bean bean, Integer lineNumber) throws Exception {
		// sets lineNumber
		extractedEntry.setLineNumber(lineNumber.toString().trim());

		// sets format
		SimpleDateFormat sdf = new SimpleDateFormat(DataExtractService.QCTO_DATE_FORMAT);
		
//		private Date nonNQFIntervRegStartDate;
		if (bean.getNonNQFIntervRegStartDate() != null) {
			extractedEntry.setNonNQFIntervRegStartDateString(sdf.format(bean.getNonNQFIntervRegStartDate()));
		}
//		private Date nonNQFIntervRegEndDate;
		if (bean.getNonNQFIntervRegEndDate() != null) {
			extractedEntry.setNonNQFIntervRegEndDateString(sdf.format(bean.getNonNQFIntervRegEndDate()));
		}
//		private Date dateStamp;
		if (bean.getDateStamp() != null) {
			extractedEntry.setDateStampString(sdf.format(bean.getDateStamp()));
		}
	}

	public void downloadData() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDate = sdf.format(new Date());
		List<SetmisFile304Extracted> allData = allSetmisFile304Extracted();
		String csv = CSVUtil.writeFixedLength(allData);
		String fileName = "MERS_0006_304_v001_" + currentDate + ".dat";
		JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), fileName, "text/plain");
	}
}
