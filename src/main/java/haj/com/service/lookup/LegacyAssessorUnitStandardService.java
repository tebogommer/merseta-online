package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyAssessorUnitStandardDAO;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.UnitStandardsService;
import haj.com.utils.GenericUtility;

public class LegacyAssessorUnitStandardService extends AbstractService {
	/** The dao. */
	private LegacyAssessorUnitStandardDAO dao = new LegacyAssessorUnitStandardDAO();

	/* Unit Standards */
	private UnitStandardsService unitStandardsService;
	private HomeAffairsService homeAffairsService = new HomeAffairsService();

	/**
	 * Get all LegacyAssessorUnitStandard
	 * 
	 * @author TechFinium
	 * @see LegacyAssessorUnitStandard
	 * @return a list of {@link haj.com.entity.LegacyAssessorUnitStandard}
	 * @throws Exception the exception
	 */
	public List<LegacyAssessorUnitStandard> allLegacyAssessorUnitStandard() throws Exception {
		return dao.allLegacyAssessorUnitStandard();
	}

	/**
	 * Create or update LegacyAssessorUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyAssessorUnitStandard
	 */
	public void create(LegacyAssessorUnitStandard entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else
			this.dao.update(entity);
	}

	/**
	 * Update LegacyAssessorUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyAssessorUnitStandard
	 */
	public void update(LegacyAssessorUnitStandard entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete LegacyAssessorUnitStandard.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see LegacyAssessorUnitStandard
	 */
	public void delete(LegacyAssessorUnitStandard entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyAssessorUnitStandard}
	 * @throws Exception the exception
	 * @see LegacyAssessorUnitStandard
	 */
	public LegacyAssessorUnitStandard findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find LegacyAssessorUnitStandard by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyAssessorUnitStandard}
	 * @throws Exception the exception
	 * @see LegacyAssessorUnitStandard
	 */
	public List<LegacyAssessorUnitStandard> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load LegacyAssessorUnitStandard
	 * 
	 * @param first    from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyAssessorUnitStandard}
	 * @throws Exception the exception
	 */
	public List<LegacyAssessorUnitStandard> allLegacyAssessorUnitStandard(int first, int pageSize) throws Exception {
		return dao.allLegacyAssessorUnitStandard(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of LegacyAssessorUnitStandard for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the LegacyAssessorUnitStandard
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
		return dao.count(LegacyAssessorUnitStandard.class);
	}

	/**
	 * Lazy load LegacyAssessorUnitStandard with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1    LegacyAssessorUnitStandard class
	 * @param first     the first
	 * @param pageSize  the page size
	 * @param sortField the sort field
	 * @param sortOrder the sort order
	 * @param filters   the filters
	 * @return a list of {@link haj.com.entity.LegacyAssessorUnitStandard}
	 *         containing data
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorUnitStandard> allLegacyAssessorUnitStandard(Class<LegacyAssessorUnitStandard> class1,
			int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters)
			throws Exception {
		return (List<LegacyAssessorUnitStandard>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder,
				filters);

	}

	/**
	 * Get count of LegacyAssessorUnitStandard for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity  LegacyAssessorUnitStandard class
	 * @param filters the filters
	 * @return Number of rows in the LegacyAssessorUnitStandard entity
	 * @throws Exception the exception
	 */
	public int count(Class<LegacyAssessorUnitStandard> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception {
		List<LegacyAssessorUnitStandard> allEntries = allLegacyAssessorUnitStandard();
		dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyAssessorUnitStandard> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public List<LegacyAssessorUnitStandard> findByAssessorIdNumber(String idNumber) throws Exception {
		return dao.findByAssessorIdNumber(idNumber);
	}

	public List<LegacyAssessorUnitStandard> findByAssessorRegNo(String assessorRegNo) throws Exception {
		return resolveUSExpiryDate(dao.findByAssessorRegNo(assessorRegNo));
	}
	
	public List<LegacyAssessorUnitStandard>  resolveUSExpiryDate(List<LegacyAssessorUnitStandard> legacyAssessorUnitStandardList)
	{
		if(legacyAssessorUnitStandardList !=null && legacyAssessorUnitStandardList.size()>0)
		{
			for(LegacyAssessorUnitStandard qual:legacyAssessorUnitStandardList)
			{
				if(qual.getUnitStandard() !=null && qual.getUnitStandard().getLastDateForEnrolment() !=null)
				{
					if(qual.getUnitStandard().getLastDateForEnrolment().before(new Date())){
						qual.setUnitStandardExpired(true);
					}
					else
					{
						qual.setUnitStandardExpired(false);
					}
				}
			}
		}
		
		return legacyAssessorUnitStandardList;
	}

	public List<LegacyAssessorUnitStandard> findByAssessorIdNumberRegNo(String entry) throws Exception {
		return dao.findByAssessorIdNumberRegNo(entry);
	}

	public Integer countAllLegacyAssessorUnitStandardNotProcessed() throws Exception {
		return dao.countAllLegacyAssessorUnitStandardNotProcessed();
	}

	public List<LegacyAssessorUnitStandard> allLegacyAssessorUnitStandardNotProcessed(int numberOfEntries)
			throws Exception {
		return dao.allLegacyAssessorUnitStandardNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyAssessorUnitStandardNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyAssessorUnitStandard> li = allLegacyAssessorUnitStandardNotProcessed(1000);
				for (LegacyAssessorUnitStandard legacyAssessorUnitStandard : li) {
					// ID Validation
					if (legacyAssessorUnitStandard.getAssessorid() != null
							&& !legacyAssessorUnitStandard.getAssessorid().trim().isEmpty()) {
						try {
							legacyAssessorUnitStandard.setValidRsaIdNumber(
									GenericUtility.checkRsaId(legacyAssessorUnitStandard.getAssessorid().trim()));
						} catch (Exception e) {
							legacyAssessorUnitStandard.setValidRsaIdNumber(false);
						}
					}
					// on home affairs file
					try {
						Integer entriesFound = homeAffairsService
								.findByDhaIdNumber(legacyAssessorUnitStandard.getAssessorid().trim());
						if (entriesFound > 0) {
							legacyAssessorUnitStandard.setAppearsOnHomeAffairsData(true);
						} else {
							legacyAssessorUnitStandard.setAppearsOnHomeAffairsData(false);
						}
					} catch (Exception e) {
						legacyAssessorUnitStandard.setAppearsOnHomeAffairsData(false);
					}
					if (legacyAssessorUnitStandard.getUnitStdCode() != null
							&& !legacyAssessorUnitStandard.getUnitStdCode().isEmpty()) {
						try {
							Integer code = Integer.valueOf(legacyAssessorUnitStandard.getUnitStdCode().trim());
							UnitStandards unitStandard = unitStandardsService.findByUnitStandartId(code);
							if (unitStandard != null && unitStandard.getId() != null) {
								legacyAssessorUnitStandard.setUnitStandard(unitStandard);
							}
						} catch (Exception e) {
						}
					}
					legacyAssessorUnitStandard.setProcessed(true);
					updateList.add(legacyAssessorUnitStandard);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyAssessorUnitStandardNotProcessed();
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
