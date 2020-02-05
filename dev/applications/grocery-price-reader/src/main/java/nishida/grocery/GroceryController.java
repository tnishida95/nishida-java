package nishida.grocery;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;

import org.springframework.beans.factory.annotation.Autowired;

import com.google.gson.Gson;

import nishida.restclient.RestClient;

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
						@RequestParam(value="store", defaultValue="ralphs") String store,
						Model model) {
		if(store.equals("all")) {
			String ralphsJson = restClient.getResource(FLIPP_ENDPOINT + "?locale=en&postal_code=" + postalCode + "&q=" + "ralphs");
			FlippResponse ralphsResponse = gson.fromJson(ralphsJson, FlippResponse.class);
			String vonsJson = restClient.getResource(FLIPP_ENDPOINT + "?locale=en&postal_code=" + postalCode + "&q=" + "vons");
			FlippResponse vonsResponse = gson.fromJson(vonsJson, FlippResponse.class);
			String sproutsJson = restClient.getResource(FLIPP_ENDPOINT + "?locale=en&postal_code=" + postalCode + "&q=" + "sprouts");
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
			String json = restClient.getResource(FLIPP_ENDPOINT + "?locale=en&postal_code=" + postalCode + "&q=" + store);
			FlippResponse flippResponse = gson.fromJson(json, FlippResponse.class);
			model.addAttribute("items", flippResponse.getFlippItems());
		}
		model.addAttribute("postalCode", postalCode);
		model.addAttribute("store", store);
		return "items";
	}
}
