package com.example.ellasindi.helloworld;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import java.io.*;
import java.net.*;
public class MainActivity extends AppCompatActivity {
    boolean flag;
    Button b1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        b1=(Button)findViewById(R.id.button);
        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String fname = ((EditText)findViewById(R.id.editText)).getText().toString();
                String sname = ((EditText)findViewById(R.id.editText2)).getText().toString();
                TextView txtView = (TextView) findViewById(R.id.txtView);
                Client myClient = new Client(fname+";"+sname);
                myClient.execute();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //Log.d("FROMSERVER", Boolean.toString(myClient.flag));
                if(flag) {

                    txtView.setText("logged in");
                }
                if(!flag){
                    txtView.setText("Not Correct");
                }
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public class Client extends AsyncTask<Void, Void, Void> {
        String word;
       // boolean flag;
        Client(String word) {
          this.word = word;
        }


        @Override
        protected Void doInBackground(Void... params) {

            String hostName = "205.178.20.186";
            int portNumber = 3003;

            try {
                Socket echoSocket = new Socket(hostName, portNumber);
                PrintWriter out =
                        new PrintWriter(echoSocket.getOutputStream(), true);
                BufferedReader in =
                        new BufferedReader(
                                new InputStreamReader(echoSocket.getInputStream()));
                BufferedReader stdIn =
                        new BufferedReader(
                                new InputStreamReader(System.in));

                String userInput = word;

                out.println(userInput);
                String fromServer = in.readLine();
                Log.d("FROMSERVER", fromServer);
                if (fromServer.equals("correct")) {
                    out.close();
                    in.close();
                    flag = true;
                    Log.d("FROMSERVER","inside correct");
                    Log.d("FROMSERVER", Boolean.toString(flag));
                } else {
                    out.close();
                    in.close();
                    flag= false;
                }

            } catch (UnknownHostException e) {
                System.err.println("Don't know about host " + hostName);
                System.exit(1);
            } catch (IOException e) {
                System.err.println("Couldn't get I/O for the connection to " +
                        hostName);
                System.exit(1);
            }
        return null;
        }
    }
}
