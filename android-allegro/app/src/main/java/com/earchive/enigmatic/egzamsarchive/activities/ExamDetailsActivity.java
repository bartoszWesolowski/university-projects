package com.earchive.enigmatic.egzamsarchive.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.earchive.enigmatic.egzamsarchive.R;
import com.earchive.enigmatic.egzamsarchive.database.ExamsDatabase;
import com.earchive.enigmatic.egzamsarchive.model.Exam;
import com.earchive.enigmatic.egzamsarchive.model.Teacher;
import com.earchive.enigmatic.egzamsarchive.model.University;
import com.earchive.enigmatic.egzamsarchive.utils.SearchComponentUtil;

import org.apache.commons.lang3.StringUtils;

public class ExamDetailsActivity extends AppCompatActivity {

    public static final String EXAM_DETAILS_PROP_KEY = "com.earchive.enigmatic.egzamsarchive.activities.egamxDetails";

    private SearchComponentUtil searchComponent = new SearchComponentUtil(this);

    private ExamsDatabase database = new ExamsDatabase(this);

    private Exam exam;
    private TextView universityTextView;
    private TextView fieldOfStudiesTextView;
    private TextView subjectTextView;
    private TextView teacherTextView;
    private TextView yearTextView;
    private TextView questionsTextView;

    private Button saveExamButton;
    private Button deleteExamButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_details);

        Intent i = getIntent();
        this.exam = (Exam) i.getExtras().getParcelable(EXAM_DETAILS_PROP_KEY);
        //exam has to be set !
        this.setUpComponents();
        displayExamDetails(this.exam);

    }

    /**
     * Save exam button onClick action
     * @param view
     */
    public void saveExam(View view) {
        Log.i("Saving:", this.exam.toString());
        final boolean saved = database.saveExam(exam);
        if (saved) {
            Toast.makeText(this, R.string.exam_details_exam_saved, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, R.string.cant_save_exam_toas_msg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Used to delete exam, if is saved.
     * @param view
     */
    public void deleteExam(View view) {
        Log.i("Szhould delete exam:", this.exam.toString());
    }

    private void setUpComponents() {
        this.universityTextView = searchComponent.findTextView(R.id.exam_details_university);
        this.fieldOfStudiesTextView = searchComponent.findTextView(R.id.exam_details_field_of_studies);
        this.subjectTextView = searchComponent.findTextView(R.id.exam_details_subject);
        this.teacherTextView = searchComponent.findTextView(R.id.exam_details_teacher);
        this.yearTextView = searchComponent.findTextView(R.id.exam_details_year);
        this.questionsTextView = searchComponent.findTextView(R.id.exam_details_questions);

        this.saveExamButton = searchComponent.findButton(R.id.exam_details_save_button);
        this.deleteExamButton = searchComponent.findButton(R.id.exam_details_delete_button);
        if(this.exam.isSaved()) {
            this.saveExamButton.setVisibility(View.GONE);
            this.deleteExamButton.setVisibility(View.VISIBLE);
        } else {
            this.saveExamButton.setVisibility(View.VISIBLE);
            this.deleteExamButton.setVisibility(View.GONE);
        }
    }

    private void displayExamDetails(Exam e) {
        final University university = e.getUniversity();
        this.universityTextView.setText(university.getShortName());
        if (StringUtils.isNotBlank(university.getFullName())) {
            universityTextView.append(" (" + university.getFullName() + ")");
        }
        this.fieldOfStudiesTextView.setText(e.getFieldOfStudies());
        this.subjectTextView.setText(e.getSubject());
        final Teacher teacher = e.getTeacher();
        this.teacherTextView.setText(teacher.getFirstName() + " " + teacher.getLastName());
        this.yearTextView.setText(String.valueOf(e.getYear()));
        this.questionsTextView.setText(e.getQuestions());
    }

}
