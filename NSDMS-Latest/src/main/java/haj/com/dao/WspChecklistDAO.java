package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Wsp;
import haj.com.entity.WspChecklist;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class WspChecklistDAO.
 */
public class WspChecklistDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all WspChecklist.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.WspChecklist}
	 * @throws Exception global exception
	 * @see    WspChecklist
	 */
	@SuppressWarnings("unchecked")
	public List<WspChecklist> allWspChecklist() throws Exception {
		return (List<WspChecklist>)super.getList("select o from WspChecklist o");
	}

	/**
	 * Get all WspChecklist between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.WspChecklist}
	 * @throws Exception global exception
	 * @see    WspChecklist
	 */
	@SuppressWarnings("unchecked")
	public List<WspChecklist> allWspChecklist(Long from, int noRows) throws Exception {
	 	String hql = "select o from WspChecklist o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<WspChecklist>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.WspChecklist}
	 * @throws Exception global exception
	 * @see    WspChecklist
	 */
	public WspChecklist findByKey(Long id) throws Exception {
	 	String hql = "select o from WspChecklist o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (WspChecklist)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find WspChecklist by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.WspChecklist}
	 * @throws Exception global exception
	 * @see    WspChecklist
	 */
	@SuppressWarnings("unchecked")
	public List<WspChecklist> findByName(String description) throws Exception {
	 	String hql = "select o from WspChecklist o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<WspChecklist>)super.getList(hql, parameters);
	}

	/**
	 * Find by WSP.
	 *
	 * @param wsp the wsp
	 * @return the wsp checklist
	 * @throws Exception the exception
	 */
	public WspChecklist findByWSP(Wsp wsp) throws Exception {
		String hql = "select o from WspChecklist o where o.wsp.id = :wspId";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("wspId", wsp.getId());
		return (WspChecklist) super.getUniqueResult(hql, parameters);
	}

}

