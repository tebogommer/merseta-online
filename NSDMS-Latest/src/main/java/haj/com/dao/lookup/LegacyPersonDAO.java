package haj.com.dao.lookup;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.MandatoryGrantDetail;
import haj.com.entity.enums.WspStatusEnum;
import haj.com.entity.lookup.LegacyApprenticeship;
import haj.com.entity.lookup.LegacyPerson;

public class LegacyPersonDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyPerson
 	 * @author TechFinium 
 	 * @see    LegacyPerson
 	 * @return a list of {@link haj.com.entity.LegacyPerson}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyPerson> allLegacyPerson() throws Exception {
		return (List<LegacyPerson>)super.getList("select o from LegacyPerson o");
	}
	
	@SuppressWarnings("unchecked")
	public List<LegacyPerson> allLegacyPersonNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyPerson o where o.processed = false";
		return (List<LegacyPerson>) super.getList(hql,  numberOfEntries);
	}
	
	public Integer countAllLegacyPersonNotProcessed() throws Exception {
	 	String hql = "select count(o) from LegacyPerson o where o.processed = false" ;
		return ((Long)super.getUniqueResult(hql)).intValue();
	}

	/**
	 * Get all LegacyPerson between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyPerson
 	 * @return a list of {@link haj.com.entity.LegacyPerson}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyPerson> allLegacyPerson(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyPerson o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyPerson>)super.getList(hql, parameters,from.intValue(),noRows);
	}
	
	

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyPerson
 	 * @return a {@link haj.com.entity.LegacyPerson}
 	 * @throws Exception global exception
 	 */
	public LegacyPerson findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyPerson o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyPerson)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyPerson by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyPerson
  	 * @return a list of {@link haj.com.entity.LegacyPerson}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyPerson> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyPerson o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyPerson>)super.getList(hql, parameters);
	}

	public LegacyPerson findByIdNo(String idNo) throws Exception {
	 	String hql = "select o from LegacyPerson o where o.idNo = :idNo" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("idNo", idNo.trim());
		return (LegacyPerson)super.getUniqueResult(hql, parameters);
	}
	
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyPerson o")).intValue();
	}
	
	public Integer countAllValidRsaIdNumberEmpty() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyPerson o where o.validIdNumber is null")).intValue();
	}
	
	public Integer countAllValidRsaIdNumberByValue(Boolean value) throws Exception {
	 	String hql = "select count(o) from LegacyPerson o where o.validIdNumber = :value " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("value", value);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countAllEntriesByIdNo(String idNo) throws Exception {
	 	String hql = "select count(o) from LegacyPerson o where o.idNo = :idNo " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("idNo", idNo.trim());
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countAllEntriesByAlternativeIdNumber(String alternativeIdNo) throws Exception {
	 	String hql = "select count(o) from LegacyPerson o where UPPER(o.alternateIDNo) = :alternativeIdNo " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("alternativeIdNo", alternativeIdNo.trim().toUpperCase());
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
}