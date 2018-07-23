package com.prism.pickany247.Fragments;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialogFragment;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.prism.pickany247.R;
import com.prism.pickany247.StationeryModule.ProductListActivity;

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

        final String id = getActivity().getIntent().getStringExtra("catId");
        final String title =getActivity().getIntent().getStringExtra("title");


        final RadioGroup radioGroup=(RadioGroup)contentView.findViewById(R.id.radioGroup);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
               dismiss();
                int selectedId=radioGroup.getCheckedRadioButtonId();
                RadioButton radioButton=(RadioButton)contentView.findViewById(selectedId);

                String sortValue ="";
                if (radioButton.getText().equals("Price Low to High")){

                    sortValue=id+"&price="+"low";
                }
                else {

                    sortValue=id+"&price="+"high";
                }

                Intent intent =new Intent(getContext(),ProductListActivity.class);
                intent.putExtra("catId", sortValue);
                intent.putExtra("title", title);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

            }
        });

       // Toast.makeText(getContext(),"sampole",Toast.LENGTH_SHORT).show();
    }

}
