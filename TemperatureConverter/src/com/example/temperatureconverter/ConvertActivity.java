package com.example.temperatureconverter;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class ConvertActivity extends Activity {

    private float mCelsius;
    private float mFahrenheit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convert);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_convert, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_share:
                share();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onConvertClick(View v) {
        RadioButton celsiusButton = (RadioButton) findViewById(R.id.radio0);
        RadioButton fahrenheitButton = (RadioButton) findViewById(R.id.radio1);

        EditText et = (EditText) findViewById(R.id.editText1);
        String inputString = et.getText().toString();

        Toast errToast = Toast.makeText(this, R.string.invalid_number, Toast.LENGTH_LONG);
        if (TextUtils.isEmpty(inputString)) {
            errToast.show();
            return;
        }

        float temperature;
        try {
            temperature = Float.parseFloat(inputString);
        } catch (NumberFormatException e) {
            errToast.show();
            return;
        }

        if (celsiusButton.isChecked()) {
            mFahrenheit = temperature;
            mCelsius = toCelsius(mFahrenheit);
            et.setText(String.valueOf(mCelsius));
            celsiusButton.setChecked(false);
            fahrenheitButton.setChecked(true);
        } else {
            mCelsius = temperature;
            mFahrenheit = toFahrenheit(mCelsius);
            et.setText(String.valueOf(mFahrenheit));
            celsiusButton.setChecked(true);
            fahrenheitButton.setChecked(false);
        }
    }

    private float toCelsius(float fahrenheit) {
        return ((fahrenheit - 32) * 5 / 9);
    }

    private float toFahrenheit(float celsius) {
        return ((celsius * 9) / 5) + 32;
    }

    public void share() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.share_subject));
        intent.putExtra(Intent.EXTRA_TEXT, getString(R.string.share_text, mCelsius, mFahrenheit));
        startActivity(intent);
    }
}
