package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.Bank;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class BankDAO.
 */
public class BankDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all Bank.
	 *
	 * @author TechFinium
	 * @return a list of {@link haj.com.entity.Bank}
	 * @throws Exception global exception
	 * @see    Bank
	 */
	@SuppressWarnings("unchecked")
	public List<Bank> allBank() throws Exception {
		return (List<Bank>)super.getList("select o from Bank o");
	}

	/**
	 * Get all Bank between from and noRows.
	 *
	 * @author TechFinium
	 * @param from the from
	 * @param noRows the no rows
	 * @return a list of {@link haj.com.entity.Bank}
	 * @throws Exception global exception
	 * @see    Bank
	 */
	@SuppressWarnings("unchecked")
	public List<Bank> allBank(Long from, int noRows) throws Exception {
	 	String hql = "select o from Bank o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<Bank>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.Bank}
	 * @throws Exception global exception
	 * @see    Bank
	 */
	public Bank findByKey(Long id) throws Exception {
	 	String hql = "select o from Bank o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Bank)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find Bank by description.
	 *
	 * @author TechFinium
	 * @param description the description
	 * @return a list of {@link haj.com.entity.Bank}
	 * @throws Exception global exception
	 * @see    Bank
	 */
	@SuppressWarnings("unchecked")
	public List<Bank> findByName(String description) throws Exception {
	 	String hql = "select o from Bank o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<Bank>)super.getList(hql, parameters);
	}
}

