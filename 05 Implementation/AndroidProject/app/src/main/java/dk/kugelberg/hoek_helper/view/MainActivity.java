package dk.kugelberg.hoek_helper.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.PopupWindow;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NavUtils;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import dk.kugelberg.hoek_helper.R;
import dk.kugelberg.hoek_helper.model.Controller;
import dk.kugelberg.hoek_helper.model.ControllerImpl;
import dk.kugelberg.hoek_helper.model.Raekke;
import dk.kugelberg.hoek_helper.model.Tabel;
import dk.kugelberg.hoek_helper.view.ViewModel.ModelViewModel;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {

    private RecyclerView recyclerView;
    private DataAdapter adapter;

    private Controller controller;

    private TextView antalEnheder;
    private TextView vo;
    private TextView ve;
    private TextView domk;
    private TextView se;
    private TextView ke;
    private TextView ko;
    private TextView sto;
    private TextView gromk;
    private TextView oms;
    private TextView db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        controller = ControllerImpl.getInstance();

        antalEnheder = findViewById(R.id.tv_antal_enheder);
        vo = findViewById(R.id.tv_vo);
        ve = findViewById(R.id.tv_ve);
        domk = findViewById(R.id.tv_domk);
        se = findViewById(R.id.tv_se);
        ke = findViewById(R.id.tv_ke);
        ko = findViewById(R.id.tv_ko);
        sto = findViewById(R.id.tv_sto);
        gromk = findViewById(R.id.tv_gromk);
        oms = findViewById(R.id.tv_oms);
        db = findViewById(R.id.tv_db);


        ActionBar actionBar = this.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setTitle("Opgave X");
        }

        setupViewModel();

        recyclerView = findViewById(R.id.recyclerView_tasks);
//        recyclerView.setHasFixedSize(true);
//        recyclerView.setNestedScrollingEnabled(false);
//        recyclerView.setItemViewCacheSize(20);
//        recyclerView.setDrawingCacheEnabled(true);
//        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
//        linearLayoutManager.scrollToPositionWithOffset(2, 3);

        recyclerView.setLayoutManager(linearLayoutManager);

        adapter = new DataAdapter(this, viewModel);
        recyclerView.setAdapter(adapter);

        System.out.println("onCreate");

        setupSharedPreferences();
    }


    private ModelViewModel viewModel;

    private void setupViewModel() {

        viewModel = ViewModelProviders.of(this).get(ModelViewModel.class);

        viewModel.getTable().observe(this, new Observer<ArrayList<Raekke>>() {

            @Override
            public void onChanged(@Nullable ArrayList<Raekke> raekker) {
                adapter.setTasks(raekker);
                System.out.println("Shit changed");
            }
        });
    }

    private String editTextChanged;

    public void setEditTextChanged(String editTextChanged) {
        this.editTextChanged = editTextChanged;
    }

    /**
     * The following code adds rows to the table
     */

    double testX = 0;
    double testVO = 0;

    public void addRow(View view) {
        viewModel.addRow();
    }

    private PopupWindow popupWindow;
    private EditText etRows;
    private EditText etAntal;
    private EditText etIncrement;

    public void addRows(View view) {
        int width = GridLayout.LayoutParams.WRAP_CONTENT;
        int height = GridLayout.LayoutParams.WRAP_CONTENT;
        View v = LayoutInflater.from(this).inflate(R.layout.popup_layout, null, false);

        etRows = v.findViewById(R.id.et_popup_rows);
        etAntal = v.findViewById(R.id.et_popup_antal);
        etIncrement = v.findViewById(R.id.et_popup_increment);

        popupWindow = new PopupWindow(v, width, height, true);
        popupWindow.showAtLocation(this.findViewById(R.id.main_layout), Gravity.CENTER, 0, 0);
    }

    public void popupInsert(View view) {
        viewModel.popupInsert(popupWindow, etRows, etAntal, etIncrement);
    }

    public void popupCancel(View view) {
        viewModel.popupCancel(popupWindow);
    }

    /**
     * The following code deals with the top right menu
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemThatWasClickedId = item.getItemId();

        if (itemThatWasClickedId == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        } else if (itemThatWasClickedId == R.id.indstillinger) {
            Intent i = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * The following code deals with changing of settings
     */

    private void visibility(SharedPreferences sharedPreferences) {

        if (sharedPreferences.getBoolean(getString(R.string.vis_antal_enheder_key), getResources().getBoolean(R.bool.vis_antal_enheder))) {
            antalEnheder.setVisibility(View.VISIBLE);
            adapter.setAntalEnhederVisible(true);
        } else {
            antalEnheder.setVisibility(View.GONE);
            adapter.setAntalEnhederVisible(false);
        }

        if (sharedPreferences.getBoolean(getString(R.string.vis_vo_key), getResources().getBoolean(R.bool.vis_vo))) {
            vo.setVisibility(View.VISIBLE);
            adapter.setVoVisible(true);
        } else {
            vo.setVisibility(View.GONE);
            adapter.setVoVisible(false);
        }

        if (sharedPreferences.getBoolean(getString(R.string.vis_ve_key), getResources().getBoolean(R.bool.vis_ve))) {
            ve.setVisibility(View.VISIBLE);
            adapter.setVeVisible(true);
        } else {
            ve.setVisibility(View.GONE);
            adapter.setVeVisible(false);
        }

        if (sharedPreferences.getBoolean(getString(R.string.vis_domk_key), getResources().getBoolean(R.bool.vis_domk))) {
            domk.setVisibility(View.VISIBLE);
            adapter.setDomkVisible(true);
        } else {
            domk.setVisibility(View.GONE);
            adapter.setDomkVisible(false);
        }
        if (sharedPreferences.getBoolean(getString(R.string.vis_domk_key), getResources().getBoolean(R.bool.vis_domk))) {
            domk.setVisibility(View.VISIBLE);
            adapter.setDomkVisible(true);
        } else {
            domk.setVisibility(View.GONE);
            adapter.setDomkVisible(false);
        }/* //TODO ADD SAME FUNCTIONS FOR (DB, KE, KO og de restende objecter)
        if (sharedPreferences.getBoolean(getString(R.string.vis_domk_key), getResources().getBoolean(R.bool.vis_domk))) {
            domk.setVisibility(View.VISIBLE);
            adapter.setDomkVisible(true);
        } else {
            domk.setVisibility(View.GONE);
            adapter.setDomkVisible(false);
        }
        if (sharedPreferences.getBoolean(getString(R.string.vis_domk_key), getResources().getBoolean(R.bool.vis_domk))) {
            domk.setVisibility(View.VISIBLE);
            adapter.setDomkVisible(true);
        } else {
            domk.setVisibility(View.GONE);
            adapter.setDomkVisible(false);
        }
        if (sharedPreferences.getBoolean(getString(R.string.vis_domk_key), getResources().getBoolean(R.bool.vis_domk))) {
            domk.setVisibility(View.VISIBLE);
            adapter.setDomkVisible(true);
        } else {
            domk.setVisibility(View.GONE);
            adapter.setDomkVisible(false);
        }
        if (sharedPreferences.getBoolean(getString(R.string.vis_domk_key), getResources().getBoolean(R.bool.vis_domk))) {
            domk.setVisibility(View.VISIBLE);
            adapter.setDomkVisible(true);
        } else {
            domk.setVisibility(View.GONE);
            adapter.setDomkVisible(false);
        }if (sharedPreferences.getBoolean(getString(R.string.vis_domk_key), getResources().getBoolean(R.bool.vis_domk))) {
            domk.setVisibility(View.VISIBLE);
            adapter.setDomkVisible(true);
        } else {
            domk.setVisibility(View.GONE);
            adapter.setDomkVisible(false);
        }*/


    }

    private void setupSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        visibility(sharedPreferences);

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

//        if (key.equals(getString(R.string.vis_antal_enheder_key))) {
//            System.out.println("Correct key");
        visibility(sharedPreferences);
//        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * The following code deals with the hardware back button
     */

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        NavUtils.navigateUpFromSameTask(this);
    }

}