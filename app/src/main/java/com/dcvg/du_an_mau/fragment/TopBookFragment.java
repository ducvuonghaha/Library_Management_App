package com.dcvg.du_an_mau.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dcvg.du_an_mau.R;
import com.dcvg.du_an_mau.adapter.TopBookAdapter;
import com.dcvg.du_an_mau.dao.CardDAO;
import com.dcvg.du_an_mau.model.Book;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class TopBookFragment extends Fragment {

    private RecyclerView rcvTop10List;
    private CardDAO cardDAO;
    private List<Book> bookList;
    private TopBookAdapter topBookAdapter;

    @Nullable
    @org.jetbrains.annotations.Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable @org.jetbrains.annotations.Nullable ViewGroup container, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_top10book, container, false);
        initView(view);
        cardDAO = new CardDAO(getContext());
        bookList = new ArrayList<>();
        bookList = cardDAO.getTopBook();
        System.out.println(bookList.get(0).getNumberOfBorrow());
        topBookAdapter = new TopBookAdapter(getContext(), bookList);
        rcvTop10List.setAdapter(topBookAdapter);
        rcvTop10List.setLayoutManager(new LinearLayoutManager(getContext()));
        return view;
    }

    private void initView(View view) {
        rcvTop10List = (RecyclerView) view.findViewById(R.id.rcvTop10List);
    }
}
