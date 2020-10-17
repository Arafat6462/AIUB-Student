package com.arafat6462.aiubstudent;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by arafat on 10/7/20 at 2:40 PM.
 */
public class ResultCalculation {

    double totalGpaCurrentSemester;
    double totalCreditCurrentSemester;
    double backUpCreditForThisSemester;
    double backUpGpaForThisSemester;

    double currentSemesterResult;
    double totalCgpa;
    int totalCreditCompleted;

    public void calculateResult(double completedCgpa, double completedCredit, int[] seekBarValue, int[] seekBarValueRetake, ArrayList<Double> perCourseCredit) {


        double[] gradePoint = new double[seekBarValue.length];
        double[] gradePointRetake = new double[seekBarValue.length];

        totalGpaCurrentSemester = 0;
        totalCreditCurrentSemester = 0;
        currentSemesterResult = 0;
        totalCgpa = 0;
        totalCreditCompleted = 0;
        backUpCreditForThisSemester = 0;
        backUpGpaForThisSemester = 0;


        // initialize all value to -1
        for (int i = 0; i < seekBarValue.length; i++) {
            gradePoint[i] = -1;
            gradePointRetake[i] = -1;
        }


        //  converting seekBar value to grading point of regular course
        // convert seekBarValue=gradePoint and seekBarValueRetake=gradePointRetake
        for (int i = 0; i < seekBarValue.length; i++) {

            switch (seekBarValue[i]) {
                case 0:
                    gradePoint[i] = -1;
                    break;
                case 1:
                    gradePoint[i] = 0.00; // for f grade
                    break;
                case 2:
                    gradePoint[i] = 2.25;
                    break;
                case 3:
                    gradePoint[i] = 2.50;
                    break;
                case 4:
                    gradePoint[i] = 2.75;
                    break;
                case 5:
                    gradePoint[i] = 3.00;
                    break;
                case 6:
                    gradePoint[i] = 3.25;
                    break;
                case 7:
                    gradePoint[i] = 3.50;
                    break;
                case 8:
                    gradePoint[i] = 3.75;
                    break;
                case 9:
                    gradePoint[i] = 4.00;
                    break;


            }
        }


        //  converting seekBar Retake value to grading point of Retake course
        for (int i = 0; i < seekBarValueRetake.length; i++) {

            switch (seekBarValueRetake[i]) {
                case 0:
                    gradePointRetake[i] = -1;
                    break;
                case 1:
                    gradePointRetake[i] = 0.00;
                    break;
                case 2:
                    gradePointRetake[i] = 2.25;
                    break;


            }

        }


        // finding total gpa and credit only for regular course.
        for (int i = 0; i < gradePoint.length; i++) {

            if (gradePoint[i] != -1) {   // except UW all result are copulative

                totalGpaCurrentSemester += perCourseCredit.get(i) * gradePoint[i];
                totalCreditCurrentSemester += perCourseCredit.get(i);

                backUpCreditForThisSemester += perCourseCredit.get(i); // for gpa
                backUpGpaForThisSemester += perCourseCredit.get(i) * gradePoint[i]; // for gpa


            } else {
                totalGpaCurrentSemester += 0;  // for 'UW' and 'default' cases, credit and gpa will be zero
                totalCreditCurrentSemester += 0;

            }

        }


        // finding total gpa and credit only for retake course.
        for (int i = 0; i < gradePointRetake.length; i++) {

            if (gradePointRetake[i] == -1) {

                totalGpaCurrentSemester += 0;  // for 'UW' and 'default' cases, credit and gpa will be zero
                totalCreditCurrentSemester += 0;
            }

            if (gradePointRetake[i] == 0.00 && gradePoint[i] != -1) { // for retake previous f grade

                totalGpaCurrentSemester += 0;                    // result is added previous code
                totalCreditCurrentSemester -= perCourseCredit.get(i); // as retake grade is f, so free the previous credit
            }

            if (gradePointRetake[i] == 2.25) { // if retake result is d, then we have to compare the result to find best one.


                if (gradePoint[i] > 0) {
                    totalGpaCurrentSemester -= gradePointRetake[i] * perCourseCredit.get(i); // this result is already added in previous semester when student get d/2.25.

                }

                if (gradePoint[i] != -1) { // as retake grade is f, so free the previous credit

                    totalCreditCurrentSemester -= perCourseCredit.get(i);
                }
            }
        }


        ///////////////////// final result //////////////


        // current semester
        currentSemesterResult = (Math.round(backUpGpaForThisSemester * 100) / 100.0d) / backUpCreditForThisSemester; // use backup gpa and credit bacause for this semester we don't count any previous result
        if (backUpCreditForThisSemester == 0.00) {
            currentSemesterResult = 0.00;
        }

        // credit
        totalCreditCompleted = (int) (completedCredit + totalCreditCurrentSemester);

        // cgpa
        totalGpaCurrentSemester = Math.round(totalGpaCurrentSemester * 100) / 100.0d;
        totalCgpa = ((completedCgpa * completedCredit) + totalGpaCurrentSemester) / (completedCredit + totalCreditCurrentSemester);


        // Log.d("value1", "gpa ...: "+ backUpGpaForThisSemester);
        Log.d("value1", "cgpa : " + currentSemesterResult);
        Log.d("value1", "credit : " + totalCgpa);

        // for founding the result after 2 decimal point
        currentSemesterResult = Math.round(currentSemesterResult * 100) / 100.0d;
        totalCgpa = Math.round(totalCgpa * 100) / 100.0d;
//
//        Log.d("value1", "cgpa : "+ currentSemesterResult);
//        Log.d("value1", "credit : "+ totalCgpa);
    }


    public double getCurrentSemesterResult() {
        return currentSemesterResult;
    }

    public double getTotalCgpa() {
        return totalCgpa;
    }

    public int getTotalCreditCompleted() {
        return totalCreditCompleted;
    }

}
