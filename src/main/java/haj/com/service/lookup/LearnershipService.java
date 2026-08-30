package haj.com.service.lookup;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.lookup.LearnershipDAO;
import haj.com.entity.LearnershipDevelopmentRegistration;
import haj.com.entity.lookup.Etqa;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.Qualification;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class LearnershipService.
 */
public class LearnershipService extends AbstractService {
	
	/** The dao. */
	private LearnershipDAO dao = new LearnershipDAO();
	
	private static LearnershipService learnershipService;
	public static synchronized LearnershipService instance() {
		if (learnershipService == null) {
			learnershipService = new LearnershipService();
		}
		return learnershipService;
	}

	/** The Service Levels */
	private EtqaService service = new EtqaService();
	private LearnershipUnitStandardsService learnershipUnitStandardsService = new LearnershipUnitStandardsService();
	
	/**
	 * Get all Learnership.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Learnership}
	 * @throws Exception the exception
	 * @see   Learnership
	 */
	public List<Learnership> allLearnership() throws Exception {
	  	return populateAdditionalInformationList(dao.allLearnership());
	}


	/**
	 * Create or update Learnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     Learnership
	 */
	public void create(Learnership entity) throws Exception {
		boolean deleteAssignedUnitStandards = false;

		// check if previous qualification assigned
		if (entity.getOrginalQualificationAssigned() == null || entity.getOrginalQualificationAssigned().getId() == null) {
			entity.setOrginalQualificationAssigned(entity.getQualification());
			deleteAssignedUnitStandards = true;
		} else {
			if (!entity.getOrginalQualificationAssigned().getId().equals(entity.getQualification().getId())) {
				entity.setOrginalQualificationAssigned(entity.getQualification());
				deleteAssignedUnitStandards = true;
			} else {
				deleteAssignedUnitStandards = false;
			}
		}

		if (dao.findUniqueSetmisCode(entity) != null)
			throw new Exception("SETMIS Code must be unique");
		if (entity.getId() == null) {
			entity.setCreateDate(new java.util.Date());
			this.dao.create(entity);
		} else 
			this.dao.update(entity);
		
		// deletes assigned unit standards
		if (deleteAssignedUnitStandards) {
			learnershipUnitStandardsService.deleteEntriesByLearnership(entity);
		}
	}
	
	public Learnership findUniqueSetmisCode(Learnership learnership) throws Exception {
		return dao.findUniqueSetmisCode(learnership);
	}


	/**
	 * Update  Learnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Learnership
	 */
	public void update(Learnership entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  Learnership.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    Learnership
	 */
	public void delete(Learnership entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Learnership}
	 * @throws Exception the exception
	 * @see    Learnership
	 */
	public Learnership findByKey(long id) throws Exception {
       return populateAdditionalInformation(dao.findByKey(id));
	}

	/**
	 * Find Learnership by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.Learnership}
	 * @throws Exception the exception
	 * @see    Learnership
	 */
	public List<Learnership> findByName(String desc) throws Exception {
		return populateAdditionalInformationList(dao.findByName(desc));
	}
	
	/**
	 * Lazy load Learnership.
	 *
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.Learnership}
	 * @throws Exception the exception
	 */
	public List<Learnership> allLearnership(int first, int pageSize) throws Exception {
		return populateAdditionalInformationList(dao.allLearnership(Long.valueOf(first), pageSize));
	}
		
	
	/**
	 * Get count of Learnership for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the Learnership
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(Learnership.class);
	}
	
    /**
     * Lazy load Learnership with pagination, filter, sorting.
     *
     * @author TechFinium
     * @param class1 Learnership class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.Learnership} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<Learnership> allLearnership(Class<Learnership> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList(( List<Learnership>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters));
	
	}
	
    /**
     * Get count of Learnership for lazy load with filters.
     *
     * @author TechFinium
     * @param entity Learnership class
     * @param filters the filters
     * @return Number of rows in the Learnership entity
     * @throws Exception the exception
     */	
	public int count(Class<Learnership> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}


	public void createLearnership(LearnershipDevelopmentRegistration entity) throws Exception {
		Learnership learnership = new Learnership();
		learnership.setCode(entity.getRegistrationNumber());
		learnership.setQualification(entity.getQualification());
		learnership.setOfoCodes(entity.getOfoCodes());
		learnership.setOrginalQualificationAssigned(entity.getQualification());
		
		if(entity.getRegistrationNumber() !=null && entity.getRegistrationNumber().equals(""))		{
			learnership.setCode(entity.getRegistrationNumber());
		}
		Etqa etqa =null;
		if(entity.getQualification()!=null && entity.getQualification().getEtqaid()!=null) {
			etqa=service.findByCode(entity.getQualification().getEtqaid());
		}
		if(etqa!=null) {
			learnership.setEtqa(etqa);
		}
		create(learnership);
	}


	public List<Learnership> findByQualification(Qualification qualification) throws Exception{
		return populateAdditionalInformationList(dao.findByQualification(qualification.getId()));
	}
	
	public List<Learnership> findAllQualification() throws Exception{
		return populateAdditionalInformationList(dao.findAllQualification());
	}
	
	public List<Learnership> findAllQualification(String desc) throws Exception{
		return populateAdditionalInformationList(dao.findAllQualification(desc));
	}
	
	public List<Learnership> completeLearnershipQualificationLastDateOfEnrollment(String desc, Date lastDateForEnrolment) throws Exception{
		return populateAdditionalInformationList(dao.completeLearnershipQualificationLastDateOfEnrollment(desc, lastDateForEnrolment));
	}	
	
	public Learnership findByCode(String code) throws Exception {
		return populateAdditionalInformation(dao.findByCode(code));
	}
	
	public List<Learnership> findAllLearnership(String code) throws Exception {
		return dao.findAllLearnership(code);
	}	

	public List<Learnership> populateAdditionalInformationList(List<Learnership> learnershipList) throws Exception {
		for (Learnership learnership : learnershipList) {
			populateAdditionalInformation(learnership);
		}
		return learnershipList;
	}

	public Learnership populateAdditionalInformation(Learnership learnership) throws Exception {
		if (learnership.getId() != null) {
			learnership.setLearnershipUnitStandards(learnershipUnitStandardsService.findByLearnership(learnership));
		}
		return learnership;
	}
	
	public Learnership findByLearnershipCode(String code) throws Exception {
		return dao.findByCode(code);
	}
	
	public Learnership findByLearnershipCode(String code, String qualificationCode) throws Exception {
		return dao.findByCode(code, qualificationCode);
	}

	public int countLearnershipByCode(String newCode) throws Exception {
		return dao.countLearnershipByCode(newCode);
	}
	
}
