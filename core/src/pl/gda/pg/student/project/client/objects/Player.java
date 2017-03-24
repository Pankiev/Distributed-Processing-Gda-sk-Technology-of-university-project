package pl.gda.pg.student.project.client.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.client.GameClient;

public class Player extends ConnectionModelObject
{
    private Texture down = GameClient.assets.get("pacman_dol.bmp");
    private Texture up = GameClient.assets.get("pacman_gora.bmp");
    private Texture left = GameClient.assets.get("pacman_lewo.bmp");
    private Texture right = GameClient.assets.get("pacman_prawo.bmp");

    public Player(Vector2 position)
    {
        super(GameClient.assets.get("pacman_dol.bmp"), position);
    }

    public void lookUp(){
        super.setTexture(up);
    }

    public void lookDown(){
        super.setTexture(down);
    }

    public void lookLeft(){
        super.setTexture(left);
    }

    public void lookRight(){
        super.setTexture(right);
    }

}
