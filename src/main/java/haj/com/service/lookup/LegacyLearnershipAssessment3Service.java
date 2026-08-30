package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyLearnershipAssessment3DAO;
import haj.com.entity.lookup.LegacyLearnershipAssessment2;
import haj.com.entity.lookup.LegacyLearnershipAssessment3;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.UnitStandardsService;
import haj.com.utils.GenericUtility;

public class LegacyLearnershipAssessment3Service extends AbstractService {
	/** The dao. */
	private LegacyLearnershipAssessment3DAO dao = new LegacyLearnershipAssessment3DAO();

	private UnitStandardsService unitStandardsService;
	
	/**
	 * Get all LegacyLearnershipAssessment3
	 * 
	 * @author TechFinium
	 * @see LegacyLearnershipAssessment3
	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment3}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyLearnershipAssessment3> allLegacyLearnershipAssessment3() throws Exception {
		return dao.allLegacyLearnershipAssessment3();
	}

	/**
	 * Create or update LegacyLearnershipAssessment3.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment3
	 */
	public void create(LegacyLearnershipAssessment3 entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyLearnershipAssessment3.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment3
	 */
	public void update(LegacyLearnershipAssessment3 entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyLearnershipAssessment3.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment3
	 */
	public void delete(LegacyLearnershipAssessment3 entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyLearnershipAssessment3}
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment3
	 */
	public LegacyLearnershipAssessment3 findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyLearnershipAssessment3 by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment3}
	 * @throws Exception
	 *             the exception
	 * @see LegacyLearnershipAssessment3
	 */
	public List<LegacyLearnershipAssessment3> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyLearnershipAssessment3
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment3}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyLearnershipAssessment3> allLegacyLearnershipAssessment3(int first, int pageSize)
			throws Exception {
		return dao.allLegacyLearnershipAssessment3(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyLearnershipAssessment3 for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyLearnershipAssessment3
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyLearnershipAssessment3.class);
	}

	/**
	 * Lazy load LegacyLearnershipAssessment3 with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyLearnershipAssessment3 class
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
	 * @return a list of {@link haj.com.entity.LegacyLearnershipAssessment3}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyLearnershipAssessment3> allLegacyLearnershipAssessment3(
			Class<LegacyLearnershipAssessment3> class1, int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) throws Exception {
		return (List<LegacyLearnershipAssessment3>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyLearnershipAssessment3 for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyLearnershipAssessment3 class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyLearnershipAssessment3 entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyLearnershipAssessment3> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyLearnershipAssessment3> allEntries = allLegacyLearnershipAssessment3();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyLearnershipAssessment3> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public Integer countAllLegacyLearnershipAssessment3NotProcessed() throws Exception {
		return dao.countAllLegacyLearnershipAssessment3NotProcessed();
	}

	public List<LegacyLearnershipAssessment3> allLegacyLearnershipAssessment3NotProcessed(int numberOfEntries)
			throws Exception {
		return dao.allLegacyLearnershipAssessment3NotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyLearnershipAssessment3NotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyLearnershipAssessment3> li = allLegacyLearnershipAssessment3NotProcessed(1000);
				for (LegacyLearnershipAssessment3 legacyLearnershipAssessment3 : li) {
					// ID Validation
					if (legacyLearnershipAssessment3.getLearnerId() != null
							&& !legacyLearnershipAssessment3.getLearnerId().trim().isEmpty()) {
						try {
							legacyLearnershipAssessment3.setValidRsaIdNumber(
									GenericUtility.checkRsaId(legacyLearnershipAssessment3.getLearnerId().trim()));
						} catch (Exception e) {
							legacyLearnershipAssessment3.setValidRsaIdNumber(false);
						}
					}
					// Unit Standard
					if (legacyLearnershipAssessment3.getUnitStdCode() != null
							&& !legacyLearnershipAssessment3.getUnitStdCode().isEmpty()) {
						try {
							Integer code = Integer.valueOf(legacyLearnershipAssessment3.getUnitStdCode().trim());
							UnitStandards unitStandard = unitStandardsService.findByUnitStandartId(code);
							if (unitStandard != null && unitStandard.getId() != null) {
								legacyLearnershipAssessment3.setUnitStandard(unitStandard);
							}
						} catch (Exception e) {
						}
					}
					legacyLearnershipAssessment3.setProcessed(true);
					updateList.add(legacyLearnershipAssessment3);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyLearnershipAssessment3NotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Learnership Assessment 3 Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
			logger.info("validateRsaIdNumbers() Complete");
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Learnership Assessment 3 Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
}
