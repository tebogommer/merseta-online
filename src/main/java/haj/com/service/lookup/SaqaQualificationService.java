package haj.com.service.lookup;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.SaqaQualificationDAO;
import haj.com.entity.Users;
import haj.com.entity.WpaChangeLog;
import haj.com.entity.lookup.SaqaQualification;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class SaqaQualificationService.
 */
public class SaqaQualificationService extends AbstractService {
	/** The dao. */
	private SaqaQualificationDAO dao = new SaqaQualificationDAO();

	/**
	 * Get all SaqaQualification.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SaqaQualification}
	 * @throws Exception the exception
	 * @see   SaqaQualification
	 */
	public List<SaqaQualification> allSaqaQualification() throws Exception {
	  	return dao.allSaqaQualification();
	}


	/**
	 * Create or update SaqaQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     SaqaQualification
	 */
	public void create(SaqaQualification entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}
	
	/**
	 * Adds to log where user changes WorkplaceApproval against qualification
	 * @param entity
	 * @param sessionUser
	 * @throws Exception
	 */
	public void createLogChangeToWorkplaceApproval(SaqaQualification entity, Users sessionUser) throws Exception{
		WpaChangeLog log = new WpaChangeLog();
		log.setQualificationId(entity.getId());
		log.setUserId(sessionUser.getId());
		log.setValue(entity.getWorkplaceApprovalRequired());
		dao.create(log);
	}


	/**
	 * Update  SaqaQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SaqaQualification
	 */
	public void update(SaqaQualification entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  SaqaQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    SaqaQualification
	 */
	public void delete(SaqaQualification entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SaqaQualification}
	 * @throws Exception the exception
	 * @see    SaqaQualification
	 */
	public SaqaQualification findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find SaqaQualification by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.SaqaQualification}
	 * @throws Exception the exception
	 * @see    SaqaQualification
	 */
	public List<SaqaQualification> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SaqaQualification.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.SaqaQualification}
	 * @throws Exception the exception
	 */
	public List<SaqaQualification> allSaqaQualification(int first, int pageSize) throws Exception {
		return dao.allSaqaQualification(Long.valueOf(first), pageSize);
	}


	/**
	 * Get count of SaqaQualification for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the SaqaQualification
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(SaqaQualification.class);
	}

    /**
     * Lazy load SaqaQualification with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 SaqaQualification class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.SaqaQualification} containing data
     * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	public List<SaqaQualification> allSaqaQualification(Class<SaqaQualification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		if (filters.containsKey("qualificationid")) {
			filters.put("qualificationid", Integer.valueOf( (""+ filters.get("qualificationid")).trim()));
		}
		return ( List<SaqaQualification>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);

	}

    /**
     * Get count of SaqaQualification for lazy load with filters.
     *
     * @author TechFinium
     * @param entity SaqaQualification class
     * @param filters the filters
     * @return Number of rows in the SaqaQualification entity
     * @throws Exception the exception
     */
	public int count(Class<SaqaQualification> entity, Map<String, Object> filters) throws Exception {
		if (filters.containsKey("qualificationid")) {
			filters.put("qualificationid", Integer.valueOf( (""+ filters.get("qualificationid")).trim()));
		}
		return  dao.count(entity, filters);
	}
}
