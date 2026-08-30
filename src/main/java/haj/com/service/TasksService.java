package haj.com.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;

import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.model.SortOrder;

import haj.com.bean.CounterBean;
import haj.com.bean.DescriptionCounterBean;
import haj.com.bean.TaskProcessReport;
import haj.com.bean.TaskReportBean;
import haj.com.bean.TaskUserReportBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.TasksDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyProcess;
import haj.com.entity.ProcessRoles;
import haj.com.entity.Signoff;
import haj.com.entity.TaskUsers;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.UsersRole;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.RagEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.MailTemplatesService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class TasksService.
 */
public class TasksService extends AbstractService {

	private TasksDAO dao = new TasksDAO();
	private HostingCompanyProcessService hostingCompanyProcessService = new HostingCompanyProcessService();
	private ProcessRolesService processRolesService = new ProcessRolesService();
	private UsersRoleService usersRoleService = new UsersRoleService();
	private static TasksService tasksService = null;
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private UsersService usersService;
	private TaskUsersService taskUsersService;

	/**
	 * Instance.
	 *
	 * @return the tasks service
	 */
	public static synchronized TasksService instance() {
		if (tasksService == null) {
			tasksService = new TasksService();
		}
		return tasksService;
	}

	/**
	 * Instantiates a new tasks service.
	 */
	public TasksService() {
		super();
	}

	/**
	 * Instantiates a new tasks service.
	 *
	 * @param auditlog
	 *            the auditlog
	 * @param resourceBundle
	 *            the resource bundle
	 */
	public TasksService(Map<String, Object> auditlog, ResourceBundle resourceBundle) {
		super(auditlog, resourceBundle);
	}

	/**
	 * Instantiates a new tasks service.
	 *
	 * @param auditlog
	 *            the auditlog
	 */
	public TasksService(Map<String, Object> auditlog) {
		super(auditlog);
	}

	/**
	 * Instantiates a new tasks service.
	 *
	 * @param resourceBundle
	 *            the resource bundle
	 */
	public TasksService(ResourceBundle resourceBundle) {
		super(resourceBundle);
	}

	/**
	 * Get all Tasks.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Tasks}
	 * @throws Exception
	 *             the exception
	 * @see Tasks
	 */
	public List<Tasks> allTasks() throws Exception {
		return dao.allTasks();
	}

	/**
	 * Create or update Tasks.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Tasks
	 */
	public void create(Tasks entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update Tasks.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Tasks
	 */
	public void update(Tasks entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Tasks.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Tasks
	 */
	public void delete(Tasks entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Tasks}
	 * @throws Exception
	 *             the exception
	 * @see Tasks
	 */
	public Tasks findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	public Tasks findByGuid(String id) throws Exception {
		return dao.findByGuid(id);
	}

	public long findUserCountForTask(ConfigDocProcessEnum docProcessEnum, Long userId) throws Exception {
		return dao.findUserCountForTask(docProcessEnum, userId);
	}

	/**
	 * Find Tasks by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.Tasks}
	 * @throws Exception
	 *             the exception
	 * @see Tasks
	 */
	public List<Tasks> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Tasks.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.Tasks}
	 * @throws Exception
	 *             the exception
	 */
	public List<Tasks> allTasks(int first, int pageSize) throws Exception {
		return dao.allTasks(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of Tasks for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Tasks
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(Tasks.class);
	}

	/**
	 * Find tasks by user incomplete.
	 *
	 * @param user
	 *            the user
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Tasks> findTasksByUserIncomplete(Users user) throws Exception {
		return dao.calcRag(dao.findTasksByUserIncomplete(user.getId()));
	}

	/**
	 * Find tasks by user incomplete count.
	 *
	 * @param user
	 *            the user
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long findTasksByUserIncompleteCount(Users user) throws Exception {
		return dao.findTasksByUserIncompleteCount(user.getId());
	}

	public void checkRag(Tasks task) {
		if (task.getTaskStatus() == TaskStatusEnum.Completed) task.setRag(RagEnum.Green);
		else if (task.getDueDate() == null) task.setRag(RagEnum.Green);
		else if (DateUtils.isSameDay(task.getDueDate(), getSynchronizedDate())) task.setRag(RagEnum.Amber);
		else if (task.getDueDate().after(getSynchronizedDate())) task.setRag(RagEnum.Green);
		else task.setRag(RagEnum.Red);
	}

	/**
	 * Lazy load Tasks with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Tasks class
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
	 * @return a list of {@link haj.com.entity.Tasks} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Tasks> allTasks(Class<Tasks> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = null;
		if (sortField == null && filters.isEmpty()) {
			// hql = "select o from Tasks o left join fetch o.createUser left join fetch
			// o.actionUser where o.taskStatus <> :completed order by o.createDate";
			hql = "select o from Tasks o left join fetch o.createUser left join fetch o.actionUser left join fetch o.processRole where o.taskStatus <> :completed and o.taskStatus <> :taskStatusError order by datediff(o.startDate,o.createDate) desc";

		} else hql = "select o from Tasks o left join fetch o.createUser left join fetch o.actionUser where o.taskStatus <> :completed and o.taskStatus <> :taskStatusError ";
		filters.put("completed", TaskStatusEnum.Completed);
		filters.put("taskStatusError", TaskStatusEnum.ERROR);
		return dao.calcRag((List<Tasks>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));

	}

	@SuppressWarnings("unchecked")
	public List<Tasks> allTasksNoSort(Class<Tasks> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = null;
		hql = "select o from Tasks o left join fetch o.createUser left join fetch o.actionUser where o.taskStatus <> :completed and o.taskStatus <> :taskStatusError";
		filters.put("completed", TaskStatusEnum.Completed);
		filters.put("taskStatusError", TaskStatusEnum.ERROR);
		return dao.calcRag((List<Tasks>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));

	}

	@SuppressWarnings("unchecked")
	public List<Tasks> allTasksNoSortWithCompleted(Class<Tasks> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = null;
		hql = "select o from Tasks o left join fetch o.createUser left join fetch o.actionUser where o.taskStatus <> :taskStatusError";
		filters.put("taskStatusError", TaskStatusEnum.ERROR);
		return dao.calcRag((List<Tasks>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));

	}

	@SuppressWarnings("unchecked")
	public List<Tasks> findTasksByUserIncomplete(Class<Tasks> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users user) throws Exception {
		String hql = "select distinct(o.task) from TaskUsers o left join fetch o.task.actionUser au where o.user.id  = :userId " + "and o.task.taskStatus <> :taskStatusComplete and o.task.taskStatus <> :taskStatusClosed and o.task.taskStatus <> :taskStatusError";
		filters.put("userId", user.getId());
		filters.put("taskStatusComplete", TaskStatusEnum.Completed);
		filters.put("taskStatusClosed", TaskStatusEnum.Closed);
		filters.put("taskStatusError", TaskStatusEnum.ERROR);
		return dao.calcRag((List<Tasks>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	@SuppressWarnings("unchecked")
	public List<Tasks> allTasksWhereUserAssigned(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users user) throws Exception {
		String hql = "select o from Tasks o left join fetch o.createUser left join fetch o.actionUser where o.taskStatus <> :taskStatusError and o.id in (select distinct(x.task.id) from TaskUsers x where x.user.id  = :userId)";
		filters.put("userId", user.getId());
		filters.put("taskStatusError", TaskStatusEnum.ERROR);
		return dao.calcRag((List<Tasks>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));

	}
	
	public int countAllTasksWhereUserAssigned(Class<Tasks> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Tasks o where o.taskStatus <> :taskStatusError and o.id in (select distinct(x.task.id) from TaskUsers x where x.user.id  = :userId)";
		return dao.countWhere(filters, hql);
	}

	public int countWithCompleted(Class<Tasks> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Tasks o where o.taskStatus <> :taskStatusError";
		return dao.countWhere(filters, hql);
	}

	public int countNotCompleted(Class<Tasks> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Tasks o where o.taskStatus <> :completed and o.taskStatus <> :taskStatusError";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Tasks> allAdminTasksForWsp(Class<Tasks> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = null;
		hql = "select o from Tasks o left join fetch o.createUser left join fetch o.actionUser where o.targetClass = :targetClass and o.firstInProcess = :firstInProcess and o.workflowProcess = :workflowProcess";
		return dao.calcRag((List<Tasks>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAdminTasksForWsp(Class<Tasks> entity, Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Tasks o where o.targetClass = :targetClass and o.firstInProcess = :firstInProcess and o.workflowProcess = :workflowProcess";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Tasks> allAdminTasksForWsp(Class<Tasks> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer finYear) throws Exception {
		String hql = null;
		hql = "select o from Tasks o left join fetch o.createUser left join fetch o.actionUser where o.targetClass = :targetClass and o.firstInProcess = :firstInProcess and o.workflowProcess = :workflowProcess";
		return dao.calcRag((List<Tasks>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAdminTasksForWsp(Class<Tasks> entity, Map<String, Object> filters, Integer finYear) throws Exception {
		String hql = "select count(o) from Tasks o where o.targetClass = :targetClass and o.firstInProcess = :firstInProcess and o.workflowProcess = :workflowProcess";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Find tasks by type and key.
	 *
	 * @param docProcessEnum
	 *            the doc process enum
	 * @param targetKey
	 *            the target key
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Tasks> findTasksByTypeAndKey(ConfigDocProcessEnum docProcessEnum, Long targetKey) throws Exception {
		return dao.calcRag(dao.findTasksByTypeAndKey(docProcessEnum, targetKey));
	}

	public List<Tasks> findTasksByTypeAndKey(ConfigDocProcessEnum docProcessEnum, String targetClass, Long targetKey) throws Exception {
		return dao.calcRag(dao.findTasksByTypeAndKey(docProcessEnum, targetClass, targetKey));
	}

	public List<Tasks> findTasksBytargetClassAndKey(String targetClass, Long targetKey) throws Exception {
		return dao.findTasksBytargetClassAndKey(targetClass, targetKey);
	}

	public long findTasksByTypeAndKeyOpen(ConfigDocProcessEnum docProcessEnum, String targetClass, Long targetKey) throws Exception {
		return dao.findTasksByTypeAndKeyOpen(docProcessEnum, targetClass, targetKey);
	}

	public List<Tasks> findTasksByTypeAndKeyLast(ConfigDocProcessEnum docProcessEnum, Long targetKey) throws Exception {
		return dao.findTasksByTypeAndKeyLast(docProcessEnum, targetKey);
	}

	public List<Tasks> allTasksByType(Class<Tasks> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, boolean employee, TaskStatusEnum taskStatusEnum) throws Exception {

		return dao.allTasksByType(class1, first, pageSize, sortField, sortOrder, filters, employee, taskStatusEnum);
	}

	public int countByType(Class<Tasks> entity, Map<String, Object> filters, Boolean employee, TaskStatusEnum taskStatusEnum) throws Exception {

		return dao.countByType(entity, filters, employee, taskStatusEnum);
	}

	/**
	 * Get count of Tasks for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            Tasks class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the Tasks entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<Tasks> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find first in process and create task.
	 *
	 * @param description
	 *            the description
	 * @param createUser
	 *            the create user
	 * @param targetKey
	 *            the target key
	 * @param targetClass
	 *            the target class
	 * @param sendMail
	 *            the send mail
	 * @param processEnum
	 *            the process enum
	 * @param users
	 *            TODO
	 * @throws Exception
	 *             the exception
	 */
	public void findFirstInProcessAndCreateTask(String description, Users createUser, Long targetKey, String targetClass, boolean sendMail, ConfigDocProcessEnum processEnum, MailDef mailDef, List<Users> users) throws Exception {
		HostingCompanyProcess hostingCompanyProcess = hostingCompanyProcessService.allHostingCompanyProcess(HostingCompanyService.instance().findByKey(HAJConstants.HOSTING_MERSETA), processEnum);
		if (hostingCompanyProcess != null) {
			List<ProcessRoles> first = processRolesService.firstProcessRoles(hostingCompanyProcess);
			if (first.size() > 0) {
				if (first.get(0).getNote() != null && !first.get(0).getNote().isEmpty()) {
					description = first.get(0).getNote();
				}
				description = FixTaskDataService.instance().replaceStrings(targetClass, description, targetKey, createUser);
				////System.out.println("users.size():"+users.size());
				//System.out.println("first.get(0).getCompanyUserType():"+first.get(0).getCompanyUserType());
				if (users != null && users.size() > 0) createWorkFlowTask(description, createUser, targetKey, targetClass, sendMail, true, first.get(0), processEnum, hostingCompanyProcess, users, mailDef, null, false);
				else if (first.get(0).getCompanyUserType() == null) createWorkFlowTask(description, createUser, targetKey, targetClass, sendMail, true, first.get(0), processEnum, hostingCompanyProcess, mailDef, null);
				else {
					//System.out.println("insode else");
					users = new ArrayList<>();
					FixTaskDataService.instance().getUsers(targetKey, targetClass, users, processEnum, true, first.get(0), createUser);
					createWorkFlowTask(description, createUser, targetKey, targetClass, true, true, first.get(0), processEnum, hostingCompanyProcess, users, null, null, false);
				}

			} else {
				throw new Exception("Hosting company setup error. No process roles assigned to Hosting company.");
			}
		}

	}

	/**
	 * Find first in process and create reject task.
	 *
	 * @param description
	 *            the description
	 * @param createUser
	 *            the create user
	 * @param targetKey
	 *            the target key
	 * @param targetClass
	 *            the target class
	 * @param sendMail
	 *            the send mail
	 * @param processEnum
	 *            the process enum
	 * @param users
	 *            TODO
	 * @throws Exception
	 *             the exception
	 */
	public void findFirstInProcessAndCreateRejectTask(String description, Users createUser, Long targetKey, String targetClass, boolean sendMail, ConfigDocProcessEnum processEnum, MailDef mailDef, List<Users> users) throws Exception {
		HostingCompanyProcess hostingCompanyProcess = hostingCompanyProcessService.allHostingCompanyProcess(HostingCompanyService.instance().findByKey(HAJConstants.HOSTING_MERSETA), processEnum);
		if (hostingCompanyProcess != null) {
			List<ProcessRoles> first = processRolesService.firstProcessRoles(hostingCompanyProcess);
			if (first.size() > 0) {
				if (first.get(0).getRejectMessage() != null && !first.get(0).getRejectMessage().isEmpty()) {
					description = first.get(0).getRejectMessage();
				}
				description = FixTaskDataService.instance().replaceStrings(targetClass, description, targetKey, createUser);

				if (users != null && users.size() > 0) createWorkFlowTask(description, createUser, targetKey, targetClass, sendMail, true, first.get(0), processEnum, hostingCompanyProcess, users, mailDef, null, false);
				else if (first.get(0).getCompanyUserType() == null) createWorkFlowTask(description, createUser, targetKey, targetClass, sendMail, true, first.get(0), processEnum, hostingCompanyProcess, mailDef, null);
				else {
					users = new ArrayList<>();
					FixTaskDataService.instance().getUsers(targetKey, targetClass, users, processEnum, true, first.get(0), createUser);
					createWorkFlowTask(description, createUser, targetKey, targetClass, true, true, first.get(0), processEnum, hostingCompanyProcess, users, null, null, false);
				}

			} else {
				throw new Exception("Hosting company setup error. No process roles assigned to Hosting company.");
			}
		}

	}

	public void findFirstInProcessAndCreateTask(Users createUser, Long targetKey, String targetClass, ConfigDocProcessEnum processEnum, boolean createIndividualTasks) throws Exception {
		HostingCompanyProcess hostingCompanyProcess = hostingCompanyProcessService.allHostingCompanyProcess(HostingCompanyService.instance().findByKey(HAJConstants.HOSTING_MERSETA), processEnum);
		String description = "";
		if (hostingCompanyProcess != null) {
			List<ProcessRoles> first = processRolesService.firstProcessRoles(hostingCompanyProcess);
			if (first.size() > 0) {
				if (first.get(0).getNote() != null && !first.get(0).getNote().isEmpty()) {
					description = first.get(0).getNote();
				}
				description = FixTaskDataService.instance().replaceStrings(targetClass, description, targetKey, createUser);
				if (first.get(0).getCompanyUserType() == null) createWorkFlowTask(description, createUser, targetKey, targetClass, true, true, first.get(0), processEnum, hostingCompanyProcess, null, null);
				else {
					List<Users> users = new ArrayList<>();
					FixTaskDataService.instance().getUsers(targetKey, targetClass, users, processEnum, true, first.get(0), createUser);
					//commented for JIRA #177
					//createWorkFlowTask(description, createUser, targetKey, targetClass, true, true, first.get(0), processEnum, hostingCompanyProcess, users, null, null, createIndividualTasks);
					createWorkFlowTask(description, createUser, targetKey, targetClass, false, true, first.get(0), processEnum, hostingCompanyProcess, users, null, null, createIndividualTasks);
				}
			} else {
				throw new Exception("Hosting company setup error. No process roles assigned to Hosting company.");
			}
		}

	}

	public void findByPositionAndCreateTask(int position, String description, Users createUser, Long targetKey, String targetClass, boolean sendMail, HostingCompany hostingCompany, ConfigDocProcessEnum processEnum, MailDef mailDef, String extraInfo, boolean useDesc) throws Exception {
		HostingCompanyProcess hostingCompanyProcess = hostingCompanyProcessService.allHostingCompanyProcess(hostingCompany, processEnum);

		List<ProcessRoles> first = processRolesService.findByPosition(hostingCompanyProcess, position);
		if (first.size() > 0) {
			if (first.get(0).getNote() != null && !first.get(0).getNote().isEmpty() && useDesc) {
				description = FixTaskDataService.instance().replaceStrings(targetClass, first.get(0).getNote(), targetKey, createUser);
			}

			if (first.get(0).getCompanyUserType() == null) {
				createWorkFlowTask(description + extraInfo, createUser, targetKey, targetClass, sendMail, true, first.get(0), processEnum, hostingCompanyProcess, mailDef, null);
			} else {
				List<Users> users = new ArrayList<>();
				FixTaskDataService.instance().getUsers(targetKey, targetClass, users, processEnum, false, first.get(0), createUser);
				createWorkFlowTask(description, createUser, targetKey, targetClass, sendMail, false, first.get(0), processEnum, hostingCompanyProcess, users, mailDef, null, false);
			}

		} else {
			throw new Exception("Hosting company setup error. No process roles assigned to Hosting company.");
		}

	}
	
	public void findByPositionAndCreateTaskWithUsers(int position, String description, Users createUser, Long targetKey, String targetClass, boolean sendMail, HostingCompany hostingCompany, ConfigDocProcessEnum processEnum, MailDef mailDef, String extraInfo, boolean useDesc, List<Users> userList) throws Exception {
		HostingCompanyProcess hostingCompanyProcess = hostingCompanyProcessService.allHostingCompanyProcess(hostingCompany, processEnum);
		List<ProcessRoles> first = processRolesService.findByPosition(hostingCompanyProcess, position);
		if (first.size() > 0) {
			if (first.get(0).getNote() != null && !first.get(0).getNote().isEmpty() && useDesc) {
				description = FixTaskDataService.instance().replaceStrings(targetClass, first.get(0).getNote(), targetKey, createUser);
			}

			if (first.get(0).getCompanyUserType() == null) {
				createWorkFlowTask(description + extraInfo, createUser, targetKey, targetClass, sendMail, true, first.get(0), processEnum, hostingCompanyProcess, mailDef, null);
			} else {
				List<Users> users = new ArrayList<>();
				if (userList == null || userList.size() == 0) {
					FixTaskDataService.instance().getUsers(targetKey, targetClass, users, processEnum, false, first.get(0), createUser);
				} else {
					users.addAll(userList);
				}
				createWorkFlowTask(description, createUser, targetKey, targetClass, sendMail, false, first.get(0), processEnum, hostingCompanyProcess, users, mailDef, null, false);
			}
		} else {
			throw new Exception("Hosting company setup error. No process roles assigned to Hosting company.");
		}
	}
	
	public void findByPositionAndCreateTaskWithUsersIgnoringCompanyUserTypeCheck(int position, String description, Users createUser, Long targetKey, String targetClass, boolean sendMail, HostingCompany hostingCompany, ConfigDocProcessEnum processEnum, MailDef mailDef, String extraInfo, boolean useDesc, List<Users> userList) throws Exception {
		HostingCompanyProcess hostingCompanyProcess = hostingCompanyProcessService.allHostingCompanyProcess(hostingCompany, processEnum);
		List<ProcessRoles> first = processRolesService.findByPosition(hostingCompanyProcess, position);
		if (first.size() > 0) {
			if (first.get(0).getNote() != null && !first.get(0).getNote().isEmpty() && useDesc) {
				description = FixTaskDataService.instance().replaceStrings(targetClass, first.get(0).getNote(), targetKey, createUser);
			}
//			if (first.get(0).getCompanyUserType() == null) {
//				createWorkFlowTask(description + extraInfo, createUser, targetKey, targetClass, sendMail, true, first.get(0), processEnum, hostingCompanyProcess, mailDef, null);
//			} else {
			List<Users> users = new ArrayList<>();
			if (userList == null || userList.size() == 0) {
				FixTaskDataService.instance().getUsers(targetKey, targetClass, users, processEnum, false, first.get(0), createUser);
			} else {
				users.addAll(userList);
			}
			createWorkFlowTask(description, createUser, targetKey, targetClass, sendMail, false, first.get(0), processEnum, hostingCompanyProcess, users, mailDef, null, false);
//			}
		} else {
			throw new Exception("Hosting company setup error. No process roles assigned to Hosting company.");
		}
	}

	public void findNextInProcessAndCreateTask(Users createUser, Tasks tasks, List<Users> users, boolean createIndividualTasks) throws Exception {
		//System.out.println("inside findNextInProcessAndCreateTask:"+createIndividualTasks);
		List<ProcessRoles> first = processRolesService.findNextProcessRoles(tasks.getProcessRole());
		//System.out.println("fist size:"+first.size());
		if (first.size() == 0 && tasks.getProcessRole().getHostingCompanyProcess().getNextProcess() != null) {
			//System.out.println("tasks.getProcessRole().getHostingCompanyProcess().getNextProcess()----"+tasks.getProcessRole().getHostingCompanyProcess().getNextProcess());
			HostingCompanyProcess hostingCompanyProcess = hostingCompanyProcessService.findByKey(tasks.getProcessRole().getHostingCompanyProcess().getId());
			first = processRolesService.firstProcessRoles(hostingCompanyProcess.getNextProcess());
		}
		//commented for JIRA issue #177
		//createProcess("", createUser, tasks.getTargetKey(), tasks.getTargetClass(), true, false, tasks, first, false, null, "", users, createIndividualTasks);
		createProcess("", createUser, tasks.getTargetKey(), tasks.getTargetClass(), false, false, tasks, first, false, null, "", users, createIndividualTasks);
		completeTask(tasks);
	}

	public void findPreviousInProcessAndCreateTask(Users createUser, Tasks tasks, List<Users> users) throws Exception {
		if (!tasks.getFirstInProcess()) {
			boolean firstInProcess = true;
			List<ProcessRoles> first = processRolesService.findPreviousProcessRoles(tasks.getProcessRole());
			if (first.size() == 0 && tasks.getProcessRole().getHostingCompanyProcess() != null) {
				HostingCompanyProcess hostingCompanyProcess = hostingCompanyProcessService.findPreviousProcess(tasks.getProcessRole().getHostingCompanyProcess());
				first = processRolesService.lastProcessRoles(hostingCompanyProcess);
				firstInProcess = false;
			} else {
				firstInProcess = processRolesService.findPreviousProcessRolesCount(first.get(0)) == 0;
			}
			createProcess("", createUser, tasks.getTargetKey(), tasks.getTargetClass(), true, firstInProcess, tasks, first, true, null, "", users, false);
			completeTask(tasks);
		}
	}
	
	public void findPreviousInProcessAndCreateFirstInProcessTask(Users createUser, Long targetKey, String targetClass, ConfigDocProcessEnum processEnum, boolean createIndividualTasks, String description) throws Exception {
		HostingCompanyProcess hostingCompanyProcess = hostingCompanyProcessService.allHostingCompanyProcess(HostingCompanyService.instance().findByKey(HAJConstants.HOSTING_MERSETA), processEnum);
		if (hostingCompanyProcess != null) {
			List<ProcessRoles> first = processRolesService.firstProcessRoles(hostingCompanyProcess);
			if (first.size() > 0) {
				description = FixTaskDataService.instance().replaceStrings(targetClass, description, targetKey, createUser);
				if (first.get(0).getCompanyUserType() == null) createWorkFlowTask(description, createUser, targetKey, targetClass, true, true, first.get(0), processEnum, hostingCompanyProcess, null, null);
				else {
					List<Users> users = new ArrayList<>();
					FixTaskDataService.instance().getUsers(targetKey, targetClass, users, processEnum, true, first.get(0), createUser);
					createWorkFlowTask(description, createUser, targetKey, targetClass, true, true, first.get(0), processEnum, hostingCompanyProcess, users, null, null, createIndividualTasks);
				}
			} else {
				throw new Exception("Hosting company setup error. No process roles assigned to Hosting company.");
			}
		}
	}

	public void findNextInProcessAndCreateTask(String description, Users createUser, Long targetKey, String targetClass, boolean sendMail, Tasks tasks, MailDef mailDef, List<Users> users) throws Exception {
		//System.out.println("inside findNextInProcessAndCreateTask:::"+targetKey);
		List<ProcessRoles> first = processRolesService.findNextProcessRoles(tasks.getProcessRole());
		if (first.size() == 0 && tasks.getProcessRole().getHostingCompanyProcess().getNextProcess() != null) {
			HostingCompanyProcess hostingCompanyProcess = hostingCompanyProcessService.findByKey(tasks.getProcessRole().getHostingCompanyProcess().getId());
			first = processRolesService.firstProcessRoles(hostingCompanyProcess.getNextProcess());
		}
		createProcess(description, createUser, targetKey, targetClass, sendMail, false, tasks, first, false, mailDef, "", users, false);
		completeTask(tasks);
	}

	/**
	 * Find previous in process and create task.
	 *
	 * @param description
	 *            the description
	 * @param createUser
	 *            the create user
	 * @param targetKey
	 *            the target key
	 * @param targetClass
	 *            the target class
	 * @param sendMail
	 *            the send mail
	 * @param tasks
	 *            the tasks
	 * @param users
	 *            TODO
	 * @throws Exception
	 *             the exception
	 */
	public void findPreviousInProcessAndCreateTask(String description, Users createUser, Long targetKey, String targetClass, boolean sendMail, Tasks tasks, MailDef mailDef, List<Users> users) throws Exception {

		if (!tasks.getFirstInProcess()) {
			boolean firstInProcess = true;
			List<ProcessRoles> first = processRolesService.findPreviousProcessRoles(tasks.getProcessRole());
			if (first.size() == 0 && tasks.getProcessRole().getHostingCompanyProcess() != null) {
				HostingCompanyProcess hostingCompanyProcess = hostingCompanyProcessService.findPreviousProcess(tasks.getProcessRole().getHostingCompanyProcess());
				first = processRolesService.lastProcessRoles(hostingCompanyProcess);
				firstInProcess = false;
			} else {
				firstInProcess = processRolesService.findPreviousProcessRolesCount(first.get(0)) == 0;
			}
			createProcess(description, createUser, targetKey, targetClass, sendMail, firstInProcess, tasks, first, true, mailDef, "", users, false);
			completeTask(tasks);
		}
	}

	/**
	 * Creates the process.
	 *
	 * @param description
	 *            the description
	 * @param createUser
	 *            the create user
	 * @param targetKey
	 *            the target key
	 * @param targetClass
	 *            the target class
	 * @param sendMail
	 *            the send mail
	 * @param firstInProcess
	 *            the first in process
	 * @param tasks
	 *            the tasks
	 * @param first
	 *            the first
	 * @param useRejectMessage
	 *            the use reject message
	 * @param createSeperateTasks
	 *            TODO
	 * @throws Exception
	 *             the exception
	 */
	private void createProcess(String description, Users createUser, Long targetKey, String targetClass, boolean sendMail, boolean firstInProcess, Tasks tasks, List<ProcessRoles> first, boolean useRejectMessage, MailDef mailDef, String extraInfo, List<Users> users, boolean createSeperateTasks) throws Exception {
		//System.out.println("createProcess");
		if (first.size() > 0) {
			//System.out.println("first.size()--"+first.size());
			if (useRejectMessage) {
				//System.out.println("useRejectMessage--"+useRejectMessage);
				if (first.get(0).getRejectMessage() != null && !first.get(0).getRejectMessage().isEmpty()) {
					description = first.get(0).getRejectMessage();
				}
				description += extraInfo;
				//System.out.println("description--"+description);
			} else {
				//System.out.println("in else");
				if (first.size() > 0 && first.get(0).getNote() != null && !first.get(0).getNote().isEmpty()) {
					description = first.get(0).getNote();
				}
				description += extraInfo;
				//System.out.println("description else--"+description);
			}

			description = FixTaskDataService.instance().replaceStrings(targetClass, description, targetKey, (tasks != null) ? tasks.getActionUser() : null);
			//System.out.println("description 2--"+description);
			HostingCompanyProcess hostingCompanyProcess = first.get(0).getHostingCompanyProcess();
			//System.out.println("hostingCompanyProcess--"+hostingCompanyProcess.toString());
			if (hostingCompanyProcess.getWorkflowProcess() == null) hostingCompanyProcess.setWorkflowProcess(tasks.getWorkflowProcess());
			//System.out.println("hostingCompanyProcess.getWorkflowProcess()--"+hostingCompanyProcess.getWorkflowProcess());
			if (users != null && users.size() > 0) {
				//System.out.println("in if createWorkFlowTask");
				createWorkFlowTask(description, createUser, targetKey, targetClass, sendMail, firstInProcess, first.get(0), tasks.getWorkflowProcess(), hostingCompanyProcess, users, mailDef, tasks, createSeperateTasks);
			} else if (first.get(0).getCompanyUserType() == null) {
				//System.out.println("in else if createWorkFlowTask--");
				createWorkFlowTask(description, createUser, targetKey, targetClass, sendMail, firstInProcess, first.get(0), tasks.getWorkflowProcess(), hostingCompanyProcess, mailDef, tasks);
			} else {
				//System.out.println("in else createWorkFlowTask--");
				users = new ArrayList<>();
				FixTaskDataService.instance().getUsers(targetKey, targetClass, users, tasks.getWorkflowProcess(), false, first.get(0), createUser);
				createWorkFlowTask(description, createUser, targetKey, targetClass, sendMail, firstInProcess, first.get(0), tasks.getWorkflowProcess(), hostingCompanyProcess, users, mailDef, tasks, createSeperateTasks);
			}
		}
	}

	/**
	 * Send mail.
	 *
	 * @param users
	 *            the to
	 * @param subject
	 *            the subject
	 * @param processEnum
	 *            the msg
	 */
	private void sendMail(Users user, String subject, MailDef mailDef, String description) {
		if (user.getRecieveEmailTaskNotification() != null && user.getRecieveEmailTaskNotification()) {
			// Code required
			if (mailDef == null) {
				GenericUtility.sendMail(user, subject, description, null);
			} else {
				MailTemplatesService.instance().sendMail(user, subject, mailDef, description);
			}
		}

	}

	private void sendMail(String email, String subject, String description) {
		GenericUtility.sendMail(email, subject, description, null);

	}

	/**
	 * Creates the work flow task.
	 *
	 * @param description
	 *            the description
	 * @param createUser
	 *            the create user
	 * @param targetKey
	 *            the target key
	 * @param targetClass
	 *            the target class
	 * @param sendMail
	 *            the send mail
	 * @param firstInProcess
	 *            the first in process
	 * @param processRoles
	 *            the process roles
	 * @param processEnum
	 *            the process enum
	 * @param hostingCompanyProcess
	 *            the hosting company process
	 * @throws Exception
	 *             the exception
	 */
	private void createWorkFlowTask(String description, Users createUser, Long targetKey, String targetClass, boolean sendMail, boolean firstInProcess, ProcessRoles processRoles, ConfigDocProcessEnum processEnum, HostingCompanyProcess hostingCompanyProcess, MailDef mailDef, Tasks task) throws Exception {
		if (checkCanCreateTask(targetClass, targetKey, processRoles)) {
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			// Task information
			int numbeOfDays = 5;
			if (processRoles.getNumberOfDays() != null) numbeOfDays = processRoles.getNumberOfDays();
			Tasks tasks = new Tasks(description, createUser, targetKey, targetClass, TaskStatusEnum.NotStarted, GenericUtility.addDaysToDateExcludeWeekends(getSynchronizedDate(), numbeOfDays), UUID.randomUUID().toString());
			if(processEnum == ConfigDocProcessEnum.LearnerRegistration && firstInProcess) {
				tasks.setTaskStatus(TaskStatusEnum.NotYetSubmitted);
			}
			tasks.setProcessRole(processRoles);
			tasks.setWorkflowProcess(processEnum);
			tasks.setFirstInProcess(firstInProcess);
			tasks.setHostingCompanyProcess(hostingCompanyProcess);
			tasks.setPreviousTask(task);
			dataEntities.add(tasks);
			//System.out.println("processRoles.getCompanyUserType():"+processRoles.getCompanyUserType());
			if (processRoles.getCompanyUserType() == null) {
				// Users for workflow process
				List<UsersRole> usersRoles = usersRoleService.findByProcess(processRoles);
				for (UsersRole usersRole : usersRoles) {

					dataEntities.add(new TaskUsers(usersRole.getUsers(), tasks));
					this.sendMail(usersRole.getUsers(), "New task created on merSETA NSDMS portal", mailDef, description);
				}
			}
			this.dao.createBatch(dataEntities);
		} else {
			logger.info("NO TASK NEEDED TO BE CREATED FOR:\t" + targetClass + "\t" + targetKey + "\t" + processEnum);
		}
	}

	/**
	 * Creates the work flow task.
	 *
	 * @param description
	 *            the description
	 * @param createUser
	 *            the create user
	 * @param targetKey
	 *            the target key
	 * @param targetClass
	 *            the target class
	 * @param sendMail
	 *            the send mail
	 * @param firstInProcess
	 *            the first in process
	 * @param processRoles
	 *            the process roles
	 * @param processEnum
	 *            the process enum
	 * @param hostingCompanyProcess
	 *            the hosting company process
	 * @param users
	 *            the users
	 * @param createSeparateTasks
	 *            TODO
	 * @throws Exception
	 *             the exception
	 */
	private void createWorkFlowTask(String description, Users createUser, Long targetKey, String targetClass, boolean sendMail, boolean firstInProcess, ProcessRoles processRoles, ConfigDocProcessEnum processEnum, HostingCompanyProcess hostingCompanyProcess, List<Users> users, MailDef mailDef, Tasks previousTask, boolean createSeparateTasks) throws Exception {
		if (checkCanCreateTask(targetClass, targetKey, processRoles)) {
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			int numbeOfDays = 5;
			if (processRoles.getNumberOfDays() != null) numbeOfDays = processRoles.getNumberOfDays();
			// Task information
			if (!createSeparateTasks) {
				Tasks tasks = new Tasks(description, createUser, targetKey, targetClass, TaskStatusEnum.NotStarted, GenericUtility.addDaysToDateExcludeWeekends(getSynchronizedDate(), numbeOfDays), UUID.randomUUID().toString());
				if(processEnum == ConfigDocProcessEnum.LearnerRegistration && firstInProcess) {
					tasks.setTaskStatus(TaskStatusEnum.NotYetSubmitted);
				}
				if (previousTask != null && previousTask.getReviewDate() != null) tasks.setDueDate(previousTask.getReviewDate());
				tasks.setProcessRole(processRoles);
				tasks.setWorkflowProcess(processEnum);
				tasks.setFirstInProcess(firstInProcess);
				tasks.setHostingCompanyProcess(hostingCompanyProcess);
				tasks.setPreviousTask(previousTask);
				dataEntities.add(tasks);
				for (Users user : users) {
					dataEntities.add(new TaskUsers(user, tasks));
					// Code modified for JIRA #177
					if (sendMail){
						this.sendMail(user, "New task created on merSETA NSDMS portal", mailDef, description);
					}
				}
			} else {
				for (Users user : users) {
					Tasks tasks = new Tasks(description, createUser, targetKey, targetClass, TaskStatusEnum.NotStarted, GenericUtility.addDaysToDateExcludeWeekends(getSynchronizedDate(), numbeOfDays), UUID.randomUUID().toString());
					if(processEnum == ConfigDocProcessEnum.LearnerRegistration && firstInProcess) {
						tasks.setTaskStatus(TaskStatusEnum.NotYetSubmitted);
					}
					if (previousTask != null && previousTask.getReviewDate() != null) tasks.setDueDate(previousTask.getReviewDate());
					tasks.setProcessRole(processRoles);
					tasks.setWorkflowProcess(processEnum);
					tasks.setFirstInProcess(firstInProcess);
					tasks.setHostingCompanyProcess(hostingCompanyProcess);
					tasks.setPreviousTask(previousTask);
					dataEntities.add(tasks);
					dataEntities.add(new TaskUsers(user, tasks));
					// Code modified for JIRA #177
					if (sendMail){
						this.sendMail(user, "New task created on merSETA NSDMS portal", mailDef, description);
					}
				}
			}
			this.dao.createBatch(dataEntities);
		} else {
			logger.info("NO TASK NEEDED TO BE CREATED FOR:\t" + targetClass + "\t" + targetKey + "\t" + processEnum);
		}
	}

	/**
	 * Creates the company task.
	 *
	 * @param comp
	 *            the comp
	 * @param subject
	 *            the subject
	 * @param description
	 *            the description
	 * @param createUser
	 *            the create user
	 * @param targetKey
	 *            the target key
	 * @param sendMail
	 *            the send mail
	 * @param createTask
	 *            the create task
	 * @param task
	 *            the task
	 * @throws Exception
	 *             the exception
	 */
	public void createCompanyTask(Company comp, String subject, String description, Users createUser, Long targetKey, boolean sendMail, boolean createTask, Tasks task) throws Exception {

		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		List<CompanyUsers> cu = companyUsersService.findByCompanyNotSDF(comp);
		Tasks tasks = null;
		if (createTask) {
			tasks = new Tasks(description, createUser, targetKey, Company.class.toString(), TaskStatusEnum.NotStarted, GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()), UUID.randomUUID().toString());
			tasks.setPreviousTask(task);
			dataEntities.add(tasks);
		}
		for (CompanyUsers users : cu) {
			if (createTask) {
				dataEntities.add(new TaskUsers(users.getUser(), tasks));
			}

			if (sendMail) {
				this.sendMail(users.getUser().getEmail(), "New task created on SETA portal", description);
			}
		}
		completeTask(task);
		this.dao.createBatch(dataEntities);
	}

	/**
	 * Creates the task.
	 *
	 * @param comp
	 *            the comp
	 * @param subject
	 *            the subject
	 * @param description
	 *            the description
	 * @param createUser
	 *            the create user
	 * @param targetKey
	 *            the target key
	 * @param sendMail
	 *            the send mail
	 * @param createTask
	 *            the create task
	 * @param task
	 *            the task
	 * @param user
	 *            the user
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @return the tasks
	 * @throws Exception
	 *             the exception
	 */
	public Tasks createTask(Company comp, String subject, String description, Users createUser, Long targetKey, boolean sendMail, boolean createTask, Tasks task, Users user, ConfigDocProcessEnum configDocProcessEnum, MailDef mailDef) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		Tasks tasks = null;
		if (createTask) {
			tasks = new Tasks(description, createUser, targetKey, Company.class.toString(), TaskStatusEnum.NotStarted, GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()), UUID.randomUUID().toString());
			tasks.setWorkflowProcess(configDocProcessEnum);
			tasks.setPreviousTask(task);
			dataEntities.add(tasks);
			dataEntities.add(new TaskUsers(user, tasks));
		}
		this.sendMail(user, "New task created on merSETA NSDMS portal", mailDef, description);
		completeTask(task);
		this.dao.createBatch(dataEntities);
		return tasks;
	}

	/**
	 * Creates the task.
	 *
	 * @param targetClass
	 *            the target class
	 * @param comp
	 *            the comp
	 * @param emailMessage
	 *            the email message
	 * @param subject
	 *            the subject
	 * @param description
	 *            the description
	 * @param createUser
	 *            the create user
	 * @param targetKey
	 *            the target key
	 * @param sendMail
	 *            the send mail
	 * @param createTask
	 *            the create task
	 * @param task
	 *            the task
	 * @param users
	 *            the users
	 * @param configDocProcessEnum
	 *            the config doc process enum
	 * @return the tasks
	 * @throws Exception
	 *             the exception
	 */
	public Tasks createTask(String targetClass, Company comp, String emailMessage, String subject, String description, Users createUser, Long targetKey, boolean sendMail, boolean createTask, Tasks task, List<Users> users, ConfigDocProcessEnum configDocProcessEnum, MailDef mailDef) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		Tasks tasks = null;
		if (createTask) {
			tasks = new Tasks(description, createUser, targetKey, targetClass, TaskStatusEnum.NotStarted, GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()), UUID.randomUUID().toString());
			tasks.setWorkflowProcess(configDocProcessEnum);
			tasks.setPreviousTask(task);
			dataEntities.add(tasks);
		}
		for (Users user : users) {
			if (createTask) {
				dataEntities.add(new TaskUsers(user, tasks));
			}
			this.sendMail(user, "New task created on merSETA NSDMS portal", mailDef, description);
		}
		completeTask(task);
		this.dao.createBatch(dataEntities);
		return tasks;
	}

	public Tasks createTaskEachUser(String targetClass, Company comp, String emailMessage, String subject, String description, Users createUser, Long targetKey, boolean sendMail, boolean createTask, Tasks task, List<Users> users, ConfigDocProcessEnum configDocProcessEnum, MailDef mailDef) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		Tasks tasks = null;
		for (Users user : users) {
			if (createTask) {
				tasks = new Tasks(description, createUser, targetKey, targetClass, TaskStatusEnum.NotStarted, GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()), UUID.randomUUID().toString());
				tasks.setWorkflowProcess(configDocProcessEnum);
				tasks.setPreviousTask(task);
				dataEntities.add(tasks);
				dataEntities.add(new TaskUsers(user, tasks));
			}
			this.sendMail(user, "New task created on merSETA NSDMS portal", mailDef, description);
		}
		completeTask(task);
		this.dao.createBatch(dataEntities);
		return tasks;
	}

	public void createTask(List<Signoff> users, String targetClass, Long targetKey, String description, Users createUser, boolean sendMail, boolean createTask, Tasks task, ConfigDocProcessEnum configDocProcessEnum, MailDef mailDef) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();

		for (Signoff user : users) {
			Tasks tasks = null;

			if (createTask) {
				tasks = new Tasks(description, createUser, targetKey, targetClass, TaskStatusEnum.NotStarted, GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()), UUID.randomUUID().toString());
				if (description.contains("#SIGN_OFF_GUID#")) description = description.replaceAll("#SIGN_OFF_GUID#", tasks.getGuid());
				tasks.setWorkflowProcess(configDocProcessEnum);
				tasks.setPreviousTask(task);
				dataEntities.add(tasks);
				dataEntities.add(new TaskUsers(user.getUser(), tasks));
			}
			if (sendMail) {
				this.sendMail(user.getUser(), "New task created on merSETA NSDMS portal", mailDef, description);
			}
		}
		completeTask(task);
		this.dao.createBatch(dataEntities);
		// return tasks;
	}

	public void createTask(List<Signoff> users, String targetClass, Long targetKey, String description, String mailDescription, Users createUser, boolean sendMail, boolean createTask, Tasks task, ConfigDocProcessEnum configDocProcessEnum, MailDef mailDef) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();

		for (Signoff user : users) {
			Tasks tasks = null;

			if (createTask) {
				tasks = new Tasks(description, createUser, targetKey, targetClass, TaskStatusEnum.NotStarted, GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()), UUID.randomUUID().toString());
				if (description.contains("#SIGN_OFF_GUID#")) {
					description = description.replaceAll("#SIGN_OFF_GUID#", user.getGuid());
					tasks.setTargetKey(user.getId());
				}
				tasks.setWorkflowProcess(configDocProcessEnum);
				tasks.setPreviousTask(task);
				dataEntities.add(tasks);
				dataEntities.add(new TaskUsers(user.getUser(), tasks));
			}
			if (sendMail) {
				if (user.getUser() != null) this.sendMail(user.getUser(), "New task created on merSETA NSDMS portal", mailDef, mailDescription);
				else GenericUtility.sendMail(user.getTempSignoff().getEmail(), "New task created on merSETA NSDMS portal", mailDescription, null);
			}
		}
		completeTask(task);
		this.dao.createBatch(dataEntities);
		// return tasks;
	}

	public void createTaskUser(List<Users> users, String targetClass, Long targetKey, String description, Users createUser, boolean sendMail, boolean createTask, Tasks task, ConfigDocProcessEnum configDocProcessEnum, boolean firstInProcess) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();

		for (Users user : users) {
			Tasks tasks = null;
			if (createTask) {
				description = FixTaskDataService.instance().replaceStrings(targetClass, description, targetKey, createUser);
				tasks = new Tasks(description, createUser, targetKey, targetClass, TaskStatusEnum.NotStarted, GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()), UUID.randomUUID().toString());
				tasks.setWorkflowProcess(configDocProcessEnum);
				tasks.setPreviousTask(task);
				tasks.setFirstInProcess(firstInProcess);
				dataEntities.add(tasks);
				dataEntities.add(new TaskUsers(user, tasks));
			}
			if (sendMail) {
				// Commented because email is missing Merseta logo
				// this.sendMail(user, "New task created on merSETA NSDMS portal", null,
				// description);
				this.sendMail(user.getEmail(), "New task created on merSETA NSDMS portal", description);
			}
		}
		completeTask(task);
		this.dao.createBatch(dataEntities);
		// return tasks;
	}

	public void createTaskUserDgAllocationFallBack(List<Users> users, String targetClass, Long targetKey, String description, Users createUser, boolean sendMail, boolean createTask, Tasks task, ConfigDocProcessEnum configDocProcessEnum, boolean firstInProcess) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();

		for (Users user : users) {
			Tasks tasks = null;
			if (createTask) {
				description = FixTaskDataService.instance().replaceStrings(targetClass, description, targetKey, createUser);
				tasks = new Tasks(description, createUser, targetKey, targetClass, TaskStatusEnum.NotStarted, GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()), UUID.randomUUID().toString());
				tasks.setWorkflowProcess(configDocProcessEnum);
				tasks.setPreviousTask(task);
				tasks.setFirstInProcess(firstInProcess);
				dataEntities.add(tasks);
				dataEntities.add(new TaskUsers(user, tasks));
			}
			if (sendMail) {
				this.sendMail(user, "New task created on merSETA NSDMS portal", null, description);
			}
		}
		if (users.size() == 0) {
			Tasks tasks = null;
			if (createTask) {
				description = FixTaskDataService.instance().replaceStrings(targetClass, description, targetKey, createUser);
				tasks = new Tasks(description, createUser, targetKey, targetClass, TaskStatusEnum.NotStarted, GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()), UUID.randomUUID().toString());
				tasks.setWorkflowProcess(configDocProcessEnum);
				tasks.setPreviousTask(task);
				tasks.setFirstInProcess(firstInProcess);
				dataEntities.add(tasks);
			}
		}
		completeTask(task);
		this.dao.createBatch(dataEntities);
		// return tasks;
	}

	public void createTaskCompanyUser(List<CompanyUsers> users, String targetClass, Long targetKey, String description, Users createUser, boolean sendMail, boolean createTask, Tasks task, ConfigDocProcessEnum configDocProcessEnum, MailDef mailDef) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();

		for (CompanyUsers user : users) {
			Tasks tasks = null;
			if (createTask) {
				FixTaskDataService.instance().replaceStrings(targetClass, description, targetKey, createUser);
				tasks = new Tasks(description, createUser, targetKey, targetClass, TaskStatusEnum.NotStarted, GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()), UUID.randomUUID().toString());
				tasks.setWorkflowProcess(configDocProcessEnum);
				tasks.setPreviousTask(task);
				dataEntities.add(tasks);
				dataEntities.add(new TaskUsers(user.getUser(), tasks));
			}
			if (sendMail) {
				this.sendMail(user.getUser(), "New task created on merSETA NSDMS portal", mailDef, description);
			}
		}
		completeTask(task);
		this.dao.createBatch(dataEntities);
		// return tasks;
	}
	
	public void createOneTaskCompanyUsers(List<CompanyUsers> users, String targetClass, Long targetKey, String description, Users createUser, boolean sendMail, boolean createTask, Tasks task, ConfigDocProcessEnum configDocProcessEnum, MailDef mailDef) throws Exception {	List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		Tasks tasks = null;
		FixTaskDataService.instance().replaceStrings(targetClass, description, targetKey, createUser);
		tasks = new Tasks(description, createUser, targetKey, targetClass, TaskStatusEnum.NotStarted, GenericUtility.add3DaysCountingFromStartDate(getSynchronizedDate()), UUID.randomUUID().toString());
		tasks.setWorkflowProcess(configDocProcessEnum);
		tasks.setPreviousTask(task);
		dataEntities.add(tasks);
		for (CompanyUsers user : users) {
			dataEntities.add(new TaskUsers(user.getUser(), tasks));
		}
		completeTask(task);
		this.dao.createBatch(dataEntities);
		for (CompanyUsers user : users) {
			if (sendMail) {
				this.sendMail(user.getUser(), "New task created on merSETA NSDMS portal", mailDef, description);
			}
		}
	}

	/**
	 * Complete task.
	 *
	 * @param task
	 *            the task
	 * @throws Exception
	 *             the exception
	 */
	public void completeTask(Tasks task) throws Exception {
		//System.out.println("completeTask");
		if (task != null) {
			task.setTaskStatus(TaskStatusEnum.Completed);
			task.setCompletionDate(getSynchronizedDate());
			update(task);
		}
	}

	/**
	 * Complete task by target key.
	 *
	 * @param docProcessEnum
	 *            the doc process enum
	 * @param targetKey
	 *            the target key
	 * @throws Exception
	 *             the exception
	 */
	public void completeTaskByTargetKey(ConfigDocProcessEnum docProcessEnum, Long targetKey) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		List<Tasks> t = findTasksByTypeAndKey(docProcessEnum, targetKey);
		for (Tasks tasks : t) {
			tasks.setTaskStatus(TaskStatusEnum.Completed);
			dataEntities.add(tasks);
		}
		dao.updateBatch(dataEntities);
	}

	public void completeTaskByTargetKey(String targetClass, Long targetKey) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		List<Tasks> t = findTasksByTypeAndKey(targetClass, targetKey);
		for (Tasks tasks : t) {
			tasks.setTaskStatus(TaskStatusEnum.Completed);
			dataEntities.add(tasks);
		}
		dao.updateBatch(dataEntities);
	}
	
	public void closeOpenTasksByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		List<Tasks> t = findTasksByTargetClassKeyAndStatusList(targetClass, targetKey, TaskStatusEnum.getTaskStatusOpen());
		for (Tasks tasks : t) {
			tasks.setTaskStatus(TaskStatusEnum.Closed);
			tasks.setCompletionDate(getSynchronizedDate());
			dataEntities.add(tasks);
		}
		dao.updateBatch(dataEntities);
	}


	public void assignUsersToTask(List<UsersRole> usersRoles, Tasks tasks, boolean sendMail) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		for (UsersRole usersRole : usersRoles) {
			dataEntities.add(new TaskUsers(usersRole.getUsers(), tasks));
			if (sendMail) {
				this.sendMail(usersRole.getUsers(), "New task created on merSETA NSDMS portal", null, tasks.getDescription());
			}
		}
		dao.createBatch(dataEntities);
	}

	public List<TaskReportBean> findTaskBySDFCompanyforCompany(Company company) throws Exception {
		if (company == null || company.getId() == null) throw new Exception("Need company details to run this report");

		return dao.findTaskBySDFCompanyforCompany(company.getId());
	}

	public List<TaskReportBean> findTaskBySDFCompanyforUser(Users user) throws Exception {
		if (user == null || user.getId() == null) throw new Exception("Need sdf details to run this report");
		List<TaskReportBean> l = dao.findTaskBySDFCompanyforUser(user.getId());
		l.addAll(findTaskByUser(user));
		return resolveUsers(l);
	}

	public List<TaskReportBean> findTaskByUser(Users user) throws Exception {
		return dao.findTaskByUser(user.getId());
	}

	public int countTasksUsersByTask(Long taskId) throws Exception {
		return dao.countTasksUsersByTask(taskId);
	}

	public List<TaskReportBean> findTaskForCompany(Company company) throws Exception {
		return dao.findTaskForCompany(company.getId());
	}

	public List<TaskReportBean> allTasksForCompany(Company company) throws Exception {
		List<TaskReportBean> l = findTaskBySDFCompanyforCompany(company);
		l.addAll(findTaskForCompany(company));
		return resolveUsers(l);
	}

	private List<TaskReportBean> resolveUsers(List<TaskReportBean> l) throws Exception {
		for (TaskReportBean tb : l) {
			tb.setTask(findByKey(tb.getTask().getId()));
		}
		return l;
	}

	public List<TaskUsers> findOpenTaskUsers(ConfigDocProcessEnum workflwoProcess) throws Exception {
		return dao.findOpenTaskUsers(workflwoProcess);
	}

	public List<Tasks> findTasksByTypeAndKey(String targetClass, Long targetKey) throws Exception {
		return dao.findTasksByTypeAndKey(targetClass, targetKey);
	}
	
	public List<Tasks> findTasksByTargetClassKeyAndStatusList(String targetClass, Long targetKey, List<TaskStatusEnum> statusList) throws Exception {
		return dao.findTasksByTargetClassKeyAndStatusList(targetClass, targetKey, statusList);
	}

	public List<Tasks> findOpenTasksByProcessRoleId(Long processRoleId) throws Exception {
		return dao.findOpenTasksByProcessRoleId(processRoleId);
	}

	public boolean checkCanCreateTask(String targetClass, Long targetKey, ProcessRoles pr) {
		if (pr.getTargetClass() != null && !pr.getTargetClass().isEmpty() && pr.getTargetMethod() != null && !pr.getTargetMethod().isEmpty()) {
			try {
				IDataEntity target = dao.getByClassAndKey(targetClass, targetKey);
				Object obj = Class.forName(pr.getTargetClass()).newInstance();
				Method m = obj.getClass().getDeclaredMethod(pr.getTargetMethod(), Class.forName(targetClass), ProcessRoles.class);
				return (boolean) m.invoke(obj, target, pr);
			} catch (NoSuchMethodException e) {
				e.printStackTrace();
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	public List<TaskUserReportBean> locateTaskSummaryEmployeesByTargetClassWsp(String targetClass, Integer finYear) throws Exception {
		return dao.locateTaskSummaryEmployeesByTargetClassWsp(targetClass, finYear);
	}

	public List<TaskUserReportBean> locateTaskSummaryEmployeesByTargetClassDG(String targetClass, Integer finYear) throws Exception {
		return dao.locateTaskSummaryEmployeesByTargetClassDG(targetClass, finYear);
	}

	public Users findUserByCompany(Company company) throws Exception {
		return dao.findUserByCompany(company);
	}

	public List<Users> findUserByQdfCompany(Long targetKey, String targetClass, ConfigDocProcessEnum workflowProcess) throws Exception {
		return dao.findUserByQdfCompany(targetKey, targetClass, workflowProcess);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tasks> allTasksByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long targetKey, String targetClass) throws Exception {
		String hql = "select o from Tasks o where o.targetKey = :targetKey and o.targetClass = :targetClass";
		filters.put("targetKey", targetKey);
		filters.put("targetClass", targetClass);
		return populateAdditionalInformationList((List<Tasks>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql));
	}

	public int countAllTasksByTargetClassAndKey( Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Tasks o where o.targetKey = :targetKey and o.targetClass = :targetClass";
		return  dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tasks> allTasksByActionUser(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long actionUserId) throws Exception {
		String hql = "select o from Tasks o where o.actionUser.id = :actionUserId";
		filters.put("actionUserId", actionUserId);
		return populateAdditionalInformationList((List<Tasks>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql));
	}

	public int countAllTasksByActionUser( Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Tasks o where o.actionUser.id = :actionUserId";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tasks> allTasksUserLinked(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long actionUserId) throws Exception {
		String hql = "select o from Tasks o where o.id in (select x.task.id from TaskUsers x where x.user.id = :actionUserId) ";
		filters.put("actionUserId", actionUserId);
		return populateAdditionalInformationList((List<Tasks>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql));
	}

	public int countAllTasksUserLinked(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from Tasks o where o.id in (select x.task.id from TaskUsers x where x.user.id = :actionUserId) ";
		return dao.countWhere(filters, hql);
	}
	
	private List<Tasks> populateAdditionalInformationList(List<Tasks> tasksList) throws Exception{
		for (Tasks tasks : tasksList) {
			populateAdditionalInformation(tasks);
		}
		return tasksList;
	}
	
	private void populateAdditionalInformation(Tasks task) throws Exception{
		if (task.getId() != null) {
			if (usersService == null) {
				usersService = new UsersService();
			}
			if (taskUsersService == null) {
				taskUsersService = new TaskUsersService();
			}
			
			// populate action user
			if (task.getActionUser() != null && task.getActionUser().getId() != null) {
				task.setActionUser(usersService.findByKey(task.getActionUser().getId()));
			}
			
			// populate task users
			List<TaskUsers> taskUsersAssigned = taskUsersService.findByTask(task);
			if (taskUsersAssigned.size() != 0) {
				task.setTaskUsersList(taskUsersAssigned);
			} else {
				task.setTaskUsersList(new ArrayList<>());
			}
		}
	}

	public boolean taskOpenBasedOnStatus(TaskStatusEnum taskStatusEnum) throws Exception{
		boolean taskOpen = false;
		switch (taskStatusEnum) {
		case NotStarted:
			taskOpen = true;
			break;
		case Underway:
			taskOpen = true;
			break;
		case Overdue:
			taskOpen = true;
			break;
		default:
			taskOpen = false;
			break;
		}
		return taskOpen;
	}

	public boolean canEditBasedOnRolePermission(UserPermissionEnum userPermission) throws Exception {
		boolean canedit = false;
		switch (userPermission) {
			case Edit:
				canedit = true;
				break;
			case EditUpload:
				canedit = true;
				break;
			case FinalEdit:
				canedit = true;
				break;
			case FinalEditUpload:
				canedit = true;
				break;
			// case Approve:
			//
			// break;
			// case FinalApproval:
			//
			// break;
			// case FinalUpload:
			//
			// break;
			// case FinalUploadApproval:
			//
			// break;
			// case FinalView:
			//
			// break;
			// case SignOff:
			//
			// break;
			// case Upload:
			//
			// break;
			// case UploadApprove:
			//
			// break;
			// case UploadCSV:
			//
			// break;
			// case View:
			//
			// break;
			default:
				canedit = false;
				break;
		}
		return canedit;
	}

	public boolean canUploadBasedOnRolePermission(UserPermissionEnum userPermission) throws Exception {
		boolean canupload = false;
		switch (userPermission) {
			case EditUpload:
				canupload = true;
				break;
			case FinalUpload:
				canupload = true;
				break;
			case FinalUploadApproval:
				canupload = true;
				break;
			case Upload:
				canupload = true;
				break;
			case UploadApprove:
				canupload = true;
				break;
			case UploadCSV:
				canupload = true;
				break;
			case FinalEditUpload:
				canupload = true;
				break;
			// case Edit:
			// canupload = true;
			// break;
			// case Approve:
			//
			// break;
			// case FinalApproval:
			//
			// break;
			// case FinalEdit:
			// canupload = true;
			// break;
			// case FinalView:
			//
			// break;
			// case SignOff:
			//
			// break;
			// case View:
			//
			// break;
			default:
				canupload = false;
				break;
		}
		return canupload;
	}
	
	public boolean canCompleteTaskBasedOnRolePermission(UserPermissionEnum userPermission) throws Exception {
		boolean completeTask = false;
		switch (userPermission) {
			case Edit:
				completeTask = true;
				break;
			case EditUpload:
				completeTask = true;
				break;
			case SignOff:
				completeTask = true;
				break;
			case View:
				completeTask = true;
				break;
			case Upload:
				completeTask = true;
				break;
			case UploadCSV:
				completeTask = true;
				break;
			default:
				completeTask = false;
				break;
		}
		return completeTask;
	}
	
	public boolean canApproveTaskBasedOnRolePermission(UserPermissionEnum userPermission) throws Exception {
		boolean approveTask = false;
		switch (userPermission) {
			case Approve:
				approveTask = true;
				break;
			case UploadApprove:
				approveTask = true;
				break;
			default:
				approveTask = false;
				break;
		}
		return approveTask;
	}
	
	public boolean canFinalApproveTaskBasedOnRolePermission(UserPermissionEnum userPermission) throws Exception {
		boolean finalApproveTask = false;
		switch (userPermission) {
			case FinalApproval:
				finalApproveTask = true;
				break;
			case FinalEdit:
				finalApproveTask = true;
				break;
			case FinalEditUpload:
				finalApproveTask = true;
				break;
			case FinalUpload:
				finalApproveTask = true;
				break;
			case FinalUploadApproval:
				finalApproveTask = true;
				break;
			case FinalView:
				finalApproveTask = true;
				break;
			default:
				finalApproveTask = false;
				break;
		}
		return finalApproveTask;
	}
	
	public boolean canSignOffTaskBasedOnRolePermission(UserPermissionEnum userPermission) throws Exception {
		boolean canSignOffTask = false;
		switch (userPermission) {
			case SignOff:
				canSignOffTask = true;
				break;
			default:
				canSignOffTask = false;
				break;
		}
		return canSignOffTask;
	}
	
	/*
	 * Reporting Start 
	 */
	public List<DescriptionCounterBean> generateReportSummaryForUser(Long userId, boolean dateRange, Date fromDate, Date toDate) throws Exception {
		List<DescriptionCounterBean> reportList = new ArrayList<>(); 
		List<CounterBean> resultsList = new ArrayList<>();
		
		// report one total tasks completed by User
		if (dateRange) {
			resultsList = locateTotalTasksByUserAndStatusAndDateRange(userId, TaskStatusEnum.Completed, fromDate, toDate);
		} else {
			resultsList = locateTotalTasksByUserAndStatus(userId, TaskStatusEnum.Completed);
		}
		if (resultsList.isEmpty()) {
			reportList.add(new DescriptionCounterBean("Total Tasks Completed By User", BigInteger.valueOf(0), getSynchronizedDate(), fromDate, toDate));
		} else {
			reportList.add(new DescriptionCounterBean("Total Tasks Completed By User", resultsList.get(0).getCounter(), getSynchronizedDate(), fromDate, toDate));
		}
		// total tasks underway by user
		resultsList.clear();
		if (dateRange) {
			resultsList = locateTotalTasksByUserAndStatusAndDateRange(userId, TaskStatusEnum.Underway, fromDate, toDate);
		} else {
			resultsList = locateTotalTasksByUserAndStatus(userId, TaskStatusEnum.Underway);
		}
		if (resultsList.isEmpty()) {
			reportList.add(new DescriptionCounterBean("Total Tasks Underway By User", BigInteger.valueOf(0), getSynchronizedDate(), fromDate, toDate));
		} else {
			reportList.add(new DescriptionCounterBean("Total Tasks Underway By User", resultsList.get(0).getCounter(), getSynchronizedDate(), fromDate, toDate));
		}
		// total tasks assigned to user
		resultsList.clear();
		if (dateRange) {
			resultsList =  countTotalTasksAssignedToUserBetweenDates(userId, TaskStatusEnum.getNotInErrorStatusList(), fromDate, toDate);
		} else {
			resultsList =  countTotalTasksAssignedToUser(userId, TaskStatusEnum.getNotInErrorStatusList());
		}
		if (resultsList.isEmpty()) {
			reportList.add(new DescriptionCounterBean("Total Tasks Assigned To User", BigInteger.valueOf(0), getSynchronizedDate(), fromDate, toDate));
		} else {
			reportList.add(new DescriptionCounterBean("Total Tasks Assigned To User", resultsList.get(0).getCounter(), getSynchronizedDate(), fromDate, toDate));
		}
		// total tasks completed within SLA
		resultsList.clear();
		if (dateRange) {
			resultsList = countTotalTasksByUserAndStatusAndBeforeDueDateBetweenDates(userId, TaskStatusEnum.Completed, fromDate, toDate);
		} else {
			resultsList = countTotalTasksByUserAndStatusAndBeforeDueDate(userId, TaskStatusEnum.Completed);
		}
		if (resultsList.isEmpty()) {
			reportList.add(new DescriptionCounterBean("Total Tasks Completed By User In SLA / Before Task Due Date", BigInteger.valueOf(0), getSynchronizedDate(), fromDate, toDate));
		} else {
			reportList.add(new DescriptionCounterBean("Total Tasks Completed By User In SLA / Before Task Due Date", resultsList.get(0).getCounter(), getSynchronizedDate(), fromDate, toDate));
		}
		// total tasks completed where went over by SLA
		resultsList.clear();
		if (dateRange) {
			resultsList = countTotalTasksByUserAndStatusAndAfterDueDateBetweenDatre(userId, TaskStatusEnum.Completed, fromDate, toDate);
		} else {
			resultsList = countTotalTasksByUserAndStatusAndAfterDueDate(userId, TaskStatusEnum.Completed);
		}
		if (resultsList.isEmpty()) {
			reportList.add(new DescriptionCounterBean("Total Tasks Completed By User Not In SLA / After Task Due Date", BigInteger.valueOf(0), getSynchronizedDate(), fromDate, toDate));
		} else {
			reportList.add(new DescriptionCounterBean("Total Tasks Completed By User Not In SLA / After Task Due Date", resultsList.get(0).getCounter(), getSynchronizedDate(), fromDate, toDate));
		}
		
		resultsList.clear();
		return reportList;
	}
	
	public List<DescriptionCounterBean> generateReportSummaryForHostingCompanyProcess(Long hostingCompanyProcessId, Long userId,  boolean dateRange, Date fromDate, Date toDate) throws Exception {
		List<DescriptionCounterBean> reportList = new ArrayList<>(); 
		List<CounterBean> resultsList = new ArrayList<>();
		
		// report one total tasks completed by User
		if (dateRange) {
			resultsList = locateTotalTasksByHostingCompanyProcessStatusAndDateRange(hostingCompanyProcessId, userId, TaskStatusEnum.Completed, fromDate, toDate, true);
		} else {
			resultsList = locateTotalTasksByHostingCompanyProcessStatusAndDateRange(hostingCompanyProcessId, userId, TaskStatusEnum.Completed, null, null, true);
		}
		StringBuilder description = new StringBuilder();
		description.append("Total Tasks Completed By Employee");
		if (userId != null) {
			description = new StringBuilder();
			description.append("Total Tasks Completed By User");
		}
		if (resultsList.isEmpty()) {
			reportList.add(new DescriptionCounterBean(description.toString(), BigInteger.valueOf(0), getSynchronizedDate(), fromDate, toDate));
		} else {
			reportList.add(new DescriptionCounterBean(description.toString(), resultsList.get(0).getCounter(), getSynchronizedDate(), fromDate, toDate));
		}
		// total tasks underway by user
		resultsList.clear();
		if (dateRange) {
			resultsList = locateTotalTasksByHostingCompanyProcessStatusAndDateRange(hostingCompanyProcessId, userId, TaskStatusEnum.Underway, fromDate, toDate, true);
		} else {
			resultsList = locateTotalTasksByHostingCompanyProcessStatusAndDateRange(hostingCompanyProcessId, userId, TaskStatusEnum.Underway, null, null, true);
		}
		description = new StringBuilder();
		description.append("Total Tasks Underway By Employees");
		if (userId != null) {
			description = new StringBuilder();
			description.append("Total Tasks Underway By User");
		}
		if (resultsList.isEmpty()) {
			reportList.add(new DescriptionCounterBean(description.toString(), BigInteger.valueOf(0), getSynchronizedDate(), fromDate, toDate));
		} else {
			reportList.add(new DescriptionCounterBean(description.toString(), resultsList.get(0).getCounter(), getSynchronizedDate(), fromDate, toDate));
		}
		// total tasks assigned to user
		resultsList.clear();
		if (dateRange) {
			resultsList =  countTotalTasksAssignedToHostingCompanyProcessBetweenDates(hostingCompanyProcessId, userId, TaskStatusEnum.getNotInErrorStatusList(), fromDate, toDate, true);
		} else {
			resultsList =  countTotalTasksAssignedToHostingCompanyProcessBetweenDates(hostingCompanyProcessId, userId, TaskStatusEnum.getNotInErrorStatusList(), null, null, true);
		}
		description = new StringBuilder();
		description.append("Total Tasks Assigned To Employees");
		if (userId != null) {
			description = new StringBuilder();
			description.append("Total Tasks Assigned To User");
		}
		if (resultsList.isEmpty()) {
			reportList.add(new DescriptionCounterBean(description.toString(), BigInteger.valueOf(0), getSynchronizedDate(), fromDate, toDate));
		} else {
			reportList.add(new DescriptionCounterBean(description.toString(), resultsList.get(0).getCounter(), getSynchronizedDate(), fromDate, toDate));
		}
		// total tasks completed within SLA
		resultsList.clear();
		if (dateRange) {
			resultsList = countTotalTasksByHostingCompanyProcessStatusAndBeforeDueDateBetweenDates(hostingCompanyProcessId, userId, TaskStatusEnum.Completed, fromDate, toDate, true);
		} else {
			resultsList = countTotalTasksByHostingCompanyProcessStatusAndBeforeDueDateBetweenDates(hostingCompanyProcessId, userId, TaskStatusEnum.Completed, null, null, true);
		}
		description = new StringBuilder();
		description.append("Total Tasks Completed By Employees In SLA / Before Task Due Date");
		if (userId != null) {
			description = new StringBuilder();
			description.append("Total Tasks Completed By User In SLA / Before Task Due Date");
		}
		if (resultsList.isEmpty()) {
			reportList.add(new DescriptionCounterBean(description.toString(), BigInteger.valueOf(0), getSynchronizedDate(), fromDate, toDate));
		} else {
			reportList.add(new DescriptionCounterBean(description.toString(), resultsList.get(0).getCounter(), getSynchronizedDate(), fromDate, toDate));
		}
		// total tasks completed where went over by SLA
		resultsList.clear();
		if (dateRange) {
			resultsList = countTotalTasksByHostingCompanyProcessStatusAndAfterDueDateBetweenDate(hostingCompanyProcessId, userId, TaskStatusEnum.Completed, fromDate, toDate, true);
		} else {
			resultsList = countTotalTasksByHostingCompanyProcessStatusAndAfterDueDateBetweenDate(hostingCompanyProcessId, userId, TaskStatusEnum.Completed, null, null, true);
		}
		description = new StringBuilder();
		description.append("Total Tasks Completed By Employees Not In SLA / After Task Due Date");
		if (userId != null) {
			description = new StringBuilder();
			description.append("Total Tasks Completed By User Not In SLA / After Task Due Date");
		}
		if (resultsList.isEmpty()) {
			reportList.add(new DescriptionCounterBean(description.toString(), BigInteger.valueOf(0), getSynchronizedDate(), fromDate, toDate));
		} else {
			reportList.add(new DescriptionCounterBean(description.toString(), resultsList.get(0).getCounter(), getSynchronizedDate(), fromDate, toDate));
		}
		resultsList.clear();
		return reportList;
	}
	
	public List<TaskProcessReport> populateTaskReportByHostingComapnyProcess(Long userId, boolean dateRange, Date fromDate, Date toDate) throws Exception {
		List<TaskProcessReport> reportList = new ArrayList<>(); 
		List<CounterBean> resultsList = new ArrayList<>();
		if (dateRange) {
			reportList = populateDistinctHostingCompanyProcessByUserIdWithDateRanges(userId, TaskStatusEnum.getNotInErrorStatusList(), fromDate, toDate);
		} else {
			reportList = populateDistinctHostingCompanyProcessByUserId(userId, TaskStatusEnum.getNotInErrorStatusList());
		}
		TaskProcessReport tallyResult = new TaskProcessReport(getSynchronizedDate(), "TOTAL");
		tallyResult.setFromDate(fromDate);
		tallyResult.setToDate(toDate);
		
		for (TaskProcessReport report : reportList) {
			report.setGenerated(getSynchronizedDate());
			report.setHostingCompanyProcess(hostingCompanyProcessService.findByKey(report.getHostingCompanyProcessId().longValue()));
			report.setFromDate(fromDate);
			report.setToDate(toDate);
			// completed by user
			resultsList.clear();
			if (dateRange) {
				resultsList = locateTotalTasksByUserAndStatusWithHostingComapnyProcessAndDateRange(userId, TaskStatusEnum.Completed, report.getHostingCompanyProcessId().longValue(), fromDate, toDate);
			} else {
				resultsList = locateTotalTasksByUserAndStatusWithHostingComapnyProcess(userId, TaskStatusEnum.Completed, report.getHostingCompanyProcessId().longValue());
			}
			if (resultsList.isEmpty()) {
				report.setCompletedCounter(BigInteger.valueOf(0));
			} else {
				report.setCompletedCounter(resultsList.get(0).getCounter());
			}
			tallyResult.setCompletedCounter(tallyResult.getCompletedCounter().add(report.getCompletedCounter()));
			resultsList.clear();
			// underway by user
			if (dateRange) {
				resultsList = locateTotalTasksByUserAndStatusWithHostingComapnyProcessAndDateRange(userId, TaskStatusEnum.Underway, report.getHostingCompanyProcessId().longValue(), fromDate, toDate);
			}else {
				resultsList = locateTotalTasksByUserAndStatusWithHostingComapnyProcess(userId, TaskStatusEnum.Underway, report.getHostingCompanyProcessId().longValue());
			}
			if (resultsList.isEmpty()) {
				report.setUnderwayCounter(BigInteger.valueOf(0));
			} else {
				report.setUnderwayCounter(resultsList.get(0).getCounter());
			}
			tallyResult.setUnderwayCounter(tallyResult.getUnderwayCounter().add(report.getUnderwayCounter()));
			// total tasks assigned to user
			resultsList.clear();
			resultsList = countTotalTasksAssignedToUserByHostingCompanyProcess(userId, TaskStatusEnum.getNotInErrorStatusList(), report.getHostingCompanyProcessId().longValue());
			
			if (dateRange) {
				resultsList = countTotalTasksAssignedToUserByHostingCompanyProcessBetweenDates(userId, TaskStatusEnum.getNotInErrorStatusList(), report.getHostingCompanyProcessId().longValue(), fromDate, toDate)	;
			}else {
				resultsList = countTotalTasksAssignedToUserByHostingCompanyProcess(userId, TaskStatusEnum.getNotInErrorStatusList(), report.getHostingCompanyProcessId().longValue());
			}
			
			if (resultsList.isEmpty()) {
				report.setAssignedCounter(BigInteger.valueOf(0));
			} else {
				report.setAssignedCounter(resultsList.get(0).getCounter());
			}
			tallyResult.setAssignedCounter(tallyResult.getAssignedCounter().add(report.getAssignedCounter()));
			// total tasks completed within SLA
			resultsList.clear();
			
			if (dateRange) {
				resultsList = countTotalTasksByUserAndStatusAndBeforeDueDateAndByHostingCompanyProcessBetweenDates(userId, TaskStatusEnum.Completed, report.getHostingCompanyProcessId().longValue(), fromDate, toDate);
			}else {
				resultsList = countTotalTasksByUserAndStatusAndBeforeDueDateAndByHostingCompanyProcess(userId, TaskStatusEnum.Completed, report.getHostingCompanyProcessId().longValue());
			}
			
			if (resultsList.isEmpty()) {
				report.setCompletedInSlaCounter(BigInteger.valueOf(0));
			} else {
				report.setCompletedInSlaCounter(resultsList.get(0).getCounter());
			}
			tallyResult.setCompletedInSlaCounter(tallyResult.getCompletedInSlaCounter().add(report.getCompletedInSlaCounter()));
			// total tasks completed where went over by SLA
			resultsList.clear();
			if (dateRange) {
				resultsList = countTotalTasksByUserAndStatusAndAfterDueDateAndByHostingCompanyProcessBetweenDates(userId, TaskStatusEnum.Completed, report.getHostingCompanyProcessId().longValue(), fromDate, toDate);
			}else {
				resultsList = countTotalTasksByUserAndStatusAndAfterDueDateAndByHostingCompanyProcess(userId, TaskStatusEnum.Completed, report.getHostingCompanyProcessId().longValue());
			}
			if (resultsList.isEmpty()) {
				report.setCompletedNotInSlaCounter(BigInteger.valueOf(0));
			} else {
				report.setCompletedNotInSlaCounter(resultsList.get(0).getCounter());
			}
			tallyResult.setCompletedNotInSlaCounter(tallyResult.getCompletedNotInSlaCounter().add(report.getCompletedNotInSlaCounter()));
		}
		resultsList.clear();
		reportList.add(tallyResult);
		return reportList;
	}
	
	public List<CounterBean> locateTotalTasksByUserAndStatus(Long userId, TaskStatusEnum status) throws Exception {
		return dao.locateTotalTasksByUserAndStatus(userId, status);
	}
	
	public List<CounterBean> locateTotalTasksByUserAndStatusAndDateRange(Long userId, TaskStatusEnum status, Date fromDate, Date toDate) throws Exception {
		return dao.locateTotalTasksByUserAndStatusAndDateRange(userId, status, fromDate, toDate);
	}
	
	public List<CounterBean> locateTotalTasksByHostingCompanyProcessStatusAndDateRange(Long hostingComapnyProcessId, Long userId, TaskStatusEnum status, Date fromDate, Date toDate, Boolean employeeTasksOnly) throws Exception {
		return dao.locateTotalTasksByHostingCompanyProcessStatusAndDateRange(hostingComapnyProcessId, userId, status, fromDate, toDate, employeeTasksOnly);
	}
	
	public List<CounterBean> locateTotalTasksByUserAndStatusWithHostingComapnyProcess(Long userId, TaskStatusEnum status, Long hostingCompanyProcessId) throws Exception {
		return dao.locateTotalTasksByUserAndStatusWithHostingComapnyProcess(userId, status, hostingCompanyProcessId);
	}
	
	public List<CounterBean> locateTotalTasksByUserAndStatusWithHostingComapnyProcessAndDateRange(Long userId, TaskStatusEnum status, Long hostingCompanyProcessId, Date fromDate, Date toDate) throws Exception {
		return dao.locateTotalTasksByUserAndStatusWithHostingComapnyProcessAndDateRange(userId, status, hostingCompanyProcessId, fromDate, toDate);
	}
	
	public List<CounterBean> countTotalTasksAssignedToUser(Long userId, List<TaskStatusEnum> statusList) throws Exception {	
		return dao.countTotalTasksAssignedToUser(userId, statusList);
	}
	
	public List<CounterBean> countTotalTasksAssignedToUserBetweenDates(Long userId, List<TaskStatusEnum> statusList, Date fromDate, Date toDate) throws Exception {
		return dao.countTotalTasksAssignedToUserBetweenDates(userId, statusList, fromDate, toDate);
	}
	
	public List<CounterBean> countTotalTasksAssignedToHostingCompanyProcessBetweenDates(Long hostingComapnyProcessId, Long userId, List<TaskStatusEnum> statusList, Date fromDate, Date toDate, Boolean employeeTasksOnly) throws Exception {
		return dao.countTotalTasksAssignedToHostingCompanyProcessBetweenDates(hostingComapnyProcessId, userId, statusList, fromDate, toDate, employeeTasksOnly);
	}
	
	public List<CounterBean> countTotalTasksAssignedToUserByHostingCompanyProcess(Long userId, List<TaskStatusEnum> statusList, Long hostingCompanyProcessId) throws Exception {
		return dao.countTotalTasksAssignedToUserByHostingCompanyProcess(userId, statusList, hostingCompanyProcessId);
	}
	
	public List<CounterBean> countTotalTasksAssignedToUserByHostingCompanyProcessBetweenDates(Long userId, List<TaskStatusEnum> statusList, Long hostingCompanyProcessId, Date fromDate, Date toDate) throws Exception {
		return dao.countTotalTasksAssignedToUserByHostingCompanyProcessBetweenDates(userId, statusList, hostingCompanyProcessId, fromDate, toDate);
	}
	
	public List<CounterBean> countTotalTasksAssignedToUserByHostingCompanyProcessWithDateRange(Long userId, List<TaskStatusEnum> statusList, Long hostingCompanyProcessId, Date fromDate, Date toDate) throws Exception {
		return dao.countTotalTasksAssignedToUserByHostingCompanyProcessWithDateRange(userId, statusList, hostingCompanyProcessId, fromDate, toDate);
	}
	
	public List<CounterBean> locateTotalTasksByUserAndStatusBetweenDates(Long userId, TaskStatusEnum status, Date fromDate, Date toDate) throws Exception {
		return dao.locateTotalTasksByUserAndStatusBetweenDates(userId, status, fromDate, toDate);
	}
	
	public List<CounterBean> countTotalTasksByUserAndStatusAndBeforeDueDate(Long userId, TaskStatusEnum status) throws Exception {
		return dao.countTotalTasksByUserAndStatusAndBeforeDueDate(userId, status);
	}
	
	public List<CounterBean> countTotalTasksByUserAndStatusAndBeforeDueDateBetweenDates(Long userId, TaskStatusEnum status, Date fromDate, Date toDate) throws Exception {
		return dao.countTotalTasksByUserAndStatusAndBeforeDueDateBetweenDates(userId, status, fromDate, toDate);
	}
	
	public List<CounterBean> countTotalTasksByHostingCompanyProcessStatusAndBeforeDueDateBetweenDates(Long hostingCompanyProcessId, Long userId, TaskStatusEnum status, Date fromDate, Date toDate, Boolean employeeTasksOnly) throws Exception {
		return dao.countTotalTasksByHostingCompanyProcessStatusAndBeforeDueDateBetweenDates(hostingCompanyProcessId, userId, status, fromDate, toDate, employeeTasksOnly);
	}
	
	public List<CounterBean> countTotalTasksByUserAndStatusAndBeforeDueDateAndByHostingCompanyProcess(Long userId, TaskStatusEnum status , Long hostingCompanyProcessId ) throws Exception {
		return dao.countTotalTasksByUserAndStatusAndBeforeDueDateAndByHostingCompanyProcess(userId, status, hostingCompanyProcessId);
	}
	
	public List<CounterBean> countTotalTasksByUserAndStatusAndBeforeDueDateAndByHostingCompanyProcessBetweenDates(Long userId, TaskStatusEnum status , Long hostingCompanyProcessId, Date fromDate, Date toDate) throws Exception {
		return dao.countTotalTasksByUserAndStatusAndBeforeDueDateAndByHostingCompanyProcessBetweenDates(userId, status, hostingCompanyProcessId, fromDate, toDate);
	}
	
	public List<CounterBean> countTotalTasksByUserAndStatusAndAfterDueDate(Long userId, TaskStatusEnum status) throws Exception {
		return dao.countTotalTasksByUserAndStatusAndAfterDueDate(userId, status);
	}
	
	public List<CounterBean> countTotalTasksByUserAndStatusAndAfterDueDateBetweenDatre(Long userId, TaskStatusEnum status, Date fromDate, Date toDate) throws Exception {
		return dao.countTotalTasksByUserAndStatusAndAfterDueDateBetweenDatre(userId, status, fromDate, toDate);
	}
	
	public List<CounterBean> countTotalTasksByHostingCompanyProcessStatusAndAfterDueDateBetweenDate(Long hostingComapnyProcessId, Long userId, TaskStatusEnum status, Date fromDate, Date toDate, Boolean employeeTasksOnly) throws Exception {
		return dao.countTotalTasksByHostingCompanyProcessStatusAndAfterDueDateBetweenDate(hostingComapnyProcessId, userId, status, fromDate, toDate, employeeTasksOnly);
	}
	
	public List<CounterBean> countTotalTasksByUserAndStatusAndAfterDueDateAndByHostingCompanyProcess(Long userId, TaskStatusEnum status, Long hostingCompanyProcessId) throws Exception {
		return dao.countTotalTasksByUserAndStatusAndAfterDueDateAndByHostingCompanyProcess(userId, status, hostingCompanyProcessId);
	}
	
	public List<CounterBean> countTotalTasksByUserAndStatusAndAfterDueDateAndByHostingCompanyProcessBetweenDates(Long userId, TaskStatusEnum status, Long hostingCompanyProcessId, Date fromDate, Date toDate) throws Exception {
		return dao.countTotalTasksByUserAndStatusAndAfterDueDateAndByHostingCompanyProcessBetweenDates(userId, status, hostingCompanyProcessId, fromDate, toDate);
	}
	
	public List<TaskProcessReport> populateDistinctHostingCompanyProcessByUserId(Long userId, List<TaskStatusEnum> statusList) throws Exception {
		return dao.populateDistinctHostingCompanyProcessByUserId(userId, statusList);
	}
	
	public List<TaskProcessReport> populateDistinctHostingCompanyProcessByUserIdWithDateRanges(Long userId, List<TaskStatusEnum> statusList, Date fromDate, Date toDate) throws Exception {
		return dao.populateDistinctHostingCompanyProcessByUserIdWithDateRanges(userId, statusList, fromDate, toDate);
	}

	/*
	 * Reporting END 
	 */
}
