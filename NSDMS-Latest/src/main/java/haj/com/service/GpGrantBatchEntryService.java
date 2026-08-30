package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.GpGrantBatchEntryDAO;
import haj.com.entity.Company;
import haj.com.entity.GpGrantBatchEntry;
import haj.com.entity.GpGrantBatchList;
import haj.com.entity.enums.GpDocumentType;
import haj.com.framework.AbstractService;
import haj.com.sars.SarsEmployerDetail;
import haj.com.utils.GenericUtility;

public class GpGrantBatchEntryService extends AbstractService {
	
	/** The dao. */
	private GpGrantBatchEntryDAO dao = new GpGrantBatchEntryDAO();
	
	/** Service Levels */
	private SarsEmployerDetailService sarsEmployerDetailService = new SarsEmployerDetailService();
	private CompanyService companyService = new CompanyService();
	
	

	/**
	 * Get all GpGrantBatchEntry
 	 * @author TechFinium 
 	 * @see   GpGrantBatchEntry
 	 * @return a list of {@link haj.com.entity.GpGrantBatchEntry}
	 * @throws Exception the exception
 	 */
	public List<GpGrantBatchEntry> allGpGrantBatchEntry() throws Exception {
	  	return dao.allGpGrantBatchEntry();
	}


	/**
	 * Create or update GpGrantBatchEntry.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     GpGrantBatchEntry
	 */
	public void create(GpGrantBatchEntry entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  GpGrantBatchEntry.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    GpGrantBatchEntry
	 */
	public void update(GpGrantBatchEntry entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  GpGrantBatchEntry.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    GpGrantBatchEntry
	 */
	public void delete(GpGrantBatchEntry entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.GpGrantBatchEntry}
	 * @throws Exception the exception
	 * @see    GpGrantBatchEntry
	 */
	public GpGrantBatchEntry findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find GpGrantBatchEntry by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.GpGrantBatchEntry}
	 * @throws Exception the exception
	 * @see    GpGrantBatchEntry
	 */
	public List<GpGrantBatchEntry> findByName(String desc) throws Exception {
		return populateAdditionalInformationList(dao.findByName(desc));
	}
	
	/**
	 * Lazy load GpGrantBatchEntry
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.GpGrantBatchEntry}
	 * @throws Exception the exception
	 */
	public List<GpGrantBatchEntry> allGpGrantBatchEntry(int first, int pageSize) throws Exception {
		return populateAdditionalInformationList(dao.allGpGrantBatchEntry(Long.valueOf(first), pageSize));
	}
		
	
	/**
	 * Get count of GpGrantBatchEntry for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the GpGrantBatchEntry
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(GpGrantBatchEntry.class);
	}
	
    /**
     * Lazy load GpGrantBatchEntry with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 GpGrantBatchEntry class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.GpGrantBatchEntry} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchEntry> allGpGrantBatchEntry(Class<GpGrantBatchEntry> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList(( List<GpGrantBatchEntry>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters));
	}
	
    /**
     * Get count of GpGrantBatchEntry for lazy load with filters
     * @author TechFinium 
     * @param entity GpGrantBatchEntry class
     * @param filters the filters
     * @return Number of rows in the GpGrantBatchEntry entity
     * @throws Exception the exception     
     */	
	public int count(Class<GpGrantBatchEntry> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public List<GpGrantBatchEntry> allGpGrantBatchEntryByBatchList(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, GpGrantBatchList gpGrantBatchList) throws Exception {
		String hql = "select o from GpGrantBatchEntry o where o.gpGrantBatchList.id = :batchListId ";
		filters.put("batchListId", gpGrantBatchList.getId());
		return populateAdditionalInformationList(( List<GpGrantBatchEntry>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters,hql));
	}
	
	public int countAllGpGrantBatchEntryByBatchList(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from GpGrantBatchEntry o where o.gpGrantBatchList.id = :batchListId ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchEntry> allGpGrantBatchEntryByBatchListViewOnly(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, GpGrantBatchList gpGrantBatchList) throws Exception {
		String hql = "select o from GpGrantBatchEntry o where o.gpGrantBatchList.id = :batchListId ";
		filters.put("batchListId", gpGrantBatchList.getId());
		return ( List<GpGrantBatchEntry>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters,hql);
	}
	
	public int countAllGpGrantBatchEntryByBatchListViewOnly(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from GpGrantBatchEntry o where o.gpGrantBatchList.id = :batchListId ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchEntry> gpGrantBatchEntryByBatchListToBeRemovedValue(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, GpGrantBatchList gpGrantBatchList, Boolean toBeRemoved) throws Exception {
		String hql = "select o from GpGrantBatchEntry o where o.gpGrantBatchList.id = :batchListId and o.toBeRemoved = :toBeRemoved ";
		filters.put("batchListId", gpGrantBatchList.getId());
		filters.put("toBeRemoved", toBeRemoved);
		return populateAdditionalInformationList((List<GpGrantBatchEntry>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters,hql));
	}
	
	public int countGpGrantBatchEntryByBatchListToBeRemovedValue(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from GpGrantBatchEntry o where o.gpGrantBatchList.id = :batchListId and o.toBeRemoved = :toBeRemoved ";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<GpGrantBatchEntry> gpGrantBatchEntryByBatchListToBeRemovedValueMandatory(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, GpGrantBatchList gpGrantBatchList, Boolean toBeRemoved) throws Exception {
		String hql = "select o from GpGrantBatchEntry o where o.gpGrantBatchList.id = :batchListId and o.toBeRemoved = :toBeRemoved ";
		filters.put("batchListId", gpGrantBatchList.getId());
		filters.put("toBeRemoved", toBeRemoved);
		return (List<GpGrantBatchEntry>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters,hql);
	}
	
	public int countGpGrantBatchEntryByBatchListToBeRemovedValueMandatory(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from GpGrantBatchEntry o where o.gpGrantBatchList.id = :batchListId and o.toBeRemoved = :toBeRemoved ";
		return dao.countWhere(filters, hql);
	}
	
	public List<GpGrantBatchEntry> allEntriesToBeProcessesed(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
		return dao.allEntriesToBeProcessesed(gpGrantBatchListId, toBeRemoved);
	}
	
	public List<GpGrantBatchEntry> allEntriesToBeProcessesedGrouping(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
		return populateAdditionalInformationList(dao.allEntriesToBeProcessesedGrouping(gpGrantBatchListId, toBeRemoved));
	}
	
	public List<GpGrantBatchEntry> allEntriesByBatchCompoundKey(String levyNumber, Integer schemeYear, Long gpGrantBatchListId) throws Exception {
		return populateAdditionalInformationList(dao.allEntriesByBatchCompoundKey(levyNumber, schemeYear, gpGrantBatchListId));
	}
	
	public List<GpGrantBatchEntry> allEntriesByBatchListToBeRemoved(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
		return populateAdditionalInformationList(dao.allEntriesByBatchListToBeRemoved(gpGrantBatchListId, toBeRemoved));
	}
	
	public List<GpGrantBatchEntry> allEntriesToBeDeleted(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
		return dao.allEntriesByBatchListToBeRemoved(gpGrantBatchListId, toBeRemoved);
	}
	
	public List<String> allDistinctLevyNumbersByBatchIdAndToBeRemoved(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
		return dao.allDistinctLevyNumbersByBatchIdAndToBeRemoved(gpGrantBatchListId, toBeRemoved);
	}
	
	public Integer countAllEntriesByBatchList(Long gpGrantBatchListId) throws Exception {
		return dao.countAllEntriesByBatchList(gpGrantBatchListId);
	}
	
	public Integer countAllEntriesByBatchListToBeRemoved(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
		return dao.countAllEntriesByBatchListToBeRemoved(gpGrantBatchListId, toBeRemoved);
	}
	
	public Double sumMandatoryLevyAllEntriesByBatchList(Long gpGrantBatchListId) throws Exception {
		return dao.sumMandatoryLevyAllEntriesByBatchList(gpGrantBatchListId);
	}
	
	public Double sumDiscretionaryLevyAllEntriesByBatchList(Long gpGrantBatchListId) throws Exception {
		return dao. sumDiscretionaryLevyAllEntriesByBatchList(gpGrantBatchListId);
	}
	
	public Double runningTotalForPaymentsDG(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
		return dao.runningTotalForPaymentsDG(gpGrantBatchListId, toBeRemoved);
	}
	
	public Double runningTotalForPaymentsDGWithRounding(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
		Double amount =  runningTotalForPaymentsDG(gpGrantBatchListId, toBeRemoved);
		if (amount != null && amount != 0.0) {
			return GenericUtility.roundToPrecision(amount,2);
		} else {
			return 0.0;
		}
	}
	
	public Double sumMandatoryLevyAllEntriesByBatchListToBeRemoved(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
		return dao.sumMandatoryLevyAllEntriesByBatchListToBeRemoved(gpGrantBatchListId, toBeRemoved);
	}
	
	public Double sumMandatoryLevyAllEntriesByBatchListToBeRemovedWithRounding(Long gpGrantBatchListId, Boolean toBeRemoved) throws Exception {
		Double amount = sumMandatoryLevyAllEntriesByBatchListToBeRemoved(gpGrantBatchListId, toBeRemoved);
		if (amount != null && amount != 0.0) {
			return GenericUtility.roundToPrecision(amount,2);
		} else {
			return 0.0;
		}
	}	
	
	public Double sumMandatoryLevyAllEntriesByBatchListToBeRemovedAndType(Long gpGrantBatchListId, Boolean toBeRemoved, GpDocumentType gpDocumentType) throws Exception {
		return dao.sumMandatoryLevyAllEntriesByBatchListToBeRemovedAndType(gpGrantBatchListId, toBeRemoved, gpDocumentType);
	}
	
	public Double sumMandatoryLevyAllEntriesByBatchListToBeRemovedAndTypeWithRounding(Long gpGrantBatchListId, Boolean toBeRemoved, GpDocumentType gpDocumentType) throws Exception {
		Double amount = sumMandatoryLevyAllEntriesByBatchListToBeRemovedAndType(gpGrantBatchListId, toBeRemoved, gpDocumentType);
		if (amount != null && amount != 0.0) {
			return GenericUtility.roundToPrecision(amount,2);
		} else {
			return 0.0;
		}
	}
	
	public List<GpGrantBatchEntry> populateAdditionalInformationList(List<GpGrantBatchEntry> gpGrantBatchEntryList) throws Exception{
		for (GpGrantBatchEntry gpGrantBatchEntry : gpGrantBatchEntryList) {
			populateAdditionalInformation(gpGrantBatchEntry);
		}
		return gpGrantBatchEntryList;
	}
	
	public GpGrantBatchEntry populateAdditionalInformation(GpGrantBatchEntry gpGrantBatchEntry) throws Exception{
		if (gpGrantBatchEntry.getId() != null) {
			if (gpGrantBatchEntry.getLevyNumber() != null && !gpGrantBatchEntry.getLevyNumber().isEmpty()) {
				
				// check if on sars employer
				List<SarsEmployerDetail> sarsEmployerDetail = sarsEmployerDetailService.findByRefNumberReturnOneResult(gpGrantBatchEntry.getLevyNumber());
				if (sarsEmployerDetail.size() != 0) {
					SarsEmployerDetail entryFound = sarsEmployerDetail.get(0);
					if (entryFound != null) {
						gpGrantBatchEntry.setOnSarsEmployerRecord(true);
						if (entryFound.getTradingStatus() != null && !entryFound.getTradingStatus().isEmpty()) {
							gpGrantBatchEntry.setEmployerRecordsStatus(entryFound.getTradingStatus());
						}else {
							gpGrantBatchEntry.setEmployerRecordsStatus("Unable to locate Status");
						}
					} else {
						gpGrantBatchEntry.setOnSarsEmployerRecord(false);
						gpGrantBatchEntry.setEmployerRecordsStatus("Not on SARS Employer Record");
					}
				} else {
					gpGrantBatchEntry.setOnSarsEmployerRecord(false);
					gpGrantBatchEntry.setEmployerRecordsStatus("Not on SARS Employer Record");
				}
				
				// check if on NSDMS
				List<Company> companyList = companyService.findByLevyNumberForStatusCheck(gpGrantBatchEntry.getLevyNumber());
				if (companyList.size() != 0) {
					Company company = companyList.get(0);
					if (company != null) {
						gpGrantBatchEntry.setOnNsdmsDatabase(true);
						if (company.getCompanyStatus() != null) {
							gpGrantBatchEntry.setNsdmdStatus(company.getCompanyStatus().getFriendlyName());
						}else {
							gpGrantBatchEntry.setNsdmdStatus("Error Locating Status on NSDMS Employer Database");	
						}
					} else {
						gpGrantBatchEntry.setOnNsdmsDatabase(false);
						gpGrantBatchEntry.setNsdmdStatus("Not on NSDMS Employer Database");
					}
				} else {
					gpGrantBatchEntry.setOnNsdmsDatabase(false);
					gpGrantBatchEntry.setNsdmdStatus("Not on NSDMS Employer Database");
				}
			}
			
		}
		return gpGrantBatchEntry;
	}
	
}