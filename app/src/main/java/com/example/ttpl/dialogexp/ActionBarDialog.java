package com.example.ttpl.dialogexp;


import android.app.Dialog;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.ttpl.dialogexp.country.Country;
import com.example.ttpl.dialogexp.country.CountryAdapter;
import com.example.ttpl.dialogexp.country.CountryLoader;

import java.util.ArrayList;

public class ActionBarDialog extends DialogFragment
        implements LoaderManager.LoaderCallbacks<ArrayList<Country>> {
    private static final String DIAL_CODE_EXTRA = "dial_code";
    SearchView searchView;
    private CountryAdapter mAdapter;
    private ArrayList<Country> mCountryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        Bundle args = getArguments();
        String title = args.getString("title");
        View view = inflater.inflate(R.layout.action_bar_dialog, container, false);

        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                // Handle the menu item
                return true;
            }
        });
        toolbar.inflateMenu(R.menu.search);
        toolbar.setTitle(title);


        RecyclerView mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        mCountryList = new ArrayList<>();
        mAdapter = new CountryAdapter(getActivity(), mCountryList);
        mRecyclerView.setAdapter(mAdapter);

        getActivity().getLoaderManager().initLoader(0, null, this);

        return view;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


    @Override
    public Loader<ArrayList<Country>> onCreateLoader(int i, Bundle bundle) {
        return (new CountryLoader(getActivity()));
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Country>> loader, ArrayList<Country> countries) {
        mCountryList.addAll(countries);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Country>> loader) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
//        inflater.inflate(R.menu.search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        searchView.setQueryHint(getString(R.string.search));
        searchView.setOnQueryTextListener(searchQueryListener);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
//                getActivity().onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private SearchView.OnQueryTextListener searchQueryListener = new SearchView.OnQueryTextListener() {
        @Override
        public boolean onQueryTextSubmit(String query) {
            return false;
        }

        @Override
        public boolean onQueryTextChange(String newText) {
            Log.d("Here", "H3");

            if (mAdapter != null && mCountryList != null && mCountryList.size() > 0) {
                mAdapter.getFilter().filter(newText);
            }
            return false;
        }
    };
}