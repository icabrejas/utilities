package org.utilities.io.s3;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

import org.utilities.core.lang.iterable.IterablePipe;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class IterableS3 implements IterablePipe<EntryS3> {

	private AmazonS3 s3Client;
	private Supplier<ListObjectsV2Request> request;
	private int trials;
	private long waitTime;

	public IterableS3(AmazonS3 s3Client, Supplier<ListObjectsV2Request> request, int trials, long waitTime) {
		this.s3Client = s3Client;
		this.request = request;
		this.trials = trials;
		this.waitTime = waitTime;
	}

	@Override
	public Iterator<EntryS3> iterator() {
		return new It(s3Client, request.get(), trials, waitTime);
	}

	private static class It implements Iterator<EntryS3> {

		private AmazonS3 s3Client;
		private ListObjectsV2Request request;
		private ListObjectsV2Result pageMarker;
		private Iterator<S3ObjectSummary> pageContent;
		private int trials;
		private long waitTime;

		public It(AmazonS3 s3Client, ListObjectsV2Request request, int trials, long waitTime) {
			this.s3Client = s3Client;
			this.request = request;
			this.trials = trials;
			this.waitTime = waitTime;
			nextPage();
		}

		private void nextPage() {
			this.pageMarker = this.s3Client.listObjectsV2(this.request);
			this.pageContent = pageMarker.getObjectSummaries()
					.iterator();
		}

		@Override
		public boolean hasNext() {
			if (!pageContent.hasNext() && pageMarker.isTruncated()) {
				request.setContinuationToken(pageMarker.getNextContinuationToken());
				nextPage();
			}
			return pageContent.hasNext();
		}

		@Override
		public EntryS3 next() {
			if (!hasNext()) {
				throw new NoSuchElementException();
			}
			return EntryS3.newInstance(s3Client, pageContent.next(), trials, waitTime);
		}

	}

}
