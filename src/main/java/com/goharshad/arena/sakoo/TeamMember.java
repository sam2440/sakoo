package com.goharshad.arena.sakoo;

public class TeamMember {
    private int img;
    private String name;
    private String occ;

    public TeamMember(int img, String name, String occ) {
        this.img = img;
        this.name = name;
        this.occ = occ;
    }

    public int getImg() {
        return img;
    }

    public String getName() {
        return name;
    }

    public String getOcc() {
        return occ;
    }
}
