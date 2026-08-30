package haj.com.service;

import java.util.List;

import haj.com.dao.WorkplaceApprovalSkillsSetDAO;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.WorkplaceApprovalSkillsSet;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.SkillsSetService;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class WorkplaceApprovalSkillsSetService extends AbstractService {
	/** The dao. */
	private WorkplaceApprovalSkillsSetDAO dao = new WorkplaceApprovalSkillsSetDAO();

	/**
	 * Get all WorkplaceApprovalSkillsSet
 	 * @author TechFinium 
 	 * @see   WorkplaceApprovalSkillsSet
 	 * @return a list of {@link haj.com.entity.WorkplaceApprovalSkillsSet}
	 * @throws Exception the exception
 	 */
	public List<WorkplaceApprovalSkillsSet> allWorkplaceApprovalSkillsSet() throws Exception {
	  	return dao.allWorkplaceApprovalSkillsSet();
	}


	/**
	 * Create or update WorkplaceApprovalSkillsSet.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WorkplaceApprovalSkillsSet
	 */
	public void create(WorkplaceApprovalSkillsSet entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WorkplaceApprovalSkillsSet.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceApprovalSkillsSet
	 */
	public void update(WorkplaceApprovalSkillsSet entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WorkplaceApprovalSkillsSet.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceApprovalSkillsSet
	 */
	public void delete(WorkplaceApprovalSkillsSet entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkplaceApprovalSkillsSet}
	 * @throws Exception the exception
	 * @see    WorkplaceApprovalSkillsSet
	 */
	public WorkplaceApprovalSkillsSet findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WorkplaceApprovalSkillsSet by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WorkplaceApprovalSkillsSet}
	 * @throws Exception the exception
	 * @see    WorkplaceApprovalSkillsSet
	 */
	public List<WorkplaceApprovalSkillsSet> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WorkplaceApprovalSkillsSet
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkplaceApprovalSkillsSet}
	 * @throws Exception the exception
	 */
	public List<WorkplaceApprovalSkillsSet> allWorkplaceApprovalSkillsSet(int first, int pageSize) throws Exception {
		return dao.allWorkplaceApprovalSkillsSet(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WorkplaceApprovalSkillsSet for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WorkplaceApprovalSkillsSet
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WorkplaceApprovalSkillsSet.class);
	}
	
    /**
     * Lazy load WorkplaceApprovalSkillsSet with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WorkplaceApprovalSkillsSet class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WorkplaceApprovalSkillsSet} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WorkplaceApprovalSkillsSet> allWorkplaceApprovalSkillsSet(Class<WorkplaceApprovalSkillsSet> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WorkplaceApprovalSkillsSet>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WorkplaceApprovalSkillsSet for lazy load with filters
     * @author TechFinium 
     * @param entity WorkplaceApprovalSkillsSet class
     * @param filters the filters
     * @return Number of rows in the WorkplaceApprovalSkillsSet entity
     * @throws Exception the exception     
     */	
	public int count(Class<WorkplaceApprovalSkillsSet> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public List<WorkplaceApprovalSkillsSet> findByWorkplaceapproval(WorkPlaceApproval workplaceapproval) throws Exception {
		SkillsSetService skillsSetService = new SkillsSetService();
		WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
		List<WorkplaceApprovalSkillsSet>list = dao.findByWorkplaceapproval(workplaceapproval.getId());
		for(WorkplaceApprovalSkillsSet workplaceApprovalSkillsSet :list) {
			workplaceApprovalSkillsSet.setSkillsSet(skillsSetService.findByKey(workplaceApprovalSkillsSet.getSkillsSet().getId()));
			workplaceApprovalSkillsSet.setWorkPlaceApproval(workPlaceApprovalService.findByKey(workplaceApprovalSkillsSet.getWorkPlaceApproval().getId()));
		}
		return list;
	}
}
