package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.CompanyLearnersAchievementStatusEISA;

public class CompanyLearnersAchievementStatusEISADAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyLearnersAchievementStatusEISA
 	 * @author TechFinium 
 	 * @see    CompanyLearnersAchievementStatusEISA
 	 * @return a list of {@link haj.com.entity.CompanyLearnersAchievementStatusEISA}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersAchievementStatusEISA> allCompanyLearnersAchievementStatusEISA() throws Exception {
		return (List<CompanyLearnersAchievementStatusEISA>)super.getList("select o from CompanyLearnersAchievementStatusEISA o");
	}

	/**
	 * Get all CompanyLearnersAchievementStatusEISA between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    CompanyLearnersAchievementStatusEISA
 	 * @return a list of {@link haj.com.entity.CompanyLearnersAchievementStatusEISA}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersAchievementStatusEISA> allCompanyLearnersAchievementStatusEISA(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompanyLearnersAchievementStatusEISA o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompanyLearnersAchievementStatusEISA>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    CompanyLearnersAchievementStatusEISA
 	 * @return a {@link haj.com.entity.CompanyLearnersAchievementStatusEISA}
 	 * @throws Exception global exception
 	 */
	public CompanyLearnersAchievementStatusEISA findByKey(Long id) throws Exception {
	 	String hql = "select o from CompanyLearnersAchievementStatusEISA o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompanyLearnersAchievementStatusEISA)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyLearnersAchievementStatusEISA by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    CompanyLearnersAchievementStatusEISA
  	 * @return a list of {@link haj.com.entity.CompanyLearnersAchievementStatusEISA}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersAchievementStatusEISA> findByName(String description) throws Exception {
	 	String hql = "select o from CompanyLearnersAchievementStatusEISA o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompanyLearnersAchievementStatusEISA>)super.getList(hql, parameters);
	}
}

