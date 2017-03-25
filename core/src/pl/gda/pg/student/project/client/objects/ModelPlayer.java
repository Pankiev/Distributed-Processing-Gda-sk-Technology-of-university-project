package pl.gda.pg.student.project.client.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

import pl.gda.pg.student.project.client.GameClient;

public class ModelPlayer extends ConnectionModelObject
{
	private Texture down = GameClient.assets.get("pacman_dol.png");
	private Texture up = GameClient.assets.get("pacman_gora.png");
	private Texture left = GameClient.assets.get("pacman_lewo.png");
	private Texture right = GameClient.assets.get("pacman_prawo.png");

    public ModelPlayer()
    {
		super(GameClient.assets.get("pacman_dol.png"), new Vector2(0, 0));
    }

    public ModelPlayer(Vector2 position)
    {
		super(GameClient.assets.get("pacman_dol.png"), position);
    }

    public void lookUp()
    {
        super.setTexture(up);
    }

    public void lookDown()
    {
        super.setTexture(down);
    }

    public void lookLeft()
    {
        super.setTexture(left);
    }

    public void lookRight()
    {
        super.setTexture(right);
    }

}
