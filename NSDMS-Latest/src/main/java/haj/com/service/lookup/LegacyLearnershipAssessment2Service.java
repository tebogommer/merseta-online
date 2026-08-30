package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyLearnershipAssessment2DAO;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.LegacyLearnershipAssessment2;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.UnitStandardsService;
import haj.com.utils.GenericUtility;

public class LegacyLearnershipAssessment2Service extends AbstractService {
	/** The dao. */
	private LegacyLearnershipAssessment2DAO dao = new LegacyLearnershipAssessment2DAO();
	
	private UnitStandardsService unitStandardsService;

	/**
	 * Get all LegacyLearnershipAssessment2
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment2
	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment2}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyLearnershipAssessment2> allLegacyLearnershipAssessment2() throws Exception {
		return dao.allLegacyLearnershipAssessment2();
	}

	/**
	 * Create or update LegacyLearnershipAssessment2.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment2
	 */
	public void create(LegacyLearnershipAssessment2 entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyLearnershipAssessment2.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment2
	 */
	public void update(LegacyLearnershipAssessment2 entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyLearnershipAssessment2.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment2
	 */
	public void delete(LegacyLearnershipAssessment2 entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyLearnershipAssessment2}
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment2
	 */
	public LegacyLearnershipAssessment2 findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyLearnershipAssessment2 by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment2}
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment2
	 */
	public List<LegacyLearnershipAssessment2> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyLearnershipAssessment2
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment2}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyLearnershipAssessment2> allLegacyLearnershipAssessment2(int first, int pageSize)
			throws Exception {
		return dao.allLegacyLearnershipAssessment2(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyLearnershipAssessment2 for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyLearnershipAssessment2
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyLearnershipAssessment2.class);
	}

	/**
	 * Lazy load LegacyLearnershipAssessment2 with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyLearnershipAssessment2 class
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
	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment2}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyLearnershipAssessment2> allLegacyLearnershipAssessment2(
			Class<LegacyLearnershipAssessment2> class1, int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) throws Exception {
		return (List<LegacyLearnershipAssessment2>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyLearnershipAssessment2 for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyLearnershipAssessment2 class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyLearnershipAssessment2 entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyLearnershipAssessment2> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyLearnershipAssessment2> allEntries = allLegacyLearnershipAssessment2();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyLearnershipAssessment2> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public Integer countAllLegacyLearnershipAssessment2NotProcessed() throws Exception {
		return dao.countAllLegacyLearnershipAssessment2NotProcessed();
	}

	public List<LegacyLearnershipAssessment2> allLegacyLearnershipAssessment2NotProcessed(int numberOfEntries)
			throws Exception {
		return dao.allLegacyLearnershipAssessment2NotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyLearnershipAssessment2NotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyLearnershipAssessment2> li = allLegacyLearnershipAssessment2NotProcessed(1000);
				for (LegacyLearnershipAssessment2 legacyLearnershipAssessment2 : li) {
					// ID Validation
					if (legacyLearnershipAssessment2.getLearnerId() != null
							&& !legacyLearnershipAssessment2.getLearnerId().trim().isEmpty()) {
						try {
							legacyLearnershipAssessment2.setValidRsaIdNumber(
									GenericUtility.checkRsaId(legacyLearnershipAssessment2.getLearnerId().trim()));
						} catch (Exception e) {
							legacyLearnershipAssessment2.setValidRsaIdNumber(false);
						}
					}
					// Unit Standard
					if (legacyLearnershipAssessment2.getUnitStdCode() != null
							&& !legacyLearnershipAssessment2.getUnitStdCode().isEmpty()) {
						try {
							Integer code = Integer.valueOf(legacyLearnershipAssessment2.getUnitStdCode().trim());
							UnitStandards unitStandard = unitStandardsService.findByUnitStandartId(code);
							if (unitStandard != null && unitStandard.getId() != null) {
								legacyLearnershipAssessment2.setUnitStandard(unitStandard);
							}
						} catch (Exception e) {
						}
					}
					legacyLearnershipAssessment2.setProcessed(true);
					updateList.add(legacyLearnershipAssessment2);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyLearnershipAssessment2NotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Learnership Assessment 2 Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Learnership Assessment 2 Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
}
