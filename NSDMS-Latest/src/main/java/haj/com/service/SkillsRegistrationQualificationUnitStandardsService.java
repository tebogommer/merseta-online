package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SkillsRegistrationQualificationUnitStandardsDAO;
import haj.com.entity.SkillsRegistration;
import haj.com.entity.SkillsRegistrationQualificationUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;

public class SkillsRegistrationQualificationUnitStandardsService extends AbstractService {
	/** The dao. */
	private SkillsRegistrationQualificationUnitStandardsDAO dao = new SkillsRegistrationQualificationUnitStandardsDAO();

	/**
	 * Get all SkillsRegistrationQualificationUnitStandards
 	 * @author TechFinium 
 	 * @see   SkillsRegistrationQualificationUnitStandards
 	 * @return a list of {@link haj.com.entity.SkillsRegistrationQualificationUnitStandards}
	 * @throws Exception the exception
 	 */
	public List<SkillsRegistrationQualificationUnitStandards> allSkillsRegistrationQualificationUnitStandards() throws Exception {
	  	return dao.allSkillsRegistrationQualificationUnitStandards();
	}


	/**
	 * Create or update SkillsRegistrationQualificationUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SkillsRegistrationQualificationUnitStandards
	 */
	public void create(SkillsRegistrationQualificationUnitStandards entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SkillsRegistrationQualificationUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SkillsRegistrationQualificationUnitStandards
	 */
	public void update(SkillsRegistrationQualificationUnitStandards entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SkillsRegistrationQualificationUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SkillsRegistrationQualificationUnitStandards
	 */
	public void delete(SkillsRegistrationQualificationUnitStandards entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SkillsRegistrationQualificationUnitStandards}
	 * @throws Exception the exception
	 * @see    SkillsRegistrationQualificationUnitStandards
	 */
	public SkillsRegistrationQualificationUnitStandards findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SkillsRegistrationQualificationUnitStandards by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SkillsRegistrationQualificationUnitStandards}
	 * @throws Exception the exception
	 * @see    SkillsRegistrationQualificationUnitStandards
	 */
	public List<SkillsRegistrationQualificationUnitStandards> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SkillsRegistrationQualificationUnitStandards
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SkillsRegistrationQualificationUnitStandards}
	 * @throws Exception the exception
	 */
	public List<SkillsRegistrationQualificationUnitStandards> allSkillsRegistrationQualificationUnitStandards(int first, int pageSize) throws Exception {
		return dao.allSkillsRegistrationQualificationUnitStandards(Long.valueOf(first), pageSize);
	}
		
	public List<UnitStandards> allUnitStandards(SkillsRegistration skillsRegistration) throws Exception {
		return dao.allUnitStandards(skillsRegistration.getId());
	}
	
	public List<SkillsRegistrationQualificationUnitStandards> allSkillsRegistrationQualificationUnitStandards(SkillsRegistration skillsRegistration) throws Exception {
		return dao.allSkillsRegistrationQualificationUnitStandards(skillsRegistration.getId());
	}
	
	/**
	 * Get count of SkillsRegistrationQualificationUnitStandards for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SkillsRegistrationQualificationUnitStandards
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SkillsRegistrationQualificationUnitStandards.class);
	}
	
    /**
     * Lazy load SkillsRegistrationQualificationUnitStandards with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SkillsRegistrationQualificationUnitStandards class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SkillsRegistrationQualificationUnitStandards} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SkillsRegistrationQualificationUnitStandards> allSkillsRegistrationQualificationUnitStandards(Class<SkillsRegistrationQualificationUnitStandards> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SkillsRegistrationQualificationUnitStandards>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SkillsRegistrationQualificationUnitStandards for lazy load with filters
     * @author TechFinium 
     * @param entity SkillsRegistrationQualificationUnitStandards class
     * @param filters the filters
     * @return Number of rows in the SkillsRegistrationQualificationUnitStandards entity
     * @throws Exception the exception     
     */	
	public int count(Class<SkillsRegistrationQualificationUnitStandards> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
