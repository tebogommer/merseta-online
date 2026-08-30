package haj.com.ui.lookup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Address;
import haj.com.entity.lookup.Office;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;
import haj.com.service.lookup.OfficeService;

@ManagedBean(name = "officeUI")
@ViewScoped
public class OfficeUI extends AbstractUI {

	private OfficeService service = new OfficeService();
	private AddressService addressService = new AddressService();
	private List<Office> officeList = null;
	private List<Office> officefilteredList = null;
	private Office office = null;
	private Address address = null;
	private LazyDataModel<Office> dataModel;

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
	 * Initialize method to get all Office and prepare a for a create of a new
	 * Office
	 * 
	 * @author TechFinium
	 * @see Office
	 * @throws Exception
	 *             the exception
	 */
	private void runInit() throws Exception {
		prepareNew();
		officeInfo();
	}

	/**
	 * Get all Office for data table
	 * 
	 * @author TechFinium
	 * @see Office
	 */
	public void officeInfo() {
		try {
			officeList = service.allOffice();
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		dataModel = new LazyDataModel<Office>() {

			private static final long serialVersionUID = 1L;
			private List<Office> retorno = new ArrayList<Office>();

			@Override
			public List<Office> load(int first, int pageSize, String sortField, SortOrder sortOrder,
					Map<String, Object> filters) {
				try {
					retorno = service.allOfficeHql(Office.class, first, pageSize, sortField, sortOrder, filters);
					dataModel.setRowCount(service.countHql(Office.class, filters));
				} catch (Exception e) {
					logger.fatal(e);
				}
				return retorno;
			}

			@Override
			public Object getRowKey(Office obj) {
				return obj.getId();
			}

			@Override
			public Office getRowData(String rowKey) {
				for (Office obj : retorno) {
					if (obj.getId().equals(Long.valueOf(rowKey)))
						return obj;
				}
				return null;
			}

		};

	}

	/**
	 * Insert Office into database
	 * 
	 * @author TechFinium
	 * @see Office
	 */
	public void officeInsert() {
		try {
			addressService.create(address);
			service.create(this.office);
			prepareNew();
			addInfoMessage(super.getEntryLanguage("update.successful"));
			officeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Preps update of office and locates address
	 * 
	 * @see Office
	 * @see Address
	 */
	public void prepOfficeUpdate() {
		try {
			if (office.getPhysicalAddress() == null) {
				address = new Address();
				office.setPhysicalAddress(address);
			} else {
				address = addressService.findByKey(office.getPhysicalAddress().getId());
			}
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Update Office in database
	 * 
	 * @author TechFinium
	 * @see Office
	 */
	public void officeUpdate() {
		try {
			addressService.update(address);
			service.update(this.office);
			addInfoMessage(super.getEntryLanguage("update.successful"));
			officeInfo();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			super.addCallBackParm("validationFailed", true);
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Delete Office from database
	 * 
	 * @author TechFinium
	 * @see Office
	 */
	public void officeDelete() {
		try {
			service.delete(this.office);
			prepareNew();
			officeInfo();
			super.addWarningMessage(super.getEntryLanguage("row.deleted"));
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Create new instance of Office
	 * 
	 * @author TechFinium
	 * @see Office
	 */
	public void prepareNew() {
		office = new Office();
		address = new Address();
		office.setPhysicalAddress(address);
	}

	/*
	 * public List<SelectItem> getOfficeDD() { List<SelectItem> l =new
	 * ArrayList<SelectItem>(); l.add(new
	 * SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
	 * officeInfo(); for (Office ug : officeList) { // l.add(new
	 * SelectItem(ug.getUserGroupId(), ug.getUserGroupDesc())); } return l; }
	 */

	/**
	 * Complete.
	 *
	 * @param desc
	 *            the desc
	 * @return the list
	 */
	public List<Office> complete(String desc) {
		List<Office> l = null;
		try {
			l = service.findByName(desc);
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

	public List<Office> getOfficeList() {
		return officeList;
	}

	public void setOfficeList(List<Office> officeList) {
		this.officeList = officeList;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	public List<Office> getOfficefilteredList() {
		return officefilteredList;
	}

	/**
	 * Prepare auto complete data
	 * 
	 * @author TechFinium
	 * @param officefilteredList
	 *            the new officefilteredList list
	 * @see Office
	 */
	public void setOfficefilteredList(List<Office> officefilteredList) {
		this.officefilteredList = officefilteredList;
	}

	public LazyDataModel<Office> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<Office> dataModel) {
		this.dataModel = dataModel;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
