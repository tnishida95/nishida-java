package nishida.grocery;

import lombok.Data;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

/**
 * POJO representation of the response received from requests to the https://backflipp.wishabi.com/items/search
 * endpoint.
 */
@Data
public class FlippResponse {

	@SerializedName("items")
	ArrayList<FlippItem> flippItems = new ArrayList<FlippItem>();

}
