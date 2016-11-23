package edu.ggc.king.currencyconverter;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.Scanner;


public class FetchAsyncTask extends AsyncTask<String, Void, Double> {

    private final static String TAG = "CurrConv";
    private TextView rslt;
    private double amt;


    FetchAsyncTask(TextView _result, double _amount) {
        rslt = _result;
        amt = _amount;
    }



    @Override
    protected Double doInBackground(String... strings) {
        Log.i(TAG, "backgrounded, called with " + strings[0] + " " + strings[1]);

        // establish the url
        String from = strings[0];
        String to = strings[1];
        String urlString = "http://api.fixer.io/latest?base=" + from + "&symbols=" + to;
        Log.i(TAG, "url=" + urlString);

        // establish the HttpURLConnection object
        InputStream in = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            in = new BufferedInputStream(conn.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // get input stream and scan it, log result
        Scanner scanner = new Scanner(in);

        StringBuffer sb = new StringBuffer();
        while (scanner.hasNextLine()) {
            sb.append(scanner.nextLine());
        }

        // test to see if status code wasn't 2xx, handle accordingly

        double rate = -1.0D;
        try {

            rate = new JSONObject(sb.toString()).getJSONObject("rates").getDouble(to);
            Log.i(TAG, " rate=" + rate);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Log.i(TAG, "line=" + sb.toString());

        // close resources
        scanner.close();
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // TODO call the long process, and unmarshall results
        return new Double(rate);
    }

    @Override
    protected void onPostExecute(Double aDouble) {
        super.onPostExecute(aDouble);
        DecimalFormat converted = new DecimalFormat("#,####.00");
        rslt.setText(converted.format(amt * aDouble));

    }
}
