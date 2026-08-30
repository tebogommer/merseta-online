package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.UsersResponsibilitiesDAO;
import haj.com.entity.CompanyUsers;
import haj.com.entity.SDFCompany;
import haj.com.entity.UsersResponsibilities;
import haj.com.entity.lookup.CompanyUserPosition;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.CompanyUserPositionService;
import haj.com.service.lookup.UserResponsibilityService;

public class UsersResponsibilitiesService extends AbstractService {
	/** The dao. */
	private UsersResponsibilitiesDAO dao = new UsersResponsibilitiesDAO();
	private static UsersResponsibilitiesService responsibilitiesService = null;
	private UserResponsibilityService service = new UserResponsibilityService();
	
	public static synchronized UsersResponsibilitiesService instance() {
		if (responsibilitiesService == null) {
			responsibilitiesService = new UsersResponsibilitiesService();
		}
		return responsibilitiesService;

	}
	/**
	 * Get all UsersResponsibilities
 	 * @author TechFinium 
 	 * @see   UsersResponsibilities
 	 * @return a list of {@link haj.com.entity.UsersResponsibilities}
	 * @throws Exception the exception
 	 */
	public List<UsersResponsibilities> allUsersResponsibilities() throws Exception {
	  	return dao.allUsersResponsibilities();
	}


	/**
	 * Create or update UsersResponsibilities.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     UsersResponsibilities
	 */
	public void create(UsersResponsibilities entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  UsersResponsibilities.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UsersResponsibilities
	 */
	public void update(UsersResponsibilities entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  UsersResponsibilities.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    UsersResponsibilities
	 */
	public void delete(UsersResponsibilities entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UsersResponsibilities}
	 * @throws Exception the exception
	 * @see    UsersResponsibilities
	 */
	public UsersResponsibilities findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find UsersResponsibilities by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.UsersResponsibilities}
	 * @throws Exception the exception
	 * @see    UsersResponsibilities
	 */
	public List<UsersResponsibilities> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load UsersResponsibilities
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.UsersResponsibilities}
	 * @throws Exception the exception
	 */
	public List<UsersResponsibilities> allUsersResponsibilities(int first, int pageSize) throws Exception {
		return dao.allUsersResponsibilities(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of UsersResponsibilities for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the UsersResponsibilities
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(UsersResponsibilities.class);
	}
	
	@SuppressWarnings("unchecked")
	public List<UsersResponsibilities> allUsersResponsibilities(Class<UsersResponsibilities> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<UsersResponsibilities>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Lazy load UsersResponsibilities with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 UsersResponsibilities class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.UsersResponsibilities} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<UsersResponsibilities> allUsersResponsibilitiesResolve(Class<UsersResponsibilities> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return resolveInfor((List<UsersResponsibilities>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters));
	
	}
	
    private List<UsersResponsibilities> resolveInfor(List<UsersResponsibilities> ur) throws Exception {
		for(UsersResponsibilities usersResponsibilities:ur) {
			resolveAdditionalInfo(usersResponsibilities);
		}
		return ur;
	}
	private void resolveAdditionalInfo(UsersResponsibilities usersResponsibilities) throws Exception {
		if(usersResponsibilities.getUserResponsibility() !=null && usersResponsibilities.getUserResponsibility() !=null && usersResponsibilities.getUserResponsibility().getId() != null) {
			usersResponsibilities.setUserResponsibility(service.findByKey(usersResponsibilities.getUserResponsibility().getId()));			
		}
		if(usersResponsibilities.getCompanyUsers()!=null && usersResponsibilities.getCompanyUsers().getCompany()!=null && usersResponsibilities.getCompanyUsers().getCompany().getId()!=null) {
			SDFCompany sDFCompany = SDFCompanyService.instance().findPrimarySDF(usersResponsibilities.getCompanyUsers().getCompany());
			if(sDFCompany!= null && sDFCompany.getSdf() != null) {
				usersResponsibilities.setSdfUser(sDFCompany.getSdf());
			}
			
			if(usersResponsibilities.getCompanyUsers().getPosition() != null && usersResponsibilities.getCompanyUsers().getPosition().getId() != null) {
				CompanyUserPositionService companyUsersService = new CompanyUserPositionService();
				usersResponsibilities.getCompanyUsers().setPosition(companyUsersService.findByKey(usersResponsibilities.getCompanyUsers().getPosition().getId()));	
			}else {
				usersResponsibilities.getCompanyUsers().setPosition(new CompanyUserPosition());
			}
		}	
	}
	/**
     * Get count of UsersResponsibilities for lazy load with filters
     * @author TechFinium 
     * @param entity UsersResponsibilities class
     * @param filters the filters
     * @return Number of rows in the UsersResponsibilities entity
     * @throws Exception the exception     
     */	
	public int count(Class<UsersResponsibilities> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<UsersResponsibilities> findByCompanyUser(CompanyUsers companyUsers) throws Exception {
		return dao.findByCompanyUser(companyUsers);
	}
	

}
