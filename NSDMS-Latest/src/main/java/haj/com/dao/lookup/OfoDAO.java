package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Ofo;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class OfoDAO.
 */
public class OfoDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Ofo.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Ofo}
	 * @throws Exception global exception
	 * @see    Ofo
	 */
	@SuppressWarnings("unchecked")
	public List<Ofo> allOfo() throws Exception {
		return (List<Ofo>)super.getList("select o from Ofo o");
	}

	/**
	 * Get all Ofo between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Ofo}
	 * @throws Exception global exception
	 * @see    Ofo
	 */
	@SuppressWarnings("unchecked")
	public List<Ofo> allOfo(Long from, int noRows) throws Exception {
	 	String hql = "select o from Ofo o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Ofo>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Ofo}
	 * @throws Exception global exception
	 * @see    Ofo
	 */
	public Ofo findByKey(Long id) throws Exception {
	 	String hql = "select o from Ofo o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Ofo)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Ofo by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Ofo}
	 * @throws Exception global exception
	 * @see    Ofo
	 */
	@SuppressWarnings("unchecked")
	public List<Ofo> findByName(String description) throws Exception {
	 	String hql = "select o from Ofo o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Ofo>)super.getList(hql, parameters);
	}
}

