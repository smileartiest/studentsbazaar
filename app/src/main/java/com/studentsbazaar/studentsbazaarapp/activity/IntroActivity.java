package com.studentsbazaar.studentsbazaarapp.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.cuneytayyildiz.onboarder.OnboarderActivity;
import com.cuneytayyildiz.onboarder.OnboarderPage;
import com.cuneytayyildiz.onboarder.utils.OnboarderPageChangeListener;
import com.studentsbazaar.studentsbazaarapp.R;


import java.util.Arrays;
import java.util.List;

public class IntroActivity extends OnboarderActivity implements OnboarderPageChangeListener {
    SharedPreferences spIntroDetails,spFavourite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        spIntroDetails = getSharedPreferences("IntroDetails", Context.MODE_PRIVATE);


        if(!spIntroDetails.getString("log","").isEmpty()){


                Intent i = new Intent(IntroActivity.this,EventActivity.class);
                startActivity(i);
                finish();


        }
        setSkipButtonHidden();


        List<OnboarderPage> pages = Arrays.asList(
                new OnboarderPage.Builder()
                        .title("SWIPE UP !")
                        .description("Swipe Up to view next page ..")
                        .imageResourceId( R.drawable.uparrow)
                        .backgroundColorId(R.color.colorPage1)
                        .titleColorId(R.color.colorBlack)
                        .descriptionColorId(R.color.tab)
                        .multilineDescriptionCentered(true)
                        .build(),

                new OnboarderPage.Builder()
                        .title("SWIPE DOWN !")
                        .description("Swipe Down to view previous page ..")
                        .imageResourceId( R.drawable.swipedown)
                        .backgroundColorId(R.color.colorPage1)
                        .titleColorId(R.color.colorBlack)
                        .descriptionColorId(R.color.tab)
                        .multilineDescriptionCentered(true)
                        .build(),

                new OnboarderPage.Builder()
                        .title("SWIPE RIGHT !")
                        .description("Swipe Right to view menu page ..")
                        .imageResourceId( R.drawable.right256)
                        .backgroundColorId(R.color.colorPage1)
                        .titleColorId(R.color.colorBlack)
                        .descriptionColorId(R.color.tab)
                        .multilineDescriptionCentered(true)
                        .build()

                // No need to write all of them :P



        );
        setOnboarderPageChangeListener(this);
        initOnboardingPages(pages);

    }

    @Override
    public void onFinishButtonPressed() {

        SharedPreferences.Editor editor = spIntroDetails.edit();
        editor.putString("log","seen");
        editor.commit();
        Intent i = new Intent(IntroActivity.this,EventActivity.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onPageChanged(int position) {

    }
}

