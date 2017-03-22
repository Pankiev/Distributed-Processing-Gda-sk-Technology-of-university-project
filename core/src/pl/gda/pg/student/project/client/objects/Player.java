package pl.gda.pg.student.project.client.objects;

import com.badlogic.gdx.graphics.Texture;
import pl.gda.pg.student.project.client.GameClient;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.libgdxcommon.objects.MovableGameObject;

/**
 * Created by Bartek on 22.03.2017. Gry Karciane
 */
public class Player extends MovableGameObject
{
    private Texture down = GameClient.assets.get("pacman_dol.bmp");
    private Texture up = GameClient.assets.get("pacman_gora.bmp");
    private Texture left = GameClient.assets.get("pacman_lewo.bmp");
    private Texture right = GameClient.assets.get("pacman_prawo.bmp");

    public Player(State linkedState)
    {
        super(GameClient.assets.get("pacman_dol.bmp"), linkedState);
    }

    @Override
    public void moveUp()
    {
        super.moveUp();
        super.getSprite().setTexture(up);
    }

    @Override
    public void moveDown()
    {
        super.moveDown();
        super.getSprite().setTexture(down);
    }

    @Override
    public void moveLeft()
    {
        super.moveLeft();
        super.getSprite().setTexture(left);
    }

    @Override
    public void moveRight()
    {
        super.moveRight();
        super.getSprite().setTexture(right);
    }

    @Override
    public void update()
    {
    }

}
