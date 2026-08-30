package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyProviderQualification;

public class LegacyProviderQualificationDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyProviderQualification
 	 * @author TechFinium 
 	 * @see    LegacyProviderQualification
 	 * @return a list of {@link haj.com.entity.LegacyProviderQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderQualification> allLegacyProviderQualification() throws Exception {
		return (List<LegacyProviderQualification>)super.getList("select o from LegacyProviderQualification o");
	}

	/**
	 * Get all LegacyProviderQualification between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyProviderQualification
 	 * @return a list of {@link haj.com.entity.LegacyProviderQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderQualification> allLegacyProviderQualification(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyProviderQualification o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyProviderQualification>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyProviderQualification
 	 * @return a {@link haj.com.entity.LegacyProviderQualification}
 	 * @throws Exception global exception
 	 */
	public LegacyProviderQualification findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyProviderQualification o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyProviderQualification)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyProviderQualification by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyProviderQualification
  	 * @return a list of {@link haj.com.entity.LegacyProviderQualification}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderQualification> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyProviderQualification o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyProviderQualification>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderQualification> findBySldNoAndQualNotNull(String sldNo) throws Exception {
	 	String hql = "select o from LegacyProviderQualification o where o.sldNo = :sldNo and o.qualification is not null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sldNo", sldNo);
		return (List<LegacyProviderQualification>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderQualification> findByAccreditationNoAndQualNotNull(String accreditationNo) throws Exception {
	 	String hql = "select o from LegacyProviderQualification o where o.accreditationNo = :accreditationNo and o.qualification is not null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("accreditationNo", accreditationNo);
		return (List<LegacyProviderQualification>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyProviderQualification o")).intValue();
	}
}

