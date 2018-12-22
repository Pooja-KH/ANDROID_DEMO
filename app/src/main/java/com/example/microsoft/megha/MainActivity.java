package com.example.microsoft.megha;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {

    TextView textView_Nameresume,textView_Addressresume,textView_Ageresume,textView_Emailresume,textView_Bcaresume,textView_Hscresume,
            textView_Sscresume,textView_CourseNameresume,textView_Hobbiesresume;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resume);

        textView_Nameresume=findViewById(R.id.textView_Nameresume);
        textView_Addressresume=findViewById(R.id.textView_Addressresume);
        textView_Ageresume=findViewById(R.id.textView_Ageresume);
        textView_Emailresume=findViewById(R.id.textView_Emailresume);
        textView_Bcaresume=findViewById(R.id.textView_B_C_Aresume);
        textView_Hscresume=findViewById(R.id.textView_H_S_Cresume);
        textView_Sscresume=findViewById(R.id.textView_S_S_Cresume);
        textView_CourseNameresume=findViewById(R.id.textView_coursenameresume);
        textView_Hobbiesresume=findViewById(R.id.textView_Hobbiesresume);
        String jsonString= null;
        try {
            jsonString = getJSONFromAsset();
        } catch (IOException e) {
            e.printStackTrace();
        }
        JSONObject resumeJSON= null;
        try {
            resumeJSON = new JSONObject(jsonString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        SetDataToWidget(resumeJSON);
    }

    private void SetDataToWidget(JSONObject resumeJSON)
    {
        try {
            textView_Nameresume.setText(resumeJSON.getString("name"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String getJSONFromAsset() throws IOException {
        InputStream is=getAssets().open("resume.json");
        int Size=is.available();
        byte[] buffer=new byte[Size];
        is.read(buffer);
        is.close();
        return new String(buffer,"UTF-8");

    }

}
