package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SetmisFile400ExtractedDAO;
import haj.com.dataextract.bean.SETMISFile400Bean;
import haj.com.entity.SetmisFile400Extracted;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.CSVUtil;

public class SetmisFile400ExtractedService extends AbstractService {
	/** The dao. */
	private SetmisFile400ExtractedDAO dao = new SetmisFile400ExtractedDAO();

	/**
	 * Get all SetmisFile400Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile400Extracted
	 * @return a list of {@link haj.com.entity.SetmisFile400Extracted}
	 * @throws Exception
	 *             the exception
	 */
	public List<SetmisFile400Extracted> allSetmisFile400Extracted() throws Exception {
		return dao.allSetmisFile400Extracted();
	}

	/**
	 * Create or update SetmisFile400Extracted.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile400Extracted
	 */
	public void create(SetmisFile400Extracted entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update SetmisFile400Extracted.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile400Extracted
	 */
	public void update(SetmisFile400Extracted entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SetmisFile400Extracted.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile400Extracted
	 */
	public void delete(SetmisFile400Extracted entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SetmisFile400Extracted}
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile400Extracted
	 */
	public SetmisFile400Extracted findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find SetmisFile400Extracted by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SetmisFile400Extracted}
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile400Extracted
	 */
	public List<SetmisFile400Extracted> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SetmisFile400Extracted
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SetmisFile400Extracted}
	 * @throws Exception
	 *             the exception
	 */
	public List<SetmisFile400Extracted> allSetmisFile400Extracted(int first, int pageSize) throws Exception {
		return dao.allSetmisFile400Extracted(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SetmisFile400Extracted for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the SetmisFile400Extracted
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SetmisFile400Extracted.class);
	}

	/**
	 * Lazy load SetmisFile400Extracted with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            SetmisFile400Extracted class
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
	 * @return a list of {@link haj.com.entity.SetmisFile400Extracted}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SetmisFile400Extracted> allSetmisFile400Extracted(Class<SetmisFile400Extracted> class1, int first,
			int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SetmisFile400Extracted>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of SetmisFile400Extracted for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            SetmisFile400Extracted class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SetmisFile400Extracted entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SetmisFile400Extracted> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	// service level
	private SETMISService setmisService = new SETMISService();

	public void deleteAllEntries() throws Exception {
		List<IDataEntity> deleteList = new ArrayList<>();
		deleteList.addAll(allSetmisFile400Extracted());
		if (deleteList.size() > 0) {
			dao.deleteBatch(deleteList);
		}
	}

	public void populateEntity() throws Exception {
		deleteAllEntries();
		List<SETMISFile400Bean> beanResultList = setmisService.extractSETMISFile400Bean();
		List<IDataEntity> createList = new ArrayList<IDataEntity>();
		if (beanResultList.size() > 0) {
			int lineNumber = 1;
			for (SETMISFile400Bean beanEntry : beanResultList) {
				SetmisFile400Extracted newEntry = new SetmisFile400Extracted();
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

	private void populateAdditionalInfoImport(SetmisFile400Extracted extractedEntry, SETMISFile400Bean bean, Integer lineNumber) throws Exception {
		// sets lineNumber
		extractedEntry.setLineNumber(lineNumber.toString().trim());

		// sets format
		SimpleDateFormat sdf = new SimpleDateFormat(DataExtractService.QCTO_DATE_FORMAT);
		
//		private Date personBirthDate;
		if (bean.getPersonBirthDate() != null) {
			extractedEntry.setPersonBirthDateString(sdf.format(bean.getPersonBirthDate()));
		}
//		private Date lastSchoolYear;
		if (bean.getLastSchoolYear() != null) {
			extractedEntry.setLastSchoolYearString(sdf.format(bean.getLastSchoolYear()));
		}
//		private Date pOPIActStatusDate;
		if (bean.getpOPIActStatusDate() != null) {
			extractedEntry.setpOPIActStatusDateString(sdf.format(bean.getpOPIActStatusDate()));
		}
//		private Date dateStamp;
		if (bean.getDateStamp() != null) {
			extractedEntry.setDateStampString(sdf.format(bean.getDateStamp()));
		}
	}

	public void downloadData() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDate = sdf.format(new Date());
		List<SetmisFile400Extracted> allData = allSetmisFile400Extracted();
		String csv = CSVUtil.writeFixedLength(allData);
		String fileName = "MERS_0006_400_v001_" + currentDate + ".dat";
		JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), fileName, "text/plain");
	}

}
