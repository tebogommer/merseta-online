package haj.com.service;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.MandatoryGrantRecommendationDAO;
import haj.com.entity.MandatoryGrant;
import haj.com.entity.MandatoryGrantRecommendation;
import haj.com.framework.AbstractService;

public class MandatoryGrantRecommendationService extends AbstractService {
	/** The dao. */
	private MandatoryGrantRecommendationDAO dao = new MandatoryGrantRecommendationDAO();

	/**
	 * Get all MandatoryGrantRecommendation
 	 * @author TechFinium 
 	 * @see   MandatoryGrantRecommendation
 	 * @return a list of {@link haj.com.entity.MandatoryGrantRecommendation}
	 * @throws Exception the exception
 	 */
	public List<MandatoryGrantRecommendation> allMandatoryGrantRecommendation() throws Exception {
	  	return dao.allMandatoryGrantRecommendation();
	}


	/**
	 * Create or update MandatoryGrantRecommendation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     MandatoryGrantRecommendation
	 */
	public void create(MandatoryGrantRecommendation entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}
	
	public void clearUpdateAdditionalEntriesBeforeSaving(MandatoryGrant mandatoryGrant, MandatoryGrantRecommendation entity, boolean skillsSet, boolean skillsProgram, boolean shortCreditBearing, boolean nonCreditBearing) throws Exception{
		if (mandatoryGrant != null && entity != null) {
			// check if learners provided, if not populate from main entry
			if (entity.getQuantity() == null) {
				entity.setQuantity(mandatoryGrant.getAmount());
			}
			
			// check if they altered intervention type
			if (entity.getInterventionType() == null) {
				entity.setInterventionType(mandatoryGrant.getInterventionType());
				if (mandatoryGrant.getQualification() != null) {
					entity.setQualification(mandatoryGrant.getQualification());
				}
				if (mandatoryGrant.getSkillsSet() != null) {
					entity.setSkillsSet(mandatoryGrant.getSkillsSet());
				}
				if (mandatoryGrant.getSkillsProgram() != null) {
					entity.setSkillsProgram(mandatoryGrant.getSkillsProgram());
				}
				if (mandatoryGrant.getNonCreditBearingIntervationTitle() != null) {
					entity.setNonCreditBearingIntervationTitle(mandatoryGrant.getNonCreditBearingIntervationTitle());
				}
				if (mandatoryGrant.getUnitStandard() != null) {
					entity.setUnitStandards(mandatoryGrant.getUnitStandard());
				}
			} else {
				
				// follow business rules for selection display and null fields not required based on intervetion type
				if (!skillsSet && !skillsProgram && !shortCreditBearing && !nonCreditBearing) {
					entity.setSkillsSet(null);
					entity.setSkillsProgram(null);
					entity.setUnitStandards(null);
					entity.setNonCreditBearingIntervationTitle(null);
				} else {
					if (skillsSet) {
						entity.setSkillsProgram(null);
						entity.setUnitStandards(null);
						entity.setQualification(null);
						entity.setNonCreditBearingIntervationTitle(null);
					}
					if (skillsProgram) {
						entity.setSkillsSet(null);
						entity.setUnitStandards(null);
						entity.setQualification(null);
						entity.setNonCreditBearingIntervationTitle(null);
					}
					if (shortCreditBearing) {
						entity.setSkillsSet(null);
						entity.setSkillsProgram(null);
						entity.setQualification(null);
						entity.setNonCreditBearingIntervationTitle(null);
					}
					if (nonCreditBearing) {
						entity.setSkillsSet(null);
						entity.setSkillsProgram(null);
						entity.setUnitStandards(null);
						entity.setQualification(null);
					}
				}
			}
		}
	}


	/**
	 * Update  MandatoryGrantRecommendation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    MandatoryGrantRecommendation
	 */
	public void update(MandatoryGrantRecommendation entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  MandatoryGrantRecommendation.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    MandatoryGrantRecommendation
	 */
	public void delete(MandatoryGrantRecommendation entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.MandatoryGrantRecommendation}
	 * @throws Exception the exception
	 * @see    MandatoryGrantRecommendation
	 */
	public MandatoryGrantRecommendation findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find MandatoryGrantRecommendation by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.MandatoryGrantRecommendation}
	 * @throws Exception the exception
	 * @see    MandatoryGrantRecommendation
	 */
	public List<MandatoryGrantRecommendation> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load MandatoryGrantRecommendation
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.MandatoryGrantRecommendation}
	 * @throws Exception the exception
	 */
	public List<MandatoryGrantRecommendation> allMandatoryGrantRecommendation(int first, int pageSize) throws Exception {
		return dao.allMandatoryGrantRecommendation(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of MandatoryGrantRecommendation for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the MandatoryGrantRecommendation
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(MandatoryGrantRecommendation.class);
	}
	
    /**
     * Lazy load MandatoryGrantRecommendation with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 MandatoryGrantRecommendation class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.MandatoryGrantRecommendation} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantRecommendation> allMandatoryGrantRecommendation(Class<MandatoryGrantRecommendation> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<MandatoryGrantRecommendation>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of MandatoryGrantRecommendation for lazy load with filters
     * @author TechFinium 
     * @param entity MandatoryGrantRecommendation class
     * @param filters the filters
     * @return Number of rows in the MandatoryGrantRecommendation entity
     * @throws Exception the exception     
     */	
	public int count(Class<MandatoryGrantRecommendation> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public List<MandatoryGrantRecommendation> findByMG(MandatoryGrant mandatoryGrant) throws Exception {
		return dao.findByMG(mandatoryGrant);
	}
	
	public MandatoryGrantRecommendation findByMandatoryGrant(MandatoryGrant mandatoryGrant) throws Exception {
		MandatoryGrantRecommendation mandatoryGrantRecommendation= null;
		List<MandatoryGrantRecommendation>list = findByMG(mandatoryGrant);
		for (MandatoryGrantRecommendation mgr : list) {
			mandatoryGrantRecommendation = mgr;
			break;
		}
		return mandatoryGrantRecommendation;
	}
}
