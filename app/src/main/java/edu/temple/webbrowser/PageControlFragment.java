package edu.temple.webbrowser;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PageControlFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PageControlFragment extends Fragment {

    EditText editTextURL;
    ImageButton btnGo;
    ImageButton btnNext;
    ImageButton btnBack;

    String loadText = "ERRR";

    public PageControlFragment() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_page_control, container, false);

        if (savedInstanceState != null) {
            loadText = savedInstanceState.getString("urlText");
        }

        editTextURL = v.findViewById(R.id.editTextURL);
        btnGo = v.findViewById(R.id.btnGo);
        btnBack = v.findViewById(R.id.btnBack);
        btnNext = v.findViewById(R.id.btnNext);
        Toast.makeText(this.getContext(), loadText, Toast.LENGTH_LONG).show();
        if(loadText!=null) editTextURL.setText(loadText);

        btnGo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                ((SendURLInterface) getActivity()).SendURL(editTextURL.getText().toString());
            }

        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((goBackInterface) getActivity()).goBack();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((goNextInterface) getActivity()).goNext();
            }
        });

        return v;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString("urlText", editTextURL.getText().toString());
        super.onSaveInstanceState(outState);
    }

    public void setText(String txt){
        if(editTextURL != null)editTextURL.setText(txt);
    }

    public String getText() { return editTextURL.getText().toString(); }

    interface SendURLInterface {
        void SendURL(String URL);
    }

    interface goBackInterface {
        void goBack();
    }

    interface goNextInterface {
        void goNext();
    }

    interface setURLInterface{
        void setURL();
    }
}