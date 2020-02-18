package nishida.grocery;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@Controller
public class GroceryController {

	private final String FLIPP_ENDPOINT = "https://backflipp.wishabi.com/flipp/items/search";

	Gson gson = new Gson();

	@Autowired
	@Qualifier("restTemplateImpl")
	RestClient restClient;

	@GetMapping("/")
	public String index(@RequestParam(value="name", defaultValue="World") String name, Model model) {
		log.info("Received GET for [/]");
		model.addAttribute("name", name);
		return "index";
	}

	@GetMapping("/items")
	public String items(@RequestParam(value="postalCode", defaultValue="92101") String postalCode,
						@RequestParam(value="store", defaultValue="all") String store,
						Model model) {
		log.info("Received GET for [/items] with postalCode=[{}] and store=[{}]", postalCode, store);
		try {
			if(store.toLowerCase().equals("all")) {
				// TODO: there is a better way to do this
				String ralphsJson = restClient.getResource(FLIPP_ENDPOINT + "?locale=en&postal_code=" + postalCode + "&q=" + "ralphs").getBody();
				FlippResponse ralphsResponse = gson.fromJson(ralphsJson, FlippResponse.class);
				String vonsJson = restClient.getResource(FLIPP_ENDPOINT + "?locale=en&postal_code=" + postalCode + "&q=" + "vons").getBody();
				FlippResponse vonsResponse = gson.fromJson(vonsJson, FlippResponse.class);
				String sproutsJson = restClient.getResource(FLIPP_ENDPOINT + "?locale=en&postal_code=" + postalCode + "&q=" + "sprouts").getBody();
				FlippResponse sproutsResponse = gson.fromJson(sproutsJson, FlippResponse.class);
				for(FlippItem f : vonsResponse.getFlippItems()) {
					ralphsResponse.getFlippItems().add(f);
				}
				for(FlippItem f : sproutsResponse.getFlippItems()) {
					ralphsResponse.getFlippItems().add(f);
				}
				model.addAttribute("items", ralphsResponse.getFlippItems());
			}
			else {
				String json = restClient.getResource(FLIPP_ENDPOINT + "?locale=en&postal_code=" + postalCode + "&q=" + store).getBody();
				FlippResponse flippResponse = gson.fromJson(json, FlippResponse.class);
				model.addAttribute("items", flippResponse.getFlippItems());
			}
		}
		catch(RestClientException e) {
			log.debug("Error retrieving JSON from Flipp endpoint", e);
			model.addAttribute("message", "Got bad response from server.  Check store/ZIP code combination.");
			return "index";
		}
		model.addAttribute("postalCode", postalCode);
		model.addAttribute("store", store.toUpperCase());
		return "items";
	}
}
