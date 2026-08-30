package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SiteVisitReportDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.ConfigDoc;
import haj.com.entity.DgVerification;
import haj.com.entity.Doc;
import haj.com.entity.Signoff;
import haj.com.entity.SiteVisit;
import haj.com.entity.SiteVisitReport;
import haj.com.entity.SiteVisitReportDispute;
import haj.com.entity.SiteVisitReportSME;
import haj.com.entity.Sites;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.enums.SiteVisitReportStatusEnum;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.RegionTownService;
import haj.com.utils.GenericUtility;

public class SiteVisitReportService extends AbstractService {

	/** The Services. */
	private SiteVisitReportSMEService smeService = new SiteVisitReportSMEService();
	private SitesService sitesService = new SitesService();
	private AddressService addressService = new AddressService();
	private ConfigDocService configDocService = new ConfigDocService();
	private SDFCompanyService sdfCompanyService = new SDFCompanyService();
	private DocService docService = new DocService();
	private CompanyService companyService;
	private SignoffService signoffService = new SignoffService();

	/** The dao. */
	private SiteVisitReportDAO dao = new SiteVisitReportDAO();
	
	
	/** The address service. */
	private static SiteVisitReportService siteVisitReportService = null;
	
	/**
	 * Instance.
	 *
	 * @return the address service
	 */
	public static synchronized SiteVisitReportService instance() {
		if (siteVisitReportService == null) {
			siteVisitReportService = new SiteVisitReportService();
		}
		return siteVisitReportService;
	}

	/**
	 * Get all SiteVisitReport
	 * 
	 * @author TechFinium
	 * @see SiteVisitReport
	 * @return a list of {@link haj.com.entity.SiteVisitReport}
	 * @throws Exception
	 *             the exception
	 */
	public List<SiteVisitReport> allSiteVisitReport() throws Exception {
		return dao.allSiteVisitReport();
	}

	/**
	 * Create or update SiteVisitReport.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SiteVisitReport
	 */
	public void create(SiteVisitReport entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update SiteVisitReport.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SiteVisitReport
	 */
	public void update(SiteVisitReport entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SiteVisitReport.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SiteVisitReport
	 */
	public void delete(SiteVisitReport entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SiteVisitReport}
	 * @throws Exception
	 *             the exception
	 * @see SiteVisitReport
	 */
	public SiteVisitReport findByKey(long id) throws Exception {
		//return prepareNewDocs(dao.findByKey(id), ConfigDocProcessEnum.SITE_VISIT_REPORT, CompanyUserTypeEnum.Company);
		return dao.findByKey(id);
	}

	/**
	 * Find SiteVisitReport by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SiteVisitReport}
	 * @throws Exception
	 *             the exception
	 * @see SiteVisitReport
	 */
	public List<SiteVisitReport> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SiteVisitReport
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SiteVisitReport}
	 * @throws Exception
	 *             the exception
	 */
	public List<SiteVisitReport> allSiteVisitReport(int first, int pageSize) throws Exception {
		return dao.allSiteVisitReport(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SiteVisitReport for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the SiteVisitReport
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SiteVisitReport.class);
	}

	/**
	 * Lazy load SiteVisitReport with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            SiteVisitReport class
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
	 * @return a list of {@link haj.com.entity.SiteVisitReport} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisitReport> allSiteVisitReport(Class<SiteVisitReport> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SiteVisitReport>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * @return List<SiteVisitReport>
	 * @throws Exception
	 */
	public List<SiteVisitReport> allSiteVisitReportByCompany(Company company) throws Exception {

		List<SiteVisitReport> list = dao.findByCompany(company);

		return list;
	}

	/**
	 * Get count of SiteVisitReport for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            SiteVisitReport class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SiteVisitReport entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SiteVisitReport> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public void create(SiteVisitReport entity, Users users, boolean createWorkflow) throws Exception {

		for (Doc doc : entity.getDocs())
			if (doc.getData() == null) throw new Exception("Please ensure all documents are uploaded");
		entity.setUsers(users);
		if (entity.getCompany().getResidentialAddress() == null) throw new Exception("Company not yet registered.");

		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);

		if (createWorkflow) {
			RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
			String desc = "There is a new Site Visit Report for #COMPANY_NAME# #TRADING_NAME# (#ENTITY_ID#) submitted by: #FIRST_NAME# #LAST_NAME# (#IDENTITY_NUMBER#). Please login and review.";
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(rt.getCrm().getUsers());
			TasksService.instance().findFirstInProcessAndCreateTask("", users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.SITE_VISIT_REPORT, null, signoffs);
		}

		for (Doc doc : entity.getDocs()) {
			doc.setTargetKey(entity.getId());
			doc.setTargetClass(SiteVisitReport.class.getName());
			DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), users);
		}
	}

	public void createOnceRejected(SiteVisitReport entity, Users users, boolean createWorkflow, Tasks task) throws Exception {

		entity.setUsers(users);
		if (entity.getCompany().getResidentialAddress() == null) throw new Exception("Company not yet registered.");

		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);

		if (createWorkflow) {
			RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
			String desc = "There is a new Site Visit Report for #COMPANY_NAME# #TRADING_NAME# (#ENTITY_ID#) submitted by: #FIRST_NAME# #LAST_NAME# (#IDENTITY_NUMBER#). Please login and review.";
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(rt.getCrm().getUsers());
			TasksService.instance().findFirstInProcessAndCreateTask("", users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.SITE_VISIT_REPORT, null, signoffs);
		}

		TasksService.instance().completeTask(task);
	}

	public SiteVisitReport prepareNewDocs(SiteVisitReport siteVisitReport, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		
		siteVisitReport.setDocs(DocService.instance().searchByTargetKeyAndClass(siteVisitReport.getClass().getName(), siteVisitReport.getId()));
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(siteVisitReport.getClass().getName(), siteVisitReport.getId(), configDocProcess, companyUserType);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				siteVisitReport.getDocs().add(new Doc(a));
			});
		}
		return siteVisitReport;
	}

	public void prepareNewDocs(SiteVisitReport siteVisitReport) throws Exception {
		siteVisitReport.setDocs(new ArrayList<Doc>());
		List<ConfigDoc> l = configDocService.findByProcess(ConfigDocProcessEnum.SITE_VISIT_REPORT, CompanyUserTypeEnum.Company);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				siteVisitReport.getDocs().add(new Doc(a));
			});
		}
	}

	public void populateSignOffs(SiteVisitReport siteVisitReport) throws Exception {
		if (siteVisitReport.getId() != null) siteVisitReport.setSignOffs(signoffService.findByTargetKeyAndClass(siteVisitReport.getId(), SiteVisitReport.class.getName()));
		else siteVisitReport.setSignOffs(new ArrayList<>());
	}

	/**
	 * @param site
	 * @param address
	 * @param user
	 * @throws Exception
	 */
	public void insertSiteAddress(Address address, Users user) throws Exception {

		if (address != null && address.getId() == null) {
			addressService.create(address);
		}
	}

	/**
	 * @param site
	 * @param address
	 * @param user
	 * @throws Exception
	 */
	public void updateSiteWithSiteAddresss(Sites site, Address address, Users user) throws Exception {

		if (address != null && address.getId() != null) {
			site.setRegisteredAddress(address);
			sitesService.update(site);
		}
	}

	/**
	 * @param siteVisitReport
	 * @param user
	 * @throws Exception
	 */
	public void insertSiteVisitReport(SiteVisitReport siteVisitReport, Users user) throws Exception {

		if (siteVisitReport.getId() != null) {
			dao.update(siteVisitReport);
		} else {
			siteVisitReport.setSiteVisitReportStatusEnum(SiteVisitReportStatusEnum.PendingSignOff);
			dao.create(siteVisitReport);
		}
	}

	/**
	 * @param smeList
	 * @param siteVisitReport
	 * @param user
	 * @throws Exception
	 */
	public void insertSiteVisitReportSmes(List<SiteVisitReportSME> smeList, SiteVisitReport siteVisitReport, Users user) throws Exception {

		// Get existing list and delete the smes if the exist against the site visit
		// report first...
		List<SiteVisitReportSME> existingList = smeService.findBySiteVisitReport(siteVisitReport);
		if (existingList.size() > 0) {
			for (SiteVisitReportSME sme : existingList) {
				smeService.delete(sme);
			}
		}
		for (SiteVisitReportSME sme : smeList) {

			if(sme.getPassport() == null || sme.getPassport().equals("")){
				sme.setPassport(" ");
			}
			if(sme.getIdentityNumber() == null || sme.getIdentityNumber().equals("")){
				sme.setIdentityNumber(" ");
			}
			sme.setSite(siteVisitReport.getSite());
			sme.setSiteVisitReport(siteVisitReport);
			sme.setUser(user);
			smeService.createOnly(sme);
		}
	}

	/**
	 * @param siteVisitReport
	 * @param smeList
	 * @param user
	 * @throws Exception
	 */
	public void updateSiteVisitReportSMesWithSiteVisitReport(SiteVisitReport siteVisitReport, List<SiteVisitReportSME> smeList, Users user) throws Exception {

		for (SiteVisitReportSME sme : smeList) {

			sme.setSite(siteVisitReport.getSite());
			sme.setSiteVisitReport(siteVisitReport);
			sme.setUser(user);
			smeService.update(sme);
		}
	}

	/**
	 * @param siteVisitReport
	 * @param user
	 * @throws Exception
	 */
	public void sendToSDF(SiteVisitReport siteVisitReport, Users user, List<SiteVisitReportSME> smeList, Tasks task) throws Exception {

		try {

			insertSiteVisitReportSmes(smeList, siteVisitReport, user);
			createSignOffs(siteVisitReport, siteVisitReport.getSignOffs());
			List<Users> sdfCompanies = new ArrayList<>();
			for (Signoff signoff : siteVisitReport.getSignOffs())
				sdfCompanies.add(signoff.getUser());
			TasksService.instance().createTaskUser(sdfCompanies, SiteVisitReport.class.getName(), siteVisitReport.getId(), "Site Visit Report was submitted and requires your sign off.", user, true, true, null, ConfigDocProcessEnum.SITE_VISIT_REPORT, false);
			if (task != null) {
				TasksService.instance().completeTask(task);
			}
		} catch (Exception exception) {
			throw exception;
		}
	}

	/**
	 * @param entity
	 * @param user
	 * @param task
	 * @throws Exception
	 */
	public void approveTaskSDF(SiteVisitReport entity, Users user, Tasks task) throws Exception {

		List<IDataEntity> dataEntities = new ArrayList<>();
		dataEntities.add(entity);
		boolean changePending = true;
		for (Signoff signoff : entity.getSignOffs()) {
			if (signoff.getUser().equals(user)) {
				signoff.setSignOffDate(getSynchronizedDate());
			}
			dataEntities.add(signoff);
			if ((signoff.getAccept() == null || !signoff.getAccept()) && (signoff.getDispute() == null || !signoff.getDispute())) {
				changePending = false;
			}
		}

		TasksService.instance().completeTask(task);
		if (changePending) {
			entity.setSiteVisitReportStatusEnum(SiteVisitReportStatusEnum.PendingFinalApproval);
			RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(rt.getCrm().getUsers());
			TasksService.instance().findFirstInProcessAndCreateTask("", user, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.SITE_VISIT_REPORT, null, signoffs);
		}
		dao.updateBatch(dataEntities);
	}

	private void createSignOffs(SiteVisitReport entity, List<Signoff> signOffs) throws Exception {
		List<IDataEntity> dataEntities = new ArrayList<>();
		for (Signoff signoff : signOffs) {
			if (signoff.getId() == null) {
				signoff.setTargetKey(entity.getId());
				dataEntities.add(signoff);
			}
		}
		this.dao.createBatch(dataEntities);
	}

	public void completeToFirst(SiteVisitReport siteVisitReport, Users formUser, Tasks task) throws Exception {
		update(siteVisitReport);
		String desc = "Site Visit Repport has been loaded onto the NSDMS. Please Approve.";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, task.getTargetKey(), task.getTargetClass(), true, ConfigDocProcessEnum.SITE_VISIT_REPORT, null, null);
		TasksService.instance().completeTask(task);
	}

	public void completeTask(SiteVisitReport siteVisitReport, Users user, Tasks task) throws Exception {
		update(siteVisitReport);
		TasksService.instance().findNextInProcessAndCreateTask("Site Visit Report was checked and submitted by the admin", user, siteVisitReport.getId(), siteVisitReport.getClass().getName(), true, task, MailDef.instance(MailEnum.SdfForApproval, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
	}

	// ********************************************************************************************************************************************************************************
	// //

	/**
	 * DOCS UPLOADED NOW NEED TO BE POPULATED WITH THE GENERATED CLASS KEY AND CLASS
	 * NAME FOR RETRIEVAL LATER ON...
	 * 
	 * @param siteVisitReport
	 * @throws Exception
	 */
	public void setSiteVisitReportIntoAllLinkedUplaodedDocs(SiteVisitReport siteVisitReport) throws Exception {

		if (siteVisitReport.getDocs() != null && siteVisitReport.getDocs().size() > 0) {
			for (Doc aDoc : siteVisitReport.getDocs()) {
				aDoc.setTargetKey(siteVisitReport.getId());
				aDoc.setTargetClass(siteVisitReport.getClass().getName());
				docService.create(aDoc);
			}
		}
	}

	/**
	 * SDF AND LABOUR SDF
	 * 
	 * @param siteVisitReport
	 * @param user
	 * @param task
	 * @throws Exception
	 */
	public void disputeAndApproveTask(SiteVisitReport siteVisitReport, Users user, Tasks task) throws Exception {

		if (siteVisitReport.getSiteVisitReportDisputes().size() > 0) {
			for (SiteVisitReportDispute dispute : siteVisitReport.getSiteVisitReportDisputes()) {
				dispute.setCreateDate(new Date(System.currentTimeMillis()));
				dispute.setCreateUser(user);
				dispute.setSiteVisitReport(siteVisitReport);
				dao.create(dispute);
			}
		}
		siteVisitReport.setSiteVisitReportStatusEnum(SiteVisitReportStatusEnum.PendingFinalApproval);
		siteVisitReport.setDateApproved(new Date());
		update(siteVisitReport);
		TasksService.instance().findFirstInProcessAndCreateTask(user, siteVisitReport.getId(), siteVisitReport.getClass().getName(), ConfigDocProcessEnum.SITE_VISIT_REPORT,false);
		TasksService.instance().completeTask(task);
	}

	/**
	 * SDF AND LABOUR SDF
	 * 
	 * @param siteVisitReport
	 * @param user
	 * @param task
	 * @throws Exception
	 */
	public void approveTask(SiteVisitReport siteVisitReport, Users user, Tasks task) throws Exception {
		siteVisitReport.setSiteVisitReportStatusEnum(SiteVisitReportStatusEnum.PendingFinalApproval);
		siteVisitReport.setDateApproved(new Date());
		update(siteVisitReport);
		TasksService.instance().findFirstInProcessAndCreateTask(user, siteVisitReport.getId(), siteVisitReport.getClass().getName(), ConfigDocProcessEnum.SITE_VISIT_REPORT,false);
		TasksService.instance().completeTask(task);
	}

	/**
	 * CRM
	 * 
	 * @param siteVisitReport
	 * @param user
	 * @param task
	 * @throws Exception
	 */
	public void acknowledgeDisputeAndFinalApproveTask(SiteVisitReport siteVisitReport, Users user, Tasks task) throws Exception {

		siteVisitReport.setSiteVisitReportStatusEnum(SiteVisitReportStatusEnum.Approved);
		siteVisitReport.setDateApproved(new Date());
		update(siteVisitReport);
		TasksService.instance().completeTask(task);
	}

	/**
	 * CRM
	 * 
	 * @param siteVisitReport
	 * @param user
	 * @param task
	 * @throws Exception
	 */
	public void acknowledgeDisputeAndFinalRejectTask(SiteVisitReport siteVisitReport, Users user, Tasks task) throws Exception {

		siteVisitReport.setSiteVisitReportStatusEnum(SiteVisitReportStatusEnum.Rejected);
		if (task.getFirstInProcess()) {
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(siteVisitReport.getUsers());
			TasksService.instance().createTaskUser(signoffs, task.getTargetClass(), task.getTargetKey(), "Site Visit Report was rejected please login and view the reason and make the relevant changes.", user, true, true, task, ConfigDocProcessEnum.SITE_VISIT_REPORT, false);
			TasksService.instance().completeTask(task);
		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask("Site Visit Report was rejected please review", user, siteVisitReport.getId(), siteVisitReport.getClass().getName(), true, task, MailDef.instance(MailEnum.SdfForApproval), null);
		}
	}

	/**
	 * CRM
	 * 
	 * @param siteVisitReport
	 * @param user
	 * @param task
	 * @throws Exception
	 */
	public void finalRejectTask(SiteVisitReport siteVisitReport, Users user, Tasks task) throws Exception {
		// siteVisitReport.setSiteVisitReportStatusEnum(SiteVisitReportStatusEnum.Rejected);
		for (Signoff signoff : siteVisitReport.getSignOffs()) {
			signoff.setAccept(false);
			signoff.setSignOffDate(null);
			dao.update(signoff);
		}

		RegionTown rt = RegionTownService.instance().findByTown(siteVisitReport.getCompany().getResidentialAddress().getTown());
		List<Users> signoffs = new ArrayList<>();
		signoffs.add(rt.getClo().getUsers());

		TasksService.instance().createTaskUser(signoffs, task.getTargetClass(), task.getTargetKey(), "Site Visit Report was rejected please login and view the reason and make the relevant changes.", user, true, true, task, ConfigDocProcessEnum.SITE_VISIT_REPORT, false);
		TasksService.instance().completeTask(task);
	}
	
	/**
	 * Sets the rejection reasons for the site visit report
	 * @param className
	 * @param targetkey
	 * @param users
	 * @param rejectReasonsSelected
	 * @param configProcess
	 * @throws Exception
	 */
	public void setRejectedReasons(String className,Long targetkey, Users users, List<RejectReasons> rejectReasonsSelected, ConfigDocProcessEnum configProcess) throws Exception {
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		if (rejectReasonsSelected != null && rejectReasonsSelected.size() != 0) {
			entityList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(targetkey, className, rejectReasonsSelected, users, configProcess));
		} else {
			RejectReasonMultipleSelectionService.instance().clearEntries(targetkey, className, configProcess);
		}
		if (entityList.size() != 0) {
			dao.createBatch(entityList);
		}
	}

	/**
	 * CRM
	 * 
	 * @param siteVisitReport
	 * @param user
	 * @param task
	 * @throws Exception
	 */
	public void finalApproveTask(SiteVisitReport siteVisitReport, Users user, Tasks task) throws Exception {
		siteVisitReport.setSiteVisitReportStatusEnum(SiteVisitReportStatusEnum.Approved);
		siteVisitReport.setDateApproved(new Date());
		update(siteVisitReport);
		TasksService.instance().completeTask(task);
	}

	public List<SiteVisitReport> populateInformationList(List<SiteVisitReport> siteVisitReportList) throws Exception {
		for (SiteVisitReport siteVisitReport : siteVisitReportList) {
			populateInformation(siteVisitReport);
		}
		return siteVisitReportList;
	}

	public SiteVisitReport populateInformation(SiteVisitReport siteVisitReport) throws Exception {
		// Checks
		if (companyService == null) {
			companyService = new CompanyService();
		}
		// sets company
		siteVisitReport.setCompany(companyService.findByKey(siteVisitReport.getCompany().getId()));
		// sets postal address
		siteVisitReport.getCompany().setPostalAddress(AddressService.instance().findByKey(siteVisitReport.getCompany().getPostalAddress().getId()));

		// clear service layers
		companyService = null;
		return siteVisitReport;
	}

	/**
	 * 
	 * 
	 * 
	 * Info to return:
	 * 0 - Can Continue with verification 
	 * 1 - No Site Visit Found in the last 6 months
	 * 2 - Site visit found however not approved
	 * @param dgVerification
	 * @see DgVerification
	 * @see SiteVisitReport
	 * @see SiteVisit
	 * @return Integer
	 * @throws Exception
	 */
	public Integer checkSixMonthRequirements(DgVerification dgVerification, Company company) throws Exception {
		Integer returnValue = 1;
		Date today = getSynchronizedDate();
		Date sixMonthsAgoDate = GenericUtility.getStartOfDay(GenericUtility.deductMonthsFromDate(today, 6));
		System.out.println("Today: " + today);
		System.out.println("6 Months Ago: " + sixMonthsAgoDate);
		boolean siteVisitReportFound = false;
		boolean siteVisitFound = false;
		
		List<SiteVisitReport> locatedSiteVisitReports = locateSiteVisitReportBetweenDatesByVisitDate(sixMonthsAgoDate, today, company);
		if (locatedSiteVisitReports.size() > 0) {
			siteVisitReportFound = true;
			if (locatedSiteVisitReports.get(0).getSiteVisitReportStatusEnum() != null && locatedSiteVisitReports.get(0).getSiteVisitReportStatusEnum() == SiteVisitReportStatusEnum.Approved) {
				returnValue = 0;
			} else {
				returnValue = 2;
			}
		} else {
			siteVisitReportFound = false;
		}
		
		// if no site visit report found, does a check against sit visit history report 
		if (!siteVisitReportFound) {
			SiteVisitService siteVisitService = new SiteVisitService();
			List<SiteVisit> sitVisit = siteVisitService.locateSiteVisitBetweenDatesByVisitDate(sixMonthsAgoDate, today, company);
			if (sitVisit.size() > 0) {
				siteVisitFound = true;
				if (sitVisit.get(0).getApprovalEnum() != null && sitVisit.get(0).getApprovalEnum() == ApprovalEnum.Approved) {
					returnValue = 0;
				} else {
					returnValue = 2;
				}
			} else {
				siteVisitFound = false;
			}
			siteVisitService = null;
		}
		
		// if none found, sets to no found
		if (!siteVisitReportFound && !siteVisitFound) {
			returnValue = 1;
		}
		return returnValue;
	}
	
	/**
	 * 
	 * 
	 * 
	 * @param fromDate
	 * @param toDate
	 * @param company
	 * @return List<SiteVisitReport>
	 * @throws Exception
	 */
	public List<SiteVisitReport> locateSiteVisitReportBetweenDatesByVisitDate(Date fromDate, Date toDate, Company company) throws Exception{
		return dao.locateSiteVisitReportBetweenDatesByVisitDate(fromDate, toDate, company.getId());
	}
	
}
