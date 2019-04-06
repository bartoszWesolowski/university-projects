package com.earchive.enigmatic.egzamsarchive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.earchive.enigmatic.egzamsarchive.R;
import com.earchive.enigmatic.egzamsarchive.database.ExamsDatabase;
import com.earchive.enigmatic.egzamsarchive.model.Exam;
import com.earchive.enigmatic.egzamsarchive.utils.SearchComponentUtil;

import java.util.ArrayList;

public class WelcomePageActivity extends AppCompatActivity {

    final SearchComponentUtil searchComponent = new SearchComponentUtil(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

    }

    public void findExamsButtonClick(View view) {
        Log.i("TAG", "Find exam button clicked");
        Intent i = new Intent(this, FindExamActivity.class);
        startActivity(i);
    }

    public void addExamButtonClick(View view) {
        Log.i("TAG", "Add exam button clicked");
    }

    public void savedExamsButtonClick(View view) {
        Intent i = new Intent(this, SavedExamsListActivity.class);
        startActivity(i);
        Log.i("TAG", "Saved exams button clicked");
    }
}
