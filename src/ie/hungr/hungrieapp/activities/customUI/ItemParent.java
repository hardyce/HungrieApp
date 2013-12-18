package ie.hungr.hungrieapp.activities.customUI;

import java.util.ArrayList;

public class ItemParent {

	private String name;
	private ArrayList<ItemChild> children;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ArrayList<ItemChild> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<ItemChild> children) {
		this.children = children;
	}

}
