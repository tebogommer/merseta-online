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

import haj.com.bean.RemittanceAdviceInfoBean;
import haj.com.bean.RemittanceAdviceOutstandingTransBean;
import haj.com.bean.RemittanceAdvicePaymentsReturnedBean;
import haj.com.bean.RemittanceAdviceRecentAdjustmentsBean;
import haj.com.bean.RemittanceAdviceRecentPaymentsBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.BankingDetails;
import haj.com.entity.Blank;
import haj.com.entity.Company;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.gptransations.ArrayOfInfo;
import haj.com.gptransations.ArrayOfRecentPayments;
import haj.com.service.BankingDetailsService;
import haj.com.service.CompanyService;
import haj.com.service.RemittanceAdviceService;

@ManagedBean(name = "companyFinanceReportUI")
@ViewScoped
public class CompanyFinanceReportUI extends AbstractUI {

	/* Entity Level */
	private Company selectedCompany = null;
	private Company selectedCompanySdfReport = null;
	private BankingDetails bankingDetails = null;
	
	/* Web service objects */
	private Vendor vendor = null;
	private ArrayOfInfo arrayOfInfo = null;
	private ArrayOfRecentPayments arrayOfPayments = null;
//	private ArrayOfRecentAdjustments arrayOfAdjustments = null;
//	private ArrayOfPaymentReturns arrayOfPaymentReturns = null;
	
	/* Version two of web services in reporting format */
	private List<RemittanceAdviceOutstandingTransBean> 	outstandingTransList	= new ArrayList<>();
	private List<RemittanceAdvicePaymentsReturnedBean> 	paymentsReturnedList	= new ArrayList<>();
	private List<RemittanceAdviceRecentAdjustmentsBean> adjustmentsList			= new ArrayList<>();
	private List<RemittanceAdviceRecentPaymentsBean> 	paymentsList			= new ArrayList<>();
	private List<RemittanceAdviceInfoBean> 				infoList				= new ArrayList<>();
	
	/* Service Level */
	private CompanyService companyService = new CompanyService();
	private BankingDetailsService bankingDetailsService = new BankingDetailsService();
	
	/* Lazy Data Models */
	private LazyDataModel<Company> dataModelComapny;
	
	/* Array lists */
	private List<BankingDetails> companyApprovedBankingDetails;
	private List<BankingDetails> bankingDetailsList;
	
	/* Booleans  and VARS */
	private boolean autoRunCalls = false;
	private boolean gpCallRan = false;
	private boolean arrayOfInfoRan = false;
	private boolean arrayOfPaymentsRan = false;
	private boolean arrayOfAdjustmentsRan = false;
	private boolean arrayOfPaymentReturnsRan = false;
	private boolean outstandingTransListRan = false;
	private boolean paymentsReturnedListRan = false;
	private boolean adjustmentsListRan = false;
	private boolean paymentsListRan = false;
	private boolean infoListRan = false;
	private String telephoneFormat = HAJConstants.TELPHONE_FORMAT;
	private String cellphoneFormat = HAJConstants.CELLPHONE_FORMAT;
	private String faxNumberFormat = HAJConstants.FAX_NUMBER_FORMAT;
	
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
	 * Initialize method to get all Blank and prepare a for a create of a new
	 * Blank.
	 *
	 * @author TechFinium
	 * @throws Exception
	 *             the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		populateCompanies();
	}
	
	private void populateCompanies() throws Exception{
		companyInfo();
	}
	
	public void downloadReport() {
		try {
			RemittanceAdviceService.instance().generateRemittanceAdvice(selectedCompany);
			selectedCompany = null;
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void companyInfo() {
		dataModelComapny = new LazyDataModel<Company>() {
			private static final long serialVersionUID = 1L;
			private List<Company> companyList = new ArrayList<>();
			@Override
			public List<Company> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null) {
						sortField = "companyStatus";
						sortOrder = SortOrder.DESCENDING;
					}
					companyList = companyService.allCompany2(Company.class, first, pageSize, sortField, sortOrder, filters);
					dataModelComapny.setRowCount(companyService.count(Company.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return companyList;
			}
			@Override
			public Object getRowKey(Company object) {
				return object.getId();
			}
			@Override
			public Company getRowData(String rowKey) {
				for (Company obj : companyList) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void enableDisableAutoRunCalls(){
		try {
			if (autoRunCalls) {
				addInfoMessage("Auto Calls Enabled");
			}else {
				addInfoMessage("Auto Calls Disabled");
			}
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void selectCompany(){
		try {
			selectedCompanySdfReport = null;
			populateBankingDetailsList();
			if (autoRunCalls) {
				populateVendorFromGP();
				populateArrayOfPayments();
				populateArrayOfAdjustments();
				populateArrayOfPaymentReturns();
			}else {
				clearCallResults();
			}
			addInfoMessage("Company Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void selectCompanySdfReport(){
		try {
			clearCallResults();
			selectedCompany = null;
			bankingDetailsList = bankingDetailsService.findByCompanyLatest(selectedCompanySdfReport);
			populateVendorFromGPSdfCompany();
			populateOutstandingTransListReport(selectedCompanySdfReport.getLevyNumber());		
			populatePaymentsReturnedListReport(selectedCompanySdfReport.getLevyNumber());
			populateAdjustmentsListReport(selectedCompanySdfReport.getLevyNumber());
			populatePaymentsListReport(selectedCompanySdfReport.getLevyNumber());
			populateInfoListReport(selectedCompanySdfReport.getLevyNumber());
			addInfoMessage("Company Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void populateOutstandingTransListReport(String levyNumber) throws Exception{
		outstandingTransListRan = true;
		outstandingTransList = RemittanceAdviceService.instance().populateRemittanceAdviceOutstandingTransBean(levyNumber);
	}
	
	private void populatePaymentsReturnedListReport(String levyNumber) throws Exception{
		paymentsReturnedListRan = true;
		paymentsReturnedList = RemittanceAdviceService.instance().populateRemittanceAdvicePaymentsReturnedBean(levyNumber);
	}
	
	private void populateAdjustmentsListReport(String levyNumber) throws Exception{
		adjustmentsListRan = true;
		adjustmentsList = RemittanceAdviceService.instance().populateRemittanceAdviceRecentAdjustmentsBean(levyNumber);
	}
	
	private void populatePaymentsListReport(String levyNumber) throws Exception{
		paymentsListRan = true;
		paymentsList = RemittanceAdviceService.instance().populateRemittanceAdviceRecentPaymentsBean(levyNumber);
	}
	
	private void populateInfoListReport(String levyNumber) throws Exception{
		infoListRan = true;
		infoList = RemittanceAdviceService.instance().populateRemittanceAdviceInfoBean(levyNumber);
	}
	
	private void populateVendorFromGPSdfCompany() throws Exception{
		bankingDetails = bankingDetailsService.findByCompanyLates(selectedCompanySdfReport);
		if (bankingDetails == null) {
			bankingDetails = new BankingDetails();
		}
		companyApprovedBankingDetails = new ArrayList<>();
		if (bankingDetailsService.findByCompanyCount(selectedCompanySdfReport) > 1) {
			companyApprovedBankingDetails = bankingDetailsService.findByCompany(selectedCompanySdfReport);
			companyApprovedBankingDetails.remove(bankingDetails);
		} else if (bankingDetails.getId() == null && bankingDetailsService.findByCompanyCount(selectedCompanySdfReport) > 0) {
			companyApprovedBankingDetails = bankingDetailsService.findByCompany(selectedCompanySdfReport);
		}
		this.vendor = bankingDetailsService.getGPDetailsForLNumber(selectedCompanySdfReport.getLevyNumber(), bankingDetails.getBankAccNumber());
	}
	
	public void closeView(){
		try {
			selectedCompany = null;
			selectedCompanySdfReport = null;
			clearCallResults();
			outstandingTransList = null;
			paymentsReturnedList = null;
			adjustmentsList = null;
			paymentsList = null;
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	private void clearCallResults() throws Exception{
		// clear GP information
		gpCallRan = false;
		vendor = null;
		
		// clear transactions objects
		arrayOfInfoRan = false;
		arrayOfInfo = null;
		arrayOfPaymentsRan = false;
		arrayOfPayments = null;
		arrayOfAdjustmentsRan = false;
//		arrayOfAdjustments = null;
		arrayOfPaymentReturnsRan = false;
//		arrayOfPaymentReturns = null;
		outstandingTransListRan = false;
		paymentsReturnedListRan = false;
		adjustmentsListRan = false;
		paymentsListRan = false;
	}

	/**
	 * Populates approved banking details assigned to company
	 * @throws Exception
	 */
	private void populateBankingDetailsList() throws Exception{
//		bankingDetailsList = bankingDetailsService.findByCompanyLatest(selectedCompany);
		bankingDetailsList = bankingDetailsService.findByCompanyLatest(selectedCompany);
	}
	
	public void runGpCall(){
		try {
			populateVendorFromGP();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Populates GP vendor information against company
	 */
	private void populateVendorFromGP() throws Exception{
		gpCallRan = true;
		bankingDetails = bankingDetailsService.findByCompanyLates(selectedCompany);
		if (bankingDetails == null) {
			bankingDetails = new BankingDetails();
		}
		companyApprovedBankingDetails = new ArrayList<>();
		if (bankingDetailsService.findByCompanyCount(selectedCompany) > 1) {
			companyApprovedBankingDetails = bankingDetailsService.findByCompany(selectedCompany);
			companyApprovedBankingDetails.remove(bankingDetails);
		} else if (bankingDetails.getId() == null && bankingDetailsService.findByCompanyCount(selectedCompany) > 0) {
			companyApprovedBankingDetails = bankingDetailsService.findByCompany(selectedCompany);
		}
		this.vendor = bankingDetailsService.getGPDetailsForLNumber(selectedCompany.getLevyNumber(), bankingDetails.getBankAccNumber());
	}
	
	public void runArrayOfInfoCall(){
		try {
			populateArrayOfInfo();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void populateArrayOfInfo() throws Exception{
		arrayOfInfoRan = true;
//		arrayOfInfo = GPTransactionsService.instance().getInfo(selectedCompany.getLevyNumber());
	}
	
	public void runArrayOfPaymentsCall(){
		try {
			populateArrayOfPayments();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void populateArrayOfPayments() throws Exception {
		arrayOfPaymentsRan = true;
//		arrayOfPayments = GPTransactionsService.instance().getPayments(selectedCompany.getLevyNumber());
	}
	
	public void runArrayOfAdjustmentsCall(){
		try {
			populateArrayOfAdjustments();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void populateArrayOfAdjustments() throws Exception {
		arrayOfAdjustmentsRan = true;
//		arrayOfAdjustments = GPTransactionsService.instance().getAjustments(selectedCompany.getLevyNumber());
	}
	
	public void runArrayOfPaymentReturnsCall(){
		try {
			populateArrayOfPaymentReturns();
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void populateArrayOfPaymentReturns() throws Exception {
		arrayOfPaymentReturnsRan = true;
//		arrayOfPaymentReturns = GPTransactionsService.instance().getPaymentReturns(selectedCompany.getLevyNumber());
	}
	
	public void runOutstandingTransListReport(){
		try {
			populateOutstandingTransListReport(selectedCompany.getLevyNumber());
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runPaymentsReturnedListReport(){
		try {
			populatePaymentsReturnedListReport(selectedCompany.getLevyNumber());
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runAdjustmentsListReport(){
		try {
			populateAdjustmentsListReport(selectedCompany.getLevyNumber());
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void runPaymentsListReport(){
		try {
			populatePaymentsListReport(selectedCompany.getLevyNumber());
			addInfoMessage("Action Complete");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/* getters and setters */
	public Company getSelectedCompany() {
		return selectedCompany;
	}

	public void setSelectedCompany(Company selectedCompany) {
		this.selectedCompany = selectedCompany;
	}

	public BankingDetails getBankingDetails() {
		return bankingDetails;
	}

	public void setBankingDetails(BankingDetails bankingDetails) {
		this.bankingDetails = bankingDetails;
	}

	public Vendor getVendor() {
		return vendor;
	}

	public void setVendor(Vendor vendor) {
		this.vendor = vendor;
	}

	public LazyDataModel<Company> getDataModelComapny() {
		return dataModelComapny;
	}

	public void setDataModelComapny(LazyDataModel<Company> dataModelComapny) {
		this.dataModelComapny = dataModelComapny;
	}

	public List<BankingDetails> getCompanyApprovedBankingDetails() {
		return companyApprovedBankingDetails;
	}

	public void setCompanyApprovedBankingDetails(List<BankingDetails> companyApprovedBankingDetails) {
		this.companyApprovedBankingDetails = companyApprovedBankingDetails;
	}

	public List<BankingDetails> getBankingDetailsList() {
		return bankingDetailsList;
	}

	public void setBankingDetailsList(List<BankingDetails> bankingDetailsList) {
		this.bankingDetailsList = bankingDetailsList;
	}

	public boolean isAutoRunCalls() {
		return autoRunCalls;
	}

	public void setAutoRunCalls(boolean autoRunCalls) {
		this.autoRunCalls = autoRunCalls;
	}

	public boolean isGpCallRan() {
		return gpCallRan;
	}

	public void setGpCallRan(boolean gpCallRan) {
		this.gpCallRan = gpCallRan;
	}

	public ArrayOfInfo getArrayOfInfo() {
		return arrayOfInfo;
	}

	public void setArrayOfInfo(ArrayOfInfo arrayOfInfo) {
		this.arrayOfInfo = arrayOfInfo;
	}

	public boolean isArrayOfInfoRan() {
		return arrayOfInfoRan;
	}

	public void setArrayOfInfoRan(boolean arrayOfInfoRan) {
		this.arrayOfInfoRan = arrayOfInfoRan;
	}

	public boolean isArrayOfPaymentsRan() {
		return arrayOfPaymentsRan;
	}

	public void setArrayOfPaymentsRan(boolean arrayOfPaymentsRan) {
		this.arrayOfPaymentsRan = arrayOfPaymentsRan;
	}

	public boolean isArrayOfAdjustmentsRan() {
		return arrayOfAdjustmentsRan;
	}

	public void setArrayOfAdjustmentsRan(boolean arrayOfAdjustmentsRan) {
		this.arrayOfAdjustmentsRan = arrayOfAdjustmentsRan;
	}

	public String getTelephoneFormat() {
		return telephoneFormat;
	}

	public String getCellphoneFormat() {
		return cellphoneFormat;
	}

	public String getFaxNumberFormat() {
		return faxNumberFormat;
	}

	public ArrayOfRecentPayments getArrayOfPayments() {
		return arrayOfPayments;
	}

	public void setArrayOfPayments(ArrayOfRecentPayments arrayOfPayments) {
		this.arrayOfPayments = arrayOfPayments;
	}

//	public ArrayOfRecentAdjustments getArrayOfAdjustments() {
//		return arrayOfAdjustments;
//	}
//
//	public void setArrayOfAdjustments(ArrayOfRecentAdjustments arrayOfAdjustments) {
//		this.arrayOfAdjustments = arrayOfAdjustments;
//	}
//
//	public ArrayOfPaymentReturns getArrayOfPaymentReturns() {
//		return arrayOfPaymentReturns;
//	}
//
//	public void setArrayOfPaymentReturns(ArrayOfPaymentReturns arrayOfPaymentReturns) {
//		this.arrayOfPaymentReturns = arrayOfPaymentReturns;
//	}

	public BankingDetailsService getBankingDetailsService() {
		return bankingDetailsService;
	}

	public void setBankingDetailsService(BankingDetailsService bankingDetailsService) {
		this.bankingDetailsService = bankingDetailsService;
	}

	public void setTelephoneFormat(String telephoneFormat) {
		this.telephoneFormat = telephoneFormat;
	}

	public void setCellphoneFormat(String cellphoneFormat) {
		this.cellphoneFormat = cellphoneFormat;
	}

	public void setFaxNumberFormat(String faxNumberFormat) {
		this.faxNumberFormat = faxNumberFormat;
	}

	public boolean isArrayOfPaymentReturnsRan() {
		return arrayOfPaymentReturnsRan;
	}

	public void setArrayOfPaymentReturnsRan(boolean arrayOfPaymentReturnsRan) {
		this.arrayOfPaymentReturnsRan = arrayOfPaymentReturnsRan;
	}

	public Company getSelectedCompanySdfReport() {
		return selectedCompanySdfReport;
	}

	public void setSelectedCompanySdfReport(Company selectedCompanySdfReport) {
		this.selectedCompanySdfReport = selectedCompanySdfReport;
	}

	public List<RemittanceAdviceOutstandingTransBean> getOutstandingTransList() {
		return outstandingTransList;
	}

	public void setOutstandingTransList(List<RemittanceAdviceOutstandingTransBean> outstandingTransList) {
		this.outstandingTransList = outstandingTransList;
	}

	public List<RemittanceAdvicePaymentsReturnedBean> getPaymentsReturnedList() {
		return paymentsReturnedList;
	}

	public void setPaymentsReturnedList(List<RemittanceAdvicePaymentsReturnedBean> paymentsReturnedList) {
		this.paymentsReturnedList = paymentsReturnedList;
	}

	public List<RemittanceAdviceRecentAdjustmentsBean> getAdjustmentsList() {
		return adjustmentsList;
	}

	public void setAdjustmentsList(List<RemittanceAdviceRecentAdjustmentsBean> adjustmentsList) {
		this.adjustmentsList = adjustmentsList;
	}

	public List<RemittanceAdviceRecentPaymentsBean> getPaymentsList() {
		return paymentsList;
	}

	public void setPaymentsList(List<RemittanceAdviceRecentPaymentsBean> paymentsList) {
		this.paymentsList = paymentsList;
	}

	public boolean isOutstandingTransListRan() {
		return outstandingTransListRan;
	}

	public void setOutstandingTransListRan(boolean outstandingTransListRan) {
		this.outstandingTransListRan = outstandingTransListRan;
	}

	public boolean isPaymentsReturnedListRan() {
		return paymentsReturnedListRan;
	}

	public void setPaymentsReturnedListRan(boolean paymentsReturnedListRan) {
		this.paymentsReturnedListRan = paymentsReturnedListRan;
	}

	public boolean isAdjustmentsListRan() {
		return adjustmentsListRan;
	}

	public void setAdjustmentsListRan(boolean adjustmentsListRan) {
		this.adjustmentsListRan = adjustmentsListRan;
	}

	public boolean isPaymentsListRan() {
		return paymentsListRan;
	}

	public void setPaymentsListRan(boolean paymentsListRan) {
		this.paymentsListRan = paymentsListRan;
	}

	public List<RemittanceAdviceInfoBean> getInfoList() {
		return infoList;
	}

	public void setInfoList(List<RemittanceAdviceInfoBean> infoList) {
		this.infoList = infoList;
	}

	public boolean isInfoListRan() {
		return infoListRan;
	}

	public void setInfoListRan(boolean infoListRan) {
		this.infoListRan = infoListRan;
	}

}
