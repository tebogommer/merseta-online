package haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;

import haj.com.entity.ActiveContracts;
import haj.com.entity.PaymentRequest;
import haj.com.entity.datamodel.PaymentRequestDatamodel;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.PaymentRequestService;
import haj.com.service.lookup.RejectReasonsService;

@ManagedBean(name = "paymentrequestUI")
@ViewScoped
public class PaymentRequestUI extends AbstractUI {

	private PaymentRequestService         service                    = new PaymentRequestService();
	private List<PaymentRequest>          paymentrequestList         = null;
	private List<PaymentRequest>          paymentrequestfilteredList = null;
	private PaymentRequest                paymentrequest             = null;
	private ActiveContracts               activeContracts;
	private LazyDataModel<PaymentRequest> dataModel;
	private List<RejectReasons>           selectedRejectionReasons;
	private List<RejectReasons>           rejectedReasons;

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
	 * Initialize method to get all PaymentRequest and prepare a for a create of a new PaymentRequest
	 * @author TechFinium
	 * @see PaymentRequest
	 * @throws Exception
	 * the exception
	 */
	private void runInit() throws Exception {
		if (super.getParameter("id", false) != null) {
			this.paymentrequest  = service.findByKey(getSessionUI().getTask().getTargetKey());
			this.activeContracts = this.paymentrequest.getActiveContracts();
			populateRejectReasons();
		} else {
			prepareNew();
			paymentrequestInfo();
		}
	}

	/**
	 * Get all PaymentRequest for data table
	 * @author TechFinium
	 * @see PaymentRequest
	 */
	public void paymentrequestInfo() {
		dataModel = new PaymentRequestDatamodel();
	}

	/* populate rejection reasons assigned */
	public void populateRejectReasons() {
		RejectReasonsService rs = new RejectReasonsService();
		try {
			rejectedReasons = rs.locateReasonsSelectedByTargetKeyClassAndProcess(paymentrequest.getId(), paymentrequest.getClass().getName(), getSessionUI().getTask().getWorkflowProcess());
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	/**
	 * Insert PaymentRequest into database
	 * @author TechFinium
	 * @see PaymentRequest
	 */
	public void paymentrequestInsert() {
		try {
			service.create(this.paymentrequest);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			paymentrequestInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/* Populate rejection reasons for selection */
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons>  l                    = null;
		try {
			l = rejectReasonsService.findByProcess(getSessionUI().getTask().getWorkflowProcess());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public void finalApproveWorkflow() throws Exception {
		service.finalApproveWorkflow(paymentrequest, getSessionUI().getActiveUser(), getSessionUI().getTask());
		redirectToDashboard();
	}

	public void finalRejectWorkflow() throws Exception {
		if (selectedRejectionReasons.isEmpty()) {
			throw new Exception("Provide Atleast one rejection reason before proceeding.");
		}
		service.finalRejectWorkflow(paymentrequest, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectionReasons);
		redirectToDashboard();
	}

	/**
	 * Update PaymentRequest in database
	 * @author TechFinium
	 * @see PaymentRequest
	 */
	public void paymentrequestUpdate() {
		try {
			service.update(this.paymentrequest);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			paymentrequestInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete PaymentRequest from database
	 * @author TechFinium
	 * @see PaymentRequest
	 */
	public void paymentrequestDelete() {
		try {
			service.delete(this.paymentrequest);
			prepareNew();
			paymentrequestInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of PaymentRequest
	 * @author TechFinium
	 * @see PaymentRequest
	 */
	public void prepareNew() {
		paymentrequest = new PaymentRequest();
	}

	/*
	 * public List<SelectItem> getPaymentRequestDD() { List<SelectItem> l =new ArrayList<SelectItem>(); l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------")); paymentrequestInfo(); for (PaymentRequest ug : paymentrequestList) { // l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 * @param desc
	 * the desc
	 * @return the list
	 */
	public List<PaymentRequest> complete(String desc) {
		List<PaymentRequest> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<PaymentRequest> getPaymentRequestList() {
		return paymentrequestList;
	}

	public void setPaymentRequestList(List<PaymentRequest> paymentrequestList) {
		this.paymentrequestList = paymentrequestList;
	}

	public PaymentRequest getPaymentrequest() {
		return paymentrequest;
	}

	public void setPaymentrequest(PaymentRequest paymentrequest) {
		this.paymentrequest = paymentrequest;
	}

	public List<PaymentRequest> getPaymentRequestfilteredList() {
		return paymentrequestfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * @author TechFinium
	 * @param paymentrequestfilteredList
	 * the new paymentrequestfilteredList list
	 * @see PaymentRequest
	 */
	public void setPaymentRequestfilteredList(List<PaymentRequest> paymentrequestfilteredList) {
		this.paymentrequestfilteredList = paymentrequestfilteredList;
	}

	public LazyDataModel<PaymentRequest> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<PaymentRequest> dataModel) {
		this.dataModel = dataModel;
	}

	public ActiveContracts getActiveContracts() {
		return activeContracts;
	}

	public void setActiveContracts(ActiveContracts activeContracts) {
		this.activeContracts = activeContracts;
	}

	public List<RejectReasons> getSelectedRejectionReasons() {
		return selectedRejectionReasons;
	}

	public void setSelectedRejectionReasons(List<RejectReasons> selectedRejectionReasons) {
		this.selectedRejectionReasons = selectedRejectionReasons;
	}

	public List<RejectReasons> getRejectedReasons() {
		return rejectedReasons;
	}

	public void setRejectedReasons(List<RejectReasons> rejectedReasons) {
		this.rejectedReasons = rejectedReasons;
	}

}
