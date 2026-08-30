package haj.com.service;

import java.util.List;

import haj.com.dao.UserSkillsProgrammeDAO;
import haj.com.entity.UserQualifications;
import haj.com.entity.UserSkillsProgramme;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class UserSkillsProgrammeService extends AbstractService {
	/** The dao. */
	private UserSkillsProgrammeDAO dao = new UserSkillsProgrammeDAO();

	/**
	 * Get all UserSkillsProgramme
 	 * @author TechFinium 
 	 * @see   UserSkillsProgramme
 	 * @return a list of {@link haj.com.entity.UserSkillsProgramme}
	 * @throws Exception the exception
 	 */
	public List<UserSkillsProgramme> allUserSkillsProgramme() throws Exception {
	  	return dao.allUserSkillsProgramme();
	}
	
	public List<UserSkillsProgramme> findByUserAMApplication(Long userId,Long amApID) throws Exception {
	  return dao.findByUserAMApplication(userId, amApID);
	}


	/**
	 * Create or update UserSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UserSkillsProgramme
	 */
	public void create(UserSkillsProgramme entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UserSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserSkillsProgramme
	 */
	public void update(UserSkillsProgramme entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UserSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserSkillsProgramme
	 */
	public void delete(UserSkillsProgramme entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UserSkillsProgramme}
	 * @throws Exception the exception
	 * @see    UserSkillsProgramme
	 */
	public UserSkillsProgramme findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UserSkillsProgramme by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UserSkillsProgramme}
	 * @throws Exception the exception
	 * @see    UserSkillsProgramme
	 */
	public List<UserSkillsProgramme> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UserSkillsProgramme
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UserSkillsProgramme}
	 * @throws Exception the exception
	 */
	public List<UserSkillsProgramme> allUserSkillsProgramme(int first, int pageSize) throws Exception {
		return dao.allUserSkillsProgramme(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UserSkillsProgramme for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the UserSkillsProgramme
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UserSkillsProgramme.class);
	}
	
    /**
     * Lazy load UserSkillsProgramme with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 UserSkillsProgramme class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UserSkillsProgramme} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UserSkillsProgramme> allUserSkillsProgramme(Class<UserSkillsProgramme> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UserSkillsProgramme>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of UserSkillsProgramme for lazy load with filters
     * @author TechFinium 
     * @param entity UserSkillsProgramme class
     * @param filters the filters
     * @return Number of rows in the UserSkillsProgramme entity
     * @throws Exception the exception     
     */	
	public int count(Class<UserSkillsProgramme> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}
