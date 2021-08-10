package com.dcvg.du_an_mau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dcvg.du_an_mau.R;
import com.dcvg.du_an_mau.adapter.BookAdapter;
import com.dcvg.du_an_mau.adapter.CardAdapter;
import com.dcvg.du_an_mau.adapter.CategoryAdapter;
import com.dcvg.du_an_mau.dao.BookDAO;
import com.dcvg.du_an_mau.dao.CategoryDAO;
import com.dcvg.du_an_mau.model.Book;
import com.dcvg.du_an_mau.model.Category;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BookFragment extends Fragment {

    private RecyclerView rcvList;
    private FloatingActionButton btnAdd;
    private List<Book> bookList;
    private BookDAO bookDAO;
    private BookAdapter bookAdapter;
    private CategoryDAO categoryDAO;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_management, container, false);
        initView(view);
        bookDAO = new BookDAO(getContext());
        categoryDAO = new CategoryDAO(getContext());
        bookList = new ArrayList<>();
        bookList = bookDAO.getAllBook();
        bookAdapter = new BookAdapter(getContext(), bookList);
        rcvList.setAdapter(bookAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvList.setLayoutManager(linearLayoutManager);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                AlertDialog alertDialog = dialogBuilder.create();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View dialogView = inflater.inflate(R.layout.custom_add_book_dialog, null);
                alertDialog.setView(dialogView);

                EditText edtBookId = (EditText) dialogView.findViewById(R.id.edtBookId);
                EditText edtBookName = (EditText) dialogView.findViewById(R.id.edtBookName);
                EditText edtBookPrice = (EditText) dialogView.findViewById(R.id.edtBookPrice);
                Spinner spBookCategory = (Spinner) dialogView.findViewById(R.id.spBookCategory);
                Button btnSave = (Button) dialogView.findViewById(R.id.btnSave);
                Button btnCloseDialog = (Button) dialogView.findViewById(R.id.btnCloseDialog);

                List<Object[]> nameAndIdCategories = categoryDAO.getAllNameAndIdCategory();
                List<String> name_categories = new ArrayList<>();
                for (Object[] object : nameAndIdCategories) {
                    name_categories.add(String.valueOf(object[1]));
                }

                ArrayAdapter<String> category_adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, name_categories);
                category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spBookCategory.setAdapter(category_adapter);
                final String[] idCategory = {String.valueOf(nameAndIdCategories.get(spBookCategory.getSelectedItemPosition())[0])};

                spBookCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idCategory[0] = String.valueOf(nameAndIdCategories.get(spBookCategory.getSelectedItemPosition())[0]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String idBook = edtBookId.getText().toString().trim();
                        String nameBook = edtBookName.getText().toString().trim();
                        String priceBook = edtBookPrice.getText().toString().trim();
                        if(idBook.isEmpty() || nameBook.isEmpty() || priceBook.isEmpty()) {
                            Toast.makeText(getContext(), "Vui lòng diền đầy đủ thông tin sách", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                if(bookDAO.insertBook(new Book(idBook, nameBook, Double.parseDouble(priceBook), idCategory[0])) > 0) {
                                    bookList.clear();
                                    bookList = bookDAO.getAllBook();
                                    bookAdapter = new BookAdapter(getContext(), bookList);
                                    rcvList.setAdapter(bookAdapter);
                                    rcvList.setLayoutManager(linearLayoutManager);
                                    bookAdapter.notifyDataSetChanged();
                                    Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                } else {
                                    Toast.makeText(getContext(), "Thêm thất bại", Toast.LENGTH_SHORT).show();
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Giá phải là số", Toast.LENGTH_SHORT).show();
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
