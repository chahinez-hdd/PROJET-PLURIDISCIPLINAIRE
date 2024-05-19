package testMVC;

public class Model {
    private String data;
    Controller controller;
    public Model(String data) {
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}


