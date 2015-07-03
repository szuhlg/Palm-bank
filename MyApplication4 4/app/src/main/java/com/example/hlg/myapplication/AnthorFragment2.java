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
public class AnthorFragment2 extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_anthor2,container,false);
        root.findViewById(R.id.imageButton35).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new PlaceholderFragment())
                .commit();
            }
        });
        root.findViewById(R.id.imageButton36).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new AnthorFragment())
                .commit();
            }
        });
        root.findViewById(R.id.imageButton38).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new AnthorFragment3())
                .commit();
            }
        });
        root.findViewById(R.id.imageButton39).setOnClickListener(new View.OnClickListener() {
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
