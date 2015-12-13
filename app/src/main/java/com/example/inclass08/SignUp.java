/**
 * In Class 08
 * Group Name- Samir Agrawal, Elijah Jesalva, Martin Miller.
 */
package com.example.inclass08;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends Activity {

	private EditText userName;
	private EditText email;
	private EditText pwd;
	private EditText confirmPwd;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_sign_up);

		userName = (EditText) findViewById(R.id.editTextUserName);
		email = (EditText) findViewById(R.id.editTextEmail);
		pwd = (EditText) findViewById(R.id.editTextPassword);
		confirmPwd = (EditText) findViewById(R.id.editTextPasswordConfirm);

		findViewById(R.id.buttonSignup).setOnClickListener(
				new View.OnClickListener() {

					@Override
					public void onClick(View v) {
						
						if (TextUtils.isEmpty(userName.getText().toString().trim()) || TextUtils.isEmpty(email.getText().toString().trim()) || TextUtils.isEmpty(pwd.getText()) || TextUtils.isEmpty(confirmPwd.getText())) {
							Toast.makeText(SignUp.this, R.string.please_fill_all_the_details, Toast.LENGTH_LONG).show();
						} else if(! android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString().trim()).matches()){
							Toast.makeText(SignUp.this, R.string.please_enter_valid_email_address, Toast.LENGTH_LONG).show();
						}else if(!pwd.getText().toString().equals(confirmPwd.getText().toString())){
							Toast.makeText(SignUp.this, R.string.please_enter_same_password_in_both_the_places, Toast.LENGTH_LONG).show();
						} else {
							signUp(userName.getText().toString().trim(), pwd.getText().toString().trim(), email.getText().toString().trim());
						}

					}
				});
		findViewById(R.id.buttonCancel).setOnClickListener( new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				goBack();
			}
		});
	}

	protected void goBack() {
		Intent intent = new Intent(SignUp.this, LoginActivity.class);
		startActivity(intent);
		finish();
	}
	
	@Override
	public void onBackPressed() {
		super.onBackPressed();
		goBack();
	}

	private void signUp(String username, String password, String email) {
		ParseUser user = new ParseUser();
		user.setUsername(email);
		user.setPassword(password);
		user.setEmail(email);
		user.put("name", username);

		user.signUpInBackground(new SignUpCallback() {
			public void done(ParseException e) {
				if (e == null) {
					// Hooray! Let them use the app now.
					System.out.println("@@ signup success");
					Toast.makeText(SignUp.this, "SignUp successful", Toast.LENGTH_LONG).show();;
					Intent intent = new Intent(SignUp.this, Messaging.class);
					startActivity(intent);
					finish();
				} else {
					// Sign up didn't succeed. Look at the ParseException
					// to figure out what went wrong
					System.out.println("@@Error : " + e.getMessage());
					Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_LONG).show();
				}
			}
		});
	}

}
