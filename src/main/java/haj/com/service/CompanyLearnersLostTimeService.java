package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import com.microsoft.schemas.dynamics.gp._2006._01.ArrayOfPurchaseInvoiceApplyReceipt;

import haj.com.bean.LostTimeReasonBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.CompanyLearnersLostTimeDAO;
import haj.com.entity.Company;
import haj.com.entity.CompanyLearners;
import haj.com.entity.CompanyLearnersChange;
import haj.com.entity.CompanyLearnersLostTime;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.ProcessRoles;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.SDFCompany;
import haj.com.entity.Signoff;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.CreatedByEnum;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.LostTimeReason;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.SignoffByEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.AbstractUI;
import haj.com.service.lookup.RolesService;
import haj.com.utils.GenericUtility;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import static haj.com.constants.HAJConstants.SKILLS_PROGRAM_LIST;
import static haj.com.constants.HAJConstants.SKILLS_SET_LIST;

public class CompanyLearnersLostTimeService extends AbstractService {
	/** The dao. */
	private CompanyLearnersLostTimeDAO dao = new CompanyLearnersLostTimeDAO();
	private ConfigDocService configDocService = new ConfigDocService();
	private CompanyLearnersService companyLearnersService = new CompanyLearnersService();

	/** Instance of service level */
	private static CompanyLearnersLostTimeService companyLearnersLostTimeService;
	public static synchronized CompanyLearnersLostTimeService instance() {
		if (companyLearnersLostTimeService == null) {
			companyLearnersLostTimeService = new CompanyLearnersLostTimeService();
		}
		return companyLearnersLostTimeService;
	}
	
	/**
	 * Populates additional information against CompanyLearnersLostTime
	 * refer to method: populateAdditionalInformation
	 * @param companyLearnersLostTimeList
	 * @return companyLearnersLostTimeList
	 * @throws Exception
	 */
	public List<CompanyLearnersLostTime> populateAdditionalInformationList(List<CompanyLearnersLostTime> companyLearnersLostTimeList) throws Exception{
		for (CompanyLearnersLostTime companyLearnersLostTime : companyLearnersLostTimeList) {
			populateAdditionalInformation(companyLearnersLostTime);
		}
		return companyLearnersLostTimeList;
	}
	
	/**
	 * Populates additional information against CompanyLearnersLostTime
	 * @param companyLearnersLostTime
	 * @return companyLearnersLostTime
	 * @throws Exception
	 */
	private CompanyLearnersLostTime populateAdditionalInformation(CompanyLearnersLostTime companyLearnersLostTime) throws Exception{
		if (companyLearnersLostTime.getCompanyLearners() != null && companyLearnersLostTime.getCompanyLearners().getId() != null) {
			companyLearnersLostTime.setCompanyLearners(companyLearnersService.findByKey(companyLearnersLostTime.getCompanyLearners().getId())) ;
		}
		return companyLearnersLostTime;
	}

	/**
	 * Get all CompanyLearnersLostTime
	 * 
	 * @author TechFinium
	 * @see CompanyLearnersLostTime
	 * @return a list of {@link haj.com.entity.CompanyLearnersLostTime}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearnersLostTime> allCompanyLearnersLostTime() throws Exception {
		return populateAdditionalInformationList(dao.allCompanyLearnersLostTime());
	}

	/**
	 * Create or update CompanyLearnersLostTime.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersLostTime
	 */
	public void create(CompanyLearnersLostTime entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update CompanyLearnersLostTime.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersLostTime
	 */
	public void update(CompanyLearnersLostTime entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete CompanyLearnersLostTime.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersLostTime
	 */
	public void delete(CompanyLearnersLostTime entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.CompanyLearnersLostTime}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersLostTime
	 */
	public CompanyLearnersLostTime findByKey(long id) throws Exception {
		return populateAdditionalInformation(dao.findByKey(id));
	}

	public void prepareNewRegistration(CompanyLearnersLostTime entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.LearnerLostTime));
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(entity.getClass().getName(), entity.getId(), ConfigDocProcessEnum.LearnerLostTime, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = configDocService.findByProcess(ConfigDocProcessEnum.LearnerLostTime, CompanyUserTypeEnum.User);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	/**
	 * Find CompanyLearnersLostTime by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.CompanyLearnersLostTime}
	 * @throws Exception
	 *             the exception
	 * @see CompanyLearnersLostTime
	 */
	public List<CompanyLearnersLostTime> findByName(String desc) throws Exception {
		return populateAdditionalInformationList(dao.findByName(desc));
	}

	/**
	 * Lazy load CompanyLearnersLostTime
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.CompanyLearnersLostTime}
	 * @throws Exception
	 *             the exception
	 */
	public List<CompanyLearnersLostTime> allCompanyLearnersLostTime(int first, int pageSize) throws Exception {
		return populateAdditionalInformationList(dao.allCompanyLearnersLostTime(Long.valueOf(first), pageSize));
	}

	/**
	 * Get count of CompanyLearnersLostTime for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the CompanyLearnersLostTime
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(CompanyLearnersLostTime.class);
	}

	/**
	 * Lazy load CompanyLearnersLostTime with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            CompanyLearnersLostTime class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.CompanyLearnersLostTime} containing
	 *         data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersLostTime> allCompanyLearnersLostTime(Class<CompanyLearnersLostTime> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList((List<CompanyLearnersLostTime>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));

	}

	/**
	 * Get count of CompanyLearnersLostTime for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            CompanyLearnersLostTime class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the CompanyLearnersLostTime entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<CompanyLearnersLostTime> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void completeCompanyLearners(CompanyLearnersLostTime companyLearners, Users user, Tasks tasks) throws Exception {
		String error = "";
		if (companyLearners.getDocs() != null) {
			for (Doc doc : companyLearners.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName() + " for " + companyLearners.getCompanyLearners().getUser().getFirstName() + " " + companyLearners.getCompanyLearners().getUser().getLastName();
				}
			}
		}
		if (error.length() > 0) throw new ValidationException(error);
		if (tasks.getFirstInProcess()) {
			companyLearners.setFirstApprovalDate(null);
			update(companyLearners);
		}
		if(companyLearners.getStatus() ==ApprovalEnum.Rejected)
		{
			companyLearners.setStatus(ApprovalEnum.PendingApproval);
			dao.update(companyLearners);
		}
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}
	
	public void completeCompanyLearnersELeaner(CompanyLearnersLostTime companyLearners, Users user, Tasks tasks) throws Exception {	
		
		String error = "";
		if (companyLearners.getDocs() != null) {
			for (Doc doc : companyLearners.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null) doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0) && doc.getConfigDoc().getRequiredDocument() != null && doc.getConfigDoc().getRequiredDocument()) {
					error += "Upload " + doc.getConfigDoc().getName() + " for " + companyLearners.getCompanyLearners().getUser().getFirstName() + " " + companyLearners.getCompanyLearners().getUser().getLastName();
				}
			}
		}
		if (error.length() > 0) throw new ValidationException(error);
		if (tasks.getFirstInProcess()) {
			companyLearners.setFirstApprovalDate(null);
			update(companyLearners);
		}
		if(companyLearners.getStatus() ==ApprovalEnum.Rejected) {
			companyLearners.setStatus(ApprovalEnum.PendingApproval);
			dao.update(companyLearners);
		}
		if (tasks.getFirstInProcess()) {
			companyLearners.setSignoffByEnum(SignoffByEnum.Learner);
			dao.update(companyLearners);
			List<Users> list = new ArrayList<>();
			CompanyLearners cl = CompanyLearnersService.instance().findByKey(companyLearners.getCompanyLearners().getId());
			Users u = cl.getUser();
			list.add(u);
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, list, false);
		}else {
			companyLearners.setSignoffByEnum(SignoffByEnum.Completed);
			dao.update(companyLearners);
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		}
	}
	
	public void downloadLPMFM011(CompanyLearnersLostTime companyLearnersLostTime) throws Exception {	
		Map<String, Object> params = new HashMap<String, Object>();
		UsersService usersService = new UsersService();
		CompanyService companyService = new CompanyService();
		SignoffService  signoffService = new SignoffService();
		Users learner = usersService.findByKey(companyLearnersLostTime.getCompanyLearners().getUser().getId());
		Company employer = companyService.findByKey(companyLearnersLostTime.getCompanyLearners().getEmployer().getId());
		Company skillsDevProvider = companyService.findByKey(companyLearnersLostTime.getCompanyLearners().getCompany().getId());
		String registrationNumber = "";
		if(companyLearnersLostTime.getCompanyLearners().getRegistrationNumber() != null) {
			registrationNumber=companyLearnersLostTime.getCompanyLearners().getRegistrationNumber();
		}
		params.put("registration_number", registrationNumber);
		params.put("learner", learner);
		params.put("employer", employer);
		params.put("skillsDevProvider", skillsDevProvider);
		params.put("originalTerminationdate", HAJConstants.sdfDDMMYYYY2.format(companyLearnersLostTime.getCompanyLearners().getCompletionDate()));
		params.put("requestedTerminationDate", HAJConstants.sdfDDMMYYYY2.format(companyLearnersLostTime.getNewCompletionDate()));
		params.put("agreementDate", new Date());// To be confirmed
		
		List<Signoff>signOffs = signoffService.findByTargetKeyAndClass(companyLearnersLostTime.getId(), companyLearnersLostTime.getClass().getName());
		if(signOffs.size()>0) {
			Signoff signoff = signOffs.get(0);
			if(signoff != null && signoff.getAccept()) {
				params.put("signoff_initials", populateInitial(signoff.getUser()));
				params.put("signoff", signoff);
			}
			Signoff learner_signoff = signOffs.get(1);
			if(learner_signoff != null && learner_signoff.getAccept()) {
				params.put("learner_signoff_initials", populateInitial(learner_signoff.getUser()));
				params.put("learner_signoff",learner_signoff);
			}
		}
		
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Lost_Time_Application_Form.pdf";
		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("LPM-FM-011-Request-for Extension-of-Termination-Date-Signoff.jasper", params, fileName);
	}
	
	public void downloadLPMTP013(CompanyLearnersLostTime companyLearnersLostTime) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		UsersService usersService = new UsersService();
		CompanyService companyService = new CompanyService();
		SignoffService  signoffService = new SignoffService();
		Users learner = usersService.findByKey(companyLearnersLostTime.getCompanyLearners().getUser().getId());
		String contractNum = "N/A";
		if (companyLearnersLostTime.getCompanyLearners().getRegistrationNumber() != null) {
			contractNum = companyLearnersLostTime.getCompanyLearners().getRegistrationNumber();
		}
		int daysExtended = companyLearnersLostTime.getDaysExtended();
		String newTerminationDate = HAJConstants.sdfDDMMYYYY2.format(companyLearnersLostTime.getNewCompletionDate());
		String expiryDateForLeve = HAJConstants.sdfDDMMYYYY2.format(companyLearnersLostTime.getNewCompletionDate());

		params.put("company_id", companyLearnersLostTime.getCompanyLearners().getCompany().getId());
		params.put("user_id", companyLearnersLostTime.getCompanyLearners().getUser().getId());
		params.put("extension_reason", companyLearnersLostTime.getLostTimeReason().getFriendlyName());
		params.put("contract_num", contractNum);
		params.put("daysExtended", daysExtended);
		params.put("newTerminationDate", newTerminationDate);
		params.put("expiryDateForLeve", expiryDateForLeve);
		boolean extensionOfAgreement = false;
		if (companyLearnersLostTime.getLostTimeReason() == LostTimeReason.ExtensionOfAgreement) {
			extensionOfAgreement = true;
		}
		ArrayList<LostTimeReasonBean> reasonList = new ArrayList<>();
		for (LostTimeReason val : LostTimeReason.values()) {
			boolean selected = false;
			if (companyLearnersLostTime.getLostTimeReason().getFriendlyName().equalsIgnoreCase(val.getFriendlyName())) {
				selected = true;
			}
			LostTimeReasonBean reason = new LostTimeReasonBean(val.getFriendlyName(), selected);
			reasonList.add(reason);
		}
				
		params.put("extensionOfAgreement", extensionOfAgreement);
		params.put("lostTimeReasonBeanDataSource", new JRBeanCollectionDataSource(reasonList));

		JasperService.addLogo(params);
		JasperService.addImage(params, "checkbox-marked.png", "checked_image");
		JasperService.addImage(params, "checkbox-outline.png", "unchecked_image");

		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Lost_Time_Extension_of_Contract_Form.pdf";
		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("LPM-TP-013-Extension-of-Contract-Template-Signoff.jasper", params, fileName);
	}
	
	public void downloadLPMAD001(CompanyLearnersLostTime companyLearnersLostTime) throws Exception {
		Map<String, Object> params = new HashMap<String, Object>();
		UsersService usersService = new UsersService();
		CompanyService companyService = new CompanyService();
		SignoffService  signoffService = new SignoffService();
		Users learner = usersService.findByKey(companyLearnersLostTime.getCompanyLearners().getUser().getId());
		Company skillsDevProvider = companyService.findByKey(companyLearnersLostTime.getCompanyLearners().getCompany().getId());

		Date commencementDate = companyLearnersLostTime.getLostTimeStartDate();
		Date endDate = companyLearnersLostTime.getNewCompletionDate();
		
		if(companyLearnersLostTime.getCompanyLearners().getInterventionType().getInterventionTypeEnum() != null && 
				(companyLearnersLostTime.getCompanyLearners().getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership || 
				companyLearnersLostTime.getCompanyLearners().getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Apprenticeship)) {
			
			Date date1=  HAJConstants.sdfDDMMYYYY2.parse("01/03/2019");
			
			if (companyLearnersLostTime.getCompanyLearners().getRegisteredDate() != null) {
				if(companyLearnersLostTime.getCompanyLearners().getRegisteredDate().before(date1)) {
					commencementDate = companyLearnersLostTime.getCompanyLearners().getRegisteredDate();
				}else {
					commencementDate = companyLearnersLostTime.getCompanyLearners().getCommencementDate();
				}
			}
		}else if(SKILLS_PROGRAM_LIST.contains(companyLearnersLostTime.getCompanyLearners().getInterventionType().getId())
				|| SKILLS_SET_LIST.contains(companyLearnersLostTime.getCompanyLearners().getInterventionType().getId())
				|| companyLearnersLostTime.getCompanyLearners().getInterventionType().getId() == HAJConstants.CREDIT_BEARING_SHORT_COURSE){
			if (companyLearnersLostTime.getCompanyLearners().getRegisteredDate() != null) {
				commencementDate = companyLearnersLostTime.getCompanyLearners().getCommencementDate();
			}
		}

		params.put("commencement_date", HAJConstants.sdfDDMMYYYY2.format(commencementDate));
		params.put("end_date", HAJConstants.sdfDDMMYYYY2.format(endDate));
		params.put("learner_id", learner.getId());
		params.put("company_id", skillsDevProvider.getId());
		List<Signoff>signOffs = signoffService.findByTargetKeyAndClass(companyLearnersLostTime.getId(), companyLearnersLostTime.getClass().getName());
		if(signOffs.size()>0) {
			Signoff signoff = signOffs.get(0);
			if(signoff != null && signoff.getAccept()) {
				params.put("signoff_initials", populateInitial(signoff.getUser()));
				params.put("signoff", signoff);
			}
			Signoff learner_signoff = signOffs.get(1);
			if(learner_signoff != null && learner_signoff.getAccept()) {
				params.put("learner_signoff_initials", populateInitial(learner_signoff.getUser()));
				params.put("learner_signoff",learner_signoff);
			}
		}

		JasperService.addLogo(params);
		
		String fileName = learner.getFirstName() + "_" + learner.getLastName() + "_Addendum_Form.pdf";
		JasperService js = new JasperService();
		js.createReportFromDBtoServletOutputStream("LPM-AD-001-Addendum-LearnershipAgreement-Signoff.jasper", params, fileName);
	}
	
	private String populateInitial(Users user) {
		String inStringForm = "";
		if(user != null) {
			inStringForm = inStringForm + user.getFirstName().trim().charAt(0) + user.getLastName().trim().charAt(0);
		}
		return inStringForm;
	}
	
	public void withdrawLostTime(CompanyLearnersLostTime companyLearnersLostTime, Users user, Tasks tasks) throws Exception {
		CompanyLearners companyLearners=companyLearnersLostTime.getCompanyLearners();
		companyLearners.setLearnerStatus(LearnerStatusEnum.Registered);
		companyLearners.setStatus(ApprovalEnum.Approved);
		companyLearnersService.update(companyLearners);
		
		companyLearnersLostTime.setStatus(ApprovalEnum.Withdrawn);
		companyLearnersLostTime.setApprovalDate(getSynchronizedDate());
		update(companyLearnersLostTime);
		
		TasksService.instance().completeTask(tasks);
	}	

	public void approveCompanyLearners(CompanyLearnersLostTime companyLearners, Users user, Tasks tasks) throws Exception {
		
		companyLearners.setStatus(ApprovalEnum.WaitingForManager);
		if (companyLearners.getFirstApprovalDate() == null) {
			companyLearners.setFirstApprovalDate(getSynchronizedDate());
		}
		dao.update(companyLearners);
		if(tasks.getProcessRole().getRoleOrder()==2)//Sending task to applicant
		{
			if (companyLearnersService == null) {
				companyLearnersService = new CompanyLearnersService();
			}
			companyLearnersService.sendLPMAD001Email(companyLearners, companyLearners.getCreateUser());
			List<Users> usersList=new ArrayList<>();
			usersList.add(companyLearners.getCreateUser());
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersList, false);
		}
		else
		{
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		}
	}

	public void approveCompanyLearnersLostTime(CompanyLearnersLostTime companyLearners, Users user, Tasks tasks) throws Exception {
		companyLearners.setStatus(ApprovalEnum.WaitingForManager);
		if (companyLearners.getFirstApprovalDate() == null) {
			companyLearners.setFirstApprovalDate(getSynchronizedDate());
		}
		dao.update(companyLearners);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		/*if(tasks.getProcessRole().getRoleOrder()==2){
			List<Users> usersList=new ArrayList<>();
			usersList.add(companyLearners.getCreateUser());
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, usersList, false);
		}else{
			TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		}*/
	}

	public void rejectCompanyLearners(CompanyLearnersLostTime companyLearners, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearnersLostTime.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.LearnerLostTime);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		companyLearners.setStatus(ApprovalEnum.Rejected);
		dao.update(companyLearners);
		if (tasks.getFirstInProcess()) {
			List<Users> userList=new ArrayList<>();
			userList.add(companyLearners.getCreateUser());
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, userList);
		}
		else
		{
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
		}
		
	}
	
	public void rejectCompanyLearnersElostTime(CompanyLearnersLostTime companyLearners, Users user, Tasks tasks, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearnersLostTime.class.getName(), selectedRejectReason, user, tasks.getWorkflowProcess());
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		companyLearners.setStatus(ApprovalEnum.Rejected);
		dao.update(companyLearners);
		if (tasks.getFirstInProcess()) {
			List<Users> userList=new ArrayList<>();
			userList.add(companyLearners.getCreateUser());
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, userList);
		} else {
			if(companyLearners.getCreatedByEnum() == CreatedByEnum.merSETA) {
				companyLearners.setSignoffByEnum(SignoffByEnum.merSETA);
			}else if(companyLearners.getCreatedByEnum() == CreatedByEnum.sdp) {
				companyLearners.setSignoffByEnum(SignoffByEnum.sdp);
			}else if(companyLearners.getCreatedByEnum() == CreatedByEnum.sdf) {
				companyLearners.setSignoffByEnum(SignoffByEnum.sdf);
			}
			dao.update(companyLearners);
			CompanyLearners cl = CompanyLearnersService.instance().findByKey(companyLearners.getCompanyLearners().getId());
			List<Users> userList=new ArrayList<>();
			userList.add(companyLearners.getCreateUser());
			String description = "The extension application for "+cl.getUser().getFirstName()+ " " +cl.getUser().getLastName()+"("+ anIDNumber(cl.getUser())+","+ cl.getUser().getEmail()+")" +" has not been approved. Please process.";		
			TasksService.instance().completeTask(tasks);
			TasksService.instance().findFirstInProcessAndCreateTask(description, user, companyLearners.getId(), companyLearners.getClass().getName(), true, ConfigDocProcessEnum.ELearnerLostTime, MailDef.instance(MailEnum.Task, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName(), MailTagsEnum.Task, ConfigDocProcessEnum.ELearnerLostTime.getTaskDescription()), userList);
			//TasksService.instance().findPreviousInProcessAndCreateFirstInProcessTask(user,  companyLearners.getId(), companyLearners.getClass().getName(), tasks.getWorkflowProcess(), false, description);
		}
		updateSignoffs(companyLearners);
	}
	
	public void updateSignoffs(CompanyLearnersLostTime companyLearners) throws Exception {
		List<Signoff>signOffs = SignoffService.instance().findByTargetKeyAndClass(companyLearners.getId(), companyLearners.getClass().getName());
		for(Signoff s:signOffs) {
			s.setAccept(false);
			SignoffService.instance().update(s);
		}
	}
	
	public void finalApproveCompanyLearners(CompanyLearnersLostTime companyLearnersLostTime, Users user, Tasks tasks) throws Exception {
		companyLearnersLostTime.setStatus(ApprovalEnum.Approved);
		companyLearnersLostTime.setApprovalDate(getSynchronizedDate());
		dao.update(companyLearnersLostTime);
		UsersService usersService=new UsersService();
		Users u=usersService.findByKey(companyLearnersLostTime.getCreateUser().getId());
		
		TasksService.instance().completeTask(tasks);
		/*If approved, LPMTP013 to be sent to applicant*/
		CompanyLearnersService companyLearnersService=new CompanyLearnersService();
		//Updating Learner Completion date
		
		CompanyLearners companyLearners=companyLearnersLostTime.getCompanyLearners();
		companyLearners.setCompletionDate(companyLearnersLostTime.getNewCompletionDate());
		companyLearners.setLearnerStatus(LearnerStatusEnum.Registered);
		CompanyLearnersService.instance().update(companyLearners);
		companyLearnersService.sendLPMTP013Email(companyLearnersLostTime, u);
	}

	public void finalRejectCompanyLearners(CompanyLearnersLostTime companyLearners, Users user, Tasks tasks,ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		companyLearners.setStatus(ApprovalEnum.Rejected);
		companyLearners.setApprovalDate(getSynchronizedDate());
		companyLearners.setApprovalUser(user);
		dao.update(companyLearners);
		
		CompanyLearners cl = CompanyLearnersService.instance().findByKey(companyLearners.getCompanyLearners().getId());
		cl.setLearnerStatus(LearnerStatusEnum.Registered);
		CompanyLearnersService.instance().update(cl);
		
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(companyLearners.getId(), CompanyLearnersLostTime.class.getName(), selectedRejectReason, user, ConfigDocProcessEnum.LearnerLostTime);
		for(RejectReasonMultipleSelection rrm:rrmList)
		{
			if(rrm !=null)
			{
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		TasksService.instance().completeTask(tasks);
		sendLearnerLostUnsuccessful(companyLearners.getCompanyLearners(), companyLearners.getCreateUser(), selectedRejectReason);
	}
	
	public int countOpenLostTimeByCompanyLearnersId(CompanyLearners companyLearners) throws Exception {
		return dao.countOpenLostTimeByCompanyLearnersId(companyLearners.getId());
	}
	
	public CompanyLearnersLostTime findByKeyAndSetDocs(long id ,ProcessRoles processRoles, ConfigDocProcessEnum process) throws Exception {
		CompanyLearnersLostTime companyLearnersLostTime = findByKey(id);
		prepareCompanyLearnersRegistrationDocs(process, companyLearnersLostTime, companyLearnersLostTime, processRoles);
		return companyLearnersLostTime;
	}
	
	public void prepareCompanyLearnersRegistrationDocs(ConfigDocProcessEnum configDocProcess, CompanyLearnersLostTime entityDoc, CompanyLearnersLostTime entity, ProcessRoles processRoles) throws Exception {
		if (entity.getId() != null) {
			if (processRoles.getCompanyUserType() == null) entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
			else entityDoc.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}
			/*l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}*/
		} else {
			entityDoc.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entityDoc.getDocs().add(new Doc(a));
				});
			}
		}
	}
	
	public void sendLearnerLostUnsuccessful(CompanyLearners cl, Users user, ArrayList<RejectReasons> selectedRejectReason) throws Exception {

		String subject = "LEARNER LOST TIME APPLICATION OUTCOME";
		String title = "";
		if (user.getTitle() != null) {
			title = user.getTitle().getDescription();
		}
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + title + " " + user.getFirstName() + " " + user.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the merSETA has considered the Learner Lost Time application for" 

				+ " " + cl.getUser().getFirstName() + " "+ cl.getUser().getLastName() + " (" + anIDNumber(cl.getUser()) + ")" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "We regret to advise that the outcome for the request " + "is <b>unsuccessful</b>. " + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<b>Rejection Reason(s):</b><br/>" + "" + convertToHTML(selectedRejectReason) + "" + "</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely," + "</p>" + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<b>merSETA Administration</b>" + "</p>";

		GenericUtility.sendMail(user.getEmail(), subject, mssg, null);

	}
	
	public String convertToHTML(List<RejectReasons> rejectReasons) {

		String sb = "<ul>";
		for (RejectReasons r : rejectReasons) {
			sb += "<li>" + r.getDescription() + "</li>";
		}
		sb += "</ul>";
		return sb;
	}
	
	
	public void prepareDocuments(ConfigDocProcessEnum configDocProcess, CompanyLearnersLostTime entity, ProcessRoles processRoles, Boolean firstInProcess) throws Exception {
		if (entity.getId() != null) {
			if(!firstInProcess)
			{
				/*if (processRoles.getCompanyUserType() == null) 
				{
					entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
				}
				else 
				{
					entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), processRoles));
				}*/
				
				entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId(), configDocProcess));
				List<ConfigDoc> l = ConfigDocService.instance().findByProcessRolesNotUploaded(entity.getClass().getName(), entity.getId(), processRoles);
				if (l != null && l.size() > 0) {
					l.forEach(a -> {
						entity.getDocs().add(new Doc(a));
					});
				}
			}
			else
			{
				entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
				if(entity.getDocs()==null || entity.getDocs().size()<=0)
				{
					entity.setDocs(new ArrayList<>());
					List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
					if (l != null && l.size() > 0) {
						l.forEach(a -> {
							entity.getDocs().add(new Doc(a));
						});
					}
				}
			}
		} else {
			entity.setDocs(new ArrayList<>());
			List<ConfigDoc> l = ConfigDocService.instance().findByProcessA(configDocProcess);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					entity.getDocs().add(new Doc(a));
				});
			}
		}
	}

	public void runLearneAggrementCheck() throws Exception {
		Date today = GenericUtility.getStartOfDay(new Date());
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		/*2.	Two reminders should be sent out prior expiry of agreement/contract:
				a.	6 months prior to expiry and 3 months, 1 month, 7 days, 3 days prior to expiry date
				b.	The system must send a notification to the workplace/provider/learner stating that the agreement 
					is about to expire and enable them to apply for an extension should they want to apply for an extension, 
				c.	If no application and amendment received by termination date, notification must go out to the regional 
					Coordinator, workplace, provider, learner stating that the agreement has been terminated
				d.	The agreement must not terminate if a trade test date is scheduled
		 * Excluding weekends
		 */
		List<CompanyLearners>list = companyLearnersService.findLearnersByStatus(ApprovalEnum.Approved, LearnerStatusEnum.Registered);
		for(CompanyLearners cl :list) {
			if(cl.getCompletionDate() != null) {
				String information = "Reminders to be sent out prior expiry of agreement/contract: " + sdf.format(GenericUtility.getStartOfDay(cl.getCompletionDate())) + ".";
				Date threeMonth = GenericUtility.getStartOfDay(GenericUtility.deductDaysFromDate(GenericUtility.getStartOfDay(cl.getCompletionDate()), 90));
				Date oneMonth = GenericUtility.getStartOfDay(GenericUtility.deductDaysFromDate(GenericUtility.getStartOfDay(cl.getCompletionDate()), 30));
				Date sevenDays = GenericUtility.getStartOfDay(GenericUtility.deductDaysFromDate(GenericUtility.getStartOfDay(cl.getCompletionDate()), 7));
				Date threeDays = GenericUtility.getStartOfDay(GenericUtility.deductDaysFromDate(GenericUtility.getStartOfDay(cl.getCompletionDate()), 3));
							
				if (today.equals(threeMonth)) {
					runCheck(threeMonth, cl, information, false);
				}else if (today.equals(oneMonth)) {
					runCheck(oneMonth, cl, information, false);			
				}else if (today.equals(sevenDays)) {
					runCheck(sevenDays, cl, information, false);	
				}else if (today.equals(threeDays)) {
					runCheck(threeDays, cl, information, false);
				}else if(today.equals(cl.getCompletionDate())) {
					runCheck(threeDays, cl, information, true);
				}
			}
		}
	}

	private void runCheck(Date date, CompanyLearners cl, String information, boolean termination) throws Exception {
		if (!termination) {
			ScheduleChangesLogService.instance().addNewEntry(cl.getCompany(), null, cl.getClass().getName(), cl.getId(), information, false);
			sendNotification( date,  cl,  information);
		}else {
			ScheduleChangesLogService.instance().addNewEntry(cl.getCompany(), null, cl.getClass().getName(), cl.getId(), information, false);
			companyLearnersService.sendLearnerTermination(cl);
			sendTerminationNotification(cl);
		}
	}

	private void sendTerminationNotification(CompanyLearners cl) throws Exception {
		List<Users>users = new ArrayList<>();
		users.add(cl.getUser());
		//company contact
		SDFCompany sdfCompany = SDFCompanyService.instance().findPrimarySDF(cl.getEmployer());
		if(sdfCompany != null) {
			users.add(sdfCompany.getSdf());
		}
		//provider contact
		CompanyUsersService companyUsersService = new CompanyUsersService();
		Users providerContactPerson = companyUsersService.findCompanyContactPerson(cl.getCompany().getId());
		if(providerContactPerson != null) {
			users.add(providerContactPerson);			
		}
		
		users.add(cl.getCreateUser());
		
		super.removeDuplicatesFromList(users);
		for(Users user: users) {
			String subject = "LEARNER TERMINATION REMINDER";
			String title = "";
			if (user.getTitle() != null) {
				title = user.getTitle().getDescription();
			}
			String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + title + " " + user.getFirstName() + " " + user.getLastName() + ",</p>"
	
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the expiry of agreement/contract for the learner. "+ cl.getUser().getFirstName()+ " "+ cl.getUser().getLastName()+ " (" + anIDNumber(cl.getUser()) + ")" + " is due on "+ cl.getCompletionDate() + "</p>"
	
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised thst you can apply for an extension should you want to apply for an extension" + "</p>"
		
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely," + "</p>" 
					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<b>merSETA Administration</b>" + "</p>";
	
			GenericUtility.sendMail(user.getEmail(), subject, mssg, null);
		}
	}

	private void sendNotification(Date date, CompanyLearners cl, String information) throws Exception {
		List<Users>users = new ArrayList<>();
		users.add(cl.getUser());
		//company contact
		SDFCompany sdfCompany = SDFCompanyService.instance().findPrimarySDF(cl.getEmployer());
		if(sdfCompany != null) {
			users.add(sdfCompany.getSdf());
		}
		//provider contact
		CompanyUsersService companyUsersService = new CompanyUsersService();
		Users providerContactPerson = companyUsersService.findCompanyContactPerson(cl.getCompany().getId());
		if(providerContactPerson != null) {
			users.add(providerContactPerson);			
		}	
		
		List<Users> list = findRegionClinetServiceCoodinator(cl.getEmployer());
		if(users!=null && users.size()>0) {
			users.addAll(list);
		}
		users.add(cl.getCreateUser());		
		super.removeDuplicatesFromList(users);
		
		for(Users user: users) {
			//String subject = "LEARNER REMIDER EXPIERY OF AGREEMENT/CONTRACT";
			String subject = "EXPIRY OF LEARNER AGREEMENT";
			String title = "";
			if (user.getTitle() != null) {
				title = user.getTitle().getDescription();
			}
			String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear " + title + " " + user.getFirstName() + " " + user.getLastName() + ",</p>"
					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that the agreement for the learner "+ cl.getUser().getFirstName()+ " "+ cl.getUser().getLastName()+ " (" + anIDNumber(cl.getUser()) + ")" + "  has been terminated." + "</p>"
	
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Please be advised that you will no longer be able apply for an extension." + "</p>"
		
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "Yours sincerely," + "</p>" 
					
					+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">" + "<b>merSETA Administration</b>" + "</p>";
	
			GenericUtility.sendMail(user.getEmail(), subject, mssg, null);
		}
	}
	
	public List<Users> findRegionClinetServiceCoodinator(Company company) throws Exception {
		HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
		RolesService rolesService = new RolesService();
		List<Users> list = new ArrayList<>();
		Roles roles = rolesService.findByKey(HAJConstants.CLIENT_SERVICE_COORDINATOR_ROLE_ID);
		list = hostingCompanyEmployeesService.locateHostingCompanyEmployeesByRegionAndRole(company.getResidentialAddress().getTown(), roles);
		return list;
	}
	
	public String anIDNumber(Users user) {
		if (user.getRsaIDNumber() != null && user.getRsaIDNumber() != "" && !user.getRsaIDNumber().isEmpty()) {
			return user.getRsaIDNumber();
		} else if (user.getPassportNumber() != null && user.getPassportNumber() != "" && !user.getPassportNumber().isEmpty()) {
			return user.getPassportNumber();
		} else {
			return "N/A";
		}
	}
	
	public int countLostTimeByCompanyLearnerTypeAndStatus(Long companyLearnerId, LostTimeReason lostTimeReason, ApprovalEnum status) throws Exception {
		return dao.countLostTimeByCompanyLearnerTypeAndStatus(companyLearnerId, lostTimeReason, status);
	}
}
