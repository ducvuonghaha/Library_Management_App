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
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dcvg.du_an_mau.R;
import com.dcvg.du_an_mau.adapter.CardAdapter;
import com.dcvg.du_an_mau.adapter.CategoryAdapter;
import com.dcvg.du_an_mau.dao.CategoryDAO;
import com.dcvg.du_an_mau.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment {

    private RecyclerView rcvCategories;
    private FloatingActionButton btnAddCategory;
    private List<Category> categoryList;
    private CategoryDAO categoryDAO;
    private CategoryAdapter categoryAdapter;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
         super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_management, container, false);
        initView(view);
        categoryDAO = new CategoryDAO(getContext());
        categoryList = new ArrayList<>();
//
//        categoryList.add(new Category("CG1", "Tiểu thuyết"));
//        categoryList.add(new Category("CG2", "Kỹ năng sống"));
//        categoryList.add(new Category("CG3", "Kinh tế"));
//
//        for (Category category : categoryList) {
//            categoryDAO.insertCategory(category);
//        }
        categoryList.clear();

        categoryList = categoryDAO.getAllCategory();
        categoryAdapter = new CategoryAdapter(getContext(), categoryList);
        rcvCategories.setAdapter(categoryAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvCategories.setLayoutManager(linearLayoutManager);

        btnAddCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                AlertDialog alertDialog = dialogBuilder.create();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View dialogView = inflater.inflate(R.layout.custom_add_category_dialog, null);
                alertDialog.setView(dialogView);

                EditText tvIdCategory = (EditText) dialogView.findViewById(R.id.tvIdCategory);
                EditText tvNameCategory = (EditText) dialogView.findViewById(R.id.tvNameCategory);
                Button btnSave = (Button) dialogView.findViewById(R.id.btnSave);
                Button btnCloseDialog = (Button) dialogView.findViewById(R.id.btnCloseDialog);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idCategory = tvIdCategory.getText().toString().trim();
                        String nameCategory = tvNameCategory.getText().toString().trim();
                        if(idCategory.isEmpty() || nameCategory.isEmpty()) {
                            Toast.makeText(getContext(), "Vui lòng điền đầy đủ thông tin loại sách", Toast.LENGTH_SHORT).show();
                        } else if (categoryDAO.checkExistCategory(idCategory) > 0) {
                            Toast.makeText(getContext(), "Loại sách đã tồn tại", Toast.LENGTH_SHORT).show();
                        } else {
                            if (categoryDAO.insertCategory(new Category(idCategory, nameCategory)) > 0) {
                                categoryList.clear();
                                categoryList = categoryDAO.getAllCategory();
                                categoryAdapter = new CategoryAdapter(getContext(), categoryList);
                                rcvCategories.setAdapter(categoryAdapter);
                                rcvCategories.setLayoutManager(linearLayoutManager);
                                categoryAdapter.notifyDataSetChanged();
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
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
        rcvCategories = (RecyclerView) view.findViewById(R.id.rcvList);
        btnAddCategory = (FloatingActionButton) view.findViewById(R.id.btnAdd);
    }
}
