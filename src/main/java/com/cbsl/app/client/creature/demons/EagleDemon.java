package com.cbsl.app.client.creature.demons;

import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.creature.Demon;
import com.cbsl.app.client.view.DraggableView;
import javafx.scene.image.Image;

public class EagleDemon extends Demon {
    public EagleDemon(){
        super();
        this.id = -4;
        this.hp=3000;
        this.power=600;
        this.defense=100;
        this.resthp=hp;

        Y = 4;
        Image image = new Image("com/cbsl/app/client/view/images/eagle.png");
        imageView = new DraggableView(id, image);
    }

    @Override
    public void specialAttack() {
        super.specialAttack();
    }
}
