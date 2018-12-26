package by.bsuir.golda.moneymanager.view;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;

import by.bsuir.golda.moneymanager.R;
import by.bsuir.golda.moneymanager.model.DBCostHelper;
import by.bsuir.golda.moneymanager.model.DBHelper;
import by.bsuir.golda.moneymanager.model.DBTransactionPlanHelper;
import by.bsuir.golda.moneymanager.view.inputActivities.InputCostActivity;

public class CostActivity extends AppCompatActivity {

    ListView listView;
    DBCostHelper db;
    Cursor cursor;
    SimpleCursorAdapter simpleCursorAdapter;
    private static final int CM_DELETE_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cost);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Costs / Gains");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);

        db = new DBCostHelper(this);
        db.open();

        cursor = db.getAllData();
        cursor.requery();

        String from[] = new String[] {DBHelper.COLUMN_COST_CATEGORY,
                DBHelper.COLUMN_COST_TITLE, DBHelper.COLUMN_COST_SUM};
        int to[] = new int[] {R.id.tv_cost_category, R.id.tv_cost_title, R.id.tv_cost_sum};

        simpleCursorAdapter = new SimpleCursorAdapter(this, R.layout.cost_item, cursor, from, to);

        listView = (ListView)findViewById(R.id.lv_cost);
        listView.setAdapter(simpleCursorAdapter);
        registerForContextMenu(listView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(getApplicationContext(), InputCostActivity.class);
               startActivityForResult(intent, 1);
            }
        });
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo){
        super.onCreateContextMenu(menu, view, contextMenuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        if(item.getItemId() == CM_DELETE_ID){
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo)
                    item.getMenuInfo();
            db.delRec(acmi.id);
            cursor.requery();
            return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null) return;
        String title = data.getStringExtra("title");
        double sum = data.getDoubleExtra("sum", 0);
        String category = data.getStringExtra("category");
        db.addRec(title, sum, category);
        cursor.requery();
    }
}
