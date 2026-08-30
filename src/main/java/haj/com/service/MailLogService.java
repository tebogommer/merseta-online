package haj.com.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.primefaces.model.SortOrder;

import haj.com.dao.MailLogDAO;
import haj.com.entity.Company;
import haj.com.entity.Doc;
import haj.com.entity.DocByte;
import haj.com.entity.MailLog;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;

// TODO: Auto-generated Javadoc
/**
 * The Class MailLogService.
 */
public class MailLogService extends AbstractService {

	/** The Constant dao. */
	private static final MailLogDAO dao = new MailLogDAO();
	
	/** The Constant logger. */
	protected static final Log logger = LogFactory.getLog(MailLogService.class);
	
	/** The users service. */
	private UsersService usersService = new UsersService();

	/**
	 * All mail log.
	 *
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<MailLog> allMailLog() throws Exception {
		return dao.allMailLog();
	}

	/**
	 * Creates the.
	 *
	 * @param entity the entity
	 */
	public static void create(MailLog entity) {
		try {
			entity.setCreateDate(new Date());
			entity.setFailed(Boolean.FALSE);
			if (entity.getUser() != null) {
				entity.setEmail(entity.getUser().getEmail());
			}
			dao.create(entity);
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	/**
	 * Update.
	 *
	 * @param entity the entity
	 */
	public static void update(MailLog entity) {
		try {
			if (entity != null && entity.getId() != null) {
				entity.setSendDate(new Date());
				dao.update(entity);
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	/**
	 * Update.
	 *
	 * @param entity the entity
	 * @param error the error
	 */
	public static void update(MailLog entity, Exception error) {
		try {
			if (entity != null && entity.getId() != null) {
				entity.setErrorMsg(error.getMessage());
				entity.setFailed(Boolean.TRUE);
				dao.update(entity);
			}
		} catch (Exception e) {
			logger.fatal(e);
		}
	}

	
	public static void update(MailLog mailLog, String attachmentName, String mime, byte[] attachment) throws Exception {
		update(mailLog);
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		Doc doc = new Doc();
		doc.setMailLog(mailLog);
		doc.setOriginalFname(attachmentName);
		if (mime.indexOf('/') > -1) {
			mime = mime.substring(mime.indexOf("/")+1);
		}
		doc.setExtension(mime);
		entityList.add(doc);
		entityList.add(new DocByte(attachment, doc));
		dao.createBatch(entityList);
	}
	
	public static void update(MailLog mailLog, String attachmentName, String mime, byte[] attachment,String targetClass,Long targetKey) throws Exception {
		update(mailLog);
		List<IDataEntity> entityList = new ArrayList<IDataEntity>();
		Doc doc = new Doc();
		doc.setTargetClass(targetClass);
		doc.setTargetKey(targetKey);
		doc.setMailLog(mailLog);
		doc.setOriginalFname(attachmentName);
		if (mime.indexOf('/') > -1) {
			mime = mime.substring(mime.indexOf("/")+1);
		}
		doc.setExtension(mime);
		entityList.add(doc);
		entityList.add(new DocByte(attachment, doc));
		dao.createBatch(entityList);
	}
	
	/**
	 * Delete.
	 *
	 * @param entity the entity
	 * @throws Exception the exception
	 */
	public void delete(MailLog entity) throws Exception {
		dao.delete(entity);
	}

	/**
	 * Find by key.
	 *
	 * @param id the id
	 * @return the mail log
	 * @throws Exception the exception
	 */
	public MailLog findByKey(long id) throws Exception {
		return dao.findByKey(id);
	}

	/**
	 * Find by name.
	 *
	 * @param desc the desc
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<MailLog> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Find user.
	 *
	 * @param user the user
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<MailLog> findUser(Users user) throws Exception {
		return dao.findUser(user.getId());
	}

	/*
	 * public List<MailLog> findByCompany(haj.com.entity.Company company) throws
	 * Exception { return dao.findByCompany(company.getId()); }
	 */

	/**
	 * All mail log.
	 *
	 * @param startingAt the starting at
	 * @param noRows the no rows
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<MailLog> allMailLog(Integer startingAt, int noRows) throws Exception {
		return dao.allMailLog(startingAt, noRows);
	}

	/**
	 * Count mail log.
	 *
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countMailLog() throws Exception {
		return dao.count(MailLog.class);
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
	public List<MailLog> findByEmail(String email, Integer startingAt, int noRows) throws Exception {
		return dao.findByEmail('%' + email.trim() + '%', startingAt, noRows);
	}

	/**
	 * Count by email.
	 *
	 * @param email the email
	 * @return the long
	 * @throws Exception the exception
	 */
	public Long countByEmail(String email) throws Exception {
		return dao.countByEmail('%' + email.trim() + '%');
	}
	
	/**
	 * All mail log between dates.
	 *
	 * @param startDate the start date
	 * @param endDate the end date
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<MailLog> allMailLogBetweenDates(Date startDate, Date endDate) throws Exception {
		return dao.allMailLogBetweenDates(startDate, endDate);
	}

	/**
	 * Populate users mail log.
	 *
	 * @throws Exception the exception
	 */
	public void populateUsersMailLog() throws Exception {
		// gathers all emails in database
		List<MailLog> mailList = dao.allMailLog();
		// loops through all emails
		for (MailLog mailLog : mailList) {
			if (mailLog.getUser() == null) {
				// finds user via email, email is unique in database
				mailLog.setUser(usersService.getUserByEmail(mailLog.getEmail()));
				update(mailLog);
			}
		}

	}

	public List<MailLog> allMailForCompany(Company company) throws Exception { 
		return stripSensitiveInfo(resolveAttachement(dao.allMailForCompany(company.getId())));
	}
	
	public Integer allMailForCompanyCount(Long companyId) throws Exception {
		return dao.allMailForCompanyCount(companyId);
	}
	
	@SuppressWarnings("unchecked")
	public List<MailLog> allMailLogForCompany(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters, Company company) throws Exception {
		String hql = "select o from MailLog o left join fetch o.user inner join CompanyUsers c on o.user.id = c.user.id and c.company.id = :companyId ";
		filters.put("companyId", company.getId());
		return stripSensitiveInfo(resolveAttachement((List<MailLog>) dao.sortAndFilterWhere(first, pageSize, sortField, sortOrder, filters, hql)));
	}

	public int countAllMailLogForCompany(Map<String, Object> filters) throws Exception {
		String hql = "select count(o) from MailLog o inner join CompanyUsers c on o.user.id = c.user.id and c.company.id = :companyId ";
		return dao.countWhere(filters, hql);
	}

	private List<MailLog> resolveAttachement(List<MailLog> list) throws Exception {
		for (MailLog mailLog : list) {
			List<Doc> docList = dao.findDocList(mailLog.getId());
			if (docList.size() != 0) {
				mailLog.setDoc(docList.get(0));
				mailLog.setDocList(docList);
			}
		}
		return list;
	}

	private List<MailLog> stripSensitiveInfo(List<MailLog> list) throws Exception{
		for (MailLog ml : list) {
			if (ml.getBody().contains("<a")) {
				String t = ml.getBody();
				t = t.replaceAll("<a", "<span");
				t = t.replaceAll("</a>", "</span>");
				ml.setBody(t);
			}
			if (ml.getBody().contains("your password is:") ) { 
						String s = ml.getBody().substring(0,ml.getBody().indexOf("password is"));
						String s1 = ml.getBody().substring(ml.getBody().indexOf("password is"));
						s1 = s1.substring(s1.indexOf("</b>")+4);
						ml.setBody(s + "password is: <b>******</b>"+s1);
						
			}
			if (ml.getBody().contains("your new password:") ) { 
				String s = ml.getBody().substring(0,ml.getBody().indexOf("new password"));
				String s1 = ml.getBody().substring(ml.getBody().indexOf("new password"));
				s1 = s1.substring(s1.indexOf("</b>")+4);
				ml.setBody(s + "password is: <b>******</b>"+s1);
			}
			if (ml.getBody().contains("OTP: ") ) { 
				String s = ml.getBody().substring(0,ml.getBody().indexOf("OTP: "));
				String s1 = ml.getBody().substring(ml.getBody().indexOf("OTP: "));
				s1 = s1.substring(s1.indexOf("</b>")+4);
				ml.setBody(s + "OTP: <b>******</b>"+s1);
			}
			
		}
		return list;
	}
	
	@SuppressWarnings("unchecked")
	public List<MailLog> allMailLog(Class<MailLog> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return populateAdditionalInformationList((List<MailLog>) dao.sortAndFilter(class1, first, pageSize, sortField, sortOrder, filters));
	}

	public int countAllMailLog(Class<MailLog> entity, Map<String, Object> filters) throws Exception {
		return dao.count(entity, filters);
	}
	
	public List<MailLog> populateAdditionalInformationList(List<MailLog> mailLogList) throws Exception{
		for (MailLog mailLog : mailLogList) {
			populateAdditionalInformation(mailLog);
		}
		return mailLogList;
	}

	public MailLog populateAdditionalInformation(MailLog mailLog)throws Exception {
		
		// strip sensitive info
		if (mailLog.getBody().contains("<a")) {
			String t = mailLog.getBody();
			t = t.replaceAll("<a", "<span");
			t = t.replaceAll("</a>", "</span>");
			mailLog.setBody(t);
		}
		if (mailLog.getBody().contains("your password is:") ) { 
			String s = mailLog.getBody().substring(0,mailLog.getBody().indexOf("password is"));
			String s1 = mailLog.getBody().substring(mailLog.getBody().indexOf("password is"));
			s1 = s1.substring(s1.indexOf("</b>")+4);
			mailLog.setBody(s + "password is: <b>******</b>"+s1);
		}
		if (mailLog.getBody().contains("your new password:") ) { 
			String s = mailLog.getBody().substring(0,mailLog.getBody().indexOf("new password"));
			String s1 = mailLog.getBody().substring(mailLog.getBody().indexOf("new password"));
			s1 = s1.substring(s1.indexOf("</b>")+4);
			mailLog.setBody(s + "password is: <b>******</b>"+s1);
		}
		
		// resolve docs
		List<Doc> docList = dao.findDocList(mailLog.getId());
		if (docList.size() != 0) {
			mailLog.setDoc(docList.get(0));
			mailLog.setDocList(docList);
		}
		return mailLog;
	}

}
