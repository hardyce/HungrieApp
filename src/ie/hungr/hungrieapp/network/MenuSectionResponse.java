package ie.hungr.hungrieapp.network;

import ie.hungr.hungrieapp.model.MenuSection;

public class MenuSectionResponse {

	private MenuSection[] menu_section;

	public MenuSection[] getMenu_sections() {
		return menu_section;
	}

	public void setMenu_sections(MenuSection[] menu_sections) {
		this.menu_section = menu_sections;
	}

}
