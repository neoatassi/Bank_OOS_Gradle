
package bank;
import javafx.stage.Stage;


public class StageHolder {
    private static StageHolder instance = null;
    private StageHolder() {}
    private Stage stage;
    public static StageHolder getInstance() {
        if (instance == null)
            instance = new StageHolder();
        return instance;
    }
    /**
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }
    /**
     * @param stage the stage to set
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
