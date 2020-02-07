package nishida.restclient;

import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;
import org.springframework.core.io.ClassPathResource;

import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;

import javax.net.ssl.SSLContext;
import javax.annotation.PostConstruct;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClients;

import java.security.KeyStore;
import java.io.IOException;
import java.io.InputStream;

@Component("restTemplateImpl")
public class RestClientImpl implements RestClient {

	@Value("${certificate.path}")
	private String certificatePath;

	@Value("${certificate.password}")
	private String certificatePassword;

	private RestTemplate restTemplate;

	@PostConstruct
	private void init() throws Exception {

		restTemplate = new RestTemplate(getClientHttpRequestFactory());
	}

	private ClientHttpRequestFactory getClientHttpRequestFactory() throws Exception {
		return new HttpComponentsClientHttpRequestFactory(getHttpClient());
	}

	private HttpClient getHttpClient() throws Exception {
		KeyStore ks = KeyStore.getInstance("pkcs12");
		char[] password = certificatePassword.toCharArray();
		try (InputStream keyStoreStream = new ClassPathResource(certificatePath, this.getClass().getClassLoader()).getInputStream()) {
			ks.load(keyStoreStream, password);
			SSLContext sslContext = SSLContexts.custom().loadKeyMaterial(ks, password).build();
			return HttpClients.custom().setSSLContext(sslContext).build();
		}
		catch(IOException e) {
			// failed to load keystore, so returning client without SSL
			return HttpClients.createDefault();
		}
	}

	public ResponseEntity<String> getResource(String url) {
		return restTemplate.getForEntity(url, String.class);
	}

}