package com.example.mini_.trivia;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class GameAdapter extends ArrayAdapter<QuestionItem> {

    ArrayList<QuestionItem> listItems;
    Context currContext;

    public GameAdapter(@NonNull Context context, int resource, @NonNull ArrayList<QuestionItem> objects) {
        super(context, resource, objects);
        this.listItems = objects;
        this.currContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate
                    (R.layout.activity_categories, parent, false);
        }

        TextView question_view = convertView.findViewById(R.id.question_text);

        String category = listItems.get(position).getCategory();

        question_view.setText(category);

        return convertView;
    }
}
