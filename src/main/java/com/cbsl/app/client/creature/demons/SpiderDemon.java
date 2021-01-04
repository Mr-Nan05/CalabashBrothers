package com.cbsl.app.client.creature.demons;

import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.creature.Demon;
import com.cbsl.app.client.view.DraggableView;
import javafx.scene.image.Image;

public class SpiderDemon extends Demon {
    public SpiderDemon(){
        super();
        this.id = -6;
        this.hp=4000;
        this.power=500;
        this.defense=200;
        this.resthp=hp;

        Y = 6;
        Image image = new Image("com/cbsl/app/client/view/images/spider.png");
        imageView = new DraggableView(id, image);
    }

    @Override
    public void specialAttack() {
        super.specialAttack();
    }
}
