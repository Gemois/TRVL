package com.iee.trvlapp.ui.SupportingClasses;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.iee.trvlapp.databinding.FragmentAboutUsBinding;
import com.iee.trvlapp.databinding.FragmentHelpBinding;

public class AboutUsFragment extends Fragment {
    private FragmentAboutUsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAboutUsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}
