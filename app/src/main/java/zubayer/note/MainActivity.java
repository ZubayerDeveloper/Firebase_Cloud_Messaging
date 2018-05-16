package zubayer.note;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.Profile;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.internal.ImageRequest;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import org.json.JSONException;
import org.json.JSONObject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    LoginButton loginButton;
    CallbackManager callbackManager;
    TextView text, email, birthday;
    ImageView imageView;
    GraphRequest request;

    @Override
    public void onActivityReenter(int resultCode, Intent data) {
        super.onActivityReenter(resultCode, data);
        callbackManager.onActivityResult(resultCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
        setContentView(R.layout.activity_main);

        genetatekeyHash();

        loginButton = (LoginButton) findViewById(R.id.login_button);
        text = (TextView) findViewById(R.id.text);
        email = (TextView) findViewById(R.id.email);
        birthday = (TextView) findViewById(R.id.birthday);
        imageView = (ImageView) findViewById(R.id.image);
        callbackManager = CallbackManager.Factory.create();



        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(final LoginResult loginResult) {
                request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        try {
                            text.setText("Facebook id:"+object.getString("id"));
                            email.setText(object.getString("first_name") + " " + object.getString("last_name"));
                            birthday.setText(object.getString("email"));

                            Glide.with(getApplicationContext()).load("https://graph.facebook.com/"+object.getString("id")+"/picture?width=800").into(imageView);
                            Log.d("myresponse", loginResult.getAccessToken().getUserId());
                            Log.d("myresponse", "https://graph.facebook.com/"+object.getString("id")+"/picture?width=800");

                        } catch (JSONException e) {

                            e.printStackTrace();
                        }
                                            }
                });

                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,first_name,last_name,email,picture");
                request.setParameters(parameters);
                request.executeAsync();
            }

            @Override
            public void onCancel() {
                // App code
                text.setText("Login cancelled");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                text.setText("Login error");
            }
        });
    }

    private void genetatekeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "zubayer.note",
                    PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);

    }
}
