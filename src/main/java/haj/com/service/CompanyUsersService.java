package haj.com.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.validation.ValidationException;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.CompanyUsersDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyUsers;
import haj.com.entity.CompanyUsersHistory;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.SDFCompany;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.UsersResponsibilities;
import haj.com.entity.UsersResponsibilitiesHistory;
import haj.com.entity.WorkplaceMonitoring;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.AssessorModType;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.SubmissionEnum;
import haj.com.entity.enums.TrainingProviderFilterEnum;
import haj.com.entity.enums.TrancheEnum;
import haj.com.entity.lookup.CompanyUserPosition;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.entity.lookup.SDFType;
import haj.com.entity.lookup.UserResponsibility;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.CompanyUserPositionService;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RolesService;
import haj.com.service.lookup.SDFTypeService;
import haj.com.service.lookup.SizeOfCompanyService;
import haj.com.ui.CompanyInfoUI;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class CompanyUsersService.
 */
public class CompanyUsersService extends AbstractService {
	
	/** The dao. */
	private CompanyUsersDAO dao = new CompanyUsersDAO();

	/** The register service. */
	private RegisterService registerService = new RegisterService();
	private RegionService regionService;
	private CompanyUserPositionService companyUserPositionService = new CompanyUserPositionService();

	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();

	private ActiveContractDetailService activeContractDetailService = new ActiveContractDetailService();

	/**
	 * Creates the company users.
	 *
	 * @param company
	 *            the company
	 * @param user
	 *            the user
	 * @throws Exception
	 *             the exception
	 */
	public void createCompanyUsers(Company company, Users user) throws Exception {
		if (company.getId() == null) {
			this.dao.create(company);
		} else
			this.dao.update(company);
		if (company.getId() != null) {
			CompanyUsers cu = new CompanyUsers();
			cu = findByUserAndCompany(company.getId(), user.getId());
			if (cu == null) {
				cu = new CompanyUsers();
				cu.setCompany(company);
				cu.setUser(user);
				cu.setCompanyUserType(ConfigDocProcessEnum.Learner);
				create(cu);
			} else {
				update(cu);
			}
		}
	}

	/**
	 * Creates the company users check existence.
	 *
	 * @param company
	 *            the company
	 * @param user
	 *            the user
	 * @throws Exception
	 *             the exception
	 * @throws ValidationException
	 *             the validation exception
	 */
	public void createCompanyUsersCheckExistence(Company company, Users user) throws Exception, ValidationException {
		CompanyUsers cu = null;
		cu = findByUserAndCompany(user.getId(), company.getId());
		if (cu == null) {
			cu = new CompanyUsers();
			cu.setCompany(company);
			cu.setUser(user);
			cu.setCompanyUserType(ConfigDocProcessEnum.Learner);
			this.dao.create(cu);
		} else
			throw new ValidationException("Learner already registered with company");
	}

	/**
	 * Get all CompanyUsers.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.CompanyUsers}
	 * @throws Exception
	 *             the exception
	 * @see CompanyUsers
	 */
	public List<CompanyUsers> allCompanyUsers() throws Exception {
		return dao.allCompanyUsers();
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
		List<CompanyUsers> cuList = new ArrayList<>();
		List<Users> uList = new ArrayList<>();
		cuList = dao.findUsersTypeOfCompany(userType, companyId);
		for (CompanyUsers companyUsers : cuList) {
			uList.add(companyUsers.getUser());
		}
		return uList;
	}
	
	public List<Users> findDistinctUsersTypeOfCompany(ConfigDocProcessEnum userType, Long companyId) {
		List<CompanyUsers> cuList = new ArrayList<>();
		List<Users> uList = new ArrayList<>();
		cuList = dao.findUsersTypeOfCompany(userType, companyId);
		for (CompanyUsers companyUsers : cuList) {
			boolean addUser = true;
			for (Users user: uList) {
				if (user.getId().equals(companyUsers.getUser().getId())) {
					addUser= false;
					break;
				}
			}
			if (addUser) {
				uList.add(companyUsers.getUser());
			}
			
		}
		return uList;
	}

	/**
	 * Find users type of company.
	 * 
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	public List<Users> findCompanyContactPersonList(Long companyId) {
		List<CompanyUsers> cuList = new ArrayList<>();
		List<Users> uList = new ArrayList<>();
		cuList = dao.findCompanyContactPerson(companyId);
		for (CompanyUsers companyUsers : cuList) {
			uList.add(companyUsers.getUser());
		}

		return uList;
	}

	/**
	 * Find company contact person.
	 * 
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	public Users findCompanyContactPerson(Long companyId) {
		List<CompanyUsers> cuList = new ArrayList<>();
		Users user = new Users();
		cuList = dao.findCompanyContactPerson(companyId);
		for (CompanyUsers companyUsers : cuList) {
			user = companyUsers.getUser();
			break;
		}

		return user;
	}

	/**
	 * Create or update CompanyUsers.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyUsers
	 */
	public void create(CompanyUsers entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Creates the with reg.
	 *
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 */
	public void createWithReg(CompanyUsers entity, List<UserResponsibility> selectedResponsibilities) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		dataEntities.add(entity);

		if (entity.getId() == null) {
			Users users = entity.getUser();
			if (users.getId() == null) {
				checkEmailUsedEmailOnly(users.getEmail());
				users.setResidentialAddress(null);
				users.setPostalAddress(null);
				registerService.register(users, true);
			} else {
				checkEmailUsedEmailOnlyWithUserId(users.getEmail(), users.getId());
			}
			this.dao.createBatch(dataEntities);
			if (selectedResponsibilities != null && !selectedResponsibilities.isEmpty()) {
				for (UserResponsibility responsibility : selectedResponsibilities) {
					dao.create(new UsersResponsibilities(entity, responsibility));
				}
			}
		} else {
			Users users = entity.getUser();
			if (users.getId() != null) {
				checkEmailUsedEmailOnlyWithUserId(users.getEmail(), users.getId());
			}
			this.dao.updateBatch(dataEntities);
			// dao.deleteBatch(entityList);
			List<UsersResponsibilities> ur = UsersResponsibilitiesService.instance().findByCompanyUser(entity);
			dao.deleteBatch((List<IDataEntity>) (List<?>) ur);
			if (selectedResponsibilities != null && !selectedResponsibilities.isEmpty()) {
				for (UserResponsibility responsibility : selectedResponsibilities) {
					dao.create(new UsersResponsibilities(entity, responsibility));
				}
			}
		}
	}

	/**
	 * Creates CompanyUser and Create Task.
	 *
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 */
	public void createWithRegAndCreatetask(CompanyUsers entity, List<UserResponsibility> selectedResponsibilities,
			Users user) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		dataEntities.add(entity);

		if (entity.getId() == null) {
			Users users = entity.getUser();
			if (users.getId() == null) {
				checkEmailUsedEmailOnly(users.getEmail());
				users.setResidentialAddress(null);
				users.setPostalAddress(null);
				registerService.register(users, true);
			}
			this.dao.createBatch(dataEntities);
			if (!entity.getNoResponsibility()) {
				for (UserResponsibility responsibility : selectedResponsibilities) {
					dao.create(new UsersResponsibilities(entity, responsibility));
				}
			}

			// Adding Change Reason
			ChangeReasonService.instance().createChangeReason(entity.getId(), entity.getClass().getName(),
					CompanyInfoUI.changeReason);

			// Creating workflow
			String desc = "Contact person has been added, please approve the information provided";
			TasksService.instance().findFirstInProcessAndCreateTask(desc, user, entity.getId(),
					entity.getClass().getName(), true, ConfigDocProcessEnum.NEW_CONTACT_PERSON,
					MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);

		} else {
			this.dao.updateBatch(dataEntities);
			dao.update(entity.getUser());
			// dao.deleteBatch(entityList);
			List<UsersResponsibilities> ur = UsersResponsibilitiesService.instance().findByCompanyUser(entity);

			List<UsersResponsibilitiesHistory> usersResponsibilitiesHistories = UsersResponsibilitiesHistoryService
					.instance().findByCompanyUserID(entity.getId());
			if (usersResponsibilitiesHistories.size() > 0) {
				dao.deleteBatch((List<IDataEntity>) (List<?>) usersResponsibilitiesHistories);
			}

			// Creating UsersResponsibilitiesHistory
			UsersResponsibilitiesHistoryService.instance().createHistory(ur);
			CompanyUsersHistoryService companyUsersService = new CompanyUsersHistoryService();
			// Creating company user history
			companyUsersService.createHistory(entity.getId());

			dao.deleteBatch((List<IDataEntity>) (List<?>) ur);
			if (!entity.getNoResponsibility()) {
				for (UserResponsibility responsibility : selectedResponsibilities) {
					dao.create(new UsersResponsibilities(entity, responsibility));
				}
			}

			// Adding Change Reason
			ChangeReasonService.instance().createChangeReason(entity.getId(), entity.getClass().getName(),
					CompanyInfoUI.changeReason);
			// Creating workflow
			String desc = "Contact person has been updated, please approve the information provided";
			TasksService.instance().findFirstInProcessAndCreateTask(desc, user, entity.getId(),
					entity.getClass().getName(), true, ConfigDocProcessEnum.CONTACT_PERSONS_CHANGE,
					MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);

		}

	}

	/**
	 * Reject contact Person Changes Task
	 * 
	 * @param CompanyUsers
	 *            the CompanyUsers
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void rejectContactPersonChangeTask(CompanyUsers companyUsers, CompanyUsersHistory companyUsersHistory, List<UsersResponsibilitiesHistory> usersResponsibilitiesHistoriesList, Tasks task) throws Exception {
		Long tempID = companyUsers.getId();

		BeanUtils.copyProperties(companyUsers, companyUsersHistory);
		companyUsers.setId(tempID);
		update(companyUsers);
		// Deleting Current UsersResponsibilities
		List<UsersResponsibilities> ur = UsersResponsibilitiesService.instance().findByCompanyUser(companyUsers);
		dao.deleteBatch((List<IDataEntity>) (List<?>) ur);
		// Revert UsersResponsibilities
		for (UsersResponsibilitiesHistory urh : usersResponsibilitiesHistoriesList) {
			UsersResponsibilities usersResponsibilities = new UsersResponsibilities();
			BeanUtils.copyProperties(usersResponsibilities, urh);
			usersResponsibilities.setId(null);
			dao.create(usersResponsibilities);
		}

		TasksService.instance().completeTask(task);
		// Sending Email to task creator
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(),
				"Contact Person Rejection", "Contact Person (" + companyUsers.getUser().getFirstName() + " "
						+ companyUsers.getUser().getLastName() + ") has been rejected on the merSETA NSDMS system.",
				null);
		// Sending email to TrainingComittee
		GenericUtility.sendMail(companyUsers.getUser().getEmail(), "Contact Person Rejection",
				"You have been rejected as contact person for " + companyUsers.getCompany().getCompanyName(), null);

	}

	/**
	 * Update CompanyUsers.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyUsers
	 */
	public void update(CompanyUsers entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CompanyUsers.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyUsers
	 */
	public void delete(CompanyUsers entity) throws Exception {
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
	public CompanyUsers findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find object by companyUsers.
	 *
	 * @author TechFinium
	 * @param CompanyUsers
	 *            the CompanyUsers
	 * @return a {@link haj.com.entity.CompanyUsers}
	 * @throws Exception
	 *             global exception
	 * @see CompanyUsers
	 */
	public CompanyUsers findByCompanyUserID(Long id) throws Exception {
		return dao.findByCompanyUser(id);
	}

	/**
	 * Find by user and company.
	 *
	 * @param companyId
	 *            the company id
	 * @param userId
	 *            the user id
	 * @return the company users
	 * @throws Exception
	 *             the exception
	 */
	public CompanyUsers findByUserAndCompany(Long companyId, Long userId) throws Exception {
		List<CompanyUsers> cu = findByUserAndCompanyList(companyId, userId);
		return (cu.size() == 0) ? null : cu.get(0);
	}

	public List<CompanyUsers> findByUserAndCompanyList(Long companyId, Long userId) throws Exception {
		return dao.findByUserAndCompany(companyId, userId);
	}

	public List<CompanyUsers> findByUserAndCompanyAndType(Long companyId, Long userId,
			ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		return dao.findByUserAndCompanyType(companyId, userId, configDocProcessEnum);
	}

	public List<CompanyUsers> findByUserAndCompanyAndTypeAndDesignation(Long companyId, Long userId,
			ConfigDocProcessEnum configDocProcessEnum, Long designationID) throws Exception {
		return dao.findByUserAndCompanyAndTypeAndDesignation(companyId, userId, configDocProcessEnum, designationID);
	}
	
	public List<CompanyUsers> findByUserAndCompanyAndTypeAndDesignationIsNotNull(Long companyId, Long userId,ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		return dao.findByUserAndCompanyAndTypeAndDesignationIsNotNull(companyId, userId, configDocProcessEnum);
	}

	public List<CompanyUsers> findByUserAndCompanyAndTypeAndAssessorModType(Long companyId, Long userId,
			ConfigDocProcessEnum configDocProcessEnum, AssessorModType assessorModType) throws Exception {
		return dao.findByUserAndCompanyAndTypeAndAssessorModType(companyId, userId, configDocProcessEnum,
				assessorModType);
	}

	/**
	 * Find CompanyUsers by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CompanyUsers}
	 * @throws Exception
	 *             the exception
	 * @see CompanyUsers
	 */
	public List<CompanyUsers> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	public List<CompanyUsers> findByUserAndName(String desc, Users users) throws Exception {
		return dao.findByUserAndName(desc, users.getId());
	}

	/**
	 * Lazy load CompanyUsers.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyUsers}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyUsers> allCompanyUsers(int first, int pageSize) throws Exception {
		return dao.allCompanyUsers(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of CompanyUsers for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the CompanyUsers
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyUsers.class);
	}

	/**
	 * Lazy load CompanyUsers with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            CompanyUsers class
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
	 * @return a list of {@link haj.com.entity.CompanyUsers} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyUsers> allCompanyUsers(Class<CompanyUsers> class1, int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<CompanyUsers>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

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
	public List<Company> allTrainingProviders(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> parameters) {
		String hql = "select distinct o.company from CompanyUsers o where o.companyUserType = :companyUserType";
		return (List<Company>) dao.hqlAndParamOnly(Company.class, first, pageSize, sortField, sortOrder, parameters,
				hql);

	}

	public int countTrainingProviders(Map<String, Object> filters) {
		String hql = "select count(distinct o.company) from CompanyUsers o where o.companyUserType = :companyUserType";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Company> allTrainingProviders(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> parameters, TrainingProviderFilterEnum trainingProviderFilterEnum) throws Exception {
	    TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
		String hql = "select distinct o.company from CompanyUsers o where o.companyUserType = :companyUserType";
		trainingProviderApplicationService.findTrainingProviderApplicationStartDateBy();
		new ArrayList<>();
		new ArrayList<>();
		if (trainingProviderFilterEnum == TrainingProviderFilterEnum.NO_FILTER) {
			hql = "select distinct o.company from CompanyUsers o where o.companyUserType = :companyUserType";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.EXPIRE_FILTER) {
			hql = "select o from Company o inner join TrainingProviderApplication tpa on o.id = tpa.company.id where "
					+ "(DATE_FORMAT(tpa.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) > 0 and (DATE_FORMAT(tpa.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) < 10000 ";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.NO_VISIT_FILTER) {
			// int count = 0;
			// for (TrainingProviderApplication trainingProviderApplication :
			// trainingProviderApplicationList) {
			// monitoringDateList.add(trainingProviderMonitoringService.findMonitoringDate(trainingProviderApplication.getCompany()));
			// Date startDate =
			// GenericUtility.addYearsToDate(trainingProviderApplication.getStartDate(),
			// 1);
			// if
			// (monitoringDateList.get(count).getMonitoringDate().before(startDate)
			// &&
			// trainingProviderApplication.getCompany().getId() ==
			// monitoringDateList.get(count).getCompany().getId()) {
			// companyList.add(trainingProviderApplication.getCompany());
			// }
			// count++;
			// }

			hql = "select distinct o from Company o inner join TrainingProviderMonitoring tpm on tpm.company.id = o.id "
					+ " inner join TrainingProviderApplication tpa on o.id = tpa.company.id where "
					+ " ((DATE_FORMAT(tpa.startDate, '%Y%m%d') + 10000) < (select MIN(DATE_FORMAT(tpmt.monitoringDate, '%Y%m%d')) "
					+ " from Company ot inner join TrainingProviderMonitoring tpmt on tpmt.company.id = ot.id "
					+ " inner join TrainingProviderApplication tpat on ot.id = tpat.company.id order by tpmt.monitoringDate asc)) ";

		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.TWO_IN_FIVE_FILTER) {

			// companyList = dao.findCompanyProvider();

			hql = "select distinct o from Company o inner join TrainingProviderApplication tpa on o.id = tpa.company.id inner join TrainingProviderMonitoring tpm on tpm.company.id = o.id "
					+ " where tpm.monitoringDate between tpa.startDate and tpa.expiriyDate ";
			// + " and (select tpmt from TrainingProviderMonitoring tpmt group
			// by tpmt
			// having count(tpmt.company.id)>2)";
		}

		// return companyList;
		return (List<Company>) dao.hqlAndParamOnly(Company.class, first, pageSize, sortField, sortOrder, parameters,
				hql);
	}

	public int countTrainingProviders(Map<String, Object> filters,
			TrainingProviderFilterEnum trainingProviderFilterEnum) {
		String hql = "select count(distinct o.company) from CompanyUsers o where o.companyUserType = :companyUserType";
		if (trainingProviderFilterEnum == TrainingProviderFilterEnum.NO_FILTER) {
			hql = "select count(distinct o.company) from CompanyUsers o where o.companyUserType = :companyUserType";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.EXPIRE_FILTER) {
			hql = "select count(o) from Company o inner join TrainingProviderApplication tpa on o.id = tpa.company.id where "
					+ "(DATE_FORMAT(tpa.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) > 0 and (DATE_FORMAT(tpa.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) < 10000 ";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.NO_VISIT_FILTER) {
			hql = "select count(distinct o) from Company o inner join TrainingProviderMonitoring tpm on tpm.company.id = o.id "
					+ " inner join TrainingProviderApplication tpa on o.id = tpa.company.id where "
					+ " ((DATE_FORMAT(tpa.startDate, '%Y%m%d') + 10000) < (select MIN(DATE_FORMAT(tpmt.monitoringDate, '%Y%m%d')) "
					+ " from Company ot inner join TrainingProviderMonitoring tpmt on tpmt.company.id = ot.id "
					+ " inner join TrainingProviderApplication tpat on ot.id = tpat.company.id order by tpmt.monitoringDate asc)) ";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.TWO_IN_FIVE_FILTER) {
			hql = "select count(o) from Company o inner join TrainingProviderApplication tpa on o.id = tpa.company.id inner join TrainingProviderMonitoring tpm on tpm.company.id = o.id "
					+ " where tpm.monitoringDate between tpa.startDate and tpa.expiriyDate ";
			// + " and (select tpmt from TrainingProviderMonitoring tpmt group
			// by tpmt
			// having count(tpmt.company.id)>2)";
		}
		return dao.countWhere(filters, hql);
	}

	/**
	 * Version Two of all training providers list
	 * 
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param parameters
	 * @param trainingProviderFilterEnum
	 * @return List<Company>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Company> allTrainingProvidersVersionTwo(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> parameters, TrainingProviderFilterEnum trainingProviderFilterEnum) throws Exception {
		String hql = "select o from Company o where o.id in (select x.company.id from CompanyUsers x where x.companyUserType = :companyUserType) and o.nonSetaCompany = false";
		if (trainingProviderFilterEnum == TrainingProviderFilterEnum.NO_FILTER) {
			hql = "select o from Company o where o.id in (select x.company.id from CompanyUsers x where x.companyUserType = :companyUserType) and o.nonSetaCompany = false";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.EXPIRE_FILTER) {
			hql = "select o from Company o inner join TrainingProviderApplication tpa on o.id = tpa.company.id where "
					+ "(DATE_FORMAT(tpa.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) > 0 and (DATE_FORMAT(tpa.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) < 10000 and o.nonSetaCompany = false";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.NO_VISIT_FILTER) {
			hql = "select distinct o from Company o inner join TrainingProviderMonitoring tpm on tpm.company.id = o.id "
					+ " inner join TrainingProviderApplication tpa on o.id = tpa.company.id where "
					+ " ((DATE_FORMAT(tpa.startDate, '%Y%m%d') + 10000) < (select MIN(DATE_FORMAT(tpmt.monitoringDate, '%Y%m%d')) "
					+ " from Company ot inner join TrainingProviderMonitoring tpmt on tpmt.company.id = ot.id "
					+ " inner join TrainingProviderApplication tpat on ot.id = tpat.company.id order by tpmt.monitoringDate asc)) and o.nonSetaCompany = false";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.TWO_IN_FIVE_FILTER) {
			hql = "select distinct o from Company o inner join TrainingProviderApplication tpa on o.id = tpa.company.id inner join TrainingProviderMonitoring tpm on tpm.company.id = o.id " 
					+ " where tpm.monitoringDate between tpa.startDate and tpa.expiriyDate and o.nonSetaCompany = false";
		}
		return (List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, parameters, hql);
	}

	public int countAllTrainingProvidersVersionTwo(Map<String, Object> filters,
			TrainingProviderFilterEnum trainingProviderFilterEnum) {
		String hql = "select count(o) from Company o where o.id in (select x.company.id from CompanyUsers x where x.companyUserType = :companyUserType) and o.nonSetaCompany = false ";
		if (trainingProviderFilterEnum == TrainingProviderFilterEnum.NO_FILTER) {
			hql = "select count(o) from Company o where o.id in (select x.company.id from CompanyUsers x where x.companyUserType = :companyUserType) and o.nonSetaCompany = false";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.EXPIRE_FILTER) {
			hql = "select count(o) from Company o inner join TrainingProviderApplication tpa on o.id = tpa.company.id where "
					+ "(DATE_FORMAT(tpa.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) > 0 and (DATE_FORMAT(tpa.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) < 10000 and o.nonSetaCompany = false";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.NO_VISIT_FILTER) {
			hql = "select count(distinct o) from Company o inner join TrainingProviderMonitoring tpm on tpm.company.id = o.id "
					+ " inner join TrainingProviderApplication tpa on o.id = tpa.company.id where "
					+ " ((DATE_FORMAT(tpa.startDate, '%Y%m%d') + 10000) < (select MIN(DATE_FORMAT(tpmt.monitoringDate, '%Y%m%d')) "
					+ " from Company ot inner join TrainingProviderMonitoring tpmt on tpmt.company.id = ot.id and o.nonSetaCompany = false "
					+ " inner join TrainingProviderApplication tpat on ot.id = tpat.company.id order by tpmt.monitoringDate asc)) ";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.TWO_IN_FIVE_FILTER) {
			hql = "select count(o) from Company o inner join TrainingProviderApplication tpa on o.id = tpa.company.id inner join TrainingProviderMonitoring tpm on tpm.company.id = o.id "
					+ " where tpm.monitoringDate between tpa.startDate and tpa.expiriyDate and o.nonSetaCompany = false";
		}
		return dao.countWhere(filters, hql);
	}

	/**
	 * Version Two of all training providers list
	 * 
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param parameters
	 * @param trainingProviderFilterEnum
	 * @return List<Company>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<Company> allTrainingProvidersByRegionVersionTwo(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> parameters, TrainingProviderFilterEnum trainingProviderFilterEnum,
			Region region) throws Exception {
		parameters.put("regionId", region.getId());
		String hql = "select o from Company o left join fetch o.residentialAddress left join fetch o.postalAddress where o.id in (select x.company.id from CompanyUsers x where x.companyUserType = :companyUserType) and o.nonSetaCompany = false and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		if (trainingProviderFilterEnum == TrainingProviderFilterEnum.NO_FILTER) {
			hql = "select o from Company o left join fetch o.residentialAddress left join fetch o.postalAddress where o.id in (select x.company.id from CompanyUsers x where x.companyUserType = :companyUserType) and o.nonSetaCompany = false and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.EXPIRE_FILTER) {
			hql = "select distinct o from Company o left join fetch o.residentialAddress left join fetch o.postalAddress inner join TrainingProviderApplication tpa on o.id = tpa.company.id where "
					+ "(DATE_FORMAT(tpa.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) > 0 and (DATE_FORMAT(tpa.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) < 10000 and o.nonSetaCompany = false and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.NO_VISIT_FILTER) {
			hql = "select distinct o from Company o left join fetch o.residentialAddress left join fetch o.postalAddress inner join TrainingProviderMonitoring tpm on tpm.company.id = o.id "
					+ " inner join TrainingProviderApplication tpa on o.id = tpa.company.id where "
					+ " ((DATE_FORMAT(tpa.startDate, '%Y%m%d') + 10000) < (select MIN(DATE_FORMAT(tpmt.monitoringDate, '%Y%m%d')) "
					+ " from Company ot inner join TrainingProviderMonitoring tpmt on tpmt.company.id = ot.id "
					+ " inner join TrainingProviderApplication tpat on ot.id = tpat.company.id order by tpmt.monitoringDate asc)) and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId) and o.nonSetaCompany = false ";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.TWO_IN_FIVE_FILTER) {
			hql = "select distinct o from Company o left join fetch o.residentialAddress left join fetch o.postalAddress inner join TrainingProviderApplication tpa on o.id = tpa.company.id inner join TrainingProviderMonitoring tpm on tpm.company.id = o.id "
					+ " where tpm.monitoringDate between tpa.startDate and tpa.expiriyDate and o.nonSetaCompany = false and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		}
		return (List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, parameters, hql);
	}

	public int countAllTrainingProvidersByRegionVersionTwo(Map<String, Object> filters,
			TrainingProviderFilterEnum trainingProviderFilterEnum) {
		String hql = "select count(o) from Company o where o.id in (select x.company.id from CompanyUsers x where x.companyUserType = :companyUserType) and o.nonSetaCompany = false and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		if (trainingProviderFilterEnum == TrainingProviderFilterEnum.NO_FILTER) {
			hql = "select count(o) from Company o where o.id in (select x.company.id from CompanyUsers x where x.companyUserType = :companyUserType) and o.nonSetaCompany = false and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.EXPIRE_FILTER) {
			hql = "select count(o) from Company o inner join TrainingProviderApplication tpa on o.id = tpa.company.id where "
					+ "(DATE_FORMAT(tpa.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) > 0 and (DATE_FORMAT(tpa.expiriyDate, '%Y%m%d') - DATE_FORMAT(current_date(), '%Y%m%d')) < 10000 and o.nonSetaCompany = false and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.NO_VISIT_FILTER) {
			hql = "select count(distinct o) from Company o inner join TrainingProviderMonitoring tpm on tpm.company.id = o.id "
					+ " inner join TrainingProviderApplication tpa on o.id = tpa.company.id where "
					+ " ((DATE_FORMAT(tpa.startDate, '%Y%m%d') + 10000) < (select MIN(DATE_FORMAT(tpmt.monitoringDate, '%Y%m%d')) "
					+ " from Company ot inner join TrainingProviderMonitoring tpmt on tpmt.company.id = ot.id "
					+ " inner join TrainingProviderApplication tpat on ot.id = tpat.company.id order by tpmt.monitoringDate asc)) and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId) and o.nonSetaCompany = false";
		} else if (trainingProviderFilterEnum == TrainingProviderFilterEnum.TWO_IN_FIVE_FILTER) {
			hql = "select count(o) from Company o inner join TrainingProviderApplication tpa on o.id = tpa.company.id inner join TrainingProviderMonitoring tpm on tpm.company.id = o.id "
					+ " where tpm.monitoringDate between tpa.startDate and tpa.expiriyDate and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId)";
		}
		return dao.countWhere(filters, hql);
	}

	/**
	 * Get count of CompanyUsers for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            CompanyUsers class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CompanyUsers entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CompanyUsers> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Check if email is already used.
	 *
	 * @param email
	 *            the email
	 * @throws Exception
	 *             the exception
	 */
	public void checkEmailUsedEmailOnly(String email) throws Exception {
		Users u = dao.checkEmailUsedEmailOnly(email);
		if (u != null)
			throw new ValidationException(super.getEntryLanguage("user.already.email"));
	}
	
	public void checkEmailUsedEmailOnlyWithUserId(String email, Long uid) throws Exception {
		Users u = dao.checkEmailUsedWithUserId(email, uid);
		if (u != null)
			throw new ValidationException(super.getEntryLanguage("user.already.email"));
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
	public List<CompanyUsers> findByCompanyType(Long companyId, ConfigDocProcessEnum configDocProcessEnum)
			throws Exception {
		return dao.findByCompanyType(companyId, configDocProcessEnum);
	}

	public List<Users> findUsersByCompanyType(Long companyId, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		return dao.findUsersByCompanyType(companyId, configDocProcessEnum);
	}
	
	public Integer countUsersByCompanyType(Long companyId, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		return dao.countUsersByCompanyType(companyId, configDocProcessEnum);
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
	public List<CompanyUsers> findByCompany(Company company) throws Exception {
		return populateSDFType(dao.findByCompany(company.getId()));
	}

	public List<Users> findUsersByCompany(Company company) throws Exception {
		return dao.findUsersByCompany(company.getId());
	}
	
	public List<Users> findDistinctUsersByCompany(Company company) throws Exception {
		return dao.findDistinctUsersByCompany(company.getId());
	}

	public List<Users> findUsersByCompanyPopulateRoles(Company company) throws Exception {
		return populateAdditionalInformationList(dao.findUsersByCompany(company.getId()), company);
	}

	public List<Users> findDistinctUsersByCompanyPopulateRoles(Company company) throws Exception {
		return populateAdditionalInformationList(dao.findDistinctUsersByCompany(company.getId()), company);
	}

	public List<Users> findCompanyUsersByCompanyPopulateRoles(Company company) throws Exception {
		List<Users> list = new ArrayList<>();
		List<Users> listOne = dao.findCompanyUsersByCompanyPopulateRoles(company.getId());
		List<Users> listTwo = SDFCompanyService.instance().findSDFUsers(company);

		list.addAll(listOne);
		list.addAll(listTwo);
		super.removeDuplicatesFromList(list);
		return populateAdditionalInformationList(list, company);
	}

	private List<CompanyUsers> populateSDFType(List<CompanyUsers> companyUsers) throws Exception {
		for (CompanyUsers cu : companyUsers) {
			SDFCompany sdflink = SDFCompanyService.instance().findByCompanyAndUser(cu.getCompany(), cu.getUser());
			if (sdflink != null && sdflink.getSdfType() != null) {
				cu.setSdfLevel(sdflink.getSdfType().getDescription());
			}
		}
		return companyUsers;
	}

	public List<Users> findByCompanyNotProcess(Long companyId) throws Exception {
		return dao.findByCompanyNotProcess(companyId);
	}

	/**
	 * Find by company not SDF.
	 *
	 * @param company
	 *            the company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyUsers> findByCompanyNotSDF(Company company) throws Exception {
		return resolveUserResponsibilities(dao.findByCompanyNotSDF(company.getId()));
	}

	public Long findByUserTypeCount(Users users, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		return dao.findByUserTypeCount(users.getId(), configDocProcessEnum);
	}
	
	public Long findByUserTypeCountAndCanRegisterMentor(Users users, ConfigDocProcessEnum configDocProcessEnum, Boolean registerMentors) throws Exception {
		return dao.findByUserTypeCountAndCanRegisterMentor(users.getId(), configDocProcessEnum, registerMentors);
	}

	public Long findLearnerCount(Users users) throws Exception {
		return dao.findLearnerCount(users.getId());
	}

	public List<CompanyUsers> findByCompanyWithPrimary(Company company) throws Exception {
		return resolveUserResponsibilities(dao.findByCompanyWithPrimary(company.getId()));
	}

	public List<CompanyUsers> resolveUserResponsibilities(List<CompanyUsers> companyUsers) throws Exception {
		for (CompanyUsers companyUser : companyUsers) {
			companyUser.setSelectedResponsibilities(
					UsersResponsibilitiesService.instance().findByCompanyUser(companyUser));
		}
		return companyUsers;
	}

	public List<CompanyUsers> findByCompanyResponsibility(Company company, ConfigDocProcessEnum forProcess)
			throws Exception {
		return dao.findByCompanyResponsibility(company.getId(), forProcess);
	}

	public List<Users> findUsersByCompanyResponsibility(Company company, ConfigDocProcessEnum forProcess)
			throws Exception {
		return dao.findUsersByCompanyResponsibility(company.getId(), forProcess);
	}
	
	public List<Users> findUsersByCompanyInResponsibilityAndUserPosition(Users user, ConfigDocProcessEnum forProcess, Long userPosistion)
			throws Exception {
		return dao.findUsersByCompanyInResponsibilityAndUserPosition(user.getId(), forProcess, userPosistion);
	}
	
	public List<Users> findUsersByCompanyInResponsibilityAndUser(Users user, ConfigDocProcessEnum forProcess)
			throws Exception {
		return dao.findUsersByCompanyInResponsibilityAndUser(user.getId(), forProcess);
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
		List<CompanyUsers> l = dao.findByUser(user.getId());
		List<Company> l2 = new ArrayList<Company>();
		for (CompanyUsers cu : l) {
			cu.getCompany().setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(cu.getCompany()));
			l2.add(cu.getCompany());

		}

		return l2;
	}
	
	public List<Company> find() throws Exception {
		List<CompanyUsers> l = dao.find();
		List<Company> l2 = new ArrayList<Company>();
		for (CompanyUsers cu : l) {
			cu.getCompany().setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(cu.getCompany()));
			l2.add(cu.getCompany());

		}

		return l2;
	}

	public List<Company> findByUserType(Users users, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		List<Company> l2 = dao.findByUserType(users.getId(), configDocProcessEnum);
		for (Company cu : l2) {
			cu.setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(cu));
		}
		return l2;
	}

	/**
	 * Approve new contact Person Task
	 * 
	 * @param companyUsers
	 *            the CompanyUsers
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void approveNewContactPersonTask(CompanyUsers companyUsers, Tasks task) throws Exception {
		companyUsers.setApprovalStatus(ApprovalEnum.Approved);
		update(companyUsers);
		TasksService.instance().completeTask(task);

		// Sending Email to task creator
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(),
				"Contact Person Approval", " Contact Person (" + companyUsers.getUser().getFirstName() + " "
						+ companyUsers.getUser().getLastName() + ") has been approved on the merSETA NSDMS system.",
				null);

		// Sending email to user
		GenericUtility.sendMail(companyUsers.getUser().getEmail(), "Contact Person Approval",
				"You have been added as contact person user for " + companyUsers.getCompany().getCompanyName(), null);

	}

	/**
	 * Approve Company User Task
	 * 
	 * @param companyUsers
	 *            the CompanyUsers
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void approveNewCompanyUserTask(CompanyUsers companyUsers, Tasks task) throws Exception {
		companyUsers.setApprovalStatus(ApprovalEnum.Approved);
		update(companyUsers);
		TasksService.instance().completeTask(task);
		// Sending Email to task creator
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(),
				"Company User Rejection", " Company user (" + companyUsers.getUser().getFirstName() + " "
						+ companyUsers.getUser().getLastName() + ") has been rejected on the merSETA NSDMS system.",
				null);
		// Sending email to user
		GenericUtility.sendMail(companyUsers.getUser().getEmail(), "Company User Rejection",
				"You have been rejected as company user for " + companyUsers.getCompany().getCompanyName(), null);

	}

	/**
	 * Reject Company User Task
	 * 
	 * @param companyUsers
	 *            the CompanyUsers
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void rejectNewCompanyUserTask(CompanyUsers companyUsers, Tasks task) throws Exception {
		companyUsers.setApprovalStatus(ApprovalEnum.Rejected);
		update(companyUsers);
		TasksService.instance().completeTask(task);
		// Sending Email to task creator
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(),
				"Company User Approval", " Company user (" + companyUsers.getUser().getFirstName() + " "
						+ companyUsers.getUser().getLastName() + ") has been approved on the merSETA NSDMS system.",
				null);

		// Sending email to user
		GenericUtility.sendMail(companyUsers.getUser().getEmail(), "Company User Approval",
				"You have been added as company for " + companyUsers.getCompany().getCompanyName(), null);

	}

	/**
	 * Reject new contact Person Task
	 * 
	 * @param CompanyUsers
	 *            the CompanyUsers
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void rejectNewContactPersonTask(CompanyUsers companyUsers, Tasks task) throws Exception {
		companyUsers.setApprovalStatus(ApprovalEnum.Rejected);
		update(companyUsers);
		TasksService.instance().completeTask(task);
		// Sending Email to task creator
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(),
				"Contact Person Rejection", "Contact Person (" + companyUsers.getUser().getFirstName() + " "
						+ companyUsers.getUser().getLastName() + ") has been rejected on the merSETA NSDMS system.",
				null);

		// Sending email to TrainingComittee
		GenericUtility.sendMail(companyUsers.getUser().getEmail(), "Contact Person Rejection",
				"You have been rejected as contact person for " + companyUsers.getCompany().getCompanyName(), null);

	}

	/**
	 * Approve contact Person Change Task
	 * 
	 * @param companyUsers
	 *            the CompanyUsers
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void approveContactPersonChangeTask(CompanyUsers companyUsers, Tasks task) throws Exception {
		companyUsers.setApprovalStatus(ApprovalEnum.Approved);
		update(companyUsers);
		TasksService.instance().completeTask(task);
		// Sending Email to task creator
		UsersService usersService = new UsersService();
		// Sending email to user who created added the training committee
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(),
				"Contact Person Approval",
				"Changes made to  Contact Person (" + companyUsers.getUser().getFirstName() + " "
						+ companyUsers.getUser().getLastName() + ") has been approved on the merSETA NSDMS system.",
				null);

		// Sending email to TrainingComittee
		GenericUtility.sendMail(companyUsers.getUser().getEmail(), "Contact Person Approval",
				"You Contact Person changes has been approved ", null);

	}

	/**
	 * Approve task to delete Contact Person from database.
	 *
	 * @author TechFinium
	 * @throws Exception
	 * @see CompanyUsers
	 */
	public void approveCompanyUsersDeleteTask(CompanyUsers companyUsers, Tasks task,
			ArrayList<UsersResponsibilities> userResponsibilities) throws Exception {

		List<UsersResponsibilitiesHistory> usersResponsibilitiesHistories = UsersResponsibilitiesHistoryService
				.instance().findByCompanyUser(companyUsers);
		for (UsersResponsibilitiesHistory urh : usersResponsibilitiesHistories) {
			UsersResponsibilitiesHistoryService.instance().delete(urh);
		}
		CompanyUsersHistoryService companyUsersHistoryService = new CompanyUsersHistoryService();
		companyUsersHistoryService.deleteByForID(companyUsers.getId());

		UsersResponsibilitiesService usersResponsibilitiesService = new UsersResponsibilitiesService();
		for (UsersResponsibilities userResp : userResponsibilities) {
			usersResponsibilitiesService.delete(userResp);
		}
		delete(companyUsers);
		TasksService.instance().completeTask(task);
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(),
				"Deleting Contact Person", " Contact Person (" + companyUsers.getUser().getFirstName() + " "
						+ companyUsers.getUser().getLastName() + ") has been deleted.",
				null);
	}

	/**
	 * Reject Task to delete Contact Person.
	 *
	 * @author TechFinium
	 * @throws Exception
	 * @see CompanyUsers
	 */
	public void rejectCompanyUsersDeleteTask(CompanyUsers companyUsers, Tasks task) throws Exception {

		companyUsers.setApprovalStatus(ApprovalEnum.Rejected);
		update(companyUsers);
		TasksService.instance().completeTask(task);
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "Deleting Contact",
				"The attempt to delete contact person (" + companyUsers.getUser().getFirstName() + " "
						+ companyUsers.getUser().getLastName() + ") has been rejected.",
				null);

	}
	
	public List<CompanyUsers> findByCompanyAndUserPosition(Company company, Long userPosistion) throws Exception {
		return dao.findByCompanyAndUserPosition(company.getId(), userPosistion);
	}
	
	public List<CompanyUsers> findByCompanyIdProcessAndUserId(Long companyId, ConfigDocProcessEnum forProcess, Long userId) throws Exception {
		return dao.findByCompanyIdProcessAndUserId(companyId, forProcess, userId);
	}
	
	public List<CompanyUsers> findByCompanyIdProcessAndUserIdWithSdpDesignation(Long companyId, ConfigDocProcessEnum forProcess, Long userId, Boolean sdpDesignation) throws Exception {
		return dao.findByCompanyIdProcessAndUserIdWithSdpDesignation(companyId, forProcess, userId, sdpDesignation);
	}

	public List<CompanyUsers> findByCompanyResponsibilityAndUserPosition(Company company, ConfigDocProcessEnum forProcess, Long userPosistion) throws Exception {
		return dao.findByCompanyResponsibilityAndUserPosition(company.getId(), forProcess, userPosistion);
	}
	
	public List<CompanyUsers> findByCompanyUserPosition(Long companyId, Long userPosistion) throws Exception {
		return dao.findByCompanyUserPosition(companyId, userPosistion);
	}

	public List<CompanyUsers> findByCompanyNotInResponsibilityAndUserPosition(Company company,
			ConfigDocProcessEnum forProcess, Long userPosistion) throws Exception {
		return dao.findByCompanyNotInResponsibilityAndUserPosition(company.getId(), forProcess, userPosistion);
	}

	public List<Users> findUsersByCompanyNotInResponsibilityAndUserPosition(Company company,
			ConfigDocProcessEnum forProcess, Long userPosistion) throws Exception {
		return dao.findUsersByCompanyNotInResponsibilityAndUserPosition(company.getId(), forProcess, userPosistion);
	}

	/**
	 * Populates additional information against user object note company can be
	 * null however wont populate occupation information
	 * 
	 * @return List<Users>
	 * @throws Exception
	 */
	public List<Users> populateAdditionalInformationList(List<Users> usersList, Company selectedCompany)
			throws Exception {
		for (Users user : usersList) {
			populateAdditionalInformation(user, selectedCompany);
		}
		return usersList;
	}

	/**
	 * Populates additional information against user object note company can be
	 * null however wont populate occupation information
	 * 
	 * @param user
	 * @param selectedCompany
	 * @return user
	 */
	public Users populateAdditionalInformation(Users user, Company selectedCompany) throws Exception {
		if (selectedCompany != null) {
			/*
			 * populates the roles user is assigned to the company
			 */
			CompanyUsersService companyUsersService = new CompanyUsersService();
			List<CompanyUsers> companyUserRoles = companyUsersService.findByUserAndCompanyList(selectedCompany.getId(),
					user.getId());
			if (companyUserRoles != null && companyUserRoles.size() != 0) {
				int count = 1;
				String roles = "";
				for (CompanyUsers companyUsers : companyUserRoles) {
					// if company user type is null, position link will be
					// populated
					if (companyUsers.getCompanyUserType() != null) {
						if (companyUsers.getCompanyUserType() == ConfigDocProcessEnum.SDF) {
							roles += "SDF ";
							SDFCompany sdfcompany = SDFCompanyService.instance().findByCompanyAndUser(selectedCompany,
									user);
							if (sdfcompany != null
									&& (sdfcompany.getSdfType() != null && sdfcompany.getSdfType().getId() != null)) {
								SDFTypeService typeService = new SDFTypeService();
								SDFType type = typeService.findByKey(sdfcompany.getSdfType().getId());
								roles += "- " + type.getDescription();
								typeService = null;
							}
						} else {
							roles += "";
						}
					} else {
						if (companyUsers.getPosition() != null && companyUsers.getPosition().getId() != null) {
							CompanyUserPositionService posistionService = new CompanyUserPositionService();
							CompanyUserPosition posistion = posistionService
									.findByKey(companyUsers.getPosition().getId());
							if (posistion != null && posistion.getId() != null) {
								roles += posistion.getDescription();
							}
						} else {
							roles += "";
						}
					}
					// for spacing for multiple roles
					if (count != companyUserRoles.size()) {
						roles += " , ";
					}
					count++;
				}
				user.setRoles(roles);
			} else {
				user.setRoles(" ");
			}
			companyUsersService = null;
		} else {
			user.setRoles(" ");
		}
		return user;
	}

	public List<?> sortAndFilterSearch(Class<?> entity, int startingAt, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) {
		return dao.sortAndFilterSearch(entity, startingAt, pageSize, sortField, sortOrder, filters);
	}

	public int countSearch(Class<?> entity, Map<String, Object> filters) {
		return dao.countSearch(entity, filters);
	}

	public void resolveUsersPosition(List<CompanyUsers> list) throws Exception {
		for (CompanyUsers cu : list) {
			cu.setPosition(companyUserPositionService.findByKey(cu.getPosition().getId()));
		}
	}

	public void createTPContact(CompanyUsers entity) throws Exception {
		 TrainingProviderApplicationService trainingProviderApplicationService = new TrainingProviderApplicationService();
		entity.setCompanyUserType(ConfigDocProcessEnum.TP);
		if (entity.getId() == null) {
			if (entity.getSmeTypeEnum() != null) {
				assfacilitatorValidation(entity);
				trainingProviderApplicationService.avialabilityOfFacilitorAssModValidation(entity.getUser());
			} else {
				contactPersonValidation(entity);
			}
			Users users = entity.getUser();
			users.setResidentialAddress(null);
			users.setPostalAddress(null);
			if (users.getId() == null) {
				checkEmailUsedEmailOnly(users.getEmail());
				registerService.register(users, true);
			} else {
				UsersService usersService = new UsersService();
				usersService.update(users);
			}
			create(entity);
		} else {
			create(entity);
			UsersService usersService = new UsersService();
			usersService.update(entity.getUser());

		}
	}

	public void assfacilitatorValidation(CompanyUsers entity) throws Exception {
		if (entity.getUser().getId() != null && entity.getCompany().getId() != null) {
			List<CompanyUsers> list = dao.findByUserAndCompanyAndTypeNullDes(entity.getCompany().getId(),
					entity.getUser().getId(), ConfigDocProcessEnum.TP);
			if (list != null && list.size() > 0) {
				throw new Exception("This user already exist as an Assessor/Facilitator");
			}
		}
	}

	public void contactPersonValidation(CompanyUsers entity) throws Exception {
		if (entity.getUser().getId() != null && entity.getCompany().getId() != null) {
			List<CompanyUsers> list = dao.findByUserAndCompanyAndCompanyUserTypeNotNullDes(entity.getCompany().getId(), entity.getUser().getId(), ConfigDocProcessEnum.TP);
			if (list != null && list.size() > 0) {
				throw new Exception("This user already exist as a contact person");
			}
		}
	}

	public List<CompanyUsers> findTPContact(Long companyId, ConfigDocProcessEnum configDocProcessEnum)
			throws Exception {
		return dao.findTPContact(companyId, configDocProcessEnum);
	}

	public List<CompanyUsers> findTPAssessorMod(Long companyId, ConfigDocProcessEnum configDocProcessEnum) throws Exception {
		return dao.findTPAssessorMod(companyId, configDocProcessEnum);
	}
	
	public List<CompanyUsers> findTPAssessorModByModType(Long companyId, ConfigDocProcessEnum configDocProcessEnum, AssessorModType assessorModType) throws Exception {
		return dao.findTPAssessorModByModType(companyId, configDocProcessEnum, assessorModType);
	}
	
	public List<CompanyUsers> findTPAssessorModByModTypeList(Long companyId, ConfigDocProcessEnum configDocProcessEnum, List<AssessorModType> assessorModTypeList) throws Exception {
		return dao.findTPAssessorModByModTypeList(companyId, configDocProcessEnum, assessorModTypeList);
	}

	public void updateContactPerson(Users contactPerson, CompanyUsers selectedTPContsctPerson) throws Exception {
		if (contactPerson.getDesignation() != null) {
			selectedTPContsctPerson.setDesignation(contactPerson.getDesignation());
		}
		if (contactPerson.getAssessorModType() != null) {
			selectedTPContsctPerson.setAssessorModType(contactPerson.getAssessorModType());
		}
		UsersService usersService = new UsersService();
		dao.update(selectedTPContsctPerson);
		usersService.update(contactPerson);
	}

	public void completeWorkflow(Company entity, Users user, Tasks tasks) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(entity.getResidentialAddress().getTown());
		List<Users> users = hostingCompanyEmployeesService.findUserByRegionAndRole(rt.getRegion(),
				tasks.getProcessRole().getNextTaskRole());
		if (users == null || users.size() == 0) {
			throw new Exception("No Region Client Service Administrator for the region");
		}
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, users, false);
	}

	public void approveWorkflow(List<CompanyLearners> companyLearners, Company entity, Users user, Tasks tasks, ScheduledEvent scheduledEvent,CompanyLearners companyLearnerParent) throws Exception {
		RegionTown rt = RegionTownService.instance().findByTown(entity.getResidentialAddress().getTown());
		List<Users> users = hostingCompanyEmployeesService.findUserByRegionAndRole(rt.getRegion(), RolesService.instance().findByKey(HAJConstants.CLIENT_SERVICE_ADMIN_ROLE_ID));
		// List<Users> users = findRegionClinetServiceAdministrator(entity);
		List<Users> list = new ArrayList<>();
		if (users == null || users.size() == 0) {
			throw new Exception("No Region Client Service Administrator for the region");
		}
		
		for (CompanyLearners companyLearner : companyLearners) {
			list.add(companyLearner.getUser());
			list.add(companyLearner.getCreateUser());
			CompanyLearnersService s = new CompanyLearnersService();
			String description = "";
			if (scheduledEvent != null && scheduledEvent.getId() != null) {
				companyLearner.setScheduledEvent(scheduledEvent);
			}
			companyLearner.setSubmissionEnum(SubmissionEnum.ForFinalApproval);
			s.create(companyLearner);
			TasksService.instance().findFirstInProcessAndCreateTask(description, user, companyLearner.getId(),
					CompanyLearners.class.getName(), true, ConfigDocProcessEnum.CompanyLearner,
					MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName,
							user.getLastName(), MailTagsEnum.Task,
							ConfigDocProcessEnum.CompanyLearner.getTaskDescription()),
					users);
		}
		CompanyLearnersService s = new CompanyLearnersService();
		companyLearnerParent.setStatus(ApprovalEnum.PendingFinalApproval);
		companyLearnerParent.setApprovalDate(getSynchronizedDate());
		s.create(companyLearnerParent);
		if(tasks !=null) {
			TasksService.instance().completeTask(tasks);
		}
	}
	
	public void approveWorkflowtoHoldingRoom(List<CompanyLearners> companyLearners, Company entity, Users user, Tasks tasks,ScheduledEvent scheduledEvent,CompanyLearners companyLearnerParent) throws Exception {
		List<Users> list = new ArrayList<>();
		CompanyLearnersService s = new CompanyLearnersService();
		companyLearnerParent.setStatus(ApprovalEnum.PendingApproval);
		companyLearnerParent.setApprovalDate(getSynchronizedDate());
		for (CompanyLearners companyLearner : companyLearners) {
			list.add(companyLearner.getCreateUser());
			list.add(companyLearner.getUser());
			
			if (scheduledEvent != null && scheduledEvent.getId() != null) {
				companyLearner.setScheduledEvent(scheduledEvent);
			}
			
			companyLearner.setSubmissionEnum(SubmissionEnum.ForFinalApproval);
			s.create(companyLearner);
			
			
			//list = new ArrayList<>();
		}
		super.removeDuplicatesFromList(list);
		for(Users u : list) {
			sendReviewDateEmail(entity, u, scheduledEvent.getFromDateTime());
		}
		s.create(companyLearnerParent);
		TasksService.instance().completeTask(tasks);
	
	}
	
	public void sendUpdateReviewDateEmail(CompanyLearners companyLearnerParent, Company taskCompany, Users activeUser, Date reviewDate) throws Exception {
		CompanyLearnersService s = new CompanyLearnersService();
		List<Users>users = new ArrayList<>();
		users.add(taskCompany.getContactPerson());
		List<CompanyLearners>list = s.findCompanyLearnersByParent(companyLearnerParent.getId());
		for(CompanyLearners companyLearner :list) {
			users.add(companyLearner.getUser());
		}
		
		for(Users user :users) {
			sendReviewDateEmail(taskCompany, user, reviewDate);
		}
	}

	public void rejectWorkflow(CompanyLearners entity, Users user, Tasks tasks, List<RejectReasons> rejectReasons)
			throws Exception {
		CompanyLearnersService cls = new CompanyLearnersService();
		List<RejectReasonMultipleSelection> rrmList = RejectReasonMultipleSelectionService.instance().removeCreateLinks(
				entity.getId(), Company.class.getName(), rejectReasons, user, ConfigDocProcessEnum.LearnerReview);
		for (RejectReasonMultipleSelection rrm : rrmList) {
			if (rrm != null) {
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		entity.setStatus(ApprovalEnum.Rejected);
		cls.update(entity);
		List<Users> list = new ArrayList<>();
		list.add(entity.getCreateUser());
		// TasksService.instance().findPreviousInProcessAndCreateTask(user,
		// tasks,
		// null);
		TasksService.instance().findPreviousInProcessAndCreateTask("", user, entity.getId(), Company.class.getName(),
				true, tasks, null, list);
	}

	public void finalApproveWorkflow(List<CompanyLearners> companyUsers, Company entity, Users user, Tasks tasks, String documentBoxID) throws Exception {
		for (CompanyLearners companyUser : companyUsers) {
			companyUser.setStatus(ApprovalEnum.Approved);
			companyUser.setRegistrationNumber(CompanyLearnersService.instance().generateCompanyLearnerRegNumber(companyUser));
			companyUser.setLearnerStatus(LearnerStatusEnum.Registered);
			companyUser.setSubmissionEnum(SubmissionEnum.Completed);
			companyUser.setApprovalDate(getSynchronizedDate());
			companyUser.setDocumentBoxID(documentBoxID);
			if (HAJConstants.DEV_TEST_PROD != "P") {
				activeContractDetailService.addTranchePaymentDetail(companyUser, user, 0.25, TrancheEnum.TRANCHE_TWO, true);
			}
			WorkplaceMonitoring workplaceMonitoring = new WorkplaceMonitoring();
			workplaceMonitoring.setCompany(companyUser.getEmployer());
			workplaceMonitoring.setUsers(user);
			WorkplaceMonitoringService workplaceMonitoringService = new WorkplaceMonitoringService();
			workplaceMonitoringService.requesteWorkflow(workplaceMonitoring, user);
			dao.update(companyUser);
		}

		TasksService.instance().completeTask(tasks);
	}

	public void finalRejectWorkflow(List<CompanyLearners> companyUsers, Company entity, Users user, Tasks tasks)
			throws Exception {
		for (CompanyLearners companyUser : companyUsers) {
			companyUser.setStatus(ApprovalEnum.Rejected);
			companyUser.setApprovalDate(getSynchronizedDate());
			dao.update(entity);
		}
		TasksService.instance().completeTask(tasks);
	}

	public List<Users> findRegionClinetServiceAdministrator(Company company) throws Exception {
		RolesService rolesService = new RolesService();
		List<Users> list = new ArrayList<>();
		Roles roles = rolesService.findByKey(HAJConstants.CLIENT_SERVICE_ADMIN_ROLE_ID);
		list = hostingCompanyEmployeesService
				.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}

	public void sendAppointmentNotification(Users user) throws Exception {
		String subject = "Learner registration Appointment Approval".toUpperCase();
		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " "
				+ user.getLastName() + " </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Your learner registration appointment has been approved " + "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA Team </p>";
		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);
	}

	private void sendReviewDateEmail(Company taskCompany, Users user, Date reviewDate) throws Exception {

		RegionTown rt = new RegionTown();
		if (taskCompany.getResidentialAddress() != null && taskCompany.getResidentialAddress().getTown() != null) {
			rt = RegionTownService.instance().findByTown(taskCompany.getResidentialAddress().getTown());
		}
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = new Region();
		String regionDsec = "";
		if (rt.getRegion() != null) {
			r = regionService.findByKey(rt.getRegion().getId());
			regionDsec = r.getDescription();
		}

		DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat timeFormat = new SimpleDateFormat("HH:mm");
		String date = dateFormat.format(reviewDate);
		String time = timeFormat.format(reviewDate);

		Users clo = getCLO(taskCompany);
		String welcome = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " +"#FirstName# #Surname#,</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<p>Please be advised that an appointment has been scheduled at: #RegionName# on  #date# at #time# as part of the learner registration process.  "
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "You will be required to bring the original documents for each learner where a learner registration application has been submitted.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<p>Should there be a change in the scheduled date, please contact the Client Liaison Officer before the visit to schedule a new date.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<p>Yours sincerely,</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<p>merSETA Client Services</p>" + "<br/>";

		welcome = welcome.replaceAll("#FirstName#", user.getFirstName());
		welcome = welcome.replaceAll("#Surname#", user.getLastName());
		welcome = welcome.replaceAll("#ClientLiaisonOfficerNameAndSurname#", clo.getFirstName() + " " + clo.getLastName());
		welcome = welcome.replaceAll("#RegionName#", regionDsec);
		welcome = welcome.replaceAll("#date#", date);
		welcome = welcome.replaceAll("#time#", time);

		GenericUtility.sendMail(user.getEmail(), "LEARNER REGISTRATION REVIEW DATE", welcome,
				null);
	}

	public Users getCLO(Company company) throws Exception {
		Users cloUser = new Users();
		if (company.getResidentialAddress() != null && company.getResidentialAddress().getTown() != null) {
			RegionTown rt = RegionTownService.instance().findByTown(company.getResidentialAddress().getTown());
			cloUser = rt.getClo().getUsers();
		}
		return cloUser;
	}

	@SuppressWarnings("unchecked")
	public List<Company> providerMonitoringTrainingProviderCompaniesByRegion(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> parameters, Long regionId,
			List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList, Date date, ApprovalEnum status)
			throws Exception {
		String hql = "select o from Company o where o.id in "
				+ "( select x.company.id from TrainingProviderApplication x where "
				+ " x.approvalStatus = :approvedStatus and DATE(x.expiriyDate) > DATE(:date) ";
		if (accreditationApplicationTypeEnumList != null && accreditationApplicationTypeEnumList.size() != 0) {
			hql += " and ( ";
			int count = 1;
			for (AccreditationApplicationTypeEnum accreditationApplicationTypeEnum : accreditationApplicationTypeEnumList) {
				if (count == accreditationApplicationTypeEnumList.size()) {
					hql += " x.accreditationApplicationTypeEnum = :applicationType" + count + " ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum);
				} else {
					hql += " x.accreditationApplicationTypeEnum = :applicationType" + count + "  or ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum);
				}
				count++;
			}
			hql += " ) ";
		}
		hql += " ) and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId) ";
		parameters.put("regionId", regionId);
		parameters.put("approvedStatus", status);
		parameters.put("date", date);
		return (List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, parameters, hql);
	}

	public int countProviderMonitoringTrainingProviderCompaniesByRegion(Map<String, Object> filters,
			List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList) {
		String hql = "select count(o) from Company o where o.id in "
				+ "( select x.company.id from TrainingProviderApplication x where "
				+ " x.approvalStatus = :approvedStatus and DATE(x.expiriyDate) > DATE(:date) ";
		if (accreditationApplicationTypeEnumList != null && accreditationApplicationTypeEnumList.size() != 0) {
			hql += " and ( ";
			int count = 1;
			for (AccreditationApplicationTypeEnum accreditationApplicationTypeEnum : accreditationApplicationTypeEnumList) {
				if (count == accreditationApplicationTypeEnumList.size()) {
					hql += " x.accreditationApplicationTypeEnum = :applicationType" + count + " ";
				} else {
					hql += " x.accreditationApplicationTypeEnum = :applicationType" + count + "  or ";
				}
				count++;
			}
			hql += " ) ";
		}
		hql += " ) and o.residentialAddress.town.id in (select x.town.id from RegionTown x where x.region.id = :regionId) ";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Company> providerMonitoringTrainingProviderCompanies(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> parameters,
			List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList, Date date, ApprovalEnum status)
			throws Exception {
		String hql = "select o from Company o where o.id in "
				+ "( select x.company.id from TrainingProviderApplication x where "
				+ " x.approvalStatus = :approvedStatus and DATE(x.expiriyDate) > DATE(:date) ";
		if (accreditationApplicationTypeEnumList != null && accreditationApplicationTypeEnumList.size() != 0) {
			hql += " and ( ";
			int count = 1;
			for (AccreditationApplicationTypeEnum accreditationApplicationTypeEnum : accreditationApplicationTypeEnumList) {
				if (count == accreditationApplicationTypeEnumList.size()) {
					hql += " x.accreditationApplicationTypeEnum = :applicationType" + count + " ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum);
				} else {
					hql += " x.accreditationApplicationTypeEnum = :applicationType" + count + "  or ";
					parameters.put("applicationType" + count, accreditationApplicationTypeEnum);
				}
				count++;
			}
			hql += " ) ";
		}
		hql += " )";
		parameters.put("approvedStatus", status);
		parameters.put("date", date);
		return (List<Company>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, parameters, hql);
	}

	public int countProviderMonitoringTrainingProviderCompanies(Map<String, Object> filters,
			List<AccreditationApplicationTypeEnum> accreditationApplicationTypeEnumList) {
		String hql = "select count(o) from Company o where o.id in "
				+ "( select x.company.id from TrainingProviderApplication x where "
				+ " x.approvalStatus = :approvedStatus and DATE(x.expiriyDate) > DATE(:date) ";
		if (accreditationApplicationTypeEnumList != null && accreditationApplicationTypeEnumList.size() != 0) {
			hql += " and ( ";
			int count = 1;
			for (AccreditationApplicationTypeEnum accreditationApplicationTypeEnum : accreditationApplicationTypeEnumList) {
				if (count == accreditationApplicationTypeEnumList.size()) {
					hql += " x.accreditationApplicationTypeEnum = :applicationType" + count + " ";
				} else {
					hql += " x.accreditationApplicationTypeEnum = :applicationType" + count + "  or ";
				}
				count++;
			}
			hql += " ) ";
		}
		hql += " ) ";
		return dao.countWhere(filters, hql);
	}

	public Boolean checkIfCompanyContactCanRegisterLearner(Users activeUser, Company selectedCompany, ConfigDocProcessEnum forProcess) throws Exception {
		List<Users> list = dao.findUsersByCompanyNotInResponsibilityAndUser(selectedCompany.getId(), forProcess, activeUser.getId());
		if(list !=null && list.size() >=1) {
			return true;
		}else {
			return false;
		}
	}
	
	public List<Users> findSdpUsersByCompanyId(Long companyId, Boolean sdpDesignation) throws Exception {
		return dao.findSdpUsersByCompanyId(companyId, sdpDesignation);
	}
	
	public Users findSdpUsersByCompanyIdReturnFirstUser(Long companyId, Boolean sdpDesignation) throws Exception {
		List<Users> usersList =  dao.findSdpUsersByCompanyId(companyId, sdpDesignation);
		for (Users user : usersList) {
			return user;
		}
		return null;
	}
	
	public List<CompanyUsers> findAllSdpCompanyUsersLinkedToCompany(Boolean sdpDesignation) throws Exception {
		return dao.findAllSdpCompanyUsersLinkedToCompany(sdpDesignation);
	}
	
	public List<CompanyUsers> findAllAssModCompanyUsersLinkedToCompany() throws Exception {
		return dao.findAllAssModCompanyUsersLinkedToCompany();
	}
}
