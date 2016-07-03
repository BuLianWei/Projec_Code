package com.example.asus.myapplication.ui.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.asus.myapplication.ui.fragment.CoinFragment;
import com.example.asus.myapplication.ui.fragment.DiceFragment;
import com.example.asus.myapplication.R;
import com.example.asus.myapplication.ui.fragment.ScrapeFragment;
import com.example.asus.myapplication.ui.fragment.SortitionFragment;
import com.example.asus.myapplication.ui.fragment.TransferFragment;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private FragmentManager mManager;
    private FragmentTransaction mTransaction;
    private TransferFragment mTransfer;
    private CoinFragment mCoinFragment;
    private DiceFragment mDice;
    private SortitionFragment mSort;
    private ScrapeFragment mScrape;
    public FloatingActionButton fab;


    @Override
    protected void initData() {

    }

    @Override
    protected void initView() {
        mManager = getFragmentManager();
        setFrame();
        mManager.beginTransaction().add(R.id.content, new TransferFragment(), "transfer").commit();

    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void setFrame() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fab = (FloatingActionButton) findViewById(R.id.fab);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

        mTransaction = mManager.beginTransaction();
        if (id == R.id.action_transfer) {
            if (mTransfer == null)
                mTransfer = new TransferFragment();
            mTransaction.replace(R.id.content, mTransfer, "tansfer").commit();
            return true;
        } else if (id == R.id.action_coin) {
            if (mCoinFragment == null)
                mCoinFragment = new CoinFragment();
            mTransaction.replace(R.id.content, mCoinFragment, "coin").commit();
            return true;
        } else if (id == R.id.action_sort) {
//            if (mDice == null)
//                mDice = new DiceFragment();
//            mTransaction.replace(R.id.content, mDice, "dice").commit();
            if (mSort == null) {
                mSort = new SortitionFragment();
            }
            mTransaction.replace(R.id.content, mSort, "sort").commit();
            return true;
        } else if (id == R.id.action_scrape) {
            if (mScrape == null) {
                mScrape = new ScrapeFragment();
            }
            mTransaction.replace(R.id.content, mScrape, "scrape").commit();
            return true;
        }
        return false;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_about) {
            startActivity(AboutActivity.class);
        } else if (id == R.id.nav_chat) {
            startActivity(LoginActivity.class);
            startChat();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void startChat() {

    }

    @Override
    public void onClick(View v) {

    }
}
