package r1651.weatherapp.ui.preferences;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import r1651.weatherapp.R;

public class Preferences extends AppCompatActivity {

    @BindView(R.id.toolbar)
    Toolbar toolbar;


    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_preferences);
        ButterKnife.bind(this);

        toolbar.setTitle(getTitle());
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        toolbar.setNavigationOnClickListener(v -> super.onBackPressed());

        getFragmentManager().beginTransaction().replace(R.id.content_preferences, new PreferencesFragment()).commit();

    }

}