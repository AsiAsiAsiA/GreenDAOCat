package com.example.semen.greendaocat;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity
public class Cat implements Serializable {

    @Id
    public Long id;

    public String name;
    static final long serialVersionUID = 42L;

    @Generated(hash = 551168960)
    public Cat(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    @Generated(hash = 205319056)
    public Cat() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name + ", Id: " + id;
    }
}
