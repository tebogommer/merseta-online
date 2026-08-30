package haj.com.service.lookup;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;
import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyModeratorQualificationDAO;
import haj.com.entity.lookup.LegacyModeratorQualification;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyModeratorQualificationService extends AbstractService {
	/** The dao. */
	private LegacyModeratorQualificationDAO dao = new LegacyModeratorQualificationDAO();

	/* Service Levels */
	private QualificationService qualificationService;
	private HomeAffairsService homeAffairsService = new HomeAffairsService();

	/**
	 * Get all LegacyModeratorQualification
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorQualification
	 * @return a list of {@link haj.com.entity.LegacyModeratorQualification}
	 * @throws Exception the exception
	 */
	public List<LegacyModeratorQualification> allLegacyModeratorQualification() throws Exception {
		return dao.allLegacyModeratorQualification();
	}

	/**
	 * Create or update LegacyModeratorQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyModeratorQualification
	 */
	public void create(LegacyModeratorQualification entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyModeratorQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyModeratorQualification
	 */
	public void update(LegacyModeratorQualification entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyModeratorQualification.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyModeratorQualification
	 */
	public void delete(LegacyModeratorQualification entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyModeratorQualification}
	 * @throws Exception the exception
	 * @see LegacyModeratorQualification
	 */
	public LegacyModeratorQualification findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyModeratorQualification by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyModeratorQualification}
	 * @throws Exception the exception
	 * @see LegacyModeratorQualification
	 */
	public List<LegacyModeratorQualification> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyModeratorQualification
	 * 
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyModeratorQualification}
	 * @throws Exception the exception
	 */
	public List<LegacyModeratorQualification> allLegacyModeratorQualification(int first, int pageSize) throws Exception {
		return dao.allLegacyModeratorQualification(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyModeratorQualification for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyModeratorQualification
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyModeratorQualification.class);
	}

	/**
	 * Lazy load LegacyModeratorQualification with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1    LegacyModeratorQualification class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.LegacyModeratorQualification}
	 *         containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorQualification> allLegacyModeratorQualification(Class<LegacyModeratorQualification> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<LegacyModeratorQualification>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of LegacyModeratorQualification for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity  LegacyModeratorQualification class
	 * @param filters the filters
	 * @return Number of rows in the LegacyModeratorQualification entity
	 * @throws Exception the exception
	 */
	public int count(Class<LegacyModeratorQualification> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {

		List<LegacyModeratorQualification> allEntries = allLegacyModeratorQualification();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyModeratorQualification> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacyModeratorQualificationNotProcessed() throws Exception {
		return dao.countAllLegacyModeratorQualificationNotProcessed();
	}

	public List<LegacyModeratorQualification> allLegacyModeratorQualificationNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyModeratorQualificationNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyModeratorQualificationNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyModeratorQualification> li = allLegacyModeratorQualificationNotProcessed(1000);
				for (LegacyModeratorQualification legacyModeratorQualification : li) {

					// ID Validation
					if (legacyModeratorQualification.getModeratorId() != null && !legacyModeratorQualification.getModeratorId().trim().isEmpty()) {
						try {
							legacyModeratorQualification.setValidRsaIdNumber(GenericUtility.checkRsaId(legacyModeratorQualification.getModeratorId().trim()));
						} catch (Exception e) {
							legacyModeratorQualification.setValidRsaIdNumber(false);
						}
					}

					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService.findByDhaIdNumber(legacyModeratorQualification.getModeratorId().trim());
						if (entriesFound > 0) {
							legacyModeratorQualification.setAppearsOnHomeAffairsData(true);
						} else {
							legacyModeratorQualification.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						legacyModeratorQualification.setAppearsOnHomeAffairsData(false);
					}

					if (qualificationService == null) {
						qualificationService = new QualificationService();
					}

					if (legacyModeratorQualification.getModeratorId() != null && !legacyModeratorQualification.getModeratorId().trim().isEmpty()) {
						try {
							legacyModeratorQualification.setValidRsaIdNumber(GenericUtility.checkRsaId(legacyModeratorQualification.getModeratorId().trim()));
						} catch (Exception e) {
							legacyModeratorQualification.setValidRsaIdNumber(false);
						}
					}

					if (legacyModeratorQualification.getQualCode() != null && !legacyModeratorQualification.getQualCode().isEmpty()) {
						try {
							Qualification qua = qualificationService.findByCodeString(legacyModeratorQualification.getQualCode().trim());
							if (qua != null && qua.getId() != null) {
								legacyModeratorQualification.setQualification(qua);
							}
						} catch (Exception e) {
						}
					}

					legacyModeratorQualification.setProcessed(true);

					updateList.add(legacyModeratorQualification);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyModeratorQualificationNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Moderator Qualification Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Moderator Qualification Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	public List<LegacyModeratorQualification> findByModeratorRegNo(String certificateNumber) throws Exception {
		return resolveExpiryDate(dao.findByModeratorRegNo(certificateNumber));
	}
	
	public  List<LegacyModeratorQualification> resolveExpiryDate(List<LegacyModeratorQualification>  list)
	{
		if(list !=null && list.size()>0)
		{
			for(LegacyModeratorQualification qual:list)
			{
				if(qual.getQualification() !=null && qual.getQualification().getLastDateForEnrolment() !=null)
				{
					if(qual.getQualification().getLastDateForEnrolment() .before(new Date()))
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
