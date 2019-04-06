package com.earchive.enigmatic.egzamsarchive.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.app.ServiceCompat;

import com.google.gson.Gson;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Simplified exam model
 */
public class Exam implements Parcelable {

    public static final String UNIVERSITY_SHORT_NAME = "universityShortName";
    public static final String UNIVERSITY_FULL_NAME = "universityFullName";
    public static final String FIELD_OF_STUDIES = "fieldOfStudies";
    public static final String SUBJECT = "subject";
    public static final String TEACHER_FIRST_NAME = "teacherFirstName";
    public static final String TEACHER_LAST_NAME = "teacherLastName";
    public static final String YEAR = "year";

    private String id;

    private University university;

    private String fieldOfStudies = "";

    private String subject;

    private Teacher teacher;

    private Integer year;

    private String questions;

    public boolean isSaved = false;

    /**
     * Empty exam.
     */
    public Exam() {
        this(new University("", ""), "", "", new Teacher("", ""), null);
    }

    public Exam(University university, String fieldOfStudies, String subject, Teacher teacher, Integer year) {
        this.university = university;
        this.fieldOfStudies = fieldOfStudies;
        this.subject = subject;
        this.teacher = teacher;
        this.year = year;
    }

    public Exam(University university, String fieldOfStudies, String subject, Teacher teacher, Integer year, String questions) {
        this.subject = subject;
        this.university = university;
        this.teacher = teacher;
        this.fieldOfStudies = fieldOfStudies;
        this.questions = questions;
        this.year = year;
    }

    public static Exam fromJSON(String json) {
        final Gson gson = new Gson();
        return gson.fromJson(json, Exam.class);
    }

    public String getQuestions() {
        return questions;
    }

    public String getSubject() {
        return subject;
    }

    public University getUniversity() {
        return university;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public int getYear() {
        return year;
    }

    public String getFieldOfStudies() {
        return fieldOfStudies;
    }

    public boolean isSaved() {
        return isSaved;
    }

    public void setIsSaved(boolean isSaved) {
        this.isSaved = isSaved;
    }

    public void setUniversity(University university) {
        this.university = university;
    }

    public void setFieldOfStudies(String fieldOfStudies) {
        this.fieldOfStudies = fieldOfStudies;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setQuestions(String questions) {
        this.questions = questions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return this.subject + ", " + this.university + ", " + this.fieldOfStudies + ", " + this.teacher + ": " + this.questions;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        out.writeParcelable(university, flags);
        out.writeParcelable(teacher, flags);
        out.writeString(id);
        out.writeString(fieldOfStudies);
        out.writeString(subject);
        out.writeString(questions);
        out.writeInt(year);
        out.writeByte((byte) (isSaved ? 1 : 0));
    }

    public static final Parcelable.Creator<Exam> CREATOR
            = new Parcelable.Creator<Exam>() {
        public Exam createFromParcel(Parcel in) {
            return new Exam(in);
        }

        public Exam[] newArray(int size) {
            return new Exam[size];
        }
    };

    private Exam(Parcel in) {
        this.university = in.readParcelable(University.class.getClassLoader());
        this.teacher = in.readParcelable(Teacher.class.getClassLoader());
        this.id = in.readString();
        this.fieldOfStudies = in.readString();
        this.subject = in.readString();
        this.questions = in.readString();
        this.year = in.readInt();
        this.isSaved = in.readByte() == 1;
    }

    /**
     * Maps all the propertes of exam. Each key is corresponding to REST API. Not null safe method.
     * @return map with all properties
     */
    public Map<String, String> getParamMap() {
        Map<String, String> params = new HashMap<>();
        params.put(UNIVERSITY_SHORT_NAME, university.getShortName());
        params.put(UNIVERSITY_FULL_NAME, university.getFullName());
        params.put(FIELD_OF_STUDIES, fieldOfStudies);
        params.put(SUBJECT, subject);
        params.put(TEACHER_FIRST_NAME, teacher.getFirstName());
        params.put(TEACHER_LAST_NAME, teacher.getLastName());
        if (year != null) {
            params.put(YEAR, String.valueOf(year));
        }
        return params;

    }
}
