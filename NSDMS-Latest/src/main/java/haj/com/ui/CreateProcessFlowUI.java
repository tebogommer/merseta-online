package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import haj.com.entity.Blank;
import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyProcess;
import haj.com.entity.ProcessRoles;
import haj.com.entity.UsersRole;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HostingCompanyProcessService;
import haj.com.service.HostingCompanyService;
import haj.com.service.ProcessRolesService;
import haj.com.service.UsersRoleService;

// TODO: Auto-generated Javadoc
/**
 * The Class CreateProcessFlowUI.
 */
@ManagedBean(name = "createprocessflowUI")
@ViewScoped
public class CreateProcessFlowUI extends AbstractUI {

	/** The hosting company. */
	private HostingCompany hostingCompany;
	
	/** The hosting company service. */
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();
	
	/** The hosting company process service. */
	private HostingCompanyProcessService hostingCompanyProcessService = new HostingCompanyProcessService();
	
	/** The hosting company process. */
	private HostingCompanyProcess hostingCompanyProcess;
	
	/** The current processes. */
	private List<HostingCompanyProcess> currentProcesses;
	
	/** The users role service. */
	private UsersRoleService usersRoleService = new UsersRoleService();

	/** The service. */
	private ProcessRolesService service = new ProcessRolesService();
	
	/** The processroles list. */
	private List<ProcessRoles> processrolesList = null;

	/** The root. */
	private TreeNode root;
	
	/** The selected node. */
	private TreeNode selectedNode;
	
	/** The expand all. */
	private boolean expandAll;

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
	 * @throws Exception             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		List<HostingCompany> l = hostingCompanyService.allHostingCompanyFirst();
		if (l.size() > 0) {
			this.hostingCompany = l.get(0);
			findCurrentProcesses();
		}
	}

	/**
	 * Find current processes.
	 */
	public void findCurrentProcesses() {
		try {
			currentProcesses = hostingCompanyProcessService.allHostingCompanyProcess(hostingCompany);
			root = new DefaultTreeNode("HostingCompany", hostingCompany, null);
			root.setExpanded(true);
			for (HostingCompanyProcess hostingCompanyProcess : currentProcesses) {
				if (hostingCompanyProcess.getWorkflowProcess() != null) {
					TreeNode processNode = new DefaultTreeNode("HostingCompanyProcess", hostingCompanyProcess, root);
					processNode.setExpanded(true);
					findRecursive(hostingCompanyProcess, processNode);
				}
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Find recursive.
	 *
	 * @param hostingCompanyProcess the hosting company process
	 * @param parent the parent
	 * @throws Exception the exception
	 */
	private void findRecursive(HostingCompanyProcess hostingCompanyProcess, TreeNode parent) throws Exception {

		processrolesList = service.allProcessRoles(hostingCompanyProcess);
		if (processrolesList.size() > 0) {
			TreeNode userNode = new DefaultTreeNode("RoleHeading", "Roles", parent);
			userNode.setExpanded(expandAll);

			for (ProcessRoles processRoles : processrolesList) {
				TreeNode processNode = new DefaultTreeNode("ProcessRoles", processRoles, userNode);
				processNode.setExpanded(expandAll);
				List<UsersRole> l =usersRoleService.findByProcess(processRoles);
				for (UsersRole hostingCompanyEmployees : l) {
					TreeNode employeeNode = new DefaultTreeNode("HostingCompanyEmployees", hostingCompanyEmployees, processNode);
				}
			}
		}

		if (hostingCompanyProcess.getNextProcess() != null) {
			TreeNode processNode = new DefaultTreeNode("HostingCompanyProcessChild", hostingCompanyProcess.getNextProcess(), parent);
			processNode.setExpanded(true);
			findRecursive(hostingCompanyProcess.getNextProcess(), processNode);
		}
		return;
	}

	/**
	 * Prep process.
	 */
	public void prepProcess() {
		hostingCompanyProcess = new HostingCompanyProcess();
		hostingCompanyProcess.setHostingCompany(hostingCompany);
	}

	/**
	 * Save process.
	 */
	public void saveProcess() {
		try {
			hostingCompanyProcessService.create(hostingCompanyProcess);
			// findCurrentProcesses();
			runClientSideExecute("PF('NewProcess').hide()");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the hosting company.
	 *
	 * @return the hosting company
	 */
	public HostingCompany getHostingCompany() {
		return hostingCompany;
	}

	/**
	 * Sets the hosting company.
	 *
	 * @param hostingCompany the new hosting company
	 */
	public void setHostingCompany(HostingCompany hostingCompany) {
		this.hostingCompany = hostingCompany;
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
	 * @param root the new root
	 */
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	/**
	 * Gets the selected node.
	 *
	 * @return the selected node
	 */
	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	/**
	 * Sets the selected node.
	 *
	 * @param selectedNode the new selected node
	 */
	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	/**
	 * Checks if is expand all.
	 *
	 * @return true, if is expand all
	 */
	public boolean isExpandAll() {
		return expandAll;
	}

	/**
	 * Sets the expand all.
	 *
	 * @param expandAll the new expand all
	 */
	public void setExpandAll(boolean expandAll) {
		this.expandAll = expandAll;
	}

	/**
	 * Gets the hosting company process.
	 *
	 * @return the hosting company process
	 */
	public HostingCompanyProcess getHostingCompanyProcess() {
		return hostingCompanyProcess;
	}

	/**
	 * Sets the hosting company process.
	 *
	 * @param hostingCompanyProcess the new hosting company process
	 */
	public void setHostingCompanyProcess(HostingCompanyProcess hostingCompanyProcess) {
		this.hostingCompanyProcess = hostingCompanyProcess;
	}

	/**
	 * Gets the current processes.
	 *
	 * @return the current processes
	 */
	public List<HostingCompanyProcess> getCurrentProcesses() {
		return currentProcesses;
	}

	/**
	 * Sets the current processes.
	 *
	 * @param currentProcesses the new current processes
	 */
	public void setCurrentProcesses(List<HostingCompanyProcess> currentProcesses) {
		this.currentProcesses = currentProcesses;
	}

}
