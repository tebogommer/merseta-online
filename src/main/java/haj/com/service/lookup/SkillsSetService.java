package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.SkillsSetDAO;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.SkillsRegistration;
import haj.com.entity.SkillsRegistrationQualificationUnitStandards;
import haj.com.entity.SkillsRegistrationUnitStandards;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.SkillsSetUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.service.SkillsRegistrationQualificationUnitStandardsService;

public class SkillsSetService extends AbstractService {
	/** The dao. */
	private SkillsSetDAO dao = new SkillsSetDAO();
	
	private static SkillsSetService skillsSetService;
	public static synchronized SkillsSetService instance() {
		if (skillsSetService == null) {
			skillsSetService = new SkillsSetService();
		}
		return skillsSetService;
	}

	/**
	 * Get all SkillsSet
	 * 
	 * @author TechFinium
	 * @see SkillsSet
	 * @return a list of {@link haj.com.entity.SkillsSet}
	 * @throws Exception
	 *             the exception
	 */
	public List<SkillsSet> allSkillsSet() throws Exception {
		return dao.allSkillsSet();
	}

	/**
	 * Create or update SkillsSet.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SkillsSet
	 */
	public void create(SkillsSet entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void create(SkillsSet entity, List<UnitStandards> unitStandards) throws Exception {
		if (entity.getId() == null) {
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			dataEntities.add(entity);
			for (UnitStandards unitStandard : unitStandards) {
				dataEntities.add(new SkillsSetUnitStandards(entity, unitStandard));
			}
			this.dao.createBatch(dataEntities);
		} else {
			this.dao.update(entity);

			List<SkillsSetUnitStandards> list = allSkillsSetUnitStandards(entity);

			dao.deleteBatch((List<IDataEntity>) (List<?>) list);

			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			for (UnitStandards unitStandard : unitStandards) {
				dataEntities.add(new SkillsSetUnitStandards(entity, unitStandard));
			}
			this.dao.createBatch(dataEntities);
		}
	}
	
	public void createSkillSetWithSkillRegistration(SkillsRegistration registration) throws Exception {
		

		List<SkillsRegistrationUnitStandards> skillsRegistrationUnitStandards = registration.getSkillsRegistrationUnitStandards();
		List<UnitStandards> unitStandards = new ArrayList<UnitStandards>();
		
		if(skillsRegistrationUnitStandards != null)
		{
			for(SkillsRegistrationUnitStandards skillsRegistrationUnitStandard: skillsRegistrationUnitStandards)
			{
				UnitStandards unitStandard = new UnitStandards();
				unitStandard = skillsRegistrationUnitStandard.getUnitStandards();
				unitStandards.add(unitStandard);
			}
		}
		
		SkillsSet skillSet = new SkillsSet();
		String title=registration.getProposedTitle();
		if(registration.getAmendedTitle() !=null && registration.getAmendedTitle().isEmpty()==false)
		{
			title=registration.getAmendedTitle() ;
		}
		skillSet.setDescription(title);
		skillSet.setQualification(registration.getQualification());	
		skillSet.setProgrammeID(registration.getRegistrationNumber());
		//skillSet.setCredits(registration.getQualification().getCredits());		
		create(skillSet, unitStandards);
	}
	
	public void createSkillsSetSkillBySkillsRegistration(SkillsRegistration registration) throws Exception{
		SkillsSet skillSet = new SkillsSet();
		String title=registration.getProposedTitle();
		if(registration.getAmendedTitle() !=null && registration.getAmendedTitle().isEmpty()==false)
		{
			title=registration.getAmendedTitle() ;
		}
		skillSet.setDescription(title);
		skillSet.setQualification(registration.getQualification());	
		skillSet.setProgrammeID(registration.getRegistrationNumber());
		
		SkillsRegistrationQualificationUnitStandardsService skillsRegistrationQualificationUnitStandardsService = new SkillsRegistrationQualificationUnitStandardsService();
		List<SkillsRegistrationQualificationUnitStandards> list = skillsRegistrationQualificationUnitStandardsService.allSkillsRegistrationQualificationUnitStandards(registration);
		List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
		
		dataEntities.add(skillSet);
		
		List<SkillsSetUnitStandards> skillsSetUnitStandards =  new ArrayList<>();
		if(list != null && list.size()>0) {
			for(SkillsRegistrationQualificationUnitStandards skillsRegistrationQualificationUnitStandards:list) {
				SkillsSetUnitStandards skillsSetUnitStandard = new SkillsSetUnitStandards();
				skillsSetUnitStandard.setSkillsSet(skillSet);
				skillsSetUnitStandard.setUnitStandards(skillsRegistrationQualificationUnitStandards.getUnitStandards());
				skillsSetUnitStandard.setQualification(skillsRegistrationQualificationUnitStandards.getQualification());
				skillsSetUnitStandards.add(skillsSetUnitStandard);
			}
		}	
		dataEntities.addAll(skillsSetUnitStandards);		
		if (skillSet.getId() == null) {
			this.dao.createBatch(dataEntities);
		}else {
			this.dao.update(skillSet);
			List<SkillsSetUnitStandards> deleteList = allSkillsSetUnitStandards(skillSet);
			dao.deleteBatch((List<IDataEntity>) (List<?>) deleteList);
			this.dao.createBatch(dataEntities);
		}
	}

	public List<SkillsSetUnitStandards> allSkillsSetUnitStandards(SkillsSet modules) throws Exception {
		return dao.allSkillsSetUnitStandards(modules);
	}

	/**
	 * Update SkillsSet.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SkillsSet
	 */
	public void update(SkillsSet entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SkillsSet.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SkillsSet
	 */
	public void delete(SkillsSet entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SkillsSet}
	 * @throws Exception
	 *             the exception
	 * @see SkillsSet
	 */
	public SkillsSet findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find SkillsSet by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SkillsSet}
	 * @throws Exception
	 *             the exception
	 * @see SkillsSet
	 */
	public List<SkillsSet> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<SkillsSet> findByNameAndLinkedToSeta(String description, String etqaID) throws Exception {
		return dao.findByNameAndLinkedToSeta(description, etqaID);
	}
	
	public List<SkillsSet> findByNameAndNotLinkedToSeta(String description, String etqaID) throws Exception {
		return dao.findByNameAndNotLinkedToSeta(description, etqaID);
	}

	public List<SkillsSet> findByNameNONMERSETA(String description) throws Exception {
		return dao.findByNameNONMERSETA(description);
	}

	/**
	 * Lazy load SkillsSet
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SkillsSet}
	 * @throws Exception
	 *             the exception
	 */
	public List<SkillsSet> allSkillsSet(int first, int pageSize) throws Exception {
		return dao.allSkillsSet(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SkillsSet for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the SkillsSet
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SkillsSet.class);
	}

	/**
	 * Lazy load SkillsSet with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            SkillsSet class
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
	 * @return a list of {@link haj.com.entity.SkillsSet} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsSet> allSkillsSet(Class<SkillsSet> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SkillsSet>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of SkillsSet for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            SkillsSet class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SkillsSet entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SkillsSet> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsSet> allSkillsSetByEtqa(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from SkillsSet o where o.etqa.code = :setaCode";
		filters.put("setaCode", HAJConstants.HOSTING_MERSETA_ETQA);
		return (List<SkillsSet>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllSkillsSetByEtqa(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SkillsSet o where o.etqa.code = :setaCode";
		return dao.countWhere(filters, hql);
	}
	
	/* Jonos code start */
	@SuppressWarnings("unchecked")
	public List<SkillsSet> allSkillsSetsByTrainingProviderLink(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey) throws Exception {
		String hql = "select o from SkillsSet o where o.id in (select x.skillsSet.id from TrainingProviderSkillsSet x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.skillsSet <> null)";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		return (List<SkillsSet>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllSkillsSetsByTrainingProviderLink(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SkillsSet o where o.id in (select x.skillsSet.id from TrainingProviderSkillsSet x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.skillsSet <> null)";
		return dao.countWhere(filters, hql);
	}
	/* Jonos code end */
	
	public List<SkillsSet> findByQualificationList( List<Qualification> qualificationList) throws Exception {
		return dao.findByQualificationList(qualificationList);
	}
	
	public List<SkillsSet> findByCompanyQualificationList(List<CompanyQualifications> companyQualificationsList) throws Exception {
		return dao.findByCompanyQualificationList(companyQualificationsList);
	}
	
	public List<SkillsSet> findByQualification(Qualification qualification) throws Exception {
		return dao.findByQualification(qualification.getId());
	}
	
	public void updateCreditsBySkillsSet(SkillsSet entity)throws Exception{
		Integer credits = 0;
		List<SkillsSetUnitStandards> list =  allSkillsSetUnitStandards(entity);
		for (SkillsSetUnitStandards skillsSetUnitStandards : list) {
			if (skillsSetUnitStandards.getUnitStandards() != null && skillsSetUnitStandards.getUnitStandards().getId() != null &&  skillsSetUnitStandards.getUnitStandards().getUnitstdnumberofcredits() != null) {
				try {
					Integer unitStandardCredits = Integer.parseInt(skillsSetUnitStandards.getUnitStandards().getUnitstdnumberofcredits());
					credits = credits+unitStandardCredits;
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}
		entity.setCredits(credits);
		update(entity);
	}
	
	public List<SkillsSet> findByProgrammeIDList(String programmeID) throws Exception {
		return dao.findByProgrammeIDList(programmeID);
	}
	
	public SkillsSet findByProgrammeID(String programmeID) throws Exception {
		return dao.findByProgrammeID(programmeID);
	}
	
	public SkillsSet findByProgrammeIDListReturnOne(String programmeID) throws Exception {
		List<SkillsSet> resultsList = findByProgrammeIDList(programmeID);
		if (resultsList.size() != 0) {
			return resultsList.get(0);
		} else {
			return null;
		}
	}
}
