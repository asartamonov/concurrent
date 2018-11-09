package caught;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.List;

public class ExceptionCatcher implements UncaughtExceptionHandler {

	private List<String> errList;

	public ExceptionCatcher(List<String> errList) {
		super();
		this.errList = errList;
	}

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("Catched from: " + t.getName());
		errList.add(t.getName() + " " + e.toString());
	}
}
