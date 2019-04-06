package com.earchive.enigmatic.egzamsarchive.activities;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.earchive.enigmatic.egzamsarchive.R;
import com.earchive.enigmatic.egzamsarchive.model.Exam;

import java.util.List;

/**
 * Created by Bartek on 2016-04-22.
 */
public class ExamsAdapter extends BaseAdapter {
    private List<Exam> exams;

    private Context context;

    public ExamsAdapter(List<Exam> exams, Context context) {
        this.exams = exams;
        this.context = context;
    }

    @Override
    public int getCount() {
        return exams.size();
    }

    @Override
    public Exam getItem(int position) {
        return exams.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View examView;

        if (convertView == null) {
            examView = LayoutInflater.from(context).inflate(R.layout.exam_row, parent, false);
        } else {
            examView = convertView;
        }

        bindExamToView(getItem(position), examView);

        return examView;
    }

    private void bindExamToView(Exam e, View view) {
        TextView subjectTextView = (TextView) view.findViewById(R.id.exam_row_subject_text_view);
        subjectTextView.setText(e.getSubject());

        TextView fieldTextView = (TextView) view.findViewById(R.id.exam_row_field_of_studies_text_view);
        fieldTextView.setText(e.getFieldOfStudies());

        TextView teacherTextView = (TextView) view.findViewById(R.id.exam_row_teacher_text_view);
        teacherTextView.setText(e.getTeacher().getLastName());

        TextView universityTextView = (TextView) view.findViewById(R.id.exam_row_university_text_view);
        universityTextView.setText(e.getUniversity().getShortName());

        TextView yearTextView = (TextView) view.findViewById(R.id.exam_row_year_text_view);
        yearTextView.setText(String.valueOf(e.getYear()));
    }
}
