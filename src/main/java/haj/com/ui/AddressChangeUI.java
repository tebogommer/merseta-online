package  haj.com.ui;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import haj.com.framework.AbstractUI;
import haj.com.entity.Address;
import haj.com.entity.AddressChange;
import haj.com.entity.AddressHistory;
import haj.com.entity.ChangeReason;
import haj.com.entity.Doc;
import haj.com.entity.Users;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.service.AddressChangeService;
import haj.com.service.AddressHistoryService;
import haj.com.service.AddressService;
import haj.com.service.ChangeReasonService;
import haj.com.service.lookup.RejectReasonsService;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FilenameUtils;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.LazyDataModel;
import haj.com.exceptions.ValidationException;

@ManagedBean(name = "addresschangeUI")
@ViewScoped
public class AddressChangeUI extends AbstractUI {

	private AddressChangeService service = new AddressChangeService();
	private List<AddressChange> addresschangeList = null;
	private List<AddressChange> addresschangefilteredList = null;
	private AddressChange addresschange = null;
	private LazyDataModel<AddressChange> dataModel; 
	private ArrayList<RejectReasons> selectedRejectReason;
	private Address residentialAddress;
	private Address postalAddress;
	private Address residentialAddressHist=new Address();
	private Address postalAddressHist=new Address();
	private Boolean copyAddress;
	private Users user;
	private Doc doc;
	public static ChangeReason changeReason = new ChangeReason();
	private List<ChangeReason> changeReasonsList = new ArrayList<>();
	private ChangeReason providedChangeReason = new ChangeReason();
	

    @PostConstruct
	public void init() {
		try {
			runInit();
		} catch (ValidationException e) {
			addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}

	/**
	 * Initialize method to get all AddressChange and prepare a for a create of a new AddressChange
 	 * @author TechFinium 
 	 * @see    AddressChange
	 * @throws Exception the exception
 	 */
	private void runInit() throws Exception {
		if(getSessionUI().getTask() !=null && getSessionUI().getTask().getWorkflowProcess()== ConfigDocProcessEnum.ADDRESS_CHANGE){
			addresschange = service.findByKey(getSessionUI().getTask().getTargetKey());
			residentialAddress=addresschange.getForResidentialAddress();
			postalAddress=addresschange.getForPostalAddress();
			user=addresschange.getUser();
			List<AddressHistory> resAddressHistoryList=AddressHistoryService.instance().findByForAddress(addresschange.getForResidentialAddress().getId());
			List<AddressHistory> postAddressHistoryList=AddressHistoryService.instance().findByForAddress(addresschange.getForPostalAddress().getId());
			if(resAddressHistoryList !=null && resAddressHistoryList.size()>0){
				Long resAdreessID=addresschange.getForResidentialAddress().getId();
				BeanUtils.copyProperties(residentialAddressHist, resAddressHistoryList.get(0));
				residentialAddressHist.setId(resAdreessID);
			}
			if(postAddressHistoryList !=null && postAddressHistoryList.size()>0){
				Long postAdreessID=addresschange.getForPostalAddress().getId();
				BeanUtils.copyProperties(postalAddressHist, postAddressHistoryList.get(0));
				postalAddressHist.setId(postAdreessID);
			}
			findChangeReason() ;
		}
		else
		{
			prepareNew();
			addresschangeInfo();
		}
	}
	
	public void findChangeReason() throws Exception {
		changeReasonsList = (List<ChangeReason>) ChangeReasonService.instance().findByTargetClassAndTargetKey(getSessionUI().getTask().getTargetClass(), getSessionUI().getTask().getTargetKey());
		if (changeReasonsList.size() > 0) {
			providedChangeReason = changeReasonsList.get(0);
		}
	}

	/**
	 * Get all AddressChange for data table
 	 * @author TechFinium 
 	 * @see    AddressChange
 	 */
	public void addresschangeInfo() {
			//dataModel = new AddressChangeDatamodel();
	}

	/**
	 * Insert AddressChange into database
 	 * @author TechFinium 
 	 * @see    AddressChange
 	 */
	public void addresschangeInsert() {
		try {
				 service.create(this.addresschange);
				 prepareNew();
				 addInfoMessage(super.getEntryLanguage("update.successful"));
				 addresschangeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
		 	   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}
	
	/**
	 * Update AddressChange in database
 	 * @author TechFinium 
 	 * @see    AddressChange
 	 */
    public void addresschangeUpdate() {
		try {
				 service.update(this.addresschange);
				  addInfoMessage(super.getEntryLanguage("update.successful"));
				 addresschangeInfo();
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
			} catch (Exception e) {
			   super.addCallBackParm("validationFailed", true);
				addErrorMessage(e.getMessage(), e);
			}
	}

	/**
	 * Delete AddressChange from database
 	 * @author TechFinium 
 	 * @see    AddressChange
 	 */
	public void addresschangeDelete() {
		try {
			 service.delete(this.addresschange);
			  prepareNew();
			 addresschangeInfo();
			 super.addWarningMessage(super.getEntryLanguage("row.deleted"));
			 } catch (ValidationException e) {
				addErrorMessage(getEntryLanguage(e.getMessage(), e.getParams()));
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	/**
	 * Create new instance of AddressChange 
 	 * @author TechFinium 
 	 * @see    AddressChange
 	 */
	public void prepareNew() {
		addresschange = new AddressChange();
	}
	
	public void clearPostal() {
		if (!copyAddress) {
			AddressService.instance().clearAddress(postalAddress);
		}
	}
	
	public void clearAddress()
	{
		try {
			AddressService.instance().clearAddress(postalAddress);
			AddressService.instance().clearAddress(residentialAddress);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void requestAddressUpdate() {
		try {
			super.runClientSideExecute("uploadStart()");
			if(copyAddress){
				AddressService.instance().copyFromToAddress(residentialAddress, postalAddress);
			}
			service.requestAddressUpdate(residentialAddress, postalAddress,getSessionUI().getActiveUser(),changeReason);
			super.runClientSideExecute("PF('dglAddreessUpdate').hide()");
			super.runClientSideExecute("uploadDone()");
			addInfoMessage("Your address changes are being processed");
			super.runClientSideUpdate("detailInfoForm");
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
			e.printStackTrace();
		}
	}
	
	public void approveAddressUpdateTask() {
		try {
			service.approveAddressUpdateTask(getSessionUI().getTask(),user,addresschange);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void rejectAddressUpdateTask() {
		try {
			if (selectedRejectReason.size() == 0) {
				super.runClientSideExecute("uploadDone()");
				throw new Exception("Please select a reject reason");
			}
			service.rejectAddressUpdateTask(getSessionUI().getTask(), getSessionUI().getActiveUser(),user,selectedRejectReason,residentialAddressHist,postalAddressHist,addresschange);
			getSessionUI().setTask(null);
			super.ajaxRedirectToDashboard();
			super.runClientSideExecute("uploadDone()");
		} catch (Exception e) {
			super.runClientSideExecute("uploadDone()");
			e.printStackTrace();
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void storeChangeNewFile(FileUploadEvent event) {
		try {
			if(doc==null){
				prepChangeDoc();
			}
			doc.setData(event.getFile().getContents());
			doc.setOriginalFname(event.getFile().getFileName());
			doc.setExtension(FilenameUtils.getExtension(doc.getOriginalFname()));
			doc.setUsers(getSessionUI().getActiveUser());
			changeReason.setDoc(doc);
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
	}
	
	public void prepChangeDoc() {
		clearChangeReason();
		doc = new Doc();
		doc.setChangeReason(changeReason);
	}
	
	public void clearChangeReason() {
		changeReason = new ChangeReason();
		changeReason.setDescription(null);
	}
	
	
	public List<RejectReasons> getRejectReasons() {
		RejectReasonsService rejectReasonsService = new RejectReasonsService();
		List<RejectReasons> l = null;
		try {
			l = rejectReasonsService.findByProcess(getSessionUI().getTask().getWorkflowProcess());
		} catch (Exception e) {
			addErrorMessage(e.getMessage(), e);
		}
		return l;
	}

/*
    public List<SelectItem> getAddressChangeDD() {
    	List<SelectItem> l =new ArrayList<SelectItem>();
    	l.add(new SelectItem(Long.valueOf(-1L),"-------------Select-------------"));
    	addresschangeInfo();
    	for (AddressChange ug : addresschangeList) {
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
    public List<AddressChange> complete(String desc) {
		List<AddressChange> l = null;
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
    
    public List<AddressChange> getAddressChangeList() {
		return addresschangeList;
	}
	public void setAddressChangeList(List<AddressChange> addresschangeList) {
		this.addresschangeList = addresschangeList;
	}
	public AddressChange getAddresschange() {
		return addresschange;
	}
	public void setAddresschange(AddressChange addresschange) {
		this.addresschange = addresschange;
	}

    public List<AddressChange> getAddressChangefilteredList() {
		return addresschangefilteredList;
	}
	
	/**
	 * Prepare auto complete data
 	 * @author TechFinium 
 	 * @param addresschangefilteredList the new addresschangefilteredList list
 	 * @see    AddressChange
 	 */	
	public void setAddressChangefilteredList(List<AddressChange> addresschangefilteredList) {
		this.addresschangefilteredList = addresschangefilteredList;
	}

	
	public LazyDataModel<AddressChange> getDataModel() {
		return dataModel;
	}

	public void setDataModel(LazyDataModel<AddressChange> dataModel) {
		this.dataModel = dataModel;
	}

	public Address getResidentialAddress() {
		return residentialAddress;
	}

	public void setResidentialAddress(Address residentialAddress) {
		this.residentialAddress = residentialAddress;
	}

	public Address getPostalAddress() {
		return postalAddress;
	}

	public void setPostalAddress(Address postalAddress) {
		this.postalAddress = postalAddress;
	}

	public Boolean getCopyAddress() {
		return copyAddress;
	}

	public void setCopyAddress(Boolean copyAddress) {
		this.copyAddress = copyAddress;
	}

	public Address getResidentialAddressHist() {
		return residentialAddressHist;
	}

	public void setResidentialAddressHist(Address residentialAddressHist) {
		this.residentialAddressHist = residentialAddressHist;
	}

	public Address getPostalAddressHist() {
		return postalAddressHist;
	}

	public void setPostalAddressHist(Address postalAddressHist) {
		this.postalAddressHist = postalAddressHist;
	}

	public Users getUser() {
		return user;
	}

	public void setUser(Users user) {
		this.user = user;
	}

	public ArrayList<RejectReasons> getSelectedRejectReason() {
		return selectedRejectReason;
	}

	public void setSelectedRejectReason(ArrayList<RejectReasons> selectedRejectReason) {
		this.selectedRejectReason = selectedRejectReason;
	}

	public Doc getDoc() {
		return doc;
	}

	public void setDoc(Doc doc) {
		this.doc = doc;
	}

	public ChangeReason getProvidedChangeReason() {
		return providedChangeReason;
	}

	public void setProvidedChangeReason(ChangeReason providedChangeReason) {
		this.providedChangeReason = providedChangeReason;
	}
	
}
