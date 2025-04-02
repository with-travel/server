//package com.arom.with_travel.domain.chat.swagger;
//
//import com.arom.with_travel.domain.chat.dto.ChatroomRequest;
//import com.arom.with_travel.global.exception.response.ErrorResponse;
//import org.springframework.http.MediaType;
//
//import java.lang.annotation.Retention;
//import java.lang.annotation.RetentionPolicy;
//
//@Retention(RetentionPolicy.RUNTIME)
//@Operation(summary = "새로운 채팅방 생성", description = "새로운 채팅방을 생성해서 DB에 저장한다.")
//@ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "",
//                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                        schema = @Schema(implementation = ChatroomRequest.class))),
//        @ApiResponse(responseCode = "4xx", description = "요청 처리 실패",
//                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                        schema = @Schema(implementation = ErrorResponse.class)))
//})
//public @interface PostNewChat {
//}
