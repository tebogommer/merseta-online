package haj.com.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import haj.com.bean.EmployerQualificationBean;
import haj.com.dao.WorkplaceMonitoringLearnerInductionDAO;
import haj.com.entity.Users;
import haj.com.entity.WorkplaceMonitoringLearnerInduction;
import haj.com.entity.WorkplaceMonitoringSiteVisit;
import haj.com.entity.enums.LearnerStatusEnum;
import haj.com.entity.enums.QualificationTypeSelectionEnum;
import haj.com.entity.lookup.Learnership;
import haj.com.entity.lookup.NonCreditBearingIntervationTitle;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

public class WorkplaceMonitoringLearnerInductionService extends AbstractService {
	/** The dao. */
	private WorkplaceMonitoringLearnerInductionDAO dao = new WorkplaceMonitoringLearnerInductionDAO();

	/**
	 * Get all WorkplaceMonitoringLearnerInduction
 	 * @author TechFinium 
 	 * @see   WorkplaceMonitoringLearnerInduction
 	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerInduction}
	 * @throws Exception the exception
 	 */
	public List<WorkplaceMonitoringLearnerInduction> allWorkplaceMonitoringLearnerInduction() throws Exception {
	  	return dao.allWorkplaceMonitoringLearnerInduction();
	}


	/**
	 * Create or update WorkplaceMonitoringLearnerInduction.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     WorkplaceMonitoringLearnerInduction
	 */
	public void create(WorkplaceMonitoringLearnerInduction entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  WorkplaceMonitoringLearnerInduction.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringLearnerInduction
	 */
	public void update(WorkplaceMonitoringLearnerInduction entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  WorkplaceMonitoringLearnerInduction.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringLearnerInduction
	 */
	public void delete(WorkplaceMonitoringLearnerInduction entity) throws Exception  {
		this.dao.delete(entity);
	}
	
	private List<WorkplaceMonitoringLearnerInduction> populateAdditionalInformationList(List<WorkplaceMonitoringLearnerInduction> list) throws Exception {
		for (WorkplaceMonitoringLearnerInduction entity : list) {
			populateAdditionalInformation(entity);
		}
		return list;
	}

	private WorkplaceMonitoringLearnerInduction populateAdditionalInformation(WorkplaceMonitoringLearnerInduction entity) throws Exception {
		if (entity.getId() != null) {
			entity.setDocs(DocService.instance().searchByTargetKeyAndClass(entity.getClass().getName(), entity.getId()));
		}
		return entity;
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WorkplaceMonitoringLearnerInduction}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringLearnerInduction
	 */
	public WorkplaceMonitoringLearnerInduction findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find WorkplaceMonitoringLearnerInduction by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerInduction}
	 * @throws Exception the exception
	 * @see    WorkplaceMonitoringLearnerInduction
	 */
	public List<WorkplaceMonitoringLearnerInduction> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load WorkplaceMonitoringLearnerInduction
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.WorkplaceMonitoringLearnerInduction}
	 * @throws Exception the exception
	 */
	public List<WorkplaceMonitoringLearnerInduction> allWorkplaceMonitoringLearnerInduction(int first, int pageSize) throws Exception {
		return dao.allWorkplaceMonitoringLearnerInduction(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of WorkplaceMonitoringLearnerInduction for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the WorkplaceMonitoringLearnerInduction
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(WorkplaceMonitoringLearnerInduction.class);
	}
	
    /**
     * Lazy load WorkplaceMonitoringLearnerInduction with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 WorkplaceMonitoringLearnerInduction class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.WorkplaceMonitoringLearnerInduction} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerInduction> allWorkplaceMonitoringLearnerInduction(Class<WorkplaceMonitoringLearnerInduction> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<WorkplaceMonitoringLearnerInduction>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	}
	
    /**
     * Get count of WorkplaceMonitoringLearnerInduction for lazy load with filters
     * @author TechFinium 
     * @param entity WorkplaceMonitoringLearnerInduction class
     * @param filters the filters
     * @return Number of rows in the WorkplaceMonitoringLearnerInduction entity
     * @throws Exception the exception     
     */	
	public int count(Class<WorkplaceMonitoringLearnerInduction> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public void updateExisitingEntry(WorkplaceMonitoringLearnerInduction entity, Users sessionUser) throws Exception{
		entity.setLastActionDate(getSynchronizedDate());
		entity.setLastActionUser(sessionUser);
		update(entity);
	}
	
	@SuppressWarnings("unchecked")
	public List<WorkplaceMonitoringLearnerInduction> allWorkplaceMonitoringLearnerInductionByTargetClassAndKey(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, String targetClass, Long targetKey) throws Exception {
		String hql = "select o from WorkplaceMonitoringLearnerInduction o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		filters.put("targetClass", targetClass);
		filters.put("targetKey", targetKey);
		return populateAdditionalInformationList((List<WorkplaceMonitoringLearnerInduction>)dao.sortAndFilterWhere(first,pageSize,sortField,sortOrder,filters, hql));
	}
	
	public int countAllWorkplaceMonitoringLearnerInductionByTargetClassAndKey(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from WorkplaceMonitoringLearnerInduction o where o.targetClass = :targetClass and o.targetKey = :targetKey";
		return dao.countWhere(filters, hql);
	}
	
	public List<EmployerQualificationBean> allEmployerQualificationByEmployerIdAndLearnerStatus(Long employerId, List<LearnerStatusEnum> learnerStatusEnumList) throws Exception {
		return dao.allEmployerQualificationByEmployerIdAndLearnerStatus(employerId, learnerStatusEnumList);
	}
	
	public void generateLearnerInductionForWorkplaceMonitoringSiteVisit(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit,  Users sessionUser) throws Exception{
		List<IDataEntity> createList = new ArrayList<>();
		List<EmployerQualificationBean> allResults =  allEmployerQualificationByEmployerIdAndLearnerStatus(workplaceMonitoringSiteVisit.getCompany().getId(), LearnerStatusEnum.getLearnerInductionValues());
		for (EmployerQualificationBean employerQualificationBean : allResults) {
			boolean addToCreate = false;
			if (employerQualificationBean.getTargetClass() != null && !employerQualificationBean.getTargetClass().trim().isEmpty() && employerQualificationBean.getTargetKey() != null && employerQualificationBean.getEmployerId() != null) {
				WorkplaceMonitoringLearnerInduction newEntry = new WorkplaceMonitoringLearnerInduction();
				try {
					if (employerQualificationBean.getTargetClass().equals(Qualification.class.getName())) {
						Qualification qualification = (Qualification) dao.getByClassAndKey(employerQualificationBean.getTargetClass(), employerQualificationBean.getTargetKey().longValue());
						if (qualification != null && qualification.getId() != null) {
							newEntry.setQualification(qualification);
							newEntry.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.Qualification);
							addToCreate = true;
						}
					} else if(employerQualificationBean.getTargetClass().equals(SkillsProgram.class.getName())) {
						SkillsProgram skillsProgram = (SkillsProgram) dao.getByClassAndKey(employerQualificationBean.getTargetClass(), employerQualificationBean.getTargetKey().longValue());
						if (skillsProgram != null && skillsProgram.getId() != null) {
							newEntry.setSkillsProgram(skillsProgram);
							newEntry.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.SkillsProgram);
							addToCreate = true;
						}
					} else if(employerQualificationBean.getTargetClass().equals(SkillsSet.class.getName())) {
						SkillsSet skillsSet = (SkillsSet) dao.getByClassAndKey(employerQualificationBean.getTargetClass(), employerQualificationBean.getTargetKey().longValue());
						if (skillsSet != null && skillsSet.getId() != null) {
							newEntry.setSkillsSet(skillsSet);
							newEntry.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.SkillsSet);
							addToCreate = true;
						}
					} else if(employerQualificationBean.getTargetClass().equals(NonCreditBearingIntervationTitle.class.getName())) {
						NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle = (NonCreditBearingIntervationTitle) dao.getByClassAndKey(employerQualificationBean.getTargetClass(), employerQualificationBean.getTargetKey().longValue());
						if (nonCreditBearingIntervationTitle != null && nonCreditBearingIntervationTitle.getId() != null) {
							newEntry.setNonCreditBearingIntervationTitle(nonCreditBearingIntervationTitle);
							newEntry.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.NonCreditBearingIntervationTitle);
							addToCreate = true;
						}
					} else if(employerQualificationBean.getTargetClass().equals(UnitStandards.class.getName())) {
						UnitStandards unitStandard = (UnitStandards) dao.getByClassAndKey(employerQualificationBean.getTargetClass(), employerQualificationBean.getTargetKey().longValue());
						if (unitStandard != null && unitStandard.getId() != null) {
							newEntry.setUnitStandard(unitStandard);
							newEntry.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.UnitStandards);
							addToCreate = true;
						}
					} else if(employerQualificationBean.getTargetClass().equals(Learnership.class.getName())) {
						Learnership learnership = (Learnership) dao.getByClassAndKey(employerQualificationBean.getTargetClass(), employerQualificationBean.getTargetKey().longValue());
						if (learnership != null && learnership.getId() != null) {
							newEntry.setLearnership(learnership);
							newEntry.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.Learnership);
							addToCreate = true;
						}
					} else {
						addToCreate = false;
					}
				} catch (Exception e) {
					logger.fatal(e);
					addToCreate = false;
				}
				
				if (addToCreate) {
					if (workplaceMonitoringSiteVisit != null && workplaceMonitoringSiteVisit.getId() != null) {
						newEntry.setTargetClass(workplaceMonitoringSiteVisit.getClass().getName());
						newEntry.setTargetKey(workplaceMonitoringSiteVisit.getId());
					}
					newEntry.setNumberOfAttendees(0);
					newEntry.setLastActionDate(getSynchronizedDate());
					if (sessionUser != null) {
						newEntry.setLastActionUser(sessionUser);
					}
					newEntry.setSystemGenerated(Boolean.TRUE);
					createList.add(newEntry);
				}
			}
		}
		if (!createList.isEmpty()) {
			dao.createBatch(createList, 100);
		}
	}
	
	public List<WorkplaceMonitoringLearnerInduction> returnLearnerInductionForWorkplaceMonitoringSiteVisitToBeCreated(WorkplaceMonitoringSiteVisit workplaceMonitoringSiteVisit,  Users sessionUser) throws Exception{
		List<WorkplaceMonitoringLearnerInduction> createList = new ArrayList<>();
		List<EmployerQualificationBean> allResults =  allEmployerQualificationByEmployerIdAndLearnerStatus(workplaceMonitoringSiteVisit.getCompany().getId(), LearnerStatusEnum.getLearnerInductionValues());
		for (EmployerQualificationBean employerQualificationBean : allResults) {
			boolean addToCreate = false;
			if (employerQualificationBean.getTargetClass() != null && !employerQualificationBean.getTargetClass().trim().isEmpty() && employerQualificationBean.getTargetKey() != null && employerQualificationBean.getEmployerId() != null) {
				WorkplaceMonitoringLearnerInduction newEntry = new WorkplaceMonitoringLearnerInduction();
				try {
					if (employerQualificationBean.getTargetClass().equals(Qualification.class.getName())) {
						Qualification qualification = (Qualification) dao.getByClassAndKey(employerQualificationBean.getTargetClass(), employerQualificationBean.getTargetKey().longValue());
						if (qualification != null && qualification.getId() != null) {
							newEntry.setQualification(qualification);
							newEntry.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.Qualification);
							addToCreate = true;
						}
					} else if(employerQualificationBean.getTargetClass().equals(SkillsProgram.class.getName())) {
						SkillsProgram skillsProgram = (SkillsProgram) dao.getByClassAndKey(employerQualificationBean.getTargetClass(), employerQualificationBean.getTargetKey().longValue());
						if (skillsProgram != null && skillsProgram.getId() != null) {
							newEntry.setSkillsProgram(skillsProgram);
							newEntry.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.SkillsProgram);
							addToCreate = true;
						}
					} else if(employerQualificationBean.getTargetClass().equals(SkillsSet.class.getName())) {
						SkillsSet skillsSet = (SkillsSet) dao.getByClassAndKey(employerQualificationBean.getTargetClass(), employerQualificationBean.getTargetKey().longValue());
						if (skillsSet != null && skillsSet.getId() != null) {
							newEntry.setSkillsSet(skillsSet);
							newEntry.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.SkillsSet);
							addToCreate = true;
						}
					} else if(employerQualificationBean.getTargetClass().equals(NonCreditBearingIntervationTitle.class.getName())) {
						NonCreditBearingIntervationTitle nonCreditBearingIntervationTitle = (NonCreditBearingIntervationTitle) dao.getByClassAndKey(employerQualificationBean.getTargetClass(), employerQualificationBean.getTargetKey().longValue());
						if (nonCreditBearingIntervationTitle != null && nonCreditBearingIntervationTitle.getId() != null) {
							newEntry.setNonCreditBearingIntervationTitle(nonCreditBearingIntervationTitle);
							newEntry.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.NonCreditBearingIntervationTitle);
							addToCreate = true;
						}
					} else if(employerQualificationBean.getTargetClass().equals(UnitStandards.class.getName())) {
						UnitStandards unitStandard = (UnitStandards) dao.getByClassAndKey(employerQualificationBean.getTargetClass(), employerQualificationBean.getTargetKey().longValue());
						if (unitStandard != null && unitStandard.getId() != null) {
							newEntry.setUnitStandard(unitStandard);
							newEntry.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.UnitStandards);
							addToCreate = true;
						}
					} else if(employerQualificationBean.getTargetClass().equals(Learnership.class.getName())) {
						Learnership learnership = (Learnership) dao.getByClassAndKey(employerQualificationBean.getTargetClass(), employerQualificationBean.getTargetKey().longValue());
						if (learnership != null && learnership.getId() != null) {
							newEntry.setLearnership(learnership);
							newEntry.setQualificationTypeSelectionEnum(QualificationTypeSelectionEnum.Learnership);
							addToCreate = true;
						}
					} else {
						addToCreate = false;
					}
				} catch (Exception e) {
					logger.fatal(e);
					addToCreate = false;
				}
				
				if (addToCreate) {
					if (workplaceMonitoringSiteVisit != null) {
						newEntry.setTargetClass(workplaceMonitoringSiteVisit.getClass().getName());
						newEntry.setTargetKey(workplaceMonitoringSiteVisit.getId());
					}
					newEntry.setNumberOfAttendees(0);
					newEntry.setLastActionDate(getSynchronizedDate());
					if (sessionUser != null && sessionUser.getId() != null) {
						newEntry.setLastActionUser(sessionUser);
					}
					newEntry.setSystemGenerated(Boolean.TRUE);
					createList.add(newEntry);
				}
			}
		}
		allResults = null;
		if (!createList.isEmpty()) {
			return createList;
		} else {
			return new ArrayList<>();
		}
	}

	public void createUpdateLearnerInduction(WorkplaceMonitoringLearnerInduction entity, String targetClass, Long targetKey, Users sessionUser, boolean systemGenerated) throws Exception{
		entity.setLastActionDate(getSynchronizedDate());
		entity.setLastActionUser(sessionUser);
		if (entity.getId() == null) {
			entity.setSystemGenerated(systemGenerated);
			entity.setTargetClass(targetClass);
			entity.setTargetKey(targetKey);
			validiateData(entity);
			create(entity);
		} else {
			validiateData(entity);
			update(entity);
		}
	}
	
	public void validiateData(WorkplaceMonitoringLearnerInduction entity) throws Exception {
		
		StringBuilder errors = new StringBuilder();
		
		if (!entity.getSystemGenerated()) {
			if (entity.getQualificationTypeSelectionEnum() == null) {
				errors.append("Qualification Type Selection can not be left empty. ");
			} else {
				if (entity.getQualificationTypeSelectionEnum() == QualificationTypeSelectionEnum.Learnership) {
					if (entity.getLearnership() == null) {
						errors.append("Learnership can not be left empty. ");
					} else {
						if (entity.getTargetClass() != null && entity.getTargetKey() != null) {
							if (countIfAlreadyAssigned(entity.getId(), entity.getTargetClass(), entity.getTargetKey(), entity.getQualificationTypeSelectionEnum(), entity.getLearnership().getId()) > 0) {
								errors.append("Learnership already assigned. ");
							}
						}
					}
					
				} else if (entity.getQualificationTypeSelectionEnum() == QualificationTypeSelectionEnum.NonCreditBearingIntervationTitle) {
					if (entity.getNonCreditBearingIntervationTitle() == null) {
						errors.append("Non-Credit Bearing Intervation can not be left empty. ");
					} else {
						if (entity.getTargetClass() != null && entity.getTargetKey() != null) {
							if (countIfAlreadyAssigned(entity.getId(), entity.getTargetClass(), entity.getTargetKey(), entity.getQualificationTypeSelectionEnum(), entity.getNonCreditBearingIntervationTitle().getId()) > 0) {
								errors.append("Non-Credit Bearing Intervation already assigned. ");
							}
						}
					}
				} else if (entity.getQualificationTypeSelectionEnum() == QualificationTypeSelectionEnum.Qualification) {
					if (entity.getQualification() == null) {
						errors.append("Qualification can not be left empty. ");
					} else {
						if (entity.getTargetClass() != null && entity.getTargetKey() != null) {
							if (countIfAlreadyAssigned(entity.getId(), entity.getTargetClass(), entity.getTargetKey(), entity.getQualificationTypeSelectionEnum(), entity.getQualification().getId()) > 0) {
								errors.append("Qualification already assigned. ");
							}
						}
					}
				} else if (entity.getQualificationTypeSelectionEnum() == QualificationTypeSelectionEnum.SkillsProgram) {
					if (entity.getSkillsProgram() == null) {
						errors.append("Skills Program can not be left empty. ");
					} else {
						if (entity.getTargetClass() != null && entity.getTargetKey() != null) {
							if (countIfAlreadyAssigned(entity.getId(), entity.getTargetClass(), entity.getTargetKey(), entity.getQualificationTypeSelectionEnum(), entity.getSkillsProgram().getId()) > 0) {
								errors.append("Skills Program already assigned. ");
							}
						}
					}
				} else if (entity.getQualificationTypeSelectionEnum() == QualificationTypeSelectionEnum.SkillsSet) {
					if (entity.getSkillsSet() == null) {
						errors.append("Skills Set can not be left empty. ");
					} else {
						if (entity.getTargetClass() != null && entity.getTargetKey() != null) {
							if (countIfAlreadyAssigned(entity.getId(), entity.getTargetClass(), entity.getTargetKey(), entity.getQualificationTypeSelectionEnum(), entity.getSkillsSet().getId()) > 0) {
								errors.append("Skills Set already assigned. ");
							}
						}
					}
				} else if (entity.getQualificationTypeSelectionEnum() == QualificationTypeSelectionEnum.UnitStandards) {
					if (entity.getUnitStandard() == null) {
						errors.append("Unit Standard can not be left empty. ");
					} else {
						if (entity.getTargetClass() != null && entity.getTargetKey() != null) {
							if (countIfAlreadyAssigned(entity.getId(), entity.getTargetClass(), entity.getTargetKey(), entity.getQualificationTypeSelectionEnum(), entity.getUnitStandard().getId()) > 0) {
								errors.append("Unit Standard already assigned. ");
							}
						}
					}
				}
			}
		}
		if (entity.getNumberOfAttendees() == null) {
			errors.append("Number of attendees can not be left empty. ");
		}
		
		if (!errors.toString().trim().isEmpty()) {
			throw new Exception("Provide the following before proceeding: " + errors.toString());
		}
	}
	
	public Integer countIfAlreadyAssigned(Long learnerInductionId, String targetClass, Long targetKey, QualificationTypeSelectionEnum type, Long qualId) throws Exception {
		Integer counter = 0;
			switch (type) {
			case Learnership:
				if (learnerInductionId == null) {
					counter = dao.countByClassKeyLearnershipId(targetClass, targetKey, qualId);
				}else {
					counter = dao.countByClassKeyLearnershipIdAndNotLearnerInductionId(targetClass, targetKey, qualId, learnerInductionId);
				}
				break;
			case NonCreditBearingIntervationTitle:
				if (learnerInductionId == null) {
					counter = dao.countByClassKeyNonCreditBearingIntervationTitleId(targetClass, targetKey, qualId);
				}else {
					counter = dao.countByClassKeyNonCreditBearingIntervationTitleIdAndNotLearnerInductionId(targetClass, targetKey, qualId, learnerInductionId);
				}
				break;
			case Qualification:
				if (learnerInductionId == null) {
					counter = dao.countByClassKeyQualificationId(targetClass, targetKey, qualId);
				}else {
					counter = dao.countByClassKeyQualificationIdAndNotLearnerInductionId(targetClass, targetKey, qualId, learnerInductionId);
				}
				break;
			case SkillsProgram:
				if (learnerInductionId == null) {
					counter = dao.countByClassKeySkillsProgramId(targetClass, targetKey, qualId);
				}else {
					counter = dao.countByClassKeySkillsProgramIdAndNotLearnerInductionId(targetClass, targetKey, qualId, learnerInductionId);
				}
				break;
			case SkillsSet:
				if (learnerInductionId == null) {
					counter = dao.countByClassKeySkillsSetId(targetClass, targetKey, qualId);
				}else {
					counter = dao.countByClassKeySkillsSetIdAndNotLearnerInductionId(targetClass, targetKey, qualId, learnerInductionId);
				}
				break;
			case UnitStandards:
				if (learnerInductionId == null) {
					counter = dao.countByClassKeyUnitStandardId(targetClass, targetKey, qualId);
				}else {
					counter = dao.countByClassKeyUnitStandardIdAndNotLearnerInductionId(targetClass, targetKey, qualId, learnerInductionId);
				}
				break;
			default:
				break;
			}
		return counter;
	}
}
