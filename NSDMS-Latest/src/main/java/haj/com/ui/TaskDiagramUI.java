package haj.com.ui;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import haj.com.bean.TechfiniumTreeNode;
import haj.com.entity.ProcessRoles;
import haj.com.entity.Tasks;
import haj.com.entity.lookup.Roles;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TaskServiceFlow;
import haj.com.service.TasksService;

// TODO: Auto-generated Javadoc
/**
 * The Class TaskDiagramUI.
 */
@ManagedBean(name = "taskDiagramUI")
@ViewScoped
public class TaskDiagramUI extends AbstractUI {

	/** The root. */
	private TreeNode root;

	private Tasks task;

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all Blank and prepare a for a create of a new
	 * TaskDiagramUI.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		// FOR TESTING !!!
		// if (getSessionUI().getTask()==null) {
		// getSessionUI().setTask(new TasksService().findByKey(35l));
		// }
		if ((getSessionUI().getTask() != null)) {
			doDiagram(getSessionUI().getTask());
		} else if (this.task != null) {
			doDiagram(task);
		}
	}

	/**
	 * Do diagram.
	 *
	 * @throws Exception
	 *             the exception
	 */
	public void doDiagram(Tasks task) throws Exception {
		task = TasksService.instance().findByKey(task.getId());
		Roles role = new Roles();

		if (task.getProcessRole() != null && task.getProcessRole().getHostingCompanyProcess() != null && task.getProcessRole().getHostingCompanyProcess().getWorkflowProcess() == null) role.setDescription(task.getProcessRole().getHostingCompanyProcess().getNote());
		else role.setDescription(super.getEntryLanguage(task.getWorkflowProcess().getRegistrationName()));

		ProcessRoles t = new ProcessRoles();
		t.setRoles(role);
		root = new TechfiniumTreeNode("none", t, null);
		root.setExpanded(true);

		List<ProcessRoles> l = TaskServiceFlow.instance().flow(task);
		Map<Integer, TreeNode> m = new TreeMap<Integer, TreeNode>();
		Integer i = 1;
		for (ProcessRoles pr : l) {
			TreeNode tn = null;
			if (pr.getStyle() == null) {
				tn = new DefaultTreeNode(pr);
			} else {
				tn = new DefaultTreeNode(pr.getStyle(), pr, null);
			}
			tn.setExpanded(true);
			m.put(i, tn);
			if (i == 1) {
				root.getChildren().add(tn);
				// tn.setParent(root);
			} else {
				m.get(i - 1).getChildren().add(tn);
			}
			i++;
		}

	}

	/**
	 * Gets the root.
	 *
	 * @return the root
	 */
	public TreeNode getRoot() {
		return root;
	}

	/**
	 * Sets the root.
	 *
	 * @param root
	 *            the new root
	 */
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public Tasks getTask() {
		return task;
	}

	public void setTask(Tasks task) {
		this.task = task;
	}

}
