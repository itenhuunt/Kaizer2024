package team.diamond.kaizer.allusers;

public class AllUserModel {

    String realUser, fakeUser, name;  // точно как в firebase
    //  т.к. оно читает данные оттуда

    // только получаем   -- геттер  / правая кнопка выши
    // так как ттолько читаем


    public String getRealUser() {
        return realUser;
    }

    public String getFakeUser() {
        return fakeUser;
    }

    public String getName() {
        return name;
    }
}
