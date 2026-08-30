package haj.com.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;

import haj.com.bean.RemittanceAdviceGpInformationBean;
import haj.com.bean.RemittanceAdviceInfoBean;
import haj.com.bean.RemittanceAdviceOutstandingTransBean;
import haj.com.bean.RemittanceAdvicePaymentsReturnedBean;
import haj.com.bean.RemittanceAdviceRecentAdjustmentsBean;
import haj.com.bean.RemittanceAdviceRecentPaymentsBean;
import haj.com.dao.BlankDAO;
import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.framework.AbstractService;
import haj.com.gptransations.ArrayOfInfo;
import haj.com.gptransations.ArrayOfRecentPayments;
import haj.com.gptransations.Info;
import haj.com.gptransations.RecentPayments;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import za.co.merseta.nsdms.externalsystems.greatplains.transactions.adapters.OutStandingTransactionsAdapter;
import za.co.merseta.nsdms.externalsystems.greatplains.transactions.adapters.RecentTransactionsAdapter;

/**
 * The Class RemittanceAdviceService.
 * Used for the finance reporting section: REMITTANCE ADVICE
 */
public class RemittanceAdviceService extends AbstractService {
	
	private static RemittanceAdviceService remittanceAdviceService;
	public static synchronized RemittanceAdviceService instance() {
		if (remittanceAdviceService == null) {
			remittanceAdviceService = new RemittanceAdviceService();
		}
		return remittanceAdviceService;
	}
	
	/** The dao. */
	private BlankDAO dao = new BlankDAO();

	/* Service levels */
	private BankingDetailsService bankingDetailsService = null;
	
	/**
	 * Section for report: Outstanding Transactions
	 * No API yet
	 * 
	 * @see RemittanceAdviceOutstandingTransBean
	 * 
	 * @param entityId 
	 * 			the entity id
	 * @return entryList 
	 * 			the results array list of RemittanceAdvicePaymentsReturnedBean 
	 * @throws Exception
	 * 			Generic Exception
	 */
	public List<RemittanceAdviceOutstandingTransBean> populateRemittanceAdviceOutstandingTransBean(String entityId) throws Exception{
		List<RemittanceAdviceOutstandingTransBean> entryList = new ArrayList<>();
		
		RemittanceAdviceOutstandingTransBean bean = new RemittanceAdviceOutstandingTransBean();
		bean.setType("Type");
		bean.setDate(getSynchronizedDate());
		bean.setNumber("Number");
		bean.setDescription("Description");
		bean.setAmount(00.00);
		bean.setBalance(00.00);
		bean.setStatusActive(true);
		bean.setStatusHold(false);
		bean.setHoldReason("Hold Reason");
		entryList.add(bean);
		
		return entryList;
	}
	
	/**
	 * Section for report: Payments Returned by the Bank
	 * 
	 * @see RemittanceAdvicePaymentsReturnedBean
	 * @see ArrayOfPaymentReturns
	 * @see PaymentReturns
	 * 
	 * 
	 * @param entityId 
	 * 			the entity id
	 * @return entryList 
	 * 			the results array list of RemittanceAdvicePaymentsReturnedBean 
	 * @throws Exception
	 * 			Generic Exception
	 */
	public List<RemittanceAdvicePaymentsReturnedBean> populateRemittanceAdvicePaymentsReturnedBean(String entityId) throws Exception{
		List<RemittanceAdvicePaymentsReturnedBean> entryList = new ArrayList<>();
//		ArrayOfPaymentReturns arrayOfPaymentReturns = GPTransactionsService.instance().getPaymentReturns(entityId);
//		if (arrayOfPaymentReturns != null && arrayOfPaymentReturns.getPaymentReturns() != null) {
//			for (PaymentReturns result : arrayOfPaymentReturns.getPaymentReturns()) {
//				RemittanceAdvicePaymentsReturnedBean beanPaymentReturn = new RemittanceAdvicePaymentsReturnedBean();
//				beanPaymentReturn.setType(result.getType());
//				beanPaymentReturn.setDate(result.getDateAsDateObject());
//				beanPaymentReturn.setNumber(result.getNumber());
//				beanPaymentReturn.setAmount(result.getAmountAsDoubleValue());
//				beanPaymentReturn.setReason(result.getReason());
//				entryList.add(beanPaymentReturn);
//			}
//		}
		return entryList;
	}
	
	/**
	 * Section for report: Recent Adjustments
	 * 
	 * @see RemittanceAdviceRecentAdjustmentsBean
	 * @see ArrayOfRecentAdjustments
	 * @see RecentAdjustments
	 * 
	 * @param entityId 
	 * 			the entity id
	 * @return entryList 
	 * 			the results array list of RemittanceAdviceRecentAdjustmentsBean 
	 * @throws Exception
	 * 			Generic Exception
	 */
	public List<RemittanceAdviceRecentAdjustmentsBean> populateRemittanceAdviceRecentAdjustmentsBean(String entityId) throws Exception{
		List<RemittanceAdviceRecentAdjustmentsBean> entryList = new ArrayList<>();
//		ArrayOfRecentAdjustments arrayOfAdjustments = GPTransactionsService.instance().getAjustments(entityId);
//		if (arrayOfAdjustments != null && arrayOfAdjustments.getRecentAdjustments() != null) {
//			for (RecentAdjustments result : arrayOfAdjustments.getRecentAdjustments()) {
//				// Payment Entry
//				RemittanceAdviceRecentAdjustmentsBean beanPayment = new RemittanceAdviceRecentAdjustmentsBean();
//				if (result.getPdoctype() != null && result.getPdoctype().trim().equalsIgnoreCase("Invoice")) {
//					beanPayment.setType("Applied to: " + result.getPdoctype());
//				} else if(result.getPdoctype() != null && result.getPdoctype().trim().equalsIgnoreCase("Misc Chg")) {
//					beanPayment.setType("Applied to: " + result.getPdoctype());
//				} else {
//					beanPayment.setType(result.getPdoctype());
//				}
////				beanPayment.setType(result.getPdoctype());
//				beanPayment.setDate(result.getPDateAsDateObject());
//				beanPayment.setNumber(result.getPNumber());
//				beanPayment.setDescription(result.getPDescription());
//				beanPayment.setAmountDescription("Original");
//				beanPayment.setAmount(result.getPamountAsDoubleValue());
//				beanPayment.setBalance(result.getPbalanceAsDoubleValue());
//				entryList.add(beanPayment);
//				
//				// Applied To: Invoice Entry
//				RemittanceAdviceRecentAdjustmentsBean beanAppliedInvoice = new RemittanceAdviceRecentAdjustmentsBean();
//				if (result.getAnType() != null && result.getAnType().trim().equalsIgnoreCase("Invoice")) {
//					beanAppliedInvoice.setType("Applied to: " + result.getAnType());
//				} else if(result.getAnType() != null && result.getAnType().trim().equalsIgnoreCase("Misc Chg")) {
//					beanAppliedInvoice.setType("Applied to: " + result.getAnType());
//				} else {
//					beanAppliedInvoice.setType(result.getAnType());
//				}
////				beanAppliedInvoice.setType(result.getAnType());
//				beanAppliedInvoice.setDate(result.getADateAsDateObject());
//				beanAppliedInvoice.setNumber(result.getAnumber());
//				beanAppliedInvoice.setDescription(result.getADescription());
//				beanAppliedInvoice.setAmountDescription("Applied");
//				beanAppliedInvoice.setAmount(result.getAamountAsDoubleValue());
//				beanAppliedInvoice.setBalance(result.getAbalanceAsDoubleValue());
//				entryList.add(beanAppliedInvoice);
//			}
//		}
		return entryList;
	}
	
	/**
	 * Section for report: Recent Payments
	 * 
	 * @see RemittanceAdviceRecentPaymentsBean
	 * @see ArrayOfRecentPayments
	 * @see RecentPayments
	 * 
	 * @param entityId 
	 * 			the entity id
	 * @return entryList 
	 * 			the results array list of RemittanceAdviceRecentPaymentsBean 
	 * @throws Exception
	 * 			Generic Exception
	 */
	public List<RemittanceAdviceRecentPaymentsBean> populateRemittanceAdviceRecentPaymentsBean(String entityId) throws Exception{
		List<RemittanceAdviceRecentPaymentsBean> entryList = new ArrayList<>();
		ArrayOfRecentPayments arrayOfPayments = new RecentTransactionsAdapter().getRecentTransactions(entityId);
		String previousInvoiceNumber = "";
		if (arrayOfPayments != null && arrayOfPayments.getRecentPayments() != null) {
			for (RecentPayments result : arrayOfPayments.getRecentPayments()) {
				// Payment Entry
				RemittanceAdviceRecentPaymentsBean beanPayment = new RemittanceAdviceRecentPaymentsBean();
				if (result.getPdoctype() != null && result.getPdoctype().trim().equalsIgnoreCase("Invoice")) {
					beanPayment.setType("Applied to: " +result.getPdoctype());
					beanPayment.setDisplaySpacer(true);
				} else if(result.getPdoctype() != null && result.getPdoctype().trim().equalsIgnoreCase("Misc Chg")) {
					beanPayment.setType("Applied to: " +result.getPdoctype());
					beanPayment.setDisplaySpacer(true);
				} else {
					if (result.getPdoctype() != null) {
						beanPayment.setType(result.getPdoctype());
					}
					beanPayment.setDisplaySpacer(false);
				}
//					beanPayment.setType(result.getPdoctype());
				if (result.getPDateReturnAsDate() != null) {
					beanPayment.setDate(result.getPDateReturnAsDate());
				}
				if (result.getPNumber() != null) {
					beanPayment.setNumber(result.getPNumber());
				}
				if (result.getPDescription() != null && !result.getPDescription().equals(" ")) {
					beanPayment.setDescription(result.getPDescription());
				}
				beanPayment.setAmountDescription("Original");
				beanPayment.setAmount(result.getPamountAsDoubleValue());
				beanPayment.setAppliedAmount(result.getPApliedAmountAsDoubleValue());
				beanPayment.setBalance(result.getPbalanceAsDoubleValue());
				if (beanPayment.getNumber() != null) {
					if (!beanPayment.getNumber().trim().equalsIgnoreCase(previousInvoiceNumber)) {
						entryList.add(beanPayment);
						previousInvoiceNumber = beanPayment.getNumber().trim();
					} 
				}
				
				
				// Applied To: Invoice Entry
				RemittanceAdviceRecentPaymentsBean beanAppliedInvoice = new RemittanceAdviceRecentPaymentsBean();
				if (result.getAnType() != null && result.getAnType().trim().equalsIgnoreCase("Invoice")) {
					beanAppliedInvoice.setType("Applied to: " +result.getAnType());
					beanPayment.setDisplaySpacer(true);
				} else if(result.getAnType() != null && result.getAnType().trim().equalsIgnoreCase("Misc Chg")) {
					beanAppliedInvoice.setType("Applied to: " +result.getAnType());
					beanPayment.setDisplaySpacer(true);
				} else {
					if (result.getAnType() != null) {
						beanAppliedInvoice.setType(result.getAnType());
					}
					beanPayment.setDisplaySpacer(false);
				}
//					beanAppliedInvoice.setType(result.getAnType());
				beanAppliedInvoice.setDate(result.getADateReturnAsDate());
				if (result.getAnumber() != null) {
					beanAppliedInvoice.setNumber(result.getAnumber());
				}
				if (result.getADescription() != null) {
					beanAppliedInvoice.setDescription(result.getADescription());
				}
				beanAppliedInvoice.setAmountDescription("Applied");
				beanAppliedInvoice.setAmount(result.getDocAmountAsDoubleValue());
				beanAppliedInvoice.setAppliedAmount(result.getAamountAsDoubleValue());
				beanAppliedInvoice.setBalance(result.getAbalanceAsDoubleValue());
				entryList.add(beanAppliedInvoice);
			}
			
		}
		return entryList;
	}
	
	public List<RemittanceAdviceInfoBean> populateRemittanceAdviceInfoBean(String entityId) throws Exception{
		List<RemittanceAdviceInfoBean> entryList = new ArrayList<>();
		ArrayOfInfo arrayOfInfo = new OutStandingTransactionsAdapter().getOutstandingTransactions(entityId);
		if (arrayOfInfo != null && arrayOfInfo.getInfo() != null) {
			for (Info result : arrayOfInfo.getInfo()) {
				RemittanceAdviceInfoBean bean = new RemittanceAdviceInfoBean();
				bean.setVendID(result.getVendID());
				bean.setVendname(result.getVendname());
				bean.setVoucherNumber(result.getVoucherNumber());
				bean.setDocType(result.getDocType());
				bean.setDocDate(result.getDocDateReturnAsDate());
				bean.setDocNumber(result.getDocNumber());
				bean.setDocDescription(result.getDocDescription());
				bean.setDocFunctionalAmount(result.getDocFunctionalAmountAsDoubleValue());
				bean.setDockBalance1(result.getDockBalance1AsDoubleValue());
				bean.setDocHold1(result.getDocHold1());
				if (result.getDocHold1() != null && !result.getDocHold1().trim().isEmpty()) {
					if (result.getDocHold1().trim().equalsIgnoreCase("0")) {
						bean.setDocHoldMessage("No");
					} else if (result.getDocHold1().trim().equals("1")) {
						bean.setDocHoldMessage("Yes");
					} else {
						bean.setDocHoldMessage("N/A");
					}
				} else {
					bean.setDocHoldMessage("N/A");
				}
				bean.setDocHoldReason1(result.getDocHoldReason1());
				entryList.add(bean);
			}
		}
		return entryList;
	}
	
	public void generateRemittanceAdvice(Company company) throws Exception {
		List<RemittanceAdviceRecentPaymentsBean> remittanceAdviceRecentPaymentsBean = populateRemittanceAdviceRecentPaymentsBean(company.getLevyNumber());
		List<RemittanceAdviceInfoBean> remittanceAdviceInfoBean = populateRemittanceAdviceInfoBean(company.getLevyNumber());
		List<BankingDetails> bankingDetails = findCompanyApprovedBankingDetails(company);
		List<RemittanceAdviceGpInformationBean> remittanceAdviceGpInformationBean = populateRemittanceAdviceGpInformationBeanByCompany(company);
//		remittanceAdviceGpInformationBean.add(new RemittanceAdviceGpInformationBean("Yes", "Company Name", "********0000", "Status", "Hold Reason"));
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");
		params.put("company", company);	
		params.put("address", company.getResidentialAddress());
		params.put("remittanceAdviceRecentPaymentsBean", new JRBeanCollectionDataSource(remittanceAdviceRecentPaymentsBean));
		params.put("remittanceAdviceInfoBean", new JRBeanCollectionDataSource(remittanceAdviceInfoBean));
		params.put("bankingDetails", new JRBeanCollectionDataSource(bankingDetails));
		params.put("remittanceAdviceGpInformationBean", new JRBeanCollectionDataSource(remittanceAdviceGpInformationBean));
		JasperService.instance().createReportFromDBtoServletOutputStream("RemittanceAdviceReport.jasper", params,"RemittanceAdviceReport.pdf");
	}
	
	public List<RemittanceAdviceGpInformationBean> populateRemittanceAdviceGpInformationBeanByCompany(Company company) {
		List<RemittanceAdviceGpInformationBean> entryList = new ArrayList<>();
		try {
			if (bankingDetailsService == null) {
				bankingDetailsService = new BankingDetailsService();
			}
			Vendor vendor = null;
			
			BankingDetails bankingDetails = bankingDetailsService.findByCompanyLates(company);
			if (bankingDetails == null) {
				bankingDetails = new BankingDetails();
			}
			List<BankingDetails> companyApprovedBankingDetails = new ArrayList<>();
			if (bankingDetailsService.findByCompanyCount(company) > 1) {
				companyApprovedBankingDetails = bankingDetailsService.findByCompany(company);
				companyApprovedBankingDetails.remove(bankingDetails);
			} else if (bankingDetails.getId() == null && bankingDetailsService.findByCompanyCount(company) > 0) {
				companyApprovedBankingDetails = bankingDetailsService.findByCompany(company);
			}
			vendor = bankingDetailsService.getGPDetailsForLNumber(company.getLevyNumber(), bankingDetails.getBankAccNumber());
			
			if (vendor == null) {
				entryList.add(new RemittanceAdviceGpInformationBean("No", "N/A", "N/A", "N/A", "N/A"));
			} else {
				RemittanceAdviceGpInformationBean bean = new RemittanceAdviceGpInformationBean();
				bean.setEntityOnGp("Yes");
				bean.setName(vendor.getName());
				if (vendor.getTaxRegistrationNumber() != null && !vendor.getTaxRegistrationNumber().isEmpty()) {
					bean.setAccountNumber(vendor.getMaskedBankAccNumber());
				} else {
					bean.setAccountNumber("N/A");
				}
				if (vendor.getDetailsOnHold() != null) {
					if (vendor.getDetailsOnHold()) {
						bean.setStatus("On Hold");
					} else {
						bean.setStatus("Not On Hold");
					}
				} else {
					bean.setStatus("N/A");
				}
				if (vendor.getUserDefined1() != null && !vendor.getUserDefined1().trim().isEmpty()) {
					bean.setHoldReason(vendor.getUserDefined1().trim());
				} else {
					bean.setHoldReason("No Reason Found");
				}
				entryList.add(bean);
			}
		} catch (Exception e) {
			entryList.add(new RemittanceAdviceGpInformationBean("N/A", "Error retrieving information. Contact Support!", "N/A", "N/A", "N/A"));
			logger.fatal(e);
		}
		return entryList;
	}
	
	private void populateVendorFromGPSdfCompany(Company company) throws Exception{
		BankingDetailsService bankingDetailsService = new BankingDetailsService();
		List<BankingDetails> companyApprovedBankingDetails;
		List<BankingDetails> bankingDetailsList;
		Vendor vendor = null;
		BankingDetails bankingDetails = bankingDetailsService.findByCompanyLates(company);
		if (bankingDetails == null) {
			bankingDetails = new BankingDetails();
		}
		companyApprovedBankingDetails = new ArrayList<>();
		if (bankingDetailsService.findByCompanyCount(company) > 1) {
			companyApprovedBankingDetails = bankingDetailsService.findByCompany(company);
			companyApprovedBankingDetails.remove(bankingDetails);
		} else if (bankingDetails.getId() == null && bankingDetailsService.findByCompanyCount(company) > 0) {
			companyApprovedBankingDetails = bankingDetailsService.findByCompany(company);
		}
		vendor = bankingDetailsService.getGPDetailsForLNumber(company.getLevyNumber(), bankingDetails.getBankAccNumber());
	}
	
	private List<BankingDetails> findCompanyApprovedBankingDetails(Company company) throws Exception{
		if (bankingDetailsService == null) {
			bankingDetailsService = new BankingDetailsService();
		}
		List<BankingDetails> bankingDetailsList = bankingDetailsService.findByCompanyLatest(company);
		return bankingDetailsList;
	}
}