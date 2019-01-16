package com.deneme.oguz.dovizzzzz;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    TextView dolarT,euroT,poundT,baslikT,tryT;
    Button guncelleB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dolarT=findViewById(R.id.dolarT);
        euroT=findViewById(R.id.euroT);
        poundT=findViewById(R.id.poundT);
        guncelleB=findViewById(R.id.guncelleB);
        baslikT=findViewById(R.id.baslikT);
        tryT=findViewById(R.id.tryT);

        guncelleB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Innerclass().execute("https://api.exchangeratesapi.io/latest?base=TRY");
            }
        });
    }


    class Innerclass extends AsyncTask<String,Void,String>{
        protected String doInBackground(String ... params){
            HttpURLConnection connection;
            BufferedReader br;

            try{
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream is=connection.getInputStream();
                br=new BufferedReader(new InputStreamReader(is));

                String satir,result="";

                while((satir=br.readLine())!=null){
                    result += satir;
                }
                return result;

            }catch(Exception e){
                euroT.setText("BAS");
                return "Hata";
            }
        }

        protected void onPostExecute(String s){

            super.onPostExecute(s);
            try{
                JSONObject json = new JSONObject(s);
                String asd = json.getString("rates");
                JSONObject json2=new JSONObject(asd);

                String d= json2.getString("USD");
                dolarT.setText("Dolar ="+d);

                d = json2.getString("EUR");
                euroT.setText("Euro ="+d);

                d=json2.getString("GBP");
                poundT.setText("Pound ="+d);

                d=json2.getString("TRY");
                tryT.setText("Turk Lirasi ="+d);


            }catch(Exception e){
                dolarT.setText("A");
            }

        }
    }
}
