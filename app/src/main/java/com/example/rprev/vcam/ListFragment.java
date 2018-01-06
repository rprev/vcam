package com.example.rprev.vcam;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by ravy on 2018/01/05.
 */

public class ListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    private TextView mTextView;
    private Button buttonGet;
    private TextView textViewResult;

    // Viewが生成し終わった時に呼ばれるメソッド
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textViewResult = view.findViewById(R.id.textViewResult);

        view.findViewById(R.id.buttonGet).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences prefer = getContext().getSharedPreferences("SaveData", MODE_PRIVATE);

                String url="https://coincheck.com/api/accounts/balance";

                HttpResponseAsync hra = new HttpResponseAsync(url);
                hra.setListener(createListener());

                CoinCheckAccessor.setHeader(hra,url,prefer.getString("a",""),prefer.getString("s",""));
                hra.execute("");
            }
        });

    }

    private HttpResponseAsync.Listener createListener() {
        return new HttpResponseAsync.Listener() {
            @Override
            public void onSuccess(String s) throws JSONException {
//                JSONObject jo = new JSONObject(s);
  //              textViewResult.setText(jo.getString("jpy"));
                textViewResult.setText(s);

            }
        };
    }




}
