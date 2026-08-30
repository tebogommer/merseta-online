package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.DetailsOfExperienceArpl;

public class DetailsOfExperienceArplDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DetailsOfExperienceArpl
 	 * @author TechFinium 
 	 * @see    DetailsOfExperienceArpl
 	 * @return a list of {@link haj.com.entity.DetailsOfExperienceArpl}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DetailsOfExperienceArpl> allDetailsOfExperienceArpl() throws Exception {
		return (List<DetailsOfExperienceArpl>)super.getList("select o from DetailsOfExperienceArpl o");
	}

	/**
	 * Get all DetailsOfExperienceArpl between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DetailsOfExperienceArpl
 	 * @return a list of {@link haj.com.entity.DetailsOfExperienceArpl}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DetailsOfExperienceArpl> allDetailsOfExperienceArpl(Long from, int noRows) throws Exception {
	 	String hql = "select o from DetailsOfExperienceArpl o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<DetailsOfExperienceArpl>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DetailsOfExperienceArpl
 	 * @return a {@link haj.com.entity.DetailsOfExperienceArpl}
 	 * @throws Exception global exception
 	 */
	public DetailsOfExperienceArpl findByKey(Long id) throws Exception {
	 	String hql = "select o from DetailsOfExperienceArpl o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DetailsOfExperienceArpl)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DetailsOfExperienceArpl by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DetailsOfExperienceArpl
  	 * @return a list of {@link haj.com.entity.DetailsOfExperienceArpl}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DetailsOfExperienceArpl> findByName(String description) throws Exception {
	 	String hql = "select o from DetailsOfExperienceArpl o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DetailsOfExperienceArpl>)super.getList(hql, parameters);
	}
	
	public int countByTradeTest(Long companyLearnersTradeTestId) throws Exception {
	 	String hql = "select count(o) from DetailsOfExperienceArpl o where o.companyLearnersTradeTest.id = :tradeTestId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("tradeTestId", companyLearnersTradeTestId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public List<DetailsOfExperienceArpl> findByCompanyLearnersTradeTest(Long  treadeTestID) throws Exception {
	 	String hql = "select o from DetailsOfExperienceArpl o where o.companyLearnersTradeTest.id = :treadeTestID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("treadeTestID", treadeTestID);
		return (List<DetailsOfExperienceArpl>)super.getList(hql, parameters);
	}
}

