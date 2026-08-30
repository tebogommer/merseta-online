package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.DetailsOfTrainingArpl;

public class DetailsOfTrainingArplDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all DetailsOfTrainingArpl
 	 * @author TechFinium 
 	 * @see    DetailsOfTrainingArpl
 	 * @return a list of {@link haj.com.entity.DetailsOfTrainingArpl}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DetailsOfTrainingArpl> allDetailsOfTrainingArpl() throws Exception {
		return (List<DetailsOfTrainingArpl>)super.getList("select o from DetailsOfTrainingArpl o");
	}

	/**
	 * Get all DetailsOfTrainingArpl between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    DetailsOfTrainingArpl
 	 * @return a list of {@link haj.com.entity.DetailsOfTrainingArpl}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DetailsOfTrainingArpl> allDetailsOfTrainingArpl(Long from, int noRows) throws Exception {
	 	String hql = "select o from DetailsOfTrainingArpl o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<DetailsOfTrainingArpl>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    DetailsOfTrainingArpl
 	 * @return a {@link haj.com.entity.DetailsOfTrainingArpl}
 	 * @throws Exception global exception
 	 */
	public DetailsOfTrainingArpl findByKey(Long id) throws Exception {
	 	String hql = "select o from DetailsOfTrainingArpl o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (DetailsOfTrainingArpl)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find DetailsOfTrainingArpl by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    DetailsOfTrainingArpl
  	 * @return a list of {@link haj.com.entity.DetailsOfTrainingArpl}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<DetailsOfTrainingArpl> findByName(String description) throws Exception {
	 	String hql = "select o from DetailsOfTrainingArpl o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<DetailsOfTrainingArpl>)super.getList(hql, parameters);
	}
	
	public int countByTradeTest(Long companyLearnersTradeTestId) throws Exception {
	 	String hql = "select count(o) from DetailsOfTrainingArpl o where o.companyLearnersTradeTest.id = :tradeTestId " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("tradeTestId", companyLearnersTradeTestId);
		return ((Long)super.getUniqueResult(hql, parameters)).intValue();
	}
	
	public List<DetailsOfTrainingArpl> findByCompanyLearnersTradeTest(Long  tradeTestID) throws Exception {
	 	String hql = "select o from DetailsOfTrainingArpl o where o.companyLearnersTradeTest.id = :tradeTestID " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("tradeTestID", tradeTestID);
		return (List<DetailsOfTrainingArpl>)super.getList(hql, parameters);
	}
}

