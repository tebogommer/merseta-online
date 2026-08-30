package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SetmisFile200ExtractedDAO;
import haj.com.dataextract.bean.SETMISFile200Bean;
import haj.com.entity.SetmisFile200Extracted;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.CSVUtil;

public class SetmisFile200ExtractedService extends AbstractService {
	/** The dao. */
	private SetmisFile200ExtractedDAO dao = new SetmisFile200ExtractedDAO();

	/**
	 * Get all SetmisFile200Extracted
	 * 
	 * @author TechFinium
	 * @see SetmisFile200Extracted
	 * @return a list of {@link haj.com.entity.SetmisFile200Extracted}
	 * @throws Exception
	 *             the exception
	 */
	public List<SetmisFile200Extracted> allSetmisFile200Extracted() throws Exception {
		return dao.allSetmisFile200Extracted();
	}

	/**
	 * Create or update SetmisFile200Extracted.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile200Extracted
	 */
	public void create(SetmisFile200Extracted entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update SetmisFile200Extracted.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile200Extracted
	 */
	public void update(SetmisFile200Extracted entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SetmisFile200Extracted.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile200Extracted
	 */
	public void delete(SetmisFile200Extracted entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SetmisFile200Extracted}
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile200Extracted
	 */
	public SetmisFile200Extracted findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find SetmisFile200Extracted by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SetmisFile200Extracted}
	 * @throws Exception
	 *             the exception
	 * @see SetmisFile200Extracted
	 */
	public List<SetmisFile200Extracted> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SetmisFile200Extracted
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SetmisFile200Extracted}
	 * @throws Exception
	 *             the exception
	 */
	public List<SetmisFile200Extracted> allSetmisFile200Extracted(int first, int pageSize) throws Exception {
		return dao.allSetmisFile200Extracted(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SetmisFile200Extracted for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the SetmisFile200Extracted
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SetmisFile200Extracted.class);
	}

	/**
	 * Lazy load SetmisFile200Extracted with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            SetmisFile200Extracted class
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
	 * @return a list of {@link haj.com.entity.SetmisFile200Extracted}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SetmisFile200Extracted> allSetmisFile200Extracted(Class<SetmisFile200Extracted> class1, int first,
			int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SetmisFile200Extracted>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of SetmisFile200Extracted for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            SetmisFile200Extracted class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SetmisFile200Extracted entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SetmisFile200Extracted> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	// service level
	private SETMISService setmisService = new SETMISService();

	public void deleteAllEntries() throws Exception {
		List<IDataEntity> deleteList = new ArrayList<>();
		deleteList.addAll(allSetmisFile200Extracted());
		if (deleteList.size() > 0) {
			dao.deleteBatch(deleteList);
		}
	}

	public void populateEntity() throws Exception {
		deleteAllEntries();
		List<SETMISFile200Bean> beanResultList = setmisService.extractSETMISFile200Bean();
		List<IDataEntity> createList = new ArrayList<IDataEntity>();
		if (beanResultList.size() > 0) {
			int lineNumber = 1;
			for (SETMISFile200Bean beanEntry : beanResultList) {
				SetmisFile200Extracted newEntry = new SetmisFile200Extracted();
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

	private void populateAdditionalInfoImport(SetmisFile200Extracted extractedEntry, SETMISFile200Bean bean, Integer lineNumber) throws Exception {
		// sets lineNumber
		extractedEntry.setLineNumber(lineNumber.toString().trim());

		// sets format
		SimpleDateFormat sdf = new SimpleDateFormat(DataExtractService.QCTO_DATE_FORMAT);
		
		// BigInteger sETAId
		if (bean.getsETAId() != null) {
			extractedEntry.setsETAIdString(bean.getsETAId().toString());
		}
//		private BigInteger employerApprovalStatusId;
		if (bean.getEmployerApprovalStatusId() != null) {
			extractedEntry.setEmployerApprovalStatusIdString(bean.getEmployerApprovalStatusId().toString());
		}
//		private Date employerApprovalStatusStartDate;
		if (bean.getEmployerApprovalStatusStartDate() != null) {
			extractedEntry.setEmployerApprovalStatusStartDateString(sdf.format(bean.getEmployerApprovalStatusStartDate()));
		}
//		private Date dateStamp;
		if (bean.getDateStamp() != null) {
			extractedEntry.setDateStampString(sdf.format(bean.getDateStamp()));
		}
	}

	public void downloadData() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String currentDate = sdf.format(new Date());
		List<SetmisFile200Extracted> allData = allSetmisFile200Extracted();
		String csv = CSVUtil.writeFixedLength(allData);
		String fileName = "MERS_0006_200_v001_" + currentDate + ".dat";
		JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), fileName, "text/plain");
	}

}
