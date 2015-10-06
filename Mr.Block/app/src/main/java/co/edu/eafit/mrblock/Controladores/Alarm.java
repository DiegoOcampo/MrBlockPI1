package co.edu.eafit.mrblock.Controladores;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

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
    private final static int RQS_1 = 1;
    private Calendar calendar1;
    ArrayList<DateTime> ddd;
    private ContactInHelper contactInHelper;
    private ArrayList<Contact> contacts = new ArrayList<Contact>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        dateHelper = new DateHelper(getApplicationContext());
        ddd = new ArrayList<DateTime>();
        ddd = dateHelper.getAllDate();

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

        textAlarmPrompt.setText("\n\n***\n" + "Alarm is set "
                + targetCal.getTime() + "\n" + "***\n");
        buttonDate2.setEnabled(true);
        buttonTime2.setEnabled(false);
    }

    private void setDate2(Calendar targetCal) {

        textAlarmPrompt.setText("\n\n***\n" + "Alarm is set "
                + targetCal.getTime() + "\n" + "***\n");
        buttonTime1.setEnabled(true);
        buttonDate2.setEnabled(false);


        for(DateTime d:ddd){
            dateHelper.delete(d);
        }

        Toast.makeText(getApplicationContext(),"fecha1: "+ year1+"-"+monthOfaYear1+"-"+dayOfMonth1+"-"+hourOfDay1+"-"+minute1,Toast.LENGTH_LONG).show();
        Toast.makeText(getApplicationContext(),"fecha2: "+ year2+"-"+monthOfaYear2+"-"+dayOfMonth2+"-"+hourOfDay2+"-"+minute2,Toast.LENGTH_LONG).show();
        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);
        while (phones.moveToNext()) {
            String name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)).replace(" ","");
            String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)).replace(" ","");
            Contact contact = new Contact(phoneNumber, name);
            if (!contacts.contains(contact)) {
                contactInHelper.addContact(contact);
                contacts.add(contact);
                DateTime dateTime= new DateTime(phoneNumber,year1,monthOfaYear1,dayOfMonth1,hourOfDay1,minute1,0,
                        year2,monthOfaYear2,dayOfMonth2,hourOfDay2,minute2,0);
                dateHelper.addDate(dateTime);
            }
        }
        int length = contacts.size();
        try {
            for (int index = length - 1; index >= 0; index--) {
                Contact contact = contacts.get(index);
                long row = contactInHelper.delete(contact);
                if (row > 0) {
                    contacts.remove(index);
                }

            }
        }catch (Exception e){
            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }

        phones.close();

        //Toast.makeText(getApplicationContext(),"num: "+dateHelper.getDate("2").getNumber() + "min1: " + dateHelper.getDate("2").getMinute1()+"min2: "+dateHelper.getDate("2").getMinute2(),Toast.LENGTH_LONG).show();


    }


}
