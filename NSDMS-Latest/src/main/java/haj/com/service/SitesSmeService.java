package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SitesSmeDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.ConfigDoc;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.DocumentTracker;
import haj.com.entity.SDFCompany;
import haj.com.entity.Sites;
import haj.com.entity.SitesSme;
import haj.com.entity.SmeQualifications;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.WorkPlaceApproval;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DocumentTrackerEnum;
import haj.com.entity.lookup.Region;
import haj.com.entity.lookup.RegionTown;
import haj.com.entity.lookup.RejectReasons;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.RegionService;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.RejectReasonsService;
import haj.com.utils.GenericUtility;

public class SitesSmeService extends AbstractService {
	/** The dao. */
	private SitesSmeDAO dao = new SitesSmeDAO();

	/** The service level */
	private ConfigDocService configDocService = new ConfigDocService();
	private SmeQualificationsService smeQualificationsService = new SmeQualificationsService();
	private RegionService regionService;

	/** Instance of service level */
	private static SitesSmeService sitesSmeService;

	public static synchronized SitesSmeService instance() {
		if (sitesSmeService == null) {
			sitesSmeService = new SitesSmeService();
		}
		return sitesSmeService;
	}

	/**
	 * Get all SitesSme
	 * 
	 * @author TechFinium
	 * @see SitesSme
	 * @return a list of {@link haj.com.entity.SitesSme}
	 * @throws Exception
	 *             the exception
	 */
	public List<SitesSme> allSitesSme() throws Exception {
		return dao.allSitesSme();
	}

	/**
	 * Create or update SitesSme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SitesSme
	 */
	public void create(SitesSme entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update SitesSme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SitesSme
	 */
	public void update(SitesSme entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SitesSme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SitesSme
	 */
	public void delete(SitesSme entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SitesSme}
	 * @throws Exception
	 *             the exception
	 * @see SitesSme
	 */
	public SitesSme findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SitesSme}
	 * @throws Exception
	 *             the exception
	 * @see SitesSme
	 */
	public SitesSme findByKeyPopulateInformation(long id, ConfigDocProcessEnum configDocProcess,
			CompanyUserTypeEnum companyUserType, Company company) throws Exception {
		return populateAdditionalInformation(dao.findByKey(id), configDocProcess, companyUserType, company);
	}

	/**
	 * Find SitesSme by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SitesSme}
	 * @throws Exception
	 *             the exception
	 * @see SitesSme
	 */
	public List<SitesSme> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	public Integer countByRsaIdOrPassort(Company company, String rsaPassport, boolean rsaIdCheck) throws Exception {
		return dao.countByRsaIdOrPassort(company.getId(), rsaPassport, rsaIdCheck);
	}

	public SitesSme findByRsaIdOrPassort(String rsaPassport, boolean rsaIdCheck) throws Exception {
		return dao.findByRsaIdOrPassort(rsaPassport, rsaIdCheck);
	}

	public Integer countByRsaIdOrPassortNotDeactivated(Company company, String rsaPassport, boolean rsaIdCheck,
			ApprovalEnum status) throws Exception {
		return dao.countByRsaIdOrPassortNotDeactivated(company.getId(), rsaPassport, rsaIdCheck, status);
	}
	
	// new method added for resolution of mentor issue REF:: 1660
	public SitesSme findByRsaIdOrPassortNotDeactivated(Company company, String rsaPassport, boolean rsaIdCheck,
			ApprovalEnum status) throws Exception {
		return dao.findByRsaIdOrPassortNotDeactivated(company.getId(), rsaPassport, rsaIdCheck, status);
	}

	public List<SitesSme> allSitesSmeByCompany(Company company) throws Exception {
		return dao.allSitesSmeByCompany(company);
	}

	/**
	 * Lazy load SitesSme
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SitesSme}
	 * @throws Exception
	 *             the exception
	 */
	public List<SitesSme> allSitesSme(int first, int pageSize) throws Exception {
		return dao.allSitesSme(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SitesSme for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the SitesSme
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SitesSme.class);
	}

	/**
	 * Lazy load SitesSme with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            SitesSme class
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
	 * @return a list of {@link haj.com.entity.SitesSme} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SitesSme> allSitesSme(Class<SitesSme> class1, int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SitesSme>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of SitesSme for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            SitesSme class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SitesSme entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SitesSme> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * All SitesSme by Company Id
	 * 
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @param company
	 * @return List<SitesSme>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SitesSme> allSitesSmeByCompany(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters, Company company, ConfigDocProcessEnum configDocProcessEnum,
			CompanyUserTypeEnum companyUserType) throws Exception {
		String hql = "select o from SitesSme o where o.company.id = :companyId and (o.softDelete is null or o.softDelete is false)";
		filters.put("companyId", company.getId());
		return populateAdditionalInformationList(
				(List<SitesSme>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql),
				configDocProcessEnum, companyUserType, company);
	}

	@SuppressWarnings("unchecked")
	public List<SitesSme> allSitesSme(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) throws Exception {
		String hql = "select o from SitesSme o where o.id is not null";
		return populateAdditionalInformationList(
				(List<SitesSme>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	@SuppressWarnings("unchecked")
	public List<SitesSme> allSitesSmeNoAdditionalPopulation(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from SitesSme o where o.id is not null";
		return (List<SitesSme>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllSitesSmeNoAdditionalPopulation(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SitesSme o where o.id is not null";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<SitesSme> allSitesSmeNoAdditionalPopulationByYear(int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters, Integer year) throws Exception {
		String hql = "select o from SitesSme o where o.id is not null and YEAR(createDate) = :year";
		filters.put("year", year);
		return (List<SitesSme>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllSitesSmeNoAdditionalPopulationByYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SitesSme o where o.id is not null and YEAR(createDate) = :year";
		return dao.countWhere(filters, hql);
	}

	public SitesSme populateAdditionalInformationBySitesSme(SitesSme sitesSme) throws Exception {
		populateAdditionalInformation(sitesSme, ConfigDocProcessEnum.WORKPLACE_APPROVAL, CompanyUserTypeEnum.User, sitesSme.getCompany());
		return sitesSme;
	}
	
	public SitesSme populateAdditionalInformationNoQuals(SitesSme sitesSme) throws Exception {
		populateAdditionalInformationNoQuals(sitesSme, ConfigDocProcessEnum.WORKPLACE_APPROVAL, CompanyUserTypeEnum.User, sitesSme.getCompany());
		return sitesSme;
	}
	
	

	private List<SitesSme> populateAdditionalInformationList(List<SitesSme> sitesSmeList) throws Exception {
		for (SitesSme sitesSme : sitesSmeList) {
			populateAdditionalInformation(sitesSme, ConfigDocProcessEnum.WORKPLACE_APPROVAL, CompanyUserTypeEnum.User,
					sitesSme.getCompany());
		}
		return sitesSmeList;
	}

	/**
	 * Count All SitesSme by Company Id
	 * 
	 * @param filters
	 * @return int the count
	 * @throws Exception
	 */
	public int countAllSitesSmeByCompany(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SitesSme o where o.company.id = :companyId";
		return dao.countWhere(filters, hql);
	}

	/**
	 * All SitesSme by Company Id and site Id
	 * 
	 * @param first
	 * @param pageSize
	 * @param sortField
	 * @param sortOrder
	 * @param filters
	 * @param company
	 * @param site
	 * @return List<SitesSme>
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public List<SitesSme> allSitesSmeByCompanyAndSite(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters, Company company, Sites site, ConfigDocProcessEnum configDocProcess,
			CompanyUserTypeEnum companyUserType) throws Exception {
		String hql = "select o from SitesSme o where o.company.id = :companyId and o.sites.id = :siteId";
		filters.put("companyId", company.getId());
		filters.put("siteId", site.getId());
		return populateAdditionalInformationList(
				(List<SitesSme>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql),
				configDocProcess, companyUserType, company);
	}

	/**
	 * Count All SitesSme by Company Id and site Id
	 * 
	 * @param filters
	 * @return int
	 * @throws Exception
	 */
	public int countAllSitesSmeByCompanyAndSite(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SitesSme o where o.company.id = :companyId and o.sites.id = :siteId";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<SitesSme> avalaibleSiteSmeForSelection(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters, WorkPlaceApproval workPlaceApproval, ConfigDocProcessEnum configDocProcessEnum,
			CompanyUserTypeEnum companyUserType) throws Exception {
		String hql = "select o from SitesSme o where o.company.id = :companyId and o.status = :statusValue and o.active = :activeValue ";
		if (workPlaceApproval.getSites() != null) {
			hql += "and o.sites.id = :siteId ";
			// filters.put("activeValue", true);
			filters.put("siteId", workPlaceApproval.getSites().getId());
		}
		hql += "and o.id not in (select x.sitesSme.id from WorkPlaceApprovalSites x where x.workPlaceApproval.id = :workplaceApprovalId)";
		filters.put("companyId", workPlaceApproval.getCompany().getId());
		filters.put("statusValue", ApprovalEnum.Approved);
		filters.put("activeValue", true);
		filters.put("workplaceApprovalId", workPlaceApproval.getId());
		return (List<SitesSme>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<SitesSme> avalaibleSiteSmeForSelectionOld(int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters, WorkPlaceApproval workPlaceApproval, ConfigDocProcessEnum configDocProcessEnum,
			CompanyUserTypeEnum companyUserType) throws Exception {
		String hql = "select o from SitesSme o where o.company.id = :companyId and o.status = :statusValue and o.active = :activeValue ";
		if (workPlaceApproval.getSites() != null) {
			hql += "and o.sites.id = :siteId ";
			// filters.put("activeValue", true);
			filters.put("siteId", workPlaceApproval.getSites().getId());
		}
		hql += "and o.id not in (select x.sitesSme.id from WorkPlaceApprovalSites x where x.workPlaceApproval.id = :workplaceApprovalId)";
		filters.put("companyId", workPlaceApproval.getCompany().getId());
		filters.put("statusValue", ApprovalEnum.Approved);
		filters.put("activeValue", true);
		filters.put("workplaceApprovalId", workPlaceApproval.getId());
		return populateAdditionalInformationList(
				(List<SitesSme>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql),
				configDocProcessEnum, companyUserType, workPlaceApproval.getCompany());
	}

	public List<SitesSme> allSitesSmeByCompanyAndStatus(Company entity, ApprovalEnum statusValue, boolean active)
			throws Exception {
		List<SitesSme> list = dao.allSitesSmeByCompanyAndStatus(entity, statusValue, active);
		for (SitesSme sme : list) {
			populateAdditionalInformation1(sme);
		}
		return list;
	}

	public int countAvalaibleSiteSmeForSelection(Map<String, Object> filters, WorkPlaceApproval workPlaceApproval)
			throws Exception {
		String hql = "select count(o) from SitesSme o where o.company.id = :companyId and o.status = :statusValue and o.active = :activeValue ";
		if (workPlaceApproval.getSites() != null) {
			hql += "and o.sites.id = :siteId ";
		}
		hql += " and o.id not in (select x.sitesSme.id from WorkPlaceApprovalSites x where x.workPlaceApproval.id = :workplaceApprovalId)";
		return dao.countWhere(filters, hql);
	}

	public SitesSme createNewSitesSme(ConfigDocProcessEnum configDocProcessEnum, CompanyUserTypeEnum companyUserType,
			Company company) throws Exception {
		// SitesSme sitesSme = populateAdditionalInformation(new SitesSme(),
		// configDocProcessEnum, companyUserType, company);
		SitesSme sitesSme = populateAdditionalMentorInformation(new SitesSme(), configDocProcessEnum, companyUserType,
				company);
		return sitesSme;
	}

	/**
	 * Creates / Updates SitesSme and sends first in process configured for
	 * ConfigDocProcessEnum.SITE_SME_REGISTRATION
	 * 
	 * @param entity
	 * @param user
	 * @throws Exception
	 */
	public void firstInProcessSend(SitesSme entity, Users user) throws Exception {
		/*
		 * Validations
		 */
		String error = "";
		if (entity.getDocs() != null) {
			for (Doc doc : entity.getDocs()) {
				if (doc.getId() != null) {
					DocByte docByte = DocService.instance().findDocByteByKey(doc.getId());
					if (docByte != null)
						doc.setData(docByte.getData());
				}
				if ((doc.getData() == null || doc.getData().length == 0)
						&& doc.getConfigDoc().getRequiredDocument() != null
						&& doc.getConfigDoc().getRequiredDocument() != null) {
					error += "Upload " + doc.getConfigDoc().getName() + " for Mentor Registration";
				}
			}
		}
		if (error.length() > 0)
			throw new ValidationException(error);

		// creates / updates SitesSme
		entity.setSoftDelete(false);
		entity.setStatus(ApprovalEnum.PendingApproval);
		entity.setLockSme(true);
		if (entity.getApprovedDate() != null) {
			entity.setApprovedDate(null);
		}
		create(entity);

		// creates docs for sme user
		for (Doc doc : entity.getDocs()) {
			if (doc.getId() == null && doc.getData() != null) {
				doc.setTargetClass(entity.getClass().getName());
				doc.setTargetKey(entity.getId());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
				// DocumentTracker documentTracker = new DocumentTracker(doc,
				// user, new java.util.Date(), DocumentTrackerEnum.Upload);
				// DocumentTrackerService se = new DocumentTrackerService();
				// se.create(documentTracker);
			}
		}
		SmeQualifications smeQualificationsParent = new SmeQualifications();
		smeQualificationsParent.setStatus(ApprovalEnum.PendingApproval);
		SmeQualificationsService.instance().create(smeQualificationsParent);
		// creates sme qualification link
		for (SmeQualifications smeQualification : entity.getSmeQualificationsList()) {
			System.out.println("smeQualification:"+smeQualification.getLearnerAssignedCount());
			smeQualification.setSmeQualificationsParent(smeQualificationsParent);
			
			smeQualification.setStatus(ApprovalEnum.PendingApproval);
			SmeQualificationsService.instance().createUpdateSmeQualifications(smeQualification, user);
		}
		RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());

		List<Users> cloList = new ArrayList<>();
		cloList.add(rt.getClo().getUsers());
		TasksService.instance().findFirstInProcessAndCreateTask("", user, entity.getId(), SitesSme.class.getName(),
				true, ConfigDocProcessEnum.SITE_SME_REGISTRATION, null, cloList);

		sendAcknoledgementEmail(user, entity, entity.getSmeQualificationsList());
		// TasksService.instance().findFirstInProcessAndCreateTask(user,
		// entity.getId(), SitesSme.class.getName(),
		// ConfigDocProcessEnum.SITE_SME_REGISTRATION, false);
		// if first in process is clo
		// TasksService.instance().findFirstInProcessAndCreateTask(null, user,
		// entity.getId(), SitesSme.class.getName(), true,
		// ConfigDocProcessEnum.SITE_SME_REGISTRATION, null,
		// locateClrCrmBySme(entity));
	}

	public void firstInProcessSendUpdate(SitesSme entity, Users user) throws Exception {
		if (entity.getStatus() == ApprovalEnum.Rejected) {
			entity.setStatus(ApprovalEnum.PendingApproval);
		}
		entity.setLockSme(true);
		create(entity);

		// creates docs for sme user
		for (Doc doc : entity.getDocs()) {
			if (doc.getId() == null && doc.getData() != null) {
				doc.setTargetClass(entity.getClass().getName());
				doc.setTargetKey(entity.getId());
				DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), user);
				DocumentTracker documentTracker = new DocumentTracker(doc, user, new java.util.Date(),
						DocumentTrackerEnum.Upload);
				DocumentTrackerService se = new DocumentTrackerService();
				se.create(documentTracker);
			}
		}
		SmeQualifications smeQualificationsParent = new SmeQualifications();
		smeQualificationsParent.setStatus(ApprovalEnum.PendingApproval);
		SmeQualificationsService.instance().create(smeQualificationsParent);
		// creates sme qualification link
		List<SmeQualifications> list = new ArrayList<>();
		for (SmeQualifications smeQualification : entity.getSmeQualificationsList()) {
			if (smeQualification.getStatus() != ApprovalEnum.Approved
					&& smeQualification.getStatus() != ApprovalEnum.Rejected) {
				list.add(smeQualification);
				smeQualification.setSmeQualificationsParent(smeQualificationsParent);
				smeQualification.setStatus(ApprovalEnum.PendingApproval);
			}

			SmeQualificationsService.instance().createUpdateSmeQualifications(smeQualification, user);
		}
		/*
		 * if(list.size() == 0) { throw new Exception(
		 * "Pleacse select different qualification"); }
		 */
		RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());

		List<Users> cloList = new ArrayList<>();
		cloList.add(rt.getClo().getUsers());
		TasksService.instance().findFirstInProcessAndCreateTask("", user, entity.getId(), SitesSme.class.getName(),
				true, ConfigDocProcessEnum.SITE_SME_UPDATE, null, cloList);

		sendAcknoledgementEmail(user, entity, list);
	}

	public void sitessmeCreateDeleteWorkflow(SitesSme entity, Users user) throws Exception {
		entity.setLockSme(true);
		create(entity);

		RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
		List<Users> cloList = new ArrayList<>();
		cloList.add(rt.getClo().getUsers());
		TasksService.instance().findFirstInProcessAndCreateTask("", user, entity.getId(), SitesSme.class.getName(),
				true, ConfigDocProcessEnum.SITE_SME_DELETE, null, cloList);
		sendDeleteAcknoledgementEmail(user, entity);
	}

	public List<Users> locateClrCrmBySme(SitesSme entity) throws Exception {
		List<Users> notifyUsers = new ArrayList<Users>();
		RegionTown rt = null;
		if (entity.getSites() == null) {
			rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
		} else {
			rt = RegionTownService.instance().findByTown(entity.getSites().getRegisteredAddress().getTown());
		}
		UsersService usersService = new UsersService();

		Users cloUser = rt.getClo().getUsers();
		if (cloUser != null && cloUser.getId() != null) {
			notifyUsers.add(usersService.findByKey(cloUser.getId()));
		}

		/*
		 * Users crmUser = rt.getCrm().getUsers(); if (crmUser != null &&
		 * crmUser.getId() != null) {
		 * notifyUsers.add(usersService.findByKey(crmUser.getId())); }
		 */
		usersService = null;
		if (notifyUsers.size() == 0) {
			throw new Exception("Unable to locate CLO");
		} else {
			return notifyUsers;
		}
	}

	/** Work flow actions */
	public void clientSubmission(SitesSme entity, Users user, Tasks tasks, boolean editCheck, boolean uploadCheck)
			throws Exception {
		SitesSmeValidation(entity, editCheck, uploadCheck);
		updateUploadInformation(entity, user, editCheck, uploadCheck);
		TasksService.instance().findFirstInProcessAndCreateTask(user, entity.getId(), SitesSme.class.getName(),
				ConfigDocProcessEnum.SITE_SME_REGISTRATION, false);
		// TasksService.instance().findFirstInProcessAndCreateTask(null, user,
		// entity.getId(), SitesSme.class.getName(), true,
		// ConfigDocProcessEnum.SITE_SME_REGISTRATION, null,
		// locateClrCrmBySme(entity));
		TasksService.instance().completeTask(tasks);
		RejectReasonMultipleSelectionService.instance().clearEntries(entity.getId(), entity.getClass().getName(),
				ConfigDocProcessEnum.SITE_SME_REGISTRATION);
	}

	public void completeWorkflow(SitesSme entity, Users user, Tasks tasks, boolean editCheck, boolean uploadCheck)
			throws Exception {
		SitesSmeValidation(entity, editCheck, uploadCheck);
		updateUploadInformation(entity, user, editCheck, uploadCheck);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
		RejectReasonMultipleSelectionService.instance().clearEntries(entity.getId(), entity.getClass().getName(),
				ConfigDocProcessEnum.SITE_SME_REGISTRATION);
	}

	/**
	 * Rejects the work flow to the previous in process if first in process
	 * sends to sdf Also creates reject reasons
	 * 
	 * @param entity
	 * @param user
	 * @param tasks
	 * @param rejectionReasons
	 * @param editCheck
	 * @param uploadCheck
	 * @throws Exception
	 */
	public void rejectWorkflow(SitesSme entity, Users user, Tasks tasks, List<RejectReasons> rejectionReasons,
			boolean editCheck, boolean uploadCheck) throws Exception {
		SitesSmeValidation(entity, editCheck, uploadCheck);
		updateUploadInformation(entity, user, editCheck, uploadCheck);
		if (tasks.getFirstInProcess() == true) {
			// adds the list of users to send task back to
			SDFCompanyService sdfCompanyService = new SDFCompanyService();
			List<Users> signoffs = new ArrayList<>();
			signoffs.addAll(sdfCompanyService.findPrimaryAndLabourSDFUsers(entity.getCompany()));
			TasksService.instance().createTaskUser(signoffs, tasks.getTargetClass(), tasks.getTargetKey(),
					"Your Mentor application was rejected please login and view the reason and make the relevant changes.",
					user, true, true, tasks, ConfigDocProcessEnum.SITE_SME_REGISTRATION, false);
			TasksService.instance().completeTask(tasks);
			sdfCompanyService = null;
		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask(user, tasks, null);
		}
		List<IDataEntity> entityCreateList = new ArrayList<IDataEntity>();
		entityCreateList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(),
				entity.getClass().getName(), rejectionReasons, user, ConfigDocProcessEnum.SITE_SME_REGISTRATION));
		if (entityCreateList.size() != 0) {
			dao.createBatch(entityCreateList);
		}
	}

	public void systemRejectWorkflow(SitesSme entity, Users admin, Tasks tasks) throws Exception {
		SDFCompanyService sdfCompanyService = new SDFCompanyService();
		Users user = new Users();
		SDFCompany sdFCompany = sdfCompanyService.findPrimarySDF(entity.getCompany());
		user = sdFCompany.getSdf();

		entity.setLockSme(false);
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setApprovedDate(getSynchronizedDate());
		dao.update(entity);
		for (SmeQualifications smeQualification : entity.getSmeQualificationsList()) {
			smeQualification.setStatus(ApprovalEnum.Rejected);
			smeQualification.setApproveUser(admin);
			smeQualification.setApprovedDate(getSynchronizedDate());
			SmeQualificationsService.instance().create(smeQualification);
		}

		TasksService.instance().completeTask(tasks);

		sendRejectEmail(user, entity, entity.getSmeQualificationsList());
	}

	public void approveWorkflow(SitesSme entity, Users user, Tasks tasks, boolean editCheck, boolean uploadCheck)
			throws Exception {
		SitesSmeValidation(entity, editCheck, uploadCheck);
		updateUploadInformation(entity, user, editCheck, uploadCheck);
		TasksService.instance().findNextInProcessAndCreateTask(user, tasks, null, false);
	}

	public void finalApproveWorkflow(SitesSme entity, Users user, Tasks tasks, boolean editCheck, boolean uploadCheck)
			throws Exception {
		SitesSmeValidation(entity, editCheck, uploadCheck);
		updateUploadInformation(entity, user, editCheck, uploadCheck);
		entity.setLockSme(false);
		entity.setSoftDelete(false);
		entity.setStatus(ApprovalEnum.Approved);
		entity.setApprovedDate(getSynchronizedDate());
		entity.setApproveUser(user);
		dao.update(entity);

		for (SmeQualifications smeQualification : entity.getSmeQualificationsList()) {
			smeQualification.setStatus(ApprovalEnum.Approved);
			smeQualification.setApproveUser(user);
			smeQualification.setApprovedDate(getSynchronizedDate());
			SmeQualificationsService.instance().create(smeQualification);
		}

		TasksService.instance().completeTask(tasks);
		RejectReasonMultipleSelectionService.instance().clearEntries(entity.getId(), entity.getClass().getName(),
				ConfigDocProcessEnum.SITE_SME_REGISTRATION);
		sendApprovateEmail(tasks.getCreateUser(), entity, tasks, entity.getSmeQualificationsList());
	}
	
	public void finalApproveWorkflow(SitesSme entity, Users user, Tasks tasks) throws Exception {
		List<IDataEntity>updateBach = new ArrayList<>();
		entity.setLockSme(false);
		entity.setSoftDelete(false);
		entity.setStatus(ApprovalEnum.Approved);
		entity.setApprovedDate(getSynchronizedDate());
		entity.setApproveUser(user);
		updateBach.add(entity);
		//dao.update(entity);
		
		
		for (SmeQualifications smeQualification : entity.getSmeQualificationsList()) {
			smeQualification.setStatus(ApprovalEnum.Approved);
			smeQualification.setApproveUser(user);
			smeQualification.setApprovedDate(getSynchronizedDate());
			updateBach.add(smeQualification);
		}
		
		dao.updateBatch(updateBach);
		TasksService.instance().completeTask(tasks);
		//RejectReasonMultipleSelectionService.instance().clearEntries(entity.getId(), entity.getClass().getName(),ConfigDocProcessEnum.SITE_SME_REGISTRATION);
		sendApprovateEmail(tasks.getCreateUser(), entity, tasks, entity.getSmeQualificationsList());
	}

	public void finalApproveUpdateWorkflow(SitesSme entity, Users user, Tasks tasks, boolean editCheck,
			boolean uploadCheck) throws Exception {
		SitesSmeValidation(entity, editCheck, uploadCheck);
		updateUploadInformation(entity, user, editCheck, uploadCheck);
		entity.setLockSme(false);
		/*
		 * entity.setStatus(ApprovalEnum.Approved);
		 * entity.setApprovedDate(getSynchronizedDate());
		 * entity.setApproveUser(user);
		 */
		if (entity.getStatus() == ApprovalEnum.PendingApproval || entity.getStatus() == ApprovalEnum.Rejected) {
			entity.setStatus(ApprovalEnum.Approved);
			entity.setApprovedDate(getSynchronizedDate());
			entity.setApproveUser(user);
		}
		dao.update(entity);

		List<SmeQualifications> list = new ArrayList<>();

		for (SmeQualifications smeQualification : entity.getSmeQualificationsList()) {
			// if(smeQualification.getStatus() != ApprovalEnum.Approved &&
			// smeQualification.getStatus() != ApprovalEnum.Rejected)
			if (smeQualification.getStatus() != ApprovalEnum.Approved
					&& smeQualification.getStatus() != ApprovalEnum.Rejected) {
				list.add(smeQualification);
				smeQualification.setStatus(ApprovalEnum.Approved);
				smeQualification.setApproveUser(user);
				smeQualification.setApprovedDate(getSynchronizedDate());
				SmeQualificationsService.instance().create(smeQualification);
			}
		}

		TasksService.instance().completeTask(tasks);
		RejectReasonMultipleSelectionService.instance().clearEntries(entity.getId(), entity.getClass().getName(),
				ConfigDocProcessEnum.SITE_SME_UPDATE);
		sendApprovateEmail(tasks.getCreateUser(), entity, tasks, list);
	}
	
	public void finalApproveUpdateWorkflow(SitesSme entity, Users user, Tasks tasks ) throws Exception {
		List<IDataEntity>updateBatch = new ArrayList<>();
		entity.setLockSme(false);
		if (entity.getStatus() == ApprovalEnum.PendingApproval || entity.getStatus() == ApprovalEnum.Rejected) {
			entity.setStatus(ApprovalEnum.Approved);
			entity.setApprovedDate(getSynchronizedDate());
			entity.setApproveUser(user);
		}
		//dao.update(entity);
		updateBatch.add(entity);

		List<SmeQualifications> list = new ArrayList<>();
		
		for (SmeQualifications smeQualification : entity.getSmeQualificationsList()) {
			if (smeQualification.getStatus() != ApprovalEnum.Approved
					&& smeQualification.getStatus() != ApprovalEnum.Rejected) {
				list.add(smeQualification);
				smeQualification.setStatus(ApprovalEnum.Approved);
				smeQualification.setApproveUser(user);
				smeQualification.setApprovedDate(getSynchronizedDate());
				updateBatch.add(smeQualification);
				//SmeQualificationsService.instance().create(smeQualification);
			}
		}
		dao.updateBatch(updateBatch);
		TasksService.instance().completeTask(tasks);
		//RejectReasonMultipleSelectionService.instance().clearEntries(entity.getId(), entity.getClass().getName(), ConfigDocProcessEnum.SITE_SME_UPDATE);
		sendApprovateEmail(tasks.getCreateUser(), entity, tasks, list);
	}

	public void finalApproveDeleteWorkflow(SitesSme entity, Users user, Tasks tasks, boolean editCheck,
			boolean uploadCheck) throws Exception {
		entity.setLockSme(false);
		entity.setSoftDelete(true);
		entity.setStatus(ApprovalEnum.Deactivated);
		dao.update(entity);
		TasksService.instance().completeTask(tasks);
		RejectReasonMultipleSelectionService.instance().clearEntries(entity.getId(), entity.getClass().getName(),
				ConfigDocProcessEnum.SITE_SME_DELETE);
		sendDeleteApprovateEmail(tasks.getCreateUser(), entity, tasks);

	}

	public void finalRejectWorkflow(SitesSme entity, Users user, Tasks tasks, List<RejectReasons> rejectionReasons,
			boolean editCheck, boolean uploadCheck) throws Exception {
		SitesSmeValidation(entity, editCheck, uploadCheck);
		updateUploadInformation(entity, user, editCheck, uploadCheck);
		entity.setLockSme(false);
		entity.setStatus(ApprovalEnum.Rejected);
		entity.setApprovedDate(getSynchronizedDate());
		entity.setApproveUser(user);
		dao.update(entity);
		for (SmeQualifications smeQualification : entity.getSmeQualificationsList()) {
			smeQualification.setStatus(ApprovalEnum.Rejected);
			smeQualification.setApproveUser(user);
			smeQualification.setApprovedDate(getSynchronizedDate());
			SmeQualificationsService.instance().create(smeQualification);
		}

		TasksService.instance().completeTask(tasks);
		List<IDataEntity> entityCreateList = new ArrayList<IDataEntity>();
		// entityCreateList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(),
		// entity.getClass().getName(), rejectionReasons, user,
		// ConfigDocProcessEnum.SITE_SME_REGISTRATION));
		entityCreateList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(
				entity.getSmeQualificationsList().get(0).getSmeQualificationsParent().getId(),
				entity.getClass().getName(), rejectionReasons, user, ConfigDocProcessEnum.SITE_SME_REGISTRATION));
		if (entityCreateList.size() != 0) {
			dao.createBatch(entityCreateList);
		}

		sendRejectEmail(tasks.getCreateUser(), entity, rejectionReasons, entity.getSmeQualificationsList());
	}

	public void finalRejectUpdateWorkflow(SitesSme entity, Users user, Tasks tasks,
			List<RejectReasons> rejectionReasons, boolean editCheck, boolean uploadCheck) throws Exception {
		SitesSmeValidation(entity, editCheck, uploadCheck);
		updateUploadInformation(entity, user, editCheck, uploadCheck);
		entity.setLockSme(false);
		// entity.setStatus(ApprovalEnum.Approved);
		// entity.setApprovedDate(getSynchronizedDate());
		// entity.setApproveUser(user);
		dao.update(entity);
		List<SmeQualifications> list = new ArrayList<>();
		for (SmeQualifications smeQualification : entity.getSmeQualificationsList()) {
			if (smeQualification.getStatus() != ApprovalEnum.Approved
					&& smeQualification.getStatus() != ApprovalEnum.Rejected) {
				smeQualification.setStatus(ApprovalEnum.Rejected);
				smeQualification.setApproveUser(user);
				smeQualification.setApprovedDate(getSynchronizedDate());
				SmeQualificationsService.instance().create(smeQualification);
				list.add(smeQualification);
			}
		}

		TasksService.instance().completeTask(tasks);
		List<IDataEntity> entityCreateList = new ArrayList<IDataEntity>();

		// entityCreateList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(),
		// entity.getClass().getName(), rejectionReasons, user,
		// ConfigDocProcessEnum.SITE_SME_UPDATE));
		entityCreateList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(
				entity.getSmeQualificationsList().get(0).getSmeQualificationsParent().getId(),
				entity.getClass().getName(), rejectionReasons, user, ConfigDocProcessEnum.SITE_SME_UPDATE));
		if (entityCreateList.size() != 0) {
			dao.createBatch(entityCreateList);
		}

		sendRejectEmail(tasks.getCreateUser(), entity, rejectionReasons, list);
	}

	public void finalRejectDeleteWorkflow(SitesSme entity, Users user, Tasks tasks,
			List<RejectReasons> rejectionReasons, boolean editCheck, boolean uploadCheck) throws Exception {
		entity.setLockSme(false);
		entity.setSoftDelete(false);
		entity.setStatus(ApprovalEnum.Approved);		
		dao.update(entity);

		TasksService.instance().completeTask(tasks);
		List<IDataEntity> entityCreateList = new ArrayList<IDataEntity>();

		if (entity.getSmeQualificationsList().get(0) != null
				&& entity.getSmeQualificationsList().get(0).getSmeQualificationsParent() != null) {
			entityCreateList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(
					entity.getSmeQualificationsList().get(0).getSmeQualificationsParent().getId(),
					entity.getClass().getName(), rejectionReasons, user, ConfigDocProcessEnum.SITE_SME_DELETE));
		} else {
			entityCreateList.addAll(RejectReasonMultipleSelectionService.instance().removeCreateLinks(entity.getId(),
					entity.getClass().getName(), rejectionReasons, user, ConfigDocProcessEnum.SITE_SME_DELETE));
		}
		if (entityCreateList.size() != 0) {
			dao.createBatch(entityCreateList);
		}
		// Users u, SitesSme entity, Tasks tasks,
		// List<RejectReasons>rejectReasons
		sendDeleteRejectEmail(user, entity, tasks, rejectionReasons);
	}

	/**
	 * Populates additional Information for List<SitesSme>
	 * 
	 * @param sitesSmeList
	 * @return List<SitesSme>
	 * @throws Exception
	 */
	public List<SitesSme> populateAdditionalInformationList(List<SitesSme> sitesSmeList,
			ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType, Company company)
			throws Exception {
		for (SitesSme sitesSme : sitesSmeList) {
			populateAdditionalInformation(sitesSme, configDocProcess, companyUserType, company);
		}
		return sitesSmeList;
	}

	/**
	 * Populates additional Information for sitesSme
	 * 
	 * @param sitesSme
	 * @return sitesSme
	 * @throws Exception
	 */
	public SitesSme populateAdditionalInformation(SitesSme sitesSme, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType, Company company) throws Exception {
		// check to ensure all values present for functions
		if (configDocProcess != null && companyUserType != null) {
			prepareNewDoc(sitesSme, configDocProcess, companyUserType);
		} else {
			// locates docs assigned if ID is not null
			if (sitesSme.getId() != null) {
				sitesSme.setDocs(DocService.instance().searchByTargetKeyAndClass(sitesSme.getClass().getName(), sitesSme.getId()));
			}
		}

		// populates the sites sme qualifications
		if (sitesSme.getId() != null) {
			// populates qualification list assigned to sme
			sitesSme.setSmeQualificationsList(smeQualificationsService.findBySme(sitesSme));
			//sitesSme.setSmeQualificationsList( smeQualificationsService.locateCountOfCompanyLearnersAgainstQualificationList( smeQualificationsService.findBySme(sitesSme), company));
		} else {
			sitesSme.setSmeQualificationsList(new ArrayList<>());
		}

		if (sitesSme.getSmeQualificationsList().size() > 0) {
			for (SmeQualifications smeQualifications : sitesSme.getSmeQualificationsList()) {
				if (smeQualifications.getStatus() == ApprovalEnum.Rejected) {
					populateRejectReasons(sitesSme, smeQualifications);
				}
			}
		}

		return sitesSme;
	}
	
	public SitesSme populateAdditionalInformationNoQuals(SitesSme sitesSme, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType, Company company) throws Exception {
		// check to ensure all values present for functions
		if (configDocProcess != null && companyUserType != null) {
			prepareNewDoc(sitesSme, configDocProcess, companyUserType);
		} else {
			// locates docs assigned if ID is not null
			if (sitesSme.getId() != null) {
				sitesSme.setDocs(DocService.instance().searchByTargetKeyAndClass(sitesSme.getClass().getName(), sitesSme.getId()));
			}
		}

		return sitesSme;
	}

	private void populateRejectReasons(SitesSme sitesSme, SmeQualifications smeQualifications) {
		RejectReasonsService rs = new RejectReasonsService();
		List<RejectReasons> rejectReason = new ArrayList<>();
		try {
			rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(
					smeQualifications.getSmeQualificationsParent().getId(), SitesSme.class.getName(),
					ConfigDocProcessEnum.SITE_SME_REGISTRATION);
			if (rejectReason.size() <= 0) {
				rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(
						smeQualifications.getSmeQualificationsParent().getId(), SitesSme.class.getName(),
						ConfigDocProcessEnum.SITE_SME_UPDATE);
			}
			if (rejectReason.size() <= 0) {
				rejectReason = rs.locateReasonsSelectedByTargetKeyClassAndProcess(
						smeQualifications.getSmeQualificationsParent().getId(), SitesSme.class.getName(),
						ConfigDocProcessEnum.SITE_SME_DELETE);
			}
			if (rejectReason.size() > 0) {
				smeQualifications.setReason(convertToString(rejectReason));
			}
		} catch (Exception e) {

		}
	}

	public SitesSme populateAdditionalMentorInformation(SitesSme sitesSme, ConfigDocProcessEnum configDocProcess,
			CompanyUserTypeEnum companyUserType, Company company) throws Exception {
		// check to ensure all values present for functions
		if (configDocProcess != null && companyUserType != null) {
			prepareNewDoc(sitesSme, configDocProcess, companyUserType);
		} else {
			// locates docs assigned if ID is not null
			if (sitesSme.getId() != null) {
				sitesSme.setDocs(DocService.instance().searchByTargetKeyAndClass(sitesSme.getClass().getName(),
						sitesSme.getId(), configDocProcess));
				// sitesSme.setDocs(DocService.instance().searchByTargetKeyAndClass(sitesSme.getClass().getName(),
				// sitesSme.getId()));
			}
		}

		// populates the sites sme qualifications
		if (sitesSme.getId() != null) {
			// populates qualification list assigned to sme
			sitesSme.setSmeQualificationsList(
					smeQualificationsService.locateCountOfCompanyLearnersAgainstQualificationList(
							smeQualificationsService.findBySme(sitesSme), company));
		} else {
			sitesSme.setSmeQualificationsList(new ArrayList<>());
		}
		return sitesSme;
	}

	public SitesSme populateAdditionalInformation1(SitesSme sme) throws Exception {
		if (sme.getId() != null) {
			sme.setSmeQualificationsList(smeQualificationsService.locateCountOfCompanyLearnersAgainstQualificationList(
					smeQualificationsService.findBySme(sme), sme.getCompany()));
		} else {
			sme.setSmeQualificationsList(new ArrayList<>());
		}
		return sme;
	}

	public SitesSme prepareNewDoc(SitesSme sitesSme, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		if (sitesSme.getId() != null) {
			// if created WorkPlaceApprovalSites
			sitesSme.setDocs(
					DocService.instance().searchByTargetKeyAndClass(sitesSme.getClass().getName(), sitesSme.getId()));
			List<ConfigDoc> l = configDocService.findByProcessNotUploaded(sitesSme.getClass().getName(),
					sitesSme.getId(), configDocProcess, companyUserType);
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					sitesSme.getDocs().add(new Doc(a));
				});
			}
		} else {
			// if new instance of WorkPlaceApprovalSites
			List<ConfigDoc> l = configDocService.findByProcess(configDocProcess, companyUserType);
			sitesSme.setDocs(new ArrayList<>());
			if (l != null && l.size() > 0) {
				l.forEach(a -> {
					sitesSme.getDocs().add(new Doc(a));
				});
			}
		}
		return sitesSme;
	}

	private void SitesSmeValidation(SitesSme entity, boolean editCheck, boolean uploadCheck) throws Exception {
		String errors = "";
		if (entity.getSmeQualificationsList().size() == 0) {
			errors += "At Least One Qualification Against The Mentor";
		} else {
			for (SmeQualifications smeQualification : entity.getSmeQualificationsList()) {
				// Document check for qualifications
				if (uploadCheck) {
					for (Doc qualificationDoc : smeQualification.getDocs()) {
						if (qualificationDoc.getId() == null && qualificationDoc.getData() == null) {
							if (!errors.equals("")) {
								errors += ", Document For Qualification: "
										+ smeQualification.getQualification().getDescription();
							} else {
								errors += "Document For Qualification: "
										+ smeQualification.getQualification().getDescription();
							}
						}
					}
				}
			}
		}

		// Document Check against SME
		if (uploadCheck) {
			for (Doc smeDoc : entity.getDocs()) {
				if (smeDoc.getId() == null && smeDoc.getData() == null) {
					if (!errors.equals("")) {
						errors += ", Document For Mentor: " + smeDoc.getConfigDoc().getName();
					} else {
						errors += "Document For Mentor: " + smeDoc.getConfigDoc().getName();
					}
				}
			}
		}

		// Edit Check
		if (editCheck) {
			// first name of sme check
			if (entity.getFirstName() == null || entity.getFirstName().isEmpty()) {
				if (!errors.equals("")) {
					errors += ", First Name For Mentor";
				} else {
					errors += "First Name For Mentor";
				}
			}
			// last name of sme check
			if (entity.getLastName() == null || entity.getLastName().isEmpty()) {
				if (!errors.equals("")) {
					errors += ", Last Name For Mentor";
				} else {
					errors += "Last Name For Mentor";
				}
			}
		}
		if (!errors.equals("")) {
			throw new ValidationException("Provide The Following Information Before Proceeding: " + errors);
		}
	}

	/**
	 * @param entity
	 * @param user
	 * @param editCheck
	 * @param uploadCheck
	 * @throws Exception
	 */
	public void updateUploadInformation(SitesSme entity, Users user, boolean editCheck, boolean uploadCheck)
			throws Exception {
		if (editCheck) {
			dao.update(entity);
		}
		if (uploadCheck) {
			// upload doc for sme
			for (Doc smeDocs : entity.getDocs()) {
				if (smeDocs.getId() == null && smeDocs.getData() != null) {
					smeDocs.setTargetClass(entity.getClass().getName());
					smeDocs.setTargetKey(entity.getId());
					DocService.instance().save(smeDocs, smeDocs.getData(), smeDocs.getOriginalFname(), user);
				}
			}
			// upload docs for Qualifications
			for (SmeQualifications smeQualifications : entity.getSmeQualificationsList()) {
				for (Doc qualificationDocs : smeQualifications.getDocs()) {
					if (qualificationDocs.getId() == null && qualificationDocs.getData() != null) {
						qualificationDocs.setTargetClass(qualificationDocs.getClass().getName());
						qualificationDocs.setTargetKey(qualificationDocs.getId());
						DocService.instance().save(qualificationDocs, qualificationDocs.getData(),
								qualificationDocs.getOriginalFname(), user);
					}
				}
			}
		}
	}

	public void sendAcknoledgementEmail(Users u, SitesSme entity, List<SmeQualifications> smeQualifications) {
		Sites site = entity.getSites();
		String sitename = "";
		String idorpassport = "";

		if (entity.getPassportNumber() != null) {
			idorpassport = entity.getPassportNumber();
		} else {
			idorpassport = entity.getIdentityNumber();
		}

		if (site != null) {
			sitename = site.getCompanyName();
		} else {
			sitename = entity.getCompany().getCompanyName();
		}

		String qualifications = convertSmeQualificationsToHTML(smeQualifications);

		String welcome = "<p>Dear #FirstName# #Surname#,</p>"
				+ "<p>ACKNOWLEDGEMENT OF MENTOR APPLICATION FOR #COMPANYNAME# (#ENTITYID#): #SITENAME#.</p>"
				+ "<p>The merSETA acknowledges receipt of the application for a Mentor - #MentorName# #MentorSurname# (#IDPassportNumber#) against the following qualification(s): </p>"
				+ qualifications
				+ "<p>Kindly be advised that it may take up to five (5) working days to process the application.</p>"
				+ "<p>Yours sincerely,</p>" + "<p>merSETA Client Services</p>" + "<br/>";

		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", entity.getCompany().getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", entity.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#SITENAME#", sitename);

		welcome = welcome.replaceAll("#MentorName#", entity.getFirstName());
		welcome = welcome.replaceAll("#MentorSurname#", entity.getLastName());
		welcome = welcome.replaceAll("#IDPassportNumber#", idorpassport);
		// welcome =
		// welcome.replaceAll("#SubmissionDate#",GenericUtility.sdf7.format(coursewaredistibution.getCreateDate()));

		GenericUtility.sendMail(u.getEmail(), "merSETA ACKNOWLEDGEMENT OF MENTOR APPLICATION ", welcome, null);
	}

	public void sendApprovateEmail(Users u, SitesSme entity, Tasks tasks, List<SmeQualifications> smeQualifications)
			throws Exception {
		Sites site = entity.getSites();
		String sitename = "";
		String idorpassport = "";
		if (entity.getPassportNumber() != null) {
			idorpassport = entity.getPassportNumber();
		} else {
			idorpassport = entity.getIdentityNumber();
		}
		if (site != null) {
			sitename = site.getCompanyName();
		} else {
			sitename = entity.getCompany().getCompanyName();
		}

		RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());

		Address address = u.getPostalAddress();
		if (address != null && address.getId() != null) {
			address = u.getPostalAddress();
		} else {
			address = u.getResidentialAddress();
		}

		if (entity.getCompany() != null && entity.getCompany().getPostalAddress() != null) {
			entity.getCompany().setPostalAddress(
					AddressService.instance().findByKey(entity.getCompany().getPostalAddress().getId()));
		} else {
			entity.getCompany().setPostalAddress(
					AddressService.instance().findByKey(entity.getCompany().getResidentialAddress().getId()));
		}

		List<SitesSme> smeList = allSitesSmeByCompanyAndStatus(entity.getCompany(), ApprovalEnum.Approved, true);

		List<SmeQualifications> list = smeQualificationsService.findBySme(entity, ApprovalEnum.Approved);
		String qualifications = convertSmeQualificationsToHTML(list);

		for (SitesSme sitesSme : smeList) {
			if (sitesSme != null && sitesSme.getId() != null) {
				sitesSme.setReason(buildString(sitesSme.getSmeQualificationsList()));
			}
		}

		String welcome = "<p>Dear #FirstName# #Surname#,</p>"
				+ "<p>#COMPANYNAME# (#ENTITYID#): #SITENAME# MENTOR APPLICATION </p>"
				+ "<p>The merSETA hereby advises that the Mentor application for #MentorName# #MentorSurname# (#IDPassportNumber#) has been approved for the following qualification(s): </p>"
				+ qualifications
				+ "<p>Please do not hesitate to contact the merSETA #RegionalOfficeName# Office for further assistance</p>"
				+ "<p>Yours sincerely,</p>" + "<p>merSETA Client Services</p>" + "<br/>";

		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", entity.getCompany().getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", entity.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#SITENAME#", sitename);

		welcome = welcome.replaceAll("#MentorName#", entity.getFirstName());
		welcome = welcome.replaceAll("#MentorSurname#", entity.getLastName());
		welcome = welcome.replaceAll("#IDPassportNumber#", idorpassport);
		welcome = welcome.replaceAll("#RegionalOfficeName#", r.getDescription());

		GenericUtility.sendMail(u.getEmail(), "merSETA APPROVAL OF MENTOR APPLICATION ", welcome, null);
	}

	public void sendApprovateEmail1(Users u, SitesSme entity) throws Exception {

		Sites site = entity.getSites();
		String sitename = "";
		String idorpassport = "";
		if (entity.getPassportNumber() != null) {
			idorpassport = entity.getPassportNumber();
		} else {
			idorpassport = entity.getIdentityNumber();
		}
		if (site != null) {
			sitename = site.getCompanyName();
		} else {
			sitename = entity.getCompany().getCompanyName();
		}
		RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());
		Address postaladdress = entity.getCompany().getPostalAddress();
		if (postaladdress == null) {
			postaladdress = entity.getCompany().getResidentialAddress();
		}
		String subject = entity.getCompany().getCompanyName() + " (" + entity.getCompany().getLevyNumber() + ") "
				+ sitename + " MENTOR APPLICATION";
		String qualifications = convertSmeQualificationsToHTML(entity.getSmeQualificationsList());
		String welcome = "<p>Dear #FirstName# #Surname#,</p>"
				+ "<p>The merSETA hereby advises that the Mentor application for #MentorName# #MentorSurname# (#IDPassportNumber#) has been approved for the following qualification(s): </p>"
				+ qualifications
				+ "<p>Please do not hesitate to contact the merSETA #RegionalOfficeName# Office for further assistance</p>"
				+ "<p>Yours sincerely,</p>" + "<p>merSETA Client Services</p>" + "<br/>";
		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", entity.getCompany().getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", entity.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#SITENAME#", sitename);
		welcome = welcome.replaceAll("#MentorName#", entity.getLastName());
		welcome = welcome.replaceAll("#MentorSurname#", entity.getLastName());
		welcome = welcome.replaceAll("#IDPassportNumber#", idorpassport);
		welcome = welcome.replaceAll("#RegionalOfficeName#", r.getDescription());
		// welcome =
		// welcome.replaceAll("#SubmissionDate#",GenericUtility.sdf7.format(coursewaredistibution.getCreateDate()));
		GenericUtility.sendMail(u.getEmail(), subject, welcome, null);
	}

	public void sendRejectEmail(Users u, SitesSme entity, List<RejectReasons> rejectReasons,
			List<SmeQualifications> smeQualifications) throws Exception {
		Sites site = entity.getSites();
		String sitename = "";
		String idorpassport = "";
		if (entity.getPassportNumber() != null) {
			idorpassport = entity.getPassportNumber();
		} else {
			idorpassport = entity.getIdentityNumber();
		}
		if (site != null) {
			sitename = site.getCompanyName();
		} else {
			sitename = entity.getCompany().getCompanyName();
		}
		String qualifications = convertSmeQualificationsToHTML(smeQualifications);
		RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());

		String rejectReason = convertToHTML(rejectReasons);

		String subject = entity.getCompany().getCompanyName() + " (" + entity.getCompany().getLevyNumber() + ") "
				+ sitename + " MENTOR APPLICATION";

		String welcome = "<p>Dear #FirstName# #Surname#,</p>"
				+ "<p>The merSETA hereby advises that the Mentor application for #MentorName# #MentorSurname# (#IDPassportNumber#) has not been approved against the qualification applied for - "
				+ qualifications + " for the following reason(s):</p>" + "<p>" + rejectReason + "</p>"
				+ "<p>Please do not hesitate to contact the merSETA #RegionalOfficeName# Office for further assistance</p>"
				+ "<p>Yours sincerely,</p>" + "<p>merSETA Client Services</p>" + "<br/>";

		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", entity.getCompany().getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", entity.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#SITENAME#", sitename);

		welcome = welcome.replaceAll("#MentorName#", entity.getFirstName());
		welcome = welcome.replaceAll("#MentorSurname#", entity.getLastName());
		welcome = welcome.replaceAll("#IDPassportNumber#", idorpassport);
		welcome = welcome.replaceAll("#RegionalOfficeName#", r.getDescription());
		// welcome =
		// welcome.replaceAll("#SubmissionDate#",GenericUtility.sdf7.format(coursewaredistibution.getCreateDate()));

		GenericUtility.sendMail(u.getEmail(), subject, welcome, null);
		// GenericUtility.sendMailWithAttachementTempWithLog(u.getEmail(),
		// "merSETA REJECTION OF MENTOR APPLICATION ", welcome, attachmentBeans,
		// null);
	}

	public void sendRejectEmail(Users u, SitesSme entity, List<SmeQualifications> smeQualifications) throws Exception {
		Sites site = entity.getSites();
		String sitename = "";
		String idorpassport = "";
		if (entity.getPassportNumber() != null) {
			idorpassport = entity.getPassportNumber();
		} else {
			idorpassport = entity.getIdentityNumber();
		}
		if (site != null) {
			sitename = site.getCompanyName();
		} else {
			sitename = entity.getCompany().getCompanyName();
		}
		String qualifications = convertSmeQualificationsToHTML(smeQualifications);
		RegionTown rt = RegionTownService.instance().findByTown(entity.getCompany().getResidentialAddress().getTown());
		if (regionService == null) {
			regionService = new RegionService();
		}
		Region r = regionService.findByKey(rt.getRegion().getId());

		String subject = entity.getCompany().getCompanyName() + " (" + entity.getCompany().getLevyNumber() + ") "
				+ sitename + " MENTOR APPLICATION";

		String welcome = "<p>Dear #FirstName# #Surname#,</p>"
				+ "<p>The merSETA hereby advises that the Mentor application for #MentorName# #MentorSurname# (#IDPassportNumber#) has not been approved against the qualification applied for - "
				+ qualifications + " for the following reason(s):</p>" + "<p>Missing Documents.</p>"
				+ "<p>Please do not hesitate to contact the merSETA #RegionalOfficeName# Office for further assistance</p>"
				+ "<p>Yours sincerely,</p>" + "<p>merSETA Client Services</p>" + "<br/>";

		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", entity.getCompany().getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", entity.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#SITENAME#", sitename);

		welcome = welcome.replaceAll("#MentorName#", entity.getFirstName());
		welcome = welcome.replaceAll("#MentorSurname#", entity.getLastName());
		welcome = welcome.replaceAll("#IDPassportNumber#", idorpassport);
		welcome = welcome.replaceAll("#RegionalOfficeName#", r.getDescription());
		// welcome =
		// welcome.replaceAll("#SubmissionDate#",GenericUtility.sdf7.format(coursewaredistibution.getCreateDate()));

		GenericUtility.sendMail(u.getEmail(), subject, welcome, null);
		// GenericUtility.sendMailWithAttachementTempWithLog(u.getEmail(),
		// "merSETA REJECTION OF MENTOR APPLICATION ", welcome, attachmentBeans,
		// null);
	}

	public void sendDeleteAcknoledgementEmail(Users u, SitesSme entity) throws Exception {
		Sites site = entity.getSites();
		String sitename = "";
		if (site != null) {
			sitename = site.getCompanyName();
		} else {
			sitename = entity.getCompany().getCompanyName();
		}
		String idorpassport = "";
		if (entity.getPassportNumber() != null) {
			idorpassport = entity.getPassportNumber();
		} else {
			idorpassport = entity.getIdentityNumber();
		}
		String subject = "ACKNOWLEDGEMENT OF MENTOR REMOVAL FOR " + entity.getCompany().getCompanyName() + " ("
				+ entity.getCompany().getLevyNumber() + "): " + sitename;
		String qual = convertSmeQualificationsToHTML(entity.getSmeQualificationsList());
		String welcome = "<p>Dear #FirstName# #Surname#,</p>"
				+ "<p>The merSETA acknowledges receipt of the request to remove Mentor #MentorName# #MentorSurname# (#IDPassportNumber#) against the following qualification(s):</p>"
				+ "<p>" + qual + "</p>"
				+ "<p>Kindly be advised that it may take up to five (5) working days to process the application.</p>"
				+ "<p>Yours sincerely,</p>" + "<p>merSETA Client Services</p>" + "<br/>";

		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", entity.getCompany().getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", entity.getCompany().getLevyNumber());

		welcome = welcome.replaceAll("#MentorName#", entity.getFirstName());
		welcome = welcome.replaceAll("#MentorSurname#", entity.getLastName());
		welcome = welcome.replaceAll("#IDPassportNumber#", idorpassport);

		GenericUtility.sendMail(u.getEmail(), subject, welcome, null);
	}

	public void sendDeleteApprovateEmail(Users u, SitesSme entity, Tasks tasks) throws Exception {
		Sites site = entity.getSites();
		String sitename = "";
		if (site != null) {
			sitename = site.getCompanyName();
		} else {
			sitename = entity.getCompany().getCompanyName();
		}
		String idorpassport = "";
		if (entity.getPassportNumber() != null) {
			idorpassport = entity.getPassportNumber();
		} else {
			idorpassport = entity.getIdentityNumber();
		}

		String subject = entity.getCompany().getCompanyName() + " (" + entity.getCompany().getLevyNumber() + "): "
				+ sitename + " MENTOR REMOVAL";
		String qual = convertSmeQualificationsToHTML(entity.getSmeQualificationsList());
		String welcome = "<p>Dear #FirstName# #Surname#,</p>"
				+ "<p>The merSETA hereby advises that the request to remove #MentorName# #MentorSurname# (#IDPassportNumber#) for following qualification(s) has been granted:</p>"
				+ "<p>" + qual + "</p>"
				+ "<p>Please do not hesitate to contact the merSETA Gauteng South Region Office for further assistance.</p>"
				+ "<p>Yours sincerely,</p>" + "<p>merSETA Client Services</p>" + "<br/>";

		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", entity.getCompany().getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", entity.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#SITENAME#", sitename);

		welcome = welcome.replaceAll("#MentorName#", entity.getFirstName());
		welcome = welcome.replaceAll("#MentorSurname#", entity.getLastName());
		welcome = welcome.replaceAll("#IDPassportNumber#", idorpassport);

		GenericUtility.sendMail(u.getEmail(), subject, welcome, null);
	}

	public void sendDeleteRejectEmail(Users u, SitesSme entity, Tasks tasks, List<RejectReasons> rejectReasons)
			throws Exception {
		Sites site = entity.getSites();
		String sitename = "";
		if (site != null) {
			sitename = site.getCompanyName();
		} else {
			sitename = entity.getCompany().getCompanyName();
		}
		String idorpassport = "";
		if (entity.getPassportNumber() != null) {
			idorpassport = entity.getPassportNumber();
		} else {
			idorpassport = entity.getIdentityNumber();
		}
		String rejectReason = convertToHTML(rejectReasons);
		String subject = entity.getCompany().getCompanyName() + " (" + entity.getCompany().getLevyNumber() + "): "
				+ sitename + " MENTOR REMOVAL";
		String qual = convertSmeQualificationsToHTML(entity.getSmeQualificationsList());
		String welcome = "<p>Dear #FirstName# #Surname#,</p>"
				+ "<p>The merSETA hereby advises that the request to remove Mentor #MentorName# #MentorSurname# (#IDPassportNumber#) for following qualification(s) has not been granted:</p>"
				+ "<p>" + qual + "</p>" + "<p>for the following reason(s):</p>" + "<p>" + rejectReason + "</p>"
				+ "<p>Please do not hesitate to contact the merSETA Gauteng South Region Office for further assistance.</p>"
				+ "<p>Yours sincerely,</p>" + "<p>merSETA Client Services</p>" + "<br/>";

		welcome = welcome.replaceAll("#FirstName#", u.getFirstName());
		welcome = welcome.replaceAll("#Surname#", u.getLastName());
		welcome = welcome.replaceAll("#COMPANYNAME#", entity.getCompany().getCompanyName());
		welcome = welcome.replaceAll("#ENTITYID#", entity.getCompany().getLevyNumber());
		welcome = welcome.replaceAll("#SITENAME#", sitename);

		welcome = welcome.replaceAll("#MentorName#", entity.getFirstName());
		welcome = welcome.replaceAll("#MentorSurname#", entity.getLastName());
		welcome = welcome.replaceAll("#IDPassportNumber#", idorpassport);

		GenericUtility.sendMail(u.getEmail(), subject, welcome, null);
	}

	public String convertToHTML(List<RejectReasons> rejectReasons) {

		String sb = "<ul>";
		for (RejectReasons r : rejectReasons) {
			sb += "<li>" + r.getDescription() + "</li>";
		}
		sb += "</ul>";
		return sb;
	}

	public String convertSmeQualificationsToHTML(List<SmeQualifications> smeQualificationsList) {

		String sb = "<ul>";
		for (SmeQualifications smeq : smeQualificationsList) {
			if (smeq.getQualification() != null && smeq.getQualification().getCode() != null) {
				sb += "<li>" + smeq.getQualification().getCode() + "  " + smeq.getQualification().getDescription()
						+ "</li>";
			} else {
				sb += "<li>" + smeq.getQualification().getDescription() + "</li>";
			}

		}
		sb += "</ul>";
		return sb;
	}

	public String buildString(List<SmeQualifications> smeQualificationsList) {
		String reason = "";
		int size = 0;
		for (SmeQualifications child : smeQualificationsList) {
			size += 1;
			if (child.getQualification().getCode() != null) {
				reason += "(" + child.getQualification().getCode() + ")";
			}
			reason += child.getQualification().getDescription();
			if (size == smeQualificationsList.size()) {
				reason += ".";
			} else {
				reason += ", ";
			}
		}
		return reason;
	}

	public String convertToString(List<RejectReasons> rejectReasons) {

		String reason = "";
		int size = 0;
		for (RejectReasons r : rejectReasons) {
			size += 1;
			reason += r.getDescription();

			if (size == rejectReasons.size()) {
				reason += ".";
			} else {
				reason += ", ";
			}
		}

		return reason;
	}

	/**
	 * Find object by targetClass and targetKey.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SitesSme}
	 * @throws Exception
	 *             global exception
	 * @see SitesSme
	 */
	@SuppressWarnings("unchecked")
	public List<SitesSme> findByTargetClassTargetKey(String targetClass, Long targetKey) throws Exception {
		return dao.findByTargetClassTargetKey(targetClass, targetKey);
	}

	public List<SitesSme> findApprovedSitesSme(Sites site, Company company) {
		return dao.findApprovedSitesSme(site, company);
	}
}
