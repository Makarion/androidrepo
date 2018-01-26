package com.example.makarion.firstapptutorial.model;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.makarion.firstapptutorial.AddRecordActivity;
import com.example.makarion.firstapptutorial.LoginActivity;
import com.example.makarion.firstapptutorial.R;

public class GuestProfileActivity extends AppCompatActivity {

    private TextView textViewUsername, textViewUserEmail, textViewUserId, textViewUserType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest_profile);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        textViewUserId = (TextView) findViewById(R.id.textViewUserId);
        textViewUsername = (TextView) findViewById(R.id.textViewUsername);
        textViewUserEmail = (TextView) findViewById(R.id.textViewUseremail);
        textViewUserType = (TextView) findViewById(R.id.textViewUsertype);


        textViewUserId.setText(SharedPrefManager.getInstance(this).getUserId().toString().trim());
        textViewUserEmail.setText(SharedPrefManager.getInstance(this).getUserEmail());
        textViewUsername.setText(SharedPrefManager.getInstance(this).getUsername());
        textViewUserType.setText(SharedPrefManager.getInstance(this).getUserType());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_guest, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (item.getItemId()) {
            case R.id.menuLogout:
                SharedPrefManager.getInstance(this).logout();
                finish();
                startActivity(new Intent(this, LoginActivity.class));
                break;
        }
        return true;
    }
}
