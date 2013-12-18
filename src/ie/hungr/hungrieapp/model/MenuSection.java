package ie.hungr.hungrieapp.model;

public class MenuSection {

	private String title;
	private String url;

	public MenuSection(String url, String title) {
		this.url = url;
		this.title = title;
	}

	public String getURL() {
		return this.url;
	}

	public String getTitle() {
		return this.title;
	}
}