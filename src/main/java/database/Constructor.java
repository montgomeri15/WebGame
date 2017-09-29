package database;

public class Constructor {

    String name;
    String pass;

    public Constructor(){
    }

    public Constructor(String name, String pass) {
        this.name = name;
        this.pass = pass;
    }

    public String getName() {
        return name;
    }

    public String setName(String name) {
        this.name = name;
        return name;
    }

    public String getPass() {
        return pass;
    }

    public String setPass(String pass) {
        this.pass = pass;
        return pass;
    }
}
