package pl.gda.pg.student.project.server.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;

public class PlayerPositioner
{
    private final static int TILE_SIZE = 27;
    private final static int GAME_HEIGHT = Gdx.graphics.getHeight();
    private final static int GAME_WIDTH = Gdx.graphics.getWidth();
    private int playerCount = 0;
    
    public Vector2 getPosition()
    {
        playerCount++;
        int positionPlace = playerCount % 4;
        if(positionPlace == 1)
            return new Vector2(TILE_SIZE, TILE_SIZE);
        else if(positionPlace == 2)
            return new Vector2(GAME_WIDTH - 2*TILE_SIZE, GAME_HEIGHT - 2*TILE_SIZE);
        else if(positionPlace == 3)
            return new Vector2(GAME_WIDTH - 2*TILE_SIZE, TILE_SIZE);
        else
            return new Vector2(TILE_SIZE, GAME_HEIGHT - 2*TILE_SIZE);
    }
}
