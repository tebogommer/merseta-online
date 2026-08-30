package haj.com.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.SortOrder;

import haj.com.bean.GrantBean;
import haj.com.dao.EmployeesDAO;
import haj.com.entity.Company;
import haj.com.entity.Employees;
import haj.com.entity.EmployeesHistory;
import haj.com.entity.EmployeesImport;
import haj.com.entity.EmployeesImportTraining;
import haj.com.entity.EmployeesTraining;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompletedPlannedEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeesService.
 */
public class EmployeesService extends AbstractService {
	/** The dao. */
	private EmployeesDAO dao = new EmployeesDAO();

	/** The employees import service. */
	private EmployeesImportService employeesImportService = new EmployeesImportService();

	/**
	 * Get all Employees.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Employees}
	 * @throws Exception
	 *             the exception
	 * @see Employees
	 */
	public List<Employees> allEmployees() throws Exception {
		return dao.allEmployees();
	}

	/**
	 * Create or update Employees.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Employees
	 */
	public void create(Employees entity) throws Exception {
		// validate(entity);
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}
	
	/**
	 * Create or update Employees And Create Task.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Employees
	 */
	public void createEmpTask(Employees entity,Users users) throws Exception {
		// validate(entity);
		if (entity.getId() == null) {
			this.dao.create(entity);
			//Creating workflow
			String desc="Employee has been added, please approve the information provided";
			TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.NEW_EMPLOYEE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
			
		} else{
			EmployeesHistoryService employeesHistoryService=new EmployeesHistoryService();
			employeesHistoryService.createHistory(entity.getId());
			this.dao.update(entity);
			//Creating workflow
			String desc="Employee has been updayed, please approve the information provided";
			TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.EMPLOYEE_CHANGE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		}
	}

	/**
	 * Creates the.
	 *
	 * @param employees
	 *            the employees
	 * @throws Exception
	 *             the exception
	 */
	public void create(List<IDataEntity> employees) throws Exception {
		for (IDataEntity entity : employees) {
			this.dao.create(entity);
		}
	}

	public void createFromBean(GrantBean discretionaryGrantBean, Wsp wsp, EmployedUnEmployedEnum enum1, EmploymentStatusEnum enum2) throws Exception {
		List<IDataEntity> employees = new ArrayList<>();
		for (int i = 0; i < discretionaryGrantBean.getAmount(); i++) {
			Employees employee = new Employees();
			employee.setGender(discretionaryGrantBean.getGender());
			employee.setEquity(discretionaryGrantBean.getEquity());
			employee.setEmployedUnEmployed(enum1);
			employee.setEmploymentStatus(enum2);
			employee.setDateOfBirth(GenericUtility.deductMonthsFromDate(getSynchronizedDate(),discretionaryGrantBean.getAgeGroup().getMiddleAge() * 12));
			employee.setDisability(discretionaryGrantBean.getDisabilityStatus());

			employee.setAgeGroup(discretionaryGrantBean.getAgeGroup());
			employee.setWsp(wsp);

			EmployeesTraining employeesTraining = new EmployeesTraining();
			employeesTraining.setEmployee(employee);
			employeesTraining.setPivotNonPivot(discretionaryGrantBean.getPivotNonPivot());
			employeesTraining.setCompletedPlanned(CompletedPlannedEnum.Planned);
			employees.add(employee);
			employees.add(employeesTraining);
		}
		dao.createBatch(employees);
	}

	/**
	 * Validate.
	 *
	 * @param emp
	 *            the emp
	 * @throws Exception
	 *             the exception
	 */
	private void validate(Employees emp) throws Exception {
		if (StringUtils.isBlank(emp.getSdlNumber()) && StringUtils.isBlank(emp.getSiteNumber())) {
			throw new Exception("Enter either SDL or Site number");
		}

	}

	/**
	 * Update Employees.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Employees
	 */
	public void update(Employees entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Update Employees.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Employees
	 */
	public void createEMPTraining(EmployeesTraining entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Delete Employees.
	 *
	 * @author TechFinium
	 * @param emp
	 *            the emp
	 * @throws Exception
	 *             the exception
	 * @see Employees
	 */
	public void delete(Employees emp) throws Exception {
		employeesImportService.delete(findEmployeesImport(emp));
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		entityList.addAll(allEmployeesTraining(emp));
		entityList.add(emp);
		dao.deleteBatch(entityList);
	}

	/**
	 * Delete.
	 *
	 * @param emp
	 *            the emp
	 * @throws Exception
	 *             the exception
	 */
	public void delete(EmployeesTraining emp) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		entityList.add(findEmployeesTrainingImport(emp));
		entityList.add(emp);
		dao.deleteBatch(entityList);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Employees}
	 * @throws Exception
	 *             the exception
	 * @see Employees
	 */
	public Employees findByKey(long id) throws Exception {
		return resolveTrainingReturn(dao.findByKey(id));
	}

	/**
	 * Find Employees by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.Employees}
	 * @throws Exception
	 *             the exception
	 * @see Employees
	 */
	public List<Employees> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Employees.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.Employees}
	 * @throws Exception
	 *             the exception
	 */
	public List<Employees> allEmployees(int first, int pageSize) throws Exception {
		return dao.allEmployees(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of Employees for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Employees
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(Employees.class);
	}

	/**
	 * Lazy load Employees with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Employees class
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
	 * @param wsp
	 *            the wsp
	 * @return a list of {@link haj.com.entity.Employees} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Employees> allEmployees(Class<Employees> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		if (company == null)
			return null;
		return (List<Employees>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters, company.getId(), false);

	}

	/**
	 * Resolve training.
	 *
	 * @param list
	 *            the list
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	private List<Employees> resolveTraining(List<Employees> list) throws Exception {
		for (Employees emp : list) {
			resolveTraining(emp);
		}
		return list;
	}

	/**
	 * Resolve training.
	 *
	 * @param emp
	 *            the emp
	 * @throws Exception
	 *             the exception
	 */
	public void resolveTraining(Employees emp) throws Exception {
		emp.setPivotalTrainingDone(findByEmplooyeePlannedDonePivotNonPivot(emp, CompletedPlannedEnum.Completed,
				PivotNonPivotEnum.Pivotal));
		emp.setPivotalTrainingPlanned(
				findByEmplooyeePlannedDonePivotNonPivot(emp, CompletedPlannedEnum.Planned, PivotNonPivotEnum.Pivotal));

		emp.setNonpivotalTrainingDone(findByEmplooyeePlannedDonePivotNonPivot(emp, CompletedPlannedEnum.Completed,
				PivotNonPivotEnum.NonPivotal));
		emp.setNonpivotalTrainingPlanned(findByEmplooyeePlannedDonePivotNonPivot(emp, CompletedPlannedEnum.Planned,
				PivotNonPivotEnum.NonPivotal));
	}

	/**
	 * Resolve training return.
	 *
	 * @param emp
	 *            the emp
	 * @return the employees
	 * @throws Exception
	 *             the exception
	 */
	public Employees resolveTrainingReturn(Employees emp) throws Exception {
		emp.setPivotalTrainingDone(findByEmplooyeePlannedDonePivotNonPivot(emp, CompletedPlannedEnum.Completed,
				PivotNonPivotEnum.Pivotal));
		emp.setPivotalTrainingPlanned(
				findByEmplooyeePlannedDonePivotNonPivot(emp, CompletedPlannedEnum.Planned, PivotNonPivotEnum.Pivotal));

		emp.setNonpivotalTrainingDone(findByEmplooyeePlannedDonePivotNonPivot(emp, CompletedPlannedEnum.Completed,
				PivotNonPivotEnum.NonPivotal));
		emp.setNonpivotalTrainingPlanned(findByEmplooyeePlannedDonePivotNonPivot(emp, CompletedPlannedEnum.Planned,
				PivotNonPivotEnum.NonPivotal));
		return emp;
	}

	/**
	 * All employees not employed.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Employees> allEmployeesNotEmployed(Wsp wsp) throws Exception {
		return resolveTraining(dao.allEmployeesNotEmployed(wsp));
	}

	/**
	 * Get count of Employees for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            Employees class
	 * @param filters
	 *            the filters
	 * @param wsp
	 *            the wsp
	 * @return Number of rows in the Employees entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<Employees> entity, Map<String, Object> filters, Company company) throws Exception {
		if (company == null)
			return 0;
		return dao.count(entity, filters, company.getId(), false);
	}

	/**
	 * Find by emplooyee planned done pivot non pivot.
	 *
	 * @param emp
	 *            the emp
	 * @param completedPlanned
	 *            the completed planned
	 * @param pivotNonPivot
	 *            the pivot non pivot
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<EmployeesTraining> findByEmplooyeePlannedDonePivotNonPivot(Employees emp,
			CompletedPlannedEnum completedPlanned, PivotNonPivotEnum pivotNonPivot) throws Exception {
		return dao.findByEmplooyeePlannedDonePivotNonPivot(emp.getId(), completedPlanned, pivotNonPivot);
	}

	/**
	 * Count gender by equity for wsp.
	 *
	 * @param equityId
	 *            the equity id
	 * @param genderId
	 *            the gender id
	 * @param wspId
	 *            the wsp id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countGenderByEquityForWsp(Long equityId, Long genderId, Long wspId) throws Exception {
		return dao.countGenderByEquityForWsp(equityId, genderId, wspId);
	}

	/**
	 * Count disability by equity for wsp.
	 *
	 * @param equityId
	 *            the equity id
	 * @param disabilityId
	 *            the disability id
	 * @param wspId
	 *            the wsp id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countDisabilityByEquityForWsp(Long equityId, Long disabilityId, Long wspId) throws Exception {
		return dao.countDisabilityByEquityForWsp(equityId, disabilityId, wspId);
	}

	/**
	 * Count disabled by equity for wsp.
	 *
	 * @param equityId
	 *            the equity id
	 * @param wspId
	 *            the wsp id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countDisabledByEquityForWsp(Long equityId, Long wspId) throws Exception {
		return dao.countDisabledByEquityForWsp(equityId, wspId);
	}

	/**
	 * Count by employment status enum for wsp.
	 *
	 * @param employStat
	 *            the employ stat
	 * @param wspId
	 *            the wsp id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countByEmploymentStatusEnumForWsp(EmploymentStatusEnum employStat, Long wspId) throws Exception {
		return dao.countByEmploymentStatusEnumForWsp(employStat, wspId);
	}

	/**
	 * Count by age range for wsp.
	 *
	 * @param fromAge
	 *            the from age
	 * @param toAge
	 *            the to age
	 * @param wspId
	 *            the wsp id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 * @throws ValidationException
	 *             the validation exception
	 */
	public Long countByAgeRangeForWsp(Integer fromAge, Integer toAge, Long wspId)
			throws Exception, ValidationException {
		if (fromAge > toAge)
			throw new ValidationException("Date Range Incorrect");
		Integer signedIntMultiplier = -1;
		Calendar fromDate = Calendar.getInstance();
		Calendar toDate = Calendar.getInstance();
		fromDate.add(Calendar.YEAR, signedIntMultiplier * fromAge);
		toDate.add(Calendar.YEAR, signedIntMultiplier * toAge);
		Date startDate = fromDate.getTime();
		Date endDate = toDate.getTime();
		return dao.countByAgeRangeForWsp(startDate, endDate, wspId);
	}

	/**
	 * Count employees training by age range for wsp.
	 *
	 * @param fromAge
	 *            the from age
	 * @param toAge
	 *            the to age
	 * @param wspId
	 *            the wsp id
	 * @param employedUnEmployed
	 *            the employed un employed
	 * @param completedPlanned
	 *            the completed planned
	 * @param pivotNonPivot
	 *            the pivot non pivot
	 * @return the long
	 * @throws Exception
	 *             the exception
	 * @throws ValidationException
	 *             the validation exception
	 */
	public Long countEmployeesTrainingByAgeRangeForWsp(Integer fromAge, Integer toAge, Long wspId,
			EmployedUnEmployedEnum employedUnEmployed, CompletedPlannedEnum completedPlanned,
			PivotNonPivotEnum pivotNonPivot) throws Exception, ValidationException {
		if (fromAge > toAge)
			throw new ValidationException("Date Range Incorrect");
		Integer signedIntMultiplier = -1;
		Calendar fromDate = Calendar.getInstance();
		Calendar toDate = Calendar.getInstance();
		fromDate.add(Calendar.YEAR, signedIntMultiplier * fromAge);
		toDate.add(Calendar.YEAR, signedIntMultiplier * toAge);
		Date startDate = fromDate.getTime();
		Date endDate = toDate.getTime();
		return dao.countEmployeesTrainingByAgeRangeForWsp(startDate, endDate, wspId, employedUnEmployed,
				completedPlanned, pivotNonPivot);
	}

	/**
	 * Count employees training gender by equity for wsp.
	 *
	 * @param equityId
	 *            the equity id
	 * @param genderId
	 *            the gender id
	 * @param wspId
	 *            the wsp id
	 * @param employedUnEmployed
	 *            the employed un employed
	 * @param completedPlanned
	 *            the completed planned
	 * @param pivotNonPivot
	 *            the pivot non pivot
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countEmployeesTrainingGenderByEquityForWsp(Long equityId, Long genderId, Long wspId,
			EmployedUnEmployedEnum employedUnEmployed, CompletedPlannedEnum completedPlanned,
			PivotNonPivotEnum pivotNonPivot) throws Exception {
		return dao.countEmployeesTrainingGenderByEquityForWsp(equityId, genderId, wspId, employedUnEmployed,
				completedPlanned, pivotNonPivot);
	}

	/**
	 * Count employees training disability by equity for wsp.
	 *
	 * @param equityId
	 *            the equity id
	 * @param disabilityId
	 *            the disability id
	 * @param wspId
	 *            the wsp id
	 * @param employedUnEmployed
	 *            the employed un employed
	 * @param completedPlanned
	 *            the completed planned
	 * @param pivotNonPivot
	 *            the pivot non pivot
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countEmployeesTrainingDisabilityByEquityForWsp(Long equityId, Long disabilityId, Long wspId,
			EmployedUnEmployedEnum employedUnEmployed, CompletedPlannedEnum completedPlanned,
			PivotNonPivotEnum pivotNonPivot) throws Exception {
		return dao.countEmployeesTrainingDisabilityByEquityForWsp(equityId, disabilityId, wspId, employedUnEmployed,
				completedPlanned, pivotNonPivot);
	}

	/**
	 * Count employees training disabled by equity for wsp.
	 *
	 * @param equityId
	 *            the equity id
	 * @param wspId
	 *            the wsp id
	 * @param employedUnEmployed
	 *            the employed un employed
	 * @param completedPlanned
	 *            the completed planned
	 * @param pivotNonPivot
	 *            the pivot non pivot
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	// This depends on the data base entries for disability
	public Long countEmployeesTrainingDisabledByEquityForWsp(Long equityId, Long wspId,
			EmployedUnEmployedEnum employedUnEmployed, CompletedPlannedEnum completedPlanned,
			PivotNonPivotEnum pivotNonPivot) throws Exception {
		return dao.countEmployeesTrainingDisabledByEquityForWsp(equityId, wspId, employedUnEmployed, completedPlanned,
				pivotNonPivot);
	}

	/**
	 * Count employees training by employment status enum for wsp.
	 *
	 * @param wspId
	 *            the wsp id
	 * @param employedUnEmployed
	 *            the employed un employed
	 * @param completedPlanned
	 *            the completed planned
	 * @param pivotNonPivot
	 *            the pivot non pivot
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public Long countEmployeesTrainingByEmploymentStatusEnumForWsp(Long wspId,
			EmployedUnEmployedEnum employedUnEmployed, CompletedPlannedEnum completedPlanned,
			PivotNonPivotEnum pivotNonPivot) throws Exception {
		return dao.countEmployeesTrainingByEmploymentStatusEnumForWsp(wspId, employedUnEmployed, completedPlanned,
				pivotNonPivot);
	}

	/**
	 * All employees training.
	 *
	 * @param emp
	 *            the emp
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<EmployeesTraining> allEmployeesTraining(Employees emp) throws Exception {
		return dao.allEmployeesTraining(emp.getId());
	}

	/**
	 * Find employees import.
	 *
	 * @param emp
	 *            the emp
	 * @return the employees import
	 * @throws Exception
	 *             the exception
	 */
	public EmployeesImport findEmployeesImport(Employees emp) throws Exception {
		return dao.findEmployeesImport(emp.getId());
	}

	/**
	 * Find employees training import.
	 *
	 * @param empTraining
	 *            the emp training
	 * @return the employees import training
	 * @throws Exception
	 *             the exception
	 */
	public EmployeesImportTraining findEmployeesTrainingImport(EmployeesTraining empTraining) throws Exception {
		return dao.findEmployeesTrainingImport(empTraining.getId());
	}

	/**
	 * All employees employed.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Employees> allEmployeesEmployed(Wsp wsp) throws Exception {
		return dao.allEmployees();
	}

	/**
	 * All employees employed count.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long allEmployeesEmployedCount(Wsp wsp) throws Exception {
		return dao.allEmployeesEmployedCount(wsp);
	}

	/**
	 * All employees not employed count.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long allEmployeesNotEmployedCount(Wsp wsp) throws Exception {
		return dao.allEmployeesNotEmployedCount(wsp);
	}

	/**
	 * All employees training count.
	 *
	 * @param emp
	 *            the emp
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long allEmployeesTrainingCount(Employees emp) throws Exception {
		return dao.allEmployeesTrainingCount(emp.getId());
	}
	
	public Long countByCompany(Company company) throws Exception {
		return dao.countByCompany(company);
	}
	
	/**
	 * Approve new Employees
	 * @param employees
	 * 		the Employees
	 * @param task
	 * 		The Tasks
	 * @throws Exception 
	 */
	public void approveNewEmployeeTask(Employees employees,Tasks task) throws Exception
	{
		employees.setApprovalStatus(ApprovalEnum.Approved);
		update(employees);
		TasksService.instance().completeTask(task);
		//Sending Email to task creator
		UsersService usersService=new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "New Employee Approval", " New employees (" + employees.getFirstName() +" "+employees.getLastName()+ ") has been approved on the merSETA NSDMS system.", null);
	   	
	
	}
	
	
	/**
	 * Approve Employees changes
	 * @param employees
	 * 		the Employees
	 * @param task
	 * 		The Tasks
	 * @throws Exception 
	 */
	public void approveEmployeeChangesTask(Employees employees,Tasks task) throws Exception
	{
		employees.setApprovalStatus(ApprovalEnum.Approved);
		update(employees);
		TasksService.instance().completeTask(task);
		//Sending Email to task creator
		UsersService usersService=new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "Employee Changes Approval", " Changes for  employee (" + employees.getFirstName() +" "+employees.getLastName()+ ") has been approved on the merSETA NSDMS system.", null);
	   	
	
	}
	
	/**
	 * Reject Employees changes
	 * @param employees
	 * 		the Employees
	 * @param task
	 * 		The Tasks
	 * @throws Exception 
	 */
	public void rejectEmployeeChangesTask(Employees employees,EmployeesHistory employeesHistory, Tasks task) throws Exception
	{
		Long tempId=employees.getId();
		
		BeanUtils.copyProperties(employees, employeesHistory);
		employees.setId(tempId);
		update(employees);
		TasksService.instance().completeTask(task);
		//Sending Email to task creator
		UsersService usersService=new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "Employee Changes Rejection", " Changes for  employee (" + employees.getFirstName() +" "+employees.getLastName()+ ") has been rejected on the merSETA NSDMS system.", null);
	   	
	
	}
	
	/**
	 * Reject new Employees
	 * @param employees
	 * 		the Employees
	 * @param task
	 * 		The Tasks
	 * @throws Exception 
	 */
	public void rejectNewEmployeeTask(Employees employees,Tasks task) throws Exception
	{
		employees.setApprovalStatus(ApprovalEnum.Rejected);;
		update(employees);
		TasksService.instance().completeTask(task);
		//Sending Email to task creator
		UsersService usersService=new UsersService();
		GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "New Employee Rejection", " New employees (" + employees.getFirstName() +" "+employees.getLastName()+ ") has been rejected on the merSETA NSDMS system.", null);
	   	
	
	}
	
	/**
	 * Approve task to delete Employees from database.
	 *
	 * @author TechFinium
	 * @throws Exception 
	 * @see Employees
	 */
	public void approveEmployeeDeleteTask(Employees employees,Tasks task) throws Exception {
	
			EmployeesHistoryService.instance().deleteByForID(employees.getId());
			delete(employees);
			TasksService.instance().completeTask(task);
			UsersService usersService=new UsersService();
			GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "Deleting Employee", " Employee (" + employees.getFirstName() +" "+employees.getLastName()+ ") has been deleted.", null);
			
	}
	
	/**
	 * Reject Task to delete Employees.
	 *
	 * @author TechFinium
	 * @throws Exception 
	 * @see Employees
	 */
	public void rejectEmployeeDeleteTask(Employees employees,Tasks task) throws Exception {
	
		    employees.setApprovalStatus(ApprovalEnum.Rejected);
			update(employees);
			TasksService.instance().completeTask(task);
			UsersService usersService=new UsersService();
			GenericUtility.sendMail(usersService.findByKey(task.getCreateUser().getId()).getEmail(), "Deleting Employee", "The attempt to delete employee (" + employees.getFirstName() +" "+employees.getLastName()+ ") has been rejected.", null);
			
	}

	public Employees findEmployeeByIdAndCompany(Users user, Company company) {
		List<Employees> list = null;
		if(user.getRsaIDNumber() != null && user.getRsaIDNumber()!="") {
			list= dao.findEmployeeByRsaIdAndCompany(user.getRsaIDNumber(), company.getId());
		}else if(user.getPassportNumber() != null && user.getPassportNumber() !="") {
			list= dao.findEmployeeByPassportIdAndCompany(user.getPassportNumber(), company.getId());
		}
		 
		if(list!=null && list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}		
	}
	
	public Employees findEmployeeByIdAndCompany(String idOrPassport, Company company, boolean isRsaId) {
		List<Employees> list = null;
		if(isRsaId) {
			list= dao.findEmployeeByRsaIdAndCompany(idOrPassport, company.getId());
		}else if(!isRsaId) {
			list= dao.findEmployeeByPassportIdAndCompany(idOrPassport, company.getId());
		}
		 
		if(list!=null && list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}		
	}
	
	public Employees findEmployeeByIdOrPassport(Users user) {
		List<Employees> list = null;
		if(user.getRsaIDNumber() != null && user.getRsaIDNumber()!="") {
			list= dao.findEmployeeByRsaId(user.getRsaIDNumber());
		}else if(user.getPassportNumber() != null && user.getPassportNumber() !="") {
			list= dao.findEmployeeByPassportId(user.getRsaIDNumber());
		}
		 
		if(list!=null && list.size()>0) {
			return list.get(0);
		}else {
			return null;
		}		
	}
	
	public List<Employees> findEmployeesByCompanyId(Long companyId) {
		return dao.findEmployeesByCompanyId(companyId);
	}
}

