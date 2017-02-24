package pl.gda.pg.student.project.libgdxcommon.input;

import java.util.HashMap;

public class KeyHandlers extends HashMap<String, KeyHandler>
{

	@Override
	public KeyHandler get(Object firstClassNameWordString)
	{
		KeyHandler keyHandler = super.get(firstClassNameWordString);
		if (keyHandler == null)
			return KeyHandler.EMPTY;
		return keyHandler;
	}

}
