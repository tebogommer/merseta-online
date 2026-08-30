package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Municipality;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class MunicipalityDAO.
 */
public class MunicipalityDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}


	/**
	 * Get all Municipality.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Municipality}
	 * @throws Exception the exception
	 * @see    Municipality
	 */
	@SuppressWarnings("unchecked")
	public List<Municipality> allMunicipality() throws Exception {
		return (List<Municipality>)super.getList("select o from Municipality o");
	}


	/**
	 * All municipality.
	 *
	 * @param from the from
	 * @param noRows the no rows
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Municipality> allMunicipality(Long from, int noRows) throws Exception {
	 	String hql = "select o from Municipality o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Municipality>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return Municipality
	 * @throws Exception the exception
	 * @see    Municipality
	 */
	public Municipality findByKey(Integer id) throws Exception {
	 	String hql = "select o from Municipality o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Municipality)super.getUniqueResult(hql, parameters);
	}
	
	/**
	 * Find by code.
	 *
	 * @param code the code
	 * @return the municipality
	 * @throws Exception the exception
	 */
	public Municipality findByCode(String code) throws Exception {
	 	String hql = "select o from Municipality o where o.code = :code " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code);
		return (Municipality)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Municipality by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Municipality}
	 * @throws Exception the exception
	 * @see    Municipality
	 */
	@SuppressWarnings("unchecked")
	public List<Municipality> findByName(String description) throws Exception {
	 	String hql = "select o from Municipality o left join fetch o.province where o.municipalityDesc like :description order by o.municipalityDesc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Municipality>)super.getList(hql, parameters);
	}
}

