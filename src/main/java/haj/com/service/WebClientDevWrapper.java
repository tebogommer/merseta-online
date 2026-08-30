package haj.com.service;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.conn.ssl.X509HostnameVerifier;
import org.apache.http.impl.client.DefaultHttpClient;

// TODO: Auto-generated Javadoc
/**
 * The Class WebClientDevWrapper.
 */
public class WebClientDevWrapper {

	/**
	 * Wrap client.
	 *
	 * @param base the base
	 * @return the http client
	 */
	public static HttpClient wrapClient(HttpClient base) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
 
         			@Override
				public void checkClientTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void checkServerTrusted(X509Certificate[] chain,
						String authType) throws CertificateException {
					// TODO Auto-generated method stub
					
				}

				@Override
				public X509Certificate[] getAcceptedIssuers() {
					// TODO Auto-generated method stub
					return null;
				}
            };
            X509HostnameVerifier verifier = new X509HostnameVerifier() {

				@Override
				public boolean verify(String hostname, SSLSession session) {
					// TODO Auto-generated method stub
					return true;
				}

				@Override
				public void verify(String arg0, SSLSocket arg1)
						throws IOException {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void verify(String arg0, X509Certificate arg1)
						throws SSLException {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void verify(String arg0, String[] arg1, String[] arg2)
						throws SSLException {
					// TODO Auto-generated method stub
					
				}
 
        
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(verifier);
            ClientConnectionManager ccm = base.getConnectionManager();
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", ssf, 443));
            return new DefaultHttpClient(ccm, base.getParams());
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
}