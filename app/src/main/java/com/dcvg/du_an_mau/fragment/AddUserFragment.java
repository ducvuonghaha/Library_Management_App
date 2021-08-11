package com.dcvg.du_an_mau.fragment;

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
import com.dcvg.du_an_mau.model.Librarian;

import org.jetbrains.annotations.NotNull;

public class AddUserFragment extends Fragment {

    private EditText edtUsername;
    private EditText edtFullname;
    private EditText edtPassword;
    private EditText edtRepassword;
    private Button btnSave;
    private LibrarianDAO librarianDAO;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.add_user_fragment, container, false);
        initView(view);
        librarianDAO = new LibrarianDAO(getContext());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = edtUsername.getText().toString().trim();
                String fullName = edtFullname.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String rePassword = edtRepassword.getText().toString().trim();
                if (userName.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền tên đăng nhập", Toast.LENGTH_SHORT).show();
                } else if (librarianDAO.checkLibrarianExist(userName) > 0) {
                    Toast.makeText(getContext(), "Tên đăng nhập đã tồn tại", Toast.LENGTH_SHORT).show();
                } else if (fullName.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng điền họ tên", Toast.LENGTH_SHORT).show();
                } else if (password.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (rePassword.isEmpty()) {
                    Toast.makeText(getContext(), "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
                } else if (!password.equals(rePassword)) {
                    Toast.makeText(getContext(), "Mật khẩu chưa khớp", Toast.LENGTH_SHORT).show();
                } else {
                    if (librarianDAO.insertLibrarianDAO(new Librarian(password, fullName, userName)) > 0) {
                        Toast.makeText(getContext(), "Lưu thành công", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Lưu thất bại", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        return view;
    }

    private void initView(View view) {
        edtUsername = (EditText) view.findViewById(R.id.edtUsername);
        edtFullname = (EditText) view.findViewById(R.id.edtFullname);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        edtRepassword = (EditText) view.findViewById(R.id.edtRepassword);
        btnSave = (Button) view.findViewById(R.id.btnSave);

    }
}
