package com.cbsl.app.client.creature.brothers;

import com.cbsl.app.client.creature.Brother;
import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.view.DraggableView;
import javafx.scene.image.Image;

public class BlueBro extends Brother {
    public BlueBro(){
        super();
        this.id = 6;
        this.hp=4000;
        this.power=500;
        this.defense=200;
        this.resthp=this.hp;

        Y = 6;
        Image image = new Image("com/cbsl/app/client/view/images/brother6.PNG");
        imageView = new DraggableView(id, image);
    }

    @Override
    public void specialAttack() {
        super.specialAttack();
    }
}
