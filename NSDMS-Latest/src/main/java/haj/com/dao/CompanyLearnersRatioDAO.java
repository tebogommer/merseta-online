package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.CompanyLearnersRatio;

public class CompanyLearnersRatioDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyLearnersRatio
 	 * @author TechFinium 
 	 * @see    CompanyLearnersRatio
 	 * @return a list of {@link haj.com.entity.CompanyLearnersRatio}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersRatio> allCompanyLearnersRatio() throws Exception {
		return (List<CompanyLearnersRatio>)super.getList("select o from CompanyLearnersRatio o");
	}

	/**
	 * Get all CompanyLearnersRatio between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    CompanyLearnersRatio
 	 * @return a list of {@link haj.com.entity.CompanyLearnersRatio}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersRatio> allCompanyLearnersRatio(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompanyLearnersRatio o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompanyLearnersRatio>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    CompanyLearnersRatio
 	 * @return a {@link haj.com.entity.CompanyLearnersRatio}
 	 * @throws Exception global exception
 	 */
	public CompanyLearnersRatio findByKey(Long id) throws Exception {
	 	String hql = "select o from CompanyLearnersRatio o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompanyLearnersRatio)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyLearnersRatio by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    CompanyLearnersRatio
  	 * @return a list of {@link haj.com.entity.CompanyLearnersRatio}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersRatio> findByName(String description) throws Exception {
	 	String hql = "select o from CompanyLearnersRatio o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompanyLearnersRatio>)super.getList(hql, parameters);
	}
}

