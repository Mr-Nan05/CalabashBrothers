package com.cbsl.app.client.creature.demons;

import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.creature.Demon;
import com.cbsl.app.client.view.DraggableView;
import javafx.scene.image.Image;

public class ToadDemon extends Demon {
    public ToadDemon(){
        super();
        this.id = -7;
        this.hp=3000;
        this.power=500;
        this.defense=200;
        this.resthp=hp;

        Y = 7;
        Image image = new Image("com/cbsl/app/client/view/images/toad.PNG");
        imageView = new DraggableView(id, image);
    }

    @Override
    public void specialAttack() {
        super.specialAttack();
    }
}