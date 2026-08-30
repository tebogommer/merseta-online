package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.ExceptionsTable;

public class ExceptionsTableDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ExceptionsTable
 	 * @author TechFinium 
 	 * @see    ExceptionsTable
 	 * @return a list of {@link haj.com.entity.ExceptionsTable}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ExceptionsTable> allExceptionsTable() throws Exception {
		return (List<ExceptionsTable>)super.getList("select o from ExceptionsTable o");
	}

	/**
	 * Get all ExceptionsTable between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ExceptionsTable
 	 * @return a list of {@link haj.com.entity.ExceptionsTable}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ExceptionsTable> allExceptionsTable(Long from, int noRows) throws Exception {
	 	String hql = "select o from ExceptionsTable o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<ExceptionsTable>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    ExceptionsTable
 	 * @return a {@link haj.com.entity.ExceptionsTable}
 	 * @throws Exception global exception
 	 */
	public ExceptionsTable findByKey(Long id) throws Exception {
	 	String hql = "select o from ExceptionsTable o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ExceptionsTable)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ExceptionsTable by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    ExceptionsTable
  	 * @return a list of {@link haj.com.entity.ExceptionsTable}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ExceptionsTable> findByName(String description) throws Exception {
	 	String hql = "select o from ExceptionsTable o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ExceptionsTable>)super.getList(hql, parameters);
	}
}

