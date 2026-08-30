package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyDTTCSkillsProgrammeDAO;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.entity.lookup.LegacyDTTCSkillsProgramme;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyDTTCSkillsProgrammeService extends AbstractService {
	/** The dao. */
	private LegacyDTTCSkillsProgrammeDAO dao = new LegacyDTTCSkillsProgrammeDAO();
	
	private SkillsProgramService skillsProgramService;

	private HomeAffairsService homeAffairsService = new HomeAffairsService();
	private QualificationService qualificationService = new QualificationService();

	/**
	 * Get all LegacyDTTCSkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacyDTTCSkillsProgramme
	 * @return a list of {@link haj.com.entity.LegacyDTTCSkillsProgramme}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyDTTCSkillsProgramme> allLegacyDTTCSkillsProgramme() throws Exception {
		return dao.allLegacyDTTCSkillsProgramme();
	}

	/**
	 * Create or update LegacyDTTCSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCSkillsProgramme
	 */
	public void create(LegacyDTTCSkillsProgramme entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyDTTCSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCSkillsProgramme
	 */
	public void update(LegacyDTTCSkillsProgramme entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyDTTCSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCSkillsProgramme
	 */
	public void delete(LegacyDTTCSkillsProgramme entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyDTTCSkillsProgramme}
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCSkillsProgramme
	 */
	public LegacyDTTCSkillsProgramme findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyDTTCSkillsProgramme by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyDTTCSkillsProgramme}
	 * @throws Exception
	 *             the exception
	 * @see LegacyDTTCSkillsProgramme
	 */
	public List<LegacyDTTCSkillsProgramme> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyDTTCSkillsProgramme
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyDTTCSkillsProgramme}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyDTTCSkillsProgramme> allLegacyDTTCSkillsProgramme(int first, int pageSize) throws Exception {
		return dao.allLegacyDTTCSkillsProgramme(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyDTTCSkillsProgramme for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyDTTCSkillsProgramme
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyDTTCSkillsProgramme.class);
	}

	/**
	 * Lazy load LegacyDTTCSkillsProgramme with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyDTTCSkillsProgramme class
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
	 * @return a list of {@link haj.com.entity.LegacyDTTCSkillsProgramme}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCSkillsProgramme> allLegacyDTTCSkillsProgramme(Class<LegacyDTTCSkillsProgramme> class1,
			int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
			throws Exception {
		return (List<LegacyDTTCSkillsProgramme>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyDTTCSkillsProgramme for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyDTTCSkillsProgramme class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyDTTCSkillsProgramme entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyDTTCSkillsProgramme> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyDTTCSkillsProgramme> allEntries = allLegacyDTTCSkillsProgramme();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyDTTCSkillsProgramme> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public Integer countAllLegacyDTTCSkillsProgrammeNotProcessed() throws Exception {
		return dao.countAllLegacyDTTCSkillsProgrammeNotProcessed();
	}

	public List<LegacyDTTCSkillsProgramme> allLegacyDTTCSkillsProgrammeNotProcessed(int numberOfEntries)
			throws Exception {
		return dao.allLegacyDTTCSkillsProgrammeNotProcessed(numberOfEntries);
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
			if (skillsProgramService == null) {
				skillsProgramService = new SkillsProgramService();
			}

			logger.info("validiationRun() Started");
			Integer count = countAllLegacyDTTCSkillsProgrammeNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyDTTCSkillsProgramme> li = allLegacyDTTCSkillsProgrammeNotProcessed(1000);
				for (LegacyDTTCSkillsProgramme legacyDTTCSkillsProgramme : li) {
					// On sProgrammeCode
					if (legacyDTTCSkillsProgramme.getSkillsProgrammeRegistrationNumber() != null
							&& !legacyDTTCSkillsProgramme.getSkillsProgrammeRegistrationNumber().isEmpty()) {
						try {
							SkillsProgram skillsProgram = skillsProgramService
									.findByProgrammeID(legacyDTTCSkillsProgramme.getSkillsProgrammeRegistrationNumber().trim());
							if (skillsProgram != null && skillsProgram.getId() != null) {
								legacyDTTCSkillsProgramme.setSkillsProgram(skillsProgram);
							}

						} catch (Exception e) {
						}
					}

					legacyDTTCSkillsProgramme.setProcessed(true);

					updateList.add(legacyDTTCSkillsProgramme);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyDTTCSkillsProgrammeNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy DTTC Skills Programme Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy DTTC Skills Programme Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
}
