package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyProviderUnitStandard;

public class LegacyProviderUnitStandardDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyProviderUnitStandard
 	 * @author TechFinium 
 	 * @see    LegacyProviderUnitStandard
 	 * @return a list of {@link haj.com.entity.LegacyProviderUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderUnitStandard> allLegacyProviderUnitStandard() throws Exception {
		return (List<LegacyProviderUnitStandard>)super.getList("select o from LegacyProviderUnitStandard o");
	}

	/**
	 * Get all LegacyProviderUnitStandard between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyProviderUnitStandard
 	 * @return a list of {@link haj.com.entity.LegacyProviderUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderUnitStandard> allLegacyProviderUnitStandard(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyProviderUnitStandard o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyProviderUnitStandard>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyProviderUnitStandard
 	 * @return a {@link haj.com.entity.LegacyProviderUnitStandard}
 	 * @throws Exception global exception
 	 */
	public LegacyProviderUnitStandard findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyProviderUnitStandard o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyProviderUnitStandard)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyProviderUnitStandard by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyProviderUnitStandard
  	 * @return a list of {@link haj.com.entity.LegacyProviderUnitStandard}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyProviderUnitStandard> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyProviderUnitStandard o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyProviderUnitStandard>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderUnitStandard> findBySldNoAndUnitStandardIsNotNull(String sldNo) throws Exception {
	 	String hql = "select o from LegacyProviderUnitStandard o where o.sldNo = :sldNo and o.unitStandard is not null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("sldNo", sldNo);
		return (List<LegacyProviderUnitStandard>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyProviderUnitStandard> findByAccreditationNoAndUnitStandardIsNotNull(String accreditationNo) throws Exception {
	 	String hql = "select o from LegacyProviderUnitStandard o where o.accreditationNo = :accreditationNo and o.unitStandard is not null " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("accreditationNo", accreditationNo);
		return (List<LegacyProviderUnitStandard>)super.getList(hql, parameters);
	}
	
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyProviderUnitStandard o")).intValue();
	}
}

