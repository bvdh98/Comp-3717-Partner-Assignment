package com.example.harrison_wark;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class ResultsAdapter extends ArrayAdapter<Parser> {
    Context _context;
    public ResultsAdapter(Context context, ArrayList<Parser> results) {
        super(context, 0, results);
        _context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Activity activity = (Activity) _context;
        // Get the data item for this position
        Parser result = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_row_layout, parent, false);
        }

        // Lookup view for data population
        TextView tvFullName = convertView.findViewById(R.id.fullName);
        TextView tvOccupation = convertView.findViewById(R.id.occupation);
        TextView tvGender = convertView.findViewById(R.id.gender);

        // Populate the data into the template view using the data object
        tvFullName.setText(String.format("%s %s", result.getFirstName(), result.getLastName()));
        tvOccupation.setText(result.getOccupation());
        tvGender.setText(result.getGender());

        ImageView imgOnePhoto = convertView.findViewById(R.id.thumbImage);
        if (result.getPictureUrl() != null) {
            new ImageDownloaderTask(imgOnePhoto).execute(result.getPictureUrl());
        }

        // Return the completed view to render on screen
        return convertView;
    }
}
