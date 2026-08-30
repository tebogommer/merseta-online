package haj.com.dao.lookup;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.enums.MailingListTypeEnum;
import haj.com.entity.lookup.MailingList;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class MailingListDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all MailingList
 	 * @author TechFinium 
 	 * @see    MailingList
 	 * @return a list of {@link haj.com.entity.MailingList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MailingList> allMailingList() throws Exception {
		return (List<MailingList>)super.getList("select o from MailingList o");
	}

	/**
	 * Get all MailingList between from and noRows
 	 * @author TechFinium 
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    MailingList
 	 * @return a list of {@link haj.com.entity.MailingList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MailingList> allMailingList(Long from, int noRows) throws Exception {
	 	String hql = "select o from MailingList o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    
		return (List<MailingList>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium 
 	 * @param id the id
 	 * @see    MailingList
 	 * @return a {@link haj.com.entity.MailingList}
 	 * @throws Exception global exception
 	 */
	public MailingList findByKey(Long id) throws Exception {
	 	String hql = "select o from MailingList o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (MailingList)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find MailingList by description
 	 * @author TechFinium 
 	 * @param description the description 
 	 * @see    MailingList
  	 * @return a list of {@link haj.com.entity.MailingList}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<MailingList> findByName(String description) throws Exception {
	 	String hql = "select o from MailingList o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<MailingList>)super.getList(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<MailingList> findByType(MailingListTypeEnum mailingListTypeEnum) throws Exception {
	 	String hql = "select o from MailingList o where o.mailingListTypeEnum =  :mailingListTypeEnum " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("mailingListTypeEnum", mailingListTypeEnum);
		return (List<MailingList>)super.getList(hql, parameters);
	}
}

