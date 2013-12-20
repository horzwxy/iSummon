package com.isummon.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.isummon.R;
import com.isummon.model.HDActivity;
import com.isummon.model.HDStatus;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by horzwxy on 12/18/13.
 */
public class ShowHdDetailActivity extends Activity {

    public static final String HDACTIVITY = "hdActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_show_act);

        getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg_black);

        Intent intent = getIntent();
        HDActivity hdActivity = (HDActivity) intent.getSerializableExtra(HDACTIVITY);

        setText(R.id.actName, R.string.act_name_prompt, hdActivity.getHdName());
        setText(R.id.actPlace, R.string.act_place_prompt, hdActivity.getHdAddress());
        DateFormat s2Date = new SimpleDateFormat(HDActivity.tmFormat);
        DateFormat date2s = new SimpleDateFormat("yyyy年MM月dd日  HH : mm");
        try {
            Date startDate = s2Date.parse(hdActivity.getHdStartTime());
            Date endDate = s2Date.parse(hdActivity.getHdEndTime());
            setText(R.id.add_act_start_time,
                    R.string.act_start_time_prompt,
                    date2s.format(startDate));
            setText(R.id.add_act_end_time,
                    R.string.act_end_time_prompt,
                    date2s.format(endDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        setText(R.id.actContent, R.string.act_content_prompt, hdActivity.getHdDesc());
        setText(R.id.act_participants, R.string.act_participants_prompt, hdActivity.getHdCurNum() + "/" + hdActivity.getHdNumLimit());
        setText(R.id.act_property, R.string.act_property_prompt, hdActivity.getHdProperty().getChn());

        ImageView typeImage = (ImageView)findViewById(R.id.act_type_image);
        TextView typeText = (TextView)findViewById(R.id.act_type_name);
        typeImage.setImageResource(hdActivity.getHdType().getDrawableId());
        typeText.setText(hdActivity.getHdType().getChn());

        if(hdActivity.getHdStatus() == HDStatus.CANCELED
                || hdActivity.getHdStatus() == HDStatus.NO_VACANCY) {
            Button applyButton = (Button) findViewById(R.id.apply_in);
            applyButton.setBackground(
                    getResources().getDrawable(R.drawable.button_bg_disabled));
            applyButton.setText(R.string.not_available);
            applyButton.setEnabled(false);
        }
    }

    private void setText(int viewId, int hintStringId, String content) {
        TextView editText = (TextView) findViewById(viewId);
        editText.setText(getString(hintStringId) + " : " + content);
    }

    public void applyIn(View v) {

    }
}
