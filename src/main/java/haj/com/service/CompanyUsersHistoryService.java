package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.CompanyUsersHistoryDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.CompanyUsersHistory;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.SizeOfCompanyService;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUsersHistoryService.
 */
public class CompanyUsersHistoryService extends AbstractService {
	/** The dao. */
	private CompanyUsersHistoryDAO dao = new CompanyUsersHistoryDAO();

	/** The register service. */
	private RegisterService registerService = new RegisterService();

	/** The Constant leftJoins. */
	private static final String leftJoins = " " + "left join fetch o.company x left join fetch o.user z left join fetch x.chamber left join fetch x.sicCode" + " ";

	
	/**
	 * Get all CompanyUsersHistory.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyUsersHistory}
	 * @throws Exception
	 *             the exception
	 * @see CompanyUsersHistory
	 */
	public List<CompanyUsersHistory> allCompanyUsers() throws Exception {
		return dao.allCompanyUsersHistory();
	}

	/**
	 * Find users type of company.
	 *
	 * @param userType
	 *            the user type
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	public List<Users> findUsersTypeOfCompany(ConfigDocProcessEnum userType, Long companyId) {
		List<CompanyUsersHistory> cuList = new ArrayList<>();
		List<Users> uList = new ArrayList<>();
		cuList = dao.findUsersTypeOfCompany(userType, companyId);
		for (CompanyUsersHistory companyUsersHistory : cuList) {
			uList.add(companyUsersHistory.getUser());
		}

		return uList;
	}

	/**
	 * Create or update companyUsersHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see companyUsersHistory
	 */
	public void create(CompanyUsersHistory entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}


	/**
	 * Update CompanyUsersHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyUsersHistory
	 */
	public void update(CompanyUsersHistory entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CompanyUsersHistory.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyUsersHistory
	 */
	public void delete(CompanyUsersHistory entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CompanyUsers}
	 * @throws Exception
	 *             the exception
	 * @see CompanyUsers
	 */
	public CompanyUsersHistory findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}
	
	/**
	 * Find object by companyUsers.
	 *
	 * @author TechFinium
	 * @param CompanyUsers the CompanyUsers
	 * @return a {@link haj.com.entity.CompanyUsersHistory}
	 * @throws Exception             global exception
	 * @see CompanyUsersHistory
	 */
	public CompanyUsersHistory findByCompanyUserID(Long id) throws Exception {
		return dao.findByCompanyUser(id);
	}

	/**
	 * Find by user and company.
	 *
	 * @param companyId
	 *            the company id
	 * @param userId
	 *            the user id
	 * @return theCompanyUsersHistory
	 * @throws Exception
	 *             the exception
	 */
	public CompanyUsersHistory findByUserAndCompany(Long companyId, Long userId) throws Exception {
		return dao.findByUserAndCompany(companyId, userId);
	}

	/**
	 * Find CompanyUsersHistory by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CompanyUsers}
	 * @throws Exception
	 *             the exception
	 * @see CompanyUsersHistory
	 */
	public List<CompanyUsersHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load CompanyUsersHistory.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyUsersHistory}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyUsersHistory> allCompanyUsersHistory(int first, int pageSize) throws Exception {
		return dao.allCompanyUsersHistory(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of CompanyUsersHistory for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the CompanyUsersHistory
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyUsers.class);
	}

	/**
	 * Lazy load CompanyUsersHistory with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            CompanyUsersHistory class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.CompanyUsersHistory} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> allCompanyUsersHistory(Class<CompanyUsers> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<CompanyUsersHistory>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * All company for learner reg.
	 *
	 * @param class1
	 *            the class 1
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param parameters
	 *            the parameters
	 * @param whereHql
	 *            the where hql
	 * @return the list
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> allCompanyForLearnerReg(Class<CompanyUsers> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> parameters, String whereHql) {
		String hql = "select o from CompanyUsersHistory o " + leftJoins + " " + whereHql + " group by x.id";
		return (List<CompanyUsersHistory>) dao.hqlAndParamOnly(class1, first, pageSize, sortField, sortOrder, parameters, hql);
	}

	/**
	 * Get count of CompanyUsersHistory for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            CompanyUsersHistory class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CompanyUsersHistory entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CompanyUsersHistory> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	

	/**
	 * Find by company type.
	 *
	 * @param companyId
	 *            the company id
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyUsersHistory> findByCompanyType(Long companyId, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		return dao.findByCompanyType(companyId, configDocProcessEnum);
	}

	public List<Users> findUsersByCompanyType(Long companyId, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		return dao.findUsersByCompanyType(companyId, configDocProcessEnum);
	}

	/**
	 * Find by company.
	 *
	 * @param company
	 *            the company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyUsersHistory> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company.getId());
	}


	public List<CompanyUsersHistory> findByCompanyResponsibility(Company company, ConfigDocProcessEnum forProcess) throws Exception {
		return dao.findByCompanyResponsibility(company.getId(), forProcess);

	}

	/**
	 * Find by user.
	 *
	 * @param user
	 *            the user
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Company> findByUser(Users user) throws Exception {
		List<CompanyUsersHistory> l = dao.findByUser(user.getId());
		List<Company> l2 = new ArrayList<Company>();
		for (CompanyUsersHistory cu : l) {
			cu.getCompany().setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(cu.getCompany()));
			l2.add(cu.getCompany());
			
		}
		
		return l2;
	}

	public void createHistory(Long id) throws Exception 
	{
		CompanyUsersService companyUsersService=new CompanyUsersService();
		CompanyUsers companyUsers=companyUsersService.findByKey(id);
		CompanyUsersHistory companyUsersHistory=new CompanyUsersHistory(companyUsers);
		BeanUtils.copyProperties(companyUsersHistory, companyUsers);
		companyUsersHistory.setId(null);
		create(companyUsersHistory);
		
		
		
	}
	
	/**
	 * Get all by Company user history id.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyUsersHistory}
	 * @throws Exception             global exception
	 * @see CompanyUsersHistory
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> findAllByCompanyUserID(Long id) throws Exception {
		return dao.findAllByCompanyUserID(id);
	}
	
	/**
	 * Get all by forCompanyUser.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyUsersHistory}
	 * @throws Exception             global exception
	 * @see CompanyUsersHistory
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsersHistory> findAllByForID(Long forCompanyUserID) throws Exception {
		return dao.findAllByForID(forCompanyUserID);
	}
	
	/**Delete by for Company user
	 * @throws Exception */
	public void deleteByForID(Long id) throws Exception
	{
		 List<CompanyUsersHistory> list=findAllByForID(id);
		 for(CompanyUsersHistory cuh: list)
		 {
			 cuh.setForCompanyUser(null);
			 delete(cuh);
		 }
	}
	
	

}
