package nishida.restclient;

import org.springframework.stereotype.Component;

@Component
public interface RestClient {

	/**
	 * Calls HTTP GET on resources at the given URL
	 *
	 * @param url to get from
	 * @return String representation of the resource
	 */
	public String getResource(String url);

}