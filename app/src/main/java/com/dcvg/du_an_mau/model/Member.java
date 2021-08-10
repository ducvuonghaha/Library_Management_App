package com.dcvg.du_an_mau.model;

import java.sql.Timestamp;

public class Member {
    public String member_id;
    public String member_name;
    public String birth;

    public String getMember_id() {
        return member_id;
    }

    public void setMember_id(String member_id) {
        this.member_id = member_id;
    }

    public String getMember_name() {
        return member_name;
    }

    public void setMember_name(String member_name) {
        this.member_name = member_name;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public Member(String member_id, String member_name, String birth) {
        this.member_id = member_id;
        this.member_name = member_name;
        this.birth = birth;
    }
}
