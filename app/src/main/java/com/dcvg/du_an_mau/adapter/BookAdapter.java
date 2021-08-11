package com.dcvg.du_an_mau.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dcvg.du_an_mau.R;
import com.dcvg.du_an_mau.dao.BookDAO;
import com.dcvg.du_an_mau.dao.CategoryDAO;
import com.dcvg.du_an_mau.helper.Config;
import com.dcvg.du_an_mau.model.Book;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BookAdapter extends RecyclerView.Adapter<BookAdapter.BookHolder> {

    private Context context;
    private List<Book> bookList;
    private CategoryDAO categoryDAO;
    private BookDAO bookDAO;

    public BookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
        categoryDAO = new CategoryDAO(context);
        bookDAO = new BookDAO(context);
    }

    @NonNull
    @NotNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.book_item, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull BookAdapter.BookHolder holder, int position) {
        holder.book = bookList.get(position);
        String idBook = holder.book.getBook_id();
        String nameBook = holder.book.getBook_name();
        String priceBook = String.valueOf(holder.book.getBook_price());
        String categoryName = categoryDAO.getNameCategoryById(holder.book.getCategory_id());
        holder.tvIdBook.setText(idBook);
        holder.tvNameBook.setText(nameBook);
        holder.tvCategoryBook.setText(categoryName);
        holder.tvPriceBook.setText(Config.decimalFormat.format(holder.book.getBook_price()));

        List<Object[]> nameAndIdCategories = categoryDAO.getAllNameAndIdCategory();
        List<String> name_categories = new ArrayList<>();
        for (Object[] object : nameAndIdCategories) {
            name_categories.add(String.valueOf(object[1]));
        }


        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(context)
                        .setMessage("Bạn có chắc chắn muốn xóa sách này ?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if(bookDAO.deleteBook(idBook) > 0) {
                                    bookList.remove(position);
                                    bookList.clear();
                                    bookList = bookDAO.getAllBook();
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

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                AlertDialog alertDialog = dialogBuilder.create();
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.custom_update_book_dialog, null);
                alertDialog.setView(dialogView);

                TextView tvBookId = (TextView) dialogView.findViewById(R.id.tvBookId);
                EditText edtBookName = (EditText) dialogView.findViewById(R.id.edtBookName);
                EditText edtBookPrice = (EditText) dialogView.findViewById(R.id.edtBookPrice);
                Spinner spBookCategory = (Spinner) dialogView.findViewById(R.id.spBookCategory);
                Button btnSave = (Button) dialogView.findViewById(R.id.btnSave);
                Button btnCloseDialog = (Button) dialogView.findViewById(R.id.btnCloseDialog);

                tvBookId.setText(idBook);
                edtBookName.setText(nameBook);
                edtBookPrice.setText(priceBook);

                ArrayAdapter<String> category_adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, name_categories);
                category_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spBookCategory.setAdapter(category_adapter);
                spBookCategory.setSelection(category_adapter.getPosition(categoryName));
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
                        String bookName = edtBookName.getText().toString().trim();
                        String bookPrice = edtBookPrice.getText().toString().trim();
                        if (bookName.isEmpty() || bookPrice.isEmpty()) {
                            Toast.makeText(context, "Vui lòng điền đầy đủ thông tin sách", Toast.LENGTH_SHORT).show();
                        } else {
                            try {
                                if(bookDAO.updateBook(new Book(idBook, bookName, Double.parseDouble(bookPrice), idCategory[0]), idBook) > 0) {
                                    bookList.clear();
                                    bookList = bookDAO.getAllBook();
                                    notifyDataSetChanged();
                                    Toast.makeText(context, "Lưu thành công", Toast.LENGTH_SHORT).show();
                                    alertDialog.dismiss();
                                } else {
                                    Toast.makeText(context, "Lưu thất bại", Toast.LENGTH_SHORT).show();
                                }
                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                                Toast.makeText(context, "Giá sách phải là số", Toast.LENGTH_SHORT).show();
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
        return bookList.size();
    }

    public class BookHolder extends RecyclerView.ViewHolder {

        private TextView tvIdBook;
        private TextView tvNameBook;
        private TextView tvPriceBook;
        private TextView tvCategoryBook;
        private ImageView btnDelete;
        private Book book;

        public BookHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvIdBook = (TextView) itemView.findViewById(R.id.tvIdBook);
            tvNameBook = (TextView) itemView.findViewById(R.id.tvNameBook);
            tvPriceBook = (TextView) itemView.findViewById(R.id.tvPriceBook);
            tvCategoryBook = (TextView) itemView.findViewById(R.id.tvCategoryBook);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDelete);
        }
    }
}
