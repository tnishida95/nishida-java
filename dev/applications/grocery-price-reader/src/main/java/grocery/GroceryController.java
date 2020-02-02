package nishida.grocery;

import org.springframework.context.annotation.Import;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import nishida.restclient.RestClientImpl;

@Import(RestClientImpl.class)
@RestController
public class GroceryController {

	// TODO: figure out calling the interface
	@Autowired
	@Qualifier("restTemplateImpl")
	RestClientImpl restClient;

	@RequestMapping("/")
	public String root(@RequestParam(value="name", defaultValue="World") String name) {
		return ("Hello, " + name + "!");
	}

	@RequestMapping("/view")
	public String view(@RequestParam(value="postalCode", defaultValue="92128") String postalCode) {
		return restClient.getResource("https://backflipp.wishabi.com/flipp/items/search?locale=en&postal_code=" + postalCode + "&q=ralphs");
	}
}