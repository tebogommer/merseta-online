package haj.com.service.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.constants.HAJConstants;
import haj.com.dao.lookup.SkillsProgramDAO;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.SkillsRegistration;
import haj.com.entity.SkillsRegistrationUnitStandards;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsProgramUnitStandards;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class SkillsProgramService extends AbstractService {
	
	/** The dao. */
	private SkillsProgramDAO dao = new SkillsProgramDAO();
	
	private static SkillsProgramService skillsProgramService;
	public static synchronized SkillsProgramService instance() {
		if (skillsProgramService == null) {
			skillsProgramService = new SkillsProgramService();
		}
		return skillsProgramService;
	}

	/**
	 * Get all SkillsProgram
	 * 
	 * @author TechFinium
	 * @see SkillsProgram
	 * @return a list of {@link haj.com.entity.SkillsProgram}
	 * @throws Exception
	 *             the exception
	 */
	public List<SkillsProgram> allSkillsProgram() throws Exception {
		return dao.allSkillsProgram();
	}

	/**
	 * Create or update SkillsProgram.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SkillsProgram
	 */
	public void create(SkillsProgram entity) throws Exception {
		if (entity.getId() == null) {
			this.dao.create(entity);
		} else this.dao.update(entity);
	}

	public void create(SkillsProgram entity, List<UnitStandards> unitStandards) throws Exception {
		if (entity.getId() == null) {
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			dataEntities.add(entity);
			for (UnitStandards unitStandard : unitStandards) {
				dataEntities.add(new SkillsProgramUnitStandards(entity, unitStandard));
			}
			this.dao.createBatch(dataEntities);
		} else {
			this.dao.update(entity);

			List<SkillsProgramUnitStandards> list = allSkillsProgramUnitStandards(entity);

			dao.deleteBatch((List<IDataEntity>) (List<?>) list);

			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			for (UnitStandards unitStandard : unitStandards) {
				dataEntities.add(new SkillsProgramUnitStandards(entity, unitStandard));
			}
			this.dao.createBatch(dataEntities);
		}
	}
	
	public void create(SkillsProgram entity, List<UnitStandards> unitStandards, Qualification qualification) throws Exception {
		if (entity.getId() == null) {
			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			dataEntities.add(entity);
			for (UnitStandards unitStandard : unitStandards) {
				dataEntities.add(new SkillsProgramUnitStandards(entity, unitStandard, qualification));
			}
			this.dao.createBatch(dataEntities);
		} else {
			this.dao.update(entity);

			List<SkillsProgramUnitStandards> list = allSkillsProgramUnitStandards(entity);

			dao.deleteBatch((List<IDataEntity>) (List<?>) list);

			List<IDataEntity> dataEntities = new ArrayList<IDataEntity>();
			for (UnitStandards unitStandard : unitStandards) {
				dataEntities.add(new SkillsProgramUnitStandards(entity, unitStandard, qualification));
			}
			this.dao.createBatch(dataEntities);
		}
	}
	
	public void createSkillProgramWithSkillRegistration(SkillsRegistration registration) throws Exception {
		
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
		String title=registration.getProposedTitle();
		if(registration.getAmendedTitle() !=null && registration.getAmendedTitle().isEmpty()==false)
		{
			title=registration.getAmendedTitle() ;
		}
		SkillsProgram skillsProgram = new SkillsProgram();
		skillsProgram.setDescription(title);
		skillsProgram.setQualification(registration.getQualification());
		//skillsProgram.setQualification(registration.getQualification());
		skillsProgram.setCredits(registration.getQualification().getCredits());
		skillsProgram.setProgrammeID(registration.getRegistrationNumber());
		
		//create(skillsProgram, unitStandards);
		create(skillsProgram, unitStandards, registration.getQualification());
	}

	public List<SkillsProgramUnitStandards> allSkillsProgramUnitStandards(SkillsProgram modules) throws Exception {
		return dao.allSkillsProgramUnitStandards(modules);
	}

	/**
	 * Update SkillsProgram.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SkillsProgram
	 */
	public void update(SkillsProgram entity) throws Exception {
		this.dao.update(entity);
	}

	/**
	 * Delete SkillsProgram.
	 *
	 * @author TechFinium
	 * @param entity
	 *            the entity
	 * @throws Exception
	 *             the exception
	 * @see SkillsProgram
	 */
	public void delete(SkillsProgram entity) throws Exception {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.SkillsProgram}
	 * @throws Exception
	 *             the exception
	 * @see SkillsProgram
	 */
	public SkillsProgram findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find SkillsProgram by description.
	 *
	 * @author TechFinium
	 * @param desc
	 *            the desc
	 * @return a list of {@link haj.com.entity.SkillsProgram}
	 * @throws Exception
	 *             the exception
	 * @see SkillsProgram
	 */
	public List<SkillsProgram> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	public List<SkillsProgram> findByNameAndETQA(String description, String etqaID) throws Exception {
		return dao.findByNameAndETQA(description, etqaID);
	}
	
	public List<SkillsProgram> findByNameAndNotETQA(String description, String etqaID) throws Exception {
		return dao.findByNameAndNotETQA(description, etqaID);
	}
	
	public List<SkillsProgram> findByNameAndQualificationList(String description, List<Qualification> qualificationList) throws Exception {
		return dao.findByNameAndQualificationList(description, qualificationList);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> findByQualificationList(List<Qualification> qualificationList) throws Exception {
		return dao.findByQualificationList(qualificationList);
	}
	
	public List<SkillsProgram> findByCompanyQualificationList(List<CompanyQualifications> companyQualificationsList) throws Exception {
		return dao.findByCompanyQualificationList(companyQualificationsList);
	}

	public List<SkillsProgram> findByNameNonMERSETA(String description) throws Exception {
		return dao.findByNameNonMERSETA(description);
	}

	/**
	 * Lazy load SkillsProgram
	 * 
	 * @param first
	 *            from key
	 * @param pageSize
	 *            no of rows to fetch
	 * @return a list of {@link haj.com.entity.SkillsProgram}
	 * @throws Exception
	 *             the exception
	 */
	public List<SkillsProgram> allSkillsProgram(int first, int pageSize) throws Exception {
		return dao.allSkillsProgram(Long.valueOf(first), pageSize);
	}

	/**
	 * Get count of SkillsProgram for lazy load
	 * 
	 * @author TechFinium
	 * @return Number of rows in the SkillsProgram
	 * @throws Exception
	 *             the exception
	 */
	public Long count() throws Exception {
		return dao.count(SkillsProgram.class);
	}

	/**
	 * Lazy load SkillsProgram with pagination, filter, sorting
	 * 
	 * @author TechFinium
	 * @param class1
	 *            SkillsProgram class
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
	 * @return a list of {@link haj.com.entity.SkillsProgram} containing data
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> allSkillsProgram(Class<SkillsProgram> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return (List<SkillsProgram>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters);
	}

	/**
	 * Get count of SkillsProgram for lazy load with filters
	 * 
	 * @author TechFinium
	 * @param entity
	 *            SkillsProgram class
	 * @param filters
	 *            the filters
	 * @return Number of rows in the SkillsProgram entity
	 * @throws Exception
	 *             the exception
	 */
	public int count(Class<SkillsProgram> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> allSkillsProgramByEtqa(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		String hql = "select o from SkillsProgram o where o.etqa.code = :setaCode";
		filters.put("setaCode", HAJConstants.HOSTING_MERSETA_ETQA);
		return (List<SkillsProgram>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllSkillsProgramByEtqa(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SkillsProgram o where o.etqa.code = :setaCode";
		return dao.countWhere(filters, hql);
	}

    @SuppressWarnings("unchecked")
    public List<SkillsProgram> findByQualification(Qualification qual) throws Exception {
        return dao.findByQualification(qual);
    }
    
    @SuppressWarnings("unchecked")
    public ArrayList<SkillsProgram> findByQualifications(ArrayList<Qualification> qualList) throws Exception {
        ArrayList<SkillsProgram> list=new ArrayList<>();
        for(Qualification qual:qualList)
        {
            ArrayList<SkillsProgram> spList=(ArrayList<SkillsProgram>) findByQualification(qual);
            if(spList !=null && spList.size()>0)
            {
                list.addAll(spList);
            }
        }
        return list;
    }
    
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> allSkillsProgramByTrainingProviderLink(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey) throws Exception {
		String hql = "select o from SkillsProgram o where o.id in (select x.skillsProgram.id from TrainingProviderSkillsProgramme x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.skillsProgram <> null)";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		return (List<SkillsProgram>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql);
	}
	
	public int countAllSkillsProgramByTrainingProviderLink(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from SkillsProgram o where o.id in (select x.skillsProgram.id from TrainingProviderSkillsProgramme x where x.targetClass = :targetClass and x.targetKey = :targetKey and x.skillsProgram <> null)";
		return dao.countWhere(filters, hql);
	}
	
	public List<SkillsProgram> findByUnitStandardIdAssigned(List<UnitStandards> unitStandardsList) throws Exception {
		return dao.findByUnitStandardIdAssigned(unitStandardsList);
	}
	
	public int countByUnitStandardIdAssigned(List<UnitStandards> unitStandardsList) throws Exception {
		return dao.countByUnitStandardIdAssigned(unitStandardsList);
	}
	
	public SkillsProgram findByProgrammeID(String programmeID) throws Exception {
		return dao.findByProgrammeID(programmeID);
	}
	
	public List<SkillsProgram> findByProgrammeIDList(String programmeID) throws Exception {
		return dao.findByProgrammeIDList(programmeID);
	}
	
	public SkillsProgram findByProgrammeIDListReturnOne(String programmeID) throws Exception {
		List<SkillsProgram> resultsList = findByProgrammeIDList(programmeID);
		if (resultsList.size() != 0) {
			return resultsList.get(0);
		} else {
			return null;
		}
	}
	
	public void updateCreditsBySkilsProgramme(SkillsProgram entity)throws Exception{
		Integer credits = 0;
		List<SkillsProgramUnitStandards> list = allSkillsProgramUnitStandards(entity);
		for (SkillsProgramUnitStandards skillsProgramUnitStandards : list) {
			if (skillsProgramUnitStandards.getUnitStandards() != null && skillsProgramUnitStandards.getUnitStandards().getId() != null &&  skillsProgramUnitStandards.getUnitStandards().getUnitstdnumberofcredits() != null) {
				try {
					Integer unitStandardCredits = Integer.parseInt(skillsProgramUnitStandards.getUnitStandards().getUnitstdnumberofcredits());
					credits = credits+unitStandardCredits;
				} catch (Exception e) {
					logger.fatal(e);
				}
			}
		}
		entity.setCredits(credits);
		update(entity);
	}
}
