package haj.com.datatakeon;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Blank;
import haj.com.entity.datatakeon.TakOnUnitstandard;
import haj.com.entity.lookup.Qualification;
import haj.com.entity.lookup.SaqaQualification;
import haj.com.entity.lookup.SaqaUsQualification;
import haj.com.entity.lookup.UnitStandards;
import haj.com.entity.lookup.temp.QualificationTemp;
import haj.com.entity.lookup.temp.SaqaUsQualificationTemp;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.saqa.unitstandards.UNITSTANDARD;

// TODO: Auto-generated Javadoc
/**
 * The Class GenericDAO.
 */
public class GenericDAO extends AbstractDAO {

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
	 * Get all Blank.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Blank}
	 * @throws Exception
	 *             global exception
	 * @see Blank
	 */
	@SuppressWarnings("unchecked")
	public List<TakOnUnitstandard> allUnitstandard() throws Exception {
		return (List<TakOnUnitstandard>) super.getList("select o from TakOnUnitstandard o");
	}

	@SuppressWarnings("unchecked")
	public List<UNITSTANDARD> findID(int id) throws Exception {
		String hql = "select o from UNITSTANDARD o where o.unitstandardid = :id";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<UNITSTANDARD>) super.getList(hql, parameters);
	}
	
	/*
	 * public Blank findByKey(Long id) throws Exception { String hql =
	 * "select o from Blank o where o.id = :id " ; Map<String, Object> parameters =
	 * new Hashtable<String, Object>(); parameters.put("id", id); return
	 * (Blank)super.getUniqueResult(hql, parameters); }
	 * 
	 * 
	 * @SuppressWarnings("unchecked") public List<Blank> findByName(String
	 * description) throws Exception { String hql =
	 * "select o from Blank o where o.description like  :description order by o.desc "
	 * ; Map<String, Object> parameters = new Hashtable<String, Object>();
	 * parameters.put("description", ""+description.trim()+"%"); return
	 * (List<Blank>)super.getList(hql, parameters); }
	 */
	
	// *********************************************************************************************************************************** //
	
	@SuppressWarnings("unchecked")
	public List<SaqaUsQualification> findSaqaUsQualificationByQualificationIdAndUnitStandardId(int qualificationid, int unitstandardid) throws Exception {
		String hql = "select o from SaqaUsQualification o where o.qualificationid = :qualificationid and o.unitstandardid = :unitstandardid";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("qualificationid", qualificationid);
		parameters.put("unitstandardid", unitstandardid);
		return (List<SaqaUsQualification>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SaqaUsQualificationTemp> findAllSaqaUsQualifationsTemp() throws Exception {
		String hql = "select o from SaqaUsQualificationTemp o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<SaqaUsQualificationTemp>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<QualificationTemp> findQualificationTempByQualificationId(int id) throws Exception {
		String hql = "select o from QualificationTemp o where o.code = :id";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<QualificationTemp>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<SaqaQualification> findSaqaQualificationByQualificationId(int id) throws Exception {
		String hql = "select o from SaqaQualification o where o.qualificationid = :id";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<SaqaQualification>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Qualification> findQualificationByQualificationId(int id) throws Exception {
		String hql = "select o from Qualification o where o.code = :id";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<Qualification>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<UnitStandards> findUnitStandardByUnitStandardId(int id) throws Exception {
		String hql = "select o from UnitStandards o where o.code = :id";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (List<UnitStandards>) super.getList(hql, parameters);
	}
	
	// *********************************************************************************************************************************** //
}
