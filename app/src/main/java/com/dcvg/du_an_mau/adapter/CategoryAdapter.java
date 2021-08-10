package com.dcvg.du_an_mau.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.dcvg.du_an_mau.R;
import com.dcvg.du_an_mau.dao.CategoryDAO;
import com.dcvg.du_an_mau.model.Category;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryHolder>  {

    private Context context;
    private List<Category> categoryList;
    private CategoryDAO categoryDAO;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        categoryDAO = new CategoryDAO(context);
    }

    @NonNull
    @NotNull
    @Override
    public CategoryAdapter.CategoryHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_item, parent, false);
        return new CategoryAdapter.CategoryHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull CategoryAdapter.CategoryHolder holder, int position) {
        holder.category = categoryList.get(position);
        String idCategory = holder.category.getCategory_id();
        String nameCategory = holder.category.getCategory_name();
        holder.tvIdCategory.setText(idCategory);
        holder.tvNameCategory.setText(nameCategory);

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                AlertDialog alertDialog = dialogBuilder.create();
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.custom_update_category_dialog, null);
                alertDialog.setView(dialogView);

                TextView tvIdCategory = (TextView) dialogView.findViewById(R.id.tvIdCategory);
                EditText edtNameCategory = (EditText) dialogView.findViewById(R.id.edtNameCategory);
                Button btnSave = (Button) dialogView.findViewById(R.id.btnSave);
                Button btnCloseDialog = (Button) dialogView.findViewById(R.id.btnCloseDialog);

                tvIdCategory.setText(idCategory);
                edtNameCategory.setText(nameCategory);

                btnSave.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String name_category = edtNameCategory.getText().toString().trim();
                        if (name_category.isEmpty()) {
                            Toast.makeText(context, "Vui lòng điền tên loại sách", Toast.LENGTH_SHORT).show();
                        } else {
                            if(categoryDAO.updateCategory(new Category(idCategory, name_category), idCategory) > 0) {
                                categoryList.clear();
                                categoryList = categoryDAO.getAllCategory();
                                notifyDataSetChanged();
                                Toast.makeText(context, "Lưu thành công", Toast.LENGTH_SHORT).show();
                                alertDialog.dismiss();
                            } else {
                                Toast.makeText(context, "Lưu thất bại", Toast.LENGTH_SHORT).show();
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

        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setMessage("Bạn có chắc chắn muốn xóa loại sách này ?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (categoryDAO.deleteCategory(idCategory) > 0) {
                                    categoryList.remove(position);
                                    categoryList.clear();
                                    categoryList = categoryDAO.getAllCategory();
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
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {

        private TextView tvIdCategory;
        private TextView tvNameCategory;
        private ImageView btnDelete;
        private Category category;

        public CategoryHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            tvIdCategory = (TextView) itemView.findViewById(R.id.tvIdCategory);
            tvNameCategory = (TextView) itemView.findViewById(R.id.tvNameCategory);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDelete);
        }
    }
}
