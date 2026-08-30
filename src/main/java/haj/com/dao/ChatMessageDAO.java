package haj.com.dao;

import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import haj.com.entity.ChatMessage;
import haj.com.entity.HostingCompanyDepartments;
import haj.com.entity.Users;
import haj.com.framework.AbstractDAO;
import haj.com.framework.AbstractDataProvider;
import haj.com.provider.MySQLProvider;

public class ChatMessageDAO extends AbstractDAO  {

	@Override
	public AbstractDataProvider getDataProvider() {
		return new MySQLProvider();
	}

	/**
	 * Get all ChatMessage
 	 * @author TechFinium
 	 * @see    ChatMessage
 	 * @return a list of {@link haj.com.entity.ChatMessage}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ChatMessage> allChatMessage() throws Exception {
		return (List<ChatMessage>)super.getList("select o from ChatMessage o");
	}

	/**
	 * Get all ChatMessage between from and noRows
 	 * @author TechFinium
 	 * @param from the from
 	 * @param noRows the no rows
 	 * @see    ChatMessage
 	 * @return a list of {@link haj.com.entity.ChatMessage}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ChatMessage> allChatMessage(Long from, int noRows) throws Exception {
	 	String hql = "select o from ChatMessage o " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();

		return (List<ChatMessage>)super.getList(hql, parameters,from.intValue(),noRows);
	}

	/**
	 * Find object by primary key
 	 * @author TechFinium
 	 * @param id the id
 	 * @see    ChatMessage
 	 * @return a {@link haj.com.entity.ChatMessage}
 	 * @throws Exception global exception
 	 */
	public ChatMessage findByKey(Long id) throws Exception {
	 	String hql = "select o from ChatMessage o where o.id = :id " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("id", id);
		return (ChatMessage)super.getUniqueResult(hql, parameters);
	}

	/**
	 * Find ChatMessage by description
 	 * @author TechFinium
 	 * @param description the description
 	 * @see    ChatMessage
  	 * @return a list of {@link haj.com.entity.ChatMessage}
 	 * @throws Exception global exception
 	 */
	@SuppressWarnings("unchecked")
	public List<ChatMessage> findByName(String description) throws Exception {
	 	String hql = "select o from ChatMessage o where o.description like  :description order by o.desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("description", ""+description.trim()+"%");
		return (List<ChatMessage>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartments> findDepartmentsConfiguredForChat() throws Exception {
		String hql = "select distinct o.hostingCompanyDepartments from HostingCompanyDepartmentsChatEmployees o";
		return (List<HostingCompanyDepartments>)super.getList(hql);
	}

	@SuppressWarnings("unchecked")
	public List<HostingCompanyDepartments> findDepartmentsConfiguredForChatForEmployee(Long userId) throws Exception {
		String hql = "select distinct o.hostingCompanyDepartments from HostingCompanyDepartmentsChatEmployees o "
				+ "where o.hostingCompanyEmployees.users.id = :userId ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userId", userId);
		return (List<HostingCompanyDepartments>)super.getList(hql, parameters);
	}


	@SuppressWarnings("unchecked")
	public List<ChatMessage> findRootChatsForUserAndDepartment(Long userID, Long deparmentId) {
	 	String hql = "select o from ChatMessage o where o.hostingCompanyDepartments.id = :deparmentId "
	 			+ "and o.user.id = :userID  and o.parentChat.id is null" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("userID", userID);
	    parameters.put("deparmentId", deparmentId);
		return (List<ChatMessage>)super.getList(hql, parameters);
	}


	@SuppressWarnings("unchecked")
	public List<ChatMessage> findRootChatsForDepartment(Long deparmentId) {
	 	String hql = "select o from ChatMessage o where o.hostingCompanyDepartments.id = :deparmentId "
	 			+ "and o.parentChat.id is null order by o.createDate desc " ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("deparmentId", deparmentId);
		return (List<ChatMessage>)super.getList(hql, parameters);
	}

	@SuppressWarnings("unchecked")
	public List<ChatMessage> findChatByParentId(Long parentId) {
	 	String hql = "select o from ChatMessage o where o.parentChat.id is :parentId" ;
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("parentId", parentId);
		return (List<ChatMessage>)super.getList(hql, parameters);
	}


	@SuppressWarnings("unchecked")
	public List<Users> findChatEmployeesForDepartment(Long departmentId) throws Exception {
		String hql = "select distinct o.hostingCompanyEmployees.users from HostingCompanyDepartmentsChatEmployees o "
				+ "where o.hostingCompanyDepartments.id = :departmentId ";
	    Map<String, Object> parameters = new Hashtable<String, Object>();
	    parameters.put("departmentId", departmentId);
		return (List<Users>)super.getList(hql, parameters);
	}

}

