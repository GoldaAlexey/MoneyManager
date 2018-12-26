package by.bsuir.golda.moneymanager.view.inputActivities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import by.bsuir.golda.moneymanager.R;

public class InputTransactionPlanActivity extends AppCompatActivity implements View.OnClickListener {

    String[] array = { "Periodically", "Once only", "Weekly", "Monthly", "Yearly"};

    EditText etTitle;
    EditText etSum;
    Spinner spinner;

    Button btnAdd;
    Button btnCancel;

    int spinnerPosition;
    String spinnerText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_transaction_plan);

        spinner = (Spinner)findViewById(R.id.input_tr_plan_spinner);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spinnerText = array[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        etTitle = (EditText)findViewById(R.id.et_transaction_plan_title);
        etSum = (EditText)findViewById(R.id.et_transaction_plan_sum);
        btnAdd = (Button)findViewById(R.id.btn_transaction_plan_add);
        btnCancel = (Button)findViewById(R.id.btn_transaction_plan_cancel);

        btnAdd.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()){
            case R.id.btn_transaction_plan_add:
                try{
                    if(etTitle.getText().toString().equals("" )
                            && etSum.getText().toString().equals("")){
                        Toast.makeText(this, "Please input title and sum", Toast.LENGTH_LONG).show();
                    } else {
                        double inputSum = Math.rint(100.0 *
                                Double.parseDouble(etSum.getText().toString())) / 100.0;
                        if(inputSum < Math.pow(10, 4)){
                            intent.putExtra("title", etTitle.getText().toString());
                            intent.putExtra("sum", inputSum);
                            intent.putExtra("category", spinnerText);
                            setResult(RESULT_OK, intent);
                            finish();
                        } else {
                            Toast.makeText(this, "Sum must be less than 10000 BYN",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e){
                    Toast.makeText(this, "Please input valid sum", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.btn_transaction_plan_cancel:
                finish();
                break;
        }
    }
}
