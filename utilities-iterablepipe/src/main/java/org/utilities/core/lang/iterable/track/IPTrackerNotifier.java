package org.utilities.core.lang.iterable.track;

public class IPTrackerNotifier<T> {

	private int serialNumber;
	private IPTracker<T> tracker;
	private boolean started = false;
	private boolean ended = false;

	public IPTrackerNotifier(int serialNumber, IPTracker<T> tracker) {
		this.serialNumber = serialNumber;
		this.tracker = tracker;
	}

	public void notifyStart() {
		if (!started) {
			tracker.onStart(serialNumber);
			started = true;
		}
	}

	public void notifyNext(T next) {
		if (!started) {
			throw new Error();
		}
		tracker.onNext(serialNumber, next);
	}

	public void notifyEnd() {
		if (!ended) {
			tracker.onEnd(serialNumber);
			ended = true;
		}
	}

}
