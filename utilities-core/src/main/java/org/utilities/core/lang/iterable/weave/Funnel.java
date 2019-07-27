package org.utilities.core.lang.iterable.weave;

import java.util.Comparator;
import java.util.Map;

import org.utilities.core.lang.iterable.observer.Observer;
import org.utilities.core.util.lambda.LambdaInt;

public interface Funnel<T> extends Observer<T>, Comparator<T> {

	public static <T> Funnel<Map.Entry<Integer, T>> sequential(int k) {
		LambdaInt idx = new LambdaInt();
		Comparator<Map.Entry<Integer, T>> comparator = (t1, t2) -> {
			int idx1 = Math.abs(t1.getKey() - idx.get());
			int idx2 = Math.abs(t2.getKey() - idx.get());
			return Integer.compare(idx1, idx2);
		};
		return new FunnelImpl<>(comparator)//
				.start(() -> idx.set(0))
				.next(t -> {
					idx.set((idx.get() + 1) % k);
				});
	}

}
