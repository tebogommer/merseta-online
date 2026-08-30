package haj.com.service.lookup;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyModeratorAccreditationDAO;
import haj.com.entity.lookup.LegacyModeratorAccreditation;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyModeratorAccreditationService extends AbstractService {
	/** The dao. */
	private LegacyModeratorAccreditationDAO dao = new LegacyModeratorAccreditationDAO();
	
	private HomeAffairsService homeAffairsService = new HomeAffairsService();
	private LegacyPersonService legacyPersonService = new LegacyPersonService();

	/**
	 * Get all LegacyModeratorAccreditation
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorAccreditation
	 * @return a list of {@link haj.com.entity.LegacyModeratorAccreditation}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyModeratorAccreditation> allLegacyModeratorAccreditation() throws Exception {
		return dao.allLegacyModeratorAccreditation();
	}

	/**
	 * Create or update LegacyModeratorAccreditation.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyModeratorAccreditation
	 */
	public void create(LegacyModeratorAccreditation entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyModeratorAccreditation.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyModeratorAccreditation
	 */
	public void update(LegacyModeratorAccreditation entity) throws Exception {
		this.dao.update(entity);
	}
	
	public List<LegacyModeratorAccreditation> findByIdNoStatusAndProcessed(String idNo,String status,Boolean processed) throws Exception {
		return this.dao.findByIdNoStatusAndProcessed(idNo,status,processed);
	}
	
	public List<LegacyModeratorAccreditation> findRegisteredOrExpiredLegacyAccreditation(String idNo,Boolean processed) throws Exception {
		return this.dao.findRegisteredOrExpiredLegacyAccreditation(idNo,processed);
	}
	
	public List<LegacyModeratorAccreditation> findRegisteredOrExpiredLegacyAccreditationVersionTwo(String idNo,Boolean processed) throws Exception {
		return dao.findRegisteredOrExpiredLegacyAccreditationVersionTwo(idNo, processed);
	}
	
	public List<LegacyModeratorAccreditation> findRegisteredOrExpiredLegacyAccreditationVersionTwoPassport(String idNo,Boolean processed) throws Exception {
		return dao.findRegisteredOrExpiredLegacyAccreditationVersionTwoPassport(idNo, processed);
	}
	
	/**
	 * Delete LegacyModeratorAccreditation.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see LegacyModeratorAccreditation
	 */
	public void delete(LegacyModeratorAccreditation entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.LegacyModeratorAccreditation}
	 * @throws Exception
	 *             the exception
	 * @see LegacyModeratorAccreditation
	 */
	public LegacyModeratorAccreditation findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyModeratorAccreditation by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.LegacyModeratorAccreditation}
	 * @throws Exception
	 *             the exception
	 * @see LegacyModeratorAccreditation
	 */
	public List<LegacyModeratorAccreditation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyModeratorAccreditation
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyModeratorAccreditation}
	 * @throws Exception
	 *             the exception
	 */
	public List<LegacyModeratorAccreditation> allLegacyModeratorAccreditation(int first, int pageSize)
			throws Exception {
		return dao.allLegacyModeratorAccreditation(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyModeratorAccreditation for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyModeratorAccreditation
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyModeratorAccreditation.class);
	}

	/**
	 * Lazy load LegacyModeratorAccreditation with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            LegacyModeratorAccreditation class
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
	 * @return a list of {@link haj.com.entity.LegacyModeratorAccreditation}
	 *         containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorAccreditation> allLegacyModeratorAccreditation(
			Class<LegacyModeratorAccreditation> class1, int first, int pageSize, String sortField, SortOrder sortOrder,
			Map<String, Object> filters) throws Exception {
		return (List<LegacyModeratorAccreditation>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorAccreditation> allLegacyModeratorAccreditationUnitStandards(int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyModeratorAccreditation o where o.idNo in (select x.moderatorId from LegacyModeratorUnitStandard x where x.unitStandard.id = :unitStandardId)";
		return (List<LegacyModeratorAccreditation>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllLegacyModeratorAccreditationUnitStandards(Map<String, Object> filters) throws Exception {
		String hql = "select count(o)  from LegacyModeratorAccreditation o where o.idNo in (select x.moderatorId from LegacyModeratorUnitStandard x where x.unitStandard.id = :unitStandardId)";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorAccreditation> allLegacyModeratorAccreditationSkillsProgram(int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyModeratorAccreditation o where o.idNo in (select x.moderatorId from LegacyModeratorSkillsProgramme x where x.skillsProgram.id = :skillsProgrammeID)";
		return (List<LegacyModeratorAccreditation>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllLegacyModeratorAccreditationSkillsProgram(Map<String, Object> filters) throws Exception {
		String hql = "select count(o)  from LegacyModeratorAccreditation o where o.idNo in (select x.moderatorId from LegacyModeratorSkillsProgramme x where x.skillsProgram.id = :skillsProgrammeID)";
		return dao.countWhere(filters, hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorAccreditation> allLegacyModeratorAccreditationQualification(int first, int pageSize,
			String sortField, SortOrder sortOrder, Map<String, Object> filters, Boolean value) throws Exception {
		String hql = "select o from LegacyModeratorAccreditation o where o.idNo in (select x.moderatorId from LegacyModeratorQualification x where x.qualification.id = :qualificationID)";
		return (List<LegacyModeratorAccreditation>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllLegacyModeratorAccreditationQualification(Map<String, Object> filters) throws Exception {
		String hql = "select count(o)  from LegacyModeratorAccreditation o where o.idNo in (select x.moderatorId from LegacyModeratorQualification x where x.qualification.id = :qualificationID)";
		return dao.countWhere(filters, hql);
	}

	/**
	 * Get count of LegacyModeratorAccreditation for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            LegacyModeratorAccreditation class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the LegacyModeratorAccreditation entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<LegacyModeratorAccreditation> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyModeratorAccreditation> allEntries = allLegacyModeratorAccreditation();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyModeratorAccreditation> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}	

	public Integer countAllLegacyModeratorAccreditationNotProcessed() throws Exception {
		return dao.countAllLegacyModeratorAccreditationNotProcessed();
	}
	
	public List<LegacyModeratorAccreditation> allLegacyModeratorAccreditationNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyModeratorAccreditationNotProcessed(numberOfEntries);
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
			List<IDataEntity> updateList = new ArrayList<>();

			updateList = new ArrayList<>();
			List<LegacyModeratorAccreditation> li = allLegacyModeratorAccreditation();
			for (LegacyModeratorAccreditation legacyModeratorAccreditation : li) {
				
				//ID Validation
				if (legacyModeratorAccreditation.getIdNo() != null && !legacyModeratorAccreditation.getIdNo().trim().isEmpty()) {
					try {
						legacyModeratorAccreditation.setValidRsaIdNumber(GenericUtility.checkRsaId(legacyModeratorAccreditation.getIdNo().trim()));
					} catch (Exception e) {
						legacyModeratorAccreditation.setValidRsaIdNumber(false);
					}
				}
				
				// on home affairs file
				try {
					Integer entriesFound = homeAffairsService.findByDhaIdNumber(legacyModeratorAccreditation.getIdNo().trim());
					if (entriesFound > 0) {
						legacyModeratorAccreditation.setAppearsOnHomeAffairsData(true);
					} else {
						legacyModeratorAccreditation.setAppearsOnHomeAffairsData(false);
					}
				} catch (Exception e) {
					legacyModeratorAccreditation.setAppearsOnHomeAffairsData(false);
				}
				
				if (legacyModeratorAccreditation.getAppearsOnHomeAffairsData() != null && legacyModeratorAccreditation.getAppearsOnHomeAffairsData()) {
					// if on home affairs file check if alive
					try {
						
						int aliveCount = homeAffairsService.findByDhaIdNumberAndDeathStatusAlive(legacyModeratorAccreditation.getIdNo().trim());
						int deathCount = homeAffairsService.findByDhaIdNumberAndDeathStatusAlive(legacyModeratorAccreditation.getIdNo().trim());
						
						if (aliveCount == 0 && deathCount == 0) {
							legacyModeratorAccreditation.setAliveOnHomeAffairsData(true);
						}else if (aliveCount > 0) {
							legacyModeratorAccreditation.setAliveOnHomeAffairsData(true);
						}else if (deathCount > 0) {
							legacyModeratorAccreditation.setAliveOnHomeAffairsData(false);
						}
//						legacyModeratorAccreditation.setAliveOnHomeAffairsData(homeAffairsService.idNoDeceasedCheckTwo(legacyModeratorAccreditation.getIdNo().trim()));
					} catch (Exception e) {
						logger.fatal(e);
						legacyModeratorAccreditation.setAliveOnHomeAffairsData(null);
					}
				}
				
				try {
					// check if on persons file
					legacyModeratorAccreditation.setOnPersonsFile(legacyPersonService.alternativeIdNumberOnPersonFile(legacyModeratorAccreditation.getIdNo().trim()));
				} catch (Exception e) {
					logger.fatal(e);
					legacyModeratorAccreditation.setOnPersonsFile(false);
				}
				
				try {
					// check if on persons file for alternative ID
					legacyModeratorAccreditation.setOnPersonsFileAlternativeId(legacyPersonService.alternativeIdNumberOnPersonFile(legacyModeratorAccreditation.getIdNo().trim()));
				} catch (Exception e) {
					logger.fatal(e);
					legacyModeratorAccreditation.setOnPersonsFileAlternativeId(false);
				}
				
				try {
					// Determine valid status
					if ((legacyModeratorAccreditation.getModeratorStatusDesc() != null && !legacyModeratorAccreditation.getModeratorStatusDesc().isEmpty()) 
							&& 
						(legacyModeratorAccreditation.getModeratorRegEndDate() != null && !legacyModeratorAccreditation.getModeratorRegEndDate().isEmpty() && !legacyModeratorAccreditation.getModeratorRegEndDate().trim().toUpperCase().equals("NULL"))) {
						
						Date modRegEndDate = GenericUtility.getStartOfDay(HAJConstants.sdfYYYYMMDD.parse(legacyModeratorAccreditation.getModeratorRegEndDate().trim()));
						switch (legacyModeratorAccreditation.getModeratorStatusDesc().trim().toUpperCase()) {
						case "DECLINED":
							if (modRegEndDate != null && modRegEndDate.before(getSynchronizedDate())) {
								legacyModeratorAccreditation.setValidStatus(true);
							} else {
								legacyModeratorAccreditation.setValidStatus(false);
							}
							break;
						case "EXPIRED": // can select only this one 
							if (modRegEndDate != null && modRegEndDate.before(getSynchronizedDate())) {
								legacyModeratorAccreditation.setValidStatus(true);
							} else {
								legacyModeratorAccreditation.setValidStatus(false);
							}
							break;
						default:
							break;
						}
					} else {
						legacyModeratorAccreditation.setValidStatus(false);
					}
				} catch (Exception e) {
					logger.fatal(e);
					legacyModeratorAccreditation.setValidStatus(false);
				}
				
//				legacyModeratorAccreditation.setProcessed(true);
				
				updateList.add(legacyModeratorAccreditation);
			}
			
			li = null;
			
			if (updateList.size() != 0) {
				dao.updateBatch(updateList);
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Moderator Accreditation Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Moderator Accreditation Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
	
}
