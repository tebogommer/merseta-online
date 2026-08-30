package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.CompanyLearnersProgress;

public class CompanyLearnersProgressDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyLearnersProgress
 	 * @author TechFinium 
 	 * @see    CompanyLearnersProgress
 	 * @return a list of {@link haj.com.entity.CompanyLearnersProgress}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersProgress> allCompanyLearnersProgress() throws Exception {
		return (List<CompanyLearnersProgress>)super.getList("select o from CompanyLearnersProgress o");
	}

	/**
	 * Get all CompanyLearnersProgress between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    CompanyLearnersProgress
 	 * @return a list of {@link haj.com.entity.CompanyLearnersProgress}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersProgress> allCompanyLearnersProgress(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompanyLearnersProgress o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompanyLearnersProgress>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    CompanyLearnersProgress
 	 * @return a {@link haj.com.entity.CompanyLearnersProgress}
 	 * @throws Exception global exception
 	 */
	public CompanyLearnersProgress findByKey(Long id) throws Exception {
	 	String hql = "select o from CompanyLearnersProgress o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompanyLearnersProgress)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyLearnersProgress by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    CompanyLearnersProgress
  	 * @return a list of {@link haj.com.entity.CompanyLearnersProgress}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersProgress> findByName(String description) throws Exception {
	 	String hql = "select o from CompanyLearnersProgress o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompanyLearnersProgress>)super.getList(hql, parameters);
	}
}

