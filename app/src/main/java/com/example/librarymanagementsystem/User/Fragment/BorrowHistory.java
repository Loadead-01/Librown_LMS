package com.example.librarymanagementsystem.User.Fragment;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.librarymanagementsystem.Database.BookDatabase;
import com.example.librarymanagementsystem.R;
import com.example.librarymanagementsystem.RecyclerView.Adapter.BorrowAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BorrowHistory#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BorrowHistory extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public BorrowHistory() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BorrowHistory.
     */
    // TODO: Rename and change types and number of parameters
    public static BorrowHistory newInstance(String param1, String param2) {
        BorrowHistory fragment = new BorrowHistory();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_borrow_history, container, false);
        Context context = getContext();
        RecyclerView rv_history = view.findViewById(R.id.rv_history);

        BookDatabase DB_BOOK = new BookDatabase(context);
        int user_id = context.getSharedPreferences("USER_SESSION", Context.MODE_PRIVATE).getInt("USER_ID", 1);
        rv_history.setAdapter(new BorrowAdapter(context, DB_BOOK.BORROW_HISTORY(user_id)));
        rv_history.setLayoutManager(new LinearLayoutManager(context));

        return view;
    }
}