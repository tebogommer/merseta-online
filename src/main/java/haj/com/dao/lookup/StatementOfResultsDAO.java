package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.StatementOfResults;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class StatementOfResultsDAO.
 */
public class StatementOfResultsDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all StatementOfResults.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.StatementOfResults}
	 * @throws Exception global exception
	 * @see    StatementOfResults
	 */
	@SuppressWarnings("unchecked")
	public List<StatementOfResults> allStatementOfResults() throws Exception {
		return (List<StatementOfResults>)super.getList("select o from StatementOfResults o");
	}

	/**
	 * Get all StatementOfResults between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.StatementOfResults}
	 * @throws Exception global exception
	 * @see    StatementOfResults
	 */
	@SuppressWarnings("unchecked")
	public List<StatementOfResults> allStatementOfResults(Long from, int noRows) throws Exception {
	 	String hql = "select o from StatementOfResults o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<StatementOfResults>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.StatementOfResults}
	 * @throws Exception global exception
	 * @see    StatementOfResults
	 */
	public StatementOfResults findByKey(Long id) throws Exception {
	 	String hql = "select o from StatementOfResults o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (StatementOfResults)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find StatementOfResults by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.StatementOfResults}
	 * @throws Exception global exception
	 * @see    StatementOfResults
	 */
	@SuppressWarnings("unchecked")
	public List<StatementOfResults> findByName(String description) throws Exception {
	 	String hql = "select o from StatementOfResults o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<StatementOfResults>)super.getList(hql, parameters);
	}
}

