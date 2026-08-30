package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import org.primefaces.model.SortOrder;
import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyModeratorUnitStandardDAO;
import haj.com.entity.lookup.LegacyModeratorQualification;
import haj.com.entity.lookup.LegacyModeratorUnitStandard;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.UnitStandardsService;
import haj.com.utils.GenericUtility;

public class LegacyModeratorUnitStandardService extends AbstractService {
	/** The dao. */
	private LegacyModeratorUnitStandardDAO dao = new LegacyModeratorUnitStandardDAO();

	private HomeAffairsService homeAffairsService = new HomeAffairsService();
	private UnitStandardsService unitStandardsService = new UnitStandardsService();

	/**
	 * Get all LegacyModeratorUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyModeratorUnitStandard
	 * @return a list of {@link haj.com.entity.LegacyModeratorUnitStandard}
	 * @throws Exception the exception
	 */
	public List<LegacyModeratorUnitStandard> allLegacyModeratorUnitStandard() throws Exception {
		return dao.allLegacyModeratorUnitStandard();
	}

	/**
	 * Create or update LegacyModeratorUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyModeratorUnitStandard
	 */
	public void create(LegacyModeratorUnitStandard entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyModeratorUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyModeratorUnitStandard
	 */
	public void update(LegacyModeratorUnitStandard entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyModeratorUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyModeratorUnitStandard
	 */
	public void delete(LegacyModeratorUnitStandard entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyModeratorUnitStandard}
	 * @throws Exception the exception
	 * @see LegacyModeratorUnitStandard
	 */
	public LegacyModeratorUnitStandard findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyModeratorUnitStandard by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyModeratorUnitStandard}
	 * @throws Exception the exception
	 * @see LegacyModeratorUnitStandard
	 */
	public List<LegacyModeratorUnitStandard> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyModeratorUnitStandard
	 * 
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyModeratorUnitStandard}
	 * @throws Exception the exception
	 */
	public List<LegacyModeratorUnitStandard> allLegacyModeratorUnitStandard(int first, int pageSize) throws Exception {
		return dao.allLegacyModeratorUnitStandard(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyModeratorUnitStandard for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyModeratorUnitStandard
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyModeratorUnitStandard.class);
	}

	/**
	 * Lazy load LegacyModeratorUnitStandard with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1    LegacyModeratorUnitStandard class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.LegacyModeratorUnitStandard}
	 *         containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyModeratorUnitStandard> allLegacyModeratorUnitStandard(Class<LegacyModeratorUnitStandard> class1,
			int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
			throws Exception {
		return (List<LegacyModeratorUnitStandard>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyModeratorUnitStandard for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity  LegacyModeratorUnitStandard class
	 * @param filters the filters
	 * @return Number of rows in the LegacyModeratorUnitStandard entity
	 * @throws Exception the exception
	 */
	public int count(Class<LegacyModeratorUnitStandard> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyModeratorUnitStandard> allEntries = allLegacyModeratorUnitStandard();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyModeratorUnitStandard> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacyModeratorUnitStandardNotProcessed() throws Exception {
		return dao.countAllLegacyModeratorUnitStandardNotProcessed();
	}

	public List<LegacyModeratorUnitStandard> allLegacyModeratorUnitStandardNotProcessed(int numberOfEntries)
			throws Exception {
		return dao.allLegacyModeratorUnitStandardNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyModeratorUnitStandardNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyModeratorUnitStandard> li = allLegacyModeratorUnitStandardNotProcessed(1000);
				for (LegacyModeratorUnitStandard legacyModeratorUnitStandard : li) {

					// ID Validation
					if (legacyModeratorUnitStandard.getModeratorId() != null
							&& !legacyModeratorUnitStandard.getModeratorId().trim().isEmpty()) {
						try {
							legacyModeratorUnitStandard.setValidRsaIdNumber(
									GenericUtility.checkRsaId(legacyModeratorUnitStandard.getModeratorId().trim()));
						} catch (Exception e) {
							legacyModeratorUnitStandard.setValidRsaIdNumber(false);
						}
					}

					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService
								.findByDhaIdNumber(legacyModeratorUnitStandard.getModeratorId().trim());
						if (entriesFound > 0) {
							legacyModeratorUnitStandard.setAppearsOnHomeAffairsData(true);
						} else {
							legacyModeratorUnitStandard.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						legacyModeratorUnitStandard.setAppearsOnHomeAffairsData(false);
					}

					if (unitStandardsService == null) {
						unitStandardsService = new UnitStandardsService();
					}
					if (legacyModeratorUnitStandard.getModeratorId() != null
							&& !legacyModeratorUnitStandard.getModeratorId().trim().isEmpty()) {
						try {
							legacyModeratorUnitStandard.setValidRsaIdNumber(
									GenericUtility.checkRsaId(legacyModeratorUnitStandard.getModeratorId().trim()));
						} catch (Exception e) {
							legacyModeratorUnitStandard.setValidRsaIdNumber(false);
						}
					}
					if (legacyModeratorUnitStandard.getUnitStdCode() != null
							&& !legacyModeratorUnitStandard.getUnitStdCode().isEmpty()) {
						try {
							Integer code = Integer.valueOf(legacyModeratorUnitStandard.getUnitStdCode().trim());
							UnitStandards unitStandard = unitStandardsService.findByUnitStandartId(code);
							if (unitStandard != null && unitStandard.getId() != null) {
								legacyModeratorUnitStandard.setUnitStandard(unitStandard);
							}
						} catch (Exception e) {

						}
					}

					legacyModeratorUnitStandard.setProcessed(true);

					updateList.add(legacyModeratorUnitStandard);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyModeratorUnitStandardNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Moderator Unit Standard Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Moderator Unit Standard Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	public List<LegacyModeratorUnitStandard> findByModeratorRegNo(String certificateNumber) throws Exception {
		return resolveExpiryDate(dao.findByModeratorRegNo(certificateNumber));
	}
	
	public  List<LegacyModeratorUnitStandard>  resolveExpiryDate(List<LegacyModeratorUnitStandard>   list)
	{
		if(list !=null && list.size()>0)
		{
			for(LegacyModeratorUnitStandard qual:list)
			{
				if(qual.getUnitStandard() !=null && qual.getUnitStandard().getLastDateForEnrolment() !=null)
				{
					if(qual.getUnitStandard().getLastDateForEnrolment() .before(new Date()))
					{
						qual.setUnitStandardExpired(true);
					}
					else
					{
						qual.setUnitStandardExpired(false);
					}
				}
			}
		}
		
		return list;
	}

}
