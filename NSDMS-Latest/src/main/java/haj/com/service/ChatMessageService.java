package haj.com.service;

import java.util.Date;
import java.util.List;

import haj.com.dao.ChatMessageDAO;
import haj.com.entity.ChatMessage;
import haj.com.entity.HostingCompanyDepartments;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import java.util.Map;
import org.primefaces.model.SortOrder;

public class ChatMessageService extends AbstractService {
	/** The dao. */
	private ChatMessageDAO dao = new ChatMessageDAO();

	/**
	 * Get all ChatMessage
 	 * @author TechFinium
 	 * @see   ChatMessage
 	 * @return a list of {@link haj.com.entity.ChatMessage}
	 * @throws Exception the exception
 	 */
	public List<ChatMessage> allChatMessage() throws Exception {
	  	return dao.allChatMessage();
	}


	/**
	 * Create or update ChatMessage.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     ChatMessage
	 */
	public void create(ChatMessage entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  ChatMessage.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ChatMessage
	 */
	public void update(ChatMessage entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  ChatMessage.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    ChatMessage
	 */
	public void delete(ChatMessage entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.ChatMessage}
	 * @throws Exception the exception
	 * @see    ChatMessage
	 */
	public ChatMessage findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find ChatMessage by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.ChatMessage}
	 * @throws Exception the exception
	 * @see    ChatMessage
	 */
	public List<ChatMessage> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}

	/**
	 * Lazy load ChatMessage
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.ChatMessage}
	 * @throws Exception the exception
	 */
	public List<ChatMessage> allChatMessage(int first, int pageSize) throws Exception {
		return dao.allChatMessage(Long.valueOf(first), pageSize);
	}


	/**
	 * Get count of ChatMessage for lazy load
	 * @author TechFinium
	 * @return Number of rows in the ChatMessage
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(ChatMessage.class);
	}

    /**
     * Lazy load ChatMessage with pagination, filter, sorting
     * @author TechFinium
     * @param class1 ChatMessage class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.ChatMessage} containing data
     * @throws Exception the exception
     */
	@SuppressWarnings("unchecked")
	public List<ChatMessage> allChatMessage(Class<ChatMessage> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<ChatMessage>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);

	}

    /**
     * Get count of ChatMessage for lazy load with filters
     * @author TechFinium
     * @param entity ChatMessage class
     * @param filters the filters
     * @return Number of rows in the ChatMessage entity
     * @throws Exception the exception
     */
	public int count(Class<ChatMessage> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}

	public List<HostingCompanyDepartments> findDepartmentsConfiguredForChat() throws Exception {
		return dao.findDepartmentsConfiguredForChat();
	}

	public List<HostingCompanyDepartments> findDepartmentsConfiguredForChatForEmployee(Users user) throws Exception {
		return dao.findDepartmentsConfiguredForChatForEmployee(user.getId());
	}

	public List<ChatMessage> findRootChatsForUserAndDepartment(Users user, HostingCompanyDepartments hostingCompanyDepartments) throws Exception{
		if (user==null || hostingCompanyDepartments==null) throw new Exception("User and Department needs to be selected");
		return resolveRestOfChat(dao.findRootChatsForUserAndDepartment(user.getId(),hostingCompanyDepartments.getId()));
	}

	private List<ChatMessage> resolveRestOfChat(List<ChatMessage> list) {
		if (!list.isEmpty()) {
			list.addAll(findChatByParentId(list.get(0).getId()));
		}
		return list;
	}


	public List<ChatMessage> findChatByParentId(Long parentId) {
		return dao.findChatByParentId(parentId);
	}


	public void save(ChatMessage chatmessage, List<ChatMessage> chatmessageList) throws Exception {
		if (chatmessageList !=null && !chatmessageList.isEmpty()) {
			chatmessage.setParentChat(chatmessageList.get(0));
		}
		dao.create(chatmessage);
	}

	public List<ChatMessage> findRootChatsForDepartment( HostingCompanyDepartments hostingCompanyDepartments)  throws Exception {
		return dao.findRootChatsForDepartment(hostingCompanyDepartments.getId());
	}

	public List<Users> findChatEmployeesForDepartment(Long departmentId) throws Exception {
		return dao.findChatEmployeesForDepartment(departmentId);
	}


	public void attendeToUpdate(Users activeUser, List<ChatMessage> selectedChatmessageList) {
		try {
			if (!selectedChatmessageList.isEmpty()) {
				selectedChatmessageList.get(0).setAttendToByStaff(activeUser);
				selectedChatmessageList.get(0).setAttendToDateByStaff(new Date());
				dao.update(selectedChatmessageList.get(0));
			}
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
}
