package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyEmployerWA2TradeDAO;
import haj.com.entity.lookup.LegacyEmployerWA2Trade;
import haj.com.entity.lookup.LegacyExperiential;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyEmployerWA2TradeService extends AbstractService {
	/** The dao. */
	private LegacyEmployerWA2TradeDAO dao = new LegacyEmployerWA2TradeDAO();
	
	private QualificationService qualificationService = new QualificationService();

	/**
	 * Get all LegacyEmployerWA2Trade
 	 * @author TechFinium 
 	 * @see   LegacyEmployerWA2Trade
 	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Trade}
	 * @throws Exception the exception
 	 */
	public List<LegacyEmployerWA2Trade> allLegacyEmployerWA2Trade() throws Exception {
	  	return dao.allLegacyEmployerWA2Trade();
	}


	/**
	 * Create or update LegacyEmployerWA2Trade.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LegacyEmployerWA2Trade
	 */
	public void create(LegacyEmployerWA2Trade entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LegacyEmployerWA2Trade.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyEmployerWA2Trade
	 */
	public void update(LegacyEmployerWA2Trade entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LegacyEmployerWA2Trade.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyEmployerWA2Trade
	 */
	public void delete(LegacyEmployerWA2Trade entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyEmployerWA2Trade}
	 * @throws Exception the exception
	 * @see    LegacyEmployerWA2Trade
	 */
	public LegacyEmployerWA2Trade findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LegacyEmployerWA2Trade by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Trade}
	 * @throws Exception the exception
	 * @see    LegacyEmployerWA2Trade
	 */
	public List<LegacyEmployerWA2Trade> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LegacyEmployerWA2Trade
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Trade}
	 * @throws Exception the exception
	 */
	public List<LegacyEmployerWA2Trade> allLegacyEmployerWA2Trade(int first, int pageSize) throws Exception {
		return dao.allLegacyEmployerWA2Trade(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LegacyEmployerWA2Trade for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the LegacyEmployerWA2Trade
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LegacyEmployerWA2Trade.class);
	}
	
    /**
     * Lazy load LegacyEmployerWA2Trade with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 LegacyEmployerWA2Trade class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LegacyEmployerWA2Trade} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Trade> allLegacyEmployerWA2Trade(Class<LegacyEmployerWA2Trade> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<LegacyEmployerWA2Trade>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of LegacyEmployerWA2Trade for lazy load with filters
     * @author TechFinium 
     * @param entity LegacyEmployerWA2Trade class
     * @param filters the filters
     * @return Number of rows in the LegacyEmployerWA2Trade entity
     * @throws Exception the exception     
     */	
	public int count(Class<LegacyEmployerWA2Trade> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception{
	    List<LegacyEmployerWA2Trade> allEntries = allLegacyEmployerWA2Trade();
	    dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyEmployerWA2Trade> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public Integer countAllLegacyEmployerWA2TradeNotProcessed() throws Exception {
		return dao.countAllLegacyEmployerWA2TradeNotProcessed();
	}

	public List<LegacyEmployerWA2Trade> allLegacyEmployerWA2TradeNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyEmployerWA2TradeNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyEmployerWA2TradeNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyEmployerWA2Trade> li = allLegacyEmployerWA2TradeNotProcessed(1000);
				for (LegacyEmployerWA2Trade legacyEmployerWA2Trade : li) {
					// On SDLNo Table
					if (legacyEmployerWA2Trade.getSdlNo() != null && !legacyEmployerWA2Trade.getSdlNo().isEmpty()) {
						try {
							Qualification qua = qualificationService.findByCodeString(legacyEmployerWA2Trade.getSdlNo().trim());
							if (qua != null && qua.getId() != null) {
								legacyEmployerWA2Trade.setQualification(qua);
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}
					
					legacyEmployerWA2Trade.setProcessed(true);

					updateList.add(legacyEmployerWA2Trade);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyEmployerWA2TradeNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Employemr WA2 Trade Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy Employemr WA2 Trade Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
	public List<LegacyEmployerWA2Trade> findByLevy(String desc) throws Exception {
		return dao.findByLevy(desc);
	}
	
	public List<LegacyEmployerWA2Trade> findDistinctByLevy(String desc) throws Exception {
		return dao.findDistinctByLevy(desc);
	}
	
	public List<LegacyEmployerWA2Trade> findDistinctByLevy(String desc,String sdlNo) throws Exception {
		return dao.findDistinctByLevy(desc,sdlNo);
	}
}
