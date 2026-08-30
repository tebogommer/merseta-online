package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SkillsIdentificationDAO;
import haj.com.entity.lookup.SkillsIdentification;
import haj.com.framework.AbstractService;

public class SkillsIdentificationService extends AbstractService {
	/** The dao. */
	private SkillsIdentificationDAO dao = new SkillsIdentificationDAO();

	/**
	 * Get all SkillsIdentification
 	 * @author TechFinium 
 	 * @see   SkillsIdentification
 	 * @return a list of {@link haj.com.entity.SkillsIdentification}
	 * @throws Exception the exception
 	 */
	public List<SkillsIdentification> allSkillsIdentification() throws Exception {
	  	return dao.allSkillsIdentification();
	}

	public List<SkillsIdentification> allSkillsIdentificationWithoutOther() throws Exception {
	  	return dao.allSkillsIdentificationWithoutOther();
	}

	/**
	 * Create or update SkillsIdentification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SkillsIdentification
	 */
	public void create(SkillsIdentification entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SkillsIdentification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SkillsIdentification
	 */
	public void update(SkillsIdentification entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SkillsIdentification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SkillsIdentification
	 */
	public void delete(SkillsIdentification entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SkillsIdentification}
	 * @throws Exception the exception
	 * @see    SkillsIdentification
	 */
	public SkillsIdentification findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SkillsIdentification by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SkillsIdentification}
	 * @throws Exception the exception
	 * @see    SkillsIdentification
	 */
	public List<SkillsIdentification> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SkillsIdentification
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SkillsIdentification}
	 * @throws Exception the exception
	 */
	public List<SkillsIdentification> allSkillsIdentification(int first, int pageSize) throws Exception {
		return dao.allSkillsIdentification(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SkillsIdentification for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SkillsIdentification
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SkillsIdentification.class);
	}
	
    /**
     * Lazy load SkillsIdentification with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SkillsIdentification class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SkillsIdentification} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SkillsIdentification> allSkillsIdentification(Class<SkillsIdentification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SkillsIdentification>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SkillsIdentification for lazy load with filters
     * @author TechFinium 
     * @param entity SkillsIdentification class
     * @param filters the filters
     * @return Number of rows in the SkillsIdentification entity
     * @throws Exception the exception     
     */	
	public int count(Class<SkillsIdentification> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
