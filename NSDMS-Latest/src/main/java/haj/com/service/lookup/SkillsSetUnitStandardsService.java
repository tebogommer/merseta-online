package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SkillsSetUnitStandardsDAO;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.SkillsSetUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;

public class SkillsSetUnitStandardsService extends AbstractService {
	/** The dao. */
	private SkillsSetUnitStandardsDAO dao = new SkillsSetUnitStandardsDAO();

	/**
	 * Get all SkillsSetUnitStandards
 	 * @author TechFinium 
 	 * @see   SkillsSetUnitStandards
 	 * @return a list of {@link haj.com.entity.SkillsSetUnitStandards}
	 * @throws Exception the exception
 	 */
	public List<SkillsSetUnitStandards> allSkillsSetUnitStandards() throws Exception {
	  	return dao.allSkillsSetUnitStandards();
	}


	/**
	 * Create or update SkillsSetUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SkillsSetUnitStandards
	 */
	public void create(SkillsSetUnitStandards entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SkillsSetUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SkillsSetUnitStandards
	 */
	public void update(SkillsSetUnitStandards entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SkillsSetUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SkillsSetUnitStandards
	 */
	public void delete(SkillsSetUnitStandards entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SkillsSetUnitStandards}
	 * @throws Exception the exception
	 * @see    SkillsSetUnitStandards
	 */
	public SkillsSetUnitStandards findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SkillsSetUnitStandards by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SkillsSetUnitStandards}
	 * @throws Exception the exception
	 * @see    SkillsSetUnitStandards
	 */
	public List<SkillsSetUnitStandards> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SkillsSetUnitStandards
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SkillsSetUnitStandards}
	 * @throws Exception the exception
	 */
	public List<SkillsSetUnitStandards> allSkillsSetUnitStandards(int first, int pageSize) throws Exception {
		return dao.allSkillsSetUnitStandards(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SkillsSetUnitStandards for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SkillsSetUnitStandards
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SkillsSetUnitStandards.class);
	}
	
    /**
     * Lazy load SkillsSetUnitStandards with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SkillsSetUnitStandards class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SkillsSetUnitStandards} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SkillsSetUnitStandards> allSkillsSetUnitStandards(Class<SkillsSetUnitStandards> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SkillsSetUnitStandards>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SkillsSetUnitStandards for lazy load with filters
     * @author TechFinium 
     * @param entity SkillsSetUnitStandards class
     * @param filters the filters
     * @return Number of rows in the SkillsSetUnitStandards entity
     * @throws Exception the exception     
     */	
	public int count(Class<SkillsSetUnitStandards> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public List<SkillsSetUnitStandards> findBySkillsSetKey(SkillsSet skillsSet) throws Exception{
		return dao.findBySkillsSetKey(skillsSet.getId());
	}
	
	public List<SkillsSetUnitStandards> findBySkillsSetKey(SkillsSet skillsSet, SummativeAssessmentReport summativeAssessmentReport) throws Exception{
		return dao.findBySkillsSetKey(skillsSet.getId(), summativeAssessmentReport.getId());
	}

	public void addSkillsSetUnitStandardsToList(SkillsSetUnitStandards skillsSetUnitStandards, SkillsSet skillsset) throws Exception {
		skillsSetUnitStandards.setSkillsSet(skillsset);
		skillsSetUnitStandards.setQualification(skillsset.getQualification());
		create(skillsSetUnitStandards);
	}
	
	public List<UnitStandards> findUnitStandardsBySkillsSetId(Long skillsSetID) {
		return dao.findUnitStandardsBySkillsSetId(skillsSetID);
	}
}
