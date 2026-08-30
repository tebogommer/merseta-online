package haj.com.service;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import haj.com.bean.AttachmentBean;
import haj.com.dataextract.bean.QCTO01Bean;
import haj.com.dataextract.bean.QCTO02Bean;
import haj.com.dataextract.bean.QCTO03Bean;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;

// TODO: Auto-generated Javadoc
/**
 * The Class DataExtractService.
 */
public class DataExtractService extends AbstractService {

	public static final String QCTO_DATE_FORMAT = "yyyyMMdd";
	public static final String QCTO_DATE_FORMAT_ONLY_YEAR = "yyyy";
	private static final String QCTO_LEARNER_INFORMATION_FILE_TYPE  = "QCTO01";
	private static final String QCTO_KNOWLEDGE_PRACTICAL_SKILLS_AND_WORK_EXPERIENCE_FILE_TYPE  = "QCTO02";
	private static final String QCTO_ASSESSMENT_PARTNER_INFORMATION_FILE_TYPE  = "QCTO03";
	private static final String QCTO_FILE_EXTENSION = ".txt";
	private static final java.text.SimpleDateFormat QCTO_SDF = new java.text.SimpleDateFormat(QCTO_DATE_FORMAT);
	private static final String QCTO_PATH = "/Users/hendrik/Desktop/";//HAJConstants.DATAEXTRACT+"QCTO/";resolveQCTOPath();



	public void  extractQCTO(String qctoSdpCode, String emailTo, Date fromDate, Date toDate) throws Exception {

		//TEST CODE START

			List<QCTO01Bean> qcto01List = new ArrayList<QCTO01Bean>();
//			qcto01List.add(new QCTO01Bean("17","6712245010081",null,"533",new Date(),new Date()));
//			qcto01List.add(new QCTO01Bean("17",null,"A124553","527",new Date(),new Date()));

			List<QCTO02Bean> qcto02List = new ArrayList<QCTO02Bean>();
			qcto02List.add(new QCTO02Bean());


			List<QCTO03Bean> qcto03List = new ArrayList<QCTO03Bean>();
			qcto03List.add(new QCTO03Bean());

		//TEST CODE END

		String qcto01FN = extractQCTOLearnerInformation(qcto01List, qctoSdpCode);
		String qcto02FN = extractQCTOKnowledgePracticalSkillsAndWorkExperience(qcto02List, qctoSdpCode);
		String qcto03FN = extractQCTOAssessmentPartnerInformation(qcto03List, qctoSdpCode);



		 List<AttachmentBean> files = new ArrayList<AttachmentBean>();
		 files.add( new AttachmentBean(qcto01FN,Files.readAllBytes(new File(QCTO_PATH+qcto01FN).toPath()),QCTO_FILE_EXTENSION.substring(1)));
		 files.add( new AttachmentBean(qcto02FN,Files.readAllBytes(new File(QCTO_PATH+qcto02FN).toPath()),QCTO_FILE_EXTENSION.substring(1)));
		 files.add( new AttachmentBean(qcto03FN,Files.readAllBytes(new File(QCTO_PATH+qcto03FN).toPath()),QCTO_FILE_EXTENSION.substring(1)));

		//mail the results as attachements
		sendEmail(qctoSdpCode,emailTo,files);
	}




	private void sendEmail(String qctoSdpCode, String emailTo,  List<AttachmentBean> files ) throws Exception {
		String msg = "<b>Please find attached the extracts for: #CODE#</p><br/>";
		msg+="<p>Regards</p>";
		msg+="<p>The "+qctoSdpCode+" Team</>";
		msg = msg.replaceAll("#CODE#", qctoSdpCode);

		GenericUtility.sendMailWithAttachement(emailTo, "Extract for "+qctoSdpCode,msg,files,null);
		for (AttachmentBean attachmentBean : files) {
			System.out.println(attachmentBean.toString());
		}

	}


	private String  extractQCTOLearnerInformation(List<QCTO01Bean> qcto01List, String qctoSdpCode) throws Exception {
			String fname = QCTO_LEARNER_INFORMATION_FILE_TYPE +"-"+GenericUtility.padRight(qctoSdpCode, 20)+QCTO_SDF.format(new Date())+QCTO_FILE_EXTENSION;
			String data = haj.com.utils.CSVUtil.writeFixedLength(qcto01List);
			writeFile(QCTO_PATH+fname,data);
			return fname;
	}

	private String  extractQCTOKnowledgePracticalSkillsAndWorkExperience(List<QCTO02Bean> qcto02List, String qctoSdpCode) throws Exception {
		String fname =  QCTO_KNOWLEDGE_PRACTICAL_SKILLS_AND_WORK_EXPERIENCE_FILE_TYPE +"-"+GenericUtility.padRight(qctoSdpCode, 20)+QCTO_SDF.format(new Date())+QCTO_FILE_EXTENSION;
		String data = haj.com.utils.CSVUtil.writeFixedLength(qcto02List);
		writeFile(QCTO_PATH+fname,data);
		return fname;
	}

	private String  extractQCTOAssessmentPartnerInformation(List<QCTO03Bean> qcto03List, String qctoSdpCode) throws Exception {
		String fname =  QCTO_ASSESSMENT_PARTNER_INFORMATION_FILE_TYPE +"-"+GenericUtility.padRight(qctoSdpCode, 20)+QCTO_SDF.format(new Date())+QCTO_FILE_EXTENSION;
		//TO DO
		String data = haj.com.utils.CSVUtil.writeFixedLength(qcto03List);
		writeFile(QCTO_PATH+fname,data);
		return fname;

	}

	private void writeFile(String fname, String data) throws Exception{
		Path path = Paths.get(fname);
		Files.write(path, Arrays.asList(data));

	}
}
