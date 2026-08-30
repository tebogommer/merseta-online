package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.LegacyDTTCApproval;
import haj.com.entity.lookup.LegacyExperiential;

public class LegacyDTTCApprovalDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all LegacyDTTCApproval
 	 * @author TechFinium 
 	 * @see    LegacyDTTCApproval
 	 * @return a list of {@link haj.com.entity.LegacyDTTCApproval}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCApproval> allLegacyDTTCApproval() throws Exception {
		return (List<LegacyDTTCApproval>)super.getList("select o from LegacyDTTCApproval o");
	}

	/**
	 * Get all LegacyDTTCApproval between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    LegacyDTTCApproval
 	 * @return a list of {@link haj.com.entity.LegacyDTTCApproval}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCApproval> allLegacyDTTCApproval(Long from, int noRows) throws Exception {
	 	String hql = "select o from LegacyDTTCApproval o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<LegacyDTTCApproval>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    LegacyDTTCApproval
 	 * @return a {@link haj.com.entity.LegacyDTTCApproval}
 	 * @throws Exception global exception
 	 */
	public LegacyDTTCApproval findByKey(Long id) throws Exception {
	 	String hql = "select o from LegacyDTTCApproval o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (LegacyDTTCApproval)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find LegacyDTTCApproval by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    LegacyDTTCApproval
  	 * @return a list of {@link haj.com.entity.LegacyDTTCApproval}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<LegacyDTTCApproval> findByName(String description) throws Exception {
	 	String hql = "select o from LegacyDTTCApproval o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<LegacyDTTCApproval>)super.getList(hql, parameters);
	}
	
	public Integer countAllResults() throws Exception {
		return ((Long)super.getUniqueResult("select count(o) from LegacyDTTCApproval o")).intValue();
	}
	
	public Integer countAllLegacyDTTCApprovalNotProcessed() throws Exception {
		String hql = "select count(o) from LegacyDTTCApproval o where o.processed = false";
		return ((Long) super.getUniqueResult(hql)).intValue();
	}

	@SuppressWarnings("unchecked")
	public List<LegacyDTTCApproval> allLegacyDTTCApprovalNotProcessed(int numberOfEntries) throws Exception {
		String hql = "select o from LegacyDTTCApproval o where o.processed = false";
		return (List<LegacyDTTCApproval>) super.getList(hql, numberOfEntries);
	}
	
}

