package com.prism.pickany247;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class InvoiceActivity extends Activity {


    @BindView(R.id.textView8)
    TextView textView8;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.textView10)
    TextView textView10;
    @BindView(R.id.imageView3)
    ImageView imageView3;
    @BindView(R.id.btnContinue)
    Button btnContinue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice);
        ButterKnife.bind(this);


    }

    @Override
    public void onBackPressed() {
        // super.onBackPressed();
        Intent intent = new Intent(InvoiceActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    @OnClick(R.id.btnContinue)
    public void onViewClicked() {
        Intent intent =new Intent(InvoiceActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
