package pl.gda.pg.student.project.server.states;

import pl.gda.pg.student.project.libgdxcommon.input.InputProcessorAdapter;
import pl.gda.pg.student.project.libgdxcommon.input.KeyHandler;
import pl.gda.pg.student.project.server.objects.ShowdownObject;

public class ShowdownInputHandler extends InputProcessorAdapter
{
    private ShowdownObject object;

    public ShowdownInputHandler(ShowdownObject object)
    {
        this.object = object;
    }
    
    public class WKeyHandler implements KeyHandler
    {

        @Override
        public void handle()
        {
            object.moveUp();
            
        }
        
    }
    
}
