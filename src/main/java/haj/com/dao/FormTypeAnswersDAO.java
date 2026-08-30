package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.formconfig.FormSectionQuestions;
import haj.com.entity.formconfig.FormTypeAnswers;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class FormTypeAnswersDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all FormTypeAnswers
	 * 
	 * @author TechFinium
	 * @see FormTypeAnswers
	 * @return a list of {@link haj.com.entity.FormTypeAnswers}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<FormTypeAnswers> allFormTypeAnswers() throws Exception {
		return (List<FormTypeAnswers>) super.getList("select o from FormTypeAnswers o");
	}

	/**
	 * Get all FormTypeAnswers between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see FormTypeAnswers
	 * @return a list of {@link haj.com.entity.FormTypeAnswers}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<FormTypeAnswers> allFormTypeAnswers(Long from, int noRows) throws Exception {
		String hql = "select o from FormTypeAnswers o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<FormTypeAnswers>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see FormTypeAnswers
	 * @return a {@link haj.com.entity.FormTypeAnswers}
	 * @throws Exception
	 *             global exception
	 */
	public FormTypeAnswers findByKey(Long id) throws Exception {
		String hql = "select o from FormTypeAnswers o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (FormTypeAnswers) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find FormTypeAnswers by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see FormTypeAnswers
	 * @return a list of {@link haj.com.entity.FormTypeAnswers}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<FormTypeAnswers> findByName(String description) throws Exception {
		String hql = "select o from FormTypeAnswers o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<FormTypeAnswers>) super.getList(hql, parameters);
	}
	@SuppressWarnings("unchecked")
	public List<FormTypeAnswers> findByQuestion(FormSectionQuestions formSectionQuestions) throws Exception {
		String hql = "select o from FormTypeAnswers o where o.formSectionQuestions.id = :formSectionQuestionsID";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("formSectionQuestionsID", formSectionQuestions.getId());
		return (List<FormTypeAnswers>) super.getList(hql, parameters);
	}
}
