package nishida.grocery;

import com.google.gson.Gson;
import nishida.restclient.RestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestClientException;

@Controller
public class GroceryController {

	private final String FLIPP_ENDPOINT = "https://backflipp.wishabi.com/flipp/items/search";

	Gson gson = new Gson();

	@Autowired
	@Qualifier("restTemplateImpl")
	RestClient restClient;

	@GetMapping("/")
	public String index(@RequestParam(value="name", defaultValue="World") String name, Model model) {
		model.addAttribute("name", name);
		return "index";
	}

	@GetMapping("/items")
	public String items(@RequestParam(value="postalCode", defaultValue="92101") String postalCode,
						@RequestParam(value="store", defaultValue="all") String store,
						Model model) {
		ResponseEntity<String> res;
		try {
			if(store.equals("all")) {
				res = restClient.getResource(FLIPP_ENDPOINT + "?locale=en&postal_code=" + postalCode + "&q=ralphs&q=sprouts&q=vons");
			}
			else {
				res = restClient.getResource(FLIPP_ENDPOINT + "?locale=en&postal_code=" + postalCode + "&q=" + store);
			}
		}
		catch(RestClientException e) {
			model.addAttribute("message", "Got bad response from server.  Check store/ZIP code combination.");
			return "index";
		}
		FlippResponse flippResponse = gson.fromJson(res.getBody(), FlippResponse.class);
		model.addAttribute("items", flippResponse.getFlippItems());
		model.addAttribute("postalCode", postalCode);
		model.addAttribute("store", store.toUpperCase());
		return "items";
	}
}
