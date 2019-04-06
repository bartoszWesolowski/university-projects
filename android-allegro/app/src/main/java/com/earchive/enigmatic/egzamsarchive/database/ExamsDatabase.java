package com.earchive.enigmatic.egzamsarchive.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.earchive.enigmatic.egzamsarchive.model.Exam;
import com.earchive.enigmatic.egzamsarchive.model.Teacher;
import com.earchive.enigmatic.egzamsarchive.model.University;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

/**
 * Created by Bartek on 2016-04-29.
 */
public class ExamsDatabase extends SQLiteOpenHelper {

    //TODO:Poprawić bazę danych
    private static final String DB_NAME = "examsDB";
    public static final String EXAMS_TABLE_NAME = "exams";
    public static final String EXAM_ID = "id";
    public static final String UNIVERSITY_SHORT_NAME_COL_NAME = "universityShortName";
    public static final String UNIVERSITY_FULL_NAME_COL_NAME = "universityFullName";
    public static final String FIELD_OD_STUDIES_COL_NAME = "fieldOfStudies";
    public static final String SUBJECT_COL_NAME = "subject";
    public static final String TEACHER_FIRST_NAME_COL_NAME = "teacherFirstName";
    public static final String TEACHER_LAST_NAME_COL_NAME = "teacherLastName";
    public static final String YEAR_COL_NAME = "year";
    public static final String QUESTIONS_COL_NAME = "questions";

    private static final String TABLE_CREATE =
            String.format("CREATE TABLE %s ( " +
                            EXAM_ID +" TEXT, " +
                            UNIVERSITY_SHORT_NAME_COL_NAME + " TEXT, " +
                            UNIVERSITY_FULL_NAME_COL_NAME + " TEXT, " +
                            FIELD_OD_STUDIES_COL_NAME + " TEXT, " +
                            SUBJECT_COL_NAME + " TEXT, " +
                            TEACHER_FIRST_NAME_COL_NAME + " TEXT, " +
                            TEACHER_LAST_NAME_COL_NAME + " TEXT, " +
                            QUESTIONS_COL_NAME + " TEXT," +
                            YEAR_COL_NAME + " INTEGER);",
                    EXAMS_TABLE_NAME);

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + EXAMS_TABLE_NAME;

    private Context context;

    public ExamsDatabase(Context context) {
        super(context, DB_NAME, null, 20);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        db.execSQL(TABLE_CREATE);
    }

    public boolean saveExam(Exam exam) {
        if (!this.isAlreadySaved(exam)) {
            SQLiteDatabase db = this.getWritableDatabase();

            ContentValues cv = new ContentValues();
            cv.put(EXAM_ID, exam.getId());
            cv.put(UNIVERSITY_SHORT_NAME_COL_NAME, exam.getUniversity().getShortName());
            cv.put(UNIVERSITY_FULL_NAME_COL_NAME, exam.getUniversity().getFullName());
            cv.put(FIELD_OD_STUDIES_COL_NAME, exam.getFieldOfStudies());
            cv.put(SUBJECT_COL_NAME, exam.getSubject());
            cv.put(TEACHER_FIRST_NAME_COL_NAME, exam.getTeacher().getFirstName());
            cv.put(TEACHER_LAST_NAME_COL_NAME, exam.getTeacher().getLastName());
            cv.put(YEAR_COL_NAME, exam.getYear());
            cv.put(QUESTIONS_COL_NAME, exam.getQuestions());

            db.insertWithOnConflict(EXAMS_TABLE_NAME, null, cv, SQLiteDatabase.CONFLICT_REPLACE);
            db.close();
            return true;
        }
        return false;
    }

    /**
     * Check if there is already an exam like this saved in db.
     * @param exam
     * @return
     */
    private boolean isAlreadySaved(Exam exam) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(EXAMS_TABLE_NAME, new String[]{EXAM_ID}, null, null, null, null, null);
        long number = DatabaseUtils.queryNumEntries(db, EXAMS_TABLE_NAME, null, null);
        for (int i = 0; i < number; i++) {
            cursor.moveToPosition(i);
            String id = cursor.getString(cursor.getColumnIndex(EXAM_ID));
            if (StringUtils.equals(exam.getId(), id)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Exam> getExams() {
        SQLiteDatabase db = this.getReadableDatabase();
        ArrayList<Exam> result = new ArrayList<>();

        long number = DatabaseUtils.queryNumEntries(db, EXAMS_TABLE_NAME, null, null);
        Cursor cursor = db.query(EXAMS_TABLE_NAME, null, null, null, null, null, null);

        for (int i = 0; i < number; i++) {
            cursor.moveToPosition(i);
            final String id = cursor.getString(cursor.getColumnIndex(EXAM_ID));
            final String universityShortName = cursor.getString(cursor.getColumnIndex(UNIVERSITY_SHORT_NAME_COL_NAME));
            final String universityFullName = cursor.getString(cursor.getColumnIndex(UNIVERSITY_FULL_NAME_COL_NAME));
            final String fieldOfStudies = cursor.getString(cursor.getColumnIndex(FIELD_OD_STUDIES_COL_NAME));
            final String subject = cursor.getString(cursor.getColumnIndex(SUBJECT_COL_NAME));
            final String teacherFirstName = cursor.getString(cursor.getColumnIndex(TEACHER_FIRST_NAME_COL_NAME));
            final String teacherLastName = cursor.getString(cursor.getColumnIndex(TEACHER_LAST_NAME_COL_NAME));
            final int year = cursor.getInt(cursor.getColumnIndex(YEAR_COL_NAME));
            final String questions = cursor.getString(cursor.getColumnIndex(QUESTIONS_COL_NAME));
            Exam exam = new Exam(new University(universityShortName, universityFullName), fieldOfStudies, subject, new Teacher(teacherFirstName, teacherLastName), year, questions);
            exam.setIsSaved(true);
            exam.setId(id);
            result.add(exam);
            Log.i("Getting exam from db: ", exam.toString());

        }
        cursor.close();
        db.close();

        return result;
    }

    public int getExamNumber() {
        SQLiteDatabase db = this.getReadableDatabase();
        long number = DatabaseUtils.queryNumEntries(db, EXAMS_TABLE_NAME, null, null);
        db.close();
        return (int)number;
    }

}
