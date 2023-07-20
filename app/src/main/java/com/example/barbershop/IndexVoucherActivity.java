package com.example.barbershop;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.barbershop.Domain.Voucher;
import com.example.barbershop.Module.VoucherDataSource;

import java.util.ArrayList;
import java.util.Calendar;

public class IndexVoucherActivity extends AppCompatActivity {
    Button btnCreateVoucher;
    ListView lvVouchers;
    ArrayList<Voucher> vouchers = new ArrayList<>();
    ArrayAdapter adapterListView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.index_vouchers);

        btnCreateVoucher = findViewById(R.id.btnCreateVoucher);
        lvVouchers = findViewById(R.id.lvVouchers);
        vouchers = VoucherDataSource.selectVouchers(IndexVoucherActivity.this);
        adapterListView = new ArrayAdapter<>(IndexVoucherActivity.this, android.R.layout.simple_list_item_1, vouchers);
        lvVouchers.setAdapter(adapterListView);

        btnCreateVoucher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogCreateVoucher();
            }
        });

        lvVouchers.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showDialogUpdateVoucher(position);
            }
        });


    }

    private void showDialogCreateVoucher() {
        AlertDialog.Builder builder = new AlertDialog.Builder(IndexVoucherActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_create_voucher, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText createName = view.findViewById(R.id.createName);
        EditText createCode = view.findViewById(R.id.createCode);
        EditText createValue = view.findViewById(R.id.createValue);
        EditText createQuantity = view.findViewById(R.id.createQuantity);
        Button createStartTime = view.findViewById(R.id.createStartTime);
        Button createEndTime = view.findViewById(R.id.createEndTime);
        Button btnAdd = view.findViewById(R.id.btnAdd);

        createStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(IndexVoucherActivity.this, new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year,
                                                  int monthOfYear, int dayOfMonth) {
                                // on below line we are setting date to our text view.
                                createStartTime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });

        createEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(IndexVoucherActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our text view.
                        createEndTime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Voucher voucher = new Voucher();
                voucher.setName(createName.getText().toString());
                voucher.setCode(createCode.getText().toString());
                voucher.setValue(Double.parseDouble(createValue.getText().toString()));
                voucher.setQuantity(Integer.parseInt(createQuantity.getText().toString()));
                voucher.setStartTime(createStartTime.getText().toString());
                voucher.setEndTime(createEndTime.getText().toString());
                VoucherDataSource voucherDataSource = new VoucherDataSource(IndexVoucherActivity.this);
//                Toast.makeText(CategoryActivity.this, "Success!", Toast.LENGTH_SHORT).show();
                if (voucherDataSource.addVoucher(voucher) instanceof Voucher) {
                    Toast.makeText(IndexVoucherActivity.this, "Thêm thành công!", Toast.LENGTH_SHORT).show();
                    vouchers.clear();
                    vouchers.addAll(VoucherDataSource.selectVouchers(IndexVoucherActivity.this));
                    adapterListView.notifyDataSetChanged();
                    dialog.dismiss();
                } else {
                    Toast.makeText(IndexVoucherActivity.this, "Thêm thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void showDialogUpdateVoucher(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(IndexVoucherActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_update_voucher, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();

        EditText editName = view.findViewById(R.id.editName);
        EditText editCode = view.findViewById(R.id.editCode);
        EditText editValue = view.findViewById(R.id.editValue);
        EditText editQuantity = view.findViewById(R.id.editQuantity);
        Button editStartTime = view.findViewById(R.id.editStartTime);
        Button editEndTime = view.findViewById(R.id.editEndTime);

        Button btnUpdate = view.findViewById(R.id.btnUpdate);
        Button btnDelete = view.findViewById(R.id.btnDelete);

        Voucher voucher = vouchers.get(pos);

        editName.setText(voucher.getName());
        editCode.setText(voucher.getCode());
        editValue.setText(voucher.getValue().toString());
        editQuantity.setText(voucher.getQuantity().toString());
        editStartTime.setText("Ngày bắt đầu: " + voucher.getStartTime());
        editEndTime.setText("Ngày kết thúc: " + voucher.getEndTime());

        editStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(IndexVoucherActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our text view.
                        editStartTime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        editEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // on below line we are getting
                // the instance of our calendar.
                final Calendar c = Calendar.getInstance();

                // on below line we are getting
                // our day, month and year.
                int year = c.get(Calendar.YEAR);
                int month = c.get(Calendar.MONTH);
                int day = c.get(Calendar.DAY_OF_MONTH);

                // on below line we are creating a variable for date picker dialog.
                DatePickerDialog datePickerDialog = new DatePickerDialog(IndexVoucherActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        // on below line we are setting date to our text view.
                        editEndTime.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                voucher.setName(editName.getText().toString());
                voucher.setCode(editCode.getText().toString());
                voucher.setValue(Double.parseDouble(editValue.getText().toString()));
                voucher.setQuantity(Integer.parseInt(editQuantity.getText().toString()));
                voucher.setStartTime(editStartTime.getText().toString());
                voucher.setEndTime(editEndTime.getText().toString());

                if(VoucherDataSource.updateService(IndexVoucherActivity.this, voucher)) {
                    Toast.makeText(IndexVoucherActivity.this, "Cập nhật thành công!", Toast.LENGTH_SHORT).show();
                    vouchers.clear();
                    vouchers.addAll(VoucherDataSource.selectVouchers(IndexVoucherActivity.this));
                    adapterListView.notifyDataSetChanged();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(IndexVoucherActivity.this, "Cập nhật thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(VoucherDataSource.deleteVoucher(IndexVoucherActivity.this, voucher.getId())) {
                    Toast.makeText(IndexVoucherActivity.this, "Xóa thành công!", Toast.LENGTH_SHORT).show();
                    vouchers.clear();
                    vouchers.addAll(VoucherDataSource.selectVouchers(IndexVoucherActivity.this));
                    adapterListView.notifyDataSetChanged();
                    dialog.dismiss();
                }
                else {
                    Toast.makeText(IndexVoucherActivity.this, "Xóa thất bại!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
