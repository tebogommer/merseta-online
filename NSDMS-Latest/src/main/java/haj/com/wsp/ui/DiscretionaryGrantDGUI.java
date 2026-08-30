package haj.com.wsp.ui;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import haj.com.bean.GrantBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.*;
import haj.com.entity.enums.*;
import haj.com.entity.lookup.InterventionType;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DgAllocationService;
import haj.com.service.EmployeesService;
import haj.com.service.MandatoryGrantService;
import haj.com.service.ProjectImplementationPlanService;
import haj.com.service.YesNoLookupService;
import haj.com.service.lookup.FundingService;
import haj.com.service.lookup.InterventionTypeService;
import haj.com.utils.GenericUtility;
import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

// TODO: Auto-generated Javadoc
/**
 * The Class DiscretionaryGrantUI.
 */
@ManagedBean(name = "discretionarygrantdgUI")
@ViewScoped
public class DiscretionaryGrantDGUI extends AbstractUI {

	private EmployeesService                 service                           = new EmployeesService();
	private FundingService                   fundingService                    = new FundingService();
	private MandatoryGrantService            mandatoryGrantService             = new MandatoryGrantService();
	private ProjectImplementationPlanService projectImplementationPlanService  = new ProjectImplementationPlanService();
	private InterventionTypeService          interventionTypeService           = new InterventionTypeService();
	private Employees                        employees                         = null;
	private List<Employees>                  employeeList;
	private GrantBean                        discretionaryGrantBean;
	private MandatoryGrant                   mandatoryGrant;
	private List<MandatoryGrant>             mandatoryGrants;
	private EmployeesTraining                employeesTraining;
	private WspReportEnum                    wspReport;
	private boolean                          skillsSet;
	private boolean                          skillsProgram;
	private boolean                          shortCreditBearing;
	private boolean                          showShortProgrammeUnemployedPanel = false;
	private boolean                          showLearnership                   = false;
	private boolean                          editLearners;
	private Date                             endDatLimit;
	private Date                             startDateLimit;
	private Date                             startDateEndLimit;
	private Integer                          maxLearners;
	private Date                             minDate;
	private Date                             maxDate;
	private Wsp wsp;

	private final static int DEFAULT_NUMBER_OF_CREDITS = 80;
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
	 * @author TechFinium
	 * @throws Exception
	 * the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		wspReport              = WspReportEnum.EMPLOYMENTDATA;
		discretionaryGrantBean = new GrantBean();
		mandatoryGrant         = new MandatoryGrant(getSessionUI().getWspSession(), wspReport);
		mandatoryGrant.setFunding(fundingService.findByKey(HAJConstants.DISC_FUNDING_ID));
		grantsInfo();
		minDate = mandatoryGrant.getWsp().getDgYear().getStartDate();
		maxDate = mandatoryGrant.getWsp().getDgYear().getEndDate();
		this.wsp = mandatoryGrant.getWsp();

		employeeList = service.allEmployeesNotEmployed(getSessionUI().getWspSession());
		checkDateLimits();
	}

	public void grantsInfo() {
		try {
			mandatoryGrants = mandatoryGrantService.findByWSP(getSessionUI().getWspSession(), wspReport);
			mandatoryGrants = mandatoryGrantService.resolveWorkplace(mandatoryGrants);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Generate employees.
	 */
	public void generateEmployeesUnemployed() {
		try {
			service.createFromBean(discretionaryGrantBean, getSessionUI().getWspSession(), EmployedUnEmployedEnum.UnEmployed, EmploymentStatusEnum.UnEmployed);
			discretionaryGrantBean = new GrantBean();
			employeeList           = service.allEmployeesNotEmployed(getSessionUI().getWspSession());
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
			// Long total = mandatoryGrant.getAfricanFemale() + mandatoryGrant.getAfricanMale() + mandatoryGrant.getColouredFemale() + mandatoryGrant.getColouredMale() + mandatoryGrant.getWhiteFemale() + mandatoryGrant.getWhiteMale();
			// if (total > mandatoryGrant.getAmount()) {
			// throw new Exception("Number of males and females gender cannot exceed total number of learners");
			// }
			mandatoryGrantService.create(mandatoryGrant);
			// projectImplementationPlanService.generateImpementationPlan(mandatoryGrant);
			mandatoryGrant = new MandatoryGrant(getSessionUI().getWspSession(), wspReport);
			mandatoryGrant.setFunding(fundingService.findByKey(HAJConstants.DISC_FUNDING_ID));
			mandatoryGrants = mandatoryGrantService.findByWSP(getSessionUI().getWspSession(), wspReport);
			super.runClientSideExecute("updatePIP()");
			// addInfoMessage(getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void calcEmploymentData() {
		if (mandatoryGrant.getEmploymentStatus() == EmploymentStatusEnum.Employed) {
			maxLearners = mandatoryGrant.getWsp().getCompany().getNumberOfEmployees();
		}
		editLearners = true;
	}

	public void applySaqaData() {
		try {
			if (SKILLS_PROGRAM_LIST.contains(mandatoryGrant.getInterventionType().getId())) {
				mandatoryGrantService.applySkillsProgram(mandatoryGrant);
			} else if (SKILLS_SET_LIST.contains(mandatoryGrant.getInterventionType().getId())) {
				mandatoryGrantService.applySkillsSet(mandatoryGrant);
			} else {
				mandatoryGrantService.applySaqaData(mandatoryGrant);
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
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
			// projectImplementationPlanService.deleteImpementationPlan(mandatoryGrant);
			mandatoryGrantService.delete(mandatoryGrant);
			mandatoryGrant = new MandatoryGrant(getSessionUI().getWspSession(), wspReport);
			mandatoryGrant.setFunding(fundingService.findByKey(HAJConstants.DISC_FUNDING_ID));
			mandatoryGrants = mandatoryGrantService.findByWSP(getSessionUI().getWspSession(), wspReport);
			addInfoMessage(getEntryLanguage("row.deleted"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void clearQualificationAligned() {
		// if (mandatoryGrant.getQualificationAligned().getQualificationAligned().getId() == HAJConstants.NO_ID) {
		// mandatoryGrant.getQualificationAligned().setImperativeAligned(null);
		// mandatoryGrant.getQualificationAligned().setKeyImperatives(null);
		// }

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

	public void applyInterventionData() {
		try {
			if (mandatoryGrant.getInterventionType() != null) {
				if (SKILLS_PROGRAM_LIST.contains(mandatoryGrant.getInterventionType().getId())) {
					this.skillsProgram      = true;
					this.skillsSet          = false;
					this.shortCreditBearing = false;
					mandatoryGrant.setSkillsSet(null);
					mandatoryGrant.setUnitStandard(null);
					mandatoryGrant.setAmount(0);
					mandatoryGrant.setEstimatedCost(0d);
				} else if (SKILLS_SET_LIST.contains(mandatoryGrant.getInterventionType().getId())) {
					this.skillsProgram      = false;
					this.skillsSet          = true;
					this.shortCreditBearing = false;
					mandatoryGrant.setSkillsProgram(null);
					mandatoryGrant.setUnitStandard(null);
					mandatoryGrant.setAmount(0);
					mandatoryGrant.setEstimatedCost(0d);
				} else if (mandatoryGrant.getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE) {
					mandatoryGrant.setSkillsProgram(null);
					mandatoryGrant.setSkillsSet(null);
					this.skillsProgram      = false;
					this.skillsSet          = false;
					this.shortCreditBearing = true;
					mandatoryGrant.setAmount(0);
					mandatoryGrant.setEstimatedCost(0d);
				} else {
					mandatoryGrant.setSkillsProgram(null);
					mandatoryGrant.setSkillsSet(null);
					mandatoryGrant.setUnitStandard(null);
					this.skillsProgram      = false;
					this.skillsSet          = false;
					this.shortCreditBearing = false;
					mandatoryGrant.setAmount(0);
					mandatoryGrant.setEstimatedCost(0d);
				}
				mandatoryGrantService.applyInterventionData(mandatoryGrant);
				checkDateLimits();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the discretionary grant bean.
	 * @return the discretionary grant bean
	 */
	public GrantBean getDiscretionaryGrantBean() {
		return discretionaryGrantBean;
	}

	public void checkDateLimits() {
		try {

			if (GenericUtility.checkBeforeApril(getNow())) {
				startDateLimit    = GenericUtility.startDateOfCurrentYear();
				endDatLimit       = GenericUtility.endDateOfCurrentYear();
				startDateEndLimit = GenericUtility.endDateOfCurrentYear();
			} else {
				startDateLimit    = GenericUtility.addYearsToDate(GenericUtility.startDateOfCurrentYear(), 1);
				endDatLimit       = GenericUtility.addYearsToDate(GenericUtility.endDateOfCurrentYear(), 1);
				startDateEndLimit = GenericUtility.addYearsToDate(GenericUtility.endDateOfCurrentYear(), 1);
			}

			if (mandatoryGrant.getFunding() != null && mandatoryGrant.getFunding().getId() == HAJConstants.DISC_FUNDING_ID) {
				endDatLimit = null;
			}

			// if (mandatoryGrant.getFunding() != null &&
			// mandatoryGrant.getFunding().getId() != HAJConstants.SP_FUNDING_ID) {
			// mandatoryGrant.setPivotNonPivot(null);
			// mandatoryGrant.setNqfAligned(null);
			// }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void applyNQFData() {
		mandatoryGrant.setInterventionLevel(mandatoryGrant.getNqfLevels().getInterventionLevel());
	}

	public void genNQFAligned() {
		try {
			if (mandatoryGrant.getPivotNonPivot() == PivotNonPivotEnum.Pivotal) {
				mandatoryGrant.setNqfAligned(YesNoLookupService.instance().findByKey(HAJConstants.YES_ID));
			} else {
				mandatoryGrant.setNqfAligned(YesNoLookupService.instance().findByKey(HAJConstants.NO_ID));
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void calcAllocationAmount()  {
		Integer employedLeaners = 1;
		Double costPerCredit =null;
		if (mandatoryGrant.getSkillsProgram()!= null) {

			if(mandatoryGrant.getSkillsProgram().getCredits() == null){
				addErrorMessage(String.format("Failed to calculate the estimated cost [ Skills Program has no associated credits ] "));
			}

			try{
				costPerCredit = NSDMSConfiguration.getDouble("dg.skillsprogram.costpercredit");
			}catch (Exception e){
				addErrorMessage(String.format("Failed to calculate the estimated cost [ Cost per credit not configured ] "));
			}

			employedLeaners = mandatoryGrant.getAmount() == null ? 1 : mandatoryGrant.getAmount();
			int numberOfCredits = DEFAULT_NUMBER_OF_CREDITS;
			try{
				numberOfCredits=(mandatoryGrant.getSkillsProgram().getCredits() == null || mandatoryGrant.getSkillsProgram().getCredits()>DEFAULT_NUMBER_OF_CREDITS )?DEFAULT_NUMBER_OF_CREDITS:mandatoryGrant.getSkillsProgram().getCredits();
			}catch(Exception e){
				numberOfCredits = DEFAULT_NUMBER_OF_CREDITS;
			}

			mandatoryGrant.setEstimatedCost(costPerCredit * numberOfCredits * employedLeaners);
		}else if (mandatoryGrant.getInterventionType() != null) {
			employedLeaners = mandatoryGrant.getAmount() == null ? 1 : mandatoryGrant.getAmount();
			mandatoryGrant.setEstimatedCost(mandatoryGrant.getInterventionType().getBasicGrantAmount() * (employedLeaners));
		}
	}

	public void checkInterventionType() {
		InterventionType shortProgrammeUnemployed = new InterventionType();
		InterventionType shortProgrammeEmployed   = new InterventionType();
		try {
			shortProgrammeUnemployed = interventionTypeService.findByKey(93);
			shortProgrammeEmployed   = interventionTypeService.findByKey(92);
			if (this.mandatoryGrant.getInterventionType().getId().intValue() == shortProgrammeUnemployed.getId().intValue() || this.mandatoryGrant.getInterventionType().getId().intValue() == shortProgrammeEmployed.getId().intValue()) {
				showShortProgrammeUnemployedPanel = true;
			} else {
				showShortProgrammeUnemployedPanel = false;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void downloadAllocationLetter() {
		try {
			DgAllocationService dgAllocationService = new DgAllocationService();
			DgAllocation        allocation          = dgAllocationService.findByKey(mandatoryGrant.getId());
			if (allocation != null) {
				// dgAllocationService.downloadAllocationLetter(allocation);
			} else {
				addInfoMessage("Erro with the allocation selected");
			}
		} catch (Exception e1) {
			e1.printStackTrace();
			addErrorMessage(e1.getMessage());
		}
	}

	/**
	 * Sets the discretionary grant bean.
	 * @param discretionaryGrantBean
	 * the new discretionary grant bean
	 */
	public void setDiscretionaryGrantBean(GrantBean discretionaryGrantBean) {
		this.discretionaryGrantBean = discretionaryGrantBean;
	}

	/**
	 * Gets the employee list.
	 * @return the employee list
	 */
	public List<Employees> getEmployeeList() {
		return employeeList;
	}

	/**
	 * Sets the employee list.
	 * @param employeeList
	 * the new employee list
	 */
	public void setEmployeeList(List<Employees> employeeList) {
		this.employeeList = employeeList;
	}

	/**
	 * Gets the employees.
	 * @return the employees
	 */
	public Employees getEmployees() {
		return employees;
	}

	/**
	 * Sets the employees.
	 * @param employees
	 * the new employees
	 */
	public void setEmployees(Employees employees) {
		this.employees = employees;
	}

	/**
	 * Gets the employees training.
	 * @return the employees training
	 */
	public EmployeesTraining getEmployeesTraining() {
		return employeesTraining;
	}

	/**
	 * Sets the employees training.
	 * @param employeesTraining
	 * the new employees training
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

	public boolean isSkillsSet() {
		return skillsSet;
	}

	public void setSkillsSet(boolean skillsSet) {
		this.skillsSet = skillsSet;
	}

	public boolean isSkillsProgram() {
		return skillsProgram;
	}

	public void setSkillsProgram(boolean skillsProgram) {
		this.skillsProgram = skillsProgram;
	}

	public boolean isShortCreditBearing() {
		return shortCreditBearing;
	}

	public void setShortCreditBearing(boolean shortCreditBearing) {
		this.shortCreditBearing = shortCreditBearing;
	}

	public Date getEndDatLimit() {
		return endDatLimit;
	}

	public void setEndDatLimit(Date endDatLimit) {
		this.endDatLimit = endDatLimit;
	}

	public Date getStartDateLimit() {
		return startDateLimit;
	}

	public void setStartDateLimit(Date startDateLimit) {
		this.startDateLimit = startDateLimit;
	}

	public Date getStartDateEndLimit() {
		return startDateEndLimit;
	}

	public void setStartDateEndLimit(Date startDateEndLimit) {
		this.startDateEndLimit = startDateEndLimit;
	}

	public Integer getMaxLearners() {
		return maxLearners;
	}

	public void setMaxLearners(Integer maxLearners) {
		this.maxLearners = maxLearners;
	}

	public boolean isEditLearners() {
		return editLearners;
	}

	public void setEditLearners(boolean editLearners) {
		this.editLearners = editLearners;
	}

	public Date getMinDate() {
		return minDate;
	}

	public void setMinDate(Date minDate) {
		this.minDate = minDate;
	}

	public Date getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(Date maxDate) {
		this.maxDate = maxDate;
	}

	public boolean isShowShortProgrammeUnemployedPanel() {
		return showShortProgrammeUnemployedPanel;
	}

	public void setShowShortProgrammeUnemployedPanel(boolean showShortProgrammeUnemployedPanel) {
		this.showShortProgrammeUnemployedPanel = showShortProgrammeUnemployedPanel;
	}

	public Wsp getWsp() {
		return mandatoryGrant.getWsp();
	}

	public void setWsp(Wsp wsp) {
		this.wsp = mandatoryGrant.getWsp();
	}

	public WspReportEnum getWspReport() {
		return wspReport;
	}

	public void setWspReport(WspReportEnum wspReport) {
		this.wspReport = wspReport;
	}

	public boolean isShowLearnership() {
		return showLearnership;
	}

	public void setShowLearnership(boolean showLearnership) {
		this.showLearnership = showLearnership;
	}
}
