package org.utilities.io.s3;

import java.io.InputStream;

import org.utilities.core.util.function.SupplierPlus;
import org.utilities.io.EntryIO;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public abstract class EntryS3 implements EntryIO<String> {

	public static EntryS3 newInstance(AmazonS3 s3Client, S3ObjectSummary summary) {
		return new Summary(s3Client, summary);
	}

	public static EntryS3 newInstance(AmazonS3 s3Client, GetObjectRequest request) {
		return new Request(s3Client, request);
	}

	public static EntryS3 newInstance(AmazonS3 s3Client, String bucketName, String key) {
		GetObjectRequest request = new GetObjectRequest(bucketName, key);
		return newInstance(s3Client, request);
	}

	private static class Request extends EntryS3 {

		private AmazonS3 s3Client;
		private GetObjectRequest request;

		public Request(AmazonS3 s3Client, GetObjectRequest request) {
			this.s3Client = s3Client;
			this.request = request;
		}

		@Override
		public String getInfo() {
			return request.getKey();
		}

		@Override
		public InputStream getContent() {
			return UtilitiesS3.get(request, s3Client)
					.getObjectContent();
		}

	}

	private static class Summary extends EntryS3 {

		private AmazonS3 s3Client;
		private S3ObjectSummary summary;

		public Summary(AmazonS3 s3Client, S3ObjectSummary summary) {
			this.s3Client = s3Client;
			this.summary = summary;
		}

		@Override
		public InputStream getContent() {
			return SupplierPlus.Noisy.tryToGet(this::getObjectContent, 3);
		}

		private S3ObjectInputStream getObjectContent() {
			return UtilitiesS3.get(summary, s3Client)
					.getObjectContent();
		}

		@Override
		public String getInfo() {
			return summary.getKey();
		}
	}

}
