
package com.example.julisarrelli.encuestasbeta;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;
import android.widget.Toast;


import com.example.julisarrelli.encuestasbeta.ClasesJava.Platform;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class Loggin extends AppCompatActivity {


    // Progress Dialog
    private ProgressDialog pDialog;

    // Creating JSON Parser object
    //JSONParser jParser = new JSONParser();
    Platform platform=Platform.getInstance();




//    // url to get all products list
//    private static String url_getallusers = "http://julisarrellidb.hol.es/mcconnect/getallusers.php";
//
//    // JSON Node names
//    private static final String TAG_SUCCESS = "success";
//    private static final String TAG_PRODUCTS = "users";
//    private static final String TAG_ID = "iduser";
//    private static final String TAG_USERNAME = "username";
//    private static final String TAG_PASS = "pass";
//    private static final String TAG_TYPE = "type";
//    private static final String TAG_PHOTO = "photo";


    // products JSONArray
    JSONArray products = null;





    @InjectView(R.id.input_username) EditText usernameText;
    @InjectView(R.id.input_password) EditText passText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggin);


        ButterKnife.inject(this);
        // Hashmap para el ListView


        // Cargar los productos en el Background Thread
        //new LoadAllUsers().execute();


        final Button button = (Button) findViewById(R.id.btn_login);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Validate();
            }
        });


        final TextView singup = (TextView) findViewById(R.id.link_signup);
        singup.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              //  Intent intent = new Intent(Loggin.this, SingUp.class);

                //startActivityForResult(intent, 0);
               // finish();
            }
        });


    }


   // class LoadAllUsers extends AsyncTask<String, String, String> {

        /**
         * Antes de empezar el background thread Show Progress Dialog
         */

//       Override protected void onPreExecute() {
//            super.onPreExecute();
//            pDialog = new ProgressDialog(Loggin.this);
//            pDialog.setMessage("Inicializando...");
//            pDialog.setIndeterminate(false);
//            pDialog.setCancelable(false);
//            pDialog.show();
//        }

        /**
         * obteniendo todos los productos
         */
//        protected String doInBackground(String... args) {
//            // Building Parameters
//            List params = new ArrayList();
//            // getting JSON string from URL
//            JSONObject json = jParser.makeHttpRequest(url_getallusers, "GET", params);
//
//            // Check your log cat for JSON reponse
//
//            try {
//                // Checking for SUCCESS TAG
//                int success = json.getInt(TAG_SUCCESS);
//
//                if (success == 1) {
//                    // products found
//                    // Getting Array of Products
//                    products = json.getJSONArray(TAG_PRODUCTS);
//
//                    // looping through All Products
//                    //Log.i("ramiro", "produtos.length" + products.length());
//                    for (int i = 0; i < products.length(); i++) {
//                        JSONObject c = products.getJSONObject(i);
//
//                        // Storing each json item in variable
//                        String id = c.getString(TAG_ID);
//                        String username = c.getString(TAG_USERNAME);
//                        String pass = c.getString(TAG_PASS);
//                        String type = c.getString(TAG_TYPE);
//                        String image= c.getString(TAG_PHOTO);
//
//
//                        byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
//                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
//
//                        // byte[] bytes = ByteBuffer.allocate(Long.SIZE / Byte.SIZE).putLong(image).array();
//
//                        //Bitmap imagebitmap= BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
//                        Drawable drawable = new BitmapDrawable(getResources(), decodedByte);
//
//
//                        //obtenemos todos los usuarios en la base y los cargamos en el hashmap,
//                        //si la base llegase a tener muchos usuarios esto no es conveniente
//                        //ya que le demanda mucho al procesador del telefono
//
//                        User user=new User(username,pass,type,drawable);
//                        platform.addUser(Integer.parseInt(id),user);
//
//
//
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//    }


    public void Validate()
    {


        String username=usernameText.getText().toString();
        String pass=passText.getText().toString();




        if (username.isEmpty()) {
            usernameText.requestFocus();
            usernameText.setError("Ingrese un usuario");
        }
        if (pass.isEmpty()) {
            passText.requestFocus();
            passText.setError("Ingrese su passs");
        }
        else if(platform.ValidateUser(username,pass))
        {
            Intent intent = new Intent(Loggin.this, MainActivity.class);

            startActivityForResult(intent, 0);
            finish();
        }

        else {
            Toast.makeText(getBaseContext(), "Usuario no encontrado", Toast.LENGTH_LONG).show();
        }
    }


}
