package com.earchive.enigmatic.egzamsarchive.activities;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.earchive.enigmatic.egzamsarchive.R;
import com.earchive.enigmatic.egzamsarchive.database.ExamsDatabase;
import com.earchive.enigmatic.egzamsarchive.model.Exam;

import java.util.ArrayList;

public class SavedExamsListActivity extends ExamsListActivity {

    final ExamsDatabase database = new ExamsDatabase(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @NonNull
    @Override
    protected ArrayList<Exam> getExams() {
        return database.getExams();
    }
}
