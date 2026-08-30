package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.MailLog;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.MailLogService;

@ManagedBean(name = "mailLogUI")
@ViewScoped
public class MailLogUI extends AbstractUI {
	
	/* Entity Levels */
	private MailLog mailLog = null;
	
	/* Service Levels */
	private MailLogService mailLogService = new MailLogService();
	
	/* Data Models */
	private LazyDataModel<MailLog> mailLogDataModel;

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

	private void runInit() throws Exception {
		validiateSuperAdmin();
		mailLogDataModelInfo();
	}	
	
	private void validiateSuperAdmin() throws Exception{
		if (!getSessionUI().getActiveUser().getSuperAdmin()) {
			ajaxRedirectToDashboard();
		}
	}

	public void mailLogDataModelInfo(){
		mailLogDataModel = new LazyDataModel<MailLog>() {
			private static final long serialVersionUID = 1L;
			private List<MailLog> retorno = new ArrayList<MailLog>();
			@Override
			public List<MailLog> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (sortField == null || sortField.isEmpty()) {

						sortField = "id";
						sortOrder = SortOrder.DESCENDING;
					}
					retorno = mailLogService.allMailLog(MailLog.class, first, pageSize, sortField, sortOrder, filters);
					mailLogDataModel.setRowCount(mailLogService.countAllMailLog(MailLog.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
					e.printStackTrace();
				}
				return retorno;
			}
			@Override
			public Object getRowKey(MailLog obj) {
				return obj.getId();
			}
			@Override
			public MailLog getRowData(String rowKey) {
				for (MailLog obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	/* Getters and Setters */
	public MailLog getMailLog() {
		return mailLog;
	}

	public void setMailLog(MailLog mailLog) {
		this.mailLog = mailLog;
	}

	public LazyDataModel<MailLog> getMailLogDataModel() {
		return mailLogDataModel;
	}

	public void setMailLogDataModel(LazyDataModel<MailLog> mailLogDataModel) {
		this.mailLogDataModel = mailLogDataModel;
	}
	
	
}