package com.mobileappscompany.training.myfactorialapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private TextView textView;
    private EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.textView);
        editText = (EditText) findViewById(R.id.editText);
    }

    public void calculateFactorial(View view) {
        Intent i = new Intent(this, MyIntentService.class);
        String text = editText.getText().toString();
        if(text.trim().isEmpty()) {
            showToastMessage("Enter a value");
            return;
        }
        long value1 = Long.valueOf(text);
        i.putExtra(MyIntentService.PARAMETER,value1);
        startService(i);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void displayResult(MyEventBusServiceHandler result) {

        if(result == null)
        {
            showToastMessage("There was a problem calculating the factorial number, choose a littler number");
        } else {
            long factorialNumber = result.getResult();

            if(factorialNumber < 0) {
                showToastMessage("Choose a littler number");
            } else
            {
                textView.setText(String.valueOf(factorialNumber));
            }
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    private void showToastMessage(String error) {
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show();
    }
}
