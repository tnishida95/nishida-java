package nishida.hello;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;

@RestController
public class HelloWorldController {

	@Autowired
	RestClient restClient;

	@RequestMapping("/hello")
	public String hello(@RequestParam(value="name", defaultValue="World") String name) {
		return ("Hello, " + name + "!");
	}

	@RequestMapping("/fetch")
	public String fetch(@RequestParam(value="resource") String resource) {
		return (restClient.getResource(resource));
	}
}