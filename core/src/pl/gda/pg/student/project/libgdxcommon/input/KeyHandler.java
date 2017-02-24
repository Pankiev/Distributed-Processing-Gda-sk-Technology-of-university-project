package pl.gda.pg.student.project.libgdxcommon.input;

public interface KeyHandler
{
	public static final KeyHandler EMPTY = () -> {};

	void handle();
}
