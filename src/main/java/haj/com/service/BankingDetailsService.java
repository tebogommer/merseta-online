package haj.com.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.io.output.ByteArrayOutputStream;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.primefaces.model.SortOrder;
import com.microsoft.schemas.dynamics._2006._01.CompanyKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ArrayOfVendorAddress;
import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorAddress;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorAddressKey;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorClassKey;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorKey;

import haj.com.bean.BankingDetailsReportBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.BankingDetailsDAO;
import haj.com.entity.Address;
import haj.com.entity.BankingDetails;
import haj.com.entity.Company;
import haj.com.entity.CompanyUsers;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.RejectReasonsChild;
import haj.com.entity.SDFCompany;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.gpservices.GPAddressTypeEnum;
import haj.com.gpservices.GPVendorClassEnum;
import haj.com.utils.GenericUtility;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.GPRequestHandler;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter.CreateVendorAdapter;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter.GetVendorByKeyAdapter;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter.UpdateVendorAdapter;

import static haj.com.constants.HAJConstants.MAX_BANK_ACCOUNT_NUMBER;
import static haj.com.constants.HAJConstants.MAX_BANK_BRANCH_NUMBER;

// TODO: Auto-generated Javadoc
/**
 * The Class BankingDetailsService.
 */
public class BankingDetailsService extends AbstractService {
	/** The dao. */
	private BankingDetailsDAO dao = new BankingDetailsDAO();
	private ConfigDocService configDocService = new ConfigDocService();
	private CompanyUsersService companyUsersService = new CompanyUsersService();

	/**
	 * Get all BankingDetails.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.BankingDetails}
	 * @throws Exception
	 *             the exception
	 * @see BankingDetails
	 */
	public List<BankingDetails> allBankingDetails() throws Exception {
		return dao.allBankingDetails();
	}

	/**
	 * Create or update BankingDetails.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see BankingDetails
	 */
	public void create(BankingDetails entity) throws Exception {
		if (entity.getId() == null) {
			bankingDetailsValidiation(entity);
			updateBankDetails(entity);
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void createNoUpdate(BankingDetails entity) throws Exception {
		bankingDetailsValidiation(entity);
		updateBankDetails(entity);
		this.dao.create(entity);
	}

	public void bankingDetailsValidiation(BankingDetails entity) throws Exception {
		entity.setBankAccNumber(entity.getBankAccNumber().trim());
		if (entity.getBankAccNumber().trim().matches(HAJConstants.BANKING_DETAILS_REGEX_TEXT) || entity.getBankAccNumber().trim().contains(" ")) {
			throw new Exception("Bank account number can not contain the following: characters / letters, underscores, hyphens and spaces. Only numbers are accepted.");
		}
		entity.setBranchCode(entity.getBranchCode().trim());
		if (entity.getBranchCode().trim().matches(HAJConstants.BANKING_DETAILS_REGEX_TEXT) || entity.getBranchCode().trim().contains(" ")) {
			throw new Exception("Bank branch code can not contain the following: characters / letters, underscores, hyphens and spaces. Only numbers are accepted.");
		}
		if(entity.getBranchCode().length() < MAX_BANK_BRANCH_NUMBER.intValue())
			throw new Exception("Bank branch code should be " + MAX_BANK_BRANCH_NUMBER.intValue() + "digits.");
	}

	public void createNoUpdate(BankingDetails entity, Users users) throws Exception {
		List<CompanyUsers> cu = companyUsersService.findByCompanyResponsibility(entity.getCompany(), ConfigDocProcessEnum.BANKING_DETAIL_MANAGEMENT);
		if (cu.size() == 0) {
			throw new Exception("You dont have any contacts to sign off banking details");
		}
		bankingDetailsValidiation(entity);
		updateBankDetails(entity);
		this.dao.create(entity);
		String desc = "Banking details for " + entity.getCompany().getCompanyName() + " (" + entity.getCompany().getLevyNumber() + ") have been uploaded/changed and require approval.";
		TasksService.instance().createOneTaskCompanyUsers(cu, entity.getClass().getName(), entity.getId(), desc, users, true, true, null, ConfigDocProcessEnum.BANKING_DETAIL_MANAGEMENT, null);
	}

	public void sendNotice(boolean areOriginalRequired, BankingDetails bankingDetails) throws Exception {
		logger.info(bankingDetails.getCompany().getCompanyName() + "\t" + areOriginalRequired);
		if (areOriginalRequired) {
			List<CompanyUsers> cu = companyUsersService.findByCompanyResponsibility(bankingDetails.getCompany(), ConfigDocProcessEnum.BANKING_DETAIL_MANAGEMENT);
			if (cu.size() > 0) {
				for (CompanyUsers companyUsers : cu) {
					sendBankingDeatildDocRequirementsEmail(companyUsers.getUser(), bankingDetails.getCompany());
				}
			}
			SDFCompany sdfCompany = findPrimarySDF(bankingDetails.getCompany());
			sendBankingDeatildDocRequirementsEmail(sdfCompany.getSdf(), bankingDetails.getCompany());
		} else {
			SDFCompany sdfCompany = findPrimarySDF(bankingDetails.getCompany());
			sendBankingDeatildDocNotRequiredEmail(sdfCompany.getSdf(), bankingDetails.getCompany(), getSynchronizedDate());
		}

	}

	public SDFCompany findPrimarySDF(Company company) throws Exception {
		return dao.findPrimarySDF(company);
	}

	/**
	 * Update BankingDetails.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see BankingDetails
	 */
	public void update(BankingDetails entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete BankingDetails.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see BankingDetails
	 */
	public void delete(BankingDetails entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.BankingDetails}
	 * @throws Exception
	 *             the exception
	 * @see BankingDetails
	 */
	public BankingDetails findByKey(long id) throws Exception {
		return prepareDocs(ConfigDocProcessEnum.BANKING_DETAIL_MANAGEMENT, dao.findByKey(id), CompanyUserTypeEnum.Company);
	}

	public BankingDetails prepareDocs(ConfigDocProcessEnum configDocProcess, BankingDetails bankingdetails, CompanyUserTypeEnum companyUserType) throws Exception {
		bankingdetails.setDocs(DocService.instance().searchByBankingDetails(bankingdetails));
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(bankingdetails, configDocProcess, companyUserType);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				bankingdetails.getDocs().add(new Doc(a, bankingdetails));
			});
		}

		return bankingdetails;
	}

	public void prepareNewDocs(ConfigDocProcessEnum configDocProcess, BankingDetails bankingdetails, CompanyUserTypeEnum companyUserType) throws Exception {

		if (bankingdetails.getId() != null) {
			bankingdetails.setOldDocs(DocService.instance().searchByBankingDetails(bankingdetails));
		}
		bankingdetails.setDocs(new ArrayList<Doc>());
		List<ConfigDoc> l = configDocService.findByProcess(configDocProcess, companyUserType);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				bankingdetails.getDocs().add(new Doc(a, bankingdetails));
			});
		}
	}

	/**
	 * Find BankingDetails by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.BankingDetails}
	 * @throws Exception
	 *             the exception
	 * @see BankingDetails
	 */
	public List<BankingDetails> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load BankingDetails.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.BankingDetails}
	 * @throws Exception
	 *             the exception
	 */
	public List<BankingDetails> allBankingDetails(int first, int pageSize) throws Exception {
		return dao.allBankingDetails(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of BankingDetails for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the BankingDetails
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(BankingDetails.class);
	}

	/**
	 * Lazy load BankingDetails with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param startingAt
	 *            the starting at
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @param company
	 *            the company
	 * @return a list of {@link haj.com.entity.BankingDetails} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<BankingDetails> allBankingDetails(int startingAt, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		String hql = "select o from BankingDetails o left join fetch o.company left join fetch o.bank where o.company.id = :companyID";
		return (List<BankingDetails>) dao.sortAndFilterWhere(startingAt, pageSize, sortField, sortOrder, filters, hql);
	}

	/**
	 * Get count of BankingDetails for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            BankingDetails class
	 * @param filters
	 *            the filters
	 * @param company
	 *            the company
	 * @return Number of rows in the BankingDetails entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<BankingDetails> entity, Map<String, Object> filters, Company company) throws Exception {
		String hql = "select count(o) from BankingDetails o where o.company.id = :companyID";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Find by company.
	 *
	 * @param company
	 *            the company
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<BankingDetails> findByCompany(Company company) throws Exception {
		return resolveRejectReason(dao.findByCompany(company));
	}

	private List<BankingDetails> resolveRejectReason(List<BankingDetails> list) throws Exception {
		RejectReasonsChildService rejectReasonsChildService = new RejectReasonsChildService();
		for(BankingDetails bankingDetails:list) {
			if(bankingDetails.getStatus() == ApprovalEnum.Rejected) {
				List<RejectReasonsChild>lists = rejectReasonsChildService.findByCompanyBankingDetails(bankingDetails.getCompany(), bankingDetails);
				List<RejectReasons>rejectReasons = new ArrayList<>();
				if(lists.size() > 0) {
					for(RejectReasonsChild r:lists) {
						rejectReasons.add(r.getRejectReasons());
					}
					bankingDetails.setRejectReasons(resolve(rejectReasons));
				}
			}
		}
		return list;
	}
	
	public String resolve(List<RejectReasons>lists) {
		String results = "";
		int count = 1;
		for (RejectReasons rejectReasons : lists) {
			if (count == lists.size()) {
				results+= rejectReasons.getDescription();
			}else {
				results+= rejectReasons.getDescription() + ", ";
			}
			count++;
		}
		return results;
	}

	/**
	 * Find by company count.
	 *
	 * @param company
	 *            the company
	 * @return the long
	 * @throws Exception
	 *             the exception
	 */
	public long findByCompanyCount(Company company) throws Exception {
		return dao.findByCompanyCount(company);
	}

	/**
	 * Find by company lates.
	 *
	 * @param company
	 *            the company
	 * @return the banking details
	 * @throws Exception
	 *             the exception
	 */
	public BankingDetails findByCompanyLates(Company company) throws Exception {
		List<BankingDetails> l = dao.findByCompanyLates(company);
		return (l != null && l.size() > 0) ? prepareDocs(ConfigDocProcessEnum.BANKING_DETAIL_MANAGEMENT, l.get(0), CompanyUserTypeEnum.Company) : null;
	}

	public List<BankingDetails> findByCompanyLatestApproved(Company company) throws Exception {
		return dao.findByCompanyLatestApproved(company);
	}
	
	public List<BankingDetails> findByCompanyLatest(Company company) throws Exception {
		return dao.findByCompanyLatest(company);
	}

	public BankingDetails findByCompanyBeforeLatest(Company company, BankingDetails bankingDetails) throws Exception {
		List<BankingDetails> l = dao.findByCompanyBeforeLatest(company, bankingDetails);
		return (l != null && l.size() > 0) ? l.get(0) : null;
	}

	public void completeTaskSignOff(BankingDetails bankingDetails, Users user, Tasks task) throws Exception {
		bankingDetails.setStatus(ApprovalEnum.PendingApproval);
		update(bankingDetails);
		String desc = "Banking details has been signed off for Company: " + bankingDetails.getCompany().getCompanyName() + " (" + bankingDetails.getCompany().getLevyNumber() + "). Please review.";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, user, bankingDetails.getId(), bankingDetails.getClass().getName(), true, ConfigDocProcessEnum.BANKING_DETAIL_MANAGEMENT, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		TasksService.instance().completeTask(task);
	}

	public void rejectDetails(BankingDetails bankingDetails, Users user, Tasks task, List<RejectReasons> selectedRejectReason) throws Exception {
		bankingDetails.setStatus(ApprovalEnum.Rejected);
		bankingDetails.setApprovalDate(getSynchronizedDate());
		bankingDetails.setApprovalUser(user);
		update(bankingDetails);
		SDFCompany sdf = SDFCompanyService.instance().findPrimarySDF(bankingDetails.getCompany());
		String desc = "Banking details for " + bankingDetails.getCompany().getCompanyName() + " (" + bankingDetails.getCompany().getLevyNumber() + ") were rejected. Please review. ";
		if (selectedRejectReason != null) {
			desc += "<br/>Reason/s: ";
			for (RejectReasons rejectReason : selectedRejectReason) {
				desc += rejectReason.getDescription() + "<br/>";
			}
		}
		GenericUtility.sendMail(sdf.getSdf().getEmail(), "Banking Details Rejected: " + bankingDetails.getCompany().getLevyNumber(), desc, null);
		TasksService.instance().completeTask(task);
	}

	public void completeTask(BankingDetails bankingDetails, Users user, Tasks task) throws Exception {
		String extraInfo = "";
		TasksService.instance().findNextInProcessAndCreateTask("Banking Details was checked and submitted by the admin", user, bankingDetails.getId(), bankingDetails.getClass().getName(), true, task, MailDef.instance(MailEnum.SdfForApproval, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
	}

	public void approveTask(BankingDetails bankingDetails, Users user, Tasks task) throws Exception {
		bankingDetails.setStatus(ApprovalEnum.Approved);
		bankingDetails.setApprovalDate(getSynchronizedDate());
		bankingDetails.setApprovalUser(user);
		update(bankingDetails);
		TasksService.instance().completeTask(task);
	}

	public void determineActionForGP(BankingDetails bankingDetails, Users user, Vendor vendor) throws Exception {
		if (bankingDetails.getBankAccNumber() != vendor.getTaxRegistrationNumber()) {
			vendor.setTaxIdentificationNumber(bankingDetails.getBranchCode());
			vendor.setTaxRegistrationNumber(bankingDetails.getBankAccNumber());
			vendor.setIsOnHold(false);
			vendor.setUserDefined1("");
			UpdateVendorAdapter adapter = new UpdateVendorAdapter();
			adapter.updateVendor(vendor);
		}
	}

	public void updateBankningDetailsOnGP(BankingDetails bankingDetails, Users user, Vendor vendor) throws Exception {
		if (vendor != null) {
			vendor.setTaxIdentificationNumber(bankingDetails.getBranchCode());
			vendor.setTaxRegistrationNumber(bankingDetails.getBankAccNumber());
			vendor.setIsOnHold(false);
			vendor.setUserDefined1("");
			UpdateVendorAdapter adapter = new UpdateVendorAdapter();
			adapter.updateVendor(vendor);
		} else {
			throw new Exception("Vendor does not exist on GP");
		}
	}

	public void updateBankningDetailsOnGPHoldStatus(Users user, Vendor vendor, boolean onHold) throws Exception {
		if (vendor != null) {
			if (onHold) {
				vendor.setIsOnHold(true);
				vendor.setUserDefined1("NO BANK DETAILS");
			} else {
				vendor.setIsOnHold(false);
				vendor.setUserDefined1("");
			}
			UpdateVendorAdapter adapter = new UpdateVendorAdapter();
			adapter.updateVendor(vendor);
		} else {
			throw new Exception("Vendor does not exist on GP");
		}
	}
	
	public void updateBankningDetailsOnGPHoldStatusWithReason(Users user, Vendor vendor, boolean onHold, String reason) throws Exception {
		if (vendor != null) {
			if (onHold) {
				vendor.setIsOnHold(true);
				vendor.setUserDefined1(reason);
			} else {
				vendor.setIsOnHold(false);
				vendor.setUserDefined1("");
			}
			UpdateVendorAdapter adapter = new UpdateVendorAdapter();
			adapter.updateVendor(vendor);
		} else {
			throw new Exception("Vendor does not exist on GP");
		}
	}

	public void approveTaskApproval(BankingDetails bankingDetails, Users user, Tasks task) throws Exception {
		bankingDetails.setStatus(ApprovalEnum.PendingApproval);
		update(bankingDetails);
		String extraInfo = "";
		TasksService.instance().findNextInProcessAndCreateTask("Banking Details was approved", user, bankingDetails.getId(), bankingDetails.getClass().getName(), true, task, MailDef.instance(MailEnum.SdfForApproval, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
	}

	public void rejectTask(BankingDetails bankingDetails, Users user, Tasks task) throws Exception {
		update(bankingDetails);
		String information = "Awaiting original Banking Details for " + bankingDetails.getCompany().getCompanyName() + " (" + bankingDetails.getCompany().getLevyNumber() + ")";
		TasksService.instance().findPreviousInProcessAndCreateTask(information, user, bankingDetails.getId(), bankingDetails.getClass().getName(), true, task, null, null);
		// TasksService.instance().findPreviousInProcessAndCreateTask(information ,
		// user, bankingDetails.getId(), bankingDetails.getClass().getName(), true,
		// task, MailDef.instance(MailEnum.SdfForApproval), null);
	}

	public void sendBankingDeatildDocRequirementsEmail(Users user, Company company) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);

		params.put("call_center_number", HAJConstants.MERSETA_CALL_CENTRE);
		params.put("company_id", company.getId());
		params.put("user_id", user.getId());
		byte[] bites = JasperService.instance().convertJasperReportToByte("Banking_Details_Required_ Final_Reminder.jasper", params);

		String subject = "BANKING DETAILS DOCUMENTATION REQUIRED - " + company.getCompanyName() + " (" + company.getLevyNumber() + ")";
		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The merSETA hereby requires the following original documentation with regards to the banking details of the company:</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<ol style=\"font-size:11.0pt;\";font-family:\"Arial\">" + " <li>An original letter on the company letterhead confirming the company's levy number/s" + " banking details and must be signed by the company Director, CEO or Financial Manager. " + "The letter must reflect the name, designation and signature of the person confirming the "
				+ "information and the levy number of the company together with the e-mail address of the" + " person who has signed the letter.<u> Please note that this letter is not the letter from your banking institution.</u> </li>" + "<br/>" + "<li>An original cancelled cheque or an original current bank statement or letter from the bank that includes the bank stamp.</li>" + "</ol>" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">The documents must be delivered to merSETA Head Office at the following address:</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<b>" + "8 Hillside Road, Metropolitan Park, Block C Parktown, 2193 <br/>" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Please note that the final assessment of the grant application will only be done upon receipt and verification of the original documentation.</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Should you have any queries please do not hesitate to contact the merSETA Call Centre on: " + HAJConstants.MERSETA_CALL_CENTRE + " for assistance. </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";

		GenericUtility.sendMailWithAttachement(user.getEmail(), subject, mssg, bites, "Banking_Details_Required_Final_Reminder.pdf", "pdf", null);

	}

	public void sendBankingDeatildDocNotRequiredEmail(Users emailRecipient, Company company, Date submitionDate) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		JasperService.addLogo(params);

		params.put("call_center_number", HAJConstants.MERSETA_CALL_CENTRE);
		params.put("company_id", company.getId());
		params.put("user_id", emailRecipient.getId());

		params.put("confirmed_date", GenericUtility.sdf5.format(submitionDate));

		byte[] bites = JasperService.instance().convertJasperReportToByte("Banking_Details_Not_Required_ Final_Reminder.jasper", params);

		String subject = "CONFIRMATION OF BANKING DETAILS - " + company.getCompanyName() + " (" + company.getLevyNumber() + ")";
		String mssg = "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + emailRecipient.getFirstName() + " " + emailRecipient.getFirstName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"

				+ "Thank you for submitting the banking details for " + company.getCompanyName() + " on the NSDMS. The merSETA wishes to inform you " + "that you are not required to send original banking detail documentation for the organisation as this " + "documentation has previously been submitted to the merSETA."

				+ "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Should you have any queries please do not hesitate to contact the merSETA Call Centre on: " + HAJConstants.MERSETA_CALL_CENTRE + " for assistance. </p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Yours in Skills Development,</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">merSETA Administration</p>";

		GenericUtility.sendMailWithAttachement(emailRecipient.getEmail(), subject, mssg, bites, "Banking_Details_Not_Required_Final_Reminder.pdf", "pdf", null);

	}

	public Boolean checkBankAccountNumber(String lNumber, String bankAccountNumber) {
		Boolean toReturn = false;
		try {
			GetVendorByKeyAdapter adapter = new GetVendorByKeyAdapter();
			Vendor vendor = adapter.getVendorByKey(lNumber);
			String vBankAccountNumber = vendor.getTaxRegistrationNumber();
			if (vBankAccountNumber != null) {
				bankAccountNumber = bankAccountNumber.replaceAll(" ", "");
				Integer padding = vBankAccountNumber.length() - bankAccountNumber.length();
				bankAccountNumber = StringUtils.repeat('0', padding) + bankAccountNumber;
				if (bankAccountNumber.equals(vBankAccountNumber)) {
					toReturn = true;
				}
			}
		} catch (Exception e) {
			// e.printStackTrace();
		}
		return toReturn;
	}

	public boolean checkBankAccountNumber(BankingDetails bankingDetails) {
		boolean toReturn = true;
		try {
			bankingDetails = dao.findByKey(bankingDetails.getId());

			String lNumber = bankingDetails.getCompany().getLevyNumber();
			String bankAccountNumber = bankingDetails.getBankAccNumber();
			GetVendorByKeyAdapter adapter = new GetVendorByKeyAdapter();
			Vendor vendor = adapter.getVendorByKey(lNumber);
			String vBankAccountNumber = vendor.getTaxRegistrationNumber();
			if (vBankAccountNumber != null) {
				bankAccountNumber = bankAccountNumber.replaceAll(" ", "");
				Integer padding = vBankAccountNumber.length() - bankAccountNumber.length();
				bankAccountNumber = StringUtils.repeat('0', padding) + bankAccountNumber;
				if (bankAccountNumber.equals(vBankAccountNumber)) {
					toReturn = false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return toReturn;
	}

	public Vendor validateGPDetailsForLNumberAndCreateIfApp(String lNumber, String bankAccountNumber) throws Exception {
		GetVendorByKeyAdapter adapter = new GetVendorByKeyAdapter();
		Vendor vendor = adapter.getVendorByKey(lNumber);
		String gpAccountNumber = vendor.getTaxRegistrationNumber();
		if (gpAccountNumber != null) {
			bankAccountNumber = bankAccountNumber.replaceAll(" ", "");
			Integer size = bankAccountNumber.length();
			vendor.setTaxRegistrationNumber(gpAccountNumber.substring(Math.max(0, gpAccountNumber.length() - bankAccountNumber.length())));
		}
		return vendor;
	}

	public Vendor getGPDetailsForLNumber(String lNumber, String bankAccountNumber) {
		Vendor vendor = null;
		if (!HAJConstants.DEV_TEST_PROD.equals("D")) {
			try {
				GetVendorByKeyAdapter adapter = new GetVendorByKeyAdapter();
				vendor = adapter.getVendorByKey(lNumber);
				String gpAccountNumber = vendor.getTaxRegistrationNumber();
				if (gpAccountNumber != null) {
					bankAccountNumber = bankAccountNumber.replaceAll(" ", "");
					Integer size = bankAccountNumber.length();
					vendor.setTaxRegistrationNumber(gpAccountNumber.substring(Math.max(0, gpAccountNumber.length() - bankAccountNumber.length())));
				}
			} catch (Exception e) {
				logger.fatal(e);
			}
		}
		return vendor;
	}

	public Vendor getGPDetailsForLNumberAndCreateIfApp(String lNumber, String bankAccountNumber) {
		System.out.println("BankingDetailsUI:: getGPDetailsForLNumberAndCreateIfApp():: Running Time:: "+LocalDateTime.now());
		System.out.println("BankingDetailsUI:: finalApproveTask():: 33333333333");
		Vendor vendor = new Vendor();
		System.out.println("BankingDetailsUI:: finalApproveTask():: 444444444444");
		if (!HAJConstants.DEV_TEST_PROD.equals("D")) {
			try {
				System.out.println("BankingDetailsUI:: finalApproveTask():: 555555555");
				GetVendorByKeyAdapter adapter = new GetVendorByKeyAdapter();
				vendor = adapter.getVendorByKey(lNumber);
				System.out.println("BankingDetailsUI:: finalApproveTask():: 666666666");
				String gpAccountNumber = vendor.getTaxRegistrationNumber();
				System.out.println("BankingDetailsUI:: finalApproveTask():: 7777777777");
				if (gpAccountNumber != null) {
					System.out.println("BankingDetailsUI:: finalApproveTask():: 8888888888");
					bankAccountNumber = bankAccountNumber.replaceAll(" ", "");
					System.out.println("BankingDetailsUI:: finalApproveTask():: 9999999999");
					Integer size = bankAccountNumber.length();
					System.out.println("BankingDetailsUI:: finalApproveTask():: 0000000000");
					vendor.setTaxRegistrationNumber(gpAccountNumber.substring(Math.max(0, gpAccountNumber.length() - bankAccountNumber.length())));
					System.out.println("BankingDetailsUI:: finalApproveTask():: aaaaaaaa");
				}
				else {
					System.out.println("BankingDetailsUI:: finalApproveTask():: bbbbbbbbb");
					vendor = createVendorVer2(lNumber, bankAccountNumber);
					System.out.println("BankingDetailsUI:: finalApproveTask():: cccccccccc");
				}
			} catch (Exception e) {
				System.out.println("BankingDetailsUI:: finalApproveTask():: ddddddddddddd:: Error Message::");
				System.out.println(e.getMessage());
				System.out.println("BankingDetailsUI:: finalApproveTask():: e.printStackTrace:: ");
				e.printStackTrace();
				if (e.getMessage().contains("Business object not found")) {
					System.out.println("BankingDetailsUI:: finalApproveTask():: eeeeeeeeee");
					System.out.println("Business object not found");
					createVendor(lNumber, bankAccountNumber);
					System.out.println("BankingDetailsUI:: finalApproveTask():: ffffffffff");
				}
			}
		}
		return vendor;
	}
	
	public Vendor getGPDetailsForLNumberNoCreate(String lNumber) {
		Vendor vendor = null;
		if (!HAJConstants.DEV_TEST_PROD.equals("D")) {
			try {
				GetVendorByKeyAdapter adapter = new GetVendorByKeyAdapter();
				vendor = adapter.getVendorByKey(lNumber);
			} catch (Exception e) {
				logger.fatal(e);
			}
		}
		return vendor;
	}

	public void createVendor(String lNumber, String bankAccountNumber) {
		try {
			CompanyService companyService = new CompanyService();
			AddressService addressService = new AddressService();
			Company company = companyService.findByLevyNumber(lNumber);
			if (company != null && company.getLevyNumber() != null && !company.getLevyNumber().isEmpty()) {
				Address residentialAddress = addressService.findByKey(company.getResidentialAddress().getId());
				if (residentialAddress != null) {
					createNewVendor(company, residentialAddress);
				} else {
					throw new Exception("Unable to locate company residentialAddress from DB with L Number: " + lNumber);
				}
			} else {
				throw new Exception("Unable to locate company from DB with L Number: " + lNumber);
			}
			companyService = null;
			addressService = null;
		} catch (Exception e) {
			e.printStackTrace();
			GenericUtility.mailError("Error Creating Vendor (GP Services)", "Error when creating new company/vendor (BankingDetailsService.createVendor(String lNumber, String bankAccountNumber)) for Levy Number: " + lNumber + " and bank account number: " + bankAccountNumber + ". Error: " + e.getMessage());
		}
	}

	private void createNewVendor(Company company, Address residentialAddress) throws Exception {
		VendorKey vendorKey = new VendorKey();
		vendorKey.setId(company.getLevyNumber());

		Vendor vendor = new Vendor();
		vendor.setKey(vendorKey);
		vendor.setName(company.getCompanyName());
		vendor.setIsActive(Boolean.TRUE);
		vendor.setIsOneTime(Boolean.FALSE);

		// sets banking details
		vendor.setTaxIdentificationNumber("");
		vendor.setTaxRegistrationNumber("");
		vendor.setIsOnHold(Boolean.TRUE);
		vendor.setUserDefined1("NO BANK DETAILS");

		ArrayOfVendorAddress addresses = new ArrayOfVendorAddress();
		addresses.getVendorAddress().add(crtAddress(company.getLevyNumber(), residentialAddress));
		vendor.setAddresses(addresses);
		VendorClassKey vendorClassKey = new VendorClassKey();
		String chamberName = GPVendorClassEnum.PLASTICS.getGPName();
		if (company.getSicCode() != null && company.getSicCode().getChamber() != null && company.getSicCode().getChamber().getId() != null && company.getSicCode().getChamber().getGpVendorClass() != null) {
			chamberName = company.getSicCode().getChamber().getGpVendorClass().getGPName();
		} else if (company.getChamber() != null && company.getChamber().getGpVendorClass() != null) {
			chamberName = company.getChamber().getGpVendorClass().getGPName();
		} 
		vendorClassKey.setId(chamberName); 
		CompanyKey companyKey = new CompanyKey();
		companyKey.setId(GPRequestHandler.MERSETA_COMPANY_ID);
		vendorClassKey.setCompanyKey(companyKey);
		vendor.setClassKey(vendorClassKey);
		CreateVendorAdapter adapter= new CreateVendorAdapter();
		adapter.createVendor(vendor);
	}

	public Vendor createVendorVer2(String lNumber, String bankAccountNumber) {
		System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
		Vendor vendor = new Vendor();
		try {
			CompanyService companyService = new CompanyService();
			System.out.println("BBBBBBBBBBBBBBBBBBBBBBBBB");
			AddressService addressService = new AddressService();
			System.out.println("CCCCCCCCCCCCCCCCCCCCCCCCCC");
			Company company = companyService.findByLevyNumber(lNumber);
			System.out.println("DDDDDDDDDDDDDDDDDDDDDDDDDDDDD");
			if (company != null && company.getLevyNumber() != null && !company.getLevyNumber().isEmpty()) {
				System.out.println("EEEEEEEEEEEEEEEEEEEEEEEEEE");
				Address residentialAddress = addressService.findByKey(company.getResidentialAddress().getId());
				System.out.println("FFFFFFFFFFFFFFFFFFFFFFFFFF");
				if (residentialAddress != null) {
					System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
					vendor = createNewVendorVer2(company, residentialAddress);
					System.out.println("rrrrrrrrrrrrrrrrrrrrrrrrrrrr");
				} else {
					System.out.println("qqqqqqqqqqqqqqqqqqqqqqqqq");
					throw new Exception("Unable to locate company residentialAddress from DB with L Number: " + lNumber);
				}
			} else {
				System.out.println("pppppppppppppppppppppppp");
				throw new Exception("Unable to locate company from DB with L Number: " + lNumber);
			}
			companyService = null;
			addressService = null;
		} catch (Exception e) {
			e.printStackTrace();
			GenericUtility.mailError("Error Creating Vendor (GP Services)", "Error when creating new company/vendor (BankingDetailsService.createVendor(String lNumber, String bankAccountNumber)) for Levy Number: " + lNumber + " and bank account number: " + bankAccountNumber + ". Error: " + e.getMessage());
		}
		return vendor;
	}

	private Vendor createNewVendorVer2(Company company, Address residentialAddress) throws Exception {
		System.out.println("GGGGGGGGGGGGGGGGGGGGGGGGGGGGGG");
		VendorKey vendorKey = new VendorKey();
		System.out.println("HHHHHHHHHHHHHHHHHHHHHHHHHHHH");
		vendorKey.setId(company.getLevyNumber());
		System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIII");

		Vendor vendor = new Vendor();
		System.out.println("JJJJJJJJJJJJJJJJJJJJJJJJJ");
		vendor.setKey(vendorKey);
		System.out.println("KKKKKKKKKKKKKKKKKKKKKKKKK");
		vendor.setName(company.getCompanyName());
		System.out.println("LLLLLLLLLLLLLLLLLLL");
		vendor.setIsActive(Boolean.TRUE);
		System.out.println("MMMMMMMMMMMMMMMMMMMMMM");
		vendor.setIsOneTime(Boolean.FALSE);
		System.out.println("NNNNNNNNNNNNNNNNNNNNN");

		// sets banking details
		vendor.setTaxIdentificationNumber("");
		System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOO");
		vendor.setTaxRegistrationNumber("");
		System.out.println("PPPPPPPPPPPPPPPPPPPPPPPPPPP");
		vendor.setIsOnHold(Boolean.TRUE);
		System.out.println("QQQQQQQQQQQQQQQQQQQQQQQQQQ");
		vendor.setUserDefined1("NO BANK DETAILS");
		System.out.println("RRRRRRRRRRRRRRRRRRRRRRRRRRRRRRR");

		ArrayOfVendorAddress addresses = new ArrayOfVendorAddress();
		System.out.println("SSSSSSSSSSSSSSSSSSSSSS");
		addresses.getVendorAddress().add(crtAddress(company.getLevyNumber(), residentialAddress));
		System.out.println("TTTTTTTTTTTTTTTTTTTTT");
		vendor.setAddresses(addresses);
		System.out.println("UUUUUUUUUUUUUUUUUUUUUUUUUU");
		VendorClassKey vendorClassKey = new VendorClassKey();
		System.out.println("VVVVVVVVVVVVVVVVVVVVVVVVVVVVV");
		String chamberName = GPVendorClassEnum.PLASTICS.getGPName();
		System.out.println("WWWWWWWWWWWWWWWWWWWWWWWWWWWWWWW");
		if (company.getSicCode() != null && company.getSicCode().getChamber() != null && company.getSicCode().getChamber().getId() != null && company.getSicCode().getChamber().getGpVendorClass() != null) {
			System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXX");
			chamberName = company.getSicCode().getChamber().getGpVendorClass().getGPName();
			System.out.println("YYYYYYYYYYYYYYYYYYYYYYYYYYYY");
		} else if (company.getChamber() != null && company.getChamber().getGpVendorClass() != null) {
			System.out.println("ZZZZZZZZZZZZZZZZZZZZZZZZ");
			chamberName = company.getChamber().getGpVendorClass().getGPName();
			System.out.println("zzzzzzzzzzzzzzzzzzzzzzzzzzz");
		} 
		System.out.println("yyyyyyyyyyyyyyyyyyyyyyyyyyyy");
		vendorClassKey.setId(chamberName); 
		System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx");
		CompanyKey companyKey = new CompanyKey();
		System.out.println("wwwwwwwwwwwwwwwwwwwwwwwwwww");
		companyKey.setId(GPRequestHandler.MERSETA_COMPANY_ID);
		System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvv");
		vendorClassKey.setCompanyKey(companyKey);
		System.out.println("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
		vendor.setClassKey(vendorClassKey);
		System.out.println("tttttttttttttttttttttttttttt");
		CreateVendorAdapter adapter= new CreateVendorAdapter();
		adapter.createVendor(vendor);
		System.out.println("ssssssssssssssssssssssssss");
		return vendor;
	}
	
	public VendorAddress crtAddress(String vendorId, Address residentailAddress) throws Exception {
		VendorAddress address = new VendorAddress();
		VendorKey vendorKey = new VendorKey();
		vendorKey.setId(vendorId);
		CompanyKey companyKey = new CompanyKey();
		companyKey.setId(GPRequestHandler.MERSETA_COMPANY_ID);
		vendorKey.setCompanyKey(companyKey);
		VendorAddressKey key = new VendorAddressKey();
		key.setCompanyKey(vendorKey.getCompanyKey());
		key.setVendorKey(vendorKey);
		key.setId(GPAddressTypeEnum.POSTAL.getGPName());
		address.setKey(key);
		address.setDeleteOnUpdate(false);
		String city = "";
		if (residentailAddress.getTown() != null) {
			city = residentailAddress.getTown().getDescription();
		}
		address.setCity(city);
		address.setLine1(residentailAddress.getAddressLine1() + " " + residentailAddress.getAddressLine2());
		address.setLine2(residentailAddress.getAddressLine3() + " " + residentailAddress.getAddressLine4());
		address.setPostalCode(residentailAddress.getPostcode());
		address.setName("POSTAL");
		address.setCountryRegion("ZA");
		address.setUPSZone("");
		return address;
	}
	
	public List<BankingDetailsReportBean> populateCompanyBankingDetailsReport() throws Exception {
		return dao.populateCompanyBankingDetailsReport();
	}
	
	public void downloadCompanyBankingDetailsReport() throws Exception{
		Workbook wb = new SXSSFWorkbook(100);
		Sheet sh = wb.createSheet();
		Map<String, Object[]> data = new TreeMap<String, Object[]>();
		Integer counter = 1;
		// headers
		data.put(counter.toString(), new Object[] {"Entity ID", "Company Name", "Company Registration Number", "Trading Name", "Bank Name", "Account Number", "Branch Code", "Account Name", "Banking Details Status", "Approved Date", "Approved By"});
		counter++;
		// data population
		populateDataForBankingDetailsReport(data, counter);
		counter = null;
		Set<String> keyset = data.keySet();
		int rownum = 0;
		for (String key : keyset) {
			Row row = sh.createRow(rownum++);
			Object[] objArr = data.get(key);
			int cellnum = 0;
			for (Object obj : objArr) {
				Cell cell = row.createCell(cellnum++);
				if (obj instanceof String)
					cell.setCellValue((String) obj);
				else if (obj instanceof Integer)
					cell.setCellValue((Integer) obj);
				else if (obj instanceof Double)
					cell.setCellValue((Double) obj);;
			}
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try {
			wb.write(bos);
			byte[] bytes = bos.toByteArray();
			String fileName = "Company Banking Details Report.xlsx";
			JasperService.instance().downloadFileExcel(bytes, fileName);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
		
	private void populateDataForBankingDetailsReport(Map<String, Object[]> data, Integer counter) throws Exception{
		int counterForPopulation = counter;
		List<BankingDetailsReportBean> resultsList = populateCompanyBankingDetailsReport();
		// populate data found into report
		for (BankingDetailsReportBean entry : resultsList) {
			populateDataBankingDetailsReport(data, entry, counterForPopulation);
			counterForPopulation++;
		}
		resultsList.clear();
		counter = counterForPopulation;
	}

	private void populateDataBankingDetailsReport(Map<String, Object[]> data, BankingDetailsReportBean entry, Integer counter) throws Exception{
		String approvedDate = "N/A";
		if (entry.getApprovalDate() != null) {
			approvedDate = HAJConstants.sdfDDMMYYYYHHmm.format(entry.getApprovalDate());
		}
		// "Entity ID", "Company Name", "Company Registration Number", "Trading Name", "Bank Name", "Account Number", "Branch Code", "Account Name", "Banking Details Status", "Approved Date", "Approved By"
		data.put(counter.toString(), new Object[] { entry.getEntityId(), entry.getCompanyName(), entry.getCompanyRegNumber(), entry.getTradingName(), entry.getBankName(), entry.getAccountNumber(), entry.getBranchCode(), entry.getBankHolder(), entry.getBankDetailStatus(), approvedDate, entry.getApprovedBy() });
	}

	public void updateBankDetails(BankingDetails bankingDetails){
		if(bankingDetails.getBankAccNumber().length() != MAX_BANK_ACCOUNT_NUMBER.intValue()){
			bankingDetails.setBankAccNumber(StringUtils.repeat('0', MAX_BANK_ACCOUNT_NUMBER.intValue()-bankingDetails.getBankAccNumber().length()) + bankingDetails.getBankAccNumber());
		}
		if(bankingDetails.getBranchCode().length() != MAX_BANK_BRANCH_NUMBER.intValue()){
			bankingDetails.setBranchCode(StringUtils.repeat('0', MAX_BANK_BRANCH_NUMBER.intValue()-bankingDetails.getBranchCode().length()) + bankingDetails.getBranchCode());
		}
	}
}