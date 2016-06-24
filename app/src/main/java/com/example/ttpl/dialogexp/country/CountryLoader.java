package com.example.ttpl.dialogexp.country;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.res.AssetManager;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import timber.log.Timber;

public class CountryLoader extends AsyncTaskLoader<ArrayList<Country>> {

    private ArrayList<Country> mData;

    public CountryLoader(Context context) {
        super(context);
    }

    @Override
    public ArrayList<Country> loadInBackground() {
        return loadCountryCSV(getContext());
    }

    @Override
    protected void onStartLoading() {
        if (mData != null)
            deliverResult(mData);

        if (takeContentChanged() || mData == null)
            forceLoad();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
    }

    @Override
    protected boolean onCancelLoad() {
        return super.onCancelLoad();
    }

    @Override
    public void deliverResult(ArrayList<Country> data) {
        if (isReset()) {
            // The Loader has been reset; ignore the result and invalidate the data.
//            releaseResources(data);
            return;
        }

        // Hold a reference to the old data so it doesn't get garbage collected.
        // We must protect it until the new data has been delivered.
        ArrayList<Country> oldData = mData;
        mData = data;

        if (isStarted()) {
            // If the Loader is in a started state, deliver the results to the
            // client. The superclass method does this for us.
            super.deliverResult(data);
        }

        // Invalidate the old data as we don't need it any more.
        if (oldData != null && oldData != data) {
//            releaseResources(oldData);
        }


    }

    String COUNTRIES_CSV = "countries.csv";

    public ArrayList<Country> loadCountryCSV(Context context) {
        ArrayList<Country> countryArrayList = new ArrayList<>();
        try {
            AssetManager assetManager = context.getAssets();
            InputStream csvStream = assetManager.open(COUNTRIES_CSV);
            InputStreamReader csvStreamReader = new InputStreamReader(csvStream);

            BufferedReader bufferedReader = new BufferedReader(csvStreamReader);
            String line;

            while ((line = bufferedReader.readLine()) != null) {

                String[] columns = line.split(",");
                Country country = new Country();
                country.setName(columns[0]);
                country.setId(columns[1]);

                if (columns.length > 2) {
                    if (!TextUtils.isEmpty(columns[2])) {
                        country.setCode(columns[2]);
                        countryArrayList.add(country);
                    }
                }
            }
        } catch (IOException e) {
            Timber.e(e.getLocalizedMessage());
        }
        Timber.d("CountryArrayList = %s", countryArrayList.toString());

        return countryArrayList;
    }
}
