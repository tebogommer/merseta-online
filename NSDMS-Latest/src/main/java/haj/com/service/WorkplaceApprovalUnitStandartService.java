package haj.com.service;

import java.util.List;

import haj.com.dao.WorkplaceApprovalUnitStandartDAO;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.WorkplaceApprovalUnitStandart;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.SkillsSetService;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class WorkplaceApprovalUnitStandartService extends AbstractService {
	/** The dao. */
	private WorkplaceApprovalUnitStandartDAO dao = new WorkplaceApprovalUnitStandartDAO();

	/**
	 * Get all WorkplaceApprovalUnitStandart
 	 * @author TechFinium 
 	 * @see   WorkplaceApprovalUnitStandart
 	 * @return a list of {@link haj.com.entity.WorkplaceApprovalUnitStandart}
	 * @throws Exception the exception
 	 */
	public List<WorkplaceApprovalUnitStandart> allWorkplaceApprovalUnitStandart() throws Exception {
	  	return dao.allWorkplaceApprovalUnitStandart();
	}


	/**
	 * Create or update WorkplaceApprovalUnitStandart.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WorkplaceApprovalUnitStandart
	 */
	public void create(WorkplaceApprovalUnitStandart entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WorkplaceApprovalUnitStandart.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceApprovalUnitStandart
	 */
	public void update(WorkplaceApprovalUnitStandart entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WorkplaceApprovalUnitStandart.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceApprovalUnitStandart
	 */
	public void delete(WorkplaceApprovalUnitStandart entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkplaceApprovalUnitStandart}
	 * @throws Exception the exception
	 * @see    WorkplaceApprovalUnitStandart
	 */
	public WorkplaceApprovalUnitStandart findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WorkplaceApprovalUnitStandart by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WorkplaceApprovalUnitStandart}
	 * @throws Exception the exception
	 * @see    WorkplaceApprovalUnitStandart
	 */
	public List<WorkplaceApprovalUnitStandart> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WorkplaceApprovalUnitStandart
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkplaceApprovalUnitStandart}
	 * @throws Exception the exception
	 */
	public List<WorkplaceApprovalUnitStandart> allWorkplaceApprovalUnitStandart(int first, int pageSize) throws Exception {
		return dao.allWorkplaceApprovalUnitStandart(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WorkplaceApprovalUnitStandart for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WorkplaceApprovalUnitStandart
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WorkplaceApprovalUnitStandart.class);
	}
	
    /**
     * Lazy load WorkplaceApprovalUnitStandart with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WorkplaceApprovalUnitStandart class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WorkplaceApprovalUnitStandart} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WorkplaceApprovalUnitStandart> allWorkplaceApprovalUnitStandart(Class<WorkplaceApprovalUnitStandart> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WorkplaceApprovalUnitStandart>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WorkplaceApprovalUnitStandart for lazy load with filters
     * @author TechFinium 
     * @param entity WorkplaceApprovalUnitStandart class
     * @param filters the filters
     * @return Number of rows in the WorkplaceApprovalUnitStandart entity
     * @throws Exception the exception     
     */	
	public int count(Class<WorkplaceApprovalUnitStandart> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public List<WorkplaceApprovalUnitStandart> findByWorkplaceapproval(WorkPlaceApproval workplaceapproval) throws Exception {
		UnitStandardsService unitStandardsService = new UnitStandardsService();
		WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
		List<WorkplaceApprovalUnitStandart> list = dao.findByWorkplaceapproval(workplaceapproval.getId());
		for(WorkplaceApprovalUnitStandart workplaceApprovalUnitStandart:list) {
			workplaceApprovalUnitStandart.setUnitStandards(unitStandardsService.findByKey(workplaceApprovalUnitStandart.getUnitStandards().getId()));
			workplaceApprovalUnitStandart.setWorkPlaceApproval(workPlaceApprovalService.findByKey(workplaceApprovalUnitStandart.getWorkPlaceApproval().getId()));
		}
		
		return list;
	}
}
