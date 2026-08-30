package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Blank;
import haj.com.entity.SdpCompanyActions;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.TaskStatusEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.SdpCompanyActionsService;
import haj.com.service.TasksService;
import haj.com.service.TrainingSiteService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "sdpCompanyActionsWorkflowUI")
@ViewScoped
public class SdpCompanyActionsWorkflowUI extends AbstractUI {
	
	/* Entity Level  */
	private SdpCompanyActions sdpCompanyActions = null;
	
	/* Service levels */
	private CompanyService companyService = new CompanyService();
	private TrainingSiteService trainingSiteService = new TrainingSiteService();
	private RejectReasonsService rejectReasonsService = new RejectReasonsService();
	private SdpCompanyActionsService sdpCompanyActionsService = new SdpCompanyActionsService();
	
	/* Array Lists */
	private List<RejectReasons> selectedRejectionReasons = new ArrayList<>();
	
	/* Vars and Enums */
	private ConfigDocProcessEnum config = null;
	
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
	 * @throws Exception the exception
	 * @see    Blank
	 */
	private void runInit() throws Exception {
		if (getSessionUI().getTask() != null && (getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.NEW_SDP_LINK
				|| getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.REMOVE_SDP_LINK
						|| getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.UPDATE_SDP_DESIGNATION)) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			if (getSessionUI().getTask().getTaskStatus() == TaskStatusEnum.Completed) {
				ajaxRedirectToDashboard();
			}
			sdpCompanyActions = sdpCompanyActionsService.findByKey(getSessionUI().getTask().getTargetKey());
			// resolve docs assigned
			sdpCompanyActionsService.populateAdditionalInformation(sdpCompanyActions);
			// set config for rejection reasons
			config =  getSessionUI().getTask().getWorkflowProcess();
			// resolve company address information
			if (sdpCompanyActions.getCompany() != null && sdpCompanyActions.getCompany().getId() != null) {
				companyService.resolveCompanyAddresses(sdpCompanyActions.getCompany());
			}
			// resolve training site information
			if (sdpCompanyActions.getTrainingSite() != null && sdpCompanyActions.getTrainingSite().getId() != null) {
				trainingSiteService.resolveAddressInformatioAndRegion(sdpCompanyActions.getTrainingSite());
			}
		} else {
			ajaxRedirectToDashboard();
		}
	}
	
	public void prepFinalRejection(){
		try {
			selectedRejectionReasons = new ArrayList<>();
			runClientSideExecute("PF('rejectReasonsDlg').show()");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public List<RejectReasons> getRejectReasonsForSelection() {
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(config);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}
	
	public void finalRejectWorkflow(){
		try {
			if (selectedRejectionReasons.isEmpty()) {
				addErrorMessage("Provide atleast one rejection reason before proceeding");
			} else {
				sdpCompanyActionsService.finalRejectAction(sdpCompanyActions, getSessionUI().getTask(), getSessionUI().getActiveUser(), selectedRejectionReasons);
				ajaxRedirectToDashboard();
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalApproveWorkflow(){
		try {
			sdpCompanyActionsService.finalApproveAction(sdpCompanyActions, getSessionUI().getTask(), getSessionUI().getActiveUser());
			ajaxRedirectToDashboard();
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Getters and setters */
	public SdpCompanyActions getSdpCompanyActions() {
		return sdpCompanyActions;
	}

	public void setSdpCompanyActions(SdpCompanyActions sdpCompanyActions) {
		this.sdpCompanyActions = sdpCompanyActions;
	}

	public List<RejectReasons> getSelectedRejectionReasons() {
		return selectedRejectionReasons;
	}

	public void setSelectedRejectionReasons(List<RejectReasons> selectedRejectionReasons) {
		this.selectedRejectionReasons = selectedRejectionReasons;
	}

	
}
