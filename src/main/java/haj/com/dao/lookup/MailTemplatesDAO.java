package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.enums.MailEnum;
import haj.com.entity.lookup.MailTemplates;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class MailTemplatesDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all MailTemplates
 	 * @author TechFinium 
 	 * @see    MailTemplates
 	 * @return a list of {@link haj.com.entity.MailTemplates}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MailTemplates> allMailTemplates() throws Exception {
		return (List<MailTemplates>)super.getList("select o from MailTemplates o");
	}

	/**
	 * Get all MailTemplates between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    MailTemplates
 	 * @return a list of {@link haj.com.entity.MailTemplates}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MailTemplates> allMailTemplates(Long from, int noRows) throws Exception {
	 	String hql = "select o from MailTemplates o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<MailTemplates>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    MailTemplates
 	 * @return a {@link haj.com.entity.MailTemplates}
 	 * @throws Exception global exception
 	 */
	public MailTemplates findByKey(Long id) throws Exception {
	 	String hql = "select o from MailTemplates o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (MailTemplates)super.getUniqueResult(hql, parameters);
	}

	public MailTemplates findCode(MailEnum mailCode) throws Exception {
	 	String hql = "select o from MailTemplates o where o.mailCode = :mailCode " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("mailCode", mailCode);
		return (MailTemplates)super.getUniqueResult(hql, parameters);
	}
	/**
	 * Find MailTemplates by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    MailTemplates
  	 * @return a list of {@link haj.com.entity.MailTemplates}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MailTemplates> findByName(String description) throws Exception {
	 	String hql = "select o from MailTemplates o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<MailTemplates>)super.getList(hql, parameters);
	}
}

