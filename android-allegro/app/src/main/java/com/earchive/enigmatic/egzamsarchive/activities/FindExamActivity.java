package com.earchive.enigmatic.egzamsarchive.activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ViewAnimator;

import com.earchive.enigmatic.egzamsarchive.R;
import com.earchive.enigmatic.egzamsarchive.database.SearchExamPreferences;
import com.earchive.enigmatic.egzamsarchive.model.Exam;
import com.earchive.enigmatic.egzamsarchive.model.Teacher;
import com.earchive.enigmatic.egzamsarchive.model.University;
import com.earchive.enigmatic.egzamsarchive.utils.SearchComponentUtil;
import com.earchive.enigmatic.egzamsarchive.utils.URLBuilder;
import com.google.gson.Gson;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;

public class FindExamActivity extends AppCompatActivity {

    private static final int PROGRES_BARR_INDEX = 0;

    private static final int INPUT_PANEL_INDEX = 1;
    private final SearchComponentUtil searchComponent = new SearchComponentUtil(this);

    private SearchExamPreferences searchExamPreferences;

    private AutoCompleteTextView universityShortNameTextView;
    private AutoCompleteTextView universityFullNameTextView;
    private AutoCompleteTextView fieldOfStudiesTextView;
    private AutoCompleteTextView subjectTextView;
    private AutoCompleteTextView teacherLastNameTextView;
    private AutoCompleteTextView teacherFirstNameTextView;
    private EditText yearEditText;

    private ViewAnimator animator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_exam);

        this.setUpViewComponents();
    }


    /**
     * Finds and initialize all the view components. All searching properties are filld with values strored in {@link #searchExamPreferences}
     */
    private void setUpViewComponents() {
        this.searchExamPreferences = new SearchExamPreferences(this);

        this.animator = searchComponent.findViewAnimator(R.id.animator);
        animator.setDisplayedChild(INPUT_PANEL_INDEX);
        universityShortNameTextView = searchComponent.findAutoCompleteTextView(R.id.autocomplete_university_short_name);
        universityFullNameTextView = searchComponent.findAutoCompleteTextView(R.id.autocomplete_university_full_name);
        fieldOfStudiesTextView = searchComponent.findAutoCompleteTextView(R.id.autocomplete_field_of_study);

        subjectTextView = searchComponent.findAutoCompleteTextView(R.id.autocomplete_subject);


        teacherFirstNameTextView = searchComponent.findAutoCompleteTextView(R.id.autocomplete_teacher_first_name);

        teacherLastNameTextView = searchComponent.findAutoCompleteTextView(R.id.autocomplete_teacher_last_name);

        yearEditText = searchComponent.findEditText(R.id.find_exam_year_edit_text);

        this.setUpSearchPreferences(searchExamPreferences.getSearchExamPreferences());
    }

    private void setUpSearchPreferences(Exam preferences) {
        universityShortNameTextView.setText(preferences.getUniversity().getShortName());
        universityFullNameTextView.setText(preferences.getUniversity().getFullName());
        fieldOfStudiesTextView.setText(preferences.getFieldOfStudies());
        subjectTextView.setText(preferences.getSubject());
        teacherFirstNameTextView.setText(preferences.getTeacher().getFirstName());
        teacherLastNameTextView.setText(preferences.getTeacher().getLastName());
    }

    @Override
    protected void onResume() {
        super.onResume();
        animator.setDisplayedChild(INPUT_PANEL_INDEX);
    }

    public void findExamsButtonClick(View view) {
        Exam toFind = getExamToFind();
        if (this.isNetworkAvailable()) {
            DownloadExamsTask findExamsTask = new DownloadExamsTask(this, toFind);
            findExamsTask.execute();
        } else {
            Toast.makeText(this, R.string.no_internet_connection_error_msg, Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Helper method to gather information from input boxes in this view and create a valid exam out of it
     * @return
     */
    @NonNull
    private Exam getExamToFind() {
        final String universityShortName = StringUtils.trimToEmpty(this.universityShortNameTextView.getText().toString());
        final String universityFullName = StringUtils.trimToEmpty(this.universityFullNameTextView.getText().toString());
        final String fieldOfStudies = StringUtils.trimToEmpty(this.fieldOfStudiesTextView.getText().toString());
        final String subject = StringUtils.trimToEmpty(this.subjectTextView.getText().toString());
        final String teacherLastName = StringUtils.trimToEmpty(this.teacherLastNameTextView.getText().toString());
        final String teacherFirstName = StringUtils.trimToEmpty(this.teacherFirstNameTextView.getText().toString());
        String yearString = this.yearEditText.getText().toString();
        final Integer year = StringUtils.isBlank(yearString) ? null : Integer.valueOf(yearString);

        //TODO:WYPEŁNIĆ DOKŁADNIE DANE Z UNIWERKIEM I NAUCZYCIELEM I ROKIEM
        return new Exam(new University(universityShortName, universityFullName), fieldOfStudies, subject, new Teacher(teacherFirstName, teacherLastName), year);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.find_exam_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_search_preferences: {
                this.searchExamPreferences.saveCurrentSearchingPreferences(getExamToFind());
                Toast.makeText(this, R.string.saved_search_pref_toas, Toast.LENGTH_SHORT).show();
                return true;
            }
            case R.id.clear_search_preferences: {
                this.setUpSearchPreferences(new Exam());
                return true;
            }
            case R.id.set_search_preferences: {
                this.setUpSearchPreferences(searchExamPreferences.getSearchExamPreferences());
                return true;
            }
            default: {
                return super.onOptionsItemSelected(item);
            }
        }
    }
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    /**
     * Task for downloading exams via rest api.
     */
    class DownloadExamsTask extends AsyncTask<Void, Void, ArrayList<Exam>> {
        private static final String URL_TO_EXAMS_RESOURCES = "http://10.0.2.2:8080/exams";
        private static final int CONNECTION_TIMEOUT = 15000;

        private Context context;
        private Exam toFind;
        private boolean connectionErrorOccured = false;

        public DownloadExamsTask(Context context, Exam toFind) {
            this.context = context;
            this.toFind = toFind;
        }

        @Override
        protected void onPreExecute() {
            animator.setDisplayedChild(PROGRES_BARR_INDEX);
        }

        /**
         * Downloading and parsing the response
         * @param urls
         * @return
         */
        @Override
        protected  ArrayList<Exam> doInBackground(Void... urls) {
            ArrayList<Exam> exams = null;
            HttpURLConnection urlConnection = null;
            BufferedReader bufferedReader = null;
            try {
                URL url = URLBuilder.addUrlQueryParams(new URL(URL_TO_EXAMS_RESOURCES), toFind.getParamMap());
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(CONNECTION_TIMEOUT);
                bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                String response = this.getResponse(bufferedReader);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    JSONArray examsJson = jsonResponse.getJSONArray("exams");
                    exams = parseResponse(examsJson);
                } catch (JSONException e) {
                    Log.e("JSON ERR", "Error occured while parsing response" + response + "\n" + e);
                }
                return exams;
            } catch(SocketTimeoutException te) {
                this.connectionErrorOccured = true;
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                urlConnection.disconnect();
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException e) {
                        Log.e("BUFFER READER ERR", "Can not close buffer reader" + e);
                    }
                }
            }
            return exams;
        }

        private ArrayList<Exam> parseResponse (JSONArray examsJson) throws JSONException {
            final Gson gson = new Gson();
            final ArrayList<Exam> exams = new ArrayList<>();
            for (int i = 0; i < examsJson.length(); i++) {
                JSONObject jsonExam = (JSONObject) examsJson.get(i);
                Exam parsedExam = null;
                try {
                    parsedExam = gson.fromJson(jsonExam.toString(), Exam.class);
                } catch (Exception e) {
                    Log.e("GSON ERR", "Error while parsing json: " + jsonExam.toString(4) + "with msg " + e.getMessage() + e.getStackTrace());
                }
                CollectionUtils.addIgnoreNull(exams, parsedExam);
            }
            return exams;
        }

        //na watki glownym UI - mozna wyswietlac, parametr to wynik funcji doInBackground
        @Override
        protected void onPostExecute(ArrayList<Exam> response) {
            if (this.connectionErrorOccured) {
                Toast.makeText(this.context, R.string.find_exams_connection_timout_error, Toast.LENGTH_LONG).show();
                animator.setDisplayedChild(INPUT_PANEL_INDEX);
                return;
            }
            if (response == null || response.isEmpty()) {
                Toast.makeText(this.context, R.string.cant_find_exams_with_given_params_toast_msg, Toast.LENGTH_SHORT).show();
                animator.setDisplayedChild(INPUT_PANEL_INDEX);
            } else {
                Toast.makeText(this.context, String.format(getString(R.string.found_results_toast_msg_template), response.size()), Toast.LENGTH_SHORT).show();
                Intent i = new Intent(this.context, ExamsListActivity.class);
                i.putParcelableArrayListExtra(ExamsListActivity.EXAM_LIST_PROP_KEY, response);
                startActivity(i);
            }
            // animator.setDisplayedChild(INPUT_PANEL_INDEX);
        }

        /**
         * Reads string from buffer
         * @param bufferedReader
         * @return
         * @throws IOException
         */
        @NonNull
        private String getResponse(BufferedReader bufferedReader) {
            StringBuilder stringBuilder = new StringBuilder();
            String line;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line).append("\n");
                }
            } catch (IOException e) {
                Log.e("RESPONSE PARSING ERR", "Error while parsing response" + e);
                return "";
            }
            return stringBuilder.toString();
        }
    }
}
