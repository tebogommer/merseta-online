package haj.com.service;

import java.util.List;

import haj.com.dao.LevyDetailMgPaymentsAlterAuditDAO;
import haj.com.entity.LevyDetailMgPaymentsAlterAudit;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import haj.com.sars.SarsFiles;
import haj.com.sars.SarsLevyDetails;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class LevyDetailMgPaymentsAlterAuditService extends AbstractService {
	
	/** The dao. */
	private LevyDetailMgPaymentsAlterAuditDAO dao = new LevyDetailMgPaymentsAlterAuditDAO();

	/* Service levels */
	private UsersService usersService = new UsersService();
	private SarsFilesService sarsFilesService;
	private SarsLevyDetailsService sarsLevyDetailsService;
	
	
	/**
	 * Get all LevyDetailMgPaymentsAlterAudit
 	 * @author TechFinium 
 	 * @see   LevyDetailMgPaymentsAlterAudit
 	 * @return a list of {@link haj.com.entity.LevyDetailMgPaymentsAlterAudit}
	 * @throws Exception the exception
 	 */
	public List<LevyDetailMgPaymentsAlterAudit> allLevyDetailMgPaymentsAlterAudit() throws Exception {
	  	return dao.allLevyDetailMgPaymentsAlterAudit();
	}


	/**
	 * Create or update LevyDetailMgPaymentsAlterAudit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LevyDetailMgPaymentsAlterAudit
	 */
	public void create(LevyDetailMgPaymentsAlterAudit entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LevyDetailMgPaymentsAlterAudit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LevyDetailMgPaymentsAlterAudit
	 */
	public void update(LevyDetailMgPaymentsAlterAudit entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LevyDetailMgPaymentsAlterAudit.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LevyDetailMgPaymentsAlterAudit
	 */
	public void delete(LevyDetailMgPaymentsAlterAudit entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LevyDetailMgPaymentsAlterAudit}
	 * @throws Exception the exception
	 * @see    LevyDetailMgPaymentsAlterAudit
	 */
	public LevyDetailMgPaymentsAlterAudit findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LevyDetailMgPaymentsAlterAudit by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LevyDetailMgPaymentsAlterAudit}
	 * @throws Exception the exception
	 * @see    LevyDetailMgPaymentsAlterAudit
	 */
	public List<LevyDetailMgPaymentsAlterAudit> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LevyDetailMgPaymentsAlterAudit
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LevyDetailMgPaymentsAlterAudit}
	 * @throws Exception the exception
	 */
	public List<LevyDetailMgPaymentsAlterAudit> allLevyDetailMgPaymentsAlterAudit(int first, int pageSize) throws Exception {
		return dao.allLevyDetailMgPaymentsAlterAudit(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LevyDetailMgPaymentsAlterAudit for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the LevyDetailMgPaymentsAlterAudit
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LevyDetailMgPaymentsAlterAudit.class);
	}
	
    /**
     * Lazy load LevyDetailMgPaymentsAlterAudit with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 LevyDetailMgPaymentsAlterAudit class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LevyDetailMgPaymentsAlterAudit} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LevyDetailMgPaymentsAlterAudit> allLevyDetailMgPaymentsAlterAudit(Class<LevyDetailMgPaymentsAlterAudit> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList((List<LevyDetailMgPaymentsAlterAudit>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters));
	
	}
	
    /**
     * Get count of LevyDetailMgPaymentsAlterAudit for lazy load with filters
     * @author TechFinium 
     * @param entity LevyDetailMgPaymentsAlterAudit class
     * @param filters the filters
     * @return Number of rows in the LevyDetailMgPaymentsAlterAudit entity
     * @throws Exception the exception     
     */	
	public int count(Class<LevyDetailMgPaymentsAlterAudit> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<LevyDetailMgPaymentsAlterAudit> populateAdditionalInformationList(List<LevyDetailMgPaymentsAlterAudit> entityList) throws Exception {
		for (LevyDetailMgPaymentsAlterAudit entity : entityList) {
			populateAdditionalInformation(entity);
		}
		return entityList;
	}


	public LevyDetailMgPaymentsAlterAudit populateAdditionalInformation(LevyDetailMgPaymentsAlterAudit entity) throws Exception {
		
		if (entity.getSarsFile() != null && entity.getSarsFile().getId() != null) {
			if (sarsFilesService == null) {
				sarsFilesService = new SarsFilesService();
			}
			entity.setSarsFile(sarsFilesService.findByKey(entity.getSarsFile().getId()));
		}
		if (entity.getSarsLevyDetails() != null && entity.getSarsLevyDetails().getId() != null) {
			if (sarsLevyDetailsService == null) {
				sarsLevyDetailsService = new SarsLevyDetailsService();
			}
			entity.setSarsLevyDetails(sarsLevyDetailsService.populateAdditionalInformation(sarsLevyDetailsService.findByKey(entity.getSarsLevyDetails().getId())));
		}
		
		return entity;
	}


	public void createAudit(Users activeUser, SarsLevyDetails sarsLevyDetails, SarsFiles sarsFile) throws Exception{
		LevyDetailMgPaymentsAlterAudit newAudit = new LevyDetailMgPaymentsAlterAudit();
		newAudit.setSarsFile(sarsFile);
		newAudit.setSarsLevyDetails(sarsLevyDetails);
		newAudit.setUsers(activeUser);
		newAudit.setMgCanProcess(sarsLevyDetails.getCanAppearOnMgPayments());
		create(newAudit);
	}
}
