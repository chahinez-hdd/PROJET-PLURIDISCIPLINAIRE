package testMVC;

public class Controller {
    private Model model;
    private View view;

    public Controller(Model model, View view) {
        this.model = model;
        this.view = view;
    }

    public void updateView() {
        String data = model.getData();
        view.display(data);
    }

    public void setModelData(String data) {
        model.setData(data);
        view.display(data);
        this.setModelData(data);
    }

    public void someOtherMethod() {
        // Another action that might or might not be cohesive
    }
}
