package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Province;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;


// TODO: Auto-generated Javadoc
/**
 * Class ProvinceDAO.
 */
public class ProvinceDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}


	/**
	 * Get all Province.
	 *
	 * @author TechFinium
	 * @return List of {@link haj.com.entity.Province}
	 * @throws Exception the exception
	 * @see    Province
	 */
	@SuppressWarnings("unchecked")
	public List<Province> allProvince() throws Exception {
		return (List<Province>)super.getList("select o from Province o");
	}


	/**
	 * All province.
	 *
	 * @param from the from
	 * @param noRows the no rows
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<Province> allProvince(Long from, int noRows) throws Exception {
	 	String hql = "select o from Province o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Province>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return Province
	 * @throws Exception the exception
	 * @see    Province
	 */
	public Province findByKey(Long id) throws Exception {
	 	String hql = "select o from Province o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Province)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Province by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Province}
	 * @throws Exception the exception
	 * @see    Province
	 */
	@SuppressWarnings("unchecked")
	public List<Province> findByName(String description) throws Exception {
	 	String hql = "select o from Province o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Province>)super.getList(hql, parameters);
	}
	
	public Province findByCode(String code) throws Exception {
	 	String hql = "select o from Province o where upper(o.code) = :code " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("code", code.trim().toUpperCase());
		return (Province)super.getUniqueResult(hql, parameters);
	}
}

