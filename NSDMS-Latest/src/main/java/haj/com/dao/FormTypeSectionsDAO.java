package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.formconfig.FormSectionQuestions;
import haj.com.entity.formconfig.FormType;
import haj.com.entity.formconfig.FormTypeSections;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class FormTypeSectionsDAO extends AbstractDAO {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all FormTypeSections
	 * 
	 * @author TechFinium
	 * @see FormTypeSections
	 * @return a list of {@link haj.com.entity.FormTypeSections}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<FormTypeSections> allFormTypeSections() throws Exception {
		return (List<FormTypeSections>) super.getList("select o from FormTypeSections o");
	}

	/**
	 * Get all FormTypeSections between from and noRows
	 * 
	 * @author TechFinium
	 * @param from
	 *            the from
	 * @param noRows
	 *            the no rows
	 * @see FormTypeSections
	 * @return a list of {@link haj.com.entity.FormTypeSections}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<FormTypeSections> allFormTypeSections(Long from, int noRows) throws Exception {
		String hql = "select o from FormTypeSections o ";
		Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<FormTypeSections>) super.getList(hql, parameters, from.intValue(), noRows);
	}

	/**
	 * Find object by primary key
	 * 
	 * @author TechFinium
	 * @param id
	 *            the id
	 * @see FormTypeSections
	 * @return a {@link haj.com.entity.FormTypeSections}
	 * @throws Exception
	 *             global exception
	 */
	public FormTypeSections findByKey(Long id) throws Exception {
		String hql = "select o from FormTypeSections o where o.id = :id ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("id", id);
		return (FormTypeSections) super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find FormTypeSections by description
	 * 
	 * @author TechFinium
	 * @param description
	 *            the description
	 * @see FormTypeSections
	 * @return a list of {@link haj.com.entity.FormTypeSections}
	 * @throws Exception
	 *             global exception
	 */
	@SuppressWarnings("unchecked")
	public List<FormTypeSections> findByName(String description) throws Exception {
		String hql = "select o from FormTypeSections o where o.description like  :description order by o.desc ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("description", "" + description.trim() + "%");
		return (List<FormTypeSections>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<FormTypeSections> findByFormType(FormType formType) throws Exception {
		String hql = "select o from FormTypeSections o where o.parentTemplate.id = :formTypeID order by o.orderPos";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("formTypeID", formType.getId());
		return (List<FormTypeSections>) super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<FormSectionQuestions> findFormSectionQuestions(FormTypeSections formType) throws Exception {
		String hql = "select o from FormSectionQuestions o where o.formTypeSections.id = :FormTypeSectionsID order by o.orderPos";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("FormTypeSectionsID", formType.getId());
		return (List<FormSectionQuestions>) super.getList(hql, parameters);
	}
}
