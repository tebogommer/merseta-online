package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.UrbalRural;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class UrbalRuralDAO.
 */
public class UrbalRuralDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all UrbalRural.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.UrbalRural}
	 * @throws Exception global exception
	 * @see    UrbalRural
	 */
	@SuppressWarnings("unchecked")
	public List<UrbalRural> allUrbalRural() throws Exception {
		return (List<UrbalRural>)super.getList("select o from UrbalRural o");
	}

	/**
	 * Get all UrbalRural between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.UrbalRural}
	 * @throws Exception global exception
	 * @see    UrbalRural
	 */
	@SuppressWarnings("unchecked")
	public List<UrbalRural> allUrbalRural(Long from, int noRows) throws Exception {
	 	String hql = "select o from UrbalRural o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<UrbalRural>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.UrbalRural}
	 * @throws Exception global exception
	 * @see    UrbalRural
	 */
	public UrbalRural findByKey(Long id) throws Exception {
	 	String hql = "select o from UrbalRural o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (UrbalRural)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find UrbalRural by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.UrbalRural}
	 * @throws Exception global exception
	 * @see    UrbalRural
	 */
	@SuppressWarnings("unchecked")
	public List<UrbalRural> findByName(String description) throws Exception {
	 	String hql = "select o from UrbalRural o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<UrbalRural>)super.getList(hql, parameters);
	}
	
	/**
	 * Find UrbalUral by UniqueId
	 * CSG 25/10/2017.
	 *
	 * @param urbalRural the urbal rural
	 * @return the urbal rural
	 * @throws Exception the exception
	 */
	public UrbalRural findUniqueCode(UrbalRural urbalRural) throws Exception {
	 	String hql = "select o from UrbalRural o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (urbalRural.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", urbalRural.getId());
	 	}
	   
	    parameters.put("code", urbalRural.getCode());
		return (UrbalRural)super.getUniqueResult(hql, parameters);
	}
}

