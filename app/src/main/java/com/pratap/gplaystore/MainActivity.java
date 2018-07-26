package com.pratap.gplaystore;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.pratap.gplaystore.adapters.RecyclerViewDataAdapter;
import com.pratap.gplaystore.models.Data;
import com.pratap.gplaystore.models.products;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ProgressDialog pDialog;

    List<Data> allSampleData;


    private String TEST_URL="http://webvoot.000webhostapp.com/api/all-categories";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);

        allSampleData = new ArrayList<Data>();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setTitle("G PlayStore");

        }


        testWebService(TEST_URL);








    }


    public void showProgressDialog() {

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);

        if (!pDialog.isShowing())
            pDialog.show();
    }

    public void dismissProgressDialog() {
        if (pDialog != null)
            pDialog.dismiss();

    }


    public void testWebService(String url)
    {
        showProgressDialog();

        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                dismissProgressDialog();

                if (statusCode == 200 && response != null) {
                    Log.i("response-", response.toString());


                    try {
                        JSONArray dataArary= response.getJSONArray("data");



                        for(int i=0;i<dataArary.length();i++)
                        {
                            JSONObject sectionObj= (JSONObject) dataArary.get(i);

                            String title= sectionObj.getString("cat_name");


                            List<products> sections= new ArrayList<products>();


                            JSONArray sectionsArray=sectionObj.getJSONArray("products");

                            for(int j=0;j<sectionsArray.length();j++)
                            {

                                JSONObject obj= (JSONObject) sectionsArray.get(j);


                                products section = new products();

                                section.setName(obj.getString("name"));
                                section.setImages(obj.getString("images"));


                                sections.add(section);
                            }



                            Data data= new Data();

                            data.setCat_name(title);
                            data.setProduct(sections);


                            allSampleData.add(data);




                        }







                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(MainActivity.this,"Parsing Error",Toast.LENGTH_SHORT).show();
                    }


                    // Converst json to Object Model data
                   /* Gson gson = new Gson();
                    Type collectionType = new TypeToken<Data>() {
                    }.getType();
                    allSampleData = gson.fromJson(response.toString(), collectionType);*/


                    // setting data to RecyclerView

                    if(allSampleData!=null) {


                        RecyclerView my_recycler_view = (RecyclerView) findViewById(R.id.my_recycler_view);

                        my_recycler_view.setHasFixedSize(true);

                        RecyclerViewDataAdapter adapter = new RecyclerViewDataAdapter(MainActivity.this, allSampleData);

                        my_recycler_view.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));

                        my_recycler_view.setAdapter(adapter);


                    }




                }
                else {
                    Toast.makeText(MainActivity.this,"Connection Error",Toast.LENGTH_SHORT).show();

                }


            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                dismissProgressDialog();
                Toast.makeText(MainActivity.this,"Connection Error",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                dismissProgressDialog();
                Toast.makeText(MainActivity.this,"Connection Error",Toast.LENGTH_SHORT).show();
            }
        });
    }



   /* public void createDummyData() {
        for (int i = 1; i <= 5; i++) {

            SectionDataModel dm = new SectionDataModel();

            dm.setTitle("products " + i);

            ArrayList<SingleItemModel> singleItem = new ArrayList<SingleItemModel>();
            for (int j = 0; j <= 5; j++) {
                singleItem.add(new SingleItemModel("Item " + j, "URL " + j));
            }

            dm.setSection(singleItem);

            allSampleData.add(dm);

        }
    }*/
}
