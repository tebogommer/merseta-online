package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.dao.UnitStandardsDAO;
import haj.com.entity.CompanyLearners;
import haj.com.entity.enums.InterventionTypeEnum;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SaqaUsQualification;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;

// TODO: Auto-generated Javadoc
/**
 * The Class UnitStandardsService.
 */
public class UnitStandardsService extends AbstractService {
	
	/** The dao. */
	private UnitStandardsDAO dao = new UnitStandardsDAO();
	
	private static UnitStandardsService unitStandardsService;
	public static synchronized UnitStandardsService instance() {
		if (unitStandardsService == null) {
			unitStandardsService = new UnitStandardsService();
		}
		return unitStandardsService;
	}

	/**
	 * Get all UnitStandards.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.lookup.UnitStandards}
	 * @throws Exception
	 *             the exception
	 * @see UnitStandards
	 */
	public List<UnitStandards> allUnitStandards() throws Exception {
		return dao.allUnitStandards();
	}

	/**
	 * Create or update UnitStandards.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see UnitStandards
	 */
	public void create(UnitStandards entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	/**
	 * Update UnitStandards.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see UnitStandards
	 */
	public void update(UnitStandards entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete UnitStandards.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see UnitStandards
	 */
	public void delete(UnitStandards entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.lookup.UnitStandards}
	 * @throws Exception
	 *             the exception
	 * @see UnitStandards
	 */
	public UnitStandards findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find UnitStandards by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.lookup.UnitStandards}
	 * @throws Exception
	 *             the exception
	 * @see UnitStandards
	 */
	public List<UnitStandards> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load UnitStandards.
	 *
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.lookup.UnitStandards}
	 * @throws Exception
	 *             the exception
	 */
	public List<UnitStandards> allUnitStandards(int first, int pageSize) throws Exception {
		return dao.allUnitStandards(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of UnitStandards for lazy load.
	 *
	 * @author TechFinium
	 * @return Number of rows in the UnitStandards
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(UnitStandards.class);
	}

	/**
	 * Lazy load UnitStandards with pagination, filter, sorting.
	 *
	 * @author TechFinium
	 * @param class1
	 *            UnitStandards class
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
	 * @return a list of {@link haj.com.entity.lookup.UnitStandards} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<UnitStandards> allUnitStandards(Class<UnitStandards> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<UnitStandards>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);

	}

	/**
	 * Get count of UnitStandards for lazy load with filters.
	 *
	 * @author TechFinium
	 * @param entity
	 *            UnitStandards class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the UnitStandards entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<UnitStandards> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}

	/**
	 * Find by title.
	 *
	 * @param description
	 *            the description
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	public List<UnitStandards> findByTitle(String description) throws Exception {
		return dao.findByTitle(description);
	}

	public List<UnitStandards> findByTitle(String description, Integer qualificationID) throws Exception {
		return dao.findByTitle(description, qualificationID);
	}

	public List<UnitStandards> findByQualification(Integer qualificationID) throws Exception {
		return dao.findByQualification(qualificationID);
	}

	public List<UnitStandards> findBySkillsSet(Long qualificationID) throws Exception {
		return dao.findBySkillsSet(qualificationID);
	}

	public List<UnitStandards> findBySkillsProgram(Long qualificationID) throws Exception {
		return dao.findBySkillsProgram(qualificationID);
	}
	
	public List<UnitStandards> findUnitStandardsBySkillsProgram(Long skillsProgramID) throws Exception {
		return dao.findUnitStandardsBySkillsProgram(skillsProgramID);
	}

	public List<UnitStandards> resolveCompanyLearners(CompanyLearners companyLearners) throws Exception {
		List<UnitStandards> unitStandards = new ArrayList<>();
		if(companyLearners.getUnitStandard()!=null){
			unitStandards.add(companyLearners.getUnitStandard());
		}else if (companyLearners.getQualification() != null && companyLearners.getQualification().getCode()!=null) {
			unitStandards.addAll(findByQualification(companyLearners.getQualification().getCode()));
		} else if (companyLearners.getSkillsProgram() != null&& companyLearners.getSkillsProgram().getId()!=null) {
			unitStandards.addAll(findUnitStandardsBySkillsProgram(companyLearners.getSkillsProgram().getId()));
		} else if (companyLearners.getSkillsSet() != null && companyLearners.getSkillsSet().getId()!=null) {
			unitStandards.addAll(findBySkillsSet(companyLearners.getSkillsSet().getId()));
		} else if (companyLearners.getInterventionType().getInterventionTypeEnum() == InterventionTypeEnum.Learnership) {
			unitStandards.addAll(findByQualification(companyLearners.getLearnership().getQualification().getCode()));
		}
		return unitStandards;
	}
	
	public List<UnitStandards> resolveUnitStandardForqualification(CompanyLearners companyLearners) throws Exception {
		List<UnitStandards> unitStandards = new ArrayList<>();
		if (companyLearners.getQualification() != null && companyLearners.getQualification().getCode()!=null) {
			unitStandards.addAll(findByQualification(companyLearners.getQualification().getCode()));
		} else if (companyLearners.getSkillsProgram() != null&& companyLearners.getSkillsProgram().getId()!=null) {
			unitStandards.addAll(findUnitStandardsBySkillsProgram(companyLearners.getSkillsProgram().getId()));
		} else if (companyLearners.getSkillsSet() != null && companyLearners.getSkillsSet().getId()!=null) {
			unitStandards.addAll(findBySkillsSet(companyLearners.getSkillsSet().getId()));
		} else if(companyLearners.getUnitStandard()!=null){
			unitStandards.add(companyLearners.getUnitStandard());
		}
		return unitStandards;
	}

	public List<SaqaUsQualification> findByUsQualification(Integer qualificationID) throws Exception {
		return dao.findByUsQualification(qualificationID);
	}

	public UnitStandards findByUnitStandartId(int id) throws Exception {
		// TODO Auto-generated method stub
		return dao.findByUnitStandartId(id);
	}
	
	public UnitStandards findByUnitStandartCodeString(String codeString) throws Exception {
		return dao.findByUnitStandartCodeString(codeString);
	}

	@SuppressWarnings("unchecked")
	public List<UnitStandards> allUnitStandardsByTrainingProviderLink(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey) throws Exception {
		String hql = "select o from UnitStandards o where o.id in (select x.unitStandard.id from CompanyUnitStandard x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.unitStandard <> null)";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		return (List<UnitStandards>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}

	public int countAllUnitStandardsByTrainingProviderLink(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from UnitStandards o where o.id in (select x.unitStandard.id from CompanyUnitStandard x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.unitStandard <> null)";
		return dao.countWhere(filters, hql);
	}

	public List<UnitStandards> findByTitleAndBeforeLastEnrolmentDate(String description, Date lastDateForEnrolment) throws Exception {
		return dao.findByTitleAndBeforeLastEnrolmentDate(description, lastDateForEnrolment);
	}

	public List<UnitStandards> findBeforeLastEnrolmentDate(Date lastDateForEnrolment) throws Exception {
		return dao.findBeforeLastEnrolmentDate(lastDateForEnrolment);
	}

	public List<UnitStandards> findByQualificationList(List<Qualification> qualificationList) throws Exception{
		return dao.findByQualificationList(qualificationList);
	}

	public List<UnitStandards> findByQualification(Qualification qualification) throws Exception {
		if(qualification.getIslearningprogramme().equalsIgnoreCase("YES") && qualification.getLearningprogrammequal() != null) {				
			Qualification qualTemp = dao.findByCode(Integer.valueOf(qualification.getLearningprogrammequal().trim()));
			return dao.findByQualification(qualTemp.getCode());
		}else {
			return dao.findByQualification(qualification.getCode());
		}
	}
	
	
	public List<UnitStandards> findMerSETAUnitStandras(String desc) throws Exception {
		return dao.findMerSETAUnitStandras(desc);
	}
	
	public List<UnitStandards> findByTitleLinkedToQualification(String description, Integer qualificationId) throws Exception {
		return dao.findByTitleLinkedToQualification(description, qualificationId);
	}
}
