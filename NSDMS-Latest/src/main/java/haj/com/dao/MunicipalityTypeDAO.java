package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.MunicipalityType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class MunicipalityTypeDAO.
 */
public class MunicipalityTypeDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}


	/**
	 * Get all MunicipalityType.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.MunicipalityType}
	 * @throws Exception the exception
	 * @see    MunicipalityType
	 */
	@SuppressWarnings("unchecked")
	public List<MunicipalityType> allMunicipalityType() throws Exception {
		return (List<MunicipalityType>)super.getList("select o from MunicipalityType o");
	}


	/**
	 * All municipality type.
	 *
	 * @param from the from
	 * @param noRows the no rows
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MunicipalityType> allMunicipalityType(Long from, int noRows) throws Exception {
	 	String hql = "select o from MunicipalityType o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<MunicipalityType>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return MunicipalityType
	 * @throws Exception the exception
	 * @see    MunicipalityType
	 */
	public MunicipalityType findByKey(Long id) throws Exception {
	 	String hql = "select o from MunicipalityType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (MunicipalityType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find MunicipalityType by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.MunicipalityType}
	 * @throws Exception the exception
	 * @see    MunicipalityType
	 */
	@SuppressWarnings("unchecked")
	public List<MunicipalityType> findByName(String description) throws Exception {
	 	String hql = "select o from MunicipalityType o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<MunicipalityType>)super.getList(hql, parameters);
	}
}

