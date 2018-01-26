package com.example.makarion.firstapptutorial;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.makarion.firstapptutorial.model.Constants;
import com.example.makarion.firstapptutorial.model.RequestHandler;
import com.example.makarion.firstapptutorial.model.SharedPrefManager;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.Map;

public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextCalories, editTextCarbohydrates, editTextInsulin;
    private TextView textViewUserId;
    private Button buttonAddRecord;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_record);

        if(!SharedPrefManager.getInstance(this).isLoggedIn()){
            finish();
            startActivity(new Intent(getApplicationContext(),LoginActivity.class));
        }

        textViewUserId = (TextView) findViewById(R.id.textViewUserId);
        textViewUserId.setText(SharedPrefManager.getInstance(this).getUserId().toString().trim());

        editTextCalories = (EditText) findViewById(R.id.editTextCalories);
        editTextCarbohydrates = (EditText) findViewById(R.id.editTextCarbohydrates);
        editTextInsulin = (EditText) findViewById(R.id.editTextInsulin);
        buttonAddRecord = (Button) findViewById(R.id.buttonAddRecord) ;


        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Proszę czekać");

        buttonAddRecord.setOnClickListener(this);


    }

    private void addRecord(){

        //final String username = SharedPrefManager.getInstance(this).getUsername();
        final String calories = editTextCalories.getText().toString().trim();
        final String carbohydrates = editTextCarbohydrates.getText().toString().trim();
        final String insulin = editTextInsulin.getText().toString().trim();
        final String patient_id = SharedPrefManager.getInstance(this).getUserId().toString().trim();
       // final String password = SharedPrefManager.getInstance(this).getPassword().toString().trim();

        progressDialog.setMessage("Trwa dodawanie wpisu");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                Constants.URL_ADD_RECORD,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();

                        try {
                            JSONObject jsonObject = new JSONObject(response);

                            Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_LONG).show();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("calories", calories);
                params.put("carbohydrates", carbohydrates);
                params.put("insulin", insulin);
                params.put("patient_id", patient_id);

                return params;
            }
        };

        RequestHandler.getInstance(this).addToRequestQueue(stringRequest);
    }

    public void onClick(View view) {
        if(view == buttonAddRecord){
            addRecord();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu2, menu);
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
            case R.id.menuShowRecords:
                finish();
                startActivity(new Intent(this, ShowRecordsActivity.class));
                break;
        }
        return true;
    }
}
