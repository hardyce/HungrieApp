package ie.hungr.hungrieapp.activities;

import ie.hungr.hungrieapp.R;
import ie.hungr.hungrieapp.model.MenuSection;
import ie.hungr.hungrieapp.model.Restaurant;
import ie.hungr.hungrieapp.network.APIHandler;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class RestaurantActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_restaurant);

		final Restaurant[] foundRestaurants = Dashboard.foundRestaurants;

		String[] sampleRestaurantItems;
		if (foundRestaurants != null && foundRestaurants.length > 0) {
			sampleRestaurantItems = new String[foundRestaurants.length];
			for (int i = 0; i < foundRestaurants.length; i++)
				sampleRestaurantItems[i] = foundRestaurants[i].getName();

		} else {
			sampleRestaurantItems = new String[1];
			sampleRestaurantItems[0] = "No restaurants were found";
		}

		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, sampleRestaurantItems);

		ListView restaurantListView = (ListView) findViewById(R.id.lvRestaurants);

		restaurantListView.setAdapter(adapter);

		restaurantListView.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {

				Restaurant chosenRestaurant = foundRestaurants[(int) id];
				Log.d("Restaurant",
						"Chosen restaurant: " + chosenRestaurant.getName());

				new GetMenusTask().execute(chosenRestaurant);

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.restaurant, menu);
		return true;
	}

	private class GetMenusTask extends
			AsyncTask<Restaurant, Void, MenuSection[]> {
		private ProgressDialog pdLoadingMenus;
		private Restaurant chosenRestaurant;

		protected void onPreExecute() {
			// show a progress dialog while data is retrieved from the server
			pdLoadingMenus = ProgressDialog.show(RestaurantActivity.this, "",
					"Retrieving menu information...", true);
		}

		protected MenuSection[] doInBackground(Restaurant... params) {
			chosenRestaurant = params[0];

			return APIHandler.getMenuSections(chosenRestaurant);
		}

		protected void onPostExecute(MenuSection[] menuSections) {
			// disable the progress dialog
			pdLoadingMenus.dismiss();

			// check if data is valid
			if (menuSections != null) {

				int size = menuSections.length;

				String[] categorynames = new String[size];
				String[] urls = new String[size];

				for (int i = 0; i <= size - 1; i++) {

					categorynames[i] = menuSections[i].getTitle();
					urls[i] = menuSections[i].getURL();

				}

				Intent intent = new Intent(getApplicationContext(),
						MenuActivity.class);
				intent.putExtra("Titles", categorynames);
				intent.putExtra("URLS", urls);
				intent.putExtra("RESTAURANT_URL", chosenRestaurant.getUrl());

				startActivity(intent);
			} else {
				Log.d("RestaurantActivity", "No categories were found");
				Toast.makeText(getApplicationContext(),
						"No categories were found", Toast.LENGTH_SHORT).show();
			}

		}
	}

}
