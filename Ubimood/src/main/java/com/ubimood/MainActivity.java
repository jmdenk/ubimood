package com.ubimood;

import android.app.AlertDialog;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.EditText;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.SocketException;

public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void SubmitButton_Click(View view)
    {
        EditText SelfReportEdit = (EditText)this.findViewById(R.id.SelfReportEditText);

        if (SelfReportEdit.getText().toString().isEmpty())
        {
            new AlertDialog.Builder(this).setTitle("Text needed").setMessage("Please enter some text, first.").setNeutralButton("Ok", null).show();

            return;
        }

        //TODO: Query q sensor, ftp all data to server. also need to come up with a data schema
        this.UploadData();
    }

    private void UploadData()
    {
        FTPClient client = new FTPClient();

        try {
            client.connect("1.2.3.4");
            client.login("USERNAME", "PASSWORD");
            String filename = "file1.txt";
            FileInputStream fis = null;
            fis = new FileInputStream(filename);
            client.storeFile(filename, fis);
            client.logout();
            fis.close();
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            Log.i("tag", "SocketException");
            new AlertDialog.Builder(this).setTitle("Upload Failure").setMessage("Failed to upload data: " + e.getMessage().toString()).setNeutralButton("Ok", null).show();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.i("tag", "IOException");
            new AlertDialog.Builder(this).setTitle("Upload Failure").setMessage("Failed to upload data: " + e.getMessage().toString()).setNeutralButton("Ok", null).show();
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }

}
