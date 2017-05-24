package com.example.android.encuentrame;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;


public class InvitacionFragment extends Fragment implements View.OnClickListener {
    EditText Id;


    public InvitacionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_invitacion, container, false);

        Button bB1=(Button) view.findViewById(R.id.bGenerar);
        bB1.setOnClickListener(this);
        Id = (EditText) view.findViewById(R.id.keyId);
        MainActivityDrawer activity = (MainActivityDrawer) getActivity();

        String recibeDato = activity.getDataFragment();

        if (recibeDato != null) {
            Log.d("datorecibido2 = ", recibeDato);
        }
        return view;
    }


    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onClick(View view) {


        switch (view.getId()) {
            case R.id.bGenerar:

                MainActivityDrawer activity = (MainActivityDrawer) getActivity();

                String recibeDato = activity.getDataFragment();

                if (recibeDato != null) {
                    Log.d("datorecibido2 = ", recibeDato);
                    Id.setText(recibeDato);
                }

                break;

        }
    }
}
