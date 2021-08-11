package com.dcvg.du_an_mau.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.dcvg.du_an_mau.R;
import com.dcvg.du_an_mau.dao.BookDAO;
import com.dcvg.du_an_mau.dao.CardDAO;
import com.dcvg.du_an_mau.dao.MemberDAO;
import com.dcvg.du_an_mau.helper.Config;
import com.dcvg.du_an_mau.model.Card;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardHolder> {

    private Context context;
    private List<Card> cardList;
    BookDAO bookDAO;
    MemberDAO memberDAO;
    CardDAO cardDAO;
    public CardAdapter(Context context, List<Card> cardList) {
        this.context = context;
        this.cardList = cardList;
        bookDAO = new BookDAO(context);
        memberDAO = new MemberDAO(context);
        cardDAO = new CardDAO(context);
    }

    @NonNull
    @org.jetbrains.annotations.NotNull

    @Override
    public CardHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item, parent, false);
        return new CardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull CardHolder holder, int position) {
        holder.card = cardList.get(position);

        String cardId = holder.card.getCard_id();
        String cardDate = holder.card.getCard_date();
        Double cardPrice = holder.card.getPrice();
        boolean[] return_book = {holder.card.isReturn_card()};

        String memberName = memberDAO.getNameMemberById(holder.card.getMember_id());
        List<String> member_names = new ArrayList<>();
        List<Object[]> nameAndIdMembers = memberDAO.getAllNameAndIdMembers();
        for (Object[] object : nameAndIdMembers) {
            member_names.add(String.valueOf(object[1]));
        }

        String bookName = bookDAO.getNameBookById(holder.card.getBook_id());
        List<String> book_names = new ArrayList<>();
        List<Object[]> nameAndIdBooks = bookDAO.getAllNameAndIdBook();
        for (Object[] object : nameAndIdBooks) {
            book_names.add(String.valueOf(object[1]));
        }

        holder.edtIdCard.setText(cardId);
        holder.edtIdMember.setText(holder.card.getMember_id());
        holder.edtIdBook.setText(holder.card.getBook_id());
        holder.edtIdBook.setText(bookName);
        holder.edtIdMember.setText(memberName);
        holder.edtPriceCard.setText(Config.decimalFormat.format(cardPrice));
        holder.edtStatusCard.setText(return_book[0] ? "Đã trả sách" : "Chưa trả sách");
        holder.edtDateCard.setText(holder.card.getCard_date());

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                AlertDialog alertDialog = dialogBuilder.create();
                LayoutInflater inflater = LayoutInflater.from(context);
                View dialogView = inflater.inflate(R.layout.custom_update_card_dialog, null);
                alertDialog.setView(dialogView);

                TextView tvCardId = (TextView) dialogView.findViewById(R.id.tvCardId);
                Spinner spMemberId = (Spinner) dialogView.findViewById(R.id.spMemberId);
                Spinner spBookId = (Spinner) dialogView.findViewById(R.id.spBookId);
                TextView tvCardDate = (TextView) dialogView.findViewById(R.id.tvCardDate);
                TextView tvCardPrice = (TextView) dialogView.findViewById(R.id.tvCardPrice);
                Button btnSaveCard = (Button) dialogView.findViewById(R.id.btnSaveCard);
                Button btnCloseDialog = (Button) dialogView.findViewById(R.id.btnCloseDialog);
                CheckBox chkReturn = (CheckBox) dialogView.findViewById(R.id.chkReturn);


                ArrayAdapter<String> member_adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, member_names);
                member_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spMemberId.setAdapter(member_adapter);
                spMemberId.setSelection(member_adapter.getPosition(memberName));
                final String[] idMember = {String.valueOf(nameAndIdMembers.get(spMemberId.getSelectedItemPosition())[0])};
                spMemberId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idMember[0] = String.valueOf(nameAndIdMembers.get(spMemberId.getSelectedItemPosition())[0]);
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


                ArrayAdapter<String> book_adapter = new ArrayAdapter<String>(context,
                        android.R.layout.simple_spinner_item, book_names);
                book_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spBookId.setAdapter(book_adapter);
                spBookId.setSelection(book_adapter.getPosition(bookName));
                final String[] idBook = {String.valueOf(nameAndIdBooks.get(spBookId.getSelectedItemPosition())[0])};
                spBookId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idBook[0] = String.valueOf(nameAndIdBooks.get(spBookId.getSelectedItemPosition())[0]);
                        tvCardPrice.setText(Config.decimalFormat.format(Double.parseDouble(bookDAO.getPriceBookById(idBook[0]))));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                tvCardId.setText(cardId);
                tvCardDate.setText(cardDate);
                tvCardPrice.setText(Config.decimalFormat.format(cardPrice));
                chkReturn.setChecked(return_book[0]);

                chkReturn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        return_book[0] = chkReturn.isChecked();
                    }
                });

                btnSaveCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id = holder.card.getCard_id();
                       if(cardDAO.updateCard(new Card(id, idMember[0], bookDAO.getCateGoryBookById(idBook[0]), idBook[0], cardDate, Double.parseDouble(tvCardPrice.getText().toString()), return_book[0]), id) > 0) {
                           cardList.clear();
                           cardList = cardDAO.getAllCards();
                           notifyDataSetChanged();
                           Toast.makeText(context, "Sửa thành công", Toast.LENGTH_SHORT).show();
                           alertDialog.dismiss();
                       } else {
                           Toast.makeText(context, "Sửa thất bại", Toast.LENGTH_SHORT).show();
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
                        .setMessage("Bạn có chắc chắn muốn xóa phiếu này ?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                if (cardDAO.deleteCard(holder.card.getCard_id()) > 0) {
                                    cardList.remove(position);
                                    cardList.clear();
                                    cardList = cardDAO.getAllCards();
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
        return cardList.size();
    }

    public class CardHolder extends RecyclerView.ViewHolder {

        private TextView edtIdCard;
        private TextView edtIdMember;
        private TextView edtIdBook;
        private TextView edtPriceCard;
        private TextView edtStatusCard;
        private TextView edtDateCard;
        private ImageView btnDelete;
        private Card card;

        public CardHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            edtIdCard = (TextView) itemView.findViewById(R.id.edtIdCard);
            edtIdMember = (TextView) itemView.findViewById(R.id.edtIdMember);
            edtIdBook = (TextView) itemView.findViewById(R.id.edtIdBook);
            edtPriceCard = (TextView) itemView.findViewById(R.id.edtPriceCard);
            edtStatusCard = (TextView) itemView.findViewById(R.id.edtStatusCard);
            edtDateCard = (TextView) itemView.findViewById(R.id.edtDateCard);
            btnDelete = (ImageView) itemView.findViewById(R.id.btnDelete);
        }
    }
}
