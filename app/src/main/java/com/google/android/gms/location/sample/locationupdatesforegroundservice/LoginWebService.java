package com.google.android.gms.location.sample.locationupdatesforegroundservice;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

public class LoginWebService extends AsyncTask<String, String, Boolean> {

    Context ctx;
    Boolean retorno = false;
    private String nome;
    private String id;
    private String token;
    private EditText login;
    private EditText senha;

    public LoginWebService() {
    }

    @Override
    protected Boolean doInBackground(String... arg0) {

        HttpClient httpclient;
        HttpPost httppost = new HttpPost(arg0[0]);
        HttpParams httpParameters = new BasicHttpParams();
        httpclient = new DefaultHttpClient(httpParameters);

        try {
            StringEntity params =new StringEntity(arg0[1]);
            httppost.addHeader("content-type", "application/json");
            httppost.addHeader("Accept","application/json");
            httppost.setEntity(params);
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity httpEntity = response.getEntity();

            int statusCode = response.getStatusLine().getStatusCode();
            String message = response.getStatusLine().getReasonPhrase();
            String serverResponse = EntityUtils.toString(httpEntity);

            /**
             * Fetching response from the server
             */

            if(statusCode == 401) {
                if(message.equals("Unauthorized")){
                    MainActivity.getInstance().runOnUiThread(new Runnable() {
                        public void run() {
                            Toast.makeText(MainActivity.getInstance(), "Usuário ou senha inválidos.", Toast.LENGTH_LONG).show();
                        }
                    });
                }

                this.retorno = false;
            }

            if(statusCode == 200 && message.equals("OK")) {
                JSONObject object = new JSONObject(serverResponse);
                JSONObject tokenAtivo = new JSONObject(object.getString("token"));

                this.nome = object.getString("nome");
                this.id = object.getString("id");
                this.token = tokenAtivo.getString("token");
                this.login = MainActivity.edtLogin;
                this.senha = MainActivity.edtSenha;

                Motorista motorista = new Motorista();
                motorista.setNome(object.getString("nome"));
                motorista.setId(Integer.parseInt(object.getString("id")));
                motorista.setToken(tokenAtivo.getString("token"));
                motorista.setLogin(String.valueOf(this.login));
                motorista.setSenha(String.valueOf(this.senha));

                this.retorno = true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return this.retorno;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        if (result) {
            Intent myIntent = new Intent(MainActivity.getInstance(), RastreamentoActivity.class);
            myIntent.putExtra("nome", this.nome);
            myIntent.putExtra("id", this.id);
            myIntent.putExtra("token", this.token);
            myIntent.putExtra("login", String.valueOf(this.login));
            myIntent.putExtra("senha", String.valueOf(this.senha));
            MainActivity.getInstance().startActivity(myIntent);
        }
    }
}