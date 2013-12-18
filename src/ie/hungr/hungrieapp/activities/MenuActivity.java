package ie.hungr.hungrieapp.activities;

import ie.hungr.hungrieapp.R;
import ie.hungr.hungrieapp.activities.customUI.ItemChild;
import ie.hungr.hungrieapp.activities.customUI.ItemParent;
import ie.hungr.hungrieapp.activities.customUI.MenuExpandableListAdapter;
import ie.hungr.hungrieapp.model.Order;
import ie.hungr.hungrieapp.model.OrderItem;
import ie.hungr.hungrieapp.model.Token;
import ie.hungr.hungrieapp.network.APIHandler;
import ie.hungr.hungrieapp.network.OrderUrl;
import ie.hungr.hungrieapp.network.RestaurantUrl;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.Toast;

public class MenuActivity extends Activity {

	private ExpandableListView mExpandableList;
	private MenuExpandableListAdapter menuAdapter;
	private Button buttonOrder;

	private String restaurantURL;

	// workaround to solve a problem in the webservice API
	private static final String ADDRESS_EXAMPLE = APIHandler.BASE_URL
			+ "/address/1/";

	private static ProgressDialog pdOrdering;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_menu);

		mExpandableList = (ExpandableListView) findViewById(R.id.elvMenus);

		Intent intent = getIntent();
		Bundle info = intent.getExtras();

		restaurantURL = info.getString("RESTAURANT_URL");

		final String[] urls = (String[]) info.get("URLS");

		new GetMenuItemTask().execute(urls);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant, menu);
		return true;
	}

	private class SendOrderInfoTask extends
			AsyncTask<RestaurantUrl, Void, Order> {

		protected void onPreExecute() {
			// show a progress dialog while data is retrieved from the server
			pdOrdering = ProgressDialog.show(MenuActivity.this, "",
					"Processing order...", true);
		}

		protected Order doInBackground(RestaurantUrl... params) {
			Token token = getUserToken(getApplicationContext());
			return APIHandler.createOrder(token, params[0]);
		}

		protected void onPostExecute(Order order) {

			String message = "";
			if (order != null) {
				// successful registration
				message = "Order created";
				String orderUrl = order.getUrl();

				Log.d("ORDER", orderUrl);

				/*
				 * Toast toast = Toast.makeText(getApplicationContext(),
				 * message, Toast.LENGTH_LONG); toast.show();
				 */

				ArrayList<ItemParent> menus = menuAdapter.getListInformation();

				ArrayList<OrderItem> selectedItems = new ArrayList<OrderItem>();
				for (ItemParent menu : menus) {
					ArrayList<ItemChild> menu_items = menu.getChildren();
					for (ItemChild item : menu_items) {
						if (item.getQuantity() > 0) {
							OrderItem orderItem = new OrderItem();
							orderItem.setOrder(orderUrl);
							orderItem.setMenu_item(item.getUrl());
							selectedItems.add(orderItem);
						}
					}
				}

				OrderUrl orderInfo = null;
				for (OrderItem item : selectedItems) {

					orderInfo = new OrderUrl();
					orderInfo.setMenu_item(item.getMenu_item());
					orderInfo.setOrder(orderUrl);

					new SendOrderItemTask().execute(orderInfo);
				}

				new GetOrderTask().execute(orderUrl);

			} else {
				pdOrdering.dismiss();
				message = "An error occurred creating order";
				Toast toast = Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_LONG);
				toast.show();
			}
		}

	}

	private class SendOrderItemTask extends
			AsyncTask<OrderUrl, Void, OrderItem> {

		protected void onPreExecute() {

		}

		protected OrderItem doInBackground(OrderUrl... params) {
			Token token = getUserToken(getApplicationContext());
			return APIHandler.addItem(token, params[0]);
		}

		protected void onPostExecute(OrderItem orderItem) {

			String message = "";
			if (orderItem != null) {
				// successful registration
				message = "Item registered";

			} else {
				pdOrdering.dismiss();
				Log.d("MenuItem", "An error occurred creating order");
				message = "An error occurred creating order";
				Toast toast = Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_LONG);
				toast.show();
			}
		}

	}

	private class GetOrderTask extends AsyncTask<String, Void, Order> {

		protected void onPreExecute() {

		}

		protected Order doInBackground(String... params) {
			Token token = getUserToken(getApplicationContext());
			return APIHandler.getOrder(token, params[0]);
		}

		protected void onPostExecute(Order order) {

			String message = "";
			if (order != null) {
				// successful registration
				message = "Order info retrieved";
				int orderStatus = order.getStatus();

				Log.d("ORDER", "Status=" + orderStatus);

				/*
				 * Toast toast = Toast.makeText(getApplicationContext(),
				 * message, Toast.LENGTH_LONG); toast.show();
				 */

				new UpdateOrderTask().execute(order);
			} else {
				pdOrdering.dismiss();
				Log.d("ORDER", "An error occurred retrieving order info");
				message = "An error occurred retrieving order info";
				Toast toast = Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_LONG);
				toast.show();
			}
		}

	}

	private class UpdateOrderTask extends AsyncTask<Order, Void, Order> {

		protected void onPreExecute() {

		}

		protected Order doInBackground(Order... params) {
			Order order = params[0];

			// change status to "created"
			order.setStatus(1);
			order.setAddress(ADDRESS_EXAMPLE);
			Token token = getUserToken(getApplicationContext());
			return APIHandler.updateOrder(token, order);
		}

		protected void onPostExecute(Order result) {
			// disable the progress dialog
			pdOrdering.dismiss();

			String message = "";
			if (result != null && result.getStatus() == 1) {
				// successful registration
				message = "Order registered. Please wait for confirmation";

				Log.d("ORDER", message);

				Toast toast = Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_LONG);
				toast.show();

				Intent intent = new Intent(MenuActivity.this,
						OrderStatusActivity.class);
				intent.putExtra("TARGET_ORDER", result.getUrl());
				startActivity(intent);

			} else {
				Log.d("ORDER", "An error occurred updating order info");
				message = "An error occurred updating order info";
				Toast toast = Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_LONG);
				toast.show();
			}
		}

	}

	private class GetMenuItemTask extends
			AsyncTask<String, Void, ArrayList<ItemParent>> {

		protected void onPreExecute() {
		}

		protected ArrayList<ItemParent> doInBackground(String... params) {

			ArrayList<ItemParent> categories = new ArrayList<ItemParent>();

			for (int i = 0; i < params.length; i++) {
				ie.hungr.hungrieapp.model.Menu menu = APIHandler
						.getMenu(params[i]);
				ItemParent category = new ItemParent();
				category.setName(menu.getTitle());

				ArrayList<ItemChild> items = new ArrayList<ItemChild>();
				ItemChild item = null;

				for (int z = 0; z < menu.getMenuItems().length; z++) {

					item = new ItemChild();
					item.setName(menu.getMenuItems()[z].getName());
					item.setPrice(Double.parseDouble(menu.getMenuItems()[z]
							.getPrice()));
					item.setUrl(menu.getMenuItems()[z].getUrl());
					items.add(item);
				}
				category.setChildren(items);

				categories.add(category);
			}

			return categories;
		}

		protected void onPostExecute(ArrayList<ItemParent> categories) {
			if (categories != null & !categories.isEmpty()) {
				menuAdapter = new MenuExpandableListAdapter(MenuActivity.this,
						categories);
				mExpandableList.setAdapter(menuAdapter);
				buttonOrder = (Button) findViewById(R.id.orderButton);
				buttonOrder.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						RestaurantUrl rURL = new RestaurantUrl();
						rURL.setRestaurant(restaurantURL);
						new SendOrderInfoTask().execute(rURL);

					}
				});
			} else {
				String message = "No menu info was found";
				Toast toast = Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}

	public static Token getUserToken(Context context) {
		Token token = null;
		// 0 for private mode
		SharedPreferences pref = context.getSharedPreferences("MyPref", 0);
		String strToken = pref.getString("SESSION_TOKEN", "");
		token = new Token();
		token.setToken(strToken);

		return token;
	}

}
