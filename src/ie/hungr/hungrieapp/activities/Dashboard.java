package ie.hungr.hungrieapp.activities;

import ie.hungr.hungrieapp.R;
import ie.hungr.hungrieapp.model.Restaurant;
import ie.hungr.hungrieapp.network.APIHandler;

import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Dashboard extends Activity {

	private final static String CLASS_NAME = Dashboard.class.getSimpleName();

	public static Restaurant[] foundRestaurants = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dashboard);

		Button bBrowseAllMenus = (Button) findViewById(R.id.btBrowseAllRestaurants);
		bBrowseAllMenus.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				new GetAllRestaurantTask().execute();
			}
		});

		Button bSearch = (Button) findViewById(R.id.btBrowseRestaurantsNearby);
		bSearch.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				Location userLocation = getUserLocation();
				if (userLocation != null) {
					String lat = Double.toString((userLocation.getLatitude()));
					String lon = Double.toString(userLocation.getLongitude());

					Log.d(CLASS_NAME, "Lat = " + lat + "Long = " + lon);

					new GetRestaurantsNearbyTask().execute(lat, lon);
				} else {
					Log.d(CLASS_NAME, "User location not found");
					Toast.makeText(getApplicationContext(),
							"User location not found", Toast.LENGTH_SHORT)
							.show();
				}

			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_dashboard, menu);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.mLogOut:
			SharedPreferences pref = getApplicationContext()
					.getSharedPreferences("MyPref", 0); // 0 - for private mode
			Editor editor = pref.edit();

			editor.clear();

			editor.commit();

			Intent intent = new Intent(getApplicationContext(),
					LoginActivity.class);
			startActivity(intent);
			finish();

			return true;

		default:
			return super.onOptionsItemSelected(item);

		}
	}

	public Location getUserLocation() {
		Location l = null;

		// getting list of providers
		LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		List<String> providers = lm.getProviders(true);

		// going backwards (GPS first, then Network) looking for a location
		for (int i = providers.size() - 1; i >= 0; i--) {
			l = lm.getLastKnownLocation(providers.get(i));
			if (l != null)
				break;
		}

		return l;
	}

	private class GetAllRestaurantTask extends
			AsyncTask<Void, Void, Restaurant[]> {
		private ProgressDialog pdLoadingRestaurants;

		protected void onPreExecute() {
			// show a progress dialog while data is retrieved from the server
			pdLoadingRestaurants = ProgressDialog.show(Dashboard.this, "",
					"Retrieving restaurant information...", true);
		}

		protected Restaurant[] doInBackground(Void... params) {
			// retrieving all restaurants
			return APIHandler.getRestaurant();
		}

		protected void onPostExecute(Restaurant[] restaurants) {
			// disable the progress dialog
			pdLoadingRestaurants.dismiss();

			// check if data is valid
			if (restaurants != null && restaurants.length > 0) {
				// save the restaurant to being accesible from next activity
				foundRestaurants = restaurants;
				// go-to Restaurant viewer activity
				Intent intent = new Intent(getApplicationContext(),
						RestaurantActivity.class);
				startActivity(intent);
			} else {
				Log.d(CLASS_NAME, "No restaurants were found");
				Toast.makeText(getApplicationContext(),
						"No restaurants were found", Toast.LENGTH_SHORT).show();
			}

		}
	}

	private class GetRestaurantsNearbyTask extends
			AsyncTask<String, Void, Restaurant[]> {
		private ProgressDialog pdLoadingRestaurants;

		protected void onPreExecute() {
			// show a progress dialog while data is retrieved from the server
			pdLoadingRestaurants = ProgressDialog.show(Dashboard.this, "",
					"Getting restaurants", true);
		}

		protected Restaurant[] doInBackground(String... params) {
			String latitude = params[0];
			String longitude = params[1];

			// retrieving all restaurants
			return APIHandler.getRestaurantByLocation(latitude, longitude);
		}

		protected void onPostExecute(Restaurant[] restaurants) {
			// disable the progress dialog
			pdLoadingRestaurants.dismiss();

			// check if data is valid
			if (restaurants != null && restaurants.length > 0) {
				// save the restaurant to being accesible from next activity
				foundRestaurants = restaurants;
				// go-to Restaurant viewer activity
				Intent intent = new Intent(getApplicationContext(),
						RestaurantActivity.class);
				startActivity(intent);
			} else {
				Log.d(CLASS_NAME, "No restaurants were found");
				Toast.makeText(getApplicationContext(),
						"No restaurants were found", Toast.LENGTH_SHORT).show();
			}

		}
	}

}
