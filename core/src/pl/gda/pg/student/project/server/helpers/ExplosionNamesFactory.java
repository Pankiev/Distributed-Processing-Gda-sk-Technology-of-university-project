package pl.gda.pg.student.project.server.helpers;

import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.libgdxcommon.exception.GameException;

public class ExplosionNamesFactory {

    private final static int TILE_SIZE = 27;

    public static String produce(Vector2 translationChunk, boolean isEnd)
    {
        if(isEnd){
            if (translationChunk.x == -TILE_SIZE && translationChunk.y == 0) {
                return "wyb_p.png";
            } else if (translationChunk.x == TILE_SIZE && translationChunk.y == 0) {
                return "wyb_l.png";
            } else if (translationChunk.x == 0 && translationChunk.y == -TILE_SIZE) {
                return "wyb_g.png";
            } else if (translationChunk.x == 0 && translationChunk.y == TILE_SIZE) {
                return "wyb_d.png";
            }
        }
        else {
            if (translationChunk.x == -TILE_SIZE && translationChunk.y == 0) {
                return "wyb_lp.png";
            } else if (translationChunk.x == TILE_SIZE && translationChunk.y == 0) {
                return "wyb_lp.png";
            } else if (translationChunk.x == 0 && translationChunk.y == -TILE_SIZE) {
                return "wyb_gd.png";
            } else if (translationChunk.x == 0 && translationChunk.y == TILE_SIZE) {
                return "wyb_gd.png";
            }
        }

        throw new ThereIsNoSuchTextureException(translationChunk, isEnd);
    }

    private static class ThereIsNoSuchTextureException extends GameException
    {
        public ThereIsNoSuchTextureException(Vector2 translationChunk, boolean isEnd)
        {
            super("Translation: " + translationChunk + " IsEnd: " + isEnd);
        }
    }

}
