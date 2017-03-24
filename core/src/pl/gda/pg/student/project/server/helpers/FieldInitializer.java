package pl.gda.pg.student.project.server.helpers;


import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.kryonetcommon.IdSupplier;
import pl.gda.pg.student.project.libgdxcommon.objects.GameObject;
import pl.gda.pg.student.project.server.objects.Box;
import pl.gda.pg.student.project.server.objects.Wall;
import pl.gda.pg.student.project.server.states.ServerPlayState;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class FieldInitializer {

    private final static int TILE_SIZE = 27;


    public static Map<Long, GameObject> initializeField(ServerPlayState playState){
        Map<Long, GameObject> objects = Collections.synchronizedMap(new HashMap<Long, GameObject>());
        Wall wall;
        Box box;
        long id;
        for (int i = 0; i <= 31; i++)
        {
            for (int j = 0; j <= 21; j++)
            {
                if(i == 0 || j == 0 || i == 30 || j == 20){
                    id = IdSupplier.getId();
                    wall = new Wall(playState, new Vector2(TILE_SIZE * i, TILE_SIZE * j));
                    wall.setId(id);
                    objects.put(id, wall);
                }
                else if (i % 2 == 0 && j % 2 == 0)
                {
                    id = IdSupplier.getId();
                    wall = new Wall(playState, new Vector2(TILE_SIZE * i, TILE_SIZE * j));
                    wall.setId(id);
                    objects.put(id, wall);
                }
                else
                {
                    Random rand = new Random();
                    if(rand.nextInt(2) == 0) {
                        id = IdSupplier.getId();
                        box = new Box(playState, new Vector2(TILE_SIZE * i, TILE_SIZE * j));
                        box.setId(id);
                        objects.put(id, box);
                    }
                }
            }
        }
       return objects;
    }


}
