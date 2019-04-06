package com.earchive.enigmatic.egzamsarchive.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.earchive.enigmatic.egzamsarchive.R;
import com.earchive.enigmatic.egzamsarchive.model.Exam;
import com.earchive.enigmatic.egzamsarchive.utils.SearchComponentUtil;

import java.util.ArrayList;

public class ExamsListActivity extends AppCompatActivity {

    public static final String EXAM_LIST_PROP_KEY = "examsList";

    protected SearchComponentUtil search = new SearchComponentUtil(this);

    protected ExamsAdapter examsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams_list);

        ArrayList<Exam> found = this.getExams();

        if (found.isEmpty()) {
            Toast.makeText(ExamsListActivity.this, R.string.no_exams_to_show_msg, Toast.LENGTH_SHORT).show();
        }
        this.examsAdapter = new ExamsAdapter(found, this);

        ListView listView = search.findListView(R.id.exams_list_view);

        listView.setAdapter(examsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Exam e = examsAdapter.getItem(position);
                Log.i("Startujem egzamin:", e.toString());
                showExam(e);
            }
        });
    }

    @NonNull
    protected ArrayList<Exam> getExams() {
        final Intent intent = getIntent();
        return intent.getParcelableArrayListExtra(EXAM_LIST_PROP_KEY);
    }

    private void showExam(Exam exam) {
        Intent i = new Intent(this, ExamDetailsActivity.class);
        i.putExtra(ExamDetailsActivity.EXAM_DETAILS_PROP_KEY, exam);
        startActivity(i);
    }
}
