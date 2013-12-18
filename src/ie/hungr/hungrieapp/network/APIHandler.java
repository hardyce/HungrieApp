//Written By: Pablo
//Assistance: Seamus
//Desc: A class to handle the rest communication with the server
//Date: 01/02/2013

package ie.hungr.hungrieapp.network;

import ie.hungr.hungrieapp.model.Menu;
import ie.hungr.hungrieapp.model.MenuSection;
import ie.hungr.hungrieapp.model.Order;
import ie.hungr.hungrieapp.model.OrderItem;
import ie.hungr.hungrieapp.model.Restaurant;
import ie.hungr.hungrieapp.model.Token;
import ie.hungr.hungrieapp.model.User;
import ie.hungr.hungrieapp.model.UserCredentials;

import java.io.IOException;
import java.util.Collections;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import android.util.Log;

public class APIHandler {

	// Production server
	public static final String BASE_URL = "http://54.228.210.184";
	// Local server
	//public static final String BASE_URL = "http://192.168.0.15:8000";

	public static final String RESOURCE_AUTH = "auth-token";
	public static final String RESOURSE_USER = "user";
	public static final String RESOURCE_RESTAURANT = "restaurant";
	public static final String RESOURCE_MENU = "menu";
	public static final String RESOURCE_ORDER = "order";
	public static final String RESOURCE_ORDER_ITEM = "order-item";

	private final static String CLASS_NAME = APIHandler.class.getSimpleName();

	public static Token getAuth(UserCredentials userCredentials) {

		Token result = null;

		// Set the Accept header
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application", "json"));
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		HttpEntity<UserCredentials> requestEntity = new HttpEntity<UserCredentials>(
				userCredentials, requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the Gson message converter
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

		String url = BASE_URL + "/" + RESOURCE_AUTH + "/";
		Log.d(CLASS_NAME, url);

		ResponseEntity<Token> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, Token.class);
		} catch (RestClientException rce) {
			Log.d(CLASS_NAME, rce.getMessage());
		} catch (Exception e) {
			Log.d(CLASS_NAME, e.getMessage());
		}

		if (response != null) {
			if (response.getStatusCode() == HttpStatus.OK)
				result = response.getBody();
			else
				Log.d(CLASS_NAME, "Status code:" + response.getStatusCode());
		}
		return result;
	}

	public static Order createOrder(Token token, RestaurantUrl restaurantUrl) {

		Order result = null;

		// Set the Accept header
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application", "json"));
		requestHeaders.set("Authorization", "token " + token.getToken());
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		HttpEntity<RestaurantUrl> requestEntity = new HttpEntity<RestaurantUrl>(
				restaurantUrl, requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the Gson message converter
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

		String url = BASE_URL + "/" + RESOURCE_ORDER + "/";
		Log.d(CLASS_NAME, url);

		ResponseEntity<Order> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, Order.class);
		} catch (RestClientException rce) {
			Log.d(CLASS_NAME, rce.getMessage());
		} catch (Exception e) {
			Log.d(CLASS_NAME, e.getMessage());
		}

		if (response != null) {
			if (response.getStatusCode() == HttpStatus.CREATED)
				result = response.getBody();
			else
				Log.d(CLASS_NAME, "Status code:" + response.getStatusCode());
		}
		return result;
	}

	public static Order getOrder(Token token, String orderURL) {

		Order result = null;

		// Set the Accept header
		HttpHeaders requestHeaders = new HttpHeaders();
		// requestHeaders.setContentType(new MediaType("application","json"));
		requestHeaders.set("Authorization", "token " + token.getToken());
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the Gson message converter
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

		// String url = BASE_URL + "/" + RESOURCE_ORDER + "/" + orderID + "/";
		String url = orderURL;
		Log.d(CLASS_NAME, url);

		ResponseEntity<Order> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Order.class);
		} catch (Exception rce) {
			Log.d(CLASS_NAME, rce.getMessage());
		}

		if (response != null) {
			if (response.getStatusCode() == HttpStatus.OK)
				result = response.getBody();
			else
				Log.d(CLASS_NAME, "Status code:" + response.getStatusCode());
		}
		return result;
	}

	public static Order updateOrder(Token token, Order order) {

		Order result = null;

		// HttpAuthentication authHeader = new HttpBasicAuthentication("token",
		// token.getToken());
		// Set the Accept header
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application", "json"));
		requestHeaders.set("Authorization", "token " + token.getToken());
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		HttpEntity<Order> requestEntity = new HttpEntity<Order>(order,
				requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the Gson message converter
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

		// String url = BASE_URL + "/" + RESOURCE_ORDER + "/";
		String url = order.getUrl();
		Log.d(CLASS_NAME, url);

		ResponseEntity<Order> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.PUT,
					requestEntity, Order.class);
		} catch (RestClientException rce) {
			Log.d(CLASS_NAME, rce.getMessage());
		} catch (Exception e) {
			Log.d(CLASS_NAME, e.getMessage());
		}

		if (response != null) {
			if (response.getStatusCode() == HttpStatus.OK)
				result = response.getBody();
			else
				Log.d(CLASS_NAME, "Status code:" + response.getStatusCode());
		}
		return result;
	}

	public static OrderItem addItem(Token token, OrderUrl orderUrl) {

		OrderItem result = null;

		// HttpAuthentication authHeader = new HttpBasicAuthentication("token",
		// token.getToken());
		// Set the Accept header
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application", "json"));
		requestHeaders.set("Authorization", "token " + token.getToken());
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		HttpEntity<OrderUrl> requestEntity = new HttpEntity<OrderUrl>(orderUrl,
				requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the Gson message converter
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

		String url = BASE_URL + "/" + RESOURCE_ORDER_ITEM + "/";
		Log.d(CLASS_NAME, url);

		ResponseEntity<OrderItem> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, OrderItem.class);
		} catch (RestClientException rce) {
			Log.d(CLASS_NAME, rce.getMessage());
		} catch (Exception e) {
			Log.d(CLASS_NAME, e.getMessage());
		}

		if (response != null) {
			if (response.getStatusCode() == HttpStatus.CREATED)
				result = response.getBody();
			else
				Log.d(CLASS_NAME, "Status code:" + response.getStatusCode());
		}
		return result;
	}

	public static Restaurant[] getRestaurant() {

		Restaurant[] result = null;

		// Set the Accept header
		HttpHeaders requestHeaders = new HttpHeaders();
		// requestHeaders.setContentType(new MediaType("application","json"));
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the Gson message converter
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

		String url = BASE_URL + "/" + RESOURCE_RESTAURANT + "/";
		Log.d(CLASS_NAME, url);

		ResponseEntity<Restaurant[]> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Restaurant[].class);
		} catch (Exception rce) {
			rce.printStackTrace();
		}

		if (response != null) {
			if (response.getStatusCode() == HttpStatus.OK)
				result = response.getBody();
			else
				Log.d(CLASS_NAME, "Status code:" + response.getStatusCode());
		}
		return result;
	}

	public static Restaurant[] getRestaurantByLocation(String latitude,
			String longitude) {

		Restaurant[] result = null;

		// Set the Accept header
		HttpHeaders requestHeaders = new HttpHeaders();
		// requestHeaders.setContentType(new MediaType("application","json"));
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the Gson message converter
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

		String url = BASE_URL + "/" + RESOURCE_RESTAURANT + "/" + "?latitude="
				+ latitude + "&longitude=" + longitude;

		Log.d(CLASS_NAME, url);

		ResponseEntity<Restaurant[]> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Restaurant[].class);
		} catch (Exception rce) {
			rce.printStackTrace();
		}

		if (response != null) {
			if (response.getStatusCode() == HttpStatus.OK)
				result = response.getBody();
			else
				Log.d(CLASS_NAME, "Status code:" + response.getStatusCode());
		}
		return result;
	}

	public static ResponseBundle signUpUser(User user) {
		final ResponseBundle responseBundle = new ResponseBundle();

		// Set the Accept header
		HttpHeaders requestHeaders = new HttpHeaders();
		requestHeaders.setContentType(new MediaType("application", "json"));
		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		HttpEntity<User> requestEntity = new HttpEntity<User>(user,
				requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the Gson message converter
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

		String url = BASE_URL + "/" + RESOURSE_USER + "/";
		Log.d("URL", url);

		ResponseEntity<User> response = null;

		restTemplate.setErrorHandler(new ResponseErrorHandler() {

			@Override
			public boolean hasError(ClientHttpResponse response)
					throws IOException {
				if (response.getStatusCode() != HttpStatus.CREATED) {
					return true;
				} else
					return false;
			}

			@Override
			public void handleError(ClientHttpResponse response)
					throws IOException {
				responseBundle.setResponseStatus(response.getStatusCode()
						.value());

				if (response.getStatusCode() == HttpStatus.BAD_REQUEST) {
					Log.d(CLASS_NAME, "Username already exist");
					responseBundle.setErrorMessage("Username already exist");
				} else if (response.getStatusCode() == HttpStatus.NOT_FOUND) {
					Log.d(CLASS_NAME, "Resource not found");
					responseBundle.setErrorMessage("Server not found");
				} else {
					Log.d(CLASS_NAME, "Unknown error");
					responseBundle.setErrorMessage("Unknown error");
				}
			}
		});

		try {
			response = restTemplate.exchange(url, HttpMethod.POST,
					requestEntity, User.class);
		} catch (Exception rce) {
			Log.d(CLASS_NAME, rce.getStackTrace().toString());
			Log.d(CLASS_NAME, "Server is down");
		}

		if (response != null) {
			if (response.getStatusCode() == HttpStatus.CREATED) {
				responseBundle.setResponseStatus(response.getStatusCode()
						.value());
				responseBundle.setResource(response.getBody());
			}
		} else if (responseBundle.getErrorMessage() == null) {
			responseBundle.setErrorMessage("Server is down");
		}
		return responseBundle;
	}

	public static Menu getMenu(String url) {

		Menu result = null;

		// Set the Accept header
		HttpHeaders requestHeaders = new HttpHeaders();

		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the Gson message converter
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

		Log.d(CLASS_NAME, url);

		ResponseEntity<Menu> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, Menu.class);
		} catch (Exception rce) {
			Log.d(CLASS_NAME, rce.getMessage());
		}

		if (response != null) {
			if (response.getStatusCode() == HttpStatus.OK)
				result = response.getBody();
			else
				Log.d(CLASS_NAME, "Status code:" + response.getStatusCode());
		}
		return result;
	}

	public static MenuSection[] getMenuSections(Restaurant restaurant) {

		MenuSectionResponse result = null;

		// Set the Accept header
		HttpHeaders requestHeaders = new HttpHeaders();

		requestHeaders.setAccept(Collections.singletonList(new MediaType(
				"application", "json")));
		HttpEntity<?> requestEntity = new HttpEntity<Object>(requestHeaders);

		// Create a new RestTemplate instance
		RestTemplate restTemplate = new RestTemplate();

		// Add the Gson message converter
		restTemplate.getMessageConverters().add(new GsonHttpMessageConverter());

		String url = restaurant.getUrl() + "menu" + "/";
		Log.d(CLASS_NAME, url);

		ResponseEntity<MenuSectionResponse> response = null;

		try {
			response = restTemplate.exchange(url, HttpMethod.GET,
					requestEntity, MenuSectionResponse.class);
		} catch (Exception rce) {
			Log.d(CLASS_NAME, rce.getMessage());
		}

		if (response != null) {
			if (response.getStatusCode() == HttpStatus.OK) {
				result = response.getBody();
			} else
				Log.d(CLASS_NAME, "Status code:" + response.getStatusCode());
		}

		return result.getMenu_sections();
	}

}
