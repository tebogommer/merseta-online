package  haj.com.wsp.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.Wsp;
import haj.com.entity.WspChecklist;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationErrorException;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.CompanyService;
import haj.com.service.DgAllocationService;
import haj.com.service.DgVerificationService;
import haj.com.service.WspChecklistService;
import haj.com.service.WspService;
import haj.com.service.lookup.RejectReasonsService;

/**
 * UI used for approval / rejction of grants awaiting Manco Approval
 */
@ManagedBean(name = "wspMancoUI")
@ViewScoped
public class WspMancoUI extends AbstractUI {
	
	/* Entity Level */
	private Wsp selectedWsp = null;
	
	/* Service Level */
	private WspService wspService = null;
	private CompanyService companyService = null;
	private DgVerificationService dgVerificationService = null;
	
	/* Array Lists */
	private List<Wsp> wspList = null;

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
		userCheck();
		populateServiceLevels();
		populateMancoGrants();
	}

	private void userCheck() throws Exception {}

	private void populateServiceLevels() throws Exception {
		if (wspService == null) {
			wspService = new WspService();
		}
		if (companyService == null) {
			companyService = new CompanyService();
		}
		if (dgVerificationService == null) {
			dgVerificationService = new DgVerificationService();
		}
	}
	
	private void populateMancoGrants() throws Exception {
		wspList = wspService.findWspAwaitingMancoApproval();
	}
	
	public void goToWspForCompany() {
		try {
			super.redirect("/pages/externalparty/wsp/reviewapplication.jsf?idW=" + this.selectedWsp.getWspGuid());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void populateWspAddInfo(){
		try {
			selectedWsp = wspService.findByKey(selectedWsp.getId());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * The Final Approve of the grant application
	 */
	public void finalApproveGrantApplication() {
		try {
			companyService.validateSDF(selectedWsp.getCompany());
			wspService.finalApproveRejectGrantApplication(selectedWsp, getSessionUI().getActiveUser(), null, WspStatusEnum.Approved);
			if (selectedWsp.getSdfAppealedGrant() != null && selectedWsp.getSdfAppealedGrant() == true) {
				wspService.sendReleventNotificationToUsers(selectedWsp, 2, null);
			} else {
				wspService.sendReleventNotificationToUsers(selectedWsp, 1, null);
			}
			dgVerificationService.generateForWSP(selectedWsp, getSessionUI().getActiveUser());
			addInfoMessage(super.getEntryLanguage("update.successful"));
			if (selectedWsp.getFinYear() != 2019) {
				dgAllocationCheckForGrant(selectedWsp);
			}
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		}  catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * The Final Reject of the grant application
	 */
	public void finalRejectGrantApplication() {
		try {
			companyService.validateSDF(selectedWsp.getCompany());
			wspService.finalApproveRejectGrantApplication(selectedWsp, getSessionUI().getActiveUser(), null, WspStatusEnum.Rejected);
			if (selectedWsp.getGrantRejected() && selectedWsp.getSdfAppealedGrant()) {
				wspService.sendReleventNotificationToUsers(selectedWsp, 4, getAdminRejectionReasons());
			}
			addInfoMessage(super.getEntryLanguage("update.successful"));
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void dgAllocationCheckForGrant(Wsp wspPassed) throws Exception {
		DgAllocationService dgAllocationService = new DgAllocationService();
		dgAllocationService.checkForAllocation(selectedWsp);
		dgAllocationService = null;
	}
	
	public List<RejectReasons> getAdminRejectionReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(selectedWsp.getId(), Wsp.class.getName(), ConfigDocProcessEnum.WSP);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/* getters and setters */
	public Wsp getSelectedWsp() {
		return selectedWsp;
	}

	public void setSelectedWsp(Wsp selectedWsp) {
		this.selectedWsp = selectedWsp;
	}

	public List<Wsp> getWspList() {
		return wspList;
	}

	public void setWspList(List<Wsp> wspList) {
		this.wspList = wspList;
	}
	
}