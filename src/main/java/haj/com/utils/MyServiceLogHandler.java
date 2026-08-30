package haj.com.utils;

import java.io.IOException;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;

import za.co.merseta.nsdms.framework.logging.NSDMSLogger;

// Following import added by Akhil for updated methods provided by Xcellence
import java.io.ByteArrayOutputStream;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;

public class MyServiceLogHandler implements SOAPHandler<SOAPMessageContext> {

	// Following 2 lines added by Akhil for updated methods provided by Xcellence
	private NSDMSLogger logger = NSDMSLogger.getLogger(MyServiceLogHandler.class);
	public static final String SOAP_MESSAGE_INDENT_AMOUNT="{http://xml.apache.org/xslt}indent-amount";
	
	@Override
	public Set<QName> getHeaders() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void close(MessageContext arg0) {
		// TODO Auto-generated method stub

	}
	
// Commented (upto line #97) by Akhil to add methods provided by Open Xcellence
//	@Override
//	public boolean handleFault(SOAPMessageContext arg0) {
//		SOAPMessage message= arg0.getMessage();
//		try {
//			message.writeTo(System.out);
//		} catch (SOAPException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return false;
//	}
//
//	@Override
//	public boolean handleMessage(SOAPMessageContext arg0) {
//		SOAPMessage message= arg0.getMessage();
//		boolean isOutboundMessage=  (Boolean)arg0.get (MessageContext.MESSAGE_OUTBOUND_PROPERTY);
//		if(isOutboundMessage){
//			System.out.println("OUTBOUND MESSAGE\n");
///*			try {
//				 OutputStream output = new OutputStream()
//				    {
//				        private StringBuilder string = new StringBuilder();
//				        @Override
//				        public void write(int b) throws IOException {
//				            this.string.append((char) b );
//				        }
//
//				  
//				        public String toString(){
//				            return this.string.toString();
//				        }
//				    };
//				message.writeTo(output);
//			
//				    FileUtils.writeStringToFile(
//				      GPService.instance().file, output.toString(),  true);
//			} catch (Exception e) {
//				// TODO: handle exception
//			}
//*/
//		}else{
//			System.out.println("INBOUND MESSAGE\n");
//		}
//		try {
//			message.writeTo(System.out);
//			System.out.println("\n");
//		} catch (SOAPException e) {
//			e.printStackTrace();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return true;
//	}

// Added by Akhil, the updated methods provided by Open Xcellence

	@Override
	public boolean handleFault(SOAPMessageContext soapFaultContext) {
		try { 
				printMessage(soapFaultContext.getMessage(), false);
		}
		catch(Exception e){ 
				logger.error(String.format("Failed to log SOAP Fault in handleFault [%s:%s]", e.getClass(), e.getMessage()), e);
		}
		return false;
	}
	
	@Override
	public boolean handleMessage(SOAPMessageContext soapMessageContext) {
		try {
			printMessage(soapMessageContext.getMessage(), (Boolean) soapMessageContext.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY));
		}
		catch(Exception e){
			logger.error(String.format("Failed to log SOAP Message in handleMessage [%s:%s]", e.getClass(), e.getMessage()), e);
		}
		return true;
	}
	
	private void printMessage(SOAPMessage message, boolean isRequest ){
		try {
			TransformerFactory tff = TransformerFactory.newInstance();
			Transformer tf = tff.newTransformer();
			tf.setOutputProperty(OutputKeys.INDENT, "yes");
			tf.setOutputProperty(SOAP_MESSAGE_INDENT_AMOUNT,"4");
			Source messageSource = message.getSOAPPart().getContent();
			ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
			StreamResult result = new StreamResult(streamOut);
			tf.transform(messageSource, result);
			String stringMessage = String.format("%s %s %s", isRequest?"REQUEST LEG:":"RESPONSE LEG:", System.lineSeparator(), streamOut.toString());
			logger.trace(stringMessage);
		}
		catch(Exception e){
			logger.error(String.format("Failed to log SOAP Message in printMessage [%s:%s]", e.getClass(), e.getMessage()),e);
		}
	}
}