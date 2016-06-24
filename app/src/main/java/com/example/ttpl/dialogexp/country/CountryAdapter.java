package com.example.ttpl.dialogexp.country;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import com.example.ttpl.dialogexp.R;

import java.util.ArrayList;



public class CountryAdapter extends RecyclerView.Adapter<CountryViewHolder>
        implements Filterable {
    private Communicator communicator;
    private ArrayList<Country> mCountryList;
    private ArrayList<Country> mCountryListAll;

    public CountryAdapter(Context context, ArrayList<Country> countryArrayList) {
        mCountryList = countryArrayList;
        mCountryListAll = countryArrayList;
        communicator = (Communicator) context;
    }

    @Override
    public CountryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.country_list_row, parent, false);
        return new CountryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(CountryViewHolder holder, int position) {
        Country country = mCountryList.get(position);
        holder.bindItem(country.getName(), country.getCode());
        View itemView = holder.itemView;
        itemView.setOnClickListener(new ViewOnClickListener(country));
    }

    @Override
    public int getItemCount() {
        return mCountryList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                results.values = getFilteredResults(constraint);
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mCountryList = (ArrayList<Country>) results.values;
                notifyDataSetChanged();
            }
        };
    }

    private ArrayList<Country> getFilteredResults(CharSequence constraint) {

        if (constraint.equals(""))
            return mCountryListAll;

        ArrayList<Country> filteredCountries = new ArrayList<>();
        for (Country c : mCountryListAll) {
            if (c.getName().toUpperCase().startsWith(((String) constraint).toUpperCase())) {
                filteredCountries.add(c);
            }
        }

        for (Country c : mCountryListAll) {
            if (!c.getName().toUpperCase().startsWith(((String) constraint).toUpperCase()) &&
                    c.getName().toUpperCase().contains(((String) constraint).toUpperCase())) {
                filteredCountries.add(c);
            }
        }
        return filteredCountries;
    }

    public interface Communicator {
        void sendDialCode(String dialCode);
    }

    private class ViewOnClickListener implements View.OnClickListener {
        Country mCountry;

        ViewOnClickListener(Country country) {
            mCountry = country;
        }

        @Override
        public void onClick(View v) {
            communicator.sendDialCode(mCountry.getCode());
        }
    }
}
