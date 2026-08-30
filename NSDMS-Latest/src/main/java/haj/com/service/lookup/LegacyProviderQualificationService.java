package haj.com.service.lookup;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.LegacyProviderQualificationDAO;
import haj.com.dao.lookup.ResolveByCodeLookupDAO;
import haj.com.entity.lookup.LegacyProviderQualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class LegacyProviderQualificationService extends AbstractService {
	/** The dao. */
	private LegacyProviderQualificationDAO dao = new LegacyProviderQualificationDAO();

	/** Other DAO */
	private ResolveByCodeLookupDAO resolveByCodeLookupDAO = new ResolveByCodeLookupDAO();
	
	
	
	/**
	 * Get all LegacyProviderQualification
 	 * @author TechFinium 
 	 * @see   LegacyProviderQualification
 	 * @return a list of {@link haj.com.entity.LegacyProviderQualification}
	 * @throws Exception the exception
 	 */
	
	private static LegacyProviderQualificationService legacyProviderQualificationService;
	public static synchronized LegacyProviderQualificationService instance() {
		if (legacyProviderQualificationService == null) {
			legacyProviderQualificationService = new LegacyProviderQualificationService();
		}
		return legacyProviderQualificationService;
	}
	
	public List<LegacyProviderQualification> allLegacyProviderQualification() throws Exception {
	  	return dao.allLegacyProviderQualification();
	}


	/**
	 * Create or update LegacyProviderQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LegacyProviderQualification
	 */
	public void create(LegacyProviderQualification entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LegacyProviderQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyProviderQualification
	 */
	public void update(LegacyProviderQualification entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LegacyProviderQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyProviderQualification
	 */
	public void delete(LegacyProviderQualification entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyProviderQualification}
	 * @throws Exception the exception
	 * @see    LegacyProviderQualification
	 */
	public LegacyProviderQualification findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LegacyProviderQualification by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyProviderQualification}
	 * @throws Exception the exception
	 * @see    LegacyProviderQualification
	 */
	public List<LegacyProviderQualification> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LegacyProviderQualification
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyProviderQualification}
	 * @throws Exception the exception
	 */
	public List<LegacyProviderQualification> allLegacyProviderQualification(int first, int pageSize) throws Exception {
		return dao.allLegacyProviderQualification(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LegacyProviderQualification for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the LegacyProviderQualification
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LegacyProviderQualification.class);
	}
	
    /**
     * Lazy load LegacyProviderQualification with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 LegacyProviderQualification class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LegacyProviderQualification} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderQualification> allLegacyProviderQualification(Class<LegacyProviderQualification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<LegacyProviderQualification>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of LegacyProviderQualification for lazy load with filters
     * @author TechFinium 
     * @param entity LegacyProviderQualification class
     * @param filters the filters
     * @return Number of rows in the LegacyProviderQualification entity
     * @throws Exception the exception     
     */	
	public int count(Class<LegacyProviderQualification> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
	    List<LegacyProviderQualification> allEntries = allLegacyProviderQualification();
	    dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyProviderQualification> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	
	public List<LegacyProviderQualification> findBySldNoAndQualNotNull(String sldNo) throws Exception {
	 	return resolveQualExpiryDate(dao.findBySldNoAndQualNotNull(sldNo));
	}
	
	public List<LegacyProviderQualification> findByAccreditationNoAndQualNotNull(String accreditationNo) throws Exception {
	 	return resolveQualExpiryDate(dao.findByAccreditationNoAndQualNotNull(accreditationNo));
	}
	
	public List<LegacyProviderQualification> resolveQualExpiryDate(List<LegacyProviderQualification> list) {
		if (list != null && list.size() > 0) {
			for (LegacyProviderQualification qual : list) {
				if (qual.getQualification() != null && qual.getQualification().getLastDateForEnrolment() != null) {
					if (qual.getQualification().getLastDateForEnrolment().before(new Date())) {
						qual.setQualificationExpired(true);
					} else {
						qual.setQualificationExpired(false);
					}
				}
			}
		}

		return list;
	}

	public LegacyProviderQualification processSingleEntry(LegacyProviderQualification entry) throws Exception{
		/*
		 * Set: linkedAccreditationNumberOnProviderAccreditation
		 * Method: ResolveByCodeLookupDAO checkLegacyProviderAccreditation
		 * Param: accreditationNo
		 */
		try {
			entry.setLinkedAccreditationNumberOnProviderAccreditation(resolveByCodeLookupDAO.checkLegacyProviderAccreditation(entry.getAccreditationNo()));
		} catch (Exception e) {
			entry.setLinkedAccreditationNumberOnProviderAccreditation(false);
		}
		
		
		try {
			entry.setQualification(resolveByCodeLookupDAO.findQualification(Integer.valueOf(entry.getQualCode())));
		} catch (Exception e) {
			entry.setQualification(null);
		}
		
		try {
			if (entry.getQualification() != null && entry.getQualification().getLastDateForEnrolment() != null) {
				if (entry.getQualification().getLastDateForEnrolment().before(new Date())) {
					entry.setQualificationExpired(true);
				} else {
					entry.setQualificationExpired(false);
				}
			}
		} catch (Exception e) {
			entry.setQualificationExpired(true);
		}
		
		return entry;
	}
	
	
}
