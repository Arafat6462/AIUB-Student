package com.arafat6462.aiubstudent;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

/**
 * Created by arafat on 10/11/20 at 3:09 PM.
 */
public class ParseDataFromPortal extends AsyncTask<Void,Void,Void> {

    private  String username,password;
    private static String userId, userName; // static for access form other activity without changing data
    private static double cgpa;
    private static int completedCredit;
    private Activity activity;
    private AlertDialog dialog;
    ArrayList<String> courseNameAll = new ArrayList<String>();
    ArrayList<String> courseCreditAll = new ArrayList<String>();

    static ArrayList<String> courseName = new ArrayList<String>();
    static ArrayList<Double> courseCredit = new ArrayList<Double>();
    static ArrayList<Double> perSemesterResult = new ArrayList<Double>();
    static ArrayList<Double> perSemesterCredit = new ArrayList<Double>();


    public ParseDataFromPortal(String username, String password, Activity activity) {
        this.username = username;
        this.password = password;
        this.activity = activity;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        try {
            Connection.Response response= Jsoup.connect("https://portal.aiub.edu" ) // Login
                    .data("username",username,"password",password)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .execute();

            Log.d("new1","do in background :");

            Map<String,String> coky=response.cookies(); // create a season. and  this cookies will keep you login for next page or other task

            Document document=Jsoup.connect("https://portal.aiub.edu/Student/GradeReport/BySemester").cookies(coky).get();




            //////////// for //////////////


            for (Element row : document.select("table.table.table-bordered.table-compressed tr")){ // tr for only tale row will select

//                    if (row.select("td:nth-of-type(2)").text().equals("")){
//                        continue;
//                    }
                if (row.select("td:nth-of-type(2)").text().length() !=4  &&  row.select("td:nth-of-type(2)").text().length() !=5){
                    courseNameAll.add(row.select("td:nth-of-type(2)").text());
                    courseCreditAll.add(row.select("td:nth-of-type(3)").text());
                }

                // CHECK if the text is double, if true then set  credit and result
                try {
                    Double result = Double.parseDouble(row.select("td:nth-of-type(4)").text());
                    perSemesterResult.add(result);
                    perSemesterCredit.add(Double.parseDouble(row.select("td:nth-of-type(3)").text()));

                } catch (NumberFormatException e) {
                 }


            }

//            ///////////////////// check //////////////////
//            Log.d("new4","perSemesterCredit :"+ perSemesterCredit.size());
//            Log.d("new4","perSemesterResult :"+ perSemesterResult.size());
//            for (int i=0; i<perSemesterCredit.size(); i++){
//
//                Log.d("new4","perSemesterCredit "+i+" :" +perSemesterCredit.get(i));
//                Log.d("new4","perSemesterResult "+i+" :"+ perSemesterResult.get(i));
//
//            }
//            ///////////////////// check //////////////////

            ////////////// set course name & credit and sort right way ///////////////
            for (int i = courseNameAll.size()-1; i>=0; i--){
                if(courseNameAll.get(i).length() ==0 ){  // empty box will break the loop in html
                    break;
                }
                else {
                    String temp =  courseCreditAll.get(i);
                    String temp1 = temp.replace(")","");
                    String temp2 = temp1.replace("(","");

                    courseCredit.add(Double.parseDouble(temp2));
                    courseName.add(courseNameAll.get(i));

//                    Log.d("new1","course name :"+ courseNameAll.get(i));
//                    Log.d("new1","course credit :"+ courseCreditAll.get(i));
//
//                    double d = Double.parseDouble(courseCreditAll.get(i));
//                    Log.d("new1","course credit in double :"+ d);

                }

            }

            for (int i=0; i<courseName.size(); i++){
                Log.d("new1","course name :"+ courseName.get(i));
                Log.d("new1","course credit :"+ courseCredit.get(i));

            }

             userId = courseCreditAll.get(0);
             userName = courseCreditAll.get(1);
             completedCredit = Integer.parseInt(courseCreditAll.get(2));
             cgpa = Double.parseDouble(courseCreditAll.get(4));

            Log.d("new1","userId :"+ courseCreditAll.get(0));
            Log.d("new1","userName :"+ courseCreditAll.get(1));
            Log.d("new1","completedCredit :"+ courseCreditAll.get(2));
            Log.d("new1","cgpa :"+ courseCreditAll.get(4));

//
//            Log.d("new1","course name size :"+ courseNameAll.size());
//            Log.d("new1","course credit size :"+ courseCreditAll.size());
            ////////////// print course name /courseCredit.get(i)//////////////
//


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        dialog=new ProgressDialog(activity);
        dialog.setTitle("Login");
        dialog.setMessage("please wait. loading...");
        dialog.show();

    }

    @Override
    protected void onCancelled(Void aVoid) {
        super.onCancelled(aVoid);
        Toast toast = Toast.makeText(activity, "canced", Toast.LENGTH_LONG);
        toast.show();
        // if login failed, then go to login page again
        Intent intent = new Intent(activity, Login.class);
        activity.startActivity(intent);
    }


    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        dialog.dismiss();
        // textView.setText(str);
        // textView.setText(str);

        // start main activity form here instead of login class.
        Intent intent = new Intent(activity, MainActivity.class);
        activity.startActivity(intent);
    }


    ////////////////// getter ////////////////


    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public double getCgpa() {
        return cgpa;
    }

    public int getCompletedCredit() {
        return completedCredit;
    }

    public ArrayList<String> getCourseName() {
        return courseName;
    }

    public ArrayList<Double> getCourseCredit() {
        return courseCredit;
    }

    public static ArrayList<Double> getPerSemesterResult() {
        return perSemesterResult;
    }

    public static ArrayList<Double> getPerSemesterCredit() {
        return perSemesterCredit;
    }
}
