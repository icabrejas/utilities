package org.utilities.core;

public class UtilitiesRuntime {

	public static int availableProcessors() {
		return Runtime.getRuntime()
				.availableProcessors();
	}

	public static Memory memoryMonitor() {
		return new Memory();
	}

	public static void monitoryMemory(long timeoutPeriod) {
		Memory memory = new Memory();
		UtilitiesConcurrent.setInterval(() -> System.out.println(memory), timeoutPeriod);
	}

	public static class Memory {

		private Runtime runtime;

		public double free() {
			return runtime.freeMemory();
		}

		public double total() {
			return runtime.totalMemory();
		}

		public double max() {
			return runtime.maxMemory();
		}

		@Override
		public String toString() {
			return new StringBuilder().append("")
					.append("Free: ")
					.append(String.format("%.3f ", parse(free())))
					.append("GB (")
					.append(String.format("%.1f", (100 * free() / max())) + "%)")
					.append(", ")
					.append("Total: ")
					.append(String.format("%.3f ", parse(total())))
					.append("GB (")
					.append(String.format("%.1f", (100 * total() / max())) + "%)")
					.append(", ")
					.append("Max: ")
					.append(String.format("%.3f ", parse(max())))
					.append("GB")
					.toString();
		}

		private double parse(double bytes) {
			return bytes /= (1024 * 1024 * 1024L);
		}

	}

}
