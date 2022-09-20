package com.bluecode.weledger;

import static com.bluecode.weledger.Constants.BASE_URL;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.bluecode.weledger.adapters.MembersAdapter;
import com.bluecode.weledger.models.Members;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class GroupDetailsActivity extends AppCompatActivity {

    RecyclerView members_recyclerview;
    RequestQueue mRequestQueue;
    ArrayList<Members> listMembers = new ArrayList<>();
    Context context;
    String str_a, members_list = BASE_URL+"list_of_group_members.php";
    MembersAdapter membersAdapter;
    TextView group_name_txt,group_id_txt,group_interest_rate_txt,group_date_created_txt;
    ImageView chairperson_approval_img,treasurer_approval_img,secretary_approval_img,prof_img_big;
    CircleImageView prof_img_small;

    String intent_chairperson_approval = "0";
    String intent_treasurer_approval = "0";
    String intent_secretary_approval = "0",img_name;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);
        toolbar = findViewById(R.id.toolbar);
        group_id_txt = findViewById(R.id.group_id);
        group_name_txt = findViewById(R.id.group_name);
        group_interest_rate_txt = findViewById(R.id.interest_rate);
        group_date_created_txt = findViewById(R.id.date_created);
        setSupportActionBar(toolbar);
        ;
        toolbar.setNavigationIcon(androidx.appcompat.R.drawable.abc_ic_ab_back_material);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.slide_out_right);
                Intent intent = new Intent(getApplicationContext(), MembersActivity.class);
                startActivity(intent);
            }
        });

        String intent_group_id = getIntent().getStringExtra("intent_group_id");
        String intent_group_name = getIntent().getStringExtra("intent_group_name");
        String intent_group_date_created = getIntent().getStringExtra("intent_group_date_created");
        String intent_group_annual_interest_rate = getIntent().getStringExtra("intent_group_annual_interest_rate");
        String intent_group_status = getIntent().getStringExtra("intent_group_status");


        group_id_txt.setText(intent_group_id);
        group_name_txt.setText(intent_group_name);
        group_date_created_txt.setText(intent_group_date_created);
        group_interest_rate_txt.setText(intent_group_annual_interest_rate);
        toolbar.setSubtitle(intent_group_name+"'s Details");


    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null;
    }


    public void errorDialog(String error_text) {

        ViewGroup viewGroup = findViewById(android.R.id.content);
        final TextView main_text;
        final Button btn_ok;
        final LinearLayout linear_buttons;
        final CardView card_ok;
        final View dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_error, viewGroup, false);


        btn_ok = dialogView.findViewById(R.id.btn_ok);
//        card_ok = dialogView.findViewById(R.id.card_ok);
        main_text = dialogView.findViewById(R.id.main_text);
        linear_buttons = dialogView.findViewById(R.id.linear_buttons);


        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);
        //finally creating the alert dialog and displaying it
        final AlertDialog reportsAlert = builder.create();
        // Let's start with animation work. We just need to create a style and use it here as follow.
        if (reportsAlert.getWindow() != null)
            reportsAlert.getWindow().getAttributes().windowAnimations = R.style.SlidingDialogAnimation;

        reportsAlert.show();
        reportsAlert.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
        btn_ok.setVisibility(View.VISIBLE);
        linear_buttons.setVisibility(View.GONE);
//        To prevent dialog box from getting dismissed on back key pressed use this
        reportsAlert.setCancelable(false);

//        And to prevent dialog box from getting dismissed on outside touch use this
        reportsAlert.setCanceledOnTouchOutside(false);
        main_text.setText(error_text);

        btn_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reportsAlert.dismiss();
            }
        });


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_stuff, menu);


        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_add) {
            Intent intent = new Intent(getApplicationContext(), FacilitatorNewMemberActivity.class);
            startActivity(intent);
        }



        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {

        finish();
        Intent intent = new Intent(getApplicationContext(),FacilitatorGroupsActivity.class);
        startActivity(intent);
    }
}