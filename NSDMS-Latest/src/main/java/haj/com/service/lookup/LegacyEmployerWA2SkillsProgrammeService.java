package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyEmployerWA2SkillsProgrammeDAO;
import haj.com.entity.lookup.LegacyEmployerWA2SkillsProgramme;
import haj.com.entity.lookup.LegacyEmployerWA2SkillsProgramme;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyEmployerWA2SkillsProgrammeService extends AbstractService {
	/** The dao. */
	private LegacyEmployerWA2SkillsProgrammeDAO dao = new LegacyEmployerWA2SkillsProgrammeDAO();
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();


	/**
	 * Get all LegacyEmployerWA2SkillsProgramme
 	 * @author TechFinium 
 	 * @see   LegacyEmployerWA2SkillsProgramme
 	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2SkillsProgramme}
	 * @throws Exception the exception
 	 */
	public List<LegacyEmployerWA2SkillsProgramme> allLegacyEmployerWA2SkillsProgramme() throws Exception {
	  	return dao.allLegacyEmployerWA2SkillsProgramme();
	}


	/**
	 * Create or update LegacyEmployerWA2SkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LegacyEmployerWA2SkillsProgramme
	 */
	public void create(LegacyEmployerWA2SkillsProgramme entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LegacyEmployerWA2SkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyEmployerWA2SkillsProgramme
	 */
	public void update(LegacyEmployerWA2SkillsProgramme entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LegacyEmployerWA2SkillsProgramme.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyEmployerWA2SkillsProgramme
	 */
	public void delete(LegacyEmployerWA2SkillsProgramme entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyEmployerWA2SkillsProgramme}
	 * @throws Exception the exception
	 * @see    LegacyEmployerWA2SkillsProgramme
	 */
	public LegacyEmployerWA2SkillsProgramme findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LegacyEmployerWA2SkillsProgramme by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2SkillsProgramme}
	 * @throws Exception the exception
	 * @see    LegacyEmployerWA2SkillsProgramme
	 */
	public List<LegacyEmployerWA2SkillsProgramme> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LegacyEmployerWA2SkillsProgramme
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2SkillsProgramme}
	 * @throws Exception the exception
	 */
	public List<LegacyEmployerWA2SkillsProgramme> allLegacyEmployerWA2SkillsProgramme(int first, int pageSize) throws Exception {
		return dao.allLegacyEmployerWA2SkillsProgramme(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LegacyEmployerWA2SkillsProgramme for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the LegacyEmployerWA2SkillsProgramme
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LegacyEmployerWA2SkillsProgramme.class);
	}
	
    /**
     * Lazy load LegacyEmployerWA2SkillsProgramme with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 LegacyEmployerWA2SkillsProgramme class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LegacyEmployerWA2SkillsProgramme} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2SkillsProgramme> allLegacyEmployerWA2SkillsProgramme(Class<LegacyEmployerWA2SkillsProgramme> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<LegacyEmployerWA2SkillsProgramme>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of LegacyEmployerWA2SkillsProgramme for lazy load with filters
     * @author TechFinium 
     * @param entity LegacyEmployerWA2SkillsProgramme class
     * @param filters the filters
     * @return Number of rows in the LegacyEmployerWA2SkillsProgramme entity
     * @throws Exception the exception     
     */	
	public int count(Class<LegacyEmployerWA2SkillsProgramme> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception{
	    List<LegacyEmployerWA2SkillsProgramme> allEntries = allLegacyEmployerWA2SkillsProgramme();
	    dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyEmployerWA2SkillsProgramme> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}

	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}
	
	public Integer countAllLegacyEmployerWA2SkillsProgrammeNotProcessed() throws Exception {
		return dao.countAllLegacyEmployerWA2SkillsProgrammeNotProcessed();
	}

	public List<LegacyEmployerWA2SkillsProgramme> allLegacyEmployerWA2SkillsProgrammeNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyEmployerWA2SkillsProgrammeNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyEmployerWA2SkillsProgrammeNotProcessed();
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyEmployerWA2SkillsProgramme> li = allLegacyEmployerWA2SkillsProgrammeNotProcessed(1000);
				for (LegacyEmployerWA2SkillsProgramme entry : li) {


					
					if (entry.getSdlNo() != null && !entry.getSdlNo().isEmpty()) {
						try {
							int countSite = legacyOrganisationSitesService.countBySdlNumber(entry.getSdlNo().trim());
							if (countSite == 1) {
								LegacyOrganisationSites los = legacyOrganisationSitesService.findBySdlNumber(entry.getSdlNo().trim());
								if (los != null && los.getId() != null) {
//									entry.setLegacyOrganisationSites(los);
								}
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}

					entry.setProcessed(true);

					updateList.add(entry);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyEmployerWA2SkillsProgrammeNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy WA2SkillsProgramme Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
			logger.info("validateRsaIdNumbers() Complete");
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Legacy WA2SkillsProgramme Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}
	
	public List<LegacyEmployerWA2SkillsProgramme> findByLevy(String desc) throws Exception {
		return dao.findByLevy(desc);
	}
	
	public List<LegacyEmployerWA2SkillsProgramme> findDistinctByLevy(String desc) throws Exception {
		return dao.findDistinctByLevy(desc);
	}
	
	public List<LegacyEmployerWA2SkillsProgramme> findDistinctByLevy(String desc, String sdlNo) throws Exception {
		return dao.findDistinctByLevy(desc, sdlNo);
	}
}
