package com.cbsl.app.client.view;
import javafx.scene.control.ProgressBar;

import static java.lang.Math.abs;

public class BloodBar extends ProgressBar{
    BloodBar(int id){
        this.setPrefSize(57, 3);
        this.setProgress(1);
        this.setStyle("-fx-accent: red;");

        switch (abs(id)){
            case 1: this.setLayoutX(15);
                    this.setLayoutY(0);
                    break;
            case 2: this.setLayoutX(89);
                this.setLayoutY(0);
                break;
            case 3: this.setLayoutX(163);
                this.setLayoutY(0);
                break;
            case 4: this.setLayoutX(238);
                this.setLayoutY(0);
                break;
            case 5: this.setLayoutX(15);
                this.setLayoutY(65);
                break;
            case 6: this.setLayoutX(89);
                this.setLayoutY(65);
                break;
            case 7: this.setLayoutX(163);
                this.setLayoutY(65);
                break;
        }
    }
}
