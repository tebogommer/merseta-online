package haj.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.EmployeesImport;
import haj.com.entity.EmployeesImportTraining;
import haj.com.entity.Wsp;
import haj.com.entity.enums.CompletedPlannedEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.EmployeesImportService;
import haj.com.service.EmployeesService;
import haj.com.utils.CSVUtil;

// TODO: Auto-generated Javadoc
/**
 * The Class EmployeesImportUI.
 */
@ManagedBean(name = "employeesimportUI")
@ViewScoped
public class EmployeesImportUI extends AbstractUI {

	/** The service. */
	private EmployeesImportService service = new EmployeesImportService();
	private EmployeesService employeesService = new EmployeesService();

	/** The employees list. */
	private List<EmployeesImport> employeesList = null;

	/** The employeesfiltered list. */
	private List<EmployeesImport> employeesfilteredList = null;

	/** The employees. */
	private EmployeesImport employees = null;

	/** The data model. */
	private LazyDataModel<EmployeesImport> dataModel;

	/** The wsp. */
	private Wsp wsp;

	/** The company. */
	private Company company;

	/** The collapsed upload. */
	private boolean collapsedUpload;

	/** The no rows. */
	private int noRows;

	/** The employees import training. */
	private EmployeesImportTraining employeesImportTraining = null;

	/** The completed planned. */
	private CompletedPlannedEnum completedPlanned;

	/** The pivotal nonpivotal. */
	private PivotNonPivotEnum pivotalNonpivotal;
	public CSVUtil csvUtil = new CSVUtil();

	/**
	 * Inits the.
	 */
	@PostConstruct
	public void init() {
		try {
			collapsedUpload = false;
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
	 * @see EmployeesImport
	 */
	private void runInit() throws Exception {
		// this.wsp = getSessionUI().getWspSession();
		prepareNew();
		employeesInfo();

	}

	/**
	 * Get all Employees for data table.
	 *
	 * @author TechFinium
	 * @see EmployeesImport
	 */
	public void employeesInfo() {

		try {
			if (company != null) {
				this.noRows = service.count(EmployeesImport.class, new HashMap<String, Object>(), company);
			} else if (wsp != null) {
				this.noRows = service.count(EmployeesImport.class, new HashMap<String, Object>(), wsp);
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
		dataModel = new LazyDataModel<EmployeesImport>() {

			private static final long serialVersionUID = 1L;
			private List<EmployeesImport> retorno = new ArrayList<EmployeesImport>();

			@Override
			public List<EmployeesImport> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					if (company != null) {
						retorno = service.allEmployees(EmployeesImport.class, first, pageSize, sortField, sortOrder, filters, company);
						dataModel.setRowCount(service.count(EmployeesImport.class, filters, company));
					} else if (wsp != null) {
						retorno = service.allEmployees(EmployeesImport.class, first, pageSize, sortField, sortOrder, filters, wsp);
						dataModel.setRowCount(service.count(EmployeesImport.class, filters, wsp));
					} else {

					}

				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(EmployeesImport obj) {
				return obj.getId();
			}

			@Override
			public EmployeesImport getRowData(String rowKey) {
				for (EmployeesImport obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}

	/**
	 * Insert Employees into database.
	 *
	 * @author TechFinium
	 * @see EmployeesImport
	 */
	public void employeesInsert() {
		try {
			int currentCount = employeesService.countByCompany(company).intValue();
			if (company.getNumberOfEmployees() != null) if (currentCount > 0 && currentCount >= company.getNumberOfEmployees()) throw new Exception("The number of employees captured for the company is already at maximum");
			service.create(this.employees);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			importData();
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
	 * @see EmployeesImport
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

	public void clearViews() {
		this.company = null;
	}

	/**
	 * Employees update training.
	 */
	public void employeesUpdateTraining() {
		try {
			service.update(this.employeesImportTraining);
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
	 * @see EmployeesImport
	 */
	public void employeesDelete() {
		try {
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

	/**
	 * Employees training delete.
	 */
	public void employeesTrainingDelete() {
		try {
			service.delete(this.employeesImportTraining);
			prepareNew();
			employeesInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Employees .
	 *
	 * @author TechFinium
	 * @see EmployeesImport
	 */
	public void prepareNew() {
		employees = new EmployeesImport();
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
	public List<EmployeesImport> complete(String desc) {
		List<EmployeesImport> l = null;
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
	public List<EmployeesImport> getEmployeesList() {
		return employeesList;
	}

	/**
	 * Sets the employees list.
	 *
	 * @param employeesList
	 *            the new employees list
	 */
	public void setEmployeesList(List<EmployeesImport> employeesList) {
		this.employeesList = employeesList;
	}

	/**
	 * Gets the employees.
	 *
	 * @return the employees
	 */
	public EmployeesImport getEmployees() {
		return employees;
	}

	/**
	 * Sets the employees.
	 *
	 * @param employees
	 *            the new employees
	 */
	public void setEmployees(EmployeesImport employees) {
		this.employees = employees;
	}

	/**
	 * Gets the employeesfiltered list.
	 *
	 * @return the employeesfiltered list
	 */
	public List<EmployeesImport> getEmployeesfilteredList() {
		return employeesfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param employeesfilteredList
	 *            the new employeesfilteredList list
	 * @see EmployeesImport
	 */
	public void setEmployeesfilteredList(List<EmployeesImport> employeesfilteredList) {
		this.employeesfilteredList = employeesfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<EmployeesImport> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<EmployeesImport> dataModel) {
		this.dataModel = dataModel;
	}

	/**
	 * Handle file upload.
	 *
	 * @param event
	 *            the event
	 */
	@SuppressWarnings("unchecked")
	public void handleFileUpload(FileUploadEvent event) {
		try {
			List<EmployeesImport> empImports = (List<EmployeesImport>) (List<?>) csvUtil.getObjects(EmployeesImport.class, event.getFile().getInputstream(), ",");
			if (company.getNumberOfEmployees() != null && empImports.size() > company.getNumberOfEmployees()) throw new Exception("The number of employees in the CSV file is greater than the total number of company employees");

			if (company.getNumberOfEmployees() != null && empImports.size() < company.getNumberOfEmployees()) throw new Exception("The number of employees in the CSV file is less than the total number of company employees");

			int currentCount = employeesService.countByCompany(company).intValue();
			currentCount += empImports.size();
			if (company.getNumberOfEmployees() == null) {
				throw new Exception("Please ensure the total number of employees is captured for the company");
			}
			if (currentCount > 0 && currentCount > company.getNumberOfEmployees()) {
				throw new Exception("The number of employees captured for the company is already at maximum");
			}

			service.save(empImports, wsp, company);
			collapsedUpload = true;
			importData();
			employeesInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		} finally {
			csvUtil = new CSVUtil();
		}

	}

	/**
	 * Handle traing file upload.
	 *
	 * @param event
	 *            the event
	 */
	@SuppressWarnings("unchecked")
	public void handleTraingFileUpload(FileUploadEvent event) {
		try {
			List<EmployeesImportTraining> empTrainingImports = (List<EmployeesImportTraining>) (List<?>) csvUtil.getObjects(EmployeesImportTraining.class, event.getFile().getInputstream(), ";");
			service.save(empTrainingImports, wsp, this.completedPlanned, this.pivotalNonpivotal);
			employeesInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		} finally {
			csvUtil = new CSVUtil();
		}

	}

	/**
	 * Validate.
	 */
	public void validate() {
		try {
			service.validate(company);
			employeesInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Import data.
	 */
	public void importData() {
		try {
			if (wsp != null) {
				service.importData(wsp);
			} else if (company != null) {
				service.importData(company);
			}
			employeesInfo();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete all.
	 */
	public void deleteAll() {
		try {
			service.deleteAll(company);
			employeesInfo();
			// super.ajaxRedirect("/pages/externalparty/wsp/reviewapplication.jsf?idW=" +
			// this.wsp.getWspGuid());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void deleteAllEmployees() {
		try {
			service.deleteAllEmployees(company);
			employeesInfo();
			addWarningMessage("Action Complete");
			// super.ajaxRedirect("/pages/externalparty/wsp/reviewapplication.jsf?idW=" +
			// this.wsp.getWspGuid());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Checks if is collapsed upload.
	 *
	 * @return true, if is collapsed upload
	 */
	public boolean isCollapsedUpload() {
		return collapsedUpload;
	}

	/**
	 * Sets the collapsed upload.
	 *
	 * @param collapsedUpload
	 *            the new collapsed upload
	 */
	public void setCollapsedUpload(boolean collapsedUpload) {
		this.collapsedUpload = collapsedUpload;
	}

	/**
	 * Gets the no rows.
	 *
	 * @return the no rows
	 */
	public int getNoRows() {
		return noRows;
	}

	/**
	 * Sets the no rows.
	 *
	 * @param noRows
	 *            the new no rows
	 */
	public void setNoRows(int noRows) {
		this.noRows = noRows;
	}

	/**
	 * Gets the completed planned.
	 *
	 * @return the completed planned
	 */
	public CompletedPlannedEnum getCompletedPlanned() {
		return completedPlanned;
	}

	/**
	 * Sets the completed planned.
	 *
	 * @param completedPlanned
	 *            the new completed planned
	 */
	public void setCompletedPlanned(CompletedPlannedEnum completedPlanned) {
		this.completedPlanned = completedPlanned;
	}

	/**
	 * Gets the pivotal nonpivotal.
	 *
	 * @return the pivotal nonpivotal
	 */
	public PivotNonPivotEnum getPivotalNonpivotal() {
		return pivotalNonpivotal;
	}

	/**
	 * Sets the pivotal nonpivotal.
	 *
	 * @param pivotalNonpivotal
	 *            the new pivotal nonpivotal
	 */
	public void setPivotalNonpivotal(PivotNonPivotEnum pivotalNonpivotal) {
		this.pivotalNonpivotal = pivotalNonpivotal;
	}

	/**
	 * Prep upload.
	 */
	public void prepUpload() {
		this.completedPlanned = null;
		this.pivotalNonpivotal = null;
	}

	/**
	 * Gets the employees import training.
	 *
	 * @return the employees import training
	 */
	public EmployeesImportTraining getEmployeesImportTraining() {
		return employeesImportTraining;
	}

	/**
	 * Sets the employees import training.
	 *
	 * @param employeesImportTraining
	 *            the new employees import training
	 */
	public void setEmployeesImportTraining(EmployeesImportTraining employeesImportTraining) {
		this.employeesImportTraining = employeesImportTraining;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

}
