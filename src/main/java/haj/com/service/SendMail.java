package haj.com.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.util.ByteArrayDataSource;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import haj.com.bean.AttachmentBean;
import haj.com.constants.HAJConstants;
import haj.com.entity.MailLog;
import haj.com.framework.AbstractService;
import haj.com.utils.GenericUtility;
import za.co.merseta.nsdms.framework.configuration.NSDMSConfiguration;

// TODO: Auto-generated Javadoc
/**
 * The Class SendMail.
 */
public class SendMail extends AbstractService {

	// old mail server information
	private String mailServer;
	private String uid;
	private String pwd;
	private boolean useExchange = true;
	private int port = 587;

	/**
	 * Zip files.
	 *
	 * @param files
	 *              the files
	 * @return the string
	 * @throws Exception
	 *                   the exception
	 */

	public String zipFiles(List<String> files) throws Exception {
		byte[] buf = new byte[20480];
		String filename = "pn_" + new java.util.Date().getTime() + ".zip";
		String outFilename = HAJConstants.DOC_PATH + filename;
		ZipOutputStream out = new ZipOutputStream(new FileOutputStream(outFilename));
		for (String f : files) {
			FileInputStream in = new FileInputStream(f);
			// Add ZIP entry to output stream.

			out.putNextEntry(new ZipEntry(GenericUtility.convertFileName(f)));

			// Transfer bytes from the file to the ZIP file
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}

			// Complete the entry
			out.closeEntry();
			in.close();
		}

		// Complete the ZIP file
		out.close();

		return filename;
	}

	/**
	 * Creates the html.
	 *
	 * @param email
	 *              the email
	 * @param body
	 *              the body
	 * @param logo
	 *              the logo
	 * @return the string
	 */
	private static String createHtml(HtmlEmail email, String body, String logo) {
		String html = "";
		html = HAJConstants.EMAIL_TEMPLATE;
		if (logo == null)
			logo = "logo1.png";
		html = html.replace("#LOGO#", logo);
		html = html.replace("#BODY#", body);
		return html;
	}

	/**
	 * Send mail commons gmail.
	 *
	 * @param to_address
	 *                   the to address
	 * @param subject
	 *                   the subject
	 * @param text
	 *                   the text
	 * @param mailLog
	 *                   the mail log
	 * @param logo
	 *                   the logo
	 */
	public synchronized void sendMailCommons(String to_address, String subject, String text, MailLog mailLog,
			String logo) {
		// commneted by Hitesh
		try {
			HtmlEmail email = new HtmlEmail();
			setupEmail(to_address, email);
			System.out.println("mail setup complete to : " + to_address + "ëmail : " + email);
			email.setSubject(subject);
			System.out.println("subject set");
			email.setHtmlMsg(createHtml(email, text, logo));
			System.out.println("html message");
			email.send();
			System.out.println("Mail successfully sent");

			if (mailLog != null) {
				MailLogService.update(mailLog);
			}
		} catch (Exception e) {
			MailLogService.update(mailLog, e);
			logger.fatal(e);
		}

	}

	private void setupEmail(String to_address, HtmlEmail email) throws EmailException {

		mailServer = NSDMSConfiguration.getString("nsdms.mail.server.host");
		uid = NSDMSConfiguration.getString("nsdms.mail.server.user");
		useExchange = NSDMSConfiguration.getBoolean("nsdms.mail.server.isexchange");
		pwd = NSDMSConfiguration.getString("nsdms.mail.server.password");
		
		if (useExchange) {
			email.setHostName(mailServer);
			// email.setSmtpPort(443);
			email.setAuthenticator(new DefaultAuthenticator(uid, pwd));
			// email.setAuthentication(uid2, pwd);
			email.setDebug(HAJConstants.MAIL_DEBUG);
			email.addTo(to_address);
			email.setFrom(uid);
		} else {
			email.setHostName(mailServer);
			email.setSmtpPort(port);
			email.setAuthenticator(new DefaultAuthenticator(uid, pwd));
			email.setStartTLSRequired(true);
			email.setDebug(HAJConstants.MAIL_DEBUG);
			email.addTo(to_address);
			email.setFrom(uid);
			System.out.println("Aaaaaa2");
		}
	}

	// private void setupEmail(String to_address, HtmlEmail email) throws
	// EmailException {
	// if (useExchange) {
	// email.setHostName(mailServer);
	// // email.setSmtpPort(443);
	// email.setAuthenticator(new DefaultAuthenticator(uid, pwd));
	// // email.setAuthentication(uid2, pwd);
	// email.setDebug(HAJConstants.MAIL_DEBUG);
	// email.addTo(to_address);
	// email.setFrom(uid);
	// } else {
	// email.setHostName(mailServer);
	// email.setSmtpPort(port);
	// email.setAuthenticator(new DefaultAuthenticator(uid, pwd));
	// email.setStartTLSRequired(true);
	// email.setDebug(HAJConstants.MAIL_DEBUG);
	// email.addTo(to_address);
	// email.setFrom(uid);
	//
	// }
	// }

	public void tryNEwEmail(String to) {
		Properties props = new Properties();
		props.put("mail.smtp.auth", "false");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", mailServer);
		// props.put("mail.smtp.port", "443");

		Session session = Session.getInstance(props, null);

		try {
			Message message = new MimeMessage(session);

			message.setFrom(new InternetAddress(uid));

			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

			message.setSubject("Test Email");

			message.setContent("This is a test email", "text/html");

			Transport transport = session.getTransport("smtp");
			transport.connect(mailServer, uid, pwd);
			transport.sendMessage(message, message.getAllRecipients());
			transport.close();

			System.out.println("The test message was sent!");
		} catch (MessagingException e) {
			System.out.println("Error: " + e);
			e.printStackTrace();
			throw new RuntimeException(e);
		}

	}

	/**
	 * Send mail commons gmail with attachement.
	 *
	 * @param to_address
	 *                   the to address
	 * @param subject
	 *                   the subject
	 * @param text
	 *                   the text
	 * @param attachment
	 *                   the attachment
	 * @param filename
	 *                   the filename
	 * @param extension
	 *                   the extension
	 * @param logo
	 *                   the logo
	 * @param bcc
	 *                   the bcc
	 */
	public void sendMailCommonsWithAttachementBcc(String to_address, String subject, String text, byte[] attachment,
			String filename, String extension, String logo, String[] bcc) {
		mailWithAttachementBcc(to_address, subject, text, attachment, filename, filename, extension, logo, bcc);
	}

	/**
	 * Send mail commons gmail with attachement.
	 *
	 * @param to_address
	 *                   the to address
	 * @param subject
	 *                   the subject
	 * @param text
	 *                   the text
	 * @param attachment
	 *                   the attachment
	 * @param filename
	 *                   the filename
	 * @param extension
	 *                   the extension
	 * @param logo
	 *                   the logo
	 */
	public void sendMailCommonsWithAttachement(String to_address, String subject, String text, byte[] attachment,
			String filename, String extension, String logo) {
		mailWithAttachement(to_address, subject, text, attachment, filename, filename, extension, logo);
	}

	/**
	 * Mail with attachement.
	 *
	 * @param to_address
	 *                              the to address
	 * @param subject
	 *                              the subject
	 * @param text
	 *                              the text
	 * @param attachment
	 *                              the attachment
	 * @param attachmentDescription
	 *                              the attachment description
	 * @param attachmentName
	 *                              the attachment name
	 * @param mime
	 *                              the mime
	 * @param logo
	 *                              the logo
	 */
	private void mailWithAttachement(String to_address, String subject, String text, byte[] attachment,
			String attachmentDescription, String attachmentName, String mime, String logo) {
		try {

			HtmlEmail email = new HtmlEmail();
			setupEmail(to_address, email);
			email.setSubject(subject);
			email.setSubject(subject);
			email.setHtmlMsg(createHtml(email, text, logo));

			// add the attachment

			email.attach(new ByteArrayDataSource(attachment, "application/" + mime.toLowerCase()), attachmentName,
					attachmentDescription, EmailAttachment.ATTACHMENT);

			// send the email
			email.send();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Mail with attachement.
	 *
	 * @param to_address
	 *                              the to address
	 * @param subject
	 *                              the subject
	 * @param text
	 *                              the text
	 * @param attachment
	 *                              the attachment
	 * @param attachmentDescription
	 *                              the attachment description
	 * @param attachmentName
	 *                              the attachment name
	 * @param mime
	 *                              the mime
	 * @param logo
	 *                              the logo
	 *
	 * @param bcc
	 *                              the bcc
	 */
	private void mailWithAttachementBcc(String to_address, String subject, String text, byte[] attachment,
			String attachmentDescription, String attachmentName, String mime, String logo, String[] bcc) {
		try {

			HtmlEmail email = new HtmlEmail();
			email.setHostName(mailServer);

			email.setAuthenticator(new DefaultAuthenticator(uid, pwd));

			email.setDebug(HAJConstants.MAIL_DEBUG);
			email.addTo(to_address);
			email.setFrom(uid);
			email.setSubject(subject);
			email.setSubject(subject);
			email.setHtmlMsg(createHtml(email, text, logo));
			email.addBcc(bcc);
			// add the attachment

			email.attach(new ByteArrayDataSource(attachment, "application/" + mime.toLowerCase()), attachmentName,
					attachmentDescription, EmailAttachment.ATTACHMENT);

			// send the email
			email.send();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Mail with attachement.
	 *
	 * @param to_address
	 *                              the to address
	 * @param subject
	 *                              the subject
	 * @param text
	 *                              the text
	 * @param attachment
	 *                              the attachment
	 * @param attachmentDescription
	 *                              the attachment description
	 * @param attachmentName
	 *                              the attachment name
	 * @param mime
	 *                              the mime
	 * @param mailLog
	 *                              the mail log
	 */
	public void mailWithAttachement(String to_address, String subject, String text, byte[] attachment,
			String attachmentDescription, String attachmentName, String mime, MailLog mailLog) {
		try {

			HtmlEmail email = new HtmlEmail();
			setupEmail(to_address, email);
			email.setSubject(subject);
			email.setSubject(subject);
			email.setHtmlMsg(createHtml(email, text, null));

			// add the attachment

			if (mime.toLowerCase().contains("image/"))
				email.attach(new ByteArrayDataSource(attachment, mime.toLowerCase()), attachmentName,
						attachmentDescription, EmailAttachment.ATTACHMENT);
			else
				email.attach(new ByteArrayDataSource(attachment, "application/" + mime.toLowerCase()), attachmentName,
						attachmentDescription, EmailAttachment.ATTACHMENT);

			// send the email
			email.send();
			if (mailLog != null) {
				MailLogService.update(mailLog, attachmentName, mime, attachment);
			}
		} catch (Exception e) {
			if (mailLog != null)
				MailLogService.update(mailLog, e);
			e.printStackTrace();
			logger.fatal(e);

		}
	}

	/**
	 * Mail with attachement.
	 *
	 * @param to_address
	 *                   the to address
	 * @param subject
	 *                   the subject
	 * @param text
	 *                   the text
	 * @param files
	 *                   the files
	 * @param logo
	 *                   the logo
	 */
	public void mailWithAttachement(String to_address, String subject, String text, List<AttachmentBean> files,
			String logo) {
		try {

			HtmlEmail email = new HtmlEmail();
			setupEmail(to_address, email);
			email.setSubject(subject);
			email.setSubject(subject);
			email.setHtmlMsg(createHtml(email, text, logo));

			// add the attachments
			for (AttachmentBean ab : files) {
				email.attach(new ByteArrayDataSource(ab.getFile(), "application/" + ab.getExt().toLowerCase()),
						ab.getFilename(), ab.getFilename(), EmailAttachment.ATTACHMENT);
			}

			// send the email
			email.send();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void mailWithAttachementTemp(String toAddress, String subject, String text, List<AttachmentBean> files,
			String logo, MailLog mailLog) {
		try {

			HtmlEmail email = new HtmlEmail();
			setupEmail(toAddress, email);
			email.setSubject(subject);
			email.setSubject(subject);
			email.setHtmlMsg(createHtml(email, text, logo));

			// add the attachments
			for (AttachmentBean ab : files) {
				email.attach(new ByteArrayDataSource(ab.getFile(), "application/" + ab.getExt().toLowerCase()),
						ab.getFilename(), ab.getFilename(), EmailAttachment.ATTACHMENT);
				MailLogService.update(mailLog, ab.getFilename(), ab.getExt(), ab.getFile());
			}

			// send the email
			email.send();

			if (mailLog != null) {
				MailLogService.update(mailLog);
			}

		} catch (Exception e) {
			if (mailLog != null)
				MailLogService.update(mailLog, e);
			logger.fatal(e);
		}
	}

	public void mailWithAttachementTemp(String to_address, String subject, String text, List<AttachmentBean> files,
			String logo, MailLog mailLog, String targetClass, long targetKey) {
		try {

			HtmlEmail email = new HtmlEmail();
			setupEmail(to_address, email);
			email.setSubject(subject);
			email.setSubject(subject);
			email.setHtmlMsg(createHtml(email, text, logo));

			// add the attachments
			for (AttachmentBean ab : files) {
				email.attach(new ByteArrayDataSource(ab.getFile(), "application/" + ab.getExt().toLowerCase()),
						ab.getFilename(), ab.getFilename(), EmailAttachment.ATTACHMENT);
				MailLogService.update(mailLog, ab.getFilename(), ab.getExt(), ab.getFile(), targetClass, targetKey);
			}
			// send the email
			email.send();

		} catch (Exception e) {
			if (mailLog != null)
				MailLogService.update(mailLog, e);
			e.printStackTrace();
			logger.fatal(e);
		}
	}

	public void sendMailCommonsWithAttachement(String to_address, String subject, String text, byte[] attachment,
			String filename, String extension, String logo, MailLog log) {
		mailWithAttachement(to_address, subject, text, attachment, filename, filename, extension, logo, log);
	}

	private void mailWithAttachement(String to_address, String subject, String text, byte[] attachment,
			String attachmentDescription, String attachmentName, String mime, String logo, MailLog mailLog) {
		try {

			HtmlEmail email = new HtmlEmail();
			setupEmail(to_address, email);
			email.setSubject(subject);
			email.setSubject(subject);
			email.setHtmlMsg(createHtml(email, text, logo));

			// add the attachment
			email.attach(new ByteArrayDataSource(attachment, "application/" + mime.toLowerCase()), attachmentName,
					attachmentDescription, EmailAttachment.ATTACHMENT);

			// send the email
			email.send();

			if (mailLog != null && mailLog.getId() != null) {
				MailLogService.update(mailLog, attachmentName, mime, attachment);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void sendMeeting(String to_address, String subject, String text, String ics) {
		try {

			logger.info("about to send meeting request to:" + to_address);

			StringBuffer sb = new StringBuffer();
			StringBuffer buffer = sb.append(ics);

			Session session = createSession();

			MimeMessage message = new MimeMessage(session);
			message.addHeaderLine("method=REQUEST");
			message.addHeaderLine("charset=UTF-8");
			message.addHeaderLine("component=vevent");

			message.setFrom(new InternetAddress(uid));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to_address));
			message.setSubject(subject);

			String template = createTemplate(text);

			// Create the message part
			BodyPart messageBodyPart = new MimeBodyPart();

			messageBodyPart.setHeader("Content-Class", "urn:content-classes:calendarmessage");
			messageBodyPart.setHeader("Content-ID", "calendar_message");
			messageBodyPart.setContent(buffer.toString(), "text/calendar");
			messageBodyPart.setContent(template, "text/html; charset=utf-8");

			// Create a Multipart
			Multipart multipart = new MimeMultipart();

			// Add part one
			multipart.addBodyPart(messageBodyPart);

			// Part two is attachment
			// Create second body part
			messageBodyPart = new MimeBodyPart();
			String filename = "Appearance.ics";
			messageBodyPart.setFileName(filename);
			messageBodyPart.setContent(buffer.toString(), "text/plain");

			// Add part two
			multipart.addBodyPart(messageBodyPart);

			// Put parts in message
			message.setContent(multipart);

			Transport.send(message);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal(e);
			e.printStackTrace();
		}

	}

	private String createTemplate(String text) throws Exception {

		String template = HAJConstants.EMAIL_TEMPLATE;
		template = template.replaceAll("#BODY#", text.trim());
		return template;
	}

	private Session createSession() {
		Properties props = new Properties();
		props.put("mail.smtp.host", mailServer);

		// MS EXCHANGE
		props.put("mail.smtp.port", "25");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.auth", "true");

		// // OFFICE 365
		// props.put("mail.smtp.port", "587");
		// props.put("mail.smtp.starttls.enable", "true");
		// props.put("mail.smtp.auth", "true");

		/*
		 * GMAIL ----- props.put("mail.smtp.socketFactory.port", "465");
		 * props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		 * props.put("mail.smtp.auth", "true"); props.put("mail.smtp.port", "465");
		 */
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(uid, pwd);
			}
		});
		return session;
	}

	public void sendMail(String to_address, String subject, String text) {
		try {

			logger.info("about to send mail! to:" + to_address);

			Session session = createSession();
			System.out.println("mailing to : " + to_address);
			System.out.print("mail text :  " + text);

			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(uid));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to_address));
			message.setSubject(subject);

			System.out.print("Perparing to send mail");

			String template = createTemplate(text);

			message.setContent(template, "text/html; charset=utf-8");
			System.out.print("main content set");

			Transport.send(message);
			System.out.print("aaaaaa mail has beem successfully send");

		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.fatal(e);
		}

	}

}
