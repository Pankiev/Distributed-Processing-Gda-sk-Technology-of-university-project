package pl.gda.pg.student.project.server.helpers;


import com.badlogic.gdx.math.Vector2;

public class BombLegalPositioninder {
    private final static int TILE_SIZE = 27;

    public Vector2 countBombLegalPosition(Vector2 playerPosition){
        Vector2 bombPosition = new Vector2();
        int playerPositionX = (int)playerPosition.x;
        int playerPositionY = (int)playerPosition.y;
        int fazeX = playerPositionX%TILE_SIZE;
        int fazeY = playerPositionY%TILE_SIZE;
        int noOfTileX = playerPositionX/TILE_SIZE;
        int noOfTileY = playerPositionY/TILE_SIZE;
        if(fazeX > TILE_SIZE/2)
            noOfTileX++;
        if(fazeY > TILE_SIZE/2)
            noOfTileY++;
        float bombPositionX = noOfTileX*TILE_SIZE;
        float bombPositionY = noOfTileY*TILE_SIZE;
        bombPosition.set(bombPositionX, bombPositionY);
        System.out.println(bombPosition);
        return bombPosition;
    }
}
