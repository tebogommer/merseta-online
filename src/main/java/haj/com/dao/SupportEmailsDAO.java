package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.SupportEmails;
import haj.com.entity.enums.SupportTypeEnum;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class SupportEmailsDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all SupportEmails
 	 * @author TechFinium 
 	 * @see    SupportEmails
 	 * @return a list of {@link haj.com.entity.SupportEmails}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SupportEmails> allSupportEmails() throws Exception {
		return (List<SupportEmails>)super.getList("select o from SupportEmails o");
	}

	/**
	 * Get all SupportEmails between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    SupportEmails
 	 * @return a list of {@link haj.com.entity.SupportEmails}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SupportEmails> allSupportEmails(Long from, int noRows) throws Exception {
	 	String hql = "select o from SupportEmails o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<SupportEmails>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    SupportEmails
 	 * @return a {@link haj.com.entity.SupportEmails}
 	 * @throws Exception global exception
 	 */
	public SupportEmails findByKey(Long id) throws Exception {
	 	String hql = "select o from SupportEmails o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (SupportEmails)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find SupportEmails by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    SupportEmails
  	 * @return a list of {@link haj.com.entity.SupportEmails}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<SupportEmails> findByName(String description) throws Exception {
	 	String hql = "select o from SupportEmails o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<SupportEmails>)super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<SupportEmails> findByRole(SupportTypeEnum supportType) throws Exception {
	 	String hql = "select o from SupportEmails o where o.supportType = :supportType " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("supportType", supportType);
		return (List<SupportEmails>)super.getList(hql, parameters);
	}
}

