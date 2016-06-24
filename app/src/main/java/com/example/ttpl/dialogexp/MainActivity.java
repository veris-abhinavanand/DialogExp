package com.example.ttpl.dialogexp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ttpl.dialogexp.country.CountryAdapter;

public class MainActivity extends AppCompatActivity implements CountryAdapter.Communicator {
    private static final int REQUEST_CODE_CHOOSE_COUNTRY = 1234;
    private TextView textViewDialCode;
    private static final String DIAL_CODE_EXTRA = "dial_code";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewDialCode = (TextView) findViewById(R.id.textViewDialCode);
        textViewDialCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ChooseCountryActivity.class);
                startActivityForResult(intent, REQUEST_CODE_CHOOSE_COUNTRY);
            }
        });

        Button dialogButton = (Button) findViewById(R.id
                .dialogButton);
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle args = new Bundle();
                args.putString("title", "Choose country");
                ActionBarDialog actionbarDialog = new ActionBarDialog();
                actionbarDialog.setArguments(args);
                actionbarDialog.show(getSupportFragmentManager(),
                        ActionBarDialog.class.getSimpleName());
            }
        });
    }

    @Override
    public void sendDialCode(String dialCode) {
        Toast.makeText(this, dialCode, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.putExtra(DIAL_CODE_EXTRA, dialCode);
        setResult(RESULT_OK, intent);
//        finish();
    }
}
