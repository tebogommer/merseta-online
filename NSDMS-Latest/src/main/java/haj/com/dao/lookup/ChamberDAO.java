package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.AbetBand;
import haj.com.entity.lookup.Chamber;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class ChamberDAO.
 */
public class ChamberDAO extends AbstractDAO {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Chamber.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Chamber}
	 * @throws Exception             global exception
	 * @see Chamber
	 */
	@SuppressWarnings("unchecked")
	public List<Chamber> allChamber() throws Exception {
		return (List<Chamber>) super.getList("select o from Chamber o");
	}

	/**
	 * Get all Chamber between from and noRows.
	 *
	 * @author TechFinium
	 * @param from            the from
	 * @param noRows            the no rows
	 * @return a list of {@link haj.com.entity.Chamber}
	 * @throws Exception             global exception
	 * @see Chamber
	 */
	@SuppressWarnings("unchecked")
	public List<Chamber> allChamber(Long from, int noRows) throws Exception {
		String hql = "select o from Chamber o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<Chamber>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id            the id
	 * @return a {@link haj.com.entity.Chamber}
	 * @throws Exception             global exception
	 * @see Chamber
	 */
	public Chamber findByKey(Long id) throws Exception {
		String hql = "select o from Chamber o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Chamber) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Chamber by description.
	 *
	 * @author TechFinium
	 * @param description            the description
	 * @return a list of {@link haj.com.entity.Chamber}
	 * @throws Exception             global exception
	 * @see Chamber
	 */
	@SuppressWarnings("unchecked")
	public List<Chamber> findByName(String description) throws Exception {
		String hql = "select o from Chamber o where o.description like  :description order by o.description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Chamber>) super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Chamber> findByNameWithoutNonMersetaChamber(String description) throws Exception {
		String hql = "select o from Chamber o where o.description like  :description and o.description Not like 'Non-merSETA Chamber' and o.description Not like 'Not Applicable' order by o.description ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Chamber>) super.getList(hql, parameters);
	}
	
	
	/**
	 * Find object by code.
	 *
	 * @author TechFinium
	 * @param chamber the chamber
	 * @return a {@link haj.com.entity.Chamber}
	 * @throws Exception global exception
	 * @see    AbetBand
	 */
	
	public Chamber findUniqueCode(Chamber chamber) throws Exception {
	 	String hql = "select o from Chamber o where o.code = :code " ;
	 	Map<String, Object> parameters = new Hashtable<String, Object>();
	 	if (chamber.getId()!=null) {
	 		hql += " and o.id <> :id ";
	 		parameters.put("id", chamber.getId());
	 	}
	   
	    parameters.put("code", chamber.getCode());
		return (Chamber)super.getUniqueResult(hql, parameters);
	}
}
