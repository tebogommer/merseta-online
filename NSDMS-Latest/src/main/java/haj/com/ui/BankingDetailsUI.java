package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;

import haj.com.constants.HAJConstants;
import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.SDFCompany;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.BankingDetailsService;
import haj.com.service.CompanyService;
import haj.com.service.RejectReasonsChildService;
import haj.com.service.TasksService;
import haj.com.service.lookup.RejectReasonsService;

// TODO: Auto-generated Javadoc
/**
 * The Class BankingDetailsUI.
 */
@ManagedBean(name = "bankingdetailsUI")
@ViewScoped
public class BankingDetailsUI extends AbstractUI {

	/** The service. */
	private BankingDetailsService service = new BankingDetailsService();
	private CompanyService companyService = new CompanyService();

	/** The bankingdetails list. */
	private List<BankingDetails> bankingdetailsList = null;

	/** The bankingdetailsfiltered list. */
	private List<BankingDetails> bankingdetailsfilteredList = null;

	/** The bankingdetails. */
	private BankingDetails bankingdetails = null;

	/** The company. */
	private Company company;

	/** The data model. */
	private LazyDataModel<BankingDetails> dataModel;

	/** The minimum size for bank account number */
	public Long MIN_BANK_ACCOUNT_NUMBER = HAJConstants.MIN_BANK_ACCOUNT_NUMBER;

	/** The maximum size for bank account number */
	public Long MAX_BANK_ACCOUNT_NUMBER = HAJConstants.MAX_BANK_ACCOUNT_NUMBER;

	/** The minimum size for bank branch number */
	public Long MIN_BANK_BRANCH_NUMBER = HAJConstants.MIN_BANK_BRANCH_NUMBER;

	/** The maximum size for bank branch number */
	public Long MAX_BANK_BRANCH_NUMBER = HAJConstants.MAX_BANK_BRANCH_NUMBER;

	/** The maximum size for bank holder */
	public Long MAX_BANK_HOLDER = HAJConstants.MAX_BANK_HOLDER;

	private RejectReasons rejectReason;

	private List<RejectReasons> selectedRejectReason;

	private String rejectReasonS;
	private Boolean areOriginalRequired;

	private RejectReasonsChildService rejectReasonsService = new RejectReasonsChildService();
	private Vendor vendor;

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
	 * Initialize method to get all BankingDetails and prepare a for a create of a
	 * new BankingDetails.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see BankingDetails
	 */
	private void runInit() throws Exception {
		// this.details = service.bySDF(getSessionUI().getTask().getTargetKey());
		if (getSessionUI().getTask() != null && getSessionUI().getTask().getWorkflowProcess() == ConfigDocProcessEnum.BANKING_DETAIL_MANAGEMENT) {
			getSessionUI().setTask(TasksService.instance().findByKey(getSessionUI().getTask().getId()));
			bankingdetails = service.findByKey(getSessionUI().getTask().getTargetKey());
			this.company = bankingdetails.getCompany();
			// bankingdetailsInfo();
			// check if employee
			if (getSessionUI().isEmployee()) {
				populateVendorFromGP();
			}
		} else {
			prepareNew();
			bankingdetailsInfo();
		}
	}

	private void populateVendorFromGP() {
		try {
			this.vendor = service.getGPDetailsForLNumberAndCreateIfApp(company.getLevyNumber(), bankingdetails.getBankAccNumber());
			if (this.vendor == null) {
				this.vendor = service.getGPDetailsForLNumber(company.getLevyNumber(), bankingdetails.getBankAccNumber());
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void validateVendor() throws Exception{
		System.out.println("BankingDetailsUI:: finalApproveTask():: 2222222222222");
		this.vendor = service.getGPDetailsForLNumberAndCreateIfApp(company.getLevyNumber(), bankingdetails.getBankAccNumber());
	}

	/**
	 * Get all BankingDetails for data table.
	 *
	 * @author TechFinium
	 * @see BankingDetails
	 */
	public void bankingdetailsInfo() {

		dataModel = new LazyDataModel<BankingDetails>() {

			private static final long serialVersionUID = 1L;
			private List<BankingDetails> retorno = new ArrayList<BankingDetails>();

			@Override
			public List<BankingDetails> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

				try {
					if (company != null) {
						filters.put("companyID", company.getId());
						retorno = service.allBankingDetails(first, pageSize, sortField, sortOrder, filters, company);
						dataModel.setRowCount(service.count(BankingDetails.class, filters, company));
					}
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(BankingDetails obj) {
				return obj.getId();
			}

			@Override
			public BankingDetails getRowData(String rowKey) {
				for (BankingDetails obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
				}
				return null;
			}

		};

	}
	
	private void validateSDF(Company company) throws Exception {
		SDFCompany primarySDF = companyService.findPrimarySDF(company);
		if (primarySDF == null) {
			throw new Exception("Unable to locate Primary SDF for: " + company.getLevyNumber());
		}
	}


	/**
	 * Insert BankingDetails into database.
	 *
	 * @author TechFinium
	 * @see BankingDetails
	 */
	public void bankingdetailsInsert() {
		try {
			bankingdetails.setCompany(company);
			service.create(this.bankingdetails);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			bankingdetailsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void completeTask() {
		try {
			service.completeTask(bankingdetails, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void signOffTask() {
		try {
			if (bankingdetails.getConfirmationDate() == null) bankingdetails.setConfirmationDate(getNow());
			service.completeTaskSignOff(bankingdetails, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectDetails() {
		try {
			validateSDF(bankingdetails.getCompany());
			if (selectedRejectReason != null && selectedRejectReason.size() == 0) {
				throw new Exception("Please select a reject reason");
			}
			service.rejectDetails(bankingdetails, getSessionUI().getActiveUser(), getSessionUI().getTask(), selectedRejectReason);
			if (selectedRejectReason != null) {
				for (RejectReasons rejectReason : selectedRejectReason) {
					if (rejectReason != null) rejectReasonsService.create(new RejectReasonsChild(rejectReason, this.bankingdetails.getCompany(), bankingdetails, getSessionUI().getTask(), rejectReasonS));
				}
			}
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void approveTask() {
		try {
			service.approveTask(bankingdetails, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void finalApproveTask() {
		try {
			System.out.println("BankingDetailsUI:: finalApproveTask():: 11111111111111");
			validateVendor();
			System.out.println("BankingDetailsUI:: finalApproveTask():: #############:: vendor:: "+vendor.toString());
			if (vendor == null) {
				throw new Exception("GP Vendor is curently not populated, contact support with Levy Number.");
			}
			service.approveTask(bankingdetails, getSessionUI().getActiveUser(), getSessionUI().getTask());
			service.determineActionForGP(bankingdetails, getSessionUI().getActiveUser(), vendor);
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			if (!(e.getMessage().contains("SOAP Fault"))) {
				super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
			else {
				System.out.println("BankingDetailsUI:: finalApproveTask():: $$$$$$$$$$$$$$:: GP Service is not working");
				super.ajaxRedirectToDashboard();
			}
		}
	}

	public void approveTaskApproval() {
		try {
			if (areOriginalRequired != null && areOriginalRequired) bankingdetails.setAreOriginalRequired(areOriginalRequired);
			service.approveTaskApproval(bankingdetails, getSessionUI().getActiveUser(), getSessionUI().getTask());
			if (bankingdetails.getNotificationSentRegardingOriginal() != null && !bankingdetails.getNotificationSentRegardingOriginal()) {
				service.sendNotice(areOriginalRequired != null && areOriginalRequired, bankingdetails);
			}
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void rejectTask() {
		try {
			service.rejectTask(bankingdetails, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void sendBackForAwaitingDocument(){
		try {
			validateSDF(bankingdetails.getCompany());
			if (areOriginalRequired != null && areOriginalRequired) bankingdetails.setAreOriginalRequired(areOriginalRequired);
			bankingdetails.setNotificationSentRegardingOriginal(true);
			service.sendNotice(areOriginalRequired != null && areOriginalRequired, bankingdetails);
			service.rejectTask(bankingdetails, getSessionUI().getActiveUser(), getSessionUI().getTask());
			super.ajaxRedirectToDashboard();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update BankingDetails in database.
	 *
	 * @author TechFinium
	 * @see BankingDetails
	 */
	public void bankingdetailsUpdate() {
		try {
			service.update(this.bankingdetails);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			bankingdetailsInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(ConfigDocProcessEnum.BANKING_DETAIL_MANAGEMENT);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	/**
	 * Delete BankingDetails from database.
	 *
	 * @author TechFinium
	 * @see BankingDetails
	 */
	public void bankingdetailsDelete() {
		try {
			service.delete(this.bankingdetails);
			prepareNew();
			bankingdetailsInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of BankingDetails.
	 *
	 * @author TechFinium
	 * @see BankingDetails
	 */
	public void prepareNew() {
		bankingdetails = new BankingDetails();
	}

	/*
	 * public List<SelectItem> getBankingDetailsDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * bankingdetailsInfo(); for (BankingDetails ug : bankingdetailsList) { //
	 * l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return
	 * l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<BankingDetails> complete(String desc) {
		List<BankingDetails> l = null;
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
	 * Gets the banking details list.
	 *
	 * @return the banking details list
	 */
	public List<BankingDetails> getBankingDetailsList() {
		return bankingdetailsList;
	}

	/**
	 * Sets the banking details list.
	 *
	 * @param bankingdetailsList
	 *            the new banking details list
	 */
	public void setBankingDetailsList(List<BankingDetails> bankingdetailsList) {
		this.bankingdetailsList = bankingdetailsList;
	}

	/**
	 * Gets the bankingdetails.
	 *
	 * @return the bankingdetails
	 */
	public BankingDetails getBankingdetails() {
		return bankingdetails;
	}

	/**
	 * Sets the bankingdetails.
	 *
	 * @param bankingdetails
	 *            the new bankingdetails
	 */
	public void setBankingdetails(BankingDetails bankingdetails) {
		this.bankingdetails = bankingdetails;
	}

	/**
	 * Gets the banking detailsfiltered list.
	 *
	 * @return the banking detailsfiltered list
	 */
	public List<BankingDetails> getBankingDetailsfilteredList() {
		return bankingdetailsfilteredList;
	}

	/**
	 * Prepare auto complete data.
	 *
	 * @author TechFinium
	 * @param bankingdetailsfilteredList
	 *            the new bankingdetailsfilteredList list
	 * @see BankingDetails
	 */
	public void setBankingDetailsfilteredList(List<BankingDetails> bankingdetailsfilteredList) {
		this.bankingdetailsfilteredList = bankingdetailsfilteredList;
	}

	/**
	 * Gets the data model.
	 *
	 * @return the data model
	 */
	public LazyDataModel<BankingDetails> getDataModel() {
		return dataModel;
	}

	/**
	 * Sets the data model.
	 *
	 * @param dataModel
	 *            the new data model
	 */
	public void setDataModel(LazyDataModel<BankingDetails> dataModel) {
		this.dataModel = dataModel;
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

	public Long getMIN_BANK_ACCOUNT_NUMBER() {
		return MIN_BANK_ACCOUNT_NUMBER;
	}

	public void setMIN_BANK_ACCOUNT_NUMBER(Long mIN_BANK_ACCOUNT_NUMBER) {
		MIN_BANK_ACCOUNT_NUMBER = mIN_BANK_ACCOUNT_NUMBER;
	}

	public Long getMAX_BANK_ACCOUNT_NUMBER() {
		return MAX_BANK_ACCOUNT_NUMBER;
	}

	public void setMAX_BANK_ACCOUNT_NUMBER(Long mAX_BANK_ACCOUNT_NUMBER) {
		MAX_BANK_ACCOUNT_NUMBER = mAX_BANK_ACCOUNT_NUMBER;
	}

	public Long getMIN_BANK_BRANCH_NUMBER() {
		return MIN_BANK_BRANCH_NUMBER;
	}

	public void setMIN_BANK_BRANCH_NUMBER(Long mIN_BANK_BRANCH_NUMBER) {
		MIN_BANK_BRANCH_NUMBER = mIN_BANK_BRANCH_NUMBER;
	}

	public Long getMAX_BANK_BRANCH_NUMBER() {
		return MAX_BANK_BRANCH_NUMBER;
	}

	public void setMAX_BANK_BRANCH_NUMBER(Long mAX_BANK_BRANCH_NUMBER) {
		MAX_BANK_BRANCH_NUMBER = mAX_BANK_BRANCH_NUMBER;
	}

	public Long getMAX_BANK_HOLDER() {
		return MAX_BANK_HOLDER;
	}

	public void setMAX_BANK_HOLDER(Long mAX_BANK_HOLDER) {
		MAX_BANK_HOLDER = mAX_BANK_HOLDER;
	}

	public RejectReasons getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(RejectReasons rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getRejectReasonS() {
		return rejectReasonS;
	}

	public void setRejectReasonS(String rejectReasonS) {
		this.rejectReasonS = rejectReasonS;
	}

	public List<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(List<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public Boolean getAreOriginalRequired() {
		return areOriginalRequired;
	}

	public void setAreOriginalRequired(Boolean areOriginalRequired) {
		this.areOriginalRequired = areOriginalRequired;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

}
