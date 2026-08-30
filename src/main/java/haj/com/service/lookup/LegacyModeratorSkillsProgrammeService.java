package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyModeratorSkillsProgrammeDAO;
import haj.com.entity.lookup.LegacyAssessorLearnership;
import haj.com.entity.lookup.LegacyModeratorSkillsProgramme;
import haj.com.entity.lookup.LegacyModeratorUnitStandard;
import haj.com.entity.lookup.LegacyModeratorSkillsProgramme;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyModeratorSkillsProgrammeService extends AbstractService {
	/** The dao. */
	private LegacyModeratorSkillsProgrammeDAO dao = new LegacyModeratorSkillsProgrammeDAO();
	
	/* Service levels */
	private SkillsProgramService skillsProgramService;
	private QualificationService qualificationService;
	private HomeAffairsService homeAffairsService = new HomeAffairsService();


	/**
	 * Get all LegacyModeratorSkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorSkillsProgramme
	 * @return a list of {@link haj.com.entity.LegacyModeratorSkillsProgramme}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyModeratorSkillsProgramme> allLegacyModeratorSkillsProgramme() throws Exception {
		return dao.allLegacyModeratorSkillsProgramme();
	}

	/**
	 * Create or update LegacyModeratorSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyModeratorSkillsProgramme
	 */
	public void create(LegacyModeratorSkillsProgramme entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyModeratorSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyModeratorSkillsProgramme
	 */
	public void update(LegacyModeratorSkillsProgramme entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyModeratorSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyModeratorSkillsProgramme
	 */
	public void delete(LegacyModeratorSkillsProgramme entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyModeratorSkillsProgramme}
	 * @throws Exception
	 *             the exception
	 * @see LegacyModeratorSkillsProgramme
	 */
	public LegacyModeratorSkillsProgramme findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyModeratorSkillsProgramme by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyModeratorSkillsProgramme}
	 * @throws Exception
	 *             the exception
	 * @see LegacyModeratorSkillsProgramme
	 */
	public List<LegacyModeratorSkillsProgramme> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyModeratorSkillsProgramme
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyModeratorSkillsProgramme}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyModeratorSkillsProgramme> allLegacyModeratorSkillsProgramme(int first, int pageSize)
			throws Exception {
		return dao.allLegacyModeratorSkillsProgramme(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyModeratorSkillsProgramme for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyModeratorSkillsProgramme
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyModeratorSkillsProgramme.class);
	}

	/**
	 * Lazy load LegacyModeratorSkillsProgramme with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyModeratorSkillsProgramme class
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
	 * @return a list of {@link haj.com.entity.LegacyModeratorSkillsProgramme}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorSkillsProgramme> allLegacyModeratorSkillsProgramme(
			Class<LegacyModeratorSkillsProgramme> class1, int first, int pageSize, String sortField,
			SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyModeratorSkillsProgramme>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyModeratorSkillsProgramme for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyModeratorSkillsProgramme class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyModeratorSkillsProgramme entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyModeratorSkillsProgramme> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyModeratorSkillsProgramme> allEntries = allLegacyModeratorSkillsProgramme();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyModeratorSkillsProgramme> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public Integer countAllLegacyModeratorSkillsProgrammeNotProcessed() throws Exception {
		return dao.countAllLegacyModeratorSkillsProgrammeNotProcessed();
	}
	
	public List<LegacyModeratorSkillsProgramme> allLegacyModeratorSkillsProgrammeNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyModeratorSkillsProgrammeNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyModeratorSkillsProgrammeNotProcessed();	
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyModeratorSkillsProgramme> li = allLegacyModeratorSkillsProgrammeNotProcessed(1000);
				for (LegacyModeratorSkillsProgramme legacyModeratorSkillsProgramme : li) {
					
					//ID Validation
					if (legacyModeratorSkillsProgramme.getModeratorId() != null && !legacyModeratorSkillsProgramme.getModeratorId().trim().isEmpty()) {
						try {
							legacyModeratorSkillsProgramme.setValidRsaIdNumber(GenericUtility.checkRsaId(legacyModeratorSkillsProgramme.getModeratorId().trim()));
						} catch (Exception e) {
							legacyModeratorSkillsProgramme.setValidRsaIdNumber(false);
						}
					}
					
					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService.findByDhaIdNumber(legacyModeratorSkillsProgramme.getModeratorId().trim());
						if (entriesFound > 0) {
							legacyModeratorSkillsProgramme.setAppearsOnHomeAffairsData(true);
						} else {
							legacyModeratorSkillsProgramme.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						legacyModeratorSkillsProgramme.setAppearsOnHomeAffairsData(false);
					}
					
					if (skillsProgramService == null) {
						skillsProgramService = new SkillsProgramService();
					}

					if (legacyModeratorSkillsProgramme.getModeratorId() != null && !legacyModeratorSkillsProgramme.getModeratorId().trim().isEmpty()) {
						try {
							legacyModeratorSkillsProgramme.setValidRsaIdNumber(GenericUtility.checkRsaId(legacyModeratorSkillsProgramme.getModeratorId().trim()));
						} catch (Exception e) {
							legacyModeratorSkillsProgramme.setValidRsaIdNumber(false);
						}
					}
					
					if (legacyModeratorSkillsProgramme.getsProgrammeCode() != null && !legacyModeratorSkillsProgramme.getsProgrammeCode().isEmpty()) {
						try {
							SkillsProgram skillsProgram = skillsProgramService.findByProgrammeID(legacyModeratorSkillsProgramme.getsProgrammeCode().trim());
							if (skillsProgram != null && skillsProgram.getId() != null) {
								legacyModeratorSkillsProgramme.setSkillsProgram(skillsProgram);
							}
							
						} catch (Exception e) {
						}
					}
					
					legacyModeratorSkillsProgramme.setProcessed(true);
					
					updateList.add(legacyModeratorSkillsProgramme);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyModeratorSkillsProgrammeNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Moderator Skills Programme Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
			logger.info("validateRsaIdNumbers() Complete");
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Moderator Skills Programme Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	public List<LegacyModeratorSkillsProgramme> findByModeratorRegNo(String certificateNumber)throws Exception {
		return resolveExpiryDate(dao.findByModeratorRegNo(certificateNumber));
	}
	
	public  List<LegacyModeratorSkillsProgramme>  resolveExpiryDate(List<LegacyModeratorSkillsProgramme>   list)
	{
		if(list !=null && list.size()>0)
		{
			for(LegacyModeratorSkillsProgramme qual:list)
			{
				if(qual.getSkillsProgram() !=null && qual.getSkillsProgram().getQualification() !=null && qual.getSkillsProgram().getQualification().getLastDateForEnrolment() !=null)
				{
					if(qual.getSkillsProgram().getQualification().getLastDateForEnrolment().before(new Date()))
					{
						qual.setQualificationExpired(true);
					}
					else
					{
						qual.setQualificationExpired(false);
					}
				}
			}
		}
		
		return list;
	}
	
	
}
