package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.constants.HAJConstants;
import haj.com.entity.CompanyQualifications;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SkillsProgram;
import haj.com.entity.lookup.SkillsProgramUnitStandards;
import haj.com.entity.lookup.SkillsSet;
import haj.com.entity.lookup.UnitStandards;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SkillsProgramDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SkillsProgram
	 * 
	 * @author TechFinium
	 * @see SkillsProgram
	 * @return a list of {@link haj.com.entity.SkillsProgram}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> allSkillsProgram() throws Exception {
		return (List<SkillsProgram>) super.getList("select o from SkillsProgram o");
	}

	/**
	 * Get all SkillsProgram between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see SkillsProgram
	 * @return a list of {@link haj.com.entity.SkillsProgram}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> allSkillsProgram(Long from, int noRows) throws Exception {
		String hql = "select o from SkillsProgram o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<SkillsProgram>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see SkillsProgram
	 * @return a {@link haj.com.entity.SkillsProgram}
	 * @throws Exception
	 *             global exception
	 */
	public SkillsProgram findByKey(Long id) throws Exception {
		String hql = "select o from SkillsProgram o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (SkillsProgram) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SkillsProgram by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see SkillsProgram
	 * @return a list of {@link haj.com.entity.SkillsProgram}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> findByName(String description) throws Exception {
		String hql = "select o from SkillsProgram o where (o.description like :description or o.programmeID like :description) and o.etqa.code = :etqaID order by o.description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		parameters.put("etqaID", HAJConstants.HOSTING_MERSETA_ETQA);
		return (List<SkillsProgram>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> findByNameAndETQA(String description, String etqaID) throws Exception {
		String hql = "select o from SkillsProgram o where (o.description like :description or o.programmeID like :description) and o.etqa.code = :etqaID order by o.description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		parameters.put("etqaID", etqaID);
		return (List<SkillsProgram>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> findByNameAndNotETQA(String description, String etqaID) throws Exception {
		String hql = "select o from SkillsProgram o where (o.description like :description or o.programmeID like :description) and (o.etqa is null or o.etqa.code <> :etqaID) order by o.description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		parameters.put("etqaID", etqaID);
		return (List<SkillsProgram>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> findByNameAndQualificationList(String description, List<Qualification> qualificationList) throws Exception {
		String hql = "select o from SkillsProgram o where (o.description like :description or o.programmeID like :description) and o.etqa.code = :etqaID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		parameters.put("etqaID", HAJConstants.HOSTING_MERSETA_ETQA);
		if (qualificationList.size() != 0) {
			int counter = 1;
			hql += " and (";
			for (Qualification qualification : qualificationList) {
				if (counter == qualificationList.size()) {
					hql += " o.qualification.id = :qualificationID" + counter ;
				} else {
					hql += " o.qualification.id = :qualificationID" + counter + " or " ;
				}
				parameters.put("qualificationID" + counter, qualification.getId());
				counter++;
			}
			hql += ")";
		}
		hql += " order by o.description ";
		return (List<SkillsProgram>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> findByQualificationList( List<Qualification> qualificationList) throws Exception {
		String hql = "select o from SkillsProgram o where o.etqa.code = :etqaID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("etqaID", HAJConstants.HOSTING_MERSETA_ETQA);
		if (qualificationList.size() != 0) {
			int counter = 1;
			hql += " and (";
			for (Qualification qualification : qualificationList) {
				if (counter == qualificationList.size()) {
					hql += " o.qualification.id = :qualificationID" + counter ;
				} else {
					hql += " o.qualification.id = :qualificationID" + counter + " or " ;
				}
				parameters.put("qualificationID" + counter, qualification.getId());
				counter++;
			}
			hql += ")";
		}
		hql += " order by o.description ";
		return (List<SkillsProgram>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> findByCompanyQualificationList( List<CompanyQualifications> companyQualificationsList) throws Exception {
		String hql = "select o from SkillsProgram o where o.etqa.code = :etqaID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("etqaID", HAJConstants.HOSTING_MERSETA_ETQA);
		if (companyQualificationsList.size() != 0) {
			int counter = 1;
			hql += " and (";
			for (CompanyQualifications compQual : companyQualificationsList) {
				if (compQual.getQualification() != null && compQual.getQualification().getId() != null) {
					if (counter == companyQualificationsList.size()) {
						hql += " o.qualification.id = :qualificationID" + counter ;
					} else {
						hql += " o.qualification.id = :qualificationID" + counter + " or " ;
					}
					parameters.put("qualificationID" + counter, compQual.getQualification().getId());
					counter++;
				}
			}
			hql += ")";
		}
		hql += " order by o.description ";
		return (List<SkillsProgram>) super.getList(hql, parameters);
	}



	@SuppressWarnings("unchecked")
	public List<SkillsProgram> findByNameNonMERSETA(String description) throws Exception {
		String hql = "select o from SkillsProgram o where (o.description like :description or o.programmeID like :description) and o.etqa.code<> :etqaID order by o.description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		parameters.put("etqaID", HAJConstants.HOSTING_MERSETA_ETQA);
		return (List<SkillsProgram>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<SkillsProgramUnitStandards> allSkillsProgramUnitStandards(SkillsProgram modules) throws Exception {
		String hql = "select o from SkillsProgramUnitStandards o where o.skillsProgram.id = :moduleID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("moduleID", modules.getId());
		return (List<SkillsProgramUnitStandards>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> findByQualification(Qualification qual) throws Exception {
		String hql = "select o from SkillsProgram o where o.qualification.id = :qualID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualID", qual.getId());
		return (List<SkillsProgram>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> findByUnitStandardIdAssigned(List<UnitStandards> unitStandardsList) throws Exception {
		String hql = "select distinct(o.skillsProgram) from SkillsProgramUnitStandards o left join o.unitStandards ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (unitStandardsList.size() != 0) {
			hql += " where ( ";
			int counter = 1;
			for (UnitStandards unitStandards : unitStandardsList) {
				if (counter == unitStandardsList.size()) {
					hql += " o.unitStandards.id = :unitStandardID" + counter;
				} else {
					hql += " o.unitStandards.id = :unitStandardID" + counter + " or ";
				}
				parameters.put("unitStandardID" + counter, unitStandards.getId());
				counter++;
			}
			hql += " ) ";
		}
		return (List<SkillsProgram>) super.getList(hql, parameters);
	}
	
	public int countByUnitStandardIdAssigned(List<UnitStandards> unitStandardsList) throws Exception {
		String hql = "select count(distinct o.skillsProgram.id) from SkillsProgramUnitStandards o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		if (unitStandardsList.size() != 0) {
			hql += " where ( ";
			int counter = 1;
			for (UnitStandards unitStandards : unitStandardsList) {
				if (counter == unitStandardsList.size()) {
					hql += " o.unitStandards.id = :unitStandardID" + counter;
				} else {
					hql += " o.unitStandards.id = :unitStandardID" + counter + " or ";
				}
				parameters.put("unitStandardID" + counter, unitStandards.getId());
				counter++;
			}
			hql += " ) ";
		}
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<SkillsProgram> findByProgrammeIDList(String programmeID) throws Exception {
		String hql = "select o from SkillsProgram o where o.programmeID = :programmeID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("programmeID", programmeID);
		return (List<SkillsProgram>) super.getList(hql, parameters);
	}
	
	public SkillsProgram findByProgrammeID(String programmeID) throws Exception {
		String hql = "select o from SkillsProgram o where o.programmeID = :programmeID ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("programmeID", programmeID.trim());
		return (SkillsProgram) super.getUniqueResult(hql, parameters);
	}
}
