package com.example.r4_11_devmobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AProposFragment extends Fragment {

    private View view;

    public AProposFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_a_propos, container, false);


        return inflater.inflate(R.layout.fragment_a_propos, container, false);
    }
}