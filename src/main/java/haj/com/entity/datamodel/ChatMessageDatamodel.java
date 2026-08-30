package haj.com.entity.datamodel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.ChatMessage;
import haj.com.service.ChatMessageService;

public class ChatMessageDatamodel extends LazyDataModel<ChatMessage> {

	private static final long serialVersionUID = 1L;
	private List<ChatMessage> retorno = new ArrayList<ChatMessage>();
	private ChatMessageService service = new ChatMessageService();

	public ChatMessageDatamodel() {
		super();
	}

	@Override
	public List<ChatMessage> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

		try {
			retorno = service.allChatMessage(ChatMessage.class, first, pageSize, sortField, sortOrder, filters);
			setRowCount(service.count(ChatMessage.class, filters));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retorno;
	}

	@Override
	public Object getRowKey(ChatMessage obj) {
		return obj.getId();
	}

	@Override
	public ChatMessage getRowData(String rowKey) {
		for (ChatMessage obj : retorno) {
			if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
		}
		return null;
	}

}
