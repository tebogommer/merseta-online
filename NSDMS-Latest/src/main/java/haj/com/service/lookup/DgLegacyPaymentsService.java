package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.DgLegacyPaymentsDAO;
import haj.com.entity.lookup.DgLegacyPayments;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class DgLegacyPaymentsService extends AbstractService {
	
	
	/** The dao. */
	private DgLegacyPaymentsDAO dao = new DgLegacyPaymentsDAO();

	/**
	 * Get all DgLegacyPayments
 	 * @author TechFinium 
 	 * @see   DgLegacyPayments
 	 * @return a list of {@link haj.com.entity.DgLegacyPayments}
	 * @throws Exception the exception
 	 */
	public List<DgLegacyPayments> allDgLegacyPayments() throws Exception {
	  	return dao.allDgLegacyPayments();
	}


	/**
	 * Create or update DgLegacyPayments.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DgLegacyPayments
	 */
	public void create(DgLegacyPayments entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DgLegacyPayments.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DgLegacyPayments
	 */
	public void update(DgLegacyPayments entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DgLegacyPayments.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DgLegacyPayments
	 */
	public void delete(DgLegacyPayments entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DgLegacyPayments}
	 * @throws Exception the exception
	 * @see    DgLegacyPayments
	 */
	public DgLegacyPayments findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DgLegacyPayments by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DgLegacyPayments}
	 * @throws Exception the exception
	 * @see    DgLegacyPayments
	 */
	public List<DgLegacyPayments> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DgLegacyPayments
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DgLegacyPayments}
	 * @throws Exception the exception
	 */
	public List<DgLegacyPayments> allDgLegacyPayments(int first, int pageSize) throws Exception {
		return dao.allDgLegacyPayments(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DgLegacyPayments for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the DgLegacyPayments
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DgLegacyPayments.class);
	}
	
    /**
     * Lazy load DgLegacyPayments with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 DgLegacyPayments class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DgLegacyPayments} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DgLegacyPayments> allDgLegacyPayments(Class<DgLegacyPayments> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<DgLegacyPayments>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of DgLegacyPayments for lazy load with filters
     * @author TechFinium 
     * @param entity DgLegacyPayments class
     * @param filters the filters
     * @return Number of rows in the DgLegacyPayments entity
     * @throws Exception the exception     
     */	
	public int count(Class<DgLegacyPayments> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DgLegacyPayments> allDgLegacyPaymentsBySchemeYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer schemeYear) throws Exception {
		String hql = "select o from DgLegacyPayments o where o.schemeYearConverted = :schemeYear";
		filters.put("schemeYear", schemeYear);
		return ( List<DgLegacyPayments>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql);
	}
	
	public int countAllDgLegacyPaymentsBySchemeYear(Class<DgLegacyPayments> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from DgLegacyPayments o where o.schemeYearConverted = :schemeYear";
		return  dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<DgLegacyPayments> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<DgLegacyPayments> allEntries = allDgLegacyPayments();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public void runValidiationsThread() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					runValidiations();
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}).start();
	}
	
	public void runValidiations() throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		List<DgLegacyPayments> link = allDgLegacyPayments();
		for (DgLegacyPayments entity : link) {
			// Dates
			try {
				// Date Format dd/MM/yyyy
				if (entity.getPostingDate() != null && !entity.getPostingDate().trim().isEmpty()) {
					entity.setPostingDateConverted(HAJConstants.sdfDDMMYYYY2.parse(entity.getPostingDate().trim()));
				}
			} catch (Exception e) {
				logger.fatal(e);
			}
			try {
				// Date Format dd/MM/yyyy
				if (entity.getDocumentDate() != null && !entity.getDocumentDate().trim().isEmpty()) {
					entity.setDocumentDateConverted(HAJConstants.sdfDDMMYYYY2.parse(entity.getDocumentDate().trim()));
				}
			} catch (Exception e) {
				logger.fatal(e);
			}
			try {
				// Date Format dd/MM/yyyy
				if (entity.getPostedDate() != null && !entity.getPostedDate().trim().isEmpty()) {
					entity.setPostedDateConverted(HAJConstants.sdfDDMMYYYY2.parse(entity.getPostedDate().trim()));
				}
			} catch (Exception e) {
				logger.fatal(e);
			}
			
			try {
				if (entity.getSchemeYear() != null && !entity.getSchemeYear().trim().isEmpty()) {
					entity.setSchemeYearConverted(Integer.valueOf(entity.getSchemeYear().trim()));
				}
			} catch (Exception e) {
				logger.fatal(e);
			}
			try {
				if (entity.getDocumentAmount() != null && !entity.getDocumentAmount().trim().isEmpty()) {
					entity.setDocumentAmountConverted(Double.valueOf(entity.getDocumentAmount().trim()));
				}
			} catch (Exception e) {
				logger.fatal(e);
			}
			try {
				if (entity.getCurrentTrxAmount() != null && !entity.getCurrentTrxAmount().trim().isEmpty()) {
					entity.setCurrentTrxAmountConverted(Double.valueOf(entity.getCurrentTrxAmount().trim()));
				}
			} catch (Exception e) {
				logger.fatal(e);
			}
			updateList.add(entity);
		}
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
	}
	
	public void runValidiationForEntry(DgLegacyPayments entity) throws Exception {
		try {
			// Date Format dd/MM/yyyy
			if (entity.getPostingDate() != null && !entity.getPostingDate().trim().isEmpty()) {
				entity.setPostingDateConverted(HAJConstants.sdfDDMMYYYY2.parse(entity.getPostingDate().trim()));
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
		try {
			// Date Format dd/MM/yyyy
			if (entity.getDocumentDate() != null && !entity.getDocumentDate().trim().isEmpty()) {
				entity.setDocumentDateConverted(HAJConstants.sdfDDMMYYYY2.parse(entity.getDocumentDate().trim()));
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
		try {
			// Date Format dd/MM/yyyy
			if (entity.getPostedDate() != null && !entity.getPostedDate().trim().isEmpty()) {
				entity.setPostedDateConverted(HAJConstants.sdfDDMMYYYY2.parse(entity.getPostedDate().trim()));
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
		try {
			if (entity.getSchemeYear() != null && !entity.getSchemeYear().trim().isEmpty()) {
				entity.setSchemeYearConverted(Integer.valueOf(entity.getSchemeYear().trim()));
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
		try {
			if (entity.getDocumentAmount() != null && !entity.getDocumentAmount().trim().isEmpty()) {
				entity.setDocumentAmountConverted(Double.valueOf(entity.getDocumentAmount().trim()));
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
		try {
			if (entity.getCurrentTrxAmount() != null && !entity.getCurrentTrxAmount().trim().isEmpty()) {
				entity.setCurrentTrxAmountConverted(Double.valueOf(entity.getCurrentTrxAmount().trim()));
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
		update(entity);
	}
}
