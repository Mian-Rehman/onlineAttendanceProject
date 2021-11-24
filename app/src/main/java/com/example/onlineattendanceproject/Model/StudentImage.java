package com.example.onlineattendanceproject.Model;

public class StudentImage {

    String studentId;
    String newImage;

    public StudentImage() {
    }

    public StudentImage(String studentId, String newImage) {
        this.studentId = studentId;
        this.newImage = newImage;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    public String getNewImage() {
        return newImage;
    }

    public void setNewImage(String newImage) {
        this.newImage = newImage;
    }
}
