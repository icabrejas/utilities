package org.utilities.io.s3;

import java.util.function.Supplier;

import org.utilities.core.lang.iterable.IterablePipe;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class S3Bucket {

	private AmazonS3 s3Client;
	private String bucketName;

	public S3Bucket(String accessKey, String secretKey, String bucketName) {
		this.s3Client = UtilitiesS3.newS3Client(accessKey, secretKey);
		this.bucketName = bucketName;
	}

	public S3IOEntry get(GetObjectRequest request, int trials, long waitTime) {
		return UtilitiesS3.get(request, s3Client, trials, waitTime);
	}

	public S3IOEntry get(S3ObjectSummary objectSummary, int trials, long waitTime) {
		return UtilitiesS3.get(objectSummary, s3Client, trials, waitTime);
	}

	public S3IOEntry get(String key, int trials, long waitTime) {
		return UtilitiesS3.get(s3Client, bucketName, key, trials, waitTime);
	}

	public IterableS3 getAll(Supplier<ListObjectsV2Request> request, int trials, long waitTime) {
		return UtilitiesS3.getAll(s3Client, request, trials, waitTime);
	}

	public IterableS3 getAllByPrefix(String prefix, int trials, long waitTime) {
		return UtilitiesS3.getAllByPrefix(s3Client, bucketName, prefix, trials, waitTime);
	}

	public IterablePipe<String> dir(String prefix) {
		return UtilitiesS3.dir(s3Client, bucketName, prefix);
	}

}
