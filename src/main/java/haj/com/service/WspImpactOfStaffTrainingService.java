package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WspImpactOfStaffTrainingDAO;
import haj.com.entity.WspImpactOfStaffTraining;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class WspImpactOfStaffTrainingService extends AbstractService {
	/** The dao. */
	private WspImpactOfStaffTrainingDAO dao = new WspImpactOfStaffTrainingDAO();

	/**
	 * Get all WspImpactOfStaffTraining
 	 * @author TechFinium 
 	 * @see   WspImpactOfStaffTraining
 	 * @return a list of {@link haj.com.entity.WspImpactOfStaffTraining}
	 * @throws Exception the exception
 	 */
	public List<WspImpactOfStaffTraining> allWspImpactOfStaffTraining() throws Exception {
	  	return dao.allWspImpactOfStaffTraining();
	}


	/**
	 * Create or update WspImpactOfStaffTraining.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WspImpactOfStaffTraining
	 */
	public void create(WspImpactOfStaffTraining entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WspImpactOfStaffTraining.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspImpactOfStaffTraining
	 */
	public void update(WspImpactOfStaffTraining entity) throws Exception  {
		this.dao.update(entity);
	}
	
	@SuppressWarnings("unchecked")
	public void updateBatch(List<WspImpactOfStaffTraining> wspimpactofstafftrainingList) throws Exception  {
		this.dao.updateBatch((List<IDataEntity>)((List<?>)wspimpactofstafftrainingList));
	}

	/**
	 * Delete  WspImpactOfStaffTraining.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspImpactOfStaffTraining
	 */
	public void delete(WspImpactOfStaffTraining entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WspImpactOfStaffTraining}
	 * @throws Exception the exception
	 * @see    WspImpactOfStaffTraining
	 */
	public WspImpactOfStaffTraining findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WspImpactOfStaffTraining by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WspImpactOfStaffTraining}
	 * @throws Exception the exception
	 * @see    WspImpactOfStaffTraining
	 */
	public List<WspImpactOfStaffTraining> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WspImpactOfStaffTraining
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspImpactOfStaffTraining}
	 * @throws Exception the exception
	 */
	public List<WspImpactOfStaffTraining> allWspImpactOfStaffTraining(int first, int pageSize) throws Exception {
		return dao.allWspImpactOfStaffTraining(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WspImpactOfStaffTraining for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WspImpactOfStaffTraining
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WspImpactOfStaffTraining.class);
	}
	
    /**
     * Lazy load WspImpactOfStaffTraining with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WspImpactOfStaffTraining class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WspImpactOfStaffTraining} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WspImpactOfStaffTraining> allWspImpactOfStaffTraining(Class<WspImpactOfStaffTraining> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WspImpactOfStaffTraining>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WspImpactOfStaffTraining for lazy load with filters
     * @author TechFinium 
     * @param entity WspImpactOfStaffTraining class
     * @param filters the filters
     * @return Number of rows in the WspImpactOfStaffTraining entity
     * @throws Exception the exception     
     */	
	public int count(Class<WspImpactOfStaffTraining> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<WspImpactOfStaffTraining> findByWspNull() throws Exception {
		return dao.findByWspNull();
	}
	public List<WspImpactOfStaffTraining> findByWsp(Long wspID) throws Exception {
		return dao.findByWsp(wspID);
	}
}
