package com.cbsl.app.client.view;

import javafx.scene.image.ImageView;

public class ChosenView extends ImageView {
    private final double UNIT = 47;
    private boolean chosen;
    ChosenView(){
        super();
        chosen = false;
        setFitWidth(UNIT);
        setFitHeight(UNIT);
        setVisible(false);
    }

    ChosenView(int id){
        super();
        chosen = false;
        setFitWidth(UNIT);
        setFitHeight(UNIT);
        setVisible(false);

        switch (id){
            case 1: setLayoutX(329);
                    setLayoutY(15);
                    break;
            case 2: setLayoutX(451);
                    setLayoutY(15);
                    break;
            case 3: setLayoutX(573);
                    setLayoutY(15);
                    break;
            case 4: setLayoutX(329);
                    setLayoutY(79);
                    break;
            case 5: setLayoutX(451);
                    setLayoutY(79);
                    break;
            case -1: setLayoutX(382);
                setLayoutY(15);
                break;
            case -2: setLayoutX(505);
                setLayoutY(15);
                break;
            case -3: setLayoutX(626);
                setLayoutY(15);
                break;
            case -4: setLayoutX(382);
                setLayoutY(79);
                break;
            case -5: setLayoutX(504);
                setLayoutY(79);
                break;
        }
    }

    void choose(){
        chosen = true;
        setVisible(true);
    }

    void cancel(){
        chosen = false;
        setVisible(false);
    }

    boolean isChosen(){
        return chosen;
    }

}
