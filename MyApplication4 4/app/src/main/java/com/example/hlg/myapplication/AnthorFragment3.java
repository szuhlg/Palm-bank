package com.example.hlg.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Cys on 2015/5/13.
 */
public class AnthorFragment3 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_anthor3,container,false);
        root.findViewById(R.id.imageButton45).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new PlaceholderFragment())
                .commit();
            }
        });
        root.findViewById(R.id.imageButton46).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new AnthorFragment())
                .commit();
            }
        });
        root.findViewById(R.id.imageButton47).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new AnthorFragment2())
                .commit();
            }
        });
        root.findViewById(R.id.imageButton49).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new AnthorFragment4())
                .commit();
            }
        });
        return root;
    }
}
