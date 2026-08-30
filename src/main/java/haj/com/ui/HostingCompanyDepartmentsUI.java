package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.organigram.OrganigramNodeDragDropEvent;
import org.primefaces.event.organigram.OrganigramNodeSelectEvent;
import org.primefaces.model.DefaultOrganigramNode;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.OrganigramNode;
import org.primefaces.model.SortOrder;

import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyDepartments;
import haj.com.entity.HostingCompanyDepartmentsChatEmployees;
import haj.com.entity.HostingCompanyDepartmentsEmployees;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.lookup.Roles;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HostingCompanyDepartmentsEmployeesService;
import haj.com.service.HostingCompanyDepartmentsService;
import haj.com.service.HostingCompanyService;
import haj.com.service.lookup.RolesService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyDepartmentsUI.
 */
@ManagedBean(name = "hostingcompanydepartmentsUI")
@ViewScoped
public class HostingCompanyDepartmentsUI extends AbstractUI {

	/** The service. */

	private HostingCompanyService hostingCompanyService = new HostingCompanyService();
	private HostingCompanyDepartmentsService service = new HostingCompanyDepartmentsService();

	/** The hostingcompanydepartments list. */
	private List<HostingCompanyDepartments> hostingcompanydepartmentsList = null;

	/** The hostingcompanydepartmentsfiltered list. */
	private List<HostingCompanyDepartments> hostingcompanydepartmentsfilteredList = null;

	/** The hostingcompanydepartments. */
	private HostingCompanyDepartments hostingcompanydepartments = null;

	/** The data model. */
	private LazyDataModel<HostingCompanyDepartments> dataModel;

	/** The hosting company departments employees list. */
	private List<HostingCompanyDepartmentsEmployees> hostingCompanyDepartmentsEmployeesList;

	/** The hosting company departments employees service. */
	private HostingCompanyDepartmentsEmployeesService hostingCompanyDepartmentsEmployeesService = new HostingCompanyDepartmentsEmployeesService();

	/** The hosting company departments employees. */
	private HostingCompanyDepartmentsEmployees hostingCompanyDepartmentsEmployees = null;

	/** The employees. */
	private List<HostingCompanyEmployees> employees;

	/** The employee. */
	private HostingCompanyDepartmentsEmployees employee;

	/** The roles. */
	private List<Roles> roles;

	/** The roles service. */
	private RolesService rolesService = new RolesService();

	/** The root node. */
	private OrganigramNode rootNode;

	/** The selection. */
	private OrganigramNode selection;

	/** The hosting company. */
	private HostingCompany hostingCompany;

	/** The selected hostingcompanydepartments. */
	private HostingCompanyDepartments selectedHostingcompanydepartments = null;

	/** The selected employee. */
	private HostingCompanyDepartmentsEmployees selectedEmployee;

	/** The selectlist. */
	private List<HostingCompanyDepartments> selectlist = null;


	private List<HostingCompanyEmployees> chatEmployees;
	private HostingCompanyDepartmentsChatEmployees chatEmployee;
	private List<HostingCompanyDepartmentsChatEmployees> hostingCompanyDepartmentsChatEmployeesList;

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
	 * Initialize method to get all HostingCompanyDepartments and prepare a for
	 * a create of a new HostingCompanyDepartments.
	 *
	 * @author TechFinium
	 * @throws Exception             the exception
	 * @see HostingCompanyDepartments
	 */
	private void runInit() throws Exception {
		prepareNew();
		hostingcompanydepartmentsInfo();

	}

	/**
	 * Get all HostingCompanyDepartments for data table.
	 *
	 * @author TechFinium
	 * @see HostingCompanyDepartments
	 */
	public void hostingcompanydepartmentsInfo() {

		try {
			this.hostingCompany = hostingCompanyService.findByKey(1l);
		} catch (Exception e) {
			logger.fatal(e);
		}

		dataModel = new LazyDataModel<HostingCompanyDepartments>() {

			private static final long serialVersionUID = 1L;
			private List<HostingCompanyDepartments> retorno = new ArrayList<HostingCompanyDepartments>();

			@Override
			public List<HostingCompanyDepartments> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allHostingCompanyDepartments(HostingCompanyDepartments.class, first, pageSize,sortField, sortOrder, filters);

					dataModel.setRowCount(service.count(HostingCompanyDepartments.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(HostingCompanyDepartments obj) {
				return obj.getId();
			}

			@Override
			public HostingCompanyDepartments getRowData(String rowKey) {
				for (HostingCompanyDepartments obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert HostingCompanyDepartments into database.
	 *
	 * @author TechFinium
	 * @see HostingCompanyDepartments
	 */
	public void hostingcompanydepartmentsInsert() {
		try {
			service.create(this.hostingcompanydepartments);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			hostingcompanydepartmentsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update HostingCompanyDepartments in database.
	 *
	 * @author TechFinium
	 * @see HostingCompanyDepartments
	 */
	public void hostingcompanydepartmentsUpdate() {
		try {
			service.update(this.hostingcompanydepartments);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			hostingcompanydepartmentsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete HostingCompanyDepartments from database.
	 *
	 * @author TechFinium
	 * @see HostingCompanyDepartments
	 */
	public void hostingcompanydepartmentsDelete() {
		try {
			service.delete(this.hostingcompanydepartments);
			prepareNew();
			hostingcompanydepartmentsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * View employees.
	 */
	public void viewEmployees() {
		try {
			this.roles = rolesService.allRoles();
			employee =  new HostingCompanyDepartmentsEmployees();
			employee.setHostingCompanyDepartments(hostingcompanydepartments);
			employees = hostingCompanyDepartmentsEmployeesService.findAllNotUsedEmployees(hostingcompanydepartments);
			hostingCompanyDepartmentsEmployeesList = hostingCompanyDepartmentsEmployeesService.findByDepartment(hostingcompanydepartments);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of HostingCompanyDepartments.
	 *
	 * @author TechFinium
	 * @see HostingCompanyDepartments
	 */
	public void prepareNew() {
		hostingcompanydepartments = new HostingCompanyDepartments();
		employee =  new HostingCompanyDepartmentsEmployees();
		employee.setHostingCompanyDepartments(hostingcompanydepartments);
		chatEmployee =  new HostingCompanyDepartmentsChatEmployees();
		chatEmployee.setHostingCompanyDepartments(hostingcompanydepartments);
	}

	/*
	 * public List<SelectItem> getHostingCompanyDepartmentsDD() {
	 * List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * hostingcompanydepartmentsInfo(); for (HostingCompanyDepartments ug :
	 * hostingcompanydepartmentsList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<HostingCompanyDepartments> complete(String desc) {
		List<HostingCompanyDepartments> l = null;
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
	 * Gets the hosting company departments list.
	 *
	 * @return the hosting company departments list
	 */
	public List<HostingCompanyDepartments> getHostingCompanyDepartmentsList() {
		return hostingcompanydepartmentsList;
	}

	/**
	 * Sets the hosting company departments list.
	 *
	 * @param hostingcompanydepartmentsList the new hosting company departments list
	 */
	public void setHostingCompanyDepartmentsList(List<HostingCompanyDepartments> hostingcompanydepartmentsList) {
		this.hostingcompanydepartmentsList = hostingcompanydepartmentsList;
	}

	/**
	 * Gets the hostingcompanydepartments.
	 *
	 * @return the hostingcompanydepartments
	 */
	public HostingCompanyDepartments getHostingcompanydepartments() {
		return hostingcompanydepartments;
	}

	/**
	 * Sets the hostingcompanydepartments.
	 *
	 * @param hostingcompanydepartments the new hostingcompanydepartments
	 */
	public void setHostingcompanydepartments(HostingCompanyDepartments hostingcompanydepartments) {
		this.hostingcompanydepartments = hostingcompanydepartments;
	}

	/**
	 * Gets the hosting company departmentsfiltered list.
	 *
	 * @return the hosting company departmentsfiltered list
	 */
	public List<HostingCompanyDepartments> getHostingCompanyDepartmentsfilteredList() {
		return hostingcompanydepartmentsfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param hostingcompanydepartmentsfilteredList            the new hostingcompanydepartmentsfilteredList list
	 * @see HostingCompanyDepartments
	 */
	public void setHostingCompanyDepartmentsfilteredList(
			List<HostingCompanyDepartments> hostingcompanydepartmentsfilteredList) {
		this.hostingcompanydepartmentsfilteredList = hostingcompanydepartmentsfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<HostingCompanyDepartments> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel the new data model
	 */
	public void setDataModel(LazyDataModel<HostingCompanyDepartments> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * View processes.
	 */
	public void viewProcesses() {
		try {
			getSessionUI().setHostingCompany(this.hostingcompanydepartments.getHostingCompany());
			super.redirect("/admin/hostingcompanydepartmentprocess.jsf?id=" + this.hostingcompanydepartments.getId());

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * Gets the hosting company departments employees list.
	 *
	 * @return the hosting company departments employees list
	 */
	public List<HostingCompanyDepartmentsEmployees> getHostingCompanyDepartmentsEmployeesList() {
		return hostingCompanyDepartmentsEmployeesList;
	}

	/**
	 * Sets the hosting company departments employees list.
	 *
	 * @param hostingCompanyDepartmentsEmployeesList the new hosting company departments employees list
	 */
	public void setHostingCompanyDepartmentsEmployeesList(
			List<HostingCompanyDepartmentsEmployees> hostingCompanyDepartmentsEmployeesList) {
		this.hostingCompanyDepartmentsEmployeesList = hostingCompanyDepartmentsEmployeesList;
	}

	/**
	 * Gets the hosting company departments employees.
	 *
	 * @return the hosting company departments employees
	 */
	public HostingCompanyDepartmentsEmployees getHostingCompanyDepartmentsEmployees() {
		return hostingCompanyDepartmentsEmployees;
	}

	/**
	 * Sets the hosting company departments employees.
	 *
	 * @param hostingCompanyDepartmentsEmployees the new hosting company departments employees
	 */
	public void setHostingCompanyDepartmentsEmployees(
			HostingCompanyDepartmentsEmployees hostingCompanyDepartmentsEmployees) {
		this.hostingCompanyDepartmentsEmployees = hostingCompanyDepartmentsEmployees;
	}

	/**
	 * Gets the employees.
	 *
	 * @return the employees
	 */
	public List<HostingCompanyEmployees> getEmployees() {
		return employees;
	}

	/**
	 * Sets the employees.
	 *
	 * @param employees the new employees
	 */
	public void setEmployees(List<HostingCompanyEmployees> employees) {
		this.employees = employees;
	}

	/**
	 * Gets the employee.
	 *
	 * @return the employee
	 */
	public HostingCompanyDepartmentsEmployees getEmployee() {
		return employee;
	}

	/**
	 * Sets the employee.
	 *
	 * @param employee the new employee
	 */
	public void setEmployee(HostingCompanyDepartmentsEmployees employee) {
		this.employee = employee;
	}


	/**
	 * Adds the employee.
	 */
	public void addEmployee() {
		try {
			this.employee.setRole(this.employee.getHostingCompanyEmployees().getJobTitle().getRoles());
			if (this.employee.getId() ==null)
				service.createIfNotExist(this.employee);

			else
			  service.update(this.employee);
			viewEmployees();
			createDiagram();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * Delete employee.
	 */
	public void deleteEmployee() {
		try {
			this.employee.setRole(this.employee.getHostingCompanyEmployees().getJobTitle().getRoles());
			hostingCompanyDepartmentsEmployeesService.delete(this.employee);
			viewEmployees();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
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
	 * @param roles the new roles
	 */
	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}

	/**
	 * Find departments.
	 */
	public void findDepartments() {
		try {
			if (this.hostingcompanydepartments!=null && this.hostingcompanydepartments.getHostingCompany()!=null)
              this.selectlist =  service.findByHc(this.hostingcompanydepartments.getHostingCompany());

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	/**
	 * Put in session.
	 */
	public void putInSession() {
		try {
            getSessionUI().setHostingCompany(this.hostingcompanydepartments.getHostingCompany());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the selectlist.
	 *
	 * @return the selectlist
	 */
	public List<HostingCompanyDepartments> getSelectlist() {
		return selectlist;
	}

	/**
	 * Sets the selectlist.
	 *
	 * @param selectlist the new selectlist
	 */
	public void setSelectlist(List<HostingCompanyDepartments> selectlist) {
		this.selectlist = selectlist;
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
	 * Creates the diagram.
	 *
	 * @throws Exception the exception
	 */
	public void createDiagram() throws Exception {

		  rootNode = new DefaultOrganigramNode("root", this.hostingCompany.getCompanyName(), null);
		   rootNode.setCollapsible(false);
		   rootNode.setDroppable(true);
		   rootNode.setDraggable(false);
		   rootNode.setSelectable(true);

		   List<HostingCompanyDepartments> l = service.findByHcRoot(this.hostingCompany);
		   for (HostingCompanyDepartments a : l) {
			   OrganigramNode node =   new DefaultOrganigramNode("department",a, rootNode);
			   node.setCollapsible(true);
			   node.setSelectable(true);
			   node.setDraggable(true);
			   node.setDroppable(true);
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
			 child.setDroppable(true);
			 child.setDraggable(true);
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

			   new  DefaultOrganigramNode("na","N/A", node);
		   }
		   else {
		    for (HostingCompanyDepartmentsEmployees b : l2) {
			  // new  DefaultOrganigramNode("employee",b.getHostingCompanyEmployees().getUsers().getFirstName() + " "+b.getHostingCompanyEmployees().getUsers().getLastName() + " "+( b.getRole()==null?"":(" ("+ b.getRole().getDescription()+")")), node);
		    	OrganigramNode emp = new  DefaultOrganigramNode("employee",b, node);
		    	  emp.setDroppable(false);
		    	  emp.setDraggable(true);
		    	  emp.setSelectable(true);

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
     * Node drag drop listener.
     *
     * @param event the event
     */
    public void nodeDragDropListener(OrganigramNodeDragDropEvent event) {
      try {
		if (event.getOrganigramNode().getData() instanceof HostingCompanyDepartments) {
			   (( HostingCompanyDepartments)event.getOrganigramNode().getData()).setParentDepartment((HostingCompanyDepartments)event.getTargetOrganigramNode().getData());
		     service.update(( HostingCompanyDepartments)event.getOrganigramNode().getData());
		     createDiagram();
		}
		else if (event.getOrganigramNode().getData() instanceof HostingCompanyDepartmentsEmployees)  {
			 (( HostingCompanyDepartmentsEmployees)event.getOrganigramNode().getData()).setHostingCompanyDepartments((HostingCompanyDepartments)event.getTargetOrganigramNode().getData());
			 hostingCompanyDepartmentsEmployeesService.update(( HostingCompanyDepartmentsEmployees)event.getOrganigramNode().getData());
			 createDiagram();
		}
		    logger.info("Node '" + event.getOrganigramNode().getData() + "' moved from " + event.getSourceOrganigramNode().getData() + " to '" + event.getTargetOrganigramNode().getData() + "'");
	} catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
	}

    }

	/**
	 * Node select listener.
	 *
	 * @param event the event
	 */
	public void nodeSelectListener(OrganigramNodeSelectEvent event) {
		try {
			this.roles = rolesService.allRoles();
			if (event.getOrganigramNode().getData() instanceof String) {
				this.hostingcompanydepartments = new HostingCompanyDepartments();
				this.hostingcompanydepartments.setHostingCompany(this.hostingCompany);
				this.hostingcompanydepartments.setParentDepartment(null);

			} else if (event.getOrganigramNode().getData() instanceof HostingCompanyDepartments) {
				this.hostingcompanydepartments = new HostingCompanyDepartments();
				this.selectedHostingcompanydepartments = (HostingCompanyDepartments) event.getOrganigramNode().getData();
				this.hostingcompanydepartments.setHostingCompany(this.hostingCompany);
				this.hostingcompanydepartments.setParentDepartment((HostingCompanyDepartments) event.getOrganigramNode().getData());
				this.roles = rolesService.allRoles();
				employee =  new HostingCompanyDepartmentsEmployees();
				employee.setHostingCompanyDepartments(selectedHostingcompanydepartments);
				employees = hostingCompanyDepartmentsEmployeesService.findAllNotUsedEmployees(selectedHostingcompanydepartments);
				hostingCompanyDepartmentsEmployeesList = hostingCompanyDepartmentsEmployeesService.findByDepartment(selectedHostingcompanydepartments);

			} else if (event.getOrganigramNode().getData() instanceof HostingCompanyDepartmentsEmployees) {
				this.selectedEmployee = (HostingCompanyDepartmentsEmployees) event.getOrganigramNode().getData();

			}

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * Prepare new department.
	 */
	public void prepareNewDepartment() {
		 try {
	         logger.info("prepareNewDepartment "+this.hostingcompanydepartments);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Save department.
	 */
	public void saveDepartment() {
		 try {
			 service.create(this.hostingcompanydepartments);
		     createDiagram();
		     super.runClientSideExecute("PF('addDepartmentDialog').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Removes the department.
	 */
	public void removeDepartment() {
		 try {
			 service.delete(this.selectedHostingcompanydepartments);
		     createDiagram();
		 } catch (org.hibernate.exception.ConstraintViolationException cv) {
		    addErrorMessage("Please delete all dependants first", cv);
		 }
		  catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Removes the user.
	 */
	public void removeUser() {
		 try {
			 service.delete(this.selectedEmployee);
		     createDiagram();

		 } catch (org.hibernate.exception.ConstraintViolationException cv) {
		    addErrorMessage("Please delete all dependants first", cv);
		 }
		  catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	/**
	 * Gets the selected hostingcompanydepartments.
	 *
	 * @return the selected hostingcompanydepartments
	 */
	public HostingCompanyDepartments getSelectedHostingcompanydepartments() {
		return selectedHostingcompanydepartments;
	}

	/**
	 * Sets the selected hostingcompanydepartments.
	 *
	 * @param selectedHostingcompanydepartments the new selected hostingcompanydepartments
	 */
	public void setSelectedHostingcompanydepartments(HostingCompanyDepartments selectedHostingcompanydepartments) {
		this.selectedHostingcompanydepartments = selectedHostingcompanydepartments;
	}

	/**
	 * Gets the selected employee.
	 *
	 * @return the selected employee
	 */
	public HostingCompanyDepartmentsEmployees getSelectedEmployee() {
		return selectedEmployee;
	}

	/**
	 * Sets the selected employee.
	 *
	 * @param selectedEmployee the new selected employee
	 */
	public void setSelectedEmployee(HostingCompanyDepartmentsEmployees selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public List<HostingCompanyEmployees> getChatEmployees() {
		return chatEmployees;
	}

	public void setChatEmployees(List<HostingCompanyEmployees> chatEmployees) {
		this.chatEmployees = chatEmployees;
	}

	public HostingCompanyDepartmentsChatEmployees getChatEmployee() {
		return chatEmployee;
	}

	public void setChatEmployee(HostingCompanyDepartmentsChatEmployees chatEmployee) {
		this.chatEmployee = chatEmployee;
	}


	public void addChatEmployee() {
		try {

			if (this.chatEmployee.getId() ==null)
				service.createIfNotExist(this.chatEmployee);

			else
			  service.update(this.chatEmployee);
			viewChatEmployees();
			createDiagram();

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	public void viewChatEmployees() {
		try {

			chatEmployee =  new HostingCompanyDepartmentsChatEmployees();
			chatEmployee.setHostingCompanyDepartments(hostingcompanydepartments);
			chatEmployees = hostingCompanyDepartmentsEmployeesService.findAllNotUsedChatEmployees(hostingcompanydepartments);
			hostingCompanyDepartmentsChatEmployeesList = hostingCompanyDepartmentsEmployeesService.findByChatmEmpDepartment(hostingcompanydepartments);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<HostingCompanyDepartmentsChatEmployees> getHostingCompanyDepartmentsChatEmployeesList() {
		return hostingCompanyDepartmentsChatEmployeesList;
	}

	public void setHostingCompanyDepartmentsChatEmployeesList(List<HostingCompanyDepartmentsChatEmployees> hostingCompanyDepartmentsChatEmployeesList) {
		this.hostingCompanyDepartmentsChatEmployeesList = hostingCompanyDepartmentsChatEmployeesList;
	}
}

