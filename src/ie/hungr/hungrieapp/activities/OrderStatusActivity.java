package ie.hungr.hungrieapp.activities;

import ie.hungr.hungrieapp.R;
import ie.hungr.hungrieapp.model.Order;
import ie.hungr.hungrieapp.model.Token;
import ie.hungr.hungrieapp.network.APIHandler;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class OrderStatusActivity extends Activity {

	private static final long POLLING_PERIOD = 30000;
	private static final long ACCEPTED_POLLING_PERIOD = 30000;

	private String targetOrder = "";

	private boolean waitingDelivered = false;

	private static Timer timer;
	private final Handler handler = new Handler();

	private TextView tvOrderStatus;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order_state);

		targetOrder = getIntent().getStringExtra("TARGET_ORDER");

		tvOrderStatus = (TextView) findViewById(R.id.tvOrderStatus);
		tvOrderStatus.setText("Waiting restaurant response");

		startPolling();

	}

	public void startPolling() {
		timer = new Timer();
		TimerTask pollingTask = new OrderPollingTimerTask();
		timer.schedule(pollingTask, 0, POLLING_PERIOD);
	}

	private class GetOrderTask extends AsyncTask<String, Void, Order> {

		protected void onPreExecute() {

		}

		protected Order doInBackground(String... params) {
			Token token = MenuActivity.getUserToken(getApplicationContext());
			return APIHandler.getOrder(token, params[0]);
		}

		protected void onPostExecute(Order order) {

			String message = "";
			if (order != null) {
				// successful registration
				int orderStatus = order.getStatus();

				Log.d("ORDER", "Status=" + orderStatus);

				if (orderStatus != 1) {
					if (orderStatus == 2 && !waitingDelivered) {
						timer.cancel();
						waitingDelivered = true;
						tvOrderStatus.setText("The order was accepted!");

						message = "Order ACCEPTED";
						timer = new Timer();
						TimerTask pollingTask = new OrderPollingTimerTask();
						timer.schedule(pollingTask, 0, ACCEPTED_POLLING_PERIOD);
					} else if (orderStatus == 3) {
						message = "Order DECLINED";
						tvOrderStatus
								.setText("Sorry, the order wasn't accepted");
						timer.cancel();
					} else if (orderStatus == 4) {
						message = "Order DELIVERED";
						tvOrderStatus.setText("Order delivered!");
						timer.cancel();
					} else if (orderStatus < 2 || orderStatus > 4) {
						tvOrderStatus.setText("Unkown state");
						message = "Unkown state";
						timer.cancel();
					}
					if (!message.equals("")) {
						Toast toast = Toast.makeText(getApplicationContext(),
								message, Toast.LENGTH_LONG);
						toast.show();
					}
				}

			} else {
				Log.d("ORDER", "An error occurred retrieving order info");
				message = "An error occurred retrieving order info";
				Toast toast = Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_LONG);
				toast.show();
			}
		}
	}

	public class OrderPollingTimerTask extends TimerTask {

		@Override
		public void run() {
			handler.post(new Runnable() {

				@Override
				public void run() {
					new GetOrderTask().execute(targetOrder);
				}
			});
		}

	}

}
