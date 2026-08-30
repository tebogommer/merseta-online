package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyUnitStandardAssessmentDAO;
import haj.com.entity.lookup.LegacyApprenticeshipTradeTest;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacyUnitStandard;
import haj.com.entity.lookup.LegacyUnitStandardAssessment;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.UnitStandardsService;
import haj.com.utils.GenericUtility;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class LegacyUnitStandardAssessmentService extends AbstractService {
	/** The dao. */
	private LegacyUnitStandardAssessmentDAO dao = new LegacyUnitStandardAssessmentDAO();
	
	private UnitStandardsService unitStandardsService;
	private LegacyUnitStandardService legacyUnitStandardService;
	private HomeAffairsService homeAffairsService = new HomeAffairsService();

	/**
	 * Get all LegacyUnitStandardAssessment
	 * 
	 * @author TechFinium
	 * @see LegacyUnitStandardAssessment
	 * @return a list of {@link haj.com.entity.LegacyUnitStandardAssessment}
	 * @throws Exception the exception
	 */
	public List<LegacyUnitStandardAssessment> allLegacyUnitStandardAssessment() throws Exception {
		return dao.allLegacyUnitStandardAssessment();
	}

	/**
	 * Create or update LegacyUnitStandardAssessment.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyUnitStandardAssessment
	 */
	public void create(LegacyUnitStandardAssessment entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyUnitStandardAssessment.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyUnitStandardAssessment
	 */
	public void update(LegacyUnitStandardAssessment entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyUnitStandardAssessment.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyUnitStandardAssessment
	 */
	public void delete(LegacyUnitStandardAssessment entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyUnitStandardAssessment}
	 * @throws Exception the exception
	 * @see LegacyUnitStandardAssessment
	 */
	public LegacyUnitStandardAssessment findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyUnitStandardAssessment by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyUnitStandardAssessment}
	 * @throws Exception the exception
	 * @see LegacyUnitStandardAssessment
	 */
	public List<LegacyUnitStandardAssessment> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyUnitStandardAssessment
	 * 
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyUnitStandardAssessment}
	 * @throws Exception the exception
	 */
	public List<LegacyUnitStandardAssessment> allLegacyUnitStandardAssessment(int first, int pageSize)
			throws Exception {
		return dao.allLegacyUnitStandardAssessment(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyUnitStandardAssessment for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyUnitStandardAssessment
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyUnitStandardAssessment.class);
	}

	/**
	 * Lazy load LegacyUnitStandardAssessment with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1    LegacyUnitStandardAssessment class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.LegacyUnitStandardAssessment}
	 *         containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandardAssessment> allLegacyUnitStandardAssessment(
			Class<LegacyUnitStandardAssessment> class1, int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) throws Exception {
		return (List<LegacyUnitStandardAssessment>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyUnitStandardAssessment for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity  LegacyUnitStandardAssessment class
	 * @param filters the filters
	 * @return Number of rows in the LegacyUnitStandardAssessment entity
	 * @throws Exception the exception
	 */
	public int count(Class<LegacyUnitStandardAssessment> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyUnitStandardAssessment> allEntries = allLegacyUnitStandardAssessment();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyUnitStandardAssessment> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public Integer countAllLegacyUnitStandardAssessmentNotProcessed() throws Exception {
		return dao.countAllLegacyUnitStandardAssessmentNotProcessed();
	}

	public List<LegacyUnitStandardAssessment> allLegacyUnitStandardAssessmentNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyUnitStandardAssessmentNotProcessed(numberOfEntries);
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
			if (legacyUnitStandardService == null) {
				legacyUnitStandardService = new LegacyUnitStandardService();
			}
			logger.info("validiationRun() Started");
			Integer count = countAllLegacyUnitStandardAssessmentNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyUnitStandardAssessment> li = allLegacyUnitStandardAssessmentNotProcessed(1000);
				for (LegacyUnitStandardAssessment legacyUnitStandardAssessment : li) {
					// ID Validation
					if (legacyUnitStandardAssessment.getIdNo() != null && !legacyUnitStandardAssessment.getIdNo().trim().isEmpty()) {
						try {
							legacyUnitStandardAssessment.setValidRsaIdNumber(GenericUtility.checkRsaId(legacyUnitStandardAssessment.getIdNo().trim()));
						} catch (Exception e) {
							legacyUnitStandardAssessment.setValidRsaIdNumber(false);
						}
					}
					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService.findByDhaIdNumber(legacyUnitStandardAssessment.getAssessorID().trim());
						if (entriesFound > 0) {
							legacyUnitStandardAssessment.setAppearsOnHomeAffairsData(true);
						} else {
							legacyUnitStandardAssessment.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						legacyUnitStandardAssessment.setAppearsOnHomeAffairsData(false);
					}
					// links unit standard
					if (legacyUnitStandardAssessment.getUnitStdCode() != null && !legacyUnitStandardAssessment.getUnitStdCode().isEmpty()) {
						try {
							Integer code = Integer.valueOf(legacyUnitStandardAssessment.getUnitStdCode().trim());
							UnitStandards unitStandard = unitStandardsService.findByUnitStandartId(code);
							if (unitStandard != null && unitStandard.getId() != null) {
								legacyUnitStandardAssessment.setUnitStandard(unitStandard);
							}
						} catch (Exception e) {
						}
					}
					// links to legacy unit standard
					try {
						if ((legacyUnitStandardAssessment.getIdNo() != null && legacyUnitStandardAssessment.getIdNo().isEmpty()) && (legacyUnitStandardAssessment.getUnitStdCode() != null && !legacyUnitStandardAssessment.getUnitStdCode().isEmpty())) {
							List<LegacyUnitStandard> list = legacyUnitStandardService.findByIdNumberAndCode(legacyUnitStandardAssessment.getIdNo().trim(), legacyUnitStandardAssessment.getUnitStdCode().trim());
							if (list != null && list.size() != 0) {
								LegacyUnitStandard legacyUnitStandard = list.get(0);
								if (legacyUnitStandard != null && legacyUnitStandard.getId() != null) {
									legacyUnitStandardAssessment.setLegacyUnitStandardFlatId(legacyUnitStandard.getId());
								}
							}
						}
					} catch (Exception e) {
					}
					
					
					
					// legacyUnitStandardAssessment.setProcessed(true);
					updateList.add(legacyUnitStandardAssessment);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyUnitStandardAssessmentNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Unit Standard Assessment Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Unit Standard Assessment Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	public List<LegacyUnitStandardAssessment> findByLegacyUnitStandard(LegacyUnitStandard legacyUnitStandard) {
		List<LegacyUnitStandardAssessment> list = new  ArrayList<>();
		if(legacyUnitStandard.getUnitStandard() != null) {
			list = dao.findByIdNo(legacyUnitStandard.getIdNo(), legacyUnitStandard.getUnitStandard().getId());
		}
		return list;
	}

}
