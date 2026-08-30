package haj.com.ui;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import haj.com.constants.HAJConstants;
import haj.com.entity.HostingCompanyEmployees;
import haj.com.entity.Users;
import haj.com.entity.enums.IdPassportEnum;
import haj.com.entity.lookup.LegacyAssessorAccreditation;
import haj.com.entity.lookup.LegacyModeratorAccreditation;
import haj.com.framework.AbstractUI;
import haj.com.framework.AbstractUIInterface;
import haj.com.rest.idverification.IdVerificationRealTime;
import haj.com.service.HostingCompanyEmployeesService;
import haj.com.service.UsersService;
import haj.com.service.lookup.LegacyAssessorAccreditationService;
import haj.com.service.lookup.LegacyModeratorAccreditationService;
import haj.com.service.lookup.TitleService;
import haj.com.utils.GenericUtility;
import haj.com.validators.CheckID;

// TODO: Auto-generated Javadoc
/**
 * The Class SearchUserPassportOrIdUI.
 */
@ManagedBean(name = "searchUserPassportOrIdUI")
@ViewScoped
public class SearchUserPassportOrIdUI extends AbstractUI {

	/** The idpassport. */
	private IdPassportEnum idpassport;
	
	/** The idnumber. */
	@CheckID(message="Not a valid RSA ID number")
	private String idnumber;
	
	/** The passport number. */
	private String passportNumber;
	
	/** The object. */
	private AbstractUIInterface object;
	
	/** The users service. */
	private UsersService usersService = new UsersService();
	
	/** The previous selection. */
	private IdPassportEnum previousSelection;

	/** The maximum RSA ID number*/
 	public  Long MAX_RSA_ID_NUMBER = HAJConstants.MAX_RSA_ID_NUMBER;
 	
 	/** The maximum size all for vax number*/
 	public  Long MAX_PASSPORT_NUMBER = HAJConstants.MAX_PASSPORT_NUMBER;
 	
 	public String passportNumberFormat = HAJConstants.passportNumberFormat;
 	
 	private Boolean searchByAssessor;
 	
 	private HostingCompanyEmployeesService hostingCompanyEmployeesService = new HostingCompanyEmployeesService();
	/**
	 * Run init.
	 *
	 * @throws Exception the exception
	 */
	public void runInit() throws Exception {
		clearCriteria();
	}

	/**
	 * Clear criteria.
	 */
	public void clearCriteria() {
		this.idnumber = "";
		this.passportNumber = "";
		this.idpassport = null;
	}

	/**
	 * Find user by ID or passport.
	 *
	 * @throws Exception the exception
	 */
	public void findUserByIDOrPassport() throws Exception {
		Users user = new Users();
		switch (idpassport) {
		case RsaId:
			user = usersService.findByRsaIdJoinAddress(this.idnumber);
			break;
		case Passport:
			user = usersService.findByPassportNumberJoinAddress(this.passportNumber);
			break;
		default:
			user = new Users();
			break;
		}
		if (user == null) {
			user = new Users();
			user.setRsaIDNumber(idnumber);
			user.setPassportNumber(passportNumber);
			user.setExistingUser(false);
			user.setRegFieldsDone(false);
		} else {
			user.setExistingUser(true);
			user.setRegFieldsDone(true);
		}
		if (idpassport == IdPassportEnum.RsaId) {
			try {
				GenericUtility.calcIDData(user);
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
			}
		}
		user.setDoneSearch(true);
		this.previousSelection = idpassport;
		clearCriteria();
		object.callBackMethod(user);
	}
	
	public void findUserByIDOrPassportExcludeEmployeeUsers() throws Exception {
		Users user = new Users();
		switch (idpassport) {
		case RsaId:
			user = usersService.findByRsaIdJoinAddress(this.idnumber);
			break;
		case Passport:
			user = usersService.findByPassportNumberJoinAddress(this.passportNumber);
			break;
		default:
			user = new Users();
			break;
		}
		if (user == null) {
			user = new Users();
			user.setRsaIDNumber(idnumber);
			user.setPassportNumber(passportNumber);
			user.setExistingUser(false);
			user.setRegFieldsDone(false);
		} else {
			// check if user is an employee
			if (hostingCompanyEmployeesService.findByUserCount(user.getId(), HAJConstants.HOSTING_MERSETA) == 0) {
				user.setExistingUser(true);
				user.setRegFieldsDone(true);
			}else {
				user = new Users();
				idpassport = null;
				throw new Exception("MerSETA Employee Information can not be used here!");
			}
		}
		if (idpassport == IdPassportEnum.RsaId) {
			try {
				GenericUtility.calcIDData(user);
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
			}
		}
		user.setDoneSearch(true);
		this.previousSelection = idpassport;
		clearCriteria();
		object.callBackMethod(user);
	}
	
	public void findUserByIDOrPassportValidation() throws Exception {
		Users user = new Users();
		switch (idpassport) {
		case RsaId:
			user = usersService.findByRsaIdJoinAddress(this.idnumber);
			break;
		case Passport:
			user = usersService.findByPassportNumberJoinAddress(this.passportNumber);
			break;
		default:
			user = new Users();
			break;
		}
		
		if (user == null) {
			user = new Users();
			user.setRsaIDNumber(idnumber);
			user.setPassportNumber(passportNumber);
			user.setExistingUser(false);
			user.setRegFieldsDone(false);
		} else {
			user.setExistingUser(true);
			user.setRegFieldsDone(true);
		}
		if (idpassport == IdPassportEnum.RsaId) {			
			try {
				IdVerificationRealTime irt = usersService.idVerificationRealTime(user);		
				user.setValidationStatus(irt.getStatus());
				if(irt != null && irt.getRealTimeResults()!= null) {
					user.setFirstName(irt.getRealTimeResults().getFirstNames());
					user.setLastName(irt.getRealTimeResults().getSurName());
					user.setDeceasedStatus(irt.getRealTimeResults().getDeceasedStatus());
					GenericUtility.calcIDData(user);
				}
			} catch (Exception e) {
				addErrorMessage(e.getMessage(), e);
			}
		}
		user.setDoneSearch(true);
		this.previousSelection = idpassport;
		clearCriteria();
		object.callBackMethod(user);
	}
	
	/**
	 * Find user by ID or passport.
	 *
	 * @throws Exception the exception
	 */
	public void findAssessoModByIDOrPassport() throws Exception {
		
		/*
		 * Search User from Legacy Data 
		 */
		LegacyAssessorAccreditationService legacyAssessorAccreditationService=new LegacyAssessorAccreditationService();
		LegacyModeratorAccreditationService legacyModeratorAccreditationService=new LegacyModeratorAccreditationService();
		
		TitleService titleService=new TitleService();
		String search=idnumber;
		if(search==null || search.isEmpty() || search.equals("")){
			search=passportNumber;
		}
		
		LegacyAssessorAccreditation legacyAssessorAccreditation=null;
		LegacyModeratorAccreditation legacyModeratorAccreditation=null;
		List<LegacyAssessorAccreditation> legacyAssessorAccrList=null;
		List<LegacyModeratorAccreditation> legacyModeratorAccrList=null;
		if(searchByAssessor) {
			/*
			 *  assessor legacy data search by RSAID or passport number
			 */
			// version three
			switch (idpassport) {
			case RsaId:
				legacyAssessorAccrList= legacyAssessorAccreditationService.findRegisteredOrExpiredLegacyAccreditationVersionTwo(search,false);
				break;
			case Passport:
				legacyAssessorAccrList= legacyAssessorAccreditationService.findRegisteredOrExpiredLegacyAccreditationVersionTwoPassport(search,false);
				break;
			default:
				break;
			}
			//version two
//			legacyAssessorAccrList= legacyAssessorAccreditationService.findRegisteredOrExpiredLegacyAccreditationVersionTwo(search,false);
			// version one
//			legacyAssessorAccrList= legacyAssessorAccreditationService.findRegisteredOrExpiredLegacyAccreditation(search,false);
		} else {
			/*
			 *  moderator legacy data search by RSAID or passport number
			 */
			// version three
			switch (idpassport) {
			case RsaId:
				legacyModeratorAccrList = legacyModeratorAccreditationService.findRegisteredOrExpiredLegacyAccreditationVersionTwo(search,false);
				break;
			case Passport:
				legacyModeratorAccrList = legacyModeratorAccreditationService.findRegisteredOrExpiredLegacyAccreditationVersionTwoPassport(search,false);
				break;
			default:
				break;
			}
			// version two
//			legacyModeratorAccrList=legacyModeratorAccreditationService.findRegisteredOrExpiredLegacyAccreditationVersionTwo(search,false);
			// version one
//			legacyModeratorAccrList=legacyModeratorAccreditationService.findRegisteredOrExpiredLegacyAccreditation(search,false);
		}
		
		if(searchByAssessor && (legacyAssessorAccrList==null || legacyAssessorAccrList.size()<1)){
			legacyAssessorAccreditation=new LegacyAssessorAccreditation();
			object.callBackMethod(legacyAssessorAccreditation);
			this.previousSelection = idpassport;
			clearCriteria();
		} else if(!searchByAssessor && (legacyModeratorAccrList==null || legacyModeratorAccrList.size()<1)){
			legacyModeratorAccreditation=new LegacyModeratorAccreditation();
			object.callBackMethod(legacyModeratorAccreditation);
			this.previousSelection = idpassport;
			clearCriteria();
		} else {
			
			Users user = new Users();
			switch (idpassport) {
			case RsaId:
				user = usersService.findByRsaIdJoinAddress(this.idnumber);
				break;
			case Passport:
				user = usersService.findByPassportNumberJoinAddress(this.passportNumber);
				break;
			default:
				user = new Users();
				break;
			}
			
			if(searchByAssessor){
				legacyAssessorAccreditation=legacyAssessorAccrList.get(0);
			} else {
				legacyModeratorAccreditation=legacyModeratorAccrList.get(0);
			}
			
			if (user == null) {
				user = new Users();
				user.setRsaIDNumber(idnumber);
				user.setPassportNumber(passportNumber);
				user.setExistingUser(false);
				user.setRegFieldsDone(false);
				if(searchByAssessor){
					user.setTitle(titleService.findByDescription(legacyAssessorAccreditation.getTitleDesc()));
					user.setFirstName(legacyAssessorAccreditation.getFirstName());
					user.setLastName(legacyAssessorAccreditation.getSurname());
				} else{
					user.setTitle(titleService.findByDescription(legacyModeratorAccreditation.getTitleDesc()));
					user.setFirstName(legacyModeratorAccreditation.getFirstName());
					user.setLastName(legacyModeratorAccreditation.getSurname());
				}
			} else {
				user.setExistingUser(true);
				user.setRegFieldsDone(true);
			}
			if (idpassport == IdPassportEnum.RsaId) {
				try {
					GenericUtility.calcIDData(user);
				} catch (Exception e) {
					addErrorMessage(e.getMessage(), e);
				}
			}
			user.setDoneSearch(true);
			this.previousSelection = idpassport;
			clearCriteria();
			
			if(searchByAssessor) {
				legacyAssessorAccreditation.setUser(user);
				object.callBackMethod(legacyAssessorAccreditation);
			} else {
				legacyModeratorAccreditation.setUser(user);
				object.callBackMethod(legacyModeratorAccreditation);
			}
		}
	}

	/**
	 * Gets the object.
	 *
	 * @return the object
	 */
	public AbstractUIInterface getObject() {
		return object;
	}

	/**
	 * Sets the object.
	 *
	 * @param object the new object
	 */
	public void setObject(AbstractUIInterface object) {
		this.object = object;
		clearCriteria();
	}

	/**
	 * Gets the idpassport.
	 *
	 * @return the idpassport
	 */
	public IdPassportEnum getIdpassport() {
		return idpassport;
	}

	/**
	 * Sets the idpassport.
	 *
	 * @param idpassport the new idpassport
	 */
	public void setIdpassport(IdPassportEnum idpassport) {
		this.idpassport = idpassport;
	}

	/**
	 * Gets the idnumber.
	 *
	 * @return the idnumber
	 */
	public String getIdnumber() {
		return idnumber;
	}

	/**
	 * Sets the idnumber.
	 *
	 * @param idnumber the new idnumber
	 */
	public void setIdnumber(String idnumber) {
		this.idnumber = idnumber;
	}

	/**
	 * Gets the passport number.
	 *
	 * @return the passport number
	 */
	public String getPassportNumber() {
		return passportNumber;
	}

	/**
	 * Sets the passport number.
	 *
	 * @param passportNumber the new passport number
	 */
	public void setPassportNumber(String passportNumber) {
		this.passportNumber = passportNumber;
	}

	/**
	 * Gets the previous selection.
	 *
	 * @return the previous selection
	 */
	public IdPassportEnum getPreviousSelection() {
		return previousSelection;
	}

	/**
	 * Sets the previous selection.
	 *
	 * @param previousSelection the new previous selection
	 */
	public void setPreviousSelection(IdPassportEnum previousSelection) {
		this.previousSelection = previousSelection;
	}

	public Long getMAX_RSA_ID_NUMBER() {
		return MAX_RSA_ID_NUMBER;
	}

	public void setMAX_RSA_ID_NUMBER(Long mAX_RSA_ID_NUMBER) {
		MAX_RSA_ID_NUMBER = mAX_RSA_ID_NUMBER;
	}

	public Long getMAX_PASSPORT_NUMBER() {
		return MAX_PASSPORT_NUMBER;
	}

	public void setMAX_PASSPORT_NUMBER(Long mAX_PASSPORT_NUMBER) {
		MAX_PASSPORT_NUMBER = mAX_PASSPORT_NUMBER;
	}

	public String getPassportNumberFormat() {
		return passportNumberFormat;
	}

	public void setPassportNumberFormat(String passportNumberFormat) {
		this.passportNumberFormat = passportNumberFormat;
	}

	public Boolean getSearchByAssessor() {
		return searchByAssessor;
	}

	public void setSearchByAssessor(Boolean searchByAssessor) {
		this.searchByAssessor = searchByAssessor;
	}

}
