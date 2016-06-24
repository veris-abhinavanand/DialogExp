package com.example.ttpl.dialogexp.country;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.ttpl.dialogexp.R;


/**
 * Simple view holder for a single text view.
 */
public class CountryViewHolder extends RecyclerView.ViewHolder {

    private TextView mTextView, mDialCode;

    public CountryViewHolder(View view) {
        super(view);

        mTextView = (TextView) view.findViewById(R.id.country_name);
        mDialCode = (TextView) view.findViewById(R.id.dial_code);
    }

    public void bindItem(String text, String dialCode) {
        mTextView.setText(text);

        if (mDialCode != null)
            mDialCode.setText("+" + dialCode);
    }

    @Override
    public String toString() {
        return mTextView.getText().toString();
    }
}
