package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;
import haj.com.entity.Company;
import haj.com.entity.CompletionLetter;

public class CompletionLetterDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all CompletionLetter
 	 * @author TechFinium 
 	 * @see    CompletionLetter
 	 * @return a list of {@link haj.com.entity.CompletionLetter}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompletionLetter> allCompletionLetter() throws Exception {
		return (List<CompletionLetter>)super.getList("select o from CompletionLetter o");
	}

	/**
	 * Get all CompletionLetter between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    CompletionLetter
 	 * @return a list of {@link haj.com.entity.CompletionLetter}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompletionLetter> allCompletionLetter(Long from, int noRows) throws Exception {
	 	String hql = "select o from CompletionLetter o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<CompletionLetter>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    CompletionLetter
 	 * @return a {@link haj.com.entity.CompletionLetter}
 	 * @throws Exception global exception
 	 */
	public CompletionLetter findByKey(Long id) throws Exception {
	 	String hql = "select o from CompletionLetter o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (CompletionLetter)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find CompletionLetter by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    CompletionLetter
  	 * @return a list of {@link haj.com.entity.CompletionLetter}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<CompletionLetter> findByName(String description) throws Exception {
	 	String hql = "select o from CompletionLetter o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<CompletionLetter>)super.getList(hql, parameters);
	}
	
	public Company findUserByKey(Long id) throws Exception {
	 	String hql = "select c from CompletionLetter o inner join Company c on c.id = o.trainingProvider.id where o.trainingProvider.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Company)super.getUniqueResult(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<CompletionLetter> findByCompanyLearner(Long companyLearnersID) {
		String hql = "select o from CompletionLetter o where o.companyLearners.id = :companyLearnersID" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyLearnersID", companyLearnersID);
		return (List<CompletionLetter>)super.getList(hql, parameters);
	}	
}

