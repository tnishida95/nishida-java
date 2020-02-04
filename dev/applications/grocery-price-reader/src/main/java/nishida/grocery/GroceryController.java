package nishida.grocery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import nishida.restclient.RestClient;

@RestController
public class GroceryController {

	private final String FLIPP_ENDPOINT = "https://backflipp.wishabi.com/flipp/items/search";

	Gson gson = new Gson();

	@Autowired
	@Qualifier("restTemplateImpl")
	RestClient restClient;

	@RequestMapping("/")
	public String root(@RequestParam(value="name", defaultValue="World") String name) {
		return ("Hello, " + name + "!");
	}

	@RequestMapping("/items")
	public String view(@RequestParam(value="postalCode", defaultValue="92128") String postalCode,
					   @RequestParam(value="store", defaultValue="ralphs") String store) {
		String json = restClient.getResource(FLIPP_ENDPOINT + "?locale=en&postal_code=" + postalCode + "&q=" + store);
		FlippResponse flippResponse = gson.fromJson(json, FlippResponse.class);
		String toReturn = "";
		for(FlippItem item : flippResponse.getFlippItems()) {
			toReturn += "[" + item.getName() + " " + item.getCurrentPrice() + "], ";
		}
		return toReturn;

		//return restClient.getResource("https://backflipp.wishabi.com/flipp/items/search?locale=en&postal_code=" + postalCode + "&q=ralphs");
	}
}