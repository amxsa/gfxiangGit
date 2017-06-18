package cn.cellcom.szba.sql;

import java.io.IOException;
import java.util.Timer;

import cn.cellcom.szba.run.SqlConsumeTime;

public class SqlConsumeTimeTest {
	public static void main(String[] args) throws IOException {
		Timer timer = new Timer();
		timer.schedule(new SqlConsumeTime(), SqlConsumeTime.getFixedTime(), SqlConsumeTime.getMilliseconds());
	}
}
