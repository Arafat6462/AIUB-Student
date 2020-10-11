package com.arafat6462.aiubstudent;

import android.os.AsyncTask;
import android.util.Log;

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

    private String username,password;
    private String getUserId, getUserName, getCompletedCredit, getcgpa;
    ArrayList<String> courseNameAll = new ArrayList<String>();
    ArrayList<String> courseCreditAll = new ArrayList<String>();

    ArrayList<String> courseName = new ArrayList<String>();
    ArrayList<Double> courseCredit = new ArrayList<Double>();


    public ParseDataFromPortal(String username, String password) {
        this.username = username;
        this.password = password;
    }


    @Override
    protected Void doInBackground(Void... voids) {

        try {
            Connection.Response response= Jsoup.connect("https://portal.aiub.edu" ) // Login
                    .data("username",username,"password",password)
                    .method(Connection.Method.POST)
                    .ignoreContentType(true)
                    .execute();


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
                else {
                      String perSemesterGpa = row.select("td:nth-of-type(2)").text();

                }

            }

            ////////////// print course name ///////////////
            for (int i = courseNameAll.size()-1; i>=0; i--){
                if(courseNameAll.get(i).length() ==0 ){  // empty box will break the loop in html
                    break;
                }
                else {

                    courseName.add(courseNameAll.get(i));

                    String temp =  courseCreditAll.get(i);
                    String temp1 = temp.replace(")","");
                    String temp2 = temp1.replace("(","");

                    courseCredit.add(Double.parseDouble(temp2));

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

             getUserId = courseCreditAll.get(0);
             getUserName = courseCreditAll.get(1);
             getCompletedCredit = courseCreditAll.get(2);
             getcgpa = courseCreditAll.get(4);

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
}
