package ie.hungr.hungrieapp.model;

import java.io.Serializable;

public class Menu implements Serializable {

	private static final long serialVersionUID = 251046867237725914L;
	private String restaurant;
	private String title;
	private MenuItem[] menu_item;

	public Menu(String restaurant, String title, MenuItem[] menuitem) {
		this.restaurant = restaurant;
		this.title = title;
		this.menu_item = menuitem;
	}

	public MenuItem[] getMenuItems() {
		return this.menu_item;
	}

	public String getTitle() {
		return this.title;
	}

	public String restaurant() {
		return this.restaurant;
	}
}