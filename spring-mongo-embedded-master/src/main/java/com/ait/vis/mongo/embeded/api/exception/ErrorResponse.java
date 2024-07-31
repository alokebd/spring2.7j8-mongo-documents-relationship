package com.ait.vis.mongo.embeded.api.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ErrorResponse {
	private final int statusCode;
	private final String message;
}
