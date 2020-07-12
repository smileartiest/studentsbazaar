package com.studentsbazaar.studentsbazaarapp.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.studentsbazaar.studentsbazaarapp.CheckUserNumber;
import com.studentsbazaar.studentsbazaarapp.R;
import com.studentsbazaar.studentsbazaarapp.activity.EventActivity;
import com.studentsbazaar.studentsbazaarapp.activity.Login_Page;
import com.studentsbazaar.studentsbazaarapp.activity.MUActivity;
import com.studentsbazaar.studentsbazaarapp.activity.Mems;
import com.studentsbazaar.studentsbazaarapp.activity.PlacementActivity;
import com.studentsbazaar.studentsbazaarapp.activity.Quiz_Events;
import com.studentsbazaar.studentsbazaarapp.activity.Tech_News;
import com.studentsbazaar.studentsbazaarapp.activity.WebActivity;
import com.studentsbazaar.studentsbazaarapp.controller.Controller;
import com.studentsbazaar.studentsbazaarapp.controller.Move_Show;

public class Frag_home extends Fragment {

    View v;
    CardView event, ifacts, auresult, muresult,placement, quiz , memes , ivideos;

    public Frag_home() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.frag_home , container , false);

        event = v.findViewById(R.id.home_event);
        ifacts = v.findViewById(R.id.home_ifacts);
        auresult = v.findViewById(R.id.home_aurs);
        muresult = v.findViewById(R.id.home_mures);
        placement = v.findViewById(R.id.home_placements);
        quiz = v.findViewById(R.id.home_quiz);
        memes = v.findViewById(R.id.home_memes);
        ivideos = v.findViewById(R.id.home_ivideos);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Move_Show(getContext(), EventActivity.class);
            }
        });

        placement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Move_Show(getContext(), PlacementActivity.class);

            }
        });
        ifacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Move_Show(getContext(), Tech_News.class);
            }
        });

        quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Controller.getprefer().equals(Controller.REG) || Controller.getprefer().equals(Controller.ADMIN) || Controller.getprefer().equals(Controller.INFOZUB)) {
                    new Move_Show(getContext(), Quiz_Events.class);
                } else {

                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(getContext());
                    builder.setDialogStyle(CFAlertDialog.CFAlertStyle.NOTIFICATION);
                    builder.setIcon(R.drawable.sb_app_icon_small_theme);
                    builder.setTitle("Hey there , Do Register !");
                    builder.setMessage("We will monitor your score and will give surprise for you.");

                    builder.addButton("LOGIN", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            new Move_Show(getContext(), Login_Page.class);
                        }
                    });

                    builder.addButton("REGISTER", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            startActivity(new Intent(getContext() , CheckUserNumber.class));
                        }
                    });

                    builder.addButton("NOT NOW", -1, -1, CFAlertDialog.CFAlertActionStyle.DEFAULT, CFAlertDialog.CFAlertActionAlignment.JUSTIFIED, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    builder.show();
                }
            }
        });
        memes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Controller.adddesignprefer(Controller.PREFER);
                startActivity(new Intent(getContext() , Mems.class));
            }
        });
        ivideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("url", "http://uniqsolutions.co.in/Admin/Files/Tech_Video.php");
                b.putString("title", "Interesting Videos");
                b.putString("data", "Interesting Videos");
                Intent intEvent = new Intent(getContext(), WebActivity.class);
                intEvent.putExtras(b);
                startActivity(intEvent);

            }
        });

        auresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle b = new Bundle();
                b.putString("url", "https://coe1.annauniv.edu/home/");
                b.putString("title", "AU Results");
                b.putString("data", "AU Results");
                Intent intEvent = new Intent(getContext(), WebActivity.class);
                intEvent.putExtras(b);
                startActivity(intEvent);
            }
        });
        muresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Move_Show(getContext(), MUActivity.class);
            }
        });

    }
}
