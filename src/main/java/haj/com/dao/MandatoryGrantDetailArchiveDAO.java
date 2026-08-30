package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.MandatoryGrantDetailArchive;

public class MandatoryGrantDetailArchiveDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all MandatoryGrantDetailArchive
 	 * @author TechFinium 
 	 * @see    MandatoryGrantDetailArchive
 	 * @return a list of {@link haj.com.entity.MandatoryGrantDetailArchive}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetailArchive> allMandatoryGrantDetailArchive() throws Exception {
		return (List<MandatoryGrantDetailArchive>)super.getList("select o from MandatoryGrantDetailArchive o");
	}

	/**
	 * Get all MandatoryGrantDetailArchive between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    MandatoryGrantDetailArchive
 	 * @return a list of {@link haj.com.entity.MandatoryGrantDetailArchive}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetailArchive> allMandatoryGrantDetailArchive(Long from, int noRows) throws Exception {
	 	String hql = "select o from MandatoryGrantDetailArchive o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<MandatoryGrantDetailArchive>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    MandatoryGrantDetailArchive
 	 * @return a {@link haj.com.entity.MandatoryGrantDetailArchive}
 	 * @throws Exception global exception
 	 */
	public MandatoryGrantDetailArchive findByKey(Long id) throws Exception {
	 	String hql = "select o from MandatoryGrantDetailArchive o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (MandatoryGrantDetailArchive)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find MandatoryGrantDetailArchive by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    MandatoryGrantDetailArchive
  	 * @return a list of {@link haj.com.entity.MandatoryGrantDetailArchive}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MandatoryGrantDetailArchive> findByName(String description) throws Exception {
	 	String hql = "select o from MandatoryGrantDetailArchive o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<MandatoryGrantDetailArchive>)super.getList(hql, parameters);
	}
}

