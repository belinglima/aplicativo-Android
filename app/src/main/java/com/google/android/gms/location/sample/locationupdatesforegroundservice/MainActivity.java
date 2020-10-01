package com.google.android.gms.location.sample.locationupdatesforegroundservice;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    private static MainActivity _instance;
    public static EditText edtLogin;
    public static EditText edtSenha;
    private Button btnEntrar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        _instance = this;
        setContentView(R.layout.activity_main);

        edtLogin = (EditText)findViewById(R.id.edtLogin);
        edtSenha = (EditText)findViewById(R.id.edtSenha);
        btnEntrar = (Button)findViewById(R.id.btnEntrar);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (edtLogin.getText().toString().trim().equals("") || edtSenha.getText().toString().trim().equals("") ) {
                    MainActivity.getInstance().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.getInstance(), "Os campos não podem ser em branco.", Toast.LENGTH_LONG).show();
                        }
                    });
                    return;
                }

                String login = edtLogin.getText().toString();
                String senha = edtSenha.getText().toString();

                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("login", login);
                    jsonObject.put("hash_senha", senha);

                    new LoginWebService().execute(
                            Constants.TRACK_URL+"/sessions",
                            jsonObject.toString()
                    );

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public static MainActivity getInstance() {
        return _instance;
    }
}
