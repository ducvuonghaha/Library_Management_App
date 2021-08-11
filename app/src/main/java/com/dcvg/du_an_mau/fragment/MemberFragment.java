package com.dcvg.du_an_mau.fragment;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dcvg.du_an_mau.R;
import com.dcvg.du_an_mau.adapter.MemberAdapter;
import com.dcvg.du_an_mau.dao.MemberDAO;
import com.dcvg.du_an_mau.model.Member;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MemberFragment extends Fragment {

    private RecyclerView rcvList;
    private FloatingActionButton btnAdd;
    private MemberDAO memberDAO;
    private List<Member> memberList;
    private MemberAdapter memberAdapter;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_management, container, false);
        initView(view);
        memberList = new ArrayList<>();
        memberDAO = new MemberDAO(getContext());
        memberList = memberDAO.getAllMembers();
        memberAdapter = new MemberAdapter(getContext(), memberList);
        rcvList.setAdapter(memberAdapter);
        rcvList.setLayoutManager(new LinearLayoutManager(getContext()));

        final Calendar myCalendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                AlertDialog alertDialog = dialogBuilder.create();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View dialogView = inflater.inflate(R.layout.custom_add_member_dialog, null);
                alertDialog.setView(dialogView);

                EditText edtMemberId = (EditText) dialogView.findViewById(R.id.edtMemberId);
                EditText edtNameMember = (EditText) dialogView.findViewById(R.id.edtNameMember);
                EditText  edtBirth = (EditText) dialogView.findViewById(R.id.edtBirth);
                Button btnSaveCard = (Button) dialogView.findViewById(R.id.btnSaveCard);
                Button btnCloseDialog = (Button) dialogView.findViewById(R.id.btnCloseDialog);

                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear,
                                          int dayOfMonth) {
                        // TODO Auto-generated method stub
                        myCalendar.set(Calendar.YEAR, year);
                        myCalendar.set(Calendar.MONTH, monthOfYear);
                        myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                        edtBirth.setText((dayOfMonth < 10 ? "0" :"")+ dayOfMonth + "-" + (monthOfYear < 10 ? "0" :"") + (monthOfYear + 1) + "-" + year);
                    }
                };

                edtBirth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(getActivity(), date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                btnSaveCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String memberId = edtMemberId.getText().toString().trim();
                        String memberName = edtNameMember.getText().toString().trim();
                        String memberBirth = edtBirth.getText().toString().trim();
                        if (memberId.isEmpty()) {
                            Toast.makeText(getContext(), "Vui lòng điền mã thành viên", Toast.LENGTH_SHORT).show();
                        } else if (memberName.isEmpty()) {
                            Toast.makeText(getContext(), "Vui lòng điền tên thành viên", Toast.LENGTH_SHORT).show();
                        } else if (memberBirth.isEmpty()) {
                            Toast.makeText(getContext(), "Vui lòng điền ngày sinh", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                Date checkDate = dateFormat.parse(memberBirth);
                                if (memberDAO.insertMember(new Member(memberId, memberName, memberBirth)) > 0) {
                                    memberList.clear();
                                    memberList = memberDAO.getAllMembers();
                                    memberAdapter = new MemberAdapter(getContext(), memberList);
                                    rcvList.setAdapter(memberAdapter);
                                    rcvList.setLayoutManager(new LinearLayoutManager(getContext()));
                                    memberAdapter.notifyDataSetChanged();
                                    Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Lưu thất bại", Toast.LENGTH_SHORT).show();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Ngày không đúng định dạng", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }
                });

                btnCloseDialog.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });

                alertDialog.show();
            }
        });
        return view;
    }

    private void initView(View view) {
        rcvList = (RecyclerView) view.findViewById(R.id.rcvList);
        btnAdd = (FloatingActionButton) view.findViewById(R.id.btnAdd);
    }
}
