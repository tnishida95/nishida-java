package nishida.hello;

import org.springframework.stereotype.Component;

@Component
public interface RestClient {

	public String getResource(String url);

}