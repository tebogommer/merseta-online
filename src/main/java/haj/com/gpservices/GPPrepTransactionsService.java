package haj.com.gpservices;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import javax.xml.datatype.XMLGregorianCalendar;

import com.microsoft.schemas.dynamics._2006._01.CompanyKey;
import com.microsoft.schemas.dynamics.gp._2006._01.ArrayOfGLTransactionLine;
import com.microsoft.schemas.dynamics.gp._2006._01.ArrayOfVendorAddress;
import com.microsoft.schemas.dynamics.gp._2006._01.BatchKey;
import com.microsoft.schemas.dynamics.gp._2006._01.GLAccountNumberKey;
import com.microsoft.schemas.dynamics.gp._2006._01.GLTransaction;
import com.microsoft.schemas.dynamics.gp._2006._01.GLTransactionKey;
import com.microsoft.schemas.dynamics.gp._2006._01.GLTransactionLine;
import com.microsoft.schemas.dynamics.gp._2006._01.GLTransactionLineKey;
import com.microsoft.schemas.dynamics.gp._2006._01.MoneyAmount;
import com.microsoft.schemas.dynamics.gp._2006._01.PhoneNumber;
import com.microsoft.schemas.dynamics.gp._2006._01.Vendor;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorAddress;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorAddressKey;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorClassKey;
import com.microsoft.schemas.dynamics.gp._2006._01.VendorKey;

import haj.com.dao.SarsFilesDAO;
import haj.com.dao.SarsLevyDetailsDAO;
import haj.com.entity.Address;
import haj.com.entity.Company;
import haj.com.framework.AbstractService;
import haj.com.framework.IDataEntity;
import haj.com.sars.SARSConstants;
import haj.com.sars.SarsFiles;
import haj.com.sars.SarsLevyDetails;
import haj.com.service.CompanyService;
import haj.com.service.JAXBService;
import haj.com.service.SupportEmailsService;
import haj.com.service.lookup.GPCreditorsService;
import haj.com.utils.GenericUtility;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.GPRequestHandler;
import za.co.merseta.nsdms.externalsystems.greatplains.dynamicgp.adapter.CreateVendorAdapter;

// TODO: Auto-generated Javadoc
/**
 * The Class GPPrepTransactionsService.
 */
public class GPPrepTransactionsService extends AbstractService {

	/** The levy DAO. */
	private SarsLevyDetailsDAO levyDAO = new SarsLevyDetailsDAO();
	
	/** The chamber code map. */
	private Map<String,Integer> chamberCodeMap = initChamberCodeMap();
	
	/** The zero amount. */
	private  MoneyAmount zeroAmount = new MoneyAmount(BigDecimal.valueOf(0.00));
	
	/** The sars files DAO. */
	private SarsFilesDAO sarsFilesDAO  = new  SarsFilesDAO();
	
	/** The company service. */
	private CompanyService  companyService = new CompanyService();
	
	/** The gp creditors service. */
	private GPCreditorsService gpCreditorsService = new GPCreditorsService();
	
	private SupportEmailsService supportEmailsService = new SupportEmailsService();
	/**
	 * Inits the chamber code map.
	 *
	 * @return the map
	 */
	private Map<String, Integer> initChamberCodeMap() {
		Map<String, Integer> m = new HashMap<String, Integer>();
		m.put("Auto", 10);
		m.put("Metal", 20);
		m.put("Motor", 30);
		m.put("New Tyre", 40);
		m.put("Plastic", 50);
		m.put("Unknown", 99);
		m.put("ACM", 25);
		return m;
	}


	/**
	 * Gp levy for month.
	 *
	 * @param sarsFileId the sars file id
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<SarsLevyDetails> gpLevyForMonth(Integer sarsFileId)  throws Exception { 
		List<SarsLevyDetails> result = levyDAO.gpLevyForMonth(sarsFileId);  
		result.forEach(a->{
			a.setChamberCode(chamberCodeMap.get(a.getChamber().trim()));
			a.setLevyAmountD(a.getMandatoryLevyD()+a.getDiscretionaryLevyD()+a.getAdminLevyD());
		});
		return result;
	}
	
	
	


	/**
	 * Group GP levy per year for month.
	 *
	 * @param sarsFileId the sars file id
	 * @return the map
	 * @throws Exception the exception
	 */
	public Map<Integer,List<SarsLevyDetails>> groupGPLevyPerYearForMonth(Integer sarsFileId)  throws Exception {  
		Map<Integer,List<SarsLevyDetails>> tm  = new TreeMap<Integer,List<SarsLevyDetails>>();
		gpLevyForMonth(sarsFileId).forEach(a-> {
			if (!tm.containsKey(a.getSchemeYear())) {
				tm.put(a.getSchemeYear(), new ArrayList<SarsLevyDetails>());
			}
			tm.get(a.getSchemeYear()).add(a);
		});
		return tm;
	}

	/**
	 * Prepare transaction.
	 *
	 * @param sarsFileId the sars file id
	 * @throws Exception the exception
	 */
	public void  prepareTransaction(Integer sarsFileId)  throws Exception {  
		Map<Integer,List<SarsLevyDetails>> tm = groupGPLevyPerYearForMonth(sarsFileId);
		for (Entry <Integer,List<SarsLevyDetails>> e : tm.entrySet()) {
				BigDecimal levyT =  BigDecimal.valueOf(e.getValue().parallelStream().map(SarsLevyDetails::getLevyAmountD).mapToDouble(Double::doubleValue).sum());
				BigDecimal interestT = BigDecimal.valueOf(e.getValue().parallelStream().map(SarsLevyDetails::getInterestD).mapToDouble(Double::doubleValue).sum());
				BigDecimal penaltyT = BigDecimal.valueOf(e.getValue().parallelStream().map(SarsLevyDetails::getPenaltyD).mapToDouble(Double::doubleValue).sum());
				BigDecimal total =  BigDecimal.valueOf(levyT.doubleValue() +interestT.doubleValue()+penaltyT.doubleValue());
				System.out.println(e.getKey() + "\t"+total.toPlainString());
		}  


		
		

	}

	/**
	 * Calc batch total.
	 *
	 * @param e the e
	 * @return the big decimal
	 */
	private BigDecimal calcBatchTotal(List<SarsLevyDetails> e) {
		BigDecimal levyT =  BigDecimal.valueOf(e.parallelStream().map(SarsLevyDetails::getLevyAmountD).mapToDouble(Double::doubleValue).sum());
		BigDecimal interestT = BigDecimal.valueOf(e.parallelStream().map(SarsLevyDetails::getInterestD).mapToDouble(Double::doubleValue).sum());
		BigDecimal penaltyT = BigDecimal.valueOf(e.parallelStream().map(SarsLevyDetails::getPenaltyD).mapToDouble(Double::doubleValue).sum());
		BigDecimal total =  BigDecimal.valueOf(levyT.doubleValue() +interestT.doubleValue()+penaltyT.doubleValue());
		return total;
	}
	
	/**
	 * Creates the GL transaction.
	 *
	 * @param sarsFileId the sars file id
	 * @param journalId 
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<GLTransaction> createGLTransaction(Integer sarsFileId,Date postDate) throws Exception {
		SarsFiles sarsFiles = sarsFilesDAO.findByKey(sarsFileId.longValue());
		Map<Integer,List<SarsLevyDetails>> tm = groupGPLevyPerYearForMonth(sarsFileId);
		List<GLTransaction> tl = new ArrayList<GLTransaction>();
			Date start = GenericUtility.getStartOfDay(postDate);
		XMLGregorianCalendar date  = JAXBService.convertDateToXml(start);
		int seq = 1;
		BatchKey batchKey = new BatchKey();
			//	batchKey =	new BatchKey("LPTRN"+e.getKey()+seq+String.format("%04d", sarsFileId),date);
				batchKey =	new BatchKey();
				batchKey.setSource("MIS-System");
				batchKey.setId("LMIS"+String.format("%011d", sarsFileId));
		for (Entry <Integer,List<SarsLevyDetails>> e : tm.entrySet()) {
			String seg2 = String.format("%02d", (e.getKey()+1)-2000) ; 
			
			GLTransaction transaction = new GLTransaction();
			//transaction.setKey(new GLTransactionKey(journalId,date));
			transaction.setKey(new GLTransactionKey(null,date));
			transaction.setBatchKey(batchKey);
			transaction.setReference("Levy for "+SARSConstants.sdf3.format(sarsFiles.getForMonth()) + "-"+sarsFileId+ " (" + e.getKey() +")");
			transaction.setLines(createTransactionLine(e,sarsFileId,seg2,transaction.getKey(),seq));
			tl.add(transaction);
			seq++;
		}
		return tl;
		
	}

	/**
	 * Creates the transaction line.
	 *
	 * @param e the e
	 * @param sarsFileId the sars file id
	 * @param seg2 the seg 2
	 * @param transactionKey the transaction key
	 * @param seq the seq
	 * @return the array of GL transaction line
	 */
	private ArrayOfGLTransactionLine createTransactionLine(Entry<Integer, List<SarsLevyDetails>> e, Integer sarsFileId,String seg2, GLTransactionKey transactionKey, int seq) {
		ArrayOfGLTransactionLine ta = new ArrayOfGLTransactionLine();
		int sequenceNumber = (seq*100);
		for (SarsLevyDetails sl : e.getValue()) {
			// 3019 levey total
			if (sl.getLevyAmountD()!=0) {
				GLTransactionLine t3019 = new GLTransactionLine(new GLTransactionLineKey(transactionKey, sequenceNumber));
				t3019.setGLAccountKey(new GLAccountNumberKey(sl.getChamberCode()+"-"+seg2+"-3019"));
				populateDebitCredit(t3019, sl.getLevyAmountD());
				ta.getGLTransactionLine().add(t3019);
				sequenceNumber++;
			}
			// 3021
			if (sl.getInterestD()!=0) {
				GLTransactionLine t3021 = new GLTransactionLine(new GLTransactionLineKey(transactionKey, sequenceNumber));
				t3021.setGLAccountKey(new GLAccountNumberKey(sl.getChamberCode()+"-"+seg2+"-3021"));
				populateDebitCredit(t3021, sl.getInterestD());
				ta.getGLTransactionLine().add(t3021);
				sequenceNumber++;
			}
			// 3020
			if (sl.getPenaltyD()!=0) {
				GLTransactionLine t3020 = new GLTransactionLine(new GLTransactionLineKey(transactionKey, sequenceNumber));
				t3020.setGLAccountKey(new GLAccountNumberKey(sl.getChamberCode()+"-"+seg2+"-3020"));
				populateDebitCredit(t3020, sl.getPenaltyD());
				ta.getGLTransactionLine().add(t3020);
				sequenceNumber++;
			}
		}
			// 1601
			Double controlTotal = calcBatchTotal(e.getValue()).doubleValue() * -1;
			GLTransactionLine t1601 = new GLTransactionLine(new GLTransactionLineKey(transactionKey, sequenceNumber));
			t1601.setGLAccountKey(new GLAccountNumberKey("00-00-1601"));
			populateDebitCredit(t1601, controlTotal);
			ta.getGLTransactionLine().add(t1601);
			
		return ta;
	}

	/**
	 * Populate debit credit.
	 *
	 * @param transactionLine the transaction line
	 * @param amount the amount
	 */
	private void populateDebitCredit(GLTransactionLine transactionLine,Double amount) {
		MoneyAmount moneyAmount = null;
		if (amount < 0) {
			moneyAmount =  new MoneyAmount(GenericUtility.roundToPrecision(  BigDecimal.valueOf( (amount*-1)),2));
			transactionLine.setCreditAmount(moneyAmount);
			transactionLine.setDebitAmount(zeroAmount);
		}
		else {
			moneyAmount = new MoneyAmount(GenericUtility.roundToPrecision(  BigDecimal.valueOf(amount),2));
			transactionLine.setDebitAmount(moneyAmount);
			transactionLine.setCreditAmount(zeroAmount);
		}
	}
	
	
	public  void createCreditorOnGp(Company comp) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					createOnGp(comp);
				} catch (Exception e) {
					supportEmailsService.reportError("Create Vendo on GP failed", 
							"<p>Error when creating Company with <b>id="+comp.getId()+"</b></p>"
							, e);
					logger.fatal(e);
				}
			}
		}).start();
	}
	
	
	/**
	 * Creates the creditor on gp.
	 *
	 * @param comp the comp
	 * @throws Exception the exception
	 */
	private void createOnGp(Company comp) throws Exception {
		comp = companyService.findByKey(comp.getId());
		if (comp.getSicCode() == null || comp.getSicCode().getChamber() == null || comp.getSicCode().getChamber().getGpVendorClass() == null) throw new Exception("SIC Code / Chamber not configured correctly");
		CompanyKey companyKey = new  CompanyKey(GPRequestHandler.MERSETA_COMPANY_ID);
		Vendor vendor = new Vendor(new VendorKey(comp.getLevyNumber()));
		vendor.getKey().setCompanyKey(companyKey);
		vendor.setName(GenericUtility.limitLength(comp.getCompanyName(), 64));
		vendor.setClassKey(new VendorClassKey(comp.getSicCode().getChamber().getGpVendorClass().getGPName()));
		vendor.getClassKey().setCompanyKey(companyKey);
		ArrayOfVendorAddress addresses = new ArrayOfVendorAddress();
		addresses.getVendorAddress().add(crtAddress(comp,comp.getPostalAddress(),GPAddressTypeEnum.POSTAL.getGPName(), vendor.getKey()));
		addresses.getVendorAddress().add(crtAddress(comp,comp.getResidentialAddress(),GPAddressTypeEnum.PHYSICAL.getGPName(), vendor.getKey()));
        vendor.setAddresses(addresses);
		CreateVendorAdapter adapter= new CreateVendorAdapter();
		adapter.createVendor(vendor);
		gpCreditorsService.create(comp);
		
	}
	
	
	/**
	 * Crt address.
	 *
	 * @param comp the comp
	 * @param addr the addr
	 * @param addrType the addr type
	 * @param vendorKey the vendor key
	 * @return the vendor address
	 */
	private VendorAddress crtAddress(Company comp, Address addr, String addrType, VendorKey vendorKey) {
		VendorAddress address = new VendorAddress(new VendorAddressKey(vendorKey, addrType ));
		address.getKey().setCompanyKey(vendorKey.getCompanyKey());
		if (addr !=null) {
			address.setCity(GenericUtility.limitLength(addr.getTown().getDescription(),35));
			address.setLine1(addr.getAddressLine1()==null?"":GenericUtility.limitLength(addr.getAddressLine1(),60));
			address.setLine2(addr.getAddressLine2()==null?"":GenericUtility.limitLength(addr.getAddressLine2(),60));
			address.setLine3(addr.getAddressLine3()==null?"":GenericUtility.limitLength(addr.getAddressLine3(),60));
			address.setState(GenericUtility.limitLength(addr.getMunicipality().getMunicipalityDesc(),29));
			address.setPostalCode(addr.getPostcode()==null?"":GenericUtility.limitLength(addr.getPostcode(),10));
		}
		address.setName(GenericUtility.limitLength(comp.getCompanyName(),64));
		address.setCountryRegion("ZA");
		if (comp.getTelNumber()!=null) {
			address.setPhone1(new PhoneNumber(comp.getTelNumber().trim().replaceAll(" ", "")));
		}
		if (comp.getFaxNumber()!=null) {
			address.setFax(new PhoneNumber(comp.getFaxNumber().trim().replaceAll(" ", "")));
		}
		return address;
	}
	
	
	
	/**
	 * Creates the InterSeta In transaction.
	 *
	 * @param sarsFileId the sars file id
	 * @param journalId 
	 * @return the list
	 * @throws Exception the exception
	 */
	public List<GLTransaction> createISITransaction(Integer sarsFileId, int journalId, Date postDate) throws Exception {
		//SarsFiles sarsFiles = sarsFilesDAO.findByKey(sarsFileId.longValue());
		//Map<Integer,List<SarsLevyDetails>> tm = groupGPLevyPerYearForMonth(sarsFileId);
		List<GLTransaction> tl = new ArrayList<GLTransaction>();
		Date start = GenericUtility.getStartOfDay(postDate);
		XMLGregorianCalendar date  = JAXBService.convertDateToXml(start);
		int seq = 1;
		BatchKey batchKey = new BatchKey();
			//	batchKey =	new BatchKey("LPTRN"+e.getKey()+seq+String.format("%04d", sarsFileId),date);
				batchKey.setSource("MIS-System");
				//Description = Transfer IN from List [List Number]
				batchKey.setId("TRFIN" + String.format("%011d", 12)); // do we need to pad the number with zeroes ? [List Number]

//		for (Entry <Integer,List<SarsLevyDetails>> e : tm.entrySet()) {
//			journalId ++;
//			String seg2 = String.format("%02d", (e.getKey()+1)-2000) ; 
//			
//			GLTransaction transaction = new GLTransaction();
//			//transaction.setKey(new GLTransactionKey(journalId,date));
//			transaction.setKey(new GLTransactionKey(null,date));
//			transaction.setBatchKey(batchKey);
//			transaction.setReference("Levy for "+SARSConstants.sdf3.format(sarsFiles.getForMonth()) + "-"+sarsFileId+ " (" + e.getKey() +")");
//			transaction.setLines(createTransactionLine(e,sarsFileId,seg2,transaction.getKey(),seq));
//			tl.add(transaction);
//			seq++;
//		}
		return tl;
	}


	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println("START");
		try {
			GPPrepTransactionsService s =  new GPPrepTransactionsService();
			
	/*		
			s.groupGPLevyPerYearForMonth(182).forEach((k,v) -> {
				System.out.println(k);
				v.forEach(a-> {
					System.out.println("\tSchemeYear: "+a.getSchemeYear() + "\tChamber: "+ a.getChamber()+ "("+a.getChamberCode()+")"+
							"\tMandatoryLev: "+ a.getMandatoryLevyD()+ "\tDiscretionaryLevy: "+ a.getDiscretionaryLevyD()+ "\tLevyAmout: "+ a.getLevyAmountD());
				});
			});
	*/		
/*			s.gpLevyForMonth(182).forEach(a-> {
				System.out.println("SchemeYear: "+a.getSchemeYear() + "\tChamber: "+ a.getChamber()+ "("+a.getChamberCode()+")"+
						"\tMandatoryLev: "+ a.getMandatoryLevyD()+ "\tDiscretionaryLevy: "+ a.getDiscretionaryLevyD()+ "\tTotal: "+ a.getTotalD());
			});
*/			
	/*		s.createGLTransaction(182).forEach(a->{
				try {
					System.out.println(JAXBService.marshallToString(a));
				} catch (Exception e) {
					e.printStackTrace();
				}

			});
			*/
			//s.prepareTransaction(182);
			Company comp = new Company();
			comp.setId(7l);
		    s.createCreditorOnGp(comp);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("DONE");
		System.exit(0);

	}
	
	public void updateDataEntity(IDataEntity iDataEntity){
		try {
			levyDAO.create(iDataEntity);
		} catch (Exception e) {
			logger.fatal(e);
		}
	}
}
