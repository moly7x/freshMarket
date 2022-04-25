package com.example.freshmarket.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.freshmarket.R;
import com.example.freshmarket.adapters.HomeAdapter;
import com.example.freshmarket.adapters.PopularAdapters;
import com.example.freshmarket.adapters.RecommendedAdapter;
import com.example.freshmarket.databinding.FragmentHomeBinding;
import com.example.freshmarket.models.HomeCategory;
import com.example.freshmarket.models.PopularModel;
import com.example.freshmarket.models.RecommendedModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    RecyclerView popularRec, homeCatRec,recommendedRec;
    FirebaseFirestore db;

    //Popular items
    List<PopularModel> popularModelList;
    PopularAdapters popularAdapters;

    // Home category
    List<HomeCategory> categoryList;
    HomeAdapter homeAdapter;

    //Recommended
    List<RecommendedModel> recommendedModalList;
    RecommendedAdapter recommendedAdapter;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        db = FirebaseFirestore.getInstance();
        popularRec = root.findViewById(R.id.pop_rec);
        homeCatRec = root.findViewById((R.id.explore_rec));
        recommendedRec = root.findViewById(R.id.recommended_rec);

        //Popular items
        popularRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        popularModelList = new ArrayList<>();
        popularAdapters = new PopularAdapters(getActivity(),popularModelList);
        popularRec.setAdapter(popularAdapters);
        db.collection("PopularProducts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                PopularModel popularModel = document.toObject(PopularModel.class);
                                popularModelList.add(popularModel);
                                popularAdapters.notifyDataSetChanged();

                            }
                        } else {
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        // Home Category

        homeCatRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        categoryList = new ArrayList<>();
        homeAdapter = new HomeAdapter(getActivity(),categoryList);
        homeCatRec.setAdapter(homeAdapter);
        db.collection("HomeCategory")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                HomeCategory homeCategory = document.toObject(HomeCategory.class);
                                categoryList.add(homeCategory);
                                homeAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

// Recommended

        recommendedRec.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recommendedModalList = new ArrayList<>();
        recommendedAdapter = new RecommendedAdapter(getActivity(),recommendedModalList);
        recommendedRec.setAdapter(recommendedAdapter);
        db.collection("Recommended")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                RecommendedModel recommendedModal = document.toObject(RecommendedModel.class);
                                recommendedModalList.add(recommendedModal);
                                recommendedAdapter.notifyDataSetChanged();
                            }
                        } else {
                            Toast.makeText(getActivity(),"Error"+task.getException(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}