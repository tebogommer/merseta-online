package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.FrequentlyAskedQuestions;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class FrequentlyAskedQuestionsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all FrequentlyAskedQuestions
 	 * @author TechFinium 
 	 * @see    FrequentlyAskedQuestions
 	 * @return a list of {@link haj.com.entity.FrequentlyAskedQuestions}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FrequentlyAskedQuestions> allFrequentlyAskedQuestions() throws Exception {
		return (List<FrequentlyAskedQuestions>)super.getList("select o from FrequentlyAskedQuestions o");
	}

	/**
	 * Get all FrequentlyAskedQuestions between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    FrequentlyAskedQuestions
 	 * @return a list of {@link haj.com.entity.FrequentlyAskedQuestions}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FrequentlyAskedQuestions> allFrequentlyAskedQuestions(Long from, int noRows) throws Exception {
	 	String hql = "select o from FrequentlyAskedQuestions o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<FrequentlyAskedQuestions>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    FrequentlyAskedQuestions
 	 * @return a {@link haj.com.entity.FrequentlyAskedQuestions}
 	 * @throws Exception global exception
 	 */
	public FrequentlyAskedQuestions findByKey(Long id) throws Exception {
	 	String hql = "select o from FrequentlyAskedQuestions o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (FrequentlyAskedQuestions)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find FrequentlyAskedQuestions by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    FrequentlyAskedQuestions
  	 * @return a list of {@link haj.com.entity.FrequentlyAskedQuestions}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FrequentlyAskedQuestions> findByName(String description) throws Exception {
	 	String hql = "select o from FrequentlyAskedQuestions o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<FrequentlyAskedQuestions>)super.getList(hql, parameters);
	}
	
	/**
	 * Find active complete FAQs.
	 *
	 * @return the FrequentlyAskedQuestions
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<FrequentlyAskedQuestions> findActiveFAQ() throws Exception {
		String hql = "select o from FrequentlyAskedQuestions o where o.questionActive = true order by o.questionSection";
		return (List<FrequentlyAskedQuestions>) super.getList(hql);
	}
	
	/**
	 * All stakeholder submitted FAQs.
	 *
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<FrequentlyAskedQuestions> allSubmittedFAQ() throws Exception {
		String hql = "select o from FrequentlyAskedQuestions o where o.questionAnswer is null";
		return (List<FrequentlyAskedQuestions>) super.getList(hql);
	}
	
	/**
	 * question and answer FAQs.
	 *
	 * @return the list
	 * @throws Exception
	 *             the exception
	 */
	@SuppressWarnings("unchecked")
	public List<FrequentlyAskedQuestions> allFAQ() throws Exception {
		String hql = "select o from FrequentlyAskedQuestions o where o.questionAnswer is not null and o.questionAsked is not null";
		return (List<FrequentlyAskedQuestions>) super.getList(hql);
	}

}

