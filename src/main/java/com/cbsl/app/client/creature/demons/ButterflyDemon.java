package com.cbsl.app.client.creature.demons;

import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.creature.Demon;
import com.cbsl.app.client.view.DraggableView;
import javafx.scene.image.Image;

public class ButterflyDemon extends Demon {
    public ButterflyDemon() {
        super();
        this.id = -5;
        this.hp=3000;
        this.power=600;
        this.defense=100;
        this.resthp=hp;

        Y = 5;
        Image image = new Image("com/cbsl/app/client/view/images/butterfly.png");
        imageView = new DraggableView(id, image);
    }

    @Override
    public void specialAttack() {
        super.specialAttack();
    }
}
