package haj.com.datatakeon;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import haj.com.entity.Tempx;
import haj.com.entity.datatakeon.TS1;
import haj.com.entity.datatakeon.TS2;
import haj.com.framework.IDataEntity;
import haj.com.service.TS1Service;
import haj.com.service.TS2Service;
import haj.com.utils.CSVUtil;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class MandatoryGrantsRecon.
 */
public class MandatoryGrantsRecon {

	/** The ts 2 service. */
	private TS2Service ts2Service = new TS2Service();
	
	/** The ts 1 service. */
	private TS1Service ts1Service = new TS1Service();
	
	/** The sdf. */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
	
	/** The sdf 2. */
	private SimpleDateFormat sdf2 =   new SimpleDateFormat("dd-MMM-yy");
	
	/** The sdf 3. */
	private SimpleDateFormat sdf3 =   new SimpleDateFormat("yyy/MM/dd");
	public CSVUtil csvUtil = new CSVUtil();

	
	
	/**
	 * Convert dates and amounts.
	 */
	public void convertDatesAndAmounts(List<TS2> l) {
		try {
			//List<TS2> l = ts2Service.allTS2();
			System.out.println(l.size());
			List<IDataEntity> entityList =  new ArrayList<IDataEntity>();
			int  i = 0,total=0;;
			for (TS2 ts2 : l) {
			 try {	ts2.setDocumentDateD(sdf.parse(ts2.getDocumentDate().trim())); } catch (Exception e) {};
		//	 try {		ts2.setPostedDateD(sdf.parse(ts2.getPostedDate().trim())); } catch (Exception e) {};
		//	 try {		ts2.setPostingDateD(sdf.parse(ts2.getPostingDate().trim())); } catch (Exception e) {};
			 
			 
			 if (ts2.getDocumentAmount().contains("(")) ts2.setDocumentType("Return");
			 else ts2.setDocumentType("Invoice");
			 try {		ts2.setCurrentTrxAmountD(GenericUtility.convertStringtoDouble(ts2.getCurrentTrxAmount())); } catch (Exception e) {};
			 try {		ts2.setDocumentAmountD(GenericUtility.convertStringtoDouble(ts2.getDocumentAmount())); } catch (Exception e) {};
			 if (ts2.getDocumentType() == null ) {
				 System.out.println(ts2.getId() +" unknown DocumentType()" );
			 }
  		    else {  if ("Return".equals(ts2.getDocumentType().trim())) {
			    	    if (ts2.getDocumentAmountD()!=null) {
			    	    	 try {	  ts2.setDocumentAmountD(ts2.getDocumentAmountD() * -1.0); } catch (Exception e) {};
			    	    }
		    	}
			 }
 		    //ts2Service.update(ts2);
			    entityList.add(ts2);
			    i++;total++;
			    if (i==30000) {
			       	ts2Service.getDao().createBatch(entityList);
			       	entityList.clear();
			       	i=0;
			       	System.out.println("Done "+total);
			       
			    }
			}
			ts2Service.getDao().createBatch(entityList);
		 	System.out.println("Complete and total = "+total);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Convert dates and amounts temp X.
	 */
	public void convertDatesAndAmountsTempX() {
		try {
			System.out.println("START");
			List<Tempx> l = ts2Service.allTempx();
			List<IDataEntity> crt = new ArrayList<IDataEntity>();
			List<IDataEntity> upd = new ArrayList<IDataEntity>();
			TS2 ts2 =null;
			System.out.println("Done READING: recs:"+l.size());
			int i =1,tot=1;
			for (Tempx t : l) {
				ts2 = new TS2();
				ts2.setDocumentDateD(sdf2.parse(t.getTransactionDate().trim()));
				try {
					ts2.setPostedDateD(sdf2.parse(t.getPostingDate().trim()));
				} catch (java.text.ParseException e) {
					try {
						ts2.setPostedDateD(sdf3.parse(t.getPostingDate().trim()));
					} catch (Exception e2) {
						// TODO: handle exception
					}
				} 
				ts2.setCurrentTrxAmountD(GenericUtility.convertStringtoDouble(t.getCurrentTransactionAmount()));
				ts2.setDocumentAmountD(GenericUtility.convertStringtoDouble(t.getMainAccountValue6()));
			    if ("Return".equals(t.getDocumentType().trim())) {
			    	    if (ts2.getDocumentAmountD()!=null) {
			    	    	    if (ts2.getDocumentAmountD() > 0) {
			    	    	      ts2.setDocumentAmountD(ts2.getDocumentAmountD() * -1.0);
			    	    	    }
			    	    }
			    	    if (ts2.getCurrentTrxAmountD()!=null) {
			    	    	  if (ts2.getCurrentTrxAmountD()  > 0) {
		    	    	          ts2.setCurrentTrxAmountD(ts2.getCurrentTrxAmountD() * -1.0);
			    	    	  }
		    	        }
			    	}
			    ts2.setVendorId(t.getSdlnumber());
			    ts2.setSchemeYear(t.getLevyYear3());
			    ts2.setVoucherNumber(t.getRefVoucherNumber4());
			    ts2.setDocumentNumber(t.getDocumentNumber12());
			    ts2.setTrxSource(t.getTrxSource());
			    ts2.setChamber(t.getChamber());
			    ts2.setTransactionDescription(t.getTransactionDescription());
			    ts2.setNewfile(true);
			    t.setConverted(Boolean.TRUE);
			    crt.add(ts2);
			    upd.add(t);
			    
			    if (i==10000) {
			    	   i=1;
						System.out.println("Done :"+tot);
						ts2Service.getDao().createBatch(crt);
						ts2Service.getDao().updateBatch(upd);
						crt.clear();
						upd.clear();
			    }
			    i++;tot++;
			}
			System.out.println("TOTAL "+tot);
			ts2Service.getDao().createBatch(crt);
			ts2Service.getDao().updateBatch(upd);
			System.out.println("DONE");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadCSV() {
		try {
			String ts2String = new String (Files.readAllBytes(new File("/Users/hendrik/Downloads/SARS/t_s2.csv").toPath()),Charset.forName("UTF-8"));
			System.out.println("Done reading file");
			@SuppressWarnings("unchecked")
			List<TS2> l = (List<TS2>) (List<?>) csvUtil.getObjects(TS2.class, new ByteArrayInputStream(ts2String.getBytes()), ";");
			System.out.println("Done parsing:" +l.size());
			convertDatesAndAmounts(l);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Resolve foreign keys.
	 */
	public void resolveForeignKeys() {
		try {
			Map<String,TS1> m = new HashMap<String,TS1>();
			List<TS1> l =	ts1Service.allTS1();
			for (TS1 ts1 : l) {
				m.put(ts1.getLevyNumber().trim()+"_"+ts1.getSchemeYear(), ts1);
			}
			
			List<TS2> l2 = ts2Service.allTS2();
			for (TS2 ts2 : l2) { 
				ts2.setTs1(m.get(ts2.getVendorId().trim()+"_"+ts2.getSchemeYear()));
				ts2Service.update(ts2);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * The main method.
	 *
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		System.out.println("Start");
		 MandatoryGrantsRecon recon = new MandatoryGrantsRecon();
		// recon.convertDatesAndAmountsTempX();
		// recon.convertDatesAndAmounts();
		   recon.loadCSV();	

		 System.out.println("Done");
		 System.exit(0);
	}

}
