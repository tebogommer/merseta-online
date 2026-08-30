package haj.com.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import haj.com.dao.AddressChangeDAO;
import haj.com.entity.Address;
import haj.com.entity.AddressChange;
import haj.com.entity.ChangeReason;
import haj.com.entity.Company;
import haj.com.entity.RejectReasonMultipleSelection;
import haj.com.entity.Tasks;
import haj.com.entity.Users;
import haj.com.entity.enums.AddressEnum;
import haj.com.entity.enums.ApprovalEnum;
import haj.com.entity.enums.CompanyStatusEnum;
import haj.com.entity.enums.ConfigDocProcessEnum;
import haj.com.entity.enums.MailEnum;
import haj.com.entity.enums.MailTagsEnum;
import haj.com.entity.lookup.RejectReasons;
import haj.com.framework.AbstractService;
import haj.com.ui.TrainingProviderApplicationUI;
import haj.com.utils.GenericUtility;
import haj.com.utils.GeoCoderUtil;

import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.primefaces.model.SortOrder;

import com.google.code.geocoder.model.LatLng;

public class AddressChangeService extends AbstractService {
	/** The dao. */
	private AddressChangeDAO dao = new AddressChangeDAO();
	/** The address service. */
	private static AddressChangeService addressChangeService = null;
	
	/**
	 * Instance.
	 *
	 * @return the address service
	 */
	public static synchronized AddressChangeService instance() {
		if (addressChangeService == null) {
			addressChangeService = new AddressChangeService();
		}
		return addressChangeService;
	}

	/**
	 * Get all AddressChange
 	 * @author TechFinium 
 	 * @see   AddressChange
 	 * @return a list of {@link haj.com.entity.AddressChange}
	 * @throws Exception the exception
 	 */
	public List<AddressChange> allAddressChange() throws Exception {
	  	return dao.allAddressChange();
	}


	/**
	 * Create or update AddressChange.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see     AddressChange
	 */
	public void create(AddressChange entity) throws Exception  {
	   if (entity.getId() ==null) {
		 this.dao.create(entity);
	   }
		else
		 this.dao.update(entity);
	}


	/**
	 * Update  AddressChange.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AddressChange
	 */
	public void update(AddressChange entity) throws Exception  {
		this.dao.update(entity);
	}

	/**
	 * Delete  AddressChange.
	 *
	 * @author TechFinium
	 * @param entity the entity
	 * @throws Exception the exception
	 * @see    AddressChange
	 */
	public void delete(AddressChange entity) throws Exception  {
		this.dao.delete(entity);
	}

	/**
	 * Find object by primary key.
	 *
	 * @author TechFinium
	 * @param id the id
	 * @return a {@link haj.com.entity.AddressChange}
	 * @throws Exception the exception
	 * @see    AddressChange
	 */
	public AddressChange findByKey(long id) throws Exception {
       return dao.findByKey(id);
	}

	/**
	 * Find AddressChange by description.
	 *
	 * @author TechFinium
	 * @param desc the desc
	 * @return a list of {@link haj.com.entity.AddressChange}
	 * @throws Exception the exception
	 * @see    AddressChange
	 */
	public List<AddressChange> findByName(String desc) throws Exception {
		return dao.findByName(desc);
	}
	
	/**
	 * Lazy load AddressChange
	 * @param first from key
	 * @param pageSize no of rows to fetch
	 * @return a list of {@link haj.com.entity.AddressChange}
	 * @throws Exception the exception
	 */
	public List<AddressChange> allAddressChange(int first, int pageSize) throws Exception {
		return dao.allAddressChange(Long.valueOf(first), pageSize);
	}
		
	
	/**
	 * Get count of AddressChange for lazy load
	 * @author TechFinium 
	 * @return Number of rows in the AddressChange
	 * @throws Exception the exception
	 */
	public Long count() throws Exception {
	       return dao.count(AddressChange.class);
	}
	
    /**
     * Lazy load AddressChange with pagination, filter, sorting
     * @author TechFinium 
     * @param class1 AddressChange class
     * @param first the first
     * @param pageSize the page size
     * @param sortField the sort field
     * @param sortOrder the sort order
     * @param filters the filters
     * @return  a list of {@link haj.com.entity.AddressChange} containing data
     * @throws Exception the exception
     */	
	@SuppressWarnings("unchecked")
	public List<AddressChange> allAddressChange(Class<AddressChange> class1, int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, Object> filters) throws Exception {
		return ( List<AddressChange>)dao.sortAndFilter(class1,first,pageSize,sortField,sortOrder,filters);
	
	}
	
    /**
     * Get count of AddressChange for lazy load with filters
     * @author TechFinium 
     * @param entity AddressChange class
     * @param filters the filters
     * @return Number of rows in the AddressChange entity
     * @throws Exception the exception     
     */	
	public int count(Class<AddressChange> entity, Map<String, Object> filters) throws Exception {
		return  dao.count(entity, filters);
	}
	
	public boolean  checkIfSameAddress(AddressChange addressChange, Address address) {
		if(addressChange.getAddressLine1().equals(address.getAddressLine1()) 
			&& addressChange.getAddressLine2().equals(address.getAddressLine2())
			&& addressChange.getAddressLine3().equals(address.getAddressLine3())
			&& addressChange.getPostcode().equals(address.getPostcode() )
			&& addressChange.getTown().equals(address.getTown() )
			&& addressChange.getStatsSaAreaCode().equals(address.getStatsSaAreaCode()))
	         return true;  
        else {
        	return false;
        }	    
	}
	
	@SuppressWarnings("unchecked")
	public int countByForPostalAndForResidentialAddress(Long forResID,Long forPostID,ApprovalEnum status) throws Exception {
	 	return dao.countByForPostalAndForResidentialAddress(forResID, forPostID, status);
	}
	
	public void copyAddressChangeToAddress(AddressChange from, Address to) {
		to.setPostcode(from.getPostcode());
		to.setAddressLine1(from.getAddressLine1());
		to.setAddressLine2(from.getAddressLine2());
		to.setAddressLine3(from.getAddressLine3());
		to.setAddressLine4(from.getAddressLine4());
		to.setTown(from.getTown());
		to.setLongitude(from.getLongitude());
		to.setLatitude(from.getLatitude());
		to.setMunicipality(from.getMunicipality());
	}
	
	public void copyAddressToAddressChange(Address from, AddressChange to) {
		if(from!= null) {
			if(from.getPostcode() != null) {
				to.setPostcode(from.getPostcode());
			}			
			if(from.getAddressLine1() != null) {
				to.setAddressLine1(from.getAddressLine1());
			}
			if(from.getAddressLine2() != null) {
				to.setAddressLine2(from.getAddressLine2());
			}
			if(from.getAddressLine3() != null) {
				to.setAddressLine3(from.getAddressLine3());
			}
			if(from.getAddressLine4() != null) {
				to.setAddressLine4(from.getAddressLine4());
			}
			if(from.getTown() != null) {
				to.setTown(from.getTown());
			}			
			if(from.getLongitude() != null) {
				to.setLongitude(from.getLongitude());
			}
			if(from.getLatitude() != null) {
				to.setLatitude(from.getLatitude());
			}
			if(from.getMunicipality() != null) {
				to.setMunicipality(from.getMunicipality());
			}			
		}
	}
	
	public AddressChange copyAddressToAddressChange(Address from) {
		AddressChange to = new AddressChange();
		to.setPostcode(from.getPostcode());
		to.setAddressLine1(from.getAddressLine1());
		to.setAddressLine2(from.getAddressLine2());
		to.setAddressLine3(from.getAddressLine3());
		to.setAddressLine4(from.getAddressLine4());
		to.setTown(from.getTown());
		to.setLongitude(from.getLongitude());
		to.setLatitude(from.getLatitude());
		to.setMunicipality(from.getMunicipality());
		return to;
	}


	/**
	 * Clear address.
	 *
	 * @param address the address
	 */
	public void clearAddressChange(AddressChange address) {
		address.setPostcode(null);
		address.setAddressLine1(null);
		address.setAddressLine2(null);
		address.setAddressLine3(null);
		address.setAddressLine4(null);
		address.setLongitude(null);
		address.setLatitude(null);
		address.setMunicipality(null);
		address.setTown(null);
	}
	
	public void saveAddresses(AddressChange addr1, AddressChange addr2) throws Exception {
		if (addr1 != null) {
			lookupLongitudeLatitude(addr1);
			if (addr1.getId() == null)
				dao.create(addr1);
			else
				dao.update(addr1);
		}
		if (addr2 != null) {
			lookupLongitudeLatitude(addr2);
			if (addr2.getId() == null)
				dao.create(addr2);
			else
				dao.update(addr2);
		}
	}
	
	public void lookupLongitudeLatitude(AddressChange addr) {
		try {
			String address = addr.getAddressLine1() == null ? ""
					: addr.getAddressLine1() + " " + addr.getAddressLine2() == null ? ""
							: addr.getAddressLine2() + " " + addr.getAddressLine3() == null ? "" : addr.getAddressLine3() + " " + addr.getAddressLine4() == null ? "" : addr.getAddressLine4() + " " + addr.getPostcode() == null ? "" : addr.getPostcode();
			if (address.trim().length() > 3) {
				LatLng ll = GeoCoderUtil.calcLatLng(address.trim());
				if (ll != null) {
					addr.setLatitude(ll.getLat().doubleValue());
					addr.setLongitude(ll.getLng().doubleValue());
				}
			}
		} catch (Exception e) {
			logger.fatal(e);
		}

	}
	
	public List<AddressChange> findByUser(Users user, AddressEnum addressEnum) throws Exception {
		 return dao.findByUser(user.getId(), addressEnum);
	}

	public void requestAddressUpdate(Address residentialAddress, Address postalAddress, Users activeUser, ChangeReason changeReason)throws Exception {
		if (residentialAddress != null) {
			AddressHistoryService.instance().createHistory(residentialAddress.getId());
			AddressService.instance().update(residentialAddress);
		}
		if (postalAddress != null) {
			AddressHistoryService.instance().createHistory(postalAddress.getId());
			AddressService.instance().update(postalAddress);
		}
		AddressChange addressChange=new AddressChange();
		addressChange.setForPostalAddress(postalAddress);
		addressChange.setForResidentialAddress(residentialAddress);
		addressChange.setStatus(ApprovalEnum.PendingApproval);
		addressChange.setUser(activeUser);
		create(addressChange);
		// Adding Change Reason
		ChangeReasonService.instance().createChangeReason(addressChange.getId(), addressChange.getClass().getName(),changeReason);
		String desc = "";
		TasksService.instance().findFirstInProcessAndCreateTask(desc, activeUser, addressChange.getId(), addressChange.getClass().getName(), true, ConfigDocProcessEnum.ADDRESS_CHANGE, MailDef.instance(MailEnum.Task, MailTagsEnum.Task, desc), null);
		sendAddressUpdateAcknowledgeEmail(activeUser);
		
	}
	
	public void sendAddressUpdateAcknowledgeEmail(Users formUser) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy");
		String subject = "ACKNOWLEDGEMENT OF ASSESSOR/MODERATOR INFORMATION AMENDMENT REQUEST";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to amend information submitted on "+sdf.format(new Date())+" is hereby acknowledged."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that the review process may take up to five (5) "
				+ "working days and should any additional information be required, "
				+ "this will be communicated to you."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(formUser.getEmail(), subject, mssg, null);

	}

	public void approveAddressUpdateTask(Tasks task, Users user, AddressChange addresschange) throws Exception {
		addresschange.setStatus(ApprovalEnum.Approved);
		update(addresschange);
		TasksService.instance().completeTask(task);
		sendApproveAddressUpdateEmail(user);
	}
	
	
	public void rejectAddressUpdateTask(Tasks task, Users activeUser, Users user,
			ArrayList<RejectReasons> selectedRejectReason, Address residentialAddressHist, Address postalAddressHist, AddressChange addresschange) throws Exception {
		
		Long tempResAddressID=addresschange.getForResidentialAddress().getId();
		Address ressAddress=addresschange.getForResidentialAddress();
		BeanUtils.copyProperties(ressAddress, residentialAddressHist);
		ressAddress.setId(tempResAddressID);

		Address postAddress=addresschange.getForPostalAddress();
		Long tempPostAddressID=addresschange.getForPostalAddress().getId();
		BeanUtils.copyProperties(postAddress,postalAddressHist);
		postAddress.setId(tempPostAddressID);
		
		AddressService.instance().update(ressAddress);
		AddressService.instance().update(postAddress);
		TasksService.instance().completeTask(task);
		// Creating RejectReason
		List<RejectReasonMultipleSelection> rrmList=RejectReasonMultipleSelectionService.instance().removeCreateLinks(addresschange.getId(), addresschange.getClass().getName(), selectedRejectReason, user, task.getWorkflowProcess());
		for(RejectReasonMultipleSelection rrm:rrmList){
			if(rrm !=null){
				RejectReasonMultipleSelectionService.instance().create(rrm);
			}
		}
		addresschange.setStatus(ApprovalEnum.Rejected);
		update(addresschange);
		rejectAddressUpdateEmail(user,selectedRejectReason);
		
	}
	
	public void  rejectAddressUpdateEmail(Users formUser, ArrayList<RejectReasons> selectedRejectReason) throws Exception {
		String subject = "ASSESSOR/MODERATOR INFORMATION AMENDMENT OUTCOME: NOT APPROVED";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"
                + "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to amend information was not approved for the following reason(s):"
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ ""+convertToHTML(selectedRejectReason)+""
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Please be advised that should you wish to re-submit the request, "
				+ "you must complete the amendment details/information again."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly contact the merSETA Head Office for additional information/assistance."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(formUser.getEmail(), subject, mssg, null);

	}
	
	public void sendApproveAddressUpdateEmail(Users formUser) throws Exception {
		String subject = "ASSESSOR/MODERATOR INFORMATION AMENDMENT OUTCOME: APPROVED";
		String mssg = "<br/>" + "<p  style=\"font-size:11.0pt;\";font-family:\"Arial\">Dear "+formUser.getTitle().getDescription()+" " +formUser.getFirstName() + " " + formUser.getLastName() + ",</p>"

				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "The request to amend information was approved."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly note that the updated information will now be used "
				+ "going forward. Please be advised that should you wish to "
				+ "amend the information again, you must complete "
				+ "the amendment request again."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Kindly contact the merSETA Head Office for additional information/assistance."
				+ "</p>"
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "Yours sincerely,"
				+ "</p>" 
				+ "<p style=\"font-size:11.0pt;\";font-family:\"Arial\">"
				+ "merSETA Administration"
				+ "</p>";
		GenericUtility.sendMail(formUser.getEmail(), subject, mssg, null);

	}
	
	public String convertToHTML(List<RejectReasons> rejectReasons){		
		String sb ="<ul  style=\"font-size:11.0pt;\";font-family:\"Arial\">"; 
		for (RejectReasons r: rejectReasons){
			sb +="<li>"+r.getDescription() +"</li>";
		}
		sb +="</ul>"; 
		return sb;
}

	
}
