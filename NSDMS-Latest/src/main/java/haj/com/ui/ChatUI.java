package  haj.com.ui;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.entity.ChatMessage;
import haj.com.entity.HostingCompanyDepartments;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.ChatMessageService;

@ManagedBean(name = "chatUI")
@ViewScoped
public class ChatUI extends AbstractUI {

	private ChatMessageService service = new ChatMessageService();
	private List<ChatMessage> chatmessageList = null;
	private List<ChatMessage> chatmessagefilteredList = null;
	private ChatMessage chatmessage = null;
	private List<HostingCompanyDepartments> availableDepartments;
	private HostingCompanyDepartments hostingCompanyDepartments;

	private ChatMessage lastChatmessage = null;

    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all ChatMessage and prepare a for a create of a new ChatMessage
 	 * @author TechFinium
 	 * @see    ChatMessage
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {

		prepareNew();
		this.availableDepartments = service.findDepartmentsConfiguredForChat();
		chatmessageInfo();
	}

	/**
	 * Get all ChatMessage for data table
 	 * @author TechFinium
 	 * @see    ChatMessage
 	 */
	public void chatmessageInfo() {

	}




    public void chatmessageUpdate() {
		try {
				 service.update(this.chatmessage);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 chatmessageInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete ChatMessage from database
 	 * @author TechFinium
 	 * @see    ChatMessage
 	 */
	public void chatmessageDelete() {
		try {
			 service.delete(this.chatmessage);
			  prepareNew();
			 chatmessageInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of ChatMessage
 	 * @author TechFinium
 	 * @see    ChatMessage
 	 */
	public void prepareNew() {
		chatmessage = new ChatMessage(hostingCompanyDepartments,getSessionUI().getActiveUser());
		chatmessage.setHostingCompanyDepartments(hostingCompanyDepartments);
	}

/*
    public List<SelectItem> getChatMessageDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	chatmessageInfo();
    	for (ChatMessage ug : chatmessageList) {
    		// l.add(new SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc()));
		}
    	return l;
    }
  */

    /**
     * Complete.
     *
     * @param desc the desc
     * @return the list
     */
    public List<ChatMessage> complete(String desc) {
		List<ChatMessage> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		}
		catch (Exception e) {
		addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

    public List<ChatMessage> getChatMessageList() {
		return chatmessageList;
	}
	public void setChatMessageList(List<ChatMessage> chatmessageList) {
		this.chatmessageList = chatmessageList;
	}
	public ChatMessage getChatmessage() {
		return chatmessage;
	}
	public void setChatmessage(ChatMessage chatmessage) {
		this.chatmessage = chatmessage;
	}

    public List<ChatMessage> getChatMessagefilteredList() {
		return chatmessagefilteredList;
	}

	/**
	 * Prepare auto complete data
 	 * @author TechFinium
 	 * @param chatmessagefilteredList the new chatmessagefilteredList list
 	 * @see    ChatMessage
 	 */
	public void setChatMessagefilteredList(List<ChatMessage> chatmessagefilteredList) {
		this.chatmessagefilteredList = chatmessagefilteredList;
	}

	public List<HostingCompanyDepartments> getAvailableDepartments() {
		return availableDepartments;
	}

	public void setAvailableDepartments(List<HostingCompanyDepartments> availableDepartments) {
		this.availableDepartments = availableDepartments;
	}

	public HostingCompanyDepartments getHostingCompanyDepartments() {
		return hostingCompanyDepartments;
	}

	public void setHostingCompanyDepartments(HostingCompanyDepartments hostingCompanyDepartments) {
		this.hostingCompanyDepartments = hostingCompanyDepartments;
	}

	public void findExistingChats() {
		try {
			if (this.hostingCompanyDepartments !=null) {
			 chatmessageList = service.findRootChatsForUserAndDepartment(getSessionUI().getUser(),this.hostingCompanyDepartments);
			 prepareNew();
			 if (chatmessageList!=null && chatmessageList.size()>0) {
				lastChatmessage = chatmessageList.get(chatmessageList.size()-1);
			  }
			  else {
				  lastChatmessage = chatmessage;
			  }

			}

		} catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	public void saveChat() {
		try {
			service.save(this.chatmessage,chatmessageList);
			findExistingChats();

		} catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}



	public ChatMessage getLastChatmessage() {
		return lastChatmessage;
	}

	public void setLastChatmessage(ChatMessage lastChatmessage) {
		this.lastChatmessage = lastChatmessage;
	}


}
