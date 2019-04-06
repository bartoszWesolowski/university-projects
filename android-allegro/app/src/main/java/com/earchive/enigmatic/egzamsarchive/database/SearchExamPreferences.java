package com.earchive.enigmatic.egzamsarchive.database;

import android.content.Context;
import android.content.SharedPreferences;

import com.earchive.enigmatic.egzamsarchive.model.Exam;
import com.earchive.enigmatic.egzamsarchive.model.Teacher;
import com.earchive.enigmatic.egzamsarchive.model.University;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by Bartek on 2016-05-22.
 * Class to store searching preferences like universty or field of studies
 */
public class SearchExamPreferences {
    private static final String PREFS_NAME = "search_exam_preferences";
    private static final String KEY_NUM_COLUMNS = "numColumns";

    private SharedPreferences preferences;

    public SearchExamPreferences(Context context) {
        preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    /**
     * Save all preference fields of exam search.
     * @param exam
     */
    public void saveCurrentSearchingPreferences(Exam exam) {
        for (Map.Entry<String, String> entry : exam.getParamMap().entrySet()) {
            preferences.edit().putString(entry.getKey(), entry.getValue()).commit();
        }
    }

    /**
     * Gets exam filled with search exam preferences. Not saved properties are replaced with {@link StringUtils#EMPTY }
     * @return
     */
    public Exam getSearchExamPreferences() {
        Exam result = new Exam();
        result.setFieldOfStudies(preferences.getString(Exam.FIELD_OF_STUDIES, StringUtils.EMPTY));
        result.setUniversity(
                new University(preferences.getString(Exam.UNIVERSITY_SHORT_NAME, StringUtils.EMPTY),
                        preferences.getString(Exam.UNIVERSITY_FULL_NAME, StringUtils.EMPTY)));
        result.setSubject(preferences.getString(Exam.SUBJECT, StringUtils.EMPTY));
        result.setTeacher(
                new Teacher(preferences.getString(Exam.TEACHER_FIRST_NAME, StringUtils.EMPTY),
                        preferences.getString(Exam.TEACHER_LAST_NAME, StringUtils.EMPTY)));
        return result;
    }
}
