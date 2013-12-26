package com.isummon.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.isummon.R;
import com.isummon.data.GlobalVariables;
import com.isummon.model.HDActivity;
import com.isummon.model.HDType;
import com.isummon.widget.ProgressTaskBundle;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 修改活动信息的界面，继承自通用的活动信息编辑界面。
 */
public class ModifyHdActivity extends ActEditActivity {

    /**
     * 通过Intent传入HDActivity实例时的key
     */
    public final static String ACTIVITY = "hdact";

    private HDActivity hdActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        hdActivity = (HDActivity) intent.getSerializableExtra(ACTIVITY);

        EditText actNameEditor = (EditText) findViewById(R.id.actName);
        actNameEditor.setText(hdActivity.getHdName());

        EditText actAddressEditor = (EditText) findViewById(R.id.actPlace);
        actAddressEditor.setText(hdActivity.getHdAddress());

        DateFormat format = new SimpleDateFormat(HDActivity.tmFormat);
        try {
            hdStartDate.setTime(
                    format.parse(hdActivity.getHdStartTime())
            );
            hdEndDate.setTime(
                    format.parse(hdActivity.getHdEndTime())
            );
            hdStartTime.setTime(
                    format.parse(hdActivity.getHdStartTime())
            );
            hdEndTime.setTime(
                    format.parse(hdActivity.getHdEndTime())
            );
        } catch (ParseException e) {
            e.printStackTrace();
        }

        EditText startDateEditor = (EditText) findViewById(R.id.act_start_date);
        startDateEditor.setText(
                getDateRepresentation(
                        hdStartDate,
                        "年","月","日"
                )
        );
        EditText startTimeEditor = (EditText) findViewById(R.id.act_start_time);
        startTimeEditor.setText(
                getTimeRepresentation(
                        hdStartTime,
                        " : "
                )
        );
        EditText endDateEditor = (EditText) findViewById(R.id.act_end_date);
        endDateEditor.setText(
                getDateRepresentation(
                        hdEndDate,
                        "年","月","日"
                )
        );
        EditText endTimeEditor = (EditText) findViewById(R.id.act_end_time);
        endTimeEditor.setText(
                getTimeRepresentation(
                        hdEndTime,
                        " : "
                )
        );

        EditText hdContentEditor = (EditText) findViewById(R.id.actContent);
        hdContentEditor.setText(hdActivity.getHdDesc());

        final ImageView typeImage = (ImageView)findViewById(R.id.act_type_image);
        final TextView typeText = (TextView)findViewById(R.id.act_type_name);

        HDType hdType = hdActivity.getHdTypeInstance();
        typeImage.setImageResource(
                getResources().getIdentifier(
                        "com.isummon:drawable/" + hdType.name().toLowerCase(),
                        null,
                        null
                )
        );
        typeText.setText(hdType.getChn());

        EditText typeEditor = (EditText) findViewById(R.id.act_type_prompt);
        typeEditor.setVisibility(View.GONE);
        typeEditor.setText(hdType.name()); // save type id in the invisible EditText

        findViewById(R.id.act_type_content).setVisibility(View.VISIBLE);
        findViewById(R.id.act_type_content).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypePicker();
            }
        });
        findViewById(R.id.act_type_name).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypePicker();
            }
        });
        findViewById(R.id.act_type_image).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showTypePicker();
            }
        });

        EditText participants = (EditText) findViewById(R.id.act_max_participants);
        participants.setText(hdActivity.getHdNumLimit() + "");
        participants.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_DONE) {
                    EditText editText = (EditText) v;
                    try {
                        int limit = Integer.parseInt(editText.getEditableText().toString());
                        if(limit > hdActivity.getHdCurNum()) {
                            showToast(R.string.error_cut_max);
                        }
                        else {
                            //?
                        }
                    }
                    catch (NumberFormatException e) {
                        showToast(R.string.error_max_not_number);
                    }
                }
                return true;
            }
        });

        EditText propertyEditor = (EditText) findViewById(R.id.act_property);
        propertyEditor.setText(hdActivity.getHdPropertyInstance().getChn());

        Button modifyButton = (Button) findViewById(R.id.act_positive);
        modifyButton.setText(getString(R.string.modify_act));
        modifyButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onModify();
                    }
                }
        );
    }

    private void onModify() {
        if(checkAndPrompt()) {
            new ProgressTaskBundle<HDActivity,Boolean>(
                    this,
                    R.string.delivering
            ) {
                @Override
                protected Boolean doWork(HDActivity... params) {
                    return GlobalVariables.netHelper.modifyHDActivity(params[0]);
                }

                @Override
                protected void dealResult(Boolean result) {
                    if(result){
                        showToast(R.string.modify_success);
                        finish();
                    }
                    else {
                        showToast(R.string.modify_failed);
                    }
                }
            }.action(hdActivity);
        }
    }
}
