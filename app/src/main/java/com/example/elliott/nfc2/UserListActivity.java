package com.example.elliott.nfc2;
import org.apache.http.NameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Elliott on 10/05/2016.
 */
public class UserListActivity extends ListActivity {

    JSONParser jsonParser = new JSONParser();

    ArrayList<HashMap<String, String>> usersList;

    private static String url_display_user = "http://10.0.3.2/android_connect/display_user.php";

    // JSON Node names
    private static final String TAG_SUCCESS = "success";
    private static final String TAG_MESSAGE = "message";

    private static final String TAG_ID = "id";


    private static final String TAG_USERS = "userlist";

    private static final String TAG_USER = "user";

    //private static final String TAG_NAME = "name";

    // employees JSONArray
    JSONArray users = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_user_list);

        usersList = new ArrayList<HashMap<String, String>>();

        new getUserList().execute();

        // getListView
        ListView lv = getListView();

        lv.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int arg2,
                                    long arg3) {

                //  String id = ((TextView) view.findViewById(R.id.uid)).getText()
                //          .toString();

                // Intent in = new Intent(getBaseContext(), StatusList.class);
                // in.putExtra(TAG_ID, uid);

                // startActivity(in);
            }
        });
    }

    class getUserList extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         * */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            UserListActivity.this.setProgressBarIndeterminateVisibility(true);
        }

        @Override
        protected String doInBackground(String... params) {
            // TODO Auto-generated method stub

            // Building Parameters
            List<NameValuePair> parametres = new ArrayList<NameValuePair>();
            // getting JSON string from URL
            JSONObject json = jsonParser.makeHttpRequest(url_display_user,
                    "GET", parametres);

            // Check your log cat for JSON reponse
            Log.d("All Users: ", json.toString());

            try {
                // Checking for SUCCESS TAG
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // products found
                    // Getting Array of Products
                    users = json.getJSONArray(TAG_USERS);

                    // looping through All Users
                    for (int i = 0; i < users.length(); i++) {
                        JSONObject c = users.getJSONObject(i);

                        // Storing each json item in variable
                        String id = c.getString(TAG_ID);
                        String user = c.getString(TAG_USER);

                        // creating new HashMap
                        HashMap<String, String> map = new HashMap<String, String>();

                        // adding each child node to HashMap key => value
                        map.put(TAG_ID, id);
                        map.put(TAG_USER, user);

                        // adding HashList to ArrayList
                        usersList.add(map);

                        return json.getString(TAG_MESSAGE);
                    }
                } else {

                    return json.getString(TAG_MESSAGE);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * **/
        protected void onPostExecute(String result) {
            // dismiss the dialog after getting all products
            if (result != null) {

                UserListActivity.this
                        .setProgressBarIndeterminateVisibility(false);
                // updating UI from Background Thread
                runOnUiThread(new Runnable() {
                    public void run() {
                        /**
                         * Updating parsed JSON data into ListView
                         * */
//                        ListAdapter adapter = new SimpleAdapter(
//                                UserListActivity.this, usersList,
//                                R.layout.list_item, new String[] { TAG_ID,
//                                TAG_USER }, new int[] { R.id.uid,
//                                R.id.name });
                        // updating listview
                       //
                       // setListAdapter(adapter);
                    }
                });

                Toast.makeText(getBaseContext(), result, Toast.LENGTH_LONG)
                        .show();

            }
        }

    }

}
