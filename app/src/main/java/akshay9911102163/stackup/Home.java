package akshay9911102163.stackup;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Home extends Activity {

   //json keys
    private static final String ITEMS_ARRAY = "items";
    private static final String TAGS_ARRAY = "tags";
    private static final String OWNER_OBJ = "owner";
    private static final String KEY_profile_img = "profile_image";
    private static final String KEY_display_name = "display_name";
    private static final String KEY_score = "score";
    private static final String KEY_last_activity_date= "last_activity_date";
    private static final String KEY_link= "link";
    private static final String KEY_title = "title";

    List<FetchedItem> fetchedItemList;



    // json object response url
    private String urlJsonObj = "https://api.stackexchange.com/2.2/search/advanced?order=desc&sort=activity&accepted=False&answers=0&tagged=android&site=stackoverflow";



    private static String TAG = Home.class.getSimpleName();
    private ImageButton btnMakeObjectRequest;

    // Progress dialog
    private ProgressDialog pDialog;

    private TextView txtResponse;

    // temporary string to show the parsed response
    private String jsonResponse;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnMakeObjectRequest = (ImageButton) findViewById(R.id.btnObjRequest);

       // txtResponse = (TextView) findViewById(R.id.txtResponse);
        listView=(ListView)findViewById(R.id.lv_items);


        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        makeJsonObjectRequest();

        btnMakeObjectRequest.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // making json object request
                makeJsonObjectRequest();
            }
        });




    }
    /**
     * Method to make json object request where json response starts wtih {
     * */
    private void makeJsonObjectRequest() {

        showpDialog();

        final JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                urlJsonObj, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                Log.d(TAG, response.toString());

                try {
                    // Parsing json object response
                    // response will be a json object
                    fetchedItemList=new ArrayList<FetchedItem>();
                    JSONArray items= (JSONArray)response.getJSONArray(Home.ITEMS_ARRAY);
                    jsonResponse = "";
                    for (int i = 0; i < items.length(); i++)
                    {

                        JSONObject item = (JSONObject) items.get(i);
                        JSONArray tags=item.getJSONArray(Home.TAGS_ARRAY);
                        List<String> tags_list= new ArrayList<String>();
                        jsonResponse += "Question No.: " + (i+1) + "\n\n";
                        for(int j=0;j<tags.length();j++)
                        {
                            tags_list.add(tags.getString(j));
                            jsonResponse += "Tags:" + (j+1)+ " "+ tags_list.get(j) + "\n\n";
                        }
                        JSONObject owner= item.getJSONObject(Home.OWNER_OBJ);
                        String profile_img = owner.getString(Home.KEY_profile_img);

                        String display_name = owner.getString(Home.KEY_display_name);
                        int score=item.getInt(Home.KEY_score);
                        int ts=item.getInt(Home.KEY_last_activity_date);
                        Date last_activity_date=new Date(ts * 1000L);
                        String dateAsText = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
                                .format(last_activity_date);
                        //Timestamp timestamp=(Timestamp)item.get(Home.KEY_last_activity_date);
                        String link = item.getString(Home.KEY_link);
                        String title=item.getString(Home.KEY_title);


                        jsonResponse += "profile_img: " + profile_img + "\n\n";
                        jsonResponse += "Name: " + display_name + "\n\n";
                        jsonResponse += "Score: " + score + "\n\n";
                        jsonResponse += "dateAsText: " + dateAsText + "\n\n";
                        jsonResponse += "link: " + link + "\n\n";
                        jsonResponse += "Title: " + title + "\n\n\n";

                        FetchedItem fetchedItem=new FetchedItem(profile_img,display_name,score,last_activity_date,link,title,tags_list);
                        fetchedItemList.add(fetchedItem);
                    }

                    //txtResponse.setText(jsonResponse);
                    setListViewWithFecthedItems();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(),
                            "Error: " + e.getMessage(),
                            Toast.LENGTH_LONG).show();
                }
                hidepDialog();
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        error.getMessage(), Toast.LENGTH_SHORT).show();
                // hide the progress dialog
                hidepDialog();
            }
        });

        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq);
    }

    private void setListViewWithFecthedItems() {
        CustomListAdapter customListAdapter=new CustomListAdapter(Home.this,fetchedItemList);
        listView.setAdapter(customListAdapter);
        Toast.makeText(this, "" + fetchedItemList.size() + "" + fetchedItemList.get(0).getTitle(), Toast.LENGTH_LONG).show();
    }



    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }

    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
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
}
