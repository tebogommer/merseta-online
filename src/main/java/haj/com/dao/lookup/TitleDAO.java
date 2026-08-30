package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Title;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class TitleDAO.
 */
public class TitleDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Title.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Title}
	 * @throws Exception global exception
	 * @see    Title
	 */
	@SuppressWarnings("unchecked")
	public List<Title> allTitle() throws Exception {
		return (List<Title>)super.getList("select o from Title o");
	}

	/**
	 * Get all Title between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Title}
	 * @throws Exception global exception
	 * @see    Title
	 */
	@SuppressWarnings("unchecked")
	public List<Title> allTitle(Long from, int noRows) throws Exception {
	 	String hql = "select o from Title o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Title>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Title}
	 * @throws Exception global exception
	 * @see    Title
	 */
	public Title findByKey(Long id) throws Exception {
	 	String hql = "select o from Title o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Title)super.getUniqueResult(hql, parameters);
	}
	
	public Title findByDescription(String description) throws Exception {
	 	String hql = "select o from Title o where o.description = :description " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", description);
		return (Title)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Title by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Title}
	 * @throws Exception global exception
	 * @see    Title
	 */
	@SuppressWarnings("unchecked")
	public List<Title> findByName(String description) throws Exception {
	 	String hql = "select o from Title o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Title>)super.getList(hql, parameters);
	}
}

