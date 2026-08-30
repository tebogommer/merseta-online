package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyProviderSkillsProgrammeDAO;
import haj.com.entity.enums.SkillsTypeEnum;
import haj.com.entity.lookup.LegacyAssessorSkillsProgramme;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.LegacyProviderAccreditation;
import haj.com.entity.lookup.LegacyProviderSkillsProgramme;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyProviderSkillsProgrammeService extends AbstractService {
	/** The dao. */
	private LegacyProviderSkillsProgrammeDAO dao = new LegacyProviderSkillsProgrammeDAO();
	
	/** The Service Levels */
	private SkillsSetService skillsSetService;
	private SkillsProgramService skillsProgramService;

	private static LegacyProviderSkillsProgrammeService legacyProviderSkillsProgrammeService;

	public static synchronized LegacyProviderSkillsProgrammeService instance() {
		if (legacyProviderSkillsProgrammeService == null) {
			legacyProviderSkillsProgrammeService = new LegacyProviderSkillsProgrammeService();
		}
		return legacyProviderSkillsProgrammeService;
	}

	/**
	 * Get all LegacyProviderSkillsProgramme
	 * 
	 * @author TechFinium
	 * @see LegacyProviderSkillsProgramme
	 * @return a list of {@link haj.com.entity.LegacyProviderSkillsProgramme}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyProviderSkillsProgramme> allLegacyProviderSkillsProgramme() throws Exception {
		return dao.allLegacyProviderSkillsProgramme();
	}

	/**
	 * Create or update LegacyProviderSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderSkillsProgramme
	 */
	public void create(LegacyProviderSkillsProgramme entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyProviderSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderSkillsProgramme
	 */
	public void update(LegacyProviderSkillsProgramme entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyProviderSkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderSkillsProgramme
	 */
	public void delete(LegacyProviderSkillsProgramme entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyProviderSkillsProgramme}
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderSkillsProgramme
	 */
	public LegacyProviderSkillsProgramme findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyProviderSkillsProgramme by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyProviderSkillsProgramme}
	 * @throws Exception
	 *             the exception
	 * @see LegacyProviderSkillsProgramme
	 */
	public List<LegacyProviderSkillsProgramme> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyProviderSkillsProgramme
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyProviderSkillsProgramme}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyProviderSkillsProgramme> allLegacyProviderSkillsProgramme(int first, int pageSize)
			throws Exception {
		return dao.allLegacyProviderSkillsProgramme(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyProviderSkillsProgramme for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyProviderSkillsProgramme
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyProviderSkillsProgramme.class);
	}

	/**
	 * Lazy load LegacyProviderSkillsProgramme with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyProviderSkillsProgramme class
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
	 * @return a list of {@link haj.com.entity.LegacyProviderSkillsProgramme}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderSkillsProgramme> allLegacyProviderSkillsProgramme(
			Class<LegacyProviderSkillsProgramme> class1, int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) throws Exception {
		return (List<LegacyProviderSkillsProgramme>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyProviderSkillsProgramme for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyProviderSkillsProgramme class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyProviderSkillsProgramme entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyProviderSkillsProgramme> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyProviderSkillsProgramme> allEntries = allLegacyProviderSkillsProgramme();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyProviderSkillsProgramme> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public List<LegacyProviderSkillsProgramme> findBySldNoAndSkillProgrameIsNotNull(String sldNo) throws Exception {
		return resolveExpiryDate(dao.findBySldNoAndSkillProgrameIsNotNull(sldNo));
	}

	public List<LegacyProviderSkillsProgramme> findByAccreditationNoAndSkillProgrameIsNotNull(String accreditationNo) throws Exception {
		return resolveExpiryDate(dao.findByAccreditationNoAndSkillProgrameIsNotNull(accreditationNo));
	}
	
	public List<LegacyProviderSkillsProgramme> findByAccreditationNoAndSkillSetIsNotNull(String accreditationNo) throws Exception {
		return resolveExpiryDate(dao.findByAccreditationNoAndSkillSetIsNotNull(accreditationNo));
	}

	public List<LegacyProviderSkillsProgramme> resolveExpiryDate(List<LegacyProviderSkillsProgramme> list) {
		if (list != null && list.size() > 0) {
			for (LegacyProviderSkillsProgramme sp : list) {
				if (sp.getSkillsProgram() != null && sp.getSkillsProgram().getQualification() != null && sp.getSkillsProgram().getQualification().getLastDateForEnrolment() != null) {
					if (sp.getSkillsProgram().getQualification().getLastDateForEnrolment().before(new Date())) {
						sp.setQualificationExpired(true);
					} else {
						sp.setQualificationExpired(false);
					}
				} else if (sp.getSkillsSet() != null && sp.getSkillsSet().getQualification() != null && sp.getSkillsSet().getQualification().getLastDateForEnrolment() != null) { 
					if (sp.getSkillsSet().getQualification().getLastDateForEnrolment().before(new Date())) {
						sp.setQualificationExpired(true);
					} else {
						sp.setQualificationExpired(false);
					}
				}
			}
		}
		return list;
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public void correctData(){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					if (skillsProgramService == null) {
						skillsProgramService = new SkillsProgramService();
					}
					if (skillsSetService == null) {
						skillsSetService = new SkillsSetService();
					}
					
					List<IDataEntity> updateList = new ArrayList<IDataEntity>();
					List<LegacyProviderSkillsProgramme> allLegacyProviderSkillsProgramme = allLegacyProviderSkillsProgramme();
					for (LegacyProviderSkillsProgramme entry : allLegacyProviderSkillsProgramme) {
						
						// Identify if skills set or skills programme
						try {
							if (entry.getMersetaskillsProgrammeNo() != null && !entry.getMersetaskillsProgrammeNo().isEmpty()) {
								if (entry.getMersetaskillsProgrammeNo().trim().toUpperCase().contains("SP")) {
									// skills programme
									entry.setSkillsTypeEnum(SkillsTypeEnum.SkillsProgram);
								} else if (entry.getMersetaskillsProgrammeNo().trim().toUpperCase().contains("SS")) {
									// skills set
									entry.setSkillsTypeEnum(SkillsTypeEnum.SkillsSet);
								} else {
									// skills programme 
									entry.setSkillsTypeEnum(SkillsTypeEnum.SkillsProgram);
								}
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
						
						// locate link to either skills programme or skills set
						try {
							if (entry.getSkillsTypeEnum() != null) {
								if (entry.getSkillsTypeEnum() == SkillsTypeEnum.SkillsProgram) {
									SkillsProgram skillsProgram = skillsProgramService.findByProgrammeIDListReturnOne(entry.getMersetaskillsProgrammeNo().trim());
									if (skillsProgram != null && skillsProgram.getId() != null) {
										entry.setSkillsProgram(skillsProgram);
									}
									skillsProgram = null;
								} else if (entry.getSkillsTypeEnum() == SkillsTypeEnum.SkillsSet) {
									SkillsSet skillsSet = skillsSetService.findByProgrammeIDListReturnOne(entry.getMersetaskillsProgrammeNo().trim());
									if (skillsSet != null && skillsSet.getId() != null) {
										entry.setSkillsSet(skillsSet);
									}
									skillsSet = null;
								}
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
						
						updateList.add(entry);
					}
					
					if (updateList.size() != 0) {
						dao.updateBatch(updateList);
					}
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "Legacy Provider Skills Programme Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
					}
				} catch (Exception e) {
					logger.fatal(e);
					for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
						GenericUtility.sendMail(emailNotificiations, "Error on Legacy Provider Skills Programme Process", "Processing Complete with Errors on site: " + HAJConstants.PL_LINK + ". Error Message: " + e.getMessage(), null);
					}
				}				
			}
		}).start();
	}
	
	
	public LegacyProviderSkillsProgramme runValidiationForSingleEntry(LegacyProviderSkillsProgramme entry) throws Exception{
		
		// Identify if skills set or skills programme
		try {
			if (entry.getMersetaskillsProgrammeNo() != null && !entry.getMersetaskillsProgrammeNo().isEmpty()) {
				if (entry.getMersetaskillsProgrammeNo().trim().toUpperCase().contains("SP")) {
					// skills programme
					entry.setSkillsTypeEnum(SkillsTypeEnum.SkillsProgram);
				} else if (entry.getMersetaskillsProgrammeNo().trim().toUpperCase().contains("SS")) {
					// skills set
					entry.setSkillsTypeEnum(SkillsTypeEnum.SkillsSet);
				} else {
					// skills programme 
					entry.setSkillsTypeEnum(SkillsTypeEnum.SkillsProgram);
				}
			}
		} catch (Exception e) {
		}
		
		// locate link to either skills programme or skills set
		try {
			if (entry.getSkillsTypeEnum() != null) {
				if (entry.getSkillsTypeEnum() == SkillsTypeEnum.SkillsProgram) {
					SkillsProgram skillsProgram = skillsProgramService.findByProgrammeIDListReturnOne(entry.getMersetaskillsProgrammeNo().trim());
					if (skillsProgram != null && skillsProgram.getId() != null) {
						entry.setSkillsProgram(skillsProgram);
					}
					skillsProgram = null;
				} else if (entry.getSkillsTypeEnum() == SkillsTypeEnum.SkillsSet) {
					SkillsSet skillsSet = skillsSetService.findByProgrammeIDListReturnOne(entry.getMersetaskillsProgrammeNo().trim());
					if (skillsSet != null && skillsSet.getId() != null) {
						entry.setSkillsSet(skillsSet);
					}
					skillsSet = null;
				}
			}
		} catch (Exception e) {
		}
		
		try {
			if (entry.getSkillsProgram() != null && entry.getSkillsProgram().getQualification() != null && entry.getSkillsProgram().getQualification().getLastDateForEnrolment() != null) {
				if (entry.getSkillsProgram().getQualification().getLastDateForEnrolment().before(new Date())) {
					entry.setQualificationExpired(true);
				} else {
					entry.setQualificationExpired(false);
				}
			} else if (entry.getSkillsSet() != null && entry.getSkillsSet().getQualification() != null && entry.getSkillsSet().getQualification().getLastDateForEnrolment() != null) { 
				if (entry.getSkillsSet().getQualification().getLastDateForEnrolment().before(new Date())) {
					entry.setQualificationExpired(true);
				} else {
					entry.setQualificationExpired(false);
				}
			}
		} catch (Exception e) {
		}
		
		/*
		 * Set: linkedAccreditationNumberOnProviderAccreditation
		 * Use: ResolveByCodeLookupDAO checkLegacyProviderAccreditation
		 * Use: accreditationNo
		 */
		
		return entry;
	}
	
}
