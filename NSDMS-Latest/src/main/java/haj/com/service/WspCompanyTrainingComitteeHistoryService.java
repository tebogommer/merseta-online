package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.primefaces.model.SortOrder;

import haj.com.dao.WspCompanyTrainingComitteeHistoryDAO;
import haj.com.entity.Wsp;
import haj.com.entity.WspCompanyEmployeesHistory;
import haj.com.entity.WspCompanyMainHistory;
import haj.com.entity.WspCompanyTrainingComitteeHistory;
import haj.com.entity.lookup.Equity;
import haj.com.entity.lookup.Title;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.lookup.EquityService;
import haj.com.service.lookup.OrganisedLabourUnionService;
import haj.com.service.lookup.TitleService;

public class WspCompanyTrainingComitteeHistoryService extends AbstractService {
	
	/** The dao. */
	private WspCompanyTrainingComitteeHistoryDAO dao = new WspCompanyTrainingComitteeHistoryDAO();
	
	/** The Service Levels */
	private GenderService genderService;
	private EquityService equityService;
	private TitleService titleService;
	private OrganisedLabourUnionService organisedLabourUnionService;
	
	private static WspCompanyTrainingComitteeHistoryService wspCompanyTrainingComitteeHistoryService;
	public static synchronized WspCompanyTrainingComitteeHistoryService instance() {
		if (wspCompanyTrainingComitteeHistoryService == null) {
			wspCompanyTrainingComitteeHistoryService = new WspCompanyTrainingComitteeHistoryService();
		}
		return wspCompanyTrainingComitteeHistoryService;
	}

	/**
	 * Get all WspCompanyTrainingComitteeHistory
 	 * @author TechFinium 
 	 * @see   WspCompanyTrainingComitteeHistory
 	 * @return a list of {@link haj.com.entity.WspCompanyTrainingComitteeHistory}
	 * @throws Exception the exception
 	 */
	public List<WspCompanyTrainingComitteeHistory> allWspCompanyTrainingComitteeHistory() throws Exception {
	  	return dao.allWspCompanyTrainingComitteeHistory();
	}


	/**
	 * Create or update WspCompanyTrainingComitteeHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WspCompanyTrainingComitteeHistory
	 */
	public void create(WspCompanyTrainingComitteeHistory entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WspCompanyTrainingComitteeHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspCompanyTrainingComitteeHistory
	 */
	public void update(WspCompanyTrainingComitteeHistory entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WspCompanyTrainingComitteeHistory.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WspCompanyTrainingComitteeHistory
	 */
	public void delete(WspCompanyTrainingComitteeHistory entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WspCompanyTrainingComitteeHistory}
	 * @throws Exception the exception
	 * @see    WspCompanyTrainingComitteeHistory
	 */
	public WspCompanyTrainingComitteeHistory findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WspCompanyTrainingComitteeHistory by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WspCompanyTrainingComitteeHistory}
	 * @throws Exception the exception
	 * @see    WspCompanyTrainingComitteeHistory
	 */
	public List<WspCompanyTrainingComitteeHistory> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WspCompanyTrainingComitteeHistory
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WspCompanyTrainingComitteeHistory}
	 * @throws Exception the exception
	 */
	public List<WspCompanyTrainingComitteeHistory> allWspCompanyTrainingComitteeHistory(int first, int pageSize) throws Exception {
		return dao.allWspCompanyTrainingComitteeHistory(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WspCompanyTrainingComitteeHistory for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WspCompanyTrainingComitteeHistory
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WspCompanyTrainingComitteeHistory.class);
	}
	
    /**
     * Lazy load WspCompanyTrainingComitteeHistory with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WspCompanyTrainingComitteeHistory class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WspCompanyTrainingComitteeHistory} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WspCompanyTrainingComitteeHistory> allWspCompanyTrainingComitteeHistory(Class<WspCompanyTrainingComitteeHistory> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WspCompanyTrainingComitteeHistory>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of WspCompanyTrainingComitteeHistory for lazy load with filters
     * @author TechFinium 
     * @param entity WspCompanyTrainingComitteeHistory class
     * @param filters the filters
     * @return Number of rows in the WspCompanyTrainingComitteeHistory entity
     * @throws Exception the exception     
     */	
	public int count(Class<WspCompanyTrainingComitteeHistory> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyTrainingComitteeHistory> allWspCompanyTrainingComitteeHistoryByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long targetKey, String targetClass) throws Exception {
		String hql = "select o from WspCompanyTrainingComitteeHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		filters.put("targetKey", targetKey);
		filters.put("targetClass", targetClass);
		return (List<WspCompanyTrainingComitteeHistory>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllWspCompanyTrainingComitteeHistoryByTargetClassAndKey(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspCompanyTrainingComitteeHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey";
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
	
	public List<WspCompanyTrainingComitteeHistory> findByWspId(Long wspId) throws Exception {
		return dao.findByWspId(wspId);
	}
	
	public List<WspCompanyTrainingComitteeHistory> findByTargetClassAndKey(String targetClass, Long targetKey) throws Exception {
		return dao.findByTargetClassAndKey(targetClass, targetKey);
	}
	
	@SuppressWarnings("unchecked")
	public List<WspCompanyTrainingComitteeHistory> allWspCompanyTrainingComitteeHistoryByTargetClassTargetKeyandMainId(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Long targetKey, String targetClass, WspCompanyMainHistory wspCompanyMainHistory) throws Exception {
		String hql = "select o from WspCompanyTrainingComitteeHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.wspCompanyMainHistory.id = :wspCompanyMainHistoryId ";
		filters.put("targetKey", targetKey);
		filters.put("targetClass", targetClass);
		filters.put("wspCompanyMainHistoryId", wspCompanyMainHistory.getId());
		return populateAdditionalInformationList((List<WspCompanyTrainingComitteeHistory>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}

	public int countAllWspCompanyTrainingComitteeHistoryHistoryByTargetClassTargetKeyandMainId(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WspCompanyTrainingComitteeHistory o where o.targetClass = :targetClass and o.targetKey = :targetKey and o.wspCompanyMainHistory.id = :wspCompanyMainHistoryId";
		return dao.countWhere(filters, hql);
	}
	
	/*
	 * 	private Equity equity;
	private Title title;
	 */
	
	public List<WspCompanyTrainingComitteeHistory> populateAdditionalInformationList(List<WspCompanyTrainingComitteeHistory> wspCompanyTrainingComitteeHistoryList) throws Exception{
		for (WspCompanyTrainingComitteeHistory wspCompanyTrainingComitteeHistory : wspCompanyTrainingComitteeHistoryList) {
			populateAdditionalInformation(wspCompanyTrainingComitteeHistory);
		}
		return wspCompanyTrainingComitteeHistoryList;
	}

	public WspCompanyTrainingComitteeHistory populateAdditionalInformation(WspCompanyTrainingComitteeHistory wspCompanyTrainingComitteeHistory) throws Exception {
		// populate Gender
		if (wspCompanyTrainingComitteeHistory.getGender() != null && wspCompanyTrainingComitteeHistory.getGender().getId() != null) {
			if (genderService == null) {
				genderService = new GenderService();
			}
			wspCompanyTrainingComitteeHistory.setGender(genderService.findByKey(wspCompanyTrainingComitteeHistory.getGender().getId()));
		}
		// populate title
		if (wspCompanyTrainingComitteeHistory.getTitle() != null && wspCompanyTrainingComitteeHistory.getTitle().getId() != null) {
			if (titleService == null) {
				titleService = new TitleService();
			}
			wspCompanyTrainingComitteeHistory.setTitle(titleService.findByKey(wspCompanyTrainingComitteeHistory.getTitle().getId()));
		}
		// populate equity
		if (wspCompanyTrainingComitteeHistory.getEquity() != null && wspCompanyTrainingComitteeHistory.getEquity().getId() != null) {
			if (equityService == null) {
				equityService = new EquityService();
			}
			wspCompanyTrainingComitteeHistory.setEquity(equityService.findByKey(wspCompanyTrainingComitteeHistory.getEquity().getId()));
		}
		
		// wspCompanyTrainingComitteeHistory
		if (wspCompanyTrainingComitteeHistory.getUnion() != null && wspCompanyTrainingComitteeHistory.getUnion().getId() != null) {
			if (organisedLabourUnionService == null) {
				organisedLabourUnionService = new OrganisedLabourUnionService();
			}
			wspCompanyTrainingComitteeHistory.setUnion(organisedLabourUnionService.findByKey(wspCompanyTrainingComitteeHistory.getUnion().getId()));
		}
		return wspCompanyTrainingComitteeHistory;
	}
}
