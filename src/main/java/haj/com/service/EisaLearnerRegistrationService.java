package haj.com.service;



import java.util.List;

import haj.com.dao.EisaLearnerRegistrationDAO;
import haj.com.entity.EisaLearnerRegistration;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class EisaLearnerRegistrationService extends AbstractService {
	/** The dao. */
	private EisaLearnerRegistrationDAO dao = new EisaLearnerRegistrationDAO();

	/**
	 * Get all EisaLearnerRegistration
 	 * @author TechFinium 
 	 * @see   EisaLearnerRegistration
 	 * @return a list of {@link haj.com.entity.EisaLearnerRegistration}
	 * @throws Exception the exception
 	 */
	public List<EisaLearnerRegistration> allEisaLearnerRegistration() throws Exception {
	  	return dao.allEisaLearnerRegistration();
	}


	/**
	 * Create or update EisaLearnerRegistration.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     EisaLearnerRegistration
	 */
	public void create(EisaLearnerRegistration entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}
	
	/**
	 * Create or update EisaLearnerRegistration.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     EisaLearnerRegistration
	 */
	public void saveRegistration(EisaLearnerRegistration entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else{
		 this.dao.update(entity);
	   }
	   //Updating user 
	   UsersService usersService=new UsersService();
	   usersService.update(entity.getLearner());
	   
	}


	/**
	 * Update  EisaLearnerRegistration.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EisaLearnerRegistration
	 */
	public void update(EisaLearnerRegistration entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  EisaLearnerRegistration.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    EisaLearnerRegistration
	 */
	public void delete(EisaLearnerRegistration entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.EisaLearnerRegistration}
	 * @throws Exception the exception
	 * @see    EisaLearnerRegistration
	 */
	public EisaLearnerRegistration findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find EisaLearnerRegistration by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.EisaLearnerRegistration}
	 * @throws Exception the exception
	 * @see    EisaLearnerRegistration
	 */
	public List<EisaLearnerRegistration> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load EisaLearnerRegistration
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.EisaLearnerRegistration}
	 * @throws Exception the exception
	 */
	public List<EisaLearnerRegistration> allEisaLearnerRegistration(int first, int pageSize) throws Exception {
		return dao.allEisaLearnerRegistration(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of EisaLearnerRegistration for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the EisaLearnerRegistration
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(EisaLearnerRegistration.class);
	}
	
    /**
     * Lazy load EisaLearnerRegistration with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 EisaLearnerRegistration class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.EisaLearnerRegistration} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<EisaLearnerRegistration> allEisaLearnerRegistration(Class<EisaLearnerRegistration> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<EisaLearnerRegistration>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of EisaLearnerRegistration for lazy load with filters
     * @author TechFinium 
     * @param entity EisaLearnerRegistration class
     * @param filters the filters
     * @return Number of rows in the EisaLearnerRegistration entity
     * @throws Exception the exception     
     */	
	public int count(Class<EisaLearnerRegistration> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
}