package haj.com.dao;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.Doc;
import haj.com.entity.MailLog;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

// TODO: Auto-generated Javadoc
/**
 * The Class MailLogDAO.
 */
public class MailLogDAO extends AbstractDAO  {

	/* (non-Javadoc)
	 * @see haj.com.framework.AbstractDAO#getDataProvider()
	 */
	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * All mail log.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MailLog> allMailLog() throws Exception {
		return (List<MailLog>)super.getList("select o from MailLog o");
	}

	
	/**
	 * All mail log.
	 *
	 * @param startingAt the starting at
	 * @param noRows the no rows
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MailLog> allMailLog(Integer startingAt, int noRows) throws Exception {
		return (List<MailLog>)super.getList("select o from MailLog o order by o.createDate desc ",null,startingAt,noRows);
	}
	
	/**
	 * Find by email.
	 *
	 * @param email the email
	 * @param startingAt the starting at
	 * @param noRows the no rows
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MailLog> findByEmail(String email,Integer startingAt, int noRows) throws Exception {
	 	String hql = "select o from MailLog o where o.email like  :email order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("email", email);
		return (List<MailLog>)super.getList(hql, parameters,startingAt,noRows);
	}
	
	
	/**
	 * Count by email.
	 *
	 * @param email the email
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByEmail(String email) throws Exception {
	 	String hql = "select count(o) from MailLog o where o.email like  :email  " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("email", email);
		return (Long)super.getUniqueResult(hql,parameters);
	}
	
/*
	@SuppressWarnings("unchecked")
	public List<MailLog> findByCompany(Integer companyId) throws Exception {
	 	String hql = "select o from MailLog o where o.company.id = :companyId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("companyId", companyId);
		return (List<MailLog>)super.getList(hql, parameters);
	}


	@SuppressWarnings("unchecked")
	public List<MailLog> byField(long key) throws Exception  {
		String hql = "select o from MailLog o where o.key = :key";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("key", key);
	    return (List<MailLog>)super.getList(hql, parameters);
	}
*/

	/**
 * Find by key.
 *
 * @param id the id
 * @return the mail log
 * @throws Exception the exception
 */
public MailLog findByKey(Long id) throws Exception {
	 	String hql = "select o from MailLog o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (MailLog)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find by name.
	 *
	 * @param description the description
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MailLog> findByName(String description) throws Exception {
	 	String hql = "select o from MailLog o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<MailLog>)super.getList(hql, parameters);
	}
	
	/**
	 * Find user.
	 *
	 * @param userId the user id
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MailLog> findUser(Long userId) throws Exception {
	 	String hql = "select o from MailLog o where o.user.uid = :userId order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
		return (List<MailLog>)super.getList(hql, parameters);
	}
	
	/**
	 * All mail log between dates.
	 *
	 * @param date the date
	 * @param endDate the end date
	 * @return the list
	 * @throws Exception the exception
	 */
	@SuppressWarnings("unchecked")
	public List<MailLog> allMailLogBetweenDates(Date date, Date endDate) throws Exception {
		String hql = "select o from MailLog o where date(o.sendDate) between date(:date) and date(:endDate) order by o.user.id";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("date", date);
		parameters.put("endDate", endDate);
		return (List<MailLog>) super.getList(hql, parameters);
	}
	
	
	@SuppressWarnings("unchecked")
	public List<MailLog> allMailForCompany(Long companyId) throws Exception {
		String hql = "select o from MailLog o " + 
				"left join fetch o.user " + 
				"inner join CompanyUsers c " + 
				"on o.user.id = c.user.id " + 
				"and c.company.id = :companyId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return (List<MailLog>) super.getList(hql, parameters);
	}
	
	public Integer allMailForCompanyCount(Long companyId) throws Exception {
		String hql = "select count(o) from MailLog o " + 
				"inner join CompanyUsers c " + 
				"on o.user.id = c.user.id " + 
				"and c.company.id = :companyId ";
		Map<String, Object> parameters = new Hashtable<String, Object>();
		parameters.put("companyId", companyId);
		return ((Long) super.getUniqueResult(hql, parameters)).intValue();
	}

	
	public Doc findDocs(Long id) throws Exception {
	 	String hql = "select o from Doc o where o.mailLog.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (Doc)super.getUniqueResult(hql, parameters);
	}
	
	@SuppressWarnings("unchecked")
	public List<Doc> findDocList(Long id) throws Exception {
	 	String hql = "select o from Doc o where o.mailLog.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (List<Doc>)super.getList(hql, parameters);
	}
	
	//select o from Doc o where o.mailLog.id = 
}

