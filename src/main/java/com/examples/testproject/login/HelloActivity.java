package com.examples.testproject.login;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

/**
 * Created by Danil on 21.03.2018.
 */

public class HelloActivity extends Activity {

    private Button startBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hello);

        startBtn = (Button) findViewById(R.id.letsStart);

        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                letsStartButtonClicked();
            }
        });
    }

    private void letsStartButtonClicked() {
        startActivity(new Intent(this, MainLogin.class));
    }
}
