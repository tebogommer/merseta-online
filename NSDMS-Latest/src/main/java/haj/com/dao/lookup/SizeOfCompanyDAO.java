package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.lookup.SizeOfCompany;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SizeOfCompanyDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SizeOfCompany
 	 * @author TechFinium 
 	 * @see    SizeOfCompany
 	 * @return a list of {@link haj.com.entity.SizeOfCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SizeOfCompany> allSizeOfCompany() throws Exception {
		return (List<SizeOfCompany>)super.getList("select o from SizeOfCompany o");
	}

	/**
	 * Get all SizeOfCompany between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SizeOfCompany
 	 * @return a list of {@link haj.com.entity.SizeOfCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SizeOfCompany> allSizeOfCompany(Long from, int noRows) throws Exception {
	 	String hql = "select o from SizeOfCompany o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SizeOfCompany>)super.getList(hql, parameters,from.intValue(),noRows);
	}
	
	public SizeOfCompany findCompanySize(int numberOfEmployees) throws Exception {
	 	String hql = "select o from SizeOfCompany o where :numberOfEmployees BETWEEN o.minSize AND coalesce(o.mazSize,:numberOfEmployees)";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("numberOfEmployees", numberOfEmployees);
		return (SizeOfCompany)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SizeOfCompany
 	 * @return a {@link haj.com.entity.SizeOfCompany}
 	 * @throws Exception global exception
 	 */
	public SizeOfCompany findByKey(Long id) throws Exception {
	 	String hql = "select o from SizeOfCompany o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SizeOfCompany)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SizeOfCompany by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SizeOfCompany
  	 * @return a list of {@link haj.com.entity.SizeOfCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SizeOfCompany> findByName(String description) throws Exception {
	 	String hql = "select o from SizeOfCompany o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SizeOfCompany>)super.getList(hql, parameters);
	}
}

