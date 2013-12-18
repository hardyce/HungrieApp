//Date: 22/01/2013
//Written By: Colin and Mat
//Description: Android Login Application

package ie.hungr.hungrieapp;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity
{
	
	private Button buttonLogin;
	private EditText etEmail;
	private EditText etPassword;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
		buttonLogin = (Button) findViewById(R.id.buttonLogin);
		
		etEmail = (EditText) findViewById(R.id.etUserEmail);
		etPassword =  (EditText) findViewById(R.id.etPassword);
		
		buttonLogin.setOnClickListener(new View.OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{	
				String strEmail = etEmail.getText().toString();
				String strPassword = etPassword.getText().toString();
				
				//toDo: do something with this
				boolean isFieldNull = fieldIsNotNull(strEmail, strPassword);				
			}
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_login, menu);
		return true;
	}
	
	public boolean fieldIsNotNull(String email, String password)
	{
		Log.e("asas", "Email = " + email + "/nPassword = " + password);
		
		String pattern = "\\b[_a-z0-9-]+(\\.[_a-z0-9-]+)*@[a-z0-9-]+(\\.[a-z0-9-]+)*(\\.[a-z]{2,4})\\b";
		
		if(email.equals("") || password.equals(""))   //check if either field is empty
		{
			
			Toast toast = Toast.makeText(getApplicationContext(), "Field Empty", Toast.LENGTH_LONG);
			toast.show();
			Log.e("asas", "null input");
			
		}
		else if (email.matches(pattern))	//login successful
		{
			Log.e("","Email fine " + email);
			return true;
		}
		else	//incorrect format
		{	
			Toast toast = Toast.makeText(getApplicationContext(), "Email format Wrong", Toast.LENGTH_LONG);
			toast.show();
			Log.e("asas", "email bad");
		}
		
		return false;
			
	}
}
