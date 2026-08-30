package haj.com.service;

import java.util.List;

import haj.com.dao.WorkPlaceApprovalSkillsProgrammeDAO;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.WorkPlaceApprovalSkillsProgramme;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.SkillsProgramService;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class WorkPlaceApprovalSkillsProgrammeService extends AbstractService {
	/** The dao. */
	private WorkPlaceApprovalSkillsProgrammeDAO dao = new WorkPlaceApprovalSkillsProgrammeDAO();

	/**
	 * Get all WorkPlaceApprovalSkillsProgramme
 	 * @author TechFinium 
 	 * @see   WorkPlaceApprovalSkillsProgramme
 	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalSkillsProgramme}
	 * @throws Exception the exception
 	 */
	public List<WorkPlaceApprovalSkillsProgramme> allWorkPlaceApprovalSkillsProgramme() throws Exception {
	  	return dao.allWorkPlaceApprovalSkillsProgramme();
	}


	/**
	 * Create or update WorkPlaceApprovalSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WorkPlaceApprovalSkillsProgramme
	 */
	public void create(WorkPlaceApprovalSkillsProgramme entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WorkPlaceApprovalSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkPlaceApprovalSkillsProgramme
	 */
	public void update(WorkPlaceApprovalSkillsProgramme entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WorkPlaceApprovalSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkPlaceApprovalSkillsProgramme
	 */
	public void delete(WorkPlaceApprovalSkillsProgramme entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkPlaceApprovalSkillsProgramme}
	 * @throws Exception the exception
	 * @see    WorkPlaceApprovalSkillsProgramme
	 */
	public WorkPlaceApprovalSkillsProgramme findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WorkPlaceApprovalSkillsProgramme by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalSkillsProgramme}
	 * @throws Exception the exception
	 * @see    WorkPlaceApprovalSkillsProgramme
	 */
	public List<WorkPlaceApprovalSkillsProgramme> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WorkPlaceApprovalSkillsProgramme
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkPlaceApprovalSkillsProgramme}
	 * @throws Exception the exception
	 */
	public List<WorkPlaceApprovalSkillsProgramme> allWorkPlaceApprovalSkillsProgramme(int first, int pageSize) throws Exception {
		return dao.allWorkPlaceApprovalSkillsProgramme(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WorkPlaceApprovalSkillsProgramme for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WorkPlaceApprovalSkillsProgramme
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WorkPlaceApprovalSkillsProgramme.class);
	}
	
    /**
     * Lazy load WorkPlaceApprovalSkillsProgramme with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WorkPlaceApprovalSkillsProgramme class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WorkPlaceApprovalSkillsProgramme} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WorkPlaceApprovalSkillsProgramme> allWorkPlaceApprovalSkillsProgramme(Class<WorkPlaceApprovalSkillsProgramme> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WorkPlaceApprovalSkillsProgramme>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WorkPlaceApprovalSkillsProgramme for lazy load with filters
     * @author TechFinium 
     * @param entity WorkPlaceApprovalSkillsProgramme class
     * @param filters the filters
     * @return Number of rows in the WorkPlaceApprovalSkillsProgramme entity
     * @throws Exception the exception     
     */	
	public int count(Class<WorkPlaceApprovalSkillsProgramme> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public List<WorkPlaceApprovalSkillsProgramme> findByWorkplaceapproval(WorkPlaceApproval workplaceapproval) throws Exception {
		SkillsProgramService skillsProgramService = new SkillsProgramService();
		WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
		List<WorkPlaceApprovalSkillsProgramme>list = dao.findByWorkplaceapproval(workplaceapproval.getId());
		for(WorkPlaceApprovalSkillsProgramme workPlaceApprovalSkillsProgramme:list) {
			workPlaceApprovalSkillsProgramme.setSkillsProgram(skillsProgramService.findByKey(workPlaceApprovalSkillsProgramme.getSkillsProgram().getId()));
			workPlaceApprovalSkillsProgramme.setWorkPlaceApproval(workPlaceApprovalService.findByKey(workPlaceApprovalSkillsProgramme.getWorkPlaceApproval().getId()));
		}
		return list;
	}
}
