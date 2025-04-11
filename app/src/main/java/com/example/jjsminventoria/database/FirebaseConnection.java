package com.example.jjsminventoria.database;

import android.text.format.DateFormat;

import androidx.annotation.NonNull;

import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.playintegrity.PlayIntegrityAppCheckProviderFactory;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Products;

public class FirebaseConnection {

    private static FirebaseConnection instance;

    private final DatabaseReference rootDb;
    private final DatabaseReference usersDb;
    private final DatabaseReference categoriesDb;
    private final DatabaseReference historyDb;

    private final StorageReference storageRef;
    private final FirebaseAuth auth;
    private final FirebaseAppCheck firebaseAppCheck;

    private FirebaseConnection() {
        rootDb = FirebaseDatabase.getInstance().getReference("Company").child("100");
        usersDb = rootDb.child("Users");
        categoriesDb = rootDb.child("Categories");
        historyDb = FirebaseDatabase.getInstance().getReference("History");

        storageRef = FirebaseStorage.getInstance().getReference();
        auth = FirebaseAuth.getInstance();
        firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(PlayIntegrityAppCheckProviderFactory.getInstance());
    }

    public static FirebaseConnection getInstance() {
        if (instance == null) {
            synchronized (FirebaseConnection.class) {
                if (instance == null) {
                    instance = new FirebaseConnection();
                }
            }
        }
        return instance;
    }

    public DatabaseReference getRootDb() {
        return rootDb;
    }

    public DatabaseReference getUserDb() {
        return usersDb;
    }

    public DatabaseReference getCategoryDb() {
        return categoriesDb;
    }

    public DatabaseReference getHistoryDb() {
        return historyDb;
    }

    public StorageReference getStorageRef() {
        return storageRef;
    }

    public FirebaseAuth getAuth() {
        return auth;
    }

    public void logout() {
        auth.signOut();
    }

    public void logHistory(String actionType, String message) {
        String userId = auth.getCurrentUser() != null ? auth.getCurrentUser().getUid() : "unknown";
        String timestamp = DateFormat.format("yyyy-MM-dd HH:mm:ss", new Date()).toString();
        String historyId = historyDb.push().getKey();

        if (historyId == null) return;

        Map<String, Object> historyMap = new HashMap<>();
        historyMap.put("id", historyId);
        historyMap.put("actionType", actionType);
        historyMap.put("message", message);
        historyMap.put("timestamp", timestamp);

        historyDb.child(userId).child(historyId).setValue(historyMap);
    }

    // ✅ Updated method to handle Firebase type mismatches gracefully
    public void fetchProductsUnderCategory(String categoryName, FetchProductsCallback callback) {
        DatabaseReference productsRef = rootDb
                .child("Categories")
                .child(categoryName)
                .child("Products");

        productsRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Products> productList = new ArrayList<>();
                for (DataSnapshot snap : snapshot.getChildren()) {
                    Products product = new Products();

                    // Use Firebase key as ID
                    product.setId(snap.getKey());

                    Object nameObj = snap.child("name").getValue();
                    if (nameObj instanceof String) product.setName((String) nameObj);

                    Object descObj = snap.child("desc").getValue();
                    if (descObj instanceof String) product.setDesc((String) descObj);

                    Object imgObj = snap.child("img").getValue();
                    if (imgObj instanceof String) product.setImg((String) imgObj);

                    Object qtyObj = snap.child("qty").getValue();
                    if (qtyObj instanceof Long) product.setQty(((Long) qtyObj).intValue());

                    Object weightObj = snap.child("weight").getValue();
                    if (weightObj instanceof Double) {
                        product.setWeight((Double) weightObj);
                    } else if (weightObj instanceof Long) {
                        product.setWeight(((Long) weightObj).doubleValue());
                    }

                    productList.add(product);
                }

                callback.onProductsFetched(productList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                callback.onProductsFetchedFailed(error.toException());
            }
        });
    }

    // ✅ Interface for async callback
    public interface FetchProductsCallback {
        void onProductsFetched(List<Products> products);
        void onProductsFetchedFailed(Exception exception);
    }
}
