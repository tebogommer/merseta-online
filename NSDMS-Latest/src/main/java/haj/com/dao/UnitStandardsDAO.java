package haj.com.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.constants.HAJConstants;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SaqaUsQualification;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.service.UnitStandardsService;

// TODO: Auto-generated Javadoc
/**
 * The Class UnitStandardsDAO.
 */
public class UnitStandardsDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UnitStandards.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.lookup.UnitStandards}
	 * @throws Exception
	 *             global exception
	 * @see UnitStandards
	 */
	@SuppressWarnings("unchecked")
	public List<UnitStandards> allUnitStandards() throws Exception {
		return (List<UnitStandards>) super.getList("select o from UnitStandards o");
	}

	/**
	 * Get all UnitStandards between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.lookup.UnitStandards}
	 * @throws Exception
	 *             global exception
	 * @see UnitStandards
	 */
	@SuppressWarnings("unchecked")
	public List<UnitStandards> allUnitStandards(Long from, int noRows) throws Exception {
		String hql = "select o from UnitStandards o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<UnitStandards>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.lookup.UnitStandards}
	 * @throws Exception
	 *             global exception
	 * @see UnitStandards
	 */
	public UnitStandards findByKey(Long id) throws Exception {
		String hql = "select o from UnitStandards o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (UnitStandards) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UnitStandards by title.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.lookup.UnitStandards}
	 * @throws Exception
	 *             global exception
	 * @see UnitStandards
	 */
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findByTitle(String description) throws Exception {
		String hql = "select o from UnitStandards o where o.title like :description or o.code like :description order by o.title ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<UnitStandards>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<UnitStandards> findByTitle(String description, Integer qualificationID) throws Exception {
		String hql = "select o from UnitStandards o where (o.title like :description or o.code like :description) and o.code in (select x.unitstandardid from SaqaUsQualification x where x.qualificationid = :qualificationid) order by o.title ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		parameters.put("qualificationid", qualificationID);
		return (List<UnitStandards>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<UnitStandards> findByQualification(Integer qualificationID) throws Exception {
		String hql = "select o from UnitStandards o where o.code in (select x.unitstandardid from SaqaUsQualification x where x.qualificationid = :qualificationid) order by o.title ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualificationid", qualificationID);
		return (List<UnitStandards>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<UnitStandards> findBySkillsSet(Long skillsSetID) throws Exception {
		String hql = "select o.unitStandards from SkillsSetUnitStandards o where o.skillsSet.id = :skillsSetID order by o.unitStandards.title ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("skillsSetID", skillsSetID);
		return (List<UnitStandards>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<UnitStandards> findBySkillsProgram(Long qualificationID) throws Exception {
		String hql = "select o.unitStandards from SkillsProgramUnitStandards o where o.skillsProgram.id = :qualificationid order by o.unitStandards.title ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualificationid", qualificationID);
		return (List<UnitStandards>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SaqaUsQualification> findByUsQualification(Integer qualificationID) throws Exception {
		String hql = "select o from SaqaUsQualification o where o.qualificationid = :qualificationid order by o.usqualtypedescription";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualificationid", qualificationID);
		return (List<SaqaUsQualification>) super.getList(hql, parameters);
	}

	/**
	 * Find UnitStandards by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.lookup.UnitStandards}
	 * @throws Exception
	 *             global exception
	 * @see UnitStandards
	 */
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findByName(String description) throws Exception {
		String hql = "select o from UnitStandards o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<UnitStandards>) super.getList(hql, parameters);
	}

	public UnitStandards findByUnitStandartId(int id) throws Exception {
		String hql = "select o from UnitStandards o where o.code = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (UnitStandards) super.getUniqueResult(hql, parameters);
	}
	
	public UnitStandards findByUnitStandartCodeString(String codeString) throws Exception {
		String hql = "select o from UnitStandards o where o.code = :codeString ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("codeString", codeString);
		return (UnitStandards) super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find UnitStandards by title and where lastDateForEnrolment before lastDateForEnrolment on UnitStandards.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 *	@param lastDateForEnrolment
	 * 			The date passed for check before last enrollment date on UnitStandards
	 * @return a list of {@link haj.com.entity.lookup.UnitStandards}
	 * @throws Exception
	 *             global exception
	 * @see UnitStandards
	 */
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findByTitleAndBeforeLastEnrolmentDate(String description, Date lastDateForEnrolment) throws Exception {
		String hql = "select o from UnitStandards o where (o.title like :description or o.code like :description) and date(o.lastDateForEnrolment) > date(:lastDateForEnrolment) order by o.title ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		parameters.put("lastDateForEnrolment", lastDateForEnrolment);
		return (List<UnitStandards>) super.getList(hql, parameters);
	}
	
	/**
	 * Finds UnitStandards where lastDateForEnrolment before lastDateForEnrolment on UnitStandards
	 * 
	 * @param lastDateForEnrolment
	 * 		The date passed
	 * 
	 * @return a list of {@link haj.com.entity.lookup.UnitStandards}
	 * @throws Exception
	 * 		global exception
	 * @see UnitStandards
	 */
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findBeforeLastEnrolmentDate(Date lastDateForEnrolment) throws Exception {
		String hql = "select o from UnitStandards o where date(o.lastDateForEnrolment) > date(:lastDateForEnrolment) order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("lastDateForEnrolment", lastDateForEnrolment);
		return (List<UnitStandards>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findUnitStandardsBySkillsProgram(Long skillsProgramID) throws Exception {
		String hql = "select o.unitStandards from SkillsProgramUnitStandards o where o.skillsProgram.id = :skillsProgramID order by o.unitStandards.title ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("skillsProgramID", skillsProgramID);
		return (List<UnitStandards>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findByListOfQualification(List<Qualification> qualList) throws Exception {
		List<Qualification> qualificationList=new ArrayList<>();
		Map<String, Object> parameters = new Hashtable<String, Object>();
		qualificationList.addAll(qualList);
		int counter = 0;
		
		String innerHQL="select x.unitstandardid from SaqaUsQualification x where x.qualificationid =:qualificationID"+counter;
		parameters.put("qualificationID" + counter,qualificationList.get(0).getCode());
		qualificationList.remove(0);
		if (qualificationList.size() != 0) {
			innerHQL += " and ";
			for (Qualification qualification : qualificationList) {
				counter++;
				if (counter == qualificationList.size()) {
					innerHQL += " x.qualificationid  = :qualificationID" + counter ;
				} else {
					innerHQL += " x.qualificationid  = :qualificationID" + counter + " or " ;
				}
				parameters.put("qualificationID" + counter, qualification.getCode());
				
			}
			innerHQL += "";
		}
		
		String hql = "select o from UnitStandards o where o.code in ("+innerHQL+") order by o.title ";
		
		return (List<UnitStandards>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findByQualificationList(List<Qualification> qualList) throws Exception {		
		List<UnitStandards> usList=new ArrayList<>();
		for(Qualification qual:qualList){
			if(qual.getIslearningprogramme().equalsIgnoreCase("YES") && qual.getLearningprogrammequal() != null) {				
				//Qualification qualTemp = findByCode(Integer.valueOf(qual.getLearningprogrammequal().trim()));
				//List<UnitStandards> list =findByQualification(qualTemp.getCode());
				//List<UnitStandards> list =findUnitStandardsByQualification(qual.getCode());	
				List<UnitStandards> list =findUnitStandardsByQualification(qual.getId());	
				if(list !=null && list.size()>0){
					for(UnitStandards us:list){
						us.setQualification(qual);
					}
					usList.addAll(list);
				}
			}else {
				List<UnitStandards> list =findByQualification(qual.getCode());
				
				if(list !=null && list.size()>0){
					for(UnitStandards us:list){
						us.setQualification(qual);
					}
					usList.addAll(list);
				}
			}			
		}
		return usList;		
	}
	
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findMerSETAUnitStandras(String description) throws Exception {
		String hql = "select o from UnitStandards o where (o.title like :description or o.code like :description) and o.code in "
				+ "(select x.unitstandardid from SaqaUsQualification x where x.qualificationid in ("
				+ "select c.code from Qualification c where c.etqaid = :etqa)"
				+ ") order by o.title ";
		
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("etqa", HAJConstants.HOSTING_MERSETA_ETQA);
		parameters.put("description", "%" + description.trim() + "%");
		return (List<UnitStandards>) super.getList(hql, parameters,10);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findByTitleLinkedToQualification(String description, Integer qualificationId) throws Exception {
		String hql = "select o from UnitStandards o where o.title like :description or o.code like :description and o.code in (select x.unitstandardid from SaqaUsQualification x where x.qualificationid = :qualificationId) order by o.title ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		parameters.put("qualificationId", qualificationId);
		return (List<UnitStandards>) super.getList(hql, parameters);
	}
	
	public Qualification findByCode(Integer code) throws Exception {
		String hql = "select o from Qualification o where o.code = :code ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (Qualification) super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findUnitStandardsByQualification(Integer code) throws Exception {
		String hql = "select o.unitStandards from QualificationUnitStandards o where o.qualification.code = :code  order by o.unitStandards.title ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("code", code);
		return (List<UnitStandards>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findUnitStandardsByQualification(Long qualificationID) throws Exception {
		String hql = "select o.unitStandards from QualificationUnitStandards o where o.qualification.id = :qualificationID  order by o.unitStandards.title ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualificationID", qualificationID);
		return (List<UnitStandards>) super.getList(hql, parameters);
	}
}
