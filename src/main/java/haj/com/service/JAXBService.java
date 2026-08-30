package haj.com.service;

import java.beans.XMLEncoder;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;

import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

// TODO: Auto-generated Javadoc
/**
 * The Class JAXBService.
 */
public class JAXBService implements Serializable {
	private static final NSDMSLogger logger = NSDMSLogger.getLogger(JAXBService.class);
	/** The jaxb context. */
	private static JAXBContext jaxbContext = null;// = buildContext();

	/**
	 * Builds the context.
	 *
	 * @param obj the obj
	 * @return the JAXB context
	 */
	private static JAXBContext buildContext(Object obj) {
		try {
			// return JAXBContext.newInstance( "xds.output" );
			return JAXBContext.newInstance(obj.getClass());
		} catch (Throwable ex) {
			System.err.println("Initial JAXBContext creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	/**
	 * Gets the jaxbcontext.
	 *
	 * @return the jaxbcontext
	 */
	public static JAXBContext getJaxbcontext() {
		return jaxbContext;
	}

	/**
	 * Gets the marshaller.
	 *
	 * @return the marshaller
	 * @throws Exception the exception
	 */
	public static Marshaller getMarshaller() throws Exception {
		Marshaller m = jaxbContext.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.FALSE);
		return m;
	}

	/**
	 * Convert date to xml.
	 *
	 * @param date the date
	 * @return the XML gregorian calendar
	 * @throws Exception the exception
	 */
	public static XMLGregorianCalendar convertDateToXml(java.util.Date date) throws Exception {
		if (date != null) {
			GregorianCalendar c = new GregorianCalendar();
			c.setTime(date);
			XMLGregorianCalendar date2 = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			return date2;
		} else
			return null;
	}

	/**
	 * Convert date to calendarl.
	 *
	 * @param date the date
	 * @return the calendar
	 * @throws Exception the exception
	 */
	public static Calendar convertDateToCalendarl(java.util.Date date) throws Exception {
		if (date != null) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			return cal;
		} else
			return null;
	}

	/**
	 * Convert XM lto date.
	 *
	 * @param date the date
	 * @return the java.util. date
	 */
	public static java.util.Date convertXMLtoDate(XMLGregorianCalendar date) {
		if (date != null)
			return date.toGregorianCalendar().getTime();
		else
			return null;
	}

	/**
	 * Marshall to string.
	 *
	 * @param o the o
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String marshallToString(Object o) throws Exception {
		StringWriter stringWriter = new StringWriter();
		Marshaller m = JAXBContext.newInstance(o.getClass()).createMarshaller();
		m.marshal(o, stringWriter);
		return stringWriter.toString();
	}

	/**
	 * Marshall to string.
	 *
	 * @param object the object to serialise
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String marshallToStringNoException(Object object) {
		String xml = null;
		try {
			StringWriter stringWriter = new StringWriter();
			JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
			@SuppressWarnings("rawtypes")
			JAXBElement element = new JAXBElement(new QName(object.getClass().getName()), object.getClass(), object);
			Marshaller marshaller = jaxbContext.createMarshaller();
			marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			marshaller.marshal(element, stringWriter);
			xml = stringWriter.toString();
		} catch (Exception e) {
			logger.error("Failed to convert object to XML", e);
			try {
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				XMLEncoder xmlEncoder = new XMLEncoder(baos);
				xmlEncoder.writeObject(object);
				xmlEncoder.close();
				xml = baos.toString();
			} catch (Exception innerException) {
				logger.error("Failed to convert object to XML", innerException);
			}

		}
		return xml;
	}

	/**
	 * Marshall to string.
	 *
	 * @param m the m
	 * @param o the o
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String marshallToString(Marshaller m, Object o) throws Exception {
		StringWriter stringWriter = new StringWriter();
		m.marshal(o, stringWriter);
		return stringWriter.toString();
	}

	/**
	 * Marshall to string no header.
	 *
	 * @param o the o
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String marshallToStringNoHeader(Object o) throws Exception {
		StringWriter stringWriter = new StringWriter();
		Marshaller m = JAXBContext.newInstance(o.getClass()).createMarshaller();
		m.setProperty(Marshaller.JAXB_FRAGMENT, true);
		m.marshal(o, stringWriter);
		return stringWriter.toString();
	}

	/**
	 * Unmarshallto object.
	 *
	 * @param xml the xml
	 * @param obj the obj
	 * @return the object
	 * @throws Exception the exception
	 */
	public static Object unmarshalltoObject(String xml, Object obj) throws Exception {
		jaxbContext = buildContext(obj);
		Unmarshaller u = jaxbContext.createUnmarshaller();
		ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
		return u.unmarshal(input);

	}

	/**
	 * Unmarshallto object.
	 *
	 * @param xml the xml
	 * @return the object
	 * @throws Exception the exception
	 */
	public static Object unmarshalltoObject(String xml) throws Exception {
		Unmarshaller u = jaxbContext.createUnmarshaller();
		ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
		return u.unmarshal(input);
	}

	/**
	 * Marshall to string.
	 *
	 * @param o       the o
	 * @param packAge the pack age
	 * @return the string
	 * @throws Exception the exception
	 */
	public static String marshallToString(Object o, String packAge) throws Exception {
		StringWriter stringWriter = new StringWriter();
		Marshaller m = JAXBContext.newInstance(packAge).createMarshaller();
		m.marshal(o, stringWriter);
		return stringWriter.toString();
	}

	/**
	 * Unmarshallto object.
	 *
	 * @param xml     the xml
	 * @param packAge the pack age
	 * @return the object
	 * @throws Exception the exception
	 */
	public static Object unmarshalltoObject(String xml, String packAge) throws Exception {
		Unmarshaller u = JAXBContext.newInstance(packAge).createUnmarshaller();
		ByteArrayInputStream input = new ByteArrayInputStream(xml.getBytes());
		return u.unmarshal(input);
	}

	/*
	 * public static void genSchemas() throws Exception {
	 * JAXBContext context = JAXBContext.newInstance(Authentication.class,
	 * SearchRequest.class,
	 * SearchResultList.class, PolicyDetail.class, PolicyDetailReq.class,
	 * OffboardRequest.class, OnboardRequest.class, NewPolicy.class,
	 * PolicyDetailReqById.class,
	 * PolicyDetailList.class);
	 * context.generateSchema(new MomentumSchemaOutputResolver());
	 * }
	 */

}
