package Wyklad_2;

public
class Main {

    public static void main(String[] args) {
        /*int weight0 = 5;
        String ingr0 = "cynamon";

        int weight1 = 5;
        String ingr1 = "cynamon";

        int weight2 = 5;
        String ingr2 = "cynamon";

        int weight3 = 5;
        String ingr3 = "cynamon";

        System.out.println(weight0);
        System.out.println(weight1);
        System.out.println(weight2);

        int[] weigth = {5, 5, 5, 5};
        String[] ingr = {"cynamon","cynamon","cynamon","cynamon"};
        */

        Cookie cookie = new Cookie();
        cookie.show();

        Cookie cookie1 = new Cookie(4, "pomarancza");
        cookie1.show();

/*        Cookie[] cookies = new Cookie[5];
        int count = 0;

        cookies[count++] = cookie;

        for(int i=0; i<count; i++)
            if(cookies[i] != null)
                cookies[i].show();

        for (Cookie val : cookies)
            val.show();*/

        MyStorage storage = new MyStorage();
        storage.add(cookie);
        storage.add(cookie1);
        storage.show();

        for (int i = 0; i < 5; i++) {
            storage.add(
                    Cookie.make()
            );
        }

        System.out.println("++++++++++++");

        MyList list = new MyList();
        list.add(cookie);

        for (int i = 0; i < 100; i++) {
            list.add(
                    Cookie.make()
            );
        }

        list.show();

        System.out.println("=============");

        list.filter(7).show();

        System.out.println("__________________");
        list.filter("cynamon").show();
    }
}
