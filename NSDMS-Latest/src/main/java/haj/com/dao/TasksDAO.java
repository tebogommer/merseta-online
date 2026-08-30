package haj.com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang3.time.DateUtils;
import org.primefaces.model.SortOrder;

import haj.com.bean.CounterBean;
import haj.com.bean.TaskProcessReport;
import haj.com.bean.TaskReportBean;
import haj.com.bean.TaskUserReportBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.TaskUsers;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.AccreditationApplicationTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.RagEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class TasksDAO.
 */
public class TasksDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Tasks.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Tasks}
	 * @throws Exception
	 *             global exception
	 * @see Tasks
	 */
	@SuppressWarnings("unchecked")
	public List<Tasks> allTasks() throws Exception {
		return (List<Tasks>) super.getList("select o from Tasks o");
	}
	
	@SuppressWarnings("unchecked")
	public List<Tasks> allTasksDesc() throws Exception {
		return (List<Tasks>) super.getList("select o from Tasks o order by o.id desc");
	}

	/**
	 * Get all Tasks between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.Tasks}
	 * @throws Exception
	 *             global exception
	 * @see Tasks
	 */
	@SuppressWarnings("unchecked")
	public List<Tasks> allTasks(Long from, int noRows) throws Exception {
		String hql = "select o from Tasks o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<Tasks>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Tasks}
	 * @throws Exception
	 *             global exception
	 * @see Tasks
	 */
	public Tasks findByKey(Long id) throws Exception {
		String hql = "select o from Tasks o left join fetch o.processRole pr left join fetch pr.roles roles left join fetch pr.hostingCompanyProcess hcp left join fetch o.actionUser au left join fetch o.createUser cu where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Tasks) super.getUniqueResult(hql, parameters);
	}

	public Tasks findByGuid(String id) throws Exception {
		String hql = "select o from Tasks o left join fetch o.processRole pr left join fetch pr.roles roles left join fetch pr.hostingCompanyProcess hcp left join fetch o.actionUser au left join fetch o.createUser cu where o.guid = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Tasks) super.getUniqueResult(hql, parameters);
	}

	public long findUserCountForTask(ConfigDocProcessEnum docProcessEnum, Long userId) throws Exception {
		String hql = "select count(o) from TaskUsers o " + "where o.user.id  = :userId " + "and o.task.taskStatus <> :taskStatusComplete " + "and o.task.taskStatus <> :taskStatusClosed " + "and o.task.taskStatus <> :taskStatusError " + "and o.task.workflowProcess  = :docProcessEnum ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		parameters.put("taskStatusComplete", TaskStatusEnum.Completed);
		parameters.put("taskStatusClosed", TaskStatusEnum.Closed);
		parameters.put("taskStatusError", TaskStatusEnum.ERROR);
		parameters.put("docProcessEnum", docProcessEnum);
		return (long) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Tasks by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.Tasks}
	 * @throws Exception
	 *             global exception
	 * @see Tasks
	 */
	@SuppressWarnings("unchecked")
	public List<Tasks> findByName(String description) throws Exception {
		String hql = "select o from Tasks o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Tasks>) super.getList(hql, parameters);
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
	@SuppressWarnings("unchecked")
	public List<Tasks> findTasksByTypeAndKey(ConfigDocProcessEnum docProcessEnum, Long targetKey) throws Exception {
		String hql = "select o from Tasks o " + "left join fetch o.actionUser au " + "left join fetch o.processRole pr " + "left join fetch pr.hostingCompanyProcess hcp " + "where o.targetKey  = :targetKey " + "and o.workflowProcess  = :docProcessEnum " + "order by o.createDate desc";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("docProcessEnum", docProcessEnum);
		parameters.put("targetKey", targetKey);
		return (List<Tasks>) super.getList(hql, parameters);
	}
	

	@SuppressWarnings("unchecked")
	public List<Tasks> findTasksByTypeAndKey(String targetClass, Long targetKey) throws Exception {
		String hql = "select o from Tasks o " 
					+ "left join fetch o.actionUser au " 
					+ "left join fetch o.processRole pr " 
					+ "left join fetch pr.hostingCompanyProcess hcp " 
					+ "where o.targetKey  = :targetKey " 
					+ "and o.targetClass  = :targetClass " 
					+ "order by o.createDate desc";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		return (List<Tasks>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tasks> findTasksByTargetClassKeyAndStatusList(String targetClass, Long targetKey, List<TaskStatusEnum> statusList) throws Exception {
		String hql = "select o from Tasks o " 
					+ "left join fetch o.actionUser au " 
					+ "left join fetch o.processRole pr " 
					+ "left join fetch pr.hostingCompanyProcess hcp " 
					+ "where o.targetKey  = :targetKey " 
					+ "and o.targetClass  = :targetClass ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		if (statusList != null && !statusList.isEmpty()) {
			hql += " and ( ";
			int count = 1;
			for (TaskStatusEnum taskStatusEnum : statusList) {
				if (count == statusList.size()) {
					hql += " o.taskStatus = :taskStatusEnum" + count + " ";
					parameters.put("taskStatusEnum" + count, taskStatusEnum); 
				} else {
					hql += " o.taskStatus = :taskStatusEnum" + count + "  or ";
					parameters.put("taskStatusEnum" + count, taskStatusEnum);  
				}
				count++;
			}
			hql += " ) ";
		}
		hql += "order by o.createDate desc";
		return (List<Tasks>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<Tasks> findTasksByTypeAndKey(ConfigDocProcessEnum docProcessEnum, String targetClass, Long targetKey) throws Exception {
		String hql = "select o from Tasks o " 
					+ "left join fetch o.actionUser au " 
					+ "left join fetch o.processRole pr " 
					+ "left join fetch pr.hostingCompanyProcess hcp " 
					+ "where o.targetKey  = :targetKey " 
					+ "and o.targetClass  = :targetClass " 
					+ "and o.workflowProcess  = :docProcessEnum " 
					+ "order by o.createDate desc";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		parameters.put("docProcessEnum", docProcessEnum);
		return (List<Tasks>) super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<Tasks> findTasksBytargetClassAndKey(String targetClass, Long targetKey) throws Exception {
		String hql = "select o from Tasks o " 
				+ "left join fetch o.actionUser au " 
				+ "left join fetch o.processRole pr " 
				+ "left join fetch pr.hostingCompanyProcess hcp " 
				+ "where o.targetKey  = :targetKey " 
				+ "and o.targetClass  = :targetClass " 
				+ "and o.workflowProcess  = :docProcessEnum " 
				+ "order by o.createDate desc";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		return (List<Tasks>) super.getList(hql, parameters);
	}

	public long findTasksByTypeAndKeyOpen(ConfigDocProcessEnum docProcessEnum, String targetClass, Long targetKey) throws Exception {
		String hql = "select count(o) from Tasks o "  
					+ "where o.targetKey  = :targetKey " 
					+ "and o.targetClass  = :targetClass " 
					+ "and o.workflowProcess  = :docProcessEnum "
					+ "and o.taskStatus in (:notStarted, :underway)";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass);
		parameters.put("targetKey", targetKey);
		parameters.put("docProcessEnum", docProcessEnum);
		parameters.put("notStarted", TaskStatusEnum.NotStarted);
		parameters.put("underway", TaskStatusEnum.Underway);
		return (long) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Tasks> findTasksByTypeAndKeyLast(ConfigDocProcessEnum docProcessEnum, Long targetKey) throws Exception {
		String hql = "select o from Tasks o " + "left join fetch o.actionUser au " + "left join fetch o.processRole pr " + "left join fetch pr.hostingCompanyProcess hcp " + "where o.targetKey  = :targetKey " + "and o.workflowProcess  = :docProcessEnum " + "order by o.createDate desc";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("docProcessEnum", docProcessEnum);
		parameters.put("targetKey", targetKey);
		return (List<Tasks>) super.getList(hql, parameters, 1);
	}

	/**
	 * Find tasks by user incomplete.
	 *
	 * @param userId
	 *            the user id
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Tasks> findTasksByUserIncomplete(Long userId) throws Exception {
		String hql = "select distinct(o.task) from TaskUsers o left join fetch o.task.actionUser au where o.user.id  = :userId "  + "and o.task.taskStatus <> :taskStatusComplete and o.task.taskStatus <> :taskStatusClosed and o.task.taskStatus <> :taskStatusError";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		parameters.put("taskStatusComplete", TaskStatusEnum.Completed);
		parameters.put("taskStatusClosed", TaskStatusEnum.Closed);
		parameters.put("taskStatusError", TaskStatusEnum.ERROR);
		return (List<Tasks>) super.getList(hql, parameters);
	}

	/**
	 * Find tasks by user incomplete count.
	 *
	 * @param userId
	 *            the user id
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long findTasksByUserIncompleteCount(Long userId) throws Exception {
		String hql = "select count(distinct o.task) from TaskUsers o " + "where o.user.id  = :userId " + "and o.task.taskStatus <> :taskStatusComplete " + "and o.task.taskStatus <> :taskStatusClosed " + "and o.task.taskStatus <> :taskStatusError";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		parameters.put("taskStatusComplete", TaskStatusEnum.Completed);
		parameters.put("taskStatusClosed", TaskStatusEnum.Closed);
		parameters.put("taskStatusError", TaskStatusEnum.ERROR);
		return (long) super.getUniqueResult(hql, parameters);
	}
	
	public int countTasksUsersByTask(Long taskId) throws Exception {
		String hql = "select count(o) from TaskUsers o where o.task.id  = :taskId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("taskId", taskId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<Tasks> findByParentTask(Long id) {
		String hql = "select o from Tasks o left join fetch o.actionUser au  left join fetch o.createUser cu  where o.previousTask.id = :id";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Tasks>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<TaskReportBean> findTaskBySDFCompanyforCompany(Long companyId) {
		String hql = "	select new haj.com.bean.TaskReportBean(a , b) from Tasks a   " + "	left join SDFCompany b " + "	on a.targetKey = b.id " + "	and a.targetClass  = 'haj.com.entity.SDFCompany' " + "	left join  Users u " + "	on b.sdf.id = u.id " + "	left join  Company c " + "	on b.company.id = c.id " + "where b.company.id = :companyId  order by a.createDate ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<TaskReportBean>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<TaskReportBean> findTaskForCompany(Long companyId) {
		String hql = "select new haj.com.bean.TaskReportBean(a , b)  from Tasks a  " + "	left join Company b " + "	on a.targetKey = b.id  " + "where  a.targetKey   = 	:companyId " + "and 	    a.targetClass = 'haj.com.entity.Company' " + " order by a.createDate ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<TaskReportBean>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<TaskReportBean> findTaskBySDFCompanyforUser(Long userId) {
		String hql = "	select new haj.com.bean.TaskReportBean(a , b) from Tasks a   " + "	left join SDFCompany b " + "	on a.targetKey = b.id " + "	and a.targetClass = 'haj.com.entity.SDFCompany' " + "	inner join  Users u " + "	on b.sdf.id = u.id " + "	inner join  Company c " + "	on b.company.id = c.id " + "where b.sdf.id = :userId order by a.createDate ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		return (List<TaskReportBean>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<TaskReportBean> findTaskByUser(Long userId) {
		String hql = "	select new haj.com.bean.TaskReportBean(a , b) from Tasks a   " + "	left join Users b " + "	on a.targetKey = b.id " + "	and a.targetClass = 'haj.com.entity.Users' " + "where b.id = :userId order by a.createDate ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("userId", userId);
		return (List<TaskReportBean>) super.getList(hql, parameters);
	}

	/**
	 * @param entity
	 * @param filters
	 * @param employee
	 * @param taskStatusEnum
	 * @return int
	 * @throws Exception
	 */
	public int countByType(Class<Tasks> entity, Map<String, Object> filters, Boolean employee, TaskStatusEnum taskStatusEnum) throws Exception {

		String hql = "";

		String taskStatusName = taskStatusEnum.getRegistrationName();

		if (employee == null) {

			hql = "select count(o) from Tasks o where o.taskStatus = :taskStatus and o.taskStatus <> :taskStatusError";

		} else {

			if (employee == true) {
				if (taskStatusEnum == TaskStatusEnum.NotStarted) hql = "select count(o) from Tasks o where o.taskStatus = :taskStatus and o.taskStatus <> :taskStatusError and o.id in (select x.task.id from TaskUsers x where x.user.id in (select y.users.id from HostingCompanyEmployees y))";
				else hql = "select count(o) from Tasks o where o.taskStatus = :taskStatus and o.taskStatus <> :taskStatusError and o.actionUser.id in (select x.users.id from HostingCompanyEmployees x)";

			} else {
				if (taskStatusEnum == TaskStatusEnum.NotStarted) hql = "select count(o) from Tasks o where o.taskStatus = :taskStatus and o.taskStatus <> :taskStatusError and o.id in (select x.task.id from TaskUsers x where x.user.id not in (select y.users.id from HostingCompanyEmployees y))";
				else hql = "select count(o) from Tasks o where o.taskStatus = :taskStatus and o.taskStatus <> :taskStatusError and o.actionUser.id not in (select x.users.id from HostingCompanyEmployees x)";
			}
		}

		filters.put("taskStatus", taskStatusEnum);

		filters.put("taskStatusError", TaskStatusEnum.ERROR);

		return this.countWhere(filters, hql);
	}

	/**
	 * @param class1
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @param employee
	 * @param taskStatusEnum
	 * @return List<Task>
	 * @throws Exception
	 */
	public List<Tasks> allTasksByType(Class<Tasks> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, boolean employee, TaskStatusEnum taskStatusEnum) throws Exception {

		String hql = null;

		if (employee == true) {
			if (taskStatusEnum == TaskStatusEnum.NotStarted) hql = "select o from Tasks o left join fetch o.createUser left join fetch o.actionUser left join fetch o.processRole where o.taskStatus = :taskStatus and o.taskStatus <> :taskStatusError and o.id in (select x.task.id from TaskUsers x where x.user.id in (select y.users.id from HostingCompanyEmployees y))";
			else hql = "select o from Tasks o left join fetch o.createUser left join fetch o.actionUser left join fetch o.processRole where o.taskStatus = :taskStatus and o.taskStatus <> :taskStatusError and o.actionUser.id in (select x.users.id from HostingCompanyEmployees x)";
		} else {
			if (taskStatusEnum == TaskStatusEnum.NotStarted) hql = "select o from Tasks o left join fetch o.createUser left join fetch o.actionUser left join fetch o.processRole where o.taskStatus = :taskStatus and o.taskStatus <> :taskStatusError and o.id in (select x.task.id from TaskUsers x where x.user.id not in (select y.users.id from HostingCompanyEmployees y))";
			else hql = "select o from Tasks o left join fetch o.createUser left join fetch o.actionUser left join fetch o.processRole where o.taskStatus = :taskStatus and o.taskStatus <> :taskStatusError and o.actionUser.id not in (select x.users.id from HostingCompanyEmployees x)";
			// hql = "select o from Tasks o left join fetch o.createUser left join fetch
			// o.actionUser left join fetch o.processRole where o.taskStatus = :taskStatus
			// and o.taskStatus <> :taskStatusError and o.actionUser.id not in (select
			// x.users.id from HostingCompanyEmployees x)";
		}

		filters.put("taskStatus", taskStatusEnum);

		filters.put("taskStatusError", TaskStatusEnum.ERROR);

		return calcRag((List<Tasks>) this.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	@Override
	public List<?> sortAndFilterWhere(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String hql) {

		if (filters != null) {
			// boolean ft = true;
			Map<String, Object> parms = new HashMap<String, Object>();
			String hvar = null;
			for (Entry<String, Object> entry : filters.entrySet()) {
				hvar = entry.getKey();
				if (hvar.contains(".")) {
					hvar = hvar.replaceAll("\\.", "");
					parms.put(hvar, entry.getValue());
				} else {
					parms.put(entry.getKey(), entry.getValue());
				}
				if (!hql.contains(hvar)) {
					hql += " and o." + entry.getKey() + " like " + " :" + hvar;
				}
			}
			filters = parms;
		}

		if (sortField != null) {

			switch (sortOrder) {
				case ASCENDING:
					hql += " order by o." + sortField + " asc ";
					break;
				case DESCENDING:
					hql += " order by o." + sortField + " desc ";
					break;
				default:
					break;
			}
		}

		return getList(hql, filters, startingAt, pageSize);
	}

	/**
	 * Calc rag.
	 *
	 * @param list
	 *            the list
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<Tasks> calcRag(List<Tasks> list) throws Exception {
		Date today = new Date();
		for (Tasks task : list) {
			if (task.getTaskStatus() == TaskStatusEnum.Completed) task.setRag(RagEnum.Green);
			else if (task.getDueDate() == null) task.setRag(RagEnum.Green);
			else if (DateUtils.isSameDay(task.getDueDate(), today)) task.setRag(RagEnum.Amber);
			else if (task.getDueDate().after(today)) task.setRag(RagEnum.Green);
			else task.setRag(RagEnum.Red);
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<Tasks> findOpenTasksByProcessRoleId(Long processRoleId) throws Exception {
		String hql = "select o from Tasks o left join fetch o.processRole pr" // left join fetch o.residentialAddress cra 
				+ " where o.taskStatus <> :taskStatusError" 
				+ " and o.taskStatus <> :taskStatusCompleted " + "and o.processRole.id = :processRoleId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("processRoleId", processRoleId);
		parameters.put("taskStatusCompleted", TaskStatusEnum.Completed);
		parameters.put("taskStatusError", TaskStatusEnum.ERROR);
		return (List<Tasks>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<TaskUsers> findOpenTaskUsers(ConfigDocProcessEnum workflwoProcess) throws Exception {
		String hql = "select o from TaskUsers o " + "where o.task.taskStatus <> :taskStatusError " + "and o.task.taskStatus <> :taskStatusCompleted " + "and o.task.workflowProcess = :workflwoProcess";

		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("workflwoProcess", workflwoProcess);
		parameters.put("taskStatusCompleted", TaskStatusEnum.Completed);
		parameters.put("taskStatusError", TaskStatusEnum.ERROR);
		return (List<TaskUsers>) super.getList(hql, parameters);
	}
	
	public List<TaskUserReportBean> locateTaskSummaryEmployeesByTargetClassWsp(String targetClass, Integer finYear) throws Exception {		
		String sql = "select u.first_name as firstName, u.last_name as lastName, u.email as emailAddress ";
		sql += ",SUM(case when t.task_status = 2 and t.action_user_id = u.id then 1 else 0 end) as completedTasks ";
		sql += ",SUM(case when t.task_status = 1 and t.action_user_id = u.id then 1 else 0 end) as underwayTasks ";
		sql += ",SUM(case when t.task_status = 0 then 1 else 0 end) as notstartedTasks ";
		sql += "from hosting_company_employees emp ";
		sql += "left join users u on u.id = emp.user_id left join task_users tu on tu.user_id = u.id left join tasks t on t.id = tu.task_id left join wsp w on w.id = t.target_key ";
		sql += "where ";
		sql += "t.target_class = :targetClass ";
		if (finYear != null) {
			sql += "and w.fin_year = :finYear ";
		}
		sql += "group by u.first_name, u.last_name, u.email";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass.trim());
		if (finYear != null) {
			parameters.put("finYear", finYear);
		}
		return (List<TaskUserReportBean>) super.nativeSelectSqlList(sql, TaskUserReportBean.class, parameters);
	}
	
	public List<TaskUserReportBean> locateTaskSummaryEmployeesByTargetClassDG(String targetClass, Integer finYear) throws Exception {		
		String sql = "select u.first_name as firstName, u.last_name as lastName, u.email as emailAddress ";
		sql += ",SUM(case when t.task_status = 2 and t.action_user_id = u.id then 1 else 0 end) as completedTasks ";
		sql += ",SUM(case when t.task_status = 1 and t.action_user_id = u.id then 1 else 0 end) as underwayTasks ";
		sql += ",SUM(case when t.task_status = 0 then 1 else 0 end) as notstartedTasks ";
		sql += "from hosting_company_employees emp ";
		sql += "left join users u on u.id = emp.user_id left join task_users tu on tu.user_id = u.id left join tasks t on t.id = tu.task_id left join dg_verification dg on dg.id = t.target_key join wsp w on w.id = dg.wsp_id ";
		sql += "where ";
		sql += "t.target_class = :targetClass ";
		if (finYear != null) {
			sql += "and w.fin_year = :finYear ";
		}
		sql += "group by u.first_name, u.last_name, u.email";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetClass", targetClass.trim());
		if (finYear != null) {
			parameters.put("finYear", finYear);
		}
		return (List<TaskUserReportBean>) super.nativeSelectSqlList(sql, TaskUserReportBean.class, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public Users findUserByCompany(Company company) throws Exception {
		List<Users> l = new ArrayList<>();
		String hql = "select u from Tasks t "
				+ " inner join QualificationsCurriculumDevelopment qcd on qcd.id = t.targetKey "
				+ " inner join Users u on u.id = qcd.createUser "
				+ "where qcd.company.id = :company order by qcd.id desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("company", company.getId());
		l = (List<Users>)super.getList(hql, parameters);
		if (l.size() > 0) return l.get(0);
		else return null;
	}

	@SuppressWarnings("unchecked")
	public List<Users> findUserByQdfCompany(Long targetKey, String targetClass, ConfigDocProcessEnum workflowProcess) {
		String hql = "select DISTINCT(o.actionUser) from Tasks o where o.targetKey = :targetKey and o.targetClass = :targetClass and o.workflowProcess = :workflowProcess";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("targetKey", targetKey);
		parameters.put("targetClass", targetClass);
		parameters.put("workflowProcess", workflowProcess);
		return (List<Users>) super.getList(hql, parameters);
	}
	
	/*
	 * Reporting Starts
	 */
	// count completed tasks by user
	public List<CounterBean> locateTotalTasksByUserAndStatus(Long userId, TaskStatusEnum status) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"	and t.action_user_id = :userId " + 
				"   and t.hosting_company_process_id is not null ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("status", status.ordinal());
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> locateTotalTasksByUserAndStatusAndDateRange(Long userId, TaskStatusEnum status, Date fromDate, Date toDate) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"	and t.action_user_id = :userId " + 
				"   and t.hosting_company_process_id is not null ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("status", status.ordinal());
		if (status == TaskStatusEnum.Completed) {
			sql += " and (t.action_date BETWEEN '"+HAJConstants.sdf4.format(fromDate)+"' AND '"+HAJConstants.sdf4.format(toDate)+"')  ";
		} else {
			sql += " and (t.create_date BETWEEN '"+HAJConstants.sdf4.format(fromDate)+"' AND '"+HAJConstants.sdf4.format(toDate)+"')   ";
		}
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> locateTotalTasksByHostingCompanyProcessStatusAndDateRange(Long hostingComapnyProcessId, Long userId,  TaskStatusEnum status, Date fromDate, Date toDate, Boolean employeeTasksOnly) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"   and t.hosting_company_process_id = :hostingComapnyProcessId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("hostingComapnyProcessId", hostingComapnyProcessId);
		parameters.put("status", status.ordinal());
		if (userId != null) {
			sql += "	and t.action_user_id = :userId ";
			parameters.put("userId", userId);
		}
		if (fromDate != null && toDate != null) {
			if (status == TaskStatusEnum.Completed) {
				sql += " and (t.action_date BETWEEN '"+HAJConstants.sdf4.format(fromDate)+"' AND '"+HAJConstants.sdf4.format(toDate)+"')  ";
			} else {
				sql += " and (t.create_date BETWEEN '"+HAJConstants.sdf4.format(fromDate)+"' AND '"+HAJConstants.sdf4.format(toDate)+"')   ";
			}
		}
		if (employeeTasksOnly != null && employeeTasksOnly) {
			sql += " and t.id in (   " + 
					"			select distinct task_id from task_users where user_id in (  " + 
					"				select distinct user_id from hosting_company_employees	  " + 
					"			)  " + 
					"		)";
		}
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> locateTotalTasksByUserAndStatusWithHostingComapnyProcess(Long userId, TaskStatusEnum status, Long hostingCompanyProcessId) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"	and t.action_user_id = :userId " + 
				"   and t.hosting_company_process_id = :hostingCompanyProcessId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("status", status.ordinal());
		parameters.put("hostingCompanyProcessId", hostingCompanyProcessId);
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> locateTotalTasksByUserAndStatusWithHostingComapnyProcessAndDateRange(Long userId, TaskStatusEnum status, Long hostingCompanyProcessId, Date fromDate, Date toDate) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"	and t.action_user_id = :userId " + 
				"   and t.hosting_company_process_id = :hostingCompanyProcessId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("status", status.ordinal());
		parameters.put("hostingCompanyProcessId", hostingCompanyProcessId);
		if (status == TaskStatusEnum.Completed) {
			sql += " and (t.action_date BETWEEN '"+HAJConstants.sdf4.format(fromDate)+"' AND '"+HAJConstants.sdf4.format(toDate)+"')  ";
		} else {
			sql += " and (t.create_date BETWEEN '"+HAJConstants.sdf4.format(fromDate)+"' AND '"+HAJConstants.sdf4.format(toDate)+"')   ";
		}
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> countTotalTasksAssignedToUser(Long userId, List<TaskStatusEnum> statusList) throws Exception {		
		String sql = "select " + 
				"	count(distinct tu.task_id) as counter " + 
				"from " + 
				"	task_users tu " + 
				"inner join tasks t on t.id = task_id " + 
				"where " + 
				"	tu.user_id = :userId " + 
				"	and t.task_status in ("+populateTaskStatusEnumResult(statusList)+") " + 
				"   and t.hosting_company_process_id is not null ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> countTotalTasksAssignedToUserBetweenDates(Long userId, List<TaskStatusEnum> statusList, Date fromDate, Date toDate) throws Exception {		
		String sql = "select " + 
				"	count(distinct tu.task_id) as counter " + 
				"from " + 
				"	task_users tu " + 
				"inner join tasks t on t.id = task_id " + 
				"where " + 
				"	tu.user_id = :userId " + 
				"	and t.task_status in ("+populateTaskStatusEnumResult(statusList)+") " + 
				"   and t.hosting_company_process_id is not null " +
				"   and (t.create_date BETWEEN '"+HAJConstants.sdf4.format(fromDate)+"' AND '"+HAJConstants.sdf4.format(toDate)+"')   ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> countTotalTasksAssignedToHostingCompanyProcessBetweenDates(Long hostingComapnyProcessId, Long userId, List<TaskStatusEnum> statusList, Date fromDate, Date toDate, Boolean employeeTasksOnly) throws Exception {		
		Map<String, Object> parameters = new HashMap<>();
		String sql = "select " + 
				"	count(distinct tu.task_id) as counter " + 
				"from " + 
				"	task_users tu " + 
				"inner join tasks t on t.id = task_id " + 
				"where " + 
				"	t.task_status in ("+populateTaskStatusEnumResult(statusList)+") " + 
				"   and t.hosting_company_process_id = :hostingComapnyProcessId ";
		if (fromDate != null && toDate != null) {
			sql += "   and (t.create_date BETWEEN '"+HAJConstants.sdf4.format(fromDate)+"' AND '"+HAJConstants.sdf4.format(toDate)+"')   ";
		}
		if (userId != null) {
			sql += "and tu.user_id = :userId ";
			parameters.put("userId", userId);
		}
		if (employeeTasksOnly != null && employeeTasksOnly) {
			sql += " and t.id in (   " + 
					"			select distinct task_id from task_users where user_id in (  " + 
					"				select distinct user_id from hosting_company_employees	  " + 
					"			)  " + 
					"		)";
		}
		
		parameters.put("hostingComapnyProcessId", hostingComapnyProcessId);
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> countTotalTasksAssignedToUserByHostingCompanyProcess(Long userId, List<TaskStatusEnum> statusList, Long hostingCompanyProcessId) throws Exception {		
		String sql = "select " + 
				"	count(distinct tu.task_id) as counter " + 
				"from " + 
				"	task_users tu " + 
				"inner join tasks t on t.id = task_id " + 
				"where " + 
				"	tu.user_id = :userId " + 
				"	and t.task_status in ("+populateTaskStatusEnumResult(statusList)+") " +
				"   and t.hosting_company_process_id = :hostingCompanyProcessId " ; 
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("hostingCompanyProcessId", hostingCompanyProcessId);
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> countTotalTasksAssignedToUserByHostingCompanyProcessBetweenDates(Long userId, List<TaskStatusEnum> statusList, Long hostingCompanyProcessId, Date fromDate, Date toDate) throws Exception {		
		String sql = "select " + 
				"	count(distinct tu.task_id) as counter " + 
				"from " + 
				"	task_users tu " + 
				"inner join tasks t on t.id = task_id " + 
				"where " + 
				"	tu.user_id = :userId " + 
				"	and t.task_status in ("+populateTaskStatusEnumResult(statusList)+") " +
				"   and t.hosting_company_process_id = :hostingCompanyProcessId " +
				"   and (t.create_date BETWEEN '"+HAJConstants.sdf4.format(fromDate)+"' AND '"+HAJConstants.sdf4.format(toDate)+"')   ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("hostingCompanyProcessId", hostingCompanyProcessId);
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> countTotalTasksAssignedToUserByHostingCompanyProcessWithDateRange(Long userId, List<TaskStatusEnum> statusList, Long hostingCompanyProcessId, Date fromDate, Date toDate) throws Exception {		
		String sql = "select " + 
				"	count(distinct tu.task_id) as counter " + 
				"from " + 
				"	task_users tu " + 
				"inner join tasks t on t.id = task_id " + 
				"where " + 
				"	tu.user_id = :userId " + 
				"	and t.task_status in ("+populateTaskStatusEnumResult(statusList)+") " +
				"   and t.hosting_company_process_id = :hostingCompanyProcessId " + 
				"   and t.create_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) "; 
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("hostingCompanyProcessId", hostingCompanyProcessId);
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	// count completed tasks by user in date range
	public List<CounterBean> locateTotalTasksByUserAndStatusBetweenDates(Long userId, TaskStatusEnum status, Date fromDate, Date toDate) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"	and t.action_user_id = :userId ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("status", status.ordinal());
		if (status == TaskStatusEnum.Completed) {
			sql += " and t.action_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and t.create_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	// count completed tasks by user before due date
	public List<CounterBean> countTotalTasksByUserAndStatusAndBeforeDueDate(Long userId, TaskStatusEnum status) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"	and t.action_user_id = :userId " + 
				"   and t.action_date < t.due_date " + 
				"   and t.hosting_company_process_id is not null ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("status", status.ordinal());
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> countTotalTasksByUserAndStatusAndBeforeDueDateBetweenDates(Long userId, TaskStatusEnum status, Date fromDate, Date toDate) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"	and t.action_user_id = :userId " + 
				"   and t.action_date < t.due_date " + 
				"   and t.hosting_company_process_id is not null " +
				"   and t.action_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("status", status.ordinal());
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> countTotalTasksByHostingCompanyProcessStatusAndBeforeDueDateBetweenDates(Long hostingCompanyProcessId, Long userId, TaskStatusEnum status, Date fromDate, Date toDate, Boolean employeeTasksOnly) throws Exception {		
		Map<String, Object> parameters = new HashMap<>();
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"   and t.action_date < t.due_date " + 
				"   and t.hosting_company_process_id = :hostingCompanyProcessId ";
		if (fromDate != null && toDate != null) {
			sql +=	"   and t.action_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		if (userId != null) {
			sql += "	and t.action_user_id = :userId ";
			parameters.put("userId", userId);
		}
		if (employeeTasksOnly != null && employeeTasksOnly) {
			sql += " and t.id in (   " + 
					"			select distinct task_id from task_users where user_id in (  " + 
					"				select distinct user_id from hosting_company_employees	  " + 
					"			)  " + 
					"		)";
		}
		
		parameters.put("hostingCompanyProcessId", hostingCompanyProcessId);
		parameters.put("status", status.ordinal());
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> countTotalTasksByHostingCompanyProcessAndStatusAndBeforeDueDateBetweenDates(Long userId, TaskStatusEnum status, Date fromDate, Date toDate, Boolean employeeTasksOnly) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"	and t.action_user_id = :userId " + 
				"   and t.action_date < t.due_date " + 
				"   and t.hosting_company_process_id is not null " +
				"   and t.action_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("status", status.ordinal());
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	// count completed tasks by user before due date and by hosting company process
	public List<CounterBean> countTotalTasksByUserAndStatusAndBeforeDueDateAndByHostingCompanyProcess(Long userId, TaskStatusEnum status , Long hostingCompanyProcessId ) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"	and t.action_user_id = :userId " + 
				"   and t.action_date < t.due_date " + 
				"   and t.hosting_company_process_id = :hostingCompanyProcessId " ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("status", status.ordinal());
		parameters.put("hostingCompanyProcessId", hostingCompanyProcessId);
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> countTotalTasksByUserAndStatusAndBeforeDueDateAndByHostingCompanyProcessBetweenDates(Long userId, TaskStatusEnum status , Long hostingCompanyProcessId, Date fromDate, Date toDate) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"	and t.action_user_id = :userId " + 
				"   and t.action_date < t.due_date " + 
				"   and t.hosting_company_process_id = :hostingCompanyProcessId " ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("status", status.ordinal());
		parameters.put("hostingCompanyProcessId", hostingCompanyProcessId);
		if (status == TaskStatusEnum.Completed) {
			sql += " and t.action_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and t.create_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	// count completed tasks by user before due date
	public List<CounterBean> countTotalTasksByUserAndStatusAndAfterDueDate(Long userId, TaskStatusEnum status) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"	and t.action_user_id = :userId " + 
				"   and t.action_date > t.due_date " + 
				"   and t.hosting_company_process_id is not null ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("status", status.ordinal());
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> countTotalTasksByUserAndStatusAndAfterDueDateBetweenDatre(Long userId, TaskStatusEnum status, Date fromDate, Date toDate) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"	and t.action_user_id = :userId " + 
				"   and t.action_date > t.due_date " + 
				"   and t.hosting_company_process_id is not null " + 
				"   and t.action_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("status", status.ordinal());
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> countTotalTasksByHostingCompanyProcessStatusAndAfterDueDateBetweenDate(Long hostingComapnyProcessId, Long userId, TaskStatusEnum status, Date fromDate, Date toDate, Boolean employeeTasksOnly) throws Exception {		
		Map<String, Object> parameters = new HashMap<>();
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"   and t.action_date > t.due_date " + 
				"   and t.hosting_company_process_id = :hostingComapnyProcessId ";
		if (fromDate != null && toDate != null) {
			sql += "   and t.action_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		if (userId != null) {
			sql +=	"	and t.action_user_id = :userId ";
			parameters.put("userId", userId);
		}
		if (employeeTasksOnly != null && employeeTasksOnly) {
			sql += " and t.id in (   " + 
					"			select distinct task_id from task_users where user_id in (  " + 
					"				select distinct user_id from hosting_company_employees	  " + 
					"			)  " + 
					"		)";
		}
		parameters.put("hostingComapnyProcessId", hostingComapnyProcessId);
		parameters.put("status", status.ordinal());
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	// count completed tasks by user before due date
	public List<CounterBean> countTotalTasksByUserAndStatusAndAfterDueDateAndByHostingCompanyProcess(Long userId, TaskStatusEnum status, Long hostingCompanyProcessId) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"	and t.action_user_id = :userId " + 
				"   and t.action_date > t.due_date " +
				"   and t.hosting_company_process_id = :hostingCompanyProcessId " ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("status", status.ordinal());
		parameters.put("hostingCompanyProcessId", hostingCompanyProcessId);
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<CounterBean> countTotalTasksByUserAndStatusAndAfterDueDateAndByHostingCompanyProcessBetweenDates(Long userId, TaskStatusEnum status, Long hostingCompanyProcessId, Date fromDate, Date toDate) throws Exception {		
		String sql = "select " + 
				"	count(t.id) as counter " + 
				"from " + 
				"	tasks t " + 
				"where " + 
				"	t.task_status = :status " + 
				"	and t.action_user_id = :userId " + 
				"   and t.action_date > t.due_date " +
				"   and t.hosting_company_process_id = :hostingCompanyProcessId " ;
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		parameters.put("status", status.ordinal());
		parameters.put("hostingCompanyProcessId", hostingCompanyProcessId);
		if (status == TaskStatusEnum.Completed) {
			sql += " and t.action_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		} else {
			sql += " and t.create_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		}
		return (List<CounterBean>) super.nativeSelectSqlList(sql, CounterBean.class, parameters);
	}
	
	public List<TaskProcessReport> populateDistinctHostingCompanyProcessByUserId(Long userId, List<TaskStatusEnum> statusList) throws Exception {		
		String sql = "select   " + 
				"	distinct t.hosting_company_process_id as hostingCompanyProcessId  " + 
				"from   " + 
				"	task_users tu  " + 
				"inner join tasks t on t.id = task_id  " + 
				"where   " + 
				"	tu.user_id = :userId " + 
				"	and t.task_status in ("+populateTaskStatusEnumResult(statusList)+") " + 
				"   and t.hosting_company_process_id is not null ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		return (List<TaskProcessReport>) super.nativeSelectSqlList(sql, TaskProcessReport.class, parameters);
	}
	
	public List<TaskProcessReport> populateDistinctHostingCompanyProcessByUserIdWithDateRanges(Long userId, List<TaskStatusEnum> statusList, Date fromDate, Date toDate) throws Exception {		
		String sql = "select   " + 
				"	distinct t.hosting_company_process_id as hostingCompanyProcessId  " + 
				"from   " + 
				"	task_users tu  " + 
				"inner join tasks t on t.id = task_id  " + 
				"where   " + 
				"	tu.user_id = :userId " + 
				"	and t.task_status in ("+populateTaskStatusEnumResult(statusList)+") " + 
				"   and t.hosting_company_process_id is not null ";
		sql += " and ( ";
		sql += " t.action_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		sql += " or t.create_date Between CAST( '"+HAJConstants.sdf.format(fromDate)+"' AS DATE) and CAST( '"+HAJConstants.sdf.format(toDate)+"' AS DATE) ";
		sql += " ) ";
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("userId", userId);
		return (List<TaskProcessReport>) super.nativeSelectSqlList(sql, TaskProcessReport.class, parameters);
	}
	
	public String populateTaskStatusEnumResult(List<TaskStatusEnum> entryList) throws Exception{
		StringBuilder result = new StringBuilder();
		int counter = 1;
		for (TaskStatusEnum entry : entryList) {
			result.append(entry.ordinal());
			if (entryList.size() != counter) {
				result.append(" , ");
			}
			counter ++;
		}
		return result.toString();
	}
	/*
	 * Reporting Ends
	 */
	
}
