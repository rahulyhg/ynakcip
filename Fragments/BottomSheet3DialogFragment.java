package com.prism.pickany247.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.prism.pickany247.R;

public class BottomSheet3DialogFragment extends BottomSheetDialogFragment {

    private BottomSheetBehavior.BottomSheetCallback
            mBottomSheetBehaviorCallback = new BottomSheetBehavior.BottomSheetCallback() {
        @Override
        public void onStateChanged(@NonNull View bottomSheet, int newState) {
            if (newState == BottomSheetBehavior.STATE_HIDDEN) {
                dismiss();
            }
        }

        @Override
        public void onSlide(@NonNull View bottomSheet, float slideOffset) {
        }
    };

    @SuppressLint("RestrictedApi")
    @Override
    public void setupDialog(final Dialog dialog, int style) {
        super.setupDialog(dialog, style);
        final View contentView = View.inflate(getContext(), R.layout.fragment_bottomsheet3, null);
        dialog.setContentView(contentView);

        final RadioGroup radioGroup=(RadioGroup)contentView.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                int selectedId=radioGroup.getCheckedRadioButtonId();
                RadioButton radioSexButton=(RadioButton)contentView.findViewById(selectedId);
                Toast.makeText(getContext(),radioSexButton.getText(),Toast.LENGTH_SHORT).show();
            }
        });

       // Toast.makeText(getContext(),"sampole",Toast.LENGTH_SHORT).show();
    }

}
