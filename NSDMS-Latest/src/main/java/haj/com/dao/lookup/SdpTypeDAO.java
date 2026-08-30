package haj.com.dao.lookup;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.lookup.SdpType;

public class SdpTypeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SdpType
 	 * @author TechFinium 
 	 * @see    SdpType
 	 * @return a list of {@link haj.com.entity.SdpType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SdpType> allSdpType() throws Exception {
		return (List<SdpType>)super.getList("select o from SdpType o");
	}

	/**
	 * Get all SdpType between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SdpType
 	 * @return a list of {@link haj.com.entity.SdpType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SdpType> allSdpType(Long from, int noRows) throws Exception {
	 	String hql = "select o from SdpType o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<SdpType>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SdpType
 	 * @return a {@link haj.com.entity.SdpType}
 	 * @throws Exception global exception
 	 */
	public SdpType findByKey(Long id) throws Exception {
	 	String hql = "select o from SdpType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SdpType)super.getUniqueResult(hql, parameters);
	}
	
	public SdpType findByDesignationId(Long designationId) throws Exception {
	 	String hql = "select o from SdpType o where o.designation.id = :designationId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("designationId", designationId);
		return (SdpType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SdpType by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SdpType
  	 * @return a list of {@link haj.com.entity.SdpType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SdpType> findByName(String description) throws Exception {
	 	String hql = "select o from SdpType o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SdpType>)super.getList(hql, parameters);
	}
	
	public Integer countByDesignationId(Long designationId) throws Exception {
	 	String hql = "select count(o) from SdpType o where o.designation.id = :designationId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("designationId", designationId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public Integer countByDesignationIdAndNotEqualsSdpTypeId(Long designationId, Long sdpTypeId) throws Exception {
	 	String hql = "select count(o) from SdpType o where o.designation.id = :designationId and o.id <> :sdpTypeId" ;
	    Map<String, Object> parameters = new HashMap<>();
	    parameters.put("designationId", designationId);
	    parameters.put("sdpTypeId", sdpTypeId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
}

