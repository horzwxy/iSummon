package com.isummon.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.isummon.R;
import com.isummon.model.HDActivity;
import com.isummon.model.HDProperty;
import com.isummon.model.HDType;

import java.util.Calendar;


public class AddActActivity extends Activity {

    static final String ADDRESS_NAME = "address_name";
    static final String LONGITUDE = "longitude";
    static final String LATITUDE = "latitude";
    static final int GET_ADDRESS = 876;

    private final static double DEFAULT_LATITUDE = 120000000d;
    private final static double DEFAULT_LONGITUDE = 35000000d;

    private HDActivity result;
    private Calendar hdStartDate;
    private Calendar hdStartTime;
    private Calendar hdEndDate;
    private Calendar hdEndTime;

    @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

        // remove the action bar
        // 'title bar' and 'action bar' refer to the same widget
        requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.activity_add);

        // Only set bg in layout file is not enough: a dark bg still exists.
        getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg_white);

        result = new HDActivity();
        hdStartDate = hdEndDate = hdStartTime = hdEndTime = Calendar.getInstance();

        Intent intent = getIntent();
        double longitude = intent.getDoubleExtra( LONGITUDE, -200000000d );
        double latitude = intent.getDoubleExtra( LATITUDE, -200000000d );
        EditText et = (EditText) findViewById(R.id.actPlace);
        et.setHint(R.string.act_place_prompt);
        // if longitude is greater than -181 degrees, it must be a valid number
        // in unit of 1e-6 degrees
        if(longitude > -181000000d ) {
            result.setLongitude(longitude);
            result.setLatitude(latitude);
            et.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddressNameEditor();
                }
            });
        }
        else {
            et.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showAddressPicker();
                }
            });
        }

        // the listeners can be set in XML, but... I want the XML to be shared by some other activities without these listeners
        findViewById(R.id.add_act_start_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(hdStartDate, R.id.add_act_start_date);
            }
        });
        findViewById(R.id.add_act_start_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(hdStartTime, R.id.add_act_start_time);
            }
        });
        findViewById(R.id.add_act_end_date).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDatePicker(hdEndDate, R.id.add_act_end_date);
            }
        });
        findViewById(R.id.add_act_end_time).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTimePicker(hdEndTime, R.id.add_act_end_time);
            }
        });

        findViewById(R.id.act_type_content).setVisibility(View.INVISIBLE);

        findViewById(R.id.act_type_prompt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showTypePicker();
            }
        });

        findViewById(R.id.act_property).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPropertyPicker();
            }
        });

        Button positiveButton = (Button) findViewById(R.id.act_positive);
        positiveButton.setText(getString(R.string.add_act));
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast(R.string.add_success);
                finish();
                toInivite();
//                if(checkAndPrompt()) {
//
//                }
//                else {
//                    //showToast(R.string.add_failed);
//                }
            }
        });

        Button negativeButton = (Button) findViewById(R.id.act_negative);
        negativeButton.setText(getString(R.string.cancel_add));
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
	}

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == GET_ADDRESS) {
            if(resultCode == RESULT_OK) {
                ((EditText)findViewById(R.id.actPlace)).setText(data.getStringExtra(ADDRESS_NAME));
                result.setLatitude(data.getDoubleExtra(LATITUDE, DEFAULT_LATITUDE));
                result.setLongitude(data.getDoubleExtra(LONGITUDE, DEFAULT_LONGITUDE));
            }
            else {
                // on error
            }
        }
    }

    private void toInivite() {
        Intent intent = new Intent(this, InviteActivity.class);
        intent.putExtra(InviteActivity.HD_ID, 0);
        startActivity(intent);
    }

    private void showPropertyPicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.act_property_prompt);
        builder.setItems(HDProperty.getChns(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                EditText promptText = (EditText) findViewById(R.id.act_property);
                promptText.setText(HDProperty.values()[which].getChn());
                result.setHdProperty(HDProperty.values()[which]);
            }
        });
        builder.create().show();
    }

    /**
     *
     * @return true if no error in forms
     */
    private boolean checkAndPrompt() {
        String hdName = getTextInEditText(R.id.actName);
        if (isEmptyString(hdName)) {
            showToast(R.string.error_no_hdname);
            return false;
        }

        String hdPlace = getTextInEditText(R.id.actPlace);
        if(isEmptyString(hdPlace)) {
            showToast(R.string.error_no_place);
            return false;
        }

        String hdStartDateString = getTextInEditText(R.id.add_act_start_date);
        if(isEmptyString(hdStartDateString)) {
            showToast(R.string.error_no_start_date);
            return false;
        }

        String hdEndDateString = getTextInEditText(R.id.add_act_end_date);
        if(isEmptyString(hdEndDateString)) {
            showToast(R.string.error_no_end_date);
            return false;
        }

        String hdStartTimeString = getTextInEditText(R.id.add_act_start_time);
        if(isEmptyString(hdStartTimeString)) {
            showToast(R.string.error_no_start_time);
            return false;
        }

        String hdEndTimeString = getTextInEditText(R.id.add_act_end_time);
        if(isEmptyString(hdEndTimeString)) {
            showToast(R.string.error_no_end_time);
            return false;
        }

        String hdContentString = getTextInEditText(R.id.actContent);
        if(isEmptyString(hdContentString)) {
            showToast(R.string.error_no_content);
            return false;
        }

        String hdTypeString = getTextInEditText(R.id.act_type_prompt);
        if(isEmptyString(hdTypeString)) {
            showToast(R.string.error_no_type);
            return false;
        }

        String hdMaxParticipantsString = getTextInEditText(R.id.act_max_participants);
        if(isEmptyString(hdMaxParticipantsString)) {
            showToast(R.string.error_no_max_participants);
            return false;
        }

        String hdPropertyString = getTextInEditText(R.id.act_property);
        if(isEmptyString(hdPropertyString)) {
            showToast(R.string.error_no_property);
            return false;
        }

        result.setHdName(hdName);
        result.setHdAddress(hdPlace);
        result.setHdStartTime(
                getDateRepresentation(hdStartDate, "-", "-", "") + ":" + getTimeRepresentation(hdStartTime, ""));
        result.setHdEndTime(
                getDateRepresentation(hdEndDate, "-", "-", "") + ":" + getTimeRepresentation(hdEndTime, ""));
        result.setHdDesc(hdContentString);
        result.setHdType(HDType.valueOf(hdTypeString));
        try {
            result.setHdNumLimit(Integer.parseInt(hdMaxParticipantsString));
        } catch (NumberFormatException e) {
            showToast(R.string.error_max_not_number);
            return false;
        }

        return true;
    }

    private String getTextInEditText(int viewId) {
        return ((EditText)findViewById(viewId)).getText().toString();
    }

    private static boolean isEmptyString(String s) {
        return "".equals(s);
    }

    private void showToast(int stringId) {
        Toast.makeText(this, stringId, Toast.LENGTH_SHORT).show();
    }

    private void showTypePicker() {
        final ImageView typeImage = (ImageView)findViewById(R.id.act_type_image);
        final TextView typeText = (TextView)findViewById(R.id.act_type_name);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.act_type_prompt);
        builder.setItems(HDType.getChns(), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                typeImage.setImageResource(HDType.values()[which].getDrawableId());
                typeText.setText(HDType.values()[which].getChn());

                EditText promptText = (EditText) findViewById(R.id.act_type_prompt);
                promptText.setVisibility(View.GONE);
                promptText.setText(HDType.values()[which].toString()); // save type id in the invisible EditText

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
            }
        });
        builder.create().show();
    }

    private void showDatePicker(final Calendar date, final int layoutId) {
        DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                date.set(Calendar.YEAR, year);
                date.set( Calendar.MONTH, month );
                date.set( Calendar.DAY_OF_MONTH, day );
                ((EditText)findViewById(layoutId)).setText(
                        getDateRepresentation(date, "年", "月", "日"));
            }
        };
        final Calendar currentDate = Calendar.getInstance();
        new DatePickerDialog( this,
                listener,
                currentDate.get(Calendar.YEAR),
                currentDate.get( Calendar.MONTH ),
                currentDate.get( Calendar.DAY_OF_MONTH ) ).show();
    }

    private void showTimePicker(final Calendar time, final int layoutId) {
        TimePickerDialog.OnTimeSetListener listener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                time.set( Calendar.HOUR_OF_DAY, hour );
                time.set( Calendar.MINUTE, minute );
                ((EditText)findViewById(layoutId)).setText( getTimeRepresentation( time, " : " ) );
            }
        };
        Calendar currentTime = Calendar.getInstance();
        new TimePickerDialog( this,
                listener,
                currentTime.get( Calendar.HOUR_OF_DAY ),
                currentTime.get( Calendar.MINUTE ),
                true ).show();
    }

    private void showAddressNameEditor() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.pick_addr_from_map_dialog_title);
        View dialogContent = getLayoutInflater().inflate(R.layout.dialog_input_addr_name, null);
        final EditText nameEditor = (EditText) dialogContent.findViewById(R.id.addr_name_editor);
        builder.setView(dialogContent);
        builder.setPositiveButton(R.string.input_positive,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onAddressNameInput(nameEditor.getText().toString());
                        // no need to call dismiss
                    }
                });
        builder.setNegativeButton(R.string.input_negative,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // no need to call dismiss. System will help that.
                    }
                });
        builder.setCancelable(true);
        builder.create().show();
    }

    /**
     *
     * @param date
     * @param sp1 separator 1, "年" or "-"
     * @param sp2 separator 2, "月" or "-"
     * @param sp3 separator 3, "日" or ""
     * @return
     */
    private String getDateRepresentation(Calendar date, String sp1, String sp2, String sp3) {
        int actualMonth = date.get(Calendar.MONTH) + 1;
        String monthResp = actualMonth < 10 ? "0" + actualMonth : "" + actualMonth;
        int actualDay = date.get(Calendar.DAY_OF_MONTH);
        String dayResp = actualDay < 10 ? "0" + actualDay : actualDay + "";
        return date.get(Calendar.YEAR) + sp1 + monthResp + sp2 + dayResp + sp3;
    }

    /**
     *
     * @param date
     * @param sp1 separator between hour and seconds
     * @return
     */
    private String getTimeRepresentation(Calendar date, String sp1) {
        String hourResp = date.get(Calendar.HOUR_OF_DAY) < 10 ? "0" + date.get(Calendar.HOUR_OF_DAY) : "" + date.get(Calendar.HOUR_OF_DAY);
        String minuteResp = date.get(Calendar.MINUTE) < 10 ? "0" + date.get(Calendar.MINUTE) : date.get(Calendar.MINUTE) + "";
        return hourResp + sp1 + minuteResp;
    }

    private void onAddressNameInput(String name) {
        if(name != null && !name.equals("")) {
            EditText et = (EditText) findViewById(R.id.actPlace);
            et.setText(name);
        }
        else {
            Toast.makeText(this, R.string.input_empty_hint, Toast.LENGTH_SHORT).show();
        }
    }

    private void showAddressPicker() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.address_choose);
        builder.setItems(R.array.address_input_source, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if(which == 1) {
                    onChooseMap();
                }
                else {
                    onChooseSearch();
                }
            }
        });
        builder.create().show();
    }

    private void onChooseMap() {
        startActivityForResult(
                new Intent(this, PickMapAddressActivity.class),
                GET_ADDRESS);
    }

    private void onChooseSearch() {
        Toast.makeText(this, R.string.search_not_available, Toast.LENGTH_SHORT).show();
//        startActivityForResult(
//                new Intent(this, SearchAddressActivity.class),
//                GET_ADDRESS
//        );
    }


}
