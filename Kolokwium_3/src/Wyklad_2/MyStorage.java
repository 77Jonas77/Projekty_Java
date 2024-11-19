package Wyklad_2;

import Wyklad_2.Cookie;

public
class MyStorage {

    private Cookie[] cookies;
    private int count;

    public MyStorage(){
        this.cookies = new Cookie[5];
        this.count = 0;
    }

    public void add (Cookie cookie){
        if(count >= cookies.length){
            Cookie[] tmp = new Cookie[cookies.length*2];
            for(int i=0; i<cookies.length; i++)
                tmp[i] = cookies[i];
            cookies = tmp;
        }
        cookies[count++] = cookie;
    }

    public void show(){
        for(int i=0; i<count; i++)
            cookies[i].show();
    }
}
