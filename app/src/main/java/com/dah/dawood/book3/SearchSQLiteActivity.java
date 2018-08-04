package com.dah.dawood.book3;

/**
 * Created by dawood on 15/02/2018.
 */

import android.Manifest;
import android.app.DownloadManager;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.Selection;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.URLUtil;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
 public class SearchSQLiteActivity extends AppCompatActivity implements View.OnTouchListener ,Handler.Callback{

    ListView listView;
     private WebView webbrowser;
     private static final int CLICK_ON_WEBVIEW = 1;
     private static final int CLICK_ON_URL = 2;
     myWebClient m=new myWebClient();
     public  int ACTIVITYB_REQUEST = 100;
     String action="";
     String s="";

     private final Handler handler = new Handler(this);

     ArrayList<Student> StudentList = new ArrayList<Student>();
    ListAdapter listAdapter;
    SQLiteHelper sqLiteHelper;
    EditText editText;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    int score = 0;
    String name, phoneNumber, query;
    List<String> zoom = new ArrayList<>();
    String path="";
    String currentact="";
    String result="";
    boolean isscore=false;
    public void write(String data,File file)
    {
        // Get the directory for the user's public pictures directory.
        final File path =
                Environment.getExternalStoragePublicDirectory
                        (
                                //Environment.DIRECTORY_PICTURES
                                   "//sdcard//Download//"
                        );

        // Make sure the path directory exists.
        if(!path.exists())
        {
            // Make it, if it doesn't exit
            path.mkdirs();
        }



        // Save your stream, don't forget to flush() it before closing it.

        try
        {
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);
            OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
            myOutWriter.append(data);

            myOutWriter.close();

            fOut.flush();
            fOut.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public String read(File file)
    {
        StringBuilder total = new StringBuilder();

        // Get the directory for the user's public pictures directory.
        final File path =
                Environment.getExternalStoragePublicDirectory
                        (
                                //Environment.DIRECTORY_PICTURES
                                "//sdcard//Download//"
                        );

        // Make sure the path directory exists.
        if(!path.exists())
        {
            // Make it, if it doesn't exit
            path.mkdirs();
        }


        try
        {
            file.createNewFile();
            FileInputStream fin = new FileInputStream(file);
            InputStreamReader myOutreader = new InputStreamReader(fin);
            BufferedReader r = new BufferedReader(new InputStreamReader(fin));
            String line;
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
            myOutreader.close();


            fin.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
return total.toString();


    }
    public String readwithimport(File file)
    {
        StringBuilder total = new StringBuilder();

        // Get the directory for the user's public pictures directory.
        final File path =
                Environment.getExternalStoragePublicDirectory
                        (
                                //Environment.DIRECTORY_PICTURES
                                "//sdcard//Download//"
                        );

        // Make sure the path directory exists.
        if(!path.exists())
        {
            // Make it, if it doesn't exit
            path.mkdirs();
        }


        try
        {
            file.createNewFile();
            FileInputStream fin = new FileInputStream(file);
            InputStreamReader myOutreader = new InputStreamReader(fin);
            BufferedReader r = new BufferedReader(new InputStreamReader(fin));
            String line;
            while ((line = r.readLine()) != null) {
                InsertDataIntoSQLiteDatabasewothupdate(Integer.toString(getProfilesCount()|+1),line.split(":")[0],line.split(":")[1],line.split(":")[2]);
                total.append(line).append('\n');
            }
            myOutreader.close();


            fin.close();
        }
        catch (IOException e)
        {
            Log.e("Exception", "File write failed: " + e.toString());
        }
        return total.toString();


    }
     public String readwithimport2(String fpath)
     {
         StringBuilder total = new StringBuilder();

         // Get the directory for the user's public pictures directory.
         final File path= new File(fpath);

         // Make sure the path directory exists.
         if(!path.exists())
         {
             // Make it, if it doesn't exit
             path.mkdirs();
         }


         try
         {
             path.createNewFile();
             FileInputStream fin = new FileInputStream(path);
             InputStreamReader myOutreader = new InputStreamReader(fin);
             BufferedReader r = new BufferedReader(new InputStreamReader(fin));
             String line;
             while ((line = r.readLine()) != null) {
                  total.append(line).append('\n');
             }
             myOutreader.close();


             fin.close();
         }
         catch (IOException e)
         {
             Log.e("Exception", "File write failed: " + e.toString());
         }
         return total.toString();


     }
    public void write(int id,String name,String text,String Score)
    {
       try{
        if (Score!="0") {


           // InsertDataIntoSQLiteDatabasewothupdate(name, text, Score);
            InsertDataIntoSQLiteDatabase(Integer.toString(getProfilesCount()|+1),name,text,Score);
            onResume();


        } else
            InsertDataIntoSQLiteDatabasewothupdate(Integer.toString(getProfilesCount()|+1),name, text, "0");

    } catch (Exception ex) {
        Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();


        editText.setText(ex.getMessage());
    }
    }
     boolean isAvailable(String url){
        try {
            Runtime runtime = Runtime.getRuntime();
            Process proc = runtime.exec("ping " + url); //<- Try ping -c 1 www.serverURL.com
            int mPingResult = proc.waitFor();
            if (mPingResult == 0) {
                return true;
            } else {
                return false;
            }
        }
        catch (Exception ex)
        {

        }
        return false;
     }
    @Override

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {

            setContentView(R.layout.activity_search_sqlite);

            listView = (ListView) findViewById(R.id.listView1);
            webbrowser=findViewById(R.id.web14);
            webbrowser.getSettings().setBuiltInZoomControls(true);

            editText = (EditText) findViewById(R.id.edittext1);

            listView.setTextFilterEnabled(true);
            listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);

            sqLiteHelper = new SQLiteHelper(this);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    // Getting Search ListView clicked item.
                    Student ListViewClickData = (Student) parent.getItemAtPosition(position);

                    // printing clicked item on screen using Toast message.
                    if (Integer.parseInt(ListViewClickData.getScore()) != 0)
                        score += Integer.parseInt(ListViewClickData.getScore());
                    else
                        score += 5;


                     if (   ListViewClickData.getNumber() .contains("http"))  {
                        try {
                            WebView webView = (WebView) findViewById(R.id.web14);
                            String url = ListViewClickData.getNumber();
                            if (url.contains("http//"))
                                url = url.replace("http//", "http://");
                            if (url.contains("https//"))
                                url = url.replace("https//","https://");
                           editText.setText(url+":go");
                        } catch (Exception ex) {
                            Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                        DisplayDataInToListView();


                    if (editText.getText().toString().contains(":register"))
                    {
                        try {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse("http://dawoodahstudio.com/game/register.php?username=dahstar2&password=123456"));
                            Toast.makeText(SearchSQLiteActivity.this, "started" + i, Toast.LENGTH_SHORT).show();

                            startActivity(i);
                        } catch (Exception ex) {
                            Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                //    http://dawoodahstudio.com/game/register.php?username=dahstar&password=123456




                    if ( ListViewClickData.getNumber().contains("http")) {
                        try {
                            WebView webView = (WebView) findViewById(R.id.web14);

                            String url = ListViewClickData.getNumber();
                            if (url.contains("http//"))
                                url = url.replace("http//", "http://");
                            if (url.contains("https//"))
                                url = url.replace("https//", "https://");
                            editText.setText(url+":go");
                             webView.setWebViewClient(m );
                            webView.loadUrl(url);
                            webView.getSettings().setUseWideViewPort(true);
                            webView.getSettings().setLoadWithOverviewMode(true);
                            webView.setScrollBarSize(2);
                            webView.getSettings().setJavaScriptEnabled(true);
                        } catch (Exception ex) {
                            Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    if (editText.getText().toString().contains(":image")) {
                        showPhoto(Uri.fromFile(new File(editText.getText().toString().replace(":image", ""))));

                        editText.setText(editText.getText().toString().replace(":image", ""));
                        editText.getText().toString().replace(":image", "");
                    }
                    editText.setText(ListViewClickData.getNumber());
                    if(editText.getText().toString().startsWith("wwww."))
                        editText.setText("https://"+editText.getText().toString()+":go");
                }
            });


            editText.addTextChangedListener(new TextWatcher() {

                public void afterTextChanged(Editable s) {
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }
                public void writeFile2(String text){
            try {
                FileOutputStream fileout = openFileOutput("mytextfile.txt", MODE_PRIVATE);
                OutputStreamWriter outputWriter = new OutputStreamWriter(fileout);
                outputWriter.write(text);
                outputWriter.close();
            }
            catch (Exception ex)
            {

            }
                }
                public String  getLines(TextView view,int i1) {
                    final List<CharSequence> lines = new ArrayList<>();
                    final Layout layout = view.getLayout();

                    if (layout != null) {
                        // Get the number of lines currently in the layout
                        final int lineCount = layout.getLineCount();

                        // Get the text from the layout.
                        final CharSequence text = layout.getText();

                        // Initialize a start index of 0, and iterate for all lines
                        for (int i = 0, startIndex = 0; i < lineCount; i++) {
                            // Get the end index of the current line (use getLineVisibleEnd()
                            // instead if you don't want to include whitespace)
                            final int endIndex = layout.getLineEnd(i);

                            // Add the subSequence between the last start index
                            // and the end index for the current line.
                            lines.add(text.subSequence(startIndex, endIndex));

                            // Update the start index, since the indices are relative
                            // to the full text.
                            startIndex = endIndex;
                        }
                    }
                    return lines.get(i1).toString() ;
                }
                public int getCurrentCursorLine(EditText editText)
                {
                    int selectionStart = Selection.getSelectionStart(editText.getText());
                    Layout layout = editText.getLayout();

                    if (!(selectionStart == -1)) {
                        return layout.getLineForOffset(selectionStart);
                    }

                    return -1;
                }

                private boolean checkIfAlreadyhavePermission() {
                    int result = ContextCompat.checkSelfPermission(SearchSQLiteActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE);
                    if (result == PackageManager.PERMISSION_GRANTED) {
                        return true;
                    } else {
                        return false;
                    }
                }
                public String gethtml(String url)

                {
                   result="";
                     WebView webView = (WebView) findViewById(R.id.web14);
                    webView.getSettings().setJavaScriptEnabled(true);
                    webView.setVisibility(View.VISIBLE);
                    webView.getSettings().setUserAgentString("Desktop");

                    webbrowser.loadUrl(url);
                    webView.getSettings().setUseWideViewPort(true);
                    webView.getSettings().setLoadWithOverviewMode(true);
                    webView.setWebViewClient(m);
                    webView.setScrollBarSize(2);
                    webView.setVisibility(View.VISIBLE);
                    webView.evaluateJavascript(
                            "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                            new ValueCallback<String>() {
                                @Override
                                public void onReceiveValue(String html) {
                                    result= html;
                                     // code here
                                }
                            });
                    return result;
                }

                public void onTextChanged(CharSequence stringVar, int start, int before, int count) {
                   int iw=0;

                   String s1=editText.getText().toString();


                     if (editText.getText().toString().contains("mximize")) {
                        maximize();
                    }
                    if (editText.getText().toString().contains(":register"))
                    {
                         String s=editText.getText().toString();
                        try {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse("http://dawoodahstudio.com/game/register.php?username="+s.split(":")[0]+"&password="+s.split(":")[1]));
                            Toast.makeText(SearchSQLiteActivity.this, "started" + i, Toast.LENGTH_SHORT).show();

                            startActivity(i);
                        } catch (Exception ex) {
                            Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    if (editText.getText().toString().contains(":send"))
                    {
                        String s=editText.getText().toString();
                        try {
                            editText.setText(s.toString().replace(         ":send",""));

                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse("http://dawoodahstudio.com/game/register.php?username="+s.split(":")[0]+"&message="+s.split(":")[1]));
                            Toast.makeText(SearchSQLiteActivity.this, "started" + i, Toast.LENGTH_SHORT).show();

                            startActivity(i);
                        } catch (Exception ex) {
                            Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    if (editText.getText().toString().contains(":login"))
                    {
                        String s=editText.getText().toString();
                        try {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse("http://dawoodahstudio.com/game/login.php?username="+s.split(":")[0]+"&password="+s.split(":")[1]));
                            Toast.makeText(SearchSQLiteActivity.this, "started" + i, Toast.LENGTH_SHORT).show();
                            GetContents d=new GetContents();
                            showmessage( d.execute("http://dawoodahstudio.com/game/login.php?username="+s.split(":")[0]+"&password="+s.split(":")[1],"").get());

                          //  startActivity(i);
                        } catch (Exception ex) {
                            Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    if (editText.getText().toString().contains(":recive"))
                    {
                        String s=editText.getText().toString();
                        editText.setText(s.toString().replace(":recive",""));

                        try {
                            Intent i = new Intent(Intent.ACTION_VIEW);
                            i.setData(Uri.parse("http://dawoodahstudio.com/game/recive.php?username="+s.split(":")[0]));
                            Toast.makeText(SearchSQLiteActivity.this, "started" + i, Toast.LENGTH_SHORT).show();
                            startActivity(i);
                        } catch (Exception ex) {
                            Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }


                    if ((stringVar.toString().contains("="))&&(stringVar.toString().contains(";"))) {
                         int m=0;
                        try {
                            if( stringVar.toString().startsWith("@"))
                            {
                                m=Integer.parseInt(getval(stringVar.toString().split("=")[0]));
                            }
                            stringVar = stringVar.toString().replace(";", "");

                            editText.setText(stringVar.toString().replace(";", ""));
                            editText.setText("" + eval(stringVar.toString().split("=")[1]));
                            if (deleteTitle(stringVar.toString().split("=")[0]) == true) {

                                InsertDataIntoSQLiteDatabase(Integer.toString(getProfilesCount()|+1),stringVar.toString().split("=")[0], "" + eval(stringVar.toString().split("=")[1]), "0");
                                onResume();
                            } else
                                InsertDataIntoSQLiteDatabase(Integer.toString(getProfilesCount()|+1),stringVar.toString().split("=")[0], stringVar.toString().split("=")[1], "0");


                        } catch (Exception ex) {
                            showmessage("error" + ex.getMessage());
                        }
                    }





                        if (editText.getText().toString().contains(":write")) {
                        try {
                            //  sqLiteHelper = new SQLiteHelper(getBaseContext());

                            Toast.makeText(SearchSQLiteActivity.this, "write successful", Toast.LENGTH_SHORT).show();
                            String s = stringVar.toString().replace(":write", "");
                            if(s.contains("http://"))
                                s=s.replace("http://","http//");
                            if(s.contains("https://"))
                                s=s.replace("https://","https//");
                            if (s.split(":").length == 3) {

                                Toast.makeText(SearchSQLiteActivity.this, "score is writed" + s.split(":")[2], Toast.LENGTH_SHORT).show();

                                InsertDataIntoSQLiteDatabasewothupdate(Integer.toString(getProfilesCount()+1),s.split(":")[0], s.split(":")[1], s.split(":")[2]);
                                onResume();


                            } else
                                InsertDataIntoSQLiteDatabasewothupdate(Integer.toString(getProfilesCount()+1),s.split(":")[0], s.split(":")[1], "0");

                        } catch (Exception ex) {
                            Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();


                            editText.setText(ex.getMessage());
                        }
                     }
                     if(stringVar.toString().contains(":fwrite"))
                     {
                         writeToSDFile();
                     }
                    if (stringVar.toString().contains(":newwrite")) {
                        try {
                            //  sqLiteHelper = new SQLiteHelper(getBaseContext());

                            Toast.makeText(SearchSQLiteActivity.this, "write successful", Toast.LENGTH_SHORT).show();
                            String s = stringVar.toString().replace(":newwrite", "");
                            if(s.contains("http://"))
                                s=s.replace("http://","http//");
                            if(s.contains("https://"))
                                s=s.replace("https://","https//");
                            if (s.split(":").length == 3) {

                                Toast.makeText(SearchSQLiteActivity.this, "score is writed" + s.split(":")[2], Toast.LENGTH_SHORT).show();

                                InsertDataIntoSQLiteDatabase(  Integer.toString(getProfilesCount()+1),s.split(":")[0], s.split(":")[1], s.split(":")[2]);
                                onResume();


                            } else {
                                InsertDataIntoSQLiteDatabase(Integer.toString(getProfilesCount()  +1), s.split(":")[0], s.split(":")[1], "0");
                                onResume();
                            }

                        } catch (Exception ex) {
                            Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();


                            editText.setText(ex.getMessage());
                        }
                    }
                    if(stringVar.toString().endsWith(":html"))
                    {
                        String url=stringVar.toString().replace(":html","");
                        WebView webView = (WebView) findViewById(R.id.web14);
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.setVisibility(View.VISIBLE);
                        webView.getSettings().setUserAgentString("Desktop");

                        webbrowser.loadUrl(url);
                        webView.getSettings().setUseWideViewPort(true);
                        webView.getSettings().setLoadWithOverviewMode(true);
                        webView.setWebViewClient(m);
                        webView.setScrollBarSize(2);
                        webView.setVisibility(View.VISIBLE);
                        webView.evaluateJavascript(
                                "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                                new ValueCallback<String>() {
                                    @Override
                                    public void onReceiveValue(String html) {
                                       editText.setText( html);
                                        // code here
                                    }
                                });
                    }
                    if ( stringVar.toString().endsWith(":dic"))
                        webbrowser.setVisibility(View.INVISIBLE);

                        if ( stringVar.toString().endsWith(":go"))
                    {
                        String url = stringVar.toString().replace(":go","");

                        WebView webView = (WebView) findViewById(R.id.web14);

                        webView.setWebViewClient(new myWebClient() );
                        if(!url.contains("www"))

                            url="www."+url;
                        if(!url.contains("http"))

                            url="https://"+url;
                        webView.loadUrl(url);


                        //webView.getSettings().setUseWideViewPort(true);
                      //  webView.getSettings().setLoadWithOverviewMode(true);

                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.setVisibility(View.VISIBLE);
                    }


                    if (stringVar.toString().contains(":random"))
                        Displayrandom();
                    if(stringVar.toString().endsWith(":search"))

                    {

                        String s=stringVar.toString().replace(":search","");
                        currentact="https://www.google.com/search?q="+s;
                        Toast.makeText(getBaseContext(), "webpageloaded", Toast.LENGTH_SHORT).show();
                        WebView webView = (WebView) findViewById(R.id.web14);
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.setVisibility(View.VISIBLE);
                        webView.getSettings().setUserAgentString("Desktop");

                        webbrowser.loadUrl("https://www.google.com/search?q="+s);
                        webView.getSettings().setUseWideViewPort(true);
                        webView.getSettings().setLoadWithOverviewMode(true);
                        webView.setWebViewClient(m);
                        webView.setScrollBarSize(2);
                        webView.setVisibility(View.VISIBLE);
                    }
                    if(stringVar.toString().endsWith(":back"))

                    {

                        String s=stringVar.toString().replace(":search","");
                        currentact="back";
                        Toast.makeText(getBaseContext(), "webpageloaded", Toast.LENGTH_SHORT).show();
                        WebView webView = (WebView) findViewById(R.id.web14);
                        webView.getSettings().setJavaScriptEnabled(true);
                        webView.setVisibility(View.VISIBLE);
                        webView.getSettings().setUserAgentString("Desktop");

                        webView.getSettings().setUseWideViewPort(true);
                        webView.getSettings().setLoadWithOverviewMode(true);
                        webView.setWebViewClient(m);

                    }
                    if ( stringVar.toString().contains(":link")) {
                        webbrowser.setVisibility(View.VISIBLE);
                        WebView webView = (WebView) findViewById(R.id.web14);


                        editText.setText(m.urls);
                    }
                    if (stringVar.toString().contains(":file")) {
                        SharedPreferences.Editor editor = getSharedPreferences("book", MODE_PRIVATE).edit();



                        editText.setText(getSharedPreferences("book",MODE_PRIVATE).getString("file",null)+"");
                       //  generateNoteOnSD2(getBaseContext(),editText.getText().toString().replace("/","//"),"welcome");
                    }
                    if (stringVar.toString().contains(":select file")) {
                        iw++;
                        //String url = "https://i.kinja-img.com/gawker-media/image/upload/s--DGTAjMVu--/c_scale,f_auto,fl_progressive,q_80,w_800/pr4yhdthhvp6vic1gsok.jpg";
                        //  currentact=url;
                        // editText.setText(url+":go");
                        Intent intent = new Intent(SearchSQLiteActivity.this, Fileexplor.class);
                         startActivityForResult(intent, ACTIVITYB_REQUEST);
                    }
                    if (stringVar.toString().contains(":run")) {
                        CodeExcute code=new CodeExcute();
                        code.execute();
                    }

                    if (stringVar.toString().contains(":export")) {
                         iw++;
                         action=":export";
                        //String url = "https://i.kinja-img.com/gawker-media/image/upload/s--DGTAjMVu--/c_scale,f_auto,fl_progressive,q_80,w_800/pr4yhdthhvp6vic1gsok.jpg";
                     //  currentact=url;
                       // editText.setText(url+":go");
                        exportalldata();


                    }
                    if(stringVar.toString().contains(":usescore"))
                        isscore=true;
                    if(stringVar.toString().contains(":notusescore"))
                        isscore=false;
                    if (stringVar.toString().contains(":read file")) {

                        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                            {
                            try {
                                iw++;
                                action = ":read file";

                                Intent intent = new Intent(SearchSQLiteActivity.this, Fileexplor.class);
                                startActivityForResult(intent, ACTIVITYB_REQUEST);
                                // String url = "https://i.kinja-img.com/gawker-media/image/upload/s--DGTAjMVu--/c_scale,f_auto,fl_progressive,q_80,w_800/pr4yhdthhvp6vic1gsok.jpg";
                                //  currentact=url;
                                // editText.setText(url+":go");
                            } catch (Exception ex) {
                                editText.setText(ex.getMessage());
                            }

                        }
                        else
                            editText.setText("for read file we need your  permission on access external storage");
                        ActivityCompat.requestPermissions(SearchSQLiteActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, ACTIVITYB_REQUEST);


                    }
                    if (stringVar.toString().contains(":save file")) {


                        exportalldata();

                    }
                    if (stringVar.toString().contains(":import")) {
                        try {
                            File dir = new File("//sdcard//Download//");
                            File file = new File(dir, "message.txt");
                            path=file.getPath();
                           editText.setText( readwithimport(file));
                            File path = getBaseContext().getFilesDir();
                            File file2 = new File(path, "my-file-name.txt");
                            file2.createNewFile();
                        }
                        catch (Exception ex)
                        {
                            Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    }
                    if (stringVar.toString().contains(":newimage")) {
                        Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
                        intent2.setType("*/*");
                        startActivityForResult(intent2, 3);

                        String s = stringVar.toString().replace(":newimage", "");
                        // Uri.fromFile(new File("/sdcard/sample.jpg"))
                    }
                    if (editText.getText().toString().contains(":image")) {
                        showPhoto(Uri.fromFile(new File(editText.getText().toString().replace(":image", ""))));

                        editText.setText(editText.getText().toString().replace(":image", ""));
                        editText.getText().toString().replace(":image", "");
                    }
                    if (stringVar.toString().contains(":delete")) {
                        try {

                            if (deleteTitle(stringVar.toString().split(":")[0]) == true)
                                Toast.makeText(SearchSQLiteActivity.this, "delete is successful", Toast.LENGTH_SHORT).show();

                        } catch (Exception ex) {
                            Toast.makeText(SearchSQLiteActivity.this, "delete is  error is " + ex.getMessage(), Toast.LENGTH_SHORT).show();

                        }


                    }
                    if (stringVar.toString().contains("go next level"))
                        DisplayDataInToListView();

                    if (stringVar.toString().contains(":sendemail")) {
                        Intent intent2 = new Intent(Intent.ACTION_GET_CONTENT);
                        intent2.setType("image/*");
                        startActivityForResult(intent2, 3);

                        String s = stringVar.toString().replace(":send", "");
                        sendemail(s.split(":")[0], s.split(":")[1]);
                        // Uri.fromFile(new File("/sdcard/sample.jpg"))
                    }

                    listAdapter.getFilter().filter(stringVar);

                }
            });

        } catch (Exception ex) {
            Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

     public void generateNoteOnSD1(Context context, String sFileName, String sBody) {
         try {
             File root = new File(Environment.getExternalStorageDirectory(), "Download");
             if (!root.exists()) {
                 root.mkdirs();
             }
             File gpxfile = new File(root, sFileName);
             FileWriter writer = new FileWriter(gpxfile);
             writer.append(sBody);
             writer.flush();
             writer.close();
             Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
             editText.setText("saved");
             editText.setText(currentact);

         } catch (IOException e) {
             e.printStackTrace();
             editText.setText("not saved"+e.getMessage());

         }
     }
     public void generateNoteOnSD2(Context context, String sFileName, String sBody) {
         try {
             File root = new File(sFileName);
             if (!root.exists()) {
                 root.mkdirs();
             }
             File gpxfile = new File( sFileName);
             FileWriter writer = new FileWriter(gpxfile);
             writer.append(sBody);
             writer.flush();
             writer.close();
             Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
             editText.setText("saved");
             editText.setText(currentact);

         } catch (IOException e) {
             e.printStackTrace();
             editText.setText("not saved"+e.getMessage());

         }
     }
    //importing database
    private void importDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data  = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "PackageName"
                        + "//databases//" + "DatabaseName";
                String backupDBPath  = "/BackupFolder/DatabaseName";
                File  backupDB= new File(data, currentDBPath);
                File currentDB  = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }
    public static double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }
    public String getPath(Uri uri) {

        String path = null;
        String[] projection = { MediaStore.Files.FileColumns.DATA };
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);

        if(cursor == null){
            path = uri.getPath();
        }
        else{
            cursor.moveToFirst();
            int column_index = cursor.getColumnIndexOrThrow(projection[0]);
            path = cursor.getString(column_index);
            cursor.close();
        }

        return ((path == null || path.isEmpty()) ? (uri.getPath()) : path);
    }
    //exporting database
    public void requestRuntimePermission() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(getBaseContext(),android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this,
                        new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }
    }
    private void sampleexport()
    {
        Intent i = new Intent(getBaseContext(), Fileexplor.class);
        i.putExtra("PersonID", 1);
        startActivity(i);
    }

    private void exportDB() {
        // TODO Auto-generated method stub

        try {
            File sd = Environment.getExternalStorageDirectory();
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String  currentDBPath= "//data//" + "PackageName"
                        + "//databases//" + "DatabaseName";
                String backupDBPath  = "/BackupFolder/DatabaseName";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                FileChannel src = new FileInputStream(currentDB).getChannel();
                FileChannel dst = new FileOutputStream(backupDB).getChannel();
                dst.transferFrom(src, 0, src.size());
                src.close();
                dst.close();
                Toast.makeText(getBaseContext(), backupDB.toString(),
                        Toast.LENGTH_LONG).show();

            }
        } catch (Exception e) {

            Toast.makeText(getBaseContext(), e.toString(), Toast.LENGTH_LONG)
                    .show();

        }
    }
    public boolean deleteTitle(String name) {
        String query = "DELETE FROM AndroidJSonTable WHERE name = '" + name + "'";
        if(name.equals("all"))
            query="DELETE  FROM AndroidJSonTable";
        sqLiteDatabase.execSQL(query);
        onResume();
        return true;
    }

    public void sendemail(String email, String text) {
        Intent i = new Intent(Intent.ACTION_SEND);
        i.setType("message/rfc822");
        i.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
        i.putExtra(Intent.EXTRA_SUBJECT, "subject of email");
        i.putExtra(Intent.EXTRA_TEXT, text);
        try {
            startActivity(Intent.createChooser(i, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(SearchSQLiteActivity.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public static void openBrowser(final Context context, String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }

       // Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
      //  context.startActivity(Intent.createChooser(intent, "Chose browser"));
    }

    public static boolean isImageFile(String path) {
        String mimeType = URLConnection.guessContentTypeFromName(path);
        return mimeType != null && mimeType.startsWith("image");
    }
    public String showwebcontent(String webPage)
    {

        try {
            URL web = new URL(webPage);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                            web.openStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null)
                System.out.println(inputLine);

            in.close();
        } catch (MalformedURLException e) {
           return  e.getMessage();
        } catch (IOException e) {
            return e.getMessage();
        }
        return "";
    }

    public String getRealPathFromURI(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    private void showPhoto(Uri photoUri) {

        Toast.makeText(SearchSQLiteActivity.this, "is image", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setDataAndType(photoUri, "image/*");
        startActivity(intent);


    }
    private String  getval(String name)
    {
        name=name.replace("@","");
        String query="SELECT * FROM AndroidJSonTable  WHERE name = '" + name+"'";
         SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        return cursor.getString(cursor.getCount());
    }

    private void showvideo(Uri videourl) {
        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setDataAndType(videourl, "video/*");

        startActivity(Intent.createChooser(intent, "play video using...."));
    }

    public void randomrow() {

         query = "AndroidJSonTable ADD COLUMN score VARCHAR(1200)\n";


        sqLiteDatabase.execSQL(query);

    }
    public static boolean isAnOperator(char c){
        return (c == '*' || c == '/' || c == '+' || c == '-' || c == '%');
    }
    /**
     * Checks position and placement of (, ), and operators in a string
     * to make sure it is a valid arithmetic expression
     * @param expression
     * @return true if the string is a valid arithmetic expression, false if not
     */
    private static boolean isValidExpression(String expression){
        //remove unnecessary whitespaces
        expression = expression.replaceAll("\\s+", "");
        //TEST 1: False if expression starts or ends with an operator
        if (isAnOperator(expression.charAt(0)) || isAnOperator(expression.charAt(expression.length()-1)))
            return false;
        //System.out.println("Does not start or end with operator");


        //TEST 2: False if test has mismatching number of opening and closing parantheses

        int unclosedParenthesis = 0;
        //System.out.println("Parentheses counter initialized to 0");

        for (int i = 0; i < expression.length(); i++){
            //System.out.println("For loop count: " + i);
            if (expression.charAt(i) == '('){
                //System.out.println("( found");
                unclosedParenthesis++;

                //SUBTEST: False if expression ends with '('
                if (i == expression.length()-1) return false;
            }
            if (expression.charAt(i) == ')'){
                unclosedParenthesis--;
                //System.out.println(") found");
                //SUBTEST: False if expression starts with ')'
                if (i == 0) return false;

            }
            if (isAnOperator(expression.charAt(i))){

                //System.out.println("Found an Operator");
                //TEST 3: False if operator is preceded by an operator or opening p aranthesis
                //or followed by closing paranthesis
                if (expression.charAt(i-1) == '(' || expression.charAt(i+1) == ')' || isAnOperator(expression.charAt(i+1))){
                    //System.out.println("Found wrongly preceding or following parenthesis to operator");
                    //System.out.println("or Found an operator following another operator");
                    return false;
                }

            }

        }
        return (unclosedParenthesis == 0);
    }
    public void maximize() {
        try {
            query = "ALTER TABLE AndroidJSonTable ADD COLUMN score VARCHAR(1200)\n";

            sqLiteDatabase.execSQL(query);
        } catch (Exception ex) {
            Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3) {
            Toast.makeText(SearchSQLiteActivity.this, data.getData().getPath(), Toast.LENGTH_SHORT).show();
            showPhoto(data.getData());
            String s = editText.getText().toString();
            InsertDataIntoSQLiteDatabase(Integer.toString(getProfilesCount()|+1),s.split(":")[0], ":image" + data.getData().toString(), "0");

        }

        if (requestCode == ACTIVITYB_REQUEST) {
            //and all went fine...
            if (resultCode == RESULT_OK) {
                //if Intent is not null
                if (data != null) {

                    //get your string

                    String newString = data.getExtras().getString("string_key");

                    //set your EditText

                    //set your EditText
                   generateNoteOnSD2(getBaseContext(),newString,s);
                   path=newString;
                    editText.setText(newString);
                      if(action.contains(":read file")) {
                          try {
                              try {

                                if(data.getExtras().getString("string_key")!=null) {
                                    File file = new File(data.getExtras().getString("string_key"));

                                    editText.setText(data.getExtras().getString("string_key") + readwithimport2(data.getExtras().getString("string_key")));
                                }

                                }
                              catch (Exception ex)
                              {
                                  Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

                              }
                          }
                          catch(Exception ex)
                          {
                              editText.setText(ex.getMessage());
                          }
                      }
                }
            }
        }
        if (requestCode == 1) {
            try {
                File file = new File(getFilesDir(), "d.txt");
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream);

                BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);

                bufferedWriter.write("d");
                FileInputStream fileinputStream = new FileInputStream(file);
                StringBuilder total = new StringBuilder();
                String line;
                BufferedReader r = new BufferedReader(new InputStreamReader(fileinputStream));

                while ((line = r.readLine()) != null) {
                    total.append(line).append('\n');
                }
                  editText.setText(file.getPath());
            }
            catch (Exception ex)
            {
                Toast.makeText(SearchSQLiteActivity.this, ex.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }



    }
    private String readFromFile(String path,Context context,int line ) {

        String ret = "";
        int i=0;
        try {
            InputStream inputStream = context.openFileInput(path);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    if(i==line)
                        return receiveString;
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return "not found that line";
    }
    private String readFromFile(String path,Context context ) {

        String ret = "";
      path=path.replace("/","//");
        try {
            InputStream inputStream = context.openFileInput(path);

            if ( inputStream != null ) {
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String receiveString = "";
                StringBuilder stringBuilder = new StringBuilder();

                while ( (receiveString = bufferedReader.readLine()) != null ) {
                    stringBuilder.append(receiveString);
                }

                inputStream.close();
                ret = stringBuilder.toString();
            }
        }
        catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        }

        return ret;
    }

    private void writeToFile(String data,String path,Context context) {
        try {
            OutputStreamWriter outputStreamWriter = new OutputStreamWriter(context.openFileOutput(path, Context.MODE_PRIVATE));
            outputStreamWriter.write(data);
            outputStreamWriter.close();
        }
        catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
    public void InsertDataIntoSQLiteDatabase(String id,String Name, String text, String score) {

        query = "INSERT or IGNORE into " + SQLiteHelper.TABLE_NAME + " (id,name,phone_number,score) VALUES(' " + id + "','" + Name + "', '" + text + "', '" + score + "');";

        sqLiteDatabase.execSQL(query);


    }
    public void InsertDataIntoSQLiteDatabasewothupdate(String id,String Name, String text, String score) {

        if (deleteTitle(Name) == true) {
            InsertDataIntoSQLiteDatabase(id,Name,text, score);
            onResume();
        }
        else
            InsertDataIntoSQLiteDatabase(id,Name, text,score);

    }
    public void showmessage(String text) {
        Toast.makeText(SearchSQLiteActivity.this,text, Toast.LENGTH_SHORT).show();

    }

     public String  getLines(TextView view,int i1) {
         final List<CharSequence> lines = new ArrayList<>();
         final Layout layout = view.getLayout();

         if (layout != null) {
             // Get the number of lines currently in the layout
             final int lineCount = layout.getLineCount();

             // Get the text from the layout.
             final CharSequence text = layout.getText();

             // Initialize a start index of 0, and iterate for all lines
             for (int i = 0, startIndex = 0; i < lineCount; i++) {
                 // Get the end index of the current line (use getLineVisibleEnd()
                 // instead if you don't want to include whitespace)
                 final int endIndex = layout.getLineEnd(i);

                 // Add the subSequence between the last start index
                 // and the end index for the current line.
                 lines.add(text.subSequence(startIndex, endIndex));

                 // Update the start index, since the indices are relative
                 // to the full text.
                 startIndex = endIndex;
             }
         }
         return lines.get(i1).toString() ;
     }
     public int getCurrentCursorLine(EditText editText)
     {
         int selectionStart = Selection.getSelectionStart(editText.getText());
         Layout layout = editText.getLayout();

         if (!(selectionStart == -1)) {
             return layout.getLineForOffset(selectionStart);
         }

         return -1;
     }
    @Override
    protected void onResume() {

            DisplayDataInToListView();


        super.onResume();
    }//
    public int getProfilesCount() {
        String countQuery = "SELECT  * FROM AndroidJSonTable";
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    public int getProfilesCount(String name1) {
        String countQuery = "SELECT  * FROM AndroidJSonTable where name = "+name1;
        SQLiteDatabase db = sqLiteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();

        return count;
    }
    public int Displayrandom() {

        sqLiteDatabase = sqLiteHelper.getWritableDatabase();
        Random r=new Random();
        int c=r.nextInt(getProfilesCount());
        showmessage(c+"");
        int i=0;
        cursor = sqLiteDatabase.rawQuery("SELECT * FROM AndroidJSonTable", null);
        Student student;
        StudentList = new ArrayList<Student>();

        if (cursor.moveToFirst()) {
            do {
             i++;
                if(i==c) {
                    String tempName = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name));

                    String tempNumber = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_PhoneNumber));
                    String tempScore = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_PhoneNumber));
                    if (score >= Integer.parseInt(tempScore)) {
                        student = new Student(tempName, tempNumber, tempScore);

                        StudentList.add(student);
                    }

                }
                listAdapter = new ListAdapter(SearchSQLiteActivity.this, R.layout.custom_layout, StudentList);
                ;
                listView.setAdapter(listAdapter);
                DisplayDataInToListView() ;
                cursor.close();
                return 1;
            } while (cursor.moveToNext());
        }

        return 0;
     }
     public String gethtml(String url)

     {
         result="";
         WebView webView = (WebView) findViewById(R.id.web14);
         webView.getSettings().setJavaScriptEnabled(true);
         webView.setVisibility(View.VISIBLE);
         webView.getSettings().setUserAgentString("Desktop");

         webbrowser.loadUrl(url);
         webView.getSettings().setUseWideViewPort(true);
         webView.getSettings().setLoadWithOverviewMode(true);
         webView.setWebViewClient(m);
         webView.setScrollBarSize(2);
         webView.setVisibility(View.VISIBLE);
         webView.evaluateJavascript(
                 "(function() { return ('<html>'+document.getElementsByTagName('html')[0].innerHTML+'</html>'); })();",
                 new ValueCallback<String>() {
                     @Override
                     public void onReceiveValue(String html) {
                         result= html;
                         // code here
                     }
                 });
         return result;
     }
     public void openweb(String url) {
         Toast.makeText(getBaseContext(), "webpageloaded", Toast.LENGTH_SHORT).show();
         WebView webView = (WebView) findViewById(R.id.web14);
         webView.getSettings().setJavaScriptEnabled(true);
         webView.setVisibility(View.VISIBLE);
         webView.getSettings().setUserAgentString("Desktop");
         String text="df";
         webView.evaluateJavascript("(function(){document.activeElement.value = '"+text+"'})()",
                 new ValueCallback<String>() {
                     @Override
                     public void onReceiveValue(String value) {

                     }
                 });
         webbrowser.loadUrl(url);
         webView.getSettings().setUseWideViewPort(true);
         webView.getSettings().setLoadWithOverviewMode(true);
         webView.setWebViewClient(m);
         webView.setScrollBarSize(2);
         webView.setVisibility(View.VISIBLE);
         webView.setWebViewClient(new WebViewClient() {

             public void onPageFinished(WebView view, String url) {
                 // do your stuff here)
                 if(gethtml(url).equals(""))
                     editText.setText("not treu");
             }
         });
      }
     public void DisplayDataInToListView() {

         sqLiteDatabase = sqLiteHelper.getWritableDatabase();
webbrowser.setVisibility(View.INVISIBLE);
         cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_NAME+"", null);

         Student student;
         StudentList = new ArrayList<Student>();


         if (cursor.moveToFirst()) {
             do {

                 String tempName = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name));

                 String tempNumber= cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_PhoneNumber));
                 String tempScore= cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_PhoneNumber));
               if(isscore) {
                   if (score >= Integer.parseInt(tempScore)) {
                       student = new Student(tempName, tempNumber, tempScore);

                       StudentList.add(student);
                   }
               }
               else
               {
                   student = new Student(tempName, tempNumber, tempScore);

                   StudentList.add(student);
               }
             } while (cursor.moveToNext());
         }

         listAdapter = new ListAdapter(SearchSQLiteActivity.this, R.layout.custom_layout, StudentList);

         listView.setAdapter(listAdapter);


         listView.invalidate();

         cursor.close();
     }
     public void DisplayDataInToListView(String title) {
try {
    sqLiteDatabase = sqLiteHelper.getWritableDatabase();

    cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + SQLiteHelper.TABLE_NAME + " where name LIKE '%" + title + "%'", null);

    Student student;
    StudentList = new ArrayList<Student>();


    if (cursor.moveToFirst()) {
        do {

            String tempName = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name));

            String tempNumber = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_PhoneNumber));
            String tempScore = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_PhoneNumber));
            if (score >= Integer.parseInt(tempScore)) {
                student = new Student(tempName, tempNumber, tempScore);

                StudentList.add(student);
            }

        } while (cursor.moveToNext());
    }

    listAdapter = new ListAdapter(SearchSQLiteActivity.this, R.layout.custom_layout, StudentList);

    listView.setAdapter(listAdapter);
    if (listView.getCount() == 0)

    {
        if ((name != null) && (name.contains(" ")))
            name = name.substring(name.lastIndexOf(" ") + 1);
        StudentList = new ArrayList<Student>();


        if (cursor.moveToFirst()) {
            do {

                String tempName = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name));

                String tempNumber = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_PhoneNumber));
                String tempScore = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_PhoneNumber));
                if (score >= Integer.parseInt(tempScore)) {
                    if ((tempNumber.contains("http")))
                        openweb(tempName);

                    student = new Student(tempName, tempNumber, tempScore);

                    StudentList.add(student);
                }

            } while (cursor.moveToNext());
        }

        listAdapter = new ListAdapter(SearchSQLiteActivity.this, R.layout.custom_layout, StudentList);

        listView.setAdapter(listAdapter);
    }

    listView.invalidate();

    cursor.close();
}
catch (Exception ex)
{
    editText.setText(ex.getMessage());
}
     }
    public void generateNoteOnSD(Context context, String sFileName, String sBody) {
        try {
            File root = new File(Environment.getExternalStorageDirectory(), "Notes");
            if (!root.exists()) {
                root.mkdirs();
            }
            File gpxfile = new File(root, sFileName);
            FileWriter writer = new FileWriter(gpxfile);
            writer.append(sBody);
            writer.flush();
            writer.close();
            Toast.makeText(context, "Saved", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void exportalldata() {

        try {
            editText.setText("exporting...");

            File dir = new File("//sdcard//Download//");

            File file = new File(dir, "message.txt"); File file2 = new File(dir, "message.txt");

            path=file.getPath();
            sqLiteDatabase = sqLiteHelper.getWritableDatabase();

            cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_NAME+"", null);

            Student student;
            StudentList = new ArrayList<Student>();

            if (cursor.moveToFirst()) {
                do {

                    String tempName = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name));

                    String tempNumber= cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_PhoneNumber));
                    String tempScore= cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_PhoneNumber));

                        student = new Student(tempName, tempNumber, tempScore);
                          s+=tempName+":"+tempNumber+":"+tempScore+"\n";
                        StudentList.add(student);


                } while (cursor.moveToNext());
            }

            listAdapter = new ListAdapter(SearchSQLiteActivity.this, R.layout.custom_layout, StudentList);


            FileOutputStream outputStream;

            listView.setAdapter(listAdapter);
            listView.invalidate();
            cursor.close();
            generateNoteOnSD1(getBaseContext(),"message.txt",s);



            SharedPreferences.Editor editor = getSharedPreferences("book", MODE_PRIVATE).edit();

             generateNoteOnSD1(getBaseContext(),"file.txt",s);

            Intent intent = new Intent(SearchSQLiteActivity.this, Fileexplor.class);
            startActivityForResult(intent, ACTIVITYB_REQUEST);


        } catch (Exception e) {
            Toast.makeText(SearchSQLiteActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }
     @Override
     protected void onPause() {

         super.onPause();
     }
    private void writeToSDFile(){

        // Find the root of the external storage.
        // See http://developer.android.com/guide/topics/data/data-  storage.html#filesExternal

        File root = android.os.Environment.getExternalStorageDirectory();

        // See http://stackoverflow.com/questions/3551821/android-write-to-sd-card-folder

        File dir = new File (root.getAbsolutePath() + "/download");
        dir.mkdirs();
        File file = new File(dir, "myData.txt");

        try {
            FileOutputStream f = new FileOutputStream(file);
            PrintWriter pw = new PrintWriter(f);
            pw.println("Hi , How are you");
            pw.println("Hello");
            pw.flush();
            pw.close();
            f.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.i(":D", "******* File not found. Did you" +
                    " add a WRITE_EXTERNAL_STORAGE permission to the   manifest?");
        } catch (IOException e) {
            e.printStackTrace();
        }
     }
    public void importalldata() {
        String s="";
        try {
            File dir = new File("//sdcard//Download//");
            File file = new File(dir, "message.txt");
            path=file.getPath();
            sqLiteDatabase = sqLiteHelper.getWritableDatabase();

            cursor = sqLiteDatabase.rawQuery("SELECT * FROM "+SQLiteHelper.TABLE_NAME+"", null);

            Student student;
            StudentList = new ArrayList<Student>();

            if (cursor.moveToFirst()) {
                do {

                    String tempName = cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_1_Name));

                    String tempNumber= cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_2_PhoneNumber));
                    String tempScore= cursor.getString(cursor.getColumnIndex(SQLiteHelper.Table_Column_3_PhoneNumber));

                    student = new Student(tempName, tempNumber, tempScore);
                    write(getProfilesCount()+1,tempName,tempNumber,tempScore);

                    s+=tempName+":"+tempNumber+":"+tempScore+"\n";
                    StudentList.add(student);


                } while (cursor.moveToNext());
            }
              s+="imported";
            listAdapter = new ListAdapter(SearchSQLiteActivity.this, R.layout.custom_layout, StudentList);

           listView.setAdapter(listAdapter);

            cursor.close();
            // file.createNewFile();

            Toast.makeText(SearchSQLiteActivity.this, "exported", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            Toast.makeText(SearchSQLiteActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }

    }

     @Override
     public boolean handleMessage(Message message) {
         if (message.what == CLICK_ON_URL){
             handler.removeMessages(CLICK_ON_WEBVIEW);
             return true;
         }
         if (message.what == CLICK_ON_WEBVIEW){
             Toast.makeText(this, "WebView clicked", Toast.LENGTH_SHORT).show();
             return true;
         }
         return false;
     }

     @Override
     public boolean onTouch(View view, MotionEvent motionEvent) {
         webbrowser.setVisibility(View.VISIBLE);
         WebView webView = (WebView) findViewById(R.id.web14);


         editText.setText(m.urls);
         Toast.makeText(SearchSQLiteActivity.this, "clicked", Toast.LENGTH_SHORT).show();

         return false;
     }
 }