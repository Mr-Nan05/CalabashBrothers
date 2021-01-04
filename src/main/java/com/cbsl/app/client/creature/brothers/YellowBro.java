package com.cbsl.app.client.creature.brothers;

import com.cbsl.app.client.creature.Brother;
import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.view.DraggableView;
import javafx.scene.image.Image;

public class YellowBro extends Brother {
    public YellowBro(){
        super();
        this.id = 3;
        this.hp=3000;
        this.power=400;
        this.defense=800;
        this.resthp=this.hp;

        Y = 3;
        Image image = new Image("com/cbsl/app/client/view/images/brother3.PNG");
        imageView = new DraggableView(id, image);
    }

    @Override
    public void specialAttack() {
        super.specialAttack();
    }
}
