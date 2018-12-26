package by.bsuir.golda.moneymanager.view.inputActivities;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Calendar;

import by.bsuir.golda.moneymanager.R;

public class InputTransactionActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etTitle;
    private Button btnAdd;
    private Button btnCancel;
    private Button btnDate;
    private EditText etSum;
    private Spinner spinner;
    private int spinnerPosition;

    private Calendar calendar = Calendar.getInstance();

    private int DIALOG_DATE = 1;
    private int myYear = calendar.get(Calendar.YEAR);
    private int myMonth = calendar.get(Calendar.MONTH) + 1;
    private int myDay = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_transaction);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        etTitle = (EditText)findViewById(R.id.et_tran_title);
        etSum = (EditText)findViewById(R.id.et_tran_sum);
        spinner = (Spinner)findViewById(R.id.input_tran_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                spinnerPosition = i + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnAdd = (Button)findViewById(R.id.btn_tran_add);
        btnCancel = (Button)findViewById(R.id.btn_tran_cancel);
        btnDate = (Button)findViewById(R.id.btn_date);

        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
        btnDate.setOnClickListener(this);
    }


    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_DATE) {
            DatePickerDialog tpd = new DatePickerDialog(this, myCallBack, myYear, myMonth, myDay);
            return tpd;
        }
        return super.onCreateDialog(id);
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {

        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            myYear = year;
            myMonth = monthOfYear + 1;
            myDay = dayOfMonth;
        }
    };

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_tran_add:
                try {
                    if(etTitle.getText().toString().equals("") && etSum.getText().toString().equals("")){
                        Toast.makeText(this, "Input title and sum", Toast.LENGTH_LONG).show();
                    }
                    else{
                        double input = Math.rint(100.0 *
                                Double.parseDouble(etSum.getText().toString())) / 100.0;
                        if(input < Math.pow(10, 4)){
                            String inputDate = "" + myYear + "." + myMonth + "." + myDay;
                            intent.putExtra("title", etTitle.getText().toString());
                            intent.putExtra("sum", input);
                            intent.putExtra("date", inputDate);
                            intent.putExtra("category", spinnerPosition);
                            setResult(RESULT_OK, intent);
                            finish();
                        }
                        else {
                            Toast.makeText(this, "Very много money",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
                } catch (Exception e) {
                    Toast.makeText(this, "Please input valid sum", Toast.LENGTH_LONG).show();
                }
            case R.id.btn_date:
                showDialog(DIALOG_DATE);
                break;
            case R.id.btn_tran_cancel:
                finish();
                break;
        }
    }
}
