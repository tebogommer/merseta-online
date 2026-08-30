package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.DgContractingBulkEntryDAO;
import haj.com.entity.ActiveContracts;
import haj.com.entity.DgContractingBulkEntry;
import haj.com.entity.DgContractingBulkItems;
import haj.com.entity.Doc;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.BulkApprovalEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.DiscretionalWithdrawalAppealEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class DgContractingBulkEntryService extends AbstractService {
	
	/** The dao. */
	private DgContractingBulkEntryDAO dao = new DgContractingBulkEntryDAO();
	
	/** The Service Levels */
	private DgContractingBulkItemsService dgContractingBulkItemsService = new DgContractingBulkItemsService();
	private ActiveContractsService activeContractsService = new ActiveContractsService();
	private UsersService usersService;

	/**
	 * Get all DgContractingBulkEntry
 	 * @author TechFinium 
 	 * @see   DgContractingBulkEntry
 	 * @return a list of {@link haj.com.entity.DgContractingBulkEntry}
	 * @throws Exception the exception
 	 */
	public List<DgContractingBulkEntry> allDgContractingBulkEntry() throws Exception {
	  	return dao.allDgContractingBulkEntry();
	}


	/**
	 * Create or update DgContractingBulkEntry.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     DgContractingBulkEntry
	 */
	public void create(DgContractingBulkEntry entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  DgContractingBulkEntry.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DgContractingBulkEntry
	 */
	public void update(DgContractingBulkEntry entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  DgContractingBulkEntry.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    DgContractingBulkEntry
	 */
	public void delete(DgContractingBulkEntry entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.DgContractingBulkEntry}
	 * @throws Exception the exception
	 * @see    DgContractingBulkEntry
	 */
	public DgContractingBulkEntry findByKey(long id) throws Exception {
       return populateAdditionalInformation(dao.findByKey(id));
	}

	/**
	 * Find DgContractingBulkEntry by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.DgContractingBulkEntry}
	 * @throws Exception the exception
	 * @see    DgContractingBulkEntry
	 */
	public List<DgContractingBulkEntry> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load DgContractingBulkEntry
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.DgContractingBulkEntry}
	 * @throws Exception the exception
	 */
	public List<DgContractingBulkEntry> allDgContractingBulkEntry(int first, int pageSize) throws Exception {
		return dao.allDgContractingBulkEntry(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of DgContractingBulkEntry for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the DgContractingBulkEntry
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(DgContractingBulkEntry.class);
	}
	
    /**
     * Lazy load DgContractingBulkEntry with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 DgContractingBulkEntry class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.DgContractingBulkEntry} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkEntry> allDgContractingBulkEntry(Class<DgContractingBulkEntry> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<DgContractingBulkEntry>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of DgContractingBulkEntry for lazy load with filters
     * @author TechFinium 
     * @param entity DgContractingBulkEntry class
     * @param filters the filters
     * @return Number of rows in the DgContractingBulkEntry entity
     * @throws Exception the exception     
     */	
	public int count(Class<DgContractingBulkEntry> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<DgContractingBulkEntry> allDgContractingBulkEntryWithStatusAssigned(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from DgContractingBulkEntry o where o.status <> null";
		return populateAdditionalInformationList((List<DgContractingBulkEntry>)dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql));
	}
	
	public int countAllDgContractingBulkEntryWithStatusAssigned(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from DgContractingBulkEntry o where o.status <> null";
		return dao.countWhere(filters, hql);
	}
	
	public List<DgContractingBulkEntry> populateAdditionalInformationList(List<DgContractingBulkEntry> DgContractingBulkEntryList) throws Exception{
		for (DgContractingBulkEntry dgContractingBulkEntry : DgContractingBulkEntryList) {
			populateAdditionalInformation(dgContractingBulkEntry);
		}
		return DgContractingBulkEntryList;
	}
	
	public DgContractingBulkEntry populateAdditionalInformation(DgContractingBulkEntry dgContractingBulkEntry) throws Exception{
		if (dgContractingBulkEntry.getId() != null) {
			dgContractingBulkEntry.setEntriesAssigned(dgContractingBulkItemsService.countEntriesAgainstDgContractingBulkEntry(dgContractingBulkEntry));
		} else {
			dgContractingBulkEntry.setEntriesAssigned(0);
		}
		return dgContractingBulkEntry;
	}


	public Integer countAllEntries() throws Exception {
		return dao.countAllEntries();
	}
	
	public Integer countOpenBulkEntries() throws Exception {
		return dao.countOpenBulkEntries();
	}
	
	public Integer countBulkEntriesWithStatus() throws Exception {
		return dao.countBulkEntriesWithStatus();
	}
	
	public DgContractingBulkEntry findOpenBatchEntry() throws Exception {
		return dao.findOpenBatchEntry();
	}
	
	public DgContractingBulkEntry createOpenEntry(DgContractingBulkEntry dgContractingBulkEntry, Users sessionUser) throws Exception{
		dgContractingBulkEntry = new DgContractingBulkEntry();
		dgContractingBulkEntry.setCreateUser(sessionUser);
		dgContractingBulkEntry.setBatchNumber(calculateBatchNumber());
		dgContractingBulkEntry.setCreateDate(getSynchronizedDate());
		dgContractingBulkEntry.setStatus(null);
		create(dgContractingBulkEntry);
		return findByKey(dgContractingBulkEntry.getId());
	}

	private Integer calculateBatchNumber() throws Exception {
		Integer count = countAllEntries();
		count ++;
		return count;
	}

	public void submitForApproval(DgContractingBulkEntry dgContractingBulkEntryOpen, Users sessionUser) throws Exception{
		dgContractingBulkEntryOpen.setSubmissionDate(getSynchronizedDate());
		dgContractingBulkEntryOpen.setSubmissionUser(sessionUser);
		dgContractingBulkEntryOpen.setStatus(ApprovalEnum.PendingFinalApproval);
		update(dgContractingBulkEntryOpen);
		TasksService.instance().findFirstInProcessAndCreateTask(sessionUser, dgContractingBulkEntryOpen.getId(), dgContractingBulkEntryOpen.getClass().getName(), ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF, false);
	}
	
	public void bulkApproveActiveContracts(DgContractingBulkEntry dgContractingBulkEntry, Users sessionUser, Tasks task) throws Exception{
		List<IDataEntity> updateBatch = new ArrayList<IDataEntity>();
		dgContractingBulkEntry.setStatus(ApprovalEnum.Approved);
		dgContractingBulkEntry.setApprovalUser(sessionUser);
		dgContractingBulkEntry.setApprovalDate(getSynchronizedDate());
		updateBatch.add(dgContractingBulkEntry);
		
		List<DgContractingBulkItems> itemsAssigned = new ArrayList<>();
		itemsAssigned = dgContractingBulkItemsService.findByDgContractingBulkEntryAllEntries(dgContractingBulkEntry);
//		itemsAssigned = dgContractingBulkItemsService.findByDgContractingBulkEntryWithoutStatus(dgContractingBulkEntry);
		for (DgContractingBulkItems dgContractingBulkItems : itemsAssigned) {
			if (dgContractingBulkItems.getProcessed() == null || !dgContractingBulkItems.getProcessed()) {
				dgContractingBulkItems.setStatusAssigned(ApprovalEnum.Approved);
				dgContractingBulkItems.setApprovalDate(getSynchronizedDate());
				dgContractingBulkItems.setApprovalUser(sessionUser);
				dgContractingBulkItems.setProcessed(true);
				updateBatch.add(dgContractingBulkItems);
				// clear any rejection reasons
				RejectReasonMultipleSelectionService.instance().clearEntries(dgContractingBulkItems.getId(), dgContractingBulkItems.getClass().getName(), ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF);
			}
			
		}
		// updates all entries
		if (updateBatch.size() != 0) {
			dao.updateBatch(updateBatch);
		}
		itemsAssigned = null;
		
		List<ActiveContracts> approvedContracts = dgContractingBulkItemsService.findActiveContractsWithDgContractingBulkEntryAndByStatus(dgContractingBulkEntry, ApprovalEnum.Approved);
		// option one
		for (ActiveContracts activeContract : approvedContracts) {
			activeContractsService.approveOneBatchEntryOfActiveContracts(activeContract, sessionUser);
			// send notification?
		}
		TasksService.instance().completeTask(task);
		
		// option two
//		activeContractsService.approveBatchOfActiveContracts(approvedContracts, sessionUser, task);
	}
	
	public void approveSingleLineItem(DgContractingBulkItems dgContractingBulkItems, Users sessionUser) throws Exception {
		List<IDataEntity> updateBatch = new ArrayList<IDataEntity>();
		dgContractingBulkItems.setStatusAssigned(ApprovalEnum.Approved);
		dgContractingBulkItems.setApprovalDate(getSynchronizedDate());
		dgContractingBulkItems.setApprovalUser(sessionUser);
		updateBatch.add(dgContractingBulkItems);
		if (updateBatch.size() != 0) {
			dao.updateBatch(updateBatch);
		}
		RejectReasonMultipleSelectionService.instance().clearEntries(dgContractingBulkItems.getId(), dgContractingBulkItems.getClass().getName(), ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF);
		ActiveContracts activeContract = dgContractingBulkItemsService.findActiveContractsByDgContractingBulkItemsId(dgContractingBulkItems);
		activeContractsService.approveOneBatchEntryOfActiveContracts(activeContract, sessionUser);
		// send notification?
	}
	
	public void bulkRejectActiveContracts(DgContractingBulkEntry dgContractingBulkEntry, Users sessionUser, Tasks task, List<RejectReasons> rejectionReasonsList) throws Exception{
		List<IDataEntity> updateBatch = new ArrayList<IDataEntity>();
		dgContractingBulkEntry.setStatus(ApprovalEnum.Rejected);
		dgContractingBulkEntry.setApprovalUser(sessionUser);
		dgContractingBulkEntry.setApprovalDate(getSynchronizedDate());
		updateBatch.add(dgContractingBulkEntry);
		
		List<DgContractingBulkItems> itemsAssigned = new ArrayList<>();
		itemsAssigned = dgContractingBulkItemsService.findByDgContractingBulkEntryAllEntries(dgContractingBulkEntry);
//		itemsAssigned = dgContractingBulkItemsService.findByDgContractingBulkEntryWithoutStatus(dgContractingBulkEntry);
		for (DgContractingBulkItems dgContractingBulkItems : itemsAssigned) {
			// set item entry 
			dgContractingBulkItems.setStatusAssigned(ApprovalEnum.Rejected);
			dgContractingBulkItems.setApprovalDate(getSynchronizedDate());
			dgContractingBulkItems.setApprovalUser(sessionUser);
			updateBatch.add(dgContractingBulkItems);
			// locate 
			
			// add rejection reasons
			RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(dgContractingBulkItems.getId(), dgContractingBulkItems.getClass().getName(), rejectionReasonsList, sessionUser, ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF);
		}
		
		// updates all entries
		if (updateBatch.size() != 0) {
			dao.updateBatch(updateBatch);
		}
		itemsAssigned = null;
		
		// create rejection reasons
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(dgContractingBulkEntry.getId(), dgContractingBulkEntry.getClass().getName(), rejectionReasonsList, sessionUser, ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF);
		
		List<ActiveContracts> rejectedContracts = dgContractingBulkItemsService.findActiveContractsWithDgContractingBulkEntryAndByStatus(dgContractingBulkEntry, ApprovalEnum.Rejected);
		// option one
		for (ActiveContracts activeContract : rejectedContracts) {
			activeContractsService.rejectEntryOfActiveContracts(activeContract, sessionUser, rejectionReasonsList);
		}
		TasksService.instance().completeTask(task);
		
		// option two
//		activeContractsService.rejectBatchOfActiveContracts(rejectedContracts, sessionUser, task);
	}
	
	public void rejectSingleLineItem(DgContractingBulkItems dgContractingBulkItems, Users sessionUser, List<RejectReasons> rejectionReasonsList) throws Exception {
		List<IDataEntity> updateBatch = new ArrayList<IDataEntity>();
		dgContractingBulkItems.setStatusAssigned(ApprovalEnum.Rejected);
		dgContractingBulkItems.setApprovalDate(getSynchronizedDate());
		dgContractingBulkItems.setApprovalUser(sessionUser);
		updateBatch.add(dgContractingBulkItems);
		if (updateBatch.size() != 0) {
			dao.updateBatch(updateBatch);
		}
		
		RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(dgContractingBulkItems.getId(), dgContractingBulkItems.getClass().getName(), rejectionReasonsList, sessionUser, ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF);
		
		activeContractsService.rejectEntryOfActiveContracts(dgContractingBulkItems.getActiveContracts(), sessionUser, rejectionReasonsList);
	}
	
	public void completeBulkEntry(DgContractingBulkEntry dgContractingBulkEntry, Users sessionUser, Tasks task) throws Exception{
		
		List<IDataEntity> updateBatch = new ArrayList<IDataEntity>();
		dgContractingBulkEntry.setStatus(ApprovalEnum.Completed);
		dgContractingBulkEntry.setApprovalUser(sessionUser);
		dgContractingBulkEntry.setApprovalDate(getSynchronizedDate());
		updateBatch.add(dgContractingBulkEntry);
		if (updateBatch.size() != 0) {
			dao.updateBatch(updateBatch);
		}
		TasksService.instance().completeTask(task);
	
	}
	
	public void completeBulkActionByStatusesAssigned(DgContractingBulkEntry dgContractingBulkEntry, Users sessionUser, Tasks task, List<RejectReasons> rejectionReasonsList) throws Exception{
		
		List<IDataEntity> updateBatch = new ArrayList<IDataEntity>();
		dgContractingBulkEntry.setStatus(ApprovalEnum.Completed);
		dgContractingBulkEntry.setApprovalUser(sessionUser);
		dgContractingBulkEntry.setApprovalDate(getSynchronizedDate());
		updateBatch.add(dgContractingBulkEntry);
		
		List<DgContractingBulkItems> itemsAssigned = dgContractingBulkItemsService.findByDgContractingBulkEntryAllEntries(dgContractingBulkEntry);
		for (DgContractingBulkItems dgContractingBulkItems : itemsAssigned) {
			dgContractingBulkItems.setApprovalDate(getSynchronizedDate());
			dgContractingBulkItems.setApprovalUser(sessionUser);
			dgContractingBulkItems.setProcessed(true);
			updateBatch.add(dgContractingBulkItems);
		}
		
		if (updateBatch.size() != 0) {
			dao.updateBatch(updateBatch);
		}
		
		itemsAssigned = dgContractingBulkItemsService.findByDgContractingBulkEntryAllEntries(dgContractingBulkEntry);
		for (DgContractingBulkItems dgContractingBulkItem : itemsAssigned) {
			if (dgContractingBulkItem.getStatusAssigned() != null) {
				if (dgContractingBulkItem.getStatusAssigned() == ApprovalEnum.Approved) {
					RejectReasonMultipleSelectionService.instance().clearEntries(dgContractingBulkItem.getId(), dgContractingBulkItem.getClass().getName(), ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF);
					activeContractsService.approveOneBatchEntryOfActiveContracts(dgContractingBulkItem.getActiveContracts(), sessionUser);
					// send notification ?
				} else {
					RejectReasonMultipleSelectionService.instance().removeCreateLinksOnDb(dgContractingBulkItem.getId(), dgContractingBulkItem.getClass().getName(), rejectionReasonsList, sessionUser, ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF);
					activeContractsService.rejectEntryOfActiveContracts(dgContractingBulkItem.getActiveContracts(), sessionUser, rejectionReasonsList);
				}
			}
		}
		TasksService.instance().completeTask(task);
	}
	
	public void withdrawBulkActionItem(DgContractingBulkItems dgContractingBulkItem, Users sessionUser,DiscretionalWithdrawalAppealEnum withdrawalAppealEnum, Doc doc) throws Exception {
		List<IDataEntity> updateBatch = new ArrayList<IDataEntity>();
		if (doc.getId() == null && doc.getData() == null) {
			throw new Exception("Upload Supporing Document Before Proceeding");
		}
		
		// update bulk item entry
		dgContractingBulkItem.setApprovalDate(getSynchronizedDate());
		dgContractingBulkItem.setApprovalUser(sessionUser);
		dgContractingBulkItem.setProcessed(true);
		dgContractingBulkItem.setBulkApproval(BulkApprovalEnum.Withdrawn);
		dgContractingBulkItem.setStatusAssigned(ApprovalEnum.Withdrawn);
		dgContractingBulkItem.setWithdrawalAppealEnum(withdrawalAppealEnum);
		updateBatch.add(dgContractingBulkItem);
		
		if (updateBatch.size() != 0) {
			dao.updateBatch(updateBatch);
		}
		
		// clears rejection reasons
		RejectReasonMultipleSelectionService.instance().clearEntries(dgContractingBulkItem.getId(), dgContractingBulkItem.getClass().getName(), ConfigDocProcessEnum.DG_CONTRACT_SIGNOFF);
		
		if (activeContractsService == null) {
			activeContractsService = new ActiveContractsService();
		}
		
		// withdraw process
		ActiveContracts activeContract = activeContractsService.findByKey(dgContractingBulkItem.getActiveContracts().getId());
		activeContractsService.withdrawActiveContracts(activeContract, sessionUser, withdrawalAppealEnum, null, doc);		
	}
	
	public void completeBulkActionByStatusesAssignedVersionTwo(DgContractingBulkEntry dgContractingBulkEntry, Users sessionUser, Tasks task) throws Exception{
		
		if (activeContractsService == null) {
			activeContractsService = new ActiveContractsService();
		}
		
		List<IDataEntity> updateBatch = new ArrayList<IDataEntity>();
		dgContractingBulkEntry.setStatus(ApprovalEnum.Completed);
		dgContractingBulkEntry.setApprovalUser(sessionUser);
		dgContractingBulkEntry.setApprovalDate(getSynchronizedDate());
		updateBatch.add(dgContractingBulkEntry);
		
		List<DgContractingBulkItems> itemsAssigned = dgContractingBulkItemsService.findByDgContractingBulkEntryAllEntries(dgContractingBulkEntry);
		for (DgContractingBulkItems dgContractingBulkItems : itemsAssigned) {
			if (dgContractingBulkItems.getProcessed() == null || !dgContractingBulkItems.getProcessed()) {
				dgContractingBulkItems.setApprovalDate(getSynchronizedDate());
				dgContractingBulkItems.setApprovalUser(sessionUser);
				updateBatch.add(dgContractingBulkItems);
			}
		}
		
		if (updateBatch.size() != 0) {
			dao.updateBatch(updateBatch);
		}
		
		// Final Approve Contracts
		itemsAssigned = dgContractingBulkItemsService.findByDgContractingBulkEntryByBulkStatus(dgContractingBulkEntry.getId(), BulkApprovalEnum.FinalApproved, false);
		for (DgContractingBulkItems finalApproveItems : itemsAssigned) {
			activeContractsService.approveOneBatchEntryOfActiveContracts(finalApproveItems.getActiveContracts(), sessionUser);
			finalApproveItems.setProcessed(true);
			dgContractingBulkItemsService.update(finalApproveItems);
			// send notification of approval
		}
		
		// Reject To SDF
		itemsAssigned = null;
		itemsAssigned = dgContractingBulkItemsService.findByDgContractingBulkEntryByBulkStatus(dgContractingBulkEntry.getId(), BulkApprovalEnum.FinalToSdf, false);
		for (DgContractingBulkItems rejectToSdf : itemsAssigned) {
			rejectToSdf = dgContractingBulkItemsService.populateAdditionalInformation(rejectToSdf);
			activeContractsService.rejectEntryOfActiveContracts(rejectToSdf.getActiveContracts(), sessionUser, rejectToSdf.getRejectReasonsList());
			rejectToSdf.setProcessed(true);
			dgContractingBulkItemsService.update(rejectToSdf);
		}
		
		// RejectTo Bulk Action
		itemsAssigned = null;
		itemsAssigned = dgContractingBulkItemsService.findByDgContractingBulkEntryByBulkStatus(dgContractingBulkEntry.getId(), BulkApprovalEnum.FinalToBulkAction, false);
		for (DgContractingBulkItems rejectToBulk : itemsAssigned) {
			rejectToBulk = dgContractingBulkItemsService.populateAdditionalInformation(rejectToBulk);
			rejectToBulk.getActiveContracts().setSignOffState(true);
			rejectToBulk.getActiveContracts().setAwaitingBatchSignOff(true);
			rejectToBulk.setProcessed(true);
			activeContractsService.update(rejectToBulk.getActiveContracts());
			dgContractingBulkItemsService.update(rejectToBulk);
		}
		
		// send notification of bulk
		if (itemsAssigned.size() != 0) {
			notifyRejectionBackToBulk(dgContractingBulkEntry, itemsAssigned, sessionUser);
		}
		
		
		itemsAssigned = null;
		itemsAssigned = dgContractingBulkItemsService.findByDgContractingBulkEntryByBulkStatus(dgContractingBulkEntry.getId(), BulkApprovalEnum.FinalRejected, false);
		for (DgContractingBulkItems finalReject : itemsAssigned) {
			finalReject = dgContractingBulkItemsService.populateAdditionalInformation(finalReject);
			ActiveContracts activeContract = activeContractsService.findByKey(finalReject.getActiveContracts().getId());
			activeContractsService.finalRejectActiveContract(activeContract, sessionUser, null, finalReject.getRejectReasonsList());
			finalReject.setProcessed(true);
			dgContractingBulkItemsService.update(finalReject);
		}
		itemsAssigned = null;
	
		TasksService.instance().completeTask(task);	
	}
	
	private void notifyRejectionBackToBulk(DgContractingBulkEntry dgContractingBulkEntry, List<DgContractingBulkItems> itemsAssigned, Users sessionUser) throws Exception {
		if (usersService == null) {
			usersService = new UsersService();
		}
		String sessionUserFullName = "";
		if (sessionUser.getTitle() != null && sessionUser.getTitle().getId() != null ) {
			sessionUserFullName += sessionUser.getTitle().getDescription() + " ";
		}
		sessionUserFullName += sessionUser.getFirstName() + " " + sessionUser.getLastName();
		
		List<Users> emailRecivers = usersService.findByDgContractBulk(true);
		String subject = "DG CONTRACT BULK REJECTION TO ASSIGNMENT LIST";
		String message = "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear #FULL_NAME#,</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Please be advised that DG Contracting Bulk Approval (Batch Number: #BACTH_NUMBER#) has processed by: #SESSION_USER_FULL_NAME# and contains rejection entries to the assignment page.</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> The reject contracts are as follows below: </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> #REJECTED_LIST# </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Please Attend to this. </p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Yours sincerely, </p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\"> Chief Executive Officer of the merSETA</p>" + "<br/>";
		message = message.replaceAll("#BACTH_NUMBER#", dgContractingBulkEntry.getBatchNumber().toString());
		message = message.replaceAll("#SESSION_USER_FULL_NAME#", sessionUserFullName);
		message = message.replaceAll("#REJECTED_LIST#", contractsRejectedToBulkList(itemsAssigned));
		if (emailRecivers.size() != 0) {
			for (Users users : emailRecivers) {
				String fullName = "";
				if (users.getTitle() != null && users.getTitle().getId() != null) {
					fullName = users.getTitle().getDescription() + " ";
				}
				fullName += users.getFirstName() + " " + users.getLastName();
				GenericUtility.sendMail(users.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), null);
			}
		} else {
			String fullName = "";
			if (sessionUser.getTitle() != null && sessionUser.getTitle().getId() != null) {
				fullName = sessionUser.getTitle().getDescription() + " ";
			}
			// welcome = welcome.replaceAll("#Title#", title);
			fullName += sessionUser.getFirstName() + " " + sessionUser.getLastName();
			GenericUtility.sendMail(sessionUser.getEmail(), subject, message.replaceAll("#FULL_NAME#", fullName), null);
		}
	}
	
	private String contractsRejectedToBulkList(List<DgContractingBulkItems> itemsAssigned) throws Exception{
		String list = "<ul>";
		try {
			for (DgContractingBulkItems dgContractingBulkItems : itemsAssigned) {
				list += "<li> " + dgContractingBulkItems.getActiveContracts().getRefnoAuto();
				if (dgContractingBulkItems.getRejectReasonsList() != null && dgContractingBulkItems.getRejectReasonsList().size() != 0) {
					list += "<ul>";
					for (RejectReasons rejectReason : dgContractingBulkItems.getRejectReasonsList()) {
						list += "<li>" + rejectReason.getDescription() + "</li>";
					}
					list += "</ul>";
				}
				list += "</li>";
			}
		} catch (Exception e) {
		}
		list += "</ul>";
		return list;
	}
	
	public void testEmail(DgContractingBulkItems dgContractingBulkItem, Users sessionUser) throws Exception {
		activeContractsService.sendApprovalNotification(dgContractingBulkItem.getActiveContracts(), sessionUser);
	}
	
}