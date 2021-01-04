package com.cbsl.app.client.creature.demons;

import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.creature.Demon;
import com.cbsl.app.client.view.DraggableView;
import javafx.scene.image.Image;

public class ScorpionDemon extends Demon {
    public ScorpionDemon(){
        super();
        this.id = -2;
        this.hp=4000;
        this.power=500;
        this.defense=200;
        this.resthp=hp;

        Y = 2;
        Image image = new Image("com/cbsl/app/client/view/images/scorpion.png");
        imageView = new DraggableView(id, image);
    }

    @Override
    public void specialAttack() {
        super.specialAttack();
    }
}
