package com.cbsl.app.client.creature.demons;

import com.cbsl.app.client.creature.Creature;
import com.cbsl.app.client.creature.Demon;
import com.cbsl.app.client.view.DraggableView;
import javafx.scene.image.Image;

public class SnakeDemon extends Demon {
    public SnakeDemon(){
        super();
        this.id = -1;
        this.hp=5000;
        this.power=400;
        this.defense=300;
        this.resthp=hp;

        Y = 1;
        Image image = new Image("com/cbsl/app/client/view/images/snake.PNG");

        imageView = new DraggableView(id, image);
    }

    @Override
    public void specialAttack() {
        super.specialAttack();
    }
}
