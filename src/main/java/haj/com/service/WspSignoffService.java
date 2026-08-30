package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WspSignoffDAO;
import haj.com.entity.ExtensionRequest;
import haj.com.entity.SDFCompany;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.WspSignoff;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.RegionTownService;
import haj.com.service.lookup.SizeOfCompanyService;

// TODO: Auto-generated Javadoc
/**
 * The Class WspSignoffService.
 */
public class WspSignoffService extends AbstractService {
	/** The dao. */
	private WspSignoffDAO dao = new WspSignoffDAO();

	/**
	 * Get all WspSignoff.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.WspSignoff}
	 * @throws Exception
	 *             the exception
	 * @see WspSignoff
	 */
	public List<WspSignoff> allWspSignoff() throws Exception {
		return dao.allWspSignoff();
	}

	/**
	 * Create or update WspSignoff.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WspSignoff
	 */
	public void create(WspSignoff entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update WspSignoff.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WspSignoff
	 */
	public void update(WspSignoff entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete WspSignoff.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see WspSignoff
	 */
	public void delete(WspSignoff entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.WspSignoff}
	 * @throws Exception
	 *             the exception
	 * @see WspSignoff
	 */
	public WspSignoff findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find WspSignoff by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.WspSignoff}
	 * @throws Exception
	 *             the exception
	 * @see WspSignoff
	 */
	public List<WspSignoff> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load WspSignoff.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspSignoff}
	 * @throws Exception
	 *             the exception
	 */
	public List<WspSignoff> allWspSignoff(int first, int pageSize) throws Exception {
		return dao.allWspSignoff(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of WspSignoff for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the WspSignoff
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(WspSignoff.class);
	}

	/**
	 * Lazy load WspSignoff with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            WspSignoff class
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
	 * @return a list of {@link haj.com.entity.WspSignoff} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<WspSignoff> allWspSignoff(Class<WspSignoff> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<WspSignoff>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of WspSignoff for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            WspSignoff class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the WspSignoff entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<WspSignoff> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by wsp.
	 *
	 * @param wsp
	 *            the wsp
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<WspSignoff> findByWsp(Wsp wsp) throws Exception {
		return dao.findByWsp(wsp);
	}
	
	public List<WspSignoff> findByWspLastestFirst(Wsp wsp) throws Exception {
		return dao.findByWspLastestFirst(wsp);
	}
	
	public Date returnLastestSignOffDate(Wsp wsp) throws Exception{
		Date latestSignOffDate = null;
		List<WspSignoff> signoffs = findByWspLastestFirst(wsp);
		for (WspSignoff wspSignoff : signoffs) {
			latestSignOffDate = wspSignoff.getSignOffDate();
			if (latestSignOffDate != null) {
				break;
			}
		}
		return latestSignOffDate;
	}

	/**
	 * Find by wsp and user.
	 *
	 * @param wsp
	 *            the wsp
	 * @param user
	 *            the user
	 * @return the wsp signoff
	 * @throws Exception
	 *             the exception
	 */
	public WspSignoff findByWspAndUser(Wsp wsp, Users user) throws Exception {
		return dao.findByWspAndUser(wsp, user);
	}

	@SuppressWarnings("unchecked")
	public List<WspSignoff> findCompletedSignOffs(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from WspSignoff o left join fetch o.user left join fetch o.wsp where o.wsp.wspStatusEnum not in (:pendingSignOffId, :pendingId, :submittedAfterDeadline) and o.wsp.company is not null";
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		filters.put("submittedAfterDeadline", WspStatusEnum.Pending);
		return findCompletedSignOffs((List<WspSignoff>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int count(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspSignoff o where o.wsp.wspStatusEnum not in (:pendingSignOffId, :pendingId, :submittedAfterDeadline) and o.wsp.company is not null";
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		filters.put("submittedAfterDeadline", WspStatusEnum.Pending);
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public List<Wsp> findWSPSNotYetComplete(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from Wsp o left join fetch o.user where o.wspStatusEnum in (:pendingSignOffId, :pendingId)";
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		return (List<Wsp>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countWSPSNotYetComplete(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspSignoff o where o.wsp.wspStatusEnum not in (:pendingSignOffId, :pendingId)";
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspSignoff> findCompletedSignOffsByFinYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Integer finYear) throws Exception {
		String hql = "select o from WspSignoff o left join fetch o.user left join fetch o.wsp where o.wsp.wspStatusEnum not in (:pendingSignOffId, :pendingId, :submittedAfterDeadline) and o.wsp.company is not null and o.wsp.finYear = :finYear";
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		filters.put("submittedAfterDeadline", WspStatusEnum.Pending);
		filters.put("finYear", finYear);
		List<WspSignoff>list=(List<WspSignoff>)dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
		findCompletedSignOffs(list);
		populateExtentionRequest(list, finYear);
		return list;
	}
	
	public int countCompletedSignOffsByFinYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspSignoff o where o.wsp.wspStatusEnum not in (:pendingSignOffId, :pendingId, :submittedAfterDeadline) and o.wsp.company is not null and o.wsp.finYear = :finYear";
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		filters.put("submittedAfterDeadline", WspStatusEnum.Pending);
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspSignoff> findCompletedSignOffsByRegionUserByFinYear(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users u, Integer finYear) throws Exception {
		String hql = "select o from WspSignoff o left join fetch o.user left join fetch o.wsp where o.wsp.wspStatusEnum not in (:pendingSignOffId, :pendingId) and o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.crm.users.id = :userID or x.clo.users.id = :userID) and o.wsp.finYear = :finYear";
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		filters.put("userID", u.getId());
		filters.put("finYear", finYear);
		return findCompletedSignOffs((List<WspSignoff>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql), u);
	}

	public int countCompletedSignOffsByRegionUserByFinYear(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspSignoff o where o.wsp.wspStatusEnum not in (:pendingSignOffId, :pendingId) and o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.crm.users.id = :userID or x.clo.users.id = :userID) and o.wsp.finYear = :finYear";
		return dao.countWhere(filters, hql);
	}

	public List<WspSignoff> findCompletedSignOffs(List<WspSignoff> l) throws Exception {
		// List<WspSignoff> l = dao.findCompletedSignOffs();
		for (WspSignoff wspSignoff : l) {
			wspSignoff.getWsp().getCompany().setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(wspSignoff.getWsp().getCompany()));
			wspSignoff.getWsp().setApplicationType(getApplicationType(wspSignoff.getWsp()));
			try {
				wspSignoff.getWsp().setRegionTown(RegionTownService.instance().findByTown(wspSignoff.getWsp().getCompany().getResidentialAddress().getTown()));
			} catch (org.hibernate.NonUniqueResultException ex) {

			} catch (Exception e) {
				logger.fatal(e.getMessage(), e);
			}

		}
		return l;
	}

	@SuppressWarnings("unchecked")
	public List<WspSignoff> findCompletedSignOffs(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Users u) throws Exception {
		String hql = "select o from WspSignoff o left join fetch o.user left join fetch o.wsp where o.wsp.wspStatusEnum not in (:pendingSignOffId, :pendingId) and o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.crm.users.id = :userID or x.clo.users.id = :userID)";
		filters.put("pendingId", WspStatusEnum.Draft);
		filters.put("pendingSignOffId", WspStatusEnum.PendingSignOff);
		filters.put("userID", u.getId());
		return findCompletedSignOffs((List<WspSignoff>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql), u);
	}

	public int countRegion(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspSignoff o where o.wsp.wspStatusEnum not in (:pendingSignOffId, :pendingId) and o.wsp.company.residentialAddress.town.id in (select x.town.id from RegionTown x where x.crm.users.id = :userID or x.clo.users.id = :userID)";
		return dao.countWhere(filters, hql);
	}

	public List<WspSignoff> findCompletedSignOffs(List<WspSignoff> l, Users user) throws Exception {
		for (WspSignoff wspSignoff : l) {
			wspSignoff.getWsp().getCompany().setSizeOfCompany(SizeOfCompanyService.instance().findCompanySize(wspSignoff.getWsp().getCompany()));
			wspSignoff.getWsp().setApplicationType(getApplicationType(wspSignoff.getWsp()));
			try {
				wspSignoff.getWsp().setRegionTown(RegionTownService.instance().findByTown(wspSignoff.getWsp().getCompany().getResidentialAddress().getTown()));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return l;
	}

	public String getApplicationType(Wsp wsp) throws Exception {
		return dao.getApplicationType(wsp.getId());
	}
	
	public void deleteSignoffList(List<IDataEntity> deleteList) throws Exception{
		dao.deleteBatch(deleteList);
	}
	
	public void createSignOffList(List<IDataEntity> entityList) throws Exception{
		dao.createBatch(entityList);
	}
	
	private void populateExtentionRequest(List<WspSignoff> list, Integer finYear) throws Exception {
		for (WspSignoff wspSignoff : list) {
			findExtensionRequestCompanyAndFinancialYear(wspSignoff, finYear);
		}
	}
	
	private void findExtensionRequestCompanyAndFinancialYear(WspSignoff wspSignoff, Integer finYear) throws Exception {	
		if(ExtensionRequestService.instance().findByCompanyNoWSPWithFinYear(wspSignoff.getWsp().getCompany(), finYear).size() > 0) {
			ExtensionRequest extensionRequest = ExtensionRequestService.instance().findByCompanyNoWSPWithFinYear(wspSignoff.getWsp().getCompany(), finYear).get(0);
			if(extensionRequest!= null) {
				wspSignoff.setExtensionRequest(extensionRequest);
			}
		}		
	}
}
