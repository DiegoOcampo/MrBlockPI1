package co.edu.eafit.mrblock.Controladores;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import co.edu.eafit.mrblock.R;

/**
 * Created by juan on 26/09/15.
 */
public class Alarm extends AppCompatActivity {
    TimePicker myTimePicker;
    Button buttonstartSetDialog, buttonfecha;
    TextView textAlarmPrompt, textView2;
    TimePickerDialog timePickerDialog;
    DatePickerDialog datePickerDialog;
    public int year1, monthOfaYear1, dayOfMonth1, hourOfDay1,minute1;

    final static int RQS_1 = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        textAlarmPrompt = (TextView) findViewById(R.id.alarm);
        textView2 =(TextView)findViewById(R.id.alarm2);
        buttonstartSetDialog = (Button) findViewById(R.id.startAlaram1);
        buttonstartSetDialog.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePickerDialog(false);
            //    Toast.makeText(getApplicationContext(),"hola", Toast.LENGTH_LONG).show();
            }
        });

        buttonfecha = (Button) findViewById(R.id.startAlaram2);
        buttonfecha.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openDatePickerDialog();
                //    Toast.makeText(getApplicationContext(),"hola", Toast.LENGTH_LONG).show();
            }
        });

    }

    private void openDatePickerDialog() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(Alarm.this,date,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle("Set Date Time");

        datePickerDialog.show();

    }

    private void openTimePickerDialog(boolean is24r) {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(Alarm.this,onTimeSetListener,calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), is24r);
        // timePickerDialog = new TimePickerDialog(MainActivity.this,
        //         onTimeSetListener, calendar.get(Calendar.HOUR_OF_DAY),
        //         calendar.get(Calendar.MINUTE), is24r);
        timePickerDialog.setTitle("Set Alarm Time");

        timePickerDialog.show();

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            year1=year;
            monthOfaYear1=monthOfYear;
            dayOfMonth1=dayOfMonth;
            calendar.add(calendar.HOUR_OF_DAY,hourOfDay1);
            calendar.add(calendar.MINUTE,minute1);
            //calendar.add(calendar.HOUR_OF_DAY,calendar.get(calendar.HOUR_OF_DAY));
            //calendar.add(calendar.MINUTE,calendar.get(calendar.MINUTE));
            //calendar.add(Calendar.HOUR_OF_DAY, Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
            //calendar.add(Calendar.MINUTE, Calendar.getInstance().get(Calendar.MINUTE));
            setDate(calendar);

        }

    };


    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {



        @Override
        public void onTimeSet(TimePicker view,int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();


            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND,0);
            calSet.set(Calendar.MILLISECOND, 0);
            hourOfDay1 = hourOfDay;
            minute1 = minute;
            calSet.add(calSet.YEAR,year1);
            calSet.add(calSet.MONTH,monthOfaYear1);
            calSet.add(calSet.DAY_OF_MONTH,dayOfMonth1);
            //calSet.add(calSet.YEAR, calSet.get(calSet.YEAR));
            //calSet.add(calSet.MONTH,calSet.get(calSet.MONTH));
            //calSet.add(calSet.DAY_OF_MONTH,calSet.get(calSet.DAY_OF_MONTH));
            //if (calSet.compareTo(calNow) <= 0) {
                // Today Set time passed, count to tomorrow
             //   calSet.add(Calendar.DATE, 0);
            //}

            setAlarm(calSet);
        }
    };

    private void setAlarm(Calendar targetCal) {

        textAlarmPrompt.setText("\n\n***\n" + "Alarm is set "
                + targetCal.getTime() + "\n" + "***\n");

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),10,
                pendingIntent);
      //  alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
      //          SystemClock.elapsedRealtime() +
      //                  60 * 1000, pendingIntent);

    }

    private void setDate(Calendar targetCal) {

        textAlarmPrompt.setText("\n\n***\n" + "Alarm is set "
                + targetCal.getTime() + "\n" + "***\n");

        Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
                pendingIntent);

    }


}
