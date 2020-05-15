package test;

import org.utilities.core.UtilitiesConcurrent;
import org.utilities.core.queue.Queue;

public class AppQueue {

	public static void main(String[] args) {
		Queue<String> queue = new Queue<>();
		queue.subscribe("1", msg -> System.out.println("1." + msg));
		queue.subscribe("2", msg -> System.out.println("2." + msg));

		System.out.println("-------- none ----------");
		queue.expirationTime(null);
		queue.expirationSize(null);
		appendMessages(queue);

		System.out.println("-------- by size ----------");
		queue.expirationTime(null);
		queue.expirationSize(6);
		appendMessages(queue);

		System.out.println("-------- by time ----------");
		queue.expirationTime(1000L);
		queue.expirationSize(null);
		appendMessages(queue);

		System.out.println("-------- by size and time ----------");
		queue.expirationTime(1000L);
		queue.expirationSize(6);
		appendMessages(queue);
		System.out.println("-------- remove time ----------");
		queue.expirationTime(null);
		appendMessages(queue);
		System.out.println("-------- remove size ----------");
		queue.expirationSize(null);
		appendMessages(queue);

		System.out.println("-------- new consumers 1 ----------");
		queue.subscribe("1", msg -> System.out.println("3." + msg));
		System.out.println("-------- new consumers 2 ----------");
		queue.subscribe("2", msg -> System.out.println("2." + msg));

		System.exit(0);
	}

	private static void appendMessages(Queue<String> queue) {
		for (int i = 0; i < 10; i++) {
			if (Math.random() < 0.5) {
				queue.append("1", "1");
			} else {
				queue.append("2", "2");
			}
			UtilitiesConcurrent.sleepQuietly(500);
		}
	}

}
