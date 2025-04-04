package com.example.librarymanagementsystem.User.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.librarymanagementsystem.Database.BookDatabase;
import com.example.librarymanagementsystem.R;
import com.example.librarymanagementsystem.RecyclerView.Adapter.BookAdapter;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MainDashboard#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MainDashboard extends Fragment {


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MainDashboard() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MainDashboard.
     */
    // TODO: Rename and change types and number of parameters
    public static MainDashboard newInstance(String param1, String param2) {
        MainDashboard fragment = new MainDashboard();
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
        View view = inflater.inflate(R.layout.fragment_main_dashboard, container, false);
        BookDatabase DB_BOOK = new BookDatabase(getContext());
        RecyclerView rv_newbook = view.findViewById(R.id.rv_newbook);

        rv_newbook.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        rv_newbook.setAdapter(new BookAdapter(getContext(), DB_BOOK.READ_NEW_BOOK()));
        return view;
    }
}