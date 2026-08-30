package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;

import haj.com.dao.lookup.LegacyOrganisationNonLevyPayingDAO;
import haj.com.entity.lookup.LegacyOrganisationNonLevyPaying;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.SarsEmployerDetailService;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class LegacyOrganisationNonLevyPayingService extends AbstractService {
	/** The dao. */
	private LegacyOrganisationNonLevyPayingDAO dao = new LegacyOrganisationNonLevyPayingDAO();
	private SarsEmployerDetailService sarsEmployerDetailService = new SarsEmployerDetailService();

	/**
	 * Get all LegacyOrganisationNonLevyPaying
 	 * @author TechFinium 
 	 * @see   LegacyOrganisationNonLevyPaying
 	 * @return a list of {@link haj.com.entity.LegacyOrganisationNonLevyPaying}
	 * @throws Exception the exception
 	 */
	public List<LegacyOrganisationNonLevyPaying> allLegacyOrganisationNonLevyPaying() throws Exception {
	  	return dao.allLegacyOrganisationNonLevyPaying();
	}


	/**
	 * Create or update LegacyOrganisationNonLevyPaying.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LegacyOrganisationNonLevyPaying
	 */
	public void create(LegacyOrganisationNonLevyPaying entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LegacyOrganisationNonLevyPaying.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyOrganisationNonLevyPaying
	 */
	public void update(LegacyOrganisationNonLevyPaying entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LegacyOrganisationNonLevyPaying.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyOrganisationNonLevyPaying
	 */
	public void delete(LegacyOrganisationNonLevyPaying entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyOrganisationNonLevyPaying}
	 * @throws Exception the exception
	 * @see    LegacyOrganisationNonLevyPaying
	 */
	public LegacyOrganisationNonLevyPaying findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LegacyOrganisationNonLevyPaying by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyOrganisationNonLevyPaying}
	 * @throws Exception the exception
	 * @see    LegacyOrganisationNonLevyPaying
	 */
	public List<LegacyOrganisationNonLevyPaying> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LegacyOrganisationNonLevyPaying
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyOrganisationNonLevyPaying}
	 * @throws Exception the exception
	 */
	public List<LegacyOrganisationNonLevyPaying> allLegacyOrganisationNonLevyPaying(int first, int pageSize) throws Exception {
		return dao.allLegacyOrganisationNonLevyPaying(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LegacyOrganisationNonLevyPaying for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the LegacyOrganisationNonLevyPaying
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LegacyOrganisationNonLevyPaying.class);
	}
	
    /**
     * Lazy load LegacyOrganisationNonLevyPaying with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 LegacyOrganisationNonLevyPaying class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LegacyOrganisationNonLevyPaying} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationNonLevyPaying> allLegacyOrganisationNonLevyPaying(Class<LegacyOrganisationNonLevyPaying> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<LegacyOrganisationNonLevyPaying>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of LegacyOrganisationNonLevyPaying for lazy load with filters
     * @author TechFinium 
     * @param entity LegacyOrganisationNonLevyPaying class
     * @param filters the filters
     * @return Number of rows in the LegacyOrganisationNonLevyPaying entity
     * @throws Exception the exception     
     */	
	public int count(Class<LegacyOrganisationNonLevyPaying> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	/*sdlNumberOnSarsEmployerFile*/
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationNonLevyPaying> allSdlNumberOnSarsEmployerFileEmpty(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from LegacyOrganisationNonLevyPaying o where o.sdlNumberOnSarsEmployerFile is null";
		return (List<LegacyOrganisationNonLevyPaying>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	/*sdlNumberOnSarsEmployerFile*/
	public int countAllSdlNumberOnSarsEmployerFileEmpty( Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationNonLevyPaying o where o.sdlNumberOnSarsEmployerFile is null";
		return dao.countWhere(filters, hql);
	}
	
	/*sdlNumberOnSarsEmployerFile*/
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationNonLevyPaying> allSdlNumberOnSarsEmployerFileByValue(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyOrganisationNonLevyPaying o where o.sdlNumberOnSarsEmployerFile = :value";
		filters.put("value", value);
		return (List<LegacyOrganisationNonLevyPaying>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	/*sdlNumberOnSarsEmployerFile*/
	public int countAllSdlNumberOnSarsEmployerFileByValue(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationNonLevyPaying o where o.sdlNumberOnSarsEmployerFile = :value";
		return dao.countWhere(filters, hql);
	}
	
	/*mainSdlNumberOnSarsEmployerFile*/
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationNonLevyPaying> allMainSdlNumberOnSarsEmployerFileEmpty(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from LegacyOrganisationNonLevyPaying o where o.mainSdlNumberOnSarsEmployerFile is null";
		return (List<LegacyOrganisationNonLevyPaying>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	/*mainSdlNumberOnSarsEmployerFile*/
	public int countAllMainSdlNumberOnSarsEmployerFileEmpty( Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationNonLevyPaying o where o.mainSdlNumberOnSarsEmployerFile is null";
		return dao.countWhere(filters, hql);
	}
	
	/*mainSdlNumberOnSarsEmployerFile*/
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationNonLevyPaying> allMainSdlNumberOnSarsEmployerFileByValue(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyOrganisationNonLevyPaying o where o.mainSdlNumberOnSarsEmployerFile = :value";
		filters.put("value", value);
		return (List<LegacyOrganisationNonLevyPaying>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	/*mainSdlNumberOnSarsEmployerFile*/
	public int countAllMainSdlNumberOnSarsEmployerFileByValue(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationNonLevyPaying o where o.mainSdlNumberOnSarsEmployerFile = :value";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyOrganisationNonLevyPaying> allEntries = allLegacyOrganisationNonLevyPaying();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyOrganisationNonLevyPaying> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public void checkSdlNumberAgaintSarsEmployerProfile() throws Exception{
		List<IDataEntity> updateList = new ArrayList<>();
		List<LegacyOrganisationNonLevyPaying> dataList = allLegacyOrganisationNonLevyPaying();
		for (LegacyOrganisationNonLevyPaying entry : dataList) {
			boolean canUpdate = false;
			if (entry.getSdlNumber() != null && !entry.getSdlNumber().isEmpty()) {
				int count = sarsEmployerDetailService.countByRefNumber(entry.getSdlNumber());
				if (count != 0) {
					entry.setSdlNumberOnSarsEmployerFile(true);
				} else {
					entry.setSdlNumberOnSarsEmployerFile(false);
				}
				canUpdate = true;
			}
			if (entry.getMainSDL() != null && !entry.getMainSDL().isEmpty()) {
				int count = sarsEmployerDetailService.countByRefNumber(entry.getMainSDL());
				if (count != 0) {
					entry.setMainSdlNumberOnSarsEmployerFile(true);
				} else {
					entry.setMainSdlNumberOnSarsEmployerFile(false);
				}
				canUpdate = true;
			}
			if (canUpdate) {
				updateList.add(entry);
			}
		}
		dataList = null;
		if (updateList.size() != 0) {
			dao.updateBatch(updateList, 1000);
		}
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllSdlNumberOnSarsEmployerFileEmpty() throws Exception {
		return dao.countAllSdlNumberOnSarsEmployerFileEmpty();
	}

	public Integer countAllSdlNumberOnSarsEmployerFileByValue(Boolean value) throws Exception {
		return dao.countAllSdlNumberOnSarsEmployerFileByValue(value);
	}

	public Integer countAllMainSdlNumberOnSarsEmployerFileEmpty() throws Exception {
		return dao.countAllMainSdlNumberOnSarsEmployerFileEmpty();
	}

	public Integer countAllMainSdlNumberOnSarsEmployerFileValue(Boolean value) throws Exception {
		return dao.countAllMainSdlNumberOnSarsEmployerFileValue(value);
	}
}
