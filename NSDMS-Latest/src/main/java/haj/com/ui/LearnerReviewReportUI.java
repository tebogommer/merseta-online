package haj.com.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.JobTitle;
import haj.com.entity.lookup.Region;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.ScheduledEventService;
import haj.com.service.UsersTrainingProviderService;
import haj.com.service.lookup.JobTitleService;
import haj.com.service.lookup.RegionService;

@ManagedBean(name = "learnerReviewReportUI")
@ViewScoped
public class LearnerReviewReportUI extends AbstractUI {
	private LazyDataModel<CompanyLearners> dataModel;
	private LazyDataModel<CompanyLearners>  dataModelList;
	/** The users CompanyLearners list. */
	private List<CompanyLearners> companyLearnersList;

	/** The company. */
	private Company taskCompany;

	/** The company service. */
	private CompanyService companyService = new CompanyService();

	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	/** The users training provider service. */
	private UsersTrainingProviderService usersTrainingProviderService = new UsersTrainingProviderService();

	private CompanyLearners companylearners;
	private CompanyLearners companyLearnerParent;
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private ScheduledEventService scheduledEventService = new ScheduledEventService();
	private ScheduledEvent scheduledEvent;

	private boolean selected = false;
	private boolean viewButton = false;
	private boolean coordinator = false;
	private boolean qaUserByRegion = false;
	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	private JobTitle jobTitle;
	private Region region;
	private RegionService regionService = new RegionService();
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
	 * Run init.
	 *
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		//populateJobTitleAndRegion() ;
		companyLearnersInfo();
		//prepareScheduledEvent();	
	}


	private void prepareScheduledEvent() {
		this.scheduledEvent = new ScheduledEvent();
	}

	private void populateJobTitleAndRegion() throws Exception {
		coordinator = false;
		if (!getSessionUI().isAdmin()) {
			if (getSessionUI().isEmployee()) {
				HostingCompanyEmployees hce = hostingCompanyEmployeesService.findByUserReturnHostCompany(getSessionUI().getUser().getId());
				if (hce.getJobTitle() != null && hce.getJobTitle().getId() != null) {
					// populate job title
					jobTitle = JobTitleService.instance().findByKey(hce.getJobTitle().getId());
					if (jobTitle.getRegion() != null && jobTitle.getRegion().getId() != null) {
						// populate region
						region = regionService.findByKey(jobTitle.getRegion().getId());
					}
					
					if (jobTitle != null && jobTitle.getRoles() !=null && jobTitle.getRoles().getId() != null && jobTitle.getRoles().getId().equals(HAJConstants.CLIENT_SERVICE_COORDINATOR_ROLE_ID)) {
						coordinator = true;
					} 
					/*if (jobTitle.getId().equals(HAJConstants.CLIENT_SERVICE_COORDINATOR_ROLE_ID)) {
						coordinator = true;
					} */
				}
			}
		}
		
		if(coordinator) {
			companyLearnersInfo();
		}
	}

	public void companyLearnersInfo() {
		dataModel = new LazyDataModel<CompanyLearners>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearners> retorno = new ArrayList<CompanyLearners>();

			@Override
			public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					
					filters.put("status", ApprovalEnum.PendingApproval);
					retorno = companyLearnersService.sortAndFilterReport(first, pageSize, sortField, sortOrder, filters);
					for(CompanyLearners companyLearnerParent: retorno) {
						scheduledEvent = scheduledEventService.findByTargetKeyAndTargetClass(companyLearnerParent.getId(), Company.class.getName());
						companyLearnerParent.setScheduledEvent(scheduledEvent);
					}
					dataModel.setRowCount(companyLearnersService.countReport(filters));
					
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearners obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearners getRowData(String rowKey) {
				for (CompanyLearners obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	
	
	
	public void pupulatedataModelList() {
		dataModelList = new LazyDataModel<CompanyLearners>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearners> retorno = new ArrayList<CompanyLearners>();

			@Override
			public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if(filters == null) {
						filters = new HashMap<>();
					}
					filters.put("companyLearnerParentID", companyLearnerParent.getId());					
					retorno = companyLearnersService.sortAndFilterSearchParent(first, pageSize, sortField, sortOrder, filters);					
					for(CompanyLearners companyLearnerParent: retorno) {
						
					}					
					dataModelList.setRowCount(companyLearnersService.countSearchParent(filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(CompanyLearners obj) {
				return obj.getId();
			}

			@Override
			public CompanyLearners getRowData(String rowKey) {
				for (CompanyLearners obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
		};
	}
	
	public void findCompanyLearners(){
		try {
			if(this.companyLearnerParent != null) {
				selected = true;
				pupulatedataModelList();
				//companyLearnersList = companyLearnersService.findCompanyLearnersByParent(this.companyLearnerParent.getId());
				this.taskCompany = companyService.findByKeyFoReview(this.companyLearnerParent.getCompany().getId());
				//checkIfCanCompleteTask();
			}else {
				
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void resolveCompanyLearnersSchedule() {
		try {
			companylearners = companyLearnersService.resolveEverything(companylearners, ConfigDocProcessEnum.Learner);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}


	public void viewCompanyLearnerData() {
		try {
			companyLearnersService.resolveAllData(companylearners);
			companyLearnersService.prepareNewRegistration(ConfigDocProcessEnum.Learner, companylearners);
			this.companylearners = companyLearnersService.resolveEverything(companylearners, ConfigDocProcessEnum.Learner);
			this.companylearners.setDocs(DocService.instance().searchByTargetKeyAndClass(this.companylearners.getClass().getName(), this.companylearners.getId(), ConfigDocProcessEnum.Learner));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void updateReviewDateForApproval() {
		try {
			scheduledEventService.update(companyLearnerParent.getScheduledEvent());			
			prepareScheduledEvent();
			scheduledEvent = scheduledEventService.findByTargetKeyAndTargetClass(companyLearnerParent.getId(), Company.class.getName());
			companyLearnersService.sendUpdateReviewDateEmail(companyLearnerParent, this.taskCompany, getSessionUI().getActiveUser(), scheduledEvent.getFromDateTime());
			populateJobTitleAndRegion();	
			checkIfCanCompleteTask();
			addInfoMessage("Review Date Updated");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void acceptReviewDateForApproval() {
		try {
			companyLearnersService.sendUpdateReviewDateEmail(companyLearnerParent, this.taskCompany, getSessionUI().getActiveUser(), scheduledEvent.getFromDateTime());
			addInfoMessage("Review Date Accepted");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	public void completeWorkflow() {
		try {
			if(scheduledEvent.getFromDateTime() == null) {
				 throw new Exception("Please Select Review Date And Click The Set Review Date Button Before Proceeding.");
			}
			if(scheduledEvent.getFromDateTime().equals( null )) {
				 throw new Exception("Please Select Review Date And Click The Set Review Date Button Before Proceeding.");
			}
			
			if (getSessionUI().isEmployee()) getSessionUI().getTask().setReviewDate(scheduledEvent.getFromDateTime());
			companyUsersService.completeWorkflow(taskCompany, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveWorkflow() {
		try {
			//companyLearnersList = companyLearnersService.findCompanyLearnersByStatus(taskCompany.getId(), ApprovalEnum.PreApproved, SubmissionEnum.ForReview, companyLearnerParent.getId());
			companyLearnersList = companyLearnersService.findCompanyLearnersByParent(companyLearnerParent.getId());
			companyUsersService.approveWorkflow(companyLearnersList, taskCompany, getSessionUI().getActiveUser(), getSessionUI().getTask(),  scheduledEvent, companyLearnerParent);
			selected = false;
			populateJobTitleAndRegion();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void approveWorkflowtoHoldingRoom() {
		try {
			//companyLearnersList = companyLearnersService.findCompanyLearnersByStatus(taskCompany.getId(), ApprovalEnum.PreApproved, SubmissionEnum.ForReview, companyLearnerParent.getId());
			companyLearnersList = companyLearnersService.findCompanyLearnersByParent(companyLearnerParent.getId());
			companyUsersService.approveWorkflowtoHoldingRoom(companyLearnersList, taskCompany, getSessionUI().getActiveUser(), getSessionUI().getTask(),  scheduledEvent, companyLearnerParent);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	private void checkIfCanCompleteTask() throws ParseException {
		if(companyLearnerParent.getScheduledEvent() !=null && companyLearnerParent.getScheduledEvent() .getId()!=null) {
			SimpleDateFormat simpleDateFormat = HAJConstants.sdfDDMMYYYY2;
			String fromDate = simpleDateFormat.format(companyLearnerParent.getScheduledEvent() .getFromDateTime());
			String now = simpleDateFormat.format(getNow());
			
			Date dateFrom =  simpleDateFormat.parse(fromDate);
			Date dateNow =  simpleDateFormat.parse(now);
			
			if(dateNow.after(dateFrom) || dateFrom.equals(dateNow)) {
				viewButton = true;			
			}else {
				viewButton = false;
			}
			
			/*if(getNow().after(companyLearnerParent.getScheduledEvent() .getFromDateTime()) || companyLearnerParent.getScheduledEvent() .getFromDateTime().equals(getNow())) {
				viewButton = true;			
			}else {
				viewButton = false;
			}*/
		}else {
			viewButton = false;
		}			
	}
	public CompanyLearners getCompanylearners() {
		return companylearners;
	}

	public void setCompanylearners(CompanyLearners companylearners) {
		this.companylearners = companylearners;
	}


	public LazyDataModel<CompanyLearners> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearners> dataModel) {
		this.dataModel = dataModel;
	}

	public Company getTaskCompany() {
		return taskCompany;
	}

	public void setTaskCompany(Company taskCompany) {
		this.taskCompany = taskCompany;
	}

	public ScheduledEvent getScheduledEvent() {
		return scheduledEvent;
	}

	public void setScheduledEvent(ScheduledEvent scheduledEvent) {
		this.scheduledEvent = scheduledEvent;
	}

	public CompanyLearners getCompanyLearnerParent() {
		return companyLearnerParent;
	}

	public void setCompanyLearnerParent(CompanyLearners companyLearnerParent) {
		this.companyLearnerParent = companyLearnerParent;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public LazyDataModel<CompanyLearners> getDataModelList() {
		return dataModelList;
	}

	public void setDataModelList(LazyDataModel<CompanyLearners> dataModelList) {
		this.dataModelList = dataModelList;
	}

	public List<CompanyLearners> getCompanyLearnersList() {
		return companyLearnersList;
	}

	public void setCompanyLearnersList(List<CompanyLearners> companyLearnersList) {
		this.companyLearnersList = companyLearnersList;
	}

	public boolean isViewButton() {
		return viewButton;
	}

	public void setViewButton(boolean viewButton) {
		this.viewButton = viewButton;
	}
}
