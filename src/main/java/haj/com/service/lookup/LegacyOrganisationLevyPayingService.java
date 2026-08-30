package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.LegacyOrganisationLevyPayingDAO;
import haj.com.entity.lookup.LegacyOrganisationLevyPaying;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.SarsEmployerDetailService;

public class LegacyOrganisationLevyPayingService extends AbstractService {
	/** The dao. */
	private LegacyOrganisationLevyPayingDAO dao = new LegacyOrganisationLevyPayingDAO();
	private SarsEmployerDetailService sarsEmployerDetailService = new SarsEmployerDetailService();

	/**
	 * Get all LegacyOrganisationLevyPaying
	 * 
	 * @author TechFinium
	 * @see LegacyOrganisationLevyPaying
	 * @return a list of {@link haj.com.entity.LegacyOrganisationLevyPaying}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyOrganisationLevyPaying> allLegacyOrganisationLevyPaying() throws Exception {
		return dao.allLegacyOrganisationLevyPaying();
	}
	
	public List<LegacyOrganisationLevyPaying> allLegacyOrganisationLevyPayingWithLimit(int numberOfEntries) throws Exception {
		return dao.allLegacyOrganisationLevyPayingWithLimit(numberOfEntries);
	}

	/**
	 * Create or update LegacyOrganisationLevyPaying.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyOrganisationLevyPaying
	 */
	public void create(LegacyOrganisationLevyPaying entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyOrganisationLevyPaying.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyOrganisationLevyPaying
	 */
	public void update(LegacyOrganisationLevyPaying entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyOrganisationLevyPaying.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyOrganisationLevyPaying
	 */
	public void delete(LegacyOrganisationLevyPaying entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyOrganisationLevyPaying}
	 * @throws Exception
	 *             the exception
	 * @see LegacyOrganisationLevyPaying
	 */
	public LegacyOrganisationLevyPaying findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyOrganisationLevyPaying by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyOrganisationLevyPaying}
	 * @throws Exception
	 *             the exception
	 * @see LegacyOrganisationLevyPaying
	 */
	public List<LegacyOrganisationLevyPaying> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyOrganisationLevyPaying
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyOrganisationLevyPaying}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyOrganisationLevyPaying> allLegacyOrganisationLevyPaying(int first, int pageSize)
			throws Exception {
		return dao.allLegacyOrganisationLevyPaying(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyOrganisationLevyPaying for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyOrganisationLevyPaying
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyOrganisationLevyPaying.class);
	}

	/**
	 * Lazy load LegacyOrganisationLevyPaying with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyOrganisationLevyPaying class
	 * @param first
	 *            the first
	 * @param pageSize
	 *            the page size
	 * @param sortField
	 *            the sort field
	 * @param sortOrder
	 *            the sort order
	 * @param filters
	 *            the filters
	 * @return a list of {@link haj.com.entity.LegacyOrganisationLevyPaying}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationLevyPaying> allLegacyOrganisationLevyPaying(
			Class<LegacyOrganisationLevyPaying> class1, int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) throws Exception {
		return (List<LegacyOrganisationLevyPaying>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyOrganisationLevyPaying for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyOrganisationLevyPaying class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyOrganisationLevyPaying entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyOrganisationLevyPaying> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/* sdlNumberOnSarsEmployerFile */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationLevyPaying> allSdlNumberOnSarsEmployerFileEmpty(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from LegacyOrganisationLevyPaying o where o.sdlNumberOnSarsEmployerFile is null";
		return (List<LegacyOrganisationLevyPaying>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	/* sdlNumberOnSarsEmployerFile */
	public int countAllSdlNumberOnSarsEmployerFileEmpty(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationLevyPaying o where o.sdlNumberOnSarsEmployerFile is null";
		return dao.countWhere(filters, hql);
	}

	/* sdlNumberOnSarsEmployerFile */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationLevyPaying> allSdlNumberOnSarsEmployerFileByValue(int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyOrganisationLevyPaying o where o.sdlNumberOnSarsEmployerFile = :value";
		filters.put("value", value);
		return (List<LegacyOrganisationLevyPaying>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder,
				filters, hql);
	}

	/* sdlNumberOnSarsEmployerFile */
	public int countAllSdlNumberOnSarsEmployerFileByValue(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationLevyPaying o where o.sdlNumberOnSarsEmployerFile = :value";
		return dao.countWhere(filters, hql);
	}

	/* mainSdlNumberOnSarsEmployerFile */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationLevyPaying> allMainSdlNumberOnSarsEmployerFileEmpty(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from LegacyOrganisationLevyPaying o where o.mainSdlNumberOnSarsEmployerFile is null";
		return (List<LegacyOrganisationLevyPaying>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	/* mainSdlNumberOnSarsEmployerFile */
	public int countAllMainSdlNumberOnSarsEmployerFileEmpty(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationLevyPaying o where o.mainSdlNumberOnSarsEmployerFile is null";
		return dao.countWhere(filters, hql);
	}

	/* mainSdlNumberOnSarsEmployerFile */
	@SuppressWarnings("unchecked")
	public List<LegacyOrganisationLevyPaying> allMainSdlNumberOnSarsEmployerFileByValue(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyOrganisationLevyPaying o where o.mainSdlNumberOnSarsEmployerFile = :value";
		filters.put("value", value);
		return (List<LegacyOrganisationLevyPaying>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	/* mainSdlNumberOnSarsEmployerFile */
	public int countAllMainSdlNumberOnSarsEmployerFileByValue(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from LegacyOrganisationLevyPaying o where o.mainSdlNumberOnSarsEmployerFile = :value";
		return dao.countWhere(filters, hql);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyOrganisationLevyPaying> allEntries = allLegacyOrganisationLevyPaying();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyOrganisationLevyPaying> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public void checkSdlNumberAgaintSarsEmployerProfile() throws Exception {
		List<IDataEntity> updateList = new ArrayList<>();
		List<LegacyOrganisationLevyPaying> dataList = allLegacyOrganisationLevyPaying();
		for (LegacyOrganisationLevyPaying entry : dataList) {
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
