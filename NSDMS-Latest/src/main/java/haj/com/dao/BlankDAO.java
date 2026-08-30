package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Blank;
import haj.com.entity.ExtractErrorList;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankDAO.
 */
public class BlankDAO extends AbstractDAO {

	/*
	 * (non-Javadoc)
	 * 
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Blank.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Blank}
	 * @throws Exception
	 *             global exception
	 * @see Blank
	 */
	@SuppressWarnings("unchecked")
	public List<Blank> allBlank() throws Exception {
		return (List<Blank>) super.getList("select o from Blank o");
	}

	/**
	 * Get all Blank between from and noRows.
	 *
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @return a list of {@link haj.com.entity.Blank}
	 * @throws Exception
	 *             global exception
	 * @see Blank
	 */
	@SuppressWarnings("unchecked")
	public List<Blank> allBlank(Long from, int noRows) throws Exception {
		String hql = "select o from Blank o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<Blank>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @return a {@link haj.com.entity.Blank}
	 * @throws Exception
	 *             global exception
	 * @see Blank
	 */
	public Blank findByKey(Long id) throws Exception {
		String hql = "select o from Blank o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (Blank) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Blank by description.
	 *
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @return a list of {@link haj.com.entity.Blank}
	 * @throws Exception
	 *             global exception
	 * @see Blank
	 */
	@SuppressWarnings("unchecked")
	public List<Blank> findByName(String description) throws Exception {
		String hql = "select o from Blank o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<Blank>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ExtractErrorList> findExportErrors() throws Exception {
		String hql = "select o from ExtractErrorList o";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		return (List<ExtractErrorList>) super.getList(hql, parameters);
	}
}
