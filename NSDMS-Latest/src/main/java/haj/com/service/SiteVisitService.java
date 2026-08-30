package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.SiteVisitDAO;
import haj.com.entity.Company;
import haj.com.entity.ConfigDoc;
import haj.com.entity.DgVerification;
import haj.com.entity.Doc;
import haj.com.entity.SiteVisit;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyUserTypeEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.lookup.RegionTown;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RegionTownService;
import haj.com.utils.GenericUtility;

public class SiteVisitService extends AbstractService {
	/** The dao. */
	private SiteVisitDAO dao = new SiteVisitDAO();
	private ConfigDocService configDocService = new ConfigDocService();
	private MandatoryGrantDetailService mandatoryGrantDetailService = null;

	/**
	 * Get all SiteVisit
	 * 
	 * @author TechFinium
	 * @see SiteVisit
	 * @return a list of {@link haj.com.entity.SiteVisit}
	 * @throws Exception
	 *             the exception
	 */
	public List<SiteVisit> allSiteVisit() throws Exception {
		return dao.allSiteVisit();
	}

	/**
	 * Create or update SiteVisit.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SiteVisit
	 */
	public void create(SiteVisit entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void create(SiteVisit entity, Users users, boolean createWorkflow) throws Exception {
		entity.setApprovalEnum(ApprovalEnum.PendingApproval);
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
			// TasksService.instance().createTaskUser(signoffs, entity.getClass().getName(), entity.getId(), desc, users, true, true, null, ConfigDocProcessEnum.SITE_VISIT, false);
			TasksService.instance().findFirstInProcessAndCreateTask("", users, entity.getId(), entity.getClass().getName(), true,  ConfigDocProcessEnum.SITE_VISIT, null, signoffs);
		}

		for (Doc doc : entity.getDocs()) {
			doc.setTargetKey(entity.getId());
			doc.setTargetClass(SiteVisit.class.getName());
			DocService.instance().save(doc, doc.getData(), doc.getOriginalFname(), users);
		}
	}
	
	/**
	 * Added 2018/06/20 - Sean and Jono
	 * 
	 * @param entity
	 * @param users
	 * @param createWorkflow
	 * @param task
	 * @throws Exception
	 */
	public void createOnceRejected(SiteVisit entity, Users users, boolean createWorkflow, Tasks task) throws Exception {

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
			// TasksService.instance().createTaskUser(signoffs, entity.getClass().getName(), entity.getId(), desc, users, true, true, null, ConfigDocProcessEnum.SITE_VISIT, false);
			TasksService.instance().findFirstInProcessAndCreateTask("", users, entity.getId(), entity.getClass().getName(), true,  ConfigDocProcessEnum.SITE_VISIT, null, signoffs);
		}
		
		TasksService.instance().completeTask(task);
	}

	public SiteVisit prepareNewDocs(SiteVisit siteVisit, ConfigDocProcessEnum configDocProcess, CompanyUserTypeEnum companyUserType) throws Exception {
		siteVisit.setDocs(DocService.instance().searchByTargetKeyAndClass(siteVisit.getClass().getName(), siteVisit.getId()));
		List<ConfigDoc> l = configDocService.findByProcessNotUploaded(siteVisit.getClass().getName(), siteVisit.getId(), configDocProcess, companyUserType);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				siteVisit.getDocs().add(new Doc(a));
			});
		}
		return siteVisit;
	}

	public void prepareNewDocs(SiteVisit siteVisit) throws Exception {
		siteVisit.setDocs(new ArrayList<Doc>());
		List<ConfigDoc> l = configDocService.findByProcess(ConfigDocProcessEnum.SITE_VISIT, CompanyUserTypeEnum.Company);
		if (l != null && l.size() > 0) {
			l.forEach(a -> {
				siteVisit.getDocs().add(new Doc(a));
			});
		}
	}

	public void completeToFirst(SiteVisit siteVisit, Users formUser, Tasks task) throws Exception {
		update(siteVisit);
		String desc = "Site Visit has been loaded onto the NSDMS. Please Approve.";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, formUser, task.getTargetKey(), task.getTargetClass(), true, ConfigDocProcessEnum.SITE_VISIT, null, null);
		TasksService.instance().completeTask(task);
	}

	public void completeTask(SiteVisit siteVisit, Users user, Tasks task) throws Exception {
		update(siteVisit);
		TasksService.instance().findNextInProcessAndCreateTask("Site Visit was checked and submitted by the admin", user, siteVisit.getId(), siteVisit.getClass().getName(), true, task, MailDef.instance(MailEnum.SdfForApproval, MailTagsEnum.FirstName, user.getFirstName(), MailTagsEnum.LastName, user.getLastName()), null);
	}

	public void approveTask(SiteVisit siteVisit, Users user, Tasks task) throws Exception {
		siteVisit.setApprovalEnum(ApprovalEnum.Approved);
		siteVisit.setDateApproved(new Date());
		update(siteVisit);
		TasksService.instance().completeTask(task);
	}

	public void rejectTask(SiteVisit siteVisit, Users user, Tasks task) throws Exception {
		if (task.getFirstInProcess()) {
			List<Users> signoffs = new ArrayList<>();
			signoffs.add(siteVisit.getUsers());
			TasksService.instance().createTaskUser(signoffs, task.getTargetClass(), task.getTargetKey(), "Site Visit was rejected please login and view the reason and make the relevant changes.", user, true, true, task, ConfigDocProcessEnum.SITE_VISIT, false);
			TasksService.instance().completeTask(task);
		} else {
			TasksService.instance().findPreviousInProcessAndCreateTask("Site Visit was rejected please review", user, siteVisit.getId(), siteVisit.getClass().getName(), true, task, MailDef.instance(MailEnum.SdfForApproval), null);
		}
	}

	/**
	 * Update SiteVisit.
	 *
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SiteVisit
	 */
	public void update(SiteVisit entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SiteVisit.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SiteVisit
	 */
	public void delete(SiteVisit entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SiteVisit}
	 * @throws Exception
	 *             the exception
	 * @see SiteVisit
	 */
	public SiteVisit findByKey(long id) throws Exception {
		return prepareNewDocs(dao.findByKey(id), ConfigDocProcessEnum.SITE_VISIT, CompanyUserTypeEnum.Company);
	}

	/**
	 * Find SiteVisit by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SiteVisit}
	 * @throws Exception
	 *             the exception
	 * @see SiteVisit
	 */
	public List<SiteVisit> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load SiteVisit
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SiteVisit}
	 * @throws Exception
	 *             the exception
	 */
	public List<SiteVisit> allSiteVisit(int first, int pageSize) throws Exception {
		return dao.allSiteVisit(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SiteVisit for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the SiteVisit
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SiteVisit.class);
	}

	/**
	 * Lazy load SiteVisit with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            SiteVisit class
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
	 * @return a list of {@link haj.com.entity.SiteVisit} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SiteVisit> allSiteVisit(Class<SiteVisit> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SiteVisit>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of SiteVisit for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            SiteVisit class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SiteVisit entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SiteVisit> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	public long allSiteVisitCount(Company company, Date date) throws Exception {
		return dao.allSiteVisitCount(company, date);
	}

	public List<SiteVisit> allSiteVisit(Company company, Date date) throws Exception {
		return dao.allSiteVisit(company, date);
	}
	
	/**
	 * Locates site visits by company id
	 * order by visit Date asc
	 * @param company
	 * @return List<SiteVisit>
	 * @throws Exception
	 */
	public List<SiteVisit> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company.getId());
	}

	/**
	 * Does a 6 month check on a site visit to see if requirements have been provided before continuing 
	 * with DgVerification.
	 * 
	 * return values:
	 * 0 - Can Continue with verification 
	 * 1 - No Site Visit Found in the last 6 months
	 * 2 - Site visit found however not approved
	 * @param dgVerification
	 * @return result
	 * @throws Exception
	 */
	public Integer checkSixMonthRequirement(DgVerification dgVerification) throws Exception{
		
		if (mandatoryGrantDetailService == null) {
			mandatoryGrantDetailService = new MandatoryGrantDetailService();
		}
		// locates mandatory grant service
		long mandatoryGrantDetailCount = mandatoryGrantDetailService.findByWSPCount(dgVerification.getWsp());
		mandatoryGrantDetailService = null;
//		System.out.println(dgVerification.getWsp().getCompany().getLevyNumber());
		
		// locates site visits
		List<SiteVisit> assignedSiteList = findByCompany(dgVerification.getWsp().getCompany());
		SiteVisit lastestSiteVisit = null;
		for (SiteVisit siteVisit : assignedSiteList) {
			lastestSiteVisit = findByKey(siteVisit.getId());
			break;
		}
		assignedSiteList = null;
		
		// month check
		if (lastestSiteVisit == null) {
			return 1;
		} else {
//			System.out.println(GenericUtility.getMonthsBetweenDates(lastestSiteVisit.getVisitDate(),new Date()) + " Months between dates.");
			if (GenericUtility.getMonthsBetweenDates(lastestSiteVisit.getVisitDate(), new Date()) <= 6) {
				if (lastestSiteVisit.getApprovalEnum() != ApprovalEnum.Approved) {
					return 2;
				} else {
					return 0;
				}
			} else {
				return 1;
			}
		}
	}

	/**
	 * Locates the site visit between dates 
	 * @param sixMonthsAgoDate
	 * @param today
	 * @param company
	 * @return List<SiteVisit>
	 */
	public List<SiteVisit> locateSiteVisitBetweenDatesByVisitDate(Date fromDate, Date toDate, Company company) throws Exception{
		return dao.locateSiteVisitBetweenDatesByVisitDate(fromDate, toDate, company.getId());
	}
}
