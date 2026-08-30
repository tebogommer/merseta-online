package haj.com.service;

import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.SortOrder;

import haj.com.dao.SitesDAO;
import haj.com.entity.Address;
import haj.com.entity.AddressHistory;
import haj.com.entity.Company;
import haj.com.entity.Sites;
import haj.com.entity.SitesHistory;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.framework.AbstractService;
import haj.com.ui.CompanyInfoUI;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class SitesService.
 */
/**
 * @author Christoph Sibiya
 *
 */
public class SitesService extends AbstractService {
	/** The dao. */
	private SitesDAO dao = new SitesDAO();

	private static SitesService sitesService = null;

	/**
	 * Instance.
	 *
	 * @return the SitesService
	 */
	public static synchronized SitesService instance() {
		if (sitesService == null) {
			sitesService = new SitesService();
		}
		return sitesService;
	}

	/**
	 * Get all Sites.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Sites}
	 * @throws Exception
	 *             the exception
	 * @see Sites
	 */
	public List<Sites> allSites() throws Exception {
		return dao.allSites();
	}

	/**
	 * Create or update Sites.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Sites
	 */
	public void create(Sites entity) throws Exception {
		if (entity.getId() == null) {
			// AddressService.instance().saveAddresses(entity.getRegisteredAddress(), null);
			AddressService.instance().saveAddresses(entity.getRegisteredAddress(), null);
			this.dao.create(entity);
			// entity.getRegisteredAddress().setSites(entity);
		} else {
			AddressService.instance().saveAddresses(entity.getRegisteredAddress(), null);
			this.dao.update(entity);
		}

	}

	/**
	 * Create or update Sites.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Sites
	 */
	public void createSiteAndCreateTask(Sites entity, Users users) throws Exception {
		AddressService.instance().saveAddresses(entity.getRegisteredAddress(), null);
		this.dao.create(entity);
		// Creating Change Reason
		ChangeReasonService.instance().createChangeReason(entity.getId(), entity.getClass().getName(), CompanyInfoUI.changeReason);
		// Creating workflow
		String desc = "Company Site has been added, please approve the information provided";
		// String description, Users createUser, Long targetKey, String targetClass,
		// boolean sendMail, HostingCompany hostingCompany, ConfigDocProcessEnum
		// processEnum, MailDef mailDef, String extraInfo) throws Exception {
		TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.NEW_SITES, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);

	}

	/**
	 * Create or update Sites.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Sites
	 */
	public void updateSiteAndCreateTask(Sites entity, Users users) throws Exception {

		AddressService.instance().saveAddresses(entity.getRegisteredAddress(), null);
		this.dao.update(entity);
		// Creating Change Reason
		ChangeReasonService.instance().createChangeReason(entity.getId(), entity.getClass().getName(), CompanyInfoUI.changeReason);
		// Creating workflow
		String desc = "Company Site has been updated, please approve the information provided";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.SITES_CHANGE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);

	}

	/**
	 * Update Sites.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Sites
	 */
	public void update(Sites entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete Sites.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Sites
	 */
	public void delete(Sites entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Sites}
	 * @throws Exception
	 *             the exception
	 * @see Sites
	 */
	public Sites findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find Sites by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.Sites}
	 * @throws Exception
	 *             the exception
	 * @see Sites
	 */
	public List<Sites> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load Sites.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.Sites}
	 * @throws Exception
	 *             the exception
	 */
	public List<Sites> allSites(int first, int pageSize) throws Exception {
		return dao.allSites(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of Sites for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Sites
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(Sites.class);
	}

	/**
	 * Lazy load Sites with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            Sites class
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
	 * @return a list of {@link haj.com.entity.Sites} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Sites> allSites(Class<Sites> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<Sites>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of Sites for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            Sites class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the Sites entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<Sites> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
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
	public List<Sites> findByCompany(Company company) throws Exception {
		return dao.findByCompany(company);
	}

	public Long findSumByCompany(Company company) throws Exception {
		return dao.findSumByCompany(company);
	}

	public String generateSiteNumber(Sites sites) {
		return sites.getCompanyName().toUpperCase().replaceAll(" ", "").substring(0, 3);
	}

	public List<Sites> findByNameAndCompany(String name, Company company) throws Exception {
		return dao.findByNameAndCompany(name, company);
	}

	/**
	 * Approve new site
	 * 
	 * @param site
	 *            the Sites
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void approveNewSiteTask(Sites site, Tasks task) throws Exception {
		site.setSiteStatus(CompanyStatusEnum.Approved);
		populateSitesSiteNumber(site);
		update(site);
		TasksService.instance().completeTask(task);
		// Sending Email to task creator
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(site.getFormUser().getId()).getEmail(), "New Site Approval", "Your new site (" + site.getCompanyName() + ") has been approved on the merSETA NSDMS system.", null);
	}

	/**
	 * Reject new site
	 * 
	 * @param site
	 *            the Sites
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void rejectNewSiteTask(Sites site, Tasks task) throws Exception {
		site.setSiteStatus(CompanyStatusEnum.Rejected);
		update(site);
		TasksService.instance().completeTask(task);
		// Sending Email to task creator
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(site.getFormUser().getId()).getEmail(), "New Site Rejection", "Your new site (" + site.getCompanyName() + ") has been rejected on the merSETA NSDMS system.", null);
	}

	/**
	 * Approve site update
	 * 
	 * @param site
	 *            the Sites
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void approveSiteUpdateTask(Sites site, Tasks task) throws Exception {
		site.setSiteStatus(CompanyStatusEnum.Approved);
		update(site);
		TasksService.instance().completeTask(task);
		// Sending Email to task creator
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(site.getFormUser().getId()).getEmail(), "Site Changes Approval", "Your updated site (" + site.getCompanyName() + ") has been approved on the merSETA NSDMS system.", null);
	}

	/**
	 * Reject site update
	 * 
	 * @param site
	 *            the Sites
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void rejectSiteUpdateTask(Sites site, Tasks task, SitesHistory sitesHistory) throws Exception {
		// Reverting the changes
		Address currentAddress = AddressService.instance().findByKey(site.getRegisteredAddress().getId());
		AddressHistory addressHistory = AddressHistoryService.instance().findByKey(sitesHistory.getRegisteredAddress().getId());

		Long tempCurrAddrID = currentAddress.getId();
		BeanUtils.copyProperties(currentAddress, addressHistory);
		currentAddress.setId(tempCurrAddrID);
		AddressService.instance().update(currentAddress);

		Long tempID = site.getId();
		BeanUtils.copyProperties(site, sitesHistory);
		site.setId(tempID);

		site.setRegisteredAddress(currentAddress);
		update(site);
		TasksService.instance().completeTask(task);

		// Sending Email to task creator
		UsersService usersService = new UsersService();
		GenericUtility.sendMail(usersService.findByKey(site.getFormUser().getId()).getEmail(), "Site Changes Rejection", "Your updated site (" + site.getCompanyName() + ") has been rejected on the merSETA NSDMS system.", null);
	}

	/**
	 * Create delete siste task.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see Sites
	 */
	public void createDeleteSisteTask(Sites entity, Users users) throws Exception {

		// Create Site History
		SitesHistoryService.instance().createHistory(entity.getId());

		entity.setSiteStatus(CompanyStatusEnum.PendingChangeApproval);
		this.dao.update(entity);

		// Creating Change Reason
		ChangeReasonService.instance().createChangeReason(entity.getId(), entity.getClass().getName(), CompanyInfoUI.changeReason);
		// Creating workflow
		String desc = "An attempt to delete site has been made, please approve the information provided";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, users, entity.getId(), entity.getClass().getName(), true, ConfigDocProcessEnum.DELETE_SISTE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);

	}

	/**
	 * Reject site delete
	 * 
	 * @param site
	 *            the Sites
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void rejectSiteDeleteTask(Sites site, Tasks task, SitesHistory sitesHistory) throws Exception {
		site.setSiteStatus(sitesHistory.getSiteStatus());
		update(site);
		TasksService.instance().completeTask(task);

		// Sending email to task creator
		GenericUtility.sendMail(task.getCreateUser().getEmail(), "Delete Site Rejection", "The attempt to delete site (" + site.getCompanyName() + "  ) has been rejected on the merSETA NSDMS system.", null);
	}

	/**
	 * Approve site delete
	 * 
	 * @param site
	 *            the Sites
	 * @param task
	 *            The Tasks
	 * @throws Exception
	 */
	public void approveSiteDeleteTask(Sites site, Tasks task, SitesHistory sitesHistory) throws Exception {
		// Delete Site History
		SitesHistoryService.instance().removeSiteHistory(site);
		// Deleting Site
		delete(site);
		TasksService.instance().completeTask(task);
		// Sending email to task creator
		if (task.getCreateUser() != null && task.getCreateUser().getId() != null && task.getCreateUser().getEmail() != null ) {
			GenericUtility.sendMail(task.getCreateUser().getEmail(), "Delete Site Approval", "The attempt to delete site (" + site.getCompanyName() + "  ) has been approve on the merSETA NSDMS system.", null);
		}
	}

	public Integer countAllSitesBySiteNumberAssigned() throws Exception {
		return dao.countAllSitesBySiteNumberAssigned();
	}
	
	public List<Sites> allSitesWhereSiteNumberNotAssigned() throws Exception {
		return dao.allSitesWhereSiteNumberNotAssigned();
	}
	
	public Integer countAllSitesWhereSiteNumberNotAssigned() throws Exception {
		return dao.countAllSitesWhereSiteNumberNotAssigned();
	}
	
	public void updateSiteNumbersWhereNoSiteNumberAssigned() {
		try {
			List<Sites> sitesList = allSitesWhereSiteNumberNotAssigned();
			for (Sites sites : sitesList) {
				populateSitesSiteNumber(sites);
				update(sites);
			}
		} catch (Exception e) {
			logger.fatal(e);
			GenericUtility.mailError("Site's Site Number Assignment Error", "Error: haj.com.service.SitesService.updateSiteNumbersWhereNoSiteNumberAssigned() <br/> Exception: " + e.getMessage());
		}
	}
	
	public void populateSitesSiteNumber(Sites site) throws Exception{		
		// syntax: SS10000000
		Integer count = countAllSitesBySiteNumberAssigned();
		Integer baseCount = 10000001;
		if (count != 0) {
			baseCount = baseCount + count;
		}
		String siteNumber = "SS" + baseCount;
		site.setCompanySiteNumber(siteNumber);
	}
	
	public void resolveSiteAddresses(Sites site) throws Exception{
		if (site != null) {
			if (site.getRegisteredAddress() != null && site.getRegisteredAddress().getId() != null) {
				site.setRegisteredAddress(AddressService.instance().findByKey(site.getRegisteredAddress().getId()));
			}
		}
	}

	
	public List<Sites> findByCompanyTraining(Company company) throws Exception {
		return dao.findByCompanyTraining(company);
	}
	
}