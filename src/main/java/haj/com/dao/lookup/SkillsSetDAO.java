package haj.com.dao.lookup;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.constants.HAJConstants;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.SkillsSetUnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SkillsSetDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SkillsSet
	 * 
	 * @author TechFinium
	 * @see SkillsSet
	 * @return a list of {@link haj.com.entity.SkillsSet}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsSet> allSkillsSet() throws Exception {
		return (List<SkillsSet>) super.getList("select o from SkillsSet o");
	}

	/**
	 * Get all SkillsSet between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see SkillsSet
	 * @return a list of {@link haj.com.entity.SkillsSet}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsSet> allSkillsSet(Long from, int noRows) throws Exception {
		String hql = "select o from SkillsSet o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<SkillsSet>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see SkillsSet
	 * @return a {@link haj.com.entity.SkillsSet}
	 * @throws Exception
	 *             global exception
	 */
	public SkillsSet findByKey(Long id) throws Exception {
		String hql = "select o from SkillsSet o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (SkillsSet) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SkillsSet by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see SkillsSet
	 * @return a list of {@link haj.com.entity.SkillsSet}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsSet> findByName(String description) throws Exception {
		String hql = "select o from SkillsSet o where (o.description like :description or o.programmeID like :description) and o.etqa.code = :etqaID order by o.description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		parameters.put("etqaID", HAJConstants.HOSTING_MERSETA_ETQA);
		return (List<SkillsSet>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsSet> findByNameAndLinkedToSeta(String description, String etqaID) throws Exception {
		String hql = "select o from SkillsSet o where (o.description like :description or o.programmeID like :description) and o.etqa.code = :etqaID order by o.description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		parameters.put("etqaID", etqaID);
		return (List<SkillsSet>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsSet> findByNameAndNotLinkedToSeta(String description, String etqaID) throws Exception {
		String hql = "select o from SkillsSet o where (o.description like :description or o.programmeID like :description) and (o.etqa is null or o.etqa.code <> :etqaID) order by o.description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		parameters.put("etqaID", etqaID);
		return (List<SkillsSet>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SkillsSet> findByNameNONMERSETA(String description) throws Exception {
		String hql = "select o from SkillsSet o where (o.description like :description or o.programmeID like :description) and o.etqa.code <> :etqaID order by o.description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		parameters.put("etqaID", HAJConstants.HOSTING_MERSETA_ETQA);
		return (List<SkillsSet>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SkillsSetUnitStandards> allSkillsSetUnitStandards(SkillsSet modules) throws Exception {
		String hql = "select o from SkillsSetUnitStandards o where o.skillsSet.id = :moduleID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("moduleID", modules.getId());
		return (List<SkillsSetUnitStandards>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsSet> findByQualificationList(List<Qualification> qualList) throws Exception {
		List<Qualification> qualificationList=new ArrayList<>();
		qualificationList.addAll(qualList);
		Map<String, Object> parameters = new Hashtable<String, Object>();
		int counter = 0;
		parameters.put("qualificationID" + counter,qualificationList.get(0).getId());
		String hql = "select o from SkillsSet o where  o.qualification.id = :qualificationID"+counter+"";
		qualificationList.remove(0);
		if (qualificationList.size() != 0) {
			hql += " and (";
			for (Qualification qualification : qualificationList) {
				counter++;
				if (counter == qualificationList.size()) {
					hql += " o.qualification.id = :qualificationID" + counter ;
				} else {
					hql += " o.qualification.id = :qualificationID" + counter + " or " ;
				}
				parameters.put("qualificationID" + counter, qualification.getId());
				
			}
			hql += ")";
		}
		hql += " order by o.description ";
		return (List<SkillsSet>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SkillsSet> findByCompanyQualificationList(List<CompanyQualifications> companyQualificationsList) throws Exception {
		String hql = "select o from SkillsSet o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (!companyQualificationsList.isEmpty()) {
			Integer counter = 1;
			hql += " where ";
			for (CompanyQualifications comapnyQ : companyQualificationsList) {
				if (counter == companyQualificationsList.size()) {
					hql += " o.qualification.id = :qualificationID" + counter ;
				} else {
					hql += " o.qualification.id = :qualificationID" + counter + " or " ;
				}
				parameters.put("qualificationID" + counter, comapnyQ.getQualification().getId());
				counter++;
			}
		}
		hql += " order by o.description ";
		return (List<SkillsSet>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsSet> findByQualification(Long qualificationID) throws Exception {
		String hql = "select o from SkillsSet o where o.qualification.id = :qualificationID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualificationID", qualificationID);
		return (List<SkillsSet>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsSet> findByProgrammeIDList(String programmeID) throws Exception {
		String hql = "select o from SkillsSet o where o.programmeID = :programmeID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("programmeID", programmeID);
		return (List<SkillsSet>) super.getList(hql, parameters);
	}
	
	public SkillsSet findByProgrammeID(String programmeID) throws Exception {
		String hql = "select o from SkillsSet o where o.programmeID = :programmeID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("programmeID", programmeID.trim());
		return (SkillsSet) super.getUniqueResult(hql, parameters);
	}
}
