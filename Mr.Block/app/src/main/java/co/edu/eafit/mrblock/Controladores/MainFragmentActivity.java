package co.edu.eafit.mrblock.Controladores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.edu.eafit.mrblock.Entidades.Complete;
import co.edu.eafit.mrblock.Entidades.Contact;
import co.edu.eafit.mrblock.Entidades.Type;
import co.edu.eafit.mrblock.R;


public class MainFragmentActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private String [] items = {"Bloquear contacto", "Bloquear todo",  "Bloquear fecha",  "Bloquear posicion", "Bloquear app"};
    private ListView listDrawer;
    private DrawerLayout mDrawerLayout;
    private boolean isWhiteList = false;
    private ArrayAdapter<String> adapterItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);


        listDrawer = (ListView) findViewById(R.id.left_drawer_items);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout_items);

        adapterItems = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,items);

        listDrawer.setAdapter(adapterItems);

        listDrawer.setOnItemClickListener(new DrawerItemClickListener());
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new BlackListFragment(), "BLACK LIST");
        adapter.addFragment(new IncomingCallsFragment(),"INCOMING CALLS");
        adapter.addFragment(new WhiteListFragment(), "WHITE LIST");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.action_contact_block:
                Intent intentBlocContact = new Intent(Intent.ACTION_GET_CONTENT);
                intentBlocContact.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intentBlocContact, 1);
                return true;
            case R.id.action_all_block:
                openAlertBlock();
                return true;
            case R.id.action_date_block:
                Intent intent2 = new Intent(getBaseContext(), Alarm.class);
                startActivity(intent2);
                return true;
            case R.id.action_date_location:
                Intent i = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(i);
                return true;
            case R.id.action_add_white_list:
                isWhiteList = true;
                Intent intent3 = new Intent(Intent.ACTION_GET_CONTENT);
                intent3.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent3, 1);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            Uri uri = data.getData();
            if (uri != null) {
                Cursor c = null;
                try {
                    c = getContentResolver().query(uri, new String[]{
                                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                                    ContactsContract.CommonDataKinds.Phone.NUMBER,},
                            null, null, null);

                    if (c != null && c.moveToFirst()) {
                        String name = c.getString(0);
                        String number = c.getString(1).replaceAll(" ", "");
                        if(!isWhiteList) {
                            BlackListFragment blackListFragment = new BlackListFragment();
                            Contact contact = blackListFragment.addContactToFragment(number, name, "contact", getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), MainFragmentActivity.class);
                            startActivity(intent);
                            isWhiteList = false;
                        }else{
                            WhiteListFragment whiteListFragment = new WhiteListFragment();
                            Contact contact = whiteListFragment.addContactToFragment(number, name, "white contact", getApplicationContext());
                            Intent intent = new Intent(getApplicationContext(), MainFragmentActivity.class);
                            startActivity(intent);
                            isWhiteList = false;
                        }
                        }
                } finally {
                    if (c != null) {
                        c.close();
                    }
                }
            }
        }
    }

    public void selectItem(int position) {
        listDrawer.setItemChecked(position, true);
        switch (position){
            case 0:
                Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
                intent1.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
                startActivityForResult(intent1, 1);
                break;
            case 1:
                openAlertBlock();
                break;
            case 2:
                Intent intent2 = new Intent(getBaseContext(), Alarm.class);
                startActivity(intent2);
                break;
            case 3:
                Intent i = new Intent(getApplicationContext(),MapsActivity.class);
                startActivity(i);
                break;



        }


        mDrawerLayout.closeDrawer(listDrawer);
    }

    private void openAlertBlock(){

        final AlertDialog.Builder alertName = new AlertDialog.Builder(MainFragmentActivity.this);
        alertName.setTitle("Advertencia");
        alertName.setMessage("Esta seguro de bloquear todos los contactos?");
        //final EditText dateName = new EditText(MainActivity.this);
        //alertName.setView(dateName);
        alertName.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                //String editTextName = dateName.getText().toString();
                //Type type1 = typeHelper.getType("Complete block");
                //if(type1==null) {
                BlackListFragment blackListFragment=new BlackListFragment();
                blackListFragment.addCompleteToFragment(getApplicationContext());
                viewPager.removeAllViews();
                finish();
                startActivity(new Intent(MainFragmentActivity.this, MainFragmentActivity.class));
                //BlackListFragment.adapter.notifyDataSetChanged();
                //Intent intent = new Intent(getApplicationContext(),MainFragmentActivity.class);
                //startActivity(intent);
            }
        });

        alertName.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
                Toast.makeText(getApplicationContext(), "Bloqueo cancelado", Toast.LENGTH_LONG).show();
            }
        });
        alertName.show();

    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            selectItem(position);
        }

    }

}