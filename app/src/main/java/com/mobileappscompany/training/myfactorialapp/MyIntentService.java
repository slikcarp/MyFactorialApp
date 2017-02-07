package com.mobileappscompany.training.myfactorialapp;

import android.app.IntentService;
import android.content.Intent;

import org.greenrobot.eventbus.EventBus;


public class MyIntentService extends IntentService {

    public static final String PARAMETER = "PARAMETER";

    public MyIntentService() {
        super("MyIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            long number = intent.getLongExtra(PARAMETER, 1l);

            long factorialResult = -1l;
            try {
                factorialResult = getFactorialNumber(number);
            } catch (Exception e) {
                e.printStackTrace();
            }

            EventBus.getDefault().post(new MyEventBusServiceHandler(factorialResult));
        }
    }

    private long getFactorialNumber(long number) throws Exception {
        return calculateFactorialNumber(number, number);
    }

    private long calculateFactorialNumber(long factorialResult, long sequenceNumber) throws Exception {
        sequenceNumber--;
        if(sequenceNumber <= 0) {
            return factorialResult;
        }
        factorialResult *= sequenceNumber;
        if (factorialResult < 1l) {
            throw new Exception();
        }
        return calculateFactorialNumber(factorialResult, sequenceNumber);
    }

}
