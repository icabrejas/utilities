package org.utilities.io.s3;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.utilities.core.lang.iterable.IterablePipe;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ListObjectsV2Result;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class UtilitiesS3 {

	public static final String DELIMITER = "/";

	public static AmazonS3 newS3Client(String accessKey, String secretKey) {
		AWSCredentialsProvider credentialsProvider = newCredentialsProvider(accessKey, secretKey);
		return AmazonS3ClientBuilder.standard()
				.withCredentials(credentialsProvider)
				.withRegion(Regions.EU_WEST_1)
				.build();
	}

	public static AWSCredentialsProvider newCredentialsProvider(String accessKey, String secretKey) {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		return new AWSStaticCredentialsProvider(credentials);
	}

	public static S3Object getObject(GetObjectRequest request, AmazonS3 s3Client) {
		return s3Client.getObject(request);
	}

	public static S3Object getObject(S3ObjectSummary summary, AmazonS3 s3Client) {
		String bucketName = summary.getBucketName();
		String key = summary.getKey();
		return getObject(s3Client, bucketName, key);
	}

	public static S3Object getObject(AmazonS3 s3Client, String bucketName, String key) {
		return s3Client.getObject(bucketName, key);
	}

	public static S3IOEntry get(GetObjectRequest request, AmazonS3 s3Client, int trials, long waitTime) {
		return S3IOEntry.newInstance(s3Client, request, trials, waitTime);
	}

	public static S3IOEntry get(S3ObjectSummary summary, AmazonS3 s3Client, int trials, long waitTime) {
		return S3IOEntry.newInstance(s3Client, summary, trials, waitTime);
	}

	public static S3IOEntry get(AmazonS3 s3Client, String bucketName, String key, int trials, long waitTime) {
		return S3IOEntry.newInstance(s3Client, bucketName, key, trials, waitTime);
	}

	public static IterableS3 getAll(AmazonS3 s3Client, Supplier<ListObjectsV2Request> request, int trials,
			long waitTime) {
		return new IterableS3(s3Client, request, trials, waitTime);
	}

	public static IterableS3 getAllByPrefix(AmazonS3 s3Client, String bucketName, String prefix, int trials,
			long waitTime) {
		return getAll(s3Client, () -> new ListObjectsV2Request().withBucketName(bucketName)
				.withPrefix(prefix), trials, waitTime);
	}

	public static IterablePipe<String> dir(AmazonS3 s3Client, String bucketName, String prefix) {
		ListObjectsV2Request request = new ListObjectsV2Request().withBucketName(bucketName)
				.withPrefix(prefix.endsWith(DELIMITER) ? prefix : (prefix + DELIMITER))
				.withDelimiter(DELIMITER);
		List<String> commonPrefixes = new ArrayList<>();
		ListObjectsV2Result pageMarker = s3Client.listObjectsV2(request);
		if (pageMarker.isTruncated()) {
			commonPrefixes.addAll(pageMarker.getCommonPrefixes());
			request.setContinuationToken(pageMarker.getNextContinuationToken());
		}
		return IterablePipe.create(commonPrefixes);
	}

}
