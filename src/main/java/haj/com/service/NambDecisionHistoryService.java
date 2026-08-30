package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.NambDecisionHistoryDAO;
import haj.com.entity.NambDecisionHistory;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RejectReasonsService;

public class NambDecisionHistoryService extends AbstractService {
	
	/** The dao. */
	private NambDecisionHistoryDAO dao = new NambDecisionHistoryDAO();
	
	/** The service levels */
	private RejectReasonsService rejectReasonsService = new RejectReasonsService();
	
	/** Instance of service level */
	private static NambDecisionHistoryService nambDecisionHistoryService;
	public static synchronized NambDecisionHistoryService instance() {
		if (nambDecisionHistoryService == null) {
			nambDecisionHistoryService = new NambDecisionHistoryService();
		}
		return nambDecisionHistoryService;
	}
	
	
	
	

	/**
	 * Get all NambDecisionHistory
 	 * @author TechFinium 
 	 * @see   NambDecisionHistory
 	 * @return a list of {@link haj.com.entity.NambDecisionHistory}
	 * @throws Exception the exception
 	 */
	public List<NambDecisionHistory> allNambDecisionHistory() throws Exception {
	  	return dao.allNambDecisionHistory();
	}


	/**
	 * Create or update NambDecisionHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     NambDecisionHistory
	 */
	public void create(NambDecisionHistory entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  NambDecisionHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NambDecisionHistory
	 */
	public void update(NambDecisionHistory entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  NambDecisionHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    NambDecisionHistory
	 */
	public void delete(NambDecisionHistory entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.NambDecisionHistory}
	 * @throws Exception the exception
	 * @see    NambDecisionHistory
	 */
	public NambDecisionHistory findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find NambDecisionHistory by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.NambDecisionHistory}
	 * @throws Exception the exception
	 * @see    NambDecisionHistory
	 */
	public List<NambDecisionHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load NambDecisionHistory
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.NambDecisionHistory}
	 * @throws Exception the exception
	 */
	public List<NambDecisionHistory> allNambDecisionHistory(int first, int pageSize) throws Exception {
		return dao.allNambDecisionHistory(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of NambDecisionHistory for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the NambDecisionHistory
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(NambDecisionHistory.class);
	}
	
	public NambDecisionHistory countByTargetClassAndKey(Long targetKey, String targetClass) throws Exception {
		return dao.countByTargetClassAndKey(targetKey, targetClass);
	}
	
    /**
     * Lazy load NambDecisionHistory with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 NambDecisionHistory class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.NambDecisionHistory} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<NambDecisionHistory> allNambDecisionHistory(Class<NambDecisionHistory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<NambDecisionHistory>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of NambDecisionHistory for lazy load with filters
     * @author TechFinium 
     * @param entity NambDecisionHistory class
     * @param filters the filters
     * @return Number of rows in the NambDecisionHistory entity
     * @throws Exception the exception     
     */	
	public int count(Class<NambDecisionHistory> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<NambDecisionHistory> allNambDecisionHistoryByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long targetKey, String targetClass) throws Exception {
		String hql = "select o from NambDecisionHistory o where o.targetKey = :targetKey and o.targetClass = :targetClass";
		filters.put("targetKey", targetKey);
		filters.put("targetClass", targetClass);
		return populateAdditionalInformationList((List<NambDecisionHistory>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql));
	}
	
	public int countAllNambDecisionHistoryByTargetClassAndKey( Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from NambDecisionHistory o where o.targetKey = :targetKey and o.targetClass = :targetClass";
		return  dao.countWhere(filters, hql);
	}
	
	public void createNewHistoryEntry(Users sessionUser, List<RejectReasons> rejecionReasonsList, String targetClass, Long targetKey, ApprovalEnum approvalEnum) throws Exception{
		NambDecisionHistory nambDecisionHistory = new NambDecisionHistory();
		nambDecisionHistory.setTargetClass(targetClass);
		nambDecisionHistory.setTargetKey(targetKey);
		if (nambDecisionHistory.getCreateUser() == null) {
			nambDecisionHistory.setCreateUser(sessionUser);
		}
		create(nambDecisionHistory);
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDbNoProcess(nambDecisionHistory.getId(), NambDecisionHistory.class.getName(), rejecionReasonsList, sessionUser);
	}
	
	public void createNewHistoryEntry(Users sessionUser, NambDecisionHistory nambDecisionHistory, List<RejectReasons> rejecionReasonsList) throws Exception{
		create(nambDecisionHistory);
		if (rejecionReasonsList != null && rejecionReasonsList.size() != 0) {
			RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDbNoProcess(nambDecisionHistory.getId(), NambDecisionHistory.class.getName(), rejecionReasonsList, sessionUser);
		}
	}
	
	public List<NambDecisionHistory> populateAdditionalInformationList(List<NambDecisionHistory> nambDecisionHistoryList) throws Exception{
		for (NambDecisionHistory nambDecisionHistory : nambDecisionHistoryList) {
			populateAdditionalInformation(nambDecisionHistory);
		}
		return nambDecisionHistoryList;
	}
	
	public NambDecisionHistory populateAdditionalInformation(NambDecisionHistory nambDecisionHistory) throws Exception{
		if (nambDecisionHistory.getId() != null) {
			nambDecisionHistory.setRejectionReasonsList(rejectReasonsService.locateReasonsSelectedByTargetKeyAndClass(nambDecisionHistory.getId(), NambDecisionHistory.class.getName()));
			nambDecisionHistory.setRejectionReasonsListString(rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcessAndReturnInString(nambDecisionHistory.getId(), NambDecisionHistory.class.getName(), null));
		} else {
			if (nambDecisionHistory.getRejectionReasonsList() == null || nambDecisionHistory.getRejectionReasonsList().size() == 0) {
				nambDecisionHistory.setRejectionReasonsList(new ArrayList<>());
				nambDecisionHistory.setRejectionReasonsListString("");
			}
		}
		return nambDecisionHistory;
	}


	
}
