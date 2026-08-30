package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.LegacyUnitStandardAssessment;

public class LegacyUnitStandardAssessmentDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyUnitStandardAssessment
 	 * @author TechFinium 
 	 * @see    LegacyUnitStandardAssessment
 	 * @return a list of {@link haj.com.entity.LegacyUnitStandardAssessment}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandardAssessment> allLegacyUnitStandardAssessment() throws Exception {
		return (List<LegacyUnitStandardAssessment>)super.getList("select o from LegacyUnitStandardAssessment o");
	}

	/**
	 * Get all LegacyUnitStandardAssessment between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyUnitStandardAssessment
 	 * @return a list of {@link haj.com.entity.LegacyUnitStandardAssessment}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandardAssessment> allLegacyUnitStandardAssessment(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyUnitStandardAssessment o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyUnitStandardAssessment>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyUnitStandardAssessment
 	 * @return a {@link haj.com.entity.LegacyUnitStandardAssessment}
 	 * @throws Exception global exception
 	 */
	public LegacyUnitStandardAssessment findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyUnitStandardAssessment o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyUnitStandardAssessment)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyUnitStandardAssessment by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyUnitStandardAssessment
  	 * @return a list of {@link haj.com.entity.LegacyUnitStandardAssessment}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandardAssessment> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyUnitStandardAssessment o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyUnitStandardAssessment>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyUnitStandardAssessment o")).intValue();
	}
	
	public Integer countAllLegacyUnitStandardAssessmentNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyUnitStandardAssessment o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandardAssessment> allLegacyUnitStandardAssessmentNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyUnitStandardAssessment o where o.processed = false";
		return (List<LegacyUnitStandardAssessment>) super.getList(hql, numberOfEntries);
	}

	@SuppressWarnings("unchecked")
	public List<LegacyUnitStandardAssessment> findByIdNo(String idNo, Long unitStandardID) {
		String hql = "select o from LegacyUnitStandardAssessment o where o.idNo = :idNo and o.unitStandard.id = :unitStandardID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("idNo", idNo);
	    parameters.put("unitStandardID", unitStandardID);
		return (List<LegacyUnitStandardAssessment>)super.getList(hql, parameters);
	}
}

