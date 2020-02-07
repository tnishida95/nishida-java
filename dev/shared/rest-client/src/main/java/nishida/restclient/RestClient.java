package nishida.restclient;

import org.springframework.http.ResponseEntity;

public interface RestClient {

	/**
	 * Calls HTTP GET on resources at the given URL
	 *
	 * @param url to get from
	 * @return ResponseEntity<String> response to the request
	 */
	public ResponseEntity<String> getResource(String url);

}