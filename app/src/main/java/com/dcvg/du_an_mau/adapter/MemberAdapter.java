package com.dcvg.du_an_mau.adapter;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dcvg.du_an_mau.R;
import com.dcvg.du_an_mau.dao.MemberDAO;
import com.dcvg.du_an_mau.model.Member;

import org.jetbrains.annotations.NotNull;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class MemberAdapter extends RecyclerView.Adapter<MemberAdapter.Holder> {

    private Context context;
    private List<Member> memberList;
    private MemberDAO memberDAO;

    public MemberAdapter(Context context, List<Member> memberList) {
        this.context = context;
        this.memberList = memberList;
        memberDAO = new MemberDAO(context);
    }

    @NonNull
    @NotNull
    @Override
    public Holder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.member_item, parent, false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MemberAdapter.Holder holder, int position) {
        holder.member = memberList.get(position);
        String idMember = holder.member.getMember_id();
        String nameMember = holder.member.getMember_name();
        String birth = holder.member.getBirth();
        holder.tvIdMember.setText(idMember);
        holder.tvNameMember.setText(nameMember);
        holder.tvBirth.setText(birth);

        final Calendar myCalendar = Calendar.getInstance();

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage("Bạn có chắc chắn muốn xóa thành viên này ?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (memberDAO.deleteMember(idMember) > 0) {
                                    memberList.remove(position);
                                    memberList.clear();
                                    memberList = memberDAO.getAllMembers();
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Xóa thành công", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Xóa thất bại", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("Hủy", null)
                        .show();
            }
        });

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                AlertDialog alertDialog = dialogBuilder.create();
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.custom_update_member_dialog, null);
                alertDialog.setView(dialogView);

                TextView tvMemberId = (TextView) dialogView.findViewById(R.id.tvMemberId);
                EditText edtNameMember = (EditText) dialogView.findViewById(R.id.edtNameMember);
                EditText edtBirth = (EditText) dialogView.findViewById(R.id.edtBirth);
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

                tvMemberId.setText(idMember);
                edtNameMember.setText(nameMember);
                edtBirth.setText(birth);

                edtBirth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new DatePickerDialog(context, date, myCalendar
                                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                                myCalendar.get(Calendar.DAY_OF_MONTH)).show();
                    }
                });

                btnSaveCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name_member = edtNameMember.getText().toString().trim();
                        String birth_member = edtBirth.getText().toString().trim();
                        if(name_member.isEmpty()) {
                            Toast.makeText(context, "Vui lòng nhập tên thành viên", Toast.LENGTH_SHORT).show();
                        } else if (birth_member.isEmpty()) {
                            Toast.makeText(context, "Vui lòng nhập ngày sinh", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                Date dp = dateFormat.parse(birth_member);
                                if(memberDAO.updateMember(new Member(idMember, name_member, birth_member), idMember) > 0) {
                                    memberList.clear();
                                    memberList = memberDAO.getAllMembers();
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Lưu thành công", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                } else {
                                    Toast.makeText(context, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                                }
                            } catch (ParseException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "Ngày sinh không đúng định dạng", Toast.LENGTH_SHORT).show();
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
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }

    public class Holder extends RecyclerView.ViewHolder {

        private TextView tvIdMember;
        private TextView tvNameMember;
        private TextView tvBirth;
        private ImageView btnDelete;
        private Member member;

        public Holder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvIdMember = (TextView) itemView.findViewById(R.id.tvIdMember);
            tvNameMember = (TextView) itemView.findViewById(R.id.tvNameMember);
            tvBirth = (TextView) itemView.findViewById(R.id.tvBirth);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDelete);
        }
    }
}
