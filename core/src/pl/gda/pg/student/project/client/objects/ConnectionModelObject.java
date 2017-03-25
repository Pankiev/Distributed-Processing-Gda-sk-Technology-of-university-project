package pl.gda.pg.student.project.client.objects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public abstract class ConnectionModelObject {
    private long id;
    protected Sprite sprite;

    public ConnectionModelObject(Texture lookout, Vector2 position)
    {
        sprite = new Sprite(lookout);
        sprite.setRegion(lookout);
        sprite.setPosition(position.x, position.y);
    }

    public void positionUpdate(Vector2 newPosition){
        sprite.setPosition(newPosition.x, newPosition.y);
    }

    public void render(SpriteBatch batch)
    {
        sprite.draw(batch);
    }

    public void setId(long id){
        this.id = id;
    }

    public long getId(){
        return id;
    }

    public void setTexture(Texture texture){
        sprite.setTexture(texture);
    }

}
