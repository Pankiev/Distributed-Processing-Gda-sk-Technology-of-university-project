package pl.gda.pg.student.project.server.helpers;


import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.kryonetcommon.IdSupplier;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.server.objects.Box;
import pl.gda.pg.student.project.server.objects.Wall;
import pl.gda.pg.student.project.server.states.ServerPlayState;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FieldInitializer {

    private final static int TILE_SIZE = 27;
	private final static int GAME_HEIGHT_IN_TILES = Gdx.graphics.getHeight()/27-1;
	private final static int GAME_WIDTH_IN_TILES = Gdx.graphics.getWidth()/27-1;
    private static Map<Long, GameObject> objects = new HashMap<Long, GameObject>();


	public static Map<Long, GameObject> initializeField(ServerPlayState playState)
	{

		for (int i = 0; i <= GAME_WIDTH_IN_TILES; i++)
        {
			for (int j = 0; j <= GAME_HEIGHT_IN_TILES; j++)
            {
				if (i == 0 || j == 0 || i == GAME_WIDTH_IN_TILES || j == GAME_HEIGHT_IN_TILES)
				{
					addWall(playState, i, j);
				} else if (i % 2 == 0 && j % 2 == 0)
				{
					addWall(playState, i, j);
				} else
				{
					tryAddBox(playState, i, j);
				}
            }
        }
       return objects;
    }

	private static void tryAddBox(ServerPlayState playState, int i, int j){
		Random rand = new Random();
		int hei = GAME_HEIGHT_IN_TILES-1;
		int wid = GAME_WIDTH_IN_TILES-1;
		if (rand.nextInt(2) == 0 && (i != 1 || j!= 1)&& (i != 1 || j!= 2)&& (i != 2 || j!= 1)
				&& (i != 1 || j!= hei)&& (i != 1 || j!= hei -1)&& (i != 2 || j!= hei)
				&& (i != wid || j!= hei)&& (i != wid || j!= hei -1)&& (i != wid-1 || j!= hei)
				&& (i != wid || j!= 1)&& (i != wid || j!= 2)&& (i != wid-1 || j!= 1)) {
			long id = IdSupplier.getId();
			Box box = new Box(playState, new Vector2(TILE_SIZE * i, TILE_SIZE * j));
			box.setId(id);
			objects.put(id, box);
		}

	}

	private static void addWall(ServerPlayState playState, int i, int j){
		long id = IdSupplier.getId();
		Wall wall = new Wall(playState, new Vector2(TILE_SIZE * i, TILE_SIZE * j));
		wall.setId(id);
		objects.put(id, wall);
	}

}
