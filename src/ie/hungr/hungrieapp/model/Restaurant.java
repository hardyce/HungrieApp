//toDo: Do something about this data class

package ie.hungr.hungrieapp.model;

public class Restaurant {

	private String url;
	private String[] category;
	private RestaurantLocation location;
	private String owner;
	private String name;
	private String street1;
	private String street2;
	private String postcode;
	private String city;
	private String country;
	private String delivery_radius;
	private String phone;
	private String email;
	private String preperation_time;
	private String delivery_minimum;
	private String delivery_cost;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String[] getCategory() {
		return category;
	}

	public void setCategory(String[] category) {
		this.category = category;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStree1t() {
		return street1;
	}

	public void setStreet1(String street1) {
		this.street1 = street1;
	}

	public String getStreet2() {
		return street2;
	}

	public void setStreet2(String street2) {
		this.street2 = street2;
	}

	public String getPostcode() {
		return postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPreperation_time() {
		return preperation_time;
	}

	public void setPreperation_time(String preperation_time) {
		this.preperation_time = preperation_time;
	}

	public String getDelivery_minimum() {
		return delivery_minimum;
	}

	public void setDelivery_minimum(String delivery_minimum) {
		this.delivery_minimum = delivery_minimum;
	}

	public String getDelivery_cost() {
		return delivery_cost;
	}

	public void setDelivery_cost(String delivery_cost) {
		this.delivery_cost = delivery_cost;
	}

	public RestaurantLocation getLocation() {
		return location;
	}

	public void setLocation(RestaurantLocation location) {
		this.location = location;
	}

	public String getDelivery_radius() {
		return delivery_radius;
	}

	public void setDelivery_radius(String delivery_radius) {
		this.delivery_radius = delivery_radius;
	}

	public String getStreet1() {
		return street1;
	}

}
