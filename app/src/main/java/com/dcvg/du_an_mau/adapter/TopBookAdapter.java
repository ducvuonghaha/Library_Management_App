package com.dcvg.du_an_mau.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
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

public class TopBookAdapter extends RecyclerView.Adapter<TopBookAdapter.BookHolder> {

    private Context context;
    private List<Book> bookList;
    private CategoryDAO categoryDAO;

    public TopBookAdapter(Context context, List<Book> bookList) {
        this.context = context;
        this.bookList = bookList;
        categoryDAO = new CategoryDAO(context);
    }

    @NonNull
    @NotNull
    @Override
    public BookHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.top_book_item, parent, false);
        return new BookHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull TopBookAdapter.BookHolder holder, int position) {
        holder.book = bookList.get(position);
        holder.tvIdBook.setText(holder.book.getBook_id());
        holder.tvNameBook.setText(holder.book.getBook_name());
        holder.tvCategoryBook.setText(categoryDAO.getNameCategoryById(holder.book.getCategory_id()));
        holder.tvPriceBook.setText(Config.decimalFormat.format(holder.book.getBook_price()));
        holder.tvQuantityBorrows.setText(holder.book.getNumberOfBorrow());
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
        private TextView tvQuantityBorrows;

        public BookHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvIdBook = (TextView) itemView.findViewById(R.id.tvIdBook);
            tvNameBook = (TextView) itemView.findViewById(R.id.tvNameBook);
            tvPriceBook = (TextView) itemView.findViewById(R.id.tvPriceBook);
            tvCategoryBook = (TextView) itemView.findViewById(R.id.tvCategoryBook);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDelete);
            tvQuantityBorrows = (TextView) itemView.findViewById(R.id.tvQuantityBorrows);

        }
    }
}
