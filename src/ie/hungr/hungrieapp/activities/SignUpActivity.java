package ie.hungr.hungrieapp.activities;

import ie.hungr.hungrieapp.R;
import ie.hungr.hungrieapp.model.User;
import ie.hungr.hungrieapp.network.APIHandler;
import ie.hungr.hungrieapp.network.ResponseBundle;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUpActivity extends Activity {

	private Button buttonSignUp;
	private EditText etUsername;
	private EditText etPassword;
	private EditText etPassword2;
	private EditText etFirstName;
	private EditText etLastName;
	private EditText etEmail;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);

		etUsername = (EditText) findViewById(R.id.etUserName);
		etPassword = (EditText) findViewById(R.id.etPassword);
		etPassword2 = (EditText) findViewById(R.id.etPassword2);
		etFirstName = (EditText) findViewById(R.id.etFirstName);
		etLastName = (EditText) findViewById(R.id.etLastName);
		etEmail = (EditText) findViewById(R.id.etEmail);

		buttonSignUp = (Button) findViewById(R.id.bSignUp);
		buttonSignUp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				String username = etUsername.getText().toString();
				String password = etPassword.getText().toString();
				String password2 = etPassword2.getText().toString();
				String firstName = etFirstName.getText().toString();
				String lastName = etLastName.getText().toString();
				String email = etEmail.getText().toString();

				// input validation
				if (!username.equals("") && !password.equals("")
						&& !password2.equals("") && !firstName.equals("")
						&& !lastName.equals("") && !email.equals("")) {

					if (password.equals(password2)) {
						// create new user with user input
						User newUser = new User();
						newUser.setUsername(username);
						newUser.setPassword(password);
						newUser.setFirst_name(firstName);
						newUser.setLast_name(lastName);
						newUser.setEmail(email);

						new SignUpUserTask().execute(newUser);

					} else {
						Toast toast = Toast.makeText(getApplicationContext(),
								"Passwords do not match", Toast.LENGTH_LONG);
						toast.show();
					}

				} else {
					Toast toast = Toast.makeText(getApplicationContext(),
							"All fields required", Toast.LENGTH_LONG);
					toast.show();
				}

			}

		});
	}

	private class SignUpUserTask extends AsyncTask<User, Void, ResponseBundle> {
		private ProgressDialog pdSignUp;

		protected void onPreExecute() {
			// show a progress dialog while data is retrieved from the server
			pdSignUp = ProgressDialog.show(SignUpActivity.this, "",
					"Registering user..", true);
		}

		protected ResponseBundle doInBackground(User... params) {

			return APIHandler.signUpUser(params[0]);
		}

		protected void onPostExecute(ResponseBundle response) {
			// disable the progress dialog
			pdSignUp.dismiss();

			String message = "";
			if (response.getResource() != null) {
				// successful registration
				message = "User registered";

				Toast toast = Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_LONG);
				toast.show();

				// launch app dashboard
				Intent intent = new Intent(getApplicationContext(),
						Dashboard.class);
				startActivity(intent);
				finish();
			} else {
				message = response.getErrorMessage();
				Toast toast = Toast.makeText(getApplicationContext(), message,
						Toast.LENGTH_LONG);
				toast.show();
			}
		}

	}
}
