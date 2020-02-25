package com.studentsbazaar.studentsbazaarapp.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.studentsbazaar.studentsbazaarapp.controller.Monitor;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.adapter.JobListAdapter;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;
import com.studentsbazaar.studentsbazaarapp.model.Campus;
import com.studentsbazaar.studentsbazaarapp.model.DownloadResponse;
import com.studentsbazaar.studentsbazaarapp.retrofit.ApiUtil;

import java.util.List;

import dmax.dialog.SpotsDialog;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlacementActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private LinearLayout layout;
    private RecyclerView mRecyclerView;
    private JobListAdapter mAdapter;
    EditText dClgName, dCompName, dDomain, dPlaced, dPackage, dDate, dcomments;
    ImageView dCancel;
    Button dSubmit;
    private RecyclerView.LayoutManager mLayoutManager;
    SpotsDialog progressDialog;
    List<Campus> drawerResponseList = null;
    SwipeRefreshLayout mSwipeRefreshLayout;
    private static String URL_DATA = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_placement);
        new Controller(this);
        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeToRefresh);
        layout = (LinearLayout) findViewById(R.id.empty3);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);

        progressDialog = new SpotsDialog(this, R.style.Custom);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {


            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("Placement's");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);


        }


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        URL_DATA = "https://uniqtechnologies.000webhostapp.com/studentsbazaar/Apis/getplacementrecords.php";

        progressDialog.show();

        loadURLData();

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadURLData();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new Move_Show(PlacementActivity.this,HomeActivity.class);
        finish();
    }

    private void loadURLData() {


        //String URL_MY_JOBS = "https://uniqtechnologies.000webhostapp.com/ShopifyDB/Events.php";
        Call<DownloadResponse> call = ApiUtil.getServiceClass().getHomeComponentList(ApiUtil.LOAD_PLACEMENT);
        call.enqueue(new Callback<DownloadResponse>() {
            @Override
            public void onResponse(Call<DownloadResponse> call, Response<DownloadResponse> response) {

                Log.d("RESPONSE", response.raw().toString());

                if (response.isSuccessful()) {


                    assert response.body() != null;
                    drawerResponseList = response.body().getCampus_details();
                    progressDialog.dismiss();
                    if (drawerResponseList.size() == 0) {
                        layout.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.INVISIBLE);
                    } else {
                        layout.setVisibility(View.INVISIBLE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        mAdapter = new JobListAdapter(drawerResponseList, PlacementActivity.this);
                        mRecyclerView.setAdapter(mAdapter);
                    }

                    // mAdapter.notifyDataSetChanged();

                }

            }

            @Override
            public void onFailure(Call<DownloadResponse> call, Throwable t) {
                //showErrorMessage();

                Log.d("RESPONSE", "err" + t.getMessage());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_placement_menu, menu);
        MenuItem shareItem = menu.findItem(R.id.item1);
        MenuItem search = menu.findItem(R.id.action_search);
        menu.findItem(R.id.item2).setVisible(false);
        SearchView searchView = (SearchView) search.getActionView();
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;

            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return false;
            }
        });


        if (Controller.getprefer().equals(Controller.REG) || Controller.getprefer().equals(Controller.VISITOR) || Controller.getprefer().equals(Controller.INFOZUB) || Controller.getprefer().equals(Controller.MEMEACCEPT)) {
            shareItem.setVisible(false);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.shareitem:
                try {
                    new Monitor(this).sharetowhatsapp();
                } catch (Exception e) {

                }

                return true;
            case R.id.item1:
                addJob();
                return true;
            case R.id.action_search:

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void addJob() {
        final Dialog dialog = new Dialog(PlacementActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.add_placement_view);

        dClgName = (EditText) dialog.findViewById(R.id.add_placement_cgname);
        dCompName = (EditText) dialog.findViewById(R.id.add_placement_compname);
        dDate = (EditText) dialog.findViewById(R.id.add_placement_date);
        dDomain = (EditText) dialog.findViewById(R.id.add_placement_department);
        dPackage = (EditText) dialog.findViewById(R.id.add_placement_spackage);
        dPlaced = (EditText) dialog.findViewById(R.id.add_placement_splaced);
        dSubmit = (Button) dialog.findViewById(R.id.complete);
        dcomments = (EditText) dialog.findViewById(R.id.ed_comment);
        dCancel = (ImageView) dialog.findViewById(R.id.add_placement_cancel);
        dClgName.requestFocus();
        dialog.show();
        Window window = dialog.getWindow();
        window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

        dSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addplacememnt();
                dialog.dismiss();
            }
        });

        dCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    void addplacememnt() {
        progressDialog.show();
        Call<String> call = ApiUtil.getServiceClass().addplacements(dDate.getText().toString(), dClgName.getText().toString(), dCompName.getText().toString(), dDomain.getText().toString(), dPackage.getText().toString(), dcomments.getText().toString(), dPlaced.getText().toString());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d("Loadplace", response.body().toString());
                progressDialog.dismiss();
                final AlertDialog.Builder builder = new AlertDialog.Builder(PlacementActivity.this);
                builder.setTitle("Placement Updated Success...");
                builder.setPositiveButton("DONE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        loadURLData();

                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d("onFailure", call.toString());
                progressDialog.dismiss();
            }
        });
    }

}
