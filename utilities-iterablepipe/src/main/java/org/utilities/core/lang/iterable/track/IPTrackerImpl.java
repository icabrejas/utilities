package org.utilities.core.lang.iterable.track;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.utilities.core.util.function.AppendOpt;
import org.utilities.core.util.function.BiConsumerPlus;
import org.utilities.core.util.function.ConsumerPlus;
import org.utilities.core.util.function.RunnablePlus;

public class IPTrackerImpl<T> implements IPTracker<T> {

	private Consumer<Integer> start;
	private BiConsumer<Integer, T> next;
	private Consumer<Integer> end;

	public IPTrackerImpl() {
		this.start = ConsumerPlus.dummy();
		this.next = BiConsumerPlus.dummy();
		this.end = ConsumerPlus.dummy();
	}

	public IPTrackerImpl(Consumer<Integer> start, BiConsumer<Integer, T> next, Consumer<Integer> end) {
		this.start = start;
		this.next = next;
		this.end = end;
	}

	public IPTrackerImpl(IPTracker<T> tracker) {
		this.start = tracker::onStart;
		this.next = tracker::onNext;
		this.end = tracker::onEnd;
	}

	@Override
	public void onStart(int serialNumber) {
		start.accept(serialNumber);
	}

	@Override
	public void onNext(int serialNumber, T next) {
		this.next.accept(serialNumber, next);
	}

	@Override
	public void onEnd(int serialNumber) {
		end.accept(serialNumber);
	}

	public IPTrackerImpl<T> start(Consumer<Integer> start) {
		this.start = start;
		return this;
	}

	public IPTrackerImpl<T> start(Runnable start) {
		return start(RunnablePlus.parseConsumer(start));
	}

	public IPTrackerImpl<T> start(AppendOpt appendOpt, Consumer<Integer> start) {
		this.start = appendOpt.append(this.start, start);
		return this;
	}

	public IPTrackerImpl<T> start(AppendOpt appendOpt, Runnable start) {
		return start(appendOpt, RunnablePlus.parseConsumer(start));
	}

	public IPTrackerImpl<T> next(AppendOpt appendOpt, BiConsumer<Integer, T> next) {
		this.next = appendOpt.append(this.next, next);
		return this;
	}

	public IPTrackerImpl<T> next(AppendOpt appendOpt, Consumer<T> next) {
		return next(appendOpt, (serialNumber, t) -> next.accept(t));
	}

	public IPTrackerImpl<T> next(BiConsumer<Integer, T> next) {
		this.next = next;
		return this;
	}

	public IPTrackerImpl<T> next(Consumer<T> next) {
		return next((serialNumber, t) -> next.accept(t));
	}

	public IPTrackerImpl<T> next(AppendOpt appendOpt, Runnable next) {
		return next(appendOpt, (serialNumber, t) -> next.run());
	}

	public IPTrackerImpl<T> end(AppendOpt appendOpt, Consumer<Integer> end) {
		this.end = appendOpt.append(this.end, end);
		return this;
	}

	public IPTrackerImpl<T> end(AppendOpt appendOpt, Runnable start) {
		return end(appendOpt, RunnablePlus.parseConsumer(start));
	}

	public IPTrackerImpl<T> end(Consumer<Integer> end) {
		this.end = end;
		return this;
	}

	public IPTrackerImpl<T> end(Runnable start) {
		return end(RunnablePlus.parseConsumer(start));
	}

}
