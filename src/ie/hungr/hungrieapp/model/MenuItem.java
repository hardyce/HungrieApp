package ie.hungr.hungrieapp.model;

import java.io.Serializable;

public class MenuItem implements Serializable {

	private static final long serialVersionUID = 1L;
	private String price;
	private String name;
	private String url;

	public String getName() {
		return this.name;
	}

	public String getPrice() {
		return this.price;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}