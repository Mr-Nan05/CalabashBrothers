package com.cbsl.app.client.creature.brothers;

import com.cbsl.app.client.creature.Brother;
import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.view.DraggableView;
import javafx.scene.image.Image;

public class PurpleBro extends Brother {
    public PurpleBro(){
        super();
        this.id = 7;
        this.hp=3000;
        this.power=500;
        this.defense=200;
        this.resthp=this.hp;

        Y = 7;
        Image image = new Image("com/cbsl/app/client/view/images/brother7.PNG");
        imageView = new DraggableView(id, image);
    }

    @Override
    public void specialAttack() {
        super.specialAttack();
    }
}
