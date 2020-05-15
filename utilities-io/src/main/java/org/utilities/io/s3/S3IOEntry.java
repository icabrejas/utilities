package org.utilities.io.s3;

import java.io.InputStream;

import org.utilities.core.util.function.SupplierPlus;
import org.utilities.io.IOEntry;
import org.utilities.io.UtilitiesIO;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public abstract class S3IOEntry implements IOEntry {

	public static S3IOEntry newInstance(AmazonS3 s3Client, S3ObjectSummary summary, int trials, long waitTime) {
		return new Summary(s3Client, summary, trials, waitTime);
	}

	public static S3IOEntry newInstance(AmazonS3 s3Client, GetObjectRequest request, int trials, long waitTime) {
		return new Request(s3Client, request, trials, waitTime);
	}

	public static S3IOEntry newInstance(AmazonS3 s3Client, String bucketName, String key, int trials, long waitTime) {
		GetObjectRequest request = new GetObjectRequest(bucketName, key);
		return newInstance(s3Client, request, trials, waitTime);
	}

	private static class Request extends S3IOEntry {

		private AmazonS3 s3Client;
		private GetObjectRequest request;
		private int trials;
		private long waitTime;

		public Request(AmazonS3 s3Client, GetObjectRequest request, int trials, long waitTime) {
			this.s3Client = s3Client;
			this.request = request;
			this.trials = trials;
			this.waitTime = waitTime;
		}

		@Override
		public InputStream get() {
			return SupplierPlus.Noisy.tryToGet(this::getObjectContent, trials, waitTime);
		}

		private InputStream getObjectContent() {
			S3ObjectInputStream inputStream = UtilitiesS3.getObject(request, s3Client)
					.getObjectContent();
			return UtilitiesIO.toByteArrayInputStream(inputStream);
		}

	}

	private static class Summary extends S3IOEntry {

		private AmazonS3 s3Client;
		private S3ObjectSummary summary;
		private int trials;
		private long waitTime;

		public Summary(AmazonS3 s3Client, S3ObjectSummary summary, int trials, long waitTime) {
			this.s3Client = s3Client;
			this.summary = summary;
			this.trials = trials;
			this.waitTime = waitTime;
		}

		@Override
		public InputStream get() {
			return SupplierPlus.Noisy.tryToGet(this::getObjectContent, trials, waitTime);
		}

		private InputStream getObjectContent() {
			S3ObjectInputStream inputStream = UtilitiesS3.getObject(summary, s3Client)
					.getObjectContent();
			return UtilitiesIO.toByteArrayInputStream(inputStream);
		}

	}

}
