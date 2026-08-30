package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyDTTCTradeDAO;
import haj.com.entity.lookup.LegacyDTTCTrade;
import haj.com.entity.lookup.LegacyExperiential;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyDTTCTradeService extends AbstractService {
	/** The dao. */
	private LegacyDTTCTradeDAO dao = new LegacyDTTCTradeDAO();
	
	private QualificationService qualificationService = new QualificationService();

	/**
	 * Get all LegacyDTTCTrade
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCTrade
	 * @return a list of {@link haj.com.entity.LegacyDTTCTrade}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyDTTCTrade> allLegacyDTTCTrade() throws Exception {
		return dao.allLegacyDTTCTrade();
	}

	/**
	 * Create or update LegacyDTTCTrade.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCTrade
	 */
	public void create(LegacyDTTCTrade entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyDTTCTrade.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCTrade
	 */
	public void update(LegacyDTTCTrade entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyDTTCTrade.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCTrade
	 */
	public void delete(LegacyDTTCTrade entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyDTTCTrade}
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCTrade
	 */
	public LegacyDTTCTrade findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyDTTCTrade by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyDTTCTrade}
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCTrade
	 */
	public List<LegacyDTTCTrade> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyDTTCTrade
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyDTTCTrade}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyDTTCTrade> allLegacyDTTCTrade(int first, int pageSize) throws Exception {
		return dao.allLegacyDTTCTrade(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyDTTCTrade for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyDTTCTrade
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyDTTCTrade.class);
	}

	/**
	 * Lazy load LegacyDTTCTrade with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyDTTCTrade class
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
	 * @return a list of {@link haj.com.entity.LegacyDTTCTrade} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCTrade> allLegacyDTTCTrade(Class<LegacyDTTCTrade> class1, int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyDTTCTrade>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyDTTCTrade for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyDTTCTrade class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyDTTCTrade entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyDTTCTrade> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyDTTCTrade> allEntries = allLegacyDTTCTrade();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyDTTCTrade> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacyDTTCTradeNotProcessed() throws Exception {
		return dao.countAllLegacyDTTCTradeNotProcessed();
	}

	public List<LegacyDTTCTrade> allLegacyDTTCTradeNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyDTTCTradeNotProcessed(numberOfEntries);
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
			logger.info("validiationRun() Started");
			Integer count = countAllLegacyDTTCTradeNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyDTTCTrade> li = allLegacyDTTCTradeNotProcessed(1000);
				for (LegacyDTTCTrade legacyDTTCTrade : li) {
					// On SAQA Table
					if (legacyDTTCTrade.getSaqaID() != null && !legacyDTTCTrade.getSaqaID().isEmpty()) {
						try {
							Qualification qua = qualificationService.findByCodeString(legacyDTTCTrade.getSaqaID().trim());
							if (qua != null && qua.getId() != null) {
								legacyDTTCTrade.setQualification(qua);
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}					
					legacyDTTCTrade.setProcessed(true);

					updateList.add(legacyDTTCTrade);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyDTTCTradeNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy DTTC Trade Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy DTTC Trade Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
}
