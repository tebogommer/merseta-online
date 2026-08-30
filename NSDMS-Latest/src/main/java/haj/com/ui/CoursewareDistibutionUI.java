package haj.com.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Company;
import haj.com.entity.CoursewareDistibution;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.Modules;
import haj.com.entity.lookup.ModulesUnitStandards;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyUsersService;
import haj.com.service.CoursewareDistibutionService;
import haj.com.service.TasksService;
import haj.com.service.lookup.ModulesService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "coursewaredistibutionUI")
@ViewScoped
public class CoursewareDistibutionUI extends AbstractUI {
	
	private CoursewareDistibutionService service = new CoursewareDistibutionService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();
	private ModulesService modulesService = new ModulesService();
	
	private List<CoursewareDistibution> coursewaredistibutionList = null;
	private List<CoursewareDistibution> coursewaredistibutionfilteredList = null;
	private List<Modules> modules;	
	private List<Company> companies = null;
	
	private ArrayList<RejectReasons> selectedRejectReason=new ArrayList<>();
	
	private LazyDataModel<CoursewareDistibution> coursewareDistibutionDataModel;
	private LazyDataModel<CoursewareDistibution> rejectedcoursewareDistibutionDataModel;
	private LazyDataModel<CoursewareDistibution> rejectedcoursewareDistibutionReport;
	
	private CoursewareDistibution coursewaredistibution = null;
	
	private Company selectedCompany;	
	private Modules selectedModule;	
	private int numberOfCourseware;
	private int numberOfRejectedCourseware;
	
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
	 * Initialize method to get all CoursewareDistibution and prepare a for a create
	 * of a new CoursewareDistibution
	 * 
	 * @author TechFinium
	 * @see CoursewareDistibution
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		allRejectedcoursewareDistibutionReport();
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.COURSEWARE_DISTRIBUTION) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			coursewaredistibution = service.findByKey(getSessionUI().getTask().getTargetKey());
			modulesService.resolveCoursewareData(coursewaredistibution);
		} else if (getSessionUI().isExternalParty()) {
			prepareNew();
			coursewaredistibutionInfo();
			
		} else {
			if(selectedCompany != null)
			allApproveAndNotAppovedCoursewareApplications();
		}
	}

	/**
	 * Get all CoursewareDistibution for data table
	 * 
	 * @author TechFinium
	 * @see CoursewareDistibution
	 */
	public void coursewaredistibutionInfo() {
		try {
			companies = companyUsersService.findByUserType(getSessionUI().getActiveUser(), ConfigDocProcessEnum.TP);
		} catch (Exception e) {
			logger.fatal(e.getMessage(), e);
		}
	}

	public void findUnitStandardsAndQualifications() {
		try {
			if(selectedCompany != null)
			{
				allModulesByCompanyUnitStandard();
				allApproveAndNotAppovedCoursewareApplications();
				allRejectedCoursewareDistibutionApplications();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void allModulesByCompanyUnitStandard()
	{
		try {
			modules = modulesService.allModulesByCompanyUnitStandard(selectedCompany);		
		} catch (Exception e) {
			e.printStackTrace();
		}
	}		

	private void allRejectedcoursewareDistibutionReport() {
				rejectedcoursewareDistibutionReport = new LazyDataModel<CoursewareDistibution>() {
			private static final long serialVersionUID = 1L;
			private List<CoursewareDistibution> retorno = new ArrayList<CoursewareDistibution>();
			@Override
			public List<CoursewareDistibution> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allCoursewareDistibutionForReport(CoursewareDistibution.class, first, pageSize, sortField, sortOrder, filters);
					modulesService.resolveEverythingForCourseware(retorno);	
					rejectedcoursewareDistibutionReport.setRowCount(service.countAll(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(CoursewareDistibution obj) {
				return obj.getId();
			}
			@Override
			public CoursewareDistibution getRowData(String rowKey) {
				for (CoursewareDistibution obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	private void allApproveAndNotAppovedCoursewareApplications() {
		try {
			Map<String, Object> filters = new HashMap<String, Object>();
			filters.put("companyID", selectedCompany.getId());
			filters.put("status", ApprovalEnum.Rejected);
			numberOfCourseware = service.countCompany(filters);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		coursewareDistibutionDataModel = new LazyDataModel<CoursewareDistibution>() {
			private static final long serialVersionUID = 1L;
			private List<CoursewareDistibution> retorno = new ArrayList<CoursewareDistibution>();
			@Override
			public List<CoursewareDistibution> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allCoursewareDistibutionAprvedNotApproved(CoursewareDistibution.class, first, pageSize, sortField, sortOrder, filters, selectedCompany);
					modulesService.resolveEverythingForCourseware(retorno);	
					coursewareDistibutionDataModel.setRowCount(service.countCompany(filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(CoursewareDistibution obj) {
				return obj.getId();
			}
			@Override
			public CoursewareDistibution getRowData(String rowKey) {
				for (CoursewareDistibution obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	private void allRejectedCoursewareDistibutionApplications(){
		try {
			Map<String, Object> filters = new HashMap<String, Object>();
			filters.put("companyID", selectedCompany.getId());
			filters.put("status", ApprovalEnum.Rejected);
			numberOfRejectedCourseware = service.countCompanyReject(filters);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		rejectedcoursewareDistibutionDataModel = new LazyDataModel<CoursewareDistibution>() {
			private static final long serialVersionUID = 1L;
			private List<CoursewareDistibution> retorno = new ArrayList<CoursewareDistibution>();
			@Override
			public List<CoursewareDistibution> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					retorno = service.allCompanyByStatus(CoursewareDistibution.class, first, pageSize, sortField, sortOrder, filters, selectedCompany, ApprovalEnum.Rejected);
					modulesService.resolveEverythingForCourseware(retorno);					
					rejectedcoursewareDistibutionDataModel.setRowCount(service.countCompanyReject(filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return retorno;
			}
			@Override
			public Object getRowKey(CoursewareDistibution obj) {
				return obj.getId();
			}
			@Override
			public CoursewareDistibution getRowData(String rowKey) {
				for (CoursewareDistibution obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}

	public void finalRejectRegistration() {
		try {
			if (selectedRejectReason.size() == 0) 
			{
				throw new Exception("Please select a reject reason");				
			}
			else
			{
				service.finalRejectRegistration(coursewaredistibution, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void finalApproveRegistration() {
		try {
			service.finalApproveRegistration(coursewaredistibution, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.COURSEWARE_DISTRIBUTION);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Insert CoursewareDistibution into database
	 * 
	 * @author TechFinium
	 * @see CoursewareDistibution
	 */
	public void coursewaredistibutionInsert() {
		try {
			CoursewareDistibution cd = service.findByCompanyAndModule(selectedCompany,selectedModule);
			
			if(cd == null)
			{
				service.requestCourseWare(coursewaredistibution, selectedCompany, selectedModule, getSessionUI().getActiveUser(), true);
				prepareNew();
				addInfoMessage("The application has been submitted for review.");
				coursewaredistibutionInfo();
				findUnitStandardsAndQualifications();
			}
			else
			{
				service.requestCourseWare(cd, selectedCompany, selectedModule, getSessionUI().getActiveUser(), true);
				prepareNew();
				addInfoMessage("The application has been submitted for review.");
				coursewaredistibutionInfo();
				findUnitStandardsAndQualifications();
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()), e);
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void requestAllCourseWare() {
		try {
			for (Modules modules : modules) {
				prepareNew();
				//service.requestCourseWare(coursewaredistibution, selectedCompany, modules, getSessionUI().getActiveUser(), true);
				CoursewareDistibution cd = service.findByCompanyAndModule(selectedCompany,modules);
				
				if(cd == null){
					service.requestCourseWare(coursewaredistibution, selectedCompany, modules, getSessionUI().getActiveUser(), true);
				}
				else{
					service.requestCourseWare(cd, selectedCompany, modules, getSessionUI().getActiveUser(), true);
				}
			}
			
			prepareNew();
			addInfoMessage("The application has been submitted for review.");
			coursewaredistibutionInfo();
			findUnitStandardsAndQualifications();			
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()), e);
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update CoursewareDistibution in database
	 * 
	 * @author TechFinium
	 * @see CoursewareDistibution
	 */
	public void coursewaredistibutionUpdate() {
		try {
			service.update(this.coursewaredistibution);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			coursewaredistibutionInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete CoursewareDistibution from database
	 * 
	 * @author TechFinium
	 * @see CoursewareDistibution
	 */
	public void coursewaredistibutionDelete() {
		try {
			service.delete(this.coursewaredistibution);
			prepareNew();
			coursewaredistibutionInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public String getRejectionReasons(CoursewareDistibution coursewareDistibution)
	{
		String bf = "";
		List<RejectReasons>list = new ArrayList<>();
		try {
			list = service.locateReasonsSelectedByTargetKeyClassAndProcess(coursewareDistibution);
			for(RejectReasons rejectReasons: list)
			{
				bf+=rejectReasons.getDescription() + ",";
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}			
		return bf;
	}
	
	public  String buildUnitStandartString(List<ModulesUnitStandards> unitsStadarts)
	{		
		String bf = "";
		for(ModulesUnitStandards unit: unitsStadarts)
		{
			bf +=  unit.getUnitStandards().getCode() +", ";		
		}
		return bf;
	}

	/**
	 * Create new instance of CoursewareDistibution
	 * 
	 * @author TechFinium
	 * @see CoursewareDistibution
	 */
	public void prepareNew() {
		coursewaredistibution = new CoursewareDistibution();
	}

	/*
	 * public List<SelectItem> getCoursewareDistibutionDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * coursewaredistibutionInfo(); for (CoursewareDistibution ug :
	 * coursewaredistibutionList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<CoursewareDistibution> complete(String desc) {
		List<CoursewareDistibution> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<CoursewareDistibution> getCoursewareDistibutionList() {
		return coursewaredistibutionList;
	}

	public void setCoursewareDistibutionList(List<CoursewareDistibution> coursewaredistibutionList) {
		this.coursewaredistibutionList = coursewaredistibutionList;
	}

	public CoursewareDistibution getCoursewaredistibution() {
		return coursewaredistibution;
	}

	public void setCoursewaredistibution(CoursewareDistibution coursewaredistibution) {
		this.coursewaredistibution = coursewaredistibution;
	}

	public List<CoursewareDistibution> getCoursewareDistibutionfilteredList() {
		return coursewaredistibutionfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param coursewaredistibutionfilteredList
	 *            the new coursewaredistibutionfilteredList list
	 * @see CoursewareDistibution
	 */
	public void setCoursewareDistibutionfilteredList(List<CoursewareDistibution> coursewaredistibutionfilteredList) {
		this.coursewaredistibutionfilteredList = coursewaredistibutionfilteredList;
	}

	public List<Company> getCompanies() {
		return companies;
	}

	public void setCompanies(List<Company> companies) {
		this.companies = companies;
	}

	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public List<Modules> getModules() {
		return modules;
	}

	public void setModules(List<Modules> modules) {
		this.modules = modules;
	}

	public Modules getSelectedModule() {
		return selectedModule;
	}

	public void setSelectedModule(Modules selectedModule) {
		this.selectedModule = selectedModule;
	}

	public LazyDataModel<CoursewareDistibution> getCoursewareDistibutionDataModel() {
		return coursewareDistibutionDataModel;
	}

	public void setCoursewareDistibutionDataModel(LazyDataModel<CoursewareDistibution> coursewareDistibutionDataModel) {
		this.coursewareDistibutionDataModel = coursewareDistibutionDataModel;
	}

	public int getNumberOfCourseware() {
		return numberOfCourseware;
	}

	public void setNumberOfCourseware(int numberOfCourseware) {
		this.numberOfCourseware = numberOfCourseware;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public LazyDataModel<CoursewareDistibution> getRejectedcoursewareDistibutionDataModel() {
		return rejectedcoursewareDistibutionDataModel;
	}

	public void setRejectedcoursewareDistibutionDataModel(
			LazyDataModel<CoursewareDistibution> rejectedcoursewareDistibutionDataModel) {
		this.rejectedcoursewareDistibutionDataModel = rejectedcoursewareDistibutionDataModel;
	}

	public LazyDataModel<CoursewareDistibution> getRejectedcoursewareDistibutionReport() {
		return rejectedcoursewareDistibutionReport;
	}

	public void setRejectedcoursewareDistibutionReport(
			LazyDataModel<CoursewareDistibution> rejectedcoursewareDistibutionReport) {
		this.rejectedcoursewareDistibutionReport = rejectedcoursewareDistibutionReport;
	}

	public int getNumberOfRejectedCourseware() {
		return numberOfRejectedCourseware;
	}

	public void setNumberOfRejectedCourseware(int numberOfRejectedCourseware) {
		this.numberOfRejectedCourseware = numberOfRejectedCourseware;
	}
	
}
