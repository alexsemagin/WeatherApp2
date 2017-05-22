package r1651.weatherapp.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.mikepenz.fontawesome_typeface_library.FontAwesome;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;

import butterknife.BindView;
import r1651.weatherapp.R;
import r1651.weatherapp.ui.preferences.Preferences;
import r1651.weatherapp.ui.weather.WeatherFragment;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.container)
    FrameLayout container;

    private FragmentTransaction ft;
    private int position;
    private Drawer mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initDrawer();
        ft = getSupportFragmentManager().beginTransaction();
        Fragment weatherFragment = getSupportFragmentManager().findFragmentByTag(WeatherFragment.class.getName());
        if (weatherFragment == null) {
            weatherFragment = new WeatherFragment();
            ft.replace(R.id.main_container, weatherFragment, WeatherFragment.class.getName()).commit();
        } else ft.replace(R.id.main_container, weatherFragment).commit();
    }

    public void initDrawer() {
        AccountHeader mAccountHeader = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.drawable.header)
                .addProfiles(new ProfileDrawerItem().withName("Alex Semagin").withEmail("semaal@rarus.ru").withIcon(ResourcesCompat.getDrawable(getResources(), R.drawable.profile, null)))
                .withOnAccountHeaderListener((view, profile, currentProfile) -> false)
                .build();

        mDrawer = new DrawerBuilder()
                .withActivity(this)
                .withAccountHeader(mAccountHeader)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        new PrimaryDrawerItem().withName(R.string.drawer_item_test1).withIcon(FontAwesome.Icon.faw_home).withIdentifier(1),
                        new DividerDrawerItem(),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_settings).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(2),
                        new SecondaryDrawerItem().withName(R.string.drawer_item_about).withIcon(FontAwesome.Icon.faw_info).withIdentifier(3)
                ).withOnDrawerItemClickListener((view, position, drawerItem) -> {
                    this.position = position;
                    switch (position) {
                        case 1:
                            break;
                        case 3:
                            Intent settingsActivity = new Intent(getBaseContext(), Preferences.class);
                            startActivity(settingsActivity);
                            break;
                        case 4:
                            break;
                        default:
                            break;
                    }
                    return false;
                })
                .build();
    }

    public Drawer getDrawer() {
        return mDrawer;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("position", position);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mDrawer.setSelection(savedInstanceState.getInt("position"), false);
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen()) mDrawer.closeDrawer();
        else super.onBackPressed();
    }

}
