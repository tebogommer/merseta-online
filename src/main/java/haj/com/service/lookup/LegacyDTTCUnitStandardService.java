package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyDTTCUnitStandardDAO;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.LegacyDTTCUnitStandard;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.UnitStandardsService;
import haj.com.utils.GenericUtility;

public class LegacyDTTCUnitStandardService extends AbstractService {
	/** The dao. */
	private LegacyDTTCUnitStandardDAO dao = new LegacyDTTCUnitStandardDAO();

	private UnitStandardsService unitStandardsService;
	
	/**
	 * Get all LegacyDTTCUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCUnitStandard
	 * @return a list of {@link haj.com.entity.LegacyDTTCUnitStandard}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyDTTCUnitStandard> allLegacyDTTCUnitStandard() throws Exception {
		return dao.allLegacyDTTCUnitStandard();
	}

	/**
	 * Create or update LegacyDTTCUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCUnitStandard
	 */
	public void create(LegacyDTTCUnitStandard entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyDTTCUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCUnitStandard
	 */
	public void update(LegacyDTTCUnitStandard entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyDTTCUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCUnitStandard
	 */
	public void delete(LegacyDTTCUnitStandard entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyDTTCUnitStandard}
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCUnitStandard
	 */
	public LegacyDTTCUnitStandard findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyDTTCUnitStandard by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyDTTCUnitStandard}
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCUnitStandard
	 */
	public List<LegacyDTTCUnitStandard> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyDTTCUnitStandard
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyDTTCUnitStandard}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyDTTCUnitStandard> allLegacyDTTCUnitStandard(int first, int pageSize) throws Exception {
		return dao.allLegacyDTTCUnitStandard(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyDTTCUnitStandard for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyDTTCUnitStandard
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyDTTCUnitStandard.class);
	}

	/**
	 * Lazy load LegacyDTTCUnitStandard with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyDTTCUnitStandard class
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
	 * @return a list of {@link haj.com.entity.LegacyDTTCUnitStandard}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCUnitStandard> allLegacyDTTCUnitStandard(Class<LegacyDTTCUnitStandard> class1, int first,
			int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyDTTCUnitStandard>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyDTTCUnitStandard for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyDTTCUnitStandard class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyDTTCUnitStandard entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyDTTCUnitStandard> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyDTTCUnitStandard> allEntries = allLegacyDTTCUnitStandard();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyDTTCUnitStandard> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public Integer countAllLegacyDTTCUnitStandardNotProcessed() throws Exception {
		return dao.countAllLegacyDTTCUnitStandardNotProcessed();
	}

	public List<LegacyDTTCUnitStandard> allLegacyDTTCUnitStandardNotProcessed(int numberOfEntries)
			throws Exception {
		return dao.allLegacyDTTCUnitStandardNotProcessed(numberOfEntries);
	}

	public void validateRsaIdNumbers() throws Exception {
		new Thread(new Runnable() {
			@Override
			public void run() {
				validiationRun();
			}
		}).start();
	}

	public void validiationRun() {
		try {

			if (unitStandardsService == null) {
				unitStandardsService = new UnitStandardsService();
			}

			logger.info("validiationRun() Started");
			Integer count = countAllLegacyDTTCUnitStandardNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyDTTCUnitStandard> li = allLegacyDTTCUnitStandardNotProcessed(1000);
				for (LegacyDTTCUnitStandard legacyDTTCUnitStandard : li) {
					
					if (legacyDTTCUnitStandard.getSaqaUsId() != null
							&& !legacyDTTCUnitStandard.getSaqaUsId().isEmpty()) {
						try {
							Integer code = Integer.valueOf(legacyDTTCUnitStandard.getSaqaUsId().trim());
							UnitStandards unitStandard = unitStandardsService.findByUnitStandartId(code);
							if (unitStandard != null && unitStandard.getId() != null) {
								legacyDTTCUnitStandard.setUnitStandard(unitStandard);
							}
						} catch (Exception e) {
						}
					}
					legacyDTTCUnitStandard.setProcessed(true);
					updateList.add(legacyDTTCUnitStandard);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyDTTCUnitStandardNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy DTTC Unit Standard Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy DTTC Unit Standard Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
}
