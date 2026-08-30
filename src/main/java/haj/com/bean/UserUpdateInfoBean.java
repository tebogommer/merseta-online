package haj.com.bean;

/**
 * The Class DiscretionaryGrantBean.
 */
public class UserUpdateInfoBean {
	
	private boolean firstName;
	private boolean middleName;
	private boolean lastName;
	private boolean title;
	private boolean email;
	private boolean rsaIDNumber;
	private boolean passportNumber;
	private boolean ofoCodes;
	private boolean cellNumber;
	private boolean telNumber;
	private boolean nationality;
	private boolean dateOfBirth;
	private boolean gender;
	private boolean equity;
	private boolean lastSchoolYear;
	private boolean previousSchools;
	private boolean disability;
	private boolean employmentStatus;
	private boolean languagesAssigned;
	private boolean disbaitiesAssigned;
	private boolean faxNumber;

	public UserUpdateInfoBean() {
		super();
		firstName = middleName = lastName = title = email = false;
		rsaIDNumber = passportNumber = ofoCodes = cellNumber = telNumber = false;
		nationality = dateOfBirth = gender = equity = lastSchoolYear = false;
		previousSchools = disability = employmentStatus = languagesAssigned = disbaitiesAssigned = false;
		faxNumber = false;
	}

	/* getters and setters */
	public boolean isFirstName() {
		return firstName;
	}

	public void setFirstName(boolean firstName) {
		this.firstName = firstName;
	}

	public boolean isMiddleName() {
		return middleName;
	}

	public void setMiddleName(boolean middleName) {
		this.middleName = middleName;
	}

	public boolean isLastName() {
		return lastName;
	}

	public void setLastName(boolean lastName) {
		this.lastName = lastName;
	}

	public boolean isTitle() {
		return title;
	}

	public void setTitle(boolean title) {
		this.title = title;
	}

	public boolean isEmail() {
		return email;
	}

	public void setEmail(boolean email) {
		this.email = email;
	}

	public boolean isRsaIDNumber() {
		return rsaIDNumber;
	}

	public void setRsaIDNumber(boolean rsaIDNumber) {
		this.rsaIDNumber = rsaIDNumber;
	}

	public boolean isPassportNumber() {
		return passportNumber;
	}

	public void setPassportNumber(boolean passportNumber) {
		this.passportNumber = passportNumber;
	}

	public boolean isOfoCodes() {
		return ofoCodes;
	}

	public void setOfoCodes(boolean ofoCodes) {
		this.ofoCodes = ofoCodes;
	}

	public boolean isCellNumber() {
		return cellNumber;
	}

	public void setCellNumber(boolean cellNumber) {
		this.cellNumber = cellNumber;
	}

	public boolean isTelNumber() {
		return telNumber;
	}

	public void setTelNumber(boolean telNumber) {
		this.telNumber = telNumber;
	}

	public boolean isNationality() {
		return nationality;
	}

	public void setNationality(boolean nationality) {
		this.nationality = nationality;
	}

	public boolean isDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(boolean dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public boolean isGender() {
		return gender;
	}

	public void setGender(boolean gender) {
		this.gender = gender;
	}

	public boolean isEquity() {
		return equity;
	}

	public void setEquity(boolean equity) {
		this.equity = equity;
	}

	public boolean isLastSchoolYear() {
		return lastSchoolYear;
	}

	public void setLastSchoolYear(boolean lastSchoolYear) {
		this.lastSchoolYear = lastSchoolYear;
	}

	public boolean isPreviousSchools() {
		return previousSchools;
	}

	public void setPreviousSchools(boolean previousSchools) {
		this.previousSchools = previousSchools;
	}

	public boolean isDisability() {
		return disability;
	}

	public void setDisability(boolean disability) {
		this.disability = disability;
	}

	public boolean isEmploymentStatus() {
		return employmentStatus;
	}

	public void setEmploymentStatus(boolean employmentStatus) {
		this.employmentStatus = employmentStatus;
	}

	public boolean isLanguagesAssigned() {
		return languagesAssigned;
	}

	public void setLanguagesAssigned(boolean languagesAssigned) {
		this.languagesAssigned = languagesAssigned;
	}

	public boolean isDisbaitiesAssigned() {
		return disbaitiesAssigned;
	}

	public void setDisbaitiesAssigned(boolean disbaitiesAssigned) {
		this.disbaitiesAssigned = disbaitiesAssigned;
	}

	public boolean isFaxNumber() {
		return faxNumber;
	}

	public void setFaxNumber(boolean faxNumber) {
		this.faxNumber = faxNumber;
	}
	
}