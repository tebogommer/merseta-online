package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.SaqaUnitstandard;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class SaqaUnitstandardDAO.
 */
public class SaqaUnitstandardDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SaqaUnitstandard.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.SaqaUnitstandard}
	 * @throws Exception global exception
	 * @see    SaqaUnitstandard
	 */
	@SuppressWarnings("unchecked")
	public List<SaqaUnitstandard> allSaqaUnitstandard() throws Exception {
		return (List<SaqaUnitstandard>)super.getList("select o from SaqaUnitstandard o");
	}

	/**
	 * Get all SaqaUnitstandard between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.SaqaUnitstandard}
	 * @throws Exception global exception
	 * @see    SaqaUnitstandard
	 */
	@SuppressWarnings("unchecked")
	public List<SaqaUnitstandard> allSaqaUnitstandard(Long from, int noRows) throws Exception {
	 	String hql = "select o from SaqaUnitstandard o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SaqaUnitstandard>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.SaqaUnitstandard}
	 * @throws Exception global exception
	 * @see    SaqaUnitstandard
	 */
	public SaqaUnitstandard findByKey(Long id) throws Exception {
	 	String hql = "select o from SaqaUnitstandard o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SaqaUnitstandard)super.getUniqueResult(hql, parameters);
	}
	
	public SaqaUnitstandard findByNqfleveldescription(String nqfleveldescription) throws Exception {
	 	String hql = "select o from SaqaUnitstandard o where o.nqfleveldescription = :nqfleveldescription " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("nqfleveldescription", nqfleveldescription.trim());
		return (SaqaUnitstandard)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SaqaUnitstandard by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.SaqaUnitstandard}
	 * @throws Exception global exception
	 * @see    SaqaUnitstandard
	 */
	@SuppressWarnings("unchecked")
	public List<SaqaUnitstandard> findByName(String description) throws Exception {
	 	String hql = "select o from SaqaUnitstandard o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SaqaUnitstandard>)super.getList(hql, parameters);
	}
}

