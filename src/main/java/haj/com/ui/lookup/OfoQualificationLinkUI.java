package haj.com.ui.lookup;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.lookup.OfoQualificationLink;
import haj.com.service.lookup.OfoQualificationLinkService;
import javax.faces.model.SelectItem;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import java.util.Map;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "ofoqualificationlinkUI")
@ViewScoped
public class OfoQualificationLinkUI extends AbstractUI {

	private OfoQualificationLinkService service = new OfoQualificationLinkService();
	private List<OfoQualificationLink> ofoqualificationlinkList = null;
	private List<OfoQualificationLink> ofoqualificationlinkfilteredList = null;
	private OfoQualificationLink ofoqualificationlink = null;
	private LazyDataModel<OfoQualificationLink> dataModel;

	@PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all OfoQualificationLink and prepare a for a
	 * create of a new OfoQualificationLink
	 * 
	 * @author TechFinium
	 * @see OfoQualificationLink
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		ofoqualificationlinkInfo();
	}

	/**
	 * Get all OfoQualificationLink for data table
	 * 
	 * @author TechFinium
	 * @see OfoQualificationLink
	 */
	public void ofoqualificationlinkInfo() {

		dataModel = new LazyDataModel<OfoQualificationLink>() {

			private static final long serialVersionUID = 1L;
			private List<OfoQualificationLink> retorno = new ArrayList<OfoQualificationLink>();

			@Override
			public List<OfoQualificationLink> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {

				try {
					retorno = service.allOfoQualificationLink(OfoQualificationLink.class, first, pageSize, sortField,
							sortOrder, filters);
					dataModel.setRowCount(service.count(OfoQualificationLink.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(OfoQualificationLink obj) {
				return obj.getId();
			}

			@Override
			public OfoQualificationLink getRowData(String rowKey) {
				for (OfoQualificationLink obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert OfoQualificationLink into database
	 * 
	 * @author TechFinium
	 * @see OfoQualificationLink
	 */
	public void ofoqualificationlinkInsert() {
		try {
			if (service.countFindByOfoQualificationId(this.ofoqualificationlink) == 0 ) {
				service.create(this.ofoqualificationlink);
				prepareNew();
				addInfoMessage(super.getEntryLanguage("update.successful"));
				ofoqualificationlinkInfo();
			} else {
				addErrorMessage("Duplacte Combination Found. Choose Either A Differenet Ofo or Qualification");
			}
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update OfoQualificationLink in database
	 * 
	 * @author TechFinium
	 * @see OfoQualificationLink
	 */
	public void ofoqualificationlinkUpdate() {
		try {
			service.update(this.ofoqualificationlink);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			ofoqualificationlinkInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete OfoQualificationLink from database
	 * 
	 * @author TechFinium
	 * @see OfoQualificationLink
	 */
	public void ofoqualificationlinkDelete() {
		try {
			service.delete(this.ofoqualificationlink);
			prepareNew();
			ofoqualificationlinkInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of OfoQualificationLink
	 * 
	 * @author TechFinium
	 * @see OfoQualificationLink
	 */
	public void prepareNew() {
		ofoqualificationlink = new OfoQualificationLink();
	}

	/*
	 * public List<SelectItem> getOfoQualificationLinkDD() { List<SelectItem> l
	 * =new ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * ofoqualificationlinkInfo(); for (OfoQualificationLink ug :
	 * ofoqualificationlinkList) { // l.add(new SelectItem(ug.getUserGroupId(),
	 * ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<OfoQualificationLink> complete(String desc) {
		List<OfoQualificationLink> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<OfoQualificationLink> getOfoQualificationLinkList() {
		return ofoqualificationlinkList;
	}

	public void setOfoQualificationLinkList(List<OfoQualificationLink> ofoqualificationlinkList) {
		this.ofoqualificationlinkList = ofoqualificationlinkList;
	}

	public OfoQualificationLink getOfoqualificationlink() {
		return ofoqualificationlink;
	}

	public void setOfoqualificationlink(OfoQualificationLink ofoqualificationlink) {
		this.ofoqualificationlink = ofoqualificationlink;
	}

	public List<OfoQualificationLink> getOfoQualificationLinkfilteredList() {
		return ofoqualificationlinkfilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param ofoqualificationlinkfilteredList
	 *            the new ofoqualificationlinkfilteredList list
	 * @see OfoQualificationLink
	 */
	public void setOfoQualificationLinkfilteredList(List<OfoQualificationLink> ofoqualificationlinkfilteredList) {
		this.ofoqualificationlinkfilteredList = ofoqualificationlinkfilteredList;
	}

	public LazyDataModel<OfoQualificationLink> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<OfoQualificationLink> dataModel) {
		this.dataModel = dataModel;
	}

}
