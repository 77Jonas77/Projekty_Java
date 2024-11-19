package w01.ex02;

public
class Main {

    public static void main(String[] args) {
        var obj = new Object(){
            public int getVal(){
                return 128;
            }
        };
        obj.getVal();


        A a = new A(10){
            @Override
            public void fun() {
                System.out.println("anonA:fun()"+w);
            }
        };
        a.fun();
    }
}

class A{

    protected int w;

    public A(int w){
        this.w = w;
    }

    public void fun(){
        System.out.println("A:fun()");
    }
}
