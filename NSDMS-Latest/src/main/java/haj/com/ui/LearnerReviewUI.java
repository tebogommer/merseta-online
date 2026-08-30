package haj.com.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersTransfer;
import haj.com.entity.Doc;
import haj.com.entity.Learners;
import haj.com.entity.ScheduledEvent;
import haj.com.entity.UserQualifications;
import haj.com.entity.Users;
import haj.com.entity.UsersTrainingProvider;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.SubmissionEnum;
import haj.com.entity.lookup.DateChangeReasons;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.QualificationType;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyLearnersService;
import haj.com.service.CompanyService;
import haj.com.service.CompanyUsersService;
import haj.com.service.DocService;
import haj.com.service.ScheduledEventService;
import haj.com.service.TasksService;
import haj.com.service.UserQualificationsService;
import haj.com.service.UsersService;
import haj.com.service.UsersTrainingProviderService;
import haj.com.service.lookup.DateChangeReasonsService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "learnerReviewUI")
@ViewScoped
public class LearnerReviewUI extends AbstractUI {
	private LazyDataModel<CompanyLearners> dataModel;
	private List<Learners> learnersfilteredList = null;
	private List<DateChangeReasons> dateChangeReasonAvalibleSelectionList = null;
	private List<DateChangeReasons> dateChangeReasonSelectionList = null;
	/** The user. */
	private Users user;
	private Users gaurdian;
	private boolean requireGaurdian = true;
	private boolean viewLearnerData = false;
	private boolean viewButoons = false;

	/** The users service. */
	private UsersService usersService = new UsersService();

	/** The company. */
	private Company company;

	/** The training provider. */
	private Company trainingProvider;

	/** The company. */
	private Company taskCompany;

	/** The company service. */
	private CompanyService companyService = new CompanyService();

	/** The company users service. */
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	/** The doc. */
	private Doc doc;

	/** Addresses. */
	private Address residentialAddress;

	/** The postal address. */
	private Address postalAddress;

	/** The copy address. */
	private boolean copyAddress;

	/** This is for the UsersQualification. */
	private QualificationType qualificationType;

	/** The users qualification. */
	private UserQualifications usersQualification;

	/** The qualification. */
	private Qualification qualification;

	/** The users qualification service. */
	private UserQualificationsService usersQualificationService = new UserQualificationsService();

	/** The users qualification list. */
	private List<UserQualifications> usersQualificationList;

	/** The users CompanyLearners list. */
	private List<CompanyLearners> companyLearnersList;

	private boolean signoff = false;

	private Date reviewDate;
	private String documentBoxID;

	/**
	 * This is for UsersTraingProvider Details After the correct training company is
	 * selected.
	 */
	private UsersTrainingProvider usersTrainingProvider;

	private ArrayList<RejectReasons> selectedRejectReason = new ArrayList<>();

	/** The users training provider service. */
	private UsersTrainingProviderService usersTrainingProviderService = new UsersTrainingProviderService();

	private CompanyLearners companylearners;
	private CompanyLearners companyLearnerParent;
	private CompanyLearnersTransfer companyLearnersTransfer;
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();
	private ScheduledEventService scheduledEventService = new ScheduledEventService();
	private boolean skillsSet;
	private boolean skillsProgram;
	private boolean shortCreditBearing;
	boolean show = false;
	private String meetingMessage;
	private ScheduledEvent scheduledEvent;
	private List<RejectReasons> rejectReason = new ArrayList<>();

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
		if (super.getParameter("id", false) != null) {
			if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.LearnerReview) {
				getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
				this.companyLearnerParent = companyLearnersService.findByKey(getSessionUI().getTask().getTargetKey());
				//this.taskCompany = companyService.findByKeyFoReview(getSessionUI().getTask().getTargetKey());
				this.taskCompany = companyService.findByKeyFoReview(companyLearnerParent.getCompany().getId());
				companyLearnersInfo();
				prepareScheduledEvent();
				scheduledEvent = scheduledEventService.findByTargetKeyAndTargetClass(companyLearnerParent.getId(), Company.class.getName());
				if (scheduledEvent == null) {
					prepareScheduledEvent();
				}				
				populateRejectReasons();
				checkIfCanCompleteTask();
			}

		} else {
			super.redirectToDashboard();
		}

		// removed
		/*
		 * if(getSessionUI().getUser() != null) { companyLearnersInfo();
		 * getDateChangeReasons(); }
		 */
	}
	
	private void checkIfCanCompleteTask() {
		if(scheduledEvent !=null && scheduledEvent.getId()!=null) {
			if(getNow().after(scheduledEvent.getFromDateTime()) || scheduledEvent.getFromDateTime().equals(getNow())) {
				viewButoons = true;			
			}else {
				viewButoons = false;
			}
		}else {
			viewButoons = false;
		}			
	}

	private void prepareScheduledEvent() {
		this.scheduledEvent = new ScheduledEvent();
	}

	private void populateRejectReasons() {
		RejectReasonsService rs = new RejectReasonsService();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(companyLearnerParent.getId(), Company.class.getName(), ConfigDocProcessEnum.LearnerReview);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void companyLearnersInfo() {
		dataModel = new LazyDataModel<CompanyLearners>() {
			private static final long serialVersionUID = 1L;
			private List<CompanyLearners> retorno = new ArrayList<CompanyLearners>();

			@Override
			public List<CompanyLearners> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					//filters.put("status", ApprovalEnum.PreApproved);
					//filters.put("submissionEnum", SubmissionEnum.ForReview);
					//filters.put("companyID", taskCompany.getId());
					filters.put("companyLearnerParentID", companyLearnerParent.getId());
					//retorno = companyLearnersService.sortAndFilterSearchStatus(first, pageSize, sortField, sortOrder, filters);
					//dataModel.setRowCount(companyLearnersService.countSearchStatus(filters));
					
					retorno = companyLearnersService.sortAndFilterSearchParent(first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(companyLearnersService.countSearchParent(filters));
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

	public void resolveCompanyLearnersSchedule() {
		try {
			companylearners = companyLearnersService.resolveEverything(companylearners, ConfigDocProcessEnum.Learner);

		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	public void updateSchedule() {
		try {
			companyLearnersService.updateSchedule(companylearners);
			addInfoMessage(super.getEntryLanguage("update.successful"));

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
			this.companylearners.setQualification(companyLearnersService.getCompanyLearnerQualification(companylearners));
			this.companylearners.setDocs(DocService.instance().searchByTargetKeyAndClass(this.companylearners.getClass().getName(), this.companylearners.getId(), ConfigDocProcessEnum.Learner));
			this.company = companyService.findByKey(companylearners.getCompany().getId());
			// companylearners.setCompany(companyService.findByKey(companylearners.getCompany().getId()));;
			this.viewLearnerData = true;
			// getShowActionButton();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}

	/**
	 * Clear current learner.
	 */
	public void clearCurrentLearner() {
		this.user = null;
		this.qualification = new Qualification();
		this.qualificationType = new QualificationType();
		this.usersQualificationList = new ArrayList<UserQualifications>();
		this.usersQualification = new UserQualifications();
	}

	/**
	 * Clear current company.
	 */
	public void clearCurrentCompany() {
		if (this.company.getId() == null) this.company = null;
		addInfoMessage(super.getEntryLanguage("company.section.cleared"));
	}

	/**
	 * Clear current training provider.
	 */
	public void clearCurrentTrainingProvider() {
		this.trainingProvider = null;
		addInfoMessage(super.getEntryLanguage("training.provider.section.cleared"));
	}

	/**
	 * Prepare training providers details.
	 */
	public void prepareTrainingProvidersDetails() {
		try {
			this.usersTrainingProvider = new UsersTrainingProvider();
			this.usersTrainingProvider = usersTrainingProviderService.findByUserCompany(this.user.getId(), this.trainingProvider.getId());

		} catch (Exception e) {
			addWarningMessage(super.getEntryLanguage("problem.with.training.provider.details"));
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveCompanyLearnersTransfer() throws Exception {
		try {
			companyLearnersService.finalApproveCompanyLearnersTransfer(companyLearnersTransfer, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void confirmReviewDate() {
		try {
			companyLearnersService.confirmReviewDate(companylearners, getSessionUI().getActiveUser(), getSessionUI().getTask());
			addInfoMessage(super.getEntryLanguage("update.successful"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reviewApproveCompanyLearners() {
		try {
			companylearners.setSignoff(signoff);
			companyLearnersService.reviewApproveCompanyLearners(companylearners, getSessionUI().getActiveUser(), getSessionUI().getTask());
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companylearners = null;
			viewLearnerData = false;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void reviewRejectCompanyLearners() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			companyLearnersService.reviewRejectCompanyLearners(companylearners, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			companylearners = null;
			viewLearnerData = false;
			clearCurrentLearner();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.Learner);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void getShowActionButton() {

		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String strDate1 = sdf.format(companylearners.getReviewCommitteeMeeting().getFromDateTime());
			String strDate2 = sdf.format(getNow());

			Date meetingDate = sdf.parse(strDate1);
			Date currentDate = sdf.parse(strDate2);

			if (meetingDate.after(currentDate)) {
				show = false;
				// dd MMMM yyyy HH:mm
				meetingMessage = "The meeting for this application is due to be held on " + new SimpleDateFormat("dd MMMM yyyy").format(companylearners.getReviewCommitteeMeeting().getFromDateTime());
			}

			if (meetingDate.before(currentDate)) {
				show = false;
				meetingMessage = "The meeting for this application was due to be held on " + new SimpleDateFormat("dd MMMM yyyy").format(companylearners.getReviewCommitteeMeeting().getFromDateTime());

			}

			if (meetingDate.equals(currentDate)) {
				show = true;
				meetingMessage = "";
			}
		} catch (ParseException e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
		show = true;
		// return show;

	}

	public void setReviewDateForApproval() {
		try {
			companyLearnersService.setFirstVisitDate(this.taskCompany, getSessionUI().getActiveUser(), scheduledEvent, companyLearnerParent);
			prepareScheduledEvent();
			scheduledEvent = scheduledEventService.findByTargetKeyAndTargetClass(companyLearnerParent.getId(), Company.class.getName());
			checkIfCanCompleteTask();
			addInfoMessage("Date Added");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateReviewDateForApproval() {
		try {
			scheduledEventService.update(scheduledEvent);			
			prepareScheduledEvent();
			scheduledEvent = scheduledEventService.findByTargetKeyAndTargetClass(companyLearnerParent.getId(), Company.class.getName());
			//companyLearnersService.sendUpdateReviewDateEmail(companyLearnerParent, this.taskCompany, getSessionUI().getActiveUser(), scheduledEvent.getFromDateTime());
			//checkIfCanCompleteTask();
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
	
		
	public List<DateChangeReasons> getDateChangeReasons() {
		return dateChangeReasonAvalibleSelectionList = DateChangeReasonsService.instance().findByProcess(ConfigDocProcessEnum.Learner);
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
			ajaxRedirectToDashboard();
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

	public void rejectWorkflow() {
		try {
			if (selectedRejectReason.size() == 0) throw new Exception("Please select a reject reason");
			companyUsersService.rejectWorkflow(companyLearnerParent, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalApproveWorkflow() {
		try {
			if(signoff == false) {
				throw new Exception("Plaese signoff before proceeding");
			}
			companyLearnersList = companyLearnersService.findCompanyLearnersByStatus(taskCompany.getId(), ApprovalEnum.PreApproved);
			companyUsersService.finalApproveWorkflow(companyLearnersList, taskCompany, getSessionUI().getActiveUser(), getSessionUI().getTask(), documentBoxID);
			ajaxRedirectToDashboard();
			
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void finalRejectWorkflow() {
		try {
			companyLearnersList = companyLearnersService.findCompanyLearnersByStatus(taskCompany.getId(), ApprovalEnum.PreApproved);
			companyUsersService.finalRejectWorkflow(companyLearnersList, taskCompany, getSessionUI().getActiveUser(), getSessionUI().getTask());
			ajaxRedirectToDashboard();
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
	 * Gets the residential address.
	 *
	 * @return the residential address
	 */
	public Address getResidentialAddress() {
		return residentialAddress;
	}

	/**
	 * Sets the residential address.
	 *
	 * @param residentialAddress
	 *            the new residential address
	 */
	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	/**
	 * Gets the postal address.
	 *
	 * @return the postal address
	 */
	public Address getPostalAddress() {
		return postalAddress;
	}

	/**
	 * Sets the postal address.
	 *
	 * @param postalAddress
	 *            the new postal address
	 */
	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	/**
	 * Gets the qualification type.
	 *
	 * @return the qualification type
	 */
	public QualificationType getQualificationType() {
		return qualificationType;
	}

	/**
	 * Sets the qualification type.
	 *
	 * @param qualificationType
	 *            the new qualification type
	 */
	public void setQualificationType(QualificationType qualificationType) {
		this.qualificationType = qualificationType;
	}

	/**
	 * Gets the qualification.
	 *
	 * @return the qualification
	 */
	public Qualification getQualification() {
		return qualification;
	}

	/**
	 * Sets the qualification.
	 *
	 * @param qualification
	 *            the new qualification
	 */
	public void setQualification(Qualification qualification) {
		this.qualification = qualification;
	}

	/**
	 * Gets the users qualification list.
	 *
	 * @return the users qualification list
	 */
	public List<UserQualifications> getUsersQualificationList() {
		return usersQualificationList;
	}

	/**
	 * Sets the users qualification list.
	 *
	 * @param usersQualificationList
	 *            the new users qualification list
	 */
	public void setUsersQualificationList(List<UserQualifications> usersQualificationList) {
		this.usersQualificationList = usersQualificationList;
	}

	/**
	 * Gets the users qualification.
	 *
	 * @return the users qualification
	 */
	public UserQualifications getUsersQualification() {
		return usersQualification;
	}

	/**
	 * Sets the users qualification.
	 *
	 * @param usersQualification
	 *            the new users qualification
	 */
	public void setUsersQualification(UserQualifications usersQualification) {
		this.usersQualification = usersQualification;
	}

	/**
	 * Gets the company.
	 *
	 * @return the company
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company.
	 *
	 * @param company
	 *            the new company
	 */
	public void setCompany(Company company) {
		this.company = company;
	}

	/**
	 * Gets the training provider.
	 *
	 * @return the training provider
	 */
	public Company getTrainingProvider() {
		return trainingProvider;
	}

	/**
	 * Sets the training provider.
	 *
	 * @param trainingProvider
	 *            the new training provider
	 */
	public void setTrainingProvider(Company trainingProvider) {
		this.trainingProvider = trainingProvider;
	}

	/**
	 * Gets the users training provider.
	 *
	 * @return the users training provider
	 */
	public UsersTrainingProvider getUsersTrainingProvider() {
		return usersTrainingProvider;
	}

	/**
	 * Sets the users training provider.
	 *
	 * @param usersTrainingProvider
	 *            the new users training provider
	 */
	public void setUsersTrainingProvider(UsersTrainingProvider usersTrainingProvider) {
		this.usersTrainingProvider = usersTrainingProvider;
	}

	/**
	 * Gets the doc.
	 *
	 * @return the doc
	 */
	public Doc getDoc() {
		return doc;
	}

	/**
	 * Sets the doc.
	 *
	 * @param doc
	 *            the new doc
	 */
	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public CompanyLearners getCompanylearners() {
		return companylearners;
	}

	public void setCompanylearners(CompanyLearners companylearners) {
		this.companylearners = companylearners;
	}

	public Users getGaurdian() {
		return gaurdian;
	}

	public void setGaurdian(Users gaurdian) {
		this.gaurdian = gaurdian;
	}

	public boolean isRequireGaurdian() {
		return requireGaurdian;
	}

	public void setRequireGaurdian(boolean requireGaurdian) {
		this.requireGaurdian = requireGaurdian;
	}

	public boolean isCopyAddress() {
		return copyAddress;
	}

	public void setCopyAddress(boolean copyAddress) {
		this.copyAddress = copyAddress;
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

	public CompanyLearnersTransfer getCompanyLearnersTransfer() {
		return companyLearnersTransfer;
	}

	public void setCompanyLearnersTransfer(CompanyLearnersTransfer companyLearnersTransfer) {
		this.companyLearnersTransfer = companyLearnersTransfer;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public boolean isSignoff() {
		return signoff;
	}

	public void setSignoff(boolean signoff) {
		this.signoff = signoff;
	}

	public LazyDataModel<CompanyLearners> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<CompanyLearners> dataModel) {
		this.dataModel = dataModel;
	}

	public List<Learners> getLearnersfilteredList() {
		return learnersfilteredList;
	}

	public void setLearnersfilteredList(List<Learners> learnersfilteredList) {
		this.learnersfilteredList = learnersfilteredList;
	}

	public boolean isViewLearnerData() {
		return viewLearnerData;
	}

	public void setViewLearnerData(boolean viewLearnerData) {
		this.viewLearnerData = viewLearnerData;
	}

	public List<DateChangeReasons> getDateChangeReasonAvalibleSelectionList() {
		return dateChangeReasonAvalibleSelectionList;
	}

	public void setDateChangeReasonAvalibleSelectionList(List<DateChangeReasons> dateChangeReasonAvalibleSelectionList) {
		this.dateChangeReasonAvalibleSelectionList = dateChangeReasonAvalibleSelectionList;
	}

	public List<DateChangeReasons> getDateChangeReasonSelectionList() {
		return dateChangeReasonSelectionList;
	}

	public void setDateChangeReasonSelectionList(List<DateChangeReasons> dateChangeReasonSelectionList) {
		this.dateChangeReasonSelectionList = dateChangeReasonSelectionList;
	}

	public String getMeetingMessage() {
		return meetingMessage;
	}

	public void setMeetingMessage(String meetingMessage) {
		this.meetingMessage = meetingMessage;
	}

	public boolean isShow() {
		return show;
	}

	public void setShow(boolean show) {
		this.show = show;
	}

	public Company getTaskCompany() {
		return taskCompany;
	}

	public void setTaskCompany(Company taskCompany) {
		this.taskCompany = taskCompany;
	}

	public Date getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}

	public ScheduledEvent getScheduledEvent() {
		return scheduledEvent;
	}

	public void setScheduledEvent(ScheduledEvent scheduledEvent) {
		this.scheduledEvent = scheduledEvent;
	}

	public String getDocumentBoxID() {
		return documentBoxID;
	}

	public void setDocumentBoxID(String documentBoxID) {
		this.documentBoxID = documentBoxID;
	}

	public List<RejectReasons> getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(List<RejectReasons> rejectReason) {
		this.rejectReason = rejectReason;
	}

	public CompanyLearners getCompanyLearnerParent() {
		return companyLearnerParent;
	}

	public void setCompanyLearnerParent(CompanyLearners companyLearnerParent) {
		this.companyLearnerParent = companyLearnerParent;
	}

	public boolean isViewButoons() {
		return viewButoons;
	}

	public void setViewButoons(boolean viewButoons) {
		this.viewButoons = viewButoons;
	}
}
