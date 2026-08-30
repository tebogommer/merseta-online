package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.DgContractingBulkItemsDAO;
import haj.com.entity.ActiveContracts;
import haj.com.entity.DgContractingBulkEntry;
import haj.com.entity.DgContractingBulkItems;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.BulkApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.service.lookup.RejectReasonsService;

public class DgContractingBulkItemsService extends AbstractService {
	/** The dao. */
	private DgContractingBulkItemsDAO dao = new DgContractingBulkItemsDAO();
	
	/* Service Levels */
	private RejectReasonsService rejectReasonsService;

	/**
	 * Get all DgContractingBulkItems
 	 * @author TechFinium 
 	 * @see   DgContractingBulkItems
 	 * @return a list of {@link haj.com.entity.DgContractingBulkItems}
	 * @throws Exception the exception
 	 */
	public List<DgContractingBulkItems> allDgContractingBulkItems() throws Exception {
	  	return dao.allDgContractingBulkItems();
	}


	/**
	 * Create or update DgContractingBulkItems.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DgContractingBulkItems
	 */
	public void create(DgContractingBulkItems entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DgContractingBulkItems.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DgContractingBulkItems
	 */
	public void update(DgContractingBulkItems entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DgContractingBulkItems.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DgContractingBulkItems
	 */
	public void delete(DgContractingBulkItems entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DgContractingBulkItems}
	 * @throws Exception the exception
	 * @see    DgContractingBulkItems
	 */
	public DgContractingBulkItems findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find DgContractingBulkItems by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DgContractingBulkItems}
	 * @throws Exception the exception
	 * @see    DgContractingBulkItems
	 */
	public List<DgContractingBulkItems> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DgContractingBulkItems
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DgContractingBulkItems}
	 * @throws Exception the exception
	 */
	public List<DgContractingBulkItems> allDgContractingBulkItems(int first, int pageSize) throws Exception {
		return dao.allDgContractingBulkItems(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DgContractingBulkItems for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the DgContractingBulkItems
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DgContractingBulkItems.class);
	}
	
    /**
     * Lazy load DgContractingBulkItems with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 DgContractingBulkItems class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DgContractingBulkItems} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkItems> allDgContractingBulkItems(Class<DgContractingBulkItems> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<DgContractingBulkItems>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DgContractingBulkItems for lazy load with filters
     * @author TechFinium 
     * @param entity DgContractingBulkItems class
     * @param filters the filters
     * @return Number of rows in the DgContractingBulkItems entity
     * @throws Exception the exception     
     */	
	public int count(Class<DgContractingBulkItems> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
		
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkItems> allDgContractingBulkItemsByBulkEntry(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, DgContractingBulkEntry dgContractingBulkEntry) throws Exception {
		String hql = "select o from DgContractingBulkItems o where o.dgContractingBulkEntry.id = :bulkEntryId";
		filters.put("bulkEntryId", dgContractingBulkEntry.getId());
		return populateAdditionalInformationList((List<DgContractingBulkItems>)dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllDgContractingBulkItemsByBulkEntry(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from DgContractingBulkItems o where o.dgContractingBulkEntry.id = :bulkEntryId";
		return dao.countWhere(filters, hql);
	}
	
	public Integer countEntriesAgainstDgContractingBulkEntry(DgContractingBulkEntry dgContractingBulkEntry) throws Exception {
		return dao.countEntriesAgainstDgContractingBulkEntry(dgContractingBulkEntry.getId());
	}

	public void linkActiveContractToBulkEntry(ActiveContracts activecontracts, DgContractingBulkEntry dgContractingBulkEntryOpen, Users sessionUser) throws Exception{
		DgContractingBulkItems bulkItem = new DgContractingBulkItems();
		bulkItem.setActiveContracts(activecontracts);
		bulkItem.setDgContractingBulkEntry(dgContractingBulkEntryOpen);
		bulkItem.setCreateDate(getSynchronizedDate());
		bulkItem.setCreateUser(sessionUser);
		bulkItem.setProcessed(false);
		create(bulkItem);
	}
	
	public void deleteLink(DgContractingBulkItems dgContractingBulkItems) throws Exception{
		delete(dgContractingBulkItems);
	}
	
	public List<DgContractingBulkItems> findByDgContractingBulkEntryAllEntries(DgContractingBulkEntry dgContractingBulkEntry) throws Exception {
		return dao.findByDgContractingBulkEntryAllEntries(dgContractingBulkEntry.getId());
	}
	
	public List<DgContractingBulkItems> findByDgContractingBulkEntryByBulkStatus(Long dgContractingBulkEntryId, BulkApprovalEnum bulkApprovalEnum, Boolean processedValue) throws Exception {
		return dao.findByDgContractingBulkEntryByBulkStatus(dgContractingBulkEntryId, bulkApprovalEnum, processedValue);
	}
	
	public List<DgContractingBulkItems> findByDgContractingBulkEntryWithoutStatus(DgContractingBulkEntry dgContractingBulkEntry) throws Exception {
		return dao.findByDgContractingBulkEntryWithoutStatus(dgContractingBulkEntry.getId());
	}
	
	public Integer countEntriesWithoutStatus(DgContractingBulkEntry dgContractingBulkEntry) throws Exception {
		return dao.countEntriesWithoutStatus(dgContractingBulkEntry.getId());
	}
	
	public Integer countEntriesByStatus(DgContractingBulkEntry dgContractingBulkEntry, ApprovalEnum approvalEnum) throws Exception {
		return dao.countEntriesByStatus(dgContractingBulkEntry.getId(), approvalEnum);
	}
	
	public Integer countEntriesWithoutNewStatus(DgContractingBulkEntry dgContractingBulkEntry) throws Exception {
		return dao.countEntriesWithoutNewStatus(dgContractingBulkEntry.getId());
	}
	
	public Integer countEntriesByNewStatusAssigned(DgContractingBulkEntry dgContractingBulkEntry, BulkApprovalEnum bulkApprovalEnum) throws Exception {
		return dao.countEntriesByNewStatusAssigned(dgContractingBulkEntry.getId(), bulkApprovalEnum);
	}
	
	public List<ActiveContracts> findActiveContractsWithDgContractingBulkEntryAndByStatus(DgContractingBulkEntry dgContractingBulkEntry, ApprovalEnum status) throws Exception {
		return dao.findActiveContractsWithDgContractingBulkEntryAndByStatus(dgContractingBulkEntry.getId(), status);
	}
	
	public ActiveContracts findActiveContractsByDgContractingBulkItemsId(DgContractingBulkItems dgContractingBulkItems) throws Exception {
		return dao.findActiveContractsByDgContractingBulkItemsId(dgContractingBulkItems.getId());
	}
	
	public List<DgContractingBulkItems> populateAdditionalInformationList(List<DgContractingBulkItems> dgContractingBulkItemsList) throws Exception{
		for (DgContractingBulkItems dgContractingBulkItems : dgContractingBulkItemsList) {
			populateAdditionalInformation(dgContractingBulkItems);
		}
		return dgContractingBulkItemsList;
	}
	
	public DgContractingBulkItems populateAdditionalInformation(DgContractingBulkItems dgContractingBulkItems) throws Exception{
		if (dgContractingBulkItems.getId() != null) {
			if (rejectReasonsService == null) {
				rejectReasonsService = new RejectReasonsService();
			}
			List<RejectReasons> rejectReasonsList = rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcess(dgContractingBulkItems.getId(), dgContractingBulkItems.getClass().getName(), ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF);
			if (rejectReasonsList.size() != 0) {
				dgContractingBulkItems.setRejectReasonsList(rejectReasonsList);
				dgContractingBulkItems.setRejectionReasonsToString(rejectReasonsService.locateReasonsSelectedByTargetKeyClassAndProcessAndReturnInString(dgContractingBulkItems.getId(), dgContractingBulkItems.getClass().getName(), ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF));
			}else{
				dgContractingBulkItems.setRejectReasonsList(new ArrayList<RejectReasons>());
				dgContractingBulkItems.setRejectionReasonsToString("N/A");
			}
		} else {
			dgContractingBulkItems.setRejectReasonsList(new ArrayList<RejectReasons>());
			dgContractingBulkItems.setRejectionReasonsToString("N/A");
		}
		return dgContractingBulkItems;
	}
	
	public List<DgContractingBulkItems> findApprovedBulkItemsToGenerateTranchPayments() throws Exception {
		return dao.findApprovedBulkItemsToGenerateTranchPayments();
	}
	
	public Integer countApprovedActiveContractsByActiveContractId(Long activeContractId) throws Exception {
		return dao.countApprovedActiveContractsByActiveContractId(activeContractId);
	}

	public List<ActiveContracts> allApprovedActiveContracts() throws Exception{
		return dao.allApprovedActiveContracts();
	}
	
	public Integer countByActiveContractId(Long activeContractId) throws Exception {
		return dao.countByActiveContractId(activeContractId);
	}
	
	public List<DgContractingBulkItems> findByActiveContractId(Long activeContractId) throws Exception {
		return dao.findByActiveContractId(activeContractId);
	}
	
}