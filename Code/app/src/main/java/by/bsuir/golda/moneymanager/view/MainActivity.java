package by.bsuir.golda.moneymanager.view;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.ContextMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.SimpleCursorTreeAdapter;
import android.widget.Toast;

import by.bsuir.golda.moneymanager.R;
import by.bsuir.golda.moneymanager.model.DBHelper;
import by.bsuir.golda.moneymanager.model.DBTransactionHelper;
import by.bsuir.golda.moneymanager.view.inputActivities.InputTransactionActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int CM_DELETE_ID = 1;
    private ExpandableListView listView;
    private DBTransactionHelper db;
    private Cursor cursor;
    private  Cursor cursorPref;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), InputTransactionActivity.class);
                startActivityForResult(intent, 1);
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);





        db = new DBTransactionHelper(this);
        db.open();

        cursor = db.getCategoryData();
        startManagingCursor(cursor);

        String[] groupFrom = {DBHelper.COLUMN_ACCOUNT_TITLE};
        int[] groupTo = {android.R.id.text1};

        String[] childFrom = {DBHelper.COLUMN_TRANSACTION_TITLE, DBHelper.COLUMN_TRANSACTION_SUM,
                DBHelper.COLUMN_TRANSACTION_DATE};
        int[] childTo = {R.id.tv_tran_title_item, R.id.tv_tran_sum_item,
                R.id.tv_tran_date_item};

        SimpleCursorTreeAdapter simpleCursorTreeAdapter = new MyAdapter(this, cursor,
                android.R.layout.simple_expandable_list_item_1, groupFrom, groupTo,
                R.layout.transaction_item, childFrom, childTo);

        listView = (ExpandableListView) findViewById(R.id.elv_category);
        listView.setAdapter(simpleCursorTreeAdapter);
        registerForContextMenu(listView);
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
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        Intent intent;
        int id = item.getItemId();

        if (id == R.id.nav_transaction_plan) {
            intent = new Intent(getApplicationContext(), TransactionPlanActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_costs) {
            intent = new Intent(getApplicationContext(), CostActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View view,
                                    ContextMenu.ContextMenuInfo contextMenuInfo){
        super.onCreateContextMenu(menu, view, contextMenuInfo);
        menu.add(0, CM_DELETE_ID, 0, "Delete");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item){
        try {
            if(item.getItemId() == CM_DELETE_ID){
                ExpandableListView.ExpandableListContextMenuInfo elcmi =
                        (ExpandableListView.ExpandableListContextMenuInfo) item.getMenuInfo();
                db.delRec(elcmi.id);
                cursor.requery();
                return true;
            }
        } catch (Exception e) {
            Toast.makeText(this, "Cannot delete from category",
                    Toast.LENGTH_SHORT).show();
        }
        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(data == null) return;
        String title = data.getStringExtra("title");
        double sum = data.getDoubleExtra("sum", 0);
        String date = data.getStringExtra("date");
        int category = data.getIntExtra("category", 0);
        db.addRec(title, sum, date, category);
        cursor.requery();
    }

    class MyAdapter extends SimpleCursorTreeAdapter {

        public MyAdapter(Context context, Cursor cursor, int groupLayout,
                         String[] groupFrom, int[] groupTo, int childLayout,
                         String[] childFrom, int[] childTo){
            super(context, cursor, groupLayout, groupFrom, groupTo,
                    childLayout, childFrom, childTo);
        }

        @Override
        protected Cursor getChildrenCursor(Cursor cursor) {
            int idColumn = cursor.getColumnIndex(DBHelper.COLUMN_ACCOUNT_ID);
            return  db.getTransactions(cursor.getInt(idColumn));
        }
    }


    protected void onDestroy() {
        super.onDestroy();
        db.close();
    }
}
