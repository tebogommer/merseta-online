package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.AppraisalChecklistDAO;
import haj.com.entity.lookup.AppraisalChecklist;
import haj.com.entity.lookup.Appraisals;
import haj.com.framework.AbstractService;

public class AppraisalChecklistService extends AbstractService {
	/** The dao. */
	private AppraisalChecklistDAO dao = new AppraisalChecklistDAO();

	/**
	 * Get all AppraisalChecklist
 	 * @author TechFinium 
 	 * @see   AppraisalChecklist
 	 * @return a list of {@link haj.com.entity.AppraisalChecklist}
	 * @throws Exception the exception
 	 */
	public List<AppraisalChecklist> allAppraisalChecklist() throws Exception {
	  	return dao.allAppraisalChecklist();
	}


	/**
	 * Create or update AppraisalChecklist.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     AppraisalChecklist
	 */
	public void create(AppraisalChecklist entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  AppraisalChecklist.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AppraisalChecklist
	 */
	public void update(AppraisalChecklist entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  AppraisalChecklist.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AppraisalChecklist
	 */
	public void delete(AppraisalChecklist entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AppraisalChecklist}
	 * @throws Exception the exception
	 * @see    AppraisalChecklist
	 */
	public AppraisalChecklist findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find AppraisalChecklist by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AppraisalChecklist}
	 * @throws Exception the exception
	 * @see    AppraisalChecklist
	 */
	public List<AppraisalChecklist> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	
	/**
	 * Find AppraisalChecklist by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AppraisalChecklist}
	 * @throws Exception the exception
	 * @see    AppraisalChecklist
	 */
	public List<AppraisalChecklist> findAppraisalCategoryCodeByAppraisal(Appraisals appraisal) throws Exception {
		return dao.findAppraisalCategoryCodeByAppraisal(appraisal.getId());
	}
	
	
	
	
	/**
	 * Lazy load AppraisalChecklist
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.AppraisalChecklist}
	 * @throws Exception the exception
	 */
	public List<AppraisalChecklist> allAppraisalChecklist(int first, int pageSize) throws Exception {
		return dao.allAppraisalChecklist(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of AppraisalChecklist for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the AppraisalChecklist
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(AppraisalChecklist.class);
	}
	
    /**
     * Lazy load AppraisalChecklist with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 AppraisalChecklist class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.AppraisalChecklist} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<AppraisalChecklist> allAppraisalChecklist(Class<AppraisalChecklist> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<AppraisalChecklist>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of AppraisalChecklist for lazy load with filters
     * @author TechFinium 
     * @param entity AppraisalChecklist class
     * @param filters the filters
     * @return Number of rows in the AppraisalChecklist entity
     * @throws Exception the exception     
     */	
	public int count(Class<AppraisalChecklist> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
