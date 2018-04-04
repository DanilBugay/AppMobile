
package com.examples.testproject.login;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.async.callback.BackendlessCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.files.BackendlessFile;

import java.io.File;

public class RegisterActivity extends Activity {
    private final static java.text.SimpleDateFormat SIMPLE_DATE_FORMAT = new java.text.SimpleDateFormat("yyyy/MM/dd");

    private EditText nameField;
    private EditText passwordField;
    private EditText emailField;
    private EditText ageField;
    private EditText countryField;
    private Button registerButton;
    private RadioGroup radioGroup;
    private RadioButton radioButton;

    private String name;
    private String password;
    private String email;
    private Integer age;
    private String country;
    private Integer selRadioId;
    private String gender;

    private BackendlessUser user;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initUI();
    }

    private void initUI() {
        nameField = (EditText) findViewById(R.id.nameField);
        emailField = (EditText) findViewById(R.id.emailField);
        passwordField = (EditText) findViewById(R.id.passwordField);
        registerButton = (Button) findViewById(R.id.registerButton);
        ageField = (EditText) findViewById(R.id.ageField);
        countryField = (EditText) findViewById(R.id.countryField);
        radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
        selRadioId = radioGroup.getCheckedRadioButtonId();
        radioButton = (RadioButton) findViewById(selRadioId);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    onRegisterButtonClicked();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void onRegisterButtonClicked() throws Exception {
        String nameText = nameField.getText().toString().trim();
        String emailText = emailField.getText().toString().trim();
        String passwordText = passwordField.getText().toString().trim();
        Integer ageText = Integer.valueOf(ageField.getText().toString().trim());
        String countryText = countryField.getText().toString().trim();
        String radioText = radioButton.getText().toString().trim();

        if (nameText.isEmpty()) {
            Toast.makeText(this, "Field 'name' cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        } else
            name = nameText;

        if (passwordText.isEmpty()) {
            Toast.makeText(this, "Field 'password' cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        } else
            password = passwordText;

        if (emailText.isEmpty()) {
            Toast.makeText(this, "Field 'email' cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        } else
            email = emailText;

        if (ageText < 5) {
            Toast.makeText(this, "Grow up first", Toast.LENGTH_SHORT).show();
            return;
        } else
            age = ageText;

        if (countryText.isEmpty()) {
            Toast.makeText(this, "Field 'country' cannot be empty.", Toast.LENGTH_SHORT).show();
            return;
        } else
            country = countryText;

        if (radioText.equals("genderMale")) {
            gender = "Male";
        } else
            gender = "Female";

        final BackendlessUser user = new BackendlessUser();

        if (email != null) {
            user.setEmail(email);
        }

        if (password != null) {
            user.setPassword(password);
        }

        if (name != null) {
            user.setProperty("login", name);
        }

        if (age != null) {
            user.setProperty("age", age);
        }
        if (gender != null) {
            user.setProperty("gender", gender);
        }
        if (country != null) {
            user.setProperty("country", country);
        }


        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
            @Override
            public void handleResponse(BackendlessUser response) {
                Resources resources = getResources();
                String message = String.format(resources.getString(R.string.registration_success_message), resources.getString(R.string.app_name));

                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage(message).setTitle(R.string.registration_success);
                AlertDialog dialog = builder.create();
                dialog.show();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                letsgo();
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                builder.setMessage(fault.getMessage()).setTitle(R.string.registration_error);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        byte[] bytes = "TestMess".getBytes();
        String uLogin = (String) user.getProperty("login");
        String fPath = uLogin + "//shared with me";
        Backendless.Files.saveFile(fPath, "test.txt", bytes, true, new AsyncCallback<String>() {
            @Override
            public void handleResponse(String response) {
            }

            @Override
            public void handleFault(BackendlessFault fault) {
            }
        });

    }

    private void letsgo() {
        startActivity(new Intent(this, MainLogin.class));
    }
}

