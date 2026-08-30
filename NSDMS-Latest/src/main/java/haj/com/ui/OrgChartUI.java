package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.organigram.OrganigramNodeSelectEvent;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.OrganigramNode;
import org.primefaces.model.TreeNode;

import haj.com.entity.Blank;
import haj.com.entity.HostingCompanyDepartmentProcess;
import haj.com.entity.HostingCompanyDepartments;
import haj.com.entity.HostingCompanyDepartmentsEmployees;
import haj.com.entity.HostingCompanyProcess;
import haj.com.entity.ProcessRoles;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HostingCompanyDepartmentProcessService;
import haj.com.service.HostingCompanyDepartmentsEmployeesService;
import haj.com.service.HostingCompanyDepartmentsService;
import haj.com.service.HostingCompanyProcessService;
import haj.com.service.ProcessRolesService;
import haj.com.service.UsersRoleService;

// TODO: Auto-generated Javadoc
/**
 * The Class OrgChartUI.
 */
@ManagedBean(name = "orgChartUI")
@ViewScoped
public class OrgChartUI extends AbstractUI {

	/** The service. */
	private HostingCompanyDepartmentsService service = new HostingCompanyDepartmentsService();
	
	/** The hosting company departments employees service. */
	private HostingCompanyDepartmentsEmployeesService hostingCompanyDepartmentsEmployeesService = new HostingCompanyDepartmentsEmployeesService();
	
	/** The hosting company process service. */
	private HostingCompanyProcessService hostingCompanyProcessService = new HostingCompanyProcessService();
	
	/** The process roles service. */
	private ProcessRolesService processRolesService = new ProcessRolesService();
	
	/** The users role service. */
	private UsersRoleService usersRoleService = new UsersRoleService();
	
	/** The root node. */
	private OrganigramNode rootNode;
	
	/** The selection. */
	private OrganigramNode selection;
	
	/** The hosting company departments. */
	private HostingCompanyDepartments hostingCompanyDepartments;
	
	/** The hosting company department process service. */
	private HostingCompanyDepartmentProcessService hostingCompanyDepartmentProcessService = new HostingCompanyDepartmentProcessService();
	
	/** The hosting company department processes. */
	private List<HostingCompanyDepartmentProcess> hostingCompanyDepartmentProcesses;
	
	/** The root. */
	private TreeNode root;
	
	/** The processroles list. */
	private List<ProcessRoles> processrolesList = null;
	
	
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
		if (getSessionUI().getHostingCompany()!=null) {
			createDiagram();

		}
	}

	
	
	/**
	 * Creates the diagram.
	 *
	 * @throws Exception the exception
	 */
	public void createDiagram() throws Exception {
		   rootNode = new DefaultOrganigramNode("root", getSessionUI().getHostingCompany().getCompanyName(), null);
		   rootNode.setCollapsible(false);
		   

		   List<HostingCompanyDepartments> l = service.findByHcRoot(getSessionUI().getHostingCompany());
		   for (HostingCompanyDepartments a : l) {
			   OrganigramNode node =   new DefaultOrganigramNode("department",a, rootNode);
			   node.setCollapsible(false);
			   node.setSelectable(true);
			   resolveEmployees(a,node);
			   buildTree(a,node);
		   }
		
	}

	/**
	 * Builds the tree.
	 *
	 * @param a the a
	 * @param node the node
	 * @throws Exception the exception
	 */
	private void buildTree(HostingCompanyDepartments a, OrganigramNode node) throws Exception {
		 List<HostingCompanyDepartments> l = service.findByParent(a);
		 for (HostingCompanyDepartments hostingCompanyDepartments : l) {
			 OrganigramNode child =   new DefaultOrganigramNode("department",hostingCompanyDepartments, node);
			 child.setCollapsible(false);
			 child.setSelectable(true);
			 resolveEmployees(hostingCompanyDepartments, child);
			 if (hostingCompanyDepartments.getParentDepartment()!=null) {
				 buildTree(hostingCompanyDepartments, child);
			 }
		}
		
	}

	/**
	 * Resolve employees.
	 *
	 * @param a the a
	 * @param node the node
	 * @throws Exception the exception
	 */
	private void resolveEmployees(HostingCompanyDepartments a, OrganigramNode node) throws Exception {
		   List<HostingCompanyDepartmentsEmployees> l2 = hostingCompanyDepartmentsEmployeesService.findByDepartment(a);
		   if (l2.size()==0)  {
			   new  DefaultOrganigramNode("N/A", node);
		   }
		   else {
		    for (HostingCompanyDepartmentsEmployees b : l2) {
			   new  DefaultOrganigramNode(b.getHostingCompanyEmployees().getUsers().getFirstName() + " "+b.getHostingCompanyEmployees().getUsers().getLastName() + " "+( b.getRole()==null?"":(" ("+ b.getRole().getDescription()+")")), node);
		    }
		   }
		
	}

	/**
	 * Gets the root node.
	 *
	 * @return the root node
	 */
	public OrganigramNode getRootNode() {
		return rootNode;
	}

	/**
	 * Sets the root node.
	 *
	 * @param rootNode the new root node
	 */
	public void setRootNode(OrganigramNode rootNode) {
		this.rootNode = rootNode;
	}

	
	/**
	 * Node select listener.
	 *
	 * @param event the event
	 */
	public void nodeSelectListener(OrganigramNodeSelectEvent event) { 
		if (event.getOrganigramNode().getData() instanceof HostingCompanyDepartments) {
			this.hostingCompanyDepartments = (HostingCompanyDepartments)event.getOrganigramNode().getData();
			processDepartment();
		}
	}

	/**
	 * Process department.
	 */
	private void processDepartment() {
		try {
			hostingCompanyDepartmentProcesses = hostingCompanyDepartmentProcessService.findByDepartment(hostingCompanyDepartments);
			root = new DefaultTreeNode("HostingCompanyDepartment", hostingCompanyDepartments, null);
			root.setExpanded(true);
			
			for (HostingCompanyDepartmentProcess hostingCompanyDepartmentProcess : hostingCompanyDepartmentProcesses) {
				TreeNode companyProcessNode = new DefaultTreeNode("HostingCompanyProcess", hostingCompanyDepartmentProcess.getHostingCompanyProcess(), root);
				companyProcessNode.setExpanded(true);
				findRecursive(hostingCompanyDepartmentProcess.getHostingCompanyProcess(), companyProcessNode);
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		
	}
	
	
	/**
	 * Find recursive.
	 *
	 * @param hostingCompanyProcess the hosting company process
	 * @param parent the parent
	 * @return true, if successful
	 * @throws Exception the exception
	 */
	private boolean findRecursive(HostingCompanyProcess hostingCompanyProcess, TreeNode parent) throws Exception {

		 hostingCompanyProcess =  hostingCompanyProcessService.findByKey(hostingCompanyProcess.getId());

		if (hostingCompanyProcess.getNextProcess() != null) {
			TreeNode processNode = new DefaultTreeNode("HostingCompanyProcessChild", hostingCompanyProcess.getNextProcess(), parent);
			processNode.setExpanded(true);
			if(!findRecursive(hostingCompanyProcess.getNextProcess(), processNode)) {
				processNode.setType("HostingCompanyProcessChildLast");
			}
		}else {
			return false;			
		}
		return true;
	}
	

	/**
	 * Gets the selection.
	 *
	 * @return the selection
	 */
	public OrganigramNode getSelection() {
		return selection;
	}

	/**
	 * Sets the selection.
	 *
	 * @param selection the new selection
	 */
	public void setSelection(OrganigramNode selection) {
		this.selection = selection;
	}

	/**
	 * Gets the hosting company departments.
	 *
	 * @return the hosting company departments
	 */
	public HostingCompanyDepartments getHostingCompanyDepartments() {
		return hostingCompanyDepartments;
	}

	/**
	 * Sets the hosting company departments.
	 *
	 * @param hostingCompanyDepartments the new hosting company departments
	 */
	public void setHostingCompanyDepartments(HostingCompanyDepartments hostingCompanyDepartments) {
		this.hostingCompanyDepartments = hostingCompanyDepartments;
	}

	/**
	 * Gets the hosting company department processes.
	 *
	 * @return the hosting company department processes
	 */
	public List<HostingCompanyDepartmentProcess> getHostingCompanyDepartmentProcesses() {
		return hostingCompanyDepartmentProcesses;
	}

	/**
	 * Sets the hosting company department processes.
	 *
	 * @param hostingCompanyDepartmentProcesses the new hosting company department processes
	 */
	public void setHostingCompanyDepartmentProcesses(
			List<HostingCompanyDepartmentProcess> hostingCompanyDepartmentProcesses) {
		this.hostingCompanyDepartmentProcesses = hostingCompanyDepartmentProcesses;
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
	 * Node selected.
	 *
	 * @param event the event
	 */
	public void nodeSelected(OrganigramNodeSelectEvent event) { 
		if (event.getOrganigramNode().getData() instanceof HostingCompanyDepartments) {
			this.hostingCompanyDepartments = (HostingCompanyDepartments)event.getOrganigramNode().getData();
			
		}
	}
}
