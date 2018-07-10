package com.prism.pickany247.MobilesModule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.prism.pickany247.HomeActivity;
import com.prism.pickany247.R;
import com.prism.pickany247.Singleton.AppController;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MobileHomeActivity extends AppCompatActivity {
    AppController appController;
    @BindView(R.id.txtNewMobile)
    TextView txtNewMobile;
    @BindView(R.id.txtNewView)
    TextView txtNewView;
    @BindView(R.id.recyclerNew)
    RecyclerView recyclerNew;
    @BindView(R.id.txtUsedMobile)
    TextView txtUsedMobile;
    @BindView(R.id.txtUsedView)
    TextView txtUsedView;
    @BindView(R.id.recyclerUsed)
    RecyclerView recyclerUsed;
    @BindView(R.id.txtTablet)
    TextView txtTablet;
    @BindView(R.id.txtTabletView)
    TextView txtTabletView;
    @BindView(R.id.recyclerSchool)
    RecyclerView recyclerSchool;
    @BindView(R.id.txtAccessories)
    TextView txtAccessories;
    @BindView(R.id.txtAccessoriesView)
    TextView txtAccessoriesView;
    @BindView(R.id.recyclerAccessories)
    RecyclerView recyclerAccessories;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbile_home);
        ButterKnife.bind(this);

        simpleSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorBlue,R.color.colorPrimary);


        if (appController.isConnection()) {

           // prepareCategoeriesData();

            simpleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                   // prepareCategoeriesData();
                    simpleSwipeRefreshLayout.setRefreshing(false);
                }
            });


        } else {

            setContentView(R.layout.internet);

            Button tryButton =(Button)findViewById(R.id.btnTryagain);
            tryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            // app.internetDilogue(KitchenitemListActivity.this);

        }


    }

    @OnClick({R.id.txtNewView, R.id.txtUsedView, R.id.txtTabletView, R.id.txtAccessoriesView})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.txtNewView:

                break;
            case R.id.txtUsedView:
                break;
            case R.id.txtTabletView:
                break;
            case R.id.txtAccessoriesView:
                break;
        }
    }
}
