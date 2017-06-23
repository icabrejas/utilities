package org.utilities.io.s3;

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
import com.amazonaws.services.s3.model.ListObjectsRequest;
import com.amazonaws.services.s3.model.ListObjectsV2Request;
import com.amazonaws.services.s3.model.ObjectListing;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectSummary;

public class UtilitiesS3 {

	public static final String DELIMITER = "/";

	public static AmazonS3 newS3Client(String accessKey, String secretKey) {
		AWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
		AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);
		return AmazonS3ClientBuilder.standard()
				.withCredentials(credentialsProvider)
				.withRegion(Regions.EU_WEST_1)
				.build();
	}

	public static S3Object get(GetObjectRequest request, AmazonS3 s3Client) {
		return s3Client.getObject(request);
	}

	public static S3Object get(S3ObjectSummary objectSummary, AmazonS3 s3Client) {
		String bucketName = objectSummary.getBucketName();
		String key = objectSummary.getKey();
		return get(bucketName, key, s3Client);
	}

	public static S3Object get(String bucketName, String key, AmazonS3 s3Client) {
		GetObjectRequest request = new GetObjectRequest(bucketName, key);
		return s3Client.getObject(request);
	}

	public static IterableS3 getAll(AmazonS3 s3Client, Supplier<ListObjectsV2Request> request) {
		return new IterableS3(s3Client, request);
	}

	public static IterableS3 getAllByPrefix(AmazonS3 s3Client, String bucketName, String prefix) {
		return getAll(s3Client, () -> new ListObjectsV2Request().withBucketName(bucketName)
				.withPrefix(prefix));
	}

	public static IterablePipe<String> dir(AmazonS3 s3Client, String bucketName, String prefix) {
		ListObjectsRequest listObjectsRequest = new ListObjectsRequest().withBucketName(bucketName)
				.withPrefix(prefix.endsWith(DELIMITER) ? prefix : (prefix + DELIMITER))
				.withDelimiter(DELIMITER);
		ObjectListing objects = s3Client.listObjects(listObjectsRequest);
		return IterablePipe.newInstance(objects.getCommonPrefixes());
	}

}