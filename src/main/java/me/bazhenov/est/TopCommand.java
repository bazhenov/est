package me.bazhenov.est;

import com.beust.jcommander.Parameter;
import com.clearspring.analytics.stream.Counter;
import com.clearspring.analytics.stream.StreamSummary;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static java.lang.System.out;
import static me.bazhenov.est.App.closeQuietly;

public class TopCommand implements Runnable {

	public static final String COMMAND_NAME = "top";

	@Parameter(names = "-c", description = "maximum size (larger capacities improve accuracy)")
	private int capacity = 1000000;

	@Parameter(names = "-k", description = "number of elements to track")
	private int k = 10;

	@Parameter(names = "-r", description = "omit headers and additional information from output")
	private boolean raw;

	public void run() {
		StreamSummary<String> summary = new StreamSummary<String>(capacity);
		int totals = 0;
		BufferedReader r = new BufferedReader(new InputStreamReader(System.in));
		try {
			String line;
			while ((line = r.readLine()) != null) {
				summary.offer(line);
				totals++;
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		} finally {
			closeQuietly(r);
		}

		if (!raw) {
			out.println(String.format("%3s %9s %5s %6s  %s", "#", "cnt.", "pr.%", "err.%", "item"));
			out.println("============================================");
			int i = 1;
			int sum = 0;
			for (Counter<String> c : summary.topK(k)) {
				long count = c.getCount();
				float error = ((float) c.getError() / count) * 100;
				sum += count;
				float cdf = ((float) sum / totals) * 100;
				if (cdf > 100) cdf = 100;
				if (error >= 0.01) {
					out.println(String.format("%3d %9d %5.1f %6.1f  %s", i++, count, cdf, error, c.getItem()));
				} else {
					out.println(String.format("%3d %9d %5.1f %6d  %s", i++, count, cdf, 0, c.getItem()));
				}
			}
		} else {
			for (Counter<String> c : summary.topK(k)) {
				out.println(c.getItem());
			}
		}
	}
}
