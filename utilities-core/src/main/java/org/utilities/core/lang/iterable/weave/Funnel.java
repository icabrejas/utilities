package org.utilities.core.lang.iterable.weave;

import java.util.Comparator;

import org.utilities.core.lang.iterable.observer.Observer;

public interface Funnel<T> extends Observer<T>, Comparator<T> {

}
