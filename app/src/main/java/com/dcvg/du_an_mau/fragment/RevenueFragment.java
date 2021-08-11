package com.dcvg.du_an_mau.fragment;

import android.app.DatePickerDialog;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.dcvg.du_an_mau.R;
import com.dcvg.du_an_mau.dao.CardDAO;
import com.dcvg.du_an_mau.helper.Config;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class RevenueFragment extends Fragment {

    private EditText edtStartDate;
    private EditText edtEndDate;
    private TextView tvTotal;
    private Button btnGetTotal;
    private Button btnGetStartDate;
    private Button btnGetEndDate;
    private CardDAO cardDAO;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_revenue, container, false);
        initView(view);

        cardDAO = new CardDAO(getContext());
        final Calendar myCalendar = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener startDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edtStartDate.setText((dayOfMonth < 10 ? "0" :"")+ dayOfMonth + "-" + (monthOfYear < 10 ? "0" :"") + (monthOfYear + 1) + "-" + year);
            }
        };

        DatePickerDialog.OnDateSetListener endDate = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                // TODO Auto-generated method stub
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                edtEndDate.setText((dayOfMonth < 10 ? "0" :"")+dayOfMonth + "-" + (monthOfYear < 10 ? "0" :"") + (monthOfYear + 1) + "-" + year);
            }
        };

        btnGetStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), startDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnGetEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(getActivity(), endDate, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnGetTotal.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                String startDate = edtStartDate.getText().toString().trim();
                String endDate = edtEndDate.getText().toString().trim();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

                if (startDate.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền thời gian bắt đàu", Toast.LENGTH_SHORT).show();
                    tvTotal.setText("0");
                } else if (endDate.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền thời gian kết thúc", Toast.LENGTH_SHORT).show();
                    tvTotal.setText("0");
                } else {
                    try {
                        Date sd = dateFormat.parse(startDate);
                        Date ed = dateFormat.parse(endDate);
                        if (sd.after(ed)) {
                            Toast.makeText(getContext(), "Thời gian kết thúc phải sau thời gian bắt đầu", Toast.LENGTH_SHORT).show();
                            tvTotal.setText("0");
                        } else {
                            tvTotal.setText(Config.decimalFormat.format(cardDAO.getTotalByTime(startDate, endDate)));
                        }
                    } catch (ParseException e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "Thời gian không đúng định dạng", Toast.LENGTH_SHORT).show();
                        tvTotal.setText("0");
                    }
                }
            }
        });
        return view;
    }

    private void initView(View view) {
        edtStartDate = (EditText) view.findViewById(R.id.edtStartDate);
        edtEndDate = (EditText) view.findViewById(R.id.edtEndDate);
        tvTotal = (TextView) view.findViewById(R.id.tvTotal);
        btnGetTotal = (Button) view.findViewById(R.id.btnGetTotal);
        btnGetStartDate = (Button) view.findViewById(R.id.btnGetStartDate);
        btnGetEndDate = (Button) view.findViewById(R.id.btnGetEndDate);
    }
}
