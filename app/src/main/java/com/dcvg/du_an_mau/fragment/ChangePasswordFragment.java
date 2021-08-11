package com.dcvg.du_an_mau.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.dcvg.du_an_mau.R;
import com.dcvg.du_an_mau.dao.LibrarianDAO;

import org.jetbrains.annotations.NotNull;

import static android.content.Context.MODE_PRIVATE;

public class ChangePasswordFragment extends Fragment {

    private EditText edtOldPassword;
    private EditText edtPassword;
    private EditText edtRepassword;
    private Button btnSave;
    private LibrarianDAO librarianDAO;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.change_password_fragment, container, false);
        initView(view);
        librarianDAO = new LibrarianDAO(getContext());
        SharedPreferences sharedPreferences = getActivity().getApplicationContext().getSharedPreferences("My Data", MODE_PRIVATE);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String oldPassword = edtOldPassword.getText().toString().trim();
               String password = edtPassword.getText().toString().trim();
               String rePassword = edtRepassword.getText().toString().trim();
               String oldPasswordSaved = librarianDAO.getPasswordByUsername(sharedPreferences.getString("username", ""));
               if (oldPassword.isEmpty()) {
                   Toast.makeText(getContext(), "Vui lòng điền mật khẩu cũ", Toast.LENGTH_SHORT).show();
               } else if (password.isEmpty()) {
                   Toast.makeText(getContext(), "Vui lòng điền mật khẩu mới", Toast.LENGTH_SHORT).show();
               } else if (rePassword.isEmpty()) {
                   Toast.makeText(getContext(), "Vui lòng điền nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
               } else if(!oldPassword.equals(oldPasswordSaved)) {
                   Toast.makeText(getContext(), "Mật khẩu cũ chưa đúng", Toast.LENGTH_SHORT).show();
               } else if (password.equals(oldPasswordSaved)) {
                   Toast.makeText(getContext(), "Mật khẩu mới trùng với mật khẩu cũ", Toast.LENGTH_SHORT).show();
               } else if (!password.equals(rePassword)) {
                   Toast.makeText(getContext(), "Mật khẩu chưa khớp", Toast.LENGTH_SHORT).show();
               } else {
                   if (librarianDAO.changePassword(password, sharedPreferences.getString("username", "")) > 0) {
                       Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                   } else {
                       Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                   }
               }
            }
        });
        return view;
    }

    private void initView(View view) {
        edtOldPassword = (EditText) view.findViewById(R.id.edtOldPassword);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        edtRepassword = (EditText) view.findViewById(R.id.edtRepassword);
        btnSave = (Button) view.findViewById(R.id.btnSave);
    }
}
