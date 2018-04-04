
package com.examples.testproject.file;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.backendless.Backendless;
import com.examples.testproject.login.R;

public class MainActivity extends Activity {
    private TextView welcomeTextField;
    private TextView urlField;
    private Button takePhotoButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        if (Defaults.APPLICATION_ID.equals("") || Defaults.API_KEY.equals("")) {
            showAlert(this, "Missing application ID and secret key arguments. Login to Backendless Console, select your app and get the ID and key from the Manage - App Settings screen. Copy/paste the values into the Backendless.initApp call");
            return;
        }
        Backendless.setUrl(Defaults.SERVER_URL);
        Backendless.initApp(this, Defaults.APPLICATION_ID, Defaults.API_KEY);

        urlField = (TextView) findViewById(R.id.urlField);
        takePhotoButton = (Button) findViewById(R.id.takePhotoButton);
        takePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, Defaults.CAMERA_REQUEST);
            }
        });
        findViewById(R.id.browseUploadedButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, BrowseActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case Defaults.CAMERA_REQUEST:
                data.setClass(getBaseContext(), UploadingActivity.class);
                startActivityForResult(data, Defaults.URL_REQUEST);
                break;

            case Defaults.URL_REQUEST:
           //     welcomeTextField.setText(getResources().getText(R.string.welcome_text));
                urlField.setText((String) data.getExtras().get(Defaults.DATA_TAG));
                takePhotoButton.setText(getResources().getText(R.string.takeAnotherPhoto));
        }
    }

    public static void showAlert(final Activity context, String message) {
        new AlertDialog.Builder(context).setTitle("An error occurred").setMessage(message).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                context.finish();
            }
        }).show();
    }
}
 