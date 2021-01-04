package com.cbsl.app.client.creature.brothers;

import com.cbsl.app.client.creature.Brother;
import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.view.DraggableView;
import javafx.scene.image.Image;

public class OrangeBro extends Brother {
    public OrangeBro(){
        super();
        this.id = 2;
        this.hp=4000;
        this.power=500;
        this.defense=200;
        this.resthp=this.hp;

        Y = 2;
        Image image = new Image("com/cbsl/app/client/view/images/brother2.PNG");
        imageView = new DraggableView(id, image);
    }

    @Override
    public void specialAttack() {
        super.specialAttack();
    }
}
