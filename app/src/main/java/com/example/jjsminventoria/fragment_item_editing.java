package com.example.jjsminventoria;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.jjsminventoria.Builder.ProductBuilder;
import com.example.jjsminventoria.database.FirebaseConnection;

import model.Products;

public class fragment_item_editing extends Fragment {

    private static final String ARG_PRODUCT = "product";
    private Products product;

    public fragment_item_editing() {}

    public static fragment_item_editing newInstance(Products product) {
        fragment_item_editing fragment = new fragment_item_editing();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            product = (Products) getArguments().getSerializable(ARG_PRODUCT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_item_editing, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        EditText itemTitle = view.findViewById(R.id.itemTitle);
        EditText description = view.findViewById(R.id.descriptionInput);
        EditText id = view.findViewById(R.id.idInput);
        EditText stock = view.findViewById(R.id.stockInput);
        EditText price = view.findViewById(R.id.PriceInput);
        EditText discount = view.findViewById(R.id.discountInput);

        if (product != null) {
            itemTitle.setText(product.getName());
            description.setText(product.getDesc());
            id.setText(product.getId() != null ? product.getId() : "");
            stock.setText(String.valueOf(product.getQty()));
            price.setText("N/A");
            discount.setText("N/A");
        } else {
            id.setEnabled(false);
        }

        TextView historyTab = view.findViewById(R.id.tabHistory);
        historyTab.setOnClickListener(v -> {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment_activity_main_menu_bottom_tabs, new fragment_history())
                    .addToBackStack(null)
                    .commit();
        });

        Button saveButton = view.findViewById(R.id.saveButton);
        saveButton.setOnClickListener(v -> {
            String name = itemTitle.getText().toString().trim(); // ✅ FIXED
            String descriptionText = description.getText().toString().trim();
            String stockText = stock.getText().toString().trim();
            String priceText = price.getText().toString().trim();
            String discountText = discount.getText().toString().trim();

            try {
                int qty = Integer.parseInt(stockText);
                double priceVal = Double.parseDouble(priceText);
                double discountVal = Double.parseDouble(discountText);
                String placeholderImg = "no_image";

                Products builtProduct = new ProductBuilder()
                        .setName(name.isEmpty() ? "Untitled" : name)
                        .setDesc(descriptionText)
                        .setQty(qty)
                        .setWeight(priceVal)
                        .setImg(placeholderImg)
                        .build();

                String category = "Default";
                String productKey = FirebaseConnection.getInstance().getRootDb()
                        .child("Categories")
                        .child(category)
                        .child("Products")
                        .push()
                        .getKey();

                if (productKey != null) {
                    builtProduct.setId(productKey);
                    FirebaseConnection.getInstance().getRootDb()
                            .child("Categories")
                            .child(category)
                            .child("Products")
                            .child(productKey)
                            .setValue(builtProduct)
                            .addOnSuccessListener(task -> {
                                Toast.makeText(getContext(), "Product saved!", Toast.LENGTH_SHORT).show();

                                // 🔁 Navigate to ItemsFragment (pass category if needed)
                                Fragment itemsFragment = ItemsFragment.newInstance(category);
                                getParentFragmentManager()
                                        .beginTransaction()
                                        .replace(R.id.nav_host_fragment_activity_main_menu_bottom_tabs, itemsFragment)
                                        .addToBackStack(null)
                                        .commit();
                            })
                            .addOnFailureListener(e -> {
                                Toast.makeText(getContext(), "Failed: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            });
                }

            } catch (NumberFormatException e) {
                Toast.makeText(getContext(), "Please fill all numeric fields correctly.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
