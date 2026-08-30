package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.json.CurrencyBean;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class CurrencyDAO.
 */
public class CurrencyDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}
	

	/**
	 * All currency bean.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<CurrencyBean> allCurrencyBean() throws Exception {
		return (List<CurrencyBean>)super.getList("select o from CurrencyBean o");
	}

/*
	@SuppressWarnings("unchecked")
	public List<Blank> findByCompany(Integer companyId) throws Exception {
	 	String hql = "select o from Blank o where o.company.id = :companyId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
		return (List<Blank>)super.getList(hql, parameters);
	}


	@SuppressWarnings("unchecked")
	public List<Blank> byField(long key) throws Exception  {
		String hql = "select o from Blank o where o.key = :key";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("key", key);
	    return (List<Blank>)super.getList(hql, parameters);
	}
*/

	/**
 * Find by key.
 *
 * @param id the id
 * @return the currency bean
 * @throws Exception the exception
 */
public CurrencyBean findByKey(Long id) throws Exception {
	 	String hql = "select o from CurrencyBean o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CurrencyBean)super.getUniqueResult(hql, parameters);
	}

	
	/**
	 * Find by base date.
	 *
	 * @param base the base
	 * @param date the date
	 * @return the currency bean
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public CurrencyBean findByBaseDate(String base, String date) throws Exception {
	 	String hql = "select o from CurrencyBean o where o.base =  :base and o.date <= :date order by o.created desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("base", base);
	    parameters.put("date", date);
	    List<CurrencyBean> l  = (List<CurrencyBean>)super.getList(hql, parameters,1);
	    if (l!=null && l.size() > 0) return l.get(0);
		return (CurrencyBean)super.getUniqueResult(hql, parameters);
	}
}

