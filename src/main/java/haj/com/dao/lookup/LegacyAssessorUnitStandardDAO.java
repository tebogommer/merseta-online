package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyAssessorUnitStandard;
import haj.com.entity.lookup.LegacyExperiential;

public class LegacyAssessorUnitStandardDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyAssessorUnitStandard
 	 * @author TechFinium 
 	 * @see    LegacyAssessorUnitStandard
 	 * @return a list of {@link haj.com.entity.LegacyAssessorUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorUnitStandard> allLegacyAssessorUnitStandard() throws Exception {
		return (List<LegacyAssessorUnitStandard>)super.getList("select o from LegacyAssessorUnitStandard o");
	}

	/**
	 * Get all LegacyAssessorUnitStandard between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyAssessorUnitStandard
 	 * @return a list of {@link haj.com.entity.LegacyAssessorUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorUnitStandard> allLegacyAssessorUnitStandard(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyAssessorUnitStandard o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyAssessorUnitStandard>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyAssessorUnitStandard
 	 * @return a {@link haj.com.entity.LegacyAssessorUnitStandard}
 	 * @throws Exception global exception
 	 */
	public LegacyAssessorUnitStandard findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyAssessorUnitStandard o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyAssessorUnitStandard)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyAssessorUnitStandard by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyAssessorUnitStandard
  	 * @return a list of {@link haj.com.entity.LegacyAssessorUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorUnitStandard> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyAssessorUnitStandard o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyAssessorUnitStandard>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyAssessorUnitStandard o")).intValue();
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorUnitStandard> findByAssessorIdNumber(String idNumber) throws Exception {
	 	String hql = "select o from LegacyAssessorUnitStandard o where o.assessorid = :assessorid " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("assessorid", idNumber.trim());
		return (List<LegacyAssessorUnitStandard>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorUnitStandard> findByAssessorRegNo(String assessorRegNo) throws Exception {
	 	String hql = "select o from LegacyAssessorUnitStandard o where o.assessorRegNo = :assessorRegNo and o.unitStandard is not null";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("assessorRegNo", assessorRegNo.trim());
		return (List<LegacyAssessorUnitStandard>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyAssessorUnitStandard> findByAssessorIdNumberRegNo(String entry) throws Exception {
	 	String hql = "select o from LegacyAssessorUnitStandard o where (o.assessorRegNo = :entry or o.assessorid = :entry)";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("entry", entry.trim());
		return (List<LegacyAssessorUnitStandard>)super.getList(hql, parameters);
	}
	
	public Integer countAllLegacyAssessorUnitStandardNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyAssessorUnitStandard o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyAssessorUnitStandard> allLegacyAssessorUnitStandardNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyAssessorUnitStandard o where o.processed = false";
		return (List<LegacyAssessorUnitStandard>) super.getList(hql, numberOfEntries);
	}
	
}