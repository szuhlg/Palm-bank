package com.example.hlg.myapplication;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import shoushimima.ChangePasswordActivity;
import shoushimima.SetPasswordActivity;

/**
 * Created by Cys on 2015/5/13.
 */
public class AnthorFragment4 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_anthor4,container,false);
        root.findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
/*                getFragmentManager()
                        .beginTransaction()
                        .replace(R.id.container, new SetPassword())
                        .commit();*/
                Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
                startActivity(intent);
            }
        });
        root.findViewById(R.id.imageButton55).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new PlaceholderFragment())
                .commit();
            }
        });
        root.findViewById(R.id.imageButton56).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.container,new AnthorFragment())
                .commit();
            }
        });
        root.findViewById(R.id.imageButton57).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new AnthorFragment2())
                .commit();
            }
        });
        root.findViewById(R.id.imageButton58).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new AnthorFragment3())
                .commit();
            }
        });

        return root;
    }
}
