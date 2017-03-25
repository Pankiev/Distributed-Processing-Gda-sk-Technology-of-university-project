package pl.gda.pg.student.project.server.helpers;

import com.badlogic.gdx.math.Vector2;
import pl.gda.pg.student.project.kryonetcommon.IdSupplier;
import pl.gda.pg.student.project.libgdxcommon.State;
import pl.gda.pg.student.project.server.objects.powerUps.NumberOfBombsPowerUp;
import pl.gda.pg.student.project.server.objects.powerUps.PowerUp;
import pl.gda.pg.student.project.server.objects.powerUps.RangePowerUp;
import pl.gda.pg.student.project.server.objects.powerUps.SpeedUpPowerUp;

import java.util.Random;

public class RandomPowerUp {

    public PowerUp getRandomPowerUp(State state, Vector2 boxPosition){
        Random random = new Random();
        PowerUp powerUp;
        int rand = random.nextInt(3);
        if(rand == 0){
            powerUp = new NumberOfBombsPowerUp(state, boxPosition);
        }else if(rand == 1){
            powerUp = new RangePowerUp(state, boxPosition);
        }else{
            powerUp = new SpeedUpPowerUp(state, boxPosition);
        }
        powerUp.setId(IdSupplier.getId());

        return powerUp;
    }


}
