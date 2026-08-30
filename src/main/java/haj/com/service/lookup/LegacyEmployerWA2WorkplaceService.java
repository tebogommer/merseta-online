package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.LegacyEmployerWA2WorkplaceDAO;
import haj.com.entity.lookup.LegacyBursary;
import haj.com.entity.lookup.LegacyEmployerWA2Workplace;
import haj.com.entity.lookup.LegacyOrganisationSites;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.GenericUtility;

public class LegacyEmployerWA2WorkplaceService extends AbstractService {
	/** The dao. */
	private LegacyEmployerWA2WorkplaceDAO dao = new LegacyEmployerWA2WorkplaceDAO();
	
	private LegacyOrganisationSitesService legacyOrganisationSitesService = new LegacyOrganisationSitesService();
	private LegacyEmployerWA2LearnershipService legacyEmployerWA2LearnershipService = new LegacyEmployerWA2LearnershipService();
	private LegacyEmployerWA2QualificationService legacyEmployerWA2QualificationService = new LegacyEmployerWA2QualificationService();
	private LegacyEmployerWA2SkillsProgrammeService legacyEmployerWA2SkillsProgrammeService = new LegacyEmployerWA2SkillsProgrammeService();
	private LegacyEmployerWA2TradeService legacyEmployerWA2TradeService = new LegacyEmployerWA2TradeService();
	private LegacyEmployerWA2UnitStandardService legacyEmployerWA2UnitStandardService = new LegacyEmployerWA2UnitStandardService();


	/**
	 * Get all LegacyEmployerWA2Workplace
 	 * @author TechFinium 
 	 * @see   LegacyEmployerWA2Workplace
 	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Workplace}
	 * @throws Exception the exception
 	 */
	public List<LegacyEmployerWA2Workplace> allLegacyEmployerWA2Workplace() throws Exception {
	  	return dao.allLegacyEmployerWA2Workplace();
	}


	/**
	 * Create or update LegacyEmployerWA2Workplace.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     LegacyEmployerWA2Workplace
	 */
	public void create(LegacyEmployerWA2Workplace entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  LegacyEmployerWA2Workplace.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyEmployerWA2Workplace
	 */
	public void update(LegacyEmployerWA2Workplace entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  LegacyEmployerWA2Workplace.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    LegacyEmployerWA2Workplace
	 */
	public void delete(LegacyEmployerWA2Workplace entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.LegacyEmployerWA2Workplace}
	 * @throws Exception the exception
	 * @see    LegacyEmployerWA2Workplace
	 */
	public LegacyEmployerWA2Workplace findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find LegacyEmployerWA2Workplace by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Workplace}
	 * @throws Exception the exception
	 * @see    LegacyEmployerWA2Workplace
	 */
	public List<LegacyEmployerWA2Workplace> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load LegacyEmployerWA2Workplace
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.LegacyEmployerWA2Workplace}
	 * @throws Exception the exception
	 */
	public List<LegacyEmployerWA2Workplace> allLegacyEmployerWA2Workplace(int first, int pageSize) throws Exception {
		return dao.allLegacyEmployerWA2Workplace(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of LegacyEmployerWA2Workplace for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the LegacyEmployerWA2Workplace
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(LegacyEmployerWA2Workplace.class);
	}
	
    /**
     * Lazy load LegacyEmployerWA2Workplace with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 LegacyEmployerWA2Workplace class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.LegacyEmployerWA2Workplace} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<LegacyEmployerWA2Workplace> allLegacyEmployerWA2Workplace(Class<LegacyEmployerWA2Workplace> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<LegacyEmployerWA2Workplace>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of LegacyEmployerWA2Workplace for lazy load with filters
     * @author TechFinium 
     * @param entity LegacyEmployerWA2Workplace class
     * @param filters the filters
     * @return Number of rows in the LegacyEmployerWA2Workplace entity
     * @throws Exception the exception     
     */	
	public int count(Class<LegacyEmployerWA2Workplace> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public void deleteUploadedEntries() throws Exception{
	    List<LegacyEmployerWA2Workplace> allEntries = allLegacyEmployerWA2Workplace();
	    dao.deleteBatch((List<IDataEntity>) (List<?>) allEntries, 1000);
	}

	@SuppressWarnings("unchecked")
	public void saveCsvUploadData(List<LegacyEmployerWA2Workplace> csvData) throws Exception {
		this.dao.createBatch((List<IDataEntity>) (List<?>) csvData);
	}
	
	public Integer countAllResults() throws Exception {
		return dao.countAllResults();
	}

	public Integer countAllLegacyEmployerWA2WorkplaceNotProcessed() throws Exception {
		return dao.countAllLegacyEmployerWA2WorkplaceNotProcessed();
	}
	
	public List<LegacyEmployerWA2Workplace> allLegacyEmployerWA2WorkplaceNotProcessed(int numberOfEntries) throws Exception {
		return dao.allLegacyEmployerWA2WorkplaceNotProcessed(numberOfEntries);
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
			Integer count = countAllLegacyEmployerWA2WorkplaceNotProcessed();	
			List<IDataEntity> updateList = new ArrayList<>();
			while (count != 0) {
				updateList = new ArrayList<>();
				List<LegacyEmployerWA2Workplace> li = allLegacyEmployerWA2WorkplaceNotProcessed(1000);
				for (LegacyEmployerWA2Workplace legacyEmployerWA2Workplace : li) {
					// On Sites Table
					if (legacyEmployerWA2Workplace.getLinkedSdl() != null && !legacyEmployerWA2Workplace.getLinkedSdl().isEmpty()) {
						try {
							int countSite = legacyOrganisationSitesService.countBySdlNumber(legacyEmployerWA2Workplace.getLinkedSdl().trim());
							if (countSite == 1) {
								LegacyOrganisationSites los = legacyOrganisationSitesService.findBySdlNumber(legacyEmployerWA2Workplace.getLinkedSdl().trim());
								if (los != null && los.getId() != null) {
									// legacyEmployerWA2Workplace.setLegacyOrganisationSites(los);
								}
							}
						} catch (Exception e) {
							logger.fatal(e);
						}
					}
					
					legacyEmployerWA2Workplace.setProcessed(true);
					
					updateList.add(legacyEmployerWA2Workplace);
				}
				li = null;
				if (updateList.size() != 0) {
					dao.updateBatch(updateList);
				}
				count = countAllLegacyEmployerWA2WorkplaceNotProcessed();
			}
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Employer WA2 Workplace Process", "Processing Complete on site: " + HAJConstants.PL_LINK, null);
			}
		} catch (Exception e) {
			logger.fatal(e);
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Legacy Data: Employer WA2 Workplace Process", "Error on processing on site: " + HAJConstants.PL_LINK + ". Error: " + e.getMessage(), null);
			}
		}
	}

	public void populateData(LegacyEmployerWA2Workplace legacyEmployerWA2Workplace) throws Exception {
		String sdlNo = "";
		String siteNumber = "";
		/**if(legacyEmployerWA2Workplace.getSdlNo() != null) {
			sdlNo = legacyEmployerWA2Workplace.getSdlNo();
		}else if(legacyEmployerWA2Workplace.getLinkedSdl() != null) {
			sdlNo = legacyEmployerWA2Workplace.getLinkedSdl();
		}*/
		
		if(legacyEmployerWA2Workplace.getLinkedSdl() != null) {
			sdlNo = legacyEmployerWA2Workplace.getLinkedSdl();
			siteNumber = legacyEmployerWA2Workplace.getSdlNo();
			
			//legacyEmployerWA2Workplace.setLegacyEmployerWA2Learnership(legacyEmployerWA2LearnershipService.findDistinctByLevy(sdlNo));
			legacyEmployerWA2Workplace.setLegacyEmployerWA2Learnership(legacyEmployerWA2LearnershipService.findDistinctByLevy(sdlNo, siteNumber));
			
			//legacyEmployerWA2Workplace.setLegacyEmployerWA2Qualification(legacyEmployerWA2QualificationService.findDistinctByLevy(sdlNo));
			legacyEmployerWA2Workplace.setLegacyEmployerWA2Qualification(legacyEmployerWA2QualificationService.findDistinctByLevy(sdlNo, siteNumber));
			
			//legacyEmployerWA2Workplace.setLegacyEmployerWA2SkillsProgramme(legacyEmployerWA2SkillsProgrammeService.findDistinctByLevy(sdlNo));
			legacyEmployerWA2Workplace.setLegacyEmployerWA2SkillsProgramme(legacyEmployerWA2SkillsProgrammeService.findDistinctByLevy(sdlNo, siteNumber));
			
			//legacyEmployerWA2Workplace.setLegacyEmployerWA2Trade(legacyEmployerWA2TradeService.findDistinctByLevy(sdlNo));
			legacyEmployerWA2Workplace.setLegacyEmployerWA2Trade(legacyEmployerWA2TradeService.findDistinctByLevy(sdlNo, siteNumber));
			
			//legacyEmployerWA2Workplace.setLegacyEmployerWA2UnitStandard(legacyEmployerWA2UnitStandardService.findDistinctByLevy(sdlNo));
			legacyEmployerWA2Workplace.setLegacyEmployerWA2UnitStandard(legacyEmployerWA2UnitStandardService.findDistinctByLevy(sdlNo, siteNumber));
		}		
		
	}
}
