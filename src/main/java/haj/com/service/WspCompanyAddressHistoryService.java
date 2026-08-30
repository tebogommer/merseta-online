package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WspCompanyAddressHistoryDAO;
import haj.com.entity.Wsp;
import haj.com.entity.WspCompanyAddressHistory;
import haj.com.entity.WspCompanyMainHistory;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class WspCompanyAddressHistoryService extends AbstractService {
	
	/** The dao. */
	private WspCompanyAddressHistoryDAO dao = new WspCompanyAddressHistoryDAO();
	
	private static WspCompanyAddressHistoryService wspCompanyAddressHistoryService;
	public static synchronized WspCompanyAddressHistoryService instance() {
		if (wspCompanyAddressHistoryService == null) {
			wspCompanyAddressHistoryService = new WspCompanyAddressHistoryService();
		}
		return wspCompanyAddressHistoryService;
	}

	/**
	 * Get all WspCompanyAddressHistory
 	 * @author TechFinium 
 	 * @see   WspCompanyAddressHistory
 	 * @return a list of {@link haj.com.entity.WspCompanyAddressHistory}
	 * @throws Exception the exception
 	 */
	public List<WspCompanyAddressHistory> allWspCompanyAddressHistory() throws Exception {
	  	return dao.allWspCompanyAddressHistory();
	}


	/**
	 * Create or update WspCompanyAddressHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WspCompanyAddressHistory
	 */
	public void create(WspCompanyAddressHistory entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WspCompanyAddressHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspCompanyAddressHistory
	 */
	public void update(WspCompanyAddressHistory entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WspCompanyAddressHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspCompanyAddressHistory
	 */
	public void delete(WspCompanyAddressHistory entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WspCompanyAddressHistory}
	 * @throws Exception the exception
	 * @see    WspCompanyAddressHistory
	 */
	public WspCompanyAddressHistory findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WspCompanyAddressHistory by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WspCompanyAddressHistory}
	 * @throws Exception the exception
	 * @see    WspCompanyAddressHistory
	 */
	public List<WspCompanyAddressHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WspCompanyAddressHistory
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspCompanyAddressHistory}
	 * @throws Exception the exception
	 */
	public List<WspCompanyAddressHistory> allWspCompanyAddressHistory(int first, int pageSize) throws Exception {
		return dao.allWspCompanyAddressHistory(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WspCompanyAddressHistory for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WspCompanyAddressHistory
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WspCompanyAddressHistory.class);
	}
	
    /**
     * Lazy load WspCompanyAddressHistory with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WspCompanyAddressHistory class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WspCompanyAddressHistory} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WspCompanyAddressHistory> allWspCompanyAddressHistory(Class<WspCompanyAddressHistory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WspCompanyAddressHistory>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WspCompanyAddressHistory for lazy load with filters
     * @author TechFinium 
     * @param entity WspCompanyAddressHistory class
     * @param filters the filters
     * @return Number of rows in the WspCompanyAddressHistory entity
     * @throws Exception the exception     
     */	
	public int count(Class<WspCompanyAddressHistory> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyAddressHistory> allWspCompanyAddressHistoryByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long targetKey, String targetClass) throws Exception {
		String hql = "select o from WspCompanyAddressHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		filters.put("targetKey", targetKey);
		filters.put("targetClass", targetClass);
		return (List<WspCompanyAddressHistory>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllWspCompanyAddressHistoryByTargetClassAndKey(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspCompanyAddressHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		return dao.countWhere(filters, hql);
	}
	
	public void deleteEntriesByWsp(Wsp wsp) throws Exception {
		List<IDataEntity> deleteList = new ArrayList<IDataEntity>();
		deleteList.addAll(findByWspId(wsp.getId()));
		if (deleteList.size() != 0) {
			dao.deleteBatch(deleteList);
		}
	}
	
	public void deleteEntriesByTargetKeyAndClass(String targetClass, Long targetKey) throws Exception{
		List<IDataEntity> deleteList = new ArrayList<IDataEntity>();
		deleteList.addAll(findByTargetClassAndKey(targetClass, targetKey));
		if (deleteList.size() != 0) {
			dao.deleteBatch(deleteList);
		}
	}
	
	public List<WspCompanyAddressHistory> findByWspId(Long wspId) throws Exception {
		return dao.findByWspId(wspId);
	}
	
	public List<WspCompanyAddressHistory> findByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
		return dao.findByTargetClassAndKey(targetClass, targetKey);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyAddressHistory> allWspCompanyAddressHistoryByTargetClassTargetKeyandMainId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long targetKey, String targetClass, WspCompanyMainHistory wspCompanyMainHistory) throws Exception {
		String hql = "select o from WspCompanyAddressHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.wspCompanyMainHistory.id = :wspCompanyMainHistoryId ";
		filters.put("targetKey", targetKey);
		filters.put("targetClass", targetClass);
		filters.put("wspCompanyMainHistoryId", wspCompanyMainHistory.getId());
		return (List<WspCompanyAddressHistory>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllWspCompanyAddressHistoryByTargetClassTargetKeyandMainId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspCompanyAddressHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.wspCompanyMainHistory.id = :wspCompanyMainHistoryId";
		return dao.countWhere(filters, hql);
	}
}
