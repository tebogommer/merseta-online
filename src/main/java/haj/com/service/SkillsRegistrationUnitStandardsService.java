package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SkillsRegistrationUnitStandardsDAO;
import haj.com.entity.SkillsRegistration;
import haj.com.entity.SkillsRegistrationUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;

public class SkillsRegistrationUnitStandardsService extends AbstractService {
	/** The dao. */
	private SkillsRegistrationUnitStandardsDAO dao = new SkillsRegistrationUnitStandardsDAO();

	/**
	 * Get all SkillsRegistrationUnitStandards
 	 * @author TechFinium 
 	 * @see   SkillsRegistrationUnitStandards
 	 * @return a list of {@link haj.com.entity.SkillsRegistrationUnitStandards}
	 * @throws Exception the exception
 	 */
	public List<SkillsRegistrationUnitStandards> allSkillsRegistrationUnitStandards() throws Exception {
	  	return dao.allSkillsRegistrationUnitStandards();
	}


	/**
	 * Create or update SkillsRegistrationUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SkillsRegistrationUnitStandards
	 */
	public void create(SkillsRegistrationUnitStandards entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SkillsRegistrationUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SkillsRegistrationUnitStandards
	 */
	public void update(SkillsRegistrationUnitStandards entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SkillsRegistrationUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SkillsRegistrationUnitStandards
	 */
	public void delete(SkillsRegistrationUnitStandards entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SkillsRegistrationUnitStandards}
	 * @throws Exception the exception
	 * @see    SkillsRegistrationUnitStandards
	 */
	public SkillsRegistrationUnitStandards findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SkillsRegistrationUnitStandards by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SkillsRegistrationUnitStandards}
	 * @throws Exception the exception
	 * @see    SkillsRegistrationUnitStandards
	 */
	public List<SkillsRegistrationUnitStandards> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SkillsRegistrationUnitStandards
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SkillsRegistrationUnitStandards}
	 * @throws Exception the exception
	 */
	public List<SkillsRegistrationUnitStandards> allSkillsRegistrationUnitStandards(int first, int pageSize) throws Exception {
		return dao.allSkillsRegistrationUnitStandards(Long.valueOf(first), pageSize);
	}
		
	public List<UnitStandards> allUnitStandards(SkillsRegistration skillsRegistration) throws Exception {
		return dao.allUnitStandards(skillsRegistration.getId());
	}
	
	public List<SkillsRegistrationUnitStandards> allSkillsRegistrationUnitStandards(SkillsRegistration skillsRegistration) throws Exception {
		return dao.allSkillsRegistrationUnitStandards(skillsRegistration.getId());
	}
	
	/**
	 * Get count of SkillsRegistrationUnitStandards for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SkillsRegistrationUnitStandards
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SkillsRegistrationUnitStandards.class);
	}
	
    /**
     * Lazy load SkillsRegistrationUnitStandards with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SkillsRegistrationUnitStandards class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SkillsRegistrationUnitStandards} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SkillsRegistrationUnitStandards> allSkillsRegistrationUnitStandards(Class<SkillsRegistrationUnitStandards> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SkillsRegistrationUnitStandards>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SkillsRegistrationUnitStandards for lazy load with filters
     * @author TechFinium 
     * @param entity SkillsRegistrationUnitStandards class
     * @param filters the filters
     * @return Number of rows in the SkillsRegistrationUnitStandards entity
     * @throws Exception the exception     
     */	
	public int count(Class<SkillsRegistrationUnitStandards> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
