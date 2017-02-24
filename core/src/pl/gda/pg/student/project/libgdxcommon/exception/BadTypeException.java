package pl.gda.pg.student.project.libgdxcommon.exception;

public class BadTypeException extends GameException
{
	public BadTypeException(String expected, String actual)
	{
		super("Bad type, " + expected + " expected, but got " + actual);
	}

}
