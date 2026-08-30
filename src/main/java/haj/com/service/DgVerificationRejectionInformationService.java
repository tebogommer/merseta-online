package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.DgVerificationRejectionInformationDAO;
import haj.com.entity.DgVerification;
import haj.com.entity.DgVerificationRejectionInformation;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.lookup.RejectReasons;
import haj.com.entity.lookup.Roles;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class DgVerificationRejectionInformationService extends AbstractService {
	
	/** The dao. */
	private DgVerificationRejectionInformationDAO dao = new DgVerificationRejectionInformationDAO();
	
	/** DgVerificationRejectionInformationService Instance  */
	private static DgVerificationRejectionInformationService dgVerificationRejectionInformationService;
	public static synchronized DgVerificationRejectionInformationService instance() {
		if (dgVerificationRejectionInformationService == null) {
			dgVerificationRejectionInformationService = new DgVerificationRejectionInformationService();
		}
		return dgVerificationRejectionInformationService;
	}

	/**
	 * Get all DgVerificationRejectionInformation
 	 * @author TechFinium 
 	 * @see   DgVerificationRejectionInformation
 	 * @return a list of {@link haj.com.entity.DgVerificationRejectionInformation}
	 * @throws Exception the exception
 	 */
	public List<DgVerificationRejectionInformation> allDgVerificationRejectionInformation() throws Exception {
	  	return dao.allDgVerificationRejectionInformation();
	}


	/**
	 * Create or update DgVerificationRejectionInformation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DgVerificationRejectionInformation
	 */
	public void create(DgVerificationRejectionInformation entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DgVerificationRejectionInformation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DgVerificationRejectionInformation
	 */
	public void update(DgVerificationRejectionInformation entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DgVerificationRejectionInformation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DgVerificationRejectionInformation
	 */
	public void delete(DgVerificationRejectionInformation entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DgVerificationRejectionInformation}
	 * @throws Exception the exception
	 * @see    DgVerificationRejectionInformation
	 */
	public DgVerificationRejectionInformation findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DgVerificationRejectionInformation by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DgVerificationRejectionInformation}
	 * @throws Exception the exception
	 * @see    DgVerificationRejectionInformation
	 */
	public List<DgVerificationRejectionInformation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DgVerificationRejectionInformation
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DgVerificationRejectionInformation}
	 * @throws Exception the exception
	 */
	public List<DgVerificationRejectionInformation> allDgVerificationRejectionInformation(int first, int pageSize) throws Exception {
		return dao.allDgVerificationRejectionInformation(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DgVerificationRejectionInformation for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the DgVerificationRejectionInformation
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DgVerificationRejectionInformation.class);
	}
	
    /**
     * Lazy load DgVerificationRejectionInformation with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 DgVerificationRejectionInformation class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DgVerificationRejectionInformation} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DgVerificationRejectionInformation> allDgVerificationRejectionInformation(Class<DgVerificationRejectionInformation> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DgVerificationRejectionInformation>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DgVerificationRejectionInformation for lazy load with filters
     * @author TechFinium 
     * @param entity DgVerificationRejectionInformation class
     * @param filters the filters
     * @return Number of rows in the DgVerificationRejectionInformation entity
     * @throws Exception the exception     
     */	
	public int count(Class<DgVerificationRejectionInformation> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<DgVerificationRejectionInformation> findByDgVerificationRoleIdAndLastestEntry(Long dgVerificationId, Long roleID, Boolean lastestEntry) throws Exception {
		return dao.findByDgVerificationRoleIdAndLastestEntry(dgVerificationId, roleID, lastestEntry);
	}
	
	public void updateCreateLastestEntries(DgVerification dgVerification, List<RejectReasons> rejectReasonsList, Users sessionUser, Tasks task, Roles role) throws Exception {
		updateOldEntries(dgVerification, sessionUser, task, role);
		craeteNewEntries(dgVerification, rejectReasonsList, sessionUser, task, role);
	}
	
	public void updateOldEntries(DgVerification dgVerification, Users sessionUser, Tasks task, Roles role) throws Exception{
		List<IDataEntity> updateList = new ArrayList<>();
		List<DgVerificationRejectionInformation> entryList = findByDgVerificationRoleIdAndLastestEntry(dgVerification.getId(), role.getId(), true);
		for (DgVerificationRejectionInformation entryToUpdate : entryList) {
			entryToUpdate.setLatestEntry(false);
			updateList.add(entryToUpdate);
		}
		if (!updateList.isEmpty()) {
			dao.updateBatch(updateList);
		}
		entryList = null;
		updateList = null;
	}
	
	public void craeteNewEntries(DgVerification dgVerification, List<RejectReasons> rejectReasonsList, Users sessionUser, Tasks task, Roles role) throws Exception {
		List<IDataEntity> createList = new ArrayList<>();
		for (RejectReasons rejectReason : rejectReasonsList) {
			DgVerificationRejectionInformation newEntry = new DgVerificationRejectionInformation(sessionUser, dgVerification, role, rejectReason, Boolean.TRUE);
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