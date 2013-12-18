//Date: 22/01/2013
//Written By: Colin and Mat
//Description: Android Login Application

package ie.hungr.hungrieapp.activities;

import ie.hungr.hungrieapp.R;
import ie.hungr.hungrieapp.model.Token;
import ie.hungr.hungrieapp.model.UserCredentials;
import ie.hungr.hungrieapp.network.APIHandler;
import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {
	private final static String CLASS_NAME = LoginActivity.class
			.getSimpleName();

	private Button buttonLogin;
	private EditText etEmail;
	private EditText etPassword;

	private UserCredentials userCredentials = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// checking if there is already an user session
		if (isUserSession()) {
			launchDashboard();
		} else {
			setContentView(R.layout.activity_login);

			TextView tvSignUpLink = (TextView) findViewById(R.id.tvSignUpLink);
			tvSignUpLink.setOnClickListener(new View.OnClickListener() {

				@Override
				public void onClick(View v) {
					// go-to sign up activity
					Intent intent = new Intent(getApplicationContext(),
							SignUpActivity.class);
					startActivity(intent);
				}
			});

			buttonLogin = (Button) findViewById(R.id.buttonLog);

			etEmail = (EditText) findViewById(R.id.etUserEmail);
			etPassword = (EditText) findViewById(R.id.etPassword);

			buttonLogin.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					String strEmail = etEmail.getText().toString();
					String strPassword = etPassword.getText().toString();

					boolean isValidationPassed = validationPassed(strEmail,
							strPassword);

					if (isValidationPassed) {
						userCredentials = new UserCredentials(strEmail,
								strPassword);

						new LogInUserTask().execute();

					}
				}
			});

		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}

	public boolean validationPassed(String email, String password) {

		if (email.equals("") || password.equals("")) {

			Toast toast = Toast.makeText(getApplicationContext(),
					"Field Empty", Toast.LENGTH_LONG);
			toast.show();

			return false;
		} else {
			return true;
		}
	}

	private void createUserSession(String username, String token) {
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				"MyPref", 0); // 0 - for private mode
		Editor editor = pref.edit();

		editor.putBoolean("SESSION_MANTAIN", true);
		editor.putString("SESSION_USERNAME", username);
		editor.putString("SESSION_TOKEN", token);

		editor.commit();
	}

	private boolean isUserSession() {
		boolean result = false;
		SharedPreferences pref = getApplicationContext().getSharedPreferences(
				"MyPref", 0); // 0 - for private mode

		result = pref.getBoolean("SESSION_MANTAIN", false);

		return result;
	}

	private void launchDashboard() {
		// launch app dashboard
		Intent intent = new Intent(getApplicationContext(), Dashboard.class);
		startActivity(intent);
		finish();
	}

	private class LogInUserTask extends AsyncTask<Void, Void, Token> {
		private ProgressDialog pdLoggingIn;

		protected void onPreExecute() {
			// show a progress dialog while data is retrieved from the server
			pdLoggingIn = ProgressDialog.show(LoginActivity.this, "",
					"Logging in..", true);
		}

		protected Token doInBackground(Void... params) {

			return APIHandler.getAuth(userCredentials);
		}

		protected void onPostExecute(Token token) {
			// disable the progress dialog
			pdLoggingIn.dismiss();

			if (token != null) {
				Log.d(CLASS_NAME, "AUTH SUCCESS TOKEN =" + token.getToken());

				// storing user session
				createUserSession(userCredentials.getUsername(),
						token.getToken());

				launchDashboard();
			} else {
				Log.d(CLASS_NAME, "AUTH NOT SUCCESS");

				Toast toast = Toast.makeText(getApplicationContext(),
						"Error tyring to logging in", Toast.LENGTH_LONG);
				toast.show();
			}

		}
	}
}
