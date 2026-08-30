package haj.com.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.model.SelectItem;

import org.primefaces.event.SelectEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.TreeNode;

import haj.com.bean.TreeTableBean;
import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.HostingCompanyProcess;
import haj.com.entity.ProcessRoles;
import haj.com.entity.UsersRole;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.UserPermissionEnum;
import haj.com.entity.lookup.Roles;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HostingCompanyDepartmentsEmployeesService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.HostingCompanyProcessService;
import haj.com.service.HostingCompanyService;
import haj.com.service.ProcessRolesService;
import haj.com.service.UsersRoleService;
import haj.com.service.lookup.RolesService;

// TODO: Auto-generated Javadoc
/**
 * The Class ProcessRolesUI.
 */
@ManagedBean(name = "processrolesUI")
@ViewScoped
public class ProcessRolesUI extends AbstractUI {

	/** The service. */
	private ProcessRolesService service = new ProcessRolesService();

	/** The processroles list. */
	private List<ProcessRoles> processrolesList = null;

	/** The processrolesfiltered list. */
	private List<ProcessRoles> processrolesfilteredList = null;

	/** The processroles. */
	private ProcessRoles processroles = null;

	/** The data model. */
	private LazyDataModel<ProcessRoles> dataModel;

	/** The roles service. */
	private RolesService rolesService = new RolesService();

	/** The roles. */
	private List<Roles> roles;

	/** The hosting company. */
	private HostingCompany hostingCompany;

	/** The company departments employees service. */
	private HostingCompanyDepartmentsEmployeesService companyDepartmentsEmployeesService = new HostingCompanyDepartmentsEmployeesService();

	/** The hosting company service. */
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();

	/** The hosting company process service. */
	private HostingCompanyProcessService hostingCompanyProcessService = new HostingCompanyProcessService();

	/** The hosting company process. */
	private HostingCompanyProcess hostingCompanyProcess;

	/** The current processes. */
	private List<HostingCompanyProcess> currentProcesses;

	/** The hosting company employees service. */
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();

	/** The hosting company employees. */
	private List<HostingCompanyEmployees> hostingCompanyEmployees;

	/** The hostin company employee. */
	private HostingCompanyEmployees hostinCompanyEmployee;

	/** The users role. */
	private UsersRole usersRole;

	/** The users role service. */
	private UsersRoleService usersRoleService = new UsersRoleService();

	/** The root. */
	private TreeNode root;

	private TreeNode tableRoot;

	/** The demo root. */
	private TreeNode demoRoot;

	/** The selected node. */
	private TreeNode selectedNode;

	/** The company role. */
	private boolean companyRole;

	private boolean canAddManager;

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
	 * Initialize method to get all ProcessRoles and prepare a for a create of a new
	 * ProcessRoles.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see ProcessRoles
	 */
	private void runInit() throws Exception {
		prepareNew();
		createDemoRoot();
		List<HostingCompany> l = hostingCompanyService.allHostingCompanyFirst();
		if (l.size() > 0) {
			this.hostingCompany = l.get(0);
			findCurrentProcesses();
		}
	}

	/**
	 * Creates the demo root.
	 */
	private void createDemoRoot() {
		demoRoot = new DefaultTreeNode("HostingCompanyDemo", "Hosting Company", null);
		TreeNode companyProcessNode = new DefaultTreeNode("HostingCompanyProcessDemo", "Process", demoRoot);
		TreeNode processNode = new DefaultTreeNode("ProcessRolesDemo", "Role", companyProcessNode);
		TreeNode employeeNode = new DefaultTreeNode("HostingCompanyEmployeesDemo", "Employee", processNode);
		demoRoot.setExpanded(true);
		processNode.setExpanded(true);
		companyProcessNode.setExpanded(true);
		employeeNode.setExpanded(true);
	}

	/**
	 * Find current processes.
	 */
	public void findCurrentProcesses() {
		try {
			prepareNew();
			root = new DefaultTreeNode("HostingCompany", hostingCompany, null);
			tableRoot = new DefaultTreeNode(new TreeTableBean("HostingCompany", "", ""));
			root.setExpanded(true);
			currentProcesses = hostingCompanyProcessService.allHostingCompanyProcess(hostingCompany);
			for (HostingCompanyProcess hostingCompanyProcess : currentProcesses) {

				if (hostingCompanyProcess.getWorkflowProcess() != null) {

					TreeNode companyProcessNode = new DefaultTreeNode("HostingCompanyProcess", hostingCompanyProcess, root);
					TreeNode companyProcessNode2 = new DefaultTreeNode(new TreeTableBean("#BOLD_RED#" + getEntryLanguage(hostingCompanyProcess.getWorkflowProcess().getRegistrationName()), "#BOLD_RED#", "#BOLD_RED#"), tableRoot);
					companyProcessNode.setExpanded(true);
					if (!findRecursive(hostingCompanyProcess, companyProcessNode)) {
						companyProcessNode.setType("HostingCompanyProcessLast");
					}
					if (!findRecursive2(hostingCompanyProcess, companyProcessNode2)) {
						companyProcessNode2.setType("HostingCompanyProcessLast");
					}
				}

			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
		runClientSideExecute("changeColor()");
	}

	/**
	 * Find recursive.
	 *
	 * @param hostingCompanyProcess
	 *            the hosting company process
	 * @param parent
	 *            the parent
	 * @return true, if successful
	 * @throws Exception
	 *             the exception
	 */
	private boolean findRecursive(HostingCompanyProcess hostingCompanyProcess, TreeNode parent) throws Exception {

		processrolesList = service.allProcessRoles(hostingCompanyProcess);
		if (processrolesList.size() > 0) {
			for (ProcessRoles processRoles : processrolesList) {
				TreeNode processNode = null;
				if (processRoles.getCompanyUserType() == null) {
					processNode = new DefaultTreeNode("ProcessRoles", processRoles, parent);
					processNode.setExpanded(expandAll);
				} else {
					processNode = new DefaultTreeNode("ProcessRolesCompany", processRoles, parent);
					processNode.setExpanded(expandAll);

				}
				List<UsersRole> l = usersRoleService.findByProcess(processRoles);
				for (UsersRole hostingCompanyEmployees : l) {
					TreeNode employeeNode = new DefaultTreeNode("HostingCompanyEmployees", hostingCompanyEmployees, processNode);
				}
			}
		}

		if (hostingCompanyProcess.getNextProcess() != null) {
			TreeNode processNode = new DefaultTreeNode("HostingCompanyProcessChild", hostingCompanyProcess.getNextProcess(), parent);
			processNode.setExpanded(true);
			if (!findRecursive(hostingCompanyProcess.getNextProcess(), processNode)) {
				processNode.setType("HostingCompanyProcessChildLast");
			}
		} else {
			return false;
		}
		return true;
	}

	private boolean findRecursive2(HostingCompanyProcess hostingCompanyProcess, TreeNode parent) throws Exception {

		processrolesList = service.allProcessRoles(hostingCompanyProcess);
		if (processrolesList.size() > 0) {
			for (ProcessRoles processRoles : processrolesList) {
				TreeNode processNode = null;
				String sla = "SLA " + (processRoles.getNumberOfDays() != null ? processRoles.getNumberOfDays() : 5);
				if (processRoles.getCompanyUserType() == null) {
					processNode = new DefaultTreeNode(new TreeTableBean("#BOLD#" + processRoles.getRoles().getDescription(), "#BOLD#" + processRoles.getRolePermission().getFriendlyName(),  "#BOLD#" + sla), parent);
				} else {
					processNode = new DefaultTreeNode(new TreeTableBean("#BOLD#" + getEntryLanguage(processRoles.getCompanyUserType().getType()) + " (Client Role)", "#BOLD#" + processRoles.getRolePermission().getFriendlyName(),  "#BOLD#" + sla), parent);

				}
				List<UsersRole> l = usersRoleService.findByProcess(processRoles);
				for (UsersRole hostingCompanyEmployees : l) {
//					#{doc.users.firstName} #{doc.users.lastName}
					TreeNode employeeNode = new DefaultTreeNode(new TreeTableBean(hostingCompanyEmployees.getUsers().getFirstName() + " " + hostingCompanyEmployees.getUsers().getLastName(), "", ""), processNode);
//					TreeNode employeeNode = new DefaultTreeNode("HostingCompanyEmployees", hostingCompanyEmployees, processNode);
				}
			}
		}

		if (hostingCompanyProcess.getNextProcess() != null) {
			TreeNode processNode = new DefaultTreeNode("HostingCompanyProcessChild", hostingCompanyProcess.getNextProcess(), parent);
			processNode.setExpanded(true);
			if (!findRecursive(hostingCompanyProcess.getNextProcess(), processNode)) {
				processNode.setType("HostingCompanyProcessChildLast");
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Display selected single.
	 */
	public void displaySelectedSingle() {
		runClientSideExecute("changeColor()");
		try {
			if (selectedNode != null) {
				String dlg = selectedNode.getType();
				switch (selectedNode.getType()) {
					case "HostingCompanyProcess":
						getRolesInfo();
						break;
					case "HostingCompanyProcessLast":
						dlg = dlg.replaceAll("Last", "");
						getRolesInfo();
						break;
					case "HostingCompanyProcessChild":
						dlg = dlg.replaceAll("Child", "");
						getRolesInfo();
						break;
					case "HostingCompanyProcessChildLast":
						dlg = dlg.replaceAll("ChildLast", "");
						getRolesInfo();
						break;
					case "ProcessRoles":
						findEmployees();
						break;
					default:
						break;
				}
				runClientSideExecute("PF('" + dlg + "').show()");
				runClientSideUpdate(dlg + "Form");

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prep process.
	 */
	public void prepProcess() {
		runClientSideExecute("changeColor()");
		hostingCompanyProcess = new HostingCompanyProcess();
		hostingCompanyProcess.setHostingCompany(hostingCompany);
		runClientSideUpdate("NewProcessForm");
	}

	/**
	 * Save process.
	 */
	public void saveProcess() {
		try {
			HostingCompanyProcess process = (HostingCompanyProcess) selectedNode.getData();
			process.setNextProcess(hostingCompanyProcess);
			// hostingCompanyProcess.setWorkflowProcess(process.getWorkflowProcess());
			hostingCompanyProcessService.create(hostingCompanyProcess);
			hostingCompanyProcessService.create(process);
			runClientSideExecute("PF('NewProcess').hide()");
			runClientSideExecute("changeColor()");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Find employees.
	 */
	private void findEmployees() {
		ProcessRoles pr = ((ProcessRoles) selectedNode.getData());
		if (!getSessionUI().getActiveUser().getSuperAdmin() && pr.getRoles().getDescription().equals("Administrator")) {
			canAddManager = true;
			this.hostingCompanyEmployees = companyDepartmentsEmployeesService.findEmployeeByRole(hostingCompany, pr.getRoles());

			if (this.hostingCompanyEmployees.size() == 0) {

				this.hostingCompanyEmployees = hostingCompanyEmployeesService.findHostingCompanyEmployeesNotInRole(pr, hostingCompany);

			}
		} else if (getSessionUI().getActiveUser().getSuperAdmin()) {
			this.hostingCompanyEmployees = companyDepartmentsEmployeesService.findEmployeeByRole(hostingCompany, pr.getRoles());
			if (this.hostingCompanyEmployees.size() == 0) {
				this.hostingCompanyEmployees = hostingCompanyEmployeesService.findHostingCompanyEmployeesNotInRole(pr, hostingCompany);
			}
			canAddManager = true;
		} else {
			canAddManager = false;
		}
	}

	/**
	 * Insert ProcessRoles into database.
	 *
	 * @author TechFinium
	 * @see ProcessRoles
	 */
	public void processrolesInsert() {
		try {
			if (processroles.getId() == null) {
				if (processrolesList != null && processrolesList.size() > 0) {
					processroles.setRoleOrder(processrolesList.get(processrolesList.size() - 1).getRoleOrder() + 1);
				} else {
					processroles.setRoleOrder(1);
				}
				processroles.setHostingCompanyProcess((HostingCompanyProcess) selectedNode.getData());
				processroles.setCompanyUserType(((HostingCompanyProcess) selectedNode.getData()).getWorkflowProcess());
			}
			if (companyRole) {
				processroles.setRoles(null);
			} else {
				processroles.setCompanyUserType(null);
			}
			service.create(this.processroles);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("insert.successful"));
			processrolesList = service.allProcessRoles((HostingCompanyProcess) selectedNode.getData());
			getRolesInfo();
			runClientSideExecute("changeColor()");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Processroles select.
	 *
	 * @param event
	 *            the event
	 */
	public void processrolesSelect(SelectEvent event) {
		clearProcessRole();
		this.processroles = (ProcessRoles) event.getObject();
		this.companyRole = processroles.getCompanyUserType() != null;
	}

	/**
	 * Processroles un select.
	 *
	 * @param event
	 *            the event
	 */
	public void processrolesUnSelect(UnselectEvent event) {
		clearProcessRole();
	}

	/**
	 * Clear process role.
	 */
	public void clearProcessRole() {
		this.processroles = new ProcessRoles();
		getRolesInfo();

	}

	/**
	 * Insert ProcessRoles into database.
	 *
	 * @author TechFinium
	 * @see ProcessRoles
	 */
	public void processInsert() {
		try {
			hostingCompanyProcess.setHostingCompany(hostingCompany);
			hostingCompanyProcessService.create(this.hostingCompanyProcess);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("insert.successful"));
			findCurrentProcesses();
			runClientSideExecute("PF('" + selectedNode.getType() + "').hide()");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
			runClientSideExecute("PF('" + selectedNode.getType() + "').hide()");
		}
	}

	/**
	 * Insert ProcessRoles into database.
	 *
	 * @author TechFinium
	 * @see ProcessRoles
	 */
	public void usersInsert() {
		try {
			usersRole.setProcessRoles((ProcessRoles) selectedNode.getData());
			usersRole.setUsers(hostinCompanyEmployee.getUsers());
			usersRoleService.create(usersRole);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("insert.successful"));
			findCurrentProcesses();
			runClientSideExecute("PF('" + selectedNode.getType() + "').hide()");
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
			runClientSideExecute("PF('" + selectedNode.getType() + "').hide()");
		}
	}

	/**
	 * Removes the process role.
	 */
	public void removeProcessRole() {
		try {
			service.delete((ProcessRoles) selectedNode.getData());
			prepareNew();
			addInfoMessage(super.getEntryLanguage("process.role.removed"));
			findCurrentProcesses();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
		runClientSideExecute("changeColor()");
	}

	/**
	 * Removes the process role dlg.
	 */
	public void removeProcessRoleDlg() {
		try {
			service.delete(processroles);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("row.deleted"));
			clearProcessRole();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
		runClientSideExecute("changeColor()");
	}

	/**
	 * Removes the process.
	 */
	public void removeProcess() {
		try {
			if (selectedNode != null) {
				processrolesList = service.allProcessRoles((HostingCompanyProcess) selectedNode.getData());
				if (processrolesList.size() == 0) {
					hostingCompanyProcessService.deleteWithPrevious((HostingCompanyProcess) selectedNode.getData());
					prepareNew();
					addInfoMessage(super.getEntryLanguage("process.removed"));
				} else {
					addWarningMessage(super.getEntryLanguage("process.not.removed"));
				}
			}
			findCurrentProcesses();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
		runClientSideExecute("changeColor()");
	}

	/**
	 * Removes the user role.
	 */
	public void removeUserRole() {
		try {
			usersRoleService.delete((UsersRole) selectedNode.getData());
			prepareNew();
			addInfoMessage(super.getEntryLanguage("user.removed"));
			findCurrentProcesses();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
		runClientSideExecute("changeColor()");
	}

	/**
	 * Update ProcessRoles in database.
	 *
	 * @author TechFinium
	 * @see ProcessRoles
	 */
	public void processrolesUpdate() {
		try {
			service.update(this.processroles);
			addInfoMessage(super.getEntryLanguage("update.successful"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete ProcessRoles from database.
	 *
	 * @author TechFinium
	 * @see ProcessRoles
	 */
	public void processrolesDelete() {
		try {
			service.delete(this.processroles);
			prepareNew();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of ProcessRoles.
	 *
	 * @author TechFinium
	 * @see ProcessRoles
	 */
	public void prepareNew() {
		hostingCompanyProcess = new HostingCompanyProcess();
		processroles = new ProcessRoles();
		usersRole = new UsersRole();
		hostinCompanyEmployee = null;
		companyRole = false;
	}

	/**
	 * Gets the work flow process no duplicates.
	 *
	 * @return the work flow process no duplicates
	 */
	public List<SelectItem> getWorkFlowProcessNoDuplicates() {
			List<ConfigDocProcessEnum> val = new ArrayList<>(Arrays.asList(ConfigDocProcessEnum.values()));
			// val.remove(ConfigDocProcessEnum.WSP);
			if (currentProcesses != null) {
				for (HostingCompanyProcess hostingCompanyProcess : currentProcesses) {
					val.remove(hostingCompanyProcess.getWorkflowProcess());
				}
			}
			List<SelectItem> l = new ArrayList<SelectItem>();
			for (ConfigDocProcessEnum selectItem : val) {
				if (selectItem != null) {
					l.add(new SelectItem(selectItem, getEntryLanguage(selectItem.getRegistrationName())));
				}
			}
		return l;	
	}

	/**
	 * Gets the roles info.
	 *
	 * @return the roles info
	 */
	private void getRolesInfo() {
		try {
			if (selectedNode != null) {
				hostingCompanyProcess = (HostingCompanyProcess) selectedNode.getData();
				processrolesList = service.allProcessRoles((HostingCompanyProcess) selectedNode.getData());
				roles = rolesService.allRoles((HostingCompanyProcess) selectedNode.getData());
				if (hostingCompanyProcess.getWorkflowProcess() == ConfigDocProcessEnum.WSP) {
					processroles.setRolePermission(UserPermissionEnum.Approve);
				}
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * On reorder.
	 */
	public void onReorder() {
		try {
			for (ProcessRoles processRoles : processrolesList) {
				processRoles.setRoleOrder(processrolesList.indexOf(processRoles) + 1);
			}
			service.update(processrolesList);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<ProcessRoles> complete(String desc) {
		List<ProcessRoles> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Gets the processroles.
	 *
	 * @return the processroles
	 */
	public ProcessRoles getProcessroles() {
		return processroles;
	}

	/**
	 * Sets the processroles.
	 *
	 * @param processroles
	 *            the new processroles
	 */
	public void setProcessroles(ProcessRoles processroles) {
		this.processroles = processroles;
	}

	/**
	 * Gets the process rolesfiltered list.
	 *
	 * @return the process rolesfiltered list
	 */
	public List<ProcessRoles> getProcessRolesfilteredList() {
		return processrolesfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param processrolesfilteredList
	 *            the new processrolesfilteredList list
	 * @see ProcessRoles
	 */
	public void setProcessRolesfilteredList(List<ProcessRoles> processrolesfilteredList) {
		this.processrolesfilteredList = processrolesfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<ProcessRoles> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<ProcessRoles> dataModel) {
		this.dataModel = dataModel;
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
	 * @param hostingCompanyProcess
	 *            the new hosting company process
	 */
	public void setHostingCompanyProcess(HostingCompanyProcess hostingCompanyProcess) {
		this.hostingCompanyProcess = hostingCompanyProcess;
	}

	/**
	 * Gets the processrolesfiltered list.
	 *
	 * @return the processrolesfiltered list
	 */
	public List<ProcessRoles> getProcessrolesfilteredList() {
		return processrolesfilteredList;
	}

	/**
	 * Sets the processrolesfiltered list.
	 *
	 * @param processrolesfilteredList
	 *            the new processrolesfiltered list
	 */
	public void setProcessrolesfilteredList(List<ProcessRoles> processrolesfilteredList) {
		this.processrolesfilteredList = processrolesfilteredList;
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
	 * @param hostingCompany
	 *            the new hosting company
	 */
	public void setHostingCompany(HostingCompany hostingCompany) {
		this.hostingCompany = hostingCompany;
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
	 * @param currentProcesses
	 *            the new current processes
	 */
	public void setCurrentProcesses(List<HostingCompanyProcess> currentProcesses) {
		this.currentProcesses = currentProcesses;
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
	 * @param selectedNode
	 *            the new selected node
	 */
	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	/**
	 * Gets the processroles list.
	 *
	 * @return the processroles list
	 */
	public List<ProcessRoles> getProcessrolesList() {
		return processrolesList;
	}

	/**
	 * Sets the processroles list.
	 *
	 * @param processrolesList
	 *            the new processroles list
	 */
	public void setProcessrolesList(List<ProcessRoles> processrolesList) {
		this.processrolesList = processrolesList;
	}

	/**
	 * Gets the roles.
	 *
	 * @return the roles
	 */
	public List<Roles> getRoles() {
		return roles;
	}

	/**
	 * Sets the roles.
	 *
	 * @param roles
	 *            the new roles
	 */
	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	/**
	 * Gets the hosting company employees.
	 *
	 * @return the hosting company employees
	 */
	public List<HostingCompanyEmployees> getHostingCompanyEmployees() {
		return hostingCompanyEmployees;
	}

	/**
	 * Sets the hosting company employees.
	 *
	 * @param hostingCompanyEmployees
	 *            the new hosting company employees
	 */
	public void setHostingCompanyEmployees(List<HostingCompanyEmployees> hostingCompanyEmployees) {
		this.hostingCompanyEmployees = hostingCompanyEmployees;
	}

	/**
	 * Gets the hostin company employee.
	 *
	 * @return the hostin company employee
	 */
	public HostingCompanyEmployees getHostinCompanyEmployee() {
		return hostinCompanyEmployee;
	}

	/**
	 * Sets the hostin company employee.
	 *
	 * @param hostinCompanyEmployee
	 *            the new hostin company employee
	 */
	public void setHostinCompanyEmployee(HostingCompanyEmployees hostinCompanyEmployee) {
		this.hostinCompanyEmployee = hostinCompanyEmployee;
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
	 * @param expandAll
	 *            the new expand all
	 */
	public void setExpandAll(boolean expandAll) {
		this.expandAll = expandAll;
	}

	/**
	 * Gets the demo root.
	 *
	 * @return the demo root
	 */
	public TreeNode getDemoRoot() {
		return demoRoot;
	}

	/**
	 * Sets the demo root.
	 *
	 * @param demoRoot
	 *            the new demo root
	 */
	public void setDemoRoot(TreeNode demoRoot) {
		this.demoRoot = demoRoot;
	}

	/**
	 * Checks if is company role.
	 *
	 * @return true, if is company role
	 */
	public boolean isCompanyRole() {
		return companyRole;
	}

	/**
	 * Sets the company role.
	 *
	 * @param companyRole
	 *            the new company role
	 */
	public void setCompanyRole(boolean companyRole) {
		this.companyRole = companyRole;
	}

	public boolean isCanAddManager() {
		return canAddManager;
	}

	public void setCanAddManager(boolean canAddManager) {
		this.canAddManager = canAddManager;
	}

	public TreeNode getTableRoot() {
		return tableRoot;
	}

	public void setTableRoot(TreeNode tableRoot) {
		this.tableRoot = tableRoot;
	}

}
