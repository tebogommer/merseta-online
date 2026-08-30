package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.TreeNode;

import haj.com.bean.TaskReportBean;
import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.TaskTreeService;
import haj.com.service.TasksService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "taskTreeUI")
@ViewScoped
public class TaskTreeUI extends AbstractUI {


	/**  TreeNode. */
	private TreeNode treeNode;

	/** The selected node. */
	private TreeNode selectedNode;

	private TaskTreeService service =  new TaskTreeService();
	private TasksService tasksService =  new TasksService();

	private Tasks task;
	private List<Tasks> taskList;

	private  List<TaskReportBean> report;
	private Company company;
	private Users sdf;


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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {
	//	this.task = new TasksService().findByKey(2510l);
	//	this.treeNode = service.buildTree(this.task);
	// = service.buildRelatedTasks(task);
/*		taskList.forEach(a-> {
			System.out.println(a.getId()+"\t"+a.getCreateDate()+"\t"+a.getActionDate());
		});
*/	}

	public void buildTree() {
		try {
			this.task = tasksService.findByKey(this.task.getId());
			taskList = service.buildRelatedTasks(task);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	public void taskForCompany() {
		try {
			this.report = null;
			this.sdf = null;
			this.report = tasksService.allTasksForCompany(company);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void taskForSDF() {
		try {
			this.report = null;
			this.company = null;
			this.report = tasksService.findTaskBySDFCompanyforUser(sdf);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public Tasks getTask() {
		return task;
	}

	public void setTask(Tasks task) {
		this.task = task;
	}

	public TreeNode getTreeNode() {
		return treeNode;
	}

	public void setTreeNode(TreeNode treeNode) {
		this.treeNode = treeNode;
	}

	public List<Tasks> getTaskList() {
		return taskList;
	}

	public void setTaskList(List<Tasks> taskList) {
		this.taskList = taskList;
	}

	public List<TaskReportBean> getReport() {
		return report;
	}

	public void setReport(List<TaskReportBean> report) {
		this.report = report;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Users getSdf() {
		return sdf;
	}

	public void setSdf(Users sdf) {
		this.sdf = sdf;
	}


}
