package haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Signoff;
import haj.com.entity.Tasks;
import haj.com.entity.TempSignoff;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.SignoffService;
import haj.com.service.TasksService;

@ManagedBean(name = "signoffUI")
@ViewScoped
public class SignoffUI extends AbstractUI {

	private SignoffService service = new SignoffService();
	private List<Signoff> signoffList = null;
	private List<Signoff> signofffilteredList = null;
	private Signoff signoff = null;
	private TempSignoff tempSignoff;
	private String idNumber;
	private String email;
	private LazyDataModel<Signoff> dataModel;
	private Tasks tasks;
	private boolean confirmedDetails;

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
	 * Initialize method to get all Signoff and prepare a for a create of a new
	 * Signoff
	 * 
	 * @author TechFinium
	 * @see Signoff
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		signoffInfo();
		idNumber = "";
	}

	/**
	 * Get all Signoff for data table
	 * 
	 * @author TechFinium
	 * @see Signoff
	 */
	public void signoffInfo() {
		if (super.getParameter("id", false) == null) {
			dataModel = new LazyDataModel<Signoff>() {

				private static final long serialVersionUID = 1L;
				private List<Signoff> retorno = new ArrayList<Signoff>();

				@Override
				public List<Signoff> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {

					try {
						retorno = service.allSignoff(Signoff.class, first, pageSize, sortField, sortOrder, filters);
						dataModel.setRowCount(service.count(Signoff.class, filters));
					} catch (Exception e) {
						logger.fatal(e);
					}
					return retorno;
				}

				@Override
				public Object getRowKey(Signoff obj) {
					return obj.getId();
				}

				@Override
				public Signoff getRowData(String rowKey) {
					for (Signoff obj : retorno) {
						if (obj.getId().equals(Long.valueOf(rowKey))) return obj;
					}
					return null;
				}
			};
		} else {
			try {
				tasks = TasksService.instance().findByGuid("" + super.getParameter("id", false));
				if (tasks != null) {
					signoff = service.findByKey(tasks.getTargetKey());
					if (signoff != null) {
						tempSignoff = signoff.getTempSignoff();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	public void confirmDetails() {
		if (this.tempSignoff != null) {
			this.confirmedDetails = tempSignoff.getEmail().equals(email) && tempSignoff.getIdNumber().equals(idNumber);
		}
		if (!confirmedDetails) addErrorMessage("Details are incorrect.");
	}

	public void saveSignOff() {
		try {
			service.saveSignOff(signoff, tasks);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Insert Signoff into database
	 * 
	 * @author TechFinium
	 * @see Signoff
	 */
	public void signoffInsert() {
		try {
			service.create(this.signoff);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			signoffInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Signoff in database
	 * 
	 * @author TechFinium
	 * @see Signoff
	 */
	public void signoffUpdate() {
		try {
			service.update(this.signoff);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			signoffInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Signoff from database
	 * 
	 * @author TechFinium
	 * @see Signoff
	 */
	public void signoffDelete() {
		try {
			service.delete(this.signoff);
			prepareNew();
			signoffInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Signoff
	 * 
	 * @author TechFinium
	 * @see Signoff
	 */
	public void prepareNew() {
		signoff = new Signoff();
	}

	/*
	 * public List<SelectItem> getSignoffDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * signoffInfo(); for (Signoff ug : signoffList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Signoff> complete(String desc) {
		List<Signoff> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Signoff> getSignoffList() {
		return signoffList;
	}

	public void setSignoffList(List<Signoff> signoffList) {
		this.signoffList = signoffList;
	}

	public Signoff getSignoff() {
		return signoff;
	}

	public void setSignoff(Signoff signoff) {
		this.signoff = signoff;
	}

	public List<Signoff> getSignofffilteredList() {
		return signofffilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param signofffilteredList
	 *            the new signofffilteredList list
	 * @see Signoff
	 */
	public void setSignofffilteredList(List<Signoff> signofffilteredList) {
		this.signofffilteredList = signofffilteredList;
	}

	public LazyDataModel<Signoff> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Signoff> dataModel) {
		this.dataModel = dataModel;
	}

	public TempSignoff getTempSignoff() {
		return tempSignoff;
	}

	public void setTempSignoff(TempSignoff tempSignoff) {
		this.tempSignoff = tempSignoff;
	}

	public boolean isConfirmedDetails() {
		return confirmedDetails;
	}

	public void setConfirmedDetails(boolean confirmedDetails) {
		this.confirmedDetails = confirmedDetails;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Tasks getTasks() {
		return tasks;
	}

	public void setTasks(Tasks tasks) {
		this.tasks = tasks;
	}

}
