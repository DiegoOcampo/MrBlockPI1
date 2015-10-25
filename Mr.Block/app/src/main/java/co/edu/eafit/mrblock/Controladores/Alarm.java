package co.edu.eafit.mrblock.Controladores;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Entidades.DateTime;
import co.edu.eafit.mrblock.Helper.ContactInHelper;
import co.edu.eafit.mrblock.Helper.DateHelper;
import co.edu.eafit.mrblock.R;

/**
 * Created by juan on 26/09/15.
 */
public class Alarm extends AppCompatActivity {
    private Button buttonTime1, buttonDate1, buttonTime2, buttonDate2;
    private TextView textAlarmPrompt;
    private TimePickerDialog timePickerDialog, timePickerDialog2;
    private DatePickerDialog datePickerDialog, datePickerDialog2;
    private int year1, monthOfaYear1, dayOfMonth1, hourOfDay1, minute1, year2, monthOfaYear2, dayOfMonth2, hourOfDay2, minute2;
    private DateHelper dateHelper;
    ArrayList<DateTime> dateTimeArrayList;
    private ContactInHelper contactInHelper;
    private ArrayList<Contact> contacts = new ArrayList<Contact>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);


        dateHelper = new DateHelper(getApplicationContext());
        dateTimeArrayList = new ArrayList<DateTime>();
        dateTimeArrayList = dateHelper.getAllDate();
        contactInHelper = new ContactInHelper(getApplicationContext());
        contacts = contactInHelper.getAllContact();


        textAlarmPrompt = (TextView) findViewById(R.id.alarm);
        buttonTime1 = (Button) findViewById(R.id.startTime1);
        buttonDate1 = (Button) findViewById(R.id.startDate1);
        buttonTime2 = (Button) findViewById(R.id.startTime2);
        buttonDate2 = (Button) findViewById(R.id.startDate2);

        buttonDate1.setEnabled(false);
        buttonTime2.setEnabled(false);
        buttonDate2.setEnabled(false);

        buttonTime1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePickerDialog1(true);
            }
        });

        buttonTime2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openTimePickerDialog2(true);
            }
        });

        buttonDate1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                openDatePickerDialog1();
            }
        });

        buttonDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textAlarmPrompt.setText("");
                //try {
                    openDatePickerDialog2();

            }
        });

    }


    private void openTimePickerDialog1(boolean is24r) {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog = new TimePickerDialog(Alarm.this,onTimeSetListener1,calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), is24r);
        timePickerDialog.setTitle("Set Alarm Time");
        timePickerDialog.show();
    }

    private void openTimePickerDialog2(boolean is24r) {
        Calendar calendar = Calendar.getInstance();
        timePickerDialog2 = new TimePickerDialog(Alarm.this,onTimeSetListener2,calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), is24r);
        timePickerDialog2.setTitle("Set Alarm Time");
        timePickerDialog2.show();
    }

    private void openDatePickerDialog1() {
        Calendar calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(Alarm.this,date1,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.setTitle("Set Date Time");
        datePickerDialog.show();

    }

    private void openDatePickerDialog2(){
        Calendar calendar = Calendar.getInstance();
        datePickerDialog2 = new DatePickerDialog(Alarm.this,date2,calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog2.setTitle("Set Date Time");
        datePickerDialog2.show();
    }



    DatePickerDialog.OnDateSetListener date1 = new DatePickerDialog.OnDateSetListener() {

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
            setDate1(calendar);

        }

    };

    DatePickerDialog.OnDateSetListener date2 = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            // TODO Auto-generated method stub
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            year2=year;
            monthOfaYear2=monthOfYear;
            dayOfMonth2=dayOfMonth;
            setDate2(calendar);

        }

    };


    TimePickerDialog.OnTimeSetListener onTimeSetListener1 = new TimePickerDialog.OnTimeSetListener() {



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
            setTime1(calSet);
        }
    };


    TimePickerDialog.OnTimeSetListener onTimeSetListener2 = new TimePickerDialog.OnTimeSetListener() {



        @Override
        public void onTimeSet(TimePicker view,int hourOfDay, int minute) {

            Calendar calNow = Calendar.getInstance();
            Calendar calSet = (Calendar) calNow.clone();


            calSet.set(Calendar.HOUR_OF_DAY, hourOfDay);
            calSet.set(Calendar.MINUTE, minute);
            calSet.set(Calendar.SECOND,0);
            calSet.set(Calendar.MILLISECOND, 0);
            hourOfDay2 = hourOfDay;
            minute2 = minute;
            setTime2(calSet);
        }
    };


    private void setTime1(Calendar targetCal) {

        textAlarmPrompt.setText("\n\n***\n" + "Alarm is set "
                + targetCal.getTime() + "\n" + "***\n");
        buttonDate1.setEnabled(true);
        buttonTime1.setEnabled(false);

        //Intent intent = new Intent(getBaseContext(), AlarmReceiver.class);
        //PendingIntent pendingIntent = PendingIntent.getBroadcast(
        //        getBaseContext(), RQS_1, intent, 0);
        //AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        //alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),
        //        pendingIntent);


        //alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(),10,
        //        pendingIntent);
      //  alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP,
      //          SystemClock.elapsedRealtime() +
      //                  60 * 1000, pendingIntent);

    }

    private void setDate1(Calendar targetCal) {

        textAlarmPrompt.setText("\n\n***\n" + "Alarm is set "
                + targetCal.getTime() + "\n" + "***\n");
        buttonTime2.setEnabled(true);
        buttonDate1.setEnabled(false);
        // user BoD suggests using Intent.ACTION_PICK instead of .ACTION_GET_CONTENT to avoid the chooser
        //Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        // BoD con't: CONTENT_TYPE instead of CONTENT_ITEM_TYPE
        //intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        //startActivityForResult(intent, 1);
        /*Date dates = new Date();
        dates.setYear(dateTime.getYear() - 1900);
        dates.setMonth(dateTime.getMonth());
        dates.setDate(dateTime.getDay());
        dates.setHours(dateTime.getHour());
        dates.setMinutes(dateTime.getMinute());
        dates.setSeconds(dateTime.getSecond());
        Date dates1 = new Date();
        Toast.makeText(getApplicationContext(),"entrada: "+dates.toString(),Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"actual: "+dates1.toString(),Toast.LENGTH_LONG).show();
        if (dates1.before(dates)){
            Toast.makeText(getApplicationContext(),"estoy antes",Toast.LENGTH_LONG).show();
        }else{
            Toast.makeText(getApplicationContext(),"estoy despues",Toast.LENGTH_LONG).show();
        }*/
        /* try {
            calendar1.set(Calendar.YEAR, year1);

            calendar1.set(Calendar.MONTH, monthOfaYear1);
            calendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth1);
            calendar1.set(Calendar.HOUR_OF_DAY, hourOfDay1);
            calendar1.set(Calendar.MINUTE, minute1);
        }catch (Exception e){
            Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }*/
        /*calendar1.set(Calendar.SECOND,0);
        Toast.makeText(getBaseContext(),"cal "+calendar1.toString(),Toast.LENGTH_LONG).show();
        Intent intent1 = new Intent(getBaseContext(), AlarmReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                getBaseContext(), RQS_1, intent1, 0);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, calendar1.getTimeInMillis(),
                pendingIntent);

    */}



    private void setTime2(Calendar targetCal) {

        buttonDate2.setEnabled(true);
        buttonTime2.setEnabled(false);
    }

    private void setDate2(Calendar targetCal) {

        buttonTime1.setEnabled(true);
        buttonDate2.setEnabled(false);
        Date date_1 = new Date();
        Date date_2 = new Date();
        Date date_3 = new Date();
        date_1.setYear(year1 - 1900);
        date_1.setMonth(monthOfaYear1);
        date_1.setDate(dayOfMonth1);
        date_1.setHours(hourOfDay1);
        date_1.setMinutes(minute1);
        date_1.setSeconds(0);
        date_2.setYear(year2 - 1900);
        date_2.setMonth(monthOfaYear2);
        date_2.setDate(dayOfMonth2);
        date_2.setHours(hourOfDay2);
        date_2.setMinutes(minute2);
        date_2.setSeconds(0);
        if(date_1.before(date_2) && !date_1.before(date_3)){
            for(DateTime d: dateTimeArrayList){
                Toast.makeText(getApplicationContext(),d.getDateName() + d.getMinute1(),Toast.LENGTH_LONG).show();
                dateHelper.delete(d.getDateName());
            }
            openAlert();


        }else{

            Toast.makeText(getApplicationContext(),"Por favor ingrese una fecha valida",Toast.LENGTH_LONG).show();
        }



    }
    public void openAlert(){

                AlertDialog.Builder alertName = new AlertDialog.Builder(Alarm.this);
                alertName.setTitle("Nombre");
                alertName.setMessage("Ingrese un nombre de bloqueo");
                final EditText dateNameEditText = new EditText(Alarm.this);
                alertName.setView(dateNameEditText);

                alertName.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        //Editable YouEditTextValue = dateName.getText();
                        final String dateName = dateNameEditText.getText().toString();
                        if (dateName.equals("")) {
                            Toast.makeText(getApplicationContext(), "Nombre invalido", Toast.LENGTH_LONG).show();
                        } else {
                            addDateTime(dateName);
//                            Toast.makeText(getApplicationContext(),dateHelper.getDate(dateName).getDateName()+"hi",Toast.LENGTH_LONG).show();
                        }
                    }
                });

                alertName.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                        Toast.makeText(getApplicationContext(), "La fecha no fue agregada", Toast.LENGTH_LONG).show();
                    }
                });

                alertName.show();
        //}catch (Exception e){
        //    Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        //}
    }

    public DateTime addDateTime(String dateName){
        Toast.makeText(getApplicationContext(), "Fecha agregada: " + dateName, Toast.LENGTH_LONG).show();
        DateTime dateTime = new DateTime(dateName, year1, monthOfaYear1, dayOfMonth1, hourOfDay1, minute1, 0,
                year2, monthOfaYear2, dayOfMonth2, hourOfDay2, minute2, 0,"date");
        dateHelper.addDate(dateTime);
        return dateTime;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent = new Intent(getApplicationContext(),MainFragmentActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
