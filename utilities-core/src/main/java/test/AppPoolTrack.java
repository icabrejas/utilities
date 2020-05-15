package test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.utilities.core.UtilitiesConcurrent;

public class AppPoolTrack {

	public static Logger logger = LoggerFactory.getLogger(AppPoolTrack.class);

	public static void main(String[] args) throws InterruptedException, ExecutionException {

		ExecutorService pool = UtilitiesConcurrent.newThreadPool(3);
		List<Future<String>> futures = new ArrayList<>();
		for (int i = 0; i < 10; i++) {
			futures.add(UtilitiesConcurrent.submitAndTrack(pool, () -> {
				UtilitiesConcurrent.sleepQuietly(500);
				return "done";
			}));
		}
		for (Future<String> future : futures) {
			UtilitiesConcurrent.sleepQuietly(200);
			System.out.println(future);
		}
		System.exit(0);
	}

}
