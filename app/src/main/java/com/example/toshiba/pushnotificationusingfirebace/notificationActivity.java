package com.example.toshiba.pushnotificationusingfirebace;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class notificationActivity extends AppCompatActivity {
 private TextView notificationMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        String message = getIntent().getStringExtra("message");
        String from = getIntent().getStringExtra("from");
        notificationMessage = (TextView)findViewById(R.id.notificationContent);
        notificationMessage.setText("This is Message "+ message + "from :"+from);

    }
}
