package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SkillsProgramUnitStandardsDAO;
import haj.com.entity.SummativeAssessmentReport;
import haj.com.entity.enums.UnitStandardTypeEnum;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsProgramUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;

public class SkillsProgramUnitStandardsService extends AbstractService {
	/** The dao. */
	private SkillsProgramUnitStandardsDAO dao = new SkillsProgramUnitStandardsDAO();

	/**
	 * Get all SkillsProgramUnitStandards
 	 * @author TechFinium 
 	 * @see   SkillsProgramUnitStandards
 	 * @return a list of {@link haj.com.entity.SkillsProgramUnitStandards}
	 * @throws Exception the exception
 	 */
	public List<SkillsProgramUnitStandards> allSkillsProgramUnitStandards() throws Exception {
	  	return dao.allSkillsProgramUnitStandards();
	}


	/**
	 * Create or update SkillsProgramUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SkillsProgramUnitStandards
	 */
	public void create(SkillsProgramUnitStandards entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  SkillsProgramUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SkillsProgramUnitStandards
	 */
	public void update(SkillsProgramUnitStandards entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SkillsProgramUnitStandards.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SkillsProgramUnitStandards
	 */
	public void delete(SkillsProgramUnitStandards entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SkillsProgramUnitStandards}
	 * @throws Exception the exception
	 * @see    SkillsProgramUnitStandards
	 */
	public SkillsProgramUnitStandards findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SkillsProgramUnitStandards by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SkillsProgramUnitStandards}
	 * @throws Exception the exception
	 * @see    SkillsProgramUnitStandards
	 */
	public List<SkillsProgramUnitStandards> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load SkillsProgramUnitStandards
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SkillsProgramUnitStandards}
	 * @throws Exception the exception
	 */
	public List<SkillsProgramUnitStandards> allSkillsProgramUnitStandards(int first, int pageSize) throws Exception {
		return dao.allSkillsProgramUnitStandards(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of SkillsProgramUnitStandards for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the SkillsProgramUnitStandards
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SkillsProgramUnitStandards.class);
	}
	
    /**
     * Lazy load SkillsProgramUnitStandards with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 SkillsProgramUnitStandards class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SkillsProgramUnitStandards} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<SkillsProgramUnitStandards> allSkillsProgramUnitStandards(Class<SkillsProgramUnitStandards> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<SkillsProgramUnitStandards>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of SkillsProgramUnitStandards for lazy load with filters
     * @author TechFinium 
     * @param entity SkillsProgramUnitStandards class
     * @param filters the filters
     * @return Number of rows in the SkillsProgramUnitStandards entity
     * @throws Exception the exception     
     */	
	public int count(Class<SkillsProgramUnitStandards> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public List<SkillsProgramUnitStandards> findBySkillsProgramKey(SkillsProgram skillsProgram) throws Exception{
		return dao.findBySkillsProgramKey(skillsProgram.getId());
	}
	
	public List<SkillsProgramUnitStandards> findBySkillsProgramKey(SkillsProgram skillsProgram, SummativeAssessmentReport summativeAssessmentReport) throws Exception{
		return dao.findBySkillsProgramKey(skillsProgram.getId(), summativeAssessmentReport.getId());
	}

	public void createLink(UnitStandards unitStandard, SkillsProgram skillsProgram, UnitStandardTypeEnum unitStandardTypeEnum) throws Exception{
		SkillsProgramUnitStandards skillsProgramUnitStandards = new SkillsProgramUnitStandards();
		skillsProgramUnitStandards.setSkillsProgram(skillsProgram);
		skillsProgramUnitStandards.setUnitStandards(unitStandard);
		skillsProgramUnitStandards.setUnitStandardTypeEnum(unitStandardTypeEnum);
		skillsProgramUnitStandards.setQualification(skillsProgram.getQualification());
		create(skillsProgramUnitStandards);
	}
	
	public void addSkillsProgramUnitStandardsToList(SkillsProgramUnitStandards skillsProgramUnitStandards , SkillsProgram skillsProgram) throws Exception{
		skillsProgramUnitStandards.setSkillsProgram(skillsProgram);
		skillsProgramUnitStandards.setQualification(skillsProgram.getQualification());
		create(skillsProgramUnitStandards);
	}
	
	public List<UnitStandards> findUnitStandardsBySkillsProgrammeId(Long skillsProgramID) throws Exception {
		return dao.findUnitStandardsBySkillsProgrammeId(skillsProgramID);
	}
}
