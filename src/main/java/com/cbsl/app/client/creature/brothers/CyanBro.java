package com.cbsl.app.client.creature.brothers;

import com.cbsl.app.client.creature.Brother;
import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.view.DraggableView;
import javafx.scene.image.Image;

public class CyanBro extends Brother {
    public CyanBro(){
        super();
        this.id = 5;
        this.hp=3000;
        this.power=600;
        this.defense=100;
        this.resthp=this.hp;

        Y = 5;
        Image image = new Image("com/cbsl/app/client/view/images/brother5.PNG");
        imageView = new DraggableView(id, image);
    }

    @Override
    public void specialAttack() {
        super.specialAttack();
    }
}