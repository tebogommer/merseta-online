package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyAssessorSkillsProgrammeDAO;
import haj.com.entity.lookup.LegacyAssessorLearnership;
import haj.com.entity.lookup.LegacyAssessorQualification;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacyExperiential;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

import java.util.Map;
import org.primefaces.model.SortOrder;

public class LegacyAssessorSkillsProgrammeService extends AbstractService {
	/** The dao. */
	private LegacyAssessorSkillsProgrammeDAO dao = new LegacyAssessorSkillsProgrammeDAO();

	/* Service levels */
	private SkillsProgramService skillsProgramService;

	private HomeAffairsService homeAffairsService = new HomeAffairsService();
	private QualificationService qualificationService = new QualificationService();

	/**
	 * Get all LegacyAssessorSkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorSkillsProgramme
	 * @return a list of {@link haj.com.entity.LegacyAssessorSkillsProgramme}
	 * @throws Exception the exception
	 */
	public List<LegacyAssessorSkillsProgramme> allLegacyAssessorSkillsProgramme() throws Exception {
		return dao.allLegacyAssessorSkillsProgramme();
	}

	/**
	 * Create or update LegacyAssessorSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyAssessorSkillsProgramme
	 */
	public void create(LegacyAssessorSkillsProgramme entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyAssessorSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyAssessorSkillsProgramme
	 */
	public void update(LegacyAssessorSkillsProgramme entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyAssessorSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyAssessorSkillsProgramme
	 */
	public void delete(LegacyAssessorSkillsProgramme entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyAssessorSkillsProgramme}
	 * @throws Exception the exception
	 * @see LegacyAssessorSkillsProgramme
	 */
	public LegacyAssessorSkillsProgramme findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyAssessorSkillsProgramme by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyAssessorSkillsProgramme}
	 * @throws Exception the exception
	 * @see LegacyAssessorSkillsProgramme
	 */
	public List<LegacyAssessorSkillsProgramme> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyAssessorSkillsProgramme
	 * 
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyAssessorSkillsProgramme}
	 * @throws Exception the exception
	 */
	public List<LegacyAssessorSkillsProgramme> allLegacyAssessorSkillsProgramme(int first, int pageSize)
			throws Exception {
		return dao.allLegacyAssessorSkillsProgramme(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyAssessorSkillsProgramme for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyAssessorSkillsProgramme
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyAssessorSkillsProgramme.class);
	}

	/**
	 * Lazy load LegacyAssessorSkillsProgramme with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1    LegacyAssessorSkillsProgramme class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.LegacyAssessorSkillsProgramme}
	 *         containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorSkillsProgramme> allLegacyAssessorSkillsProgramme(
			Class<LegacyAssessorSkillsProgramme> class1, int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) throws Exception {
		return (List<LegacyAssessorSkillsProgramme>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyAssessorSkillsProgramme for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity  LegacyAssessorSkillsProgramme class
	 * @param filters the filters
	 * @return Number of rows in the LegacyAssessorSkillsProgramme entity
	 * @throws Exception the exception
	 */
	public int count(Class<LegacyAssessorSkillsProgramme> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyAssessorSkillsProgramme> allEntries = allLegacyAssessorSkillsProgramme();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyAssessorSkillsProgramme> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public List<LegacyAssessorSkillsProgramme> findByAssessorIdNumber(String idNumber) throws Exception {
		return dao.findByAssessorIdNumber(idNumber);
	}

	public List<LegacyAssessorSkillsProgramme> findByAssessorRegNo(String assessorRegNo) throws Exception {
		return resolveExpiryDate(dao.findByAssessorRegNo(assessorRegNo));
	}
	
	public List<LegacyAssessorSkillsProgramme> resolveExpiryDate(List<LegacyAssessorSkillsProgramme> list)
	{
		if(list !=null && list.size()>0)
		{
			for(LegacyAssessorSkillsProgramme sp:list)
			{
				if(sp.getSkillsProgram() !=null && sp.getSkillsProgram().getQualification() !=null && sp.getSkillsProgram().getQualification().getLastDateForEnrolment() !=null)
				{
					if(sp.getSkillsProgram().getQualification().getLastDateForEnrolment().before(new Date()))
					{
						sp.setQualificationExpired(true);
					}
					else
					{
						sp.setQualificationExpired(false);
					}
				}
			}
		}
		return list;
	}

	public List<LegacyAssessorSkillsProgramme> findByAssessorIdNumberRegNo(String entry) throws Exception {
		return dao.findByAssessorIdNumberRegNo(entry);
	}

	public Integer countAllLegacyAssessorSkillsProgrammeNotProcessed() throws Exception {
		return dao.countAllLegacyAssessorSkillsProgrammeNotProcessed();
	}

	public List<LegacyAssessorSkillsProgramme> allLegacyAssessorSkillsProgrammeNotProcessed(int numberOfEntries)
			throws Exception {
		return dao.allLegacyAssessorSkillsProgrammeNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyAssessorSkillsProgrammeNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyAssessorSkillsProgramme> li = allLegacyAssessorSkillsProgrammeNotProcessed(1000);
				for (LegacyAssessorSkillsProgramme legacyAssessorSkillsProgramme : li) {
					// ID Validation
					if (legacyAssessorSkillsProgramme.getAssessorId() != null
							&& !legacyAssessorSkillsProgramme.getAssessorId().trim().isEmpty()) {
						try {
							legacyAssessorSkillsProgramme.setValidRsaIdNumber(
									GenericUtility.checkRsaId(legacyAssessorSkillsProgramme.getAssessorId().trim()));
						} catch (Exception e) {
							legacyAssessorSkillsProgramme.setValidRsaIdNumber(false);
						}
					}
					// On sProgrammeCode
					if (legacyAssessorSkillsProgramme.getsProgrammeCode() != null
							&& !legacyAssessorSkillsProgramme.getsProgrammeCode().isEmpty()) {
						try {
							SkillsProgram skillsProgram = skillsProgramService
									.findByProgrammeID(legacyAssessorSkillsProgramme.getsProgrammeCode().trim());
							if (skillsProgram != null && skillsProgram.getId() != null) {
								legacyAssessorSkillsProgramme.setSkillsProgram(skillsProgram);
							}

						} catch (Exception e) {
						}
					}
					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService
								.findByDhaIdNumber(legacyAssessorSkillsProgramme.getAssessorId().trim());
						if (entriesFound > 0) {
							legacyAssessorSkillsProgramme.setAppearsOnHomeAffairsData(true);
						} else {
							legacyAssessorSkillsProgramme.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						legacyAssessorSkillsProgramme.setAppearsOnHomeAffairsData(false);
					}

					if (legacyAssessorSkillsProgramme.getsProgrammeCode() != null
							&& !legacyAssessorSkillsProgramme.getsProgrammeCode().isEmpty()) {
						try {
							SkillsProgram skillsProgram = skillsProgramService
									.findByProgrammeID(legacyAssessorSkillsProgramme.getsProgrammeCode().trim());
							if (skillsProgram != null && skillsProgram.getId() != null) {
								legacyAssessorSkillsProgramme.setSkillsProgram(skillsProgram);
							}

						} catch (Exception e) {
						}
					}

					legacyAssessorSkillsProgramme.setProcessed(true);

					updateList.add(legacyAssessorSkillsProgramme);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyAssessorSkillsProgrammeNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Experiential Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Experiential Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
}
