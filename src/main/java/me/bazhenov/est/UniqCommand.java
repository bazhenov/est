package me.bazhenov.est;

import com.beust.jcommander.Parameter;
import com.clearspring.analytics.stream.cardinality.ICardinality;
import com.clearspring.analytics.stream.cardinality.LinearCounting;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static me.bazhenov.est.App.closeQuietly;

public class UniqCommand implements Runnable {

	public static final String COMMAND_NAME = "uniq";

	@Parameter(names = "-s", description = "size of the binary mask")
	private int size = 1000000;

	public void run() {
		ICardinality counter = new LinearCounting(size);
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		try {
			String line;
			while ((line = r.readLine()) != null) {
				counter.offer(line);
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			closeQuietly(r);
		}
		System.out.println(counter.cardinality());
	}
}
