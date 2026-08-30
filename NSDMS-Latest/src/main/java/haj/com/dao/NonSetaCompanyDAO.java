package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.NonSetaCompany;

public class NonSetaCompanyDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all NonSetaCompany
 	 * @author TechFinium 
 	 * @see    NonSetaCompany
 	 * @return a list of {@link haj.com.entity.NonSetaCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NonSetaCompany> allNonSetaCompany() throws Exception {
		return (List<NonSetaCompany>)super.getList("select o from NonSetaCompany o");
	}

	/**
	 * Get all NonSetaCompany between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    NonSetaCompany
 	 * @return a list of {@link haj.com.entity.NonSetaCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NonSetaCompany> allNonSetaCompany(Long from, int noRows) throws Exception {
	 	String hql = "select o from NonSetaCompany o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<NonSetaCompany>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    NonSetaCompany
 	 * @return a {@link haj.com.entity.NonSetaCompany}
 	 * @throws Exception global exception
 	 */
	public NonSetaCompany findByKey(Long id) throws Exception {
	 	String hql = "select o from NonSetaCompany o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (NonSetaCompany)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find NonSetaCompany by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    NonSetaCompany
  	 * @return a list of {@link haj.com.entity.NonSetaCompany}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<NonSetaCompany> findByName(String description) throws Exception {
	 	String hql = "select o from NonSetaCompany o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<NonSetaCompany>)super.getList(hql, parameters);
	}

	public NonSetaCompany findRegistrationNumber(String companyRegistrationNumber) {
		String hql = "select o from NonSetaCompany o where o.companyRegistrationNumber = :companyRegistrationNumber " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyRegistrationNumber", companyRegistrationNumber);
		return (NonSetaCompany)super.getUniqueResult(hql, parameters);
	}
}

