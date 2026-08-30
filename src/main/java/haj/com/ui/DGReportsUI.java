package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Blank;
import haj.com.entity.DgVerification;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.DgVerificationService;

// TODO: Auto-generated Javadoc
/**
 * The Class BlankUI.
 */
@ManagedBean(name = "dgReportsUI")
@ViewScoped
public class DGReportsUI extends AbstractUI {

	private DgVerificationService service = new DgVerificationService();
	private List<DgVerification> dgverificationList = null;
	private List<DgVerification> dgverificationfilteredList = null;
	private DgVerification dgverification = null;
	private LazyDataModel<DgVerification> dataModel;

	/**
	 * Inits the.
	 */
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
	 * Initialize method to get all Blank and prepare a for a create of a new Blank.
	 *
	 * @author TechFinium
	 * @throws Exception the exception
	 * @see Blank
	 */
	private void runInit() throws Exception {
		dgverificationLMR16();
	}

	public void dgverificationLMR16() {
		dataModel = new LazyDataModel<DgVerification>() {

			private static final long serialVersionUID = 1L;
			private List<DgVerification> retorno = new ArrayList<DgVerification>();

			@Override
			public List<DgVerification> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					retorno = service.allDgVerificationReporting(DgVerification.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.count(DgVerification.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(DgVerification obj) {
				return obj.getId();
			}

			@Override
			public DgVerification getRowData(String rowKey) {
				for (DgVerification obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}
		};
	}

	public List<DgVerification> getDgverificationList() {
		return dgverificationList;
	}

	public void setDgverificationList(List<DgVerification> dgverificationList) {
		this.dgverificationList = dgverificationList;
	}

	public List<DgVerification> getDgverificationfilteredList() {
		return dgverificationfilteredList;
	}

	public void setDgverificationfilteredList(List<DgVerification> dgverificationfilteredList) {
		this.dgverificationfilteredList = dgverificationfilteredList;
	}

	public DgVerification getDgverification() {
		return dgverification;
	}

	public void setDgverification(DgVerification dgverification) {
		this.dgverification = dgverification;
	}

	public LazyDataModel<DgVerification> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<DgVerification> dataModel) {
		this.dataModel = dataModel;
	}
}