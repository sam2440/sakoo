package com.goharshad.arena.sakoo;

/**
 * Created by Home on 8/29/2018.
 */

public class Taklif {
    private String lesson;
    private String taklif;
    private TaklifStatus status;

    public Taklif(String lesson, String taklif, TaklifStatus status) {
        this.lesson = lesson;
        this.taklif = taklif;
        this.status = status;
    }

    public String getLesson() {
        return lesson;
    }

    public String getTaklif() {
        return taklif;
    }

    public TaklifStatus getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "Taklif{" +
                "lesson='" + lesson + '\'' +
                ", taklif='" + taklif + '\'' +
                ", status=" + status.toString().toLowerCase()+
                '}';
    }
}
