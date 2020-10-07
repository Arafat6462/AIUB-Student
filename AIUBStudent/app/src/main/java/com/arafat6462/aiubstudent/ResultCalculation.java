package com.arafat6462.aiubstudent;

/**
 * Created by arafat on 10/7/20 at 2:40 PM.
 */
public class ResultCalculation {

    double totalGpaCurrentSemester;
    double totalCreditCurrentSemester ;

    double currentSemesterResult;
    double totalCgpa ;
    double totalCredit ;

    public void calculateResult(double completedCgpa, int completedCredit, int[] seekBarValue, int[] seekBarValueRetake, int[] perCourseCredit) {


        double[] gradePoint = new double[seekBarValue.length];
        double[] gradePointRetake = new double[seekBarValue.length];

          totalGpaCurrentSemester = 0;
          totalCreditCurrentSemester = 0;


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

            if (gradePointRetake[i] == 0.00) { // for retake previous f grade

                totalGpaCurrentSemester += 0;
                totalCreditCurrentSemester -= perCourseCredit[i]; // as retake grade is f, so free the previous credit
            }

            if (gradePointRetake[i] == 2.25) { // if retake result is d, then we have to compare the result to find best one.

                if (gradePointRetake[i] <= gradePoint[i]) {

                    totalGpaCurrentSemester += gradePoint[i];
                } else {
                    totalGpaCurrentSemester += gradePointRetake[i];

                }

                totalCreditCurrentSemester += 0;
            }
        }


        /////////////////////
        currentSemesterResult = totalGpaCurrentSemester / totalCreditCurrentSemester;
        totalCgpa = completedCgpa + currentSemesterResult;
        totalCredit = completedCredit + totalCreditCurrentSemester;





    }


    public double getCurrentSemesterResult() {
        return currentSemesterResult;
    }

    public double getTotalCgpa() {
        return totalCgpa;
    }

    public double getTotalCredit() {
        return totalCredit;
    }
}
