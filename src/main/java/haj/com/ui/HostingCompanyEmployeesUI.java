package haj.com.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.HostingCompany;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.UserPermissions;
import haj.com.entity.Users;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.lookup.Office;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.HostingCompanyService;
import haj.com.service.RegisterService;
import haj.com.service.UserPermissionsService;
import haj.com.service.UsersService;

// TODO: Auto-generated Javadoc
/**
 * The Class HostingCompanyEmployeesUI.
 */
@SuppressWarnings("serial")
@ManagedBean(name = "hostingCompanyEmployeesUI")
@ViewScoped
public class HostingCompanyEmployeesUI extends AbstractUI {

	/** The user. */
	private Users user;

	/** The users service. */
	private UsersService usersService = new UsersService();

	/** The hosting company. */
	private HostingCompany hostingCompany;

	/** The hosting company service. */
	private HostingCompanyService hostingCompanyService = new HostingCompanyService();

	/** The hc employee. */
	private HostingCompanyEmployees hcEmployee;

	/** The hc employee filtered list. */
	private List<HostingCompanyEmployees> hcEmployeeFilteredList = new ArrayList<HostingCompanyEmployees>();

	/** The hc employee data model. */
	private LazyDataModel<HostingCompanyEmployees> hcEmployeeDataModel;

	/** The hc employee service. */
	private HostingCompanyEmployeesService hcEmployeeService = new HostingCompanyEmployeesService();
	private IdPassportEnum idPass;

	/** The format Tell phone format. */
	public String TELPHONE_FORMAT = HAJConstants.TELPHONE_FORMAT;

	/** The format cell phone format. */
	public String CELLPHONE_FORMAT = HAJConstants.CELLPHONE_FORMAT;

	/** The maximum size first name . */
	private Long MAX_FIRST_NAME_SIZE = HAJConstants.MAX_FIRST_NAME_SIZE;

	/** The maximum size last name . */
	private Long MAX_LAST_NAME_SIZE = HAJConstants.MAX_LAST_NAME_SIZE;

	/** The maximum size email . */
	private Long MAX_EMAIL_SIZE = HAJConstants.MAX_EMAIL_SIZE;

	/** The maximum RSA ID size */
	private Long MAX_RSA_ID_NUMBER = HAJConstants.MAX_RSA_ID_NUMBER;

	/** The maximum passport size . */
	private Long MAX_PASSPORT_NUMBER = HAJConstants.MAX_PASSPORT_NUMBER;
	
	private RegisterService regService = new RegisterService();
	
	private Users selectedEmployee = null;
	private UserPermissions userPermissions = null;

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
	 * Initialize method to get all HostingCompanyEmployees and prepare a for a
	 * create of a new HostingCompanyEmployees.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see HostingCompanyEmployees
	 */
	private void runInit() throws Exception {
		prepNew();
	}

	public void prepNew() throws Exception {
		this.user = new Users();
		this.user.setActive(Boolean.TRUE);
		this.hcEmployee = new HostingCompanyEmployees();
		this.hostingCompany = hostingCompanyService.findByKey(HAJConstants.HOSTING_MERSETA);
		this.hcEmployeeFilteredList = new ArrayList<HostingCompanyEmployees>();
		hostingCompanyEmployeesInfo();
	}

	public void reregister() {
		try {
			regService.reRegister(hcEmployee.getUsers());
			addInfoMessage("Confirmation email sent");
			prepNew();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void deactivateEmployee()
	{
		try {
			hcEmployeeService.deactivateEmployee(hcEmployee);
			hostingCompanyEmployeesInfo();
			this.hcEmployee = new HostingCompanyEmployees();
			addWarningMessage("Employee deactivated");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void activateEmployee()
	{
		try {
			hcEmployeeService.activateEmployee(hcEmployee);
			hostingCompanyEmployeesInfo();
			this.hcEmployee = new HostingCompanyEmployees();
			addInfoMessage("Employee activated");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
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
	public List<HostingCompany> completeHostingCompany(String desc) {
		List<HostingCompany> l = null;
		try {
			l = hostingCompanyService.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Get all HostingCompanyEmployees for data table.
	 *
	 * @author TechFinium
	 * @see HostingCompanyEmployees
	 */
	public void hostingCompanyEmployeesInfo() {
		hcEmployeeDataModel = new LazyDataModel<HostingCompanyEmployees>() {
			private static final long serialVersionUID = 1L;
			private List<HostingCompanyEmployees> retorno = new ArrayList<HostingCompanyEmployees>();

			@Override
			public List<HostingCompanyEmployees> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = hcEmployeeService.allHostingCompanyEmployees(HostingCompanyEmployees.class, first, pageSize, sortField, sortOrder, filters);
					hcEmployeeDataModel.setRowCount(hcEmployeeService.count(HostingCompanyEmployees.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(HostingCompanyEmployees obj) {
				return obj.getId();
			}

			@Override
			public HostingCompanyEmployees getRowData(String rowKey) {
				for (HostingCompanyEmployees obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	public Long getMAX_EMAIL_SIZE() {
		return MAX_EMAIL_SIZE;
	}

	public void setMAX_EMAIL_SIZE(Long mAX_EMAIL_SIZE) {
		MAX_EMAIL_SIZE = mAX_EMAIL_SIZE;
	}

	/**
	 * Deletes Hosting Company Employee.
	 *
	 * @author TechFinium
	 * @see HostingCompanyEmployees
	 */
	public void deleteHostingCompanyEmployee() {
		try {
			hcEmployeeService.delete(this.hcEmployee);
			usersService.delete(this.hcEmployee.getUsers());
			hostingCompanyEmployeesInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Deletes Hosting Company Employee.
	 *
	 * @author TechFinium
	 * @see HostingCompanyEmployees
	 */
	public void addEmployeeToHostCompany() {
		try {
			//addInfoMessage("successfully submitted");

			if (hcEmployee != null && hcEmployee.getId() != null) {
				if(hcEmployee.getJobTitle().getDescription().trim().startsWith("Administrator")) {
					Long  office = user.getOffice().getId();
					System.out.println("office data is--"+office.toString());
					String [] officeArray = String.valueOf(office).split(",");
					System.out.println("officeArray data is--"+Arrays.toString(officeArray));
					System.out.println("officeArray.length:"+officeArray.length);
					for (int i = 0; i < officeArray.length; i++) {
						System.out.println("inside for loop");
						hcEmployeeService.create(hcEmployee);
					}
				}else {
					hcEmployeeService.create(hcEmployee);
				}
//				hcEmployeeService.create(hcEmployee);
				usersService.save(user);
			} else {
				usersService.create(this.user, this.hostingCompany);
			}
			addInfoMessage("update successful");
			hostingCompanyEmployeesInfo();
			this.user = new Users();
			this.hcEmployee = new HostingCompanyEmployees();
			
		} catch (ValidationException e) {
			addErrorMessage(e.getMessage());
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage());
		}
	}

	/**
	 * updates Hosting Company Employee by setting its values to user and
	 * hostingCompany.
	 *
	 * @author TechFinium
	 * @see HostingCompanyEmployees
	 */
	public void updateHCEmployee() {
		this.user = this.hcEmployee.getUsers();
		this.hostingCompany = this.hcEmployee.getHostingCompany();
	}

	/**
	 * Clears the values to user and hostingCompany.
	 *
	 * @author TechFinium
	 * @see HostingCompanyEmployees
	 */
	public void clearForm() {
		// this.hostingCompany = new HostingCompany();
		hcEmployee = new HostingCompanyEmployees();
		this.user = new Users();
	}
	
	public void selectEmployeeForPermissions(){
		try {
			if (selectedEmployee == null) {
				addErrorMessage("Unable to locate employee, contact support!");
			} else {
				userPermissions = UserPermissionsService.instance().findByUserAndCreate(selectedEmployee);
				runClientSideExecute("PF('userPermissionsDlg').show()");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateUserPermissions(){
		try {
			UserPermissionsService.instance().update(userPermissions);
			userPermissions = null;
			selectedEmployee = null;
			addInfoMessage("Action Complete");
			runClientSideExecute("PF('userPermissionsDlg').hide()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Gets the user.
	 *
	 * @return the user
	 */
	public Users getUser() {
		return user;
	}

	/**
	 * Sets the user.
	 *
	 * @param user
	 *            the new user
	 */
	public void setUser(Users user) {
		this.user = user;
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
	 * Gets the hc employee filtered list.
	 *
	 * @return the hc employee filtered list
	 */
	public List<HostingCompanyEmployees> getHcEmployeeFilteredList() {
		return hcEmployeeFilteredList;
	}

	/**
	 * Sets the hc employee filtered list.
	 *
	 * @param hcEmployeeFilteredList
	 *            the new hc employee filtered list
	 */
	public void setHcEmployeeFilteredList(List<HostingCompanyEmployees> hcEmployeeFilteredList) {
		this.hcEmployeeFilteredList = hcEmployeeFilteredList;
	}

	/**
	 * Gets the hc employee data model.
	 *
	 * @return the hc employee data model
	 */
	public LazyDataModel<HostingCompanyEmployees> getHcEmployeeDataModel() {
		return hcEmployeeDataModel;
	}

	/**
	 * Sets the hc employee data model.
	 *
	 * @param hcEmployeeDataModel
	 *            the new hc employee data model
	 */
	public void setHcEmployeeDataModel(LazyDataModel<HostingCompanyEmployees> hcEmployeeDataModel) {
		this.hcEmployeeDataModel = hcEmployeeDataModel;
	}

	/**
	 * Gets the hc employee.
	 *
	 * @return the hc employee
	 */
	public HostingCompanyEmployees getHcEmployee() {
		return hcEmployee;
	}

	/**
	 * Sets the hc employee.
	 *
	 * @param hcEmployee
	 *            the new hc employee
	 */
	public void setHcEmployee(HostingCompanyEmployees hcEmployee) {
		this.hcEmployee = hcEmployee;
	}

	public IdPassportEnum getIdPass() {
		return idPass;
	}

	public void setIdPass(IdPassportEnum idPass) {
		this.idPass = idPass;
	}

	/** Getters and Setters **/

	public Long getMAX_LAST_NAME_SIZE() {
		return MAX_LAST_NAME_SIZE;
	}

	public void setMAX_LAST_NAME_SIZE(Long mAX_LAST_NAME_SIZE) {
		MAX_LAST_NAME_SIZE = mAX_LAST_NAME_SIZE;
	}

	public Long getMAX_RSA_ID_NUMBER() {
		return MAX_RSA_ID_NUMBER;
	}

	public void setMAX_RSA_ID_NUMBER(Long mAX_RSA_ID_NUMBER) {
		MAX_RSA_ID_NUMBER = mAX_RSA_ID_NUMBER;
	}

	public Long getMAX_FIRST_NAME_SIZE() {
		return MAX_FIRST_NAME_SIZE;
	}

	public void setMAX_FIRST_NAME_SIZE(Long mAX_FIRST_NAME_SIZE) {
		MAX_FIRST_NAME_SIZE = mAX_FIRST_NAME_SIZE;
	}

	public Long getMAX_PASSPORT_NUMBER() {
		return MAX_PASSPORT_NUMBER;
	}

	public void setMAX_PASSPORT_NUMBER(Long mAX_PASSPORT_NUMBER) {
		MAX_PASSPORT_NUMBER = mAX_PASSPORT_NUMBER;
	}

	public String getTELPHONE_FORMAT() {
		return TELPHONE_FORMAT;
	}

	public void setTELPHONE_FORMAT(String tELPHONE_FORMAT) {
		TELPHONE_FORMAT = tELPHONE_FORMAT;
	}

	public String getCELLPHONE_FORMAT() {
		return CELLPHONE_FORMAT;
	}

	public void setCELLPHONE_FORMAT(String cELLPHONE_FORMAT) {
		CELLPHONE_FORMAT = cELLPHONE_FORMAT;
	}

	public Users getSelectedEmployee() {
		return selectedEmployee;
	}

	public void setSelectedEmployee(Users selectedEmployee) {
		this.selectedEmployee = selectedEmployee;
	}

	public UserPermissions getUserPermissions() {
		return userPermissions;
	}

	public void setUserPermissions(UserPermissions userPermissions) {
		this.userPermissions = userPermissions;
	}
	
	public boolean checkIfAdmin() {
		System.out.println("checkIfAdmin");
		if(hcEmployee.getJobTitle() != null && hcEmployee.getJobTitle().getDescription().trim().startsWith("Administrator")) {
			return true;
		}else {
			return false;
		}
	}

}
