package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.TradeAppraisalsChecklistDAO;
import haj.com.entity.TradeAppraisalsChecklist;
import haj.com.entity.WorkPlaceApproval;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.AppraisalChecklistService;
import haj.com.service.WorkPlaceApprovalService;

// TODO: Auto-generated Javadoc
/**
 * The Class TradeAppraisalsChecklistService.
 */
public class TradeAppraisalsChecklistService extends AbstractService {
	/** The dao. */
	private TradeAppraisalsChecklistDAO dao = new TradeAppraisalsChecklistDAO();
	private AppraisalChecklistService appraisalChecklistService = new AppraisalChecklistService();
	private WorkPlaceApprovalService workPlaceApprovalService = new WorkPlaceApprovalService();
	/**
	 * Get all TradeAppraisalsChecklist.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.TradeAppraisalsChecklist}
	 * @throws Exception the exception
	 * @see   TradeAppraisalsChecklist
	 */
	public List<TradeAppraisalsChecklist> allTradeAppraisalsChecklist() throws Exception {
	  	return dao.allTradeAppraisalsChecklist();
	}


	/**
	 * Create or update TradeAppraisalsChecklist.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     TradeAppraisalsChecklist
	 */
	public void create(TradeAppraisalsChecklist entity) throws Exception  {
	   if (entity.getId() ==null) {
		 entity.setCreateDate(new java.util.Date());
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  TradeAppraisalsChecklist.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TradeAppraisalsChecklist
	 */
	public void update(TradeAppraisalsChecklist entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  TradeAppraisalsChecklist.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    TradeAppraisalsChecklist
	 */
	public void delete(TradeAppraisalsChecklist entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.TradeAppraisalsChecklist}
	 * @throws Exception the exception
	 * @see    TradeAppraisalsChecklist
	 */
	public TradeAppraisalsChecklist findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find TradeAppraisalsChecklist by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.TradeAppraisalsChecklist}
	 * @throws Exception the exception
	 * @see    TradeAppraisalsChecklist
	 */
	public List<TradeAppraisalsChecklist> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load TradeAppraisalsChecklist.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.TradeAppraisalsChecklist}
	 * @throws Exception the exception
	 */
	public List<TradeAppraisalsChecklist> allTradeAppraisalsChecklist(int first, int pageSize) throws Exception {
		return dao.allTradeAppraisalsChecklist(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of TradeAppraisalsChecklist for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the TradeAppraisalsChecklist
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(TradeAppraisalsChecklist.class);
	}
	
    /**
     * Lazy load TradeAppraisalsChecklist with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 TradeAppraisalsChecklist class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.TradeAppraisalsChecklist} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<TradeAppraisalsChecklist> allTradeAppraisalsChecklist(Class<TradeAppraisalsChecklist> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<TradeAppraisalsChecklist>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of TradeAppraisalsChecklist for lazy load with filters.
     *
     * @author TechFinium
     * @param entity TradeAppraisalsChecklist class
     * @param filters the filters
     * @return Number of rows in the TradeAppraisalsChecklist entity
     * @throws Exception the exception
     */	
	public int count(Class<TradeAppraisalsChecklist> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public List<TradeAppraisalsChecklist> findByWorkPlaceApproval(WorkPlaceApproval workPlaceApproval) throws Exception {
		List<TradeAppraisalsChecklist> list = dao.findByWorkPlaceApproval(workPlaceApproval.getId());
		return populateAdditionalInformationList(list);
	}
	
	private List<TradeAppraisalsChecklist> populateAdditionalInformationList(List<TradeAppraisalsChecklist> tradeAppraisalsChecklists) throws Exception {
		for (TradeAppraisalsChecklist tradeAppraisalsChecklist: tradeAppraisalsChecklists)
		{
			populateAdditionalInformation(tradeAppraisalsChecklist);
		}
		return tradeAppraisalsChecklists;
	}

	private TradeAppraisalsChecklist populateAdditionalInformation(TradeAppraisalsChecklist tradeAppraisalsChecklist) throws Exception {
		if(tradeAppraisalsChecklist.getAppraisalChecklist().getId() !=null)
		{
			tradeAppraisalsChecklist.setAppraisalChecklist(appraisalChecklistService.findByKey(tradeAppraisalsChecklist.getAppraisalChecklist().getId()));
		}
		if(tradeAppraisalsChecklist.getWorkPlaceApproval().getId() !=null)
		{
			tradeAppraisalsChecklist.setWorkPlaceApproval(workPlaceApprovalService.findByKey(tradeAppraisalsChecklist.getWorkPlaceApproval().getId()));
		}
		return tradeAppraisalsChecklist;
	}


	public void updateAll(List<TradeAppraisalsChecklist> tradeAppraisalsChecklists) throws Exception {
		for(TradeAppraisalsChecklist tradeAppraisalsChecklist:tradeAppraisalsChecklists)
		{
			update(tradeAppraisalsChecklist);
		}
	}
}
