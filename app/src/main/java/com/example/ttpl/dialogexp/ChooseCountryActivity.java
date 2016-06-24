package com.example.ttpl.dialogexp;

import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.ttpl.dialogexp.country.Country;
import com.example.ttpl.dialogexp.country.CountryAdapter;
import com.example.ttpl.dialogexp.country.CountryLoader;

import java.util.ArrayList;


public class ChooseCountryActivity extends AppCompatActivity
        implements LoaderManager.LoaderCallbacks<ArrayList<Country>>,
        CountryAdapter.Communicator, SearchView.OnQueryTextListener {
    private static final String DIAL_CODE_EXTRA = "dial_code";
    private CountryAdapter mAdapter;
    private ArrayList<Country> mCountryList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_country);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mCountryList = new ArrayList<>();

        mAdapter = new CountryAdapter(this, mCountryList);
        mRecyclerView.setAdapter(mAdapter);

        getLoaderManager().initLoader(0, null, this);
    }


    @Override
    public Loader<ArrayList<Country>> onCreateLoader(int id, Bundle args) {
        return (new CountryLoader(this));
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Country>> loader, ArrayList<Country> data) {
        mCountryList.addAll(data);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onLoaderReset(Loader<ArrayList<Country>> loader) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setQueryHint(getString(R.string.search));
        searchView.setOnQueryTextListener(this);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void sendDialCode(String dialCode) {
        Intent intent = new Intent();
        intent.putExtra(DIAL_CODE_EXTRA, dialCode);
        setResult(RESULT_OK, intent);
        finish();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (mAdapter != null && mCountryList != null && mCountryList.size() > 0) {
            mAdapter.getFilter().filter(newText);
        }
        return false;
    }
}
