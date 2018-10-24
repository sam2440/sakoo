package com.goharshad.arena.sakoo;

public class DailyReportObject {

    private String lesson;
    private String taklif;
    private int studyTime;

    public DailyReportObject(String lesson, String taklif, int studyTime) {
        this.lesson = lesson;
        this.taklif = taklif;
        this.studyTime = studyTime;
    }

    public String getLesson() {
        return lesson;
    }

    public String getTaklif() {
        return taklif;
    }

    public int getStudyTime() {
        return studyTime;
    }
}
