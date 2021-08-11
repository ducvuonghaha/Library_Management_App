package com.dcvg.du_an_mau.screen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.dcvg.du_an_mau.R;
import com.dcvg.du_an_mau.dao.LibrarianDAO;

public class LoginActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private CheckBox chkSavePass;
    private Button btnLogin;
    private LibrarianDAO librarianDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("My Data", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        librarianDAO = new LibrarianDAO(this);

        boolean remember = sharedPreferences.getBoolean("remember", true);
        if (remember) {
            edtUsername.setText(sharedPreferences.getString("usernameRemember", ""));
            edtPassword.setText(sharedPreferences.getString("passwordRemember", ""));
            chkSavePass.setChecked(true);
        }


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng điền đầy đủ thông tin đăng nhập", Toast.LENGTH_SHORT).show();
                } else {
                    if (librarianDAO.login(username, password) > 0) {
                        editor.putString("username", username);
                        editor.putString("password", password);
                        if (chkSavePass.isChecked()) {
                            editor.putString("usernameRemember", username);
                            editor.putString("passwordRemember", password);
                            editor.putBoolean("remember", true);
                        } else {
                            editor.putString("usernameRemember", "");
                            editor.putString("passwordRemember", "");
                            editor.putBoolean("remember", false);
                        }
                        editor.apply();
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                    } else {
                        Toast.makeText(LoginActivity.this, "Sai thông tin", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        return;
    }

    private void initView() {
        edtUsername = (EditText) findViewById(R.id.edtUsername);
        edtPassword = (EditText) findViewById(R.id.edtPassword);
        chkSavePass = (CheckBox) findViewById(R.id.chkSavePass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
    }
}