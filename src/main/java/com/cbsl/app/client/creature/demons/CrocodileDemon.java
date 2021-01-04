package com.cbsl.app.client.creature.demons;

import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.creature.Demon;
import com.cbsl.app.client.view.DraggableView;
import javafx.scene.image.Image;

public class CrocodileDemon extends Demon {
    public CrocodileDemon(){
        super();
        this.id = -3;
        this.hp=3000;
        this.power=400;
        this.defense=800;
        this.resthp=hp;

        Y = 3;
        Image image = new Image("com/cbsl/app/client/view/images/crocodile.png");

        imageView = new DraggableView(id, image);
    }

    @Override
    public void specialAttack() {
        super.specialAttack();
    }
}
