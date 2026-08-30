package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;

import haj.com.dao.lookup.LearnershipUnitStandardsDAO;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.LearnershipUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class LearnershipUnitStandardsService extends AbstractService {
	
	/** The dao. */
	private LearnershipUnitStandardsDAO dao = new LearnershipUnitStandardsDAO();

	/**
	 * Get all LearnershipUnitStandards
 	 * @author TechFinium 
 	 * @see   LearnershipUnitStandards
 	 * @return a list of {@link haj.com.entity.LearnershipUnitStandards}
	 * @throws Exception the exception
 	 */
	public List<LearnershipUnitStandards> allLearnershipUnitStandards() throws Exception {
	  	return dao.allLearnershipUnitStandards();
	}


	/**
	 * Create or update LearnershipUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LearnershipUnitStandards
	 */
	public void create(LearnershipUnitStandards entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LearnershipUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LearnershipUnitStandards
	 */
	public void update(LearnershipUnitStandards entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LearnershipUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LearnershipUnitStandards
	 */
	public void delete(LearnershipUnitStandards entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LearnershipUnitStandards}
	 * @throws Exception the exception
	 * @see    LearnershipUnitStandards
	 */
	public LearnershipUnitStandards findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LearnershipUnitStandards by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LearnershipUnitStandards}
	 * @throws Exception the exception
	 * @see    LearnershipUnitStandards
	 */
	public List<LearnershipUnitStandards> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LearnershipUnitStandards
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LearnershipUnitStandards}
	 * @throws Exception the exception
	 */
	public List<LearnershipUnitStandards> allLearnershipUnitStandards(int first, int pageSize) throws Exception {
		return dao.allLearnershipUnitStandards(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LearnershipUnitStandards for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the LearnershipUnitStandards
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LearnershipUnitStandards.class);
	}
	
    /**
     * Lazy load LearnershipUnitStandards with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 LearnershipUnitStandards class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LearnershipUnitStandards} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LearnershipUnitStandards> allLearnershipUnitStandards(Class<LearnershipUnitStandards> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<LearnershipUnitStandards>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of LearnershipUnitStandards for lazy load with filters
     * @author TechFinium 
     * @param entity LearnershipUnitStandards class
     * @param filters the filters
     * @return Number of rows in the LearnershipUnitStandards entity
     * @throws Exception the exception     
     */	
	public int count(Class<LearnershipUnitStandards> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}

	public List<LearnershipUnitStandards> findByLearnership(Learnership learnership) throws Exception{
		return dao.findByLearnership(learnership.getId());
	}
	
	public void deleteEntriesByLearnership(Learnership learnership) throws Exception{
		List<IDataEntity> deleteList = new ArrayList<>();
		deleteList.addAll(findByLearnership(learnership));
		if (!deleteList.isEmpty()) {
			dao.deleteBatch(deleteList);
		}
	}
	
	public Integer countByUnitStandardAndLearnership(UnitStandards unitStandard, Learnership learnership) throws Exception {
		return dao.countByUnitStandardAndLearnership(unitStandard.getId(), learnership.getId());
	}
	
	public void createLink(UnitStandards unitStandard, Learnership learnership) throws Exception{
		LearnershipUnitStandards learnershipUnitStandards = new LearnershipUnitStandards();
		learnershipUnitStandards.setLearnership(learnership);
		learnershipUnitStandards.setUnitStandards(unitStandard);
		learnershipUnitStandards.setQualification(learnership.getQualification());
		create(learnershipUnitStandards);
	}


	public void createLink(LearnershipUnitStandards learnershipUnitStandards, Learnership learnership) throws Exception{
		learnershipUnitStandards.setLearnership(learnership);
		learnershipUnitStandards.setQualification(learnership.getQualification());
		create(learnershipUnitStandards);
	}
	public void updateLink(LearnershipUnitStandards learnershipUnitStandards) throws Exception{
		create(learnershipUnitStandards);
	}
}
