package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.formconfig.FormType;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class FormTypeDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all FormType
 	 * @author TechFinium 
 	 * @see    FormType
 	 * @return a list of {@link haj.com.entity.FormType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FormType> allFormType() throws Exception {
		return (List<FormType>)super.getList("select o from FormType o");
	}

	/**
	 * Get all FormType between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    FormType
 	 * @return a list of {@link haj.com.entity.FormType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FormType> allFormType(Long from, int noRows) throws Exception {
	 	String hql = "select o from FormType o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<FormType>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    FormType
 	 * @return a {@link haj.com.entity.FormType}
 	 * @throws Exception global exception
 	 */
	public FormType findByKey(Long id) throws Exception {
	 	String hql = "select o from FormType o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (FormType)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find FormType by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    FormType
  	 * @return a list of {@link haj.com.entity.FormType}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<FormType> findByName(String description) throws Exception {
	 	String hql = "select o from FormType o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<FormType>)super.getList(hql, parameters);
	}
}

