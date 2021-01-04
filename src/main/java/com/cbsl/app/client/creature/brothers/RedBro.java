package com.cbsl.app.client.creature.brothers;

import com.cbsl.app.client.creature.Brother;
import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.view.DraggableView;
import javafx.scene.image.Image;

public class RedBro extends Brother {
    public RedBro(){
        super();
        this.id = 1;
        this.hp=5000;
        this.power=400;
        this.defense=300;
        this.resthp=this.hp;

        Y = 1;
        Image image = new Image("com/cbsl/app/client/view/images/brother1.PNG");
        imageView = new DraggableView(id, image);
    }

    @Override
    public void specialAttack() {
        super.specialAttack();
    }
}
