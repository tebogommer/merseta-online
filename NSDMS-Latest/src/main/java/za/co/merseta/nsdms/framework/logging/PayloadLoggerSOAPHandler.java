//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package za.co.merseta.nsdms.framework.logging;

import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import org.apache.logging.log4j.util.Supplier;

public class PayloadLoggerSOAPHandler implements SOAPHandler<SOAPMessageContext> {
    private NSDMSLogger logger = NSDMSLogger.getLogger(PayloadLoggerSOAPHandler.class);
    public static final String SOAP_MESSAGE_INDENT_AMOUNT = "{http://xml.apache.org/xslt}indent-amount";

    public PayloadLoggerSOAPHandler() {
    }

    public Set<QName> getHeaders() {
        return null;
    }

    public void close(MessageContext arg0) {
    }

    public boolean handleFault(SOAPMessageContext soapFaultContext) {
        try {
            this.printHttpHeaders((Map)soapFaultContext.get("javax.xml.ws.http.response.headers"));
            this.printMessage(soapFaultContext.getMessage(), false);
        } catch (Exception var3) {
            this.logger.error(String.format("Failed to log SOAP Fault in handleFault [%s:%s]", var3.getClass(), var3.getMessage()), var3);
        }

        return true;
    }

    public boolean handleMessage(SOAPMessageContext soapMessageContext) {
        try {
            String url = (String)soapMessageContext.get("javax.xml.ws.service.endpoint.address");
            this.logger.debug("Calling Endpoint URL={}", new Object[]{url});
            if ((Boolean)soapMessageContext.get("javax.xml.ws.handler.message.outbound")) {
                this.printHttpHeaders((Map)soapMessageContext.get("javax.xml.ws.http.request.headers"));
            } else {
                this.printHttpHeaders((Map)soapMessageContext.get("javax.xml.ws.http.response.headers"));
            }

            this.printMessage(soapMessageContext.getMessage(), (Boolean)soapMessageContext.get("javax.xml.ws.handler.message.outbound"));
        } catch (Exception var3) {
            this.logger.error(String.format("Failed to log SOAP Message in handleMessage [%s:%s]", var3.getClass(), var3.getMessage()), var3);
        }

        return true;
    }

    private void printMessage(SOAPMessage message, boolean isRequest) {
        try {
            TransformerFactory tff = TransformerFactory.newInstance();
            Transformer tf = tff.newTransformer();
            tf.setOutputProperty("indent", "yes");
            tf.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            Source messageSource = message.getSOAPPart().getContent();
            ByteArrayOutputStream streamOut = new ByteArrayOutputStream();
            StreamResult result = new StreamResult(streamOut);
            tf.transform(messageSource, result);
            String stringMessage = String.format("%s %s %s", isRequest ? "REQUEST LEG:" : "RESPONSE LEG:", System.lineSeparator(), streamOut.toString());
            this.logger.trace(stringMessage, new Supplier[0]);
        } catch (Exception var9) {
            this.logger.error(String.format("Failed to log SOAP Message in printMessage [%s:%s]", var9.getClass(), var9.getMessage()), var9);
        }

    }

    private void printHttpHeaders(Map<String, List<String>> headers) {
        String headersString = "";
        if (headers != null) {
            Iterator var3 = headers.keySet().iterator();

            while(true) {
                String key;
                do {
                    if (!var3.hasNext()) {
                        this.logger.debug("[HTTP HEADERS] \n {}", new Object[]{headersString});
                        return;
                    }

                    key = (String)var3.next();
                } while(key != null && key.toLowerCase().contains("password"));

                headersString = String.format("%s \t %s : %s \n", headersString, key, headers.get(key));
            }
        }
    }

    static {
        System.setProperty("com.sun.xml.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.client.HttpTransportPipe.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dump", "true");
        System.setProperty("com.sun.xml.internal.ws.transport.http.HttpAdapter.dumpTreshold", "999999");
    }
}
