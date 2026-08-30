package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.CompanyLearnersQualificationAchievementStatus;

public class CompanyLearnersQualificationAchievementStatusDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompanyLearnersQualificationAchievementStatus
 	 * @author TechFinium 
 	 * @see    CompanyLearnersQualificationAchievementStatus
 	 * @return a list of {@link haj.com.entity.CompanyLearnersQualificationAchievementStatus}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersQualificationAchievementStatus> allCompanyLearnersQualificationAchievementStatus() throws Exception {
		return (List<CompanyLearnersQualificationAchievementStatus>)super.getList("select o from CompanyLearnersQualificationAchievementStatus o");
	}

	/**
	 * Get all CompanyLearnersQualificationAchievementStatus between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    CompanyLearnersQualificationAchievementStatus
 	 * @return a list of {@link haj.com.entity.CompanyLearnersQualificationAchievementStatus}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersQualificationAchievementStatus> allCompanyLearnersQualificationAchievementStatus(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompanyLearnersQualificationAchievementStatus o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompanyLearnersQualificationAchievementStatus>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    CompanyLearnersQualificationAchievementStatus
 	 * @return a {@link haj.com.entity.CompanyLearnersQualificationAchievementStatus}
 	 * @throws Exception global exception
 	 */
	public CompanyLearnersQualificationAchievementStatus findByKey(Long id) throws Exception {
	 	String hql = "select o from CompanyLearnersQualificationAchievementStatus o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompanyLearnersQualificationAchievementStatus)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompanyLearnersQualificationAchievementStatus by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    CompanyLearnersQualificationAchievementStatus
  	 * @return a list of {@link haj.com.entity.CompanyLearnersQualificationAchievementStatus}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompanyLearnersQualificationAchievementStatus> findByName(String description) throws Exception {
	 	String hql = "select o from CompanyLearnersQualificationAchievementStatus o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompanyLearnersQualificationAchievementStatus>)super.getList(hql, parameters);
	}
}

