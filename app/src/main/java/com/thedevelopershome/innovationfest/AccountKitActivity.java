package com.thedevelopershome.innovationfest;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.thedevelopershome.innovationfest.customfonts.MyEditText;

import com.facebook.accountkit.AccessToken;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import es.dmoral.toasty.Toasty;

public class AccountKitActivity extends AppCompatActivity {


    private String phoneNumberString;
    SharedPreferences sharedPreferences;

    private MyEditText name,rollNo,branch,collageName,email;
    TextView save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_kit);

        sharedPreferences=getSharedPreferences("userData", Activity.MODE_PRIVATE);

        if (sharedPreferences.getInt("dataCheck", 0) == 0){

            AccessToken accessToken = AccountKit.getCurrentAccessToken();

            if (accessToken != null) {
                if(!new OftenFunctions(AccountKitActivity.this).isNetworkAvailable()) {
                    Toast.makeText(this, "NO INTERNET CONNECTION", Toast.LENGTH_SHORT).show();
                }



            } else {
                phoneLogin(getCurrentFocus());
            }

        }


        name=(MyEditText)findViewById(R.id.nameEdit);
        rollNo=(MyEditText)findViewById(R.id.roll);
        branch=(MyEditText)findViewById(R.id.branch);
        collageName=(MyEditText)findViewById(R.id.clgName);
        email=(MyEditText)findViewById(R.id.email);

        save=(TextView)findViewById(R.id.save);

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(name.getText().toString().equals("")){
                    Toast.makeText(AccountKitActivity.this, "you must have some name", Toast.LENGTH_SHORT).show();
                  }
              else
                if(rollNo.getText().toString().equals("")){
                    Toast.makeText(AccountKitActivity.this, "please enter roll no", Toast.LENGTH_SHORT).show();
                }
                if(branch.getText().toString().equals("")){
                    Toast.makeText(AccountKitActivity.this, "you must have some branch", Toast.LENGTH_SHORT).show();
                }
                else
                if(collageName.getText().toString().equals("")){
                    Toast.makeText(AccountKitActivity.this, "you must have some collage name", Toast.LENGTH_SHORT).show();
                }
                else
                if(email.getText().toString().equals("")){
                    Toast.makeText(AccountKitActivity.this, "you must have some email Id", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    String registrationId;
                    try {
                        registrationId = "INC" + name.getText().toString().substring(0, 4).toUpperCase() + sharedPreferences.getString("userphoneno", "XXXXXXXXXX").substring(10, 13);
                    }
                    catch (IndexOutOfBoundsException e){
                        registrationId = "INC" + name.getText().toString().toUpperCase() + sharedPreferences.getString("userphoneno", "XXXXXXXXXX").substring(7, 10);

                    }
                    registrationId=registrationId.replaceAll(" ","_");

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                     editor.putString("name", name.getText().toString());
                    editor.putString("branch", branch.getText().toString());
                    editor.putString("clgName", collageName.getText().toString());
                    editor.putString("email", email.getText().toString());
                    editor.putString("rollNo", rollNo.getText().toString());
                     editor.putString("regId", registrationId);
                    editor.apply();
                    Intent intent=new Intent(AccountKitActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        });

    }

    public static int   APP_REQUEST_CODE = 99;

    public void phoneLogin(final View view) {
        final Intent intent = new Intent(AccountKitActivity.this, com.facebook.accountkit.ui.AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        com.facebook.accountkit.ui.AccountKitActivity.ResponseType.TOKEN); // or .ResponseType.TOKEN
        // ... perform additional configuration
        // ..
        intent.putExtra(
                com.facebook.accountkit.ui.AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, APP_REQUEST_CODE);
    }






    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            String toastMessage;
            if (loginResult.getError() != null) {
                toastMessage = "error in verification";
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled";
            } else {
                if (loginResult.getAccessToken() != null) {
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(final Account account) {
                            String accountKitId = account.getId();
                            PhoneNumber phoneNumber = account.getPhoneNumber();
                            phoneNumberString = phoneNumber.toString();
                            SharedPreferences.Editor editor = sharedPreferences.edit();
                            editor.putString("userphoneno", phoneNumberString);
                            editor.apply();
                            Toast.makeText(AccountKitActivity.this, phoneNumberString, Toast.LENGTH_SHORT).show();

                        }

                        @Override
                        public void onError(final AccountKitError error) {
                            Toast.makeText(AccountKitActivity.this, "error:" + error.getErrorType(), Toast.LENGTH_SHORT).show();
                        }
                    });


                } else {
                    toastMessage = String.format(
                            "Success:%s...",
                            loginResult.getAuthorizationCode().substring(0, 10));

                }

                // If you have an authorization code, retrieve it from
                // loginResult.getAuthorizationCode()
                // and pass it to your server and exchange it for an access token.

                // Success! Start your next activity...
                Toast.makeText(this, "successfully verified", Toast.LENGTH_SHORT).show();
            }

            // Surface the result to your user in an appropriate way.

        }

    }

}
