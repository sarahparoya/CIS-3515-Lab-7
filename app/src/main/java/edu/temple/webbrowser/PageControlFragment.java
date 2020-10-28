package edu.temple.webbrowser;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;


public class PageControlFragment extends Fragment {

    EditText editTextURL;
    ImageButton btnGo;
    ImageButton btnRight;
    ImageButton btnLeft;

    public PageControlFragment() {

    }

    public static PageControlFragment newInstance() {
        PageControlFragment fragment = new PageControlFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_page_control, container, false);

        editTextURL = v.findViewById(R.id.editURL);
        btnGo = v.findViewById(R.id.btnGo);
        btnLeft = v.findViewById(R.id.btnLeft);
        btnRight = v.findViewById(R.id.btnRight);

        btnGo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ((SendURLInterface) getActivity()).SendURL(editTextURL.getText().toString());
            }

        });

        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((goBackInterface) getActivity()).goBack();
            }
        });

        btnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((goNextInterface) getActivity()).goNext();
            }
        });

        return v;
    }

    public void setText(String txt){
        editTextURL.setText(txt);
    }

    interface SendURLInterface {
        void SendURL(String URL);
    }

    interface goBackInterface {
        void goBack();
    }

    interface goNextInterface {
        void goNext();
    }


}