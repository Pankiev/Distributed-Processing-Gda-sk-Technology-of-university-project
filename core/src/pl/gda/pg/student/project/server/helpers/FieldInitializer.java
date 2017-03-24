package pl.gda.pg.student.project.server.helpers;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import com.badlogic.gdx.math.Vector2;

import pl.gda.pg.student.project.kryonetcommon.IdSupplier;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.server.objects.Box;
import pl.gda.pg.student.project.server.objects.Wall;
import pl.gda.pg.student.project.server.states.ServerPlayState;

public class FieldInitializer {

    private final static int TILE_SIZE = 27;


	public static Map<Long, GameObject> initializeField(ServerPlayState playState)
	{
		Map<Long, GameObject> objects = new HashMap<Long, GameObject>();

		for (int i = 0; i <= 30; i++)
        {
			for (int j = 0; j <= 20; j++)
            {
				if (i == 0 || j == 0 || i == 30 || j == 20)
				{
					long id = IdSupplier.getId();
					Wall wall = new Wall(playState, new Vector2(TILE_SIZE * i, TILE_SIZE * j));
					wall.setId(id);
					objects.put(id, wall);
				} else if (i % 2 == 0 && j % 2 == 0)
				{
					long id = IdSupplier.getId();
					Wall wall = new Wall(playState, new Vector2(TILE_SIZE * i, TILE_SIZE * j));
					wall.setId(id);
					objects.put(id, wall);
				} else
				{
					Random rand = new Random();
					if (rand.nextInt(2) == 0)
					{
						long id = IdSupplier.getId();
						Box box = new Box(playState, new Vector2(TILE_SIZE * i, TILE_SIZE * j));
						box.setId(id);
						objects.put(id, box);
                    }
				}
            }
        }
       return objects;
    }


}
