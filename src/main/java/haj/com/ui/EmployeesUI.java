package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.AjaxBehaviorEvent;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.Employees;
import haj.com.entity.EmployeesTraining;
import haj.com.entity.Sites;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.EmployeesHistoryService;
import haj.com.service.EmployeesService;
import haj.com.service.MailDef;
import haj.com.service.SitesService;
import haj.com.service.TasksService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeesUI.
 */
@ManagedBean(name = "employeesUI")
@ViewScoped
public class EmployeesUI extends AbstractUI {

	/** The service. */
	private EmployeesService service = new EmployeesService();

	/** The employees list. */
	private List<Employees> employeesList = null;

	/** The employeesfiltered list. */
	private List<Employees> employeesfilteredList = null;

	/** The employees. */
	private Employees employees = null;

	private Company company;

	/** The data model. */
	private LazyDataModel<Employees> dataModel;

	/** The employees training. */
	private EmployeesTraining employeesTraining;

	/** maximum size of first name */
	private Long MAX_FIRST_NAME_SIZE = HAJConstants.MAX_FIRST_NAME_SIZE;

	/** maximum size of last name */
	private Long MAX_LAST_NAME_SIZE = HAJConstants.MAX_LAST_NAME_SIZE;

	/** maximum size of RSA ID number */
	private Long MAX_RSA_ID_NUMBER = HAJConstants.MAX_RSA_ID_NUMBER;

	/** maximum size of passport number */
	private Long MAX_PASSPORT_NUMBER = HAJConstants.MAX_PASSPORT_NUMBER;

	/** maximum size of site number */
	private Long MAX_SITE_NUMBER = HAJConstants.MAX_SITE_NUMBER;

	/** The Constant allow only number format. */
	public String passportNumberFormat = HAJConstants.passportNumberFormat;

	/** Site Service */
	SitesService sitesService = new SitesService();

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
	 * Initialize method to get all Employees and prepare a for a create of a new
	 * Employees.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Employees
	 */
	private void runInit() throws Exception {

		if (getSessionUI().getWspSession() != null) {
			this.company = getSessionUI().getWspSession().getCompany();

		}

		prepareNew();
		employeesInfo();

	}

	/**
	 * Get all Employees for data table.
	 *
	 * @author TechFinium
	 * @see Employees
	 */
	public void employeesInfo() {

		dataModel = new LazyDataModel<Employees>() {

			private static final long serialVersionUID = 1L;
			private List<Employees> retorno = new ArrayList<Employees>();

			@Override
			public List<Employees> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allEmployees(Employees.class, first, pageSize, sortField, sortOrder, filters, company);
					dataModel.setRowCount(service.count(Employees.class, filters, company));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Employees obj) {
				return obj.getId();
			}

			@Override
			public Employees getRowData(String rowKey) {
				for (Employees obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
		prepareNew();

	}

	public void clearViews() {
		this.company = null;
	}

	/**
	 * Insert Employees into database.
	 *
	 * @author TechFinium
	 * @see Employees
	 */
	public void employeesInsert() {
		try {
			if (employees.getId() == null) 
				if (company.getNumberOfEmployees() != null) 
					if (service.countByCompany(company) != null && service.countByCompany(company) > 0 && service.countByCompany(company) >= company.getNumberOfEmployees()) throw new Exception("The number of employees captured for the company is already at maximum");
			
			employees.setEmployedUnEmployed(EmployedUnEmployedEnum.Employed);
			employees.setCompany(company);
			service.create(this.employees);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			employeesInfo();
		}catch (javax.validation.ConstraintViolationException e) {
			extractValidationErrors(e);
			System.out.println(getValidationErrors());
			addErrorMessage(getValidationErrors());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Insert Employees into database.
	 *
	 * @author TechFinium
	 * @see Employees
	 */
	public void employeesInsertAndCreateTask() {
		try {
			employees.setEmployedUnEmployed(EmployedUnEmployedEnum.Employed);
			employees.setCompany(company);
			employees.setApprovalStatus(ApprovalEnum.PendingApproval);
			service.createEmpTask(this.employees, getSessionUI().getActiveUser());
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			employeesInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Employees in database.
	 *
	 * @author TechFinium
	 * @see Employees
	 */
	public void employeesUpdate() {
		try {
			service.update(this.employees);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			employeesInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Employees from database.
	 *
	 * @author TechFinium
	 * @see Employees
	 */
	public void employeesDelete() {
		try {

			EmployeesHistoryService.instance().deleteByForID(this.employees.getId());
			service.delete(this.employees);
			prepareNew();
			employeesInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void createDeleteTask() {
		try {
			this.employees.setApprovalStatus(ApprovalEnum.PendingApproval);
			String desc = "Employee has been deleted, please approve";
			service.update(this.employees);
			TasksService.instance().findFirstInProcessAndCreateTask(desc, getSessionUI().getActiveUser(), this.employees.getId(), this.employees.getClass().getName(), true, ConfigDocProcessEnum.DELETE_EMPLOYEE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
			addInfoMessage("Request has been sent for approval");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}

	}

	/**
	 * Create new instance of Employees.
	 *
	 * @author TechFinium
	 * @see Employees
	 */
	public void prepareNew() {

		employees = new Employees();
		employees.setWsp(getSessionUI().getWspSession());
		employees.setCompany(company);
		employees.setEmployedUnEmployed(EmployedUnEmployedEnum.Employed);
		employeesTraining = new EmployeesTraining();
		employeesTraining.setEmployee(employees);
	}

	/**
	 * Employees training update.
	 */
	public void employeesTrainingUpdate() {
		try {
			service.createEMPTraining(this.employeesTraining);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			employeesInfo();
			this.employees = service.findByKey(employees.getId());
			if (employeesTraining.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				prepareNewPivitol();
			} else {
				prepareNewNonPivitol();
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Prepare new pivitol.
	 */
	public void prepareNewPivitol() {
		employeesTraining = new EmployeesTraining();
		employeesTraining.setEmployee(employees);
		employeesTraining.setPivotNonPivot(PivotNonPivotEnum.Pivotal);
	}

	public ArrayList<Sites> completeSite(String name) {
		ArrayList<Sites> sites = new ArrayList<>();

		try {
			sites = (ArrayList<Sites>) sitesService.findByNameAndCompany(name, company);

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return sites;

	}

	/**
	 * Prepare new non pivitol.
	 */
	public void prepareNewNonPivitol() {
		employeesTraining = new EmployeesTraining();
		employeesTraining.setEmployee(employees);
		employeesTraining.setPivotNonPivot(PivotNonPivotEnum.NonPivotal);
	}

	/*
	 * public List<SelectItem> getEmployeesDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * employeesInfo(); for (Employees ug : employeesList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Employees> complete(String desc) {
		List<Employees> l = null;
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
	 * Gets the employees list.
	 *
	 * @return the employees list
	 */
	public List<Employees> getEmployeesList() {
		return employeesList;
	}

	/**
	 * Sets the employees list.
	 *
	 * @param employeesList
	 *            the new employees list
	 */
	public void setEmployeesList(List<Employees> employeesList) {
		this.employeesList = employeesList;
	}

	/**
	 * Gets the employees.
	 *
	 * @return the employees
	 */
	public Employees getEmployees() {
		return employees;
	}

	/**
	 * Sets the employees.
	 *
	 * @param employees
	 *            the new employees
	 */
	public void setEmployees(Employees employees) {
		this.employees = employees;
	}

	/**
	 * Gets the employeesfiltered list.
	 *
	 * @return the employeesfiltered list
	 */
	public List<Employees> getEmployeesfilteredList() {
		return employeesfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param employeesfilteredList
	 *            the new employeesfilteredList list
	 * @see Employees
	 */
	public void setEmployeesfilteredList(List<Employees> employeesfilteredList) {
		this.employeesfilteredList = employeesfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<Employees> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<Employees> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * Gets the employees training.
	 *
	 * @return the employees training
	 */
	public EmployeesTraining getEmployeesTraining() {
		return employeesTraining;
	}

	/**
	 * Sets the employees training.
	 *
	 * @param employeesTraining
	 *            the new employees training
	 */
	public void setEmployeesTraining(EmployeesTraining employeesTraining) {
		this.employeesTraining = employeesTraining;
	}

	/**
	 * Employees delete training.
	 */
	public void employeesDeleteTraining() {
		try {
			this.employees = this.employeesTraining.getEmployee();
			service.delete(this.employeesTraining);

			service.resolveTraining(this.employees);
			employeesInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Generate date of birth, gender and set nationality For South African user
	 */
	public void setDOBGenderNationality(AjaxBehaviorEvent even) {
		try {
			GenericUtility.calcIDData(employees);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Set municipality when selecting site
	 */
	public void populateMunicipality() {
		try {
			AddressService addressService = new AddressService();
			Address address = addressService.findByKey(employees.getSite().getRegisteredAddress().getId());
			employees.setMunicipality(address.getMunicipality());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void clearEmpInfo() {
		try {
			employees.setDateOfBirth(null);
			employees.setGender(null);
			employees.setNationality(null);
			employees.setRsaIDNumber(null);
			employees.setPassportNumber(null);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Long getMAX_FIRST_NAME_SIZE() {
		return MAX_FIRST_NAME_SIZE;
	}

	public void setMAX_FIRST_NAME_SIZE(Long mAX_FIRST_NAME_SIZE) {
		MAX_FIRST_NAME_SIZE = mAX_FIRST_NAME_SIZE;
	}

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

	public Long getMAX_SITE_NUMBER() {
		return MAX_SITE_NUMBER;
	}

	public void setMAX_SITE_NUMBER(Long mAX_SITE_NUMBER) {
		MAX_SITE_NUMBER = mAX_SITE_NUMBER;
	}

	public String getPassportNumberFormat() {
		return passportNumberFormat;
	}

	public void setPassportNumberFormat(String passportNumberFormat) {
		this.passportNumberFormat = passportNumberFormat;
	}

	public Long getMAX_PASSPORT_NUMBER() {
		return MAX_PASSPORT_NUMBER;
	}

	public void setMAX_PASSPORT_NUMBER(Long mAX_PASSPORT_NUMBER) {
		MAX_PASSPORT_NUMBER = mAX_PASSPORT_NUMBER;
	}

}
