package com.example.makarion.firstapptutorial;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.makarion.firstapptutorial.model.Constants;
import com.example.makarion.firstapptutorial.model.GuestProfileActivity;
import com.example.makarion.firstapptutorial.model.RequestHandler;
import com.example.makarion.firstapptutorial.model.SharedPrefManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ShowRecordsActivity extends AppCompatActivity {

   // private TextView textViewCalories, textViewCarbohydrates, textViewInsulin, textViewDate;
    //private TextView textViewCalories1, textViewCarbohydrates1, textViewInsulin1, textViewDate1;
    private TextView [] textViewCaloriesArray = new TextView[10];
    private TextView [] textViewCarbohydratesArray = new TextView[10];
    private TextView [] textViewInsulinArray = new TextView[10];
    private TextView [] textViewDatesArray = new TextView[10];

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_records);

        if (!SharedPrefManager.getInstance(this).isLoggedIn()) {
            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        //textViewCalories = (TextView) findViewById(R.id.textViewCalories);
       // textViewCarbohydrates = (TextView) findViewById(R.id.textViewCarbohydrates);
       // textViewInsulin = (TextView) findViewById(R.id.textViewInsulin);
       // textViewDate = (TextView) findViewById(R.id.textViewDate);

       // textViewCalories1 = (TextView) findViewById(R.id.textViewCalories1);
       // textViewCarbohydrates1 = (TextView) findViewById(R.id.textViewCarbohydrates1);
       // textViewInsulin1 = (TextView) findViewById(R.id.textViewInsulin1);
       // textViewDate1 = (TextView) findViewById(R.id.textViewDate1);

        textViewCaloriesArray[0] = (TextView) findViewById(R.id.textViewCalories);
        textViewCaloriesArray[1] = (TextView) findViewById(R.id.textViewCalories1);
        textViewCaloriesArray[2] = (TextView) findViewById(R.id.textViewCalories2);
        textViewCaloriesArray[3] = (TextView) findViewById(R.id.textViewCalories3);
        textViewCaloriesArray[4] = (TextView) findViewById(R.id.textViewCalories4);
        textViewCaloriesArray[5] = (TextView) findViewById(R.id.textViewCalories5);
        textViewCaloriesArray[6] = (TextView) findViewById(R.id.textViewCalories6);
        textViewCaloriesArray[7] = (TextView) findViewById(R.id.textViewCalories7);
        textViewCaloriesArray[8] = (TextView) findViewById(R.id.textViewCalories8);
        textViewCaloriesArray[9] = (TextView) findViewById(R.id.textViewCalories9);

        textViewCarbohydratesArray[0] = (TextView) findViewById(R.id.textViewCarbohydrates);
        textViewCarbohydratesArray[1] = (TextView) findViewById(R.id.textViewCarbohydrates1);
        textViewCarbohydratesArray[2] = (TextView) findViewById(R.id.textViewCarbohydrates2);
        textViewCarbohydratesArray[3] = (TextView) findViewById(R.id.textViewCarbohydrates3);
        textViewCarbohydratesArray[4] = (TextView) findViewById(R.id.textViewCarbohydrates4);
        textViewCarbohydratesArray[5] = (TextView) findViewById(R.id.textViewCarbohydrates5);
        textViewCarbohydratesArray[6] = (TextView) findViewById(R.id.textViewCarbohydrates6);
        textViewCarbohydratesArray[7] = (TextView) findViewById(R.id.textViewCarbohydrates7);
        textViewCarbohydratesArray[8] = (TextView) findViewById(R.id.textViewCarbohydrates8);
        textViewCarbohydratesArray[9] = (TextView) findViewById(R.id.textViewCarbohydrates9);

        textViewInsulinArray[0] = (TextView) findViewById(R.id.textViewInsulin);
        textViewInsulinArray[1] = (TextView) findViewById(R.id.textViewInsulin1);
        textViewInsulinArray[2] = (TextView) findViewById(R.id.textViewInsulin2);
        textViewInsulinArray[3] = (TextView) findViewById(R.id.textViewInsulin3);
        textViewInsulinArray[4] = (TextView) findViewById(R.id.textViewInsulin4);
        textViewInsulinArray[5] = (TextView) findViewById(R.id.textViewInsulin5);
        textViewInsulinArray[6] = (TextView) findViewById(R.id.textViewInsulin6);
        textViewInsulinArray[7] = (TextView) findViewById(R.id.textViewInsulin7);
        textViewInsulinArray[8] = (TextView) findViewById(R.id.textViewInsulin8);
        textViewInsulinArray[9] = (TextView) findViewById(R.id.textViewInsulin9);

        textViewDatesArray[0] = (TextView) findViewById(R.id.textViewDate);
        textViewDatesArray[1] = (TextView) findViewById(R.id.textViewDate1);
        textViewDatesArray[2] = (TextView) findViewById(R.id.textViewDate2);
        textViewDatesArray[3] = (TextView) findViewById(R.id.textViewDate3);
        textViewDatesArray[4] = (TextView) findViewById(R.id.textViewDate4);
        textViewDatesArray[5] = (TextView) findViewById(R.id.textViewDate5);
        textViewDatesArray[6] = (TextView) findViewById(R.id.textViewDate6);
        textViewDatesArray[7] = (TextView) findViewById(R.id.textViewDate7);
        textViewDatesArray[8] = (TextView) findViewById(R.id.textViewDate8);
        textViewDatesArray[9] = (TextView) findViewById(R.id.textViewDate9);

        getRecords();

    }

    private void getRecords() {
        final String patient_id = SharedPrefManager.getInstance(this).getUserId().toString().trim();

        StringRequest stringRequest = new StringRequest(
                Request.Method.POST,
                Constants.URL_GET_RECORDS,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            //JSONObject obj = jsonArray.getJSONObject(1);
                            for(int i = 0; i <jsonArray.length(); i++) {

                                JSONObject obj = jsonArray.getJSONObject(i);
                                textViewCaloriesArray[i].setText(obj.getString("calories").trim());
                                textViewCarbohydratesArray[i].setText(obj.getString("carbohydrates").trim());
                                textViewInsulinArray[i].setText(obj.getString("insulin").trim());
                                textViewDatesArray[i].setText(obj.getString("date").trim());
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Toast.makeText(
                                getApplicationContext(),
                                error.getMessage(),
                                Toast.LENGTH_LONG
                        ).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("patient_id",patient_id);
                return params;
            }
        };
        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_show, menu);
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
            case R.id.menuBackToProfile:
                finish();
                startActivity(new Intent(this, ProfileActivity.class));
                break;
            case R.id.menuAddRecord:
                finish();
                startActivity(new Intent(this, AddRecordActivity.class));
                break;

        }
        return true;
    }
}