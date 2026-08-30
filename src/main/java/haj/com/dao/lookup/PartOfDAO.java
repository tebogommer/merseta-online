package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.PartOf;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class PartOfDAO.
 */
public class PartOfDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all PartOf.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.PartOf}
	 * @throws Exception global exception
	 * @see    PartOf
	 */
	@SuppressWarnings("unchecked")
	public List<PartOf> allPartOf() throws Exception {
		return (List<PartOf>)super.getList("select o from PartOf o");
	}

	/**
	 * Get all PartOf between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.PartOf}
	 * @throws Exception global exception
	 * @see    PartOf
	 */
	@SuppressWarnings("unchecked")
	public List<PartOf> allPartOf(Long from, int noRows) throws Exception {
	 	String hql = "select o from PartOf o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<PartOf>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.PartOf}
	 * @throws Exception global exception
	 * @see    PartOf
	 */
	public PartOf findByKey(Long id) throws Exception {
	 	String hql = "select o from PartOf o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (PartOf)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find PartOf by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.PartOf}
	 * @throws Exception global exception
	 * @see    PartOf
	 */
	@SuppressWarnings("unchecked")
	public List<PartOf> findByName(String description) throws Exception {
	 	String hql = "select o from PartOf o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<PartOf>)super.getList(hql, parameters);
	}
	
	/**
	 * Find unique code.
	 *
	 * @param partOf the part of
	 * @return the part of
	 * @throws Exception the exception
	 */
	public PartOf findUniqueCode(PartOf partOf) throws Exception {
	 	String hql = "select o from PartOf o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (partOf.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", partOf.getId());
	 	}
	   
	    parameters.put("code", partOf.getCode());
		return (PartOf)super.getUniqueResult(hql, parameters);
	}
}

