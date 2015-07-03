package com.example.hlg.myapplication;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Cys on 2015/5/12.
 */
public class AnthorFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root=inflater.inflate(R.layout.fragment_anthor,container,false);
        root.findViewById(R.id.imageButton25).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new PlaceholderFragment())
                .commit();
            }
        });
        root.findViewById(R.id.imageButton27).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new AnthorFragment2())
                .commit();
            }
        });
        root.findViewById(R.id.imageButton28).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager()
                .beginTransaction()
                .replace(R.id.container, new AnthorFragment3())
                .commit();
            }
        });
        root.findViewById(R.id.imageButton29).setOnClickListener(new View.OnClickListener() {
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
