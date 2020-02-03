package nishida.grocery;

import lombok.Data;

@Data
public class FlippItem {

	private int flyer_item_id;
	private int flyer_id;
	private String name;
	private String current_price;
	private String pre_price_text;
	private String post_price_text;

}

/*
			"flyer_item_id": 471229943,
			"flyer_id": 3265016,
			"left": 468.5,
			"right": 788.5,
			"bottom": -1377.37,
			"top": -1253.37,
			"clipping_image_url": "http://f.wishabi.net/page_items/182344999/1579899959/extra_large.jpg",
			"name": "Modelo, Stella Artois, Heineken, Pacifico, Sam Adams or Kona",
			"current_price": 9.99,
			"pre_price_text": "Must Buy 3 FINAL COST",
			"post_price_text": "Ea. With Card",
			"sale_story": null,
			"valid_to": "2020-02-05T04:59:59+00:00",
			"valid_from": "2020-01-29T05:00:00+00:00",
			"merchant_id": 2550,
			"merchant_name": "Ralphs",
			"merchant_logo": "http://s3.amazonaws.com/images.wishabi.ca/merchants/dFsA12vGbDernw==/RackMultipart20191105-1-ljsed6.jpg",
			"_L1": "Food, Beverages & Tobacco",
			"_L2": "Beverages",
			"score": 37380.574,
			"clean_image_url": "http://f.wishabi.net/page_items/182344999/1579899959/extra_large.jpg",
			"item_weight": 5.5341000000000005,
			"premium": true
 */