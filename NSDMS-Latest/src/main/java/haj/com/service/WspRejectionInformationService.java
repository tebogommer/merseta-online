package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WspRejectionInformationDAO;
import haj.com.entity.DgVerification;
import haj.com.entity.DgVerificationRejectionInformation;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.Wsp;
import haj.com.entity.WspRejectionInformation;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class WspRejectionInformationService extends AbstractService {
	
	/** The dao. */
	private WspRejectionInformationDAO dao = new WspRejectionInformationDAO();
	
	/** WspRejectionInformationService Instance  */
	private static WspRejectionInformationService wspRejectionInformationService;
	public static synchronized WspRejectionInformationService instance() {
		if (wspRejectionInformationService == null) {
			wspRejectionInformationService = new WspRejectionInformationService();
		}
		return wspRejectionInformationService;
	}


	/**
	 * Get all WspRejectionInformation
 	 * @author TechFinium 
 	 * @see   WspRejectionInformation
 	 * @return a list of {@link haj.com.entity.WspRejectionInformation}
	 * @throws Exception the exception
 	 */
	public List<WspRejectionInformation> allWspRejectionInformation() throws Exception {
	  	return dao.allWspRejectionInformation();
	}


	/**
	 * Create or update WspRejectionInformation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WspRejectionInformation
	 */
	public void create(WspRejectionInformation entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WspRejectionInformation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspRejectionInformation
	 */
	public void update(WspRejectionInformation entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WspRejectionInformation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspRejectionInformation
	 */
	public void delete(WspRejectionInformation entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WspRejectionInformation}
	 * @throws Exception the exception
	 * @see    WspRejectionInformation
	 */
	public WspRejectionInformation findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WspRejectionInformation by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WspRejectionInformation}
	 * @throws Exception the exception
	 * @see    WspRejectionInformation
	 */
	public List<WspRejectionInformation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WspRejectionInformation
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspRejectionInformation}
	 * @throws Exception the exception
	 */
	public List<WspRejectionInformation> allWspRejectionInformation(int first, int pageSize) throws Exception {
		return dao.allWspRejectionInformation(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WspRejectionInformation for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WspRejectionInformation
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WspRejectionInformation.class);
	}
	
    /**
     * Lazy load WspRejectionInformation with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WspRejectionInformation class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WspRejectionInformation} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WspRejectionInformation> allWspRejectionInformation(Class<WspRejectionInformation> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WspRejectionInformation>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WspRejectionInformation for lazy load with filters
     * @author TechFinium 
     * @param entity WspRejectionInformation class
     * @param filters the filters
     * @return Number of rows in the WspRejectionInformation entity
     * @throws Exception the exception     
     */	
	public int count(Class<WspRejectionInformation> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	
	public List<WspRejectionInformation> findByWspRoleIdAndLastestEntry(Long wspId, Long roleID, Boolean lastestEntry) throws Exception {
		return dao.findByWspRoleIdAndLastestEntry(wspId, roleID, lastestEntry);
	}
	
	public List<WspRejectionInformation> findByWspUserIdAndLastestEntry(Long wspId, Long userId, Boolean lastestEntry) throws Exception {
		return dao.findByWspUserIdAndLastestEntry(wspId, userId, lastestEntry);
	}
	
	public List<WspRejectionInformation> findByWspAndLastestEntry(Long wspId, Boolean lastestEntry) throws Exception {
		return dao.findByWspAndLastestEntry(wspId, lastestEntry);
	}
	
	public void updateCreateLastestEntries(Wsp wsp, List<RejectReasons> rejectReasonsList, Users sessionUser, Tasks task, Roles role) throws Exception {
		updateOldEntries(wsp, sessionUser, task, role);
		createNewEntries(wsp, rejectReasonsList, sessionUser, task, role);
	}
	
	public void updateOldEntries(Wsp wsp, Users sessionUser, Tasks task, Roles role) throws Exception{
		List<IDataEntity> updateList = new ArrayList<>();
//		List<WspRejectionInformation> entryList = findByWspRoleIdAndLastestEntry(wsp.getId(), role.getId(), true);
		List<WspRejectionInformation> entryList = findByWspUserIdAndLastestEntry(wsp.getId(), sessionUser.getId(), true);
		for (WspRejectionInformation entryToUpdate : entryList) {
			entryToUpdate.setLatestEntry(false);
			updateList.add(entryToUpdate);
		}
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
		entryList = null;
		updateList = null;
	}
	
	public void createNewEntries(Wsp wsp, List<RejectReasons> rejectReasonsList, Users sessionUser, Tasks task, Roles role) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		for (RejectReasons rejectReason : rejectReasonsList) {
			WspRejectionInformation newEntry = new WspRejectionInformation(sessionUser, wsp, role, rejectReason, Boolean.TRUE);
			if (task != null && task.getId() != null) {
				newEntry.setFlatTaskLink(task.getId());
			}
			createList.add(newEntry);
			newEntry = null;
		}
		if (!createList.isEmpty()) {
			dao.createBatch(createList);
		}
		createList = null;
	}
}
