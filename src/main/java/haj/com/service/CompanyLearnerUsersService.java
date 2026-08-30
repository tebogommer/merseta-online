package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.primefaces.model.SortOrder;

import haj.com.dao.CompanyLearnerUsersDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearnerUsers;
import haj.com.entity.Learners;
import haj.com.entity.CompanyLearnerUsers;
import haj.com.entity.Users;
import haj.com.entity.UsersDisability;
import haj.com.entity.UsersLanguage;
import haj.com.entity.enums.UsersStatusEnum;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyLearnerUsersService.
 */
public class CompanyLearnerUsersService extends AbstractService {
	/** The dao. */
	private CompanyLearnerUsersDAO dao = new CompanyLearnerUsersDAO();
	private UsersService usersService= new UsersService();
	/**
	 * Get all CompanyLearnerUsers.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyLearnerUsers}
	 * @throws Exception the exception
	 * @see CompanyLearnerUsers
	 */
	public List<CompanyLearnerUsers> allCompanyLearnerUsers() throws Exception {
		return dao.allCompanyLearnerUsers();
	}

	/**
	 * Create or update CompanyLearnerUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see CompanyLearnerUsers
	 */
	public void create(CompanyLearnerUsers entity) throws Exception {
		if (entity.getId() == null) {
			entity.setCreateDate(new java.util.Date());
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update CompanyLearnerUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see CompanyLearnerUsers
	 */
	public void update(CompanyLearnerUsers entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CompanyLearnerUsers.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see CompanyLearnerUsers
	 */
	public void delete(CompanyLearnerUsers entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.CompanyLearnerUsers}
	 * @throws Exception the exception
	 * @see CompanyLearnerUsers
	 */
	public CompanyLearnerUsers findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find CompanyLearnerUsers by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.CompanyLearnerUsers}
	 * @throws Exception the exception
	 * @see CompanyLearnerUsers
	 */
	public List<CompanyLearnerUsers> findByCompany(Long companyID) throws Exception {
		return dao.findByCompany(companyID);
	}
	
	public  CompanyLearnerUsers findByCompanyAndUser(Long companyID, Long userID) throws Exception {
		return dao.findByCompanyAndUser(companyID, userID);
	}

	/**
	 * Lazy load CompanyLearnerUsers.
	 *
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyLearnerUsers}
	 * @throws Exception the exception
	 */
	public List<CompanyLearnerUsers> allCompanyLearnerUsers(int first, int pageSize) throws Exception {
		return dao.allCompanyLearnerUsers(Long.valueOf(first), pageSize);
	}

	@SuppressWarnings("unchecked")
	public List<CompanyLearnerUsers> allLearners(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearnerUsers o ";
		return (List<CompanyLearnerUsers>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countLearners(Class<CompanyLearnerUsers> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnerUsers o ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<CompanyLearnerUsers> allLearnersByCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from CompanyLearnerUsers o where o.company.id = :companyID";
		return (List<CompanyLearnerUsers>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countLearnersByCompany(Class<CompanyLearnerUsers> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from CompanyLearnerUsers o where o.company.id = :companyID";
		return dao.countWhere(filters, hql);
	}
	/**
	 * Get count of CompanyLearnerUsers for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the CompanyLearnerUsers
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyLearnerUsers.class);
	}

	/**
	 * Lazy load CompanyLearnerUsers with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1    CompanyLearnerUsers class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.CompanyLearnerUsers} containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnerUsers> allCompanyLearnerUsers(Class<CompanyLearnerUsers> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<CompanyLearnerUsers>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of CompanyLearnerUsers for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity  CompanyLearnerUsers class
	 * @param filters the filters
	 * @return Number of rows in the CompanyLearnerUsers entity
	 * @throws Exception the exception
	 */
	public int count(Class<CompanyLearnerUsers> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void createNewLearner(Users activeUser, Users entity, CompanyLearnerUsers companyLearnerUsers, ArrayList<UsersLanguage> usersLanguages, List<UsersDisability> usersDisabilityList, boolean requireGaurdian) throws Exception {
		if (entity.getId() == null) {
			usersService.checkEmailUsedEmailOnly(entity.getEmail());								
		}else if (entity.getId() != null) {
			if(countCompanyLearnerUsers(entity,companyLearnerUsers.getCompany())> 0) {
				throw new Exception("This learner has already been linked to this company");
			}
		}
		
		if(requireGaurdian && entity.getLegalGaurdian() != null) {
			usersService.checkEmailUsedEmailOnly(entity.getLegalGaurdian().getEmail());
		}
	
		List<IDataEntity> createBatch = new ArrayList<>();
		List<IDataEntity> updateBatch = new ArrayList<>();
		String pwdLearner = "";
		Users gaurdian = null;
		if (requireGaurdian) {
			gaurdian = entity.getLegalGaurdian();
		} else {
			gaurdian = null;
			entity.setLegalGaurdian(gaurdian);
		}
		
		if (gaurdian != null) {
			if (gaurdian.getId() == null) {
				AddressService.instance().saveAddresses(gaurdian.getResidentialAddress(), gaurdian.getPostalAddress());
				String pwd = GenericUtility.genPassord();
				gaurdian.setPassword(LogonService.encryptPassword(pwd));
				gaurdian.setStatus(UsersStatusEnum.EmailNotConfrimed);
				gaurdian.setEmailGuid(UUID.randomUUID().toString());
				gaurdian.setRegistrationDate(new java.util.Date());
				gaurdian.setAdmin(false);
				gaurdian.setAcceptPopi(false);
				createBatch.add(gaurdian);
			} else {
				updateBatch.add(gaurdian);
			}

			entity.setLegalGaurdian(gaurdian);
		}
		AddressService.instance().saveAddresses(entity.getResidentialAddress(), entity.getPostalAddress());
		
		if (entity.getId() == null) {
			pwdLearner = GenericUtility.genPassord();
			new LogonService();
			entity.setPassword(LogonService.encryptPassword(pwdLearner));
			entity.setStatus(UsersStatusEnum.EmailNotConfrimed);
			entity.setEmailGuid(UUID.randomUUID().toString());
			entity.setRegistrationDate(new java.util.Date());
			entity.setAdmin(false);
			entity.setAcceptPopi(false);
			createBatch.add(entity);
		} else {
			updateBatch.add(entity);
		}
		
		if (usersLanguages != null && usersLanguages.size() > 0) {
			if (entity != null && entity.getId() != null) {
				UsersLanguageService uls = new UsersLanguageService();
				List<UsersLanguage> list = (ArrayList<UsersLanguage>) uls.findByUser(entity);
				if (list != null && list.size() > 0) {
					List<IDataEntity> usersLanguagesList = new ArrayList<>();
					usersLanguagesList.addAll(list);
					dao.deleteBatch(usersLanguagesList);
				}
			}

			for (UsersLanguage ul : usersLanguages) {
				ul.setUser(entity);
				createBatch.add(ul);
			}
		}

		if (usersDisabilityList != null && usersDisabilityList.size() > 0) {
			if (entity != null && entity.getId() != null) {
				UsersDisabilityService usersDisabilityService = new UsersDisabilityService();
				List<UsersDisability> usersDisabilitylistRemove = (ArrayList<UsersDisability>) usersDisabilityService.findByKeyUser(entity);
				if (usersDisabilitylistRemove != null && usersDisabilitylistRemove.size() > 0) {
					List<IDataEntity> usersDisabilitylistIDataEntity = new ArrayList<>();
					usersDisabilitylistIDataEntity.addAll(usersDisabilitylistRemove);
					dao.deleteBatch(usersDisabilitylistIDataEntity);
				}
			}

			for (UsersDisability ud : usersDisabilityList) {
				ud.setUser(entity);
				createBatch.add(ud);
			}
		}
		
		companyLearnerUsers.setCreateUser(activeUser);
		companyLearnerUsers.setUser(entity);
		if (companyLearnerUsers.getId() == null) {
			createBatch.add(companyLearnerUsers);
		}else if (companyLearnerUsers.getId() != null) {
			updateBatch.add(companyLearnerUsers);
		}

		this.dao.createAndUpdateBatch(createBatch, updateBatch);
	}

	private int countCompanyLearnerUsers(Users entity, Company company) throws Exception{
		return dao.countCompanyLearnerUsers(entity, company);
	}
		
}