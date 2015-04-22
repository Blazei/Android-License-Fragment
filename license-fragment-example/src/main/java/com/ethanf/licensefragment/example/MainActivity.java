package com.ethanf.licensefragment.example;

import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.widget.DrawerLayout;

import com.ethanf.licensefragment.LicenseFragmentBase;
import com.ethanf.licensefragment.ListViewLicenseFragment;
import com.ethanf.licensefragment.RecyclerViewLicenseFragment;
import com.ethanf.licensefragment.ScrollViewLicenseFragment;
import com.ethanf.licensefragment.model.LicenseID;

public class MainActivity extends AppCompatActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, LicenseFragmentBase.OnAttachedListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    private static int fragmentId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        if (savedInstanceState == null) mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment;

        int[] licenseIds = { LicenseID.RETROFIT };

        switch (position) {
            case 0:
                if (fragmentManager.findFragmentById(R.id.container) instanceof ScrollViewLicenseFragment) return;
                fragment = ScrollViewLicenseFragment.newInstance(licenseIds);
                break;
            case 1:
                if (fragmentManager.findFragmentById(R.id.container) instanceof ListViewLicenseFragment) return;
                fragment = ListViewLicenseFragment.newInstance(licenseIds).withLicenseChain(false);
                break;
            case 2:
                if (fragmentManager.findFragmentById(R.id.container) instanceof RecyclerViewLicenseFragment) return;
                fragment = RecyclerViewLicenseFragment.newInstance(licenseIds).withLicenseChain(true);
                break;
            default:
                return;
        }

        // update the main content by replacing fragments
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .commit();

        fragmentId = position + 1;
    }

    @Override
    public void onAttached() {
        switch (fragmentId) {
            case 1: mTitle = getString(R.string.title_section1); break;
            case 2: mTitle = getString(R.string.title_section2); break;
            case 3: mTitle = getString(R.string.title_section3); break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();

        if (actionBar == null) return;

//        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}
