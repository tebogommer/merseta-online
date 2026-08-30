package haj.com.wsp.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.bean.GrantBean;
import haj.com.entity.Blank;
import haj.com.entity.Employees;
import haj.com.entity.EmployeesTraining;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.enums.CompletedPlannedEnum;
import haj.com.entity.enums.EmployedUnEmployedEnum;
import haj.com.entity.enums.EmploymentStatusEnum;
import haj.com.entity.enums.PivotNonPivotEnum;
import haj.com.entity.enums.WspReportEnum;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.EmployeesService;
import haj.com.service.MandatoryGrantService;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantUI.
 */
@ManagedBean(name = "discretionarygrantUI")
@ViewScoped
public class DiscretionaryGrantUI extends AbstractUI {

	/** The service. */
	private EmployeesService service = new EmployeesService();

	/** The employee list. */
	private List<Employees> employeeList;

	/** The discretionary grant bean. */
	private GrantBean discretionaryGrantBean;

	private MandatoryGrant mandatoryGrant;
	private List<MandatoryGrant> mandatoryGrants;
	private MandatoryGrantService mandatoryGrantService = new MandatoryGrantService();

	/** The employees. */
	private Employees employees = null;

	/** The employees training. */
	private EmployeesTraining employeesTraining;

	private WspReportEnum wspReport;

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
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		wspReport = WspReportEnum.EMPLOYMENTDATA;
		discretionaryGrantBean = new GrantBean();
		mandatoryGrant = new MandatoryGrant(getSessionUI().getWspSession(), wspReport);
		mandatoryGrants = mandatoryGrantService.findByWSP(getSessionUI().getWspSession(), wspReport);
		employeeList = service.allEmployeesNotEmployed(getSessionUI().getWspSession());
	}

	/**
	 * Generate employees.
	 */
	public void generateEmployeesUnemployed() {
		try {
			service.createFromBean(discretionaryGrantBean, getSessionUI().getWspSession(), EmployedUnEmployedEnum.UnEmployed, EmploymentStatusEnum.UnEmployed);
			discretionaryGrantBean = new GrantBean();
			employeeList = service.allEmployeesNotEmployed(getSessionUI().getWspSession());
			addInfoMessage(getEntryLanguage("update.successful"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Generate employees.
	 */
	public void generateEmployeesEmployed() {
		try {
			mandatoryGrantService.create(mandatoryGrant);
			mandatoryGrant = new MandatoryGrant(getSessionUI().getWspSession(), wspReport);
			mandatoryGrants = mandatoryGrantService.findByWSP(getSessionUI().getWspSession(), wspReport);
			addInfoMessage(getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Employees training update.
	 */
	public void employeesTrainingUpdate() {
		try {
			service.createEMPTraining(this.employeesTraining);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			if (employeesTraining.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				prepareNewPivitol();
			} else {
				prepareNewNonPivitol();
			}
			employeeList = service.allEmployeesNotEmployed(getSessionUI().getWspSession());
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void deleteMandatory() {
		try {
			mandatoryGrantService.delete(mandatoryGrant);
			mandatoryGrant = new MandatoryGrant(getSessionUI().getWspSession(), wspReport);
			mandatoryGrants = mandatoryGrantService.findByWSP(getSessionUI().getWspSession(), wspReport);
			addInfoMessage(getEntryLanguage("row.deleted"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Prepare new pivitol.
	 */
	public void prepareNewPivitol() {
		if (employees.getPivotalTrainingPlanned() != null && !employees.getPivotalTrainingPlanned().isEmpty()) {
			employeesTraining = employees.getPivotalTrainingPlanned().get(0);
		} else {
			employeesTraining = new EmployeesTraining();
			employeesTraining.setEmployee(employees);
			employeesTraining.setPivotNonPivot(PivotNonPivotEnum.Pivotal);
			employeesTraining.setCompletedPlanned(CompletedPlannedEnum.Planned);
		}
	}

	/**
	 * Prepare new non pivitol.
	 */
	public void prepareNewNonPivitol() {
		if (employees.getNonpivotalTrainingPlanned() != null && !employees.getNonpivotalTrainingPlanned().isEmpty()) {
			employeesTraining = employees.getNonpivotalTrainingPlanned().get(0);
		} else {
			employeesTraining = new EmployeesTraining();
			employeesTraining.setEmployee(employees);
			employeesTraining.setPivotNonPivot(PivotNonPivotEnum.NonPivotal);
			employeesTraining.setCompletedPlanned(CompletedPlannedEnum.Planned);
		}
	}

	/**
	 * Gets the discretionary grant bean.
	 *
	 * @return the discretionary grant bean
	 */
	public GrantBean getDiscretionaryGrantBean() {
		return discretionaryGrantBean;
	}

	/**
	 * Sets the discretionary grant bean.
	 *
	 * @param discretionaryGrantBean
	 *            the new discretionary grant bean
	 */
	public void setDiscretionaryGrantBean(GrantBean discretionaryGrantBean) {
		this.discretionaryGrantBean = discretionaryGrantBean;
	}

	/**
	 * Gets the employee list.
	 *
	 * @return the employee list
	 */
	public List<Employees> getEmployeeList() {
		return employeeList;
	}

	/**
	 * Sets the employee list.
	 *
	 * @param employeeList
	 *            the new employee list
	 */
	public void setEmployeeList(List<Employees> employeeList) {
		this.employeeList = employeeList;
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

	public MandatoryGrant getMandatoryGrant() {
		return mandatoryGrant;
	}

	public void setMandatoryGrant(MandatoryGrant mandatoryGrant) {
		this.mandatoryGrant = mandatoryGrant;
	}

	public List<MandatoryGrant> getMandatoryGrants() {
		return mandatoryGrants;
	}

	public void setMandatoryGrants(List<MandatoryGrant> mandatoryGrants) {
		this.mandatoryGrants = mandatoryGrants;
	}

}
