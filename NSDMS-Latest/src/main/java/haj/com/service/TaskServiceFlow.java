package haj.com.service;

import java.util.ArrayList;
import java.util.List;

import haj.com.entity.ProcessRoles;
import haj.com.entity.Tasks;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskServiceFlow.
 */
public class TaskServiceFlow extends AbstractService {

	/** The process roles service. */
	private ProcessRolesService processRolesService = new ProcessRolesService();

	/** The task service flow. */
	private static TaskServiceFlow taskServiceFlow = null;

	/**
	 * Instance.
	 *
	 * @return the task service flow
	 */
	public static synchronized TaskServiceFlow instance() {
		if (taskServiceFlow == null) {
			taskServiceFlow = new TaskServiceFlow();
		}
		return taskServiceFlow;
	}

	/**
	 * Flow.
	 *
	 * @param task
	 *            the task
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<ProcessRoles> flow(Tasks task) throws Exception {
		if (task.getProcessRole() == null) {
			return new ArrayList<>();
		}
		List<ProcessRoles> l = processRolesService.allProcessRoles(task.getProcessRole().getHostingCompanyProcess());
		l.forEach(a -> {
			if (a.getRoleOrder() < task.getProcessRole().getRoleOrder()) {
				a.setStyle("completed");
			} else if (a.getId() == task.getProcessRole().getId()) {
				switch (task.getTaskStatus()) {
					case NotStarted:
						a.setStyle("notstarted");
						break;
					case Underway:
						a.setStyle("underway");
						break;
					case Completed:
						a.setStyle("completed");
						break;
					default:
						break;
				}
			}
		});
		return l;
	}
}
