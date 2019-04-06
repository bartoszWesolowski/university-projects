package com.earchive.enigmatic.egzamsarchive.utils;

import android.animation.Animator;
import android.app.Activity;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ViewAnimator;

/**
 * Created by Bartek on 2016-04-09.
 */
public class SearchComponentUtil {
    private Activity activity;

    public SearchComponentUtil(Activity activity) {
        this.activity = activity;
    }
    protected <T> T findById(int id) {
        return (T) this.activity.findViewById(id);
    }

    public TextView findTextView(int id) {
        return this.findById(id);
    }

    public Button findButton(int id) {
        return this.findById(id);
    }

    public AutoCompleteTextView findAutoCompleteTextView(int id) {
        return this.findById(id);
    }

    public ListView findListView(int id) {
        return this.findById(id);
    }

    public ViewAnimator findViewAnimator(int id) { return this.findById(id); }

    public EditText findEditText(int id) {
        return this.findById(id);
    }
}
