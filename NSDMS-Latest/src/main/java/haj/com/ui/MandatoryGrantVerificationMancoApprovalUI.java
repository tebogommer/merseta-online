package haj.com.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.AssessorModeratorApplication;
import haj.com.entity.Company;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.MgVerification;
import haj.com.entity.Sites;
import haj.com.entity.Wsp;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.MandatoryGrantDetailService;
import haj.com.service.MandatoryGrantRecommendationService;
import haj.com.service.MandatoryGrantService;
import haj.com.service.MgVerificationService;
import haj.com.service.SignoffService;
import haj.com.service.SitesService;
import haj.com.service.TasksService;
import haj.com.service.WspService;
import haj.com.service.YesNoLookupService;
import haj.com.service.lookup.EtqaService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

//TODO: Auto-generated Javadoc
/**
 * The Class MandatoryGrantVerificationUI.
 */
@ManagedBean(name = "mandatoryGrantVerificationmancoApprovalUI")
@ViewScoped
public class MandatoryGrantVerificationMancoApprovalUI extends AbstractUI {

	/** Entity Layer */
	private MgVerification mgVerification;
	private Company company;
	private Wsp wsp;
	private Date today = GenericUtility.getStartOfDay(new Date());

	/** Service Layer */
	private MandatoryGrantDetailService mandatoryGrantDetailService = new MandatoryGrantDetailService();
	private EtqaService etqaService = new EtqaService();
	private YesNoLookupService yesNoService = new YesNoLookupService();
	private MandatoryGrantService mandatoryGrantService = new MandatoryGrantService();
	private WspService wspService = new WspService();
	private CompanyService companyService = new CompanyService();
	private MgVerificationService mgVerificationService = new MgVerificationService();
	private MandatoryGrantService grantService = new MandatoryGrantService();
	private MandatoryGrantRecommendationService grantRecommendationService = new MandatoryGrantRecommendationService();

	/** Lists */	
	private LazyDataModel<MgVerification> mgVerificationDataModel;
	private LazyDataModel<MandatoryGrant> dataModel = null;

	private SignoffService signoffService = new SignoffService();

	/** Boolean */
	private Boolean disableEdit;
	private Boolean disableSignOffButton;
	private boolean selected = false;

	private List<Sites> sites;
	private SitesService sitesService = new SitesService();
	private ArrayList<RejectReasons> selectedRejectReason=new ArrayList<>();
	
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

	private void runInit() throws Exception {
		//startMG();
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.MG_VERIFICATION && super.getParameter("id", false) != null) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			mgVerification = mgVerificationService.findByKey(getSessionUI().getTask().getTargetKey());
			selctemgVerification();
		} else {
			disableEdit = false;
			mgVerificationInfo();
		}
	}

	/**
	 * Lazy load for wsp
	 * 
	 * @throws Exception
	 */
	private void mgVerificationInfo() throws Exception {
		mgVerificationDataModel = new LazyDataModel<MgVerification>() {
			private static final long serialVersionUID = 1L;
			private List<MgVerification> retorno = new ArrayList<MgVerification>();
			
			@Override
			public List<MgVerification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					
					//filters.put("status", ApprovalEnum.PendingCommitteeApproval);
					/*retorno = mgVerificationService.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters);
					dataModel.setRowCount(mgVerificationService.count(MgVerification.class,filters));*/
					
					retorno = mgVerificationService.rejectedMgVerification(MgVerification.class, first, pageSize, sortField, sortOrder, filters);
					mgVerificationDataModel.setRowCount(mgVerificationService.countRejectedSearch(MgVerification.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(MgVerification obj) {
				return obj.getId();
			}

			@Override
			public MgVerification getRowData(String rowKey) {
				for (MgVerification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}
			
		};		
	}
	
	private void pivitolInfoWsp() throws Exception {
		company = companyService.findByKey(mgVerification.getWsp().getCompany().getId());
		sites = sitesService.findByCompany(company);
		dataModel = new LazyDataModel<MandatoryGrant>() {

			private static final long serialVersionUID = 1L;
			private List<MandatoryGrant> retorno = new ArrayList<MandatoryGrant>();

			@Override
			public List<MandatoryGrant> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = grantService.allMandatoryGrant(MandatoryGrant.class, first, pageSize, sortField, sortOrder, filters, mgVerification.getWsp());
					retorno.forEach(mg -> {
						try {
							mg.setGrantRecommendations(grantRecommendationService.findByMG(mg));
							// mg.setWorkPlaceApproval(workPlaceApprovalService.findByDiscretionaryGrant(mg));
						} catch (Exception e) {
							e.printStackTrace();
						}
					});
					dataModel.setRowCount(grantService.count(MandatoryGrant.class, filters, mgVerification.getWsp()));
				} catch (Exception e) {
					e.printStackTrace();
				}
				return retorno;
			}

			@Override
			public Object getRowKey(MandatoryGrant obj) {
				return obj.getId();
			}

			@Override
			public MandatoryGrant getRowData(String rowKey) {
				for (MandatoryGrant obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};
	}
	
	public void finalApproveMgVerification()
	{
		try {
			mgVerificationService.finalApproveMgVerification(getSessionUI().getActiveUser(), mgVerification);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			//super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	public void finalRejectMGVerification() {
		try {
			if (selectedRejectReason.size() == 0){
				throw new Exception("Please select a reject reason");				
			}
			mgVerificationService.finalReject(mgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			//service.rejectRegistration(skillsregistration, getSessionUI().getActiveUser(), getSessionUI().getTask(),  selectedRejectReason);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
			
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void uploadMGVerificationEvidence()
	{
		try {
			mgVerificationService.uploadMGVerificationEvidence(getSessionUI().getActiveUser(), mgVerification, getSessionUI().getTask());
			prepareNew();
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}
	
	
	public void cancel()
	{
		try {			
			prepareNew();
			//super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(),e);
		}
	}

	private void prepareNew() throws Exception {
		mgVerification = null;
		dataModel= null;
		selected = false;
		mgVerificationInfo();
	}

	public void setVisitDateOnWorkplaceApproval()
	{
		mgVerification.setDateForVisitProvided(true);
		try {
			mgVerificationService.update(mgVerification);
			addInfoMessage("Update Succesful");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepVisitDateOnWorkplaceApproval() {
		
	}

	/**
	 * When a wsp is selected locates all mandatory grants by wsp company
	 */
	public void selctemgVerification() {
		try {
			dataModel=null;
			this.company = companyService.findByKey(mgVerification.getWsp().getCompany().getId());
			selected = true;
			locateWspMgVerification();
			pivitolInfoWsp();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}


	/**
	 * locates MG verification by WSP
	 * 
	 * @throws Exception
	 */
	private void locateWspMgVerification() throws Exception {
		mgVerification = mgVerificationService.findByWspId(mgVerification.getWsp());
		if (mgVerification == null || mgVerification.getId() == null) {
			mgVerification = new MgVerification();
			mgVerification.setWsp(mgVerification.getWsp());
		}
		this.mgVerification = signoffService.resolveMgSignOff(mgVerification);
			mgVerification.getSignOffs().forEach(sign -> {
			if (disableSignOffButton == null || !disableSignOffButton) {
				disableSignOffButton = sign.getUser().equals(getSessionUI().getUser()) && (sign.getCompleted() == null || !sign.getCompleted());
			}
		});
	}

	/**
	 * Create / Update MG Verification submit button and relocate MG Verification by
	 * WSP
	 */
	public void updateMgVerification() {
		try {
			updateMg();
			locateWspMgVerification();
			addInfoMessage("Update Succesful");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void startMG() {
		try {
			mandatoryGrantDetailService.createMGVerificationData();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void signOffVerification() {
		try {
			if (mgVerification.getDateCaptured() == null) {
				mgVerification.setDateCaptured(new Date());
			}
			mgVerificationService.saveSignOff(mgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
			locateWspMgVerification();
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create / Update MG Verification
	 * 
	 * @throws Exception
	 */
	private void updateMg() {
		try {
			if (mgVerification.getDateCaptured() == null) {
				mgVerification.setDateCaptured(new Date());
			}
			mgVerificationService.createWithSignOff(mgVerification, getSessionUI().getActiveUser(), getSessionUI().getTask());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void generateEmployeesEmployed() {
		try {
		
			addInfoMessage(getEntryLanguage("update.successful"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.MG_VERIFICATION);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public Boolean getDisableEdit() {
		return disableEdit;
	}

	public void setDisableEdit(Boolean disableEdit) {
		this.disableEdit = disableEdit;
	}

	public MgVerification getMgVerification() {
		return mgVerification;
	}

	public void setMgVerification(MgVerification mgVerification) {
		this.mgVerification = mgVerification;
	}

	public Boolean getDisableSignOffButton() {
		return disableSignOffButton;
	}

	public void setDisableSignOffButton(Boolean disableSignOffButton) {
		this.disableSignOffButton = disableSignOffButton;
	}

	public LazyDataModel<MgVerification> getMgVerificationDataModel() {
		return mgVerificationDataModel;
	}

	public void setMgVerificationDataModel(LazyDataModel<MgVerification> mgVerificationDataModel) {
		this.mgVerificationDataModel = mgVerificationDataModel;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public Company getCompany() {
		return company;
	}

	public void setCompany(Company company) {
		this.company = company;
	}

	public Date getToday() {
		return today;
	}

	public void setToday(Date today) {
		this.today = today;
	}

	public LazyDataModel<MandatoryGrant> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<MandatoryGrant> dataModel) {
		this.dataModel = dataModel;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}
	
}
