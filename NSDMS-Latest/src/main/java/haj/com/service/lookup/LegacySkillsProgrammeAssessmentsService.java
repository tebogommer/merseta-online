package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacySkillsProgrammeAssessmentsDAO;
import haj.com.entity.lookup.LegacyApprenticeshipTradeTest;
import haj.com.entity.lookup.LegacySkillsProgramme;
import haj.com.entity.lookup.LegacySkillsProgrammeAssessments;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.UnitStandardsService;
import haj.com.utils.GenericUtility;

public class LegacySkillsProgrammeAssessmentsService extends AbstractService {
	/** The dao. */
	private LegacySkillsProgrammeAssessmentsDAO dao = new LegacySkillsProgrammeAssessmentsDAO();

	private HomeAffairsService homeAffairsService = new HomeAffairsService();
	private UnitStandardsService unitStandardsService = new UnitStandardsService();

	/**
	 * Get all LegacySkillsProgrammeAssessments
	 * 
	 * @author TechFinium
	 * @see LegacySkillsProgrammeAssessments
	 * @return a list of {@link haj.com.entity.LegacySkillsProgrammeAssessments}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacySkillsProgrammeAssessments> allLegacySkillsProgrammeAssessments() throws Exception {
		return dao.allLegacySkillsProgrammeAssessments();
	}

	/**
	 * Create or update LegacySkillsProgrammeAssessments.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacySkillsProgrammeAssessments
	 */
	public void create(LegacySkillsProgrammeAssessments entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update LegacySkillsProgrammeAssessments.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacySkillsProgrammeAssessments
	 */
	public void update(LegacySkillsProgrammeAssessments entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacySkillsProgrammeAssessments.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacySkillsProgrammeAssessments
	 */
	public void delete(LegacySkillsProgrammeAssessments entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacySkillsProgrammeAssessments}
	 * @throws Exception
	 *             the exception
	 * @see LegacySkillsProgrammeAssessments
	 */
	public LegacySkillsProgrammeAssessments findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacySkillsProgrammeAssessments by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacySkillsProgrammeAssessments}
	 * @throws Exception
	 *             the exception
	 * @see LegacySkillsProgrammeAssessments
	 */
	public List<LegacySkillsProgrammeAssessments> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacySkillsProgrammeAssessments
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacySkillsProgrammeAssessments}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacySkillsProgrammeAssessments> allLegacySkillsProgrammeAssessments(int first, int pageSize) throws Exception {
		return dao.allLegacySkillsProgrammeAssessments(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacySkillsProgrammeAssessments for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacySkillsProgrammeAssessments
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacySkillsProgrammeAssessments.class);
	}

	/**
	 * Lazy load LegacySkillsProgrammeAssessments with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacySkillsProgrammeAssessments class
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
	 * @return a list of {@link haj.com.entity.LegacySkillsProgrammeAssessments}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacySkillsProgrammeAssessments> allLegacySkillsProgrammeAssessments(Class<LegacySkillsProgrammeAssessments> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacySkillsProgrammeAssessments>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacySkillsProgrammeAssessments for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacySkillsProgrammeAssessments class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacySkillsProgrammeAssessments entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacySkillsProgrammeAssessments> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacySkillsProgrammeAssessments> allEntries = allLegacySkillsProgrammeAssessments();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacySkillsProgrammeAssessments> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacySkillsProgrammeAssessmentsNotProcessed() throws Exception {
		return dao.countAllLegacySkillsProgrammeAssessmentsNotProcessed();
	}

	public List<LegacySkillsProgrammeAssessments> allLegacySkillsProgrammeAssessmentsNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacySkillsProgrammeAssessmentsNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacySkillsProgrammeAssessmentsNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacySkillsProgrammeAssessments> li = allLegacySkillsProgrammeAssessmentsNotProcessed(1000);
				for (LegacySkillsProgrammeAssessments legacySkillsProgrammeAssessments : li) {

					// ID Validation
					if (legacySkillsProgrammeAssessments.getIdNo() != null && !legacySkillsProgrammeAssessments.getIdNo().trim().isEmpty()) {
						try {
							legacySkillsProgrammeAssessments.setValidRsaIdNumber(GenericUtility.checkRsaId(legacySkillsProgrammeAssessments.getIdNo().trim()));
						} catch (Exception e) {
							legacySkillsProgrammeAssessments.setValidRsaIdNumber(false);
						}
					}

					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService.findByDhaIdNumber(legacySkillsProgrammeAssessments.getIdNo().trim());
						if (entriesFound > 0) {
							legacySkillsProgrammeAssessments.setAppearsOnHomeAffairsData(true);
						} else {
							legacySkillsProgrammeAssessments.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						legacySkillsProgrammeAssessments.setAppearsOnHomeAffairsData(false);
					}

					try {
						UnitStandards us = unitStandardsService.findByUnitStandartCodeString(legacySkillsProgrammeAssessments.getUnitStdCode().trim());
						if (us != null && us.getId() != null) {
							// legacySkillsProgrammeAssessments.setQualification(us.getQualification());
						}
					} catch (Exception e) {
					}

					// legacySkillsProgrammeAssessments.setProcessed(true);

					updateList.add(legacySkillsProgrammeAssessments);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacySkillsProgrammeAssessmentsNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Skills Programme Assessments Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Skills Programme Assessments Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	public List<LegacySkillsProgrammeAssessments> findByLegacySkillsProgramme( LegacySkillsProgramme legacySkillsProgramme) {
		List<LegacySkillsProgrammeAssessments> list = new  ArrayList<>();
		list = dao.findByIdNo(legacySkillsProgramme.getIdNo(), legacySkillsProgramme.getsProgrammeCode());
		return list;
	}

}
