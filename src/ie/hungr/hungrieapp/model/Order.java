package ie.hungr.hungrieapp.model;

public class Order {

	String url;
	OrderItem[] orderItem;
	String user;
	String total;
	String restaurant;
	String created;
	String comment;
	String address;
	int status;
	int trustworthy_factor;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public OrderItem[] getOrderItem() {
		return orderItem;
	}

	public void setOrderItem(OrderItem[] orderItem) {
		this.orderItem = orderItem;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}

	public int getTrustworthy_factor() {
		return trustworthy_factor;
	}

	public void setTrustworthy_factor(int trustworthy_factor) {
		this.trustworthy_factor = trustworthy_factor;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

}
