package  haj.com.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import haj.com.entity.Address;
import haj.com.exceptions.ValidationException;
import haj.com.framework.AbstractUI;
import haj.com.service.AddressService;


@ManagedBean(name = "monitorAddressInformationUI")
@ViewScoped
public class MonitorAddressInformationUI extends AbstractUI {

	/* Entity Level */
	private Address selectedAddress = null;
	
	/* Service Level */
	private AddressService addressService = new AddressService();
	
	/* Lazy Data Models */
	private LazyDataModel<Address> dataModelAddress;
	
	/* VARS */
	private Long addressID = null;
	private boolean useAddressID = false;
	private boolean alterGps = false;
	
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
		getSessionUI().setValidationErrors("");
		populateAddress();
	}
	
	private void populateAddress() throws Exception{
		addressInfo();
	}
	
	public void applyFilter(){
		selectedAddress = null;
		useAddressID = true;
		addressInfo();
	}
	
	public void clearFilter(){
		selectedAddress = null;
		useAddressID = false;
		addressInfo();
	}
	
	public void addressInfo() {
		dataModelAddress = new LazyDataModel<Address>() {
			private static final long serialVersionUID = 1L;
			private List<Address> list = new ArrayList<>();
			@Override
			public List<Address> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) {
				try {
					if (useAddressID && addressID != null) {
						list = addressService.allAddressByID(first, pageSize, sortField, sortOrder, filters, addressID);
						dataModelAddress.setRowCount(addressService.countAllAddressByID(filters));
					} else {
						list = addressService.allAddress(Address.class, first, pageSize, sortField, sortOrder, filters);
						dataModelAddress.setRowCount(addressService.count(Address.class, filters));
					}
				} catch (Exception e) {
					logger.fatal(e);
					addErrorMessage(e.getMessage(), e);
				}
				return list;
			}
			@Override
			public Object getRowKey(Address object) {
				return object.getId();
			}
			@Override
			public Address getRowData(String rowKey) {
				for (Address obj : list) {
					if (obj.getId().equals(Long.valueOf(rowKey))) {
						return obj;
					}
				}
				return null;
			}
		};
	}
	
	public void deselectObject(){
		try {
			getSessionUI().setValidationErrors("");
			selectedAddress = null;
			addInfoMessage("Address De-selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void selectObject(){
		try {
			alterGps = false;
			getSessionUI().setValidationErrors("");
			selectedAddress = addressService.findByKey(selectedAddress.getId());
			addInfoMessage("Address Selected");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		} 
	}
	
	public void runValidiationChecks(){
		try {
			getSessionUI().setValidationErrors("");
			Address addressForValidiation = addressService.findByKey(selectedAddress.getId());
			addressForValidiation.setValidiationRanDate(getNow());
			addressService.updateAddressForValidiation(addressForValidiation);
			addressForValidiation = null;
			addInfoMessage("Check User No Validiation Errors on address level");
		} catch (javax.validation.ConstraintViolationException e) {
			addErrorMessage("Validiation Expection. Review Errors");
			extractValidationErrors(e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void updateInformation(){
		try {
			if (selectedAddress == null && selectedAddress.getId() == null) {
				throw new Exception("Unable to locate address information, contact support!");
			} else {
				addressService.updateAddressForValidiation(selectedAddress);
			}
			getSessionUI().setValidationErrors("");
			selectedAddress = null;
			addInfoMessage("Update Complete");
		} catch (javax.validation.ConstraintViolationException e) {
			addErrorMessage("Validiation Expection. Review Errors");
			extractValidationErrors(e);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		} 
	}

	
	public void getCoOrdinates_vh() {
		System.out.println("in getCoOrdinates_vh");
		System.out.println("-----"+selectedAddress.getAddressLine1());
		System.out.println("-----"+selectedAddress.getAddressLine2());
		addressService.lookupLongitudeLatitude(selectedAddress);
	}
	
	/** getters and setters */
	public Address getSelectedAddress() {
		return selectedAddress;
	}

	public void setSelectedAddress(Address selectedAddress) {
		this.selectedAddress = selectedAddress;
	}

	public LazyDataModel<Address> getDataModelAddress() {
		return dataModelAddress;
	}

	public void setDataModelAddress(LazyDataModel<Address> dataModelAddress) {
		this.dataModelAddress = dataModelAddress;
	}

	public Long getAddressID() {
		return addressID;
	}

	public void setAddressID(Long addressID) {
		this.addressID = addressID;
	}

	public boolean isUseAddressID() {
		return useAddressID;
	}

	public void setUseAddressID(boolean useAddressID) {
		this.useAddressID = useAddressID;
	}

	public boolean isAlterGps() {
		return alterGps;
	}

	public void setAlterGps(boolean alterGps) {
		this.alterGps = alterGps;
	}	
}