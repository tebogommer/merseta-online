package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.WspCompanyHistoryDAO;
import haj.com.entity.Wsp;
import haj.com.entity.WspCompanyAddressHistory;
import haj.com.entity.WspCompanyHistory;
import haj.com.entity.WspCompanyMainHistory;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class WspCompanyHistoryService extends AbstractService {
	
	/** The dao. */
	private WspCompanyHistoryDAO dao = new WspCompanyHistoryDAO();
	
	private static WspCompanyHistoryService wspCompanyHistoryService;
	public static synchronized WspCompanyHistoryService instance() {
		if (wspCompanyHistoryService == null) {
			wspCompanyHistoryService = new WspCompanyHistoryService();
		}
		return wspCompanyHistoryService;
	}

	/**
	 * Get all WspCompanyHistory
 	 * @author TechFinium 
 	 * @see   WspCompanyHistory
 	 * @return a list of {@link haj.com.entity.WspCompanyHistory}
	 * @throws Exception the exception
 	 */
	public List<WspCompanyHistory> allWspCompanyHistory() throws Exception {
	  	return dao.allWspCompanyHistory();
	}


	/**
	 * Create or update WspCompanyHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WspCompanyHistory
	 */
	public void create(WspCompanyHistory entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WspCompanyHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspCompanyHistory
	 */
	public void update(WspCompanyHistory entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WspCompanyHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspCompanyHistory
	 */
	public void delete(WspCompanyHistory entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WspCompanyHistory}
	 * @throws Exception the exception
	 * @see    WspCompanyHistory
	 */
	public WspCompanyHistory findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WspCompanyHistory by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WspCompanyHistory}
	 * @throws Exception the exception
	 * @see    WspCompanyHistory
	 */
	public List<WspCompanyHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WspCompanyHistory
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspCompanyHistory}
	 * @throws Exception the exception
	 */
	public List<WspCompanyHistory> allWspCompanyHistory(int first, int pageSize) throws Exception {
		return dao.allWspCompanyHistory(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WspCompanyHistory for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WspCompanyHistory
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WspCompanyHistory.class);
	}
	
    /**
     * Lazy load WspCompanyHistory with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WspCompanyHistory class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WspCompanyHistory} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WspCompanyHistory> allWspCompanyHistory(Class<WspCompanyHistory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WspCompanyHistory>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WspCompanyHistory for lazy load with filters
     * @author TechFinium 
     * @param entity WspCompanyHistory class
     * @param filters the filters
     * @return Number of rows in the WspCompanyHistory entity
     * @throws Exception the exception     
     */	
	public int count(Class<WspCompanyHistory> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public int countCompanyHistoryByWspId(Long wspId) throws Exception {
		return dao.countCompanyHistoryByWspId(wspId);
	}
	
	public int countCompanyHistoryByTargetClassAndKey( String targetClass, Long targetKey) throws Exception {
		return dao.countCompanyHistoryByTargetClassAndKey(targetClass, targetKey);
	}
	
	public WspCompanyHistory findCompanyHistoryByWspId(Long wspId) throws Exception {
		return dao.findCompanyHistoryByWspId(wspId);
	}
	
	public List<WspCompanyHistory> findCompanyHistoryListByWspId(Long wspId) throws Exception {
		return dao.findCompanyHistoryListByWspId(wspId);
	}
	
	public List<WspCompanyHistory> findCompanyHistoryListByTargetClassAndKey( String targetClass, Long targetKey) throws Exception {
		return dao.findCompanyHistoryListByTargetClassAndKey(targetClass, targetKey);
	}
	
	public WspCompanyHistory findCompanyHistoryByWspIdAndReturnLastestEntry(Long wspId) throws Exception {
		List<WspCompanyHistory> entryList = findCompanyHistoryListByWspId(wspId);
		if (entryList.size() == 0) {
			return null;
		} else {
			return entryList.get(0);
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyHistory> allWspCompanyHistoryByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long targetKey, String targetClass) throws Exception {
		String hql = "select o from WspCompanyHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		filters.put("targetKey", targetKey);
		filters.put("targetClass", targetClass);
		return (List<WspCompanyHistory>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllWspCompanyHistoryByTargetClassAndKey(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspCompanyHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey";
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
	
	public List<WspCompanyHistory> findByWspId(Long wspId) throws Exception {
		return dao.findByWspId(wspId);
	}
	
	public List<WspCompanyHistory> findByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
		return dao.findByTargetClassAndKey(targetClass, targetKey);
	}
	
	public List<WspCompanyHistory> findByTargetClassAndKeyAndMainLink( String targetClass, Long targetKey, WspCompanyMainHistory wspCompanyMainHistory) throws Exception {
		return dao.findByTargetClassAndKeyAndMainLink(targetClass, targetKey, wspCompanyMainHistory.getId());
	}
	
	public WspCompanyHistory findByTargetClassAndKeyAndMainLinkReturnOne(Long targetKey,String targetClass , WspCompanyMainHistory wspCompanyMainHistory) throws Exception{
		List<WspCompanyHistory> list = findByTargetClassAndKeyAndMainLink(targetClass, targetKey, wspCompanyMainHistory);
		if (list != null && list.size() != 0) {
			return list.get(0);
		} else {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyHistory> allWspCompanyHistoryByTargetClassTargetKeyandMainId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long targetKey, String targetClass, WspCompanyMainHistory wspCompanyMainHistory) throws Exception {
		String hql = "select o from WspCompanyHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.wspCompanyMainHistory.id = :wspCompanyMainHistoryId ";
		filters.put("targetKey", targetKey);
		filters.put("targetClass", targetClass);
		filters.put("wspCompanyMainHistoryId", wspCompanyMainHistory.getId());
		return (List<WspCompanyHistory>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllWspCompanyHistoryByTargetClassTargetKeyandMainId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspCompanyHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.wspCompanyMainHistory.id = :wspCompanyMainHistoryId";
		return dao.countWhere(filters, hql);
	}

}