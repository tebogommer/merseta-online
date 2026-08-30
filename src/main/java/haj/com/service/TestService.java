package haj.com.service;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintViolation;

import haj.com.bean.ExceptionBean;
import haj.com.constants.HAJConstants;
import haj.com.dao.TestDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.entity.ExceptionsTable;
import haj.com.entity.Users;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.utils.CSVUtil;
import haj.com.utils.GenericUtility;

public class TestService extends AbstractService {

	private TestDAO dao = new TestDAO();
	public final static String EXPORT_FOLDER = findExportFolder();
	
	private static String findExportFolder() {
		String folder = findProperty("EXPORT_FOLDER");
		if (folder == null) folder = "/home/merseta/data_extracts/";
		return folder;
	}
	
	private static String findProperty(String prop) {
		if (System.getProperties().get("DD-PROPERTIES") != null && ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty(prop) != null) {
			return ((java.util.Properties) System.getProperties().get("DD-PROPERTIES")).getProperty(prop);
		}
		return null;
	}
	
	public void runAll(){
		for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
			GenericUtility.sendMail(emailNotificiations, "SETMIS Validiation Run ALL Service: User, Company, Address START", "Validiation run has started on:" + HAJConstants.PL_LINK, null);
		}
		checkUserValidation();
		checkCompanyValidiation();
		checkAddressValidiation();
		for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
			GenericUtility.sendMail(emailNotificiations, "SETMIS Validiation Run Service: User, Company, Address END", "Validiation run has finished on:" + HAJConstants.PL_LINK, null);
		}
	}

	public void checkUserValidation() {
		List<ExceptionBean> exceptions = new ArrayList<ExceptionBean>();
		List<IDataEntity> createList = new ArrayList<>();
		try {
			List<Users> users = allUsers();
			for (Users user : users) {
				user.setValidiationRanDate(getSynchronizedDate());
				String validationErrors = "";
				try {
					updateEntity(user);
				} catch (javax.validation.ConstraintViolationException e) {
					validationErrors = "";
					for (ConstraintViolation<?> elt : e.getConstraintViolations()) {
						validationErrors += elt.getMessage() + "\n";
					}
					exceptions.add(new ExceptionBean(user.getId(), validationErrors));
					createList.add(new ExceptionsTable(user.getId(), user.getClass().getName(), validationErrors));
				}
			}
			if (!exceptions.isEmpty()) {
//				String csv = CSVUtil.writeCSVNoAnnotaions(exceptions, ",", false);
////				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "UserValidationErrors.csv", "text/csv");
//				PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "UserValidationErrors.csv"));
//				if (csv.length() > 0) writer.write(csv);
//				users = null;
//				writer.close();
			}
			if (!createList.isEmpty()) {
				dao.createBatch(createList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Validiation Run Service: checkUserValidation ERROR", "checkUserValidation has encountered an error on:" + HAJConstants.PL_LINK + ". Error Message: " + e.getMessage(), null);
			}
		}
		exceptions = null;
	}
	
	public void checkCompanyValidiation() {
		List<ExceptionBean> exceptions = new ArrayList<ExceptionBean>();
		List<IDataEntity> createList = new ArrayList<>();
		try {
			List<Company> companyList = allCompany();
			for (Company user : companyList) {
				user.setValidiationRanDate(getSynchronizedDate());
				String validationErrors = "";
				try {
					updateEntity(user);
				} catch (javax.validation.ConstraintViolationException e) {
					validationErrors = "";
					for (ConstraintViolation<?> elt : e.getConstraintViolations()) {
						validationErrors += elt.getMessage() + "\n";
					}
					exceptions.add(new ExceptionBean(user.getId(), validationErrors));
					createList.add(new ExceptionsTable(user.getId(), user.getClass().getName(), validationErrors));
				}
			}
			if (!exceptions.isEmpty()) {
//				String csv = CSVUtil.writeCSVNoAnnotaions(exceptions, ",", false);
////				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "CompanyValidationErrors.csv", "text/csv");
//				PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "CompanyValidationErrors.csv"));
//				if (csv.length() > 0) writer.write(csv);
//				companyList = null;
//				writer.close();
			}
			if (!createList.isEmpty()) {
				dao.createBatch(createList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Validiation Run Service: checkCompanyValidiation ERROR", "checkCompanyValidiation has encountered an error on:" + HAJConstants.PL_LINK + ". Error Message: " + e.getMessage(), null);
			}
		}
		exceptions = null;
	}
	
	public void checkAddressValidiation() {
		List<ExceptionBean> exceptions = new ArrayList<ExceptionBean>();
		List<IDataEntity> createList = new ArrayList<>();
		try {
			List<Address> adressList = allAddress();
			for (Address user : adressList) {
				user.setValidiationRanDate(getSynchronizedDate());
				String validationErrors = "";
				try {
					updateEntity(user);
				} catch (javax.validation.ConstraintViolationException e) {
					validationErrors = "";
					for (ConstraintViolation<?> elt : e.getConstraintViolations()) {
						validationErrors += elt.getMessage() + "\n";
					}
					exceptions.add(new ExceptionBean(user.getId(), validationErrors));
					createList.add(new ExceptionsTable(user.getId(), user.getClass().getName(), validationErrors));
				}
			}
			if (!exceptions.isEmpty()) {
//				String csv = CSVUtil.writeCSVNoAnnotaions(exceptions, ",", false);
////				JasperService.convertByteArrayToServletOutputStream(csv.getBytes(), "AddressValidationErrors.csv", "text/csv");
//				PrintWriter writer = new PrintWriter(new File(EXPORT_FOLDER + "AddressValidationErrors.csv"));
//				if (csv.length() > 0) writer.write(csv);
//				adressList = null;
//				writer.close();
			}
			if (!createList.isEmpty()) {
				dao.createBatch(createList);
			}
		} catch (Exception e) {
			e.printStackTrace();
			for (String emailNotificiations : GenericUtility.processEmailNotfications()) {
				GenericUtility.sendMail(emailNotificiations, "Validiation Run Service: checkAddressValidiation ERROR", "checkAddressValidiation has encountered an error on:" + HAJConstants.PL_LINK + ". Error Message: " + e.getMessage(), null);
			}
		}
		exceptions = null;
	}

	private List<Users> allUsers() throws Exception {
		return dao.allUsers();
	}

	private void updateEntity(IDataEntity dataEntity) throws Exception {
		dao.update(dataEntity);
	}
	
	public List<Company> allCompany() throws Exception {
		return dao.allCompany();
	}
	
	public List<Address> allAddress() throws Exception {
		return dao.allAddress();
	}
}