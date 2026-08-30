package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.formconfig.FormSectionQuestions;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class FormSectionQuestionsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all FormSectionQuestions
 	 * @author TechFinium 
 	 * @see    FormSectionQuestions
 	 * @return a list of {@link haj.com.entity.FormSectionQuestions}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FormSectionQuestions> allFormSectionQuestions() throws Exception {
		return (List<FormSectionQuestions>)super.getList("select o from FormSectionQuestions o");
	}

	/**
	 * Get all FormSectionQuestions between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    FormSectionQuestions
 	 * @return a list of {@link haj.com.entity.FormSectionQuestions}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FormSectionQuestions> allFormSectionQuestions(Long from, int noRows) throws Exception {
	 	String hql = "select o from FormSectionQuestions o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<FormSectionQuestions>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    FormSectionQuestions
 	 * @return a {@link haj.com.entity.FormSectionQuestions}
 	 * @throws Exception global exception
 	 */
	public FormSectionQuestions findByKey(Long id) throws Exception {
	 	String hql = "select o from FormSectionQuestions o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (FormSectionQuestions)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find FormSectionQuestions by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    FormSectionQuestions
  	 * @return a list of {@link haj.com.entity.FormSectionQuestions}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FormSectionQuestions> findByName(String description) throws Exception {
	 	String hql = "select o from FormSectionQuestions o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<FormSectionQuestions>)super.getList(hql, parameters);
	}
}

