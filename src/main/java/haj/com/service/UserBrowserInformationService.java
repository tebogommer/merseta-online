package haj.com.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.UserBrowserInformationDAO;
import haj.com.entity.UserBrowserInformation;
import haj.com.framework.AbstractService;

public class UserBrowserInformationService extends AbstractService {
	/** The dao. */
	private UserBrowserInformationDAO dao = new UserBrowserInformationDAO();

	/**
	 * Get all UserBrowserInformation
 	 * @author TechFinium
 	 * @see   UserBrowserInformation
 	 * @return a list of {@link haj.com.entity.UserBrowserInformation}
	 * @throws Exception the exception
 	 */
	public List<UserBrowserInformation> allUserBrowserInformation() throws Exception {
	  	return dao.allUserBrowserInformation();
	}


	/**
	 * Create or update UserBrowserInformation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UserBrowserInformation
	 */
	public void create(UserBrowserInformation entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UserBrowserInformation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserBrowserInformation
	 */
	public void update(UserBrowserInformation entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UserBrowserInformation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UserBrowserInformation
	 */
	public void delete(UserBrowserInformation entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UserBrowserInformation}
	 * @throws Exception the exception
	 * @see    UserBrowserInformation
	 */
	public UserBrowserInformation findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UserBrowserInformation by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UserBrowserInformation}
	 * @throws Exception the exception
	 * @see    UserBrowserInformation
	 */
	public List<UserBrowserInformation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load UserBrowserInformation
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UserBrowserInformation}
	 * @throws Exception the exception
	 */
	public List<UserBrowserInformation> allUserBrowserInformation(int first, int pageSize) throws Exception {
		return dao.allUserBrowserInformation(Long.valueOf(first), pageSize);
	}


	/**
	 * Get count of UserBrowserInformation for lazy load
	 * @author TechFinium
	 * @return Number of rows in the UserBrowserInformation
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UserBrowserInformation.class);
	}

    /**
     * Lazy load UserBrowserInformation with pagination, filter, sorting
     * @author TechFinium
     * @param class1 UserBrowserInformation class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UserBrowserInformation} containing data
     * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	public List<UserBrowserInformation> allUserBrowserInformation(Class<UserBrowserInformation> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<UserBrowserInformation>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);

	}

    /**
     * Get count of UserBrowserInformation for lazy load with filters
     * @author TechFinium
     * @param entity UserBrowserInformation class
     * @param filters the filters
     * @return Number of rows in the UserBrowserInformation entity
     * @throws Exception the exception
     */
	public int count(Class<UserBrowserInformation> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}

	public void resolveIP(UserBrowserInformation userbrowserinformation) throws Exception {
		if (StringUtils.isEmpty(userbrowserinformation.getIpAddressDecoded())) {
			userbrowserinformation.setIpAddressDecoded(ResolveIPService.instance().sendGet(userbrowserinformation.getIpAddress()));
			dao.update(userbrowserinformation);
		}
		userbrowserinformation.setIpLocation(ResolveIPService.instance().descodeString(userbrowserinformation.getIpAddressDecoded()));
		if (userbrowserinformation.getLatitude()==null) {
			if (userbrowserinformation.getIpLocation()!=null && userbrowserinformation.getIpLocation().getLat()!=null) {
				userbrowserinformation.setLatitude(userbrowserinformation.getIpLocation().getLat());
				userbrowserinformation.setLongitude(userbrowserinformation.getIpLocation().getLon());
				dao.update(userbrowserinformation);
			}
		}

	}
}
