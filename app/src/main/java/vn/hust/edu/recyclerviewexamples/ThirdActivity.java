package vn.hust.edu.recyclerviewexamples;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import io.bloco.faker.Faker;
import vn.hust.edu.recyclerviewexamples.adapters.EmailItemAdapter;
import vn.hust.edu.recyclerviewexamples.models.EmailItemModel;

public class ThirdActivity extends AppCompatActivity {

    List<EmailItemModel> items;
    List<EmailItemModel> favorite_items;
    boolean favorite = false;
    EmailItemAdapter adapter;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);
        Faker faker = new Faker();
        items = new ArrayList<>();
        for (int i = 0; i < 10; i++)
            items.add(new EmailItemModel(faker.name.name(), faker.lorem.sentence(), faker.lorem.paragraph(), "12:00 PM"));

        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new EmailItemAdapter(items);
        recyclerView.setAdapter(adapter);

        Button btn_favorite = findViewById(R.id.btn_favorite);
        btn_favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!favorite) {
                    favorite = true;
                    favorite_items = new ArrayList<>();
                    for (int i = 0; i < 10; i++) {
                        if (items.get(i).isFavorite())
                            favorite_items.add(items.get(i));
                    }
                    EmailItemAdapter adapter = new EmailItemAdapter(favorite_items);
                    recyclerView.setAdapter(adapter);
                } else {
                    favorite = false;
                    recyclerView.setAdapter(adapter);
                }
            }
        });

        EditText txt_search = findViewById(R.id.txt_search);
        txt_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    adapter = new EmailItemAdapter(items);
                    recyclerView.setAdapter(adapter);
                } else if (s.toString().length() > 2) {
                    search(s.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void search(String text) {
        ArrayList<EmailItemModel> searchList = new ArrayList<>();
//        if (favorite) {
//            for (EmailItemModel item : favorite_items) {
//                if (item.getName().toLowerCase().contains(text.toLowerCase())
//                        || item.getSubject().toLowerCase().contains(text.toLowerCase())
//                        || item.getContent().toLowerCase().contains(text.toLowerCase())) {
//                    searchList.add(item);
//                }
//            }
//        } else
            for (EmailItemModel item : items) {
                if (item.getName().toLowerCase().contains(text.toLowerCase())
                        || item.getSubject().toLowerCase().contains(text.toLowerCase())
                        || item.getContent().toLowerCase().contains(text.toLowerCase())) {
                    searchList.add(item);
                }
            }
        adapter = new EmailItemAdapter(searchList);
        recyclerView.setAdapter(adapter);
    }
}
