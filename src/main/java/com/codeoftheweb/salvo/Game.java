package com.codeoftheweb.salvo;

import java.util.Date;

public class Game {

    private Date creationDate;


    public Game() { }

    public Game(Date created) {
        this.creationDate = created;

    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate() {
        Date date=new Date;
        this.creationDate = date;
    }


}
