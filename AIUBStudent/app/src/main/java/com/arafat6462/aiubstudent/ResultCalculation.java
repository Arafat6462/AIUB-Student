package com.arafat6462.aiubstudent;

import android.util.Log;

/**
 * Created by arafat on 10/7/20 at 2:40 PM.
 */
public class ResultCalculation {

    double totalGpaCurrentSemester;
    double totalCreditCurrentSemester ;
    double backUpCreditForThisSemester ;
    double backUpGpaForThisSemester;

    double currentSemesterResult;
    double totalCgpa;
    double totalCredit ;

    public void calculateResult(double completedCgpa, double completedCredit, int[] seekBarValue, int[] seekBarValueRetake, int[] perCourseCredit) {



        double[] gradePoint = new double[seekBarValue.length];
        double[] gradePointRetake = new double[seekBarValue.length];

          totalGpaCurrentSemester = 0;
          totalCreditCurrentSemester = 0;
          currentSemesterResult =0;
          totalCgpa =0;
          totalCredit =0;
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

                totalGpaCurrentSemester += perCourseCredit[i] * gradePoint[i];
                totalCreditCurrentSemester += perCourseCredit[i];

                backUpCreditForThisSemester += perCourseCredit[i]; // for gpa
                backUpGpaForThisSemester += perCourseCredit[i] * gradePoint[i]; // for gpa


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
                totalCreditCurrentSemester -= perCourseCredit[i]; // as retake grade is f, so free the previous credit
            }

            if (gradePointRetake[i] == 2.25) { // if retake result is d, then we have to compare the result to find best one.


                if (gradePoint[i] > 0) {
                    totalGpaCurrentSemester -= gradePointRetake[i] * perCourseCredit[i]; // this result is already added in previous semester when student get d/2.25.

                }

                if (gradePoint[i] != -1) { // as retake grade is f, so free the previous credit

                    totalCreditCurrentSemester -= perCourseCredit[i];
                }
            }
        }


        ///////////////////// final result //////////////
        currentSemesterResult = backUpGpaForThisSemester / backUpCreditForThisSemester;
        if (backUpCreditForThisSemester == 0.00) {
            backUpCreditForThisSemester = 0.00;
        }

        totalCredit = completedCredit + totalCreditCurrentSemester;
        totalCgpa = ((completedCgpa * completedCredit) + totalGpaCurrentSemester) / (completedCredit + totalCreditCurrentSemester);



        Log.d("value1", "gpa ...: "+ completedCgpa);
        Log.d("value1", "cgpa : "+ completedCredit);
        Log.d("value1", "credit : "+ totalCredit);


    }


    public double getCurrentSemesterResult() {
        return currentSemesterResult;
    }

    public double getTotalCgpa() {
        return totalCgpa;
    }

    public int getTotalCredit() {
        return (int) totalCredit;
    }
}
