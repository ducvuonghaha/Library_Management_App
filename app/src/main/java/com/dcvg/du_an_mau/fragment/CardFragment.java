package com.dcvg.du_an_mau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dcvg.du_an_mau.R;
import com.dcvg.du_an_mau.adapter.CardAdapter;
import com.dcvg.du_an_mau.dao.BookDAO;
import com.dcvg.du_an_mau.dao.CardDAO;
import com.dcvg.du_an_mau.dao.MemberDAO;
import com.dcvg.du_an_mau.model.Book;
import com.dcvg.du_an_mau.model.Card;
import com.dcvg.du_an_mau.model.Member;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class CardFragment extends Fragment {

    private List<Card> cardList;
    private CardDAO cardDAO;
    private RecyclerView rcvCards;
    private FloatingActionButton btnAddCard;
    private MemberDAO memberDAO;
    private BookDAO bookDAO;
    private CardAdapter cardAdapter;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_management, container, false);
        initView(view);
        cardList = new ArrayList<>();
        cardDAO = new CardDAO(getContext());
//        cardList.add(new Card("C1","M1","CG1","B1","10-08-2021", 2000, true));
//        cardList.add(new Card("C2","M2","GG2","B2","10-08-2021", 1000, false));
//        cardList.add(new Card("C3","M3","CG3","B3","10-08-2021", 3000, true));
//        for (Card card : cardList) {
//            cardDAO.insertCard(card);
//        }
//
//        List<Member> memberList = new ArrayList<>();
//        memberList.add(new Member("M1", "Đức Vượng", "20-10-1999"));
//        memberList.add(new Member("M2", "Quang Trung", "15-08-1999"));
//        memberList.add(new Member("M3", "Huy Anh", "29-07-1999"));
//        MemberDAO memberDAO = new MemberDAO(getContext());
//        for (Member member : memberList) {
//            memberDAO.insertMember(member);
//        }
//
//        List<Book> bookList = new ArrayList<>();
//        bookList.add(new Book("B1", "Nhà Giả Kim", 2000, "CG1"));
//        bookList.add(new Book("B2", "Đắc Nhân Tâm", 1000, "CG2"));
//        bookList.add(new Book("B3", "Phi Lý Trí", 3000, "CG3"));
//        BookDAO bookDAOInsert = new BookDAO(getContext());
//        for (Book book : bookList) {
//            bookDAOInsert.insertBook(book);
//        }
//
//        cardDAO = new CardDAO(getContext());
//        cardList = new ArrayList<>();

        memberDAO = new MemberDAO(getContext());
        List<String> member_names = new ArrayList<>();
        List<Object[]> nameAndIdMembers = memberDAO.getAllNameAndIdMembers();
        for (Object[] object : nameAndIdMembers) {
            member_names.add(String.valueOf(object[1]));
        }

        bookDAO = new BookDAO(getContext());
        List<String> book_names = new ArrayList<>();
        List<Object[]> nameAndIdBooks = bookDAO.getAllNameAndIdBook();
        for (Object[] object : nameAndIdBooks) {
            book_names.add(String.valueOf(object[1]));
        }
        cardList.clear();
        cardList = cardDAO.getAllCards();
        cardAdapter = new CardAdapter(getContext(), cardList);
        rcvCards.setAdapter(cardAdapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        rcvCards.setLayoutManager(linearLayoutManager);

        btnAddCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                AlertDialog alertDialog = dialogBuilder.create();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View dialogView = inflater.inflate(R.layout.custom_add_card_dialog, null);
                alertDialog.setView(dialogView);

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                Date currentTime = Calendar.getInstance().getTime();

                TextView tvCardId = (TextView) dialogView.findViewById(R.id.tvCardId);
                Spinner spMemberId = (Spinner) dialogView.findViewById(R.id.spMemberId);
                Spinner spBookId = (Spinner) dialogView.findViewById(R.id.spBookId);
                Button btnSaveCard = (Button) dialogView.findViewById(R.id.btnSaveCard);
                Button btnCloseDialog = (Button) dialogView.findViewById(R.id.btnCloseDialog);
                CheckBox chkReturn = (CheckBox) dialogView.findViewById(R.id.chkReturn);
                TextView tvCardDate = (TextView) dialogView.findViewById(R.id.tvCardDate);
                TextView tvCardPrice = (TextView) dialogView.findViewById(R.id.tvCardPrice);


                tvCardDate.setText(dateFormat.format(currentTime));

                ArrayAdapter<String> member_adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, member_names);
                member_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spMemberId.setAdapter(member_adapter);
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


                ArrayAdapter<String> book_adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, book_names);
                book_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spBookId.setAdapter(book_adapter);
                final String[] idBook = {String.valueOf(nameAndIdBooks.get(spBookId.getSelectedItemPosition())[0])};
                tvCardPrice.setText(bookDAO.getPriceBookById(idBook[0]));
                spBookId.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        idBook[0] = String.valueOf(nameAndIdBooks.get(spBookId.getSelectedItemPosition())[0]);
                        tvCardPrice.setText(bookDAO.getPriceBookById(idBook[0]));
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });

                btnSaveCard.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String id_card = tvCardId.getText().toString().trim();
                        if(id_card.equals("")) {
                            Toast.makeText(getContext(), "Vui lòng điền mã phiếu mượn", Toast.LENGTH_SHORT).show();
                        } else {
                            if(cardDAO.insertCard(new Card(id_card, idMember[0], bookDAO.getCateGoryBookById(idBook[0]), idBook[0], tvCardDate.getText().toString(), Double.parseDouble(tvCardPrice.getText().toString()), chkReturn.isChecked())) > 0) {
                                cardList.clear();
                                cardList = cardDAO.getAllCards();
                                cardAdapter = new CardAdapter(getContext(), cardList);
                                rcvCards.setAdapter(cardAdapter);
                                rcvCards.setLayoutManager(linearLayoutManager);
                                cardAdapter.notifyDataSetChanged();
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
        rcvCards = (RecyclerView) view.findViewById(R.id.rcvList);
        btnAddCard = (FloatingActionButton) view.findViewById(R.id.btnAdd);
    }
}
